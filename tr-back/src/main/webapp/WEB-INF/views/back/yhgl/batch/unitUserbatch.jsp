<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.dialogContent{position: relative;}
#resultInfo{position: absolute;top: 0;display:none;}
#resultInfo a{position: absolute;right:20px;top: 6px;color: red;}
.pageFormContent p{width:553px;}
</style>
<div class="pageContent">
	<form id="fileSubmit" method="post" action="${ctx }jy/back/yhgl/batchRegister" class="pageForm required-validate"  enctype="multipart/form-data" onsubmit="return beforeSubmit();" target="hidden_frame">
		<c:if test="${!empty orga}">
		<input type="hidden" id="sel_org_id" value="" name="orgId">
		</c:if>
		<input type="hidden" name="registerType" value="qyyh"/>
		<div class="pageFormContent" layoutH="56">
			<div style="position: relative;top: 25px;" ></div>
				
			<p style="color: red;height:32px;border-bottom: 1px dotted #bbb;">
					注：请下载表格模板进行填写后，再导入 "批量导入区域用户模板.xls"。<br>
					<a style="color: blue;line-height:27px;" href="javascript:void(0);" onclick="downUnitMode()">下载模板</a>
			</p>
			<c:if test="${empty orga}">
				<p>
					<label>所属单位：</label>
					<select name="orgId" id="sele_user" style="width:203px;" class="required">
						<option value="">请选择</option>
						<c:forEach items="${orglist}" var="org">
							<option value="${org.id}">${org.name}</option>
						</c:forEach>
					</select>
				</p>
			</c:if>
			<c:if test="${!empty orga}">
				<p>
					<label>所属单位：</label>
					<span style="position: relative;top:5px;">${orgName}</span>
				</p>
			</c:if>
			<p style="width: 100%;">
				<label>导入学校：</label>
				<input id="file_id" type="file" name="registerFile" class="required" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">导入</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<div id="resultInfo">
	<a onclick="closeIt();" style="cursor: pointer;">关闭</a>
	<iframe name='hidden_frame' id="hidden_frame" frameborder="0" scrolling="no" style="width:564px;height:228px;border:1px #ccc solid;background:#fff;">11111111</iframe>
</div>
<script type="text/javascript">
	var orga = "${orga}";
function beforeSubmit(){
	if(orga == ""){
		var selOrgId = $("#sele_user").val();
		if(selOrgId == ""){
			alertMsg.info("请选择单位！");
			return false;
		}
	}else{
		$("#sel_org_id").val("${orga.id}");
	}
	if($("#file_id").val()==""){
		alertMsg.info("请选择文件！");
		return false;
	}
	$("#background,#progressBar").show();
	return true;
}
function showResultInfo(){
	$("#resultInfo").show();
}
function closeIt(){
	$("#resultInfo").hide();
}
function downUnitMode(){
	if(orga == ""){
		var selOrgId = $("#sele_user").val();
		if(selOrgId == ""){
			alertMsg.info("请选择单位！");
			return false;
		}
		window.location.href="${ctx }jy/back/yhgl/downLoadRegisterTemplate?templateType=qyyh&orgId="+selOrgId+"";
	}else{
		
	}
	if(orga != ""){
		var orgId = "${orga.id}";
		window.location.href="${ctx }jy/back/yhgl/downLoadRegisterTemplate?templateType=qyyh&orgId="+orgId+"";
	}
}
</script>

	