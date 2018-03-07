<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${empty vipschools}">
	<div class="check-bottom_1_right_top" align="center"
		style="padding-top: 10px;">暂无同伴校!</div>
</c:if>
<c:if test="${not empty vipschools}">
	<c:forEach items="${vipschools}" var="p" begin="0" end="4" step="1">
		<a href="${ctx}jy/schoolview/index?orgID=${p.id}" target="_self"  style="cursor: pointer;">
		 
		 <jy:ds var="picresource" key="${p.logo}"
				className="com.tmser.tr.manage.resources.service.ResourcesService"></jy:ds>
			<ui:photo src="${picresource.path}" height="116" width="122" defaultSrc="${ctxStatic}/modules/schoolview/images/school/schDefalutPic.png"></ui:photo>
			<br/><span>${p.name}</span>
			</a>
	</c:forEach>
</c:if>