<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<div id="unit_info"> 
	<ul class="info">
		<li style="height:30px;">
			<p>
				<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">搜索名称：</label>
				<input type="text" id="key_unit" value="" class="empty" style="width: 108px;float:left;margin-top: 4px;"/>
				<a class="btnLook" onclick="searZZjgUni()" style="margin-top: 4px;margin-left:5px;cursor:pointer;"></a><br/>
				
			</p>
		</li>
	</ul>
	<div class="pageContent" layoutH="10">
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
			<ul id="unit_Tree" class="ztree" layoutH="53"></ul>
		</div>
		<div id="operation_load" class="unitBox" style="margin-left:226px;">
			<div class="prompt" id="zzjg_prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	var settings_operation = {
		data: {
			key: {
				title: "name"
			},
			simpleData: {
				enable : true
			}				
		},
		callback : 
		{
			beforeClick: beforeClickArea,
			onClick: onclickArea
		}
	};

	function beforeClickArea(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		return check;
	}
	function onclickArea(event, treeId, treeNode){
		$("#operation_load").loadUrl("${pageContext.request.contextPath}/jy/back/operation/toOrgOperationInfoList?areaId="+treeNode.id,{},function(){
			$("#operation_load").find("[layoutH]").layoutH();
		});
    }
	
	$().ready(function(){
		loadZzjgOrgTree("unit_Tree",settings_operation,"operation");
	});
	function searZZjgUni(){
		var zzjg_unit_tree = [];
		searchAreaC("unit_Tree",settings_operation,"key_unit",zzjg_unit_tree,"operation");
	}
</script>
</html>