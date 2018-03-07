require(['jquery','editor','jp/jquery-ui.min','jp/jquery.dragsort.min','jp/jquery.blockui.min'],function(){
	var jq = require("jquery");
	jq(function(){
		//网络编辑器
		webEditorOptions = {
			width:"100%",
			height:'175px',  
			style:"2",
		};
		editor = jq("#web_editor").editor(webEditorOptions);
	});
	require("jp/jquery.dragsort.min")
	jq(function(){
		init();
	});
	function init() {
	}
});

//保存通知公告
function release(state){
	var html = editor[0].html();//文本框的html代码
	var text = editor[0].text();//文本框的文字
	$('#web_editor').text(html);//设置上html代码
	if(text.length>200){
		alert("转发说明字数不能超过200");
		return false;
	}
	if(state){
		if(confirm("您确定要发布吗？"))
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/annunciate/forwardAnnunciate?status=1",
				data:$("#annunciate_form").serialize(),
				success:function(data){
					if(data.result.code==1){
						alert("通知公告发布成功");
						window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs";
					}else{
						alert("通知公告发布失败");
					}
					
				}
			});
	}else{
		if(confirm("您确定要存入草稿箱吗？"))
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/annunciate/forwardAnnunciate?status=0",
				data:$("#annunciate_form").serialize(),
				success:function(data){
					if(data.result.code==1){
						window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs";
					}else{
						alert("通知公告存草稿失败");
						}
						
					}
				});
	}
}
