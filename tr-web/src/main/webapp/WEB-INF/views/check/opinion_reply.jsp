<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="check-bottom_2" id="cont_${reply.id }">
		<div class="check-bottom_2_left">
		<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
			<ui:photo src="${u.photo}" width="60" height="65"></ui:photo>
		 </jy:di> 
			</div>
		<div class="check-bottom_2_right">
		<div class="check-bottom_2_right_top1">
				${u.name }&nbsp;说：<c:out value="${reply.content }"></c:out>
			</div>
			<div class="check-bottom_2_right_botm">
			 <strong class="reply_rq" data-uname="${u.name }" data-opinionid="${reply.opinionId}" data-id="${reply.id }" data-index="${reply.id }">回复</strong>
		     <span>
		      <fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/>&nbsp;
		     </span>
	</div>
  </div>
</div>
<div class="clear"></div>
