;(function ( window, $) {
	$(document).ready(function () {
		
		$('.collective_cont h3 ul li').click(function(){ 
		    $(this).addClass("collective_cont_act").siblings().removeClass("collective_cont_act");
		    listActivity(this);
	    });
		
		 $('.word_tab ul li').click(function(){ 
			    //$(this).addClass("word_tab_act").siblings().removeClass("word_tab_act");
			    $(".word_tab_big .word_tab_big_tab").hide().eq($('.word_tab ul li').index(this)).show(); 
		 });
		 
	});
var list = function () {
		
	    $('.nav ul li span').mouseover(function (){
	    	$('.nav ol').show();
	    })
	    $('.nav ol').mouseleave(function (){
	    	$('.nav ol').hide();
	    })
	    $(".Pre_hide").click(function(){
		  	$(".wrap_top_1").slideToggle();
		  	$('#nav_1').show();
		});

		$('.draft').click(function (){
			$('.draft_wrap').show();
			$('.box').show();
		});
		$('.close').click(function (){
			$('.draft_wrap').hide();
			$('.box').hide();
		})
		$('.collective_cont h3 ul li').click(function(){ 
		    $(this).addClass("collective_cont_act").siblings().removeClass("collective_cont_act");
		    $(".collective_cont_big .collective_cont_tab").hide().eq($('.collective_cont h3 ul li').index(this)).show(); 
	    });
	   
	}
})(window,jQuery);

