<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div id="areaCbsBox" class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }jy/back/schconfig/publisher/area/toadd?phaseId=${phaseId}&subjectId=${subjectId}&areaId=${areaId}" target="dialog" mask="true" width="660" ><span>添加出版社</span></a></li>
			<li><a class="delete" href="${ctx }jy/back/schconfig/publisher/del/{sid_obj}?phaseId=${phaseId}&subjectId=${subjectId}&areaId=${areaId}" target="ajaxTodo" title="确定要删除吗?" callback="reLoadAreaCbs" ><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="104" >
		<thead>
			<tr>
				<th width="100">ID</th>
				<th width="280">出版社全称</th>
				<th width="100">显示顺序</th>
			</tr>
		</thead>
		<tbody style="overflow-y: auto; ">
			<c:forEach items="${publisherList }" var="cbs" varStatus="c">
				<tr target="sid_obj" rel="${cbs.id }">
					<td>${cbs.id }</td>
					<td>${cbs.name }</td>
					<td>${c.count }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">

	</div>
</div>
<script type="text/javascript">
function reLoadAreaCbs(){
	$("#areaCbsBox").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/publisher/area/list?phaseId=${phaseId}&subjectId=${subjectId}&areaId=${areaId}",{},function(){
		$("#areaCbsBox").find("[layoutH]").layoutH();
	});
}
</script>
