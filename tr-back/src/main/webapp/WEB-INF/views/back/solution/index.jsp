<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<body>
	<div id="solution" class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form id="pagerForm" action="${ctx}/jy/back/solution/index" 
			method="post" onsubmit="return navTabSearch(this);">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}jy/back/solution/goAddOrEdit" target="dialog" mask="true" width="600" height="390"><span>新增方案</span></a></li>
			<li><a class="edit" href="${ctx}jy/back/solution/goAddOrEdit?id={solu_id}" target="dialog" mask="true" width="600" height="390"><span>修改</span></a></li>
			<li><a class="delete" href="jy/back/solution/delete?id={solu_id}" callback="reloadSolution" target="ajaxTodo" title="此操作将清除方案所有相关信息，请确保没有机构使用该方案，否则相关机构的用户将受到影响。确定要删除吗?" ><span>删除</span></a></li>
			<li><a class="add" href="${ctx }jy/back/solution/yyfw?id={solu_id}" target="navTab" rel="solution_orgs"><span>应用范围管理</span></a></li>
			<li><a class="add" href="${ctx }jy/back/jsgl/show_role?solutionId={solu_id}" target="navTab" rel="solution_roles"><span>方案权限管理</span></a></li>
		</ul>
		<span style="font-weight: bold;margin-left: 30px">方案名称：</span><input type="text" value="${model.name}" name="name">
		<span style="font-weight: bold;">应用范围：</span><input id="fangAn" type="text" value="${model.descs}" name="descs">
		<input type="submit" value="搜索">
	</div>
		<table class="table" width="100%" layoutH="76">
			<thead>
				<tr align="center" target="solu_id" rel="1">
					<th>编号</th>
					<th>方案名称</th>
					<th>方案描述</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="solution" items="${data.datalist }" >
				<tr rel="${solution.id }" target="solu_id" align="center">
						<td>${solution.id }</td>
						<td>${solution.name }</td>
						<td>${solution.descs }</td>
						<td><fmt:formatDate value="${solution.crtDttm }" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<div class="panelBar">
			<div class="pages">
						<span>显示</span>
						<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
							<option value="10" ${data.pageSize == 10 ? 'selected':''}>10</option>
							<option value="20" ${data.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${data.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${data.pageSize == 100 ? 'selected':''}>100</option>
						</select>
						<span>条，共${data.totalCount}条</span>
				  </div>
			    <input type="hidden" name="page.pageSize" value="${data.pageSize }" />
				<input type="hidden" name="page.currentPage" value="1" />
			    <input type="hidden" name="order" value="" />
			    <input type="hidden" name="flago" value="" />
				<input type="hidden" name="flags" value="" />
			    <div class="pagination" rel="solution" totalCount="${data.totalCount }" numPerPage="${data.pageSize }" pageNumShown="10" currentPage="${data.currentPage }"></div>
		</div>
		</form> 
	</div>
	<script type="text/javascript">
	function reloadSolution(){
		var name=$("input[name='name']").val();
		$("#solution").loadUrl(_WEB_CONTEXT_+"/jy/back/solution/index",{name:name},function(){
			$("#solution").find("[layoutH]").layoutH();
		});
	}
	</script>
</body>