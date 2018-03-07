<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>非法访问</title>
</head>
<body>
	<div style="width:100%;height:500px;margin:0 auto;font-size:22px;text-align:center;font-weight:bold;line-height:500px;">
		<s:message code="no.permission"></s:message> <span id="time">5</span> 秒后返回首页。
	</div>
	<script type="text/javascript">
	var code=5;
	var URL;
	function turnoff(url){
		URL=url;
		for(var i=code; i>=0; i--){
			window.setTimeout("a("+i+")",(code-i)*1000);}
		}
	function a(num){
		document.getElementById("time").innerHTML=num;
		if(num==0){window.location=URL;} 
	 }
	turnoff("index");
	</script>
</body>
</html>