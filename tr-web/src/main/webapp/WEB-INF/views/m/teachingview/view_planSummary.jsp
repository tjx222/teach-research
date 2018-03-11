<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:if test="${not empty searchVo.flago}">
	<ui:htmlHeader title="查阅计划总结内容"></ui:htmlHeader>
</c:if>
<c:if test="${empty searchVo.flago}">
	<ui:htmlHeader title="计划总结内容"></ui:htmlHeader>
</c:if>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<style type="text/css">
	.chosen-container{margin-top:40px;margin-right:28px;float:right;}
	.chosen-container-single .chosen-single span{ padding-top:0;color:#474747;font-size:14px;}
</style>
</head>
<body>
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="计划总结"></ui:tchTop>
	</div>
	
	<div class="check_teacher_wrap">
		<div class="check_teacher_wrap2">
			<h3 class="file_title">${ps.title }</h3>
			<div class="file_info">
				<c:if test="${not empty searchVo.flago}">
					<div class="file_info_l">
						<span></span>
						作者：${ps.editName}
					</div>
					<div class="file_info_r">
						<span></span>
						提交日期：<fmt:formatDate value="${ps.submitTime}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
				<c:if test="${empty searchVo.flago}">
					<div class="file_info_r">
						<span></span>
						撰写日期：<fmt:formatDate value="${ps.crtDttm}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
			</div>
			<div class="word_plug_ins">
				<iframe id="fileView" src="jy/scanResFile?resId=${ps.contentFileKey}"  width="100%" height="660px;" border="0" style="border: none;margin: 0 auto;display: block;">
				</iframe>
			</div>
		</div>
		<div class="border"></div>
		<div class="check_teacher_wrap2"> 
			<c:if test="${not empty searchVo.flago}">
				<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" height="650" scrolling="no" src="jy/teachingView/view/infoIndex?flags=false&resType=${(ps.category==1||ps.category==3)?8:9}&authorId=${ps.userId}&titleShow=true&resId=${ps.id}"></iframe>
			</c:if>
			<iframe id="commentBox" onload="setCwinHeight(this,false,600)"
					src="jy/teachingView/view/comment/list?titleShow=true&authorId=${ps.userId}&flags=true&resType=8&resId=${ps.id}&title=<ui:sout value='${ps.title}' encodingURL='true' escapeXml='true'></ui:sout>"
					width="100%" height="100%" style="border: none;display:block;margin:0 auto;" scrolling="no" frameborder="no"></iframe>
		</div>
</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>