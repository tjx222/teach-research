<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<dl>
		<c:if test="${overviews ne null}">
			<c:forEach items="${overviews}" var="overview">
				<dd>
					<a
						data-url="jy/schoolview/show/school_survey?showId=${overview.id}" onclick="opearDom(this,'_blank',false)" href="javascript:" >
						<ui:photo src="${overview.images}" width="100%" height="100%" defaultSrc="${ctxStatic}/modules/schoolview/images/school/nopic.png" />
					</a>
				</dd>
				<dt>
					<a
						data-url="jy/schoolview/show/school_survey?showId=${overview.id}" onclick="opearDom(this,'_blank',false)" href="javascript:" >
						${overview.title}
					</a>
				</dt>
			</c:forEach>
		</c:if>
		<c:if test="${empty overviews}">
			<dd>
				<ui:photo src="" width="100%" height="100%" defaultSrc="${ctxStatic}/modules/schoolview/images/school/nopic.png" />
			</dd>
		</c:if>
	</dl>
