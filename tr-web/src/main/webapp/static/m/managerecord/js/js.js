define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	/*var content_bottom1 = new IScroll('.content_bottom1',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});*/
    	$('.semester').click(function (){
			 $('.semester_wrapper').show(); 
		     $('.mask').show();
		}); 
    	//切换学期
    	$(".semester_wrap1 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.semester_wrapper').hide(); 
		 	 $('.mask').hide(); 
		 });
    	$('.semester_wrapper').click(function(){
            $('.semester_wrapper').hide(); 
            $('.mask').hide();
        }); 
    	//数据统计页面
    	$('#datacount').click(function(){
    		window.location.href =  _WEB_CONTEXT_+"/jy/managerecord/indexdata?term=0&grade="+$(this).attr("data-grade")+"&subject="+$(this).attr("data-subject");
    	});
    }
});