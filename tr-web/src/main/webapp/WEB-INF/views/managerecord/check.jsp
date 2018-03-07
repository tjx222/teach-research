<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
	<ui:htmlHeader title="查阅记录"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/record/css/list.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/index.css" media="screen">
	<script  type="text/javascript"  src="${ctxStatic }/modules/managerecord/js/highcharts.js"></script>
	<script type="text/javascript">
	 var term = '${term}';
	 var grade = '${grade}';
	 var subject = '${subject}';
	</script>
	<script type="text/javascript" src="${ctxStatic }/modules/managerecord/js/check.js"></script> 
</head>
<body>
<div class="wrapper">
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="查阅"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="cyjl"></jy:nav></h3>
	</div>
	<div class="clear"></div>
	<div class="managerCont">
		<div class="managerCont_title">
			<form action="">
				请选择：
				<input style="margin-top:-3px;" type="radio"  onclick ="checkMainUserList(this)" name="term"  <c:if test='${term==0}'>checked="checked"</c:if>   value="0">上学期
				<input style="margin-top:-3px;" type="radio"  onclick ="checkMainUserList(this)" name="term"   <c:if test='${term==1}'>checked="checked"</c:if>  value="1">下学期
			</form>
		</div>
		<div id="container"></div>
		<a id="bottom" name="bottom"></a>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
	</div>
</body>
</html>