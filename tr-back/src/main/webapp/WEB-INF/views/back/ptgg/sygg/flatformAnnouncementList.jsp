<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>
	<div id="syggId">
	<div>
<div class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
		  	<li><a class="delete" href="${ctx}/jy/back/ptgg/sygg/batchdelete" target="selectedTodo" rel="ids" postType="string" title="确定要删除吗？" callback="reloadsygg"><span>批量删除</span></a></li>&nbsp;&nbsp;
			<li><a class="add" href="${ctx }/jy/back/ptgg/sygg/toReleaseHomeAds" width="600" height="480"  target="dialog" mask="true"><span>发布首页广告</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/ptgg/sygg/flatformAnnouncementList" method="post" onsubmit="return validateCallback(this, reloadsygg);">
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr align="center">
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="200" orderField="id" class="${flatformAnnouncement.flago == 'id' ?  flatformAnnouncement.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
				<th>图片名称</th>
				<th orderField="cdate" class="${flatformAnnouncement.flago == 'cdate' ?  flatformAnnouncement.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">发布时间</th>
				<th>作者</th>
				<th width="100"><a class="btnDisplay"/>表示已显示</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${flatformAnnouncementList.datalist}" var="fd">
				<tr target="sid_obj" rel="${fd.id}" align="center">
				<td><input name="ids" value="${fd.id}" type="checkbox"></td>
					<td>${fd.id}</td>
					<td>${fd.pictureName}</td>
					<td><fmt:formatDate value="${fd.cdate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${fd.username}</td>
					<td>
					<c:if test="${fd.isview eq 0}">
							<a title="您确定要显示该首页广告么？显示后将替换原有的首页广告！" target="ajaxTodo" href="${ctx}/jy/back/ptgg/sygg/isShowpic?id=${fd.id}&&flag=1"  class="btnDisplay2" callback="reloadsygg" mask="true" width="780" height="600"></a>
						</c:if>
						<c:if test="${fd.isview eq 1}">
							<a title="您确定要不显示该首页广告么？不显示后将隐藏原有的图片广告！?" target="ajaxTodo" href="${ctx}/jy/back/ptgg/sygg/isShowpic?id=${fd.id}&&flag=0"  class="btnDisplay" callback="reloadsygg" mask="true" width="780" height="600"></a>
						</c:if>
					</td>
					 <td >
						 <a title="编辑" target="dialog" width="600" height="480" href="${pageContext.request.contextPath}/jy/back/ptgg/sygg/toReleaseHomeAds?id=${fd.id}"  value="" class="btn_Edit"  rel="edit_sch"></a>
						 <a title="确定要删除吗？" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/ptgg/sygg/batchdelete?ids=${fd.id}"   value="${fd.id}" class="btnDelete schDel" callback="reloadsygg"></a>
					 </td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
		<div class="panelBar">
		<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
						<option value="10" ${flatformAnnouncementList.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${flatformAnnouncementList.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${flatformAnnouncementList.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${flatformAnnouncementList.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${flatformAnnouncementList.totalCount}条</span>
			  </div>
			 <input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${flatformAnnouncementList.pageSize }" />
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
			<input type="hidden" name="pageNum" value="1" /><!--当前页-->
		    <div class="pagination" rel="syggId" totalCount="${flatformAnnouncementList.totalCount }" numPerPage="${flatformAnnouncementList.pageSize }" pageNumShown="10" currentPage="${flatformAnnouncementList.currentPage }"></div>
		</div>
		</form> 
</div>
</div>
</div>
</body>
<script type="text/javascript" >
	function reloadsygg(){
		var t = $("#syggId").find("input[id='title']").val();
		var _title = encodeURI(t);
		$("#syggId").loadUrl( _WEB_CONTEXT_+"/jy/back/ptgg/sygg/flatformAnnouncementList",{},function(){
			$("#syggId").find("[layoutH]").layoutH();
		}		
		);
	}
</script>