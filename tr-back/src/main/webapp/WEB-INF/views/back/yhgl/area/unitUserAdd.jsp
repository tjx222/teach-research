<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#ulogoFileInput{float:left;}
#ulogoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#logoDiv button{margin-left:80px;margin-top:10px;}
.img_editblock{position: absolute;  left: 230px;height:20px;top:15px}
</style>

<div class="pageContent">
	<form method="post" id="unit_user_save" action="${ctx }jy/back/yhgl/saveUnitUser" class="pageForm required-validate" onsubmit="return unitUserFormAdd(this);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>所属单位：</label>
				<c:if test="${empty org}">
					<select name="orgId" id="" style="width:203px;" class="required">
						<option value="">请选择</option>
						<c:forEach items="${orglist}" var="org">
							<option value="${org.id}">${org.name}</option>
						</c:forEach>
					</select>
				</c:if>
				<c:if test="${!empty org}">
					<label>${org.name}</label>
				</c:if>
			</div>
			<div class="unit">
				<label>账号：</label>
				<input name="loginname" id="unit_loginname" class="loginName_val required val_name_ val_email_user" minlength="5" maxlength="16" type="text" size="30" value=""  />
			</div>
			<div class="unit">
				<label>姓名：</label>
				<input name="name" id="unit_name" type="text" size="30" value="" class="required" maxlength="5"/>
			</div>
			<div class="unit">
				<label>昵称：</label>
				<input type="text" name="nickname"  maxlength="5" value="${user.nickname }"/>
			</div>
			<div class="unit">
				<label>性别：</label>
				<span><input type="radio" name="sex" value="0" ${user.sex == 0 ? 'checked':'' } class="required"/>男</span>
				<span><input type="radio" name="sex" value="1" ${user.sex == 1 ? 'checked':'' } checked class="required"/>女</span>
			</div>
			<div class="unit">
				<label>职称：</label>
				<input name="profession" id="unit_profession" type="text" size="30" value="" maxlength="6" />
			</div>
			<div class="unit">
				<label>教龄：</label>
				<input name="schoolAge" id="unit_schoolAge" type="text" size="30" value="" class="digits"/>
			</div>
			<div class="unit">
				<label>荣誉称号：</label>
				<input name="honorary" id="unit_honorary" type="text" size="30" value="" maxlength="15"/>
			</div>
			<div class="unit">
				<label>骨干教师：</label>
				<select name="teacherLevel" id="unit_teacherLevel" style="width:203px;">
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
				<input type="text" name="birthday" class="date" minDate="1940-01-01" maxDate="now" value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' />"/>
			</div>
			<div class="unit">
				<label>身份证号：</label>
				<input type="text" name="idcard"  maxlength="18" value="${user.idcard }" />
			</div>
			<div class="unit">
				<label>教师证号：</label>
				<input type="text" name="cercode" maxlength="17" value="" />
			</div>
			<div class="unit">
				<label>联系电话：</label>
				<input type="text" name="cellphone" id="jy_ph" size="30" class="mobile" maxlength="11" minlength="11"/>
			</div>
			<div class="unit">
				<label>邮编：</label>
				<input type="text" name="postcode"  size="30" maxlength="6" class="digits"/>
			</div>
			<div class="unit">
				<label>联系地址：</label>
				<textarea rows="3" cols="30" name="address"  maxlength="50"></textarea>
			</div>
			<div class="unit">
				<label>个人简介：</label>
				<textarea rows="3" cols="30" name="remark"  maxlength="500"></textarea>
			</div>
			<div class="unit">
				<label>上传头像：</label>
					<div id="unit_pho_div">
						<input style="width:300px" id="ulogoFileInput" type="file" name="file" 
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
							onUploadSuccess:uploadunitp			 
						}"
						/> 
						<input id="unit_photo" type="hidden" name="photo" value=""/>
						<input id="unit_originalPhoto" type="hidden" name="originalPhoto" value=""/>
		 				<input type="button" onclick="$('#ulogoFileInput').uploadify('upload', '*');" value="上传" style="float:right;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
						<script type="text/javascript">
							function uploadunitp(file, data, response){
								var data = eval('(' + data + ')');
								$("#unit_photo").val(data.data);
								$("#unit_originalPhoto").val(data.data);
								$("#picDiv_unit").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><div class='img_editblock' onclick='deleteunitphoto(this);'></div>");
								$("#picDiv_unit").show();
								$("#unit_pho_div").hide();
							}
						</script> 
					</div>
					<div id="picDiv_unit" style="clear:both;margin-left:128px;display:none"> 
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
	
	function yhdialogAja(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadUnitUs();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteunitphoto(obj){
		$("#unit_photo").val("");
		$("#unit_originalPhoto").val("");
		$(obj).parent().hide();
		$("#unit_pho_div").show();
	}
	$.validator.addMethod("loginName_val", function(value, element) {
		var mobilecheck = /[^\a-\z\A-\Z]/g;
		var sub = value.substring(0,1);
		return this.optional(element) || !mobilecheck.test(sub)
	}, "首位必须是字母");
	
	function unitUserFormAdd(form){
		var loginNameExist;
		var name = $.trim($(".val_name_").val());
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
		if (!loginNameExist && name!="") {
			alertMsg.confirm("区域用户账号重复");
			return false;
		}
		return validateCallback(form, yhdialogAja);
	}
</script>
