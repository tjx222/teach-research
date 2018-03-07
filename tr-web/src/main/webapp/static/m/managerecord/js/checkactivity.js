define(["require","zepto","iscroll"], function (require) {
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
      		click:true,
      	});
    	$('.semester').click(function (){
			 $('.semester_wrapper').show(); 
		     $('.mask').show();
		     if($('#hid_term').val()==0||$('#hid_term').val()==null){
		    	 $('.semester_wrapper p').eq(0).addClass("act"); 
		     }else{
		    	 $('.semester_wrapper p').eq(1).addClass("act");
		     }
		}); 
    	//切换学期
    	$(".semester_wrap1 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.semester_wrapper').hide(); 
		 	 $('.mask').hide(); 
		 	 $('#hid_term').val($(this).attr('data-term'));
		 	 $('#hiddenForm').submit();
		 });
    	$('.semester_wrapper').click(function(){
            $('.semester_wrapper').hide(); 
            $('.mask').hide();
        }); 
	   	$('header ul li').click(function(){
	   		$('#hid_listType').val($(this).find('a').attr('data-type'));
	   		$('#hiddenForm').submit();
	   	});
	   	//切换已查阅与查阅意见
	    var listType=$('#hid_listType').val();
	    if(listType==0){
	    	$('.referCourseware_content_box').hide();
	    	$('.content_bottom1').show();
	    }else{
	    	$('.content_bottom1').hide();
	    	$('.referCourseware_content_box').show();
	    }
	    $('.activity_tch').click(function(){
	    	var activityId=$(this).attr("data-activityId");
	    	window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/5/chayueActivity?activityId="+activityId,"_blank";
	    });
	    $('.referCourseware_content_title b').click(function(){
	    	var activityId=$(this).attr("data-resId");
	    	window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/5/chayueActivity?activityId="+activityId,"_blank";
	    });
    }
});