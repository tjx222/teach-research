<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="教研进度表"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/teachschedule/css/teachschedule.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript">
	//表单提交
	function backSave(data) {
		if (data.code == 0) {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/teachschedule/save.json",
				data : $("#jy_from").serialize(),
				success : function(data) {
					alert("保存成功");
					//刷新页面
					location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
				}
			});
		}
	}

	function change() {
		var circleId = $("#sel_st").val();
		if (circleId != "") {
			var name = "";
			var contentStr = "";
			var schoolOptions = "";
			<c:forEach items="${stlist}" var="stc">
			var id = "${stc.id }";
			if (circleId == id) {
				name = "${stc.name }";
				<c:forEach items="${stc.stcoList }" var="stco">
				var state = "${stco.state }";
				var orgId = "${stco.orgId }";
				var orgName = "${stco.orgName }";
				if (state == 1) {
					contentStr += '<li><a title="'+orgName+'" class="w180">' + orgName + '</a><span class="z_zc">待接受</span></li>';
				} else if (state == 2) {
					contentStr += '<li><a title="'+orgName+'" class="w180">' + orgName + '</a><span class="z_ty">已同意</span></li>';
					schoolOptions += '<option value="'+orgId+'">' + orgName + '</option>';
				} else if (state == 3) {
					contentStr += '<li><a title="'+orgName+'" class="w180">' + orgName + '</a><span class="z_jj">已拒绝</span></li>';
				} else if (state == 4) {
					contentStr += '<li><a title="'+orgName+'" class="w180">' + orgName + '</a><span class="z_tc">已退出</span></li>';
				} else if (state == 5) {
					contentStr += '<li><a title="'+orgName+'" class="w180">' + orgName + '</a><span class="z_ty">已恢复</span></li>';
					schoolOptions += '<option value="'+orgId+'">' + orgName + '</option>';
				}
				</c:forEach>
			}
			</c:forEach>
			$("#circleName").html(name);
			$("#circleOrg").html(contentStr);
			$("#circleContent").css("display", "block");
		} else {
			$("#circleContent").css("display", "none");
		}
	}
	//修改校际进度表
	function updateTeachSchedule(id, schoolTeachCircleId, subjectId, gradeId, name, resId) {
		if (resId != "") {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/teachschedule/getFileById.json",
				data : {
					"resId" : resId
				},
				success : function(data) {
					if (data.res != null) {
						$("#originFileName").val(data.res.name + "." + data.res.ext);
						$("#hiddenFileId").val(resId);
						$(".mes_file_process").html("");
					}
				}
			});
		}
		$(".formError").remove();
		$("#id").attr("value", id);
		$("#name").attr("value", name);
		$("#sel_st").val(schoolTeachCircleId);
		$("#sel_sb").val(subjectId);
		$("#sel_gd").val(gradeId);
		//显示修改,不修改按钮,隐藏保存按钮
		$("#save").attr("class", "Update");
		$("#noUpdate").attr("style", "");

		$("#sel_st").trigger('chosen:updated');
		$("#sel_sb").trigger('chosen:updated');
		$("#sel_gd").trigger('chosen:updated');

	}
	function notUpdate(subjectId) {
		$("#id").attr("value", "");
		$("#name").attr("value", "");
		$("#sel_st").val("");
		$("#sel_sb").val("");
		$("#sel_gd").val("");

		$("#originFileName").val("请选择文件");
		$("#hiddenFileId").val("");
		//显示保存按钮同时隐藏修改与不修改按钮
		$("#save").attr("class", "Upload1");
		$("#noUpdate").attr("style", "display: none;");

		$("#sel_st").trigger('chosen:updated');
		$("#sel_sb").trigger('chosen:updated');
		$("#sel_gd").trigger('chosen:updated');
	}
	function deleteTeachSchedule(id, resId) {
		if (confirm("您确定要删除此文件吗")) {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/teachschedule/delete.json",
				data : {
					"id" : id,
					"resId" : resId
				},
				success : function(data) {
					if (data.isOk) {
						alert("删除成功！");
						location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
					} else {
						alert("删除失败！");
					}
				}
			})
		}
	}
	function releaseTeachSchedule(id, isRelease) {
		var releaseOk = "您要确定发布该文件吗？";
		if (!isRelease) {
			releaseOk = "您要确定取消发布该文件吗？";
		}
		if (confirm(releaseOk)) {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/teachschedule/release.json",
				data : {
					"id" : id,
					"isRelease" : isRelease
				},
				success : function(data) {
					if (data.isRelease) {
						if (data.isOk) {
							alert("发布成功！");
							location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
						} else {
							alert("发布失败！");
						}
					} else {
						if (data.isOk) {
							alert("取消发布成功！");
							location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
						} else {
							alert("取消发布失败！");
						}
					}
				}
			})
		}
	}
	function searchContent() {
		var search = $("#search").val();
		if (search != null && search != "") {
			$("#search_form").submit();
			$("#whole").attr("style", "margin-top:-2px;");
		}
	}
	//表单验证
	function start(fileArraySize) {
		if ($("#originFileName").val() == "请选择文件") {
			$("#scfj_to").html(getCheckHtml(505, "请 选 择 文 件"));
			return false;
		} else {
			$("#scfj_to").html();
		}
		if (Number(fileArraySize) > 0) {
			return ($("#jy_from").validationEngine('validate'));
		} else {
			if ($("#jy_from").validationEngine('validate')) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/teachschedule/save.json",
					data : $("#jy_from").serialize(),
					success : function(data) {
						alert("保存成功");
						//window.location.reload();
						//刷新页面
						location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
					}
				});
			}
		}
	}
	//getcheckhtml
	function getCheckHtml(topNum, content) {
		var str = '<div class="kjmsformError parentFormkj_form formError" style="opacity:0.87; position:absolute; top:'+topNum+'px; left:280px;">'
				+ '<div class="formErrorContent" style="padding:8px 10px;">* ' + content + '<br></div>'
				+ '<div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div>'
				+ '<div class="line7"></div><div class="line6"></div><div class="line5"></div>'
				+ '<div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>' + '</div></div>';
		return str;
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop modelName="校际教研"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="jyjdb">
				</jy:nav>
			</h3>
		</div>
		<div class="clear"></div>
		<div class="research_circle">
			<div class="research_circle_l">
				<h3>
					<span>上传教研进度表</span>
				</h3>
				<form id="jy_from" action="jy/teachschedule/save" method="post">
					<ui:token></ui:token>
					<div class="from_list">
						<span class="from_list_span">*</span>
						<label for="">教研圈：</label> 
						<input type="hidden" id="id" name="id" /> 
						<div class="from_list_sel">
							<select name="schoolTeachCircleId" id="sel_st" style="width: 190px;height:27px;" onchange="change()" class="chosen-select-deselect jyq validate[required]">
								<option value="">请选择</option>
								<c:forEach var="s" items="${stlist}">
									<option value="${s.id}">${s.name}</option>
								</c:forEach>
							</select>
						</div>
						<div id="circleContent" class="hover_td">
							查 看
							<div class="school1" style="color: #474747;">
								<h5 style="font-weight: bold; border-bottom: 1px #ccc solid;">
									教研圈名称：<span id="circleName"></span>
								</h5>
								<ol id="circleOrg"></ol>
							</div>
						</div> 
					</div> 
					<div class="from_list">
						<span class="from_list_span">*</span>
						<label for="">学科：</label>
						<div class="from_list_sel">
						    <select name="subjectId" style="width: 190px;height:27px;" id="sel_sb" class="chosen-select-deselect jyq validate[required]">
								<option value="">请选择</option>
								<ui:relation var="list" type="xdToXk" id="${_CURRENT_SPACE_.phaseId}">
									<c:forEach var="xk" items="${list}">
										<option value="${xk.id}">${xk.name}</option>
									</c:forEach>
								</ui:relation>
							</select>
						</div>
					</div>
					<div class="from_list">
						<span class="from_list_span">*</span>
						<label for="" >年级：</label>
						<div class="from_list_sel">
							<select name="gradeId" style="width: 190px;height:27px;" id="sel_gd" class="chosen-select-deselect jyq validate[required]">
								<option value="">请选择</option>
								<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${grades }" var="g" varStatus="st">
									<li data="${g.id }"
										class="grade ${grade == g.id || (empty grade && st.index == 0) ? 'nj_act':'' }">
										<option value="${g.id}">${g.name }</option>
									</li>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="from_list">
						<span class="from_list_span">*</span>
						<label for="">标题：</label>
						<div class="from_list_sel">
							<input type="text" id="name" name="name" class="txt validate[required,maxSize[30]]">
						</div>
					</div>
					<div id="scfj_to"></div>
					<div id="fileuploadContainer" class="from_list">
						<span class="from_list_span">*</span>
						<label for="">上传附件：</label>
						<ui:upload containerID="fileuploadContainer"
							fileType="docx,doc,xlsx,xls,pdf" fileSize="50"
							startElementId="save" callback="backSave" beforeupload="start"
							relativePath="teachschedule/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }"
							name="resId"></ui:upload>
					</div>
					<p style="text-align: center; margin-top: 80px;">
						<input id="save" type="button" class="Upload1" value="上传" /> 
						<input id="noUpdate" type="button" class="NoUpdate" value="不改了"  style="display: none;" onclick="notUpdate('${_CURRENT_SPACE_.subjectId}')" />
					</p>
				</form>
			</div>
			<div class="research_circle_r">
				<h3>教研进度表</h3>
				<div class="research_circle_r1">
					<c:choose>
						<c:when test="${!empty tSchedules }">
							<c:forEach items="${tSchedules}" var="data">
								<div class="Reflect_cont_right_1_dl">
									<dl>
										<a target="_blank" href="jy/teachschedule/view?id=${data.id}&listType=upload">
											<dd>
												<ui:icon ext="${data.fileSuffix}"></ui:icon>
											</dd>
											<dt>
												<span title="${data.name}">[<jy:dic
														key="${data.subjectId}"></jy:dic>][<jy:dic
														key="${data.gradeId}"></jy:dic>] <ui:sout
														value="${data.name}" length="18" needEllipsis="true"></ui:sout>
												</span> <span><fmt:formatDate value="${data.lastupDttm}"
														pattern="yyyy-MM-dd" /></span>
											</dt>
										</a>
									</dl>
									<div class="show_p">
										<ol>
											<c:choose>
												<c:when test="${data.isRelease==true}">
													<li title="禁止修改" class="menu_li_11"></li>
												</c:when>
												<c:otherwise>
													<li title="修改" class="menu_li_1"
														onclick="updateTeachSchedule('${data.id}','${data.schoolTeachCircleId}','${data.subjectId}','${data.gradeId}','${data.name}','${data.resId}')"></li>
												</c:otherwise>
											</c:choose>
											<li title="删除" class="menu_li_2"
												onclick="deleteTeachSchedule('${data.id}','${data.resId}')"></li>
											<c:choose>
												<c:when test="${data.isRelease==true}">
													<li title="取消发布" class="menu_li_31"
														onclick="releaseTeachSchedule('${data.id}',false)"></li>
													<!-- <li class="menu_li_32"></li> -->
												</c:when>
												<c:otherwise>
													<li title="发布" class="menu_li_3"
														onclick="releaseTeachSchedule('${data.id}',true)"></li>
												</c:otherwise>
											</c:choose>
											<a
												href="<ui:download filename="${data.name}" resid="${data.resId}"></ui:download>">
												<li title="下载" class="menu_li_6"></li>
											</a>
										</ol>
									</div>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div class="empty_wrap">
								<div class="empty_img"></div>
								<div class="empty_info" style="text-align: center;">您还没有上传教研进度表哟，赶紧去左边“上传教研进度表”吧！</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
	<script type="text/javascript">
		$(function() {
			//下拉列表
			var config = {
				'.chosen-select' : {},
				'.chosen-select-deselect' : {
					allow_single_deselect : true
				},
				'.chosen-select-deselect' : {
					disable_search : true
				}
			};
			for ( var selector in config) {
				$(selector).chosen(config[selector]);
			}
		})
	</script>
</body>
</html>
