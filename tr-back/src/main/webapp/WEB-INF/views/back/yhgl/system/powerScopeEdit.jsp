<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<style>
#templateFileInput{float:left;}
#icoFileInput{float:left;}
#templateFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#icoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
</style>
</head>
<body>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
	<form method="post" action="jy/back/yhgl/sys/savePowerScope" class="pageForm" onsubmit="return beforeSubmit(this)">
		
		<div style="height: 40px; border-bottom: 1px #ccc solid;margin-bottom: 10px;line-height: 40px; padding-left: 5px;">
			<label style="color: #00f;font-size: 14px;">角色：</label>
			<label style="color: #00f;font-size: 14px;">${role.roleName }</label>
			<c:if test="${role.sysRoleId == 2001 }">
				<input id="asorg" type="radio" name = "scope" value="1" checked/><label for="asorg">按机构</label>
				<input id="asarea" type="radio" name = "scope" value="0"/><label for="asarea">按区域</label>
			</c:if>
		</div>
		<div style="height: 21px; line-height: 21px;  padding-left: 5px;">
		选择管理范围：
		</div>
		<div id="backUserScope_orgTree" class="ztree" style="margin-top:0; width:275px;height:243px;overflow:auto;">
			
		</div>
		<div class="formBar">
			<button type="submit" style="width: 56px;height: 27px;margin-left:110px;"> 保存</button>
		</div>
	</form>
</div>
<c:if test='${jfn:checkSysRole(role.sysRoleId,"YWGLY") }'>
<input id="c_role" type="hidden" value="${pageContext.request.contextPath}/jy/back/yhgl/findUnit"/>
</c:if>
<c:if test="${jfn:checkSysRole(role.sysRoleId,'QYGLY') }">
<input id="c_role" type="hidden" value="${pageContext.request.contextPath}/jy/back/yhgl/findUnit?type=1"/>
</c:if>
<c:if test='${jfn:checkSysRole(role.sysRoleId,"XXGLY") }'>
<input id="c_role" type="hidden" value="${pageContext.request.contextPath}/jy/back/yhgl/findUnit?type=0"/>
</c:if>
</body>
<script type="text/javascript">

function beforeSubmit(obj){
	var $form = $(obj);
	var type= $('input:radio[name="scope"]:checked').val() == 0 ? 0:1;
	
	<c:if test="${role.sysRoleId == 2001 }">
		if(typeof type == 'undefined'){
			alert("请先选择分类");
			return false;
		}
	</c:if>
	var checkedNodes;
	if(type==0){
		checkedNodes = backUser_ztreeObj.getNodesByFilter(function(node){
			return (node.flag=='area' && node.checked==true && !node.isParent);
		});
	} else {
		checkedNodes = backUser_ztreeObj.getNodesByFilter(function(node){
			return (node.flag=='unit' && node.checked==true);
		});
		if(checkedNodes == null || checkedNodes.length <= 0){
			alert("请选择机构,单击区县节点加载机构！");
			return false;
		}
	}
	var checkedNodesArray = backUser_ztreeObj.transformToArray(checkedNodes);
	var idsStr = "";
	for(var i=0;i<checkedNodesArray.length;i++){
		idsStr = idsStr + checkedNodesArray[i].id+",";
	}
	if(idsStr!=""){
		idsStr = idsStr.substring(0,idsStr.length-1);
	}
	var _submitFn = function(){
		$.ajax({
			type: obj.method || 'POST',
			url:$form.attr("action"),
			data:{'idsStr':idsStr,'userId':${userId},'roleId':${role.id},'scope':type},
			dataType:"json",
			cache: false,
			success: afterEdit,
			error: DWZ.ajaxError
		});
	}
	_submitFn();
	return false;
}

function afterEdit(json){
	alertMsg.correct(json[DWZ.keys.message])
	$.pdialog.closeCurrent();
}

var backUser_ztreeObj;
var url = $("#c_role").val();
var settingbm = {
		data: {
			simpleData: {
				enable: true
			}
		},
		key:{
			name: "name",
			checked: "isChecked"
		},
		check: {
			chkStyle:"checkbox",
			chkDisabledInherit:false,
			enable: true,
			autoCheckTrigger: true,
			chkboxType: { "Y": "ps", "N": "s" }
		},
		callback: {
			beforeClick: beforeClickbm,
			onClick: onClickbm
		}
	};
	
