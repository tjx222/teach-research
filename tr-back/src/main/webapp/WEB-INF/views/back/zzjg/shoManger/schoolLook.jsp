<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>    <h3>${newOrg.id}</h3>
			</div>
			<div class="unit">
				<label>机构类别：</label>
				<c:if test="${newOrg.orgType==0}"><label>会员校</label></c:if>
				<c:if test="${newOrg.orgType==1}"><label>体验校</label></c:if>
				<c:if test="${newOrg.orgType==2}"><label>演示校</label></c:if>
			</div>
			<div class="unit">
				<label>学校全称：</label>
				<label>${newOrg.name}</label>
			</div>
			<div class="unit">
				<label>学校简称：</label>
				<label>${newOrg.shortName}</label>
			</div>
			<div class="unit">
				<label>英文名称：</label>
				<label>${newOrg.englishName}</label>
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<label>${newOrg.areaName}</label>
			</div>
			<div class="unit">
				<label>学校类型：</label>
				<label>${schShip.name}</label>
			</div>
			<div class="unit">
				<label>所属学制：</label>
				<label>${xzShip.name}</label>
			</div>
			<div class="unit">
				<label>联系人：</label>
				<label>${newOrg.contactPerson}</label>
			</div>
			<div class="unit">
				<label>联系方式：</label>
				<label>${newOrg.phoneNumber}</label>
			</div>
			<div class="unit">
				<label>邮件：</label>
				<label>${newOrg.email}</label>
			</div>
			<div class="unit">
				<label>地址：</label>
				<label>${newOrg.address}</label>
			</div>
			<div class="unit">
				<label>学校网址：</label>
				<label>${newOrg.schWebsite}</label>
			</div>
			<div class="unit">
				<label>校徽：</label>
				<c:if test="${!empty newOrg.logo}">
				<div><img width='50' height='50' src='${ctx }jy/manage/res/download/${newOrg.logo}?isweb=true'></div>
				</c:if>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>



