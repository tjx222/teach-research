<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="校际教研活动-草稿箱"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css"
	media="screen" />
</head>
<body>
	<div class="table_cont">
		<c:if test="${ !empty activityDraftList}">
			<table border=1>
				<tr>
					<th style="width: 428px;">活动主题</th>
					<th style="width: 125px;">操作</th>
				</tr>
				<c:forEach items="${activityDraftList.datalist}" var="activity">
					<tr data-id="${activity.id}" data-typeId="${activity.typeId }">
						<td title="${activity.activityName}">【${activity.typeName}】<a
							href="javascript:void(0);" class='td_name'><ui:sout
									value="${activity.activityName}" length="40"
									needEllipsis="true"></ui:sout></a>
						</td>
						<td><span title='继续编辑' class="continue_edit_btn"></span> <span
							title='删除' class='delete_btn' style="margin: 0;"></span></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty activityDraftList.datalist}">
			<div class="empty_wrap">
				<div class="empty_img"></div>
				<div class="empty_info" style="text-align: center;">草稿箱中还没有任何东西哟！</div>
			</div>
		</c:if>
		<div class="clear"></div>
		<div style="margin-top: 10px">
			<form name="pageForm" method="post">
				<ui:page url="${ctx}jy/schoolactivity/indexDraft"
					data="${activityDraftList}" />
				<input type="hidden" class="currentPage" name="page.currentPage">
			</form>
		</div>
	</div>

</body>
<script type="text/javascript"
	src="${ctxStatic }/modules/schoolactivity/js/activity_draft.js"></script>
</html>