function beforeClickbm(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	return check;
}

function onClickbm(e, treeId, treeNode) {
	var type= $('input:radio[name="scope"]:checked').val();
	if(${role.sysRoleId == 2001 }){
		if(typeof type == 'undefined'){
			alert('请先选择分类');
			return;
		}
	}
	if(type==0){
		treeNode.checked = true;
		backUser_ztreeObj.updateNode(treeNode);
		return;
	}
	if(treeNode.flag=="area"){
		$.ajax({
			type : "post",
			dataType : "json",
			url : url, 
			data : {"areaId":treeNode.id},
			success : function(data){ 
				//加入子节点并显示复选框
				if(eval(data).length>0){
					treeNode.nocheck = false;
					$.each(data,function(index,obj){ 
						backUser_ztreeObj.addNodes(treeNode, {id:obj.id, parentId:treeNode.id, name:obj.shortName,title:obj.name,flag:"unit"}); 
					}); 
				}
			}
		});
	}
}
var areaNodes = [];
function loadScopeTree(){
	$("#backUserScope_orgTree").html("");
	loadZzjgOrgTree("backUserScope_orgTree",settingbm,"area",true);
	backUser_ztreeObj = $.fn.zTree.getZTreeObj("backUserScope_orgTree");
	var userId = '${userId}';
	if(userId!=''){
		$.ajax({ 
			async : false,  
	        cache:true,
	        dataType : "json",
	        data:{'userId':userId},
			url : "${pageContext.request.contextPath}/jy/back/yhgl/sys/getManagescope", 
			success:callback
		});
	}
}
$(document).ready(function(){
	loadScopeTree();
	$('input:radio[name="scope"]').click(function(){
		var type = $(this).val();
		//如果选择了按区域，就将已经选择的学校节点删除
		if(type==0){
			var checkedNodes = backUser_ztreeObj.getNodesByFilter(function(node){
				return (node.flag=='unit');
			});
			for (var i = 0; i < checkedNodes.length; i++) {
				if(checkedNodes[i] != null) {
					backUser_ztreeObj.removeNode(checkedNodes[i]);
				}
			}
		}else{
			loadScopeTree();
		}
	});
	
});

//展开并勾选已选择的机构
function callback(data){
	var areaNode1 = backUser_ztreeObj.getNodesByFilter(function(node){
		return (node.flag=='area');
	});
	for(var i = 0; i < areaNode1.length; i++){
		areaNode1[i].nocheck = false;
		backUser_ztreeObj.updateNode(areaNode1[i]);
	}
	var scopeList = data.scopeList;
	for(var i=0;i<scopeList.length;i++){
		var areaNode = backUser_ztreeObj.getNodesByFilter(function(node){
			return (node.flag=='area' && node.id==scopeList[i].areaId);
		},true);
		if(scopeList[0].orgId != 0){
			$("#asorg").attr("checked",'checked');
		    $.ajax({
		        async : false,
		    	type : "post",
		    	dataType : "json",
		    	url : url, 
		    	data : {"areaId":areaNode.id},
		    	success : function(data){ 
		    		//加入子节点
		    		if(eval(data).length>0){
		    			$.each(data,function(index,obj){ 
		    				backUser_ztreeObj.addNodes(areaNode, {id:obj.id, parentId:areaNode.id, name:obj.shortName,title:obj.name,flag:"unit"}); 
		    			});
		    		}
		    	}
		    });
		    //勾选机构节点
		    var orgNode = backUser_ztreeObj.getNodeByParam("id", scopeList[i].orgId, areaNode);
		    backUser_ztreeObj.checkNode(orgNode, true, true);
		} else if (scopeList[0].orgId == 0) {
			$("#asarea").attr("checked",'checked');
			var areaNode = backUser_ztreeObj.getNodeByParam("id",scopeList[i].areaId);
			backUser_ztreeObj.checkNode(areaNode, true, true);
		}
        
	}
}
</script>
