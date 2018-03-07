<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="教学反思"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/rethink/css/teaching_reflection.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<ui:require module="rethink/js"></ui:require>
	<style>
	.chosen-container .chosen-drop{
	width:99%;}
	</style>
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="教学反思"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="jxfs"></jy:nav></h3>
	</div>
	<div class="clear"></div>
		<input id="bkb_lessonId" type="hidden" value="${editModel.lessonId}">
		<input id="bkb_planId" type="hidden" value="${editModel.planId}">
		<input id="bkb_resId" type="hidden" value="${editModel.resId}">
		<input id="bkb_planType" type="hidden" value="${editModel.planType}">
	<div class="home_cont">
		<form id="hiddenForm" action="${ctx}jy/rethink/index" method="post">
			<input id="hi_lessonId" type="hidden" name="lessonId" >
			<input id="hi_planType" type="hidden" name="planType" value="${planType}">
			<c:if test="${!empty rethinkList.datalist }">
				<input id="hi_currentPage" type="hidden" name="currentPage" value="${rethinkList.page.currentPage }">
			</c:if>
		</form>
			<div class="home_cont_l"> 
				<h3 class="courseware_title"><span></span><strong>撰写反思</strong></h3>
					<form id="fs_form" action="${ctx}jy/rethink/save"  method="post" >
						<ui:token/>
						<input type="hidden" name="lessonId" id="lessonId">
						<input type="hidden" name="planName" id="planName">
						<input type="hidden" name="planId" id="planId">
						<div class="courseware_title_p">
							<span class="courseware_title_p_span">*</span>
							<label for="">反思类型：</label>
							<div class="project_directory_sel">
								<span id="type_zc">
								  <input id="planType_2" style="" name="planType" type="radio" value="2" checked="checked" onclick="changeTitle('khfs')">课后反思
								  <input id="planType_3" style="" name="planType" type="radio" value="3" onclick="changeTitle('qtfs')">其他反思
								</span>
								<span id="type_audit" style="display: none;">
									<span id="typeStr"></span>
									<input type="hidden" name="planType" id="typeVal" value="">
								</span>
							</div>
						</div> 
						<div class="courseware_title_p sel_fs_to" id="ktmlyz_to"> 
							<span class="courseware_title_p_span">*</span>
							<label for="" id="ktml">课题目录：</label>
							<span id="fsbt" style="display:none;">
								<label for="">反思标题：</label>
							</span>
							<div class="project_directory_sel" id="sel_fsbt" style="display:none;">
								<input type="text" id="sel_fs" maxlength="30" onchange="setValue('sel_fs')" >
							</div>
							<div class="project_directory_sel" id="sel_ktml">
								<select id="sel_kt" style="width:180px;height:25px;" class="chosen-select-deselect" onchange="setValue('sel_kt')">
										<option value="">请选择</option>
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
						</div> 
						<div class="courseware_title_p scfj_to" style="height:40px;" id="fileuploadContainer">
							<span class="courseware_title_p_span"></span>
							<label for="">上传附件：</label>  
							<ui:upload containerID="fileuploadContainer" fileType="docx,doc,pptx,ppt,pdf" fileSize="50" startElementId="save" beforeupload="start" callback="backSave" name="resId" relativePath="rethink/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload>
						</div>
						<div id="qt_jcsj" class="courseware_title_p" >
							<span class="courseware_title_p_span"></span>
							<label>教材书籍：</label>
							<select id="sel_qt_sjjc" name="bookId" style="width:180px;height:25px;" class="chosen-select-deselect" >
								<c:forEach items="${books }" var="book">
									<option value="${book.comId }">${book.comName }</option>
								</c:forEach>
							</select>
						</div>
						<div id="def_but" class="btn_bottom">
							<input id="save" type="button" class="uploadBtn" value='上传'> 
							<input id="noUpdate" type="button" class="uploadBtn uploadBtn0" value='不改了'  onclick="notUpdate()" style="display:none;">
						</div>
					</form>
			</div>
			<div class="home_cont_r">
				<div class="home_cont_r1">
				<div class="home_cont_r1_h3">
			 		<ul id="UL">
						<li <c:if test="${planType==2 }">class="active_1"</c:if> onclick="selectFSLX('2')">课后反思</li>
						<li <c:if test="${planType==3 }">class="active_1"</c:if> onclick="selectFSLX('3')">其他反思</li>
					</ul>
			 		<div class="selWrap">
			 			<c:if test="${planType==2 }">
			 			<label for="">课题目录：</label>
			 			<select id="selectKT" class="chosen-select-deselect" style="width:200px;height:25px;cursor: pointer;" onchange="selectKT()">
								<option value="">全部</option>
								<optgroup label="${fasiciculeName}">
								<c:forEach items="${bookChapters }" var="bookChapter">
									<ui:bookChapter data="${bookChapter }" selectedid="${lessonId }"></ui:bookChapter>
								</c:forEach>
								</optgroup>
								<c:if test="${not empty fasiciculeName2 }">
									<optgroup label="${fasiciculeName2}">
									<c:forEach items="${bookChapters2 }"  var="bookChapter">
										<ui:bookChapter data="${bookChapter }" selectedid="${lessonId }"></ui:bookChapter>
									</c:forEach>
									</optgroup>
								</c:if>
							</select>
							</c:if>
			 		</div>
			 		<input type="button" class="submit_up" value='提交给上级'>
			 	</div>
				<div class="clear"></div>
				
   				<div class="home_cont_r1_bottom">
					<div class="Pre_cont_right_1">
						<c:choose>
		           			<c:when test="${!empty rethinkList.datalist }">
								
								<c:forEach items="${rethinkList.datalist  }" var="data">
									
									<div class="Pre_cont_right_1_dl">
										<dl onclick="scanResFile('${data.resId }')">
											<jy:ds key="${data.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
											<dd><ui:icon ext="${res.ext }"></ui:icon></dd>
											<dt style="word-break:break-all;">
												<span id="planid_${data.planId }" title="${data.planName }" ><ui:sout value="${data.planName }" length="40" needEllipsis="true"></ui:sout></span>
												<strong style="display:block;"><fmt:formatDate value="${data.lastupDttm  }" pattern="yyyy-MM-dd"/></strong>
												<input type="hidden" id="planName_${data.planId }" value="${data.planName }">
											</dt>
										</dl>
										<div class="show_p">
											<ol>
												<c:choose>
													<c:when test="${data.isSubmit || data.isShare}">
														<li title="禁止修改" class="jz_li_edit"></li>
														<li title="禁止删除" class="jz_li_del"></li>
													</c:when>
													<c:otherwise>
														<li title="修改" class="li_edit" onclick="updateThis('${data.planId}','${data.planType }','${data.lessonId }','${data.resId }')"></li>
														<li title="删除" class="li_del" onclick="deleteThis('${data.planId}','${data.resId }','${data.planType }')"></li>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${data.isShare}">
														<c:if test="${!data.isComment }"><li title="取消分享" class="qx_li_share" onclick="sharingThis('${data.planType }','${data.planId}',false)"></li></c:if>
														<c:if test="${data.isComment }"><li title="禁止取消分享" class="jz_li_share"></li></c:if>
													</c:when>
													<c:otherwise>
														<li title="分享" class="li_share" onclick="sharingThis('${data.planType }','${data.planId}',true)"></li>
													</c:otherwise>
												</c:choose>
												<a title="下载" href="<ui:download resid="${data.resId}" filename="${data.planName }"></ui:download>"><li class="li_down"></li></a>
												<c:if test="${data.isScan}">
													<c:if test="${data.scanUp}"><li title="查阅意见" class="li_yue" onclick="cyyj('${data.planType}','${data.infoId}','${data.planId}','true')"><span class="spot"></span></li></c:if>
													<c:if test="${!data.scanUp}"><li title="查阅意见" class="li_yue" onclick="cyyj('${data.planType}','${data.infoId}','${data.planId}','false')"></li></c:if>
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
									<ui:page url="${ctx}jy/rethink/index" data="${rethinkList}"  />
									<input type="hidden" class="currentPage" name="currentPage">
									<input type="hidden" name="planType" value="${planType}">
									<input type="hidden" name="lessonId" value="${lessonId}" <c:if test="${empty lessonId}">disabled="disabled"</c:if> >
								</form>
		           			</c:when>
		           			<c:otherwise>
		           				<!-- 无文件 -->
		           				<div class="empty_wrap">
								    <div class="empty_img"></div>
								    <div class="empty_info">您还没有上传反思哟，赶紧去左边“上传反思”吧！</div> 
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
		<div id="submit_fs" class="dialog"> 
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
						</div>
						<div class="clear"></div>
						<iframe id="submit_iframe" name="submit_content"  width="800" height="545" frameborder="0" scrolling="no" style="overflow: hidden;"></iframe>
					</div>
				</div>
			</div>
		</div>
	<div id="jxfs_review" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">评论意见</span>
				<span class="dialog_close" onclick="location.reload()"></span>
			</div>
			<div class="dialog_content">
				<iframe name="comment" id="commentBox" style="width:100%;height:100%;border:0;"></iframe>
			</div>
		</div>
	</div>
	<div id="jxfs_option" class="dialog"> 
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
	<div id="jxfs_share" class="dialog"> 
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
	<div id="jxfs_del" class="dialog"> 
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
$(function(){
	$(".chosen-select-deselect").chosen({disable_search : true});
});
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','teaching_reflection'],function(){});
</script>
</html>
