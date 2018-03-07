<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
	<div class="slide_tab1">
		<c:forEach items="${pictureNew_data}" var="pictureNew_data">
		<dl>
		<jy:ds var="picresource" key="${pictureNew_data.attachs}" className="com.tmser.tr.manage.resources.service.ResourcesService"></jy:ds>
			<dd>
			<a data-url="jy/schoolview/show/atlas?id=${pictureNew_data.id}"
			  onclick="opearDom(this,'_blank',false)" href="javascript:"><ui:photo src="${picresource.path}"/></a>
			</dd>
			<dt>${pictureNew_data.title}</dt>
		</dl>
	</c:forEach>
	</div>
</body>
</html>