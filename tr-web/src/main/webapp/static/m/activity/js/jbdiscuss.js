define(["require","zepto","iscroll","placeholder"], function (require) {
	var $= Zepto; 
	var zt_par_dis;
	$(function(){
		init();
	}); 
	function init(){
		zt_par_dis = new IScroll('#zt_par_dis',{
     		 scrollbars:true,
     		 mouseWheel:true,
     		 fadeScrollbars:true,
     		 click:true
     	});
		$(".publish1_btn").click(function(){
			$("#discussLevel").val(1);
			$("#parentId").val(0);
			saveDiscuss("publish1");
		});
	}
	
	window.discussionreply = function(obj){
		var id = $(obj).attr("divId");
		if($("#rep"+id).length == 1)return false;
		$(".reply_comment_div").remove();
		var parentid = $(obj).attr("parentId");
		$("#parentId").val(parentid);
		$("#discussLevel").val(2);
	//		$("#authorId").val($(obj).attr("data-authorid"));
		var uname = $(obj).attr("uname");
		var classname = $(obj).attr("class");
		var htmlStr = "";
		if(classname=="discussion_reply"){
			htmlStr = '<div id="rep'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="" cols="" style="width:100%;height:5rem;margin-top:1rem;border:0.083rem #bdbdbd solid;"></textarea>'+
			'<div onclick="saveDiscuss(\'contentval\')" class="btn_fs_reply">发送</div></div>';
		}else if(classname=="discussion_reply1"){
			htmlStr = '<div id="rep'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="" cols="" style="width:100%;height:5rem;margin-top:1rem;border:0.083rem #bdbdbd solid;"></textarea>'+
			 '<div onclick="saveDiscuss(\'contentval\');" class="btn_fs_reply">发送</div></div>';
		}
	    $(obj).after(htmlStr);
	    $(".contentval")[0].focus(); 
	    zt_par_dis.refresh();
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
							if(data.msg=="ok"){
								window.parent.successAlert("发送讨论成功！",false,function(){
									document.location.reload();
								});
							}else{
								window.parent.successAlert(data.msg,false,function(){
									document.location.reload();
								});
							}
						}
					});
				}
			}else{
				window.parent.successAlert("讨论信息不能超过300字！");
			}
		}else{
			window.parent.successAlert("请您填写讨论信息！");
		}
	};
	
	window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").append(content.find(".partake_discussion"));
		zt_par_dis.refresh();
	};
	
});