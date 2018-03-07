define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() {  
    	var referCourseware_content_box = new IScroll('.referCourseware_content_box',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	var referCourseware_cont_box = new IScroll('.referCourseware_cont_box',{
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
	    	$('.referCourseware_cont_box').show();
	    }else{
	    	$('.referCourseware_cont_box').hide();
	    	$('.referCourseware_content_box').show();
	    }
	    //切换反思 选项
	    $('.managerecord_slide span').click(function(){
	    	//$(this).addClass('mana_act').siblings().removeClass('mana_act');
	    	$('#hid_resType').val($(this).attr('data-resType'));
	    	$('#hiddenForm').submit();
	    });
	    //查看
	    $('.viewLesson').click(function(){
        	var planInfoId=$(this).attr("data-id");
        	window.location.href= _WEB_CONTEXT_ + "/jy/managerecord/check/0/viewLessonPlanCheckInfo?planInfoId="+planInfoId;
        });
	    $('.viewKeJian').click(function(){
        	var resId=$(this).attr("data-id");
        	window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/1/kejianView?type=1&lesInfoId="+resId;
        });
	    $('.viewFanSi').click(function(){
        	var resId=$(this).attr("data-id");
        	var resType=$(this).attr("data-resType");
        	window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/2-3/fansiView?type="+resType+"&lesInfoId="+resId;
        });
	    $('.viewPlan').click(function(){
        	var resId=$(this).attr("data-id");
        	var resType=$(this).attr("data-resType");
        	window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/2-3/fansiView?type="+resType+"&lesInfoId="+resId;
        });
	    //查看意见跳转
	    $('.referCourseware_content_title b').click(function(){
	    	var type=$(this).attr("data-type");
	    	var resId=$(this).attr("data-resId");
	    	var resType=$(this).attr("data-resType");
	    	if(type==0){
	    		window.location.href= _WEB_CONTEXT_ + "/jy/managerecord/check/0/viewLessonPlanCheckInfo?planInfoId="+resId;
	    	}else if(type==1){
	    		window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/1/kejianView?type=1&lesInfoId="+resId;
	    	}else if(type==2||type==3){
	    		window.location.href=_WEB_CONTEXT_+"/jy/managerecord/check/2-3/fansiView?type="+resType+"&lesInfoId="+resId;
	    	}else{
	    	}
	    });
    }
});