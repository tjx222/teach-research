define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto; 
	jq(function(){
		init();
	});
    function init() {  
    	var le_cont_bottom = new IScroll('.lecturerecords_cont_bottom',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	jq(".courseware_img_2").click(function () {
    		var plan = jq(this).parent();
    		plan.addClass('border').siblings().removeClass('border');
          	plan.find(".cw_option_mask").show();
          	plan.find('.cw_option').show();
          	plan.find(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
        });
    	jq(".cw_option_close").click(function () {
      	  var plan = jq(this).parent().parent();
      	  plan.removeClass('border');
   		 plan.find('.cw_option_mask').hide();
   		 plan.find('.cw_option').hide(); 
   		 plan.find(".cw_option_edit").css('-webkit-animation', 'edit .5s');
   		 plan.find(".cw_option_del").css('-webkit-animation', 'del .5s');
   		 plan.find(".cw_option_submit").css('-webkit-animation', 'submit .5s');
   		 plan.find(".cw_option_share").css('-webkit-animation', 'share .5s');
   		 plan.find(".cw_option_down").css('-webkit-animation', 'down .5s');
       });
    	jq('.courseware_img_21').click(function (){
    		jq('.mask').show();
    		jq('.submit_upload_wrap').show();
    	});
    	jq('.close').click(function (){
    		jq('.mask').hide();
    		jq('.submit_upload_wrap').hide();
    	});
    	jq('.add_cour').click(function (){
    		jq('.zx_option_wrap').show();
    		jq('.mask').show();
    	});
    	jq('.zx_option_wrap').click(function (){
    		jq('.zx_option_wrap').hide();
    		jq('.mask').hide();
    	});
    	jq('.annunciate_right').click(function (){
    		jq('.school_lectures_wrap').show();
    		jq('.mask').show();
    		var school_lectures_wrapper = new IScroll('.school_lectures_wrapper',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	jq('.school_lectures_wrap').click(function (){
    		jq('.school_lectures_wrap').hide();
    		jq('.mask').hide();
    	});
    	jq(".school_lectures_wrapper p").click(function () { 
		 	 jq( this ).addClass("act").siblings().removeClass("act"); 
		 	 jq('.school_lectures_wrap').hide();
		 	 jq('.mask').hide(); 
		  });
    	jq('.in_school').click(function (event){
    		event.stopPropagation();
    	});
    	jq('.outside_school').click(function (event){
    		event.stopPropagation();
    	});
    }
});