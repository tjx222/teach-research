<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <ui:htmlHeader title="查阅集体备课列表内容页"></ui:htmlHeader>
 <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
    
    <script type="text/javascript">
    	//选择学期
    	function selectTerm(){
    		$("#hiddenTerm").val($("#term").val());
    		$("#hiddenForm").submit();
    	}
    	//查阅
    	function chayue(activityId,typeId){
    		window.parent.chayueActivity(activityId,typeId);
    	}
    </script>
    <script type="text/javascript">
	$(document) .ready( function() {
		$(".semester").chosen({disable_search : true});
		$(".table_th tr th").last().css({"border-right":"none"});
		$(".table_td tr").each(function (){
			$(this).find("td").last().css({"border-right":"none"});
		}); 
	})
	</script>
	<style>
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 99%;
	}
	</style>
  </head>
  <body style="background:#fff;">

	<div class="check_info">
		<div class="grade_subject"><jy:dic key="${grade }"></jy:dic><jy:dic key="${subject }"></jy:dic></div>
		<div class="grade_subject">活动总数：${countAll }</div>
		<div class="grade_subject">查阅总数：${countAudit }</div>
		<div class="semester_sel_wrap" style="margin-top:7px;">
			<label for="">学期：</label>
			<div class="semester_sel">
				<select id="term" class="chosen-select-deselect semester" style="width: 80px; height: 25px;" onchange="selectTerm()">
					<option value="0" <c:if test="${term == 0 }">selected="selected"</c:if>>上学期</option>
					<option value="1" <c:if test="${term == 1 }">selected="selected"</c:if>>下学期</option>
				</select>
			</div>
		</div>
	</div>
	
	<c:if test="${!empty listPage.datalist }">
	<table class="table_th" style="width:890px;">
		<tr>
			<th style="width:161px;">活动主题</th>
			<th style="width:80px;">学科</th>
			<th style="width:100px;">参与年级</th>
			<th style="width:70px;">发起人</th>
			<th style="width:180px;">活动时限</th>
			<!-- <th style="width:50px;">评论数</th> -->
			<th style="width:50px;">操作</th>
		</tr>
	</table>
	<div class="table_td_wrap1" >
		<table class="table_td" style="margin:0 auto;width:890px;" >
			<c:forEach items="${listPage.datalist}" var="activity">
				<tr>
					<td title="${activity.activityName}" style="width:160px;"><strong>【${activity.typeName}】</strong><a onclick="chayue('${activity.id}','${activity.typeId }')"><ui:sout value="${activity.activityName}" length="10" needEllipsis="true"></ui:sout></a></td>
					<td title="${activity.subjectName}" style="width:80px;text-align:center;"><ui:sout value="${activity.subjectName}" length="10" needEllipsis="true"></ui:sout></td>
					<td title="${activity.gradeName}" style="width:100px;text-align:center;"><ui:sout value="${activity.gradeName}" length="16" needEllipsis="true"></ui:sout></td>
					<td style="width:70px;text-align:center;">${activity.organizeUserName}</td>
					<td style="width:180px;text-align:center;"><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
					<%-- <td style="width:50px;text-align:center;">${activity.commentsNum}</td> --%>
					<td style="width:50px;text-align:center;">
						<c:if test="${activity.isAudit}">
				    	   	<b style="cursor: pointer;" onclick="chayue('${activity.id}','${activity.typeId }')">已查阅</b>
			    		</c:if>
				    	<c:if test="${!activity.isAudit}">
				    	   	<strong style="cursor: pointer;" onclick="chayue('${activity.id}','${activity.typeId }')">查阅</strong>
				    	</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<form name="pageForm" method="post" style="display: block;">
		<ui:page url="${ctx}jy/check/activity/activitylist" data="${listPage}"  />
		<input type="hidden" class="currentPage" name="currentPage">
		<input type="hidden" name="grade" value="${grade}">
		<input type="hidden" name="subject" value="${subject}">
		<input type="hidden" name="term" value="${term}">
	</form>
	</c:if>
	<form id="hiddenForm" action="${ctx}jy/check/activity/activitylist" method="post">
		<input type="hidden" name="grade" value="${grade}">
		<input type="hidden" name="subject" value="${subject}">
		<input type="hidden" id="hiddenTerm" name="term" value="${term}">
	</form> 








  
