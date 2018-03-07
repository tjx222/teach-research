<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="src" type="java.lang.String" required="true" description="要显示的图像地址" %>
<%@ attribute name="style" type="java.lang.String" required="false" description="图像属性" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="图像宽" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="图像高" %>
<%@ attribute name="alt" type="java.lang.String" required="false" description="图像alt" %>
<%@ attribute name="defaultSrc" type="java.lang.String" required="false" description="默认图像地址" %>
<c:if test="${empty src && empty defaultSrc }">
	<c:set var="src" value="static/common/images/default.jpg"/>
</c:if>
<c:if test="${empty src && not empty defaultSrc }">
	<c:set var="src" value="${defaultSrc}"/>
</c:if>
<img src ="${src }" height="${height }" width="${width }"  alt="${alt }"/>