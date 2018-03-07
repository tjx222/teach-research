<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<ui:htmlHeader title="教研平台-后台管理"></ui:htmlHeader>
</head>
<body>
<div style="width: 565px;  height: 210px;line-height: 22px;padding-top: 20px; background-color: #fff;overflow:auto;">
${resultStr }
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	if('${resultStr }'!=""){
		alert("执行完毕，详见反馈信息");
		parent.showResultInfo();
		//刷新后台
		if("${flag}"=='xxyh'){
			parent.parent.reload_sch_users();
		}else if("${flag}"=='qyyh'){
			parent.parent.reloadUnitUs();
		}
		$("#background,#progressBar",window.parent.document).hide();
	}
});
</script>
</html>