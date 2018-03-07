<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add" href="${ctx}jy/back/schconfig/subject/toadd/custom?phaseId=${phaseId}&orgId=${orgId}" target="dialog" mask="true"><span>自定义学科</span></a></li>
		</ul>
	</div>
	<table style="width:566px;" class="table" layoutH="80">
		<thead>
			<tr>
				<th style="width: 18px;">
						<input type="checkbox" group="subjectAddIds" class="checkboxCtrl">
				</th>
				<th style="width: 240px; cursor: pointer;">ID</th>
				<th style="width: 280px; cursor: pointer;">名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${unAddedList }" var="meta">
				<tr target="grade_meta" rel="${meta.id }">
					<td style="width: 18px;"><input name="subjectAddIds" value="${meta.id }" type="checkbox"></td>
					<td style="width: 240px;">${meta.id }</td>
					<td style="width: 280px;">${meta.name }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="formBar">
		<ul>
			<li><a rel="subjectAddIds" target="selectedTodo" targetType="dialog" postType="string" href="${ctx }jy/back/schconfig/subject/save?phaseId=${phaseId}&orgId=${orgId}" class="button" callback="reloadSchXKAfterAdd"><span>保存</span></a></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>


<script type="text/javascript">
	function reloadSchXKAfterAdd(data){
		DWZ.ajaxDone(data);
		$.pdialog.closeCurrent();
		parent.reloadOrgXkglBox("${phaseId }");
		
	}
</script>
