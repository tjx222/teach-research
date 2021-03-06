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
<div class="semester_wrapper">
	<div class="semester_wrap">
		<span class="check_menu_top"></span>
		<div class="semester_wrap1">  
			<p data-type="activity" data-term="0">上学期</p>
			<p data-type="activity" data-term="1">下学期</p> 
		</div>
	</div>
</div>
<div class="mask"></div>
<input type="hidden" id="listType" name="listType" value="${listType}">
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_ });"></span>
		<ul>
			<li><a href="jy/managerecord/activity?listType=0&term=${term}&_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" class="${listType==0?'header_act':''}">发起(${count1})</a></li>
			<li><a href="jy/managerecord/activity?listType=1&term=${term}&_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" class="${listType==1?'header_act':''}">参与(${count2})</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<form id="hiddenForm" action="${ctx }jy/managerecord/activity?_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" method="post">
			<input id="hid_term" type="hidden" name="term" value="${term }">
			<input id="hid_listType" type="hidden" name="listType" value="${listType }">
	    </form>
		<div class="managerecord_bottom_wrap">
			<div class="content_bottom">
				<div class="semester">
				     <c:if test="${term==0}">上学期</c:if><c:if test="${term==1}">下学期</c:if>
				  <strong></strong>
				</div>
			</div>
			<div class="content_bottom1">
				<div id="listdiv">
				<div id="loadmore">
				<c:forEach items="${activityList.datalist}" var="activity">
					<div class="activity_tch" data-activity="activity" data-id="${activity.id}" data-typeId="${activity.typeId}" data-isOver="${activity.isOver}" data-startDate="<fmt:formatDate value='${activity.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"> 
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
							视<br />频<br />研<br />讨
							</div> 
						</c:if>
						<div class="activity_tch_right">
							<h3><span class="title"><ui:sout value="${activity.activityName}" length="28" needEllipsis="true"></ui:sout></span><c:if test="${activity.isOver}"><span class="end"></span></c:if>
							<c:if test="${listType!=1}"><c:if test="${activity.isShare}"><span class="y_share"></span></c:if><c:if test="${activity.isSubmit }"><span class="y_submit"></span></c:if></c:if></h3>
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
						<ui:page url="${ctx}jy/managerecord/activity" data="${activityList}" callback="addData" dataType="html" />
						<input type="hidden" class="currentPage" name="currentPage">
						<input type="hidden" id="" name="listType" value="${listType}"> 
						<input type="hidden" id="count1" name="count1" value="${count1}"> 
				        <input type="hidden" id="count2" name="count2" value="${count2}"> 
				        <input type="hidden" id="term" name="term" value="${term}"> 
				    </form>
				   <div style="height:2rem;clear:both;"></div>
				</div>
				<c:if test="${empty activityList.datalist}"><div class="content_k"><dl><dd></dd><dt>您还没有可<c:if test="${listType==0}">发起</c:if><c:if test="${listType==1}">参与</c:if>的教研活动，请稍后再来吧！</dt></dl></div></c:if>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','activity'],function($){	
	});  
</script>
</html>