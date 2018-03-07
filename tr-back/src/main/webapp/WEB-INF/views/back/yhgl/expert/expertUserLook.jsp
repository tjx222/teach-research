<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileInput{float:left;}
#logoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#logoDiv button{margin-left:80px;margin-top:10px;}
</style>

<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>编号：</label>
				<label>${loginUser.id}</label>
			</div>
			<div class="unit">
				<label>账号：</label>
				<label>${loginUser.loginname }</label>
			</div>
			<div class="unit">
				<label>姓名：</label>
				<label>${user.name }</label>
			</div>
			<div class="unit">
				<label>昵称：</label>
				<label>${user.nickname }</label>
			</div>
			<div class="unit">
				<label>单位：</label>
				<h3>${user.orgName}</h3>
			</div>
			<div class="unit">
				<label>专业：</label>
				<h3>${user.cercode}</h3>
			</div>
			<div class="unit">
				<label>性别：</label>
				<c:if test="${user.sex==0}"><label>男</label></c:if>
				<c:if test="${user.sex==1}"><label>女</label></c:if>
			</div>
			<div class="unit">
				<label>职称：</label>
				<label>${user.profession }</label>
			</div>
			<div class="unit">
				<label>荣誉称号：</label>
				<label>${user.honorary }</label>
			</div>
			<div class="unit">
				<label>骨干教师：</label>
				<label>${user.teacherLevel}</label>
			</div>
			
			<div class="unit">
				<label>出生日期：</label>
				<label><fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' /></label>
			</div>
			<div class="unit">
				<label>身份证号：</label>
				<label>${user.idcard }</label>
			</div>
			<div class="unit">
				<label>联系电话：</label>
				<label>${user.cellphone }</label>
			</div>
			<div class="unit">
				<label>邮编：</label>
				<label>${user.postcode }</label>
			</div>
			<div class="unit">
				<label>联系地址：</label>
				<span>${user.address }</span>
			</div>
			<div class="unit">
				<label>个人简介：</label>
				<span>${user.remark }</span>
			</div>
			<div class="unit">
				<label>上传头像：</label>
				<div><ui:photo src="${user.originalPhoto }" width='50' height='50' ></ui:photo></div>
			</div>
			 
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>




<script type="text/javascript">
</script>
