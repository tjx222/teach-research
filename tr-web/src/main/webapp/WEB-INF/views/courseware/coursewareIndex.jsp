<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="上传课件"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/courseware/css/courseware.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/courseware/css/dlog_submit.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	
	<script type="text/javascript" src="${ctxStatic }/modules/courseware/js/courseware.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<ui:require module="courseware/js"></ui:require>
	<style>
	.chosen-container .chosen-drop{
	width:99%;}
	</style>
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="上传课件"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="sckj"></jy:nav></h3>
	</div>
	<div class="clear"></div>
		<input id="bkb_flago" type="hidden" value="${editPlanId.flago}">
		<input id="bkb_lessonId" type="hidden" value="${editPlanId.lessonId}">
		<input id="bkb_planId" type="hidden" value="${editPlanId.planId}">
		<input id="bkb_resId" type="hidden" value="${editPlanId.resId}">
	<div class='home_cont'>
		<form id="hiddenForm" action="${ctx}jy/courseware/index" method="post">
			<input id="hi_lessonId" type="hidden" name="lessonId" >
			<input type="hidden" name="currentPage" value="${coursewareList.page.currentPage }">
		</form>
			<div class="home_cont_l">
				<h3 class="courseware_title"><span></span><strong>上传课件</strong></h3>
				<form id="kj_form" action="${ctx}jy/courseware/save" method="post">
					<ui:token/>
					<input type="hidden" name="planType" value="1">
					<input type="hidden" name="lessonId" id="lessonId">
					<input type="hidden" name="planName" id="planName">
					<input type="hidden" name="planId" id="planId">
					<div class="courseware_title_p" id="ktmlyz_to">
						<span class="courseware_title_p_span">*</span>
						<label for="">课题目录：</label>
						<div class="project_directory_sel">
							<select id="ktml" style="width:180px;height:25px;" class="chosen-select-deselect" onchange="setValue()" tabindex="2">
								<option value="">请选择</option>
								<optgroup label="${fasiciculeName}">
								<c:forEach items="${bookChapters }" var="bookChapter">
									<ui:bookChapter data="${bookChapter }" selectedid="${lessonId }"></ui:bookChapter>
								</c:forEach>
								</optgroup>
								<c:if test="${not empty fasiciculeName2 }">
									<optgroup label="${fasiciculeName2}">
									<c:forEach items="${bookChapters2 }" var="bookChapter2">
										<ui:bookChapter data="${bookChapter2 }" selectedid="${lessonId }"></ui:bookChapter>
									</c:forEach>
									</optgroup>
								</c:if>
								
							</select>
						</div>
						</div>
						<div id="fileuploadContainer" class="courseware_title_p scfj_to">
							<span class="courseware_title_p_span">*</span>
							<label for="">上传附件：</label>
							<ui:upload containerID="fileuploadContainer" fileType="ppt,pptx,zip,rar" fileSize="50" startElementId="save" beforeupload="start" callback="backSave" name="resId" relativePath="courseware/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload>
						</div>
						<div class="courseware_title_p" style="height:180px;">
							<span class="courseware_title_p_span"></span>
							<label for="">课件描述：</label>
							<div class="project_directory_sel">
								<textarea name="digest" id="kjms" cols="30" rows="10" class="validate[maxSize[40]]"></textarea>
							</div>
						</div>
						<div class="clear"></div>
						<div class="btn_bottom" style="margin-top:20px;">
							<input id="save" type="button" class="uploadBtn" value='上传'>
							<input id="empty" type="button" class="uploadBtn uploadBtn0" value='不改了' onclick="notUpdate()" style="display:none;">
						</div>
					</form>
			</div>
			<div class="home_cont_r">
				<div class="home_cont_r1">
				<div class="home_cont_r1_h3">
			 		<span>课件列表</span>  
			 		<div class="selWrap">
			 			<label for="">课题目录：</label>
			 			<select id="selectKT" class="chosen-select-deselect" style="width:200px;height:25px;margin-top:6px;cursor: pointer;" onchange="selectKJ()">
						<option value="">全部</option>
						<optgroup label="${fasiciculeName}">
						<c:forEach items="${bookChapters }" var="bookChapter">
							<ui:bookChapter data="${bookChapter }" selectedid="${lessonId }"></ui:bookChapter>
						</c:forEach>
						</optgroup>
						<c:if test="${not empty fasiciculeName2}">
							<optgroup label="${fasiciculeName2}">
							<c:forEach items="${bookChapters2 }" var="bookChapter">
								<ui:bookChapter data="${bookChapter }" selectedid="${lessonId }"></ui:bookChapter>
							</c:forEach>
							</optgroup>
						</c:if>
					</select>
			 		</div>
			 		<input type="button" class="submit_up" value='提交给上级'>
			 	</div>
				<div class="clear"></div>
				
				<div class="home_cont_r1_bottom">
					<div class="Pre_cont_right_1">
						<c:choose>
		           			<c:when test="${!empty coursewareList.datalist }">
								<c:forEach items="${coursewareList.datalist  }" var="data">
									<div class="Pre_cont_right_1_dl">
										<dl onclick="scanResFile('${data.resId }')">
											<jy:ds key="${data.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
											<dd><ui:icon ext="${res.ext }" title="${data.digest}"></ui:icon></dd>
											<dt>
												<span title="${data.planName }" ><ui:sout value="${data.planName }" length="40" needEllipsis="true"></ui:sout></span>
												<strong style="display:block;"><fmt:formatDate value="${data.lastupDttm  }" pattern="yyyy-MM-dd"/></strong>
												<input type="hidden" id="planName_${data.planId}" value="${data.planName }"/>
												<input type="hidden" id="digest_${data.planId}" value="${data.digest }"/>
											</dt>
										</dl>
										<div class="show_p">
											<ol>
												<c:choose>
													<c:when test="${data.isSubmit || data.isShare || data.origin == 1}">
														<li title="禁止修改" class="jz_li_edit"></li>
														<li title="禁止删除" class="jz_li_del"></li>
													</c:when>
													<c:otherwise>
														<li title="修改" class="li_edit" onclick="updateThis('${data.planId}','${data.lessonId }','${data.resId }')"></li>
														<li title="删除" class="li_del" onclick="deleteThis('${data.planId}')"></li>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${data.isShare}">
														<c:if test="${!data.isComment}"><li title="取消分享" class="qx_li_share" onclick="sharingThis('${data.planId }',false)"></li></c:if>
														<c:if test="${data.isComment}"><li title="禁止取消分享" class="jz_li_share"></li></c:if>
													</c:when>
													<c:otherwise>
														<li title="分享" class="li_share" onclick="sharingThis('${data.planId }',true)"></li>
													</c:otherwise>
												</c:choose>
												<c:if test="${data.origin != 1 }">
													<a title="下载" href="<ui:download resid="${data.resId}" filename="${data.planName }"></ui:download>"><li class="li_down"></li></a>
												</c:if>
												
												<c:if test="${data.isScan}">
													<c:if test="${data.scanUp}"><li title="查阅意见" class="li_yue" onclick="showScanListBox('${data.planType}','${data.infoId}','true')"><span class="spot"></span></li></c:if>
													<c:if test="${!data.scanUp}"><li title="查阅意见" class="li_yue" onclick="showScanListBox('${data.planType}','${data.infoId}','false')"></li></c:if>
												</c:if>
												<c:if test="${data.isComment }">
													<c:if test="${data.commentUp }"><li title="评论意见" class="li_ping" onclick="showCommentListBox('${data.planType}','${data.planId}','true')"><span class="spot"></span></li></c:if>
													<c:if test="${!data.commentUp }"><li title="评论意见" class="li_ping" onclick="showCommentListBox('${data.planType}','${data.planId}','false')"></li></c:if>
												</c:if>
											</ol>
										</div>
									</div>
									
								</c:forEach>
								<div class="clear"></div>
								<form name="pageForm" method="post">
									<ui:page url="${ctx}jy/courseware/index" data="${coursewareList}"  />
									<input type="hidden" class="currentPage" name="currentPage">
									<input type="hidden" name="lessonId" value="${lessonId}" <c:if test="${empty lessonId}">disabled="disabled"</c:if> >
								</form>
								
		           			</c:when>
		           			<c:otherwise>
		           				<!-- 无文件 -->
								<div class="empty_wrap"> 
								    <div class="empty_info">您还没有上传课件哟，赶紧去左边“上传课件”吧！</div> 
								</div>
		           			</c:otherwise>
		           		</c:choose>
					</div>
				</div>
				
			</div>
		</div>
	</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
		<div id="submit_kj" class="dialog"> 
			<div class="dialog_wrap"> 
				<div class="dialog_head">
					<span class="dialog_title">提交上级</span>
					<span class="dialog_close" onclick="location.reload()"></span>
				</div>
				<div class="dialog_content" id="load_submit_bkb">
					<div class="upload-bottom">	
						<div class="upload-bottom_tab">
							<ul>
								<li id="wtjButton" class="upload-bottom_tab_blue">未提交</li>
								<li id="ytjButton" class="">已提交</li>
							</ul>
							<div class="clear"></div>
							<iframe id="submit_iframe" name="submit_content"  width="800" height="470" frameborder="0" scrolling="no" style="overflow: hidden;"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	<div id="course_option" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">查阅意见</span>
				<span class="dialog_close" onclick="location.reload()"></span>
			</div>
			<div class="dialog_content">
				<iframe name="checkedBox" id="checkedBox" style="width:100%;height:100%;" frameborder="0"></iframe>
			</div>
		</div>
	</div>
	<div id="course_review" class="dialog"> 
		<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">评论意见</span>
			<span class="dialog_close" onclick="location.reload()"></span>
		</div>
		<div class="dialog_content">
			<iframe name="comment" id="commentBox" style="width:100%;height:100%;" frameborder="0"></iframe>
		</div>
		</div>
	</div>
	<div id="course_share" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">分享</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="share_info">
					<h4>
					您确定要分享“<span id="res_title"></span>”课件吗？分享成功后，您的小伙伴们就可以看到喽！您也可以去“<a href="${ctx}jy/comres/index">同伴资源</a>”中查看其它小伙伴的课件哦！
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
	<div id="course_del" class="dialog"> 
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
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min'],function(){});
		$(function(){
			$(".chosen-select-deselect").chosen({disable_search : true});
			$("#kj_form").validationEngine();
		});
	</script>
</html>
