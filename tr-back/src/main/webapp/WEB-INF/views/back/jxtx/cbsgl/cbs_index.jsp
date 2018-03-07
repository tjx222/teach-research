<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }/jy/back/jxtx/addCBS?phaseId=${pr.phaseId}&subjectId=${pr.subjectId}" target="dialog" mask="true" ><span>添加出版社</span></a></li>
			<li><a class="delete" href="${ctx }/jy/back/jxtx/deleteCBS?id={sid_obj}" target="ajaxTodo" title="确定要删除吗?" callback="del_reloadCbsBox" ><span>删除</span></a></li>
			<li><a class="edit" href="${ctx }/jy/back/jxtx/editCBS?&id={sid_obj}" target="dialog" mask="true" ><span>修改</span></a></li>
			<li><a class="edit" href="${ctx }/jy/back/jxtx/refreshMetaCache" target="ajaxTodo"><span>刷新数据缓存</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="104" >
		<thead>
			<tr>
				<th width="100" orderField="number" class="asc">编号</th>
				<th width="280">出版社全称</th>
				<th width="180">出版社简称</th>
				<th width="100">显示顺序</th>
				<th width="160">创建时间</th>
			</tr>
		</thead>
		<tbody style="overflow-y: auto; ">
			<c:forEach items="${cbslist }" var="cbs">
				<tr target="sid_obj" rel="${cbs.id }">
					<td>${cbs.id }</td>
					<td>${cbs.name }</td>
					<td>${cbs.shortName }</td>
					<td>${cbs.sort }</td>
					<td><fmt:formatDate value="${cbs.crtDttm }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">

	</div>
</div>
<script type="text/javascript">
	function  del_reloadCbsBox(){
		parent.reloadCbsBox("${pr.phaseId }","${pr.subjectId}");
	}
</script>
