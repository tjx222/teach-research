<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅资源查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅教学文章"></ui:tchTop>
</div>
<div class="check_teacher_wrap">
	<jy:di key="${userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
	<input type="hidden" value="${findOne.resId}" id="view_resId">
	<input type="hidden" value="${findOne.thesisTitle}" id="view_title">
	<div class="check_teacher_wrap2"> 
		<h3 class="file_title"><ui:sout value="${findOne.thesisTitle}" escapeXml="true" length="50" needEllipsis="true"/></h3>
		<div class="file_sel">
			 
		</div>
		<div class="file_info" style="width:340px;float:left;margin-left:90px;">
			<div class="file_info_l">
				<span></span>
				提交人：${u.name}
			</div>
			<div class="file_info_r">
				<span></span>
				提交日期：<fmt:formatDate value="${findOne.submitTime}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="file_down_btn">
			<input type="button" class="download" />
		</div>
		<div class="word_plug_ins">
			<iframe id="other_view"  width="100%"	height="595px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
		</div>
	</div> 
</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
	<ui:require module="check/check_thesis/js"></ui:require>
	<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','AmazeUI/amazeui.chosen.min','check_thesis'],function($){
	}); 
	</script>
</body>
</html>
