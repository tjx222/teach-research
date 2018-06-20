<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教师姓名"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="${userSpace.username }"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${empty search.flagz }">
		<jy:nav id="jyyl_jsjy"><jy:param name="phaseId" value="${param.phaseId }"></jy:param></jy:nav> > ${userSpace.username }
		</c:if>
		<c:if test="${search.flagz=='grade' }">
		<jy:nav id="jyyl_jsjy_grade"><jy:param name="phaseId" value="${param.phaseId }"></jy:param>
		<jy:param name="gradeName" value="${gradeName }"></jy:param></jy:nav> > ${userSpace.username }
		</c:if>
		<c:if test="${search.flagz=='subject' }">
		<jy:nav id="jyyl_jsjy_subject"><jy:param name="phaseId" value="${param.phaseId }"></jy:param>
		<jy:param name="subjectName" value="${subjectName }"></jy:param></jy:nav> > ${userSpace.username }
		</c:if>
	</div>
	<div class="teachingTesearch_managers_details_content">
		<div class="managers_details_title">
			<dl class="managers_details_title_News">
				<dt class="photo"><ui:photo src="${user.photo }" /></dt>
				<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
				<dd><span class="teacher_name">${userSpace.username }</span><span class="teacher_identity">${userSpace.spaceName }</span></dd>
			</dl>
		</div>
		<div class="managers_details_con">
			<ul class="managers_details_con_box">
				<li>
					<dl class="managers_details_con_type">
						<dt>
						    <a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_jiaoan?spaceId=${searchVo.spaceId}&phaseId=${param.phaseId}&termId=${searchVo.termId}&flagz=${search.flagz}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img1.png"/></b>
								<span>教案</span>
							</a>
						</dt>
						<dd>
							<span>撰写数：${dataMap['jiaoanWrite']}</span>
							<span>分享数：${dataMap['jiaoanShare']}</span>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_kejian?spaceId=${searchVo.spaceId}&phaseId=${param.phaseId}&termId=${searchVo.termId}&flagz=${search.flagz}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img2.png"/></b>
								<span>课件</span>
							</a>
						</dt>
						<dd>
							<span>撰写数：${dataMap['kejianWrite']}</span>
							<span>分享数：${dataMap['kejianShare']}</span>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_fansi?spaceId=${searchVo.spaceId}&phaseId=${param.phaseId}&termId=${searchVo.termId}&flagz=${search.flagz}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img3.png"/></b>
								<span>反思</span>
							</a>
						</dt>
						<dd>
							<span>撰写数：${dataMap['fansiWrite']}</span>
							<span>分享数：${dataMap['fansiShare']}</span>
						</dd>
					</dl>
				</li>
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_activity?spaceId=${searchVo.spaceId}&phaseId=${param.phaseId}&termId=${searchVo.termId}&flagz=${search.flagz}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img5.png"/></b>
								<span>集体备课</span>
							</a>
						</dt>
						<dd>
							<span>可参与数：${dataMap['activityCanJoin']}</span>
							<span>参与数：${dataMap['activityJoin']}</span>
						</dd>
					</dl>
				</li>
				<li style="border-right:none;">
					<dl class="managers_details_con_type">
						<dt>
							<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_companion?spaceId=${searchVo.spaceId}&phaseId=${param.phaseId}&termId=${searchVo.termId}&flagz=${search.flagz}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img8.png"/></b>
								<span>同伴互助</span>
							</a>
						</dt>
						<dd>
							<span>留言数：${dataMap['companionMessage']}</span>
							<span>资源交流数：${dataMap['companionRes']}</span>
						</dd>
					</dl>
				</li>
				<li style="border-bottom:none;">
					<dl class="managers_details_con_type">
						<dt>
							<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_recordBag?spaceId=${searchVo.spaceId}&phaseId=${param.phaseId}&termId=${searchVo.termId}&flagz=${search.flagz}">
								<b><img src="${ctxStatic }/modules/teachingview/images/img9.png"/></b>
								<span>成长档案袋</span>
							</a>
						</dt>
						<dd>
							<span>精选资源数：${dataMap['teacherRecordRes']}</span>
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
</html>
