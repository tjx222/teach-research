<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <h3>${orgDir.id}</h3>
			</div>
			<div class="unit">
				<label>学校全称：</label>
				<label>${orgDir.name}</label>
			</div>
			<div class="unit">
				<label>直属区域：</label>
				<label>${areaDir.name}</label>
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<span id="dirLevelId">
				<c:if test="${orgDir.dirLevelId==0 }">省级</c:if>
				<c:if test="${orgDir.dirLevelId==1 }">市级</c:if>
				</span>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
