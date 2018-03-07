define(["require","jquery"], function (require) {
	var $ = require("jquery");
	$(document).ready(function () {
		var typeId = $("#typeId").val();
		var year = $("#year").val();
		var spaceId = $("#search_form").find("input[name='spaceId']").val();
		var typeStr;
		window.parent.changeNav('管理查阅');
		if(typeId == 0){
			typeStr = "查阅教案";
		}else if(typeId == 1){
			typeStr = "查阅课件";
		}else if(typeId == 2){
			typeStr = "查阅反思";
		}else if(typeId == 8){
			typeStr = "查阅计划总结";
		}else if(typeId == 5){
			typeStr = "查阅集体备课";
		}else if(typeId == 10){
			typeStr = "查阅教学文章";
		}else if(typeId == 6){
			typeStr = "查阅听课记录";
		}
		if(typeId == 0 || typeId == 1 || typeId == 2 || typeId == 8 || typeId == 5 || typeId == 10 || typeId == 6){
			window.parent.changeNav(_WEB_CONTEXT_+"/jy/history/"+year+"/cygljl/index?id="+spaceId,typeStr);
		}
		/*点击左侧菜单切换*/
		$(".calendar_year_left_ul li a").click(function (){
			$(this).addClass("c_y_l_act").parent().siblings().children().removeClass("c_y_l_act");
		});
		/* 文本框提示语 */
		$('.ser_txt').placeholder({
			word : '输入关键词进行搜索'
		});
		
		/*下拉*/ 
		$(".class_teacher").chosen({disable_search : true});  
		$(".full_year").chosen({disable_search : true});  
		
		
		$(".resources_table tr th").last().css({"border-right":"none"});
		
		
		$(".look_up").click(function(){
			$('#look_up_dialog').dialog({
				width: 420,
				height: 317
			});
		});
		
		$(".menu_switch span").click(function (){
			$(this).addClass("menu_switch_act").siblings().removeClass("menu_switch_act");
			$(".resources_table_wrap .resources_table_wrap_tab").hide().eq($(".menu_switch span").index(this)).show().find(".menu_switch_nav span").removeClass("menu_switch_nav_act").eq(0).addClass("menu_switch_nav_act").show();  
			$(".resources_table_wrap_tab").eq($(".menu_switch span").index(this)).find(".check_lesson_wrap_tab").hide().eq(0).show();
			if(typeId == 0 || typeId == 1 || typeId == 5){//教案 课件  集体备课 
				var formObj = $(".resources_table_wrap_tab").eq($(".menu_switch span").index(this)).find("form"); 
				$("#search_form").find("input[name='flago']").val(formObj.find("input[name='flago']").val());
			}
		});
		
		$(".menu_switch_nav span").click(function (){
			$(this).addClass("menu_switch_nav_act").siblings().removeClass("menu_switch_nav_act");
			$(".menu_switch_nav_tab .check_lesson_wrap_tab").hide().eq($(".menu_switch_nav span").index(this)).show(); 
			var formObj = $(".check_lesson_wrap_tab").eq($(".menu_switch_nav span").index(this)).find("form"); 
			$("#search_form").find("input[name='flago']").val(formObj.find("input[name='flago']").val());
			$("#search_form").find("input[name='flags']").val(formObj.find("input[name='flags']").val());
		});  
	});
	function submitForm() {
		if ($('.ser_txt').attr("data-flag") == '0') {
			$('.ser_txt').val("");
		}
		$("#search_form").submit();
	}
	$("#sear_term").on("change",function(){
		submitForm();
	});
	$(".ser_btn").click(function(){
		submitForm();
	});
	$(".all").click(function(){
		$(this).parents("div .down_resource").find(".single_down").prop("checked",$(this).prop('checked'));
	});
	$(".single_down").click(function(){
		var checkedSize = $(this).parents("div .down_resource").find("input.single_down:checked").size();
		var size = $(this).parents("div .down_resource").find(".single_down").size();
		if(checkedSize==size){
			$(this).parents("div .down_resource").find(".all").prop("checked",true);
		}else{
			$(this).parents("div .down_resource").find(".all").prop("checked",false);
		}
	});
	//单个下载
	$(".download").click(function() {
		var check = $(this).parents("div .check_lesson").find("input[type='checkbox']").prop("checked");
		if(check){
			var resType = $(this).attr("data-type");
			if(resType){//下载计划总结
				var resid = $(this).attr("data-id");
				var filename = $(this).attr("data-name");
				window.open(_WEB_CONTEXT_+ "/jy/manage/res/download/"
						+ resid + "?filename=" + encodeURI(filename),
				"_self");
			}else{
				var resids = $(this).attr("data-id").split(",");
				window.open(_WEB_CONTEXT_+ "/jy/manage/res/batchDownload"+"?resids="+resids,"_self");
			}
		}else{
			alert("请选择下载资源");
			return false;
		}
	});
	$(".batch_edit").click(function() {
		var resids = [];
		var count = 0;
		$(this).parents("div .down_resource").find(".download").each(function(i,obj){
			var check = $(obj).parents("div .check_lesson").find("input[type='checkbox']").prop("checked");
			if(check){
				resids.push($(obj).attr("data-id").split(","));
				count++;
			}
		});
		if(count==0){
			alert("请选择下载资源");
			return false;
		}
		window.open(_WEB_CONTEXT_+ "/jy/manage/res/batchDownload"+"?resids="+resids,"_self");
	});
});