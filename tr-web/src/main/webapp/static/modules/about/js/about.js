$(document).ready(function(){
					
	$("ul.expmenu li > div.header").click(function(){
		$(this).addClass("headers").parent().siblings().children().removeClass("headers");
		
	});
	
	
});