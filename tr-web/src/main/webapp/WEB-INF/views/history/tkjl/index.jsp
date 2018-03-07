<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title=""></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/common.css"media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98%;
	}
	</style>
	<ui:require module="history/js"></ui:require>
</head>
<body style="background: #fff;">
<div class="calendar_year_center">
	<div class="page_option"> 
		<form action="jy/history/${year}/tkjl/index" id="lr_form" method="post"> 
			<div class="a1">
				<select class="chosen-select-deselect school_year " name="term" style="width: 90px; height: 25px;" onchange="formsubmit()">
					<option value="" ${empty lr.term? 'selected':'' }>全学年</option>
					<option value="0" ${lr.term==0? 'selected' : '' }>上学期</option>
					<option value="1" ${lr.term==1? 'selected' : '' }>下学期</option>
				</select>
			</div>  
			<div class="left_border"></div>
			<div class="a2">
				<select class="chosen-select-deselect category " name="type" style="width: 120px; height: 25px;" onchange="formsubmit()">
					<option value="" ${empty lr.type? 'selected':'' }>全部资源</option>
					<option value="0" ${lr.type==0? 'selected' : '' }>校内</option>
					<option value="1" ${lr.type==1? 'selected' : '' }>校外</option>
				</select>
			</div> 
			<div class="serach">
				<input type="text" class="ser_txt" name="topic" value="${lr.topic }"/>
				<input type="button" class="ser_btn" onclick="formsubmit()"/>
			</div>
		</form>
	</div>
	<div class="resources_table_wrap">
		<c:if test="${not empty data.datalist}">
			<table class="resources_table">
				<tr>
					<th style="width:411px;text-align:left;padding-left:10px;">课题</th>
					<th style="width:130px;">年级学科</th>
					<th style="width:120px;">授课人</th>
					<th style="width:100px;">听课节数</th>
					<th style="width:110px;">听课时间</th>
					<th style="width:110px;">分享状态</th>
				</tr>
				<c:forEach var="lr" items="${data.datalist }">
				    <tr>
						<td style="text-align:left;">
						    <a href="jy/teachingView/view/LectureRecord?id=${lr.id }" target="_blank">${lr.type=='0'?'【校内】':'【校外】'}<ui:sout value="${lr.topic}" length="26" needEllipsis="true"/></a>
							 <c:if test="${lr.isReply=='1'}"><span class="hui"  data-id="${lr.id }" data-teachingPeopleId="${lr.teachingpeopleId}" data-lecturePeopleId="${lr.lecturepeopleId}"></span></c:if>
							 <c:if test="${lr.isComment=='1'}"><span class="ping" data-id="${lr.id }" data-resType="${lr.resType }" data-userId="${_CURRENT_USER_.id}"></span></c:if>
						</td>
						<td><c:if test="${lr.type=='0'}">
					    		${lr.grade}${lr.subject}
					    	</c:if>
						    <c:if test="${lr.type=='1'}">
					    		${empty lr.gradeSubject?'-':lr.gradeSubject}
					    	</c:if>
						</td>
						<td>${empty lr.teachingPeople?'-':lr.teachingPeople}</td>
						<td>${lr.numberLectures}</td>
						<td><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/></td>
						<td><c:if test="${lr.isShare==1}">已分享</c:if><c:if test="${lr.isShare==0}">未分享</c:if></td>
					</tr>
				</c:forEach>
			
			</table>
		</c:if>
		<c:if test="${empty data.datalist}">
			<div class="cont_empty">
			    <div class="cont_empty_img"></div>
			    <div class="cont_empty_words">没有资源哟！</div> 
			</div>
		</c:if>
		<div class="pages">
			<!--设置分页信息 -->
			<form name="pageForm" method="post">
				<ui:page url="jy/history/${year}/tkjl/index?flags=${flags}" data="${data}" views="5"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
				<input type="hidden" name="term" value="${lr.term }">
				<input type="hidden" name="type" value="${lr.type }">
			</form>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','lecturerecords'],function(){});
</script>
</html>