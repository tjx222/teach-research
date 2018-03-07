<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="查阅反思"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="查阅反思"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="查阅反思"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_lesson_fansi?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
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
		    <ul class="managers_rethink_con_bigType">
				<li value="1" <c:if test="${searchVo.flags==null || searchVo.flags=='0'}"> class="li_active3" </c:if>>课后反思（<span>${empty fansiData.page.totalCount?0:fansiData.page.totalCount}</span>）</li>
				<li value="2" <c:if test="${searchVo.flags=='1'}"> class="li_active3" </c:if>>其他反思（<span>${qiTaFanSiData.page.totalCount}</span>）</li>
			</ul>
			<div class="managers_rethink_outBox">
				<div class="managers_rethink_outBox_type <c:if test="${searchVo.flags==null || searchVo.flags=='0'}"> show </c:if>">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty fansiData.datalist}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty fansiData.datalist}">
								<c:forEach items="${fansiData.datalist}" var="res">
									<dl>
									    <a href="${ctx}jy/teachingView/view/lesson?type=2&infoId=${res.id}&showType=manager"  target="_blank">
		 									<dt><ui:icon ext="doc"></ui:icon></dt>
		 									<dd class="article_name" title='<ui:sout value="${res.lessonName }" escapeXml="true"/>'>
											 <ui:sout value="${res.lessonName}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${res.submitTime}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
		 								<c:if test="${not empty fansiChekInfoData[res.id] }">
											<strong class="trans"></strong>
										</c:if>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
						</div>
						<form  name="pageForm" method="post">
							<ui:page url="${ctx}jy/teachingView/manager/m_lesson_fansi" data="${fansiData}"  />
							<input type="hidden" class="currentPage" name="page.currentPage">
							<input type="hidden" id="" name="flags" value="0"> 
							<input type="hidden" id="" name="userId" value="${searchVo.userId}"> 
							<input type="hidden" id="" name="termId" value="${searchVo.termId}"> 
							<input type="hidden" id="" name="phaseId" value="${searchVo.phaseId}"> 
							<input type="hidden" id="" name="orgId" value="${searchVo.orgId}"> 
						</form>
					</div>
				<div class="managers_rethink_outBox_type <c:if test="${searchVo.flags=='1'}">show</c:if>">
					<div class="managers_rethink_intBox ">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty qiTaFanSiData.datalist}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty qiTaFanSiData.datalist}">
								<c:forEach items="${qiTaFanSiData.datalist}" var="res">
									<dl>
									    <a href="${ctx}jy/teachingView/view/other/lesson?type=3&planId=${res.planId}&showType=manager"  target="_blank">
		 									<dt><ui:icon ext="doc"></ui:icon></dt>
		 									<dd class="article_name" title='<ui:sout value="${res.planName }" escapeXml="true"/>'>
											 <ui:sout value="${res.planName}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${res.submitTime}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
		 								
		 								<c:if test="${not empty qiTaFanSiChekInfoData[res.planId] }">
											<strong class="trans"></strong>
										</c:if>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
					</div>
					<form  name="pageForm1" method="post">
						<ui:page url="${ctx}jy/teachingView/manager/m_lesson_fansi" data="${qiTaFanSiData}"  />
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden" id="" name="flags" value="1"> 
						<input type="hidden" id="" name="userId" value="${searchVo.userId}"> 
						<input type="hidden" id="" name="termId" value="${searchVo.termId}"> 
						<input type="hidden" id="" name="phaseId" value="${searchVo.phaseId}"> 
						<input type="hidden" id="" name="orgId" value="${searchVo.orgId}"> 
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery',"jp/jquery.form.min",'managerList'],function(){});
</script>
</html>
