<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="delete" href="jy/back/jxtx/jamb/delete?tpId={tpId}&pageNum=${page.currentPage}" callback="reloadjamb2" target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
		</ul>
	</div>
	<div id="div_org">
		<form id="pagerForm" action="jy/back/jxtx/jamb/getOrgTemplateList" onsubmit="return divSearch(this, 'org_tempcontent');" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td><input type="text"  name="searchStr" alt="按照区域或学校搜索" value="${searchStr }"/></td>
						<td><button type="submit">搜索</button></td>
					</tr>				
				</tbody>
			</table>
		</div>
		<table class="table" width="99%" layoutH="145" rel="org_tempcontent">
			<thead>
				<tr>
					<th orderField="tpId" class="${model.flago == 'tpId' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >编号</th>
					<th>模板名称</th>
					<th>适用学校</th>
					<th orderField="sort" class="${model.flago == 'sort' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">显示顺序</th>
					<th orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="template" items="${templateList.datalist }" >
				<tr rel="${template.tpId }" target="tpId">
					<td>${template.tpId }</td>
					<td><a href="jy/back/jxtx/jamb/scanTemplate?tpId=${template.tpId }" target="_blank">${template.tpName }</a></td>
					<td>${template.orgName }</td>
					<td>${template.sort }</td>
					<td><fmt:formatDate value="${template.crtDttm}" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
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
		    <div class="pagination" rel="org_tempcontent" totalCount="${templateList.totalCount }" numPerPage="${templateList.pageSize }" pageNumShown="10" currentPage="${templateList.currentPage }"></div>
			</div>
		</form> 
	</div>
<script type="text/javascript">
function  reloadjamb2(){
	//navTab.reload(_WEB_CONTEXT_+"/jy/back/jxtx/jamb/getOrgTemplateList", {navTabId:'org_tempcontent'});
	$("#org_tempcontent").loadUrl(_WEB_CONTEXT_+"/jy/back/jxtx/jamb/getOrgTemplateList",function(){
		$("#org_tempcontent").find("[layoutH]").layoutH();
	} );
}

</script>