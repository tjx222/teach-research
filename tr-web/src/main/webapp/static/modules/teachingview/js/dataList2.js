define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
var $=require("jquery");
$(document).ready(function(){
	$(".managers_rethink_con_bigType li").each(function(){
		$(this).click(function(){
			$("#bigType").val($(this).attr("typeId"));
			$("#smallType").val("0");
			$("#currentPage").val("1");
			$("#pageForm").submit();
		})
	});
	$(".managers_rethink_con_smallType").each(function(index){
		$(this).find("li").click(function(){
			$("#smallType").val($(this).attr("typeId"));
			$("#bigType").val(index);
			$("#currentPage").val("1");
			$("#pageForm").submit();
		})
	});
	
	if($("#bigType").val()=='1'){
		$(".managers_rethink_con_bigType li").eq(1).addClass("li_active3").siblings().removeClass("li_active3");
		$(".managers_rethink_outBox_type").eq(1).addClass("show").siblings().removeClass("show");
	}
	if($("#smallType").val()=='1'){
		$(".managers_rethink_con_smallType").each(function(){
			$(this).find("li").eq(1).addClass("li_active4").siblings().removeClass("li_active4");
			$(this).next().find(".managers_rethink_intBox_type").eq(1).addClass("show").siblings().removeClass("show");
		});
	}
	
	
})




});