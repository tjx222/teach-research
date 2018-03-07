<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
 <script type="text/javascript">
	/*  function rhm_deleteCallback(json) {
		alert(json.data);
		alert($("#rhmCurrentIndex").attr("currentIndex"));
		$("#rhmCurrentIndex").attr("currentIndex",json.data);
		
	}  */
</script>
</head>
<body>
	<div id="redHead" class="pageContent">
		<div class="tabs" currentIndex="0" id="rhmCurrentIndex"  eventType="click">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					  <%--   <c:forEach items="${uList}" var="u" varStatus="status">
						<li><a href="javascript:;"  data_id="${status.index}"><span>${u.orgName}</span></a></li>
						</c:forEach> --%>
						<li><a href="javascript:;"><span>${orgName}</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height: 500px;">
					<div>
						<div class="pageContent"
							style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
							<div class="panelBar">
								<ul class="toolBar">
									<li><a class="add" href="${ctx}/jy/back/xxsy/redhm/addOrEditRedHead?orgId=${orgId}" 
										target="dialog" mask="true"><span>添加红头</span></a></li>
									<li><a class="delete"
										href="${ctx }/jy/back/xxsy/redhm/deleteRedHead?id={sid_obj}"
										target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
									<li><a class="edit"
										href="${ctx }/jy/back/xxsy/redhm/addOrEditRedHead?id={sid_obj}&&orgId=${orgId}"
										target="dialog" mask="true"><span>修改</span></a></li>
									<li class="line">line</li>
								</ul>
							</div>
							<table id="table" class="table" width="50%" layoutH="300">
								<thead>
									<tr>
										<th width="50">红头列表</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${rhmList}" var="rhm">
									     <tr target="sid_obj" rel="${rhm.id}">
									         <td>${rhm.title}</td>
									     </tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
	<p>&nbsp;</p>
</body>
</html>