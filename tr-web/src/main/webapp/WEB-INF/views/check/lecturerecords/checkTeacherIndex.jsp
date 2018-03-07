<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅听课记录"></ui:htmlHeader>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<style>
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98%;
	}
	</style>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅教师"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：<jy:nav id="check_teacher">
				<jy:param name="gradeId" value="${lr.gradeId }"></jy:param>
				<jy:param name="termId" value="${lr.term }"></jy:param>
				<jy:param name="subjectId" value="${lr.subjectId }"></jy:param>
		   </jy:nav>
</div>
<form id="form1" action="${ctx}jy/check/lectureRecords/toCheckTeacherIndex" method="post">
<input type="hidden" id="gradeId" name="gradeId" value="${lr.gradeId }"/>
<input type="hidden" id="subjectId" name="subjectId" value="${lr.subjectId }"/>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top">
			<div class="semester_sel_wrap">
				<label for="">学期：</label>
				<div class="semester_sel">
					<select name="term" class="chosen-select-deselect semester" style="width: 80px; height: 25px;" onchange="form1.submit();">
						<option value="0" <c:if test='${lr.term==0 }'>selected="selected"</c:if>>上学期</option>
						<option value="1" <c:if test='${lr.term==1 }'>selected="selected"</c:if>>下学期</option>
					</select>
				</div>
			</div>
		</div>
		<div class="check_teacher_bottom clearfix">
			<div class="out_reconsideration_see_title_box">
			        <span class="scroll_leftBtn"></span>
			        <div class="in_reconsideration_see_title_box"> 
						<ol class="olgrade reconsideration_see_title"> 
							<c:forEach var="grade" items="${gradeList }">
								<li class="li_bg <c:if test='${lr.gradeId==grade.id }'>li_act</c:if>" gradeId="${grade.id }">${grade.name }</li>
							</c:forEach> 
						</ol> 
					</div>
					<span class="scroll_rightBtn"></span>
				</div>
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="ol_subject">
						<c:forEach var="subject" items="${subjectList }">
							<li class="ol_subject_li <c:if test='${lr.subjectId==subject.id }'>ol_subject_li_act</c:if>" subjectId="${subject.id }">${subject.name }</li>
						</c:forEach>
					</ol>
				</div>
				<div class="check_teacher_bottom_inside_r">
					<div class="check_info">
						<div id="grade_subject" class="grade_subject">一年级语文</div><div class="grade_subject">共${teacherCount }人</div>
						<div class="grade_subject">撰写总数：${totalWriteCount }课</div>
						<div class="grade_subject">提交总数：${totalSubmitCount }课</div>
						<div class="grade_subject">已查阅总数：${totalScanCount }课</div>
					</div>
					<c:forEach var="teacherMap" items="${teacherMapList }">
						<div class="teacher_info" userId="${teacherMap.userSpace.userId }" termId="${lr.term }">
							<div class="teacher_info_top">
								<div class="teacher_info_top_bg">
									<ol class="tea_info">
										<li><span></span>撰写：${teacherMap.writeCount }课</li>
										<li><span></span>提交：${teacherMap.submitCount }课</li>
										<li><span></span>已查阅：${teacherMap.scanCount }课</li>
									</ol> 
								</div>
								<jy:di key="${teacherMap.userSpace.userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
								<ui:photo src="${u.photo}" width="74" height="74"></ui:photo>
								<!-- <img src="../../static/check_thesis/images/img.png" class="teacher_head" alt=""> -->
								<!-- <div class="hide_info">
									<span>学科：品德与社会</span>
									<span>年级：二年级</span>
									<span>职务：教师</span>
									<span>职称：小学高级语文</span>
								</div> -->
							</div>
							<div class="teacher_info_bottom">${teacherMap.userSpace.username }</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
<ui:htmlFooter style="1"></ui:htmlFooter>
<ui:require module="check/check_lecturerecords/js"></ui:require>
<script type="text/javascript">
require(['jquery','check_lecturerecords']);
</script>
</body>
</html>