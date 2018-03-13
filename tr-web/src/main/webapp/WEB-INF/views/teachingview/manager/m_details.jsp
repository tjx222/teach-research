<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教学管理情况一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教学管理情况一览"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_glzxm">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
		</jy:nav>
	</div>
	<div class="teachingTesearch_managers_details_content">
		<div class="managers_details_title">
			<dl class="managers_details_title_News">
				<dt class="photo">
					<span><ui:photo src="${user2.photo }"></ui:photo></span>
				</dt>
				<dt class="photo_mask">
					<img src="${ctxStatic }/modules/teachingview/images/state.png"/>
				<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
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
		<div class="managers_details_con">
			<ul class="managers_details_con_box">
				<li>
					<dl class="managers_details_con_type">
						<dt>
						    <a href="${ctx}jy/teachingView/manager/m_lesson?userId=${searchVo.userId}&termId=${searchVo.termId}&flago=0&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img1.png"/></b>
								<span>查阅教案</span>
							</a>
						</dt>
						<dd>
						    <a href="${ctx}jy/teachingView/manager/m_lesson?userId=${searchVo.userId}&termId=${searchVo.termId}&flago=0&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.jiaoan_allow_read}</span>
								<span>查阅数：${dataMap.jiaoan_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_lesson?userId=${searchVo.userId}&termId=${searchVo.termId}&flago=1&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img2.png"/></b>
								<span>查阅课件</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_lesson?userId=${searchVo.userId}&termId=${searchVo.termId}&flago=1&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.kejian_allow_read}</span>
								<span>查阅数：${dataMap.kejian_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_lesson_fansi?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img3.png"/></b>
								<span>查阅反思</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_lesson_fansi?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.fansi_allow_read}</span>
								<span>查阅数：${dataMap.fansi_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li style="border-right:none">
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_check_jitibeike?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img5.png"/></b>
								<span>查阅集备</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_check_jitibeike?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.activity_allow_read}</span>
								<span>查阅数：${dataMap.activity_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_partLaunchActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img5.png"/></b>
								<span>发起/参与集备</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_partLaunchActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<c:if test="${dataMap.haveOrgActivity}">
									<span>发起数：${dataMap.activity_origination}</span>
									<span>分享数：${dataMap.activity_share}</span>
								</c:if>
								<span>可参与数：${dataMap.activity_allow_part_in}</span>
								<span>参与数：${dataMap.activity_part_in}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_checkPlanSummary?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img61.png"/></b>
								<span>查阅计划总结</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_checkPlanSummary?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.plan_summary_allow_read}</span>
								<span>查阅数：${dataMap.plan_summary_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_planSummaryWrite?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img6.png"/></b>
								<span>撰写计划总结</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_planSummaryWrite?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>撰写数：${dataMap.personplan_write}</span>
								<span>分享数：${dataMap.personplan_share}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li style="border-right:none">
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_lecture_record?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img4.png"/></b>
								<span>听课记录</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_lecture_record?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>听课节数：${dataMap.lecture_hours}</span>
								<span>分享数：${dataMap.lecture_share}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_check_lecture?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img41.png"/></b>
								<span>查阅听课记录</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_check_lecture?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.lecture_allow_read}</span>
								<span>查阅数：${dataMap.lecture_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_thesis?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img7.png"/></b>
								<span>教学文章</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_thesis?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>撰写数：${dataMap.thesis_write}</span>
								<span>分享数：${dataMap.thesis_share}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_checkThesis?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img71.png"/></b>
								<span>查阅教学文章</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_checkThesis?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>可查阅数：${dataMap.thesis_allow_read}</span>
								<span>查阅数：${dataMap.thesis_read}</span>
							</a>
						</dd>
					</dl>
				</li>
				<li style="border-right:none">
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_companion?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img8.png"/></b>
								<span>同伴互助</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_companion?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<span>留言数：${dataMap.companion_message}</span>
								<span>资源交流数：${dataMap.companion_discuss}</span>
							</a>
						</dd>
					</dl>
				</li>
<!-- 				<li> -->
<!-- 					<dl class="managers_details_con_type"> -->
<!-- 						<dt> -->
<%-- 							<a href="${ctx}jy/teachingView/manager/m_regionActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}"> --%>
<%-- 								<b><img src="${ctxStatic }/modules/teachingview/images/img10.png"/></b> --%>
<!-- 								<span>区域教研</span> -->
<!-- 							</a> -->
<!-- 						</dt> -->
<!-- 						<dd> -->
<%-- 							<a href="${ctx}jy/teachingView/manager/m_regionActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}"> --%>
<%-- 								<span>可参与数：${dataMap.region_allow_part_in}</span> --%>
<%-- 								<span>参与数：${dataMap.region_part_in}</span> --%>
<!-- 							</a> -->
<!-- 						</dd> -->
<!-- 					</dl> -->
<!-- 				</li> -->
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${ctx}jy/teachingView/manager/m_partLaunchSchoolActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img11.png"/></b>
								<span>发起/参与校际教研</span>
							</a>
						</dt>
						<dd>
							<a href="${ctx}jy/teachingView/manager/m_partLaunchSchoolActivity?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}">
								<c:if test="${dataMap.haveOrgSchActivity}">
									<span>发起数：${dataMap.school_activity_launch}</span>
									<span>分享数：${dataMap.school_activity_share}</span>
								</c:if>
								<span>可参与数：${dataMap.school_activity_allow_part_in}</span>
								<span>参与数：${dataMap.school_activity_part_in}</span>
							</a>
							
						</dd>
					</dl>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','managerList'],function(){});


</script>
</html>