<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="管理记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/managerecord/css/managerecord.css" media="screen" />
	<ui:require module="../m/managerecord/js"></ui:require>	
</head>
<body> 
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>管理记录
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="managerecord_bottom_wrap">
			<div class="managerecord_top">
				<div class="managerecord_top_center">
					<input type="button" id="datacount" value="查看数据统计表" data-grade="${_CURRENT_SPACE_.gradeId}" data-subject="${_CURRENT_SPACE_.subjectId}">
				</div>
			</div>
			<div class="managerecord_bottom">
				<div class="managerecord_bottom_l">
					<div class="m_b_l_b"></div>
					<a <c:if test="${checkCount !=0}"> href="./jy/managerecord/check" </c:if>>查看详情</a>
					<span>查阅数:${checkCount }</span>
					<span>查阅意见:${optionCount}</span>
				</div>
				<div class="managerecord_bottom_c">
					<div class="m_b_l_b"></div>
					<a <c:if test="${writeCount !=0}"> href="./jy/managerecord/tch" </c:if>>查看详情</a>
					<span>撰写数:${writeCount }</span>
					<span>分享数:${punishCount}</span>
				</div>
				<div class="managerecord_bottom_r">
					<div class="m_b_l_b"></div>
					<a <c:if test="${myCount !=0 || jionCount!=0}">  href="./jy/managerecord/jyhd" </c:if>>查看详情</a>
					<span>发起数:${myCount}</span>
					<span>分享数:${shareCount}</span>
					<span>参与数:${jionCount}</span>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','js'],function($){	
	});  
</script>
</html>