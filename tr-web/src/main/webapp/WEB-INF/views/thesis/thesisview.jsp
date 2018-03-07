<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看教学文章"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<script type="text/javascript"
	src="${ctxStatic }/modules/thesis/js/js.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(window).scroll(function(){
			$("#kongdiv").toggle();
		});
	});
</script>
</head>
<body>
	<div class="check_teacher_wrap">
		<jy:di key="${thesis.userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
		<input type="hidden" value="${thesis.resId}" id="view_resId">
		<input type="hidden" value="${thesis.thesisTitle}" id="view_title">
		<div class="check_teacher_wrap2"> 
			<h3 class="file_title"><ui:sout value="${thesis.thesisTitle}" escapeXml="true" length="50" needEllipsis="true"/></h3>
			<div class="file_sel">
				 
			</div>
			<div class="file_info" style="width:480px;float:left">
				<div class="file_info_r"> 
					学校：<jy:di key="${u.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">
					  	</jy:di>${org.name}&nbsp;&nbsp;|&nbsp;&nbsp;
					作者：${u.name}&nbsp;&nbsp;|&nbsp;&nbsp;
					分享时间：<fmt:formatDate value="${thesis.shareTime}" pattern="yyyy-MM-dd"/>
				</div> 
			</div>
			<div class="file_down_btn">
				<a href="<ui:download filename="${thesis.thesisTitle}" resid="${thesis.resId}"></ui:download>"><input type="button" class="download" /></a>
			</div>
			<div class="word_plug_ins">
				<iframe id="other_view" src="jy/scanResFile?to=true&resId=${thesis.resId}" width="100%"	height="595px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
			</div>
		</div> 
	</div>
		<div class="clear"></div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
