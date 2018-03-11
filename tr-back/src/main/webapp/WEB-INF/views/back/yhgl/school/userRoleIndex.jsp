<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div  id="sch_user_role_gl">
	<div class="pageContent">	
		<div class="panelBar">
			<ul class="toolBar">
				<li><a target="dialog" href="${ctx}jy/back/yhgl/addSchUserRole?userId=${user.id}" class="add"  rel="add_box"  mask="true" ><span>设置用户角色</span></a></li>
				<li class="line">line</li>
			</ul>
		</div>
		<table class="table" width="100%" layoutH="55" >
			<thead>
				<tr>
					<th>角色</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody id="sch_info" style="overflow-y: auto; ">
				<c:forEach items="${userRoles}" var="data" >
					<tr target="dept_obj" rel="${data.id}" data-id="${data.id}">
		            	<td>
		            		<jy:di key="${data.roleId }" className="com.tmser.tr.uc.service.RoleService" var="role"/>
		            		${role.roleName}
		            	</td>
			            <td>
							<a title="确定要删除吗？" target="ajaxTodo" href="${ctx}jy/back/yhgl/delUserRole?id=${data.id }"  class="btnDelete" callback="reloadSchUserRole"></a>
						</td>
			        </tr>
			     </c:forEach>
			      
			</tbody>
		</table>
	</div>
</div>	
	<script>
	function reloadSchUserRole(){
		$.pdialog.reload(_WEB_CONTEXT_+"/jy/back/yhgl/manageSchUserRole", {data:{userId:"${user.id}"}, dialogId:"ad_sch_ro_us", callback:null})
	}
	</script>