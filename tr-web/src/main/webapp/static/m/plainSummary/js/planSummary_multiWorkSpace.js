define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	var contentTemplate = null;				
	$(function(){ 
		init();
		loadData();
	});
    function init() {  
    	$('.check_content_block span').click(function (){
    		if($('.check_menu_wrap p').length!=0){
	    		$('.check_menu_wrap').show();
	    		$('.mask').show(); 
	    		var myScroll2 = new IScroll('#wrap2',{
	          		scrollbars:true,
	          		mouseWheel:true,
	          		fadeScrollbars:true, 
	          		click:true,
	          	});
    		}
    	});
    	$('.check_content_block1 span').click(function (){ 
    		if($('.check_menu1_wrap p').length!=0){
	    		$('.check_menu1_wrap').show();
	    		$('.mask').show();
	    		var myScroll3 = new IScroll('#wrap3',{
	          		scrollbars:true,
	          		mouseWheel:true,
	          		fadeScrollbars:true, 
	          		click:true,
	          	});
    		}
    	});
    	
    	if($('.check_menu_wrap').length==0){
    		$('.check_block_menu1').css("margin-left","-27.3rem");
    	}
    	
		 $("#wrap2 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu_wrap').hide();
		 	 $('.mask').hide(); 
		 	 $('.check_content_block span').html($(this).text());
		 	 $('#selectsubject').attr("data",$(this).attr('data'));		 	
		 	 loadData(); 
		  });
    	
    	$('#wrap3 p').click(function(){
             $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu1_wrap').hide();
		 	 $('.mask').hide();
		 	 $('.check_content_block1 span').html($(this).text());
		 	 $('#selectgrade').attr("data",$(this).attr('data'));
		 	 loadData();
        });     	    			    	
    	    	 
    	$('.check_menu1_wrap').click(function(){
            $('.check_menu1_wrap').hide(); 
            $('.mask').hide();
        });
		$('.check_menu_wrap').click(function(){
            $('.check_menu_wrap').hide(); 
            $('.mask').hide();
        }); 
    	$('.semester').click(function (){
    		 $('.semester_wrapper').show(); 
             $('.mask').show();
    	}); 
        $('.close').click(function (){
    		
    	});
        
		window.loadData=function(){
			var HTMLDATA = {};
			var roleId=$("#roleId").val();
			var term=$("#term").val();			
			var gradeId = $('#selectgrade').attr('data');
			var subjectId = $('#selectsubject').attr('data');			
			var name = "s"+subjectId+"g"+gradeId;
    		var html = HTMLDATA[name];    		    		
    		if(!html){
				$.get('./jy/planSummaryCheck/role/'+roleId+'/plainSummary/listMulti?category='+$("#category").val()+'&gradeId=' + (gradeId==null?'':gradeId) + '&subjectId='
						+(subjectId==null?'':subjectId) +((term==""?'':'&term='+term)),
						function(data) {
							HTMLDATA[name] = data;	
							$('#userListContent').html(data);							
							var check_c_b = new IScroll('#check_c_b',{
					      		scrollbars:true,
					      		mouseWheel:true,
					      		fadeScrollbars:true,
					      		click:true,
					      	});							
							
							var catet = $("#category").val();
							if(catet=='3'){
								$(".njzj").hide();
								$(".njjh").show();	
							}
							if(catet=='4'){
								$(".njjh").hide();
								$(".njzj").show();
							}
							
							//类型切换
							$(".nav_tab ul li a").click(function(e){
								$(".content_k").hide();
								var category=$(this).attr("data-category");
								if(category=='3'){
									$(".header_act").removeClass("header_act");
									$(this).addClass("header_act");					
									$(".njzj").hide();
									$(".njjh").show();		
									if($(".content_k").attr("data-plan")=="true"){
										$(".content_k").show();
									}
								}else if(category=='4'){
									$(".header_act").removeClass("header_act");
									$(this).addClass("header_act");					
									$(".njjh").hide();
									$(".njzj").show();	
									if($(".content_k").attr("data-summary")=="true"){
										$(".content_k").show();
									}
								}	
								$("#category").val(category);
								loadData();
							});
							
//							$(".nav_tab ul li a.header_act").trigger("click");
							
							//切换
					    	$(".semester_wrap1 p").click(function (e) { 
							 	 $(this).addClass("act").siblings().removeClass("act"); 
							 	 $('.semester_wrapper').hide(); 
							 	 $('.mask').hide(); 
							 	 $('.semester').html($(this).text());
							 	 var roleId=$('#roleId').val();
							 	 var term=$(this).attr("data-val");
							 	 $("#term").val(term);	
							 	 location.href ="./jy/planSummaryCheck/role/"+roleId+"/planSummaryMulti?category="+$("#category").val()+'&gradeId=' + (gradeId==null?'':gradeId) + '&subjectId='
										+(subjectId==null?'':subjectId)+((term=="")?"":"&term="+term);
					    	});
					    	
					    	$('.semester_wrapper').click(function(){
					            $('.semester_wrapper').hide(); 
					            $('.mask').hide();
					        });
					    	
					    	$('.semester').click(function (){
					    		 $('.semester_wrapper').show(); 
					             $('.mask').show();
					    	});
						});
    		}else{
    			$("#userListContent").html(html);
    		}
    	}
    }	
});
