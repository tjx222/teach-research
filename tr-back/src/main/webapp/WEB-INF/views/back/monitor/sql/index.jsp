<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="monitor_sqlbox" >
<div class="pageHeader" style="padding-top:5px;">
	<form id="pagerForm" onsubmit="return divSearch(this, 'monitor_sqlbox');" action="${ctx}/jy/back/monitor/sql/sql" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td style="vertical-align:top">
					请输入SQL(不可恢复，谨慎执行)：
					<textarea name="sql" style="width: 500px;height: 100px">${sql }</textarea>
				</td>
				<td><div class="buttonActive" style="margin-left: 5px;"><div class="buttonContent"><button type="submit">执行</button></div></div></td>
			</tr>
		</table>
	</div>
	 <input type="hidden" name="page.currentPage" value="1" />
	 <input type="hidden" name="page.pageSize" value="${empty data.pageSize ?10:data.pageSize}" />
	 <input type="hidden" name="flago" value="" />
	 <input type="hidden" name="flags" value="" />
</form>
</div>
	<div class="pageContent" id="view"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
		</div>
		<c:choose>
			<c:when test="${not empty data}">
				<table class="table" width="100%" layoutH="200" rel="monitor_sqlbox">
					<thead>
						<tr>
						<c:forEach items="${names}" var="name">
							<th width="60" >${name }</th>
						</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${data.datalist}" var="map">
								<tr>
								<c:forEach items="${names}" var="name">
									<td >${map[name] }</td>
								</c:forEach>
								</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="panelBar">
					 <div class="pages">
						<span>显示</span>
						<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'monitor_sqlbox')">
							<option value="10" ${data.pageSize == 10 ? 'selected':''}>10</option>
							<option value="20" ${data.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${data.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${data.pageSize == 100 ? 'selected':''}>100</option>
						</select>
						<span>条，共${data.totalCount}条</span>
					  </div>
					 
				      <div class="pagination" rel="monitor_sqlbox"  totalCount="${data.totalCount }" numPerPage="${data.pageSize }" pageNumShown="10" currentPage="${data.currentPage }"></div>
				</div>
			</c:when>
			<c:when test="${not empty updateCount}">
				<div class="prompt" style="margin-top: 160px; ">
					<p>
						<span>执行成功，影响${updateCount } 条数据！ </span>
					</p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="prompt" style="background-image:none;margin-top: 16px;margin-left:20px;overflow:auto;" layoutH="180">
					<p>
						<span>${empty error ? '没有找到相关结果！':'出错了！<br/>' } ${error }</span>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
