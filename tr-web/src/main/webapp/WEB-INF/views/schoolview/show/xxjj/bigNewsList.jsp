<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="学校要闻"></ui:htmlHeader>
</head>

<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include>
	</div>
	<div class="not_cont">
		<div class="top_nav">
			当前位置： <jy:nav id="xxyw">
		             <jy:param name="orgID" value="${cm.orgID}"></jy:param>
					 <jy:param name="xdid" value="${cm.xdid}"></jy:param>
	             </jy:nav>
		</div>
		<div class="top_nav1">
			<ol>
				<li class="cont_3_right_cont_act">学校要闻</li>
			</ol>
		</div>
		<div class="clear"></div>
		<div class="not_cont_li">
			<ul>
			<c:if test="${empty data.datalist }" >
				<div style="text-align: center;padding-top: 10px;">暂无资源!</div>
			</c:if>
			<c:if test="${not empty data.datalist}" >
				<c:forEach var="bigNews" items="${data.datalist }">
					<li data-url="jy/schoolview/show/school_survey?showId=${bigNews.id}" onclick="opearDom(this,'_blank',false)">【学校要闻】 ${bigNews.title} <span>[<fmt:formatDate value="${bigNews.crtDttm }" pattern="yyyy-MM-dd HH:mm"/>]</span></li>
				</c:forEach>
			</c:if>
			</ul>
			<div class="clear"></div>
			<div class="pages">
				<!--设置分页信息 -->
				<form name="pageForm" method="post">
					<ui:page url="jy/schoolview/show/bigNewsList?orgID=${cm.orgID}&xdid=${cm.xdid}" data="${data}" views="10"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
				</form>
			</div>
		</div>
	</div>	
	<div class="clear"></div>
	<%@include file="../../common/bottom.jsp" %>
</div>
</body>
</html>