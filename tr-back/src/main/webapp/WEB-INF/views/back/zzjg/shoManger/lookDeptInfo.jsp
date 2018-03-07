<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <label>${orgDept.id}</label>
			</div>
		
			<div class="unit">
				<label>部门名称：</label>
				<label>${orgDept.name}</label>
			</div>
			<div class="unit">
				<label>部门简称：</label>
				<label>${orgDept.shortName}</label>
			</div>
			<div class="unit">
				<c:if test="${orgParetDept.type==0}"><label>所属学校：</label></c:if>
				<c:if test="${orgParetDept.type==1}"><label>所属单位：</label></c:if>
				<label>${schAreaName}</label>
			</div>
			<div class="unit">
				<label>部门说明：</label>
				<label>${orgDept.note}</label>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
