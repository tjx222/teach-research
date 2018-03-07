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
		<div class="sch_cont">
			<div class="top_nav">
				当前位置：
				<c:if test="${schoolShow.type eq 'bignews'}">
					<jy:nav id="xxyw">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav>
				</c:if>
				<c:if test="${schoolShow.type ne 'bignews'}">
					<jy:nav id="xxzyzsindex">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav>
		              &gt;<c:if test="${schoolShow.type eq 'master'}">
						<td>校长风采</td>
					</c:if>
					<c:if test="${schoolShow.type eq 'overview'}">
						<td>学校概况</td>
					</c:if>
				</c:if>
			</div>
			<ol>
				<c:if test="${schoolShow.type eq 'bignews'}">
					<li class="cont_3_right_cont_act">学校要闻</li>
				</c:if>
				<c:if test="${schoolShow.type ne 'bignews'}">
					<c:if test="${schoolShow.type eq 'master'}">
						<li class="cont_3_right_cont_act">校长风采</li>
					</c:if>
					<c:if test="${schoolShow.type eq 'overview'}">
						<li class="cont_3_right_cont_act">学校概况</li>
					</c:if>
				</c:if>
				
			</ol>
			<div class="clear"></div>
			<h3>${schoolShow.title}</h3>
			<h4>
				作者：<span>${schoolShow.author}</span>时间：<span><fmt:formatDate
						value="${schoolShow.crtDttm }" pattern="yyyy-MM-dd HH:mm" /></span>
			</h4>

			<c:if test="${imgUrls ne null}">
				<c:forEach items="${imgUrls}" var="imgUrl">
					<div class="sch_cont_pic">
						<ui:photo src="${imgUrl}" width="100%" height="100%" />
					</div>
				</c:forEach>
			</c:if>
			<div class="show_cont">${schoolShow.introduction }</div>
		</div>
		<div class="clear"></div>
		<c:if test="${schoolShow.type eq 'bignews'}">
			<div class="preparation_resources_2">
				<ul>
				    <c:if test="${not empty previousShow}">
					    <c:forEach items="${previousShow}" var="schoolShow">
						     <li><a href="javascript:void(0)" data-url="jy/schoolview/show/school_survey?showId=${schoolShow.id}" onclick="opearDom(this,'_self',false)">上一篇</a></li>
						</c:forEach>
					</c:if>
					<c:if test="${not empty nextShow}">
						<c:forEach items="${nextShow}" var="schoolShow">
					     	<li style="border-right:0;"><a href="javascript:void(0)" data-url="jy/schoolview/show/school_survey?showId=${schoolShow.id}" onclick="opearDom(this,'_self',false)">下一篇</a></li>
				    	</c:forEach>
				    </c:if>
				</ul>
			</div>
		</c:if>
		<c:if test="${schoolShow.type eq 'master'}">
			<div class="preparation_resources_2">
				<ul>
				    <c:if test="${not empty previousShow}">
					    <c:forEach items="${previousShow}" var="schoolShow">
						     <li><a href="javascript:void(0)" data-url="jy/schoolview/show/school_survey?showId=${schoolShow.id}" onclick="opearDom(this,'_self',false)">上一位</a></li>
						</c:forEach>
					</c:if>
					<c:if test="${not empty nextShow}">
						<c:forEach items="${nextShow}" var="schoolShow">
					     	<li style="border-right:0;"><a href="javascript:void(0)" data-url="jy/schoolview/show/school_survey?showId=${schoolShow.id}" onclick="opearDom(this,'_self',false)">下一位</a></li>
				    	</c:forEach>
				    </c:if>
				</ul>
			</div>
		</c:if>
		<div class="clear"></div>
		<%@include file="../../common/bottom.jsp"%>
	</div>
</body>
</html>