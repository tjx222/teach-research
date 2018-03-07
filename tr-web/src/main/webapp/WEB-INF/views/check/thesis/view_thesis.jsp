<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="查阅资源查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript">
var sessionId = "${sessionId}";
</script>
</head>
<body>
<div class="jyyl_nav">
	<jy:di key="${userId}" className="com.tmser.tr.uc.service.UserService" var="us"/>
</div>
<div class="check_teacher_wrap">
	<form id="view_form">
		<input type="hidden" name="ids" value="${thesisIds}" id="thesis_ids">
		<input type="hidden" name="id" value="${findOne.id}" id="thesisId">
		<input type="hidden" name="schoolTerm" value="${findOne.schoolTerm}" id="view_schoolTerm">
		<input type="hidden" name="flago" value="${thesis.flago}" id="view_flago">
		<input type="hidden" name="flags" value="${thesis.flags}" id="view_flags">
		<input type="hidden" value="${findOne.resId}" id="view_resId">
		<input type="hidden" value="${findOne.thesisTitle}" id="view_title">
		<input type="hidden" value="${userId}" id="view_userId">
		<input type="hidden" value="${idIndex}" id="view_idindex">
		<input type="hidden" name="gradeId" id="f_grade" value="${paramSpace.gradeId}">
		<input type="hidden" name="subjectId" id="f_subject" value="${paramSpace.subjectId}">
		<input type="hidden" name="sysRoleId" id="f_sysRoleId" value="${paramSpace.sysRoleId}">
	</form>
	<div class="check_teacher_wrap2"> 
		<h3 class="file_title"><ui:sout value="${findOne.thesisTitle}" escapeXml="true" length="50" needEllipsis="true"/></h3>
		<div class="file_sel">
			<div class="anti_plagiarism">
				<label for="">反抄袭：</label>
				<div class="ser">
					<input type="text" class="ser_txt" id="searchFcx"/>
					<input type="button" class="ser_button" />
				</div>
			</div>
		</div>
		<div class="file_info" style="width:340px;float:left;margin-left:90px;">
			<div class="file_info_l">
				<span></span>
				提交人：${us.name}
			</div>
			<div class="file_info_r">
				<span></span>
				提交日期：<fmt:formatDate value="${findOne.submitTime}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="refer">
		    <c:choose>
		    	<c:when test="${not isCheck}"><b></b></c:when>
		    	<c:otherwise><span></span></c:otherwise>
		    </c:choose>
		</div>
		<div class="file_down_btn">
			<input type="button" class="download" />
		</div>
		<div class="word_plug_ins">
			<iframe id="view"  width="100%"	height="680px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>
	<div class="border"></div>
	<div class="check_teacher_wrap2"> 
		<div class="same_submission">
			<h3 class="same_submission_h3"><span></span>他们也提交了同类文章：</h3>
				<div class="same_submission_cont">
					<c:if test="${not empty thesisData}">
					<ol>
						<c:forEach items="${thesisData}" var="thesis">
							<jy:di key="${thesis.userId}" className="com.tmser.tr.uc.service.UserService" var="u"/>
							<li title="[${thesis.thesisType}]${thesis.thesisTitle }"><b></b><a href="${ctx}/jy/check/thesis/tch/other?id=${findOne.id}" target="_blank">[${thesis.thesisType}]${thesis.thesisTitle }</a><span>${u.name}</span></li>
						</c:forEach>
					</ol>
					</c:if>
					<c:if test="${empty thesisData}">
						<div>
							<div class="check_k"></div>
							<div style="color:#ccc;font-size:15px;font-weight:bold;line-height:20px;height:30px;text-align:center;">其他人还没有提交呦！</div>
						</div>
					</c:if>
				</div>
		</div>
		<iframe id="checkedBox" src="jy/check/lookCheckOption?flags=true&term=${findOne.schoolTerm}&title=<ui:sout value='${findOne.thesisTitle}' encodingURL='true' escapeXml='true'></ui:sout>&resType=10&authorId=${findOne.userId}&resId=${findOne.id}"
		 onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#view").attr("src","jy/scanResFile;jsessionid="+sessionId+"?to=true&resId="+$("#view_resId").val());
});
</script>
	<ui:require module="check/check_thesis/js"></ui:require>
	<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','AmazeUI/amazeui.chosen.min','check_thesis'],function($){}); 
	</script>
	
</body>
</html>
