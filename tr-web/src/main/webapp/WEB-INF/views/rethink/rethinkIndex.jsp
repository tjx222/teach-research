<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="教学反思"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/rethink/css/rethink.css" media="screen">
	<ui:require module="../m/rethink/js"></ui:require>
</head>
<body>
<input type="hidden" id="planTypeIdHid" value="${model.planType }" />

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

<div class="cw_menu_wrap" >
	<div class="cw_menu_list" >
		<span class="cw_menu_list_top"></span>
		<div id="wrap2" class="cw_menu_list_wrap1" style="height: 6.5rem;"> 
			<div id="scroller">
				<p level="leaf" value="2" >课后反思</p>
				<p level="leaf" value="3" >其他反思</p>
			</div>
		</div>
	</div> 
</div>
<div class="share_upload_wrap">
	<div class="share_upload">
		<div class="share_upload_title">
			<h3>分享反思</h3>
			<span class="close"></span>
		</div>
		<div class="share_upload_content">
			<div class="share_width">
				<q class="dlog_share"></q>
				<span>您确定要分享该反思吗？分享后，您的小伙伴就可以看到喽！</span>
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
			<h3>提交反思</h3>
			<span class="close"></span>
		</div>
		<div class="submit_upload_content">
			<div class="submit_width">
				<q></q>
				<span>您确定要提交该反思吗？提交后，学校管理者将看到这些内容！</span>
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
			<h3>删除反思</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该反思吗？</span>
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
	<div class="add_upload_wrap1"></div>
	<div class="add_upload">
		<div class="add_upload_title">
			<h3>撰写反思</h3>
			<span class="close"></span>
		</div>
		<div class="add_upload_content">
			<form id="fs_form" action="${ctx}jy/rethink/save" method="post">
			<ui:token/>
			<input type="hidden" name="bookId" value="${bookId }">
			<input type="hidden" name="planType" id="planType" value="${planType }">
			<input type="hidden" name="lessonId" id="lessonId" value="">
			<input type="hidden" name="planName" id="planName" value="">
			<input type="hidden" name="planId" id="planId" value="">
			<input type="hidden" name="resId" id="resId" value="">
			<input type="hidden" name="gradeId" value="${model.gradeId}"/> 
			<input type="hidden" name="subjectId" value="${model.subjectId}"/> 
			<input type="hidden" name="phaseId" value="${model.phaseId}"/> 
				<div id="kh_kt" class="form_input">
					<label>课题目录</label>
					<strong class="select" id="uploadLesson">请选择<q></q></strong>
					<div class="menu_list" >
						<span class="menu_list_top"></span>
							<div id="wrap1" class="menu_list_wrap1"> 
								<div id="scroller">
									<div class="p_label" >${fasiciculeName }</div>
									<c:forEach items="${bookChapters }" var="bookChapter">
										<c:if test="${bookChapter.isLeaf}">
											<p level="leaf" value="${bookChapter.lessonId }" >${bookChapter.lessonName } </p>
										</c:if>
										<c:if test="${!bookChapter.isLeaf}">
											<p level="parent" value="${bookChapter.lessonId }">${bookChapter.lessonName } </p>
											<c:forEach items="${bookChapter.bookLessons }" var="bookChapter2">
												<c:if test="${bookChapter2.isLeaf}">
													<p class="menu_2" level="leaf" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
												</c:if>
												<c:if test="${!bookChapter2.isLeaf}">
													<p class="menu_2" level="parent" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
													<c:forEach items="${bookChapter2.bookLessons }" var="bookChapter3">
														<p class="menu_3" level="leaf" value="${bookChapter3.lessonId }">${bookChapter3.lessonName } </p>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
									<c:if test="${not empty fasiciculeName2 }">
										<div class="p_label" >${fasiciculeName2 }</div>
										<c:forEach items="${bookChapters2 }" var="bookChapter">
											<c:if test="${bookChapter.isLeaf}">
												<p level="leaf" value="${bookChapter.lessonId }" >${bookChapter.lessonName } </p>
											</c:if>
											<c:if test="${!bookChapter.isLeaf}">
												<p level="parent" value="${bookChapter.lessonId }">${bookChapter.lessonName } </p>
												<c:forEach items="${bookChapter.bookLessons }" var="bookChapter2">
													<c:if test="${bookChapter2.isLeaf}">
														<p class="menu_2" level="leaf" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
													</c:if>
													<c:if test="${!bookChapter2.isLeaf}">
														<p class="menu_2" level="parent" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
														<c:forEach items="${bookChapter2.bookLessons }" var="bookChapter3">
															<p class="menu_3" level="leaf" value="${bookChapter3.lessonId }">${bookChapter3.lessonName } </p>
														</c:forEach>
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:if>
								</div>
							</div>
					</div> 
				</div>
				<div id="qt_kt" class="form_input" style="display: none;">
					<label>反思标题</label>
					<strong><input type="text" id="qt_planName" name="planName" style="width: 22.5rem;height: 3.5rem;border-radius: 0.5rem;padding-left:0.5rem;font-weight: normal;" maxlength="30"/></strong>
				</div>
				<div id="fileuploadContainer" class="form_input">
					<label style="background-color: #fff; position: absolute;left: -3px;z-index: 1;">上传附件</label>
					<div class="enclosure_name" style="display: none;">
						<q></q>
						<span id="uploadFileName"></span>
						<div class="enclosure_del"></div>
					</div>
					<strong id="uploadId" style="display: none;margin-left:7rem;">
						<ui:upload_m fileType="doc,docx,ppt,pptx,pdf" fileSize="50" startElementId="save" beforeupload="start" callback="afterUpload" relativePath="rethink/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload_m>					
					</strong>
				</div>
				<div>
					<input id="save" type="button" class="btn_edit" value="上传"> 
					<input type="button" class="btn_cencel" value="取消">
				</div>
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
<div class="zx_option_wrap" style="display: none;">
	<div class="zx_option">
		<div class="zx_option_kh"></div>
		<div class="zx_option_qt"></div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_ });"></span>教学反思
		<div class="more" onclick="more()"></div>
	</header>
	<section>
	 <form id="hiddenForm" action="${ctx }jy/rethink/index?_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" method="post">
			<input id="form_planType" type="hidden" name="planType" value="">
		<div class="content">
			<div class="content_top">
				<div class="content_top_left">
					<input type="button" class="btn_submit" value="提交上级">
				</div>
				<div id="rethinktype" class="content_top_right">
					<label>反思类型：</label>
					<span id="currentLesson"></span>
					<strong></strong>
				</div>
				<div class="content_top_right">
				    <label>年级学科:</label>
				    <select id="spacelist" name="spaceId" style="width: 10rem; border: none;  line-height: 3rem;  height: 3rem; font-size: 1.267rem; color: #999; background: #f7f8f9;" >
					<c:forEach items="${sessionScope._USER_SPACE_LIST_}" var="space">
						<c:if test="${not empty space.gradeId && not empty space.subjectId && not empty space.bookId }">
							<option value="${space.id }" ${currentBookId == space.bookId ?'selected':''}><jy:dic key="${space.gradeId}"></jy:dic><jy:dic key="${space.subjectId}"></jy:dic></option>
						</c:if>
					</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</form>
		<div class="content_bottom" id="wrap">
			<div id="scroller">
				<div class="content_bottom_width">
					<div class="add_cour">
						<div class="add_cour_div">
							<div class="add_cour_div_top">
								<div class="add_cour_div_top_img"></div> 
							</div>
							<div class="add_cour_div_bottom">撰写反思</div>
						</div>
					</div>
					<c:forEach var="fansi" items="${rethinkList.datalist }">
						<jy:ds key="${fansi.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<div class="courseware_ppt" planId="${fansi.planId }" planType="${fansi.planType }" lessonId="${fansi.lessonId }" resId="${fansi.resId }" >
							<div class="courseware_img_1">反思</div>
							<h3>${fansi.planName }</h3>
							<p><ui:icon ext="${res.ext }" title="${fansi.planName }"></ui:icon></p>
							<div class="courseware_img_2" title="操作"></div>
							<c:if test="${fansi.isScan}">
								<c:if test="${fansi.planType==2 }">
									<div class="courseware_img_3" title="查阅意见" infoId="${fansi.infoId }" planType="${fansi.planType }" isUpdate="${fansi.scanUp?'1':'0' }" >${fansi.scanUp?'<span></span>':'' }</div>
								</c:if>
								<c:if test="${fansi.planType==3 }">
									<div class="courseware_img_3" title="查阅意见" planId="${fansi.planId }" planType="${fansi.planType }" isUpdate="${fansi.scanUp?'1':'0' }" >${fansi.scanUp?'<span></span>':'' }</div>
								</c:if>
							</c:if>
							<c:if test="${fansi.isComment}">
								<div class="courseware_img_4" title="评论意见" planId="${fansi.planId }" planType="${fansi.planType }" isUpdate="${fansi.commentUp?'1':'0' }" >${fansi.commentUp?'<span></span>':'' }</div>
							</c:if>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<c:choose>
									<c:when test="${fansi.isSubmit || fansi.isShare}">
										<div class="cw_option_jz_edit" title="编辑"></div>
										<div class="cw_option_jz_del" title="删除"></div>
									</c:when>
									<c:otherwise>
										<div class="cw_option_edit" title="编辑"></div>
										<div class="cw_option_del" title="删除"></div>
									</c:otherwise>
								</c:choose>
								<div <c:if test="${!fansi.isSubmit }">class="cw_option_submit" title="提交"</c:if><c:if test="${fansi.isSubmit && !fansi.isScan}">class="cw_option_qx_submit" title="取消提交"</c:if><c:if test="${fansi.isSubmit && fansi.isScan}">class="cw_option_jz_submit" title="已被查阅，禁止取消提交"</c:if> ></div>
								<div  <c:if test="${!fansi.isShare }">class="cw_option_share" title="分享"</c:if><c:if test="${fansi.isShare && !fansi.isComment}">class="cw_option_qx_share" title="取消分享"</c:if><c:if test="${fansi.isShare && fansi.isComment}">class="cw_option_jz_share" title="已有评论，禁止取消分享"</c:if>></div>
								<div class="cw_option_down" title="下载" href="<ui:download resid="${fansi.resId}" filename="${fansi.planName }"></ui:download>"></div>
								<div class="cw_option_close" ></div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
</div>

<!-- 提交上级 -->
<div id="submitDiv" style="display: none;">
	<iframe id="submitIframe" frameborder="0" scrolling="no" width="100%" height="100%" src=""></iframe>
</div>

</body>
<script type="text/javascript">
	require(['zepto','js'],function($){	
	}); 
</script>
</html>