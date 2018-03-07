<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="教学文章内容"></ui:htmlHeader>
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
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教学文章内容"></ui:tchTop>
	</div>
	
	<div class="check_teacher_wrap">
		<div class="check_teacher_wrap2"> 
			<h3 class="file_title"><ui:sout value="${thesis.thesisTitle}" escapeXml="true" length="50" needEllipsis="true"/></h3>
			<div class="file_info">
				<c:if test="${empty showType}">
					<div class="file_info_r">
						<span></span>
						<c:if test="${thesis.isShare==1}">
							分享时间：<fmt:formatDate value="${thesis.shareTime}" pattern="yyyy-MM-dd" />
						</c:if>
						<c:if test="${thesis.isShare!=1}">
							创建时间：<fmt:formatDate value="${thesis.crtDttm}" pattern="yyyy-MM-dd" />
						</c:if>
					</div>
				</c:if>
				<c:if test="${not empty showType}">
					<div class="file_info_l">
						<span></span>
						<jy:di key="${thesis.userId}" className="com.tmser.tr.uc.service.UserService" var="us"/>
						提交人：${us.name}
					</div>
					<div class="file_info_r">
						<span></span>
						提交日期：<fmt:formatDate value="${thesis.submitTime}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
			</div>
			<div class="word_plug_ins">
				<iframe id="view" src="jy/scanResFile?resId=${thesis.resId}" width="100%"	height="660px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
			</div>
			<div class="check_teacher_wrap2" style="margin-top:20px;">
				<c:if test="${not empty showType}">
					<iframe id="checkedBox" onload="setCwinHeight(this,false,100)"
						src="jy/teachingView/view/infoIndex?flags=false&resType=10&authorId=${thesis.crtId}&titleShow=true&resId=${thesis.id}&title=<ui:sout value='${thesis.thesisTitle}' encodingURL='true' escapeXml='true'></ui:sout>"
					style="border:none;width:100%;" height="100%" scrolling="no"></iframe>
				</c:if>
			<iframe id="commentBox" onload="setCwinHeight(this,false,600)"
					src="jy/teachingView/view/comment/list?authorId=${thesis.crtId}&flags=true&resType=10&titleShow=true&resId=${thesis.id}&title=<ui:sout value='${thesis.thesisTitle}' encodingURL='true' escapeXml='true'></ui:sout>"
					width="100%" height="100%" style="border: none;display:block;margin:0 auto;" scrolling="no" frameborder="no"></iframe>
			</div>
		</div> 
		<div class="clear"></div>
	</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
