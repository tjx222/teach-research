<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="管理记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/managerecord/css/managerecord.css" media="screen" />
	<ui:require module="../m/managerecord/js"></ui:require>	
</head>
<body> 
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_ });"></span>
		<ul>
			<li><a href="jy/managerecord/indexdata?term=0&grade=${_CURRENT_SPACE_.gradeId}&subject=${_CURRENT_SPACE_.subjectId}&_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" class="${term==0?'com_header_act':''}">上学期</a></li>
			<li><a href="jy/managerecord/indexdata?term=1&grade=${_CURRENT_SPACE_.gradeId}&subject=${_CURRENT_SPACE_.subjectId}&_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" class="${term==1?'com_header_act':''}">下学期</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="managerecord_bottom_wrap">
			<div>
				<div class="recordsManagement recordsManagement_one">
					<table cellpadding="0"  cellspacing="0" class="recordsManagement_table_content">
						<tr class="recordsManagement_table_title">
							<th><a href="#">查阅记录</a></th>
							<th>各学科提交总数</th>
							<th>查阅数</th>
							<th>查阅意见数</th>
							<th></th>
						</tr>
						<c:forEach items="${checklist}" var="check">
							<c:forEach items="${check.type}" var="type">
								<c:set value="true" var="tag"></c:set>
								<c:if test="${type==6 || type==10}">
									<c:set value="false" var="tag"></c:set>
								</c:if>
							</c:forEach>
							<c:if test="${tag}">
								<tr class="recordsManagement_table_con">
									<td class="recordsManagement_td" >${check.name}</td>
									<td>${check.submitNum}</td>
									<td>${check.checkNum}</td>
									<td>${check.commentNum}</td>
									<td><a href="jy/managerecord/check/${check.uri}" class="recordsManagement_seeButton">查看详情</a></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
				<div class="recordsManagement recordsManagement_two">
					<table cellpadding="0"  cellspacing="0" class="recordsManagement_table_content">
					    <tr class="recordsManagement_table_title">
							<th><a href="#">教学管理记录</a></th>
							<th>全校总数</th>
							<th>撰写数</th>
							<th>分享数</th>
							<th></th>
						</tr>
						<c:forEach items="${managelist}" var="manage">
							<tr class="recordsManagement_table_con">
								<td class="recordsManagement_td">${manage.name}</td>
								<td>${manage.submitNum}</td>
								<td>${manage.checkNum}</td>
								<td>${manage.shareNum}</td>
								<td><a href="jy/managerecord/${manage.uri}" class="recordsManagement_seeButton">查看详情</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="recordsManagement recordsManagement_three">
					<table cellpadding="0"  cellspacing="0" class="recordsManagement_table_content">
					    <tr class="recordsManagement_table_title">
							<th><a href="#">教研活动记录</a></th>
							<th>全校总数</th>
							<th>发起总数</th>
							<th>分享数</th>
							<th>参与数</th>
							<th></th>
						</tr>
						<c:forEach items="${activitylist}" var="activity">
							<tr class="recordsManagement_table_con">
								<td class="recordsManagement_td">${activity.name}</td>
								<td>${activity.submitNum}</td>
								<td>${activity.checkNum}</td>
								<td>${activity.shareNum}</td>
								<td>${activity.commentNum}</td>
								<td><a href="jy/managerecord/${activity.uri}" class="recordsManagement_seeButton">查看详情</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div style="height:2rem; clear:both;"></div>
			</div>
		</div> 
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','indexdata'],function($){	
	});  
</script>
</html>