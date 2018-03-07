define(["require","zxui/lib","jquery",'editor'], function (require) {
	var HTMLDATA = {};
	function loadData(term){
		var sub = $("li.ol_subject_li_act").attr("data");
		var grade = $("li.li_act").attr("data");
		var sysRoleId = $("#js_role").val();
		var flago = $("#flago_thesis").val();
		var flags = $("#flags_thesis").val();
		var name = "s"+sub+"g"+grade+"t"+term;
		var html = HTMLDATA[name];
		if(typeof(sysRoleId) != "undefined" && typeof(grade) != "undefined"){
			if(!html){
				$.get("jy/check/thesis/tchlist?subjectId="+sub+"&gradeId="+grade+"&schoolTerm="+term+"&sysRoleId="+sysRoleId+"&flago="+flago+"&flags="+flags+"",function(data){
					HTMLDATA[name] = data;
					$(".check_teacher_bottom_inside_r").html(data);
				});
			}else{
				$(".check_teacher_bottom_inside_r").html(html);
			}
		}
	}
	function loadManagerData(term,sysRoleId,gradeId,spaceName){
		var name = "s"+sysRoleId+"t"+term+"g"+gradeId;
		var flago = $("#flago_thesis").val();
		var url;
		var param={};
		if(typeof(sysRoleId) != "undefined" || typeof(gradeId) != "undefined"){
			if(gradeId == -1){//不是备课组长
				param = {sysRoleId:sysRoleId,schoolTerm:term,flago:flago,spaceName:spaceName};
			}else{
				param = {sysRoleId:sysRoleId,schoolTerm:term,flago:flago,spaceName:spaceName,gradeId:gradeId};
			}
			var html = HTMLDATA[name];
			if(!html){
				$(".check_teacher_bottom_inside_r").load(_WEB_CONTEXT_+"/jy/check/thesis/tchlist",param,function(data){HTMLDATA[name] = data;});
			}else{
				$(".check_teacher_bottom_inside_r").html(html);
			}
		}
	}
	var check_thesis = function () {
		$(".semester").chosen({disable_search : true});
		$(".ol_grade li").click(function (){ 
			$(this).addClass("li_act").siblings().removeClass("li_act");  
		});
		$(".ol_subject_li").click(function (){ 
			$(this).addClass("ol_subject_li_act").siblings().removeClass("ol_subject_li_act");  
		});
	}
	$(document).ready(function(){
		check_thesis();
		$(".chosen-select-deselect").chosen({disable_search : true}); 
		var term = $('#tesis_term').val();
		var flago = $('#flago_thesis').val();
		if(flago=='m'){//加载管理者数据
			var sysRoleName = $("ol.m_sysrole li.ol_subject_li_act").text();
			var sysRoleId = $("ol.m_sysrole li.ol_subject_li_act").attr("data");
			var gradeId=-1;
			var gradeName="";
			var bkzzRole = $("#bkzz_role").val();
			if(sysRoleId == bkzzRole){
				$("#bkzz_grade").show();
				gradeId = $("#bkzz_grade .li_act").attr("data");
				gradeName = $("#bkzz_grade .li_act").text();
			}else{
				$("#bkzz_grade").hide();
			}
			var spaceName = gradeName+""+sysRoleName;
			if(typeof(sysRoleId) != "undefined"){
				loadManagerData(term,sysRoleId,gradeId,spaceName);
			}
		}else if(flago=='t'){//加载教师数据
			loadData(term);
		}
		$("ol.te_grade li").click(function(){
			$this = $(this);
			if($this.attr("css") != "li_act"){
				$(".te_grade li.li_act").removeClass("li_act");
				$this.addClass("li_act");
				var term = $("#fasciculeId").children('option:selected').val();
				loadData(term);
			}
		});
		
		$("ol.te_subject li").click(function(){
			$this = $(this);
			if($this.attr("css") != "ol_subject_li_act"){
				$("li.ol_subject_li_act").removeClass("ol_subject_li_act");
				$this.addClass("ol_subject_li_act");
				var term = $("#fasciculeId").children('option:selected').val();
				loadData(term);
			}
		});
		
		$("ol.m_sysrole li").click(function(){
			$this = $(this);
			if($this.attr("css") != "ol_subject_li_act"){
				$("ol.m_sysrole li.ol_subject_li_act").removeClass("ol_subject_li_act");
				$this.addClass("ol_subject_li_act");
				var term = $("#fasciculeId").children('option:selected').val();
				var sysRoleId = $(this).attr("data");
				var sysRoleName= $(this).text();
				var bkzzRole = $("#bkzz_role").val();
				var gradeId=-1;
				var gradeName="";
				if(sysRoleId == bkzzRole){
					$("#bkzz_grade").show();
					gradeId = $("#bkzz_grade .li_act").attr("data");
					gradeName = $("#bkzz_grade .li_act").text();
				}else{
					$("#bkzz_grade").hide();
				}
				var spaceName = gradeName+""+sysRoleName;
				loadManagerData(term,sysRoleId,gradeId,spaceName);
			}
		});
		$("ol.m_grade li").click(function(){
			$this = $(this);
			if($this.attr("css") != "li_act"){
				$("ol.m_grade li.li_act").removeClass("li_act");
				$this.addClass("li_act");
				var term = $("#fasciculeId").children('option:selected').val();
				var gradeId = $this.attr("data");
				var gradeName = $this.text();
				var sysRoleId = $("ol.m_sysrole li.ol_subject_li_act").attr("data");
				var sysRoleName = $("ol.m_sysrole li.ol_subject_li_act").text();
				var spaceName = gradeName+""+sysRoleName;
				loadManagerData(term,sysRoleId,gradeId,spaceName);
			}
		});
		$('#fasciculeId').change(function() {
			var term = $(this).children('option:selected').val();
			var flago = $('#flago_thesis').val();
			if(flago=='m'){//加载管理者数据
				var sysRoleName = $("ol.m_sysrole li.ol_subject_li_act").text();
				var sysRoleId = $("ol.m_sysrole li.ol_subject_li_act").attr("data");
				var gradeId = -1;
				var spaceName = sysRoleName;
				var isHidden = $("#bkzz_grade").is(':hidden');
				if(!isHidden){
					gradeId = $("#bkzz_grade .li_act").attr("data");
				}
				loadManagerData(term,sysRoleId,gradeId,spaceName);
			}else if(flago=='t'){//加载教师数据
				loadData(term);
			}
//			window.location.href = _WEB_CONTEXT_ + "/jy/check/thesis/index?&grade=${grade}&subject=${subject}&fasciculeId=" + p1;//页面跳转并传参 
		
		});
		$('#fasciculeId_detail').change( function() {
			var schoolTerm = $(this).children( 'option:selected') .val(); 
			var userId = $("#d_userId").val(); 
			var grade = $("#d_grade").val(); 
			var subject = $("#d_subject").val(); 
			var sysRoleId = $("#d_sysRoleId").val(); 
			var flago = $("#flago_thesis").val();
			var flags = $("#flags_thesis").val();
			window.location.href = _WEB_CONTEXT_ + "/jy/check/thesis/tch/"+userId+"?schoolTerm="+schoolTerm +
					"&flago="+flago+"&flags="+flags+"";//页面跳转并传参 
		
		});
		
		/************教学文章展示*****************/
	
		$(".download").click(function(){
			var name = $("#view_title").val();
			var resId = $("#view_resId").val();
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resId+"?filename="+encodeURI(name),"_self");
		});
		$('.ser_button').click(function(){
			var search=$('#searchFcx').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
		$("div.page_wprd_l .up_b").click(function(){
			loadNextData("up");
		});
		$("div.page_wprd_r .down_b").click(function(){
			loadNextData("down");
		});
		var li1 = $(".in_reconsideration_see_title_box .li_bg ");
		var window1 = $(".out_reconsideration_see_title_box ol");
		var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
		var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
		window1.css("width", li1.length*95+"px");  
		if(li1.length >= 7){
			left1.show();
			right1.show();
		}else{
			left1.css({"visibility":"hidden"});
			right1.css({"visibility":"hidden"});
		} 
		var lc1 = 0;
		var rc1 = li1.length-7; 
		left1.click(function(){
			if (lc1 < 1) {
				return;
			}
			lc1--;
			rc1++;
			window1.animate({left:'+=90px'}, 500);  
		});

		right1.click(function(){
			if (rc1 < 1){
				return;
			}
			lc1++;
			rc1--;
			window1.animate({left:'-=90px'}, 500); 
		});
		function loadNextData(param){
			var thesisIds = $("#thesis_ids").val();
			var thesisId = $("#thesisId").val();
			var index = $("#view_idindex").val();
			var thesisIdsStr = thesisIds.split(",");
			if(param=="up") index--;
			if(param=="down") index++;
			if(index >= thesisIdsStr.length){
				alert("已经是最后一篇了");
				index = ids.length-1;
				return false;
			}
			if(index < 0){
				alert("已经是第一篇了");
				index = 0;
				return false;
			}
			var resId = thesisIdsStr[index];
			var userId = $('#view_userId').val();
			$("#thesisId").val(resId);
			$("#view_form").attr("action",_WEB_CONTEXT_ + "/jy/check/thesis/tch/"+userId+"/view");
			$("#view_form").attr("method","post");
			$("#view_form").submit();
		}
		
		$("#other_view").attr("src","jy/scanResFile?to=true&resId="+$("#view_resId").val());
		/*******去详情列表页******/
		$(".go_viewa").click(function(obj){
			goViewOnclick(this);
		})
		var goViewOnclick = function(obj){
			var userId = $(obj).attr("data-userid");
			
			var ids = new Array();
			$("div .doc_dl").each(function(index,obj){
				ids.push($(obj).attr("data-resid"));
			})
			ids = ids.join(",");
			$("#view_id").val($(obj).attr("data-id"));
			$("#view_term").val($(obj).attr("data-term"));
			$("#view_flago").val($(obj).attr("data-flago"));
			$("#view_flags").val($(obj).attr("data-flags"));
			$("#view_ids").val(ids);
			$("#view_form").attr("action",_WEB_CONTEXT_ + "/jy/check/thesis/tch/"+userId+"/view");
			$("#view_form").attr("method","post");
			$("#view_form").submit();
			
		}
	});
	
});
