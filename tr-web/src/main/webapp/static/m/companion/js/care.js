define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	var expmenu_ex ;
	$(function(){
		init(); 
	}); 
	function init() {
			expmenu_ex = new IScroll('#expmenu_ex',{
		   		 scrollbars:true,
		   		 mouseWheel:true,
		   		 fadeScrollbars:true, 
		   		 click:true,
			});
			$("ol.expmenu_ex li > a.header").click(function() {
				$(this).parent().find("ol.menu").toggle();
				var arrow = $(this).find("span.arrow");
				if (arrow.hasClass("up")) { 
					arrow.removeClass("up");
					arrow.addClass("down");
				} else if (arrow.hasClass("down")) {
					arrow.removeClass("down");
					arrow.addClass("up");
				} 
				expmenu_ex.refresh();
			});
			
		$('.search1_btn').click(function (){
			$("#username").val($.trim($("#searchContent").val()));
			$("#companionForm").submit();
		});
		$('.split').click(function (){
			window.parent.userSenderMsg($(this).attr("userid"));
		});
	}
});