<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="可查阅听课记录列表"></ui:htmlHeader>
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
	<ui:tchTop style="1" modelName="查阅听课记录"></ui:tchTop>
</div>
<div class="jyyl_nav">
<c:if test="${lectureRecords.flago!='1' }">
	当前位置：<jy:nav id="check_teacher">
				<jy:param name="gradeId" value="${lectureRecords.gradeId }"></jy:param>
				<jy:param name="termId" value="${lectureRecords.term }"></jy:param>
				<jy:param name="subjectId" value="${lectureRecords.subjectId }"></jy:param>
		   </jy:nav> 
		   > ${user.name }
</c:if>
<c:if test="${lectureRecords.flago=='1' }">
<c:if test="${!empty lectureRecords.gradeId }">
	当前位置：<jy:nav id="check_leader1">
	       		<jy:param name="gradeId" value="${lectureRecords.gradeId }"></jy:param>
				<jy:param name="termId" value="${lectureRecords.term }"></jy:param>
				<jy:param name="sysRoleId" value="${sysRoleId}"></jy:param>
	       </jy:nav> 
	       > ${user.name }
</c:if>
<c:if test="${empty lectureRecords.gradeId }">
当前位置：<jy:nav id="check_leader">
				<jy:param name="termId" value="${lectureRecords.term }"></jy:param>
				<jy:param name="sysRoleId" value="${sysRoleId}"></jy:param>
	       </jy:nav> 
	       > ${user.name }
</c:if>
</c:if>
</div>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top check_teacher_top1">
			<div class="teacher_news"> 
				<div class="teacher_news_head">
					<div class="teacher_news_head_bg"></div>
					<jy:di key="${user.id}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
					<ui:photo src="${u.photo}" width="62" height="62" ></ui:photo>
					<!-- <img src="../../static/check_thesis/images/img.png" class="teacher_news_head_img" width="54" height="54" alt=""> -->
					<!-- <div class="hide_info">
						<span>学科：品德与社会</span>
						<span>年级：二年级</span>
						<span>职务：教师</span>
						<span>职称：小学高级语文</span>
					</div> -->
				</div>
				<div class="name">${user.name }</div>
				<div class="name_right">
					<span>撰写：${dataMap.writeCount }课</span>
					<span>提交：${dataMap.submitCount }课</span>
					<span>已查阅：${dataMap.scanCount }课</span>
				</div>
			</div>
			<form id="form1" name="form1" action="${ctx}jy/check/lectureRecords/toLectureRecordsList">
			<input type="hidden" name="lecturepeopleId" value="${user.id }"/>
			<input type="hidden" name="flago" value="${lectureRecords.flago }"/>
			<div class="semester_sel_wrap semester_sel_wrap1">
				<label for="">学期：</label>
				<div class="semester_sel">
					<select name="term" class="chosen-select-deselect semester" style="width: 80px; height: 25px;" onchange="form1.submit();">
						<option value="0" <c:if test='${lectureRecords.term==0 }'>selected="selected"</c:if>>上学期</option>
						<option value="1" <c:if test='${lectureRecords.term==1 }'>selected="selected"</c:if>>下学期</option>
					</select>
				</div>
				<!-- <div class="semester_sel" style="width: 90px;float:right;">
					<select class="chosen-select-deselect semester" style="width: 90px; height: 25px;">
						<option value="">已查阅</option>
						<option value="">未查阅</option>
					</select>
				</div> -->
			</div>
			</form>
		</div> 
		<div class="check_teacher_bottom2">
		<c:if test="${!empty dataMap.recordsList }">
			<table class="table_th">
				<tr>
					<th class="table_th1">课题</th>
					<th class="table_th2">年级学科</th>
					<th class="table_th3">授课人</th>
					<th class="table_th4">听课节数</th>
					<th class="table_th5">听课时间</th>
					<th class="table_th6">查阅状态</th>
				</tr>
			</table>
			<div class="table_td_wrap">
				<table class="table_td">
					<c:forEach var="lr" items="${dataMap.recordsList }" varStatus="status">
						<tr>
							<td class="table_td1"><a href="${ctx}jy/check/lectureRecords/toCheckLectureRecords?flago=${lectureRecords.flago }&lecturepeopleId=${user.id}&term=${lectureRecords.term}&flags=${status.index}&gradeId=${lr.gradeId}&subjectId=${lr.subjectId}&sysRoleId=${sysRoleId}" title="${lr.topic }">【${lr.type==0?'校内':'校外' }】<ui:sout value="${lr.topic }" length="27" needEllipsis="true"></ui:sout></a></td>
							<td class="table_th2"><c:if test="${lr.type==0 }">${lr.grade }${lr.subject }</c:if><c:if test="${lr.type==1 }">${lr.gradeSubject }</c:if></td>
							<td class="table_th3">${lr.teachingPeople }</td>
							<td class="table_th4">${lr.numberLectures }</td>
							<td class="table_th5"><fmt:formatDate value="${lr.lectureTime }" pattern="yyyy-MM-dd"/></td>
							<td class="table_th6">${lr.isScan!=1?'<strong>未查阅</strong>':'已查阅' }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
<ui:require module="check/check_lecturerecords/js"></ui:require>
<script type="text/javascript">
require(['jquery','check_lecturerecords']);
</script>
</body>
</html>