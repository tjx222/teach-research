<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageHeader" style="padding-top:5px;">
		<form id="pagerForm" action="${pageContext.request.contextPath}/jy/back/yhgl/expertUserIndex?userType=3" method="post" onsubmit="return navTabSearch(this, 'expert_user');">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						姓名：<input type="text" name="name" value="${searchStr }"/>
					</td>
					<td>
						<div class="buttonContent"><button type="submit" style="cursor: pointer;">搜索</button></div>
					</td>
				</tr>
			</table>
		</div>
			    <input type="hidden" name="order" value="" />
			    <input type="hidden" name="flago" value="" />
				<input type="hidden" name="flags" value="" />
				<input type="hidden" name="page.currentPage" value="1" />
				<input type="hidden" name="page.pageSize" value="${expertUserlist.pageSize }" />
		</form>
	</div>
		<div class="pageContent" id="expert_user_div" >
					<div class="panelBar">
						<ul class="toolBar">
							<li><a target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/addOrEditExpertUser" class="add" id="ad_sch_href"  rel="add_sch"  mask="true"><span>新建账号</span></a></li>
							<li><a href="${ctx}jy/back/yhgl/exportOrgUser?userType=3" class="icon" ><span>导出</span></a></li>
							<li class="line">line</li>
							
							<li> <input type="hidden" name="pageNum" value="1" /><!--【必须】value=1可以写死--></li>
							<li> <input type="hidden" name="numPerPage" value="10" /><!--【可选】每页显示多少条--></li>
						</ul>
					</div>
					<c:choose>
					<c:when test="${empty expertUserlist.datalist}">
						<div class="prompt">
								<p>
									<span>
										没有相关信息呦!
									</span>
								</p>
							</div>
					</c:when>
					<c:otherwise>
					<table class="table" width="100%" layoutH="88" >
						<thead>
							<tr>
								<th width="160" orderField="id" class="${model.flago == 'id' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">编号</th>
								<th width="160">账号</th>
								<th width="160" orderField="name" class="${model.flago == 'name' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">姓名</th>
								<th width="160" orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
								<th width="160">状态</th>
								<th width="160">操作</th>
							</tr>
						</thead>
						<tbody id="unit_user_info" style="overflow-y: auto; ">
							<c:forEach items="${expertUserlist.datalist}" var="data">
								<tr target="unit_glId" rel="${data.id}" data-id="${data.id}">
						             <td >${data.id}</td>
						             <td >${data.nickname}</td>
						             <td >${data.name}</td>
						             <td ><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd HH:dd:mm"/></td>
						             <td >
						             	<c:choose>
						             		<c:when test="${data.enable==1 }">未冻结</c:when>
						             		<c:otherwise>已冻结</c:otherwise>
						             	</c:choose>
									 </td>
						             <td >
							             <a title="查看" target="dialog"  href="${pageContext.request.contextPath}/jy/back/yhgl/lookExpertUser?unitId=${data.orgId}&id=${data.id}"  value="" class="btnSee"  rel="look_sch"></a>
										 <a title="编辑" target="dialog" href="${pageContext.request.contextPath}/jy/back/yhgl/addOrEditExpertUser?id=${data.id}"  value="" class="btn_Edit"  rel="edit_u"></a>
										 <c:if test="${data.enable ==1}">
										 <a title="您确定要冻结账号${data.nickname}吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${data.id}&enable=0" class="btnClose" rel="lock_sch" callback="djExpert"></a>
										 </c:if> 
										 <c:if test="${data.enable ==0}">
										 <a title="您确定要启用吗?" target="ajaxTodo" href="${pageContext.request.contextPath}/jy/back/yhgl/djUser?id=${data.id}&enable=1" class="btnOpen" rel="lock_sch" callback="djExpert"></a>
										 </c:if> 
										 <a title="您确定要为账号${data.nickname}重置密码吗?" target="ajaxTodo"  href="${pageContext.request.contextPath}/jy/back/yhgl/resetUserPass?id=${data.id}"   value="${data.id}" class="btnReset" callback="djExpert"></a>
									 </td>
						        </tr>
					        </c:forEach>
						</tbody>
					</table>
					<div class="panelBar">
						 <div class="pages">
							<span>显示</span>
							<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
								<option value="10" ${expertUserlist.pageSize == 10 ? 'selected':''}>10</option>
								<option value="20" ${expertUserlist.pageSize == 20 ? 'selected':''}>20</option>
								<option value="50" ${expertUserlist.pageSize == 50 ? 'selected':''}>50</option>
								<option value="100" ${expertUserlist.pageSize == 100 ? 'selected':''}>100</option>
							</select>
							<span>条，共${expertUserlist.totalCount}条</span>
						  </div>
						  
					      <div class="pagination" targetType="navTab"  totalCount="${expertUserlist.totalCount }" numPerPage="${expertUserlist.pageSize }" pageNumShown="10" currentPage="${expertUserlist.currentPage }"></div>
					</div>
		</c:otherwise>
		</c:choose>
<!-- 		</form> -->
	 </div>
	<script>
	 function reloadExpertUs(){
		navTab.reload(_WEB_CONTEXT_+"/jy/back/yhgl/expertUserIndex?userType=3", {navTabId:'expert_user'});
	}
	 function djExpert(json){
		DWZ.ajaxDone(json);
		navTab.reload(_WEB_CONTEXT_+"/jy/back/yhgl/expertUserIndex?userType=3", {navTabId:'expert_user'});
	} 
	 $.validator.addMethod("mobile", function(value, element) {
			var mobilecheck = /^(13|15|18)\d{9}$/i;
			return this.optional(element) || mobilecheck.test(value);
		}, "手机号码格式不正确");
	 $(function(){
			$("#expert_user_div").find("[layoutH]").layoutH();
	 })
	</script>