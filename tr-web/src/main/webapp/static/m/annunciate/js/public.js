define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init();
		clearUnUseLink();
	});
    function init() { 
    	var  annunciate_content = new IScroll('#annunciate_content',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    }
    function clearUnUseLink(){
    	var link = $('.Review_r a').attr('href');
		if(link.indexOf(_WEB_CONTEXT_)>0){
			$('.Review_r a').removeAttr('href');
		}
    }
})