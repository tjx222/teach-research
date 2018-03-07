<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileEdit{float:left;}
#logoFileEdit-queue{position: absolute;  left: 339px; top: -8px;}
#logoEditDiv button{margin-left:80px;margin-top:10px;}
.img_editblock{position: absolute;  left: 230px;height:167px;top:15px}
</style>
<div class="pageContent">
	<form method="post" id="" action="${ctx }jy/back/zzjg/saveSchool" class="pageForm required-validate" onsubmit="return schFormEditLog(this);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <h3>${id}</h3>
				  <input type="hidden" name="id" value="${id}"/>
			</div>
		
			<div class="unit">
				<label>机构类别：</label>
				<select name="" style="width:203px;" class="required" disabled="disabled">
					<option value="">请选择</option>
					<option value="0" ${newOrg.orgType==0 ? 'selected="selected"' : ''}>会员校</option>
					<option value="1" ${newOrg.orgType==1 ? 'selected="selected"' : ''}>体验校</option>
					<option value="2" ${newOrg.orgType==2 ? 'selected="selected"' : ''}>演示校</option>
				</select>
			</div>
			<div class="unit">
				<label>学校全称：</label>
				<input name="name" id="sch_name_" type="text" size="30" value="${newOrg.name }" class="val_name_ required" maxlength="20"/><span id="val_sch_name" style="color: red;font-weight:bold;"></span>
<%-- 				<input name="name" class="required" type="text" size="30" value="${newOrg.name }" maxlength="20" remote="${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${newOrg.areaId}&type=0&sid=${id}"/> --%>
			</div>
			<div class="unit">
				<label>学校简称：</label>
				<input type="text" name="shortName"  size="30" class="required" maxlength="10" value="${newOrg.shortName }"/>
			</div>
			<div class="unit">
				<label>英文名称：</label>
				<input type="text"  value="${newOrg.englishName }" size="30" name="englishName" class="textInput" maxlength="100">
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<input id="citySel" type="text" readonly value="${newOrg.areaName }${newOrg.name }"  size="30"/>
			</div>
			<div class="unit">
				<label>学校类型：</label>
			<input id="" type="text" readonly value="${xxTypeMeta.name }"  size="30"/>
			</div>
			<div class="unit">
				<label>联系人：</label>
				<input type="text" name="contactPerson" value="${newOrg.contactPerson }" size="30" maxlength="5"/>
			</div>
			<div class="unit">
				<label>联系方式：</label>
				<input type="text" name="phoneNumber" value="${newOrg.phoneNumber }" size="30" maxlength="11" class="phone"/>
			</div>
			<div class="unit">
				<label>邮件：</label>
				<input type="text" name="email"  size="30" value="${newOrg.email }" class="email"/>
			</div>
			<div class="unit">
				<label>地址：</label>
				<textarea rows="3" cols="30" name="address"  maxlength="50">${newOrg.address }</textarea>
			</div>
			<div class="unit">
				<label>学校网址：</label>
				<textarea rows="3" cols="30" name="schWebsite" maxlength="100" class="url">${newOrg.schWebsite }</textarea>
			</div>
			<div class="unit">
				<label>校徽：</label>
				<div id="p_edit" style="display: none">
				<input style="width:300px" id="logoFileEdit" type="file" name="file" 
				uploaderOption="{
					swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
					uploader:'${ctx }jy/manage/res/upload',
					formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'zzjg/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
					buttonText:'请选择图片', 
					fileSizeLimit:'1000KB', 
					fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
					fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
					auto:false,
					multi:false,
					onUploadSuccess:uploadLogoEdit			 
				}"
				/> 
				<input id="logoEdit" type="hidden" name="logo" value="${newOrg.logo}"/>
				<input id="oldPiclogo" type="hidden" name="oldLogo" value="${newOrg.logo}"/>
 				<input type="button" onclick="$('#logoFileEdit').uploadify('upload', '*');" value="上传" style="float:left;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
				<script type="text/javascript">
					function uploadLogoEdit(file, data, response){
						var data = eval('(' + data + ')');
						$("#logoEdit").val(data.data);
						$("#picDiv_sch_edit").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><div class='img_editblock' onclick='deleteLog(this);'></div>");
						$("#picDiv_sch_edit").show();
						$("#p_edit").hide();
					}
				</script> 
				</div>
				<div id="picDiv_sch_edit" style="clear:both;margin-left:128px;"> 
						<img width='100' height='50' src='${ctx }jy/manage/res/download/${newOrg.logo}?isweb=true'><div class="img_editblock"  onclick='deleteLog(this);'></div>
 				</div> 
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
	$(document).ready(function(){
		if("${newOrg.logo}"==""){
			$("#p_edit").show();
			$("#picDiv_sch_edit").hide();
		}
	});
	
	function schFormEditLog(form) {
		var loginNameExist;
		var name = $.trim($(".val_name_").val());
		$.ajax({
			async : false, 
			url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${newOrg.areaId}&type=0&sid=${id}",
			data:{'name':name},
			dataType:"json",
			cache: false,
			success: function(data){
				loginNameExist=data;
			},
		});
		if (!loginNameExist && name!="") {
			alertMsg.confirm("学校名称重复");
			return false;
		}
		return validateCallback(form, dialogSchI);
	}
	function dialogSchI(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteLog(obj){
		$("#logoEdit").val("");
		$(obj).parent().hide();
		$("#p_edit").show();
	}
</script>
