;(function ( window, $) {
	$(document).ready(function () {
		list();
	});
	$( document ).ready(function(e) {
		$('.add_class_hour').click(function (){ 
			var li_len = $('#ul1 li').length-1;
			//最多只能添加20个课时
			if(li_len>19){
				return;
			}
			li_len++;
			//当增加到20时将按钮置灰
			if(li_len>19){
				$(this).attr("disabled","disabled");
			}
			$('#ul1').append("<li><input type='checkbox' name='lessonHours' value='"+li_len+"' style='margin-top:0.5px;'/>第"+li_len+"课时<b class='d-chenkbox' style='display: none;'></b></li>")
		});
		
		$( ".com_cont_right1 .com_cont_right1_left" ).click( function(){
			if($( ".com_cont_right1 .com_cont_right1_left" ).attr("class")=="com_cont_right1_left open"){
				$( ".com_cont_right1" ).stop().animate( {"right": -5}, "fast", function(){
					$( ".com_cont_right1 .com_cont_right1_left" ).removeClass( "open" );	
				});
			}else{
				$( ".com_cont_right1" ).stop().animate( {"right": -44}, "fast", function(){
					$( ".com_cont_right1 .com_cont_right1_left" ).addClass( "open" );	
				});
			}
		});
		$('.hiden_left').click(function (){
			if($(".com_cont_left").css('display')=='block'){
				
				$(".com_cont_left").css({'display':'none'});
				$( ".hiden_left .hiden_left_right" ).addClass( "open1" );
				$('.com_cont_right').css({'width':'950px'});
				$('#iframe2').css({'width':'950px'});
				$('#qwe').css({'width':'950px'});
				$('.hiden_left').css({'left':'0'});
				$('.hiden_left_right').attr("title","展开目录");
				return false;
			}
			
			if($(".com_cont_left").css('display')=='none'){
			
				$(".com_cont_left").css({'display':'block'});
				$( ".hiden_left .hiden_left_right" ).removeClass( "open1" );
				$('.com_cont_right').css({'width':'700px'});
				$('#iframe2').css({'width':'700px'});
				$('#qwe').css({'width':'700px'});
				$('.hiden_left').css({'left':'249px'});
				$('.hiden_left_right').attr("title","收起目录")
				return;
			}
		});
	});
	var list = function () {
		$('.close').click(function (){
			$('.add_lesson_wrap').hide();
			$('.add_push_wrap').hide();
			$('.add_electronics_wrap').hide();
			$('.add_peer_wrap').hide();
		})
		$('#jiaoan').click(function (){
			$('.add_lesson_wrap').show();
			$('.add_push_wrap').hide();
			$('.add_electronics_wrap').hide();
			$('.add_peer_wrap').hide();
		});
		$('#push').click(function (){
			$('.add_push_wrap').show();
			$('.add_lesson_wrap').hide();
			$('.add_electronics_wrap').hide();
			$('.add_peer_wrap').hide();
		});
		$('#electronics').click(function (){
			$('.add_electronics_wrap').show();
			$('.add_lesson_wrap').hide();
			$('.add_push_wrap').hide();
			$('.add_peer_wrap').hide();
		});
		$('#peer').click(function (){
			$('.add_peer_wrap').show();
			$('.add_lesson_wrap').hide();
			$('.add_push_wrap').hide();
			$('.add_electronics_wrap').hide();
		});
		$('.com_cont_right1_right ul li').click(function(){ 
		    $(this).addClass("color_blue").siblings().removeClass("color_blue");
		  
		}) 
		
		$('.menu_list ul li').click(function(){ 
		    $(this).addClass("menu_list_act").siblings().removeClass("menu_list_act");
		    $(".menu_list_big .menu_list_tab").hide().eq($('.menu_list ul li').index(this)).show(); 
		}) 
		$('.menu_body ul li').click(function(){ 
		    $(this).addClass("menu_body_blue").siblings().removeClass("menu_body_blue");
		    /*$(".menu_body_tab_big .menu_body_tab").hide().eq($('.menu_body ul li').index(this)).show(); */
		}) 
	}
})(window,jQuery);
$(document).ready(function(){
	$("#firstpane .menu_body:eq(0)").show();
	$("#firstpane p.menu_head").click(function(){
		$(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
		$(this).siblings().removeClass("current");
		$("#planType").val($(this).val());
	});
});
