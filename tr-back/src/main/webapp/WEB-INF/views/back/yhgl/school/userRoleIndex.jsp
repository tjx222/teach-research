<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div  id="sch_user_role_gl">
	<div class="pageContent">	
		<div class="panelBar">
			<ul class="toolBar">
				<li><a target="dialog" href="${ctx}jy/back/yhgl/addSchUserRole?userId=${us.userId}&orgId=${us.orgId}" class="add"  rel="add_box"  mask="true" ><span>新建身份</span></a></li>
				<li class="line">line</li>
			</ul>
		</div>
		<table class="table" width="100%" layoutH="55" >
			<thead>
				<tr>
					<th width="">所属部门</th>
					<th width="">学段</th>
					<th width="160">身份名称</th>
					<th width="120">角色</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody id="sch_info" style="overflow-y: auto; ">
				<c:forEach items="${uslist}" var="data" >
					<tr target="dept_obj" rel="${data.id}" data-id="${data.id}">
		            	<td>
		            		<jy:di key="${data.departmentId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org"/>
		            		${org.name}
		            	</td>
		            	<td>
		            		<jy:di key="${data.phaseId }" className="com.tmser.tr.manage.meta.service.MetaRelationshipService" var="meta"/>
		            		${meta.name}
		            	</td>
		            	<td>${data.spaceName}</td>
		            	<td>
		            		<jy:di key="${data.roleId }" className="com.tmser.tr.uc.service.RoleService" var="role"/>
		            		${role.roleName}
		            	</td>
			            <td>
				            <a title="查看"  target="dialog" href="${ctx}jy/back/yhgl/detailUserRole?id=${data.id}" class="btnSee" mask="true"></a>
				            <c:if test="${sessionScope._CURRENT_SCHOOLYEAR_ == data.schoolYear}">
				            <a title="编辑" mask="true" target="dialog" href="${ctx}jy/back/yhgl/editSchUserRole?id=${data.id}" class="btn_Edit"  rel="edit_sch_user_role"></a>
							<a title="确定要删除吗？" target="ajaxTodo" href="${ctx}jy/back/yhgl/delUserRole?id=${data.id }"  class="btnDelete" callback="reloadSchUserRole"></a>
							<a title="确定要${data.enable == 1?'禁用':'启用' }${data.spaceName}身份吗？" id="userspacestate${data.id }" data-state="${data.enable }"target="ajaxTodo" href="${ctx}jy/back/yhgl/enableUserRole?id=${data.id }"  class="${data.enable == 1 ? 'btnClose':'btnOpen'}" callback="chageSchUserRoleState"></a>
							</c:if>
						</td>
			        </tr>
			     </c:forEach>
			      
			</tbody>
		</table>
	</div>
</div>	
	<script>
	function reloadSchUserRole(){
		$.pdialog.reload(_WEB_CONTEXT_+"/jy/back/yhgl/manageSchUserRole", {data:{userId:"${us.userId}",orgId:"${us.orgId}"}, dialogId:"ad_sch_ro_us", callback:null})
	}
	function chageSchUserRoleState(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			var abtn = $("#userspacestate"+json.data);
			if(abtn.attr("data-state") == "1"){
				abtn.attr("data-state","0");
				abtn.removeClass("btnClose").addClass("btnOpen");
			}else{
				abtn.attr("data-state","1");
				abtn.removeClass("btnOpen").addClass("btnClose");
			}
		}
	}
	</script>