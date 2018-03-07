define(["require","zepto","iscroll","placeholder"], function (require) {
	var $= Zepto; 
	var comment_list;
	$(function(){
			init();
	});
	function init(){
		comment_list = new IScroll('#comment_list',{
      		 scrollbars:true,
      		 mouseWheel:true,
      		 fadeScrollbars:true,
      		 click:true
      	});
		$(".publish_btn").click(function(){
			saveComment();
		});
	}
	
	window.commentreply = function(obj){
		var id = $(obj).attr("divId");
		if($("#rep"+id).length == 1)return false;
		$(".reply_comment_div").remove();
		var parentid = $(obj).attr("parentId");
		$("#parentId").val(parentid);
		$("#opinionId").val($(obj).attr("opinionId"));
	//		$("#authorId").val($(obj).attr("data-authorid"));
		var uname = $(obj).attr("uname");
		var classname = $(obj).attr("class");
		var htmlStr = "";
		if(classname=="comments_reply"){
			htmlStr = '<div id="rep'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="" cols="" style="width:100%;height:5rem;margin-top:1rem;border:0.083rem #bdbdbd solid;" ></textarea>'+
			'<div onclick="submitReply()" class="btn_fs_reply">发送</div></div>';
		}else if(classname=="comments_reply1"){
			htmlStr = '<div id="rep'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="" cols="" style="width:100%;height:5rem;margin-top:1rem;border:0.083rem #bdbdbd solid;" ></textarea>'+
			 '<div onclick="submitReply();" class="btn_fs_reply">发送</div></div>';
		}
	    $(obj).after(htmlStr);
	    $(".contentval")[0].focus(); 
	    comment_list.refresh();
	    $(".contentval").placeholder({ 
	    	 word: '回复'+uname 
	   	});
	};
	
	window.saveComment = function(clssname){
		var content = $.trim($(".publish").val());
		if(content!="" && content.length>0){
			if(content.length<=300){
				$("#content_hidden").val(content);
				if(confirm("您确定要发送评论意见吗？")){
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/comment/addComment",
						data:$("#jb_comment_form").serializeArray(),
						success:function(data){
							if(data.msg=="ok"){
								window.successAlert("发送评论成功！",false,function(){
									document.location.reload();
								});
							}else{
								window.successAlert(data.msg,false,function(){
									document.location.reload();
								});
							}
							
						}
					});
				}
			}else{
				window.successAlert("评论信息不能超过300字！");
			}
		}else{
			window.successAlert("请您填写评论信息！");
		}
	};
	
	window.submitReply = function(){
		var content = $.trim($('.contentval').val());
		if(content!="" && content.length>0){
			if(content.length<=300){
				$("#content_hidden").val(content);
				if(confirm("您确定要提交评论意见吗？")){
					$.ajax({
						type:"post",
						dataType:"html",
						url:_WEB_CONTEXT_+"/jy/comment/relyComment",
						data:$("#jb_comment_form").serializeArray(),
						success:function(data){
							$(".reply_comment_div").remove();
							window.successAlert("提交成功！",false,function(){
								document.location.reload();
							});
						}
					});
				}
			}else{
				window.successAlert("回复信息不能超过300字！");
			}
		}else{
			window.successAlert("请您填写回复信息！");
		}
	 };
		 
	 window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").append(content.find(".consult_comments"));
		comment_list.refresh();
	 };
	
});