<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent" id="zb_exportTable">
<form id="pagerForm" action="jy/back/zbkt/kttjDetail" method="post" onsubmit="return dialogSearch(this);">
	<input type="hidden" name="classId" value="${model.classId}">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a id="exportZbData" class="icon" href="javascript:void(0);"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="88">
		<thead>
			<tr>
				<th width="80">用户ID</th>
				<th width="120">用户名</th>
				<th>单位</th>
				<th width="130">加入时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${classDetail.datalist}" var="detail">
				<tr>
					<td>${detail.userId}</td>
					<td>${detail.userName}</td>
					<td>${detail.orgName}</td>
					<td>${detail.joinTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="10" ${classDetail.pageSize == 10 ? 'selected':''}>10</option>
				<option value="20" ${classDetail.pageSize == 20 ? 'selected':''}>20</option>
				<option value="50" ${classDetail.pageSize == 50 ? 'selected':''}>50</option>
				<option value="100" ${classDetail.pageSize == 100 ? 'selected':''}>100</option>
			</select>
			<span>条，共${classDetail.totalCount}条</span>
		</div>
		<input type="hidden" name="page.currentPage" value="${classDetail.currentPage }" />
	    <input type="hidden" name="page.pageSize" value="${classDetail.pageSize }" />
	    <input type="hidden" name="order" value="" />
	    <input type="hidden" name="flago" value="" />
		<input type="hidden" name="flags" value="" />
	    <div class="pagination" targetType="dialog" totalCount="${classDetail.totalCount }" numPerPage="${classDetail.pageSize }" pageNumShown="10" currentPage="${classDetail.currentPage }"></div>
	</div>
</form>
</div>
<script type="text/javascript">
	$("#exportZbData").click(function(e){
		$('#zb_exportTable').tableExport({type:'excel',escape:'false',fileName:"参会详情.xls",htmlContent:'false'},e);
	})
</script>
