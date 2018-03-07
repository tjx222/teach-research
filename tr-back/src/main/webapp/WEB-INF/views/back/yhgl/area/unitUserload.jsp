<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageHeader" style="border:1px #B8D0D6 solid;width:px">
		<c:if test="${empty org}">
			<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/yhgl/unitUserIndex" method="post" onsubmit="return divSearch(this, 'yh_unit_load');">
			<input type="hidden" style="width:118px" id="" name="areaId" alt="" value="${areaId}"/>
		</c:if>
		<c:if test="${!empty org}">
			<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/yhgl/unitUserIndex?orgId=${org.id}" method="post" onsubmit="return navTabSearch(this, 'area_user');">
			<input type="hidden" style="width:118px" id="" name="areaId" alt="" value="${areaId}"/>
		</c:if>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
							编号：<input type="text" style="width:118px" id="sr_id" name="id" alt="" value="${model.id}"/>
					</td>
					<td>
							姓名：<input type="text" style="width:118px" id="sr_name" name="name" alt="" value="${searchStr }"/>
					</td>
					<c:if test="${empty org}">
						<td>
							<label style="width: 50px;">所属单位:</label><select class="combox"  name="orgId" >
								<option value="">请选择单位</option>
								<c:forEach items="${orglist }" var="org">
									<option value="${org.id }" ${model.orgId==org.id ? 'selected':'' }>${org.name }</option>
								</c:forEach>
							</select>
						</td>
					</c:if>
					<td>
						<label style="width: 30px;">角色:</label><select class="combox"  name="roleId" >
							<option value="">请选择角色</option>
							<c:forEach items="${roleList }" var="role">
								<option value="${role.id }" ${selRoleId==role.id ? 'selected':'' }>${role.roleName }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="buttonContent"><button type="submit" style="cursor: pointer;">搜索</button></div>
					</td>
				</tr>
			</table>
		</div>
			     <input type="hidden" name="page.currentPage" value="1" />
			      <input type="hidden" name="page.pageSize" value="${unitUserlist.pageSize }" />
			       <input type="hidden" name="order" value="${model.flago} ${model.flags}" />
		          <input type="hidden" name="flago" value="${model.flago}" />
			       <input type="hidden" name="flags" value="${model.flags}" />
		</form>
	</div>
	<c:choose>
			<c:when test="${empty unitUserlist.datalist}">
				<c:if test="${empty org}">
					<div class="pageContent" id="unit_user_div" rel="yh_unit_load">
				</c:if>
				<c:if test="${!empty org}">
					<div class="pageContent" id="unit_user_div" >
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${empty org}">
					<div class="pageContent" id="unit_user_div" rel="yh_unit_load" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;width:px;">
				</c:if>
				<c:if test="${!empty org}">
					<div class="pageContent" id="unit_user_div" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;width:px;">
				</c:if>
			</c:otherwise>
	</c:choose>
					<div class="panelBar">
						<ul class="toolBar">
							<li><a target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/addOrEditUnitUser?areaId=${areaId}" class="add" id="ad_sch_href"  rel="add_sch"  mask="true"><span>新建账号</span></a></li>
							<li><a class="edit" href="${pageContext.request.contextPath}/jy/back/yhgl/unitUserRole?userId={unit_glId}" class="edit" id="ad_unit_role" target="dialog" rel="ad_unit_ro" mask="true"><span>身份管理</span></a></li>
							<c:if test="${empty org}">
								<li><a id="many_r" class="add" href="${ctx }/jy/back/yhgl/toUnitUserBatch?areaId=${areaId}" target="dialog" mask="true" width="580" height="300"><span>批量新建</span></a></li>
							</c:if>
							<c:if test="${!empty org}">
								<li><a id="many_r" class="add" href="${ctx }/jy/back/yhgl/toUnitUserBatch?orgId=${org.id}" target="dialog" mask="true" width="580" height="300"><span>批量新建</span></a></li>
							</c:if>
							<c:if test="${empty org}">
								<li><a href="${ctx}jy/back/yhgl/exportAreaUser?areaId=${areaId}&userType=1" target="dialog" width="580" height="300" class="icon" ><span>导出</span></a></li>
							</c:if>
							<c:if test="${!empty org}">
								<li><a href="${ctx}jy/back/yhgl/exportOrgUser?orgId=${org.id}&userType=1" width="580" height="300" class="icon" ><span>导出</span></a></li>
							</c:if>
							<li class="line">line</li>
							
							<li> <input type="hidden" id="sel_ut_sId" name="sysRoleId" value=""></li>
						</ul>
					</div>
					<c:choose>
					<c:when test="${empty unitUserlist.datalist}">
						<div class="prompt">
								<p>
									<span>
										没有相关信息呦!
									</span>
								</p>
							</div>
					</c:when>
					<c:otherwise>
					<c:if test="${empty org}">
						<table class="table" width="100%"  layoutH="145" rel="yh_unit_load">
					</c:if>
					<c:if test="${!empty org}">
						<table class="table" width="100%"  layoutH="115">
					</c:if>
						<thead>
							<tr>
								<th width="160" orderField="u.id" class="${model.flago == 'u.id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
								<th width="160" >账号</th>
								<th width="160" orderField="u.name" class="${model.flago == 'u.name' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">姓名</th>
								<th width="200" orderField="u.orgId" class="${model.flago == 'u.orgId' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">机构</th>
								<th width="160" orderField="u.crtDttm" class="${model.flago == 'u.crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
								<th width="160">状态</th>
								<th width="160">操作</th>
							</tr>
						</thead>
						<tbody id="unit_user_info" style="overflow-y: auto; ">
							<c:forEach items="${unitUserlist.datalist}" var="data">
								<tr target="unit_glId" rel="${data.id}" data-id="${data.id}">
						             <td >${data.id}</td>
						             <td >${data.nickname}</td>
						             <td >${data.name}</td>
						             <td >${data.orgName }</td>
						             <td ><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						             <td >
						             	<c:choose>
						             		<c:when test="${data.enable==1 }">未冻结</c:when>
						             		<c:otherwise>已冻结</c:otherwise>
						             	</c:choose>
									 </td>
						             <td >
							             <a title="查看" target="dialog"  href="${pageContext.request.contextPath}/jy/back/yhgl/lookUnitUser?areaId=${areaId}&id=${data.id}"  value="" class="btnSee"  rel="look_sch"></a>
										 <a title="编辑" target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/addOrEditUnitUser?areaId=${areaId}&id=${data.id}"  value="" class="btn_Edit"  rel="edit_u"></a>
										 <c:if test="${data.enable ==1}">
										 <a title="您确定要冻结账号${data.nickname}吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${data.id}&enable=0" class="btnClose" rel="lock_sch" callback="unitUserDone"></a>
										 </c:if> 
										 <c:if test="${data.enable ==0}">
										 <a title="您确定要启用吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${data.id}&enable=1" class="btnOpen" rel="lock_sch" callback="unitUserDone"></a>
										 </c:if> 
										 <a title="您确定要为账号${data.nickname}重置密码吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/yhgl/resetUserPass?id=${data.id}"   value="${data.id}" class="btnReset" callback="unitUserDone"></a>
									 </td>
						        </tr>
					        </c:forEach>
						</tbody>
					</table>
					<div class="panelBar">
						 <div class="pages">
							<span>显示</span>
							<c:if test="${empty org}">
								<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'yh_unit_load')">
							</c:if>
							<c:if test="${!empty org}">
								<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
							</c:if>
								<option value="10" ${unitUserlist.pageSize == 10 ? 'selected':''}>10</option>
								<option value="20" ${unitUserlist.pageSize == 20 ? 'selected':''}>20</option>
								<option value="50" ${unitUserlist.pageSize == 50 ? 'selected':''}>50</option>
								<option value="100" ${unitUserlist.pageSize == 100 ? 'selected':''}>100</option>
							</select>
							<span>条，共${unitUserlist.totalCount}条</span>
						  </div>
						 <c:if test="${empty org}">
						      <div class="pagination" rel="yh_unit_load" totalCount="${unitUserlist.totalCount }" numPerPage="${unitUserlist.pageSize }" pageNumShown="10" currentPage="${unitUserlist.currentPage }"></div>
						 </c:if>
						 <c:if test="${!empty org}">
						      <div class="pagination" targetType="navTab" totalCount="${unitUserlist.totalCount }" numPerPage="${unitUserlist.pageSize }" pageNumShown="10" currentPage="${unitUserlist.currentPage }"></div>
						 </c:if>
					</div>
		</c:otherwise>
		</c:choose>
	 </div>
	<script>
	 function reloadUnitUs(){
		 <c:if test="${empty org}">
		 	var ser_Name = $("#sr_name").val();
			$("#yh_unit_load").loadUrl(_WEB_CONTEXT_+"/jy/back/yhgl/unitUserIndex",{areaId:"${areaId}",roleId:"${selRoleId}",name:encodeURIComponent(ser_Name),userType:'1'},function(){
				$("#yh_unit_load").find("[layoutH]").layoutH();
			} );
		</c:if>
		 <c:if test="${!empty org}">
		 	navTab.reload(_WEB_CONTEXT_+"/jy/back/yhgl/unitUserIndex?orgId=${org.id}&roleId=${selRoleId}&userType=1", {navTabId:'area_user'});
		 </c:if>
		
	}
	 function unitUserDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				reloadUnitUs();
			}
		}
	} 
	</script>