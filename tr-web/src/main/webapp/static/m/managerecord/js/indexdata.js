define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	var managerecord_bottom_wrap = new IScroll('.managerecord_bottom_wrap',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    }
});