<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="data" type="com.tmser.tr.manage.meta.vo.BookLessonVo" required="true" description="章节"%>
<%@ attribute name="selectedid" type="java.lang.String" required="false" description="选中的id"%>
<%@ attribute name="space" type="java.lang.Integer" required="false" description="缩进"%>
<c:if test="${empty space}"><c:set var="space" value="0"/></c:if>
<c:if test="${data.isLeaf }">
	<option id="${data.lessonId }" ${data.lessonId eq selectedid?'selected="selected"':'' } value="${data.lessonId }"><c:forEach begin="0" end="${space }" var="i">&nbsp;</c:forEach>${data.lessonName }</option>
</c:if>
<c:if test="${!data.isLeaf }">
	<option disabled="disabled" value="${data.lessonId }"><c:forEach begin="0" end="${space }" var="i">&nbsp;</c:forEach>${data.lessonName }</option>
	<c:if test="${not empty space}">
	    <c:set var="space" value="${space + 1 }"/>
	</c:if>
	<c:forEach var="bc" items="${data.bookLessons }">
		<ui:bookChapter data="${bc }" space="${space}" selectedid='${selectedid }'></ui:bookChapter>
	</c:forEach>
</c:if>