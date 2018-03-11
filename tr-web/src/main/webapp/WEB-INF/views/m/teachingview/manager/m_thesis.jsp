<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教学文章"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教学文章"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="教学文章"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_thesis?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
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
				<li <c:if test="${searchVo.flago==null || searchVo.flago=='0'}"> class="li_active3" </c:if> value="0">撰写（<span>${thesisWriteData.page.totalCount}</span>）</li>
				<li <c:if test="${searchVo.flago=='1'}"> class="li_active3" </c:if> value="1">分享（<span>${shareThesisData.page.totalCount}</span>）</li>
			</ul>
			<div class="managers_rethink_outBox">
				<div class="managers_rethink_outBox_type <c:if test="${searchVo.flago==null || searchVo.flago=='0'}">show</c:if>">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty thesisWriteData.datalist}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty thesisWriteData.datalist}">
								<c:forEach items="${thesisWriteData.datalist}" var="data">
									<dl>
										<a href="${ctx}jy/teachingView/view/thesisview?id=${data.id}" target="_blank">
											<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
											<dd class="article_name" title='[${data.thesisType}] ${data.thesisTitle}'>
												[${data.thesisType}]
												<ui:sout value="${data.thesisTitle}" length="18"
													needEllipsis="true"></ui:sout>
											</dd>
											<dd class="article_date"><fmt:formatDate value="${data.lastupDttm}"
												pattern="yyyy-MM-dd" /></dd>
										</a>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
						</div>
						<form  name="pageForm" method="post">
							<ui:page url="${ctx}jy/teachingView/manager/m_thesis?userId=${searchVo.userId}&termId=${searchVo.termId}" data="${thesisWriteData}"  />
							<input type="hidden" class="currentPage" name="page.currentPage">
							<input type="hidden" id="" name="flago" value="0"> 
						</form>
					</div>
				<div class="managers_rethink_outBox_type <c:if test="${searchVo.flago=='1'}">show</c:if>">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty shareThesisData.datalist}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty shareThesisData.datalist}">
								<c:forEach items="${shareThesisData.datalist }" var="data">
									<dl>
									    <a href="${ctx}jy/teachingView/view/thesisview?id=${data.id}" target="_blank">
											<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
											<dd class="article_name" title='[${data.thesisType}] ${data.thesisTitle}'>
												[${data.thesisType}]
												<ui:sout value="${data.thesisTitle}" length="18"
													needEllipsis="true"></ui:sout>
											</dd>
											<dd class="article_date"><fmt:formatDate value="${data.lastupDttm}"
												pattern="yyyy-MM-dd" /></dd>
										</a>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
					</div>
					<form  name="pageForm1" method="post">
						<ui:page url="${ctx}jy/teachingView/manager/m_thesis?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}" data="${shareThesisData}"  />
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
	require(['jquery',"jp/jquery.form.min",'managerList'],function(){});
</script>
</html>
