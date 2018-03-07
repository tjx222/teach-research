<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<ui:htmlHeader title="同伴互助"></ui:htmlHeader>
<body>
<c:if test="${jfn:canView(res.ext) }">
<jsp:forward page="/jy/manage/res/download/${res.id}"></jsp:forward>
</c:if>
	<div style="width:930px;margin:10px auto;height:auto;">
		<a style="line-height:25px;display:block;height:25px;width:930px;" href="<ui:download filename="${res.name}" resid="${res.id}" > </ui:download>">
			<b >可以点击下载哟！</b><input type="button" class="download" value="下载" style="float:right;">
		</a>
		<div>
			<div style="width: 0px; height: 0px; display: none;" id="kongdiv"></div>
			<iframe id="view" src="jy/scanResFile?resId=${res.id}" width="930px" height="700px;" border="0" frameborder="0" style="border: none;"></iframe>
		</div>
	</div>
</body>
</html>