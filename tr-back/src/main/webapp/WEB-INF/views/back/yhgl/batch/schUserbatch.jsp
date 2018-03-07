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
		<input type="hidden" value="${orgId}" name="orgId">
		<input type="hidden" name="registerType" value="xxyh"/>
		<div class="pageFormContent" layoutH="56">
			<div style="position: relative;top: 25px;" ></div>
				
			<p style="color: red;height:32px;border-bottom: 1px dotted #bbb;">
				注：请下载表格模板进行填写后，再导入。<br>
				<c:forEach var="phase" items="${phaseList }">
					<a style="color: blue;line-height:27px;" href="${ctx }jy/back/yhgl/downLoadRegisterTemplate?templateType=xxyh&orgId=${orgId}&phaseId=${phase.id}">${phase.name }模板.xls</a>
				</c:forEach>
			</p>
			<p>
				<label>所属学校：</label>
				<span style="position: relative;top:5px;">${orgName}</span>
			</p>
			<p>
				<label>学段：</label>
				<select name="phaseId">
					<c:forEach var="phase" items="${phaseList }">
					<option value="${phase.id }">${phase.name }</option>
					</c:forEach>
				</select>
			</p>
			<p style="width: 100%;">
				<label>导入学校用户：</label>
				<input id="file_id" type="file" name="registerFile" class="required" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">导入</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button class="close">取消</button></div></div>
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
function beforeSubmit(){
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
</script>

	