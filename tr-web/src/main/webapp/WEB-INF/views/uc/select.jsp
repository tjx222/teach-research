<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="选择学段"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/school/css/school.css" media="screen">
	<ui:require module="../m/school/js"></ui:require>
</head>
<body>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		选择学段
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="select_learning_section">
			<div class="select_wrap">
				<ul>
					<c:forEach items="${phaseList }" var="phase">
						<li><a href="jy/school/mng/index?phaseId=${phase.id }"><ui:icon ext="${phase.eid }" width="211" height='211'></ui:icon></a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</section>
</div>
</body>
</html>