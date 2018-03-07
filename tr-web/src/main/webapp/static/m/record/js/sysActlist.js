define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto;  
	jq(function(){
		init(); 
	});
    function init() {
    	var  record_cont_bottom = new IScroll('.record_cont_bottom',{
      		 scrollbars:true,
      		 mouseWheel:true,
      		 fadeScrollbars:true,
      	 	 click:true,
      	});
    	jq('.activity_tch_right h4 span.wei').click(function (){
    		jq('.edit_portfolio_wrap').show();
    		jq('.mask').show();
    	});
    	jq('.close').click(function (){
    		jq('.edit_portfolio_wrap').hide();
    		jq('.mask').hide();
    		jq('.del_upload_wrap').hide();
    	});
    	jq('.portfolio_edit').click(function (){
    		jq(this).parent().siblings().find('.name_txt').css({'border':'0.083rem #dbdbdb solid'});
    		jq(this).parent().siblings().find('.portfolio_btn').show();
    		jq(this).parent().siblings().find('.border_bottom').show();
    		jq(this).parent().siblings().find('.note').show();
    	});
    	jq('.activity_tch_right h4 span.del').click(function (){
    		jq('.del_upload_wrap').show();
    		jq('.mask').show();
    	});
    }
});