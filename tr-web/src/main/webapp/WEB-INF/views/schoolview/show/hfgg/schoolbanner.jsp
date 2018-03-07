<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<c:if test="${not empty imgUrls}">
	<div class="top4_banner">
		<div class='recommend'>
			<div class='recommend-content'>
				<div class="container">
					<div id="slides">
						<c:forEach var="data" items="${imgUrls}">
							<div class='clearfix'
								<c:if test="${data.key != 0}">style="display: none"</c:if>>
								<ui:photo src="${ctx}${data.value}" width="100%" height="161"></ui:photo>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
</html>

