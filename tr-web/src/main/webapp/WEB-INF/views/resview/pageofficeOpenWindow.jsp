<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="gb2312"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript">
<%
String url = (String)request.getAttribute("url");
if(url.indexOf("?") > 0){
  url = url +"&nocookie=true";
}else{
  url = url +"?nocookie=true";
}
url = PageOfficeLink.openWindow(request,request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+url,"width=1250px;height=800px;");
url = url.replaceAll("\\r\\n", "");
url = url.replaceAll("\\n", "");
%>
document.location.href='<%=url %>';
setTimeout("window.close();",4000);
</script>
</head>
</html>

