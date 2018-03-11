<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="同伴互助"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/companion/css/companion.css" media="screen">
	<ui:require module="../m/companion/js"></ui:require> 
</head>
<body>
<form id="companionForm" action="${ctx }/jy/companion/companions/mycomps" method="post">
	<input type="hidden" value="false" name="iscare" >
	<input type="hidden" id="username" name="username" >
</form>
<div class="companion_content_r_l_top" >
	<div class="serch1">
		<input id="searchContent" type="search" class="search1" value="${username }"  placeholder="输入教师姓名进行查找">
		<input type="button" class="search1_btn">
	</div>
</div>
<div class="companion_content_r_l_bottom" id="expmenu_ex">
	<div id="scroller">
		<c:forEach items="${data.allCompMsg }" var="comp">
			<li class="checkbox_1">
				<jy:di key="${comp.userIdCompanion }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
				<div class="split" userid="${u.id }">
					<div class="split_l">
						<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
					</div>
					<div class="split_r">
						<h3>${u.name }</h3>
						<span><ui:sout value="${comp.lastMsg }" length="26" needEllipsis="true" ></ui:sout></span>
					</div>
				</div>
			</li>
		</c:forEach>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto","care"],function($){	
	});
</script>
</html>