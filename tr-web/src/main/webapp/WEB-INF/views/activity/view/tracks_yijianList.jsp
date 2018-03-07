<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<ui:htmlHeader title="集备意见教案列表"></ui:htmlHeader>
<head>
	<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
<script type="text/javascript">
//新选项卡查看修改教案
function scanLessonPlanTrack(resId,orgId){
	window.open(_WEB_CONTEXT_+"/jy/activity/scanLessonPlanTrack?resId="+resId+"&orgId="+orgId,"hidenframe");
}
</script>
</head>
<body style="background:#fff;">
<c:if test="${!empty yijianList }">
<c:forEach var="yijian" items="${yijianList }">
<dl class="participant_edit_right_cont_dl">
	<dd>
		<img src="${ctxStatic }/common/icon/base/word.png" title="${yijian.planName }" onclick="scanLessonPlanTrack('${yijian.resId}','${yijian.orgId}');">
	</dd>
	<dt>${yijian.userName }</dt>
</dl>
</c:forEach>
</c:if>
<c:if test="${empty yijianList }">
	<div class="emptyInfo">还没有参与人修改留痕！</div>
</c:if> 
<iframe id="hiddenIframe" style="display: none;"></iframe>
</body>
</html>
