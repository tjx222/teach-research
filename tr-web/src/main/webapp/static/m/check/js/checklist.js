define(["require","zepto","iscroll","placeholder"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	var myScroll3;
	$(function(){
		init();
	}); 
	function init() {
		 myScroll3 = new IScroll('#look_opinion_list',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      		click:true,
      	});	
		$('.thesis_img_3').click(function (){
          	$('.mask').show(); 
          	myScroll3.refresh();
        });
		$('.publish_btn').click(function (){
			submitCheck();
		});
		$('.my_publish_index_m .cyxx span').click(function (){
			var spid = $(this).attr("spid");
			$.each($('.my_publish_index_m .cyxx span'),function(index,obj){
				if(spid == index) $(obj).removeClass("jz");
				else $(obj).addClass("jz");
			});
			$("#level").val(spid);
		});
	}
	window.submitReply = function(replyId){
		var content = $.trim($('.contentval').val());
		if(content!="" && content.length>0){
			if(content.length<=300){
				$("#content").val(content);
				if(confirm("您确定要提交查阅意见吗？")){
					$.ajax({
						type:"post",
						dataType:"html",
						url:_WEB_CONTEXT_+"/jy/check/reply",
						data:$("#check_comment_form").serializeArray(),
						success:function(data){
							$(".reply_comment_div").remove();
							successAlert("提交成功！",false,function(){
								document.location.reload();
							});
						}
					});
				}
			}else{
				successAlert("回复信息不能超过300字！");
			}
		}else{
			successAlert("请您填写回复信息！");
			$(".contentval")[0].focus();
		}
	 };
	 window.submitCheck = function(){
		 var content = $.trim($('.publish').val());
		 if(content.length<=300){
			 $("#checkFlago").val(content);
			 if(confirm("您确定要提交查阅意见吗？")){
				 $.ajax({
					 type:"post",
					 dataType:"json",
					 url:_WEB_CONTEXT_+"/jy/check/check",
					 data:$("#submit_check_comment_form").serializeArray(),
					 success:function(data){
						 successAlert("提交成功！",false,function(){
							document.location.reload();
						});
					 }
				 });
			 }
		 }else{
			 successAlert("查阅信息不能超过300字！");
		 }
	 };
	 window.checkreply = function(obj){
		var id = $(obj).attr("divId");
		if($("#"+id).length == 1)return false;
		$(".reply_comment_div").remove();
		var parentid = $(obj).attr("parentId");
		$("#parentId").val(parentid);
		$("#opinionId").val($(obj).attr("opinionId"));
		var uname = $(obj).attr("uname");
		var classname = $(obj).attr("class");
		var htmlStr = "";
		if(classname=="reply"){
			htmlStr = '<div id="'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="4" cols="62" autofocus style="width:30rem;height:6rem;margin-top:1rem;border:0.083rem #bdbdbd solid;"></textarea>'+
			'<div onclick="submitReply('+parentid+')" class="btn_fs">发送</div></div>';
		}else if(classname=="reply1"){
			htmlStr = '<div id="'+id+'" class="reply_comment_div" ><textarea class="contentval" rows="4" cols="52" autofocus style="width:26rem;height:6rem;margin-top:1rem;border:0.083rem #bdbdbd solid;"></textarea>'+
			 '<div onclick="submitReply('+parentid+');" class="btn_fs">发送</div></div>';
		}
	    $(obj).after(htmlStr);
	    $(".contentval")[0].focus(); 
	    myScroll3.refresh();
	    $(".contentval").placeholder({ 
	    	 word: '回复'+uname 
	   	});
	 };
	 window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").append(content.find(".activity_tch"));
		myScroll3.refresh();
	 };
});