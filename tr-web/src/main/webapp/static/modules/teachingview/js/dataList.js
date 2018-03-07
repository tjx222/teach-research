define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
var $=require("jquery");
$(document).ready(function(){
	$(".managers_rethink_con_bigType li").each(function(){
		var self=this;
		$(this).click(function(){
			var index=$(self).index();
			$(self).addClass("li_active3").siblings().removeClass("li_active3");
			$(".managers_rethink_outBox_type").eq(index).addClass("show").siblings().removeClass("show");
		})
	});
	$(".managers_rethink_con_smallType li").each(function(){
		var self=this;
		$(this).click(function(){
			var index=$(self).index();
			$(self).addClass("li_active4").siblings().removeClass("li_active4");
			$(self).parent().next().find(".managers_rethink_intBox_type").eq(index).addClass("show").siblings().removeClass("show");
		})
	});
	
	if($("#bigType").val()=='1'){
		$(".managers_rethink_con_bigType li").eq(1).addClass("li_active3").siblings().removeClass("li_active3");
		$(".managers_rethink_outBox_type").eq(1).addClass("show").siblings().removeClass("show");
	}
	
	/**
	 * ----------集体备课部分-----------------------------------------------------------------------------
	 */
	$(".jitibeike_con_type").last().css("border-bottom","none")
	
	$(".article_box").each(function(){
		$(this).find("li").last().css("border-bottom","none")
	})
	//展开收起
	$(".toggle_sh").each(function(){
		var self=this;
		$(this).click(function(){
			$(self).hide();
			$(self).next().show();
			$(self).parent().next().hide();
		})
	})
	$(".toggle_sh2").each(function(){
		var self=this;
		$(this).click(function(){
			$(self).hide();
			$(self).prev().show();
			$(self).parent().next().show();
		})
	})
	
	/**
	 * ------------------------------------------------------------------------------------------------
	 */
})




});