<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent" id="ico_list"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="pageFormContent" layoutH="28" >
		<c:forEach items="${icolist}" var="ico">
			<a href="javascript:$.bringBack({icoId:'${ico.id }', img_src:'${basepath }/${ico.imgSrc}'})" title="${ico.description}"><img src="${basepath }/${ico.imgSrc}" title="${ico.description}" alt="${ico.description}" width="41" height="40" data-id="${ico.id }"/></a>
		</c:forEach>
		</div>
</div>
<script type="text/javascript">
</script>

