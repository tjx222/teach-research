<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="xxlxgl" class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }/jy/back/jxtx/goAddOrUpdate" rel="add_schType" target="dialog" mask="true"><span>新建学校类型</span></a></li>
			<li><a class="delete" href="${ctx}/jy/back/jxtx/delSchType?id={sid_obj}" target="ajaxTodo" title="确定要删除吗?" callback="navTabAjaxDone"><span>删除</span></a></li>
			<li><a class="edit" href="${ctx}/jy/back/jxtx/goAddOrUpdate?id={sid_obj}" target="dialog" mask="true"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="76" rel="xxlxgl">
		<thead>
			<tr align="center">
				<th width="120" orderField="number" class="asc">编号</th>
				<th orderField="name">学校类型名称</th>
				<th width="300">学校类型描述</th>
				<th width="333">显示顺序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${schTypeList}" var="xl">
				<tr target="sid_obj" rel="${xl.id }" align="center">
					<td>${xl.id }</td>
					<td>${xl.name }</td>
					<td><ui:sout value="${xl.descs }" length="30" needEllipsis="true"></ui:sout></td>
					<td>${xl.sort }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar"></div>
</div>
