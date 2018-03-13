<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="上传课件"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/courseware/css/courseware.css" media="screen">
	<ui:require module="../m/courseware/js"></ui:require>
</head>
<body>
<div class="return_1"></div>
<input type="hidden" id="selectedlessonId" value="${lessonId }" />
<div class="submit_upload_wrap">
	<div class="submit_upload">
		<div class="submit_upload_title">
			<h3>提交课件</h3>
			<span class="close"></span>
		</div>
		<div class="submit_upload_content">
			<div class="submit_width">
				<q class="dlog_submit"></q>
				<span>您确定要提交给上级吗？提交后，学校管理者将看到这些内容！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="cw_menu_wrap">
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
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>提交课件
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_top">
				<div class="content_top_left">
					<input type="button" class="btn_submit" value="提交">
					<input type="button" class="btn_cencel_r" value="取消">
					<span act="qx">全选</span>
				</div>
				<div class="content_top_right">
					<label>课题目录：</label>
					<span id="currentLesson">全部</span>
					<strong></strong>
				</div>
			</div>
		</div>
		<div class="content_bottom" id="wrap">
			<div id="scroller">
				<div class="content_bottom_width">
					<c:forEach var="kejian" items="${coursewareList.datalist }">
					<jy:ds key="${kejian.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<div class="courseware_img">
						<div class="courseware_img_1">课件</div>
						<h3>${kejian.planName }</h3>
						<p><ui:icon ext="${res.ext }" title="${kejian.planName }"></ui:icon></p>
						<div class="courseware_img_2"></div>
						<div class="courseware_img_3"></div>
						<c:if test="${kejian.isSubmit }">
						<div id="${kejian.planId }" class="cw_option_mask_act3"></div>
						</c:if>
						<c:if test="${!kejian.isSubmit }">
						<div id="${kejian.planId }" class="cw_option_mask_act1"></div>
						</c:if>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript"> 
require(['submit'],function(){	
});

</script>
</html>
