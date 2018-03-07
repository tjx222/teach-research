<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent">
		<form method="post" action="${ctx }/jy/back/yygl/save"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, _app_ajaxCallback)">
			<div class="pageFormContent" layoutH="58">
				<div class="unit">
					 <c:if test="${not empty aManage}">
					     <label>编号：</label>
						 <h3>${aManage.id}</h3>
						 <input type="hidden" name="id" value="${aManage.id}"/>
					</c:if>
				</div>
				<div class="unit">	    
					<label>父模块：</label>
					<h3>${ap.name}</h3>
					<input type="hidden" name="sysRoleId" value="${empty id ? ap.sysRoleId : aManage.sysRoleId}"/>
					<input type="hidden" name="parentid" value="${empty id ? ap.id : aManage.parentid}"/>
				</div>
				<div class="unit">
					<label>模块名称：</label>
					<input type="text" name="name" size="30" maxlength="10"
						   class="required" value="${aManage.name}"/>
				</div>
				<div class="unit">		   
				    <label>是否支持pc：</label>
				    <input type="radio" name="isNormal" size="40" ${(empty aManage.isNormal || aManage.isNormal) ? 'checked':''}   class="required" value="true"/>是
				    <input type="radio" name="isNormal" size="40" ${not empty aManage.isNormal && !aManage.isNormal ? 'checked':''}  class="required" value="false"/>否
				</div>
				<div class="unit">		   
				    <label>是否支持平板：</label>
				    <input type="radio" name="isTablet" size="40" ${not empty aManage.isTablet && aManage.isTablet ? 'checked':''}   class="required" value="true"/>是
				    <input type="radio" name="isTablet" size="40" ${empty aManage.isTablet || !aManage.isTablet ? 'checked':''}  class="required" value="false"/>否
				</div>
				<div class="unit">		   
				    <label>是否支持移动端：</label>
				    <input type="radio" name="isMobile" size="40" ${not empty aManage.isMobile && aManage.isMobile ? 'checked':''}   class="required" value="true"/>是
				    <input type="radio" name="isMobile" size="40" ${empty aManage.isMobile || !aManage.isMobile ? 'checked':''}  class="required" value="false"/>否
				</div>	
				<div class="unit">		   
				    <label>链接：</label>
				    <input type="text" name="url" size="40" maxlength="120"
						   class="required" value="${aManage.url}"/>
				</div>
				<div class="unit">
					<label>安全代码：</label>
					<input type="text" name="code" size="10" maxlength="30"
						   class="required" value="${aManage.code}"/>
				</div>
				<div class="unit">		   
				    <label>是否固定：</label>
				    <input type="radio" name="fixed" size="40" ${aManage.fixed==1 ? 'checked':''} maxlength="80"  class="required" value="1"/>是
				    <input type="radio" name="fixed" size="40" ${aManage.fixed==0 ? 'checked':''} maxlength="80"  class="required" value="0"/>否
				</div>
				<div class="unit">		   
				    <label>打开方式：</label>
				    <input type="radio" name="target" size="40" ${empty aManage.target ? 'checked':''}  class="required" value=""/>当前页
				    <input type="radio" name="target" size="40" ${aManage.target=="_blank" ? 'checked':''}  class="required" value="_blank"/>新页面
				</div>
				<div class="unit">		   
				    <label>图标：</label>
				   <input name="imgSrc" type="file"/>
				</div>
				<div class="unit">		   
				    <label>顺序：</label>
				    <input type="text" name="sort" size="10" maxlength="10"
						   class="required digits" value="${aManage.sort}"/>
				</div>
				<div class="unit">		   
				    <label>提示语：</label>
				    <textarea  name="desc" size="40" maxlength="100" value="" cols="42" rows="3">${aManage.desc}</textarea>
				</div>

			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" style="margin-top: 0px;">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div></li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
