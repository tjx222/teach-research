<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageHeader" style="border:1px #B8D0D6 solid;">
		<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/zzjg/unitInfoFind?areaId=${model.areaId}&type=1" method="post" onsubmit="return divSearch(this, 'zzjg_load');">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>单位名称：<input type="text"  name="orgName" alt="" value="${searchStr }"/></td>
					<td>机构类别：
					<select name="orgType" id="org_type" class="required">
						<option value="">请选择</option>
						<option value="0" ${model.orgType==0?'selected':'' }>会员单位</option>
						<option value="1" ${model.orgType==1?'selected':'' }>体验单位</option>
						<option value="2" ${model.orgType==2?'selected':'' }>演示单位</option>
					</select>
					<td><button type="submit" id="search_unit">搜索</button></td>
				</tr>	
			</table>
		</div>
			      <input type="hidden" name="page.currentPage" value="1" />
			      <input type="hidden" name="page.pageSize" value="${unitInfoList.pageSize }" />
			      <input type="hidden" name="order" value="" />
			      <input type="hidden" name="flago" value="" />
				  <input type="hidden" name="flags" value="" />
				  <input type="hidden" name="pageNum" value="1" />
				  <input type="hidden" name="numPerPage" value="5" />
		</form>
	</div>
		<div class="pageContent" id="unit_info" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
				<div class="panelBar">
					<ul class="toolBar">
						<c:if test="${not empty model.areaId }">
							<li><a target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/addUnit?areaId=${model.areaId}" class="add" id="ad_unit_href"  rel="add_unit"  mask="true"><span>新建单位</span></a></li>
						</c:if>
						<li><a class="add" href="${pageContext.request.contextPath}/jy/back/zzjg/unitDeptInfoFind?parentId={unit_obj}&type=${model.type}" class="edit" id="ad_u" target="dialog" rel="ad_unit_dept" mask="true"><span>管理部门</span></a></li>
						<li class="line">line</li>
					</ul>
				</div>
			<c:choose>
				<c:when test="${empty unitInfoList.datalist}">
					<div class="prompt">
							<p>
								<span>
									没有相关信息呦!
								</span>
							</p>
						</div>
				</c:when>
				<c:otherwise>
					<table class="table" width="99%"  rel="zzjg_load" layoutH="155">
						<thead>
							<tr>
								<th width="100" orderField="id" class="${model.flago == 'id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
								<th width="100" orderField="name" class="${model.flago == 'name' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">单位名称</th>
								<c:if test="${empty model.areaId }">
								<th>所属区域</th></c:if>
								<th width="100" orderField="orgType" class="${model.flago == 'orgType' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">机构类别</th>
								<th width="100" orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
								<th width="100">操作</th>
							</tr>
						</thead>
						<tbody id="unit_info" style="overflow-y: auto; ">
							<c:forEach items="${unitInfoList.datalist}" var="data">
								<tr target="unit_obj" rel="${data.id}" data-id="${data.id}">
						             <td >${data.id}</td>
						             <td >${data.name}</td>
						             	<c:if test="${empty model.areaId }">
									<td>${data.areaName}</td></c:if>
						             <td >
						              	 <c:if test="${data.orgType ==0}">会员单位</c:if> 
							             <c:if test="${data.orgType ==1}">体验单位</c:if> 
							             <c:if test="${data.orgType ==2}">演示单位</c:if>
						             </td>
						             <td ><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						             <td >
							             <a title="查看单位" target="dialog"  href="${pageContext.request.contextPath}/jy/back/zzjg/lookUnitInfo?id=${data.id}"  value="" class="btnSee"  rel="look_sch"></a>
										 <a title="编辑" target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/editUnit?id=${data.id}"  value="" class="btn_Edit"  rel="edit_sch"></a>
										 <a title="确认要删除吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/zzjg/delUnitInfo?id=${data.id}"   value="${data.id}" class="btnDelete schDel" callback=unit_Done></a>
									 </td>
						        </tr>
					        </c:forEach>
						</tbody>
					</table>
					<div class="panelBar">
							<div class="pages">
								<span>显示</span>
								<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'zzjg_load')">
									<option value="10" ${unitInfoList.pageSize == 10 ? 'selected':''}>10</option>
									<option value="20" ${unitInfoList.pageSize == 20 ? 'selected':''}>20</option>
									<option value="50" ${unitInfoList.pageSize == 50 ? 'selected':''}>50</option>
									<option value="100" ${unitInfoList.pageSize == 100 ? 'selected':''}>100</option>
								</select>
								<span>条，共${unitInfoList.totalCount}条</span>
						  </div>
					      <div class="pagination" rel="zzjg_load" totalCount="${unitInfoList.totalCount }" numPerPage="${unitInfoList.pageSize }" pageNumShown="10" currentPage="${unitInfoList.currentPage }"></div>
					</div>
				</c:otherwise>
			</c:choose>
	</div>
	<script>

	 function reloadUnitInfoBox(){
		$("#zzjg_load").loadUrl(_WEB_CONTEXT_+"/jy/back/zzjg/unitInfoFind",{areaId:"${model.areaId}",name:'${searchStr }',type:1},function(){
			$("#zzjg_load").find("[layoutH]").layoutH();
		}  );
	}
	 function unit_Done(json){
			DWZ.ajaxDone(json);
			if (json.statusCode == DWZ.statusCode.ok){
				if ("closeCurrent" == json.callbackType) {
					reloadUnitInfoBox();
				}
			}
		} 
	</script>