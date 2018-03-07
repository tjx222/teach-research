<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="资源查看"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
</head>
<body>
	<div class="wrap">
		<ui:tchTop hideMenuList="false" style="1"></ui:tchTop>
		<div class="wrap_top_2">
			<h3>
				当前位置：
				<jy:nav id="zyck">
					<jy:param name="name" value="同伴资源"></jy:param>
					<jy:param name="ckname" value="资源查看"></jy:param>
					<jy:param name="jxlwHref" value="jy/comres/index"></jy:param>
				</jy:nav>
			</h3>
		</div>
			<div class="resources_view_cont">
				<form action="jy/comres/tokensave">
				<input type="text" name="name"/>
					<ui:token />
					<input type="submit" value="save"/>
				</form>
		   </div>
	</div>
	<div class="clear"></div>
<ui:htmlFooter></ui:htmlFooter>
</body>
</html>
