<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.List"%>
<%@page import="com.tmser.tr.uc.bo.UserSpace"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="身份空间禁用"></ui:mHtmlHeader>
</head>
<body>
<div id="wrapper">
	<section>
		<div class="identity_space_content">
			<div class="identity_space">
				<dl>
					<dd></dd>
					<dt>您的账号没有配置身份，或此账号的身份已被禁用，请联系管理员给此账号添加身份或解锁。<a style="color:blue;" href="${ctx }jy/uc/loginout">点击退出</a></dt>
				</dl>
			</div>
		</div>
	</section>
</div>
</body>
</html>