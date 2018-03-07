<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageHeader" style="border:1px #B8D0D6 solid;">
		<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/zzjg/schInfoFind" method="post" onsubmit="return divSearch(this, 'sch_load');">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>学校名称：<input type="text"  name="name" alt="" value="${selAreaName }"/>
					<td>机构类别：
					<select name="orgType" id="org_type" class="required">
						<option value="">请选择</option>
						<option value="0" ${model.orgType==0?'selected':'' }>会员校</option>
						<option value="1" ${model.orgType==1?'selected':'' }>体验校</option>
						<option value="2" ${model.orgType==2?'selected':'' }>演示校</option>
					</select>
					<td><button type="submit">搜索</button></td>
				</tr>
			</table>
		</div>
			      <input type="hidden" name="page.currentPage" value="1" />
			      <input type="hidden" name="page.pageSize" value="${schInfoList.pageSize }" />
 				  <input type="hidden" name="order" value="${model.flago } ${model.flags }" />
		     	  <input type="hidden" name="flago" value="${model.flago }" />
			      <input type="hidden" name="flags" value="${model.flags }" />
				  <input type="hidden" name="areaId" value="${model.areaId}" />
				  <input type="hidden" name="areaIds" value="${model.areaIds}" />
				  <input type="hidden" name="type" value="${model.type}" />
		</form>
	</div>
		<div class="pageContent" id="xx_info" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
					<div class="panelBar">
						<ul class="toolBar">
						<c:if test="${model.areaIds == null }">
							<li><a target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/addSchool?areaId=${model.areaId}" class="add" id="ad_sch_href"  rel="add_sch"  mask="true"><span>新建学校</span></a></li>
							<li><a class="add" href="${ctx }/jy/back/zzjg/batchRegisterSchool?areaId=${model.areaId}" target="dialog" mask="true"><span>批量注册学校</span></a></li>
						</c:if>	
							<li><a class="edit" href="${pageContext.request.contextPath}/jy/back/zzjg/deptInfoFind?parentId={sch_obj}&type=${type}" class="edit" id="ad_xx_dept" target="dialog" rel="ad_sch_dept" mask="true"><span>管理部门</span></a></li>
							<li class="line">line</li>
						</ul>
					</div>
				<c:choose>
					<c:when test="${empty schInfoList.datalist}">
						<div class="prompt">
								<p>
									<span>
										没有相关信息呦!
									</span>
								</p>
							</div>
					</c:when>
					<c:otherwise>
						<table class="table" width="100%"  rel="sch_load" layoutH="193">
							<thead>
								<tr>
									<th width="160" orderField="id" class="${model.flago == 'id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
									<th width="160" orderField="name" class="${model.flago == 'name' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">学校名称</th>
									<th width="" >所属区域</th>
									<th width="160" orderField="orgType" class="${model.flago == 'orgType' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">机构类别</th>
									<th width="160" orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
									<th width="160">操作</th>
								</tr>
							</thead>
							<tbody id="sch_info" style="overflow-y: auto; ">
								<c:forEach items="${schInfoList.datalist}" var="data">
									<tr target="sch_obj" rel="${data.id}" data-id="${data.id}">
							             <td >${data.id}</td>
							             <td >${data.name}</td>
							             <td >${data.areaName}</td>
							             <td >
							             <c:if test="${data.orgType ==0}">会员校</c:if> 
							             <c:if test="${data.orgType ==1}">体验校</c:if> 
							             <c:if test="${data.orgType ==2}">演示校</c:if> 
							             </td>
							             <td ><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							             <td >
								             <a title="查看" target="dialog"  href="${pageContext.request.contextPath}/jy/back/zzjg/lookSchoolInfo?id=${data.id}"  value="" class="btnSee"  rel="look_sch"></a>
											 <a title="编辑" target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/editSchoolInfo?id=${data.id}"  value="" class="btn_Edit"  rel="edit_sch"></a>
											 <a title="直属校设置"  target="dialog" href="${pageContext.request.contextPath}/jy/back/zzjg/directSchInfo?id=${data.id}" class="btnSet" rel="lishu_sch" value="${data.id}"></a>
											 <c:if test="${data.enable ==1}">
											 <a title="您确定要冻结${data.name}吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/zzjg/lockSch?id=${data.id}&enable=0" class="btnClose" rel="lock_sch" callback="sch_Done"></a>
											 </c:if> 
											 <c:if test="${data.enable ==0}">
											 <a title="您确定要启用吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/zzjg/lockSch?id=${data.id}&enable=1" class="btnOpen" rel="lock_sch" callback="sch_Done"></a>
											 </c:if> 
											 <a title="确认要删除吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/zzjg/delSchoolInfo?id=${data.id}"   value="${data.id}" class="btnDelete schDel" callback="sch_Done"></a>
										 </td>
							        </tr>
						        </c:forEach>
							</tbody>
					</table>
					<div class="panelBar">
						<div class="pages">
								<span>显示</span>
								<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'sch_load')">
									<option value="10" ${schInfoList.pageSize == 10 ? 'selected':''}>10</option>
									<option value="20" ${schInfoList.pageSize == 20 ? 'selected':''}>20</option>
									<option value="50" ${schInfoList.pageSize == 50 ? 'selected':''}>50</option>
									<option value="100" ${schInfoList.pageSize == 100 ? 'selected':''}>100</option>
								</select>
								<span>条，共${schInfoList.totalCount}条</span>
						  </div>
					      <div class="pagination" rel="sch_load" totalCount="${schInfoList.totalCount }" numPerPage="${schInfoList.pageSize }" pageNumShown="10" currentPage="${schInfoList.currentPage }"></div>
					</div>
				</c:otherwise>
			</c:choose>
	 </div>
	<script>
	 function reloadXXInfoBox(){
		$("#sch_load").loadUrl(_WEB_CONTEXT_+"/jy/back/zzjg/schInfoFind",{areaId:"${model.areaId}",name:'${selAreaName }',type:0,areaIds:"${model.areaIds}"},function(){
			$("#sch_load").find("[layoutH]").layoutH();
		} );
	}
	 function sch_Done(json){
			DWZ.ajaxDone(json);
			if (json.statusCode == DWZ.statusCode.ok){
				if ("closeCurrent" == json.callbackType) {
					reloadXXInfoBox();
				}
			}
		} 
	</script>