<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
#templateFileInput{float:left;}
#icoFileInput{float:left;}
#templateFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#icoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
</style>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
	<form method="post" action="jy/back/jxtx/jamb/saveTemplate" class="pageForm required-validate" onsubmit="return beforeSubmit_jamb(this)">
		<input type="hidden" name="tpId" value="${template.tpId }"/>
		<input type="hidden" name="tpType" value="${tpType}"/>
		<div class="pageFormContent">
			<div style="height:30px;" class="unit">
				<label>模板名称：</label>
				<input type="text" name="tpName" class="required" value="${template.tpName }" maxlength="50"/>
			</div>
			<div id="p_file" style="width:579px;height:42px;position: relative;<c:if test="${!empty fileRes }">display: none;</c:if>">
				<label style="line-height:37px;">模板文件上传：</label>
				<input id="templateFileInput" type="file" name="file" 
					uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:false,relativePath:'template'},
						buttonText:'请选择模板文件',
						fileSizeLimit:'1000KB',
						fileTypeDesc:'*.doc;*.docx;',
						fileTypeExts:'*.doc;*.docx;',
						auto:false,
						multi:false,
						onUploadSuccess:uploadSuccess				
					}"
				/>
				<input id="resId" type="hidden" name="resId" value=""/>
				<input type="button" onclick="$('#templateFileInput').uploadify('upload', '*');" value="上传" style="float:left;position: absolute;left: 249px; top: 8px;margin: -6px 0 0 20px;width: 56px;height: 27px;"/>
				<script type="text/javascript">
					function uploadSuccess(file, data, response){
						var data = eval('(' + data + ')');
						$("#resId").val(data.data);
						$("#fileDiv").html(file.name+"&nbsp;&nbsp;<input type='button' onclick='deletefile(this);'>");
						$("#fileDiv").parent().show();
						$("#p_file").hide();
					}
				</script>
			</div>
			<div <c:if test="${empty fileRes }">style="display: none;height:37px;" </c:if>>
				<label style="line-height:37px;">模板文件：</label>
				<div id="fileDiv" style="height:37px;line-height:37px;">
				    ${fileRes.name}.${fileRes.ext }&nbsp;&nbsp;<input type='button' onclick='deletefile(this);'>
				</div>
			</div>
			<div id="p_pic" style="width:579px;margin-top:10px;height:42px;position: relative;<c:if test="${!empty template.ico}">display: none;</c:if>" >
				<label style="line-height:37px;">模板图片上传：</label>
				<input style="width:300px" id="icoFileInput" type="file" name="file" 
					uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:false,relativePath:'template/ico'},
						buttonText:'请选择图片',
						fileSizeLimit:'1000KB',
						fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
						fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
						auto:false,
						multi:false,
						onUploadSuccess:uploadIco			
					}"
				/>
				<input id="ico" type="hidden" name="ico" value=""/>
				<input type="button" onclick="$('#icoFileInput').uploadify('upload', '*');" value="上传" style="float:left;position: absolute;left: 249px; top: 8px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/>
				<script type="text/javascript">
					function uploadIco(file, data, response){
						var data = eval('(' + data + ')');
						$("#ico").val(data.data);
						$("#picDiv").html("<img width='50' height='50' src='${ctx }jy/manage/res/download/"+data.data+"'><input type='button' onclick='deletePic(this);'/>");
						$("#picDiv").parent().show();
						$("#p_pic").hide();
					}
				</script>
			</div>
			<div <c:if test="${empty template.ico}">style="display: none;" </c:if> >
				<label style="line-height:33px;height:33px;">模板图片：</label>
				<div id="picDiv">
					<img width='50' height='50' src='${ctx }jy/manage/res/download/${template.ico}'><input type='button' onclick='deletePic(this);'/>
				</div>
			</div>
			<div style="height:30px;margin-top:10px;" class="unit">
				<label>显示顺序：</label>
				<input type="text" name="sort" class="required digits" value="${template.sort }" maxlength="3"/>
			</div>
			<div id="p1" style="margin-top:5px;height:20px;" class="unit">
				<input id="phaseIds" type="hidden" name="phaseIds" value="${template.phaseIds }"/>
				<input id="phaseNames" type="hidden" name="phaseNames" value="${template.phaseNames }"/>
				<label>适用学段：</label>
				<c:forEach var="phase" items="${phaseList }">
					<input type="checkbox" style="margin-right:3px;" name="phaseId" class="required" phaseName=${phase.name } value="${phase.eid }"/>${phase.name }
				</c:forEach>
			</div>
		</div>
		<div class="formBar">
			<button type="submit" style="width: 56px;height: 27px;margin-left:190px;"> 确定</button>
		</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#p1").find("input[type='checkbox']").each(function(){
		if("${template.phaseIds}".indexOf(","+$(this).val()+",")>-1){
			$(this).prop("checked",true);
		}
	});
});

function beforeSubmit_jamb(obj){
	var $form = $(obj);
	
	if (!$form.valid()) {
		return false;
	}
	if(${empty template.tpId }){
		if($("#resId").val()==""){
			alert("请上传教案模板文件");
			return false;
		}
	}
	var phaseIdsStr = ",";
	var phaseNamesStr = "";
	$("#p1").find("input[type='checkbox']:checked").each(function(){
		phaseIdsStr = phaseIdsStr+ $(this).val()+",";
		phaseNamesStr = phaseNamesStr+$(this).attr("phaseName")+"、";
	});
	$("#phaseIds").val(phaseIdsStr);
	$("#phaseNames").val(phaseNamesStr.substring(0,phaseNamesStr.length-1));
	var _submitFn = function(){
		$.ajax({
			type: obj.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: reloadjamb,
			error: DWZ.ajaxError
		});
	}
	_submitFn();
	return false;
}
function reloadjamb(json){
	alertMsg.correct(json[DWZ.keys.message])
	navTab.reloadFlag("jambId");
	$.pdialog.closeCurrent();
}
function deletefile(obj){
	$("#p_file").show();
	$(obj).parent().parent().hide();
	$("#resId").val("");
}
function deletePic(obj){
	$("#p_pic").show();
	$(obj).parent().parent().hide();
	$("#ico").val("");
}
</script>
