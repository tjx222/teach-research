<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileInput{float:left;}
#logoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#logoDiv button{margin-left:80px;margin-top:10px;}
.img_editblock{position: absolute;  left: 230px;height:20px;top:15px}
</style>

<div class="pageContent">
	<form method="post" id="unit_user_save" action="${ctx }/jy/back/yhgl/saveUnitUser" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" value="${user.orgName}" name="orgName" id="hiSelOrgName">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>编号：</label>
				<h3>${loginUser.id}</h3>
				<input type="hidden" name="id" value="${loginUser.id}"/>
			</div>
			<div class="unit">
				<label>所属单位：</label>
				<label>${user.orgName }</label>
			</div>
			<div class="unit">
				<label>账号：</label>
				<input name="loginname" readonly id="unit_loginname" type="text" size="30" value="${loginUser.loginname }"/>
			</div>
			<div class="unit">
				<label>姓名：</label>
				<input name="name" id="unit_name" type="text" size="30" value="${user.name }" class="required" maxlength="5"/>
			</div>
			<div class="unit">
				<label>昵称：</label>
				<input type="text" name="nickname" maxlength="5"  value="${user.nickname }"/>
			</div>
			<div class="unit">
				<label>性别：</label>
				<span><input type="radio" name="sex" value="0" ${user.sex == 0 ? 'checked':'' } class="required"/>男</span>
				<span><input type="radio" name="sex" value="1" ${user.sex == 1 ? 'checked':'' } class="required"/>女</span>
			</div>
			<div class="unit">
				<label>职称：</label>
				<input name="profession" id="unit_profession" type="text" size="30" value="${user.profession }" maxlength="6" />
			</div>
			<div class="unit">
				<label>教龄：</label>
				<input name="schoolAge" id="unit_schoolAge" type="text" size="30" value="${user.schoolAge }" class="digits"/>
			</div>
			<div class="unit">
				<label>荣誉称号：</label>
				<input name="honorary" id="unit_honorary" type="text" size="30" value="${user.honorary }" maxlength="15"/>
			</div>
			<div class="unit">
				<label>骨干教师：</label>
				<select name="teacherLevel" id="unit_teacherLevel" style="width:203px;">
					<option value="">请选择</option>
					<option value="国家骨干" ${user.teacherLevel == '国家骨干' ? 'selected':''}>国家骨干</option>
					<option value="特级骨干" ${user.teacherLevel == '特级骨干' ? 'selected':''}>特级骨干</option>
					<option value="省级骨干" ${user.teacherLevel == '省级骨干' ? 'selected':''}>省级骨干</option>
					<option value="市级骨干" ${user.teacherLevel == '市级骨干' ? 'selected':''}>市级骨干</option>
					<option value="区县级骨干" ${user.teacherLevel == '区县级骨干' ? 'selected':''}>区县级骨干</option>
					<option value="校级骨干" ${user.teacherLevel == '校级骨干' ? 'selected':''}>校级骨干</option>
				</select>
			</div>
			
			<div class="unit">
				<label>出生日期：</label>
				<input type="text" name="birthday" class="date" minDate="1940-01-01" maxDate="now" value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' />"/>
			</div>
			<div class="unit">
				<label>身份证号：</label>
				<input type="text" name="idcard"  maxlength="18" value="${user.idcard }" />
			</div>
			<div class="unit">
				<label>教师证号：</label>
				<input type="text" name="cercode" maxlength="17" value="${user.cercode }" />
			</div>
			<div class="unit">
				<label>联系电话：</label>
				<input type="text" name="cellphone" value="${user.cellphone }" id="jy_dh_xg" class="mobile" size="30" maxlength="11" minlength="11"/>
			</div>
			<div class="unit">
				<label>邮编：</label>
				<input type="text" name="postcode" value="${user.postcode }"  size="30" maxlength="6" class="digits"/>
			</div>
			<div class="unit">
				<label>联系地址：</label>
				<textarea rows="3" cols="30" name="address"  maxlength="50">${user.address }</textarea>
			</div>
			<div class="unit">
				<label>个人简介：</label>
				<textarea rows="3" cols="30" name="remark"  maxlength="500">${user.remark }</textarea>
			</div>
			<div class="unit">
				<label>上传头像：</label>
					<div id="unit_pho_edit" style="display: none">
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
							onUploadSuccess:uploadunitph			 
						}"
						/> 
						<input id="unit_photo_edit" type="hidden" name="originalPhoto" value=""/>
		 				<input type="button" onclick="$('#logoFileInput').uploadify('upload', '*');" value="上传" style="float:right;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
						<script type="text/javascript">
							function uploadunitph(file, data, response){
								var data = eval('(' + data + ')');
								$("#unit_photo_edit").val(data.data);
								$("#picDiv_u_edit").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><div class='img_editblock' onclick='deleteunitpho(this);'></div>");
								$("#picDiv_u_edit").show();
								$("#unit_pho_edit").hide();
							}
						</script> 
					</div>
					<div id="picDiv_u_edit" style="clear:both;margin-left:128px;"> 
						<ui:photo src="${user.originalPhoto }" width='100' height='50' ></ui:photo><div class="img_editblock" onclick='deleteunitpho(this);'></div>
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

	function dialogAjaxDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadUnitUs();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteunitpho(obj){
		$("#unit_photo_edit").val("del");
		$(obj).parent().hide();
		$("#unit_pho_edit").show();
	}

</script>
