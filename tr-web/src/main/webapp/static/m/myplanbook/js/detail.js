define(["require","zepto","iscroll"], function (require) {
	var jq = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	var myplanbook_catalog = new IScroll('.myplanbook_catalog',{
       		scrollbars:true,
       		mouseWheel:true,
       		fadeScrollbars:true,
       		click:true,
    	});
    	var content_right_cont = new IScroll('.content_right_cont',{
       		scrollbars:true,
       		mouseWheel:true,
       		fadeScrollbars:true,
       		click:true,
    	});
    	jq(".courseware_img_2").click(function () {
    		var plan = jq(this).parent();
    		plan.addClass('border').removeClass('border1');
          	plan.find(".cw_option_mask").show();
          	plan.find('.cw_option').show();
          	plan.find(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_submit").css({'-webkit-animation': 'submit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_down").css({'-webkit-animation': 'down .5s',"-webkit-animation-fill-mode":"forwards"});
    	});
    	 jq(".cw_option_close").click(function () {
    		 var plan = jq(this).parent().parent();
       	  	 plan.addClass('border1').removeClass('border');
    		 plan.find('.cw_option_mask').hide();
    		 plan.find('.cw_option').hide(); 
    		 plan.find(".cw_option_edit").css('-webkit-animation', 'edit .5s');
    		 plan.find(".cw_option_del").css('-webkit-animation', 'del .5s');
    		 plan.find(".cw_option_submit").css('-webkit-animation', 'submit .5s');
    		 plan.find(".cw_option_share").css('-webkit-animation', 'share .5s');
    		 plan.find(".cw_option_down").css('-webkit-animation', 'down .5s');
        });
    	 
    	/* 课题树形 start*/
    	 jq('.myplanbook_catalog1 h4 a').each(function(){
    		 var that = this;
    		 jq(this).click(function (){ 
        		 if( jq(that).parent().parent().find('ul').length == 0){
        			 jq(that).addClass('book_act').parent().parent().siblings().find('a').removeClass('book_act');
        		 }else if(jq(that).parent().parent().find('ul').length > 0){
        			 jq(that).removeClass('book_act');
        			  if(jq(that).parent().parent().find('ul').css('display')=='block'){
        			 	 jq(that).parent().siblings().hide();
	        		  }else{
	        		     jq(that).parent().siblings().show();
	        		  } 
        		 } 
        	 });
    	 });
    	 jq('.myplanbook_catalog1 ul li a').each(function (){
    		 var that = this;
    		 var two = jq(that).parent().parent().parent().siblings();
    		 jq(this).click(function (){
    			 if(jq(that).parent().find('ol').length == 0){ 
    				 jq(that).addClass('book_act').parent().siblings().find('a').removeClass('book_act');
    				 two.find('ul a').removeClass('book_act');
    				 two.find('h4 a').removeClass('book_act');
    			 }else if(jq(that).parent().find('ol').length > 0){ 
        			 jq(that).removeClass('book_act');
        			 if(jq(that).parent().find('ol').css('display')=='block'){
        			 	 jq(that).parent().find('ol').hide();
	        		  }else{
	        		     jq(that).parent().find('ol').show();
	        		  } 
        		 }
    		 });
    	 });
    	 jq('.myplanbook_catalog1 ul li ol li a').each(function (){
    		 var that = this;
    		 var three =  jq(that).parent().parent().parent();
    		 jq(this).click(function (){
    			 jq(that).addClass('book_act').parent().siblings().find('a').removeClass('book_act');
    			 three.siblings().find('a').first().removeClass('book_act');
    			 three.parent().parent().siblings().find('h4 a').removeClass('book_act');
    			 three.parent().parent().siblings().find('ul a').removeClass('book_act');
    		 });
    	 }); 
    	 /* 课题树形 end*/
    	 
    	 
    	 jq('.add_cour').click(function (){
    		 jq('.mask').show();
    		 jq('.zx_option_wrap').show();
    	 });
    	 jq('.zx_option_wrap').click(function (){
    		 jq('.mask').hide();
    		 jq('.zx_option_wrap').hide();
    	 });
    	 jq('.menu_list').hide();
    	 jq('.zx_option_kj').click(function (event){
    		 jq('.zx_option_wrap').hide();
    		 event.stopPropagation();
    		 jq('.add_upload_wrap').show();
    	 });
    	 jq('.zx_option_fs').click(function (event){
    		 jq('.zx_option_wrap').hide();
    		 event.stopPropagation();
    		 jq('.add_up_wrap').show();
    	 });
    	 jq('.close').click(function (){
    		 jq('.add_upload_wrap').hide();
    		 jq('.mask').hide();
    		 jq('.add_up_wrap').hide();
    		 jq('.class_comments_wrap').hide();
    		 
    	 });
    	  jq( ".menu_list_wrap1 p" ).click(function () {
     	 	 jq( this ).addClass("act").siblings().removeClass("act");
     	 	 jq( ".menu_list ").hide();
     	  });
     	
          jq('.select').click(function (){
               jq('.menu_list').toggle();
               if(jq('.menu_list').css("display") == 'block' ){
         		  jq('.add_upload_wrap1').show();   //如果元素为隐藏,则将它显现
         	  }else{
         		  jq('.add_upload_wrap1').hide();     //如果元素为显现,则将其隐藏
         	  } 
               var  myScroll1 = new IScroll('#wrap1',{
             		scrollbars:true,
             		mouseWheel:true,
             		fadeScrollbars:true,
             		click:true,
             	}); 
           });
           jq('.add_upload_wrap1').click(function(){ 
         	  jq('.menu_list').hide();
           }); 
           
           
           jq('.peer_resources').click(function (){
        	   jq('.class_comments_wrap').show();
        	   jq('.mask').show();
        	   var  class_table = new IScroll('.class_table',{
            		scrollbars:true,
            		mouseWheel:true,
            		fadeScrollbars:true,
            		click:true,
            	}); 
           });
    }
});