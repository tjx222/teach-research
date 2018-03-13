<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="区域教研"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="区域教研"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="区域教研"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_regionActivity?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
		</jy:nav>
	</div>
	<div class="teachingTesearch_jitibeike_content">
		<div class="teachingTesearch_jitibeike_title">
			<dl class="teachingTesearch_jitibeike_title_News">
				<dt class="photo">
					<span><ui:photo src="${user2.photo }"></ui:photo></span>
				</dt>
				<dt class="photo_mask">
					<img src="${ctxStatic }/modules/teachingview/images/state.png"/>
				</dt>
				<dd>
					<span class="teacher_name">${user2.name}</span>
					<span class="teacher_identity">
					<c:forEach items="${searchVo.userSpaceList}" var="space" varStatus="c">
						<c:if test="${fn:length(searchVo.userSpaceList)==c.count}">
							${space.spaceName}
						</c:if>
						<c:if test="${fn:length(searchVo.userSpaceList)!=c.count}">
							${space.spaceName}、
						</c:if>
					</c:forEach>
					</span>
				</dd>
			</dl>
		</div>
		<div class="teachingTesearch_jitibeike_con">
			<div class="teachingTesearch_jitibeike_outBox">
					<div class="teachingTesearch_jitibeike_outBox_type show">
						<table cellpadding="0" cellspacing="0" class="teachingTesearch_jitibeike_table">
							<tr>
								<th style="width:35%;">活动主题</th>
								<th style="width:8%;">参与学校</th>
								<th style="width:10%;">参与学科</th>
								<th style="width:12%;">参与年级</th>
								<th style="width:8%;">发起人</th>
								<th style="width:19%;">活动时限</th>
								<th style="width:8%;">评论数</th>
							</tr>
							<c:if test="${not empty dataMap.datalist}">
								<c:forEach var="activity" items="${dataMap.datalist }">
									  <tr>
									     <td>
									     	<c:if test="${activity.typeId==1}">
												<a href="${ctx}jy/teachingView/view/view_regoinActivity_jibei?id=${activity.id}" target="_blank">
											</c:if>
											<c:if test="${activity.typeId==2}">
												<a href="${ctx}jy/teachingView/view/view_regoinActivity_zhuYan?id=${activity.id}" target="_blank">
											</c:if>
											     <b>【${activity.typeName}】</b>
											     <span title="${activity.activityName}"><ui:sout value="${activity.activityName}" length="26" needEllipsis="true"/></span>
										     </a>
									     </td>
									    <td data-id="${activity.id}" style="z-index:100;" class="regoin_sch">
									    	<span id="td_span">${activity.orgsJoinCount}</span>
									    	 <div class="school">
												<ul id="orgUl_${activity.id}">
												</ul>
											 </div>
									    </td>
									    <td title="${activity.subjectName}">
									    	<ui:sout value="${activity.subjectName}" length="14" needEllipsis="true"></ui:sout>
										</td>
									    <td title="${activity.gradeName}">
									   		<ui:sout value="${activity.gradeName}" length="14" needEllipsis="true"></ui:sout>
										</td>
									    <td title="${activity.organizeUserName}">
									    	<ui:sout value="${activity.organizeUserName}" length="10" needEllipsis="true"></ui:sout>
									    </td>
									    <td title="<fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/>">
									    	<fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>
									    	至
									    	<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.endTime}"> ~ </c:if>
									    </td>
									    <td class="jitibeike_td7">
									    	${empty activity.commentsNum?0:activity.commentsNum}
									    </td>
									  </tr>
						  		</c:forEach>
							</c:if>
						</table>
						<c:if test="${empty dataMap.datalist}">
							<!-- 无文件 -->
			   				<div class="nofile">
								<div class="nofile1">
									暂时还没有数据，稍后再来查阅吧！
								</div>
							</div>
						</c:if>
					</div>
			</div>
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/teachingView/manager/m_regionActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}" data="${dataMap}"  />
					<input type="hidden" class="currentPage" name="page.currentPage">
				</form>
			</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','managerList'],function(){});

</script>
</html>
