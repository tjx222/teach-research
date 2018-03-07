<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table width="100%" class="table">
		<thead>
			<tr>
				<th style="width: 18px;">
						<input id="AllChosen" type="checkbox" group="bookAddIds" class="checkboxCtrl">
				</th>
				<th width="100">编号</th>
				<th width="160">教材名称</th>
				<th width="160">出版社</th>
				<th width="120">出版社简称</th>
				<th width="100">年级</th>
				<th width="80">版次</th>
				<th width="80">册别</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${booklist }"  var="book" varStatus="st">
				<tr data-nj="${book.gradeLevelId }" target="grade_meta" rel="${book.comId }">
					<td style="width: 18px;"><input id="tid_${book.comId }" name="bookAddIds" value="${book.comId }#${book.formatName}" type="checkbox"></td>
					<td>${book.comId  }</td>
					<td>${book.comName }</td>
					<td >${book.publisher }</td>
					<td><input id="shortName_${st.index }" onblur="parseValue(this,'${book.comId }')" class="saveCbsShortName" type="text" value="${book.formatName }" maxlength="10" /></td>
					<td>${book.gradeLevel }</td>
					<td>${book.bookEdtion }</td>
					<td>${book.fascicule }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<script type="text/javascript">
	
</script>
