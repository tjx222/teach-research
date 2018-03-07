define(['require','jquery'],function(require){
	var jq = require('jquery');
	// 标识id
	var markId;
	// 监听回调map
	var listenerCallBackMap = new Object();
	// 获取标识id后执行函数队列
	var markIdInitedFuncs = new Array();
	
	$.get('./jy/push/',function(result){
		if(result.code==1){
			markId=result.data;
			for(var i=0;i<markIdInitedFuncs.length;i++){
				markIdInitedFuncs[i]();
			}
		}
	},'json');
	
	// 数据拉取
	var poll=function(){
		var url = './jy/push/'+markId;
		$.get(url,function(result){
			poll();
			// 返回错误消息
			if(result.error){
				consol.log(result.error);
			}else{// 返回消息
				listenerCallBackMap[result.pushType](result);
			}
		},'json');
	};
	markIdInitedFuncs.push(poll);
	
	return {
		register:function(pushType){
			var registerRemote = function(){
				var url = './jy/push/'+markId+'/listenerTypes';
				$.post(url,{'pushType':pushType});
			}
			//markid已初始化直接调用
			if(markId!=null){
				registerRemote();
			}else{//markid未初始化，等待初始化完成后再调
				markIdInitedFuncs.push(registerRemote);
			}
		},
		remove:function(pushType){
			var removeRemote = function(){
				var url = './jy/push/'+markId+'/listenerTypes';
				$.ajax({
					'url':url,
					data:{'pushType':pushType},
					method:'delete'
				});
			}
			//markid已初始化直接调用
			if(markId!=null){
				removeRemote();
			}else{//markid未初始化，等待初始化完成后再调
				markIdInitedFuncs.push(removeRemote);
			}
		}
	}
});