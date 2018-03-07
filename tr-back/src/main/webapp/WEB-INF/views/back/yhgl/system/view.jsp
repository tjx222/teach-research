<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<style>
.dialog .dialogContent{overflow-y:auto;}
#templateFileInput{float:left;}
#icoFileInput{float:left;}
#templateFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#icoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
.pageFormContent_l{width:300px;float:left;}
.pageFormContent_l div{height:30px;}
.cancel{position: absolute;right: 10px;top: 10px;}
.uploadify-progress{display:none;}

</style>
</head>
<body>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;">
	<form method="post" action="jy/back/yhgl/sys/saveUser" class="pageForm required-validate" onsubmit="return beforeSubmit(this)">
		<input type="hidden" name="userId" value="${user.id }"/>
		<div class="pageFormContent">
			<div class="pageFormContent1">
				<div class="pageFormContent_l">
					<div>
						<label style="width:70px;">编号：</label>
						<label style="width:170px;">${user.id }</label>
					</div>
				    <div>
						<label style="width:70px;">角色：</label>
						<label style="width:170px;">${role.roleName }</label>
					</div>
					<div>
						<label style="width:70px;">账号：</label>
						<label style="width:170px;">${login.loginname }</label>
					</div>
					<div>
						<label style="width:70px;">姓名：</label>
						<label style="width:170px;">${user.name }</label>
					</div>
					<div>
						<label style="width:70px;">性别：</label>
						<label style="width:170px;">${user.sex==0?'男':'女'}</label>
					</div>
					 <div>
						<label style="width:70px;">昵称：</label>
						<label style="width:170px;">${user.nickname }</label>
					</div> 
					<div>
						<label style="width:70px;">出生日期：</label>
						<label style="width:170px;"><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></label>
					</div>
					<div>
						<label style="width:70px;">身份证号：</label>
						<label style="width:170px;">${user.idcard }</label>
					</div>
					<div>
						<label style="width:70px;">邮件地址：</label>
						<label style="width:170px;">${user.mail}</label>
					</div> 
					<div>
						<label style="width:70px;">联系电话：</label>
						<label style="width:170px;">${user.cellphone}</label>
					</div>
					<div>
						<label style="width:70px;">邮编：</label>
						<label style="width:170px;">${user.postcode}</label>
					</div>
					<div>
						<label style="width:70px;">联系地址：</label>
						<label style="width:170px;">${user.address}</label>
					</div>
					<div style="height:88px;">
						<label style="width:70px;">个人简介：</label>
						<textarea name="explains" rows="5" cols="22" style="width:145px;" maxlength="1000" readonly="readonly">${user.explains}</textarea>
					</div>
					<div <c:if test="${empty user.originalPhoto}"> </c:if> style="height:50px;">
						<label style="line-height:33px;height:33px;width:70px;">头像：</label>
						<div id="picDiv">
							<ui:photo src="${user.originalPhoto }" width='50' height='50' ></ui:photo>
						</div>
					</div> 
				</div>
				<div class="pageFormContent_r">
					<div style="margin-top:0; width:200px;height:300px;overflow-y: auto;">
						<label>管辖范围：</label>
						<div style="clear:both;"></div>
						<div id="orgTree" class="ztree">
					
						</div>
					</div>
				</div>
			</div> 
			<div style="clear:both;"></div>
			
			
		</div>
	</form>
</div>
</body>
<script type="text/javascript">
var ztreeObj;
var settingbm = {
		data: {
			simpleData: {
				idKey: "id",
				pIdKey: "parentId",
				enable: true
			}
		},
		key:{
			name: "name",
		},
		callback: {
			beforeRemove: deleteUnopenNode
		}
	};
	
var areaNodes = [];
$(document).ready(function(){
	$.ajax({ 
		async : false,  
        cache:true,
        dataType : "json",
		url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
		success : function(data){ 
			$.each(data,function(n,d){ 
				areaNodes.push({ 
					id:d.id, 
					name:d.name, 
					parentId:d.parentId,
					flag:"area",
				}) 
			}); 
		}
	});
	ztreeObj = $.fn.zTree.init($("#orgTree"), settingbm, areaNodes);
	
	if(${userId}!=null){
		$.ajax({ 
			async : false,  
	        cache:true,
	        dataType : "json",
	        data:{'userId':${userId}},
			url : "${pageContext.request.contextPath}/jy/back/yhgl/sys/getManagescope", 
			success:callback
		});
	}
});

//展开区域并添加已选择的机构
function callback(data){
	var scopeList = data.scopeList;
	for(var i=0;i<scopeList.length;i++){
		var areaNode = ztreeObj.getNodeByParam("id", scopeList[i].areaId, null);
		ztreeObj.addNodes(areaNode, {id:scopeList[i].orgId, parentId:areaNode.areaId, name:scopeList[i].orgName,title:scopeList[i].orgName,flag:"unit"}); 
		areaNode.open = true;
	}
	//删除非打开状态的地区节点
    var delNodes =  ztreeObj.getNodesByParam("open",false, null);
	for(var i=0;i<delNodes.length;i++) {
		ztreeObj.removeNode(delNodes[i],true);
	}
}

function deleteUnopenNode(treeId, treeNode){
	if(treeNode.flag=='area'){
		return true;
	}
	return false;
}
</script>
