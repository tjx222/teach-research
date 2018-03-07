<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<c:if test="${not empty slideNoticeDate}">
	<div class="notice">
		<span></span> <strong>通知公告</strong>
		<marquee style="width: 90%; height: 100%;" behavior="scroll" onmouseout="this.start()" onmouseover="this.stop()" scrollamount="4" direction="left">
			<div id="notice_index_load" style="width: 1100px;">
				<c:forEach items="${slideNoticeDate}" var="data">
					<p>
						<a title="${data.title }"
							href="${ctx}/jy/schoolview/show/notice_announcement?id=${data.id}&orgID=${cm.orgID}&xdid=${cm.xdid}">
							<c:choose>
								<c:when test="${data.orgId==-1}">【系统通知】</c:when>
								<c:otherwise>【学校通知】</c:otherwise>
							</c:choose> <ui:sout value="${data.title}" length="50" needEllipsis="true"></ui:sout>
							<c:if test="${data.redTitleId!=0}">
								<b style="float: left;"><u></u></b>
							</c:if>
						</a>
					</p>
				</c:forEach>
			</div>
		</marquee>
	</div>
</c:if>
</body>
</html>