define(["require","zepto","iscroll","editor"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init();
	});
    function init() { 
    	if($("#annunciate_b_wrap").length>0){
			var annunciate_b_wrap = new IScroll('#annunciate_b_wrap',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true,
	      	});
		}
		if($(".act_draft_content").length>0){
			var act_draft_content = new IScroll('.act_draft_content',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true
	      	});
		}
    	$('.draft').click(function (){ 
    		$('#activity_iframe').attr("src","jy/annunciate/draft");
    		$('.act_draft_wrap').show();
        	$('.mask').show();  
    	});
    	$('.fbtz').click(function (){
    		$('.fb_option_wrap').show();
    		$('.mask').show();
    	});
    	$('.act_draft_title span').click(function (){
    		$('.act_draft_wrap').hide();
    		$('.mask').hide();
    		window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs?site_preference=mobile"
    	});
    	$('.fb_option_wrap').click(function (){
    		$('.fb_option_wrap').hide();
    		$('.mask').hide();
    	});
    	$('.del_thesis_title span').click(function(){
    		$('.del_thesis_wrap').hide();
    		$('.mask').hide();
    	});
        $('.btn_cencel').click(function(){
        	$('.del_thesis_wrap').hide();
        	$('.mask').hide();
    	});
    	$('.deleteNotice').click(function(){
    		$('.del_thesis_wrap').show();
    		$('.mask').show();
    		$('#deleteThesis').attr("data-id",$(this).attr("data-id"));
    		$('#deleteThesis').attr("data-status",$(this).attr("data-status"));
    	});
    	$('.draft_list_bottom').find('.del').click(function(){
    		var id=$(this).attr("data-id");
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/annunciate/deleteDraft",
    			data: {"id": id},
    			success:function(data){	
    			}
    	    });
    		$(this).parent().parent().parent().remove();
    	});
    	//删除通知公告
    	$('.btn_confirm').click(function(){
    		var id=$('#deleteThesis').attr("data-id");
            var status=$('#deleteThesis').attr("data-status");
            if(status==1){
            	$.ajax({
        			type:"post",
        			dataType:"json",
        			url:_WEB_CONTEXT_+"/jy/annunciate/deleteAnnunciate",
        			data: {"id": id},
        			success:function(data){
        				window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs?site_preference=mobile"
        			}
        	    });
            }else{
            	$('.del_thesis_wrap').hide();
            	$('.act_draft_content div').find('.deletedraft').remove();
            	$.ajax({
        			type:"post",
        			dataType:"json",
        			url:_WEB_CONTEXT_+"/jy/annunciate/deleteDraft",
        			data: {"id": id},
        			success:function(data){	}
        	    });
            }
    	});
    	//进入发布页面
    	$('.fb_option_pt').click(function(){
    		location.href= _WEB_CONTEXT_+"/jy/annunciate/release?type=0";
    	});
    	$('.fb_option_ht').click(function(){
    		location.href= _WEB_CONTEXT_+"/jy/annunciate/release?type=1";
    	})
    	//草稿箱修改
    	$('.draft_list_bottom').find('.edit').click(function(){
    		var id=$(this).attr("data-id");
    		var type=$(this).attr("data-type");
    		parent.location.href=_WEB_CONTEXT_+"/jy/annunciate/release?id="+id+"&type="+type;
    	});
    	//瀑布流加载
    	window.addData=function(data){
    		var content = $("#annunciateform",data).html();
			$("#annunciateform").append(content);
			var annunciate_b_wrap = new IScroll('#annunciate_b_wrap',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true,
	      	});
    	}
    	//置顶
    	$('.top').click(function(){
    		window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/isTop?id="+$(this).attr("data-id")+"&isTop=false";
    	});
    	//取消置顶
    	$('.qx_top').click(function(){
    		window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/isTop?id="+$(this).attr("data-id")+"&isTop=true";
    	});
    }
})