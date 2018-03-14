define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function loaded() {
 		setTimeout(function () {
 			 var swiper = new Swiper('.swiper-container', {
 				pagination: '.swiper-pagination',
 		        paginationClickable: true,
 		        spaceBetween: 30,
 		    });
 	    })
	}
    window.addEventListener('load', loaded, false); 
	function init() {
		$('.identity img').click(function (){
			location.href = "jy/uc/modify";
		});
		$('#jydt').click(function (e){ 
			$('.header_ul_menu_wrap').toggle();
			e.stopPropagation();
			/*location.href = "jy/school/mng/schoolmoving";*/
		});
		$('#wrapper').click(function (){ 
			$('.header_ul_menu_wrap').hide(); 
		});
		$('.identity_option').click(function (){
			if($('.identity_menu_wrap').length>0){
				$('.mask').show();
				$('.identity_menu_wrap').show();
			}
		});
		$('.identity_menu_wrap').click(function (){
			$('.mask').hide();
			$('.identity_menu_wrap').hide();
		});
		
//		$(".swiper-wrapper").find("dd").click(function(){
//			location.href = $(this).attr("url");
//		});
		
		$("#tzgg").click(function(){
			location.href = "jy/annunciate/noticeIndex";
		});
		$("#tzgg2").click(function(){
			location.href = "jy/annunciate/noticeIndex";
		});
		$(".identity_menu_wrap1").find("p").click(function(){
			location.href = $(this).attr("url");
		});
		
		//初始化子菜单
		$(".slide_div dd img ").click(function(e){
			var jqthis = $(this);
			showChild([],jqthis);
		});
	}
	
	function showChild(children,container){
			var dddom = container.parent("dd");
			var newWin = window.open('页面加载中。。。');  
            newWin.location.href = dddom.attr("url");
	}
});