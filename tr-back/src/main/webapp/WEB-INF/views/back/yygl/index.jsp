<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	function appdialogAjax(){
		$.pdialog.closeCurrent();
		navTab.reloadFlag("applicationManage");
	}
</script>
<div id="applicationManageBox" >
<div class="pageHeader" style="padding-top:5px;">
	<form id="pagerForm" onsubmit="return divSearch(this, 'applicationManageBox');" action="${ctx}/jy/back/yygl/index" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					类型：
					<input type="radio" name="sysRoleId" value="" ${empty model.sysRoleId  ?'checked' :'' }/>全部
					<input type="radio" name="sysRoleId" value="2" ${model.sysRoleId == 2 ?'checked' :'' }/>学校
					<input type="radio" name="sysRoleId" value="1" ${model.sysRoleId == 1 ?'checked' :'' }/>区域
					<input type="radio" name="sysRoleId" value="3" ${model.sysRoleId == 3 ?'checked' :'' }/>系统
				</td>
				<td>
					模块名称：<input type="text" name="name" value="${model.name }"/>
				</td>
				<td><div class="buttonActive" style="margin-left: 5px;"><div class="buttonContent"><button type="submit">搜索</button></div></div></td>
			</tr>
		</table>
	</div>
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
</form>
</div>
	<div class="pageContent" id="view"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add" href="${ctx}/jy/back/yygl/addOrEdit/-1"
					target="dialog" mask="true" width="580" height="400" rel="yygl_add"><span>新建模块</span></a></li>
				<li><a class="edit"
					href="${ctx}/jy/back/yygl/view/{sid_obj}" target="dialog"
					mask="true" width="580" height="400"><span>查看</span></a></li>
				<li><a class="delete"
					href="${ctx}/jy/back/yygl/delete/{sid_obj}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span></a></li>
				<li><a class="edit"
					href="${ctx}/jy/back/yygl/addOrEdit/{sid_obj}" target="dialog"
					mask="true" width="580" height="400" rel="yygl_edit"><span>修改</span></a></li>
				<li class="line">line</li>
				<li><a class="add" href="${ctx}/jy/back/yygl/viewSon/{sid_obj}" target="dialog" id="viewSon${a.id}"
						rel="sonView" mask="true" title="查看子模块" style="width: 65px;"><span>子模块</span></a>
					</li>
				<li class="line">line</li>
			</ul>
		</div>
		<c:choose>
			<c:when test="${!empty amlist}">
				<table class="table" width="100%" layoutH="90" rel="applicationManageBox">
					<thead>
						<tr>
							<th width="80" >编号</th>
							<th width="250" orderField="name" class="${model.flago == 'name' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >模块名称</th>
							<th width="100">所属类型</th>
							<th width="100">支持pc</th>
							<th width="100">支持平板</th>
							<th width="100">支持移动</th>
							<th width="100" orderField="sort" class="${model.flago == 'sort' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >顺序</th>
							<th>提示语</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${amlist}" var="a">
							<c:if test="${a.parentid==0}">
								<tr target="sid_obj" rel="${a.id}">
									<td>${a.id}</td>
									<td>${a.name}</td>
									<c:if test="${a.sysRoleId==1}">
										<td>区域</td>
									</c:if>
									<c:if test="${a.sysRoleId==2}">
										<td>学校</td>
									</c:if>
									<c:if test="${a.sysRoleId==3}">
										<td>系统</td>
									</c:if>
									<td>${a.isNormal?'是':'否'}</td>
									<td>${a.isTablet?'是':'否'}</td>
									<td>${a.isMobile?'是':'否'}</td>
									<td>${a.sort}</td>
									<td>
										${a.desc }
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="prompt" style="margin-top: 160px; ">
					<p>
						<span>没有相关信息哟！ </span>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
		<!-- <div class="panelBar"></div> -->
	</div>
</div>
