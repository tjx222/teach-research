<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>
	<div class="grid" id="tzggId" style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div style="float: left; padding-right: 20px;" class="panelBar">
			<ul class="toolBar">
				<li style="float: left;"><a class="delete" href="${ctx}/jy/back/ptgg/tzgg/batchdelete" target="selectedTodo" rel="ids" postType="string" title="确定要删除吗?" callback="reloadtzgg"><span>批量删除</span></a></li>
				<li><a class="add" href="${ctx }/jy/back/ptgg/tzgg/addNoticeAnnouncement" width="780" height="600" target="dialog" mask="true"><span>发布通知公告</span></a></li>
				<li class="line">line</li>
			</ul>
		</div>
		<input id="title" type="text" value=""> 标题：<input
		onclick="reloadtzgg()" type="button" value="搜索" />
		<form id="pagerForm"
			action="${pageContext.request.contextPath}/jy/back/ptgg/tzgg/toNoticeAnnouncementList"
			method="post" onsubmit="return validateCallback(this, reloadtzgg);">
			<table class="table" width="100%" layoutH="110">
				<thead>
					<tr align="center">
						<th width="22"><input type="checkbox" group="ids"
							class="checkboxCtrl"></th>
						<th>编号</th>
						<th >标题</th>
						<th>发布时间</th>
						<th >作者</th>
						<th >操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${noticeAnnouncementList.datalist}" var="fd">
						<tr target="sid_obj" rel="${fd.id}" align="center">
							<td><input name="ids" value="${fd.id}" type="checkbox"></td>
							<td>${fd.id}</td>
							<td>${fd.title}</td>
							<td><fmt:formatDate value="${fd.crtDttm}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td><jy:di var="user" key="${fd.crtId}" className="com.tmser.tr.uc.service.UserService"></jy:di>${user.name}</td>
							<td><a title="查看" target="dialog" href="${ctx}/jy/back/ptgg/tzgg/viewNoticeAnnouncement?id=${fd.id}" value="" class="btnSee" callback="reloadtzgg" mask="true" width="780" height="600"></a> 
								<a title="确定要删除吗？" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/ptgg/tzgg/batchdelete?ids=${fd.id}" value="${fd.id}" class="btnDelete schDel" callback="reloadtzgg"></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="panelBar">
			<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'tzggId')">
						<option value="10" ${noticeAnnouncementList.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${noticeAnnouncementList.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${noticeAnnouncementList.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${noticeAnnouncementList.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${noticeAnnouncementList.totalCount}条</span>
			  </div>
			<input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${noticeAnnouncementList.pageSize }" />
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
			<input type="hidden" name="pageNum" value="1" /><!--当前页-->
			<div class="pagination" rel="tzggId" totalCount="${noticeAnnouncementList.totalCount }" numPerPage="${noticeAnnouncementList.pageSize }" currentPage="${noticeAnnouncementList.currentPage }"></div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" >
	function reloadtzgg(){
		var _title = encodeURI($("#title").val());
		$("#tzggId").loadUrl( _WEB_CONTEXT_+"/jy/back/ptgg/tzgg/toNoticeAnnouncementList",{title:_title},function(){
			$("#tzggId").find("[layoutH]").layoutH();
		}		
		);
	}
	$(function(){
		$("#strongid").html('${_count}')
	})
</script>