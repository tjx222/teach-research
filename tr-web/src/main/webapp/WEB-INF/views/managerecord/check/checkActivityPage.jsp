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
			<p data-term="0">上学期</p>
			<p data-term="1">下学期</p> 
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_ });"></span>
		<ul>
			<li><a class="${listType==0?'com_header_act':''}" data-type="0">已查阅(${checkCount})</a></li>
			<li><a class="${listType==1?'com_header_act':''}" data-type="1">查阅意见(${yijianCount})</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
	    <form id="hiddenForm" action="${ctx }jy/managerecord/check/5?_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" method="post">
			<input id="hid_term" type="hidden" name="term" value="${term }">
			<input id="hid_listType" type="hidden" name="listType" value="${listType }">
	    </form>
		<div class="managerecord_bottom_wrap">
			<div class="content_bottom">
				<div class="semester">
				    <c:if test="${empty term||term==0}">上学期</c:if> <c:if test="${term==1}">下学期</c:if>
				  <strong></strong>
				</div>
			</div>
			<div class="content_bottom1">
				<div id="listdiv">
				    <c:forEach items="${activityList.datalist}" var="activity">
						<div class="activity_tch" data-activityId="${activity.id}"> 
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
								<h3><span class="title" title="${activity.activityName}"><ui:sout value="${activity.activityName}" length="36" needEllipsis="true" ></ui:sout></span><c:if test="${activity.isShare }"><span class="end"></span><span class="y_share" style="right:10rem;"></span></c:if></h3>
								<div class="option">
									<div class="promoter"><strong></strong>发起人：<span>王大伟</span></div>
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
				<c:if test="${empty activityList.datalist}"><div class="content_k"><dl><dd></dd><dt>您还没有可查阅的集体备课，请稍后再来吧！</dt></dl></div></c:if>
			</div>
		 	<div class="referCourseware_content_box" >
				<div class="referCourseware_content_box1">
				   <c:forEach items="${checkMapList}" var="checkMap">
						<div class="referCourseware_content referCourseware_content_two">
							<p class="referCourseware_content_title">
								<span>课题名称：</span>
								<b data-resId="${checkMap.checkInfo.resId }">${checkMap.checkInfo.title }</b>
							</p>
							<c:forEach items="${checkMap.optionMapList }" var="optionMap">
							    <ul>
							    	<li>
							    		<span class="referCourseware_name">${optionMap.parent.username }：</span>
										<p class="referCourseware_text">&nbsp;${optionMap.parent.content }</p>
										<span class="referCourseware_time"><fmt:formatDate value="${optionMap.parent.crtTime }" pattern="yyyy-MM-dd"/></span>
										<div class="clear"></div>
									</li>
								</ul>
								<ul>
								   <!-- 回复 -->
								   <c:forEach items="${optionMap.childList }" var="child">
								        <jy:di key="${child.userId}" className="com.tmser.tr.uc.service.UserService" var="u"/>
										<li style="padding-left:5%;">
											<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
											<span class="referCourseware_name1">${child.username}：</span>
											<p class="referCourseware_text1">${child.content }</p>
											<span class="referCourseware_time"><fmt:formatDate value="${child.crtTime}" pattern="yyyy-MM-dd"/></span>
											<div class="clear"></div>
										</li>
									</c:forEach>
									</ul>
							</c:forEach>
						</div> 
					</c:forEach>
					<c:if test="${empty checkMapList}"><div class="content_k"><dl><dd></dd><dt>还没有查阅意见，请稍后再来吧！</dt></dl></div></c:if>
				</div>
			</div> 
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','checkactivity'],function($){	
	});  
</script>
</html>