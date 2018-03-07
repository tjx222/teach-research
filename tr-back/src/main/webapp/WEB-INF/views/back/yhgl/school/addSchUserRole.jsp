<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.unit label{text-align: right;}
</style>
<div class="pageContent">
	<form method="post" id="add_sch_role" action="${ctx }/jy/back/yhgl/saveSchUserRole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDoneSch);">
	<div  id="" class="pageFormContent" layoutH="56">
	<input type="hidden" name="userId" value="${us.userId }">
	<input id="org_id" type="hidden" name="orgId" value="${us.orgId }">
		<div class="unit">
			<label>所属部门：</label>
			<select name="departmentId">
				<option value="">请选择</option>
				<c:forEach items="${deptOrgs}" var="dept">
					<option value="${dept.id}" >${dept.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit">
			<label>角色：</label>
			<input type="hidden" name="roleId" id="role_id" value="">
			<input type="hidden" name="sysRoleId" id="sys_role_id" value="">
			<select class="required" name="xxx" id="roleIds" onchange="selRoelSch()" >
				<option value="">请选择</option>
				<c:forEach items="${roles }" var="role">
					<option value="${role.id }_${role.sysRoleId }" >${role.roleName }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit" id="gxbm_id">
			<label>管辖部门：</label>
			<c:forEach items="${deptOrgs}" var="dept">
				<input type="checkbox" name="deptIds" value="${dept.id}" />${dept.name} &nbsp;&nbsp;
			</c:forEach>
		</div>
		<div class="unit" id="xueduan_id">
			<label>学段：</label>
			<input type="hidden" name="phaseId" id="phase_id" value="">
			<input type="hidden" name="phaseType" id="phase_type" value="">
			<select id="phaseId" name="xx" class="required" onchange="selPhase()">
				<option value="">请选择</option>
				<c:forEach items="${phases }" var="meta">
					<option value="${meta.id }_${meta.eid }" >${meta.name }</option>
				</c:forEach>
				<c:if test="${phases.size() > 1}"><option id="quanbu" style="" value="0_0">全部</option></c:if>
			</select>
		</div>
		<div class="unit" id="xueke_id">
			<label>学科：</label>
			<select class="required" name="subjectId" id="sel_xd_xk" onchange="findPublish('xk')">
				<option value="">请选择</option>
			</select>
		</div>
		<div class="unit" id="nianji_id">
			<label>年级：</label>
			<select class="required" name="gradeId" id="sel_xd_nj" onchange="findPublish('nj')">
				<option value="">请选择</option>
			</select>
		</div>
		<div class="unit" id="jcbb_id">
			<label>教材版本：</label>
			<select class="required" name="x" id="sel_cbs" onchange="findBook()">
				<option value="">请选择</option>
			</select>
		</div>
		<div class="unit" id="jiaocai_id">
			<label>教材：</label>
			<select class="required" name="bookId" id="sel_book">
				<option value="">请选择</option>
			</select>
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
<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 320,width:580}}, $.pdialog.getCurrent(), "");
	var roleType_xk="",roleType_nj="" ;
	function dialogAjaxDoneSch(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadSchUserRole();
				$.pdialog.closeCurrent();
			}
		}
	}
	//首先清理会涉及到的表单项
	function resetInputs(){
		$("#sel_book").val("");
		$("#sel_cbs").val("");
		$("#sel_xd_nj").val("");
		$("#sel_xd_xk").val("");
		$("#phaseId").val("");
	}
	//选择角色
	function selRoelSch(){		
		resetInputs();
		var roleIds = $("#roleIds").val();
		if(roleIds!=""){
			var idArr = roleIds.split("_");
			$("#role_id").val(idArr[0]);
			$("#sys_role_id").val(idArr[1]);
			if(idArr[1]=="1377" || idArr[1]=="1376" || idArr[1]=="2000"){
				toggleOptionShow($("#phaseId"),["quanbu"]);
			}else{
				toggleOptionShow($("#phaseId"),[],["quanbu"]);
			}
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/back/yhgl/getRoleTypeObj",
				data:{"sysRoleId":idArr[1]},
				success:function(data){
					if(data==null) return false;
					roleType_xk = data.isNoXk;
					roleType_nj = data.isNoNj;
					if(!data.isNoBmManager){
						$("#gxbm_id").css("display","none");
					}else{
						$("#gxbm_id").css("display","");
					}
					if(!data.isNoXz){
						$("#xueduan_id").css("display","none");
						$("#xueke_id").css("display","none");
						$("#nianji_id").css("display","none");
						$("#jcbb_id").css("display","none");
						$("#jiaocai_id").css("display","none");
						$("#phaseId").attr("class","");
						$("#sel_xd_xk").attr("class","");
						$("#sel_xd_nj").attr("class","");
						$("#sel_cbs").attr("class","");
						$("#sel_book").attr("class","");
					}else{
						$("#xueduan_id").css("display","");
						$("#phaseId").attr("class","required");
						if(!data.isNoXk){
							$("#xueke_id").css("display","none");
							$("#sel_xd_xk").attr("class","");
						}else{
							$("#xueke_id").css("display","");
							$("#sel_xd_xk").attr("class","required");
						}
						if(!data.isNoNj){
							$("#nianji_id").css("display","none");
							$("#sel_xd_nj").attr("class","");
						}else{
							$("#nianji_id").css("display","");
							$("#sel_xd_nj").attr("class","required");
						}
						if(data.isNoXk && data.isNoNj){
							$("#jcbb_id").css("display","");
							$("#jiaocai_id").css("display","");
							$("#sel_cbs").attr("class","required");
							$("#sel_book").attr("class","required");
							$("#sel_xd_xk").attr("onchange","findPublish('xk')");
							$("#sel_xd_nj").attr("onchange","findPublish('nj')");
						}else{
							$("#jcbb_id").css("display","none");
							$("#jiaocai_id").css("display","none");
							$("#sel_cbs").attr("class","");
							$("#sel_book").attr("class","");
							$("#sel_xd_xk").attr("onchange","");
							$("#sel_xd_nj").attr("onchange","");
						}
					}
					if($("#sys_role_id").val()=="1373"){//备课组长
						$("#jcbb_id").css("display","none");
						$("#jiaocai_id").css("display","none");
						$("#sel_cbs").attr("class","");
						$("#sel_book").attr("class","");
						$("#sel_xd_xk").attr("onchange","");
						$("#sel_xd_nj").attr("onchange","");
					}
				}
			});
		}else{
			$("#role_id").val("");
			$("#sys_role_id").val("");
		}
	}
	//选择学段
	function selPhase(){
		$("#sel_book").val("");
		$("#sel_cbs").val("");
		$("#sel_xd_nj").val("");
		$("#sel_xd_xk").val("");
		var phaseIds = $("#phaseId").val();
		if(phaseIds!=""){
			var idArr = phaseIds.split("_");
			$("#phase_id").val(idArr[0]);
			$("#phase_type").val(idArr[1]);
			if(roleType_xk==1 || roleType_nj==1){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/back/yhgl/findMetaByPhase",
					data:{"phaseId":idArr[0],"orgId":${us.orgId }},
					success:function(data){
						var subjects = data.subjects;
						if(subjects){
							var subjectStr = '<option value="">请选择</option>';
							for(var i=0;i<subjects.length;i++){  
							   subjectStr += '<option value="'+subjects[i].id+'">'+subjects[i].name+'</option>';
							}
							$("#sel_xd_xk").html(subjectStr);
						}else{
							$("#sel_xd_xk").html('<option value="">请选择</option>');
						}
						var grades = data.grades;
						if(grades){
							var gradesStr = '<option value="">请选择</option>';
							for(var i=0;i<grades.length;i++){ 
								gradesStr += '<option value="'+grades[i].id+'">'+grades[i].name+'</option>';
							} 
							$("#sel_xd_nj").html(gradesStr);
						}else{
							$("#sel_xd_nj").html('<option value="">请选择</option>');
						}
					}
				});
			}
		}else{
			$("#phase_id").val("");
			$("#phase_type").val("");
			$("#sel_xd_xk").html('<option value="">请选择</option>')
			$("#sel_xd_nj").html('<option value="">请选择</option>')
		}
	}
	//选择学科-查询出版社
	function findPublish(type){
		$("#sel_book").val("");
		$("#sel_cbs").val("");
		var flag = true;
		if(type=="nj"){
			var gradeLevelId = $("#sel_xd_nj").val();
			var subjectId = $("#sel_xd_xk").val();
			if(subjectId!="" && gradeLevelId!=""){
				flag = true;
			}else{
				flag = false;
			}
		}
		if(flag){
			var phaseId = $("#phase_id").val();
			var subjectId = $("#sel_xd_xk").val();
			var orgId = $("#org_id").val();
			if(subjectId!="" && roleType_xk==1 && roleType_nj==1){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/back/yhgl/findPublishs",
					data:{"phaseId":phaseId,"subjectId":subjectId,"orgId":orgId},
					success:function(data){
						if(data instanceof Array){
							var cbsStr = '<option value="">请选择</option>';
							$.each(data,function(index,obj){
								cbsStr += '<option value="'+obj.id+'">'+obj.name+'</option>';
							});
							$("#sel_cbs").html(cbsStr);
						}else{
							$("#sel_cbs").html('<option value="">请选择</option>');
						}
					}
				});
			}else{
				$("#sel_cbs").html('<option value="">请选择</option>');
			}
		}
		
	}
	//选择出版社-查询书籍
	function findBook(){
		$("#sel_book").val("");
		var phaseId = $("#inp_phaseId").val();
		var subjectId = $("#sel_xd_xk").val();
		var gradeLevelId = $("#sel_xd_nj").val();
		var publisherId = $("#sel_cbs").val();
		/* var orgId = $("#org_id").val(); */
		if(publisherId!=""){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/back/yhgl/findBooks",
				data:{"phaseId":phaseId,"subjectId":subjectId,"gradeLevelId":gradeLevelId,"publisherId":publisherId},
				success:function(data){
					if(data instanceof Array){
						var bookStr = '';
						if(data.length>1){
							bookStr = '<option value="">请选择</option>';
						}
						$.each(data,function(index,obj){
							bookStr += '<option value="'+obj.comId+'">'+obj.comName+obj.bookEdtion+obj.fascicule+'</option>';
						});
						$("#sel_book").html(bookStr);
					}else{
						$("#sel_book").html('<option value="">请选择</option>');
						alertMsg.info("没有找到对应的教材，请更改教材版本再试试！");
					}
				}
			});
		}else{
			$("#sel_book").html('<option value="">请选择</option>');
		}
	}
	
</script>