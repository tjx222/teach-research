<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.tmser.tr.uc.SysRole"%>
<c:set var="XZ" value="<%=SysRole.XZ%>"/>
<c:set var="FXZ" value="<%=SysRole.FXZ%>"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="教学文章"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/dlog_submit.css" media="screen">
	<ui:require module="thesis/js"></ui:require> 
	<script type="text/javascript"
		src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript"
		src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript"
		src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<link rel="stylesheet"
		href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript"
		src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript">
		require([ 'jquery', 'jp/jquery-ui.min', 'jp/jquery.blockui.min', 'js','thesis_submit'],
				function() {
				});
	</script>
<script type="text/javascript">
$('#ytjButton').click(function(){
	console.info(12);
	$(this).addClass("upload-bottom_tab_blue");
	$("#wtjButton").removeClass("upload-bottom_tab_blue");
// 	$("#submit_iframe").attr("src","jy/courseware/preSubmit?isSubmit=1&"+ Math.random());
});
$('#wtjButton').click(function(){ 
	console.info(34);
    $(this).addClass("upload-bottom_tab_blue");
    $("#ytjButton").removeClass("upload-bottom_tab_blue");
//     $("#submit_iframe").attr("src","jy/courseware/preSubmit?isSubmit=0&"+ Math.random());
}); 
	//表单提交
	function backSave(data) {
		if (data.code == 0) {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/thesis/save.json",
				data : $("#th_form").serialize(),
				success : function(data) {
					alert("保存成功");
					//刷新页面
					location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
				}
			});
		}
	}
	//修改教学论文
	function updateThesis(id, thesisTitle, subjectId, thesisType, resId) {
		if (resId != "") {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/thesis/getFileById.json",
				data : {
					"resId" : resId
				},
				success : function(data) {
					if (data.res != null) {
						$("#originFileName").val(
								data.res.name + "." + data.res.ext);
						$("#hiddenFileId").val(resId);
						$(".mes_file_process").html("");
					}
				}
			});
		}

		$(".formError").remove();
		$("#id").attr("value", id);
		$("#thesisTitle").attr("value", thesisTitle);
		$("#sel_xk").val(subjectId);
		$("#sel_lx").val(thesisType);
		//显示修改,不修改按钮,隐藏保存按钮
