<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var searchState_publisher = true;
	var yhgl_sch_ztree;
	var setting_publisher_sch = {
		data: {
			key: {title: "title"},
			simpleData: {enable : true}
		},
		callback : {
			onClick: org_teach_phase_click
		}
	};

	function org_teach_phase_click(event, treeId, treeNode){
			if(treeNode.flag=="school" || treeNode.flag =="schSearch" ){
				$("#org_teach_publisher_copy").hide();
				$("#org_teach_publisher_load").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/publisher/org?orgId="+treeNode.id,{},function(){
					$("#org_teach_publisher_load").find("[layoutH]").layoutH();
				});
			}else if ((treeNode.pId != null && treeNode.pId !=0) && '${showArea == true}'){
					$("#org_teach_publisher_copy").hide();
					$("#org_teach_publisher_load").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/publisher/area?areaId="+treeNode.id,{},function(){
						$("#org_teach_publisher_load").find("[layoutH]").layoutH();
					});
					if ('${showSch}'){
						//查询最后一层区域节点下所有学校节点
						if(!treeNode.isParent){
							$.ajax({
								type : "GET",
								dataType : "json",
								url : "${pageContext.request.contextPath}/jy/back/yhgl/findUnit",
								data : {"type":0, "areaId":treeNode.id},
								success : function(data){
									$.each(data,function(index,obj){
										yhgl_sch_ztree = $.fn.zTree.getZTreeObj("org_publisher_tree");
										yhgl_sch_ztree.addNodes(treeNode, {id:obj.id, pId:treeNode.id, name:obj.shortName,title:obj.name,flag:"school",open:true});
									});
								}
							});
						}
						
					}
			}
	}

	var yhgl_sch_ZNode =[];
	yhgl_sch_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
	$(document).ready(function(){
		loadZzjgOrgTree("org_publisher_tree",setting_publisher_sch,"area");
	});
	//搜索学校
	function searAllSchPub(){
		$("#org_teach_publisher_copy").show();
		$("#org_teach_publisher_load").empty();
		var keyword = $("#key_look_publisher").val();
		searchState_publisher = searAllSchOrg("org_publisher_tree", keyword,setting_publisher_sch,searchState_publisher,1);
	}
	
	$("#key_look_publisher",$.pdialog._current).focus(false).click(false).keyup(function(event){
		switch(event.keyCode){
			case DWZ.keyCode.ESC:
			case DWZ.keyCode.TAB:
			case DWZ.keyCode.SHIFT:
			case DWZ.keyCode.HOME:
			case DWZ.keyCode.END:
			case DWZ.keyCode.LEFT:
			case DWZ.keyCode.RIGHT:
			case DWZ.keyCode.ENTER:
			case DWZ.keyCode.DOWN:
			case DWZ.keyCode.UP:
				break;
			default:
				searAllSchPub();
		}
	});
</script>
<ul class="info">
	<li style="height:30px;">
		<p>
			<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">学校名称：</label>
			<input type="text" id="key_look_publisher" value="" maxlength="10" class="empty" style="width: 125px;float:left;margin-top: 4px;"/>
			<a class="btnLook" onclick="searAllSchPub()" style="margin-top: 4px;margin-left:5px;cursor: pointer;"></a>
		</p>
	</li>
</ul>
<div class="pageContent" layoutH="15">
	<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
		<ul id="org_publisher_tree" class="ztree" layoutH="46"></ul>
	</div>
	<div class="unitBox" style="margin-left:226px;" id="org_teach_publisher_load" >
		<div class="prompt">
			<p>
				<span>请先选择区域下的学校，然后再查看信息！ </span>
			</p>
		</div>
	</div>
	<div class="unitBox" id="org_teach_publisher_copy" style="margin-left:226px;display: none">
		<div class="prompt">
			<p>
				<span>请先选择区域下的学校，然后再查看信息！ </span>
			</p>
		</div>
	</div>
</div>
	
