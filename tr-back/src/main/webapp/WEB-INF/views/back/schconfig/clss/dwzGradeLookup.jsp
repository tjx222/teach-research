<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<ui:relation var="grades" type="xdToNj" id="${phase }" orgId="${orgId }"></ui:relation>
	<select name="classes[#index#].gradeId" >
		<c:forEach items="${grades }" var="g">
			<option value="${g.id }" ${grade == g.id ? 'selected':'' }>${g.name }</option>
		</c:forEach>
	</select>

