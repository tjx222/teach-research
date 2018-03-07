<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="历年资源-教研进度表"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/teachschedule/css/teachschedule.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css" media="screen">
</head>
<body>
<div class="Teaching_schedule" style="margin-left: 7px;width: 800px;height: 830px;">
	<h3>教研进度表</h3>
	<c:choose>
		<c:when test="${!empty tlist.datalist }">
			<div class="Teaching_schedule1" style="height: 600px;margin-bottom: 20px;">
			<c:forEach items="${tlist.datalist }" var="data">
				<div class="Reflect_cont_right_1_dl" style="margin-left: 25px;">
					<dl>
					   <a target="_blank" href="jy/teachschedule/view?id=${data.id}">
						<dd>
								<ui:icon ext="${data.fileSuffix}"></ui:icon>
						</dd>
						<dt>
							<span title="${data.name}">[<jy:dic key="${data.subjectId}"></jy:dic>][<jy:dic
									key="${data.gradeId}"></jy:dic>]
									<ui:sout value="${data.name}" length="18" needEllipsis="true"></ui:sout>
							</span> <span><fmt:formatDate value="${data.lastupDttm}"
									pattern="yyyy-MM-dd" /></span>
						</dt>
					   </a>
					</dl>
					<div class="show_p">
						<ul>
						<c:if test="${data.crtId==_CURRENT_USER_.id}">
						  <ui:isView ext="${data.fileSuffix}">
							<a target="_blank" href="jy/teachschedule/view?id=${data.id}">
								<li title="查看" class="menu_li_5"></li></a>
						 </ui:isView>
						 </c:if>
							<a	href="<ui:download filename="${data.name}" resid="${data.resId}"></ui:download>"><li
								title="下载" class="menu_li_6"></li></a>
						</ul>
					</div>
				</div>
			</c:forEach>
			</div>
			<div class="clear"></div>
			<form name="pageForm" method="post"  >
				<ui:page url="${ctx}jy/history/${sv.schoolYear }/xjjy/teachschedule" data="${tlist}" />
				<input type="hidden" class="currentPage" name="page.currentPage">
				<input type="hidden" name="spaceId" value="${sv.spaceId }">
			</form>
		</c:when>
		<c:otherwise>
			<div class="cont_empty">
			    <div class="cont_empty_img"></div>
			    <div class="cont_empty_words">没有教研进度表哟！</div> 
			</div>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>
