<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div  id="deptBox">
	<div class="pageHeader" style="border:1px #B8D0D6 solid;width:800px">
		<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/zzjg/unitDeptInfoFind?parentId=${parentId}" method="post" onsubmit="return dialogSearch(this, 'ad_u');">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>部门名称：<input type="text"  name="serchUnit" alt="" value="${serchUnit }"/></td>
					<td><button type="submit">搜索</button></td>
				</tr>
			</table>
		</div>
			      <input type="hidden" name="order" value="" />
			      <input type="hidden" name="flago" value="" />
				  <input type="hidden" name="flags" value="" />
		</form>
	</div>
	<div class="pageContent">	
<%-- 		<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/zzjg/unitDeptInfoFind?parentId=${parentId}" method="post" onsubmit="return dialogSearch(this, 'ad_u');"> --%>
			<div class="panelBar">
				<ul class="toolBar">
					<li><a target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/unitAddDept?parentId=${parentId}&schName=${schName}" class="add"  rel="add_sch"  mask="true"><span>新建部门</span></a></li>
					<li class="line">line</li>
				</ul>
			</div>
			<c:choose>
				<c:when test="${empty orgList}">
					<div class="prompt" style="margin-top: 10px;margin-left: 74px;">
							<p>
								<span>
									没有相关信息呦!
								</span>
							</p>
						</div>
				</c:when>
				<c:otherwise>
					<table class="table" width="100%" layoutH="103" >
						<thead>
							<tr>
								<th width="160" >编号</th>
								<th width="160" >部门名称</th>
								<th width="160" >创建时间</th>
								<th width="160">操作</th>
							</tr>
						</thead>
						<tbody id="sch_info" style="overflow-y: auto; ">
							<c:forEach items="${orgList}" var="data" >
								<tr target="dept_obj" rel="${data.id}" data-id="${data.id}">
						             <td>${data.id}</td>
						             <td>${data.name}</td>
						             <td><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						             <td>
							             <a title="查看"  target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/lookDeptInfo?id=${data.id}&parentId=${parentId}" class="btnSee" mask="true"></a>
										 <a title="修改"  target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/unitEditDept?id=${data.id}&parentId=${parentId}" class="btn_Edit"  rel="edit_sch" mask="true"></a>
										 <a title="确定要删除吗？" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/zzjg/delDept?id=${data.id}"  class="btnDelete" callback="reloadUnitDeptBox"></a>
									 </td>
						        </tr>
					        </c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
<!-- 		</form> -->
	</div>

</div>	
	<script>
	function reloadUnitDeptBox(){
// 		var serchUnit = $("[name='serchUnit']").val();
		$.pdialog.reload(_WEB_CONTEXT_+"/jy/back/zzjg/unitDeptInfoFind", {data:{parentId:"${parentId}",serchUnit:'${serchUnit }'}, dialogId:"ad_unit_dept", callback:null})
	}
	</script>