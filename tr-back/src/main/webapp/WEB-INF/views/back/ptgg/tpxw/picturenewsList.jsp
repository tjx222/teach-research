<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>
<div id="tpxwid"><div>
<div class="pageContent j-resizeGrid" id="tpxw" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div style="float: left;padding-right: 20px;" class="panelBar">
		<ul class="toolBar">
			<li style="float: left;"><a class="delete" href="${ctx}/jy/back/ptgg/tpxw/batchdelete" target="selectedTodo" rel="ids" postType="string" title="确定要删除吗?" callback="reloadtpxw"><span>批量删除</span></a></li>&nbsp;&nbsp;&nbsp;&nbsp;
			<li><a class="add" href="${ctx }/jy/back/ptgg/tpxw/addPicturenews" width="780" height="600"  target="dialog" mask="true"><span>发布图片新闻</span></a></li>			
			<li class="line">line</li>
		</ul>
	</div>
		标题：<input id="title" type="text" value="">
		<input onclick="reloadtpxw()" type="button" value="搜索"/>
	<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/ptgg/tpxw/picturenewsList" method="post" onsubmit="return validateCallback(this, reloadtpxw);">
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr align="center">
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="200">编号</th>
				<th>标题</th>
				<th>发布时间</th>
				<th>作者</th>
				<th width="50">置顶</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pictureNewsList.datalist}" var="fd">
				<tr target="sid_obj" rel="${fd.id}" align="center">
				<td><input name="ids" value="${fd.id}" type="checkbox"></td>
					<td>${fd.id}</td>
					<td>${fd.title}</td>
					<td><fmt:formatDate value="${fd.crtDttm}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${fd.crtname}</td>
					<td>
						<c:if test="${fd.istop eq 0}">
						<a title="确认要置顶吗?" target="ajaxTodo" href="${ctx }/jy/back/xxsy/tpxw/setTop?id=${fd.id}&&flag=1"  class="btnTop" callback="reloadtpxw" mask="true" width="780" height="600"></a>
						</c:if>
						<c:if test="${fd.istop ne 0}">
							<a title="要取消置顶吗?" target="ajaxTodo" href="${ctx }/jy/back/xxsy/tpxw/setTop?id=${fd.id}&&flag=0"  class="btnTop2" callback="reloadtpxw" mask="true" width="780" height="600"></a>
						</c:if>
					</td>
					 <td >
						<a title="查看" target="dialog" href="${ctx}/jy/back/ptgg/tpxw/viewPictureNews?id=${fd.id}" value="" class="btnSee" callback="reloadtpxw" mask="true" width="780" height="600"></a>
						<a title="修改" target="dialog" href="${ctx}/jy/back/ptgg/tpxw/editPictureNews?id=${fd.id}" value="" class="btn_Edit" callback="reloadtpxw" mask="true" width="780" height="600"></a>
						<a title="确定要删除吗？" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/ptgg/tpxw/batchdelete?ids=${fd.id}"   value="${fd.id}" class="btnDelete" callback="reloadtpxw"></a>
						 <c:if test="${fd.isDisplay eq 0}">
							<a title="确认要发布吗?" target="ajaxTodo" href="${ctx }/jy/back/xxsy/tpxw/setView?id=${fd.id}&&flag=1"  class="btnPunish" callback="notice.release" mask="true" width="780" height="600"></a>
						</c:if>
						<c:if test="${fd.isDisplay ne 0}">
							<a title="要取消发布吗?" target="ajaxTodo" href="${ctx }/jy/back/xxsy/tpxw/setView?id=${fd.id}&&flag=0"  class="btnPunish2" callback="reloadtpxw" mask="true" width="780" height="600"></a>
						</c:if>
					 </td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
		<div class="panelBar">
			<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'tpxwid')">
						<option value="10" ${pictureNewsList.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${pictureNewsList.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${pictureNewsList.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${pictureNewsList.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${pictureNewsList.totalCount}条</span>
			  </div>
			<input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${pictureNewsList.pageSize }" />
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
			<input type="hidden" name="pageNum" value="1" /><!--当前页-->
			<div class="pagination" rel="tpxwid" totalCount="${pictureNewsList.totalCount }" numPerPage="${pictureNewsList.pageSize }" currentPage="${pictureNewsList.currentPage }"></div>
		</div>
		</form> 
</div></div></div>
</body>
<script type="text/javascript" >
	function reloadtpxw(){
		var t = $("#tpxw").find("input[id='title']").val();
		var _title = encodeURI(t);
		$("#tpxwid").loadUrl( _WEB_CONTEXT_+"/jy/back/ptgg/tpxw/picturenewsList",{title:_title},function(){
			$("#tpxwid").find("[layoutH]").layoutH();
		}		
		);
	}
	$(function(){
		$("#picnumid").html('${_count}')
	})
</script>