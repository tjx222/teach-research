<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.grid .gridScroller {
	height: 440px;
}
</style>
<form id="pagerForm" onsubmit="return navTabSearch(this, 'dlrz');"
	action="${ctx}/jy/back/rzgl/logginLogIndex" method="post">
	<div class="pageHeader" style="padding-top: 5px;">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>账号：<input type="text" name="searchUserName"
						value="${lsv.searchUserName}" />
					</td>
					<td>用户类型： <select name="sysRoleId">
							<c:if test="${jfn:checkSysRole(sysRoleIdNow,'ADMIN')}">
								<option value="">全部</option>
								<option value="<%=com.tmser.tr.uc.SysRole.ADMIN.getId() %>" ${jfn:checkSysRole(sysRoleId,'ADMIN')? 'selected':''}>超级管理员</option>
								<option value="<%=com.tmser.tr.uc.SysRole.YWGLY.getId() %>" ${jfn:checkSysRole(sysRoleId,'YWGLY')? 'selected':''}>运维管理员</option>
								<option value="<%=com.tmser.tr.uc.SysRole.QYGLY.getId() %>" ${jfn:checkSysRole(sysRoleId,'QYGLY')? 'selected':''}>区域管理员</option>
								<option value="<%=com.tmser.tr.uc.SysRole.XXGLY.getId() %>" ${jfn:checkSysRole(sysRoleId,'XXGLY')? 'selected':''}>学校管理员</option>
							</c:if>
							<c:if test="${jfn:checkSysRole(sysRoleIdNow,'YWGLY')}">
								<option value="">全部</option>
								<option value="<%=com.tmser.tr.uc.SysRole.YWGLY.getId() %>" ${jfn:checkSysRole(sysRoleId,'YWGLY')? 'selected':''}>运维管理员</option>
								<option value="<%=com.tmser.tr.uc.SysRole.QYGLY.getId() %>" ${jfn:checkSysRole(sysRoleId,'QYGLY')? 'selected':''}>区域管理员</option>
								<option value="<%=com.tmser.tr.uc.SysRole.XXGLY.getId() %>" ${jfn:checkSysRole(sysRoleId,'XXGLY')? 'selected':''}>学校管理员</option>
							</c:if>
							<c:if test="${jfn:checkSysRole(sysRoleIdNow,'QYGLY')}">
								<option value="<%=com.tmser.tr.uc.SysRole.QYGLY.getId() %>">区域管理员</option>
							</c:if>
							<c:if test="${jfn:checkSysRole(sysRoleIdNow,'XXGLY')}">
								<option value="<%=com.tmser.tr.uc.SysRole.XXGLY.getId() %>">学校管理员</option>
							</c:if>
					</select>
					</td>
					<td>姓名：<input type="text" name="searchName"
						value="${lsv.searchName}" />
					</td>
					<!-- <td>
					所属单位：<input type="text" name="aaa" value=""/>
				</td> -->
					<td><div class="buttonActive" style="margin-left: 20px;">
							<div class="buttonContent">
								<button type="submit">搜索</button>
							</div>
						</div></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li class="line">line</li>
			</ul>
		</div>
		<c:choose>
			<c:when test="${!empty pageList.datalist}">
				<table class="table" width="100%" rel="LoggerManageBox" layoutH="122">
					<thead>
						<tr>
							<th width="80">账号</th>
							<th width="80">姓名</th>
							<th width="80">用户类型</th>
							<th width="100">所属单位</th>
							<th width="100">来源IP</th>
							<th width="100">登录时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageList.datalist}" var="l">
							<tr>
								<jy:di key="${l.userId}"
									className="com.tmser.tr.uc.service.LoginService" var="lo">
									<td>${lo.loginname}</td>
								</jy:di>
								<jy:di key="${l.userId}"
									className="com.tmser.tr.uc.service.UserService" var="u">
									<td>${u.name}</td>
								</jy:di>
								<c:if test="${jfn:checkSysRole(l.sysRoleId,'ADMIN')}">
									<td>超级管理员</td>
								</c:if>
								<c:if test="${jfn:checkSysRole(l.sysRoleId,'YWGLY')}">
									<td>运维管理员</td>
								</c:if>
								<c:if test="${jfn:checkSysRole(l.sysRoleId,'QYGLY')}">
									<td>区域管理员</td>
								</c:if>
								<c:if test="${jfn:checkSysRole(l.sysRoleId,'XXGLY')}">
									<td>学校管理员</td>
								</c:if>
								<td>系统</td>
								<td>${l.ip}</td>
								<td>${l.loginTime}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="panelBar">
					<div class="pages">
						<span>显示</span> <select class="combox" name="numPerPage"
							onchange="navTabPageBreak({numPerPage:this.value})">
							<option value="10" ${page.pageSize == 10 ? 'selected':''}>10</option>
							<option value="20" ${page.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${page.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${page.pageSize == 100 ? 'selected':''}>100</option>
						</select> <span>条，共${page.totalCount}条</span>
					</div>
					<input type="hidden" name="page.currentPage" value="1" /> <input
						type="hidden" name="page.pageSize" value="${page.pageSize }" /> <input
						type="hidden" name="order" value="" /> <input type="hidden"
						name="flago" value="" /> <input type="hidden" name="flags"
						value="" />
					<div class="pagination" totalCount="${page.totalCount }"
						numPerPage="${page.pageSize }" pageNumShown="10"
						currentPage="${page.currentPage }"></div>
				</div>

			</c:when>
			<c:otherwise>
				<div class="prompt" style="margin-top: 160px;">
					<p>
						<span>没有相关信息哟！ </span>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
		<!-- <div class="panelBar"></div> -->
	</div>
</form>