<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent" id="xx_zzjg" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
		<div class="panelBar"><h3 style="text-align: center;line-height: 25px">直属校管理</h3></div>
		<c:choose>
			<c:when test="${empty dirInfoList.datalist}">
				<div class="prompt">
						<p>
							<span>
								没有相关信息呦!
							</span>
						</p>
					</div>
			</c:when>
			<c:otherwise>
				<table class="table" width="100%"  rel="dirt_load" layoutH="155">
					<thead>
						<tr>
							<th width="160" style="text-align: center;" orderField="id" class="${model.flago == 'id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
							<th width="160" orderField="name" class="${model.flago == 'name' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">学校名称</th>
							<th width="160" orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
							<th width="160">操作</th>
						</tr>
					</thead>
					<tbody id="sch_info" style="overflow-y: auto; ">
						<c:forEach items="${dirInfoList.datalist}" var="data">
							<tr target="sid_obj" rel="2" data-id="${data.id}">
					             <td width="140">${data.id}</td>
					             <td width="140">${data.name}</td>
					             <td width="140"><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					             <td width="140">
						             <a title="查看"  target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/lookdirSch?id=${data.id}" class="btnSee"></a>
									 <a title="修改"  target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/directInfoEdit?id=${data.id}" class="btn_Edit"  rel="edit_sch"></a>
									 <a title="确定要删除吗?"  target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/zzjg/delDirSch?id=${data.id}" callback="reloadZSXXBox"  class="btnDelete schDel" ></a>
								 </td>
					        </tr>
				        </c:forEach>
					</tbody>
				</table>
				<div class="panelBar">
					<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/zzjg/dirtSchFind?directAreaId=${model.directAreaId}" method="post" onsubmit="return divSearch(this, 'dirt_load');">
<!-- 					    <input type="hidden" name="pageNum" value="1" /> -->
<!-- 					    <input type="hidden" name="numPerPage" value="5" /> -->
					    <div class="panelBar">
							<div class="pages">
								<span>显示</span>
								<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'dirt_load')">
									<option value="10" ${dirInfoList.pageSize == 10 ? 'selected':''}>10</option>
									<option value="20" ${dirInfoList.pageSize == 20 ? 'selected':''}>20</option>
									<option value="50" ${dirInfoList.pageSize == 50 ? 'selected':''}>50</option>
									<option value="100" ${dirInfoList.pageSize == 100 ? 'selected':''}>100</option>
								</select>
								<span>条，共${dirInfoList.totalCount}条</span>
							  </div>
							  <input type="hidden" name="page.currentPage" value="1" />
						      <input type="hidden" name="page.pageSize" value="${dirInfoList.pageSize }" />
						      <input type="hidden" name="order" value="" />
						      <input type="hidden" name="flago" value="" />
							  <input type="hidden" name="flags" value="" />
						      <div class="pagination" rel="dirt_load" totalCount="${dirInfoList.totalCount }" numPerPage="${dirInfoList.pageSize }" pageNumShown="10" currentPage="${dirInfoList.currentPage }"></div>
						</div>
					</form> 
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	
	<script>
	function reloadZSXXBox(){
		$("#dirt_load").loadUrl(_WEB_CONTEXT_+"/jy/back/zzjg/dirtSchFind?directAreaId=${model.directAreaId}",null,function(){
			$("#dirt_load").find("[layoutH]").layoutH();
		} );
	}
	</script>