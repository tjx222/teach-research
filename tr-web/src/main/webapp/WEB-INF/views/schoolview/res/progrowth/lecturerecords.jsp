<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看单个听课"></ui:htmlHeader>
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css" media="screen" />
	
	
</head>

<body>
	<div class="return_1"></div>
	<div class="wraper">
		<div class="top">
			<jsp:include page="../../common/top.jsp"></jsp:include>
		</div>
		<div class="pro_cont">
			<div class="top_nav">
				<h3>
					当前位置：
					<jy:nav id="zych">
						<jy:param name="orgID" value="${lr.orgId}"></jy:param>
						<jy:param name="xdid" value="${lr.phaseId}"></jy:param>
					</jy:nav>
				</h3>
			</div>
			<div class="clear"></div>

			<div class="record_sheet" style="height: auto;">
				<h3>听课记录表</h3>
				<!-- 校外听课查看 -->
				<c:if test="${lr.type=='1'}">
					<div class="record_sheet_cont" style="height: auto;">
						<div class="r_s_c">
							<h1 style="text-align: left;">
								<a href="">*</a>课题
							</h1>
							<strong>${lr.topic}</strong> <b>听课地点</b> <strong>${lr.lectureAddress}</strong>
						</div>
						<div class="r_s_c">
							<h1 style="border-top-left-radius: 0;">授课教师</h1>
							<strong>${lr.teachingPeople}</strong> <b>单位</b> <strong>${lr.lectureCompany}</strong>
							<b>年级学科</b> <strong>${lr.gradeSubject}</strong>
						</div>
						<div class="r_s_c">
							<h1 style="border-top-left-radius: 0;">听课人</h1>
							<strong>${lr.lecturePeople}</strong> <b>听课时间</b> <strong><fmt:setLocale
									value="zh" />
								<fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd" /></strong>
							<b>听课节数</b> <strong>${lr.numberLectures}</strong>
						</div>
						<div class="r_s_c" style="width: 930px; height: 35px; float: left;">
							<b style="width: 930px; height: 35px; line-height: 35px;border-left:none;border-right:none;">听课意见</b>
						</div>
						<div class="clear"></div>
						<div class="l_cont">${lr.lectureContent}</div>
					</div>
				</c:if>

				<!-- 校内听课查看 -->
				<c:if test="${lr.type=='0'}">
					<div class="record_sheet_cont" style="height: auto;">
						<div class="r_s_c">
							<h1>课题</h1>
							<strong>${lr.topic}</strong> <b>评价等级</b> <strong>${lr.evaluationRank}</strong>
						</div>
						<div class="r_s_c">
							<h1 style="border-top-left-radius: 0;">授课教师</h1>
							<strong>${lr.teachingPeople}</strong> <b>学科</b> <strong>${lr.subject}</strong>
							<b>年级</b> <strong>${lr.grade}</strong>
						</div>
						<div class="r_s_c">
							<h1 style="border-top-left-radius: 0;">听课人</h1>
							<strong>${lr.lecturePeople}</strong> <b>听课时间</b> <strong><fmt:setLocale
									value="zh" />
								<fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd" /></strong>
							<b>听课节数</b> <strong>${lr.numberLectures}</strong>
						</div>
						<div class="r_s_c"
							style="width: 930px; height: 35px; float: left;">
							<b style="width: 930px; height: 35px; line-height: 35px;border-left:none;border-right:none;">听课意见</b>
						</div>
						<div class="clear"></div>
						<div class="l_cont">${lr.lectureContent}</div>
					</div>
				</c:if>
				<div class="clear"></div>
				<iframe id="commentBox" onload="setCwinHeight(this,false,600)"
					src="jy/comment/list?authorId=${lr.lecturepeopleId}&flags=false&resType=${resType}&resId=${lr.id}&title=<ui:sout value='${lr.topic}' encodingURL='true' escapeXml='true'></ui:sout>"
					width="100%" height="600px;" style="border: none;" scrolling="no"></iframe>
			</div>
			<div class="clear"></div>
			<!-- 单个听课意见的回复 -->
			<div id="huifu"></div>
		</div>
		<div class="clear"></div>
		<%@include file="../../common/bottom.jsp"%>

	</div>

</body>
</html>