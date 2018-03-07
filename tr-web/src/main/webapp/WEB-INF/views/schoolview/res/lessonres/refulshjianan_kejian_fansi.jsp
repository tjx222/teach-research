<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${empty lessonPlans}" >
	<div class="check-bottom_1_right_top" align="center" style="padding-top: 10px;">
		暂无备课资源!
	</div>
</c:if>

<c:if test="${not empty lessonPlans}" >
	<c:forEach items="${lessonPlans}" var="p" begin="0" end="4" step="1">
		<div class="cont_3_right_cont1_tab" style="padding-bottom:0;">
			
			<div class="c_3_r_c_t_c" style="width:602px;">
				<h5>
					<c:choose>
						<c:when test="${p.planType=='1'}"><!-- 判断资源类型所显示的图片不一样 -->
							<img src="${ctxStatic}/common/icon/base/ppt.png" alt="">
						</c:when>
						<c:otherwise>
							<img src="${ctxStatic}/common/icon/base/word.png" alt="">
						</c:otherwise>
					</c:choose>
					<span><a href="javascript:" onclick="opearDom(this,'_blank')" class="user_login" data-url="jy/schoolview/res/lessonres/view?lesid=${p.planId}">${p.planName}</a></span>
					<strong style="line-height:35px;">
						<jy:di key="${p.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
							${u.name}
						</jy:di>
					</strong>
				</h5>
				<h5><b>【<jy:dic key="${p.subjectId}"></jy:dic>】【${p.bookShortname}】【<jy:dic key="${p.gradeId}"></jy:dic><jy:dic key="${p.fasciculeId}"></jy:dic>】</b><u style="line-height:35px;"><fmt:formatDate value="${p.shareTime}"  pattern="yyyy-MM-dd"/></u></h5>
			</div>
		</div>
	</c:forEach>
</c:if>