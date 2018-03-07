<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="pageContent" >
<div class="tabs" style="margin-top: 5px;" currentIndex="0" eventType="click">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;" rel="tmpcontent" id="tmpt_sys"><span>平台模板管理</span></a></li>
				<li><a href="${ctx}/jy/back/jxtx/jamb/getOrgTemplateList" class="j-ajax" rel="org_tempcontent" id="tmpt_sch"><span>学校模板管理</span></a></li>
			</ul>
		</div>
	</div>
<div class="tabsContent" >
<div id="tmpcontent">
			<div class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="jy/back/jxtx/jamb/edit?tpType=0" target="dialog" mask="true" width="600" height="315"><span>新增模板</span></a></li>
			<li><a class="edit" href="jy/back/jxtx/jamb/edit?tpId={tpId}" target="dialog" mask="true" width="600" height="315"><span>修改</span></a></li>
			<li><a class="delete" href="jy/back/jxtx/jamb/delete?tpId={tpId}&pageNum=${page.currentPage}" callback="reloadjamb1" target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
		</ul>
	</div>
		<table class="table" width="99%" layoutH="118">
			<thead>
				<tr>
					<th orderField="tpId" class="${model.flago == 'tpId' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >编号</th>
					<th>模板名称</th>
					<th>适用学段</th>
					<th orderField="sort" class="${model.flago == 'sort' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">显示顺序</th>
					<th orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
					<th>禁用</th>
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
						<c:if test="${template.enable==0 }"><a class="btnClose" target="ajaxTodo" callback="reloadjamb1" href="jy/back/jxtx/jamb/unforbid?tpId=${template.tpId}" title="您确定要启用它吗？"></a></c:if>
						<c:if test="${template.enable==1 }"><a class="btnOpen" target="ajaxTodo" callback="reloadjamb1" href="jy/back/jxtx/jamb/forbid?tpId=${template.tpId}" title="您确定要禁用它吗？"></a></c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<form id="pagerForm" action="${ctx}jy/back/jxtx/jamb/index_sys" method="post">
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
		    <input type="hidden" name="order"/>
		    <input type="hidden" name="flago"/>
			<input type="hidden" name="flags"/>
		    <div class="pagination" totalCount="${templateList.totalCount }" numPerPage="${templateList.pageSize }" pageNumShown="10" currentPage="${templateList.currentPage }"></div>
		</div>
		</form> 
	</div>
</div>
<div id="org_tempcontent"></div>
</div>
</div>
	
</div>
<script type="text/javascript">
function  reloadjamb1(){
	navTab.reload(_WEB_CONTEXT_+"/jy/back/jxtx/jamb/index_sys", {navTabId:'jambId'});
//	$("#jambId").loadUrl(_WEB_CONTEXT_+"/jy/back/jxtx/jamb/index_sys",function(){
		//$("#jambId").find("[layoutH]").layoutH();
//	} );
}

</script>