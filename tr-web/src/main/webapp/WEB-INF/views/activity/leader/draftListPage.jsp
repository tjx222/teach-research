<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="集体备课-草稿箱"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
<ui:require module="activity/js"></ui:require>
<script type="text/javascript">
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','activity'],function(){});
</script>
</head>
<body style="background:#fff;">
<div class="table_cont">
	<table border=1 >
		<tr>
			<th style="width:428px;">活动主题</th>
			<th style="width:125px;">操作</th>
		</tr>
		 <c:forEach items="${activityDraftList.datalist}" var="activity">
		<tr>
			<td>
				<span class='spanTd'>【${activity.typeName}】</span><a class='td_name td_width' onclick="editActivity('${activity.id}');" >${activity.activityName}</a>
			</td>
			<td>
				<span title='继续编辑' class="continue_edit_btn" onclick="editActivity('${activity.id}');"></span>
				<span title='删除' class='delete_btn' style="margin:0;" onclick="delActivityDraft('${activity.id}');" ></span>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
</body>

</html>