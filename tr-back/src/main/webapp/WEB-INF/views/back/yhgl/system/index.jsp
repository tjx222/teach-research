<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
.form_cont span{display: block; float: left; height: 23px; line-height: 20px;  font-size: 12px; margin-left: 12px;}
.form_cont input{float:left;width:100px;}
.form_cont button{margin-left:10px;}
</style>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<form id="pagerForm" action="jy/back/yhgl/sys/index" method="post" onsubmit="return navTabSearch(this, 'system_user');">
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" href="jy/back/yhgl/sys/editUser" target="dialog" mask="true" width="510" heigth="400"><span>新建账号</span></a></li>
		<li><a href="${ctx}jy/back/yhgl/exportOrgUser?userType=2" class="icon" ><span>导出</span></a></li>
		<li class="line">line</li>
	</ul>
	<div class="form_cont">
		<span>姓名：</span>
		<input type="text" name="name" value="${search_name }"/>
		<span>角色类型：</span>
		<select name="appId">
			<option value=''>请选择...</option>
			<c:forEach var="role" items="${roleList }">
				<shiro:hasRole name="cjgly">
					<option value="${role.id }" <c:if test="${role.id== search_role}">selected="selected"</c:if> >${role.roleName }</option>
				</shiro:hasRole>
				<shiro:hasRole name="ywgly">
					<c:if test="${role.roleCode == 'xxgly' }">
						<option value="${role.id }" <c:if test="${role.id== search_role}">selected="selected"</c:if> >${role.roleName }</option>
					</c:if>
				</shiro:hasRole>
		   </c:forEach>
		</select>
		<button type="submit">搜索</button>
	</div>
</div>
			<table class="table" width="99%" layoutH="78">
				<thead>
					<tr>
						<th orderField="id" class="${user.flago == 'id' ?  user.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >编号</th>
						<th>账号</th>
						<th>姓名</th>
						<th orderField="crtDttm" class="${user.flago == 'crtDttm' ?  user.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >创建时间</th>
						<th>角色</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="sysUser" items="${sysUserList.datalist }" >
					<tr>
						<td>${sysUser.id }</td>
						<td>${sysUser.flags }</td>
						<td>${sysUser.name }</td>
						<td><fmt:formatDate value="${sysUser.crtDttm}" pattern="yyyy-MM-dd HH:mm"/></td>
						<td>${sysUser.remark }</td>
						<td>${sysUser.enable==1?'未冻结':'已冻结' }</td>
						<td>
							<a title="查看" target="dialog" mask="true"  href="${pageContext.request.contextPath}/jy/back/yhgl/sys/viewUser?userId=${sysUser.id}" class="btnSee" class="btn_Edit" width="600" height="580"></a>
						   	<shiro:hasRole name="cjgly">
							    <a title="编辑" target="dialog" mask="true" href="${pageContext.request.contextPath}/jy/back/yhgl/sys/editUser?userId=${sysUser.id}" class="btn_Edit" width="510" height="635"></a>
							    <c:if test="${sysUser.enable ==1}">
							    <a title="您确定要冻结该用户吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${sysUser.id}&enable=0" class="btnClose" rel="lock_sch" callback="reloadSysUser"></a>
							    </c:if> 
							    <c:if test="${sysUser.enable ==0}">
							    <a title="您确定要取消冻结该用户吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${sysUser.id}&enable=1" class="btnOpen" rel="lock_sch" callback="reloadSysUser"></a>
							    </c:if> 
							    <a title="您确定要重置密码吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/yhgl/resetUserPass?id=${sysUser.id} " class="btnReset" callback="reloadSysUser"></a>
							    <a title="您确定要删除吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/yhgl/sys/delSysUser?userId=${sysUser.id}" class="btnDelete" callback="reloadSysUser"></a>
							    <c:if test='${!jfn:checkSysRole(sysUser.appId,"ADMIN") }'>
							    	<a title="管理范围授权" mask="true" target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/sys/powerScopeEdit?userId=${sysUser.id}" class="btnSet" width="300" height="400"></a>					
							    </c:if>
						    </shiro:hasRole>
						    <shiro:hasRole name="ywgly">
						       <c:if test="${sysUser.flago=='xxgly' }">
							    	<a title="编辑" target="dialog" mask="true" href="${pageContext.request.contextPath}/jy/back/yhgl/sys/editUser?userId=${sysUser.id}" class="btn_Edit" width="510" height="635"></a>
								    <c:if test="${sysUser.enable ==1}">
								    <a title="您确定要冻结该用户吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${sysUser.id}&enable=0" class="btnClose" rel="lock_sch" callback="reloadSysUser"></a>
								    </c:if> 
								    <c:if test="${sysUser.enable ==0}">
								    <a title="您确定要取消冻结该用户吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${sysUser.id}&enable=1" class="btnOpen" rel="lock_sch" callback="reloadSysUser"></a>
								    </c:if> 
								    <a title="您确定要重置密码吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/yhgl/resetUserPass?id=${sysUser.id} " class="btnReset" callback="reloadSysUser"></a>
								    <a title="您确定要删除吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/yhgl/sys/delSysUser?userId=${sysUser.id}" class="btnDelete" callback="reloadSysUser"></a>
								    <c:if test='${!jfn:checkSysRole(sysUser.appId,"ADMIN") }'>
								    	<a title="管理范围授权" mask="true" target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/sys/powerScopeEdit?userId=${sysUser.id}" class="btnSet" width="300" height="400"></a>					
								    </c:if>
							    </c:if>
						    </shiro:hasRole>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="panelBar">
				<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
						<option value="10" ${sysUserList.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${sysUserList.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${sysUserList.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${sysUserList.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${sysUserList.totalCount}条</span>
			  	</div>
				<input type="hidden" name="page.currentPage" value="1" />
			    <input type="hidden" name="page.pageSize" value="${sysUserList.pageSize }" />
			    <input type="hidden" name="order" value="" />
			    <input type="hidden" name="flago" value="" />
				<input type="hidden" name="flags" value="" />
			    <div class="pagination" totalCount="${sysUserList.totalCount }" numPerPage="${sysUserList.pageSize }" pageNumShown="10" currentPage="${sysUserList.currentPage }"></div>
			</div>
		</form> 
</div>
	
<script type="text/javascript">
function  reloadSysUser(json){
	DWZ.ajaxDone(json);
	navTab.reload(_WEB_CONTEXT_+"/jy/back/yhgl/sys/index", {navTabId:'system_user'});
}
function reloadList(){
	navTab.reload(_WEB_CONTEXT_+"/jy/back/yhgl/sys/index", {navTabId:'system_user'});
}
var PW_TREE_CHILD ={};
</script>
