<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	function _app_ajaxCallback(json) {
		$.pdialog.closeCurrent();
		$.pdialog.reload("${ctx}/jy/back/yygl/viewSon/${id}",{dialogId:"sonView"});
	}
	function _app_deleteCallback(json) {
		$.pdialog.reload("${ctx}/jy/back/yygl/viewSon/${id}",{dialogId:"sonView"});
	}
</script>
	<div class="pageContent" id="son">
		<div class="pageFormContent" layoutH="58">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="edit"
						href="${ctx}/jy/back/yygl/addSon/${id}"
						target="dialog" mask="true" width="580" height="420"><span>新建子模块</span></a></li>
					<li><a class="edit"
						href="${ctx}/jy/back/yygl/view/{sid_obj}" target="dialog"
						mask="true" width="580" height="420"><span>查看</span></a></li>
					<li><a class="delete"
						href="${ctx}/jy/back/yygl/delete/{sid_obj}" target="ajaxTodo"
						callback="_app_deleteCallback" title="确定要删除吗?"><span>删除</span></a></li>
					<li><a class="edit"
						href="${ctx}/jy/back/yygl/editSon/{sid_obj}"
						target="dialog" mask="true" width="580" height="420"><span>修改</span></a></li>
					<li class="line">line</li>
				</ul>
			</div>
			<c:choose>
				<c:when test="${!empty amlist}">
					<table class="table" width="70%" layoutH="60" rel="son">
						<thead>
							<tr>
								<th width="80">编号</th>
								<th orderField="name" width="100">模块名称</th>
								<th width="100">所属类型</th>
								<th width="100">顺序</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${amlist}" var="m">
								<tr target="sid_obj" rel="${m.id}">
									<td>${m.id}</td>
									<td>${m.name}</td>
									<c:if test="${m.sysRoleId==1}">
										<td>区域</td>
									</c:if>
									<c:if test="${m.sysRoleId==2}">
										<td>学校</td>
									</c:if>
									<c:if test="${m.sysRoleId==3}">
										<td>系统</td>
									</c:if>
									<td>${m.sort}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="prompt" style="margin-top: 10px;margin-left: 74px;">
						<p>
							<span>没有相关信息哟！  </span>
						</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
