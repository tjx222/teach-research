<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.pageFormContent .unit label{text-align: right;}
	.unit span{position: relative;top: 4px;}
</style>

<div class="pageContent">
		<div class="pageFormContent" layoutH="56" style="padding:0;">
			<div class="unit">
				<label>所属学校：</label>
				<span>${org.areaName}</span>
			</div>
			<div class="unit">
				<label>账号：</label>
				<span>${login.loginname }</span>
			</div>
			<div class="unit">
				<label>姓名：</label>
				<span>${user.name }</span>
			</div>
			<div class="unit">
				<label>昵称：</label>
				<span>${user.nickname }</span>
			</div>
			<div class="unit">
				<label>性别：</label>
				<span>
					<c:if test="${user.sex==0 }">男</c:if>
					<c:if test="${user.sex==1 }">女</c:if>
				</span>
			</div>
			<div class="unit">
				<label>职称：</label>
				<span>${user.profession }</span>
			</div>
			<div class="unit">
				<label>教龄：</label>
				<span>${user.schoolAge }</span>
			</div>
			<div class="unit">
				<label>荣誉称号：</label>
				<span>${user.honorary }</span>
			</div>
			<div class="unit">
				<label>骨干教师：</label>
				<span>${user.teacherLevel }</span>
			</div>
			<div class="unit">
				<label>出生日期：</label>
				<span><fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd"/></span>
			</div>
			<div class="unit">
				<label>身份证号：</label>
				<span>${user.idcard }</span>
			</div>
			<div class="unit">
				<label>教师证号：</label>
				<span>${user.cercode }</span>
			</div>
			<div class="unit">
				<label>邮件地址：</label>
				<span>${user.mail }</span>
			</div>
			<div class="unit">
				<label>联系电话：</label>
				<span>${user.cellphone }</span>
			</div>
			<div class="unit">
				<label>邮编：</label>
				<span>${user.postcode }</span>
			</div>
			<div class="unit">
				<label>联系地址：</label>
				<span>${user.address }</span>
			</div>
			<div class="unit">
				<label>个人简介：</label>
				<textarea rows="5" cols="50" readonly="readonly" name="explains" maxlength="500" >${user.explains }</textarea>
			</div>
			<div class="unit">
				<label>头像：</label>
				<div><ui:photo src="${user.originalPhoto}" width="50" height="50"></ui:photo></div>
			</div>
			 
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
</div>




<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 400,width:600}}, $.pdialog.getCurrent(), "");
	$.validator.addMethod("mobile", function(value, element) {
		var mobilecheck = /^(13|15|18)\d{9}$/i;
		return this.optional(element) || mobilecheck.test(value);
	}, "手机号码格式不正确");
	function dialogAjaxDone(json){
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
	$(document).ready(function(){
		if("${user.teacherLevel}"!=""){
			$("#teacherLevel").val("${user.teacherLevel}");
		}
	});
</script>
