<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	
	<div class="content_wrap" layoutH="50" style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="edit" href="${ctx}/jy/back/zzjg/syncSch/execute/{sid_obj}" target="ajaxTodo" title="同步任务对数据影响较大，确定要执行吗?">
					<span>开始同步</span></a>
				</li>
			</ul>
		</div>
		<c:choose>
			<c:when test="${!empty tasks}">
				<table class="table" width="100%" layoutH="70" rel="applicationManageBox">
					<thead>
						<tr>
							<th width="100" >同步时间</th>
							<th width="100">appId</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tasks}" var="task">
								<tr target="sid_obj" rel='${task.id  }'>
									<td>${task.createtime }</td>
									<td>${task.threadName }</td>
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
