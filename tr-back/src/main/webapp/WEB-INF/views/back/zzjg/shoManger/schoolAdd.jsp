<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileInput{float:left;}
#logoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#logoDiv button{margin-left:80px;margin-top:10px;}
.img_editblock{position: absolute;  left: 230px;height:167px;top:15px}
</style>

<div class="pageContent">
	<form method="post" id="sch_save" action="${ctx }/jy/back/zzjg/saveSch" class="pageForm required-validate" onsubmit="return schFormAdd(this);">
		<input type="hidden" value="${areaId}" name="areaId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>机构类别：</label>
				<select name="orgType" id="org_type" style="width:203px;" class="required">
					<option value="">请选择</option>
					<option value="0">会员校</option>
					<option value="1">体验校</option>
					<option value="2">演示校</option>
				</select>
			</div>
			<div class="unit">
				<label>学校全称：</label>
				<input name="name" id="sch_name" type="text" size="30" value="" class="val_name_ required" maxlength="20"/>
			</div>
			<div class="unit">
				<label>学校简称：</label>
				<input type="text" name="shortName"  size="30" class="required" maxlength="10"/>
			</div>
			<div class="unit">
				<label>学校编码：</label>
				<input type="text" name="code"  size="30" class="val_code_" maxlength="10"/>
			</div>
			<div class="unit">
				<label>英文名称：</label>
				<input type="text"  value="" size="30" name="englishName" class="textInput" maxlength="100">
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<input name="areaName" readonly="readonly" type="text" size="30" value="${selAreaName }"/>
			</div>
			<div class="unit">
				<label>学校类型：</label>
				<select name="schoolings" id="sch_xx" style="width:203px;" class="required">
					<option value="">请选择</option>
					<c:forEach var="schType" items="${schTypeList}">
						<option value="${schType.id}">${schType.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="unit">
				<label>联系人：</label>
				<input type="text" name="contactPerson"  size="30" maxlength="5"/>
			</div>
			<div class="unit">
				<label>联系方式：</label>
				<input type="text" name="phoneNumber"  size="30" maxlength="11" class="phone"/>
			</div>
			<div class="unit">
				<label>邮件：</label>
				<input type="text" name="email"  size="30" class="email"/>
			</div>
			<div class="unit">
				<label>地址：</label>
				<textarea rows="3" cols="30" name="address"  maxlength="50"></textarea>
			</div>
			<div class="unit">
				<label>学校网址：</label>
				<textarea rows="3" cols="30" name="schWebsite" maxlength="100" class="url"></textarea>
			</div>
			<div class="unit">
				<label>校徽：</label>
					<div id="p_p">
						<input style="width:300px" id="logoFileInput" type="file" name="file" 
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
							onUploadSuccess:uploadLogo			 
						}"
						/> 
						<input id="logo" type="hidden" name="logo" value="${org.logo}"/>
		 				<input type="button" onclick="$('#logoFileInput').uploadify('upload', '*');" value="上传" style="float:right;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
						<script type="text/javascript">
							function uploadLogo(file, data, response){
								var data = eval('(' + data + ')');
								$("#logo").val(data.data);
								$("#picDiv_sch").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><div class='img_editblock' onclick='deleteLogo(this);'></div>");
								$("#picDiv_sch").show();
								$("#p_p").hide();
							}
						</script> 
					</div>
					<div id="picDiv_sch" style="clear:both;margin-left:128px;display:none"> 
 					</div>
			</div>
			 
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
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
				parent.reloadXXInfoBox();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteLogo(obj){
		$("#logo").val("");
		$(obj).parent().hide();
		$("#p_p").show();
	}
	
function schFormAdd(form) {
	var loginNameExist;
	//校验学校名称是否冲突
	var name = $.trim($(".val_name_").val());
	$.ajax({
		type:"post",
		async : false, 
		url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${areaId}&type=0",
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
	//校验学校code是否冲突
	var code = $.trim($(".val_code_").val());
	$.ajax({
		type:"post",
		async : false, 
		url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${areaId}&type=0",
		data:{'code':code},
		dataType:"json",
		cache: false,
		success: function(data){
			loginNameExist=data;
		},
	});
	if (!loginNameExist && code!="") {
		alertMsg.confirm("学校编码重复");
		return false;
	}
	return validateCallback(form, dialogAjaxDoneSA);
}
</script>
