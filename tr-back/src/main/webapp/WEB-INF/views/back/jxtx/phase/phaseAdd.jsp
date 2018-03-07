<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form id="phForm" method="post"  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<table class="table" width="100%" layoutH="60">
					<thead>
						<tr>
							<th width="60" style="text-align: center;">选择</th>
							<th width="240">代码</th>
							<th width="180">名称</th>
							<th width="180">显示顺序</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${iMetadataList }" var="meta" varStatus="st">
						<tr target="sid_user" rel="1">
							<td style="text-align: center;"><input class="mtid" value="${meta.id }" type="checkbox" name="mti" attrName="${meta.id }" class="required"></td>
							<td><input type="text" style="width: 168px" disabled="disabled" value="${meta.standardCode }"/></td>
							<td><input  value="${meta.name }" type="text" style="width: 149px;border-style: solid; border-width: 0" class="required" minlength="0" maxlength="10" id="mName" name="imate_name"></td>
							<td align="center"><input id="mSort" style="width: 190px;border-style: solid; border-width: 0" class="required digits" disabled="disabled" type="text" size="30" value="${st.count }"/></td>
						</tr>
					</c:forEach>
						
					</tbody>
				</table>
		<div class="formBar">
			<label style="float:left"><input type="checkbox" class="checkboxCtrl" group="mti" />全选</label>
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
		var isreturn = false;
		var paramObj = {};
		$("[class='mtid']:checked").each(function(i,obj){
			  var $td = $(obj).parents('tr').children('td');
			  $.each($td,function(j,inputObj){
				  var inp = $(inputObj).find('input');
				  if(j==0 && $(inp).val() != ""){
					  paramObj["metas["+i+"].eid"] = $(inp).val();
				  }
				  if($.trim($(inp).val())==""){
						 alertMsg.warn("选择项内容不能为空");
						 isreturn = true;
						 return false;
					}

				  if(j==2 && $(inp).val() != ""){
					  paramObj["metas["+i+"].name"] = $(inp).val();
				  }
				  
				  if(j==3 && $(inp).val() != ""){
					  paramObj["metas["+i+"].sort"] = $(inp).val();
				  }
			  });
		});
		if(isreturn){
			return false;
		}
		$.ajax({
			type : "POST",
			url  : "${ctx }jy/back/jxtx/save",
			data : paramObj,
			beforeSend : function() {
				return true;
			},
			success:function(){
				$.pdialog.closeCurrent();
				parent.reloadPhaseBox('3');
			}
		});
	}
		
</script>
