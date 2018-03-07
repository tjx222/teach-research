<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html>
<ui:htmlHeader title="集备意见教案列表"></ui:htmlHeader>
<head>
<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css" />
</head>
<body style="background:#fff;">
<div class='participant_edit_right1_cont clearfix'>
	<div class="edit_zb_lessonplan_cont_cyr">
		<span>参与人</span>
	</div> 
	<div class="participant_edit_right_cont">
		<c:forEach var="yijian" items="${yijianList }">
			<dl>
				<jy:ds key="${yijian.userId }" className="com.tmser.tr.uc.service.UserService" var="user" />
				<dd style="cursor: pointer;" onclick="scanResFile('${yijian.resId }')" >
					<jy:ds key="${yijian.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
					<ui:icon ext="${res.ext }" title="${yijian.planName }"></ui:icon>
					<span class="title_span" title="${yijian.planName }"></span>
				</dd>
				<c:if test="${_CURRENT_SPACE_.orgId==user.orgId }">
					<dt>${yijian.userName }</dt>
				</c:if>
				<c:if test="${_CURRENT_SPACE_.orgId!=user.orgId }">
					<dt>${user.nickname }</dt>
				</c:if>
			</dl>
		</c:forEach>
		<c:if test="${empty yijianList }">
		
			<div class="Revision_record">还没有人提出修改建议哟！</div>
		</c:if>
	</div>
</div>
</body>
</html>
