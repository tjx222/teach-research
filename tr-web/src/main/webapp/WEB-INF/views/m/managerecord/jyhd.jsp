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
			<div class="managerecord_check">
				<div class="managerecord_check_top">
					<header>
						<ul data-term="${term}">
							<li><a class="${term==0?'com_header_act':''}" data-term="0">上学期</a></li>
							<li><a class="${term==1?'com_header_act':''}" data-term="1">下学期</a></li>
						</ul>
					</header>
				</div>
				<div class="managerecord_check_bottom">
					<div id="chart"></div>
					<div id="chart_btn">
						<p class="p1"><a href="jy/managerecord/activity">查看详情</a></p>
						<p class="p1"><a href="jy/managerecord/school">查看详情</a></p> 
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','echarts/echarts','jyhd'],function($){	
	});  
</script>
</html>