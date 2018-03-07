<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <label>${newOrg.id}</label>
			</div>
			<div class="unit">
		        <label>机构类别：</label>
			    <c:if test="${newOrg.orgType==0}"><label>会员单位</label></c:if>
				<c:if test="${newOrg.orgType==1}"><label>体验单位</label></c:if>
				<c:if test="${newOrg.orgType==2}"><label>演示单位</label></c:if>
			</div>
			<div class="unit">
				<label>部门名称：</label>
				<label>${newOrg.name}</label>
			</div>
			<div class="unit">
				<label>部门简称：</label>
				<label>${newOrg.shortName}</label>
			</div>
			<div class="unit">
				<label>所属区域：</label>
				&nbsp;&nbsp;<span>${newOrg.areaName}</span>
			</div>
			<div class="unit">
				<label>部门说明：</label>
				<label>${newOrg.note}</label>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
