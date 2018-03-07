<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>集备教案浏览</title>
<style>
html,body{margin:0px; 
height:100%;
}
</style>
<script type="text/javascript">
var wordObj; //word控件实体
function AfterDocumentOpened() {
    wordObj = document.getElementById("PageOfficeCtrl1");
    wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
    wordObj.Titlebar = false;//不显示标题栏
}
</script>
</head>
<body>
    	<div style="height:100%;width:100%;overflow-y:hidden">
	    	<po:PageOfficeCtrl id="PageOfficeCtrl1" height="700">
	        </po:PageOfficeCtrl>
    	</div>
</body>
</html>