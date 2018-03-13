<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="反思提交"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/rethink/css/rethink.css" media="screen">
	<ui:require module="../m/rethink/js"></ui:require>
</head>
<body>
<input type="hidden" id="planTypeIdHid" value="${planType }" />
<div class="submit_upload_wrap">
	<div class="submit_upload">
		<div class="submit_upload_title">
			<h3>提交反思</h3>
			<span class="close"></span>
		</div>
		<div class="submit_upload_content">
			<div class="submit_width">
				<q></q>
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
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>提交反思
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_top">
				<div class="content_top_left">
					<input type="button" class="btn_submit" value="提交">
					<input type="button" class="btn_cencel_r" value="取消">
					<span act="qx">全选</span><span act="qxqx">全选</span>
				</div>
				<div class="content_top_right">
					<label>反思类型：</label>
					<span id="currentLesson"></span>
					<strong></strong>
				</div>
			</div>
		</div>
		<div class="content_bottom" id="wrap">
			<div id="scroller">
				<div class="content_bottom_width">
					<c:forEach var="fansi" items="${rethinkList.datalist }">
					<jy:ds key="${fansi.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
						<div class="courseware_img">
						<div class="courseware_img_1">反思</div>
						<h3>${fansi.planName }</h3>
						<p><ui:icon ext="${res.ext }" title="${fansi.planName }"></ui:icon></p>
						<div class="courseware_img_2"></div>
						<div class="courseware_img_3"></div>
						<c:if test="${fansi.isSubmit }">
						<div id="${fansi.planId }" class="cw_option_mask_act3"></div>
						</c:if>
						<c:if test="${!fansi.isSubmit }">
						<div id="${fansi.planId }" class="cw_option_mask_act1"></div>
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
require(['zepto','submit'],function($){	
}); 
</script>
</html>
