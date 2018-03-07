<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="校际教研"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/schoolactivity/css/schoolactivity.css" media="screen" />
	<ui:require module="../m/schoolactivity/js"></ui:require>	
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>教研进度表
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="content_bottom2">
			<div>
				<div class="content_bottom_width">
			        <c:if test="${!empty tlist.datalist }">
			             <c:forEach items="${tlist.datalist }" var="data">
				             <div class="courseware_ppt" >
								<div class="courseware_img_0"></div>
								<h4><jy:dic key="${data.gradeId}"></jy:dic><jy:dic key="${data.subjectId}"></jy:dic></h4>
								<h4 title="${data.name}"><ui:sout value="${data.name}" length="18" needEllipsis="true"></ui:sout></h4>
								<p data-id="${data.id}">
								<img src="${ctxStatic }/m/activity/images/word_d.png" />
								</p>
								<a	href="<ui:download filename="${data.name}" resid="${data.resId}"></ui:download>"><div class="courseware_img_down" title="下载"></div></a> 
							</div> 
						</c:forEach>
			        </c:if>
				</div>
				<c:if test="${empty tlist.datalist }"><div class="content_k" style="margin:9rem auto;"><dl><dd></dd><dt>教师还没有上传教研进度表哟，稍后再来查看吧！</dt></dl></div></c:if>
				<div style="height:2rem;clear:both;"></div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','teach'],function($){	
	});  
</script>
</html>