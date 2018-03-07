<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid;">
	<form id="form" method="post" action="${ctx }/jy/back/jsgl/addOrUpdateRole" class="pageForm required-validate"
		onsubmit="return validateCallback(this,jsModifyAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p id="jsyc">
				<label>角色类型：</label>
				<select onchange="check(this)" name="sysRoleId" id="sel" ${empty role.id ?'':'disabled' }>
					<option value="-2">--请选择--</option>
					<c:forEach items="${rtList}" var="rt">
						<option  value="${rt.code}" data-usePosition="${rt.usePosition }" data-name="${rt.roleTypeName }" data-desc="${rt.roleTypeDesc }" ${rt.code==role.sysRoleId?"selected='selected'":""} >${rt.roleTypeName}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>角色名称：</label> 
				<input type="hidden" name="solutionId" value="${role.solutionId }">
				<input type="hidden" name="id" value="${role.id }">
				<input type="hidden" name="usePosition" value="${role.usePosition }">
				<input class="required " name="roleName" type="text" size="36" maxlength="10" value="${role.roleName }"/><br>
			</p>
			
			<p>
				<label>权限标识：</label> 
				<input type="text" name="roleCode" value="${role.roleCode }" required="required">
			</p>
			
			<p>
				<label>角色说明：</label>
				<textarea maxlength="500" rows="1" cols="33" name="roleDesc" >${role.roleDesc}</textarea>
			</p>
			
			<p style="margin-top: 20px;width: 100%;">
				<label>功能权限：</label><span style="width: 160px;display: block;float: left;text-align: left; ">模块名称</span>别名
			</p>
			<div style="margin-left: 125px; width: 400px;float:left" id="qx">	</div>	
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="CheckS()" id="bc">保存</button>
							<button type="submit" id = "sub" >保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"> 
							<button type="Button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>

		</div>
	</form>
</div>
<script type="text/javascript">
	function CheckS(){
		if($("#sel").val() == -2 ){
			alertMsg.info('请选择角色类型！');
			return false;
		}
	}

	$(function (){
		if($("#sel").val()==-2){
			$("#sub").hide();
			$("#bc").show();
		}else{
			$("#bc").hide();
			$("#sub").show();
		}
		if($("#sel").val()!=-2){
			$.post("${ctx }/jy/back/jsgl/getPermByRoleTypeId",{id:$("#sel").val()},
				function (result){
					$("#qx").empty();
					$.each(result,function(index,perm){
							if(perm != null && perm.parentid == 0){
								$("#qx").append('<span style="display:inline-block;overflow:hidden; width: 95px;  height: 18px;margin-right:10px;">'
										+'<input data-pid="'+perm.parentid+'" onclick="checkParentSon(this,'+perm.parentid+')" id="jx'+perm.id+'" type="checkbox" class="roleTypePerm"  name="menuIds" value="'+perm.id+'">'+perm.name+'</span>'+'<input data-id="'+perm.id+'"   type="text" style="width:200px;" value="'+perm.name+'" /><br/>');
								$.each(result,function(i,child){
									if(child != null && perm.id == child.parentid){
										$("#qx").append('<span style="display: inline-block;overflow:hidden; width: 95px;  height: 18px;margin-right:10px;margin-left: 30px">'
											+'<input data-pid="'+child.parentid+'" onclick="checkParentSon(this,'+child.parentid+')" id="jx'+child.id+'" type="checkbox" class="roleTypePerm"  name="menuIds" value="'+child.id+'">'+child.name+'</span>'+'<input data-id="'+child.id+'" type="text"   style="width:170px;" value="'+child.name+'" /><br/>');
									}
									});
							}

						});
					var ids = "${str}";
					var pm = "${perm}";
					if(ids!=""){
						var idsArr = ids.split(",");
						var perm = pm.split(",");
						for (var i=0;i<idsArr.length;i++)
						{
							$("#jx"+idsArr[i]).attr("checked",true);
							for (var j=0;j<perm.length;j++)
							{
								$("#jspm"+idsArr[i]).val(perm[i]);
								
							}
						}
					}
				},"json")
		}
	})

	function jsModifyAjaxDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
					parent.reloadRole();
				}
		}
	}

	function check(obj){
		if(obj.value!=-2){
			$("#bc").hide();
			$("#sub").show();
			$("input[name='roleName']").val($(obj).find("option:selected").attr("data-name"));
			$("input[name='usePosition']").val($(obj).find("option:selected").attr("data-usePosition"));
			$("input[name='roleDesc']").val($(obj).find("option:selected").attr("data-desc"));
			$.post(
					"${ctx }/jy/back/jsgl/getPermByRoleTypeId",
					{id:obj.value},
					function (result){
						$("#qx").empty();
						$.each(result,function(index,perm){
							if(perm.parentid == 0){
								$("#qx").append('<span style="display: inline-block;overflow:hidden; width: 95px;  height: 18px;margin-right:10px;">'
										+'<input  onclick="checkParentSon(this,'+perm.parentid+')" data-pid="'+perm.parentid+'" id="jx'+perm.id+'" type="checkbox" class="roleTypePerm"  name="menuIds" value="'+perm.id+'">'+perm.name+'</span>'+'<input data-id="'+perm.id+'"   type="text"  style="width:200px;"  value="'+perm.name+'" /><br/>');
								$.each(result,function(i,child){
									if(perm.id == child.parentid){
										$("#qx").append('<span style="display: inline-block;overflow:hidden; width: 95px;  height: 18px;margin-right:10px;margin-left: 30px">'
											+'<input  onclick="checkParentSon(this,'+child.parentid+')" data-pid="'+child.parentid+'" id="jx'+child.id+'" type="checkbox" class="roleTypePerm"  name="menuIds" value="'+child.id+'">'+child.name+'</span>'+'<input data-id="'+child.id+'" type="text" style="width:170px;" value="'+child.name+'" /><br/>');
									}
									});
							}

						})
					},"json"
			)

		}else{
			$("input[name='roleName']").val("");
			$("input[name='roleDesc']").val("");
			$("#sub").hide();
			$("#bc").show();
			$("#qx").empty();
		}
	}
	var ids = '';
	//子父互选
	function checkParentSon(pm,pid){
		if(pm.checked==true){
			$("#jx"+pid).prop("checked",true);
		}else{
			$("input[data-pid='"+$(pm).val()+"']").prop("checked",false);
		} 
	}
</script>

