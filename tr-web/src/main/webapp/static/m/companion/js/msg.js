define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	var fileName = "";
	var chat_content;
	$(function(){
		init(); 
	});
	function init() {
		chat_content = new IScroll('#chat_content',{
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true
      	});
		$('.span1').click(function (){//用户互通消息列表
			$("#msglist_form").submit();
		});
		$('.span2').click(function (){//用户信息和分享信息
			var userid = $(this).attr("userid");
			window.location.href = _WEB_CONTEXT_+"/jy/companion/companions/compshare?companionId="+userid+"&selfinfo=true";
		});
		$('.first_page').click(function (){
			if($("#currentPageSet").val()=="1"){
				successAlert("已经是第一页了");
			}else{
				$("#currentPageSet").val("1");
				$("#msglist_form").submit();
			}
		});
		$('.prev_page').click(function (){
			if($("#currentPageSet").val()=="1"){
				successAlert("已经是第一页了");
			}else{
				$("#currentPageSet").val($("#currentPageSet").val()*1-1*1);
				$("#msglist_form").submit();
			}
		});
		$('.next_page').click(function (){
			if($("#currentPageSet").val()==$.trim($(".lastPageClassValue").text())){
				successAlert("已经是最后一页了");
			}else{
				$("#currentPageSet").val($("#currentPageSet").val()*1+1*1);
				$("#msglist_form").submit();
			}
		});
		$('.last_page').click(function (){
			if($("#currentPageSet").val()==$.trim($(".lastPageClassValue").text())){
				successAlert("已经是最后一页了");
			}else{
				$("#currentPageSet").val($.trim($(".lastPageClassValue").text()));
				$("#msglist_form").submit();
			}
		});
		$('.fs_btn').click(function(){
			var message = $.trim($(".send_message_txt").val());
			if(message!=""){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/companion/messages",
					data:$("#send_message_form").serializeArray(),
					success:function(data){
						if(data.result.code==1){
							window.location.reload(); 
						}else{
							successAlert(data.result.msg);
						}
						
					} 
				
					
				});
			}else{
				successAlert("内容不能为空！");
			}
		});
	}
	window.beforeUpload = function(){
		var file = $("#fileToUpload_1").val();
		var pos=file.lastIndexOf("\\");
		fileName = file.substring(pos+1);
		return true;
	};
	window.afterUpload = function(data){
		if(data.code==0){
			$("#attachment1").val(data.data);
			$("#attachment1Name").val(fileName);
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/companion/messages",
				data:$("#send_message_form").serializeArray(),
				success:function(data){
					if(data.result.code==1){
						window.location.reload();
					}else{
						successAlert(data.result.msg);
					}
				}
			});
		}
	};
	window.addmoredatas = function(data){
		var content = $("#addmoredatas",data);
		$("#addmoredatas").prepend(content.find(".chat_content_div"));
		chat_content.refresh();
	 };
});