// 		$("#save").attr("class", "btn_bottom_3");
// 		$("#save").attr("style", "margin-left: 6px;");
		$("#noUpdate").show();
		$("#sel_xk").trigger('chosen:updated');
		$("#sel_lx").trigger('chosen:updated');

	}
	function notUpdate(subjectId) {
		$("#thesisTitle").attr("value", "");
		$("#sel_xk").val(subjectId);
		$("#sel_lx").val("教学论文");

		$("#originFileName").val("请选择文件");
		$("#hiddenFileId").val("");
		//显示保存按钮同时隐藏修改与不修改按钮
// 		$("#save").attr("class", "btn_bottom_1");
// 		$("#save").attr("style", "display: none;");
		$("#noUpdate").hide();
// 		$("#noUpdate").attr("style", "display: none;");
		$("#sel_xk").trigger('chosen:updated');
		$("#sel_lx").trigger('chosen:updated');

	}
	function deleteClose(id, resId,cancel){
		if(cancel){
			$("#thesis_del").dialog("close");
		}else{
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/thesis/delete.json",
				data : {
					"id" : id,
					"resId" : resId
				},
				success : function(data) {
					if (data.isOk) {
						location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
					} else {
						$("#thesis_del").find(".del_info").html("删除失败！");
						$("#bt_delete").attr("onclick","deleteClose("+id+",'"+resId+"','cancel')");
					}
				}
			})
		}
	}
	function deleteThesis(id, resId, thesisTitle) {
		$("#bt_delete").attr("onclick","deleteClose("+id+",'"+resId+"')");
		$("#bt_cancel_delete").attr("onclick","deleteClose("+id+",'"+resId+"','cancel')");
		$("#thesis_del").dialog({width:400,height:220});
	}
	function sharingOk(id,isShare){
		$.ajax({
			type : "post",
			dataType : "json",
			url : _WEB_CONTEXT_ + "/jy/thesis/share.json",
			data : {
				"id" : id,
				"isShare" : isShare
			},
			success : function(data) {
				if (data.isShare == 1) {
					if (data.isOk) {
						location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
					} else {
						$("#thesis_share").find(".del_info").show().html("分享失败！");
						$("#thesis_share").find(".share_info").hide();
						$("#querenbut").attr("onclick","sharingClose()");
					}
				} else {
					if (data.isOk) {
						location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
					} else {
						$("#thesis_share").find(".del_info").show().html("取消分享失败！");
						$("#thesis_share").find(".share_info").hide();
						$("#querenbut").attr("onclick","sharingClose()");
					}
				}
			}
		})
	}
	function shareThesis(id, isShare, thesisTitle) {
		var shareOk = "您要确定分享”" + thesisTitle + "“教学文章吗？分享成功后，您的小伙伴们就可以看到喽！";
		if (isShare == 0) {
			shareOk = "您要确定取消分享”" + thesisTitle + "“教学文章吗？";
			$("#thesis_share").find(".del_info").show().html("您确定要取消分享吗？");
			$("#thesis_share").find(".share_info").hide();
			$("#querenbut").attr("onclick","sharingOk("+id+","+isShare+")");
			$("#thesis_share").dialog({width:450,height:250});
		}else{
			$("#thesis_share").find(".del_info").hide();
			$("#thesis_share").find(".share_info").show();
			$("#thesis_share").find(".share_info").find("h4").html(shareOk);
			$("#querenbut").attr("onclick","sharingOk("+id+","+isShare+")");
			$("#thesis_share").dialog({width:550,height:300});
		}
	}
	function sharingClose(){
		$("#thesis_share").dialog("close");
	}
	function searchContent() {
		var search = $("#search").val();
		if (search != null && search != "") {
			$("#search_form").submit();
			$("#whole").attr("style", "margin-top:-2px;");
		}

	}
	$(function() {
		$("#th_form").validationEngine();
	});
	//表单验证
	function start(fileArraySize) {
		if ($("#originFileName").val() == "请选择文件") {
			$(".scfj_to").css({"position":"relative"});
			$(".scfj_to").prepend(getCheckHtml(-220, "请 选 择 文 件"));
			return false;
		}else{
			$(".scfj_to").find(".parentFormkj_form").remove();
		}
		if (Number(fileArraySize) > 0) {
			return ($("#th_form").validationEngine('validate'));
		} else {
			$(".scfj_to").find(".parentFormkj_form").remove();
			if ($("#th_form").validationEngine('validate')) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/thesis/save.json",
					data : $("#th_form").serialize(),
					success : function(data) {
						alert("保存成功");
						//刷新页面
						location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
					}
				});
			}
		}
	}
	//getcheckhtml
	function getCheckHtml(topNum, content) {
		var str = '<div class="kjmsformError parentFormkj_form formError" style="opacity:0.87; position:absolute; top:'+topNum+'px; left:95px;">'
				+ '<div class="formErrorContent" style="padding:8px 10px;">* '
				+ content
				+ '<br></div>'
				+ '<div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div>'
				+ '<div class="line7"></div><div class="line6"></div><div class="line5"></div>'
				+ '<div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>'
				+ '</div></div>';
		return str;
	}

	/* require([ 'jquery', 'jp/jquery-ui.min', 'jp/jquery.blockui.min', 'js' ],
			function() {
			}); */
