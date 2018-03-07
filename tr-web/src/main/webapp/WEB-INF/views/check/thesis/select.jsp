<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.tmser.tr.uc.SysRole"%>
<c:set var="BKZZ" value="<%=SysRole.BKZZ%>"/>
<c:set var="TEACHER" value="<%=SysRole.TEACHER%>"/>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅教学文章"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
<ui:require module="check/check_thesis/js"></ui:require>
</head>
<body>
 	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="查阅教学文章"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="check_thesis">
			<jy:param name="name" value="查阅教学文章"></jy:param>
			<jy:param name="gradeId" value="${grade}"></jy:param>
			<jy:param name="subjectId" value="${subject}"></jy:param>
			<jy:param name="sysRoleId" value="${sysRole}"></jy:param>
		</jy:nav>
	</div>
	<div class="check_select">
		<input type="hidden" value="${term}" id="tesis_term">
		<input type="hidden" value="${BKZZ.id}" id="bkzz_role">
		<input type="hidden" value="${TEACHER.id}" id="js_role">
		<input type="hidden" value="${flago}" id="flago_thesis">
		<div class="check_select_wrap">
			<div class="check_select_wrap_l">
				<dl>
					
					<a href="${ctx}/jy/check/thesis/select?flago=t"><dd class="check_teacher"></dd></a>
					<dt><a href="${ctx}/jy/check/thesis/select?flago=t">查阅教师</a></dt>
					
				</dl>
			</div>
			<div class="check_select_wrap_r">
				<dl>
					<a href="${ctx}/jy/check/thesis/select?flago=m"><dd class="check_controller"></dd></a>
					<dt><a href="${ctx}/jy/check/thesis/select?flago=m">查阅管理者</a></dt>
				</dl>
			</div>
		</div>
	</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','AmazeUI/amazeui.chosen.min','check_thesis'],function($){
	}); 
</script>
</body>
</html>