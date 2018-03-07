<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileInput{float:left;}
.img_editblock{position: absolute;  left: 230px;height:167px;top:15px}
</style>
<div class="pageContent">
	<form method="post" id="sch_save" action="${ctx }jy/back/schconfig/publisher/save/custom" class="pageForm required-validate" onsubmit="return publisherFormAdd(this);">
		<input type="hidden" value="${areaId}" name="areaId">
		<input type="hidden" value="${phaseId }" name="phaseId">
		<input type="hidden" value="${subjectId }" name="subjectId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>出版社名称：</label>
				<input name="name" id="publisher_name" type="text" size="30" value="" class="val_name_ required" maxlength="20"/>
			</div>
			<div class="unit">
				<label>描述：</label>
				<input name="descs" id="publisher_desc" type="text" size="30" value="" maxlength="20"/>
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
				parent.reLoadAreaCbs();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteLogo(obj){
		$("#logo").val("");
		$(obj).parent().hide();
		$("#p_p").show();
	}
	
function publisherFormAdd(form) {
	var publisherNameExist;
	var name = $.trim($(".val_name_").val());
	$.ajax({
		type:"post",
		async : false, 
		url:"${pageContext.request.contextPath}/jy/back/dic/exists?areaId=${areaId}&parentId=98",
		data:{'name':name},
		dataType:"json",
		cache: false,
		success: function(result){
			publisherNameExist=result.data;
		},
	});
	debugger;
	if (publisherNameExist && name!="") {
		alertMsg.confirm("出版社名称已存在");
		return false;
	}
	return validateCallback(form, dialogAjaxDoneSA);
}
</script>
