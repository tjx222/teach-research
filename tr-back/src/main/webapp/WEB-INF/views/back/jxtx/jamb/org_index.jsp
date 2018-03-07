<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" href="jy/back/jxtx/jamb/edit?tpType=1" target="dialog" mask="true" width="600" height="315"><span>新增模板</span></a></li>
		<li><a class="edit" href="jy/back/jxtx/jamb/edit?tpId={tpId}" target="dialog" mask="true" width="600" height="315"><span>修改</span></a></li>
		<li><a class="delete" href="jy/back/jxtx/jamb/delete?tpId={tpId}" target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
	</ul>
</div>
			<table class="table" width="99%" layoutH="88">
				<thead>
					<tr>
					<th orderField="tpId" class="${model.flago == 'tpId' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >编号</th>
					<th>模板名称</th>
					<th>适用学段</th>
					<th orderField="sort" class="${model.flago == 'sort' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">显示顺序</th>
					<th orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
					<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="template" items="${templateList.datalist }" >
					<tr rel="${template.tpId }" target="tpId">
						<td>${template.tpId }</td>
						<td><a href="jy/back/jxtx/jamb/scanTemplate?tpId=${template.tpId }" target="_blank">${template.tpName }</a></td>
						<td>${template.phaseNames }</td>
						<td>${template.sort }</td>
						<td><fmt:formatDate value="${template.crtDttm}" pattern="yyyy-MM-dd"/></td>
						<td>
							<c:if test="${template.enable==0 }"><a class="btnClose" target="ajaxTodo" callback="reloadjamb3" href="jy/back/jxtx/jamb/unforbid?tpId=${template.tpId}" title="您确定要启用它吗？"></a></c:if>
							<c:if test="${template.enable==1 }"><a class="btnOpen" target="ajaxTodo" callback="reloadjamb3" href="jy/back/jxtx/jamb/forbid?tpId=${template.tpId}" title="您确定要禁用它吗？"></a></c:if>
							<c:if test="${template.tpIsdefault==0 }"><a class="btnDefault" target="ajaxTodo" callback="reloadjamb3" href="jy/back/jxtx/jamb/beDefault?tpId=${template.tpId}" title="置为默认?"></a></c:if>
							<c:if test="${template.tpIsdefault==1 }"><a class="btnDefault2" target="ajaxTodo" callback="reloadjamb3" href="jy/back/jxtx/jamb/unDefault?tpId=${template.tpId}" title="默认模板"></a></c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		<form id="pagerForm" action="jy/back/jxtx/jamb/index_org" method="post">
			<div class="panelBar">
				<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
						<option value="10" ${templateList.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${templateList.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${templateList.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${templateList.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${templateList.totalCount}条</span>
			  </div>
			<input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${templateList.pageSize }" />
		    <input type="hidden" name="order" value="${param.orderField}" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
		    <div class="pagination" totalCount="${templateList.totalCount }" numPerPage="${templateList.pageSize }" pageNumShown="10" currentPage="${templateList.currentPage }"></div>
			</div>
		</form> 
</div>
<script type="text/javascript">
function  reloadjamb3(){
	navTab.reload(_WEB_CONTEXT_+"/jy/back/jxtx/jamb/index_org", {navTabId:'jambId'});
}
</script>