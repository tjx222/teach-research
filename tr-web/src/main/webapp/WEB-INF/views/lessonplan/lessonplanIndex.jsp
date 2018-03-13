<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="我的教案"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/lessonplan/css/lessonplan.css" media="screen">
	<ui:require module="../m/lessonplan/js"></ui:require>
	<style>
		#progressDiv {
	      width: 45rem;
		  height: 3rem;
		}
		#process {
		  width: 45rem;
		  height: 0.1rem;
		  margin-left: -12rem;
		  margin-top: -14rem;/* 
		  background-color: #fe7e37; */
		  border:0;
		}
		#process span{
		   background-color: #fe7e37; 
		   display: block;
		  height: 0.1rem;
		}
		#processPercent_1{
		display:none;
		}
	</style>
</head>
<body>
<div class="return_1"></div>
<input type="hidden" id="selectedlessonId" value="${lessonPlan.lessonId }" />
<div class="cw_menu_wrap" >
	<div class="cw_menu_list" >
		<span class="cw_menu_list_top"></span>
		<div id="wrap2" class="cw_menu_list_wrap1"> 
			<div id="scroller">
			<p level="leaf" value="" >全部</p>
				<div class="p_label" >${fasiciculeName }</div>
				<c:forEach items="${bookChapters }" var="bookChapter">
					<c:if test="${bookChapter.isLeaf}">
						<p level="leaf" value="${bookChapter.lessonId }" >${bookChapter.lessonName } </p>
					</c:if>
					<c:if test="${!bookChapter.isLeaf}">
						<p level="parent" value="${bookChapter.lessonId }">${bookChapter.lessonName } </p>
						<c:forEach items="${bookChapter.bookLessons }" var="bookChapter2">
							<c:if test="${bookChapter2.isLeaf}">
								<p class="cw_menu_2" level="leaf" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
							</c:if>
							<c:if test="${!bookChapter2.isLeaf}">
								<p class="cw_menu_2" level="parent" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
								<c:forEach items="${bookChapter2.bookLessons }" var="bookChapter3">
									<p class="cw_menu_3" level="leaf" value="${bookChapter3.lessonId }">${bookChapter3.lessonName } </p>
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
									<p class="cw_menu_2" level="leaf" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
								</c:if>
								<c:if test="${!bookChapter2.isLeaf}">
									<p class="cw_menu_2" level="parent" value="${bookChapter2.lessonId }" >${bookChapter2.lessonName } </p>
									<c:forEach items="${bookChapter2.bookLessons }" var="bookChapter3">
										<p class="cw_menu_3" level="leaf" value="${bookChapter3.lessonId }">${bookChapter3.lessonName } </p>
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
<div class="share_upload_wrap">
	<div class="share_upload">
		<div class="share_upload_title">
			<h3>分享教案</h3>
			<span class="close"></span>
		</div>
		<div class="share_upload_content">
			<div class="share_width">
				<q class="dlog_share"></q>
				<span>您确定要分享该教案吗？分享后，您的小伙伴就可以看到喽！</span>
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
			<h3>提交教案</h3>
			<span class="close"></span>
		</div>
		<div class="submit_upload_content">
			<div class="submit_width">
				<q class="dlog_submit"></q>
				<span>您确定要提交该教案吗？提交后，学校管理者将看到这些内容！</span>
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
			<h3>删除教案</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该教案吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>

