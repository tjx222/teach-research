<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<div class="grid" id="show_yzResources"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add" href="${ctx}/jy/back/zygl/yz/goDaoRuZiYuan?id=${model.lessonId}"  id="d_zy" 
					target="dialog" mask="true" width="780" height="360"><span>导入资源</span></a></li>
				<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"  postType="string"  callback="reloadShow_yzResources"
					href="${ctx}/jy/back/zygl/yz/batchDelete" class="delete"><span>批量删除</span></a></li>
				<li><a class="edit" href="${ctx}/jy/back/zygl/yz/goEdit?id={receId}" target="dialog" mask="true"><span>修改</span></a></li>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="combox" name="resType" onchange="searchKtLx(this)">
							<option value="-1">请选择资源类型</option>
							<c:forEach items="${resourcesType }" var="resType">
								<option value="${resType.id }" ${model.resType == resType.id ? "selected='selected'" : '' }>${resType.name }</option>
							</c:forEach>
						</select>
				<li class="line">line</li>
			</ul>
		</div>
		<form id="pagerForm" action="${ctx}/jy/back/zygl/yz/show_zylist?lessonId=${model.lessonId}" method="post" onsubmit="return divSearch(this, 'resourcesByLid_zy');">
			<table class="table" width="99%" layoutH="160" rel="resourcesByLid_zy">
				<thead>
					<tr align="center">
						<th width="60"><input type="checkbox" onclick="quanX(this)">选择</th>
						<th width="80" orderField="id" class="${model.flago == 'id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
						<th width="60">资源类型</th>
						<th width="100">资源名称</th>
						<th width="60" orderField="qualify"  class="${model.flago == 'qualify' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">优质资源</th>
						<th width="160" orderField="uploadTime"  class="${model.flago == 'uploadTime' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">上传时间</th>
						<th width="60"  orderField="sort"  class="${model.flago == 'sort' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">显示顺序</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${data.datalist}" var="resources" varStatus="status">
						<tr align="center" target="receId" rel="${resources.id }">
							<td><input type="checkbox" name="ids" value="${resources.id}" ></td>
							<td>${resources.id}</td>
							<c:if test="${resources.resType == 0}">
								<td>教案</td>
							</c:if>
							
							<c:if test="${resources.resType == 1}">
								<td>课件</td>
							</c:if>
							
							<c:if test="${resources.resType == 2}">
								<td>习题</td>
							</c:if>
							
							<c:if test="${resources.resType == 3}">
								<td>素材</td>
							</c:if>
							
							<td><a href="${ctx }/jy/scanResFile?resId=${resources.resId }" target="_block">${resources.title }</a></td>
							<c:if test="${resources.qualify == 1 }">
								<td>是</td>
							</c:if>
							<c:if test="${resources.qualify == 0 }">
								<td>否</td>
							</c:if>
							<td>
								<fmt:formatDate value="${resources.uploadTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>${resources.sort}</td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
		<div class="panelBar">
			<div class="pages">
						<span>显示</span>
						<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'resourcesByLid_zy')">
							<option value="10" ${data.pageSize == 10 ? 'selected':''}>10</option>
							<option value="20" ${data.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${data.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${data.pageSize == 100 ? 'selected':''}>100</option>
						</select>
						<span>条，共${data.totalCount}条</span>
				  </div>
			    <input type="hidden" name="page.pageSize" value="${data.pageSize }" />
				<input type="hidden" name="page.currentPage" value="1" />
			    <input type="hidden" name="order" value="" />
			    <input type="hidden" name="flago" value="" />
				<input type="hidden" name="flags" value="" />
			    <div class="pagination" rel="resourcesByLid_zy" totalCount="${data.totalCount }" numPerPage="${data.pageSize }" pageNumShown="10" currentPage="${data.currentPage }"></div>
		</div>
		</form> 
	</div>
<script type="text/javascript">

		function reloadShow_yzResources(){
			var str = "${model.lessonId}";
			$("#resourcesByLid_zy").loadUrl(_WEB_CONTEXT_+"/jy/back/zygl/yz/show_zylist",{lessonId:str},function(){
				$("#resourcesByLid_zy").find("[layoutH]").layoutH();
			} );
		}

		
		function reloadResources(lx){
			var str = "${model.lessonId}";
			$("#resourcesByLid_zy").loadUrl(_WEB_CONTEXT_+"/jy/back/zygl/yz/show_zylist",{lessonId:str,resType:lx},function(){
				$("#resourcesByLid_zy").find("[layoutH]").layoutH();
			}  );
		}
		
      function quanX(obj){
    	  var a=document.getElementsByName("ids");
    	  if(obj.checked==false){
    		  for ( var i = 0; i < a.length; i++) {
  					a[i].checked=false;
  			}
    	  }else{
    		  for ( var i = 0; i < a.length; i++) {
					a[i].checked=true;
			}
    	  }
      }
      
      function searchKtLx(obj){
    	  if(obj.value != -1){
	    	  reloadResources(obj.value);
    	  }
      }
</script>