define(["require","zepto","iscroll"], function (require) {
	var jq = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	var myplanbook_catalog_1 = new IScroll('.myplanbook_catalog_1',{
       		scrollbars:true,
       		mouseWheel:true,
       		fadeScrollbars:true,
       		click:true,
    	}); 
    	jq('.top_book').click(function (){
    		jq('.mask').show();
    		jq('.cw_menu_wrap').show();
    		var cw_menu_list_wrap1 = new IScroll('.cw_menu_list_wrap1',{
           		scrollbars:true,
           		mouseWheel:true,
           		fadeScrollbars:true,
           		click:true,
        	});
    	});
    	jq('.cw_menu_wrap').click(function (){
    		jq('.mask').hide();
    		jq('.cw_menu_wrap').hide();
    	});
    	jq('.cw_menu_list_wrap1 p').click(function (){ 
        	jq(this).addClass("act").siblings().removeClass("act");
    	});
    	jq('.qx_submit_btn').click(function (){
    		jq('.myplanbook_cont_r_hide').hide();
    		jq('.myplanbook_cont_r').show();
    		var myplanbook_catalog_1 = new IScroll('.myplanbook_catalog_1',{
           		scrollbars:true,
           		mouseWheel:true,
           		fadeScrollbars:true,
           		click:true,
        	}); 
    	});
    	jq('.submit_btn').click(function (){
    		jq('.myplanbook_cont_r_hide').show();
    		jq('.myplanbook_cont_r').hide();
    		var myplanbook_catalog2 = new IScroll('.myplanbook_catalog2',{
           		scrollbars:true,
           		mouseWheel:true,
           		fadeScrollbars:true,
           		click:true,
        	});
    		
    	});
    	/* 课题树形 start*/
    	jq('.myplanbook_catalog1 h4 a strong').click(function (){ 
    		var one = jq(this).parent().parent().next();
		    if(jq(this).hasClass('span_act')){
		    	jq(this).removeClass('span_act'); 
				jq(this).addClass('strong_act');
				one.find('a strong').removeClass('span_act');
				one.find('a strong').addClass('strong_act');
				one.find('.sub_strong_act').removeClass('strong_act').removeClass('span_act');
			}else{
				jq(this).addClass('span_act');
				jq(this).removeClass('strong_act');
				one.find('a strong').addClass('span_act');
				one.find('a strong').removeClass('strong_act');
				one.find('.sub_strong_act').removeClass('strong_act').removeClass('span_act');
			} 
    	});
    	jq('.myplanbook_catalog1 ul>li>a strong').click(function (){ 
    		var two = jq(this).parent().next();
    		var one_level = jq(this).parent().parent().parent().parent();
    		if(jq(this).hasClass('span_act')){
		    	jq(this).removeClass('span_act'); 
				jq(this).addClass('strong_act');
				two.find('a strong').removeClass('span_act');
				two.find('a strong').addClass('strong_act');
				one_level.find('h4 strong').removeClass('span_act').addClass('strong_act');
			}else{
				jq(this).addClass('span_act');
				jq(this).removeClass('strong_act');
				two.find('a strong').addClass('span_act'); 
				two.find('a strong').removeClass('strong_act');
			}  
    	});
    	jq('.myplanbook_catalog1 ol li a strong').click(function (){ 
    		var three = jq(this).parent().parent().parent().parent();
    		if(jq(this).hasClass('span_act')){
		    	jq(this).removeClass('span_act'); 
				jq(this).addClass('strong_act');
				three.find("a").first().find("strong").removeClass('span_act').addClass('strong_act');
    	        three.parent().parent().find('h4 strong').removeClass('span_act').addClass('strong_act');
			}else{
				jq(this).addClass('span_act');
				jq(this).removeClass('strong_act');
			} 
    	});
    	/* 课题树形 end*/
    }
});