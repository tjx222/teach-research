<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:htmlHeader title="教案修改"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/edit.css">
<script type="text/javascript" src="${ctxStatic}/modules/writelessonplan/js/js.js"></script>
<script type="text/javascript">
function saveToServer_iframe(){
	try {
		document.getElementById("iframe3").contentWindow.saveToServer();
　　     } catch(error) {
	
　　     }
}
function fadeInOrOut_iframe(){
	try {
		document.getElementById("iframe3").contentWindow.fadeInOrOut();
　　     } catch(error) {
	
　　     }
}
function uploadLocalLesson(){
	$(".pageOfficediv",document.getElementById("iframe3").contentWindow.document).css("margin-top","1000px");
	if($(".dialog_content").html()==""){
		$("#book_option").dialog({'width':'400',"height":"260","url":"${ctx}jy/lessonplan/tolessonplanEditFile"});
	}else{
		$("#book_option").dialog("show");
	}
}
function dialog_close_callback(){
	$(".pageOfficediv",document.getElementById("iframe3").contentWindow.document).css("margin-top","2px");
}
</script>
</head>


<body style="margin:0 auto;">
<input type="hidden" id="plan_id" name="planId" value="${planId}"/>
<div class="wrap_edit" style="height:100%;">
<div id="qwe" style="height: 35px;line-height:35px;overflow:hidden;">
	<h3 style="width: 170px;float: left;cursor: pointer;margin-right:10px;margin-left: 10px;" onclick="fadeInOrOut_iframe();">
		<span class="qwe_h3_span" title="工具栏隐藏/显示"></span>点击这里，展开/收起工具栏
	</h3>
	<input type="button" onclick="uploadLocalLesson();" class="saveJA"
			value="上传本地教案">
	<input type="button" onclick="saveToServer_iframe();" value="保存教案" class="saveJA">
	<div id="book_option" class="dialog"> 
		  <div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">上传本地教案</span>
				<span onclick="dialog_close_callback();" class="dialog_close"></span>
			</div>
			<div class="dialog_content"></div>
		  </div>
	</div>
</div>
	<iframe id="iframe3" style="border: none;z-index:-1;width:100%;height:100%;" scrolling="no" frameborder="0"  allowtransparency="true" src="${pageContext.request.contextPath }/jy/lessonplan/toEditLessonPlanView?planId=${planId}">
	
	</iframe>

</div>
<!-- <div class="clear"></div> -->
<script>
 $(document).ready(function(){
	var wH = document.documentElement.clientHeight? document.documentElement.clientHeight - 20:document.body.clientHeight - 20;
	$(".wrap_edit").css({"height": wH});
	$("#iframe3").css({"height":wH-35})
	$(window).resize(function() {
		var wH = document.documentElement.clientHeight? document.documentElement.clientHeight - 20:document.body.clientHeight - 20;
		$(".wrap_edit").css({"height": wH});
		$("#iframe3").css({"height":wH-35})
	});
}) 

</script>
</body>
</html>