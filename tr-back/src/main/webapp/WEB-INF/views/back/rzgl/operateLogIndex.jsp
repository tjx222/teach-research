<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.grid .gridScroller {
	height: 440px;
}
</style>
<form id="pagerForm" onsubmit="return navTabSearch(this, 'czrz');"
	action="${ctx}/jy/back/rzgl/operateLogIndex" method="post">
	<div class="pageHeader" style="padding-top: 5px;">

		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>账号：<input type="text" name="searchUserName"
						value="${lsv.searchUserName}" />
					</td>
					<td>用户类型： <select name="sysRoleIdVo">
							<c:if test="${sysRoleIdNow==243}">
								<option value="">全部</option>
								<option value="243" ${lsv.sysRoleIdVo==243?  'selected':''}>超级管理员</option>
								<option value="1388" ${lsv.sysRoleIdVo==1388? 'selected':''}>运维管理员</option>
								<option value="1389" ${lsv.sysRoleIdVo==1389? 'selected':''}>区域管理员</option>
								<option value="1390" ${lsv.sysRoleIdVo==1390? 'selected':''}>学校管理员</option>
							</c:if>
							<c:if test="${sysRoleIdNow==1388}">
								<option value="">全部</option>
								<option value="1388" ${lsv.sysRoleIdVo==1388? 'selected':''}>运维管理员</option>
								<option value="1389" ${lsv.sysRoleIdVo==1389? 'selected':''}>区域管理员</option>
								<option value="1390" ${lsv.sysRoleIdVo==1390? 'selected':''}>学校管理员</option>
							</c:if>
							<c:if test="${sysRoleIdNow==1389}">
								<option value="1389">区域管理员</option>
							</c:if>
							<c:if test="${sysRoleIdNow==1390}">
								<option value="1390">学校管理员</option>
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
		<input type="hidden" name="order" value="" /> <input type="hidden"
			name="flago" value="" /> <input type="hidden" name="flags" value="" />

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
							<th width="50">所属单位</th>
							<th width="100">模块名称</th>
							<th width="50">操作类型</th>
							<th width="150">操作时间</th>
							<th width="200">描述信息</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${oList}" var="o">
							<tr>
								<jy:di key="${o.userId}"
									className="com.tmser.tr.uc.service.LoginService" var="lo">
									<td>${lo.loginname}</td>
								</jy:di>
								<jy:di key="${o.userId}"
									className="com.tmser.tr.uc.service.UserService" var="u">
									<td>${u.name}</td>
								</jy:di>
								<!-- <td>超级管理员</td> -->
								<c:if test="${o.sysRoleId==243}">
									<td>超级管理员</td>
								</c:if>
								<c:if test="${o.sysRoleId==1388}">
									<td>运维管理员</td>
								</c:if>
								<c:if test="${o.sysRoleId==1389}">
									<td>区域管理员</td>
								</c:if>
								<c:if test="${o.sysRoleId==1390}">
									<td>学校管理员</td>
								</c:if>
								<td>系统</td>
								<td>${o.module}</td>
								<td>${o.type}</td>
								<td>${o.createtime}</td>
								<td>${o.message}</td>
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