<div class="mask"></div>  
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_ });"></span>
		我的教案 
		<div class="more" onclick="more()"></div>	
	</header>
	<section>
	 <form id="hiddenForm" action="${ctx }jy/lessonplan/index?_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" method="post">
			<input id="form_lessonId" type="hidden" name="lessonId" value="">
			<input type="hidden" name="site_preference=" value="mobile">
			<input type="hidden" name="pageSize" value="1000">
	  
		<div class="content">
			<div class="content_top">
				<div class="content_top_left">
					<input type="button" class="btn_submit" value="提交上级">
				</div>
				<div id="chapter" class="content_top_right">
					<label>课题目录：</label>
					<span id="currentLesson">全部</span>
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
							  <a href="jy/toWriteLessonPlan" >
								<div class="add_cour_div_top_img"></div> 
								</a>
							</div>
							<div class="add_cour_div_bottom">撰写教案</div>
						</div>
					</div>
					<c:forEach var="kejian" items="${lessonplanList.datalist }">
						<jy:ds key="${kejian.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<div class="lessonplan_ppt" planId="${kejian.planId }" lessonId="${kejian.lessonId }" resId="${kejian.resId }" >
						<div class="lessonplan_img_1">教案</div>
						<h3>${kejian.planName }</h3>
						<p><ui:icon ext="${res.ext }" title="${kejian.planName }"></ui:icon></p>
						<div class="lessonplan_img_2" title="操作"></div>
						<c:if test="${kejian.isScan}">
						<c:if test="${kejian.scanUp}"><div class="lessonplan_img_3" title="查阅意见" infoId="${kejian.infoId }" planType="${kejian.planType }" isUpdate="true"><span></span></div></c:if>
						<c:if test="${!kejian.scanUp}"><div class="lessonplan_img_3" title="查阅意见" infoId="${kejian.infoId }" planType="${kejian.planType }" isUpdate="false"></div></c:if>
						</c:if>
						<c:if test="${kejian.isComment }">
						<c:if test="${kejian.commentUp }"><div class="lessonplan_img_4" title="评论意见" infoId="${kejian.planId }" planType="${kejian.planType }" isUpdate="true"><span></span></div></c:if>
						<c:if test="${!kejian.commentUp }"><div class="lessonplan_img_4" title="评论意见" infoId="${kejian.planId }" planType="${kejian.planType }" isUpdate="false"></div></c:if>
						</c:if>
						<div class="cw_option_mask" style="display:none;"></div>
						<div class="cw_option" style="display:none;">
							<c:choose>
							<c:when test="${kejian.isSubmit || kejian.isShare || kejian.origin == 1}">
								<div class="cw_option_jz_edit" title="编辑"></div>
								<div class="cw_option_jz_del" title="删除"></div>
							</c:when>
							<c:otherwise>
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div>
							</c:otherwise>
							</c:choose>
							<div <c:if test="${!kejian.isSubmit }">class="cw_option_submit" title="提交"</c:if><c:if test="${kejian.isSubmit && !kejian.isScan}">class="cw_option_qx_submit" title="取消提交"</c:if><c:if test="${kejian.isSubmit && kejian.isScan}">class="cw_option_jz_submit" title="已被查阅，禁止取消提交"</c:if> ></div>
							<div  <c:if test="${!kejian.isShare }">class="cw_option_share" title="分享"</c:if><c:if test="${kejian.isShare && !kejian.isComment}">class="cw_option_qx_share" title="取消分享"</c:if><c:if test="${kejian.isShare && kejian.isComment}">class="cw_option_jz_share" title="已有评论，禁止取消分享"</c:if>></div>
							<c:if test="${kejian.origin != 1 }">
								<div class="cw_option_down" title="下载" href="<ui:download resid="${kejian.resId}" filename="${kejian.planName }"></ui:download>"></div>
							</c:if>
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
<div id="submitDiv">
	<iframe name="parentPage" id="submitIframe" frameborder="0" scrolling="no" width="100%" height="100%" src=""></iframe>
</div>
<!-- 查阅意见 -->
<div class="opinions_comment_wrap" id="checkComment" style="display: none;">
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>查阅意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_scan" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
<!-- 评论意见 -->
<div class="opinions_comment_wrap" id="comment_div" style="display: none;" >
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>评论意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_comment" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
</body>
<script type="text/javascript">
	require(['js'],function(){	
	}); 
</script>
</html>