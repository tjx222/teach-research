define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function() {			
		//类型切换
		$(".nav_tab ul li a").click(function(e){
			initCategory($(this));
		});		

		$(".nav_tab ul li a[data-category='"+$("#category").val()+"']").trigger("click");		
		
		//切换学期
    	$(".semester_wrap1 p").click(function (e) { 
		 	 $(this).addClass("act").siblings().removeClass("act"); 
		 	 $('.semester_wrapper').hide(); 
		 	 $('.mask').hide(); 
		 	 $('.check_content_right_option div').html($(this).text());
		 	 var workSpaceId=$('.semester_wrap1').attr("data-workSpaceId");
		 	 var term=$(this).attr("data-val");
		 	 $(this).attr("data-term",term);	
		 	 location.href ="./jy/planSummaryCheck/userSpace/"+workSpaceId+"/planSummarySingle?category="+$("#category").val()+((term=="")?"":"&term="+term);
    	});	
    	
    	$('.semester_wrapper').click(function(e){
            $('.semester_wrapper').hide(); 
            $('.mask').hide();
        }); 
		$('.check_menu_wrap').click(function(e){
            $('.check_menu_wrap').hide(); 
            $('.mask').hide();
        }); 
    	$('.semester').click(function (e){
    		 $('.semester_wrapper').show(); 
             $('.mask').show();
    	}); 
    	$('.close').click(function (){
    		
    	});
    	
    	function initCategory(val){
    		$(".content_k").hide();
			var category=val.attr("data-category");
			if(category==1){
				$(".header_act").removeClass("header_act");
				val.addClass("header_act");					
				$(".njzj").hide();
				$(".njjh").show();	
				if($(".content_k").attr("data-plan")=="true"){
					$(".content_k").show();
				}
			}else if(category==2){
				$(".header_act").removeClass("header_act");
				val.addClass("header_act");					
				$(".njjh").hide();
				$(".njzj").show();	
				if($(".content_k").attr("data-summary")=="true"){
					$(".content_k").show();
				}
			}		
			$("#category").val(category);
    	}
	});
});