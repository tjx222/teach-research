<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${ctx}" />
<title>查看资源</title>
<style>
html,body{margin:0px; 
height:100%;
}
</style>
<script type="text/javascript">
function AfterDocumentOpened(){
	 document.getElementById("PDFCtrl1").SetPageFit(3);
	 document.getElementById("PDFCtrl1").BookmarksVisible = false;
}
</script>
</head>
<body>
    	<div style="height:100%;width:100%;overflow-y:hidden">
	    	<po:PDFCtrl id="PDFCtrl1" />
    	</div>
</body>
</html>