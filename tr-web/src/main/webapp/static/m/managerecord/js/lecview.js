define(["require","zepto","iscroll"], function (require) {
	require("zepto");
	var $ = Zepto;
	$(function(){ 
		init();
		if($(".inneredit_content").length>0){ 
			var check_content_bottom = new IScroll('.inneredit_content',{
				scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true, 
	      	});
		 }
	}); 
    function init() {
    	
    }
})