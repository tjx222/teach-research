define(["require","jquery",'tablexport'], function (require) {
var $=require("jquery");
require('tablexport/base64');
$(document).ready(function(){
	$(".teachingTesearch_managers_middle_nav li").each(function(){
	    var self=this;
	    $(this).click(function(){
			$(self).addClass("li_active2");
            $(self).siblings().removeClass("li_active2");
            $("#gradeId").val($(this).attr("id"));
            $("#form1").submit();
		});
	});
	$(".teachingTesearch_managers_b_l li").each(function(){
	    var self=this;
	    $(this).click(function(){
			$(self).addClass("li_active1");
            $(self).siblings().removeClass("li_active1");
            $("#subjectId").val($(this).attr("id"));
            $("#form1").submit();
		});
	});
	$(".teachingTesearch_managers_semester input").change(function(){
		$("#form1").submit();
	});
	
	$(".teachingTesearch_managers_table").find("td").click(function(){
		var orderFlag = $(this).attr("orderFlag");
		if(orderFlag!=null){
			$("#orderFlag").val(orderFlag);
			if($("#orderMode").val()=='up'){
				$("#orderMode").val("down");
			}else{
				$("#orderMode").val("up");
			}
			$("#form1").submit();
		}
	});
	$(".teachingTesearch_class_table").find("td").click(function(){
		var orderFlag = $(this).attr("orderFlag");
		if(orderFlag!=null){
			$("#orderFlag").val(orderFlag);
			if($("#orderMode").val()=='up'){
				$("#orderMode").val("down");
			}else{
				$("#orderMode").val("up");
			}
			$("#form1").submit();
		}
	});
	$("#export").click(function(e){
		var fileName = $(this).attr("tablename");
		var tableHeader = $("#header").html();
		$("#expertTable").prepend(tableHeader);
		$("#expertTable").find("tr").last().remove();
		$('#expertTable').tableExport({type:'excel',escape:'false',fileName:fileName,htmlContent:'false'},e);
		window.location.reload();
	});
	
	var li1 = $(".in_reconsideration_see_title_box li");
	var window1 = $(".out_reconsideration_see_title_box ul");
	var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
	var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
	window1.css("width", li1.length*90+"px"); 
	if(li1.length >= 8){
		left1.show();
		right1.show();
	}else{
		left1.css({"visibility":"hidden"});
		right1.css({"visibility":"hidden"});
	} 
	var lc1 = 0;
	var rc1 = li1.length-8; 
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
	
	$(".teachingTesearch_managers_table").find("td").last().css("border-right","none");
	$(".teachingTesearch_managers_table2").find("tr").last().find("td").css("border-bottom","none");
	$(".change_bg:even").css("background","#f9f9f9");
	
	/**
	 * 年级教研情况一览---------------------------------------------------------------------------------------
	 */
	$(".teachingTesearch_class_semester input").change(function(){
		$("#form1").submit();
	});
	$(".teachingTesearch_class_table").find("td").last().css("border-right","none");
	$(".change_bg:even").css("background","#f9f9f9");
	$(".teachingTesearch_class_table2").find("tr").last().find("td").css("border-bottom","none");
	/**
	 * -------------------------------------------------------------------------------------------------
	 */
})
window.onload=function(){
	if($(".teachingTesearch_managers_b_l").height()>=260){
		$(".teachingTesearch_managers_b_l").find("li").last().css("border-bottom","none");
	}
}




});