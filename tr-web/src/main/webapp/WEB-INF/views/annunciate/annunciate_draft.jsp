<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader  title="通知公告-草稿箱"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/annunciate/css/notice.css" media="screen">
<ui:require module="annunciate/js"></ui:require>
</head>
<body style="background:#fff;">
	<div class="table_cont">
		<table border=1 >
			<tr>
				<th style="width:428px;">通知主题</th>
				<th style="width:125px;">操作</th>
			</tr>
			<c:forEach items="${draftlist.datalist}" var="d">
				<tr>
					<td>
						<span class='red_head'><c:if test="${d.redTitleId!=0}"><b></b></c:if></span>
						<c:choose>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
								【区域通知】<a class="td_name" href="${ctx}/jy/annunciate/release?id=${d.id}" title="${d.title}" target="_parent"><ui:sout value="${d.title}" length="40" needEllipsis="true" ></ui:sout></a>
							</c:when>
							<c:otherwise>
								<c:if test="${d.annunciateType==0}">【学校通知】<a class="td_name" href="${ctx}/jy/annunciate/release?id=${d.id}" title="${d.title}" target="_parent"><ui:sout value="${d.title}" length="40" needEllipsis="true" ></ui:sout></a></c:if>
								<c:if test="${d.annunciateType==1}">【区域通知】<a class="td_name" href="${ctx}/jy/annunciate/toForwardAnnunciate?id=${d.id}" title="${d.title}" target="_parent"><ui:sout value="${d.title}" length="40" needEllipsis="true" ></ui:sout></a></c:if>
							</c:otherwise> 
						</c:choose>
					</td>
					<td>
						<span title='继续编辑' class="continue_edit_btn" data-id="${d.id }" data-annunciateType="${d.annunciateType}" data-roleId="${_CURRENT_SPACE_.sysRoleId }"></span>
						<span title='删除'  class='delete_btn deleteDraft' data-id="${d.id }" data-status="${d.status }" style="margin:0;"></span>
					</td>
				</tr>
			</c:forEach>
		</table>
		<form  name="pageForm" method="post">
			<ui:page url="${ctx}jy/annunciate/draft" data="${draftlist}"  />
			<input type="hidden" class="currentPage" name="currentPage">
		</form>
	</div>
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','index'],function(){
		
	});
</script>
</html>