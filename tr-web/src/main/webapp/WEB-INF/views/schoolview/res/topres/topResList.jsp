<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="热点排行"></ui:htmlHeader>
	<style type="text/css">
		.count_span{
			float:right;
		}
		.date_span{
			padding-left:30px;
			float:right;
		}
		li{
			cursor:pointer;
		}
	</style>
</head>
<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include>
	</div>
	<div class="not_cont">
		<div class="top_nav">
			当前位置：<jy:nav id="xxzyzsindex">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav> &gt; 热点排行
		</div>
		<div class="top_nav1">
			<ol>
				<li class="cont_3_right_cont_act">热点排行</li>
			</ol>
		</div>
		<div class="clear"></div>
		<div class="not_cont_li">
			<ul>
			<c:if test="${empty data.datalist }" >
				<div style="text-align: center;padding-top: 10px;">暂无资源!</div>
			</c:if>
			<c:if test="${not empty data.datalist}" >
				<c:forEach var="topRes" items="${data.datalist }">
					<li data-url="jy/schoolview/res/lessonres/view?lesid=${topRes.resObj.planId}" onclick="opearDom(this,'_self')">
						<a>【热点排行】 ${topRes.resObj.planName}</a> 
						<span class="date_span">[<fmt:formatDate value="${topRes.resObj.crtDttm }" pattern="yyyy-MM-dd HH:mm"/>]</span>
						<span class="count_span">[浏览次数${topRes.bc.count}]</span>
					</li>
				</c:forEach>
			</c:if>
			</ul>
			<div class="clear"></div>
			<div class="pages">
				<!--设置分页信息 -->
				<form name="pageForm" method="post">
					<ui:page url="jy/schoolview/res/topres/getTopResDetailed?orgID=${cm.orgID}&xdid=${cm.xdid}" data="${data}" views="10"/>
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