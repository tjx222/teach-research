<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div id="hfggid">
	<div>
<div class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
		  	<li><a class="delete" href="${ctx}/jy/back/xxsy/hfgg/batchdelete" target="selectedTodo" rel="ids" postType="string" title="确定要删除吗？" callback="reloadhfgg"><span>批量删除</span></a></li>
			<li><a class="add" href="${ctx }/jy/back/xxsy/hfgg/toReleaseSchoolBanner?orgId=${orgId}"  width="450" height="520"  target="dialog" mask="true"><span>发布横幅广告</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<form id="pagerForm" action="jy/back/xxsy/hfgg/schoolBannerList" method="post" onsubmit="return validateCallback(this, reloadhfgg);">
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr align="center">
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="200" orderField="id" class="${schoolBanner.flago == 'id' ?  schoolBanner.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
				<th>图片名称</th>
				<th orderField="crtDttm" class="${schoolBanner.flago == 'crtDttm' ?  schoolBanner.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">发布时间</th>
				<th>作者</th>
				<th width="100"><a class="btnDisplay"/>表示已显示</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${schoolBannerList.datalist}" var="fd">
				<tr target="sid_obj" rel="${fd.id}" align="center">
				<td><input name="ids" value="${fd.id}" type="checkbox"></td>
					<td>${fd.id}</td>
					<td>${fd.attachsname}</td>
					<td><fmt:formatDate value="${fd.crtDttm}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td><jy:di var="user" key="${fd.crtId}" className="com.tmser.tr.uc.service.UserService"></jy:di>${user.name}</td>
					<td>
						<c:if test="${fd.isview eq 0}">
							<a title="您确定要在首页显示该横幅广告么？最多可以设置三张,同时显示如果设置过多，则最早设置的将被取消显示。" target="ajaxTodo" href="${ctx}/jy/back/xxsy/hfgg/isShowBanner?id=${fd.id}&&flag=1&&orgId=${orgId}"  class="btnDisplay2" callback="reloadhfgg" mask="true" width="780" height="600"></a>
						</c:if>
						<c:if test="${fd.isview eq 1}">
							<a title="确定取消显示 吗?" target="ajaxTodo" href="${ctx}/jy/back/xxsy/hfgg/isShowBanner?id=${fd.id}&&flag=0&&orgId=${orgId}"  class="btnDisplay" callback="reloadhfgg" mask="true" width="780" height="600"></a>
						</c:if>
					</td>
					 <td >
						 <a title="编辑" target="dialog" width="600" height="480" href="${pageContext.request.contextPath}/jy/back/xxsy/hfgg/toReleaseSchoolBanner?id=${fd.id}"  value="" class="btn_Edit"  rel="edit_sch"></a>
						 <a title="确定要删除吗？" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/xxsy/hfgg/batchdelete?ids=${fd.id}"   value="${fd.id}" class="btnDelete schDel" callback="reloadhfgg"></a>
					 </td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
	<input type="hidden" name="orgId" value="${orgId}" />
		<div class="panelBar">
		<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
						<option value="10" ${schoolBannerList.pageSize == 10 ? 'selected':''}>10</option>
						<option value="20" ${schoolBannerList.pageSize == 20 ? 'selected':''}>20</option>
						<option value="50" ${schoolBannerList.pageSize == 50 ? 'selected':''}>50</option>
						<option value="100" ${schoolBannerList.pageSize == 100 ? 'selected':''}>100</option>
					</select>
					<span>条，共${schoolBannerList.totalCount}条</span>
			  </div>
			 <input type="hidden" name="page.currentPage" value="1" />
		    <input type="hidden" name="page.pageSize" value="${schoolBannerList.pageSize }" />
		    <input type="hidden" name="order" value="" />
		    <input type="hidden" name="flago" value="" />
			<input type="hidden" name="flags" value="" />
			<input type="hidden" name="pageNum" value="1" /><!--当前页-->
		    <div class="pagination" rel="syggId" totalCount="${schoolBannerList.totalCount }" numPerPage="${schoolBannerList.pageSize }" pageNumShown="10" currentPage="${schoolBannerList.currentPage }"></div>
		</div>
		</form> 
</div>
</div>
</div>
<script type="text/javascript" >
	function reloadhfgg(){
		var orgId='${orgId}';
		$("#hfggid").loadUrl( _WEB_CONTEXT_+"/jy/back/xxsy/hfgg/schoolBannerList",{orgId:orgId},function(){
			$("#hfggid").find("[layoutH]").layoutH();
		}		
		);
	}
</script>