<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅集备"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<ui:require module="../m/check/js"></ui:require>
</head> 
<body>
<div id="userListContent">
	<div class="check_content_center">
		<div class="class_name"><jy:dic key="${grade }"></jy:dic><jy:dic key="${subject }"></jy:dic></div>
		<div class="class_name_option">
			<span>活动总数：${countAll }</span>    
			<span>查阅总数：${countAudit }</span>
		</div>
	</div>
	<div class="check_content_bottom" style="top:5rem;">
		<div id="scroller"> 
			<c:if test="${!empty listPage.datalist }">
			<div id="addmoredatas">
			<c:forEach items="${listPage.datalist}" var="activity">
				<div class="activity_tch"> 
					<c:if test="${activity.typeId==1 }">
						<div class="activity_tch_left">
							同<br />备<br />教<br />案
						</div> 
					</c:if>
					<c:if test="${activity.typeId==2 }">
						<div class="activity_tch_left1">
							主<br />题<br />研<br />讨
						</div> 
					</c:if>
					<c:if test="${activity.typeId==3 }">
						<div class="activity_tch_left2">
							视<br />频<br />研<br />讨
						</div> 
					</c:if>
					<div class="activity_tch_right" onclick="chayuejb(this)" activityId="${activity.id }" typeId="${activity.typeId }" subject="${subject }" grade="${grade }" term="${term }">
						<h3>
							<span class="title">${activity.activityName}</span>
							<c:if test="${activity.isAudit}">
								<span class="have_access"></span>
					    	</c:if>
						</h3>
						<div class="option">
							<div class="promoter"><strong></strong>发起人：<span>${activity.organizeUserName}</span></div>
							<div class="partake_sub"><strong></strong>参与学科：<span>${activity.subjectName}</span></div>
							<div class="partake_class"><strong></strong>参与年级：<span>${activity.gradeName}</span></div> 
						</div>
						<div class="option">
							<div class="time"><strong></strong><span><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></span></div>
							<div class="discussion_number"><strong></strong>讨论数：<span>${activity.commentsNum}</span></div>
						</div>
					</div> 
				</div>
			</c:forEach>
			</div>
			<form  name="pageForm" method="post">
				<ui:page url="${ctx}jy/check/activity/activitylist" data="${data}" dataType="true" callback="addmoredatas"/>
				<input type="hidden" name="subject" value="${subject }">
				<input type="hidden" name="grade" value="${grade }">
				<input type="hidden" name="term" value="${term }">
				<input type="hidden" class="currentPage" name="currentPage">
			</form>
			</c:if>
			<c:if test="${empty listPage.datalist }">
				<div class="content_k" style="margin-top: 4rem;">
					<dl>
						<dd></dd>
						<dt>现在还没有已经完成的集体备课，稍后再来查阅吧！</dt>
					</dl>
				</div>
			</c:if>
			<div style="height:2rem;clear:both;"></div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto","js"],function(){
	}); 
</script>
</html>