define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	}); 
	function init() {
		if($(".com_per_cont_care")!=null){
			var com_per_cont_care = new IScroll('.com_per_cont_care',{
				scrollbars:true,
				mouseWheel:true,
				fadeScrollbars:true,
				click:true,
			});
		}
		
		$('.right_option div a .right_option_act').each(function(index,obj){
			if(index==0){
				$(this).addClass('right_option_act1');
			}
			var that=this;
			$(this).click(function(){
				var index=$(that).parent().index()+1;
				$(that).addClass('right_option_act1').parent().siblings().find(".right_option_act").removeClass('right_option_act1');
				com_per_cont_care.scrollToElement(document.querySelector('#scroll>div:nth-child('+index+')'));
			});
			$('.right_option div a').last().find('.border1').remove();
		});
		
		$('.chat span').click(function(){
			var userid = $(this).attr("userid");
			window.location.href = _WEB_CONTEXT_+"/jy/companion/companions/compSendMsg/"+userid;
		});
	}
});