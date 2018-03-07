<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title=""></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen">  
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
	<ui:require module="history/js"></ui:require>
</head>
<body style="background: #fff;">
<div class="calendar_year_center">
	<div class="page_option">  
		<form action="jy/history/${schoolYear}/czda/showList" id="selectForm" method="post">
		    <div class="a1">${recordbag.name }</div>
			<%-- 
				<select class="chosen-select-deselect full_year " name="schoolTerm" style="width: 101px; height: 25px;" onchange="this.form.submit();">
					<option value="" ${empty thesis.schoolTerm?'selected':''}>全学年</option>
					<option value="0" ${thesis.schoolTerm==0?'selected':''}>上学期</option>
					<option value="1" ${thesis.schoolTerm==1?'selected':''}>下学期</option>
				</select> <div class="left_border"></div>
			  --%> 
			<div class="serach">
				<input type="text" class="ser_txt" name="searchName" value="${searchName }"/>
				<input type="button" class="ser_btn" onclick="formsubmit()"/>
				<input type="hidden" name="name" value="${recordbag.name }"/>
				<input type="hidden" name="id" value="${recordbag.id }"/>
				<input type="hidden" name="year" value="${schoolYear}"/>
			</div>
		</form>
	</div>
	<div class="resources_table_wrap">
		<c:if test="${empty rlist.datalist }"> 
			<div class="cont_empty">
				<div class="cont_empty_img"></div>
				<div class="cont_empty_words">您还没有精选${recordbag.name }哟！</div> 
			</div>
		</c:if>
		<c:if test="${not empty rlist.datalist }">
			<table class="resources_table">
				<tr>
					<th style="width:250px;text-align:left;padding-left:30px;">活动主题</th>
					<th style="width:110px;">参与学科</th>
					<th style="width:110px;">参与年级</th> 
					<th style="width:90px;">发起人</th>
					<th style="width:200px;">活动时限</th>
					<th style="width:50px;">评论数</th>
				</tr>
				<c:forEach items="${rlist.datalist }" var="data">
					<tr id="list">
						<td style="text-align:left;">
							<a title="${data.recordName}" href="${ctx}/jy/teachingView/view/chayueActivity?activityId=${data.resId}&typeId=${data.ext.typeId}" target="_blank">${data.flago }
							<ui:sout value="${data.recordName}" length="11" needEllipsis="true"></ui:sout></a>
							<c:if test="${!empty data.desc}"><span class="weiping wei"  data-desc="${data.desc }" data-name="${data.recordName}"></span> </c:if> 
						</td>
						<td title="${data.ext.subjectName}"><ui:sout value="${data.ext.subjectName}" length="13"  needEllipsis="true" > </ui:sout></td>
						<td title="${data.ext.gradeName}"><ui:sout value="${data.ext.gradeName}" length="13" needEllipsis="true"></ui:sout></td> 
						<td>${data.ext.mainUserName}</td>
						<td>${data.ext.startTime}
						    <c:if test="${empty data.ext.startTime}"> ~ </c:if>
						      至<c:if test="${empty data.ext.endTime}"> ~ </c:if> 
						    ${data.ext.endTime}</td>
						<td>${data.ext.commentsNum}</td>
					</tr>
				</c:forEach>
			</table>
			<form name="pageForm" method="post">
				<ui:page url="${ctx}jy/history/${schoolYear}/czda/showList" data="${rlist}" />
				<input type="hidden" class="currentPage" name="currentPage">
				<input type="hidden" name="name" value="${recordbag.name }"/>
				<input type="hidden" name="id" value="${recordbag.id }"/>
				<input type="hidden" name="year" value="${schoolYear}"/>
			</form>
		</c:if>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','recordbag'],function($){
		//调用方法修改面包屑
		window.parent.changeNav(_WEB_CONTEXT_+"/jy/history/${schoolYear}/czda/index",'${recordbag.name}');
	});
</script>
</html>