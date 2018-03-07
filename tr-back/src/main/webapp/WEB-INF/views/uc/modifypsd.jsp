<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<style>
	#photoFileInput{text-align:center;margin:20px auto;float:none;}
	</style>
<div class="pageContent" style="border-color:#fff";>
<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>个人信息</span></a></li>
					<li><a href="javascript:;"><span>头像设置</span></a></li>
					<li><a href="javascript:;"><span>修改密码</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:470px;">
		<div>
			<form method="post" action="jy/uc/saveuserinfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
				<div class="pageFormContent" layoutH="58">
					<div class="unit">
						<label>账号：</label>
						<strong>${login.loginname }</strong>
					</div>
					<div class="unit">
						<label>姓名：</label>
						<input type="text" name="name" maxlength="20" class="required" value="${user.name }" />
					</div>
					<div class="unit">
						<label>性别：</label>
						<span><input type="radio" name="sex" value="0" ${user.sex == 0 ? 'checked':'' } />男</span>
						<span><input type="radio" name="sex" value="1" ${user.sex == 1 ? 'checked':'' } />女</span>
					</div>
					<div class="unit">
						<label>昵称：</label>
						<input type="text" name="nickname" maxlength="20" value="${user.nickname }"/>
					</div>
					<div class="unit">
						<label>出生日期：</label>
						<input type="text" name="birthday" class="date" minDate="1940-01-01" maxDate="now" readonly="true" value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' />"/>
					</div>
					<div class="unit">
						<label>身份证号：</label>
						<input type="text" name="idcard" class="idcard" value="${user.idcard }" />
					</div>
					<div class="unit">
						<label>邮件地址：</label>
						<input type="text" name="mail" class="email" value="${user.mail }" alt="请输入您的电子邮件"/>
					</div>
					<div class="unit">
						<label>联系电话：</label>
						<input type="text" name="cellphone" class="phone" alt="请输入您的电话或手机"/>
					</div>
					<div class="unit">
						<label>邮编：</label>
						<input type="text" name="postcode" class="postcode" value="${user.postcode }" name="minlength_maxlength10" maxlength="6"/>
					</div>
					<div class="unit">
						<label>联系地址：</label>
						<input name="field1" type="text" name="address" value="${user.address }"/>
					</div>
					<div class="unit">
						<label>个人简介：</label>
						<textarea name="explains" cols="50" rows="4">${user.explains }</textarea>
					</div>
						<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
					</div>
			</form>
		</div>
		<div style="text-align:center;height:309px;background:#fff;">
		<div class="pageFormContent" >
		<ui:photo src="${_CURRENT_USER_.photo }" width="128" height="134" />
		<input id="photoFileInput" type="file" name="file" 
			uploaderOption="{
				swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
				uploader:'${ctx }jy/manage/res/upload;jsessionid=<%=session.getId() %>',
				formData:{isWebRes:true,relativePath:'photo'},
				buttonText:'请选择图片',
				fileSizeLimit:'1000KB',
				fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
				fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
				auto:true,
				multi:false,
				onUploadSuccess:uploadphoto				
			}"
		/>
		<form method="post" id="photo_form"action="jy/uc/modifyPhoto" class="pageForm">
		 <div class="">
		 	<input type="hidden" name="photoPath" id="photoPath"/>
			<button type="button" onclick="$('#photoFileInput').uploadify('upload', '*');">确认修改</button>
		</div>
		</form>
		<script type="text/javascript">
		function uploadphoto(file, data, response){
			var data = eval('(' + data + ')');
			$("#photoPath").val(data.data);
			validateCallback($("#photo_form"), dialogAjaxDone);
		}
		</script>
		</div>
		</div>
	<div>
		<form method="post" action="jy/uc/modifypsd" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
			<div class="pageFormContent" layoutH="58">
				<div class="unit">
					<label>旧密码：</label>
					<input type="password" name="flago" size="30" minlength="6" maxlength="20" class="required" />
				</div>
				<div class="unit">
					<label>新密码：</label>
					<input type="password" id="cp_newPassword" name="password" size="30" minlength="6" maxlength="20" class="required alphanumeric"/>
				</div>
				<div class="unit">
					<label>重复输入新密码：</label>
					<input type="password" name="flags" size="30" equalTo="#cp_newPassword" class="required alphanumeric"/>
				</div>
			   <div class="">
				<ul style="margin-top:40px;margin-left:185px;">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				</ul>
			</div>
			</div>
		</form>
	</div>
	</div>
</div>
</div>
	