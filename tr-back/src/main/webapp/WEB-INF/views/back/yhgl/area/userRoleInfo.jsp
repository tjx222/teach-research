<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div  id="user_role_gl">
		<div class="pageContent">	
				<div class="panelBar">
					<ul class="toolBar">
						<li><a target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/addOrEditUnitRole?userId=${userId}" class="add"  rel="add_box"  mask="true"><span>新建身份</span></a></li>
						<li class="line">line</li>
					</ul>
				</div>
				<c:choose>
					<c:when test="${empty uslist}">
						<div class="prompt" style="margin-top: 10px;margin-left: 74px;">
							<p style="width:380px;text-align:center;">
								<span>
									没有相关信息呦!
								</span>
							</p>
						</div>
					</c:when>
					<c:otherwise>
						<table class="table" width="100%" layoutH="103" >
							<thead>
								<tr>
									<th width="160">所属单位</th>
									<th width="160">所属部门</th>
									<th width="160">角色</th>
									<th width="160">学年</th>
									<th width="160">操作</th>
								</tr>
							</thead>
							<tbody id="sch_info" style="overflow-y: auto; ">
									<c:forEach items="${uslist}" var="data" >
										<tr target="dept_obj" rel="${data.id}" data-id="${data.id}">
							            	<td>
							            		<jy:di key="${data.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org"/>
							            		${org.name}
							            	</td>
							            	<td>
							            		<jy:di key="${data.departmentId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org"/>
							            		${org.name}
							            	</td>
							            	<td>
							            		${data.spaceName}
							            	</td>
							            	<td>${data.schoolYear }
							            	</td>
								            <td>
									            <a title="查看"  target="dialog" href="${ctx}jy/back/yhgl/lookUnitUserRole?id=${data.id}" class="btnSee" mask="true"></a>
												<a title="确定要删除吗？" target="ajaxTodo" href="${ctx}jy/back/yhgl/delUserRole?id=${data.id }&userId=${data.userId}"  class="btnDelete" callback="reloadUnitRole"></a>
											</td>
								        </tr>
								     </c:forEach>
							      
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
		</div>

</div>	
	<script>
	function reloadUnitRole(){
		$.pdialog.reload(_WEB_CONTEXT_+"/jy/back/yhgl/unitUserRole", {data:{userId:'${userId}'}, dialogId:"ad_unit_ro", callback:null})
	}
	</script>