<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<body>
	<div class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<form method="post" action="${ctx }/jy/back/solution/goAddFw?id=${model.id}"
					class="pageForm required-validate" onsubmit="return navTabSearch(this);">
		<input type="hidden" value="${model.id }" name="id">
		<div class="panelBar">
			<span style="font-weight: bold;margin-left: 30px">学校名称：</span><input type="text" value="${model.name}" name="name">
			<span style="font-weight: bold;">所属区域：</span><input type="text" value="${model.areaName}" name="areaName">
			<input type="submit" value="搜索">
		</div>
	</form>
	<form method="post" action="${ctx }/jy/back/solution/addFw"
					class="pageForm required-validate"
					onsubmit="return validateCallback(this, dialogAjaxDone)">
		<table class="table" width="99%" layoutH="55">
			<thead>
				<tr align="center">
					<th>选择</th>
					<th>学校名称</th>
					<th>所属区域</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="org" items="${orlist.datalist }" >
				<tr align="center">
					<td><input type="checkbox" name="ids" value="${org.id }" name="checkFx"></td>
					<td>${org.shortName }</td>
					<td>${org.areaName }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="formBar">
				<ul>
					<li><div class="button">
							<div class="buttonContent">
								<button type="submit" >提交</button>
							</div>
						</div></li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div></li>
				</ul>
		</div>
		</form>
	</div>
	<script type="text/javascript">
	function dialogAjaxDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
				parent.reloadOrg_solution();
			}
		}
	}
</script>
</body>