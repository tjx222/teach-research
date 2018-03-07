<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${bignews ne null}">
	<c:forEach items="${bignews}" var="news">
		<div class="school_new" data-url="jy/schoolview/show/school_survey?showId=${news.id}" onclick="opearDom(this,'_blank',false)">
			<div class="school_new_l" >
				<h4>
					<fmt:formatDate value="${news.crtDttm }" pattern="dd" />
				</h4>
				<h5>
					<fmt:formatDate value="${news.crtDttm }" pattern="yyyy-MM" />
				</h5>
			</div>
			<div class="school_new_r">${news.title}</div>
		</div>
	</c:forEach>
</c:if>
<c:if test="${empty bignews}">
	<div class="school_new_r">暂无学校要闻！</div>
</c:if>



