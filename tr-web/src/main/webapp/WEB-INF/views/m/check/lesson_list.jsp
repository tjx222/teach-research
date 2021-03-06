<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅意见"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/comment/css/comment.css" media="screen">
	<ui:require module="../m/comment/js"></ui:require>
</head>
<body> 
<div class="opinions_comment_content" id="wrap3">
	<div id="scroller">
		<c:if test="${empty data.datalist }">
			<div class="comment_k" style="margin-top: 10rem;">
				<dl>
					<dd></dd>
					<dt>暂时还没有查阅信息哦</dt>
				</dl>
			</div>
		</c:if>
		<div id="addmoredatas">
		<c:forEach items="${data.datalist }" var="co">
			<div class="consult_opinion">
				<jy:di key="${co.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
				<div class="consult_opinion_left">
					<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
				</div>
				<div class="consult_opinion_right">
					<span>${u.name }：</span>
					<strong><fmt:formatDate value="${co.crtTime }" pattern="yyyy-MM-dd"/></strong>
					<div class="comment_content1">
						<c:out value="${co.content }"></c:out>
					</div>
					<div id="reply_${co.id }" class="reply" onclick="checkreply(this)" divId="div_${co.id }" opinionId="${co.id }" parentId="${co.id }" uname="${u.name }">回复</div>
					<div class="clear"></div>
					<c:forEach items="${coMap[co.id]}" var="reply">
						<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
						<div id="reply_${reply.id }" class="reply_opinion">
							<div class="reply_opinion_left">
								<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
							</div>
							<div class="reply_opinion_right">
								<span>${u.name }：</span>
								<strong><fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/></strong>
								<div class="reply_content">
									<c:out value="${reply.content }"></c:out>
								</div>
								<div class="reply1"  onclick="checkreply(this)" divId="div_${reply.id }" opinionId="${co.id }" parentId="${reply.id }" uname="${u.name }">回复</div>
							</div>
						</div>
					</c:forEach>
				</div> 
			</div>
		</c:forEach>
		</div>
		<form  name="pageForm" method="post">
			<ui:page url="${ctx}jy/check/checklist?resId=${model.resId }&flags=${model.flags }&resType=${model.resType }" data="${data}" dataType="true" callback="addmoredatas"/>
			<input type="hidden" class="currentPage" name="page.currentPage">
		</form> 
		<div style="width:100%;height:1.5rem;"></div>
	</div>
</div>
<form id="check_comment_form" method="post">
	<input type="hidden" name="content" id="content" value=""/>
	<input type="hidden" name="parentId" id="parentId" value=""/>
	<input type="hidden" name="opinionId" id="opinionId" value=""/>
</form>
</body>
<script type="text/javascript"> 
require(["zepto",'js'],function($){	
}); 
</script>
</html>