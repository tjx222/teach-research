<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="校际教研"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/schoolactivity/css/schoolactivity.css" media="screen" />
	<ui:require module="../m/schoolactivity/js"></ui:require>	
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header> 
		<span onclick="javascript:window.history.go(-1);"></span>校际教研 
		<div class="more" onclick="more()"></div>
	</header>
	<section>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','js'],function($){	
	});  
</script>
</html>