<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" id="dw_roel" action="${ctx }/jy/back/yhgl/saveyhRole" class="pageForm required-validate" onsubmit="return unitUserRoleFormAdd(this);">
	<div  id="" class="pageFormContent" layoutH="56">
	<input type="hidden" name="userId" value="${userId }">
	<input type="hidden" name="phaseType" value="${phaseType }">
	<input type="hidden" name="editId" value="${findSpace.id }">
	<input type="hidden" id="sel_hi_id" name="roleId" value="${findSpace.roleId }">
	<input type="hidden" id="sel_hi_syId" name="sysRoleId" value="${findSpace.sysRoleId }">
	<input type="hidden" id="sel_hi_syName" name="spaceName" value="${findSpace.spaceName }">
		<div class="unit">
			<label>所属单位：</label>
			<label>${org.name}</label>
		</div>
		<div class="unit">
			<label>所属部门：</label>
			<select name="departmentId" id="unit_t" style="width:203px;">
					<option value="">请选择</option>
					<c:forEach items="${allDeptOrg}" var="dept">
					<option value="${dept.id}" ${deptId==dept.id ? 'selected':'' }>${dept.name}</option>
					</c:forEach>
			</select>
		</div>
		<div class="unit">
			<label>角色：</label>
			<c:choose>
				<c:when test="${empty findSpace.id }">
					<select  style="width:203px;" id="sys_ro_id" onchange="rolIdChange(this.value)" class="required">
						<option value="">请选择</option>
						<c:forEach items="${roleList }" var="role">
						<option value="${role.sysRoleId}&${role.id}&${role.roleName}" ${findSpace.sysRoleId==role.sysRoleId ? 'selected':'' }>${role.roleName }</option>
						</c:forEach>
					</select>
				</c:when>
				<c:otherwise>
					<input  type="text" readonly value="${findSpace.spaceName }"  style="width:120px;"/>
				</c:otherwise>
			</c:choose>
			
		</div>
		<div class="unit" id="yhgl_gxbm_" style="display: none">
			<label>管辖部门：</label>
			<div style="width: 300px;float: left;">
				<c:forEach items="${allDeptOrg}" var="dept" varStatus="co" >
				<input type="checkbox" class="" name="dwgxbm" value="${dept.id}" <c:forEach items="${gxbmList}" var="selgx">${selgx==dept.id ? 'checked':'' }</c:forEach> />&nbsp;${dept.name}&nbsp;&nbsp;
				${(co.count) mod 3 == 0 ? "<br/><br/>" : "" }
				</c:forEach>
			</div>
		</div>
		<br>
		<div class="unit" id="yhgl_xd_" style="display: none">
			<label>学段：</label>
			<select class="combox " id="js_xg" name="phaseId" class="" ref="add_box_xd" refUrl="${pageContext.request.contextPath}/jy/back/yhgl/sujectInfoByPhase/{value}">
					<option value="-1" >请选择</option>
					<c:forEach items="${metaList }" var="meta">
					<option value="${meta.id }" ${findSpace.phaseId==meta.id ? 'selected':'' }>${meta.name }</option>
					</c:forEach>
			</select>
		</div>
		<div class="unit" id="yhgl_xk_" style="display: none">
			<label>学科：</label>
			<select class="combox " name="subjectId"  id="add_box_xd">
					<c:if test="${!empty findSpace.subjectId }"><option value="${findSpace.subjectId }" selected>${subjectName }</option></c:if>
					<option value="" >请选择</option>
			</select>
		</div>
	</div>	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit" >保存</button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>
</div>
	<script>
	function dialogAjaxDoneArea(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadUnitRole();
				$.pdialog.closeCurrent();
			}
		}
	}
	  function rolIdChange(obj){
		var role_id = obj.split("&");
		var space_name = role_id[2];
		$("#sel_hi_id").val(role_id[1]);
		$("#sel_hi_syId").val(role_id[0]);
// 		$("#sel_hi_syName").val(role_id[2]);
		$("#yhgl_gxbm_").hide();
		
		$("#yhgl_xd_").hide();
		$("#add_box_xd").removeClass("required");
		
		$("#yhgl_xk_").hide();
		$("#js_xg").removeClass("required");
		$.ajax({
			async : false, 
			type: 'POST',
			url:_WEB_CONTEXT_+"/jy/back/yhgl/showInfoBySysRoleId",
			data:{'sysRoleId':role_id[0]},
			dataType:"json",
			cache: false,
			success: function(data){
				if(data.isNoXd){
					$("#yhgl_xd_").show();
					$("#js_xg").addClass("required");
					if(data.isNoXk){
						$("#yhgl_xk_").show();
						$("#add_box_xd").addClass("required");
						$("#add_box_xd").prop({disabled: false});
					}else{
						$("#add_box_xd").prop({disabled: true});
					}
				}
				if(data.isNobm){
					$("#yhgl_gxbm_").show();
				}
			},
       	})
	  }
	  
	  function unitUserRoleFormAdd(form){
		var phase_id = $("#yhgl_xd_:visible").find("select").val();
		if (phase_id ==-1) {
			alertMsg.confirm("学段不能为空");
			return false;
		}
		var phase_name = $("#yhgl_xd_:visible").find("select").find("option:selected").text();
		var sysrole = $("#sys_ro_id option:selected").text();
		var subject_name = $("#yhgl_xk_:visible").find("select").find("option:selected").text();
		if(typeof(subject_name) == "undefined"){
			subject_name="";
		}
		if(typeof(phase_name) == "undefined"){
			phase_name="";
		}
		var space_name = phase_name+subject_name+sysrole;//拼接用户空间名称
		$("#sel_hi_syName").val(space_name);
		return validateCallback(form, dialogAjaxDoneArea);
	}
	</script>