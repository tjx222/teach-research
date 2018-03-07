/**
 * 同伴互助停靠菜单基础js
 */
;(function ( window, $) {
	$(document).ready(function () {
		$(".companion_menu").mouseenter(function () {
			$(".suspension").stop().animate({ right:0},500 );
		});
		$(".suspension").mouseleave(function () {
			$(".suspension").stop().animate({ right:-253},500);
		});
		$(".return").mouseenter(function () {
			$(".return").stop().animate({ right:0},500 );
		});
		$(".return").mouseleave(function () {
			$(".return").stop().animate({ right:-79},500);
		});
        $(window).scroll(function(){
            if ($(window).scrollTop()>1300){
                $(".return").fadeIn(1500);
            }
            else
            {
                $(".return").fadeOut(1500);
                }
            });
 
            //当点击跳转链接后，回到页面顶部位置
        $(".return").click(function(){
            $('body,html').animate({scrollTop:0},1000);
            return false;
        });

	}); 
})(window,jQuery);