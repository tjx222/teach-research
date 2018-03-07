<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent" id="feedbackId">
<form id="pagerForm" action="jy/back/fkgl/feedbackList" method="post" onsubmit="return navTabSearch(this, 'reloadfkgl');">
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" href="${ctx }/jy/back/fkgl/editfeedback?pid={sid_obj}" target="dialog" mask="true" width="500" height="400"><span>回复</span></a></li>
		<li><a class="delete" href="${ctx}/jy/back/fkgl/delfeedback" target="selectedTodo" rel="ids" postType="string" title="确定要删除吗?" callback="reloadfkgl"><span>批量删除</span></a></li>
	</ul>
	<div>
		反馈者姓名：<input type="text" id="userNameSender" name="userNameSender"/>
		是否已回复：
		<select id="select" name="ishavareply">
			<option value="">请选择...</option>
			<option value="1" <c:if test="${1== recieve.ishavareply}">selected="selected"</c:if> >已回复</option>
			<option value="0" <c:if test="${0== recieve.ishavareply}">selected="selected"</c:if> >未回复</option>			
		</select>
		<input onclick="reloadfkgl()" type="button" value="搜索">
	</div>
</div>
			<table class="table" width="100%" layoutH="108">
				<thead>
					<tr align="center">
						<th ><input type="checkbox" group="ids" class="checkboxCtrl"></th>
						<th  orderField="id" class="${recieve.flago == 'id' ?  recieve.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >编号</th>
						<th>反馈者姓名</th>
						<th>反馈内容</th>
						<th orderField="senderTime" class="${recieve.flago == 'senderTime' ?  recieve.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">反馈时间</th>
						<th>是否已回复</th>
						<th>操作</th>
						<th hidden="true">附件</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${feedbacklist.datalist}" var="fd">
					<tr target="sid_obj" rel="${fd.id}" align="center">
						<td><input name="ids" value="${fd.id}" type="checkbox"></td>
						<td>${fd.id}</td>
						<td>${fd.userNameSender}</td>
						<td>${fd.message}</td>
						<td><fmt:formatDate value="${fd.senderTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						<td>${fd.ishavareply==1?'已回复':'暂未回复'}</td>
						<td>
						<a title="查看" target="dialog" href="${ctx }/jy/back/fkgl/fkgldetail?id=${fd.id}" class="btnSee" callback="reloadfkgl" mask="true" width="780" height="600"></a>
						<a title="确定要删除吗？" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/fkgl/delfeedback?ids=${fd.id}"　value="${fd.id}" class="btnDelete" callback="reloadfkgl"></a>
						<td hidden="true">${fd.attachment1 }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<input type="hidden" name="orgId" value="${orgId}" />
			<div class="panelBar">
			<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'feedbackId')">
						<option value="10" ${feedbacklist.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${feedbacklist.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${feedbacklist.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${feedbacklist.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${feedbacklist.totalCount}条</span>
			  </div>
			<input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${feedbacklist.pageSize }" />
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
			<input type="hidden" name="pageNum" value="1" /><!--当前页-->
		    <div class="pagination" rel="feedbackId" totalCount="${feedbacklist.totalCount }" numPerPage="${feedbacklist.pageSize }" pageNumShown="20" currentPage="${feedbacklist.currentPage }"></div>
		</div>
		</form> 
</div>
	
<script type="text/javascript">
function reloadfkgl(){
	var ishavareply = $("#select  option:selected").val();
	var _name = encodeURI($("#userNameSender").val());
	var orgId='${orgId}';
	$("#feedbackId").loadUrl( _WEB_CONTEXT_+"/jy/back/fkgl/feedbackList",{ishavareply:ishavareply,orgId:orgId},function(){
		$("#feedbackId").find("[layoutH]").layoutH();
	}		
	);
}

</script>
