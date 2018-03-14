define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function init() { 
		 var  mating_li_wrap = new IScroll('#mating_li_wrap',{
	    	 scrollbars: true, 
	    	 mouseWheel:true,
	    	 fadeScrollbars:true,
	    	 click:true,
	    });
	    mating_li_wrap.on('scrollEnd',demo); 
	    function demo(){ 
	    	 if ((this.y - this.maxScrollY) <= -(500+this.maxScrollY)) {  
	   	     	  $(".return_1").show(); 
	   	     	  $(".return_1").click(function (){
	   	     		mating_li_wrap.scrollTo(0, 0, 1000, IScroll.utils.ease.circular);
	 	          });
	   	     }else{
	   	     	 $(".return_1").hide();      
	   	     }
	    };
		$('#versionSelect').click(function (){
          	$('.mask').show();
          	$('.cw_menu_wrap').show(); 
          	var  myScroll2 = new IScroll('#wrap2',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
          	});
          });
		$('.check_menu_wrap').click(function(){
            $('.check_menu_wrap').hide(); 
            $('.mask').hide();
        }); 
		$('.cw_menu_wrap').click(function (){
			$('.cw_menu_wrap').hide();
			$('.mask').hide();
		});
//		$('header ul li a').click(function(){
//			if(!$(this).hasClass("header_act")){
//				if($(this).attr("typeStr")=="ptzy"){
//					location.href =  _WEB_CONTEXT_+"/jy/comres/index";
//				}else if($(this).attr("typeStr")=="flzy"){
//					location.href =  _WEB_CONTEXT_+"/jy/comres/index_type";
//				}
//			}
//		})
		//版本查询选择列表
		$('#wrap2 div').on("click",".selected",function (){
			$('.cw_menu_wrap').hide();
            $('.mask').hide();
			$(this).addClass("act").siblings().removeClass("act");
			$(".version span").html($(this).html());
			$("input[name=bookId]").val($(this).attr("id"));
			var text=$(this).text();
			$("#bookShortname").val($(this).text());
		})
		//根据条件检索
		$("#searchBtn").click(function(){
			$("#searchPlanForm").submit();
		});
		$('.close').click(function (){
	    		$('.look_opinion_list_wrap').hide();
	    		$('.mask').hide();
	    		$("#iframe1").animate({width:"100%"});
	    });
		//配套资源瀑布流加载下一页
		window.addData=function(data){
			var content = $("#pageContent",data).html();
			$("#pageContent").append(content);
			 var  mating_li_wrap = new IScroll('#mating_li_wrap',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    });
		}
		//分类资源瀑布流加载下一页
		window.addDataType=function(data){
			var content = $("#pageContent_type",data).html();
			$("#pageContent_type").append(content);
			 var  mating_li_wrap = new IScroll('#mating_li_wrap',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    });
		}
	}
});