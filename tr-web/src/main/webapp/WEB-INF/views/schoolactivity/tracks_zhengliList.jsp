<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="集备意见教案列表"></ui:htmlHeader>
<head>
<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/edit.css" media="all">
<script type="text/javascript">
	//新选项卡查看修改教案
	function scanLessonPlanTrack(resId, orgId) {
		window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/scanLessonPlanTrack?resId=" + resId + "&orgId=" + orgId, "hidenframe");
	}
	function wantToEdit() {
		$("#edit_lessonplan", window.parent.document).trigger("click");
	}
</script>
</head>
<body style="background:#fff;"> 
	<c:if test="${zhengliList==null || fn:length(zhengliList)==0 }">
		<script type="text/javascript">
			$("#sendToJoiners", window.parent.document).hide();
		</script>
		<div class="Revision_record">
			主备人暂无修改教案的信息哟！
		</div>
	</c:if>
	<c:if test="${zhengliList!=null && fn:length(zhengliList)>0 }">
		<div class="word_tab_1_big_tab" style="height: 124px;">
			<div style="overflow: auto; height: 124px; width: 850px;">
				<c:forEach var="zhengli" items="${zhengliList }">
					<dl>
						<dd style="cursor: pointer;" onclick="scanResFile('${zhengli.resId }')">
							<jy:ds key="${zhengli.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
							<ui:icon ext="${res.ext }" title="${zhengli.planName }"></ui:icon>
						</dd>
						<dt title="${zhengli.planName }">
							<span><ui:sout value="${zhengli.planName }" length="28"
									needEllipsis="true"></ui:sout></span>
						</dt>
					</dl>
				</c:forEach>
			</div>
		</div>
	</c:if>
</body>
</html>
