define(["require","zepto","iscroll","placeholder"], function (require) {
	var $= Zepto;  
	var partake_discussion;
	$(function(){
		if($("#partake_discussion").length>0){
		partake_discussion = new IScroll('#partake_discussion',{
    		 scrollbars:true,
    		 mouseWheel:true,
    		 fadeScrollbars:true,
    		 click:true
    	});
		}
	});
	window.discussionreply = function(obj){
		var id = $(obj).attr("divId");
		if($("#rep"+id).length == 1)return false;
		$(".reply_comment_div").remove();
		var parentid = $(obj).attr("parentId");
		$("#parentId").val(parentid);
		$("#discussLevel").val(2);
		var uname = $(obj).attr("uname");
		var classname = $(obj).attr("class");
		var htmlStr = "";
		if(classname=="discussion_reply"){
			htmlStr = '<div id="rep'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="" cols="" autofocus style="width:100%;height:5rem;margin-top:1rem;border:0.083rem #bdbdbd solid;" ></textarea>'+
			'<div onclick="saveDiscuss(\'contentval\')" class="btn_fs_reply">发送</div></div>';
		}else if(classname=="discussion_reply1"){
			htmlStr = '<div id="rep'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="" cols="" autofocus style="width:100%;height:5rem;margin-top:1rem;border:0.083rem #bdbdbd solid;" ></textarea>'+
			 '<div onclick="saveDiscuss(\'contentval\');" class="btn_fs_reply">发送</div></div>';
		}
	    $(obj).after(htmlStr);
	    $(".contentval")[0].focus(); 
	    partake_discussion.scrollToElement(document.querySelector('.reply_comment_div'),0);
	    partake_discussion.refresh();
	    $(".contentval").placeholder({ 
	    	 word: '回复'+uname 
	   	});
	};
	window.saveDiscuss = function(clssname){
		var content = $.trim($("."+clssname).val());
		if(content!="" && content.length>0){
			if(content.length<=300){
				$("#content_hidden").val(content);
				if(confirm("您确定要发送讨论意见吗？")){
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/comment/discussSave",
						data:$("#jbdiscussform").serializeArray(),
						success:function(data){
							
							$("."+clssname).val("");
							if(data.msg=="ok"){
								alert("发送讨论成功！")
								window.location.reload();
							}else{
								alert(data.msg);
							}
						}
					});
				}
			}else{
				alert("讨论信息不能超过300字！");
			}
		}else{
			alert("请您填写讨论信息！");
		}
	};
	
	window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").append(content.find(".partake_discussion"));
		partake_discussion.refresh();
	};
	
});