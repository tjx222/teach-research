<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<form id="detail_form">
		<input type="hidden" name="gradeId" id="f_grade" value="${grade}">
		<input type="hidden" name="subjectId" id="f_subject" value="${subject}">
		<input type="hidden" name="sysRoleId" id="f_sysRoleId" value="${sysRoleId}">
		<input type="hidden" name="schoolTerm" id="f_term">
		<input type="hidden" name="flago" id="f_flago">
		<input type="hidden" name="flags" id="f_flags">
	</form>
<div class="check_info">
	<div class="grade_subject">
		<c:if test="${thesis.flago=='m'}">${spaceName}</c:if>
		<c:if test="${thesis.flago=='t'}"><jy:dic key="${grade }"></jy:dic><jy:dic key="${subject }"></jy:dic></c:if>
	</div>
	<div class="grade_subject">撰写总数：<b id="zxCount"></b>课</div>
	<div class="grade_subject">提交总数：<b id="tjCount"></b>课</div>
	<div class="grade_subject">已查阅总数：<b id="cyCount"></b>课</div>
</div>
<c:forEach var="item" items="${staticsMap }">
	<div class="teacher_info">
		<div class="teacher_info_top">
			<a href="jy/check/thesis/tch/${item.key}?schoolTerm=${thesis.schoolTerm}&flago=${thesis.flago}&flags=${thesis.flags}&gradeId=${grade}&subjectId=${subject}&sysRoleId=${sysRoleId}">
			<div class="teacher_info_top_bg" data-userId="${item.key}" data-grade="${grade}" data-subject="${subject}" data-sysRoleId="${sysRoleId}" data-term="${thesis.schoolTerm}" data-flago="${thesis.flago }" data-flags="${thesis.flags }">
				<ol class="tea_info">
					<li><span></span>撰写：${staticsMap[item.key]['write']}课<c:set var="zxCount" value="${zxCount+ staticsMap[item.key]['write']}" ></c:set></li>
					<li><span></span>提交：${staticsMap[item.key]['submit']}课<c:set var="tjCount" value="${tjCount+ staticsMap[item.key]['submit']}" ></c:set></li>
					<li><span></span>已查阅：${staticsMap[item.key]['check']}课<c:set var="cyCount" value="${cyCount+ staticsMap[item.key]['check']}" ></c:set></li>
				</ol>
			</div>
			<jy:di key="${item.key}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
			<ui:photo src="${u.photo}" width="74" height="74"></ui:photo>
			</a>
		</div>
		<div class="teacher_info_bottom">${u.name}</div>
	</div>
</c:forEach>
<c:if test="${empty staticsMap }">
	<div class="cont_empty">
	    <div class="cont_empty_img"></div>
	    <div class="cont_empty_words">没有资源哟！</div> 
	</div>
</c:if>
<script type="text/javascript">
$(document).ready(function(){
	$("#zxCount").html("${empty zxCount ? 0:zxCount}");
	$("#tjCount").html("${empty tjCount ? 0:tjCount}");
	$("#cyCount").html("${empty cyCount ? 0:cyCount}");
	$(".teacher_info_top_bg").click(function(){
		var userId = $(this).attr("data-userId");
		$("#f_term").val($(this).attr("data-term"));
		$("#f_flago").val($(this).attr("data-flago"));
		$("#f_flags").val($(this).attr("data-flags"));
		$("#detail_form").attr("action",_WEB_CONTEXT_ + "/jy/check/thesis/tch/"+userId);
		$("#detail_form").attr("method","post");
		$("#detail_form").submit();
	})
});
</script>