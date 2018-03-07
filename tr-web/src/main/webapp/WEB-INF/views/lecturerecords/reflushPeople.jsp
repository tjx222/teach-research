<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${userList}" var="user" varStatus="c">
	<dl onclick="writeLectureRecordsInnerInput(${user.id});">
		<dd>
			<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
			<ui:photo src="${u.photo}"></ui:photo>
		</dd>
		<dt>
			<h2>${user.username}</h2>
		</dt>
	</dl>
</c:forEach>