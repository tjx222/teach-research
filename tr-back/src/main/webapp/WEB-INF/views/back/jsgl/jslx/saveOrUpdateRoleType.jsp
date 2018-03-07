<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form id="form" method="post" action="${ctx }/jy/back/jsgl/jslx/addOrUpdateRoleType" class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
		
			<p>
				<input type="hidden" name="id" value="${roleType.id }">
				<input type="hidden" name="roleTypeName" id="roleTypeNid" value="${roleType.roleTypeName }">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;角色类型：</label> 
				<select name="code" id="selJsLx">
					<c:forEach items="${xtjs }" var="xt">
						<option value="${xt.id }" ${xt.id==roleType.code?"selected='selected'":""} onclick="fuRoleTypeName('${xt.cname }')">${xt.cname }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应用于：</label> 
				<input  type="radio" class="usePosition" name="usePosition" value="1" onclick="getPerm(this)">区域&nbsp;&nbsp;&nbsp;&nbsp;
				<input  type="radio" class="usePosition" name="usePosition" value="2" onclick="getPerm(this)">学校&nbsp;&nbsp;&nbsp;&nbsp;
				<input  type="radio" class="usePosition" name="usePosition" value="3" onclick="getPerm(this)">体系
			</p>
			<p>
				<label>角色类型描述：</label>
				<textarea id="ds" maxlength="500" name="roleTypeDesc" cols="30" rows="1">${roleType.roleTypeDesc }</textarea>
			</p>
			<p>
				<label>角色类型入口：</label>
				<input type="text" name="homeUrl" value="${roleType.homeUrl }">
			</p>
			<p></p>
			<p style="margin-top: -15px">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管辖部门：</label>
				<input type="radio" class="isNoBmManager" name="isNoBmManager" value="1" checked="checked"> 显示&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" class="isNoBmManager"  name="isNoBmManager" value="0"> 不显示
				&nbsp;&nbsp;<span style="color: gray;">是否设置管辖部门</span>
			</p>
			
			<p>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学段：</label>
				<input type="radio" class="isNoXz" name="isNoXz" value="1" checked="checked"> 显示&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="isNoXz" name="isNoXz" value="0"> 不显示
				&nbsp;&nbsp;<span style="color: gray;">是否需要设置学段</span>
			</p>
			
			<p>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学科：</label>
				<input type="radio" class="isNoXk" name="isNoXk" value="1" checked="checked">显示&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" class="isNoXk" name="isNoXk" value="0"> 不显示
				&nbsp;&nbsp;<span style="color: gray;">是否需要设置学科</span>
			</p>

			<p>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年级：</label>
				<input type="radio" class="isNoNj" name="isNoNj" value="1" checked="checked">显示&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" class="isNoNj" name="isNoNj" value="0"> 不显示
				&nbsp;&nbsp;<span style="color: gray;">是否需要设置年级</span>
			</p>

			<p>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;排序：</label>
				<input class="required number" maxlength="2" placeholder="请输入正确的整数" name="sort" type="text" size="36" value="${roleType.sort }" />
			</p>
			
			<p>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;功能权限：</label>
			</p>

			<p id="p" style="margin-left:88px;width:430px;">
			</p>
		</div>

		<div class="formBar">

			<ul>
	
				<li>

					<div class="button">
						<div class="buttonContent">
							<button type="submit" >保存</button>
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

$(function (){
	 var a = $("#selJsLx").find("option:selected").text();
	 $("#roleTypeNid").val(a);
})

function dialogAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
			parent.reloadRoleType();
		}
	}
}

	//应用方向回显
	var up = "${roleType.usePosition}";
	var rd = $(".usePosition");
	$.each(rd,function(index,r){
		if(r.value == up ){
			r.checked="checked";
			getPerm(r);
		}
	})
	
	//管辖部门回显
	var bm = "${roleType.isNoBmManager?1:0}";
	var ibm = $(".isNoBmManager");
	$.each(ibm,function(index,m){
		if(m.value == bm ){
			m.checked="checked";
		}
	})
	
	//学段回显
	var xz = "${roleType.isNoXz?1:0}";
	var ixz = $(".isNoXz");
	$.each(ixz,function(index,z){
		if(z.value == xz ){
			z.checked="checked";
		}
	})
	
	//学科回显
	var xk = "${roleType.isNoXk?1:0}";
	var ixk = $(".isNoXk");
	$.each(ixk,function(index,k){
		if(k.value == xk ){
			k.checked="checked";
		}
	})
	
	//年级回显
	var nj = "${roleType.isNoNj?1:0}";
	var inj = $(".isNoNj");
	$.each(inj,function(index,n){
		if(n.value == nj ){
			n.checked="checked";
		}
	})

	
	//权限功能获取
	function getPerm(obj){
		$.post(
				"${ctx }/jy/back/jsgl/jslx/getMenuRoleTypeByRoleId",
				{"sysRoleId":obj.value},
				function (data){
					$("#p").empty();
					$.each(data,function(index,perm){
						if(perm.parentid == 0){
							$("#p").append('<span style="display: inline-block;overflow:hidden; width: 95px;  height: 18px;margin-right:10px;">'
									+'<input data-pid="'+perm.parentid+'"  onclick="checkPerm(this,'+perm.parentid+')" id="jslx'+perm.id+'" type="checkbox" class="roleTypePerm"  name="roleTypePerm" value="'+perm.id+'">'+perm.name+'</span>'+'<input type="text" style="width:265px;" value="'+perm.desc+'" /><br/>');
							$.each(data,function(i,child){
								if(perm.id == child.parentid){
									$("#p").append('<span style="display: inline-block;overflow:hidden; width: 95px;  height: 18px;margin-right:10px;margin-left: 30px">'
										+'<input data-pid="'+child.parentid+'"  onclick="checkPerm(this,'+child.parentid+')" id="jslx'+child.id+'" type="checkbox" class="roleTypePerm"  name="roleTypePerm" value="'+child.id+'">'+child.name+'</span>'+'<input type="text" style="width:235px;" value="'+child.desc+'" /><br/>');
								}
								});
						}

					})
					var ids = "${roleType.roleTypePerm}";
					if(ids!=""){
						var idsArr = ids.split(",");
						$.each(idsArr,function(index,obj){
							$("#jslx"+obj).attr("checked",true);
						});
					}
 				},"json"
				)
	}
	
	//子父互选
	function checkPerm(pm,pid){
		if(pm.checked==true){
			$("#jslx"+pid).prop("checked",true);		
		}else{
			$("input[data-pid='"+$(pm).val()+"']").prop("checked",false);
		} 
	}
	
	function fuRoleTypeName(rtName){
		$("#roleTypeNid").val(rtName); 
	}
	
</script>

