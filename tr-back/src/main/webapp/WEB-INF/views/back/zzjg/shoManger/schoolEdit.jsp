<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#logoFileEdit{float:left;}
#logoFileEdit-queue{position: absolute;  left: 339px; top: -8px;}
#logoEditDiv button{margin-left:80px;margin-top:10px;}
.img_editblock{position: absolute;  left: 230px;height:167px;top:15px}
</style>
<div class="pageContent">
	<form method="post" id="sch_add" action="${ctx }/jy/back/zzjg/saveSch" class="pageForm required-validate" onsubmit="return schFormEdit(this);">
		<input type="hidden" value="${area.id}" name="areaId" id="hiSelId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
			      <label>编号：</label>
				  <h3>${id}</h3>
				  <input type="hidden" name="id" value="${id}"/>
			</div>
		
			<div class="unit">
				<label>机构类别：</label>
				<select name="orgType" style="width:203px;" class="required">
					<option value="">请选择</option>
					<option value="0" ${newOrg.orgType==0 ? 'selected="selected"' : ''}>会员校</option>
					<option value="1" ${newOrg.orgType==1 ? 'selected="selected"' : ''}>体验校</option>
					<option value="2" ${newOrg.orgType==2 ? 'selected="selected"' : ''}>演示校</option>
				</select>
			</div>
			<div class="unit">
				<label>学校全称：</label>
				<input name="name" id="sch_name_edit" type="text" size="30" value="${newOrg.name }" class="val_name_ required" maxlength="20"/><span id="val_n_edit" style="color: red;font-weight:bold;"></span>
			</div>
			<div class="unit">
				<label>学校简称：</label>
				<input type="text" name="shortName"  size="30" class="required" maxlength="10" value="${newOrg.shortName }"/>
			</div>
			<div class="unit">
				<label>学校编码：</label>
				<input type="text" name="code"  size="30" class="val_code_" maxlength="32" value="${newOrg.code }"/>
			</div>
			<div class="unit">
				<label>英文名称：</label>
				<input type="text"  value="${newOrg.englishName }" size="30" name="englishName" class="textInput" maxlength="100">
			</div>
			<div class="unit">
				<label>所属区域：</label>
				<input id="citySel" type="text" readonly value="${area.name }" name="areaName" style="width:120px;"/>
				<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
			</div>
			<div class="unit">
				<label>学校类型：</label>
				<select name="schoolings" style="width:203px;" class="required">
					<option value="">请选择</option>
					<c:forEach var="schType" items="${schTypeList}">
						<option value="${schType.id}" ${schType.id==newOrg.schoolings?'selected="selected"' : ''} >${schType.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="unit">
				<label>联系人：</label>
				<input type="text" name="contactPerson" value="${newOrg.contactPerson }" size="30" maxlength="5"/>
			</div>
			<div class="unit">
				<label>联系方式：</label>
				<input type="text" name="phoneNumber" value="${newOrg.phoneNumber }" size="30" maxlength="11" class="phone"/>
			</div>
			<div class="unit">
				<label>邮件：</label>
				<input type="text" name="email"  size="30" value="${newOrg.email }" class="email"/>
			</div>
			<div class="unit">
				<label>地址：</label>
				<textarea rows="3" cols="30" name="address"  maxlength="50">${newOrg.address }</textarea>
			</div>
			<div class="unit">
				<label>学校网址：</label>
				<textarea rows="3" cols="30" name="schWebsite" maxlength="100" class="url">${newOrg.schWebsite }</textarea>
			</div>
			<div class="unit">
				<label>校徽：</label>
				<div id="p_edit" style="display: none">
				<input style="width:300px" id="logoFileEdit" type="file" name="file" 
				uploaderOption="{
					swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
					uploader:'${ctx }jy/manage/res/upload',
					formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'zzjg/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
					buttonText:'请选择图片', 
					fileSizeLimit:'1000KB', 
					fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
					fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
					auto:false,
					multi:false,
					onUploadSuccess:uploadLogoEdit			 
				}"
				/> 
				<input id="logoEdit" type="hidden" name="logo" value="${newOrg.logo}"/>
				<input id="oldPiclogo" type="hidden" name="oldLogo" value="${newOrg.logo}"/>
 				<input type="button" onclick="$('#logoFileEdit').uploadify('upload', '*');" value="上传" style="float:left;position: absolute;left: 249px; top: 13px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/> 
				<script type="text/javascript">
					function uploadLogoEdit(file, data, response){
						var data = eval('(' + data + ')');
						$("#logoEdit").val(data.data);
						$("#picDiv_sch_edit").html("<img width='100' height='50' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><div class='img_editblock' onclick='deleteLog(this);'></div>");
						$("#picDiv_sch_edit").show();
						$("#p_edit").hide();
					}
				</script> 
				</div>
				<div id="picDiv_sch_edit" style="clear:both;margin-left:128px;"> 
						<img width='100' height='50' src='${ctx }jy/manage/res/download/${newOrg.logo}?isweb=true'><div class="img_editblock"  onclick='deleteLog(this);'></div>
 				</div> 
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
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="editTreeSch" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>



<script type="text/javascript">
	var settingxx = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClickxx,
				onClick: onClickxx
			}
		};
		
	function beforeClickxx(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) alert("只能选择城市...");
		return check;
	}
	
	function onClickxx(e, treeId, treeNode) {
		$("#hiSelId").val(treeNode.id);
		var zTree = $.fn.zTree.getZTreeObj("editTreeSch"),
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
	
	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({left:"142" + "px", top:"200" + "px"}).slideDown("fast");
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
	var zNode_s =[]; 
	var preSelId;
	$(document).ready(function(){
		preSelId = "${area.id}";
		$.ajax({ 
			dataType : "json",
			url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
			success : function(data){ 
				$.each(data,function(n,d){ 
					zNode_s.push({ 
						id:d.id, 
						name:d.name, 
						pId:d.parentId 
					}) 
				}); 
				$.fn.zTree.init($("#editTreeSch"), settingxx, zNode_s);
			}
		});
		if("${newOrg.logo}"==""){
			$("#p_edit").show();
			$("#picDiv_sch_edit").hide();
		}
	});
	
	function dialogSchEdit(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				parent.reloadXXInfoBox();
				$.pdialog.closeCurrent();
			}
		}
	}
	function deleteLog(obj){
		$("#logoEdit").val("");
		$(obj).parent().hide();
		$("#p_edit").show();
	}
	function schFormEdit(form) {
		var loginNameExist;
		//校验学校名称是否重冲突
		var name = $.trim($(".val_name_").val());
		$.ajax({
			async : false, 
			type:"post",
			url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${newOrg.areaId}&type=0&sid=${id}",
			data:{'name':name},
			dataType:"json",
			cache: false,
			success: function(data){
				loginNameExist=data;
			},
		});
		if (!loginNameExist && name!="") {
			alertMsg.confirm("学校名称重复");
			return false;
		}
		//校验学校code是否冲突
		var code = $.trim($(".val_code_").val());
		$.ajax({
			type:"post",
			async : false, 
			url:"${pageContext.request.contextPath}/jy/back/zzjg/valiSch?areaId=${newOrg.areaId}&type=0&sid=${id}",
			data:{'code':code},
			dataType:"json",
			cache: false,
			success: function(data){
				loginNameExist=data;
			},
		});
		if (!loginNameExist && code!="") {
			alertMsg.confirm("学校编码重复");
			return false;
		}
		return validateCallback(form, dialogSchEdit);
	}
</script>
