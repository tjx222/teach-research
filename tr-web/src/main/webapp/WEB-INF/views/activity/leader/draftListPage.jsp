<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="草稿箱"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="act_draft_content" id="wrap_draft">
	<div id="scroller">
		<c:forEach items="${activityDraftList.datalist}" var="activity">
			<div class="draft_list">
				<c:if test="${activity.typeId==1 }">
					<div class="draft_list_left">
						同<br />备<br />教<br />案
					</div>
				</c:if>
				<c:if test="${activity.typeId==2 }">
					<div class="draft_list_left1">
						主<br />题<br />研<br />讨
					</div>
				</c:if>
				<div class="draft_list_right">
					<h3><span class="title">${activity.activityName}</span></h3> 
					<ul>
						<li class="edit" activityId="${activity.id}" typeId="${activity.typeId}"><span>修改</span></li>
						<li class="del" activityId="${activity.id}" style="border-right:none;"><span>删除</span></li>
					</ul>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
</body>
<script type="text/javascript">
	require(['leader'],function(){	
		
	}); 
</script>
</html>