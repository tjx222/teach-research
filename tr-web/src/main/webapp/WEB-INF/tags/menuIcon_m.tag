<%@tag pageEncoding="UTF-8"%>
<%--  
icon 、iconId 或 iconIdentity 属性至少有一个 
宽和高只对图片类型有效
--%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ tag import="com.tmser.tr.utils.SpringContextHolder" %>
<%@ tag import="com.tmser.tr.manage.meta.service.IconService" %>
<%@ attribute name="iconId" type="java.lang.Integer" required="false" description="当前图标id" %>
<%@ attribute name="width" type="java.lang.Integer" required="false" description="图标宽" %>
<%@ attribute name="height" type="java.lang.Integer" required="false" description="图标高" %>
<%@ attribute name="iconUrl" type="java.lang.String" required="false" description="原菜单图标路径" %>
<%!private IconService iconService;%>
<%
    if(iconService == null) {
    	iconService = SpringContextHolder.getBean(IconService.class);
    }
%>
<c:choose>
	 <c:when test="${not empty iconId}">
	 <jy:di key="${iconId }" className="com.tmser.tr.manage.meta.service.IconService" var="icon"></jy:di>
	 <c:set var="iconUrl" value="${not empty icon ? icon.imgSrc : iconUrl }"></c:set>
	 </c:when>
</c:choose>
	<c:if test="${not empty iconUrl}">
        <img src="${ctx }${fn:replace(iconUrl,'/icon','/icon_m')}" ${not empty width ?'width="':''}${width }${not empty width ?'"':''} ${not empty height ?'height="':''}${height }${not empty height ?'"':''} title="${not empty icon ? icon.description :'' }"/>
  </c:if>
