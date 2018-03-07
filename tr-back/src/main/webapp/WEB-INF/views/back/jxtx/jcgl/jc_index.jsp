<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }jy/back/jxtx/add_jc?phaseId=${book.phaseId}&subjectId=${book.subjectId}&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}" target="dialog" mask="true" ><span>同步教材</span></a></li>
			<li><a class="delete" href="${ctx }jy/back/jxtx/delete_jc?id={sid_obj}" target="ajaxTodo" title="确定要删除吗?" callback="del_reload_Jc_Box" ><span>删除</span></a></li>
			<li><a class="edit" href="${ctx }jy/back/jxtx/edit_jc?id={sid_obj}" target="dialog" mask="true" ><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx }jy/back/jxtx/edit_gl?id={sid_obj}" height="250" target="dialog" mask="true" ><span>关联</span></a></li>
			<li><a class="edit" rel="jiaocai_box" callback="del_reload_Jc_Box" href="${ctx }jy/back/jxtx/auto_gl?phaseId=${book.phaseId}&subjectId=${book.subjectId}&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}" target="ajaxTodo" mask="true" ><span>自动关联</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="104" rel="jbsxBox">
		<thead>
			<tr width="100%">
				<th orderField="number" class="asc">编号</th>
				<th width="20%">教材名称</th>
				<th width="15%">教材简称</th>
				<th width="10%">年级</th>
				<th width="10%">版次</th>
				<th width="8%">册别</th>
				<th width="10%">关联状态</th>
				<th width="8%">排序</th>
				<th width="15%">创建时间</th>
			</tr>
		</thead>
		<tbody style="overflow-y: auto; ">
			<c:forEach items="${booklist }" var="book">
				<tr target="sid_obj" rel="${book.id }">
					<td>${book.comId }</td>
					<td>${book.comName }</td>
					<td>${book.formatName }</td>
					<td>${book.gradeLevel }</td>
					<td>${book.bookEdtion }</td>
					<td>${book.fascicule }</td>
					<td title="${book.relationComId}">${book.fasciculeId != 178 && not empty book.relationComId ? '已关联':'<font color="red" style="line-height:20px">未关联</font>' }</td>
					<td>${book.comOrder }</td>
					<td><fmt:formatDate value="${book.bookInTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">

	</div>
</div>
<script type="text/javascript">
function jxtx_jc_gl_ajaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
			alertMsg.correct("修改成功！");
			reload_Jc_Box("${book.phaseId }","${book.subjectId}","${book.publisherId }","${book.gradeLevelId}");
		}
	}
}
function  del_reload_Jc_Box(json){
	DWZ.ajaxDone(json);
	reload_Jc_Box("${book.phaseId }","${book.subjectId }","${book.publisherId }","${book.gradeLevelId }");
}
</script>
