define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function init() { 
		var resTypeid = $("#resTypeid").val();
		var classes = "";
		if(resTypeid==0){
			classes = ".resources_wraper_li1";
		}else if(resTypeid==1){
			classes = ".resources_wraper_li2";
		}else if(resTypeid==2){
			classes = ".resources_wraper_li3";
		}else if(resTypeid==3){
			classes = ".resources_wraper_li4";
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
	   				var resType = $(this).attr("resType");
	   				var params = "lessonId="+lessonId+"&resType="+resType;
	   				window.location.href = _WEB_CONTEXT_+"/jy/api/writelessonplan/getCommendResource?"+params;
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