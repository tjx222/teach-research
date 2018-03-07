<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅听课记录"></ui:htmlHeader>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅听课记录"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：<jy:nav id="check_index"></jy:nav>
</div>
<div class="check_select">
	<div class="check_select_wrap">
		<div class="check_select_wrap_l">
			<dl>
				<dd class="check_teacher"></dd>
				<dt>查阅教师</dt>
			</dl>
		</div>
		<div class="check_select_wrap_r">
			<dl>
				<dd class="check_controller"></dd>
				<dt>查阅管理者</dt>
			</dl>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
<ui:require module="check/check_lecturerecords/js"></ui:require>
<script type="text/javascript">
require(['jquery','check_lecturerecords']);
</script>
</body>
</html>