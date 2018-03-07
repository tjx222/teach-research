<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" id="dept_add" action="${ctx }jy/back/zzjg/saveDept" class="pageForm required-validate" onsubmit="return unitDeptFormAdd(this);">
		<input type="hidden" value="${parentId}" name="parentId" id="addDept_info">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>部门名称：</label>
				<input name="name" id="ad_det" type="text" size="30" value="" class="val_name_ required" maxlength="20"/>
			</p>
			<p>
				<label>部门简称：</label>
				<input name="shortName" type="text" size="30" value="" maxlength="10" class="required"/>
			</p>
			<p>
				<label>所属单位：</label>
				<input id="deptName" type="text" readonly value="${org.areaName}${org.name}" size="30" />
			</p>
				<div class="unit">
				<label>部门说明：</label>
				<textarea rows="5" cols="28" maxlength="500" name="note"></textarea>
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
		<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
			<ul id="deptInfoEdit" class="ztree" style="margin-top:0; width:160px;"></ul>
		</div>



<script type="text/javascript">
	
	function dialogUnitDeptAdd(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadUnitDeptBox();
				$.pdialog.closeCurrent();
			}
		}
	}
	function unitDeptFormAdd(form) {
		var loginNameExist;
		var name = $.trim($(".val_name_").val());
		$.ajax({
			type:"post",
			async : false, 
			url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?parentId=${parentId}&type=2",
			data:{'name':name},
			dataType:"json",
			cache: false,
			success: function(data){
				loginNameExist=data;
			},
		});
		if (!loginNameExist && name!="") {
			alertMsg.confirm("单位部门名称重复");
			return false;
		}
		return validateCallback(form, dialogUnitDeptAdd);
	}
</script>
