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
		$("#jhzj").click(function(){
			location.href = "jy/planSummary/punishs";
		});
		$(".identity_menu_wrap1").find("p").click(function(){
			location.href = $(this).attr("url");
		});
		
		//初始化子菜单
		var _CHILDMAP = [];
		var i=0;
		$(".slide_div").click(function(e){ 
			i++;
			if(i%2==0){
				$('.slide_div').find('.slide_hide').remove();
				_CHILDMAP=[];
			}else{
				var jqthis = $(this), mid = jqthis.attr("data-id"), m = _CHILDMAP[mid];
				if(!m){
					$.getJSON(_WEB_CONTEXT_+"/jy/uc/listChild?mid="+mid+"&"+Math.random(),function(data){
						  if(data.code == 0){
							  _CHILDMAP[mid]=data.data;
							  showChild(data.data,jqthis);
							}
						});
				}
			}
			
		});
	}
	
	function showChild(children,container){
		if(children.length > 0){
			var dl = $("<div class='slide_hide' />").append($("<div class='slide_hide_bg'>").append($("<ul/>")));
			for(var i=0;i<children.length;i++){
					var m = children[i];
					$("ul",dl).append($('<li/>').append($("<a/>").attr("href",m.url).html(m.name)));
				}
			$(container).append(dl);
		}else{
			location.href = container.find("dl").find("dd").attr("url");
		}
	}
});