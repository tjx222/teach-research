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
				<label>所属单位：</label>
				<span>
				<c:if test="${empty findOneSpace.orgId }">无</c:if>
				<c:if test="${!empty findOneSpace.orgId }">
				<jy:di key="${findOneSpace.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org"/>
					${org.name}
				</c:if>
				</span>
			</div>
			<div class="unit">
				<label>所属部门：</label>
				<span>
				<c:if test="${empty findOrgName.name }">无</c:if>
				<c:if test="${!empty findOrgName.name }">${findOrgName.name }</c:if>
				</span>
			</div>
			<div class="unit">
				<label>角色：</label>
				<span>${findOneRole.roleName}</span>
			</div>
			<div class="unit">
				<label>管辖部门：</label>
				<span>
				<c:if test="${empty gxbmName }">无</c:if>
				<c:if test="${!empty gxbmName }">${gxbmName }</c:if>
				</span>
			</div>
			<div class="unit">
				<label>学段：</label>
				<span>
				<c:if test="${empty findOnePhase.name }">无</c:if>
				<c:if test="${!empty findOnePhase.name }">${findOnePhase.name }</c:if>
				</span>
			</div>
			<div class="unit">
				<label>学科：</label>
				<span>
				<c:if test="${empty subject.name }">无</c:if>
				<c:if test="${!empty subject.name }">${subject.name}</c:if>
				</span>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>
