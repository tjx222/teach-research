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
			<jy:param name="name" value="查阅教学文章"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_checkThesis?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
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
				<div class="managers_rethink_outBox_type <c:if test="${searchVo.flago==null || searchVo.flago=='1'}">show</c:if>">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty thesisData }">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty thesisData }">
								<c:forEach items="${thesisData}" var="thesis_">
									<dl>
									    <a href="${ctx}jy/teachingView/view/thesisview?id=${thesis_.id}&showType=manager" target="_blank">
		 									<dt>
			 									<img src="${ctxStatic }/common/icon/base/word.png"/>
		 									</dt>
		 									<dd class="article_name" title='[${thesis_.thesisType}] ${thesis_.thesisTitle}'>[${thesis_.thesisType}]
											 <ui:sout value="${thesis_.thesisTitle}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${thesis_.submitTime}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
		 								<c:if test="${not empty chekInfoThesisData[thesis_.id]}">
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
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery',"jp/jquery.form.min",'managerList'],function(){});
</script>
</html>
