<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<style>
#zj_photo_input{float:left;}
#zj_photo_input-queue{position: absolute;  left: 339px; top: -8px;}
#logoDiv button{margin-left:80px;margin-top:10px;}
.img_editblock{position: absolute;  left: 230px;height:20px;top:15px}
</style>

<div class="pageContent">
	<form method="post" id="expert_user_save" action="${ctx }jy/back/yhgl/saveExpertUser" class="pageForm required-validate" onsubmit="return expertUserFormAdd(this);">
		<input type="hidden" value="${user.orgId}" name="orgId" id="expertSelOrgId">
		<input type="hidden" value="${user.orgName}" name="orgName" id="expertOrgName">
		<input type="hidden" value="${user.id}" name="id" id="ex_id">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>账号：</label>
				<c:choose>
					<c:when test="${empty user.id}">
					<input name="loginname" id="expert_loginname" class="loginName_val required val_name_ val_email_user" minlength="5" maxlength="16" type="text" size="30" value=""  /><span id="val_spa" style="color: red;font-weight:bold;"></span>
					</c:when>
					<c:otherwise>
					<input name="loginname" readonly id="unit_loginname"  minlength="5" maxlength="15" type="text" size="30" value="${loginUser.loginname }" />
					</c:otherwise>
				</c:choose>
			</div>
			<div class="unit">
				<label>姓名：</label>
				<input name="name" id="unit_name" type="text" size="30" value="${user.name }" class="required" maxlength="5"/>
			</div>
			<div class="unit">
				<label>昵称：</label>
				<input type="text" name="nickname"  maxlength="5" value="${user.nickname }"/>
			</div>
			<div class="unit">
				<label>所属单位：</label>
				<input id="expertSel_user" type="text" class="required" readonly value="${user.orgName }" name="areaName" style="width:120px;"/>
				<a id="expert_sel" href="#" onclick="showExpertMenu(); return false;">选择</a>
			</div>
			<div class="unit">
				<label>专业：</label>
				<input name="cercode" id="unit_name" type="text" size="30"  class="required" maxlength="15" value="${user.cercode }"/>
			</div>
			<div class="unit">
				<label>性别：</label>
				<span><input type="radio" name="sex" value="0" ${user.sex == 0 ? 'checked':'' } class="required"/>男</span>
				<c:if test="${empty user.sex}">
					<span><input type="radio" name="sex" value="1" ${user.sex == 1 ? 'checked':'' } checked class="required"/>女</span>
				</c:if>
				<c:if test="${!empty user.sex}">
					<span><input type="radio" name="sex" value="1" ${user.sex == 1 ? 'checked':'' } class="required"/>女</span>
				</c:if>
			</div>
			
			<div class="unit">
				<label>职称：</label>
				<input name="profession" id="unit_profession" type="text" size="30" value="${user.profession }" maxlength="6" />
			</div>
			<div class="unit">
				<label>荣誉称号：</label>
				<input name="honorary" id="unit_honorary" type="text" size="30" value="${user.honorary }" maxlength="15"/>
			</div>
			<div class="unit">
				<label>骨干教师：</label>
				<select name="teacherLevel" id="unit_teacherLevel" style="width:203px;">
					<option value="">请选择</option>
					<option value="国家骨干" ${user.teacherLevel == '国家骨干' ? 'selected':''}>国家骨干</option>
					<option value="特级骨干" ${user.teacherLevel == '特级骨干' ? 'selected':''}>特级骨干</option>
					<option value="省级骨干" ${user.teacherLevel == '省级骨干' ? 'selected':''}>省级骨干</option>
					<option value="市级骨干" ${user.teacherLevel == '市级骨干' ? 'selected':''}>市级骨干</option>
					<option value="区县级骨干" ${user.teacherLevel == '区县级骨干' ? 'selected':''}>区县级骨干</option>
					<option value="校级骨干" ${user.teacherLevel == '校级骨干' ? 'selected':''}>校级骨干</option>
				</select>
			</div>
			
			<div class="unit">
				<label>出生日期：</label>
				<input type="text" name="birthday" class="date" minDate="1940-01-01" maxDate="now" value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' />"/>
			</div>
			<div class="unit">
				<label>身份证号：</label>
				<input type="text" name="idcard"  maxlength="18" value="${user.idcard }" />
			</div>
			<div class="unit">
				<label>联系电话：</label>
				<input type="text" id="jy_dhhm" name="cellphone" class="mobile" size="30" value="${user.cellphone }" maxlength="11" minlength="11"/>
			</div>
			<div class="unit">
				<label>邮编：</label>
				<input type="text" name="postcode"  size="30" value="${user.postcode }" maxlength="6" class="digits"/>
			</div>
			<div class="unit">
				<label>联系地址：</label>
				<textarea rows="3" cols="30" name="address"  maxlength="50">${user.address }</textarea>
			</div>
			<div class="unit">
				<label>个人简介：</label>
				<textarea rows="3" cols="30" name="remark"  maxlength="500">${user.remark }</textarea>
			</div>
			<div class="unit">
				<label>上传头像：</label>
					<div id="zj_pho_div" <c:if test="${!empty user.photo}">style="display: none;"</c:if>>
						<input style="width:300px" id="zj_photo_input" type="file" name="file" 
						uploaderOption="{
							swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }jy/manage/res/upload',
							formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'photo/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
							buttonText:'请选择图片', 
							fileSizeLimit:'1000KB', 
							fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
							fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
							auto:false,
							multi:false,
							onUploadSuccess:uploadunitzj			 
						}"
						/> 
						<input id="zj_originalPh" type="hidden" name="originalPhoto" value=""/>
		 				<input type="button" onclick="$('#zj_photo_input').uploadify('upload', '*');" value="上传" style="float:right;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
						<script type="text/javascript">
							function uploadunitzj(file, data, response){
								var data = eval('(' + data + ')');
								$("#zj_originalPh").val(data.data);
								$("#picDiv1").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><div class='img_editblock' onclick='deletezjphoto(this);'></div>");
								$("#picDiv1").show();
								$("#zj_pho_div").hide();
							}
						</script> 
					</div>
					<div id="picDiv1"  style="clear:both;margin-left:128px;<c:if test="${empty user.photo}">display: none; </c:if>"> 
						<ui:photo src="${user.originalPhoto }" width='100' height='50' ></ui:photo><div class="img_editblock" onclick='deletezjphoto(this);'></div>
 					</div>
			</div>
			 
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="button"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="sys_tree_md" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>


