<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="roleType" class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }/jy/back/jsgl/jslx/goAddOrUpdate" target="dialog" mask="true"><span>导入角色类型</span></a></li>
			<li><a class="delete" href="${ctx}/jy/back/jsgl/jslx/delRoleType?id={sid_obj}" target="ajaxTodo" title="确定要删除吗?" callback="reloadRoleType"><span>删除</span></a></li>
			<li><a class="edit" href="${ctx}/jy/back/jsgl/jslx/goAddOrUpdate?id={sid_obj}" target="dialog" mask="true"><span>修改</span></a></li>
			<li><a class="add" href="${ctx}/jy/back/jsgl/jslx/toSee?id={sid_obj}" target="dialog" mask="true"><span>查看</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<c:choose>
	<c:when test="${!empty rtList}">
	<table class="table" width="100%" layoutH="76" rel="roleType">
		<thead>
			<tr align="center">
				<th width="120" orderField="number" >编号</th>
				<th orderField="name">名称</th>
				<th width="300">角色类型描述</th>
				<th width="300">显示顺序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rtList}" var="rt">
				<tr target="sid_obj" rel="${rt.id }" align="center">
					<td>${rt.id }</td>
					<td>${rt.roleTypeName }</td>
					<td>${rt.roleTypeDesc }</td>
					<td>${rt.sort }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:when>
		<c:otherwise>
				<div class="prompt" style="margin-top: 160px; ">
					<p>
						<span>没有相关信息哟！ </span>
					</p>
				</div>
		</c:otherwise>
	</c:choose>
	<div class="panelBar"></div>
</div>

<script type="text/javascript">
	function reloadRoleType(){
		$("#roleType").loadUrl(_WEB_CONTEXT_+"/jy/back/jsgl/jslx/show_roleType",{},function(){
			$("#roleType").find("[layoutH]").layoutH();
		});
	}
</script>