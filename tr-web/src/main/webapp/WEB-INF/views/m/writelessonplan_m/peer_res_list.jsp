<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="同伴资源"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/writelessonplan/css/writelessonplan.css" media="screen">
	<ui:require module="../m/writelessonplan/js"></ui:require> 
</head> 
<body>
<input type="hidden" id="planType" value="${planType }">
<div class="push_resources_content">
	<ul>
		<li lessonId="${lessonId }" spaceId="${spaceId }" planType="0"><a href="javascript:void(0);" class="${planType==0?'push_act':'' }">教案</a></li>
		<li lessonId="${lessonId }" spaceId="${spaceId }" planType="1"><a href="javascript:void(0);" class="${planType==1?'push_act':'' }">课件</a></li>
		<li lessonId="${lessonId }" spaceId="${spaceId }" planType="2"><a href="javascript:void(0);" class="${planType==2?'push_act':'' }">反思</a></li>
	</ul>
	<div class="resources_wrap"> 
		<c:if test="${planType==0 }">
		<div class="resources_wrap_li resources_wrap_li1">
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_1">教案</div>
						<h3>${data.planName }</h3>
						<jy:ds key="${data.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<p resId="${data.resId}"><ui:icon ext="${res.ext }" title="${data.planName }"></ui:icon></p> 
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.planName }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		<c:if test="${planType==1 }">
		<div class="resources_wrap_li resources_wrap_li2" >
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_2">课件</div>
						<h3>${data.planName }</h3>
						<jy:ds key="${data.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<p resId="${data.resId}"><ui:icon ext="${res.ext }" title="${data.planName }"></ui:icon></p>
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.planName }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		<c:if test="${planType==2 }">
		<div class="resources_wrap_li resources_wrap_li3" >
			<div>
				<c:forEach items="${datalist }" var="data">
					<div class="courseware_ppt">
						<div class="courseware_img_3">反思</div>
						<h3>${data.planName }</h3>
						<jy:ds key="${data.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<p resId="${data.resId}"><ui:icon ext="${res.ext }" title="${data.planName }"></ui:icon></p>
						<div class="you"></div>
						<a class="courseware_img_down" title="下载" href="<ui:download resid="${data.resId}" filename="${data.planName }"></ui:download>"></a>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'peer_js'],function(){	
	});
</script>
</html>