</script> 
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="教学文章"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		当前位置：
			<jy:nav id="jxlw">
				<jy:param name="name" value="教学文章"></jy:param>
			</jy:nav>
	</div>
	<div class="clear"></div>
	
	<div class="home_cont">
			<div class="home_cont_l">
				<h3 class="courseware_title"><span></span><strong>上传教学文章</strong></h3>
				<input type="hidden" id="currentUserId" value="${_CURRENT_USER_.id}">
					<form id="th_form" action="jy/thesis/save" method="post">
					    <ui:token />
						<div class="courseware_title_p">
							<span class="courseware_title_p_span">*</span>
							<label for="">标题：</label> <input type="hidden" id="id" name="id" />
							<div class="project_directory_sel">
								<input id="thesisTitle" class="validate[required,maxSize[30]]"
								name="thesisTitle" type="text" />
							</div>
						</div>
						<div class="courseware_title_p">
							<span class="courseware_title_p_span">*</span>
							<label for="">分类：</label> 
							<div class="project_directory_sel">
								<select id="sel_xk" style="width: 105px; height: 25px; border: 1px #999 solid;" class="chosen-select-deselect validate[required]" name="subjectId">
									<ui:relation var="xkList" type="xdToXk" id="${_CURRENT_SPACE_.phaseId}"></ui:relation>
									<c:forEach var="xk" items="${xkList}">
										<option ${_CURRENT_SPACE_.subjectId  ==xk.id ? 'selected':'' } value="${xk.id}">${xk.name}</option>
									</c:forEach>
									<option value="1383" ${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'ZR') || jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') ? 'selected':''}>其他</option>
								</select> 
								<select id="sel_lx" class="chosen-select-deselect"
									style="width: 105px; height: 25px; border: 1px #999 solid;"
									class="validate[required]" name="thesisType">
										<option id="jxlw" value="教学论文">教学论文</option>
										<option id="jxal" value="教学案例">教学案例</option>
										<option id="ktyj" value="课题研究">课题研究</option>
										<option id="jxsb" value="教学随笔">教学随笔</option>
										<option id="jyxs" value="教育叙事">教育叙事</option>
										<option id="jsbj" value="读书笔记">读书笔记</option>
										<option id="shgw" value="生活感悟">生活感悟</option>
										<option id="qt" value="其他">其他</option>
								</select>
							</div>
						</div>
						<div class="courseware_title_p" style="height:180px;" id="fileuploadContainer">
							<span class="courseware_title_p_span"></span>
							<label for="">内容：</label>
							<div class="project_directory_sel">
								<ui:upload containerID="fileuploadContainer"
								fileType="doc,docx,ppt,pptx,pdf" fileSize="50" relativePath="thesis/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }"
								startElementId="save" callback="backSave" beforeupload="start"
								name="resId"></ui:upload>
							</div>
						</div>
						<div id="def_but" class="scfj_to">
							<input id="save" type="button" class="uploadBtn"
								value="上传"/>
							<input id="noUpdate" type="button" class="uploadBtn uploadBtn1" style="display: none;" value="不改了"
								onclick="notUpdate('${_CURRENT_SPACE_.subjectId==0?1383:_CURRENT_SPACE_.subjectId}')" />
						</div>
					</form>
			</div>
			<div class="home_cont_r">
				<div class="home_cont_r1">
				<div class="home_cont_r1_h3">
					<form id="search_form" action="jy/thesis/index" method="post">
						<span>教学文章列表</span>
						<div class="selWrap">
							<c:choose>
							<c:when test="${!empty thesis.thesisTitle}">
								<label for="" id="whole"><a
									href="jy/thesis/index" style="color: #014efd;">全部<</a></label>
							</c:when>
							<c:otherwise>
								<label for="" id="whole"><span>&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
							</c:otherwise>
							</c:choose>
							<div class="ser_bor">
								<input type="text" class="txt_seh" id="search" name="thesisTitle"
								placeholder="输入文章名称进行搜索" value="${thesis.thesisTitle}" /> 
								<input type="button" class="txt_btn" onclick="searchContent()" />
							</div> 
				 		</div>
						<c:if test="${sysRoleId!=XZ.id && sysRoleId!=FXZ.id}">
							<input type="button" value="提交给上级" class="submit_up">
						</c:if>
					</form>
				</div>
				<div class="clear"></div>

				<div class="home_cont_r1_bottom">
						<c:choose>
							<c:when test="${!empty tList.datalist }">
								<div class="Pre_cont_right_1">

									<c:forEach items="${tList.datalist }" var="data">

										<div class="Pre_cont_right_1_dl">
											<dl>
												<dd>
													<a target="_blank"
														href="jy/thesis/thesisview?id=${data.id}"> <ui:icon
															ext="${data.fileSuffix}"></ui:icon></a>
												</dd>
												<dt>
													<span title="[${data.thesisType}] ${data.thesisTitle}">[${data.thesisType}]
														<ui:sout value="${data.thesisTitle}" length="18"
															needEllipsis="true"></ui:sout>
													</span> <strong><fmt:formatDate value="${data.lastupDttm}"
															pattern="yyyy-MM-dd" /></strong>
												</dt>
											</dl>
											<div class="show_p">
												<ol>
													<c:choose>
														<c:when test="${data.isShare==1 || data.isSubmit==1}">
															<li title="禁止修改" class="jz_li_edit"></li>
														</c:when>
														<c:otherwise>
															<li title="修改" class="li_edit"
																onclick="updateThesis('${data.id}','${data.thesisTitle}','${data.subjectId}','${data.thesisType}','${data.resId}')"></li>
														</c:otherwise>
													</c:choose>
														<c:if test="${data.isShare==0 && data.isSubmit==0}">
															<li title="删除" class="li_del"
																onclick="deleteThesis('${data.id}','${data.resId}','${data.thesisTitle}')"></li>
														</c:if>
														<c:if test="${data.isShare==1 || data.isSubmit==1}">
															<li title="禁止删除" class="jz_li_del"></li>
														</c:if>
													<c:choose>
														<c:when test="${data.isShare==1}">
															<li title="取消分享" class="qx_li_share"
																onclick="shareThesis('${data.id}',0,'${data.thesisTitle}')"></li>
															<!-- <li class="menu_li_32"></li> -->
														</c:when>
														<c:otherwise>
															<li title="分享" class="li_share"
																onclick="shareThesis('${data.id}',1,'${data.thesisTitle}')"></li>
														</c:otherwise>
													</c:choose>
														<a title="下载" href="<ui:download resid="${data.resId}" filename="${data.thesisTitle}"></ui:download>"><li class="li_down"></li></a>
													<c:if test="${data.isScan==1}">
														<c:if test="${data.scanUp==1}">
															<li title="查阅意见" class="li_yue check_option_t" data-id="${data.id}" data-scan="ture" data-type="10"><span class="span_dian"></span></li>
														</c:if>
														<c:if test="${data.scanUp!=1}">
															<li title="查阅意见" class="li_yue check_option_t" data-id="${data.id}" data-scan="ture" data-type="10"></li>
														</c:if>
													</c:if>
													<c:choose>
														<c:when test="${data.isComment==1||data.isComment==2}">
															<c:if test="${data.isComment!=2}">
																<li title="评论" class="li_ping menu_li_6" data-id="${data.id}" data-userId=${data.crtId }><span class="spot"></span></li>
															</c:if>
															<c:if test="${data.isComment==2}">
																<li title="评论" class="li_ping menu_li_6" data-id="${data.id}" data-userId=${data.crtId }></li>
															</c:if>
														</c:when>
														<c:otherwise>
														</c:otherwise>
													</c:choose>
												</ol>
											</div>
										</div>
									</c:forEach>
								</div>
								<div class="clear"></div>

								<form name="pageForm" method="post">
									<ui:page url="${ctx}jy/thesis/index" data="${tList}" />
									<input type="hidden" class="currentPage"
										name="currentPage">
								</form>
							</c:when>
							<c:otherwise>
								<!-- 无文件 -->
								<c:choose>
									<c:when test="${!empty thesis.thesisTitle }">
										<div class="empty_wrap">
										    <div class="empty_img"></div>
										    <div class="empty_info">
												很抱歉，没有找到相关内容，请输入内容重新查找！
										    </div> 
										</div>
									</c:when>
									<c:otherwise>
										<div class="empty_wrap">
										    <div class="empty_img"></div>
										    <div class="empty_info">
												您还没有上传教学文章哟，赶紧去左边“上传教学文章”吧！
										    </div> 
										</div>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
				</div>
			</div>
		</div>
	</div>
	<div id="submit_thesis" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">提交上级</span>
				<span class="dialog_close" onclick="location.reload()"></span>
			</div>
			<div class="dialog_content" style="overflow:hidden;">
				<div class="upload-bottom">
					<div class="upload-bottom_tab">
						<ul>
							<li id="wtjButton" class="upload-bottom_tab_blue">未提交</li>
							<li id="ytjButton" class="">已提交</li>
						</ul>
					</div>
					<div class="clear"></div>
					<iframe id="submit_iframe" name="submit_content"  width="800" height="600" frameborder="0" scrolling="no" style="overflow: hidden;"></iframe>
				</div>
			</div>
		</div>
	</div>
	<div id="thesis_option" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">查阅意见</span>
				<span class="dialog_close" onclick="location.reload()"></span>
			</div>
			<div class="dialog_content">
				<iframe name="checkedBox" id="checkedBox" style="width:100%;height:100%;border:0;"></iframe>
			</div>
		</div>
	</div>
	<div id="thesis_review" class="dialog"> 
		<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">评论意见</span>
			<span class="dialog_close" onclick="location.reload()"></span>
		</div>
		<div class="dialog_content">
			<iframe id="commentBox" style="border: none; width:100%; height:100%;"> </iframe>
			</div>
		</div>
	</div>
	<div id="thesis_share" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">分享</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="share_info">
					<h4>
					您确定要分享“<span id="res_title"></span>”反思吗？分享成功后，您的小伙伴们就可以看到喽！您也可以去“<a href="${ctx}jy/comres/index">同伴资源</a>”中查看其它小伙伴的反思哦！
					</h4>		
				</div>
				<div class="del_info" style="display:none;">
					您确定要取消分享吗？			
				</div>
				<div class="BtnWrap">
					<input type="button" id="querenbut" value="确定" class="confirm">
				    <input type="button" value="取消" class="cancel" data="" onclick="sharingClose()">  
				</div> 
			</div>
		</div>
	</div>
	<div id="thesis_del" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">删除</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					您确定要删除吗？				
				</div>
				<div class="BtnWrap">
					<input type="button" value="确定" class="confirm" id="bt_delete">
				    <input type="button" value="取消" class="cancel" id="bt_cancel_delete"> 
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	$(function(){
		$(".chosen-select-deselect").chosen({disable_search : true});
	});
</script>
</html>
