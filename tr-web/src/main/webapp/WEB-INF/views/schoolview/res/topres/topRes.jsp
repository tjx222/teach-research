<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

	<div class="plume">
	<c:if test="${empty topRes1}">
		<center>暂无热点排行!</center>
	</c:if>
	<c:forEach items="${topRes1}" var="data1"  begin="0" end ="5" varStatus="status">   

			<div class="hot_spot" data-url="jy/schoolview/res/lessonres/view?lesid=${data1.resObj.planId}" onclick="opearDom(this,'_self')">
				<div class="hot_spot_l">
					<h4> <c:out value="${status.count}"/> </h4>
				</div>
				<div class="hot_spot_r">
					<h4>${data1.resObj.planName}</h4>
					<h5>浏览次数：<span>${data1.bc.count} </span>次</h5>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="plume">
		<c:forEach items="${topRes2}" var="data2"  begin="0" end ="5" varStatus="status">   

			<div class="hot_spot" data-url="jy/schoolview/res/lessonres/view?lesid=${data2.resObj.planId}" onclick="opearDom(this,'_self')">
				<div class="hot_spot_l">
					<h4> <c:out value="${5+status.count}"/> </h4>
				</div>
				<div class="hot_spot_r">
					<h4>${data2.resObj.planName}</h4>
					<h5>浏览次数：<span>${data2.bc.count} </span>次</h5>
				</div>
			</div>
		</c:forEach>
	</div>
