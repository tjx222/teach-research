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
	<form method="post" action="${ctx }/jy/back/solution/addFw" class="pageForm" onsubmit="return beforeSubmit(this)">
		<div style="height: 40px; border-bottom: 1px #ccc solid;margin-bottom: 10px;line-height: 40px; padding-left: 5px;">
			<label style="color: #00f;font-size: 14px;">增加销售方案应用范围,考虑到销售方案的重要性，不允许在此取消应用范围</label>
		</div>
		<div style="height: 21px; line-height: 21px;  padding-left: 5px;">
		选择管理范围：
		</div>
		<div id="solution_orgTree" class="ztree" style="margin-top:0; width:275px;height:243px;overflow:auto;">
			
		</div>
		<div class="formBar">
			<button type="submit" style="width: 56px;height: 27px;margin-left:110px;"> 保存</button>
		</div>
	</form>
</div>
</body>
<script type="text/javascript">
 function beforeSubmit(obj){
	var $form = $(obj);
	var checkedNodes = solution_ztreeObj.getNodesByFilter(function(node){
		return (node.flag=='unit' && node.checked==true);
	});
	var checkedNodesArray = solution_ztreeObj.transformToArray(checkedNodes);
	var orgIdsStr = "";
	for(var i=0;i<checkedNodesArray.length;i++){
		orgIdsStr = orgIdsStr + checkedNodesArray[i].id+",";
	}
	if(orgIdsStr!=""){
		orgIdsStr = orgIdsStr.substring(0,orgIdsStr.length-1);
	}
	
	var _submitFn = function(){
		$.ajax({
			type: obj.method || 'POST',
			url:$form.attr("action"),
			data:{'ids':orgIdsStr,'solId':${model.id}},
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
	navTab.reload();
}

var solution_ztreeObj;
var url = "${ctx}/jy/back/yhgl/findUnit?type=0";
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
			chkboxType: { "Y": "ps", "N": "ps" }
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
						solution_ztreeObj.addNodes(treeNode, {id:obj.id, parentId:treeNode.id, name:obj.shortName,title:obj.name,flag:"unit"}); 
					}); 
				}
			}
		});
	}
}
var areaNodes = [];
$(document).ready(function(){
	loadZzjgOrgTree("solution_orgTree",settingbm,"area",true);
	solution_ztreeObj = $.fn.zTree.getZTreeObj("solution_orgTree");
	var solution_id = '${model.id}';
	if(solution_id!=''){
		$.ajax({ 
			async : false,  
	        cache:true,
	        dataType : "json",
	        data:{'id':solution_id},
			url : "${ctx}/jy/back/solution/getSolutionScope",
			success:callback
		});
	}
});

//展开并勾选已选择的机构
function callback(data){
	var scopeList = data;
	for(var i=0;i<scopeList.length;i++){
		var areaNode = solution_ztreeObj.getNodesByFilter(function(node){
			return (node.flag=='area' && node.id==scopeList[i].areaId);
		},true);
        if(areaNode.nocheck){
        	$.ajax({
        		async : false,
    			type : "post",
    			dataType : "json",
    			url : url, 
    			data : {"areaId":areaNode.id},
    			success : function(data){ 
    				//加入子节点并显示复选框
    				if(eval(data).length>0){
    					areaNode.nocheck = false;
    					$.each(data,function(index,obj){ 
    						solution_ztreeObj.addNodes(areaNode, {id:obj.id, parentId:areaNode.id, name:obj.shortName,title:obj.name,flag:"unit"}); 
    					});
    				}
    			}
    		});
        }
        //勾选机构节点
        var orgNode = solution_ztreeObj.getNodeByParam("id", scopeList[i].id, areaNode);
        solution_ztreeObj.checkNode(orgNode, true, true);
        solution_ztreeObj.setChkDisabled(orgNode,true,false,false);//不允许批量取消关联
	}
}
</script>
