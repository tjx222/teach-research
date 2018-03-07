<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" id="unit_info_add" action="${ctx }/jy/back/zzjg/saveUnit" class="pageForm required-validate" onsubmit="return unitFormEdit(this);">
		<input type="hidden" value="${orgUnit.areaId}" name="areaId" id="unitSelId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <h3>${orgUnit.id}</h3>
				  <input type="hidden" name="id" value="${orgUnit.id}"/>
			</div>
			<div class="unit">
				<label>机构类别：</label>
				<select name="orgType" style="width:203px;" class="required">
					<option value="">请选择</option>
					<option value="0" ${orgUnit.orgType==0 ? 'selected="selected"' : ''}>会员单位</option>
					<option value="1" ${orgUnit.orgType==1 ? 'selected="selected"' : ''}>体验单位</option>
					<option value="2" ${orgUnit.orgType==2 ? 'selected="selected"' : ''}>演示单位</option>
				</select>
			</div>
			<div class="unit">
				<label>单位全称：</label>
				<input name="name" id="" type="text" size="30" value="${orgUnit.name }" class="val_name_ required" maxlength="20"/>
			</div>
			<div class="unit">
				<label>单位简称：</label>
				<input type="text" name="shortName" value="${orgUnit.shortName }" size="30" maxlength="10"/>
			</div>
			
			<div class="unit">
				<label>所属区域：</label>
				<input id="citySel" type="text" readonly value="${areaName }" name="areaName" style="width:120px;"/>
				<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
			</div>
			<div class="unit">
				<label>单位说明：</label>
				<textarea rows="5" cols="28" maxlength="500" name="note">${orgUnit.note}</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="editTreeUnit" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>



<script type="text/javascript">
// 	var seclNodeId ;
	var settingEdit = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
// 				beforeClick: beforeClickEdit,
				onClick: onClickEdit
			}
		};
		
// 	function beforeClickEdit(treeId, treeNode) {
// 		var check = (treeNode && !treeNode.isParent);
// 		if (!check) alert("只能选择城市...");
// 		return check;
// 	}
	
	function onClickEdit(e, treeId, treeNode) {
		$("#unitSelId").val(treeNode.id);
		var zTree = $.fn.zTree.getZTreeObj("editTreeUnit"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v = nodes[0].name;
		}
		if (v.length > 0 ) v = v.substring(0, v.length);
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
	}
	
	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({left:"142" + "px", top:"175" + "px"}).slideDown("fast");
	
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	var zNode =[]; 
	var preSelId;
	$(document).ready(function(){
		preSelId = "${area.id}";
		$.ajax({ 
			type:"post",
			dataType : "json",
			url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
			success : function(data){ 
				$.each(data,function(n,d){ 
					zNode.push({ 
						id:d.id, 
						name:d.name, 
						pId:d.parentId 
					}) 
				}); 
				$.fn.zTree.init($("#editTreeUnit"), settingEdit, zNode);
			}
		});
	});
	
	function dialogAjaxDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadUnitInfoBox();
				$.pdialog.closeCurrent();
			}
		}
	}
	function unitFormEdit(form) {
		var loginNameExist;
		var name = $.trim($(".val_name_").val());
		$.ajax({
			type:"post",
			async : false, 
			url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${orgUnit.areaId}&type=1&sid=${orgUnit.id}",
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
