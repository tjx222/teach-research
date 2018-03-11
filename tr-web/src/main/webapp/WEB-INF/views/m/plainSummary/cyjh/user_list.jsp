<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="check_content_center">
	<c:set var="contentTitle"><jy:dic key="${gradeId }"></jy:dic><jy:dic key="${subjectId }"></jy:dic></c:set>
	<div class="class_name">${empty contentTitle?'一年级语文':contentTitle}</div>
	<div class="class_name_option">
		<span>撰写总数：<b>${total.plainNum+total.summaryNum }</b>课</span>    
		<span>提交总数：<b>${total.plainSubmitNum+ total.summarySubmitNum}</b>课</span>
		<span>已查阅总数：<b>${total.plainCheckedNum+total.summaryCheckedNum }</b>课</span>
	</div>
</div>
<div class="check_content_bottom" id="check_c_b">
	<div id="scroller">		
		<div class="check_cont_wrap">
			<c:forEach var="item" items="${checkStatisticses}">
				<a href="./jy/planSummaryCheck/userSpace/${item.userSpaceId}/planSummarySingle?grade=${gradeId}&subject=${subjectId }">
					<div class="check_cont"> 
						<div class="check_cont_left">
							<ui:photo src="${empty item.user.photo?'static/common/images/default.jpg':item.user.photo}" />
							<span><ui:sout value="${item.user.name}" length="10" needEllipsis="true"></ui:sout></span>
						</div>
						<div class="check_cont_right">
							<span>撰写：${item.plainNum+item.summaryNum }课</span>
							<span>提交：${item.plainSubmitNum+item.summarySubmitNum }课</span>
							<span>已查阅：${item.plainCheckedNum+item.summaryCheckedNum }课</span>
						</div> 
					</div>
				</a>
			</c:forEach>
		</div>		
	</div>
</div>
