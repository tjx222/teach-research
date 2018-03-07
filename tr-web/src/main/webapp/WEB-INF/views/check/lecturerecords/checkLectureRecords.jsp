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
		width: 98.5%;
	}
	</style>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅"></ui:tchTop>
</div>
<div class="jyyl_nav">
<c:if test="${lectureRecords.flago=='0' }">
	当前位置：<jy:nav id="check_teacher">
				<jy:param name="gradeId" value="${lr.gradeId }"></jy:param>
				<jy:param name="termId" value="${lr.term }"></jy:param>
				<jy:param name="subjectId" value="${lr.subjectId }"></jy:param>
		   </jy:nav> > <a href="${ctx}jy/check/lectureRecords/toLectureRecordsList?flago=0&lecturepeopleId=${lr.lecturepeopleId }&term=${lr.term}&gradeId=${lr.gradeId}&subjectId=${lr.subjectId}">${lr.lecturePeople }</a> > 查阅
</c:if>
<c:if test="${lectureRecords.flago!='0' }">
	<c:if test="${!empty lectureRecords.gradeId }">
	当前位置：<jy:nav id="check_leader1">
				<jy:param name="gradeId" value="${lr.gradeId }"></jy:param>
				<jy:param name="termId" value="${lr.term }"></jy:param>
				<jy:param name="sysRoleId" value="${sysRoleId}"></jy:param>
		   </jy:nav> > <a href="${ctx}jy/check/lectureRecords/toLectureRecordsList?flago=1&lecturepeopleId=${lr.lecturepeopleId }&term=${lr.term}&gradeId=${lr.gradeId}&subjectId=${lr.subjectId}&sysRoleId=${sysRoleId}">${lr.lecturePeople }</a> > 查阅
	</c:if>
	<c:if test="${empty lectureRecords.gradeId }">
	当前位置：<jy:nav id="check_leader">
				<jy:param name="termId" value="${lr.term }"></jy:param>
				<jy:param name="sysRoleId" value="${sysRoleId}"></jy:param>
		   </jy:nav> > <a href="${ctx}jy/check/lectureRecords/toLectureRecordsList?flago=1&lecturepeopleId=${lr.lecturepeopleId }&term=${lr.term}&subjectId=${lr.subjectId}&sysRoleId=${sysRoleId}">${lr.lecturePeople }</a> > 查阅
	</c:if>
</c:if>
</div>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap2"> 
		<h3 class="file_title">${lr.topic }</h3>
		<div class="file_sel">
			<div class="anti_plagiarism">
				<label for="">反抄袭：</label>
				<div class="ser">
					<input type="text" class="ser_txt"/>
					<input type="button" class="ser_btn1" />
				</div>
			</div>
		</div>
		<div class="file_info">
			<div class="file_info_l">
				<span></span>
				提交人：${lr.lecturePeople }
				<!-- <div class="hide_info">
					<b>学科：品德与社会</b>
					<b>年级：二年级</b>
					<b>职务：教师</b>
					<b>职称：小学高级语文</b>
				</div> -->
			</div>
			<div class="file_info_r">
				<span></span>
				提交日期：<fmt:formatDate value="${lr.submitTime }" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="word_plug_ins">
			<c:if test="${lr.type==1}">
			  	<div class="record_sheet_cont" style="height:auto;">
					<div class="r_s_c">
						<h1 style="text-align:left;"><a href="">*</a>课题</h1>
						<strong title="${lr.topic}">${lr.topic}</strong>
						<b>听课地点</b>
						<strong style="width:518px;">${lr.lectureAddress}</strong>
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
					<div  class="r_s_c" style="width: 1120px;height: 35px;float: left;">
						<b style="border-left:none;border-right:none;width: 1120px;height: 35px;line-height: 35px;">听课意见</b>
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
					<div  class="r_s_c" style="width: 1120px;height: 35px;float: left;">
						<b style="border-left:none;border-right:none;width: 1120px;height: 35px;line-height: 35px;">听课意见</b>
					</div>
					<div class="clear"></div>
					<div class="lecturerecords_style">${lr.lectureContent}</div>
				</div>
			</c:if>
		</div>
		<div class="page_wprd">
			<div class="page_wprd_l" num="${lectureRecords.flags-1<0?'noPre':lectureRecords.flags-1 }" userId="${lr.lecturepeopleId }" term="${lectureRecords.term }">
				<span></span>
				上一篇
			</div>
			<div class="page_wprd_r" num="${lectureRecords.flags+1>=submitCount?'noNext':lectureRecords.flags+1 }" userId="${lr.lecturepeopleId }" term="${lectureRecords.term }"> 
				下一篇
				<span></span>
			</div>
		</div>
	</div>
	<div class="border"></div>
	<div class="check_teacher_wrap2"> 
		<c:if test="${lr.type==0&&!empty otherRecords}">
		<div class="same_submission">
			<h3 class="same_submission_h3"><span></span>他们也提交了本课的听课记录：</h3>
			<div class="same_submission_cont">
				<c:forEach var="record" items="${otherRecords }">
					<div class="head_dl">
						<dl>
							<dd>
								<jy:di key="${record.teachingpeopleId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
								<ui:photo src="${u.photo}"></ui:photo>
								<c:if test="${record.isScan==1 }"><span class="have_access1"></span></c:if>
							</dd>
							<dt>
								<span class="head_name">${record.lecturePeople }</span> 
							</dt>
						</dl>
					</div>
				</c:forEach>
				
			</div>
		</div>
		</c:if>
		<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"
		src="jy/check/lookCheckOption?flags=true&term=${lectureRecords.term}&title=<ui:sout value='${lr.topic}' encodingURL='true' escapeXml='true' ></ui:sout>&resType=6&authorId=${lr.lecturepeopleId}&resId=${lr.id}"></iframe>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
<ui:require module="check/check_lecturerecords/js"></ui:require>
<script type="text/javascript">
require(['jquery','check_lecturerecords']);
</script>
</body>
</html>