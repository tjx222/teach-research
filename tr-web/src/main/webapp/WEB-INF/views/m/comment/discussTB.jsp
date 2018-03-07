<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="集体备课"></ui:mHtmlHeader> 
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity_cy.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div id="partake_discussion">
	<div>
	<c:if test="${empty discussList.datalist }">
		<div class="comment_k" style="margin-top: 8rem;">
			<dl>
				<dd></dd>
				<dt>暂时还没有讨论信息</dt>
			</dl>
		</div>
	</c:if>
	<div id="addmoredatas"> 
	<c:forEach items="${discussList.datalist  }" var="data" varStatus="coStu">
		<div class="partake_discussion">
			<jy:di key="${data.crtId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
			<div class="partake_discussion_left">
				<ui:photo src="${u.photo }" width="36" height="36"></ui:photo>
			</div>
			<div class="partake_discussion_right">
				<span>${u.name }：</span>
				<strong><fmt:formatDate value="${data.crtDttm  }" pattern="yyyy-MM-dd HH:mm"/></strong>
				<div class="discussion_content">
					<c:out value="${data.content }"></c:out>
				</div>
				<c:if test="${canReply }">
					<div class="discussion_reply" onclick="discussionreply(this)" divId="${data.id }" parentId="${data.id }" uname="${u.name }">回复</div>
				</c:if>
				<div class="clear"></div>
				<c:forEach items="${data.childList  }" var="dataTwo">
					<jy:di key="${dataTwo.crtId }" className="com.tmser.tr.uc.service.UserService" var="u2"/>
					<div class="reply_discussion">
						<div class="reply_discussion_left">
							<ui:photo src="${u2.photo }" width="36" height="36"></ui:photo>
						</div>
						<div class="reply_discussion_right">
							<span>${u2.name }：</span>
							<strong><fmt:formatDate value="${dataTwo.crtDttm  }" pattern="yyyy-MM-dd HH:mm"/></strong>
							<div class="discussion_content1">
								<c:out value="${dataTwo.content }"></c:out>
							</div>
							<c:if test="${canReply }">
								<div class="discussion_reply1" onclick="discussionreply(this)" divId="${dataTwo.id }" parentId="${data.id }" uname="${u2.name }">回复</div>
							</c:if>
						</div>
					</div>
				</c:forEach>
			</div> 
		</div> 
	</c:forEach>
	</div> 
	<div style="height:1rem;clear:both;">&nbsp;&nbsp;</div>
	<form name="pageForm" method="post">
		<ui:page url="${ctx}/jy/comment/discussIndexTB?activityId=${activityDiscuss.activityId }&typeId=${activityDiscuss.typeId }&canReply=${canReply }" data="${discussList}" dataType="true" callback="addmoredatas"/>
		<input type="hidden" class="currentPage" name="currentPage">
	</form> 
	<div style="height:1rem;clear:both;"></div>
	 
	<form id="jbdiscussform">
		<input type="hidden" id="activityId" name="activityId" value="${activityDiscuss.activityId }"> 
		<input type="hidden" name="typeId" value="${activityDiscuss.typeId}">
		<input type="hidden" id="discussLevel" name="discussLevel" value="1"> 
		<input type="hidden" id="parentId" name="parentId" value="0"> 
		<input type="hidden" name="content" id="content_hidden"/>
	</form>
	</div>
</div>
</body>
<script type="text/javascript">
	require(['jbdiscuss_tb'],function(){	
	}); 
	
</script>
</html>