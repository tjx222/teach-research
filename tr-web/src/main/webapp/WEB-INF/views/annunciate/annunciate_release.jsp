<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="通知公告"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/annunciate/css/notice.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen"> 
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<ui:require module="annunciate/js"></ui:require>
<style>
.dlog-body {
	overflow: auto;
	height: 487px;
} 
</style>
</head>
<body>
 <div id="preview_box" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">预览</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content" style="height: 500px;overflow: auto;overflow-x:hidden;">
				<div class='file_wrap'> 
					<div class="file_wrap_bottom">
						<h3 class="pt_file_wrap_h3"><span></span></h3> 
						<div class="clear"></div>
						<h4 class="file_wrap_bottom_h4">发布时间：&nbsp;&nbsp;|&nbsp;&nbsp;作者：</h4> 
						<div class="file_wrap_border"></div>
						<p></p>
					</div>
					<%-- <div class="file_enclosure">
						<h3>
							<span></span>
							<strong>附件</strong>
							<b>3</b>
						</h3>
						<div class="file_enclosure_cont">
							<c:forEach items="${rList}" var="res">
								<dl onclick="scanResFile('${res.id}');">
									<dd></dd>
									<dt><ui:sout value="${res.name}.${res.ext }" length="30" needEllipsis="true" ></ui:sout></dt>
								</dl>
							</c:forEach>
						</div>
					</div> --%>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="assessment_committee_wrap" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">选择学校</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="asses_scope_content">
					<div class="Invitation_bottom">
						<div class="centent"> 
							<div class="search_wrap">
								<input type="text" placeholder="输入学校名称进行查找" id="search" class="search2">
								<input type="button" class="btn">
							</div>
							<span style="margin-left:78px;font-size:14px;display: inline-block;padding-top: 11px;" id="selectOrg">已选学校(0)：</span>
							<div class="clear"></div>
							<div class="centent_left">
								<ul id="ul1">
								</ul>
							</div>
							<div class="centent_center">
								<input type="button" value="添加" class="add_1" id="add_1">
								<input type="button" value="删除" class="add_2" id="add_2">
							</div>
							<div class="centent_right">
								<ul id="ul2">
									<div id="quanxuan2" style="display:none;"><input type="checkbox" name="checkbox2"  onclick='quanxuan(this);'>&nbsp;全选<div class="clear"></div></div>
									<c:forEach items="${orgList}" var="org">
										<li id="s_${org.id }" value="${org.id }">
											<input type="checkbox" name="checkbox1">
											<a>${org.name }</a> 
										</li>
								 	</c:forEach>
									<!--<li>
										<input type="checkbox" name="checkbox1">
										<a>明德小学</a> 
									</li> -->
								</ul>
							</div>
							<div class="clear"></div>
							<input type="button" class="btn_sub" value="确定">
						</div>	
					</div>
				</div>
			</div>
		</div> 
	</div>
	<div class="wrapper">
		<div class="jyyl_top"><ui:tchTop style="1" modelName="通知公告"></ui:tchTop></div>
		<div class="jyyl_nav">
			<h3>
				当前位置：<jy:nav id="fbtzgg">
							<jy:param name="tzggname" value="发布通知公告"></jy:param>
						</jy:nav>
			</h3>
		</div>
		<div class="clear"></div>
		<div class='fq_activity_cont'>
			<form id="annunciate_form" method="get">
				<input type="hidden" id="annunciateType" name="annunciateType" value="${jaAnnunciate.annunciateType}">
				<input type="hidden" id="id" name="id" value="${jaAnnunciate.id}">
				<input type="hidden" id="redTitleId" name="redTitleId" value="${jaAnnunciate.redTitleId }">
				<input type="hidden" id="orgsjoinIds" name="orgsJoinIds" value="${jaAnnunciate.orgsJoinIds }">
				<input type="hidden" id="orgsJoinCount" name="orgsJoinCount" value="${jaAnnunciate.orgsJoinCount }">
				<input type="hidden" id="name" name="name" value="${_CURRENT_USER_.name}">
				<div class='activity_info_wrap'>
					<div class='activity_type'>
						<div class='activity_type_icon'></div>
						<div class='activity_type_right'>
							<h3 class='activity_type_right_h3'>类型<span>*</span></h3>
							<div class='tbja'> 
								<input type='radio' id='tbja' name="type" value="0" ${jaAnnunciate.type==0||type==0? 'checked':''} checked/>
								<label for='tbja'>普通文件</label>
							</div>
							<div class='ztyt'> 
								<input type='radio' id='ztyt' name="type" value="1" ${jaAnnunciate.type==1||type==1? 'checked':''}/>
								<label for='ztyt'>红头文件</label>
							</div>
								
						</div>
					</div>
					<div class='red_header' id='red_header'>
						<div class='red_header_icon'></div>
						<div class='red_header_right'>
							<h3 class='red_header_right_h3'>红头<span>*</span></h3>
							<div class="red_head_title">
							 	<c:if test="${empty list}"> 
									<input type="button" class="ht_btn" value="+ 添加红头标题"/> 
									<input type="hidden"  id="changRedHead" value="1"/>
								</c:if>
								<c:if test="${!empty list}">
									<h3><strong id="titleId"><c:if test="${jaAnnunciate.redTitleId!=0&&jrt.isDelete==0}">${jrt.title}</c:if></strong><span class='down_sel'></span></h3>
									<div class="red_head_title_name">
										<div class="hover_sj1"></div>
										<div class="red_head_cont">
											<c:forEach items="${list}" var="l">
												<div class="red_head_name" data-id="${l.id}">
													<span title='${l.title}' id="ht_${l.id }" ><ui:sout value="${l.title}" length="80" needEllipsis="true" ></ui:sout></span><b id="htremove_${l.id }"></b>  
												</div>
											</c:forEach>
											<div class="red_head_name">
												<input type="button" class="add_red_btn" value='添加' />
											</div>
										</div>
									</div>
									<c:choose>
										<c:when test="${empty jaAnnunciate.fromWhere}">
											<input type="text" id="fromWhere" class="validate[required,maxSize[20]]" name="fromWhere"style="border: white; margin-top:30px; text-align:center; width:400px;" value="XXX(20XX)XX号">
										</c:when>
										<c:otherwise>
											<input type="text" id="fromWhere" class="validate[required,maxSize[20]]" name="fromWhere"style="border: white; margin-top:30px; text-align:center; width:400px;" value="${jaAnnunciate.fromWhere}">
										</c:otherwise>
									</c:choose>
								</c:if>
								<div class="red_head_title_save" style="display:none;">
									<input type="text" class="redtitle" id="redTitle" name="redTitle" value="">
									<input type="button" class="saveRed" value="保存">
									<input type="button" class="cancel"  value="取消">
								</div>
							</div>
						</div>
					</div>
					<div class='activity_theme'>
						<div class='activity_theme_icon'></div>
						<div class='activity_theme_right'>
							<h3 class='activity_theme_right_h3'>标题<span>*</span></h3>
							<input type="text" id="title" name="title" class='theme_txt validate[required,maxSize[200]]' value="${jaAnnunciate.title}"/>
						</div> 
					</div>
					<div class='activity_require'>
						<div class='activity_require_icon'></div>
						<div class='activity_require_right'>
							<h3 class='activity_require_right_h3'>内容<span>*</span></h3> 
							<div class="editor">
								<textarea id="web_editor" name="content" style="float:right">${jaAnnunciate.content}</textarea>
							</div>
						</div> 
					</div> 
					<div class='reference_material' style="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr') ? '' : 'border-left:none;'}">
						<div class='reference_material_icon'></div>
						<div class='reference_material_right'>
							<h3 class='reference_material_right_h3'>附件</h3>
							<div class='upload_files'> 
								<div id="fileuploadContainer">
									<div id="span1">
										<input type="hidden" id="tzggRes" name="attachs">
										<ui:upload containerID="span1"
											fileType="doc,docx,ppt,pptx,flV,swf,txt,pdf,zip,rar"
											fileSize="50" startElementId="uploadTzgg"
											beforeupload="beforeUpload" callback="backUpload"
											name="backresId" relativePath="/annunciate/o_${_CURRENT_USER_.orgId}"></ui:upload>
									</div>
									<input type="button" id="uploadTzgg" class="file_btn" value="上传附件" />
								</div>
								<div class='files' id="resdownload">
									<c:forEach items="${rList}" var="res">
										<c:if test="${!empty res.id}">
											<div class='files_wrap' data-resId="${res.id}">
												<div class='files_wrap_left'></div>
												<div class='files_wrap_center'>
													<ui:sout value="${res.name}.${res.ext }" length="30" needEllipsis="true"></ui:sout>
												</div>
												<div class='files_wrap_right'></div>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>   
						</div>
						<div class='clear'></div> 
					</div>
					<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
						<input type="hidden" id="roleId" name="roleId" value="${_CURRENT_SPACE_.sysRoleId}">
						<div class='participation_scope'>
							<div class='participation_scope_icon'></div>
							<div class='participation_scope_right'>
								<h3 class='participation_scope_right_h3'>发布范围<span>*</span>：</h3>   
								<div class='participation_subject'>
									 <h3><span id="orgCount">参与学校（<c:if test="${empty jaAnnunciate.orgsJoinCount}">0</c:if><c:if test="${!empty jaAnnunciate.orgsJoinCount}">${jaAnnunciate.orgsJoinCount}</c:if>）</span>：<span class='please_select'>请选择</span></h3>
									 <div class='participation_subject_b'>
									 	<ul id="ul3">
									 		<c:forEach items="${orgList}" var="org">
									 			<li id="${org.id }"><span></span><strong title="${org.name }">${org.name }</strong><b id="${org.id }"></b></li>
									 		</c:forEach>
									 	</ul>
									 </div>
									 <div class='clear'></div>
								</div>
							</div> 
						</div>
					</c:if>
					<div class="publish_index">
						<input type="checkbox" id='publish_check' ${jaAnnunciate.isDisplay==1?'checked':''} name="isDisplay" value="1">
						<c:choose>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
								<label for="publish_check">发布到区域首页</label>
							</c:when>
							<c:otherwise>
								<label for="publish_check">发布到学校首页</label>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class='activity_info_border'></div>
				<div class="activity_btn">
					<input type="button" class='publish'  data-state="true"/>
					<input type="button" class='preview' />
					<input type="button" class='save_drafts'  data-state="false"/>
				</div>
			</form> 
		</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','annunciate'],function(){
		
	});
</script>
</html>
