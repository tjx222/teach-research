<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.unit label{text-align: right;}
</style>
<div class="pageContent">
	<form method="post" id="add_sch_role" action="${ctx }/jy/back/yhgl/saveSchUserRole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDoneSch);">
	<div  id="" class="pageFormContent" layoutH="56">
	<div class="unit">
			<input type="hidden" name="userId"  value="${user.id }">
				<c:forEach items="${roles }" var="role" varStatus="s">
				<c:if test="${s.index % 4 == 0}"><label></label></c:if>
					<input type="checkbox" name="roleIds" value="${role.id }" ${not empty oldRoles[role.id] ?'checked':'' }>${role.roleName }&nbsp;&nbsp;
				</c:forEach>
			</select>
		</div>
	</div>	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>
</div>
<script type="text/javascript">
	function dialogAjaxDoneSch(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadSchUserRole();
				$.pdialog.closeCurrent();
			}
		}
	}

			
</script>