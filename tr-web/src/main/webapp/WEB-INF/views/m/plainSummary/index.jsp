<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,initial-scale=1.0" />
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" />
	<meta content="telephone=no" name="format-detection" />	
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="计划总结"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/common/css/index_m.css" media="screen" />
	<link rel="stylesheet" href="${ctxStatic }/m/plainSummary/css/plainSummary.css" media="screen" /> 
	<ui:require module="../m/plainSummary/js"></ui:require>	
</head>
<body>
<div class="share_upload_wrap">
	<div class="share_upload">
		<div class="share_upload_title">
			<h3>分享计划总结</h3>
			<span class="close" data-title="share"></span>
		</div>
		<div class="share_upload_content">
			<div class="share_width">
				<q></q>
				<span>您确定要分享该计划总结吗？分享后，您的小伙伴就可以看到喽！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="submit_upload_wrap">
	<div class="submit_upload">
		<div class="submit_upload_title">
			<h3>提交计划总结</h3>
			<span class="close" data-title="submit"></span>
		</div>
		<div class="submit_upload_content">
			<div class="submit_width">
				<q></q>
				<span>您确定要提交该计划总结吗？提交后，学校管理者将看到这些内容！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="publish_upload_wrap">
	<div class="publish_upload">
		<div class="publish_upload_title">
			<h3>发布计划总结</h3>
			<span class="close" data-title="publish"></span>
		</div>
		<div class="publish_upload_content">
			<div class="publish_width">
				<q class="dlog_publish"></q>
				<span>您确定要发布该计划总结吗？提交后，学校管理者将看到这些内容！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="del_upload_wrap">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除计划总结</h3>
			<span class="close" data-title="del"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该计划总结吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>

<div class="add_upload_wrap">
	<div class="add_upload">
		<div class="add_upload_title">
			<h3>撰写年级计划</h3>
			<span class="close" data-title="add"></span>
		</div>
		<div class="add_upload_content">			
			<form id="ps_form" method="post">
				<ui:token />
				<input type="hidden" name="category" id="category" value="3" />
				<input type="hidden" name="id" id="ps_id" value="" />
				<input type="hidden" name="contentFileKey" id="contentFileKey" />
				<input type="hidden" name="contentFileName" id="contentFileName" />	
				<input type="hidden" name="contentFileType" id="contentFileType" />
				<input type="hidden" name="userSpaceId"	id="userSpaceId" />
				<input type="hidden" name="sysRoleId"	id="sysRoleId" />
				<input type="hidden" id="subject" />
				<input type="hidden" id="grade" />
				<div class="form_input">
					<label>标题</label>
					<input type="text" class="form_tx" name="title" data-default="true"  placeholder="请输入标题">
				</div>
				<div id="fileuploadContainer" class="form_input">
					<label style="background-color: #fff; position: absolute;z-index: 1;">文件</label>
					<strong id="uploadId" style="margin-left:8.8rem;">
						<ui:upload_m fileType="doc,docx,ppt,pptx,pdf" fileSize="50" relativePath="plansummary" startElementId="savePs" beforeupload="startUpload" callback="afterUpload"></ui:upload_m>
					</strong>
				</div>
				<input type="button" class="btn_upload" id="savePs" value="上传">
				<div class="btn_sc" style="display: none;">
					<div class="spinner ">
					  <div class="rect1"></div>
					  <div class="rect2"></div>
					  <div class="rect3"></div>
					  <div class="rect4"></div>
					  <div class="rect5"></div>
					</div>
					<span>上传中...</span>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 查阅意见 -->
