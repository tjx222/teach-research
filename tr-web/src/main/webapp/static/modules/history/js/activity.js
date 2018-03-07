define(["require","jquery"], function (require) {
	var $ = require("jquery");
	$(function(){
		init();
	}); 
	function init() {
		//加载下拉框
		$(".class_teacher").chosen({ disable_search : true });
		$(".full_year").chosen({ disable_search : true });
		
		$(".page_option").find("select").change(function(){
			$("#pageForm").submit();
		});
		
		$(".ser_btn").click(function(){
			$("#pageForm").submit();
		});
		
		$(".menu_switch").find("span").click(function(){
			$("#flags").val($(this).attr("flags"));
			$("#pageForm").submit();
		});
		
		$(".resources_table").find("span").click(function(){
			window.parent.dialog({
				url:_WEB_CONTEXT_+"/jy/check/infoIndex?flago=false&flags=false&resType=5&resId="+$(this).attr("activityId"),
				width:954,
				height:514,
				title:"查阅意见"
			})   
		});
	}
	
	window.dosearch = function(){
		if ($('.ser_txt').attr("data-flag") == '0') {
			$('.ser_txt').val("");
		}
	}
	
});