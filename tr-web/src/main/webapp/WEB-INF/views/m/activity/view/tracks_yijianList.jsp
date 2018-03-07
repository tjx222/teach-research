<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<ui:mHtmlHeader title="集备意见教案列表"></ui:mHtmlHeader>
<head>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="act_hour_wrap" id="act_hour">
		<div id="scroller">
			<div class="act_hour_wrap_tab" > 
				<c:forEach var="yijian" items="${yijianList }">
					<div class="hour" resId="${yijian.resId }">
					<div class="hour_title">教案</div>
					<h3><ui:sout value="${yijian.planName }" length="21" needEllipsis="true"></ui:sout> </h3>
					<p><img src="${ctxStatic }/common/icon_m/base/doc.png" /></p>
					<div class="hour_modified">
						修改人：${yijian.userName }
					</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div> 
</body>
<script type="text/javascript">
	require(['list'],function(){	
	}); 
</script>
</html>
