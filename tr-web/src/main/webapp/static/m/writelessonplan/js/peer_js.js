define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function init() { 
		var planType = $("#planType").val();
		var classes = "";
		if(planType==0){
			classes = ".resources_wrap_li1";
		}else if(planType==1){
			classes = ".resources_wrap_li2";
		}else if(planType==2){
			classes = ".resources_wrap_li3";
		}
		var resources_wrap_li1 = new IScroll(classes,{
	    	scrollbars: true, 
	    	mouseWheel:true,
	    	fadeScrollbars:true,
	    	click:true
	    });
		/* 推送资源 */
	   	$('.push_resources_content ul li').each(function(){
	   		 $(this).click(function (){
	   			var curclass = $(this).find('a').attr("class");
	   			if(curclass!="push_act"){
	   				var lessonId = $(this).attr("lessonId");
	   				var spaceId = $(this).attr("spaceId");
	   				var planType = $(this).attr("planType");
	   				var params = "lessonId="+lessonId+"&spaceId="+spaceId+"&planType="+planType;
	   				window.location.href = _WEB_CONTEXT_+"/jy/api/writelessonplan/getPeerResource?"+params;
	   			}
	   		 });
	    });
	   	$(".courseware_ppt p").each(function(){
  		  $(this).click(function(){
  			  var resId = $(this).attr("resId");
  			  scanResFile_m(resId);
  		  });
  	  }); 
	}
});