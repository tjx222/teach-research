<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="check_content_center">
				<div class="class_name"><jy:dic key="${grade }"></jy:dic><jy:dic key="${subject }"></jy:dic></div>
				<div class="class_name_option">
					<span>撰写总数：<b id="zxCount"></b>课</span>    
					<span>提交总数：<b id="tjCount"></b>课</span>
					<span>已查阅总数：<b id="cyCount"></b>课</span>
				</div>
</div>
    <div class="check_content_bottom" id="check_c_b" style="top:13rem;">
		<div id="scroller">
			<div class="check_cont_wrap">
			<c:forEach var="item" items="${staticsMap }">
			<jy:di key="${item.key }" className="com.tmser.tr.uc.service.UserService" var="u"/>
			        <a  href="jy/check/lesson/${type }/tch/${u.id}?grade=${grade}&subject=${subject}">
					<div class="check_cont">
						<div class="check_cont_left">
							<ui:photo src="${u.photo }" />
							<span><ui:sout value="${u.name }" length="10" needEllipsis="true"></ui:sout></span>
						</div>
						<div class="check_cont_right">
							<span>撰写：${staticsMap[item.key][0]}课<c:set var="zxCount" value="${zxCount+ staticsMap[item.key][0]}" ></c:set></span>
							<span>提交：${staticsMap[item.key][1]}课<c:set var="tjCount" value="${tjCount+ staticsMap[item.key][1]}" ></c:set></span>
							<span>已查阅：${staticsMap[item.key][2]}课<c:set var="cyCount" value="${cyCount+ staticsMap[item.key][2]}" ></c:set></span>
						</div>
					</div></a>
			</c:forEach>
			</div>
			<c:if test="${empty staticsMap }">
			    <div class="content_k" style="margin:5rem auto;"><dl><dd></dd><dt>教师还没有提交${type == 0 ? '教案': type == 1? '课件':'反思' }哟，稍后再来查阅吧！</dt></dl></div>
			</c:if>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#zxCount").html("${empty zxCount ? 0:zxCount}");
	$("#tjCount").html("${empty tjCount ? 0:tjCount}");
	$("#cyCount").html("${empty cyCount ? 0:cyCount}");
});
</script>