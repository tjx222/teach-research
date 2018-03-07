<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:htmlHeader title="主备教案修改"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_activity.css"
	media="all">
</head>
<body>
	<input type="hidden" id="openModeType" value="${openModeType }" />
	<input type="hidden" id="editType" name="editType" value="${editType }" />
	<input type="hidden" id="planId" name="planId" value="${planId }" />
	<input type="hidden" id="activityId" name="activityId"
		value="${activityId }" />
	<input type="hidden" id="isSend" value="${isSend}" />
	<input type="hidden" id="trackId" name="trackId" value="${trackId }" />
	<input type="hidden" id="resId" value="${resId }" />
	<div class="word_tab_big_tab" style="width:1000px; height: 495px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1">
		</po:PageOfficeCtrl>
	</div>
</body>
<script type="text/javascript"
	src="${ctxStatic }/modules/schoolactivity/js/activity_lessonPlan.js"></script>
</html>
