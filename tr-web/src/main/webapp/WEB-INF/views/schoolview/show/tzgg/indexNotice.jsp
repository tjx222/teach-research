<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<c:choose>
<c:when test="${not empty annunciateList}">
	<c:forEach items="${annunciateList}" var="data">
		<div class="school_notice">
			<div class="school_notice_l">
				<h4>${data.flago}</h4>
			</div>
			<div class="school_notice_r">
				<a style="float:left;" title="${data.title}" href="${ctx}/jy/schoolview/show/notice_announcement?id=${data.id}&orgID=${cm.orgID}&xdid=${cm.xdid}"><span><c:choose><c:when test="${data.orgId==-1}">【系统通知】</c:when><c:otherwise>【学校通知】 </c:otherwise></c:choose>
				<ui:sout value="${data.title}" length="25" needEllipsis="true"></ui:sout></span>
				<c:if test="${data.redTitleId!=0}"><b style="float:right;"><u></u></b></c:if></a>
				<h5><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd" /></h5>
			</div>
		</div>
	</c:forEach>
</c:when>
<c:otherwise>
	<div style="text-align: center;font-size: 12px;padding-top: 10px;">
		暂无通知公告! 
	</div>
</c:otherwise>
</c:choose>
		
</body>
</html>