<%@tag pageEncoding="UTF-8"%>
<%--  
icon 、iconId 或 iconIdentity 属性至少有一个 
宽和高只对图片类型有效
--%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ tag import="com.tmser.tr.utils.SpringContextHolder" %>
<%@ tag import="com.tmser.tr.manage.meta.service.IconService" %>

<%@ attribute name="icon" type="com.tmser.tr.manage.meta.bo.Icon" required="false" description="当前图标" %>
<%@ attribute name="iconId" type="java.lang.Integer" required="false" description="当前图标id" %>
<%@ attribute name="iconIdentity" type="java.lang.String" required="false" description="当前图标identity" %>
<%@ attribute name="width" type="java.lang.Integer" required="false" description="图标宽id" %>
<%@ attribute name="height" type="java.lang.Integer" required="false" description="图标高" %>
<%@ attribute name="ext" type="java.lang.String" required="false" description="文件后缀" %>
<%@ attribute name="name" type="java.lang.String" required="false" description="名称" %>
<%@ attribute name="title" type="java.lang.String" required="false" description="title" %>
<%!private IconService iconService;
   private String url_path = null;%>
<%
    if(iconService == null) {
    	iconService = SpringContextHolder.getBean(IconService.class);
    }
	if(url_path == null){
		java.util.Properties prop = SpringContextHolder.getBean("config");
    	url_path = prop.getProperty("front_web_url","");
	}
%>
<c:choose>
	 <c:when test="${not empty iconId}">
	 	<%
	 	jspContext.setAttribute("icon",iconService.findOne(iconId));
	 	%>
	 </c:when>
	 <c:when test="${not empty iconIdentity}">
	 	<%
	 	jspContext.setAttribute("icon",iconService.findByIdentity(iconIdentity));
	 	%>
	 </c:when>
</c:choose>
<c:if test="${not empty icon}">
<c:choose>
    <c:when test="${not empty icon.cssClass && 'sprite' != icon.type}">
        <i name="${name }" class="${icon.cssClass}" title="${title }"></i>
    </c:when>
    <c:when test="${not empty icon.imgSrc && 'sprite' == icon.type}">
        <i name="${name }" title="${title }" style="background:url(<%=url_path %>/${icon.imgSrc});background-position:${icon.top}px ${icon.left }px;width:${icon.width }px;height:${icon.height }px"></i>
    </c:when>
    <c:when test="${not empty icon.imgSrc}">
        <img name="${name }" src="<%=url_path %>/${icon.imgSrc}" title="${icon.description }" width="${empty width ?icon.width : width }" height="${empty height ?icon.height : height }" />
    </c:when>
</c:choose>
</c:if>
<c:if test="${empty icon }">
	<c:if test="${not empty ext}">
        <img name="${name }" src="<%=url_path %>/static/common/icon/base/${fn:toLowerCase(ext)}.png" ${not empty width ?'width="':''}${width }${not empty width ?'"':''} ${not empty height ?'height="':''}${height }${not empty height ?'"':''} title="${title }"/>
    </c:if>
    <c:if test="${empty ext}">
        <img name="${name }" src="" ${not empty width ?'width="':''}${width }${not empty width ?'"':''} ${not empty height ?'height="':''}${height }${not empty height ?'"':''} title="无图标" style="display:none"/>
    </c:if>
</c:if>
