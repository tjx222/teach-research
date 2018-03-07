<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="反思"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="计划总结"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="计划总结"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_planSummaryWrite?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
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
				<li class="li_active3">撰写（<span>${WriteCount}</span>）</li>
				<li>分享（<span>${shareCount}</span>）</li>
			</ul>
			<div class="managers_rethink_outBox">
				<div class="managers_rethink_outBox_type show">
					<ul class="managers_rethink_con_smallType">
						<li class="li_active4">计划（<span>${fn:length(planWriteData)}</span>）</li>|
						<li>总结（<span>${fn:length(summaryWriteData)}</span>）</li>
					</ul>
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty planWriteData}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty planWriteData}">
								<c:forEach items="${planWriteData }" var="ps">
									<dl>
									    <a href="${ctx}jy/teachingView/view/planSummary/${ps.id}?userId=${searchVo.userId}" target="_blank">
		 									<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
		 									<dd class="article_name" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
		 									<c:if test="${ps.category==1}">[个人计划]</c:if><c:if test="${ps.category==2}">[个人总结]</c:if>
											<c:if test="${ps.category==3}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级计划]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')}">[学校计划]</c:if></c:if>
											<c:if test="${ps.category==4}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级总结]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')||jfn:checkSysRole(ps.roleId, 'fxz')}">[学校总结]</c:if></c:if>
											 <ui:sout value="${ps.title}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
						<div class="managers_rethink_intBox_type">
							<c:if test="${empty summaryWriteData}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty summaryWriteData}">
								<c:forEach items="${summaryWriteData }" var="ps">
									<dl>
									    <a href="${ctx}jy/teachingView/view/planSummary/${ps.id}?userId=${searchVo.userId}" target="_blank">
		 									<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
		 									<dd class="article_name" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
		 									<c:if test="${ps.category==1}">[个人计划]</c:if><c:if test="${ps.category==2}">[个人总结]</c:if>
											<c:if test="${ps.category==3}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级计划]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')}">[学校计划]</c:if></c:if>
											<c:if test="${ps.category==4}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级总结]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')||jfn:checkSysRole(ps.roleId, 'fxz')}">[学校总结]</c:if></c:if>
											 <ui:sout value="${ps.title}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div class="managers_rethink_outBox_type">
					<ul class="managers_rethink_con_smallType">
						<li class="li_active4">计划（<span>${fn:length(planShareData)}</span>）</li>|
						<li>总结（<span>${fn:length(summaryShareData)}</span>）</li>
					</ul>
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:if test="${empty planShareData}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty planShareData}">
								<c:forEach items="${planShareData}" var="ps">
									<dl>
									    <a href="${ctx}jy/teachingView/view/planSummary/${ps.id}?userId=${searchVo.userId}" target="_blank">
		 									<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
		 									<dd class="article_name" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
		 									<c:if test="${ps.category==1}">[个人计划]</c:if><c:if test="${ps.category==2}">[个人总结]</c:if>
											<c:if test="${ps.category==3}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级计划]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')}">[学校计划]</c:if></c:if>
											<c:if test="${ps.category==4}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级总结]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')||jfn:checkSysRole(ps.roleId, 'fxz')}">[学校总结]</c:if></c:if>
											 <ui:sout value="${ps.title}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
									</dl>
								</c:forEach>
							</c:if>
							<div class="clear"></div>
						</div>
						<div class="managers_rethink_intBox_type">
							<c:if test="${empty summaryShareData}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查阅吧！
									</div>
								</div>
							</c:if>
							<c:if test="${not empty summaryShareData}">
								<c:forEach items="${summaryShareData}" var="ps">
									<dl>
									    <a href="${ctx}jy/teachingView/view/planSummary/${ps.id}?userId=${searchVo.userId}" target="_blank">
		 									<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
		 									<dd class="article_name" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
		 									<c:if test="${ps.category==1}">[个人计划]</c:if><c:if test="${ps.category==2}">[个人总结]</c:if>
											<c:if test="${ps.category==3}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级计划]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课计划]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')}">[学校计划]</c:if></c:if>
											<c:if test="${ps.category==4}"><c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">[学科总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">[年级总结]</c:if><c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">[备课总结]</c:if>
											<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr')||jfn:checkSysRole(ps.roleId, 'fxz')}">[学校总结]</c:if></c:if>
											 <ui:sout value="${ps.title}" escapeXml="true" length="12" needEllipsis="true"/>
											 </dd>
		 									<dd class="article_date"><fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/></dd>
		 								</a>
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
