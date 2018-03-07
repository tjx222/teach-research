<%@tag pageEncoding="UTF-8"%>
<%--  
icon 、iconId 或 iconIdentity 属性至少有一个 
宽和高只对图片类型有效
--%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="width" type="java.lang.Integer" required="false" description="图标宽" %>
<%@ attribute name="height" type="java.lang.Integer" required="false" description="图标高" %>
<%@ attribute name="iconUrl" type="java.lang.String" required="true" description="原菜单图标路径" %>
	<c:if test="${not empty iconUrl}">
        <img src="${ctx }${fn:replace(iconUrl,'/icon','/icon_m')}" ${not empty width ?'width="':''}${width }${not empty width ?'"':''} ${not empty height ?'height="':''}${height }${not empty height ?'"':''} title="${title }"/>
    </c:if>
