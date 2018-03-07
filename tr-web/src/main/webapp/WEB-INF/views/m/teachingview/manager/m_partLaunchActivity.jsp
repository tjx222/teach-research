<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="集体备课"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="集体备课"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="集体备课"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_partLaunchActivity?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
		</jy:nav>
	</div>
	<div id="check_box" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">查阅意见</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content" style="overflow: hidden;"> 
				<iframe id="checkedBox" src="" width="945" height="520" style="overflow: hidden;" frameborder="0" scrolling="no"  ></iframe>
			</div>
		</div>
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
			<ul class="managers_rethink_con_bigType">
				<li data-url="/jy/teachingView/manager/m_partLaunchActivity" data-userId="${searchVo.userId}" data-term="${searchVo.termId}" <c:if test="${searchVo.flago==null || searchVo.flago=='0'}"> class="li_active3" </c:if> value="0">发起（<span>${launchData.page.totalCount}</span>）</li>
				<li data-url="/jy/teachingView/manager/m_partLaunchActivity" data-userId="${searchVo.userId}" data-term="${searchVo.termId}" <c:if test="${searchVo.flago=='1'}"> class="li_active3" </c:if> value="1">参与（<span>${partInData.page.totalCount}</span>）</li>
			</ul>
			<div class="teachingTesearch_jitibeike_outBox">
				<div class="teachingTesearch_jitibeike_outBox_type <c:if test="${searchVo.flago==null || searchVo.flago=='0'}">show</c:if>">
					<c:if test="${not empty launchData.datalist}">
					<table cellpadding="0" cellspacing="0" class="teachingTesearch_jitibeike_table">
						<tr>
							<th style="width:32%;">活动主题</th>
							<th style="width:10%;">学科</th>
							<th style="width:12%;">参与年级</th>
							<th style="width:22%;">活动时限</th>
							<th style="width:8%;">评论数</th>
							<th style="width:8%;">提交状态</th>
							<th style="width:8%;">分享状态</th>
						</tr>
							<c:forEach items="${launchData.datalist}" var="activity">
								<tr>
									<td style="text-align:left;">
										<a href="${ctx}jy/teachingView/view/chayueActivity?activityId=${activity.id}&typeId=${activity.typeId}" target="_blank">
											<b>【${activity.typeName}】</b>
											<span  title="${activity.activityName}"><ui:sout value="${activity.activityName}" length="24" needEllipsis="true"></ui:sout></span>
										</a>
										<c:if test="${activity.isAudit}">
									    	<strong class="s avtivi_check_option" style="cursor: pointer;" data-id="${activity.id}"></strong>
									    </c:if>
									</td>
									<td title="${activity.subjectName}">
										<ui:sout value="${activity.subjectName}" length="10" needEllipsis="true"></ui:sout>
									</td>
									<td title="${activity.gradeName}">
										<ui:sout value="${activity.gradeName}" length="12" needEllipsis="true"></ui:sout>
									</td>
									<td title="<fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/>">
										<fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/>
									</td>
									<td>${activity.commentsNum}</td>
									<td>
										<c:if test="${activity.isSubmit}">已提交</c:if>
										<c:if test="${!activity.isSubmit}">未提交</c:if>
									</td>
									<td class="jitibeike_td7">
										<c:if test="${activity.isShare}">已分享</c:if>
										<c:if test="${!activity.isShare}">未分享</c:if>
									</td>
								</tr>
							</c:forEach>
				</table>
				</c:if>
				<c:if test="${empty launchData.datalist}">
					<!-- 无文件 -->
	   				<div class="nofile">
						<div class="nofile1">
							暂时还没有数据，稍后再来查阅吧！
						</div>
					</div>
				</c:if>
				<form name="pageForm" method="post">
					<ui:page url="${ctx}jy/teachingView/manager/m_partLaunchActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}" data="${launchData}"  />
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" id="" name="flago" value="0"> 
				</form>
				</div>
				<div class="teachingTesearch_jitibeike_outBox_type <c:if test="${searchVo.flago=='1'}">show</c:if>">
					<c:if test="${not empty partInData.datalist}">
				 <table cellpadding="0" cellspacing="0" class="teachingTesearch_jitibeike_table">
					<tr>
						<th style="width:35%;">活动主题</th>
						<th style="width:8%;">学科</th>
						<th style="width:15%;">参与年级</th>
						<th style="width:10%;">发起人</th>
						<th style="width:23%;">活动时限</th>
						<th style="width:9%;">评论数</th>
					</tr>
							<c:forEach items="${partInData.datalist}" var="activity">
								<tr>
									<td class="jitibeike_td1_1">
										<a target="_blank" href="${ctx}jy/teachingView/view/chayueActivity?activityId=${activity.id}&typeId=${activity.typeId}">
											<b>【${activity.typeName}】</b>
											<span title="${activity.activityName}"><ui:sout value="${activity.activityName}" length="50" needEllipsis="true"></ui:sout></span>
										</a>
									</td>
									<td class="jitibeike_td2" title="${activity.subjectName}">
										<ui:sout value="${activity.subjectName}" length="10" needEllipsis="true"></ui:sout>
									</td>
									<td class="jitibeike_td3" title="${activity.gradeName}">
										<ui:sout value="${activity.gradeName}" length="12" needEllipsis="true"></ui:sout>
									</td>
									<td class="jitibeike_td4_4" title="${activity.organizeUserName}">
										<ui:sout value="${activity.organizeUserName}" length="10" needEllipsis="true"></ui:sout>
									</td>
									<td class="jitibeike_td5_5">
										<fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/>
									</td>
									<td class="jitibeike_td6_6">${activity.commentsNum}</td>
								</tr>
							</c:forEach>
				</table>
				</c:if>
				<c:if test="${empty partInData.datalist}">
					<!-- 无文件 -->
	   				<div class="nofile">
						<div class="nofile1">
							暂时还没有数据，稍后再来查阅吧！
						</div>
					</div>
				</c:if>
				<form  name="pageForm1" method="post">
					<ui:page url="${ctx}jy/teachingView/manager/m_partLaunchActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}" data="${partInData}"  />
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" id="" name="flago" value="1"> 
				</form>
				</div>
			</div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
	
</body>
<script type="text/javascript">

require(['jquery','managerList','jp/jquery-ui.min','jp/jquery.blockui.min'],function(){});
</script>
</html>
