<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent">
		<form method="post" action="${ctx }/jy/back/monitor/app/saveApp"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, app_info_reload)">
			<div class="pageFormContent" layoutH="58">

				<c:if test="${not empty model.id}">
					<div class="unit">
				      <label>编号：</label>
					  <h3>${model.id}</h3>
					  <input type="hidden" name="id" value="${model.id}"/>
					</div>
			    </c:if>
				<div class="unit">
					<label>应用名称：</label>
					<input type="text" name="appname" size="40" maxlength="200"
						   class="required" value="${model.appname}"/>
				</div>
				<div class="unit">
					<label>应用访问账号：</label>
					<input type="text" name="appid" size="40" maxlength="200"
						   readonly value="${model.appid}"/>
				</div>
				<div class="unit">		   
				    <label>应用访问密匙：</label>
				    <input type="text" name="appkey" size="40" maxlength="200"
						   readonly value="${model.appkey}"/>
				</div>
				<div class="unit">
					<label>登录名前缀：</label>
					<input type="text" name="loginPre" size="40" maxlength="200" value="${model.loginPre}"/>
				</div>
				<div class="unit">		   
				    <label title="用户退出回调地址">退出地址：</label>
				    <input type="text" name="loginUrl" size="40" maxlength="200" value="${model.loginUrl}"/>
				</div>
				<div class="unit">		   
				    <label>信息不全跳转地址：</label>
				    <input type="text" name="url" size="40" maxlength="200" value="${model.url}"/>
				</div>
				<div class="unit">		   
				    <label>联系人电话：</label>
				    <input type="text" name="phone" size="20" maxlength="20" value="${model.phone}"/>
				</div>
				<div class="unit">		   
				    <label>联系人：</label>
				    <input type="text" name="code" size="20" maxlength="20" value="${model.contact}"/>
				</div>
				<div class="unit">	   
				    <label>对接方式：</label>
				    <select name="type" style="width:150px;" onchange="getAppParam(this)">
						<option value="">请选择</option>
						<option value="platform_callback" ${model.type=='platform_callback' ? 'selected="selected"' : ''}>平台回调对接</option>
						<option value="user_idcard" ${model.type=='user_idcard' ? 'selected="selected"' : ''}>身份证线下对接</option>
					</select>
				</div>
				<div id="load_app_param"></div>
				<div class="unit">		   
				    <label>是否有效：</label>
				    <input type="radio" name="enable" size="40" ${model.enable==1 ? 'checked':''} value="1"/>有效
				    <input type="radio" name="enable" size="40" ${model.enable==0 ? 'checked':''} value="0"/>无效
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

	<script type="text/javascript">
		$(function(){
			if("${model.type}"!=""){
				getAppParam(null,"${model.type}","${model.appid}");
			}
		})
		function getAppParam(obj,appType,appId){
			var type = obj==null?appType:$(obj).val();
			$("#load_app_param").load("${ctx}jy/back/monitor/app/getDataByType", {type:type,appid:appId}, function(){
			 });
		}
	</script>