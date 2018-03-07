<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${empty lessonInfos}" >
	<div class="check-bottom_1_right_top" align="center" style="padding-top: 10px;">
		暂无备课资源!
	</div>
</c:if>
<c:if test="${not empty lessonInfos}" >
	<c:forEach items="${lessonInfos}" var="p" begin="0" end="4" step="1">
		<div class="cont_3_right_cont_tab">
			<span><a href="javascript:" onclick="opearDom(this,'_blank')" class="user_login" data-url="jy/schoolview/res/lessonres/viewlesson?lessPlan=${p.id}">${p.lessonName}</a></span>
			<b>【<jy:dic key="${p.subjectId}"></jy:dic>】【${p.bookShortname}】【<jy:dic key="${p.gradeId}"></jy:dic><jy:dic key="${p.fasciculeId}"></jy:dic>】</b>
			<strong>
				<jy:di key="${p.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
					${u.name}
				</jy:di>
			</strong>
			<div class="clear"></div>
			<ol>
				<li class="ja">教案(${p.jiaoanShareCount}个)</li>
				<li class="kj">课件(${p.kejianShareCount}个)</li>
				<li class="ja">反思(${p.fansiShareCount}个)</li>
			</ol>
			<u><fmt:formatDate value="${p.shareTime}"  pattern="yyyy-MM-dd"/></u>
		</div>
	</c:forEach>
</c:if>
