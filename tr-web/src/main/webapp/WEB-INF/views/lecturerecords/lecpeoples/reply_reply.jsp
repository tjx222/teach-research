<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="check-bottom_2">
	<div class="check-bottom_2_left">
		<jy:di key="${childList.userId }" className="com.tmser.tr.uc.service.UserService" var="u"><!-- 查找用户 -->
 			 <ui:photo src="${u.photo}" width="40" height="40"></ui:photo><!-- 显示用户图片 -->
 		</jy:di> 
	</div>
	<div class="check-bottom_2_right">
		<div class="check-bottom_2_right_top" style="color:#474747;">
			${childList.username}:${childList.content}
		</div>
		<div class="check-bottom_2_right_botm" style="width: 742px;">
			<span>
				<fmt:formatDate value="${childList.crtDttm}"  pattern="yyyy-MM-dd"/>
			</span>
		</div>
	</div>
</div>
<div class="clear"></div>