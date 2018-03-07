<%@ page import="org.springframework.cache.CacheManager" %>
<%@ page import="net.sf.ehcache.Statistics" %>
<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jspf" %>
<c:if test="${empty src }">
	前台地址接口地址未配置。
</c:if>
<c:if test="${not empty src }">
<iframe src="${src }" width="100%" layoutH="50"  frameborder="0" scrolling="yes" ></iframe>
</c:if>
