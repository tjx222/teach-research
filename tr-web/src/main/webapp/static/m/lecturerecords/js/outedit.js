define(["require","zepto","iscroll"], function (require) {
	var $= Zepto; 
	$(function(){
		init();
	});
    function init() {  
    	var inneredit_content = new IScroll('.inneredit_content',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	}); 
    	$('#reply').click(function (){
    		$('.mask').show();
    		$('.opinions_comment_wrap').show();
    	});
    	$('.close').click(function (){
    		$('.mask').hide();
    		$('.opinions_comment_wrap').hide();
    	});
    	
    	//回复听课意见
    	$(".publish1_btn").click(function(){
    		var content = $.trim($(".publish1").val());
    		if(content!=""){
    			var resId = $("#resId").val();
    			var authorId = $("#authorId").val();
    			var teacherId = $("#teacherId").val();
    			$.ajax({
    				type:"post",
    				dataType:"json",
    				url:_WEB_CONTEXT_+"/jy/lecturereply/addreply.json",
    				data:{"content":content,
    					"authorId":authorId,
    		    		"resId":resId,
    		    		"teacherId":teacherId
    		    	},
    				success:function(data){
    					$(".publish1").val("");
    					if(data.code == 1){
    						$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/lecturereply/reply?resId="+resId+"&authorId="+authorId+"&teacherId="+teacherId);
    					}else{
    						successAlert("回复失败，请稍后重试！");
    					}
    				}
    			});
    		}else{
    			successAlert("请输入回复信息!");
    		}
    	});
    }
});