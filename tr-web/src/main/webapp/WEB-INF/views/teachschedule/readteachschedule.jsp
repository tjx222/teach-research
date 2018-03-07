<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="教研进度表"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css"
	media="screen" />
</head>
<body>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="校际教研"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="zyck">
					<jy:param name="jxlwHref"
						value="jy/schoolactivity/index?listType=1"></jy:param>
					<jy:param name="name" value="校际教研"></jy:param>
					<jy:param name="ckname" value="查看教研进度表"></jy:param>
				</jy:nav>
			</h3>
		</div>
		<div class='teaching_table_cont'>
			<div class='teaching_table_c'>
				<h3>
					<span></span><strong>教研进度表</strong>
				</h3>
				<div class="teaching_table_b">
					<c:choose>
						<c:when test="${!empty tlist.datalist }">
							<c:forEach items="${tlist.datalist }" var="data">
								<div class="teaching_dl">
									<dl>
										<a target="_blank" href="jy/teachschedule/view?id=${data.id}&listType=read">
											<dd>
												<ui:icon ext="${data.fileSuffix}"></ui:icon>
											</dd>
											<dt>
												<span title="${data.name}">[<jy:dic
														key="${data.subjectId}"></jy:dic>][<jy:dic
														key="${data.gradeId}"></jy:dic>] <ui:sout
														value="${data.name}" length="18" needEllipsis="true"></ui:sout>
												</span> <span><fmt:formatDate value="${data.lastupDttm}"
														pattern="yyyy-MM-dd" /></span>
											</dt>
										</a>
									</dl>
									<div class="operation">
										<ul>
											<c:if test="${data.crtId==_CURRENT_USER_.id}">
												<ui:isView ext="${data.fileSuffix}">
													<a target="_blank"
														href="jy/teachschedule/view?id=${data.id}&listType=read">
														<li title="查看" class="see"></li>
													</a>
												</ui:isView>
											</c:if>
											<a
												href="<ui:download filename="${data.name}" resid="${data.resId}"></ui:download>"><li
												title="下载" class="download"></li></a>
										</ul>
									</div>
								</div>
							</c:forEach>
							<div class="clear"></div>
							<form name="pageForm" method="post">
								<ui:page url="${ctx}jy/teachschedule/read" data="${tlist}" />
								<input type="hidden" class="currentPage" name="currentPage">
							</form>
						</c:when>
						<c:otherwise>
							<div class="empty_wrap">
								<div class="empty_img"></div>
								<div class="empty_info" style="text-align: center;">当前列表为空，请稍后再来！</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</html>