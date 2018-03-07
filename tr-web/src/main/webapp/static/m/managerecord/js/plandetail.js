define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() {  
    	var referCourseware_cont_box = new IScroll('.referCourseware_cont_box',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	var referCourseware_cont_box = new IScroll('.referCourseware_cont_sum_box',{
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
	   	$('.managerecord_slide span').click(function(){
	   		$(this).addClass('mana_act').siblings().removeClass('mana_act');
	   		if($(this).attr("data-type")==0){
	   			$('.referCourseware_cont_sum_box').hide();
	   			$('.referCourseware_cont_box').show();
	   		}else{
	   			$('.referCourseware_cont_box').hide();
	   			$('.referCourseware_cont_sum_box').show();
	   		}
	   	});
	   	//查看
	   	$('.courseware_ppt').click(function(){
	   		scanResFile_m($(this).attr('data-id'));
	   	});
	   	$('.planSummary_ppt').click(function(){
	   		scanResFile_m($(this).attr('data-id'));
	   	})
    }
});