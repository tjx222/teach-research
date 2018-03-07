<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:if test="${empty showType}">
	<c:set var="theme" value="其他反思"></c:set>
	<ui:htmlHeader title="其他反思"></ui:htmlHeader>
</c:if>
<c:if test="${not empty showType}">
	<c:set var="theme" value="查阅其他反思"></c:set>
	<ui:htmlHeader title="查阅其他反思"></ui:htmlHeader>
</c:if>
<ui:htmlHeader title="其他反思"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
	<div class="check_teacher_wrap">
		<div class="check_teacher_wrap2"> 
		<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
			<h3 class="file_title"><ui:sout value="${data.planName }" escapeXml="true" length="50" needEllipsis="true"/></h3>
			<div class="file_info">
				<c:if test="${not empty showType}">
					<div class="file_info_l">
						<span></span>
						提交人：${u.name}
					</div>
					<div class="file_info_r">
						<span></span>
						提交日期：<fmt:formatDate value="${data.submitTime}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
				<c:if test="${empty showType}">
					<div class="file_info_r">
						<fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
				<input type="button" class="download" data-name="${data.planName }" data-resId="${data.resId}" id="downloadBtn"/>
			</div>
			<div class="word_plug_ins">
				<iframe id="view" src="jy/scanResFile?to=true&resId=${data.resId}" width="100%"	height="660px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
			</div>
		</div>
		<div class="border"></div>
		<div class="check_teacher_wrap2"> 
			<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" scrolling="no"></iframe>
		<iframe id="commentBox" onload="setCwinHeight(this,false,100)" width="100%" height="100%" style="border: none;display:block;margin:0 auto;" scrolling="no" frameborder="no"></iframe>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
	    $(window).scroll(function (){
				$("#kongdiv").toggle();
			});
		/* var resid = $("li.see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?resId="+resid); */
		$("#checkedBox").attr("src","jy/teachingView/view/infoIndex?titleShow=true&flags=false&term=${data.termId}&gradeId=${data.gradeId}&subjectId=${data.subjectId}&resType=${type}&authorId=${data.userId}&resId=${data.planId}&title=<ui:sout value='${data.planName }' escapeXml='true' encodingURL='true'></ui:sout>");
		$("#commentBox").attr("src","jy/teachingView/view/comment/list?titleShow=true&authorId=${data.userId}&resType=${type}&flags=true&resId=${data.planId}");
		$("li.see_word_nav_1").click(function(){
			$this = $(this);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		});
		$("div.see_word_Annex dl").click(function(){
			 scanResFile($(this).attr("data-resId"));
		});
		$("#downloadBtn").click(function(){
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+$(this).attr("data-resId")+"?filename="+encodeURI($(this).attr("data-name")),"_self");
		});
	});
	</script>
</body>
</html>
