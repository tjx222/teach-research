<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="参与视频研讨"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity_spjy.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="act_info_wrap">
	<div class="act_info_1">
		<div class="act_info_1_title">
			<h3>${activity.activityName }</h3>
			<span class="close"></span>
		</div>
		<div class="act_info_1_content">
			<div class="act_info_1_left">
				<h3><span class="fqr"></span><span>发起人：${activity.organizeUserName }</span></h3>
				<h3><span class="cyfw"></span><span>参与范围：${activity.subjectName }   ${activity.gradeName}</span></h3>
				<h3><span class="sj"></span><span><fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></span></h3>
				<c:if test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id }">
					<input type="button" value="结束活动" class="input" id="overSpjy" activityId="${activity.id }">
				</c:if>
			</div>
			<div class="act_info_1_right">
				<div class="act_info_1_right_c">
					<h3><span class="require"></span>活动要求</h3>
					<p>
						${activity.remark }
					</p>
					<h3><span class="enclosure"></span>活动附件</h3>
					<c:forEach var="attach" items="${attachList }" varStatus="status">
						<div class="enclosure_content" resId="${attach.resId}">
							<span></span>
							<q><ui:sout value="${attach.attachName }.${attach.ext}" length="15" needEllipsis="true"></ui:sout></q>
							<ul>
								<li>查看</li><a href="<ui:download resid="${attach.resId }" filename="${attach.attachName }"></ui:download>"><li>下载</li></a>
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="partake_discussion_vrap">
	<div class="partake_discussion1" >
		<div class="partake_discussion_title">
			<q></q>
			<h3>参与讨论</h3>
			<span class="close"></span>
		</div>
		<div class="partake_discussion_content" id="partake_discussion"> 
			<h3>
				<span class="participant"></span>参与人
				<q>(点击头像可阅读他的全部留言)</q>
				<a class="par_head_r">更多</a>
			</h3>
			<div class="par_head">
				<c:forEach items="${usList }" var="user">
				   	<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
					<div class="head_independent" activityId="${activity.id }" userId="${u.id }" canReply="false" userName="${u.name }">
					   	<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
					</div>
			   </c:forEach> 
			</div>
			<div class="par_head_float">
				<div> 
					<c:forEach items="${usList }" var="user">
					   	<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
						<div class="head_independent1" activityId="${activity.id }" userId="${u.id }" canReply="false" userName="${u.name }">
						   	<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
						</div>
				   </c:forEach> 
				</div>
			</div>
			<div class="clear"></div>
			<div class="par_head_1">
				<div class="par_head_l">
					<div class="head_independent" id="userPhoto">
						<!-- 头像 -->
					</div>
					<div class="head_independent_r">
						<!-- 数据 -->
					</div>
				</div>
				<div class="par_head_r1" activityId="${activity.id }" canReply="false">
					取消
				</div> 
			</div>
			<div class="clear"></div>
			<div id="partake_discussion_content1" class="partake_discussion_content1"> 
				<!-- 加载讨论内容 -->
<!-- 				<iframe id="iframe_discuss" style="width:100%;height:100%;" frameborder="0" scrolling="no" src=""></iframe> -->
			</div>
		</div>
	</div>
</div>
<div class="view_comments_wrap">
	<div class="view_comments">
		<div class="view_comments_title">
			<q></q>
			<h3>查看评论</h3>
			<span class="close"></span>
		</div>
		<iframe id="commentBox" style="width: 100%;height: 91%;border: none;" frameborder="0" scrolling="no" ></iframe>
	</div>
</div>
<div class="mask" style="display:none;"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>集体备课
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1">
				<div class="show">
				</div>
				<div class="content_bottom1_cen" style=" z-index: 1001;">
					
					<iframe id="iframe1" editType="0" style="height:100%;" name="iframe1" frameborder="0" scrolling="no" src="${activity.url }"></iframe>
					
				</div>
				<div class="content_bottom1_right">
					<div class="content_list_sp">
						<figure > 
						  <span class="xx_list"></span>
						  <p>备课信息</p>
						</figure> 
						<figure activityId="${activity.id }" canReply="true">
						  <span class="cy_list"></span>
						  <p>参与讨论</p>
						</figure>
						<figure resId="${activity.id }" isOver="${activity.isOver }" title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>" authorId="${activity.organizeUserId}">
						  <span class="ck_list"></span>
						  <p>查看评论</p>
						</figure>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["join","jbdiscuss_tb"],function(){
	}); 
</script>
</html>