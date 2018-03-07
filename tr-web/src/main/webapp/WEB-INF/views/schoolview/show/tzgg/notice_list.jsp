<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="学校资源展示页"></ui:htmlHeader>
</head>

<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include> 
	</div>
	<div class="not_cont">
		<div class="top_nav">
		             当前位置：   <jy:nav id="xizy_tzgg">
		             <jy:param name="orgID" value="${cm.orgID}"></jy:param>
					 <jy:param name="xdid" value="${cm.xdid}"></jy:param>
		             </jy:nav>
		</div>
		<div class="top_nav1">
			<ol>
				<li class="cont_3_right_cont1_act">通知公告</li>
			</ol>
		</div>
		<div class="clear"></div>
		<div class="not_cont_li">
			<ul>
			    <c:forEach items="${pagelist.datalist}" var="data">
			        <li>
			        	<a style="float: left;" title="${data.title}" href="${ctx}/jy/schoolview/show/notice_announcement?id=${data.id}&orgID=${cm.orgID}&xdid=${cm.xdid}"><c:choose><c:when test="${data.orgId==-1}">【系统通知】</c:when><c:otherwise>【学校通知】 </c:otherwise></c:choose><ui:sout value="${data.title}" length="50" needEllipsis="true" ></ui:sout><c:if test="${data.redTitleId!=0}"><b style="float: right;"><u></u></b></c:if></a>
			        	<span>[<fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd" />]</span>
			        </li>
			    </c:forEach>
			</ul>
			<div class="clear"></div>
				<div class="pages">
					<!--设置分页信息 -->
					<form name="pageForm" method="post">
						<ui:page url="${ctx}/jy/schoolview/show/notice" data="${pagelist}" />
						<input type="hidden" class="currentPage" name="currentPage">
						<input type="hidden" name="orgID" name="${cm.orgID }">
						<input type="hidden" name="xdid" name="${cm.xdid }">
					</form>
				</div>
		</div>
	</div>
	<%@include file="../../common/bottom.jsp" %> 
</div>
</body>
</html>
