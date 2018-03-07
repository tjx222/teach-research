define(["require","zepto","iscroll","placeholder"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	}); 
    function init() {
    	var content_bottom1 = new IScroll('.content_bottom1',{
      		scrollbars:true,	
      		mouseWheel:true,
      		fadeScrollbars:true, 
      	}); 
    	$(".remark").placeholder({
	   		 word: '输入活动要求（200字内）'
	   	});
    	$('.range_class p').click(function (){
		    if($(this).hasClass('class_act')){
		    	$(this).removeClass('class_act');
			}else{
				$(this).addClass('class_act');
			}
		});
    	$('.close').click(function (){
    		$('.partake_school_wrap1').hide();
    		$('.mask').hide();
    	});
    	$('.see').click(function (){
    		$('.partake_school_wrap1').show();
    		$('.mask').show();
    	});
    }
});