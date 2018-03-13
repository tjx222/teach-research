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
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${userSpace.id!=_CURRENT_SPACE_.id }">
		<jy:nav id="jyyl_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
		</jy:nav> > 区域教研
		</c:if>
		<c:if test="${userSpace.id==_CURRENT_SPACE_.id }">
		<jy:nav id="jyyl"></jy:nav> > 区域教研
		</c:if>
	</div>
	<div class="jitibeike_content">
		<c:if test="${userSpace.id!=_CURRENT_SPACE_.id }">
		<div class="jitibeike_title">
			<dl class="jitibeike_title_News">
				<dt class="photo"><ui:photo src="${user.photo }" /></dt>
				<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
				<dd><span class="teacher_name">${userSpace.username }</span><span class="teacher_identity">${userSpace.spaceName }</span></dd>
			</dl>
		</div>
		</c:if>
		<div class="jitibeike_con">
			<c:forEach var="activityMap" items="${activityMapList }">
				<div class="jitibeike_con_type">
					<div class="jitibeike_con_type_title">
						<a href="<c:if test="${activityMap['activity'].typeId==1}">${ctx}jy/teachingView/view/view_regoinActivity_jibei?id=${activityMap['activity'].id}</c:if><c:if test="${activityMap['activity'].typeId==2}">${ctx}jy/teachingView/view/view_regoinActivity_zhuYan?id=${activityMap['activity'].id}</c:if>">
							<span <c:if test="${activityMap['activity'].typeId==2 }">style="background:#FD8484"</c:if>>${activityMap['activity'].typeName }</span>
							<p>${activityMap['activity'].activityName }</p>
						</a>
						<span class="toggle_sh" style="margin-top:-17px;">
							<b></b>
							<strong>收起</strong>
						</span>
						<span class="toggle_sh2" style="margin-top:-17px;">
							<b></b>
							<strong>展开</strong>
						</span>
					</div>
					<div class="jitibeike_con_type_content">
					   <div>
							<p class="erji_title_box">
								<span class="erji_title">已整理列表</span>
								<span class="toggle_sh">
									<b></b>
									<strong>收起</strong>
								</span>
								<span class="toggle_sh2">
									<b></b>
									<strong>展开</strong>
								</span>
							</p>
							<div class="article_box">
								<c:forEach var="track" items="${activityMap['trackList'] }">
									<dl>
										 <a href="${pageContext.request.contextPath }/jy/activity/scanLessonPlanTrack?resId=${track.resId }&orgId=${userSpace.orgId}" target="_blank">
											<dt><img src="${ctxStatic }/modules/teachingview/images/w_img1.png"/></dt>
											<dd class="article_name" title="${track.planName }">${track.planName }</dd>
											<dd class="article_date"><fmt:formatDate value="${track.crtDttm}" pattern="yyyy-MM-dd"/></dd>
										 </a>
									</dl>
								</c:forEach>
								<div class="clear"></div>
							</div>
						</div>
						<div>
							<p class="erji_title_box">
								<span class="erji_title">讨论列表</span>
								<span class="toggle_sh">
									<b></b>
									<strong>收起</strong>
								</span>
								<span class="toggle_sh2">
									<b></b>
									<strong>展开</strong>
								</span>
							</p>
							<div class="article_box">
								<ul>
									<c:forEach var="discuss" items="${activityMap['discussList'] }">
										<li>
											<img src="${ctxStatic }/modules/teachingview/images/point.png"/>
											<p>${discuss.content }</p>
											<span><fmt:formatDate value="${discuss.crtDttm}" pattern="yyyy-MM-dd"/></span>
											<div class="clear"></div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			<c:if test="${empty activityMapList}">
				<!-- 无文件 -->
	  				<div class="nofile">
					<div class="nofile1">
						暂时还没有数据，稍后再来查阅吧！
					</div>
				</div>
			</c:if>
		</div>
		<form id="pageForm"  name="pageForm" method="post">
			<ui:page url="${ctx}jy/teachingView/teacher/list_regionActivity" data="${activityList}"  />
			<input id="currentPage" type="hidden" class="currentPage" name="page.currentPage" >
			<input type="hidden" name="termId" value="${searchVo.termId }">
			<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
		</form>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','dataList'],function(){});
</script>
</html>
