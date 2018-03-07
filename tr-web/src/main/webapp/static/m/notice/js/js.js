define(["require","zepto","iscroll"], function (require) {
	var jq = Zepto;
	var  annunciate_b_wrap;
	$(function(){ 
		init();
		bindEvent();
	});
    function init() { 
    	    annunciate_b_wrap = new IScroll('#annunciate_b_wrap',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    }
    function bindEvent(){
    	jq(".annunciate_bottom").find("q").click(function(){ //绑定单选
    		if(jq(this).attr("class")=="radio_act"){
    			jq(this).removeClass("radio_act");
    		}else{
    			jq(this).addClass("radio_act");
    		}
    	});
    	jq(".notice").find("q").click(function(){ //绑定全选
    		if(jq(this).attr("class")=="radio_act"){
    			jq(this).removeClass("radio_act");
    			jq(".annunciate_bottom").find("q").removeClass("radio_act");
    		}else{
    			jq(this).addClass("radio_act");
    			jq(".annunciate_bottom").find("q").addClass("radio_act");
    		}
    	});
    	
    	jq("#tableList").on("click",".del",function(){ //绑定单条删除
    		var r=confirm("您确定要删除该条消息吗？删除后将无法恢复！");
			if(r){
				jq.ajax({
					url:_WEB_CONTEXT_+'/jy/notice/notices/'+$(this).attr('dataId'),
					'type':'delete',
					'dataType':'json',
					'success':function(result){
						if(result.result.code==1){
							location.href=location.href;
						}else{
							alert("删除失败："+result.result.msg);
						}
					}
				});
			}
    	});
    	
    	jq(".delete").click(function(){ //绑定批量删除
    		var noticeIds = jq(".annunciate_bottom").find("q[class='radio_act']");
    		if(noticeIds.length<=0){
    			alert('您还没有选择要删除的通知！');
				return;
    		}else{
    			var noticeIdAttr=[];
    			noticeIds.each(function(){
    				noticeIdAttr.push(jq(this).attr("dataId"));
    			});
        		$.ajax({
        			url:_WEB_CONTEXT_+'/jy/notice/deleteNotices?noticeIds='+noticeIdAttr.join(','),
    				'type':'delete',
    				'dataType':'json',
    				'success':function(result){
    					if(result.result.code==1){
    						location.href=location.href;
    					}else{
    						alert("删除失败："+result.result.msg);
    					}
    				}
    			});
    		}
    	});
    	
    	jq(".yd").click(function(){ //绑定标记已读
    		var noticeIds = jq(".annunciate_bottom").find("q[class='radio_act']");
    		if(noticeIds.length<=0){
    			alert('您还没有选择要标记已读的消息！');
				return;
    		}else{
    			var noticeIdAttr=[];
    			noticeIds.each(function(){
    				noticeIdAttr.push(jq(this).attr("dataId"));
    			});
    			$.ajax({
    				url:_WEB_CONTEXT_+'/jy/notice/readedNotices?noticeIds='+noticeIdAttr.join(','),
					'type':'post',
					'dataType':'json',
					'success':function(result){
						if(result.result.code==1){
							location.href=location.href;
						}else{
							alert("标记已读失败："+result.result.msg);
						}
					}
				});
    		}
    	});
    	
    	jq(".annunciate_bottom").on("click","span",function(){
    		location.href = _WEB_CONTEXT_+"/jy/notice/notices/"+jq(this).attr("dataId");
    	});
    	
    }
    
    window.addData = function(data){
    	var content = jq("#tableList",data);
    	content.find("tr").eq(0).remove();
    	jq("#tableList").append(content.html());
    	annunciate_b_wrap.refresh();
    }
    
    
});