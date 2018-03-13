<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="集体备课"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>集体备课
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
			<div class="content_bottom1" id="wrap">
				<div id="scroller">
				<div id="listdiv">
					<c:forEach items="${activityList.datalist}" var="activity">  
					<div class="activity_tch">
						<c:if test="${activity.typeId==1}">
						<div class="activity_tch_left">
						同<br />备<br />教<br />案
						</div>
						</c:if>
						<c:if test="${activity.typeId==2}">
						<div class="activity_tch_left1">
						主<br />题<br />研<br />讨
						</div>
						</c:if>
						<c:if test="${activity.typeId==3}">
						<div class="activity_tch_left2">
						视<br />频<br />教<br />研
						</div>
						</c:if>
						
						<div class="activity_tch_right" activityId="${activity.id }" typeId="${activity.typeId }" isOver="${activity.isOver }" startDate="${activity.startTime}">
							<h3><span class="title">${activity.activityName}</span><c:if test="${activity.isOver }"><span class="end"></span></c:if></h3>
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
						<ui:page url="${ctx}jy/activity/tchIndex" data="${activityList}"  callback="addData"/>
						<input type="hidden" class="currentPage" name="currentPage">
					</form> 
					<div style="height:1rem;"></div> 
				</div>
				
			</div>
			</c:if>
			<c:if test="${empty activityList.datalist || fn:length(activityList.datalist)<=0}">
			<div class="content_bottom1" >
				<div class="content_k" style="margin:10rem auto;">
					<dl>
						<dd></dd>
						<dt>您现在还没有可参与的集体备课，稍后再来吧！</dt>
					</dl>
				</div>
			</div>
			</c:if>
		</div>
	</section>
	
</div>
</body>
<script type="text/javascript">
	require(['activity'],function($){	
	}); 
</script>
</html>