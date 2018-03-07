<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" id="unit_info_add" action="${ctx }jy/back/zzjg/saveUnit" class="pageForm required-validate" onsubmit="return unitFormAdd(this);">
		<input type="hidden" value="${areaId}" name="areaId" id="addUnit_info">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>机构类别：</label>
				<select name="orgType" id="org_type" style="width:203px;" class="required">
					<option value="">请选择</option>
					<option value="0">会员单位</option>
					<option value="1">体验单位</option>
					<option value="2">演示单位</option>
				</select>
			</div>
			<div class="unit">
				<label>单位全称：</label>
				<input name="name" id="" type="text" size="30" value="${orgDept.name }" class="val_name_ required" maxlength="20"/>
			</div>
			<div class="unit">
				<label>单位简称：</label>
				<input name="shortName" type="text" size="30" value="" maxlength="10" class="required"/>
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<input id="unitArea" type="text" name="areaName" readonly value="${selAreaName}" size="30" />
			</div>
			<div class="unit">
			<label>单位说明：</label>
			<textarea rows="5" cols="28" name="note" maxlength="500"></textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="button"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>



<script type="text/javascript">
	
	function dialogAjaxDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadUnitInfoBox();
				$.pdialog.closeCurrent();
			}
		}
	}
	function unitFormAdd(form) {
		var loginNameExist;
		var name = $.trim($(".val_name_").val());
		$.ajax({
			type:"post",
			async : false, 
			url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${areaId}&type=1",
			data:{'name':name},
			dataType:"json",
			cache: false,
			success: function(data){
				loginNameExist=data;
			},
		});
		if (!loginNameExist && name!="") {
			alertMsg.confirm("单位名称重复");
			return false;
		}
		return validateCallback(form, dialogAjaxDone);
	}
</script>
