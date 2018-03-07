<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<div id="moniter_app_div" class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form id="pagerForm" action="${ctx}/jy/back/monitor/app/index" 
			method="post" onsubmit="return navTabSearch(this);">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}jy/back/monitor/app/addOrEditApp" target="dialog" mask="true" width="600" height="390"><span>新增</span></a></li>
			<li><a class="edit" href="${ctx}jy/back/monitor/app/addOrEditApp?id={app_id}" target="dialog" mask="true" width="600" height="390"><span>修改</span></a></li>
			<li><a class="delete" href="jy/back/monitor/app/deleteApp?id={app_id}" callback="app_info_reload" target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
		</ul>
		<span style="font-weight: bold;margin-left: 30px">应用名称：</span><input type="text" value="${model.appname}" name="appname">
		<input type="submit" value="搜索">
	</div>
		<table class="table" width="100%" layoutH="76">
			<thead>
				<tr align="center">
					<th orderField="id" class="${model.flago == 'id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
					<th>应用名称</th>
					<th>应用ID</th>
					<th>应用KEY</th>
					<th>登录地址</th>
					<th width="160" orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
					<th width="160" orderField="enable" class="${model.flago == 'enable' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">是否有效</th>
				</tr>
			</thead>
			<tbody style="overflow-y: auto; ">
			<c:forEach var="app" items="${data.datalist}" >
				<tr rel="${app.id }" target="app_id" align="center">
						<td>${app.id }</td>
						<td>${app.appname }</td>
						<td>${app.appid }</td>
						<td>${app.appkey }</td>
						<td>${app.loginUrl }</td>
						<td><fmt:formatDate value="${app.crtDttm}" pattern="yyyy-MM-dd"/></td>
						<td>
							<c:if test="${app.enable==0}">无效</c:if>
							<c:if test="${app.enable==1}">有效</c:if>
						</td>
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
			 <div class="pagination" targetType="navTab" totalCount="${data.totalCount }" numPerPage="${data.pageSize }" pageNumShown="10" currentPage="${data.currentPage }"></div>
		</div>
	</form>
	</div>
	<script type="text/javascript">
		function app_info_reload(){
			$.pdialog.closeCurrent();
			navTab.reloadFlag("moniter_app");
		}
	</script>
