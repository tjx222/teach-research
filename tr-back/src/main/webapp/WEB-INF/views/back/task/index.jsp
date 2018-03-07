<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="com.tmser.tr.back.task.Task"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="taskManageBox" >
 <div class="pageContent" id="view"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="edit"
					href="${ctx}/jy/back/task/excute/{sid_obj}/" target="ajaxTodo" title="计划任务对数据影响较大，确定要执行吗?">
					<span>强制执行</span></a></li>
			</ul>
		</div>
		<c:choose>
			<c:when test="${!empty tasks}">
				<table class="table" width="100%" layoutH="70" rel="applicationManageBox">
					<thead>
						<tr>
							<th width="100" >编码</th>
							<th width="100">执行计划</th>
							<th>描述</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tasks}" var="task">
								<tr target="sid_obj" rel='<%=((Task)pageContext.getAttribute("task")).code() %>'>
									<td><%=((Task)pageContext.getAttribute("task")).code() %></td>
									<td><%=((Task)pageContext.getAttribute("task")).cron() %></td>
									<td>
										<%=((Task)pageContext.getAttribute("task")).desc() %>
									</td>
								</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="prompt" style="margin-top: 160px; ">
					<p>
						<span>没有相关计划任务哟！ </span>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
