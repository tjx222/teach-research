<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<c:if test="${searchVo.flago=='0'}">
		<ui:htmlHeader title="查阅教案"></ui:htmlHeader>
	</c:if>
	<c:if test="${searchVo.flago=='1'}">
		<ui:htmlHeader title="查阅课件"></ui:htmlHeader>
	</c:if>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<c:if test="${searchVo.flago=='0'}">
		<ui:tchTop style="1" modelName="查阅教案"></ui:tchTop>
		</c:if>
		<c:if test="${searchVo.flago=='1'}">
		<ui:tchTop style="1" modelName="查阅课件"></ui:tchTop>
		</c:if>
	</div>
		<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<c:if test="${searchVo.flago=='0'}">
				<jy:param name="username" value="${user2.name}"></jy:param>
				<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
				<jy:param name="name" value="查阅教案"></jy:param>
				<jy:param name="urlxqlb" value="jy/teachingView/manager/m_lesson?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&flago=${searchVo.flago}&phaseId=${searchVo.phaseId}"></jy:param>
			</c:if>
			<c:if test="${searchVo.flago=='1'}">
				<jy:param name="username" value="${user2.name}"></jy:param>
				<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
				<jy:param name="name" value="查阅课件"></jy:param>
				<jy:param name="urlxqlb" value="jy/teachingView/manager/m_lesson?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&flago=${searchVo.flago}&phaseId=${searchVo.phaseId}"></jy:param>
			</c:if>
		</jy:nav>
	</div>
	<div class="teachingTesearch_managers_rethink_content">
		<div class="managers_rethink_title">
			<dl class="managers_rethink_title_News">
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
		<div class="managers_rethink_con">
			<div class="managers_rethink_outBox">
				<div class="managers_rethink_outBox_type show">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty lessonInfoData.datalist}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty lessonInfoData.datalist}">
								<c:forEach items="${lessonInfoData.datalist}" var="res">
									<dl>
									    <a href="${ctx}jy/teachingView/view/lesson?type=${searchVo.flago}&infoId=${res.id}&showType=manager" target="_blank">
		 									<dt>
			 									<c:if test="${searchVo.flago=='0'}"><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></c:if>
										    	<c:if test="${searchVo.flago=='1'}"><ui:icon ext="ppt"></ui:icon></c:if>
		 									</dt>
		 									<dd class="article_name" title='<ui:sout value="${res.lessonName }" escapeXml="true"/>'>
											 <ui:sout value="${res.lessonName}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${res.submitTime}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
		 								<c:if test="${not empty chekInfoData[res.id] }">
											<strong class="trans"></strong>
										</c:if>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
						</div>
					</div>
			</div>
		</div>
		<form  name="pageForm" method="post">
			<ui:page url="${ctx}jy/teachingView/manager/m_lesson" data="${lessonInfoData}"  />
			<input type="hidden" class="currentPage" name="page.currentPage">
			<input type="hidden" id="" name="flago" value="${searchVo.flago}"> 
			<input type="hidden" id="" name="userId" value="${searchVo.userId}"> 
			<input type="hidden" id="" name="termId" value="${searchVo.termId}"> 
			<input type="hidden" id="" name="phaseId" value="${searchVo.phaseId}"> 
			<input type="hidden" id="" name="orgId" value="${searchVo.orgId}"> 
		</form>		
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery',"jp/jquery.form.min",'managerList'],function(){});
</script>
</html>
