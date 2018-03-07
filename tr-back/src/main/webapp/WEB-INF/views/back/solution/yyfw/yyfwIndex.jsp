<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<body>
	<div id="org_solution" class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="jy/back/solution/goAddFw?id=${model.id}" target="dialog" mask="true" width="300" height="400"><span>新增</span></a></li>
			<li><a class="delete" href="jy/back/solution/delFw?id={ogId}&sid=${model.id}" callback="reloadOrg_solution" target="ajaxTodo" title="删除应用机构将影响该机构下使用该方案的用户正常使用，确定要删除吗?" ><span>删除</span></a></li>
		</ul>
	</div>
	<c:choose>
	<c:when test="${!empty data.datalist}">
		<table class="table" width="100%" layoutH="76">
			<thead>
				<tr align="center">
					<th>编号</th>
					<th>学校名称</th>
					<th>所属区域</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="orgn" items="${data.datalist }" >
				<tr rel="${orgn.id }" target="ogId">
					<td>${orgn.id }</td>
					<td>${orgn.shortName }</td>
					<td>${orgn.areaName }</td>
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
		</c:when>
		<c:otherwise>
				<div class="prompt" style="margin-top: 160px; ">
					<p>
						<span>没有相关信息哟！ </span>
					</p>
				</div>
		</c:otherwise>
		</c:choose>
	</div>
	<script type="text/javascript">
		function reloadOrg_solution(){
			navTab.reload();
		}
	</script>
</body>