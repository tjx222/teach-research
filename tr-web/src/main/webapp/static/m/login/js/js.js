define(["require","zepto","iscroll"], function (require) { 
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init();
	});
    function init() {  
    	$('.check span').click(function (){
		    if($(this).hasClass('class_act')){
		    	$(this).removeClass('class_act');
			}else{
				$(this).addClass('class_act');
			}
		});
    	$(".login_btn").click(function (){ 
    		$(this).hide();
    		$(".btn_login").show();
    	});
    }
});