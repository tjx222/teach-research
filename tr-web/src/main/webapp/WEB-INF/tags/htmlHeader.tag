<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@attribute name="title" type="java.lang.String" required="false" %>
<%@include file="/WEB-INF/include/header.jspf"%>
<title>${title}</title>
<c:set var="__uri" value="${pageContext.request.contextPath }/WEB-INF/views/kpi"/>
<c:if test="${!fn:startsWith(pageContext.request.requestURI,__uri) }">
	<link rel="stylesheet" href="${ctxStatic }/common/css/index.css" media="screen">
</c:if>
<iframe id="hidenframe" name="hidenframe" style="display:none"></iframe>