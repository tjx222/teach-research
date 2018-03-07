<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileInput{float:left;}
.img_editblock{position: absolute;  left: 230px;height:167px;top:15px}
</style>
<div class="pageContent">
	<form method="post" id="sch_save" action="${ctx }jy/back/schconfig/subject/custom/save" class="pageForm required-validate" onsubmit="return subjectFormAdd(this);">
		<input type="hidden" value="${orgId}" name="orgId">
		<input type="hidden" value="${phaseId }" name="phaseId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>学科名称：</label>
				<input name="name" id="subject_name" type="text" size="30" value="" class="val_name_ required" maxlength="20"/>
			</div>
			<div class="unit">
				<label>描述：</label>
				<input name="descs" id="subject_desc" type="text" size="30" value="" maxlength="20"/>
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

	function dialogAjaxDoneSA(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				reloadSubjectBox();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteLogo(obj){
		$("#logo").val("");
		$(obj).parent().hide();
		$("#p_p").show();
	}
	
function subjectFormAdd(form) {
	var subjectNameExist;
	//校验学校名称是否冲突
	var name = $.trim($(".val_name_").val());
	$.ajax({
		type:"post",
		async : false, 
		url:"${pageContext.request.contextPath}/jy/back/dic/exists?orgId=${orgId}&parentId=97",
		data:{'name':name},
		dataType:"json",
		cache: false,
		success: function(result){
			subjectNameExist=result.data;
		},
	});
	if (subjectNameExist && name!="") {
		alertMsg.confirm("学科名称已存在");
		return false;
	}
	return validateCallback(form, dialogAjaxDoneSA);
}
</script>
