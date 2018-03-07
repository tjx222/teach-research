<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>
<div class="pageContent j-resizeGrid" id="tpxwdraftindexId" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div style="float: left; padding-right: 20px;" class="panelBar">
			<ul class="toolBar">
				<li style="float: left;"><a class="delete" href="${ctx}/jy/back/xxsy/tpxw/batchdelete" target="selectedTodo" rel="ids" postType="string" title="确定要删除吗?" callback="reloadtpxw"><span>批量删除</span></a></li>
				<li class="line">line</li>
			</ul>
		</div>
	标题：<input id="title" type="text" value="">
		<input onclick="reloadtpxw()" type="button" value="搜索">
	<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/xxsy/tpxw/picturenewsDraft" method="post" onsubmit="return validateCallback(this, reloadtpxw);">
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr align="center">
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="20">编号</th>
				<th width="200">标题</th>
				<th width="30">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pictureNewsDraft.datalist}" var="fd">
				<tr target="sid_obj" rel="${fd.id}" align="center">
				<td><input name="ids" value="${fd.id}" type="checkbox"></td>
					<td>${fd.id}</td>
					<td>${fd.title}</td>
					 <td >
						<%-- <a title="继续编辑" target="dialog" href="${ctx}/jy/back/xxsy/tpxw/editPictureNews?id=${fd.id}" value="" class="btn_Edit" callback="reloadtpxw" mask="true" width="780" height="600"></a> --%>
						<a title="确定要删除吗？" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/xxsy/tpxw/batchdelete?ids=${fd.id}"   value="${fd.id}" class="btnDelete" callback="reloadtpxw"></a>&nbsp;&nbsp;
					 </td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
		<div class="panelBar">
			<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'tpxwdraftindexId')">
						<option value="10" ${pictureNewsDraft.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${pictureNewsDraft.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${pictureNewsDraft.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${pictureNewsDraft.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${pictureNewsDraft.totalCount}条</span>
			  </div>
			<input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${pictureNewsDraft.pageSize }" />
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
			<input type="hidden" name="pageNum" value="1" /><!--当前页-->
			<div class="pagination" rel="tpxwdraftindexId" totalCount="${pictureNewsDraft.totalCount }" numPerPage="${pictureNewsDraft.pageSize }" currentPage="${pictureNewsDraft.currentPage }"></div>
		</div>
		</form> 
</div>
</body>
<script type="text/javascript" >
function reloadtpxw(){
	var t = $("#tpxwdraftindexId").find("input[id='title']").val();
	var _title = encodeURI(t);
	var orgId='${orgId}';
	$("#tpxwdraftindexId").loadUrl( _WEB_CONTEXT_+"/jy/back/xxsy/tpxw/picturenewsDraft",{title:_title,orgId:orgId},function(){
		$("#tpxwdraftindexId").find("[layoutH]").layoutH();
	}		
	);
}
$(function(){
	$("#picnumid").html('${_count}')
})
</script>