<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
#templateFileInput{float:left;}
#icoFileInput{float:left;}
#templateFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#icoFileInput-queue{position: absolute;  left: 129px; top: 39px;}
.pageFormContent_l{width:300px;float:left;}
.pageFormContent_l div{height:28px;}
.cancel{position: absolute;right: 0px;top: 3px;}
.uploadify-progress{display:none;}
</style>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
	<form method="post" action="jy/back/yhgl/sys/saveUser" class="pageForm required-validate" onsubmit="return beforeSubmit_sysUser(this)">
		<input type="hidden" name="userId" value="${user.id }"/>
		<div class="pageFormContent" layoutH="56" style="padding:0;">
			<div class="pageFormContent1">
				<div class="pageFormContent_l">
					<div>
						<label>角色：</label>
						<select name="roleId">
							<c:if test="${!empty userId }">
								<option value="${role.id }">${role.roleName }</option>
							</c:if>
							<c:forEach var="role" items="${roleList }">
							<shiro:hasRole name="cjgly">
								<option value="${role.id }">${role.roleName }</option>
							</shiro:hasRole>
							<shiro:hasRole name="ywgly">
								<c:if test="${role.roleCode == 'xxgly' }">
									<option value="${role.id }">${role.roleName }</option>
								</c:if>
							</shiro:hasRole>
							</c:forEach>
						</select>
					</div>
					<div class="unit">
						<label>账号：</label>
						<input id="loginname" type="text" name="loginname" class="required loginName_val val_email_user" value="${login.loginname }" maxlength="20"/>
					</div>
					<div class="unit">
						<label>姓名：</label>
						<input type="text" name="name" class="required" value="${user.name }" maxlength="16"/>
					</div>
					<div class="unit">
						<label>性别：</label>
						男：<input type="radio" name="sex" class="required" value="0" <c:if test="${user.sex==0}">checked="checked"</c:if>/>
						女：<input type="radio" name="sex" class="required" value="1" <c:if test="${user.sex==1 || empty user.sex }">checked="checked"</c:if>/>
					</div>
					<div>
						<label>昵称：</label>
						<input type="text" name="nickname" value="${user.nickname }" maxlength="16"/>
					</div>
					<div class="unit">
						<label>出生日期：</label>
						<input type="text" name="birthday" value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>" readonly="readonly"  maxDate="%y-%M-%d" class="date"/>
						<a class="inputDateButton" href="javascript:;">选择</a>
					</div>
					<div>
						<label>身份证号：</label>
						<input type="text" name="idcard" value="${user.idcard }" maxlength="18"/>
					</div>
					<div class="unit">
						<label>邮件地址：</label>
						<input type="text" name="mail" value="${user.mail}" maxlength="32" class="email"/>
					</div> 
					<div class="unit">
						<label>联系电话：</label>
						<input type="text" name="cellphone" value="${user.cellphone}" maxlength="32" class="phone"/>
					</div>
					<div>
						<label>邮编：</label>
						<input type="text" name="postcode" value="${user.postcode}" maxlength="10"/>
					</div>
					<div>
						<label>联系地址：</label>
						<input type="text" name="address" value="${user.address}" maxlength="50"/>
					</div> 
					<div style="height:88px;">
						<label>个人简介：</label>
						<textarea name="explains" rows="5" cols="22" style="width:145px;" maxlength="1000">${user.explains}</textarea>
					</div>
				</div>
			</div> 
			<div style="clear:both;"></div>
			<div id="p_pic" style="margin-bottom:20px;width:379px;height:65px;position: relative;<c:if test="${!empty user.originalPhoto }">display: none;</c:if>">
				<label style="line-height:37px;">上传头像：</label>
				<input style="width:300px" id="icoFileInput" type="file" name="file" 
					uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'photo/xtyh/userIco'},
						buttonText:'请选择图片',
						fileSizeLimit:'1000KB',
						fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
						fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
						auto:false,
						multi:false,
						onUploadSuccess:uploadIco			
					}"
				/>
				<input type="button" onclick="$('#icoFileInput').uploadify('upload', '*');" style="width:50px;height:26px;float:left;margin-left:12px;margin-top:5px;" value="上传" />
				<input id="ico" type="hidden" name="originalPhoto" value=""/>
				<script type="text/javascript">
					function uploadIco(file, data, response){
						var data = eval('(' + data + ')');
						$("#ico").val(data.data);
						$("#picDiv").html("<img width='50' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><input type='button' onclick='deletePic(this);'/>");
						$("#picDiv").parent().show();
						$("#p_pic").hide();
					}
				</script>
			</div>
			<c:if test="${empty user.photo}">
				<div style="height:65px;margin-bottom:20px;" >
					<label style="line-height:33px;height:33px;">头像：</label>
					<div id="picDiv">
						<ui:photo src="${user.photo }" width='50' height='50' ></ui:photo><input type='button' onclick='deletePic(this);'/>
					</div>
				</div> 
		    </c:if>
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
function beforeSubmit_sysUser(obj){
	var $form = $(obj);
	
	if (!$form.valid()) {
		return false;
	}
	//检验用户名是否重复
	var loginname = $("#loginname").val();
	var loginNameExist;
	if(${empty user.id }){
		$.ajax({
			async : false, 
			dataType : "json",
			type: obj.method || 'POST',
			url:_WEB_CONTEXT_+"/jy/back/yhgl/sys/userExistOrNot",
			data:{'loginname':loginname},
			dataType:"json",
			cache: false,
			success: function(data){
				loginNameExist = data.isExist
			},
			error: function(){
				alert("系统错误");
				return false;
			}
		});
		if(loginNameExist){
			alert("用户名已存在！");
			return false;
		}
	}
	var _submitFn = function(){
		$.ajax({
			async : false, 
			type: obj.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: afterSysUserAdd,
			error: DWZ.ajaxError
		});
	}
	_submitFn();
	return false;
}
function afterSysUserAdd(json){
	alertMsg.correct(json[DWZ.keys.message])
	navTab.reloadFlag("system_user");
	$.pdialog.closeCurrent();
}
function deletePic(obj){
	$("#p_pic").show();
	$(obj).parent().parent().hide();
	$("#ico").val("del");
}
$.validator.addMethod("loginName_val", function(value, element) {
	var mobilecheck = /[^\a-\z\A-\Z]/g;
	var sub = value.substring(0,1);
	return this.optional(element) || !mobilecheck.test(sub);
}, "首位必须是字母");
</script>
