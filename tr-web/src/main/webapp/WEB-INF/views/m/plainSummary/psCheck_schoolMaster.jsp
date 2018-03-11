<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="计划总结"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/common/css/index_m.css" media="screen" />
	<link rel="stylesheet" href="${ctxStatic }/m/plainSummary/css/plainSummary.css" media="screen">	
</head>
<body>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅计划总结
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="plainSummary_content">			
			<div class="check_lesson">
				<div class="check_lesson_1">
					<a href="${ctx }jy/planSummaryCheck/teacher">
						<img src="${ctxStatic }/m/plainSummary/images/cy.png" alt="">
					</a>
				</div>
				<div class="check_lesson_2">
					<a href="${ctx }jy/planSummaryCheck/role/1374/planSummary">
						<img src="${ctxStatic }/m/plainSummary/images/cy1.png" alt="">
					</a>
				</div>
				<div class="check_lesson_3">
					<a href="${ctx }jy/planSummaryCheck/role/1375/planSummary">
						<img src="${ctxStatic }/m/plainSummary/images/cy2.png" alt="">
					</a>
				</div>
				<div class="check_lesson_4">
					<a href="${ctx }jy/planSummaryCheck/role/1373/planSummary">
						<img src="${ctxStatic }/m/plainSummary/images/cy3.png" alt="">
					</a>
				</div>				
			</div>
		</div>
	</section>
</div>
</body>
</html>