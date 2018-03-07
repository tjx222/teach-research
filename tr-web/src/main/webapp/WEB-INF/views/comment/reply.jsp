<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jy:di key="${childList.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di><!-- 查找用户 -->
<div class="check-bottom_2" id="cont_${childList.id }">
	<div class="check-bottom_2_left">
 			 <ui:photo src="${u.photo}" width="60" height="65"></ui:photo><!-- 显示用户图片 -->
	</div>
	<div class="check-bottom_2_right">
		<div class="check-bottom_2_right_top">
			${u.nickname}&nbsp;说：${childList.content}
		</div>
		<div class="check-bottom_2_right_botm">
		 <strong class="reply_rq" data-authorid="${u.id}" data-uname="${u.nickname}" data-opinionid="${childList.opinionId}" data-id="${childList.id }" data-index="${childList.id }">回复</strong>
			<span>
 						<jy:di key="${u.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">
 				 			${org.name }
 			  			</jy:di>
				<fmt:formatDate value="${childList.crtDttm}"  pattern="yyyy-MM-dd"/>
			</span>
		</div>
	</div>
</div>
<div class="clear"></div>