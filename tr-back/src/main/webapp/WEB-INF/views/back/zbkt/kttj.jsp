<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<form id="pagerForm" action="${ctx}jy/back/zbkt/kttj" method="post" onsubmit="return navTabSearch(this, 'zb_kttj');">
	<div class="pageHeader" style="padding-top:5px;">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<select class="combox" onchange="getZBSearchName(this)" name="searchName">
								<option value="id" <c:if test="${searchName=='id'}">selected="selected"</c:if>>课堂id</option>
								<option value="userName" <c:if test="${searchName=='userName'}">selected="selected"</c:if>>主持人</option>
								<option value="orgName" <c:if test="${searchName=='orgName'}">selected="selected"</c:if>>机构</option>
							</select>
						</td>
						<td>
							<input type="text" id="zbSearchName" name="${empty searchName?'id':searchName}" value="${searchValue}"/>
						</td>
						<td><div class="buttonActive" style="margin-left: 5px;"><div class="buttonContent"><button type="submit">搜索</button></div></div></td>
					</tr>
				</table>
			</div>
		
	</div>
	<table class="table" width="99%" layoutH="88">
		<thead>
			<tr>
				<th orderField="c.id" class="${model.flago == 'c.id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >课堂id</th>
				<th>课堂主题</th>
				<th>主持人</th>
				<th>机构</th>
				<th>课堂状态</th>
				<th orderField="c.startTime" class="${model.flago == 'c.startTime' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">课堂开始时间</th>
				<th orderField="c.endTime" class="${model.flago == 'c.endTime' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">课堂结束时间</th>
				<th>详细信息</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${classInfoList.datalist}" var="classInfo">
				<tr>
					<td>${classInfo.id}</td>
					<td>${classInfo.title}</td>
					<td>${classInfo.userName}</td>
					<td>${classInfo.flago}</td>
					<td>开始</td>
					<td>${classInfo.startTime}</td>
					<td>${classInfo.endTime}</td>
					<td>
						<a class="add" href="${ctx}jy/back/zbkt/kttjDetail?classId=${classInfo.id}" target="dialog" rel="yhjrsjDetail" mask="true" title="参会详情" style="width: 65px;">详细信息</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10" ${classInfoList.pageSize == 10 ? 'selected':''}>10</option>
				<option value="20" ${classInfoList.pageSize == 20 ? 'selected':''}>20</option>
				<option value="50" ${classInfoList.pageSize == 50 ? 'selected':''}>50</option>
				<option value="100" ${classInfoList.pageSize == 100 ? 'selected':''}>100</option>
			</select>
			<span>条，共${classInfoList.totalCount}条</span>
	  	</div>
		<input type="hidden" name="page.currentPage" value="${classInfoList.currentPage }" />
	    <input type="hidden" name="page.pageSize" value="${classInfoList.pageSize }" />
	    <input type="hidden" name="order" value="" />
	    <input type="hidden" name="flago" value="" />
		<input type="hidden" name="flags" value="" />
	    <div class="pagination" totalCount="${classInfoList.totalCount }" numPerPage="${classInfoList.pageSize }" pageNumShown="10" currentPage="${classInfoList.currentPage }"></div>
	</div>
</form>
</div>
<script type="text/javascript">
	function getZBSearchName(obj){
		$("#zbSearchName").attr("name",$(obj).val());
	}
</script>