<div class="opinions_comment_wrap" id="checkComment">
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>查阅意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_scan" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
<!-- 评论意见 -->
<div class="opinions_comment_wrap" id="comment_div">
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>评论意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_comment" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
<div class="plainSummary_wrap">
	<div class="plainSummary_mask">
		<div class="plainSummary_mask_l" style="display:none;" data-category="1">
			<span>个人计划</span>
		</div> 
		<div class="plainSummary_mask_l" style="display:none;" data-category="3">
			<span>当前身份计划</span>
		</div>
		<div class="plainSummary_mask_r" style="display:none;" data-category="2">
			<span>个人总结</span>
		</div>
		<div class="plainSummary_mask_r" style="display:none;" data-category="4">
			<span>当前身份总结</span>
		</div>
	</div>
</div>
<div class="mask"></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>计划总结
	</header>
	<section> 
		<div class="plainSummary_content">
			<div class="plainSummary_content_l"></div>
			<div class="plainSummary_content_c" >
				<div class="plainSummary_content_c1">
				<div>
				<input type="hidden" id="scrollLeft" name="scrollLeft" value="${scrollLeft }"/>	
				<ul>
					<c:if test="${totalUsNum>0 }">
						<c:forEach items="${usl }" var="us" begin="0" end="${totalUsNum-1 }">
							<li id="${us.id }">
								<a href="javascript:void(0)" title="${us.spaceName }">
									<ui:sout value="${us.spaceName }" escapeXml="false" length="22" needEllipsis="true" />
								</a>
							</li>
						</c:forEach>
					</c:if>
				</ul>
				
				</div>
				</div>
				<input type="hidden" id="schoolYear" name="schoolYear" value="${schoolYear }" />
				<input type="hidden" id="currentUserId" name="currentUserId" value="${_CURRENT_USER_.id}" />
				<input type="hidden" id="schoolTerm" name="schoolTerm" value="${schoolTerm }" />							
				<div class="plain_center_cont_wrap"> 
					<c:forEach items="${usl }" var="us" begin="0" end="${totalUsNum-1 }">
						<div class="plain_center_cont" id="plain_center_cont">
							<div>
								<div class="content_bottom_width"
								data-sysRoleId="${us.sysRoleId }"
								data-userSpaceId="${us.id }"
								data-subject="<jy:dic key='${us.subjectId}'/>"
								data-grade="<jy:dic key='${us.gradeId}'/>">
									<c:set var="add_edit_del_share" value="${(jfn:checkSysRole(us.sysRoleId,'TEACHER')||jfn:checkSysRole(us.sysRoleId,'BKZZ')||jfn:checkSysRole(us.sysRoleId,'NJZZ')||jfn:checkSysRole(us.sysRoleId,'XKZZ')||jfn:checkSysRole(us.sysRoleId,'XZ')||jfn:checkSysRole(us.sysRoleId,'ZR')||jfn:checkSysRole(us.sysRoleId,'FXZ'))?1:0}" />
									<c:if test="${add_edit_del_share eq '1' }"> 
									<div class="add_cour">
										<div class="add_cour_div">
											<div class="add_cour_div_top">
												<div class="add_cour_div_top_img"></div> 
											</div>
											<div class="add_cour_div_bottom">撰写计划总结</div>
										</div>
									</div>	
									</c:if>						
									<c:forEach items="${plianSummaryMap[us.id] }" var="ps">
									<div class="courseware_ppt" 
										data-id="${ps.id }" 
										data-title="${ps.title }"
										data-category="${ps.category }"
										data-fileName="${ps.contentFileName }"
										data-fileId="${ps.contentFileKey }"
										data-roleId="${us.sysRoleId }"
										data-term="${ps.term }"
										data-userId="${ps.userId }"
										data-gradeId="${ps.gradeId }"
										data-subjectId="${ps.subjectId }">
										<div class="courseware_img_1">										
										</div>
										<h3>										
											<ui:sout value="${ps.title }" escapeXml="false" length="36" needEllipsis="true" />
										</h3>
										<p class="onlineScan"><img src="${ctxStatic }/m/plainSummary/images/word.png" /></p>
										<div class="courseware_img_2"></div>
										<!-- 查阅 -->
										<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'XZ')&&!jfn:checkSysRole(us.sysRoleId,'FXZ') && ps.isCheck!=0 }">		
										   <div class="courseware_img_3" title="查阅" data-status="${ps.isCheck }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></div>
										</c:if>	
										<!-- 评论 -->
										<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'XZ')&&!jfn:checkSysRole(us.sysRoleId,'FXZ') && ps.isReview!=0 }">
											<div class="courseware_img_4" title="评论" data-status="${ps.isReview }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></div>		
										</c:if>											
										<div class="cw_option_mask" style="display:none;"></div>
										<div class="cw_option" style="display:none;">
											<!-- 编辑 -->
											<c:set var="unableEdit"  value="${(ps.isPunish==0&&ps.isShare==0&&ps.isSubmit==0 && add_edit_del_share eq '1')?0:1}"/>
											<div class="cw_option_${unableEdit==0?'':'jz_' }edit" title="${unableEdit==0?'编辑':'禁止编辑' }" data-status="${unableEdit }"></div>
											
											<c:set var="publishHide1" value="${(jfn:checkSysRole(us.sysRoleId,'ZR')||jfn:checkSysRole(us.sysRoleId,'XZ')||jfn:checkSysRole(us.sysRoleId,'FXZ')) && (ps.category==1||ps.category==2) ? '1' : '' }" />
											<c:set var="publishHide2" value="${(jfn:checkSysRole(us.sysRoleId,'BKZZ')||jfn:checkSysRole(us.sysRoleId,'XKZZ')||jfn:checkSysRole(us.sysRoleId,'NJZZ')) ? '2' : '' }" />
											<!-- 删除 -->
											<c:set var="unableDelete" value="${(ps.isPunish==0&&ps.isShare==0&&ps.isSubmit==0 && add_edit_del_share eq '1')?0:1}"/>
											<div class="cw_option_${unableDelete==0?'':'jz_' }del${publishHide1}${publishHide2}" title="${unableDelete==0?'删除':'禁止删除' }" data-status="${unableDelete }"></div>												
											<!-- 提交-->								
											<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'XZ')&&!jfn:checkSysRole(us.sysRoleId,'FXZ') }">
												<c:set var="submitState" value="${(ps.isSubmit==1&&ps.isCheck!=0)?2:ps.isSubmit }"/>
												<div class="cw_option_${submitState==0?'':(submitState==2?'jz_':'qx_') }submit${publishHide2}" title="${submitState==0?'提交':(submitState==2?'禁止取消提交':'取消提交') }" data-status="${submitState }"></div>
											</c:if>
											<!-- 发布-->
											<c:if test="${!jfn:checkSysRole(us.sysRoleId,'TEACHER') && (ps.category==3||ps.category==4)}">
												<div class="cw_option_${ps.isPunish==0?'':'qx_'}publish${publishHide2}" title="${ps.isPunish==0?'发布':'取消发布'}" data-status="${ps.isPunish }"></div>
											</c:if>
											<!-- 分享-->
											<c:set var="shareState" value="${(ps.isShare==1&&ps.isReview!=0 && add_edit_del_share eq '1')?2:ps.isShare }"/>
											<div class="cw_option_${shareState==0?'':(shareState==2?'jz_':'qx_') }share${publishHide1}${publishHide2}" title="${shareState==0?'分享':(shareState==2?'禁止取消分享':'取消分享') }" data-status="${shareState }"></div>
											<!-- 下载-->											
											<a class="cw_option_down" title="下载" href="<ui:download resid='${ps.contentFileKey }' filename='${ps.contentFileName }'></ui:download>" data-ext="${ps.contentFileType }"></a>											
											<!-- 关闭-->
											<div class="cw_option_close" ></div>
										</div>
									</div> 
									</c:forEach>
								</div>
							</div>
						</div>
					</c:forEach>
				</div> 
			</div>
			<div class="plainSummary_content_r"></div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','js'],function($){	
	});  
</script>
</html>