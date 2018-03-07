<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#logoFileInput{float:left;}
	#logoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
	#logoDiv button{margin-left:80px;margin-top:10px;}
	.pageFormContent .unit label{text-align: right;}
</style>

<div class="pageContent">
	<form method="post" id="sch_user_save" action="${ctx }/jy/back/yhgl/saveSchUser" class="pageForm required-validate" onsubmit="return schUseFormAdd(this);">
		<input type="hidden" value="${org.id}" name="orgId">
		<input type="hidden" value="${org.name}" name="orgName">
		<input type="hidden" name="id" value="${user.id }"/>
		<div class="pageFormContent" layoutH="56" style="padding:0;">
			<div class="unit">
				<label>所属学校：</label>
				<span style="position: relative;top: 3px;">${org.areaName}${org.name}</span>
			</div>
			<div class="unit">
				<label>账号：</label>
				<c:if test="${not empty login.loginname }">
					<input name="loginname" readonly="readonly" type="text" value="${login.loginname }" size="29" class="required alphanumeric" minlength="5" maxlength="16" />
				</c:if>
				<c:if test="${empty login.loginname }">
					<input name="loginname" id="school_loginname" class="loginName_val required val_email_user" minlength="5" maxlength="16" type="text" size="30" value=""  />
				</c:if>
			</div>
			<div class="unit">
				<label>姓名：</label>
				<input name="name" type="text" value="${user.name }" size="29" class="required" maxlength="5" />
			</div>
			<div class="unit">
				<label>昵称：</label>
				<input type="text" name="nickname" value="${user.nickname }" size="29" maxlength="5"/>
			</div>
			<div class="unit">
				<label>性别：</label>
				<c:if test="${not empty user.id }">
					<input type="radio" name="sex" class="required" <c:if test="${user.sex==0 }">checked="checked"</c:if> value="0"/>男 &nbsp;&nbsp;&nbsp;
					<input type="radio" name="sex" class="required" <c:if test="${user.sex==1 }">checked="checked"</c:if> value="1"/>女
				</c:if>
				<c:if test="${empty user.id }">
					<input type="radio" name="sex" class="required" value="0"/>男 &nbsp;&nbsp;&nbsp;
					<input type="radio" name="sex" class="required" checked="checked" value="1"/>女
				</c:if>
			</div>
			<div class="unit">
				<label>职称：</label>
				<input name="profession" type="text" value="${user.profession }" size="29" maxlength="6"/>
			</div>
			<div class="unit">
				<label>教龄：</label>
				<input name="schoolAge" class="digits" value="${user.schoolAge }" type="text" size="29" maxlength="2" style="width: 30px;"/>&nbsp;<span style="position: relative;top:5px;">年</span>
			</div>
			<div class="unit">
				<label>荣誉称号：</label>
				<input name="honorary" type="text" value="${user.honorary }" size="29"  maxlength="15"/>
			</div>
			<div class="unit">
				<label>骨干教师：</label>
				<select name="teacherLevel" id="teacherLevel" style="width:120px;">
					<option value="">请选择</option>
					<option value="国家骨干">国家骨干</option>
					<option value="特级骨干">特级骨干</option>
					<option value="省级骨干">省级骨干</option>
					<option value="市级骨干">市级骨干</option>
					<option value="区县级骨干">区县级骨干</option>
					<option value="校级骨干">校级骨干</option>
				</select>
			</div>
			<div class="unit">
				<label>出生日期：</label>
				<input type="text" name="birthday" class="date" readonly="readonly" value="<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd"/>" maxDate="%y-%M-%d"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<div class="unit">
				<label>身份证号：</label>
				<input type="text" name="idcard"  value="${user.idcard }" size="29" maxlength="18" />
			</div>
			<div class="unit">
				<label>教师证号：</label>
				<input type="text" name="cercode" value="${user.cercode }" size="29" maxlength="17" />
			</div>
			<div class="unit">
				<label>联系电话：</label>
				<input type="text" name="cellphone" value="${user.cellphone }" class="mobile"  size="29" maxlength="11" minlength="11"/>
			</div>
			<div class="unit">
				<label>邮编：</label>
				<input type="text" name="postcode" value="${user.postcode }" size="29" maxlength="6"/>
			</div>
			<div class="unit">
				<label>联系地址：</label>
				<input type="text" name="address" maxlength="50" value="${user.address }" style="width: 316px;">
			</div>
			<div class="unit">
				<label>个人简介：</label>
				<textarea rows="5" cols="50" name="explains" maxlength="500" >${user.explains }</textarea>
			</div>
			<div class="unit">
				<label>上传头像：</label>
					<div id="p_p">
						<input style="width:300px" id="logoFileInput" type="file" name="file" 
						uploaderOption="{
							swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }jy/manage/res/upload',
							formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'photo/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
							buttonText:'请选择图片', 
							fileSizeLimit:'1000KB', 
							fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
							fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
							auto:false,
							multi:false,
							onUploadSuccess:uploadLogo			 
						}"
						/> 
						<input id="logo" type="hidden" name="originalPhoto" value=""/>
		 				<input type="button" onclick="$('#logoFileInput').uploadify('upload', '*');" value="上传" style="float:right;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
						<script type="text/javascript">
							function uploadLogo(file, data, response){
								var data = eval('(' + data + ')');
								$("#logo").val(data.data);
								$("#picDiv").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><input type='button' onclick='deleteLogo(this);'/>");
								$("#picDiv").show();
								$("#p_p").hide();
							}
						</script> 
					</div>
					<div id="picDiv" style="clear:both;margin-left:128px;"> 
						<c:if test="${not empty user.photo }">
							<ui:photo src="${user.photo}" width="50" height="50"></ui:photo>
						</c:if>
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
	$.pdialog.resizeDialog({style: {height: 400,width:600}}, $.pdialog.getCurrent(), "");
	$.validator.addMethod("mobile", function(value, element) {
		var mobilecheck = /^(13|15|18)\d{9}$/i;
		return this.optional(element) || mobilecheck.test(value);
	}, "手机号码格式不正确");
	function dialogAjaxDonSchol(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reload_sch_users();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteLogo(obj){
		$("#logo").val("del");
		$(obj).parent().hide();
		$("#p_p").show();
	}
	$(document).ready(function(){
		if("${user.teacherLevel}"!=""){
			$("#teacherLevel").val("${user.teacherLevel}");
		}
	});
	$.validator.addMethod("loginName_val", function(value, element) {
		var mobilecheck = /[^\a-\z\A-\Z]/g;
		var sub = value.substring(0,1);
		return this.optional(element) || !mobilecheck.test(sub);
	}, "首位必须是字母");
	function schUseFormAdd(form){
		var loginNameExist;
		var name = $.trim($(".loginName_val").val());
		if(${empty login.loginname}){
			$.ajax({
				type:"post",
				async : false, 
				url:"${pageContext.request.contextPath}/jy/back/yhgl/valUnitName",
				data:{'loginname':name},
				dataType:"json",
				cache: false,
				success: function(data){
					loginNameExist=data;
				},
			});
			if (!loginNameExist && name != "") {
				alertMsg.confirm("学校用户账号重复");
				return false;
			}
		}
		return validateCallback(form, dialogAjaxDonSchol);
	}
</script>
