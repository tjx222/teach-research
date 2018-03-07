<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="resid" type="java.lang.String" required="true" description="要下载资源id" %>
<%@ attribute name="filename" type="java.lang.String" required="false" description="自定义下载文件名称" %>
jy/manage/res/download/${resid}?filename=<ui:sout value="${filename}" escapeXml="true" encodingURL="true"></ui:sout>