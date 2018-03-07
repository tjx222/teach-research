define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	var term = "";
	var subjectId = "";
	var gradeId = "";
	$(function(){ 
		init();
	});
    function init() {
    	window.loadactivitydata = function(){
        	$("#activitylist").attr("src",_WEB_CONTEXT_+"/jy/check/activity/activitylist?grade="+gradeId+"&subject="+subjectId+"&term="+ term);
        }
    	$("#wrap1 p.act").each(function(index,obj){
//    		if(index==0){
    			term = $(this).attr("data");
    			var termname = $(this).attr("dataname");
    			$("#termcontent").html(termname+"<strong></strong>")
    			$( this ).addClass("act").siblings().removeClass("act"); 
//    		}
    	});
    	$("#wrap2 p.act").each(function(index,obj){
//    		if(index==0){
    			subjectId = $(this).attr("data");
    			var subjectname = $(this).attr("dataname");
    			$("#subjectcontent").html(subjectname+"<strong></strong>")
   		 	 	$( this ).addClass("act").siblings().removeClass("act"); 
//    		}
    	});
    	$("#wrap3 p.act").each(function(index,obj){
//    		if(index==0){
    			gradeId = $(this).attr("data");
       		 	var gradename = $(this).attr("dataname");
       		 	$("#gradecontent").html(gradename+"<strong></strong>")
       		 	$( this ).addClass("act").siblings().removeClass("act"); 
//    		}
    	});
    	$('.check_content_block span').click(function (){  
    		$('.mask').show(); 
    		var menuzdy = "";
    		var wrap = "";
    		var contentid = $(this).attr("id");
    		if(contentid=="termcontent"){
    			wrap = "wrap1";
    			menuzdy = "menuzdy1";
    		}else if(contentid=="subjectcontent"){
    			wrap = "wrap2";
    			menuzdy = "menuzdy2";
    		}else if(contentid=="gradecontent"){
    			wrap = "wrap3";
    			menuzdy = "menuzdy3";
    		}
    		$('.'+menuzdy).show();
    		var myScroll = new IScroll('#'+wrap,{
    			scrollbars:true,
    			mouseWheel:true,
    			fadeScrollbars:true,
    			click:true, 
    		});	
    	});
    	$('.check_menu1_wrap').click(function(){
            $('.check_menu1_wrap').hide(); 
            $('.mask').hide();
        });
		$('.check_menu_wrap').click(function(){
            $('.check_menu_wrap').hide(); 
            $('.mask').hide();
        }); 
		$("#wrap1 p").click(function () { 
			term = $(this).attr("data");
			var termname = $(this).attr("dataname");
			$("#termcontent").html(termname+"<strong></strong>")
			$( this ).addClass("act").siblings().removeClass("act"); 
			$('.check_menu_wrap').hide();
			$('.mask').hide(); 
			loadactivitydata();
		});
    	 $("#wrap2 p").click(function () { 
    		 subjectId = $(this).attr("data");
    		 var subjectname = $(this).attr("dataname");
    		 $("#subjectcontent").html(subjectname+"<strong></strong>")
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu_wrap').hide();
		 	 $('.mask').hide(); 
		 	 loadactivitydata();
		  });
    	 $("#wrap3 p").click(function () { 
    		 gradeId = $(this).attr("data");
    		 var gradename = $(this).attr("dataname");
    		 $("#gradecontent").html(gradename+"<strong></strong>")
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu_wrap').hide();
		 	 $('.mask').hide(); 
		 	 loadactivitydata();
		  });
//    	  term = $("#term").val();
//    	  subjectId = $("#subject").val();
//    	  gradeId = $("#grade").val();
    	  loadactivitydata();
    }
    window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").append(content.find(".consult_opinion"));
		myScroll3.refresh();
	 };
    
});