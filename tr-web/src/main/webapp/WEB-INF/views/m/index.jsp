<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="登录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/login/css/login.css" media="screen">
	<ui:require module="../m/login/js"></ui:require>
	<script type="text/javascript">
	if(self != top)
	   top.location.replace(self.location);
	</script>
</head>
<body>
<shiro:user >
	<c:redirect url="/jy/uc/workspace"></c:redirect>
</shiro:user>
<div class="mask"></div>
<div id="loginWrap">
	<section>
		<div class="login_wrap">
			<div class="head">
				<div class="head_bg"></div>
			</div>
			<div class="head_btm">
				<form id="login" action="jy/uc/login" method="post">	
					<div style="color:#f00;position: absolute;width: 100%;">
							&nbsp;<span id="errmsg" style="position: absolute;top: -24px;height: 19px;line-height: 14px;">${error }</span>
					</div> 
					 <input type="text" name="username" class="validate[required,minSize[4]] txt" placeholder="用户名" />
					<input type="password" class="validate[required,minSize[6]] pwd" name="password" placeholder="密码" />
 					<input type="submit" class="btn login_btn" value="登录" />  
					<div class="btn_login" style="display:none;">
						<div class="spinner login_spinner" >
						  <div class="rect1"></div>
						  <div class="rect2"></div>
						  <div class="rect3"></div>
						  <div class="rect4"></div>
						  <div class="rect5"></div>
						</div>
						<span>登录中...</span>
					</div>
					<!-- <p class="check" id="rememberPassword"><span class="class_span"></span><strong>记住密码</strong></p> -->
				</form>
			</div>
			<div class="head_btm1">
				<div class="head_btm1_l"><a href="#"></a></div>
				<div class="head_btm1_c">or</div>
				<div class="head_btm1_r"><!-- <a href="jy/uc/findps/retrievepassword" class="a_retrieve">忘记密码</a><span></span> --></div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'js'],function($){	
		
	}); 
	 /* $(function(){
		if($.cookie("_checked_")){
			$('input[name="username"]').val($.cookie("_username_"));
			$('input[name="password"]').val($.cookie("_password_"));
			$("#rememberPassword").prop("checked",true);
		}
		$('input[name="username"]').blur(function(){
			var newname = $(this).val();
			if($.cookie("_checked_")){
				var oldname = $.cookie("_username_");
				if(newname != oldname){
					$('input[name="password"]').val("");
					$("#rememberPassword").prop("checked",false);
				}
			}
		});
		$("#login").validationEngine('attach',{
			onValidationComplete: function(form, status){
				if(status){
					if($("#rememberPassword").prop("checked")){
						var nm = $('input[name="username"]').val();
						var ps = $('input[name="password"]').val();
						$.cookie("_username_",nm,{expires:7});
						$.cookie("_password_",ps,{expires:7});
						$.cookie("_checked_",true,{expires:7});
					}else{
						$.removeCookie("_username_");
						$.removeCookie("_password_");
						$.removeCookie("_checked_");
					}
					return true;
				}
			},scroll:false,onFieldFailure:function(){$("#errmsg").hide();
			}
		}); 
	});  */
</script>
</html>