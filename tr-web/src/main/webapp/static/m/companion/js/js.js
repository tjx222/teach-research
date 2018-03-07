define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	var companion_strength;
	$(function(){
		init();
		bindEvent();
	});
    function init() {
    	companion_strength = new IScroll('#companion_strength',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	if($("#select_list p.act").text()!=null){
    		$("#role_span").html($("#select_list p.act").text());
    	}
    	if($("#select_list1 p.act").text()!=null){
    		$("#grade_span").html($("#select_list1 p.act").text());
    	}
    	if($("#select_list2 p.act").text()!=null){
    		$("#subject_span").html($("#select_list2 p.act").text());
    	}
    }
	function bindEvent() {
		$('.show_all').click(function (){ 
			$("#form_userName").val("");
			$('#form_roleId').val("");
			$('#form_subjectId').val("");
			$('#form_gradeId').val("");
			$('#form_profession').val("");
			$('#form_schoolAge').val("");
			$('#form_schoolName').val("");
			$('#pageForm').submit();
		});
		$('.search_btn').click(function (){ 
			var userName = $.trim($(".search").val());
			if(userName!=""){
				$("#form_userName").val(userName);
				$('#pageForm').submit();
			}
		});
		$('.companion_content_r header ul li').click(function (){ 
			$("#form_isSameSchool").val($(this).attr("data-isSameSchool"));
			$("#currentPage").val(1);
			$("#form_roleId").val("");
			$("#form_userName").val("");
			$("#form_subjectId").val("");
			$("#form_gradeId").val("");
			$("#form_profession").val("");
			$("#form_schoolAge").val("");
			$("#form_schoolName").val("");
			$("#form_isSearchPage").val("");
			$('#pageForm').submit();
		});
		$('.advance_option').click(function (){ 
			$('.mask').show();
			$('.advance_option_wrap').show();
		});
		$('.select').click(function (){
			$('.menu_list').toggle();
			$('.menu_list1').hide();
			$('.menu_list2').hide();
			if($('.menu_list').css("display") == 'block'){
      		   $('.advance_option_wrap1').show();   //如果元素为隐藏,则将它显现
      	    }else{
      		  $('.advance_option_wrap1').hide();     //如果元素为显现,则将其隐藏
      	    } 
			var select_list = new IScroll('#select_list',{
 	      		scrollbars:true,
 	      		mouseWheel:true,
 	      		fadeScrollbars:true,
 	      		click:true,
 	      	});
		});
		$('.select1').click(function (){
			$('.menu_list1').toggle();
			$('.menu_list').hide();
			$('.menu_list2').hide();
			if($('.menu_list1').css("display") == 'block'){
      		   $('.advance_option_wrap1').show();   //如果元素为隐藏,则将它显现
      	    }else{
      		  $('.advance_option_wrap1').hide();     //如果元素为显现,则将其隐藏
      	    } 
			var select_list1 = new IScroll('#select_list1',{
 	      		scrollbars:true,
 	      		mouseWheel:true,
 	      		fadeScrollbars:true,
 	      		click:true,
 	      	});
		});
		$('.select2').click(function (){
			$('.menu_list2').toggle();
			$('.menu_list').hide();
			$('.menu_list1').hide();
			if($('.menu_list2').css("display") == 'block'){
	      		   $('.advance_option_wrap1').show();   //如果元素为隐藏,则将它显现
	      	    }else{
	      		  $('.advance_option_wrap1').hide();     //如果元素为显现,则将其隐藏
	      	    } 
			var select_list2 = new IScroll('#select_list2',{
 	      		scrollbars:true,
 	      		mouseWheel:true,
 	      		fadeScrollbars:true,
 	      		click:true,
 	      	}); 
		});
		$('.advance_option_wrap1').click(function (){
			 $('.menu_list').hide();
			 $('.menu_list1').hide();
			 $('.menu_list2').hide();
		});
		$("#select_list p").click(function () {
			$(this).addClass("act").siblings().removeClass("act");
			$("#role_span").html($(this).text());
			$('#Advanced_search_roleId').val($(this).attr("roleId"));
			$(".menu_list ").hide();
		});
		$("#select_list1 p").click(function () {
    	 	$(this).addClass("act").siblings().removeClass("act");
    	 	$("#grade_span").html($(this).text());
    	 	$('#Advanced_search_grade').val($(this).attr("gradeId"));
    	 	$(".menu_list1 ").hide();
    	 });
		$("#select_list2 p").click(function () {
			$(this).addClass("act").siblings().removeClass("act");
			$("#subject_span").html($(this).text());
			$('#Advanced_search_subject').val($(this).attr("subjectId"));
			$(".menu_list2 ").hide();
		});
		$('.close').click(function (){
			$('.advance_option_wrap').hide();
			$('.mask').hide();
		});
		$('.companion_content_l dl').eq(1).click(function (){ 
			location.href = _WEB_CONTEXT_+"/jy/companion/companions/mymsg";
		});
		$('.companion_content_l dl').eq(2).click(function (){ 
			location.href = _WEB_CONTEXT_+"/jy/companion/companions/mycare";
		});
		$('.companions_img').click(function (){ 
			var companionid = $(this).attr("companionid");
			window.location.href = _WEB_CONTEXT_+"/jy/companion/companions/compshareindex?companionId="+companionid;
		});
		//加关注
		$('.btn_gz').click(function(){
			var userIdCompanion=$(this).attr('data-userIdCompanion');
			var url = _WEB_CONTEXT_+'/jy/companion/friends/'+userIdCompanion;
			$.post(url,{},function(result){
				if(result.result.code==1){
					successAlert('恭喜您，添加成功！',false,function(){
						location.href = _WEB_CONTEXT_+"/jy/companion/companions/index?isSameSchool="+$("#form_isSameSchool").val()+"&"+Math.random();;
					});
				}else{
					successAlert('添加关注失败：'+result.result.errorMsg);
				}
			},'json');
		});
		//搜索
		$('.btn_ser').click(function(){
			if(isNaN($('#Advanced_search_schooleAge').val())){
				successAlert('教龄必须是数字');
				return;
			}
			if(document.getElementById("Advanced_search_roleId")) $('#form_roleId').val($('#Advanced_search_roleId').val());
			$('#form_userName').val($('#Advanced_search_userName').val());
			if(document.getElementById("Advanced_search_subject")) $('#form_subjectId').val($('#Advanced_search_subject').val());
			if(document.getElementById("Advanced_search_grade")) $('#form_gradeId').val($('#Advanced_search_grade').val());
			$('#form_profession').val($('#Advanced_search_profession').val());
			$('#form_schoolAge').val($('#Advanced_search_schooleAge').val());
			if(document.getElementById("Advanced_search_schoolName")) $('#form_schoolName').val($('#Advanced_search_schoolName').val());
			$('#currentPage').val(1);
			$('#pageForm').submit();
		});	
	}
	window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").append(content.find(".com_str"));
		companion_strength.refresh();
		bindEvent();
	 };
});