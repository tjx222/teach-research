<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅计划总结"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/ps_check.css" media="screen">
<link rel="stylesheet" type="text/css"  href="${ctxStatic }/modules/check_lecturerecords/css/check_lecturerecords.css"> 
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(window).scroll(function() {
			$("#kongdiv").toggle();
		});
	});
</script>
</head>
<body>
    <div class="jyyl_top">
		<ui:tchTop style="1" hideMenuList="false"></ui:tchTop>
    </div>
    <div class="jyyl_nav">
		当前位置：<jy:nav id="jhzj_check"></jy:nav>
	</div>
	<div class="clear"></div>
	<div class="wrap" style="margin-bottom:20px;">
		<div class="check_lesson">
			<div class="check_lesson_1">
				<a href="./jy/planSummaryCheck/teacher">
					<img src="${ctxStatic }/modules/planSummary/images/cy.png" alt="">
			</a>
		</div>
		<div class="check_lesson_2">
			<a href="./jy/planSummaryCheck/role/1374/planSummary">
				<img src="${ctxStatic }/modules/planSummary/images/cy1.png" alt="">
			</a>
		</div>
		<div class="check_lesson_3">
			<a href="./jy/planSummaryCheck/role/1375/planSummary">
				<img src="${ctxStatic }/modules/planSummary/images/cy2.png" alt="">
			</a>
		</div>
		<div class="check_lesson_4">
			<a href="./jy/planSummaryCheck/role/1373/planSummary">
				<img src="${ctxStatic }/modules/planSummary/images/cy3.png" alt="">
				</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
