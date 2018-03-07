<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.unit label{text-align: right;}
</style>
<div class="pageContent">
	<c:if test="${empty data }"><div>用户的空间失效</div></c:if>
	<c:if test="${not empty data }">
	<form method="post" id="add_sch_role" action="${ctx }/jy/back/yhgl/saveSchUserRole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDoneSch);">
	<input type="hidden" name="id" value="${data.us.id }"/>
	<input type="hidden" name="userId" value="${data.us.userId }"/>
	<div  id="" class="pageFormContent" layoutH="56">
		<div class="unit">
			<label>所属部门：</label>
			<select name="departmentId">
				<option value="">请选择</option>
				<c:forEach items="${data.deptOrgs}" var="dept">
					<option value="${dept.id}" ${data.us.departmentId==dept.id?'selected="selected"':'' } >${dept.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit">
			<label>角色：</label>
			<input type="hidden" name="roleId" id="role_id" value="${data.us.roleId }" />
			<input type="hidden" name="sysRoleId" id="sys_role_id" value="${data.us.sysRoleId }" />
			<select class="required" name="xxx" id="roleIds" onchange="selRoelSch()" disabled="disabled" >
				<c:forEach items="${data.roles }" var="role">
					<option ${data.us.roleId==role.id?'selected="selected"':'' } value="${role.id }_${role.sysRoleId}">${role.roleName }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit" id="gxbm_id">
			<label>管辖部门：</label>
			<c:forEach items="${data.deptOrgs}" var="dept">
				<input id="dept_${dept.id}" type="checkbox" name="deptIds" value="${dept.id}" />${dept.name} &nbsp;&nbsp;
			</c:forEach>
		</div>
		<div class="unit" id="xueduan_id">
			<label>学段：</label>
			<input type="hidden" name="phaseId" id="phase_id" value="${data.us.phaseId }" />
			<input type="hidden" name="phaseType" id="phase_type" value="${data.us.phaseType }" />
			<select id="phaseId" name="xx" class="required" onchange="selPhase()" >
				<c:forEach items="${data.phases }" var="meta">
					<option ${data.us.phaseId==meta.id?'selected="selected"':'' } value="${meta.id }_${meta.eid }">${meta.name }</option>
				</c:forEach>
				<c:if test="${data.phases.size() > 1}"><option id="quanbu" ${data.us.phaseId==0?'selected="selected"':'' } value="0_0">全部</option></c:if>
			</select>
		</div>
		<div class="unit" id="xueke_id">
			<label>学科：</label>
			<select class="required" name="subjectId" id="sel_xd_xk" onchange="findPublish('xk')">
				<c:forEach items="${data.subjects }" var="subject">
					<option ${data.us.subjectId==subject.id?'selected="selected"':'' } value="${subject.id }" >${subject.name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit" id="nianji_id">
			<label>年级：</label>
			<select class="required" name="gradeId" id="sel_xd_nj" onchange="findPublish('nj')" >
				<c:forEach items="${data.grades }" var="grade">
					<option ${data.us.gradeId==grade.id?'selected="selected"':'' } value="${grade.id }" >${grade.name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit" id="jcbb_id">
			<label>教材版本：</label>
			<select class="required" name="x" id="sel_cbs" onchange="findBook()">
				<c:forEach items="${data.cbslist }" var="cbs">
					<option ${data.cbsId==cbs.id?'selected="selected"':'' } value="${cbs.id }" >${cbs.name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit" id="jiaocai_id">
			<label>教材：</label>
			<select class="required" name="bookId" id="sel_book">
				<c:forEach items="${data.books }" var="book">
					<option ${data.us.bookId==book.comId?'selected="selected"':'' } value="${book.comId }" >${book.comName }</option>
				</c:forEach>
			</select>
		</div>
	</div>	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>
 	</c:if>
</div>

<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 320,width:580}}, $.pdialog.getCurrent(), "");
	var roleType_xk="",roleType_nj="" ;
	$(document).ready(function(){
		var conDepIds = "${data.us.conDepIds}";
		$.each(conDepIds.split(","),function(index,obj){
			$("#dept_"+obj).attr("checked","checked");
		}); 
		selRoelSch();
	});
	function dialogAjaxDoneSch(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadSchUserRole();
				$.pdialog.closeCurrent();
			}
		}
	}
	//选择角色
	function selRoelSch(){
		var roleIds = $("#roleIds").val();
		if(roleIds!=""){
			var idArr = roleIds.split("_");
			$("#role_id").val(idArr[0]);
			$("#sys_role_id").val(idArr[1]);
			if(idArr[1]=="1377" || idArr[1]=="1376" || idArr[1]=="2000"){
				$("#quanbu").css("display","");
			}else{
				$("#quanbu").css("display","none");
			}
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/back/yhgl/getRoleTypeObj",
				data:{"sysRoleId":idArr[1]},
				success:function(data){
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
					data:{"phaseId":idArr[0],"orgId":${data.us.orgId }},
					success:function(data){
						var subjects = data.subjects;
						if(subjects!=null){
							var subjectStr = '<option value="">请选择</option>';
							for(var i=0;i<subjects.length;i++){  
							   subjectStr += '<option value="'+subjects[i].id+'">'+subjects[i].name+'</option>';
							}
							$("#sel_xd_xk").html(subjectStr);
						}else{
							$("#sel_xd_xk").html('<option value="">请选择</option>');
						}
						var grades = data.grades;
						if(grades!=null){
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
			if(subjectId!="" && roleType_xk==1 && roleType_nj==1){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/back/yhgl/findPublishs",
					data:{"phaseId":phaseId,"subjectId":subjectId,"orgId":${data.us.orgId }},
					success:function(data){
						if(data){
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
		var phaseId = $("#inp_phaseId").val();
		var subjectId = $("#sel_xd_xk").val();
		var gradeLevelId = $("#sel_xd_nj").val();
		var publisherId = $("#sel_cbs").val();
		if(publisherId!=""){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/back/yhgl/findBooks",
				data:{"phaseId":phaseId,"subjectId":subjectId,"gradeLevelId":gradeLevelId,"publisherId":publisherId},
				success:function(data){
					if(data && data.length>0){
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