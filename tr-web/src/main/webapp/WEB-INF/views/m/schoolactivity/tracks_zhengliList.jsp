<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<ui:mHtmlHeader title="集备整理教案列表"></ui:mHtmlHeader>
<head>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<ui:require module="../m/schoolactivity/js"></ui:require>
<script type="text/javascript">
</script>
</head>
<body>
<div class="act_modify_content1" id="act_modify">
	<div id="scroller">
		<c:forEach var="zhengli" items="${zhengliList }">
			<div class="hour" resId="${zhengli.resId }">
				<div class="hour_title">教案</div>
				<h3><ui:sout value="${zhengli.planName }" length="21" needEllipsis="true"></ui:sout></h3>
				<p><img src="${ctxStatic }/common/icon_m/base/doc.png" /></p>
				<div class="hour_modified">
				</div>
			</div>
		</c:forEach>
	</div> 
</div>
</body>
<script type="text/javascript">
	require(['list'],function(){	
	}); 
</script>
</html>
