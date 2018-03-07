<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看教研进度表"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/teachschedule/css/teachschedule.css"
	media="screen">
<link rel="stylesheet"
	href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
<script type="text/javascript"
	src="${ctxStatic }/modules/thesis/js/js.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(window).scroll(function() {
			$("#kongdiv").toggle();
		});
	});
</script>
</head>
<body onloadstart="showName()">
	<div class="wrapper">
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<h3>${teachSchedule.name}</h3>
				<h4>
					教研圈：<span class="hover_td">${schoolTeachCircleName} <span
						class="school2" style="color: #474747;"> <iframe
								style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
								frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
							<ol>
								<c:forEach items="${stcolist}" var="stco">
									<c:if test="${stco.state==1}">
										<li><a title="${stco.orgName }" class="w180">${stco.orgName }</a><span
											class="z_zc">待接受</span></li>
									</c:if>
									<c:if test="${stco.state==2}">
										<li><a title="${stco.orgName }" class="w180">${stco.orgName }</a><span
											class="z_ty">已接受</span></li>
									</c:if>
									<c:if test="${stco.state==3}">
										<li><a title="${stco.orgName }" class="w180">${stco.orgName }</a><span
											class="z_jj">已拒绝</span></li>
									</c:if>
									<c:if test="${stco.state==4}">
										<li><a title="${stco.orgName }" class="w180">${stco.orgName }</a><span
											class="z_tc">已退出</span></li>
									</c:if>
									<c:if test="${stco.state==5}">
										<li><a title="${stco.orgName }" class="w180">${stco.orgName }</a><span
											class="z_ty">已恢复</span></li>
									</c:if>
								</c:forEach>
							</ol>
					</span>
					</span>
					<jy:di key="${teachSchedule.crtId}"
						className="com.tmser.tr.uc.service.UserService" var="u">
					学校：<span><jy:di key="${u.orgId }"
								className="com.tmser.tr.manage.org.service.OrganizationService"
								var="org">
					  				${org.name }  
					  			  </jy:di> </span>作者：<span> ${u.name } </span>发布时间：<span><fmt:formatDate
								value="${teachSchedule.releaseTime}" pattern="yyyy-MM-dd" /></span>
					</jy:di>
				</h4>
				<a
					href="<ui:download filename="${teachSchedule.name}" resid="${teachSchedule.resId}"></ui:download>"><b
					class="download">下载</b></a>
				<div class="see_word">
					<div style="width: 0px; height: 0px; display: none;" id="kongdiv"></div>
					<iframe id="view" scrolling="no" frameborder="0"
						src="jy/scanResFile?to=true&resId=${teachSchedule.resId}" width="100%"
						height="700px;"></iframe>
				</div>
			</div>
			<div class="clear"></div>

		</div>
	</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