<%-- <div class="grade_big" style="width:775px;">
	<div class="grade_tab" style="width:760px;padding-left:10px;">
		<h1 style="width:750px;">
			<span><jy:dic key="${grade }"></jy:dic><jy:dic key="${subject }"></jy:dic></span>
			<strong>活动总数：${countAll }</strong>
			<strong>查阅总数：${countAudit }</strong>
			<strong style="margin-left: 185px;width: 120px">学期：
				<select id="term" class="chosen-select-deselect" style="width:80px;" onchange="selectTerm()"> 
					<option value="0" <c:if test="${term == 0 }">selected="selected"</c:if> >上学期</option>
					<option value="1" <c:if test="${term == 1 }">selected="selected"</c:if> >下学期</option>
				</select>
			</strong>
		</h1>
		<div class="collective_cont_big">
			<c:if test="${!empty listPage.datalist }">
				<div class="collective_cont_tab">
					<table border="1" style="width:750px;">
					  <tr>
					    <th style="width:236px;">活动主题</th>
					    <th style="width:90px;">学科</th>
					    <th style="width:100px;">参与年级</th>
					    <th style="width:60px;">发起人</th>
					    <th style="width:155px;">活动时限</th>
					    <th style="width:55px;">讨论数</th>
					    <th style="">操作</th>
					  </tr>
					  
				  	<c:forEach items="${listPage.datalist}" var="activity">
						  <tr>
						    <td title="${activity.activityName}" style="text-align:left;"><strong>【${activity.typeName}】</strong><span style="cursor: pointer;width:162px;" onclick="chayue('${activity.id}','${activity.typeId }')"><ui:sout value="${activity.activityName}" length="24" needEllipsis="true"></ui:sout></span></td>
						    <td title="${activity.subjectName}"><label style="display:block;width:82px;padding-left:5px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${activity.subjectName}</label></td>
						    <td title="${activity.gradeName}"><label style="display:block;width:92px;padding-left:5px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${activity.gradeName}</label></td>
						    <td>${activity.organizeUserName}</td>
						    <td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
						    <td>${activity.commentsNum}</td>
						    <td>
						    	<c:if test="${activity.isAudit}">
						    	   	<b style="cursor: pointer;" onclick="chayue('${activity.id}','${activity.typeId }')">已查阅</b>
					    		</c:if>
						    	<c:if test="${!activity.isAudit}">
						    	   	<u style="cursor: pointer;" onclick="chayue('${activity.id}','${activity.typeId }')">查阅</u>
						    	</c:if>
						    </td>
						  </tr>
					  </c:forEach>
					  
					</table>
				</div>
				
			</c:if>
			<c:if test="${empty listPage.datalist }">
				<!-- 无文件 -->
   				<div class="nofile">
					<div class="nofile1">
						现在还没有已经完成的集体备课，稍后再来查阅吧！
					</div>
				</div>
			</c:if>
		</div>
		<div class="clear"></div>
		<form name="pageForm" method="post" style="display: block;">
					<ui:page url="${ctx}jy/check/activity/activitylist" data="${listPage}"  />
					<input type="hidden" class="currentPage" name="currentPage">
					<input type="hidden" name="grade" value="${grade}">
					<input type="hidden" name="subject" value="${subject}">
					<input type="hidden" name="term" value="${term}">
				</form>
	</div>

    <form id="hiddenForm" action="${ctx}jy/check/activity/activitylist" method="post">
		<input type="hidden" name="grade" value="${grade}">
		<input type="hidden" name="subject" value="${subject}">
		<input type="hidden" id="hiddenTerm" name="term" value="${term}">
	</form>
</div> --%>
  </body>
</html>
