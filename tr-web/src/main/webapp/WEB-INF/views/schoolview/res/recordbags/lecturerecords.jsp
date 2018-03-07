<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看单个听课"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css">
</head>

<body>
<div class="box1"></div>
	<div class="wraper">
		<div class="top">
			<jsp:include page="../../common/top.jsp"></jsp:include>
		</div>
		<div class="gro_cont">
		<div class="top_nav">
		<h3>当前位置：
		<jy:nav id="dadzych">
					    <jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
						<jy:param name="subjectId" value="${cm.subjectId}"></jy:param>
					    <jy:param name="gradeId" value="${cm.gradeId}"></jy:param>
					    <jy:param name="teacherId" value="${cm.teacherId}"></jy:param>
						<jy:di key="${lr.lecturepeopleId }" className="com.tmser.tr.uc.service.UserService" var="u">
							<jy:param name="userId" value="${u.id}"></jy:param>
							<jy:param name="bagmaster" value="${u.name}"></jy:param>
						</jy:di>
						<jy:param name="id" value="${recordbagID}"></jy:param>
						<jy:param name="zyname" value="${recordbagName}"></jy:param>
				</jy:nav>
		</h3>
	</div>
	<div class="clear"></div>
	
	<div class="record_sheet" style="height:auto;">
	<h3>听课记录表</h3>
	<!-- 校外听课查看 -->
	  <c:if test="${lr.type=='1'}">
	  <div class="record_sheet_cont" style="height:auto;">
			<div class="r_s_c">
				<h1 style="text-align:left;"><a href="">*</a>课题</h1>
				<strong>${lr.topic}</strong>
				<b>听课地点</b>
				<strong>${lr.lectureAddress}</strong>
			</div>
			<div class="r_s_c">
				<h1 style="border-top-left-radius:0;">授课教师</h1>
				<strong>${lr.teachingPeople}</strong>
				<b>单位</b>
				<strong>${lr.lectureCompany}</strong>
				<b>年级学科</b>
				<strong>${lr.gradeSubject}</strong>
			</div>
			<div class="r_s_c">
				<h1 style="border-top-left-radius:0;">听课人</h1>
				<strong>${lr.lecturePeople}</strong>
				<b>听课时间</b>
				<strong><fmt:setLocale value="zh"/><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/></strong>
				<b>听课节数</b>
				<strong>${lr.numberLectures}</strong>
			</div>
			<div  class="r_s_c" style="width: 930px;height: 35px;float: left;">
				<b style="border-left:none;border-right:none;width: 930px;height: 35px;line-height: 35px;">听课意见</b>
			</div>
			<div class="clear"></div>
			<div style="height: auto;min-height:300px;margin: 20px;line-height:20px;border: 1px #ddd solid;border-top:0;">${lr.lectureContent}</div>
		</div>
		</c:if>
		
		<!-- 校内听课查看 -->
		<c:if test="${lr.type=='0'}">
		<div class="record_sheet_cont" style="height:auto;">
				<div class="r_s_c">
					<h1>课题</h1>
					<strong>${lr.topic}</strong>
					<b>评价等级</b>
					<strong>${lr.evaluationRank}</strong>
				</div>
				<div class="r_s_c">
					<h1 style="border-top-left-radius:0;">授课教师</h1>
					<strong>${lr.teachingPeople}</strong>
					<b>学科</b>
					<strong>${lr.subject}</strong>
					<b>年级</b>
					<strong>${lr.grade}</strong>
				</div>
				<div class="r_s_c">
					<h1 style="border-top-left-radius:0;">听课人</h1>
					<strong>${lr.lecturePeople}</strong>
					<b>听课时间</b>
					<strong><fmt:setLocale value="zh"/><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/></strong>
					<b>听课节数</b>
					<strong>${lr.numberLectures}</strong>
				</div>
				<div  class="r_s_c" style="width: 930px;height: 35px;float: left;">
					<b style="width: 930px;height: 35px;line-height: 35px;border-left:none;border-right:none;">听课意见</b>
				</div>
				<div class="clear"></div>
				<div class="l_cont">${lr.lectureContent}</div>
				</div>
		</c:if>
		<iframe id="commentBox" onload="setCwinHeight(this,false,600)" src="jy/comment/list?authorId=${_CURRENT_USER_.id}&resType=${lr.resType}&resId=${lr.id}&title=<ui:sout value='${lr.topic}' encodingURL='true' escapeXml='true'></ui:sout>" width="100%" height="600px;" style="border:none;" scrolling="no"></iframe>
		</div>
		<div class="clear"></div>
	<!-- 单个听课意见的回复 -->
	<div id="huifu"></div>
	<div class="clear"></div>
	</div>
</div>
	<%@include file="../../common/bottom.jsp" %>
</body>
</html>