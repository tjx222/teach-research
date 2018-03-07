<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<select class="combox" name="rcdVo[#index#].resType">
				<c:forEach items="${resourcesType }" var="resType">
					<option value="${resType.id }">${resType.name} </option>
				</c:forEach>
</select>
