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
<div class='edit_zb_lessonplan_cont'>
<c:if test="${!empty zhengliList }">
<c:forEach var="zhengli" items="${zhengliList }">
<dl>
	<dd>
		<img src="${ctxStatic }/common/icon/base/word.png" title="${zhengli.planName }" onclick="scanLessonPlanTrack('${zhengli.resId}','${zhengli.orgId}');">
	</dd>
	<dt title="${zhengli.planName }" >${zhengli.planName }</dt>
</dl>
</c:forEach>
</c:if>
<c:if test="${empty zhengliList }">
<script type="text/javascript">
$("#fasong",window.parent.document).hide();
</script>
	<div class="emptyInfo">大家已提出了修改建议，您赶紧去<span onclick="parent.wantToEdit1();">"修改教案"</span>吧！</div>
</c:if>
</div>
<iframe id="hiddenIframe" style="display: none;"></iframe>
</body>
</html>
