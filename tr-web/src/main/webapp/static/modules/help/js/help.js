$(document).ready(function(){
	$("ul.expmenu li > div.header").click(function(){
		var arrow = $(this).find("span.arrow");
		if(arrow.length > 0){
			if(arrow.hasClass("up")){
				arrow.removeClass("up").addClass("down");
			}else if(arrow.hasClass("down")){
				arrow.removeClass("down").addClass("up");
			}
		}
		
		if(!$(this).hasClass("headers")){
			$("div.headers").removeClass("headers");
			$(this).addClass("headers");
		}
		$(this).parent().find("ul.menu").slideToggle();
		
	});
	$("ul.expmenu li > a.header1").click(function(){
		$(this).parent().find("ul.menu1").slideToggle();
	});
	
	$("ul.menu li a").click(function(){
		$("ul.menu li a.menu1_act").removeClass("menu1_act");
		$(this).addClass("menu1_act")
		
	});
	
});