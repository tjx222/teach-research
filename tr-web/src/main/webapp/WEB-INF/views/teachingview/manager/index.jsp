<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.tmser.tr.uc.SysRole"%>
<c:set var="BKZZ" value="<%=SysRole.BKZZ%>"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教研一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
</head>
<body>
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教研一览"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl">
		</jy:nav>
	</div>
	<div class="index_content">
		<div class="index_title">
			教研一览
		</div>
		<div class="index_con">
			<ul>
			<c:if test="${roleId != BKZZ.id}">
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_t"><img src="${ctxStatic }/modules/teachingview/images/index_img1.png"/></a>
					<span>教师教研情况一览</span>
				</li>
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_g"><img src="${ctxStatic }/modules/teachingview/images/index_img2.png"/></a>
					<span>年级教研情况一览</span>
				</li>
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_s"><img src="${ctxStatic }/modules/teachingview/images/index_img3.png"/></a>
					<span>学科教研情况一览</span>
				</li>
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/m_user_list">
					<img src="${ctxStatic }/modules/teachingview/images/index_img4.png"/></a>
					<span>教学管理情况一览</span>
				</li>
			</c:if>
			<c:if test="${roleId == BKZZ.id}">
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_t"><img src="${ctxStatic }/modules/teachingview/images/index_img1.png"/></a>
					<span>教师教研情况一览</span>
				</li>
			</c:if>
			</ul>
			<div class="clear"></div>
		</div>
		<div class="index_bottom"></div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body> 
</html>
