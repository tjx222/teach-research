<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageHeader" style="border:1px #B8D0D6 solid;">
		<form id="pagerForm" class="required-validate" action="${ctx}jy/back/yhgl/schoolUserIndex" method="post" onsubmit="return divSearch(this, 'yh_sch_load');">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				 	<td>
							编号：<input type="text" name="id" class="digits valid" style="width: 140px;" maxlength="10" value="${model.id }"/>
					</td>
					<td>
							账号：<input type="text" name="account" style="width: 140px;" maxlength="20" value="${model.account }"/>
					</td>
					<td>
							学校：<input type="text" name="orgName" style="width: 140px;" maxlength="20" value="${model.orgName }"/>
					</td>
					<td>
							姓名：<input type="text" name="name" style="width: 70px;" maxlength="5" value="${model.name }"/>
					</td>
					<td>
						<div class="buttonContent"><button type="submit" style="cursor: pointer;">搜索</button></div>
					</td>
				</tr>
			</table>
		</div>
	      <input type="hidden" name="page.currentPage" value="1" />
	      <input type="hidden" name="page.pageSize" value="${schuserlist.pageSize }" />
      	  <input type="hidden" name="order" value="${model.flago } ${model.flags }" />
		  <input type="hidden" name="flago" value="${model.flago }" />
		  <input type="hidden" name="flags" value="${model.flags }" />
		  <input type="hidden" name="orgId" value="${model.orgId }" />
		  <input type="hidden" name="areaId" value="${model.areaId }" />
		</form>
	</div>
	<div class="pageContent" id="xx_info" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
				<div class="panelBar">
					<ul class="toolBar">
						<li><a class="edit" href="${ctx}jy/back/yhgl/manageSchUserRole?userId={sch_user_id}" class="edit" id="ad_sch_user_role" target="dialog" rel="ad_sch_ro_us" mask="true"><span>身份管理</span></a></li>
					</ul>
				</div>
				<c:choose>
				<c:when test="${empty schuserlist.datalist}">
					<div class="prompt">
							<p>
								<span>
									没有相关信息呦!
								</span>
							</p>
						</div>
				</c:when>
				<c:otherwise>
				<table class="table" width="100%" layoutH="147" rel="yh_sch_load" >
					<thead>
						<tr>
							<th width="160" orderField="l.id" class="${model.flago == 'l.id' ?  model.flags == 'asc' ? 'asc' : 'desc' :'cansort'}">编号</th>
							<th width="160">账号</th>
							<th width="160" orderField="u.name" class="${model.flago == 'u.name' ?  model.flags == 'asc' ? 'asc' : 'desc' :'cansort'}">姓名</th>
							<th width="160" orderField="u.orgId" class="${model.flago == 'u.orgId' ?  model.flags == 'asc' ? 'asc' : 'desc' :'cansort'}">学校</th>
							<th width="160" orderField="u.crtDttm" class="${model.flago == 'u.crtDttm' ?  model.flags == 'asc' ? 'asc' : 'desc' :'cansort'}">创建时间</th>
							<th width="160">操作</th>
						</tr>
					</thead>
					<tbody id="sch_info" style="overflow-y: auto; ">
						<c:forEach items="${schuserlist.datalist}" var="data">
							<tr target="sch_user_id" rel="${data.id}" data-id="${data.id}">
					             <td >${data.id}</td>
					             <td >${data.loginname}</td>
					             <td >${data.name}</td>
					             <td >
					              <a href="${ctx}jy/back/yhgl/schoolUserIndex?orgId=${data.orgId}" target="ajax" rel="yh_sch_load">${data.orgName }</a>
					             </td>
					             <td ><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					             <td >
									 <a title="查看" mask="true" target="dialog"  href="${ctx}jy/back/yhgl/lookSchoolUser?orgId=${data.orgId}&id=${data.id}"  class="btnSee"  rel="look_sch_user"></a>
									 <a title="编辑" mask="true" target="dialog" href="${ctx}jy/back/yhgl/addOrEditSchoolUser?orgId=${data.orgId}&id=${data.id}" class="btn_Edit"  rel="edit_sch_user"></a>
									 <c:if test="${data.enable ==1}">
									 <a title="您确定要冻结账号${data.loginname}吗?" target="ajaxTodo" href="${ctx}jy/back/yhgl/djUser?id=${data.id}&enable=0" class="btnClose" rel="lock_sch_user" callback="schUserDone"></a>
									 </c:if> 
									 <c:if test="${data.enable ==0}">
									 <a title="您确定要启用吗?" target="ajaxTodo" href="${ctx}jy/back/yhgl/djUser?id=${data.id}&enable=1" class="btnOpen" rel="lock_sch" callback="schUserDone"></a>
									 </c:if> 
									 <a title="您确定要为账号${data.loginname}重置密码吗?" target="ajaxTodo"  href="${ctx}jy/back/yhgl/resetUserPass?id=${data.id}"   value="${data.id}" class="btnReset" callback="schUserDone"></a>
								 </td>
					        </tr>
				        </c:forEach>
					</tbody>
				</table>
				<div class="panelBar">
					 <div class="pages">
						<span>显示</span>
						<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'yh_sch_load')">
							<option value="10" ${schuserlist.pageSize == 10 ? 'selected':''}>10</option>
							<option value="20" ${schuserlist.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${schuserlist.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${schuserlist.pageSize == 100 ? 'selected':''}>100</option>
						</select>
						<span>条，共${schuserlist.totalCount}条</span>
					  </div>
					 
				      <div class="pagination" rel="yh_sch_load" totalCount="${schuserlist.totalCount }" numPerPage="${schuserlist.pageSize }" pageNumShown="10" currentPage="${schuserlist.currentPage }"></div>
				</div>
			</c:otherwise>
		</c:choose>
	 </div>
<script type="text/javascript">
	function schUserDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				reload_sch_users();
			}
		}
	}
	 function reload_sch_users(){
		$("#yh_sch_load").loadUrl(_WEB_CONTEXT_+"/jy/back/yhgl/schoolUserIndex",{orgId:"${model.orgId}",areaId:"${model.areaId}",orgName:"${model.orgName}",name:"${model.name}"},function(){
			$("#yh_sch_load").find("[layoutH]").layoutH();
		});
	}
</script>