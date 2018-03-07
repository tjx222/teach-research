<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="听课记录内容"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">
</head>
<body>
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="听课记录内容"></ui:tchTop>
	</div>
	
	<div class="check_teacher_wrap">
	<div class="check_teacher_wrap2"> 
		<h3 class="file_title">${lr.topic }</h3>
		<div class="word_plug_ins">
			<c:if test="${lr.type==1}">
			  	<div class="record_sheet_cont" style="height:auto;">
					<div class="r_s_c">
						<h1 style="text-align:left;"><a href="">*</a>课题</h1>
						<strong>${lr.topic}</strong>
						<b>听课地点</b>
						<strong style="width:200px;">${lr.lectureAddress}</strong>
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
					<div  class="r_s_c" style="width: 920px;height: 35px;float: left;">
						<b style="border-left:none;border-right:none;width: 920px;height: 35px;line-height: 35px;">听课意见</b>
					</div>
					<div class="clear"></div>
					<div class="lecturerecords_style">${lr.lectureContent}</div>
				</div>
			</c:if>
			
			<!-- 校内听课查看 -->
			<c:if test="${lr.type==0}">
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
					<div  class="r_s_c" style="width: 920px;height: 35px;float: left;">
						<b style="border-left:none;border-right:none;width: 920px;height: 35px;line-height: 35px;">听课意见</b>
					</div>
					<div class="clear"></div>
					<div class="lecturerecords_style">${lr.lectureContent}</div>
				</div>
			</c:if>
		</div>
		
	</div>
</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>