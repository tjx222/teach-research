<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="clear"></div>
<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
<div class="reply_opinion" style="width:38rem;">
	<div class="reply_opinion_left">
		<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
<!-- 								<img src="${ctxStatic }/m/comment/images/img1.png" alt=""> -->
	</div>
	<div class="reply_opinion_right">
		<span>${u.name }：</span>
		<strong><fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/></strong>
		<div class="reply_content" style="width:34rem;">
			<c:out value="${reply.content }"></c:out>
		</div>
		<div class="reply1" divId="div_${reply.id }" opinionId="${co.id }" parentId="${reply.id }" uname="${u.name }">回复</div>
	</div>
</div>
