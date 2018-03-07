define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function init() { 
		var myplanbook_catalog = new IScroll('.myplanbook_catalog',{
	    	 scrollbars: true, 
	    	 mouseWheel:true,
	    	 fadeScrollbars:true,
	    });
		/*选择上下册  start*/
		$('.top1').click(function (){
			$('.mask').show();
			$('.cw_menu_wrap').show();
			var wrap2 = new IScroll('#wrap2',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    });
		});
		$("#wrap2 p").each(function(){
    		$(this).click(function(){ 
    			$(this).addClass("act").siblings().removeClass("act");
    			$('.mask').hide();
             	$('.cw_menu_wrap').hide();
    		});
    	});
		$('.cw_menu_wrap').click(function (){
			$('.cw_menu_wrap').hide();
			$('.mask').hide();
		});
		
		/*选择上下册  end*/
		
		
		/* 课题树形 start*/
		$('.myplanbook_catalog1 h4 a').each(function(){
	   		 var that = this;
	   		 $(this).click(function (){ 
	       		 if( $(that).parent().parent().find('ul').length == 0){
	       			 $(that).addClass('book_act').parent().parent().siblings().find('a').removeClass('book_act');
	       			 $(that).parent().append("<div class='class_hour'><div class='class_hour_wrap'><strong class='class_h'></strong>全  案</div><div class='class_hour_1'><strong class='class_h class_prohibit'></strong>第一课时</div><div class='class_hour_1'><strong class='class_h'></strong>第二课时</div><div class='class_hour_1'><strong class='class_h'></strong>第三课时</div><div class='class_hour_1'><strong class='class_h'></strong>第四课时</div><div class='class_hour_1'><strong class='class_h'></strong>第五课时</div><input type='button' value='确定'/></div>").show().parent().siblings().find("h4 div").hide();
	       		     $(that).parent().parent().parent().find('ul div').hide();
	       		     $('.class_hour div').each(function(){
	       		    	 var that = this;
	       		    	 $(this).click(function (){
	       		    		 var strong = $(that).find('strong');
	       		    		if($(that).find('strong').hasClass('class_prohibit')){
	       		    	    	strong.removeClass('class_h').addClass('class_prohibit'); 
	       		    	     }
	       		    	     if($(that).find('strong').hasClass('class_h')){
	       		    	    	strong.removeClass('class_h').addClass('class_span');
	       		    	     }else{
	       		    	    	strong.removeClass('class_span').addClass('class_h');
	       		    	     }
	       		    	    
			      	   	 });
	       		    	 $('.class_hour input').click(function (){
	       		    		 $('.class_hour').hide();
	       		    	 });
	       		     });
	       		 }else if($(that).parent().parent().find('ul').length > 0){
	       			 $(that).removeClass('book_act');
	       			  if($(that).parent().parent().find('ul').css('display')=='block'){
	       			 	 $(that).parent().siblings().hide();
		        		  }else{
		        		     $(that).parent().siblings().show();
		        		  } 
	       		 } 
	       		$('#scroll div').last().remove();
	       		$('#scroll').append('<div class="k"></div>');
	       		 
	       	 });
	   		
	   	 });
	   	 $('.myplanbook_catalog1 ul li a').each(function (){
	   		 var that = this;
	   		 var two = $(that).parent().parent().parent().siblings();
	   		 $(this).click(function (){
	   			 if($(that).parent().find('ol').length == 0){ 
	   				 $(that).addClass('book_act').parent().siblings().find('a').removeClass('book_act');
	   				 two.find('ul a').removeClass('book_act');
	   				 two.find('h4 a').removeClass('book_act'); 
	   				 $(that).parent().append("<div class='class_hour'><div class='class_hour_wrap'><strong class='class_h'></strong>全  案</div><div class='class_hour_1'><strong class='class_h'></strong>第一课时</div><div class='class_hour_1'><strong class='class_h'></strong>第二课时</div><div class='class_hour_1'><strong class='class_h'></strong>第三课时</div><div class='class_hour_1'><strong class='class_h'></strong>第四课时</div><div class='class_hour_1'><strong class='class_h'></strong>第五课时</div><input type='button' value='确定'/></div>").show().siblings().find("li div").hide();
	   				 two.find('ul div').hide();
	   				 two.find('h4 div').hide();
	   				 $('.class_hour div').each(function(){
	       		    	 var that = this;
	       		    	 $(this).click(function (){
	       		    		 var strong = $(that).find('strong');
	       		    		if($(that).find('strong').hasClass('class_prohibit')){
	       		    	    	strong.removeClass('class_h').addClass('class_prohibit'); 
	       		    	     }
	       		    	     if($(that).find('strong').hasClass('class_h')){
	       		    	    	strong.removeClass('class_h').addClass('class_span');
	       		    	     }else{
	       		    	    	strong.removeClass('class_span').addClass('class_h');
	       		    	     }
	       		    	    
			      	   	 });
	       		    	 $('.class_hour input').click(function (){
	       		    		 $('.class_hour').hide();
	       		    	 });
	       		     });
	   			 }else if($(that).parent().find('ol').length > 0){ 
	       			 $(that).removeClass('book_act');
	       			 if($(that).parent().find('ol').css('display')=='block'){
	       			 	 $(that).parent().find('ol').hide();
		        		  }else{
		        		     $(that).parent().find('ol').show();
		        		  } 
	       		 }
	   			$('#scroll div').last().remove();
	       		$('#scroll').append('<div class="k"></div>');
	   		 });
	   	 });
	   	 $('.myplanbook_catalog1 ul li ol li a').each(function (){
	   		 var that = this;
	   		 var three =  $(that).parent().parent().parent();
	   		 $(this).click(function (){
	   			 $(that).addClass('book_act').parent().siblings().find('a').removeClass('book_act');
	   			 three.siblings().find('a').first().removeClass('book_act');
	   			 three.parent().parent().siblings().find('h4 a').removeClass('book_act');
	   			 three.parent().parent().siblings().find('ul a').removeClass('book_act');
	   			 $(that).parent().append("<div class='class_hour'><div class='class_hour_wrap'><strong class='class_h'></strong>全  案</div><div class='class_hour_1'><strong class='class_h'></strong>第一课时</div><div class='class_hour_1'><strong class='class_h'></strong>第二课时</div><div class='class_hour_1'><strong class='class_h'></strong>第三课时</div><div class='class_hour_1'><strong class='class_h'></strong>第四课时</div><div class='class_hour_1'><strong class='class_h'></strong>第五课时</div><input type='button' value='确定'/></div>").show().siblings().find("li div").hide();
	   			 three.parent().parent().parent().find('h4 div').hide();
	   			 $('#scroll div').last().remove();
	       		 $('#scroll').append('<div class="k"></div>');
	       		 $('.class_hour div').each(function(){
       		    	 var that = this;
       		    	 $(this).click(function (){
       		    		 var strong = $(that).find('strong');
       		    		if($(that).find('strong').hasClass('class_prohibit')){
       		    	    	strong.removeClass('class_h').addClass('class_prohibit'); 
       		    	     }
       		    	     if($(that).find('strong').hasClass('class_h')){
       		    	    	strong.removeClass('class_h').addClass('class_span');
       		    	     }else{
       		    	    	strong.removeClass('class_span').addClass('class_h');
       		    	     }
       		    	    
		      	   	 });
       		    	 $('.class_hour input').click(function (){
       		    		 $('.class_hour').hide();
       		    	 });
       		     });
	   		 });
	   		
	   	 }); 
	   	
	   	 /* 课题树形 end*/
	   	 
	   	 
	   	/* 推送资源  start*/
	   	 $('.push_resources').click(function (){
	   		 $('.push_resources_wrap').show();
	   		 $('.mask').show();
	   		 var resources_wraper_li1 = new IScroll('.resources_wraper_li1',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    }); 
	   		 
	   	 }); 
	   	 $('.push_resources_content ul li').each(function(){
	   		 var that = this;
	   		 $(this).click(function (){ 
	   			 var index = $(this).index();
	   			 $(that).find('a').addClass('push_act').parent().siblings().find('a').removeClass('push_act');
	   		     $('.resources_wraper .resources_wraper_li').eq(index).show().siblings().hide();
	   		
	   			 var resources_wraper_li1 = new IScroll('.resources_wraper_li1',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   			 var resources_wraper_li2 = new IScroll('.resources_wraper_li2',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   			 var resources_wraper_li3 = new IScroll('.resources_wraper_li3',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   			 var resources_wraper_li4 = new IScroll('.resources_wraper_li4',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   		 });
	   		 
	     });
	 	/* 推送资源  end*/
	   	 
	   	/* 同伴资源  start*/
	   	$('.peer_resources').click(function (){
	   		 $('.peer_resources_wrap').show();
	   		 $('.mask').show();
	   		var resources_wrap_li1 = new IScroll('.resources_wrap_li1',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    });
	   		 
	   	 }); 
	   	$('.peer_resources_content ul li').each(function(){
	   		 var that = this;
	   		 $(this).click(function (){ 
	   			 var index = $(this).index();
	   			 $(that).find('a').addClass('push_act').parent().siblings().find('a').removeClass('push_act');
	   		     $('.resources_wrap .resources_wrap_li').eq(index).show().siblings().hide();
	   		
	   			 var resources_wrap_li1 = new IScroll('.resources_wrap_li1',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   			 var resources_wrap_li2 = new IScroll('.resources_wrap_li2',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   			 var resources_wrap_li3 = new IScroll('.resources_wrap_li3',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    }); 
	   		 });
	   		 
	     });
	   	/* 同伴资源  end*/
	   	/*关闭弹矿  start*/
	   	 $('.close').click(function (){
	   		 $('.push_resources_wrap').hide();
	   		 $('.peer_resources_wrap').hide();
	   		 $('.mask').hide();
	   	 });
	   	/*关闭弹矿  end*/
	   	/*选择模板 start*/
	   	 $('.template').click(function (){
	   		 $('.mask').show();
	   		 $('.choice_template_wrap').show();
	   	 });
	 	 $('.choice_template_b ul li').each(function(){
	   		 var that = this;
	   		 $(this).click(function (){ 
	   			 var index = $(this).index();
	   			 $(that).addClass('li_act1').siblings().removeClass('li_act1');
	   		     $('.choice_template_b_c .cons').eq(index).show().siblings().hide();
	   		 });
	 	 });
	 	 var sWidth = $(".choice_template_b_c").width(); //获取焦点图的宽度（显示面积）
		 var len = $(".choice_template_b ul li").length; //获取焦点图个数
		 var index = 0;
	 	 $(".choice_template_b_l").click(function() {
			index -= 1;
			if(index == -1) {index = len - 1;}
			showPics(index);
		 });
	 	 $(".choice_template_b_r").click(function() {
			index += 1;
			if(index == len) {index = 0;}
			showPics(index);
		 });
	 	 function showPics(index) {
			var nowLeft = -index*sWidth;
			$('.choice_template_b_c .cons').eq(index).show().siblings().hide();
			$('.choice_template_b ul li').removeClass("li_act1").eq(index).addClass("li_act1"); 
		 }
	 	 
	 	 $('.choice').click(function (){
	 		 $('.choice_template_wrap').hide();
	 		 $('.mask').hide();
	 	 });
	 	 /*选择模板 end*/
	}
});