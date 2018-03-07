<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<c:if test="${mapUrl!=null}">
<div class="pic_hide">
	<div class="pic_hide_close"></div>
	<a href="###">
		<div class="pic_hide1">
			<!-- 广告大图 -->
			<ui:photo src="${ctx}${mapUrl.bigPath}" width="885" height="352"></ui:photo>
		</div>
	</a>
</div> 
<div class="pic_hide_small">
<!-- 广告小图 -->
<ui:photo src="${ctx}${mapUrl.littlePath}" width="61" height="146"></ui:photo>
</div> 
<div class="bg_box"></div>
 <!-- 广告首页信息 -->
</c:if>
 
</body>
</html>