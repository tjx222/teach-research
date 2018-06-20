<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="课件"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body>
<input type="hidden" id="bigType" value="${searchVo.flags }">
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="课件"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${not isTeacher }">
		<c:if test="${empty searchVo.flagz }">
		<jy:nav id="jyyl_js">
			<jy:param name="phaseId" value="${param.phaseId }"></jy:param>
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }&phaseId=${param.phaseId }"></jy:param>
		</jy:nav>
		</c:if>
		<c:if test="${searchVo.flagz=='grade' }">
		<jy:nav id="jyyl_grade_js">
			<jy:param name="phaseId" value="${param.phaseId }"></jy:param>
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }&phaseId=${param.phaseId }"></jy:param>
			<jy:param name="gradeName" value="${gradeName }"></jy:param>
		</jy:nav>
		</c:if>
		<c:if test="${searchVo.flagz=='subject' }">
		<jy:nav id="jyyl_subject_js">
			<jy:param name="phaseId" value="${param.phaseId }"></jy:param>
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }&phaseId=${param.phaseId }"></jy:param>
			<jy:param name="subjectName" value="${subjectName }"></jy:param>
		</jy:nav>
		</c:if> > 课件
		</c:if>
		<c:if test="${isTeacher }">
		<jy:nav id="jyyl">
		<jy:param name="spaceId" value="${param.spaceId }"></jy:param>
		</jy:nav> > 课件
		</c:if>
	</div>
	<div class="teachingTesearch_managers_rethink_content">
		<div class="managers_rethink_title">
			<dl class="managers_rethink_title_News">
				<dt class="photo"><ui:photo src="${user.photo }" /></dt>
				<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
				<dd><span class="teacher_name">${userSpace.username }</span><span class="teacher_identity">${userSpace.spaceName }</span></dd>
			</dl>
		</div>
		<div class="managers_rethink_con">
		    <ul class="managers_rethink_con_bigType">
				<li class="li_active3">撰写（<span>${fn:length(lessonList)}</span>）</li>
				<li>分享（<span>${planCount}</span>）</li>
			</ul>
			<div class="managers_rethink_outBox">
				<div class="managers_rethink_outBox_type show">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:forEach var="lesson" items="${lessonList }">
								<dl>
								    <a href="${ctx}jy/teachingView/view/lesson?type=1&infoId=${lesson.id}" target="_blank">
										<dt><ui:icon ext="ppt"></ui:icon></dt>
										<dd class="article_name" title="${lesson.lessonName }">${lesson.lessonName }</dd>
										<dd class="article_date"><fmt:formatDate value="${lesson.crtDttm}" pattern="yyyy-MM-dd"/></dd>
									</a>
								</dl>
							</c:forEach>
							<c:if test="${empty lessonList}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查看吧！
									</div>
								</div>
							</c:if>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div class="managers_rethink_outBox_type">
					<div class="managers_rethink_intBox">
						<div class="managers_rethink_intBox_type show">
							<c:forEach var="plan" items="${planList.datalist }">
								<dl>
								<jy:ds key="${plan.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
								    <a href="${ctx}jy/scanResFile?resId=${plan.resId}" target="_blank">
										<dt><ui:icon ext="${res.ext}"></ui:icon></dt>
										<dd class="article_name" title="${plan.planName }">${plan.planName }</dd>
										<dd class="article_date"><fmt:formatDate value="${plan.crtDttm}" pattern="yyyy-MM-dd"/></dd>
									</a>
								</dl>
							</c:forEach>
							<c:if test="${empty planList.datalist}">
								<!-- 无文件 -->
				   				<div class="nofile">
									<div class="nofile1">
										暂时还没有数据，稍后再来查看吧！
									</div>
								</div>
							</c:if>
							<div class="clear"></div>
						</div>
					</div>
					<form  name="pageForm" method="post">
						<ui:page url="${ctx}jy/teachingView/teacher/list_kejian" data="${planList}"  />
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden" name="flags" value="1">
						<input type="hidden" name="termId" value="${searchVo.termId }">
						<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
						<input type="hidden" name="flagz" value="${searchVo.flagz }">
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','dataList'],function(){});
</script>
</html>
