<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.dialogContent{position: relative;}
#resultInfo{position: absolute;top: 0;display:none;}
#resultInfo a{position: absolute;right:20px;top: 6px;color: red;}
.pageFormContent p{width:553px;}
</style>
<div class="pageContent">
	<form id="fileSubmit" method="post" action="${ctx }jy/back/yhgl/exportsDetails" class="pageForm required-validate" onsubmit="return unitUserFormExport(this);">
	<input type="hidden" name="userType" value="1">
	<input type="hidden" name="templateType" value="qyyh">
		<div class="pageFormContent" layoutH="56">
			<div style="position: relative;top: 25px;" ></div>
				
			<p>
				<label>所属单位：</label>
				<select name="orgId" id="sele_user" style="width:203px;" class="required">
					<option value="">请选择</option>
					<c:forEach items="${orglist}" var="org">
						<option value="${org.id}">${org.name}</option>
					</c:forEach>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">导出</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function unitUserFormExport(){
	if($("#sele_user").val()==""){
		alertMsg.info("请选择单位！");
		return false;
	}
	return true;
}
// 	$("#background,#progressBar").show();
// 	return true;
// }
// function showResultInfo(){
// 	$("#resultInfo").show();
// }
// function closeIt(){
// 	$("#resultInfo").hide();
// }

</script>

	