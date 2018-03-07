require(['jquery','editor'],function(){
	var jq = require("jquery");
	var clickcount = 0;
	var editor;
	var $ = jq;
	jq(function(){
		webEditorOptions = {width:"550px",height:"166px",style:0,afterChange : function() {
	    	var txtcount = this.count('text');
	    	if(txtcount > 300){
	    		$('#w_count').html("<font color='red'>"+txtcount+"</font>");
	    	}else{
	    		$('#w_count').html(txtcount);
	    	}
		}
	    };
		editor = $("#discussion_content").editor(webEditorOptions)[0];
	});
	
	window.fabu = function(activityId){
		if(clickcount==0){
			clickcount=1;
			faqi(activityId);
		}
	}
	
	/**
	 * 发布讨论
	 */
	window.faqi = function(activityId){
		editor.sync();
		var context = $("#discussion_content").val();
		if(context == ""){
			clickcount=0;
			alert("讨论内容不能为空！");
			return false;
		}
		if($("#w_count").text() > 300){
			alert("您最多可以输入300个字！");
			clickcount=0;
			return false;
		}
		$("#content_hidden").val(context);
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/comment/discussSave.json",
			data:$("#fabu").serialize(),
			success:function(data){
				editor.text("");
				if(data.isSuccess){
					if(data.msg == "ok"){
//						alert("提交成功！");
						$("#discuss_iframe").attr("src","jy/comment/discussIndex?typeId=5&activityId="+activityId+"&canReply=true&"+ Math.random());
					}else if(data.msg!=null && data.msg!=""){
						alert(data.msg);
					}
				}else{
					alert("保存失败");
				}
				clickcount=0;
			}
		});
	};
	/**
	 * 查看此用户发起的讨论
	 */
	window.discussUser = function(obj,activityId,userId,canReply){
		$("#discuss_iframe").attr("src","jy/comment/discussIndex?typeId=5&activityId="+activityId+"&crtId="+userId+"&canReply="+canReply+"&"+ Math.random());
		$(obj).addClass("partake_wrap1").siblings().removeClass("partake_wrap1");  
	};
});
