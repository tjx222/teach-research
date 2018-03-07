define(["require","zepto","iscroll"], function (require) {
	var $= Zepto;
	$(function(){
		init(); 
	});
	function init() {
		//绑定查看教研进度表右侧操作列表
		$('.content_list figure').eq(0).click(function (){ 
			$("#iframe1").css({'width':'50%','-webkit-transition':'width .1s',"-webkit-animation-fill-mode":"forwards"});
			$('.act_info_wrap').show(); 
		});
		$('.close').click(function (){
			$('.act_info_wrap').hide();  
		});
		$('.par_head_r1').click(function (){
			$('.par_head_1').hide();
			$('.par_head').show();
			
		});
		$('.par_head_r').click(function (){
			if($(this).html()=='查看'){ 
				$(this).html('取消');
				$('.par_head_float').show(); 
			}else if($(this).html()=='取消'){
				$(this).html('查看');
				$('.par_head_float').hide(); 
			} 
			var par_head_float = new IScroll('.par_head_float',{ 
				 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      	});
		});
	}
});