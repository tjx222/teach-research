<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent" id="msgContent" >
	<form id="fileSubmit" method="post" action="${ctx }jy/back/zzjg/batchInsertSch.json"  enctype="multipart/form-data" target="hidden_frame">
		<input type="hidden" value="${area.id }" name="areaId">
		<div class="pageFormContent" layoutH="56">
			<div style="border-bottom: 1px dotted #bbb;position: relative;top: 25px;" ></div>
				
			<p style="color: red;">
					注：请下载表格模板进行填写后，再导入 "学校模板.xls"。
					<a style="color: blue;" href="${ctx }jy/back/yhgl/downLoadRegisterTemplate?templateType=xx&orgId=${area.id }">下载模板</a>
			</p>
			<p>
				<label>所属区域：</label>
				<span style="position: relative;top:5px;">${area.name }</span>
			</p>
			<p>
				<label>机构类别：</label>
				<select id="school_type" name="orgType" class="required" >
					<option value="" > 请 选 择 </option>
					<option value="0"> 会 员 校 </option>
					<option value="1"> 体 验 校 </option>
					<option value="2"> 演 示 校 </option>
				</select>
			</p>
			<p style="width: 100%;">
				<label>导入学校：</label>
				<input id="file_id" type="file" name="file" class="required" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" onclick="uploadeSchool();" >保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
<script type="text/javascript">
function uploadeSchool(){
	if($("#school_type").val()==""){
		alertMsg.info("请选择机构类别！");
	}else{
		if($("#file_id").val()==""){
			alertMsg.info("请选择文件！");
		}else{
			$("#fileSubmit").submit();
		}
	}
}
//上传成功的回调
function batchschoolcallback(obj){
	var jsonObj = eval('(' + obj + ')'); 
	if(jsonObj.flag=="success"){
// 		alertMsg.correct(jsonObj.msg);
		alertMsg.correct("文件处理成功");
// 		$.pdialog.closeCurrent();
	$("#msgContent").css({"font-size":"14px","line-height":"25px"});
	$("#msgContent").html(jsonObj.msg);
		parent.reloadXXInfoBox();
	}else if(jsonObj.flag=="warn"){
		alertMsg.warn(jsonObj.msg);
	}else if(jsonObj.flag=="fail"){
		alertMsg.error(jsonObj.msg);
		$.pdialog.closeCurrent();
	}
}
</script>

	