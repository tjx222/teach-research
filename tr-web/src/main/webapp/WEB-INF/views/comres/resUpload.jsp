<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<ui:htmlHeader title="导入推荐资源"></ui:htmlHeader>
<style>
.datagrid-mask {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  opacity: 0.3;
  filter: alpha(opacity=30);
  display: none;
}
.datagrid-mask-msg {
  position: absolute;
  clear:both;
  margin-top: 20px;
  padding: 12px 5px 10px 20px;
  width: auto;
  height: 16px;
  border-width: 3px;
  border-style: solid;
  display: none;
}
</style>
<script>
function showLoading(){
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	 $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载中......").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}
function removeLoading(){
	$("body").find("div.datagrid-mask-msg").remove(); 
	$("body").find("div.datagrid-mask").remove(); 
}
</script>
</head>
<body style="height: 500px">
	<form name="form1" action="resUpload">
	<div>
		上传资源目录：<input name="resfile" id="resfile" type="text" name="resfile"/>
		<input type="button" value="上传" onclick="uploadRes();"/>
	</div>
    </form>
</body>
</html>

<script type="text/javascript">
function uploadRes(){
	var resfile = $("input[name='resfile']").val();
	if(confirm("您确定要上传资源  ["+resfile+"] 吗？")){
		showLoading();
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/comresUpload/resUpload",
			data:{"resfile":resfile},
			success:function(data){
				if(data){
					removeLoading();
					alert("提交成功！");
					document.location.reload();
				}else{
					removeLoading();
					alert("提交失败！");
				}
			}
		});
	}
}
</script>
