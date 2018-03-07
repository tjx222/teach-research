<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
	<ui:htmlHeader title="管理记录"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/index.css" media="screen">
</head>
<body>
<div class="wrapper">
	<div class="jyyl_top"> 
		<ui:tchTop modelName="查阅集备"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="gljl"></jy:nav></h3>
	</div>
	<div class="clear"></div>
	<div class="Record_management">
			<div class="Record_management_l">
				<dl>
					<dd><a <c:if test="${checkCount !=0}"> href="./jy/managerecord/check" </c:if>></a></dd>
					<dt>
						<span>查阅数:${checkCount }</span>
						<span>查阅意见:${optionCount}</span>
					</dt>
				</dl>
			</div>
			<div class="Record_management_c">
				<dl>
					<dd><a <c:if test="${writeCount !=0}"> href="./jy/managerecord/tch" </c:if>></a></dd>
					<dt>
						<span>撰写数:${writeCount }</span>
						<span>分享数:${punishCount}</span>
					</dt>
				</dl>
			</div>
			<div class="Record_management_r">
				<dl>
					<dd><a <c:if test="${myCount !=0 || jionCount!=0}">  href="./jy/managerecord/jyhd" </c:if>></a></dd>
					<dt>
						<span>发起数:${myCount}</span>
						<span>分享数:${shareCount}</span>
						<span>参与数:${jionCount}</span>
					</dt>
				</dl>
			</div>

		</div>
	</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>