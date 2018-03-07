define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto; 
	jq(function(){
		init();
	});
    function init() {  
    	var check_content_bottom = new IScroll('.check_content_bottom',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	jq('.check_content_block span').click(function (){ 
    		jq('.check_menu_wrap').show();
    		jq('.mask').show(); 
    		var check_menu_wrap1 = new IScroll('.check_menu_wrap1',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	jq('.check_content_block1 span').click(function (){ 
    		jq('.check_menu1_wrap').show();
    		jq('.mask').show(); 
    		var check_menu_wrap2 = new IScroll('.check_menu_wrap2',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	 jq(".c_m_w p").click(function () { 
		 	 jq( this ).addClass("act").siblings().removeClass("act"); 
		 	 jq('.check_menu_wrap').hide();
		 	 jq('.mask').hide(); 
		  });
    	jq('.check_menu_wrap').click(function (){
    		jq('.check_menu_wrap').hide();
    		jq('.mask').hide();
    	});
    	jq('.check_menu1_wrap').click(function (){
    		jq('.check_menu1_wrap').hide();
    		jq('.mask').hide();
    	});
    }
});