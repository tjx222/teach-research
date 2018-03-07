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
#down_load_a{
  line-height: 30px; 
  height: 30px;
  width: 70px;
  background: #ff8400;
  text-align: center;
  display: block;
  text-decoration: blink;
  font-size: 16px;
  color: #fff;
  float: right;
  margin-right: 10px;
  margin-bottom:10px;
  font-weight: bold;}
</style>
</head>
<body>
<div style="width:100%;margin:10px auto;height:10px;"> 
    <c:if test="${empty param.orgId || _CURRENT_SPACE_.orgId==param.orgId }">
		<a id="down_load_a" style="display:none" href="<ui:download resid="${param.resId}" > </ui:download>">
			下载
		</a>
	</c:if>
</div>
    	<div style="height:100%;width:100%;overflow-y:hidden">
	    	<po:PageOfficeCtrl id="PageOfficeCtrl1" height="700" width='930'>
	        </po:PageOfficeCtrl>
    	</div>
<script  type="text/javascript">
if(top.location.href == self.location.href){
	document.getElementById("down_load_a").style.display="block";
}
</script>
</body>
</html>