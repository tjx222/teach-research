<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="推送资源"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/writelessonplan/css/writelessonplan.css" media="screen">
	<ui:require module="../m/writelessonplan/js"></ui:require> 
</head> 
<body>
<input type="hidden" id="resTypeid" value="${res.resType }">
<div class="push_resources_content">
	<ul>
		<li lessonId="${res.lessonId }" resType="0"><a href="javascript:void(0);" class="${res.resType==0?'push_act':'' }">教案</a></li>
		<li lessonId="${res.lessonId }" resType="1"><a href="javascript:void(0);" class="${res.resType==1?'push_act':'' }">课件</a></li>
		<li lessonId="${res.lessonId }" resType="2"><a href="javascript:void(0);" class="${res.resType==2?'push_act':'' }">习题</a></li>
		<li lessonId="${res.lessonId }" resType="3"><a href="javascript:void(0);" class="${res.resType==3?'push_act':'' }">素材</a></li>
	</ul>
	<div class="resources_wraper"> 
		<c:if test="${res.resType==0 }">
		<div class="resources_wraper_li resources_wraper_li1">
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_1">教案</div>
						<h3>${data.title }</h3>
						<p resId="${data.resId}"><ui:icon ext="${data.ext }" title="${data.title }"></ui:icon></p> 
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.title }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		<c:if test="${res.resType==1 }">
		<div class="resources_wraper_li resources_wraper_li2" >
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_2">课件</div>
						<h3>${data.title }</h3>
						<p resId="${data.resId}"><ui:icon ext="${data.ext }" title="${data.title }"></ui:icon></p> 
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.title }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		<c:if test="${res.resType==2 }">
		<div class="resources_wraper_li resources_wraper_li3" >
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_1">习题</div>
						<h3>${data.title }</h3>
						<p resId="${data.resId}"><ui:icon ext="${data.ext }" title="${data.title }"></ui:icon></p> 
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.title }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		<c:if test="${res.resType==3 }">
		<div class="resources_wraper_li resources_wraper_li4" >
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_1">素材</div>
						<h3>${data.title }</h3>
						<p resId="${data.resId}"><ui:icon ext="${data.ext }" title="${data.title }"></ui:icon></p> 
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.title }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'push_js'],function(){	
	});
</script>
</html>