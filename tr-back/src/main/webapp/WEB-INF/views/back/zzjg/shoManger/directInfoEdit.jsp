<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" id="sch_add" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" value="${pareArea.id}" name="directSelId" id="directSelId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <h3>${id}</h3>
				  <input type="hidden" name="id" value="${id}"/>
			</div>
		
			<div class="unit">
				<label>学校全称：</label>
				<input name="name"  readonly="readonly" type="text" size="30" value="${newOrg.name }" maxlength="20"/>
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<input id="citySel" type="text" readonly value="${dirArea.name }"  name="directAreaId" class="required" style="width:120px;"/>
				<a id="menuBtn" href="#" onclick="showMenuDir(); return false;" style="position: relative;">选择</a>
			</div>
			<div class="unit">
				<label>直属校级别：</label>
				<span id="dirLevelId">
				<c:if test="${newOrg.dirLevelId==0 }">省级</c:if>
				<c:if test="${newOrg.dirLevelId==1 }">市级</c:if>
				</span>
			</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
					<li><div class="button"><div class="buttonContent"><button type="submit" onclick="saveSchLevel()">保存</button></div></div></li>
					<li>
						<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
					</li>
				</ul>
			</div>
	</form>
</div>
		
				<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
					<ul id="directSch" class="ztree" style="margin-top:0; width:160px;"></ul>
				</div>


<script type="text/javascript">
	var directAreaId; 
	var settingD = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClickD
			}
		};
		
	
	function onClickD(e, treeId, treeNode) {
		directAreaId = treeNode.id;
		if(treeNode.level==0){
			$("#dirLevelId").html("省级");
		}
		if(treeNode.level==1){
			$("#dirLevelId").html("市级");
		}
		var zTree = $.fn.zTree.getZTreeObj("directSch"),
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
	
	function showMenuDir() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({left:"142" + "px", top:"121" + "px"}).slideDown("fast");
	
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
	var dirZNode =[]; 
	var dirSelId;
	$(document).ready(function(){
		dirSelId = "${area.id}";
		$.ajax({ 
			type : "POST",
			dataType : "json",
			url : "${pageContext.request.contextPath}/jy/back/zzjg/directSchTree", 
			success : function(data){ 
					$.each(data,function(n,d){ 
						if(d.parentId !=792 && d.parentId !=2244 && d.parentId !=1 && d.parentId !=20){
							dirZNode.push({ 
								id:d.id, 
								name:d.name, 
								pId:d.parentId 
							}) 
						}
					}); 
				$.fn.zTree.init($("#directSch"), settingD, dirZNode);
			}
		});
	});
	
	function saveSchLevel(){
		var dirLevelId;
		var dirLevel = $("#dirLevelId").html();
		if(dirLevel=='省级'){
			dirLevelId=0;
		}
		if(dirLevel=='市级'){
			dirLevelId=1;
		}
		
		$.ajax({
			type : "POST",
			dataType : "json",
			url  : "${ctx }/jy/back/zzjg/saveDirLevel",
			data : {directAreaId:directAreaId,
				dirLevelId:dirLevelId,id:"${id}"},  
			beforeSend : function() {
				if(directAreaId==undefined){
					
					return false;
				}
				return true;
			},
			success:function(){
				parent.reloadZSXXBox();
				$.pdialog.closeCurrent();
			}
		});
	}
</script>
