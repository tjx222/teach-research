<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="check_info">
	<div class="grade_subject">
		<jy:dic key="${grade }"></jy:dic><jy:dic key="${subject }"></jy:dic>
	</div>
	<div class="grade_subject">撰写总数：<b id="zpxCount"></b>篇/<b id="zxCount"></b>课</div>
	<div class="grade_subject">提交总数：<c:if test="${type == 0}"><b id="tjpCount"></b>篇/</c:if><b id="tjCount"></b>课</div>
	<div class="grade_subject">已查阅总数：<c:if test="${type == 0}"><b id="cypCount"></b>篇/</c:if><b id="cyCount"></b>课</div>
</div>
<c:forEach var="item" items="${staticsMap }">
	<jy:di key="${item.key}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
	<div class="teacher_info">
		<div class="teacher_info_top">
			<a href="jy/check/lesson/${type }/tch/${u.id}?grade=${grade}&subject=${subject}">
			<div class="teacher_info_top_bg">
				<ol class="tea_info">
					<li><span></span>撰写:${staticsMap[item.key][3]}篇/${staticsMap[item.key][0]}课<c:set var="zxCount" value="${zxCount+ staticsMap[item.key][0]}" ></c:set><c:set var="zpxCount" value="${zpxCount+ staticsMap[item.key][3]}" ></c:set></li>
					<li><span></span>提交:<c:if test="${type == 0}">${staticsMap[item.key][4]}篇/</c:if>${staticsMap[item.key][1]}课<c:set var="tjCount" value="${tjCount+ staticsMap[item.key][1]}" ></c:set><c:set var="tjpCount" value="${tjpCount+ staticsMap[item.key][4]}" ></c:set></li>
					<li><span></span>已查阅:<c:if test="${type == 0}">${staticsMap[item.key][5]}篇/</c:if>${staticsMap[item.key][2]}课<c:set var="cyCount" value="${cyCount+ staticsMap[item.key][2]}" ></c:set><c:set var="cypCount" value="${cypCount+ staticsMap[item.key][5]}" ></c:set></li>
				</ol>
			</div>
			<ui:photo src="${u.photo}" width="74" height="74"></ui:photo>
			</a>
		</div>
		<div class="teacher_info_bottom">${u.name}</div>
	</div>
</c:forEach>
<c:if test="${empty staticsMap }">
	<div class="cont_empty">
	    <div class="cont_empty_img"></div>
	    <div class="cont_empty_words">教师还没有提交${type == 0 ? '教案': type == 1? '课件':'反思' }哟，稍后再来查阅吧！</div> 
	</div>
</c:if>
<script type="text/javascript">
$(document).ready(function(){
	$("#zxCount").html("${empty zxCount ? 0:zxCount}");
	$("#zpxCount").html("${empty zpxCount ? 0:zpxCount}");
	$("#tjCount").html("${empty tjCount ? 0:tjCount}");
	$("#tjpCount").html("${empty tjpCount ? 0:tjpCount}");
	$("#cyCount").html("${empty cyCount ? 0:cyCount}");
	$("#cypCount").html("${empty cypCount ? 0:cypCount}");
});
</script>