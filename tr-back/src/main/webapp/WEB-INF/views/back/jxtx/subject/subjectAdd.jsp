<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent">
	<form id="phForm" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<table class="table" width="100%" layoutH="60">
					<thead>
						<tr>
							<th width="60" style="text-align: center;">选择</th>
							<th width="240">代码</th>
							<th width="180">名称</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${iMetadataList }" var="meta" varStatus="st">
							<tr target="sid_user" rel="1">
								<td style="text-align: center;"><input class="mtid" value="${meta.id }" name="mti" type="checkbox" class="required"></td>
								<td>${meta.standardCode }</td>
								<td>${meta.name }</td>
							</tr>
						</c:forEach>
						
					</tbody>
				</table>
		<div class="formBar">
<!-- 			<label style="float:left"><input type="checkbox" class="checkboxCtrl" group="mti" />全选</label> -->
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onclick="saveDatameta();">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>


<script type="text/javascript">
	function saveDatameta(){
		var checkedElts = $(":checkbox[class='mtid']:checked");
		var idArray = [];
		var sendData = "";
		$.each(checkedElts,function(i,n){
			idArray.push(this.value);
		});
		sendData = idArray.join(",");
		$.ajax({
			type : "POST",
			url  : "${ctx }jy/back/jxtx/saveSubject",
			data : {"ids":sendData,"eid":"${eid}"},
			beforeSend : function() {
				return true;
			},
			success:function(){
				$.pdialog.closeCurrent();
				parent.reloadXkglBox("${eid }");
			}
		});
	}
	
// 	function dialogAjaxDone(json){
// 		DWZ.ajaxDone(json);
// 		if (json.statusCode == DWZ.statusCode.ok){
// 			if ("closeCurrent" == json.callbackType) {
// 				$.pdialog.closeCurrent();
// 				parent.reloadJbsxBox("${eid}");
// 			}
// 		}
// 	}
</script>
