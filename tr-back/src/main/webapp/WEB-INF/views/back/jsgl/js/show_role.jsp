<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.panelBar ul{float:left;}
</style>
<body>
<div class="pageContent j-resizeGrid" id="role" data-sid="${model.solutionId}" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form id="pagerForm" action="${ctx}/jy/back/jsgl/show_role?solutionId=${model.solutionId}" method="post" onsubmit="return navTabSearch(this);">
	<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${model.solutionId != 0 }">
				<li><a class="add" href="${ctx }/jy/back/jsgl/tongBu?qid=${model.solutionId}" target="ajaxTodo" mask="true"  callback="tongBu"><span>同步</span></a></li>
			</c:if>
			<li><a class="add" href="${ctx }/jy/back/jsgl/goAddOrEdit?solutionId=${model.solutionId}" target="dialog" mask="true"><span>新建角色</span></a></li>
			<li><a class="delete" href="${ctx}/jy/back/jsgl/delRole?id={sid_obj}" target="ajaxTodo" title="确定要删除吗?" callback="reloadRole"><span>删除</span></a></li>
			<li><a class="edit" href="${ctx}/jy/back/jsgl/goAddOrEdit?id={sid_obj}&solutionId=${model.solutionId}" target="dialog" mask="true"><span>修改</span></a></li>
			<li><a class="add" href="${ctx }/jy/back/jsgl/toSeeRole?id={sid_obj}" target="dialog" mask="true"><span>查看</span></a></li>
			<li><a class="delete" href="${ctx }jy/back/monitor/ehcache/authorizationInfoCache/clear" target="ajaxTodo" mask="true"><span>清除缓存</span></a></li>
			<li class="line">line</li>
		</ul>
		<span style="font-weight: bold;">角色名称：</span><input id="jsmc" type="text" value="${model.roleName }" name="roleName">
		<input type="submit" value="搜索">
	</div>
	<table class="table" width="100%" layoutH="76">
		
		<thead>
			<tr align="center">
				<th width="220" >编号</th>
				<th width="400">角色名称</th>
				<th width="400">角色类型</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.datalist}" var="rl">
				<tr target="sid_obj" rel="${rl.id }" align="center">
					<td>${rl.id }</td>
					<td>${rl.roleName }</td>
					<td>
						<jy:dic key="${rl.sysRoleId }"></jy:dic>
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
			    <div class="pagination" rel="role" totalCount="${data.totalCount }" numPerPage="${data.pageSize }" pageNumShown="10" currentPage="${data.currentPage }"></div>
		</div>
		</form> 
</div>
</body>
<script type="text/javascript" >
	function reloadRole(){
		var sqid =$("#role",navTab. getCurrentPanel()).attr("data-sid");
		var pagesize = $("input[name='page.pageSize']",navTab. getCurrentPanel()).val();
		$("#role",navTab. getCurrentPanel()).loadUrl(_WEB_CONTEXT_+"/jy/back/jsgl/show_role",{solutionId:sqid,"page.pageSize":pagesize},function(){
			$("#role").find("[layoutH]").layoutH();
		});
	} 
	
</script>