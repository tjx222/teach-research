<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent">
			<div class="pageFormContent" layoutH="58">
			    <div class="unit">
					<label>编号：</label>
					<h3>${aManage.id}</h3>
				</div>
				<c:if test="${aManage.parentid!=0}">
					<div class="unit">
							<label>父模块：</label>
							<h3>${pname}</h3>
					</div>
				</c:if>
				<div class="unit">
					<label>模块名称：</label> <label>${aManage.name}</label> 
				</div>
				<div class="unit">
					<label>所属类型：</label> 
					<c:if test="${aManage.sysRoleId==1}"><label>区域</label></c:if>
					<c:if test="${aManage.sysRoleId==2}"><label>学校</label></c:if>
					<c:if test="${aManage.sysRoleId==3}"><label>系统</label></c:if>
					
				</div>
				<div class="unit">
					<label>链接：</label> <label>${aManage.url}</label>
				</div>
				<div class="unit">
					<label>安全代码：</label> <label>${aManage.code}</label> 
				</div>
				<div class="unit">
					<label>是否固定：</label>
					<c:choose>
					<c:when test="${aManage.fixed==1}"><label>是</label></c:when>
					<c:otherwise><label>否</label></c:otherwise></c:choose>
				</div>
				<div class="unit">
					<label>打开方式：</label> 
					<c:choose>
					<c:when test="${empty aManage.target}"><label>当前页</label></c:when>
					<c:otherwise><label>新页面</label></c:otherwise></c:choose>
				</div>
				<div class="unit">
					<label>图标：</label>  
				</div>
				<div class="unit">
				    <label>顺序：</label>   <label>${aManage.sort}</label>
				</div>
				<div class="unit">
					<label>提示语：</label>  <label>${aManage.desc}</label>
				</div>

			</div>
			<div class="formBar">
				<ul>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">关闭</button>
							</div>
						</div></li>
				</ul>
			</div>
	</div>