<script type="text/javascript">

	function dialogExpert(json){
// 		DWZ.ajaxDone(json);
// 		if (json.statusCode == DWZ.statusCode.ok){
// 			if ("closeCurrent" == json.callbackType) {
// 				parent.reloadExpertUs();
// 				$.pdialog.closeCurrent();
// 			}
// 		}
		alertMsg.correct(json[DWZ.keys.message])
		parent.reloadExpertUs();
		$.pdialog.closeCurrent();
	}
	function deletezjphoto(obj){
		$("#zj_photo").val("");
		$("#zj_originalPh").val("del");
		$(obj).parent().hide();
		$("#zj_pho_div").show();
	}
	var yhgl_expert_user;
	var expert_znod=[]; 
	var settingExpert = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClickzj,
				onClick: onClickzj
			}
		};

		function beforeClickzj(treeId, treeNode) {
			var checks = (treeNode && !treeNode.isParent);
			if (!checks) alert("只能选择城市...");
			return checks;
		}
		
		function onClickzj(e, treeId, treeNode) {
			if(treeNode.flag=="area"){
				$.ajax({
					type : "post",
					dataType : "json",
					url : "${pageContext.request.contextPath}/jy/back/yhgl/findUnit", 
					data : {"areaId":treeNode.id,type:1},
					success : function(data){ 
						$.each(data,function(index,obj){ 
							yhgl_expert_user.addNodes(treeNode, {id:obj.id, pId:treeNode.id, name:obj.shortName,title:obj.name,flag:"unit",open:true}); 
						}); 
						loadTo(treeNode);
					}
				});
			}
			if(treeNode.flag=="unit"||treeNode.flag=="school"){
				$("#expertSelOrgId").val(treeNode.id);
				$("#expertOrgName").val(treeNode.name);
				var zTree = $.fn.zTree.getZTreeObj("sys_tree_md"),
				nodes = zTree.getSelectedNodes(),
				v = "";
				nodes.sort(function compare(a,b){return a.id-b.id;});
				for (var i=0, l=nodes.length; i<l; i++) {
					v = nodes[0].name;
				}
				if (v.length > 0 ) v = v.substring(0, v.length);
				var cityObj = $("#expertSel_user");
				cityObj.attr("value", v);
			}
		}
		function　loadTo(treeNode){
			$.ajax({
				type : "post",
				dataType : "json",
				url : "${pageContext.request.contextPath}/jy/back/yhgl/findSchool", 
				data : {"areaId":treeNode.id,type:0},
				success : function(data){ 
					$.each(data,function(index,obj){ 
						yhgl_expert_user.addNodes(treeNode, {id:obj.id, pId:treeNode.id, name:obj.shortName,title:obj.name,flag:"school",open:true}); 
					}); 
				}
			});
		}
		function showExpertMenu() {
			var cityObj = $("#expertSel_user");
			var cityOffset = $("#expertSel_user").offset();
			$("#menuContent").css({left:"142" + "px", top:"122" + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "expert_sel" || event.target.id == "expertSel_user" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		
		$(document).ready(function(){
			$.ajax({ 
				type : "post",
				dataType : "json",
				url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
				success : function(data){ 
					$.each(data,function(n,d){ 
						expert_znod.push({ 
							id:d.id, 
							name:d.name, 
							pId:d.parentId,
							flag:"area",
						}) 
					}); 
					$.fn.zTree.init($("#sys_tree_md"), settingExpert, expert_znod);
					yhgl_expert_user = $.fn.zTree.getZTreeObj("sys_tree_md");
				}
			});
		});
		$.validator.addMethod("loginName_val", function(value, element) {
			var mobilecheck = /[^\a-\z\A-\Z]/g;
			var sub = value.substring(0,1);
			return this.optional(element) || !mobilecheck.test(sub);
		}, "首位必须是字母");
		function expertUserFormAdd(form){
			var loginNameExist;
			var name = $.trim($(".val_name_").val());
			if(${empty user.id}){
				$.ajax({
					type:"post",
					async : false, 
					url:"${pageContext.request.contextPath}/jy/back/yhgl/valUnitName",
					data:{'loginname':name},
					dataType:"json",
					cache: false,
					success: function(data){
						loginNameExist=data;
					},
				});
				if (!loginNameExist && name!="") {
					alertMsg.confirm("专家账号重复");
					return false;
				}
			}
			return validateCallback(form, dialogExpert);
		}
</script>
