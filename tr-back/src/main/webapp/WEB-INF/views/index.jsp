<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.cookie.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css" media="all">
<script type="text/javascript">
var _WEB_CONTEXT_ = '${pageContext.request.contextPath }';
if(self != top)
   top.location.replace(self.location);
</script>
<link rel="stylesheet"
	href="${ctxStatic }/modules/uc/login/css/login.css" media="screen">
<title>教研平台后台管理系统-登录</title>
</head>
<body>
	<shiro:user >
		<c:redirect url="/jy/uc/workspace"></c:redirect>
	</shiro:user>
	<shiro:guest>
	<div class="wraper" >
		<div class="wraper_w">
			<div class="login">
				<form id="login" action="jy/uc/login" method="post">
					<div style="text-align:center;width:493px;line-height:60px;height:56px;font-size:20px;font-weight:bold;">教研平台后台管理系统</div>
					<div style="margin-left: 362px;color:#fffe88;position: relative;width: 136px;">
						&nbsp;<span id="errmsg" style="position: absolute;top:34px;left:0px;color:#f00;height:20px;line-height: 16px;">${error }</span>
					</div>
					<div class="txt_wrap">
						<span class="text_bg"></span>
					 	<input type="text" name="username" class="validate[required,minSize[4]] txt" placeholder="用户名">
					</div>
					<div class="txt_wrap1">
						<span class="paw_bg"></span>
						<input type="password" class="validate[required,minSize[6]] txt" name="password" placeholder="密码">
					</div>
					<div class="clear"></div>
					<div class="check1">
					</div>
					<input type="submit" class="btn" value="">
					<!-- <a href="jy/uc/findps/retrievepassword" class="a_retrieve">找回密码</a> -->
				</form>
			</div>
			<ui:htmlFooter />
		</div>
	</div>
	</shiro:guest>
</body>
</html>