 <%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅集备"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity_ztyt.css" media="screen">
<!-- 	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen"> -->
	<ui:require module="../m/check/js"></ui:require>
</head> 
<body>
<div class="look_op_l_wrap">
	<div class="look_opinion_list">
		<div class="look_opinion_list_title">
		    <q></q>
			<h3 id="lessonName_check">查阅意见列表</h3>
			<span class="close act_info_close"></span>
		</div>
		<iframe id="iframe_checklist" style="border:none;overflow:hidden;width:100%;height:40rem;" ></iframe>
		<input type="hidden" id="checklistobj" resType="${type}" authorId="${activity.organizeUserId}" resId="${activity.id}" title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>">
	</div>
</div>
<div class="act_info_wrap">
	<div class="act_info">
		<div class="act_info_title">
			<h3>${activity.activityName }</h3>
			<span class="close act_info_close"></span>
		</div>
		<div class="act_info_content">
			<div class="act_info_left"> 
			<div>
				<div class="act_info_left_w">
					<h3><span class="fqr"></span><span>发起人：${activity.organizeUserName }</span></h3>
					<h3><span class="cyfw"></span><span>参与范围：${activity.subjectName }   ${activity.gradeName}</span></h3>
					<h3><span class="sj"></span><span><fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></span></h3>
	<!-- 				<input type="button" value="已结束" class="input1"> -->
					</div>
				</div>
			</div>
			<div class="act_info_right">
				<div class="act_info_right_c">
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
<div class="mask" style="display:none;"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅集体备课(主题研讨)
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1">
				<div class="content_bottom1_left">
				<!-- 	<div class="content_bottom1_left_1"></div> -->
				</div>
				<div class="content_bottom_center_zt">
					<div class="partake_discussion_content">
						<iframe id="discuss_iframe" style="width: 80%;height:100%;border: none;" ></iframe>
						<div class="participants" style="float: right;">
							<h3><span class="participant"></span><b>参与人</b></h3>
							<h4>(点击头像可看全部留言)</h4>
							<div class="clear"></div>
							<div class="head_wrap" id="zt_head_wrap">
								<div id="scroller">
									<div>
										<c:forEach items="${usList }" var="user">
										   	<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
											<div class="head" activityId="${activity.id }" userId="${u.id }" canReply="false">
											   	<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
											   	<p><ui:sout value="${u.name }" length="10" needEllipsis="true"></ui:sout></p>	
											</div>
									   </c:forEach>
									</div>
								</div>
							</div>
						</div> 
					</div>
				</div>
				<div class="content_bottom_center_ck" style="display:none;">
					<div class="look_opinion_list_wrap">
						<div class="look_opinion_list">
							<div class="look_opinion_list_title">
							    <q></q>
								<h3 id="lessonName_check">查阅意见列表</h3>
								<span class="close act_info_close"></span>
							</div>
							<iframe id="iframe_checklist" style="border:none;overflow:hidden;width:100%;height:35rem;" ></iframe>
							<input type="hidden" id="checklistobj" resType="${type}" authorId="${activity.organizeUserId}" resId="${activity.id}" title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>">
						</div>
					</div>
				</div>
				<div class="content_bottom1_right">
					<div class="content_list">
						<div class="xx_list1"> 
						  <span class="xx_list"></span>
						  <p>备课信息</p>
						</div>
<%-- 						<div class="cy_list1 figure_bg">
						  <span class="cy_list"></span>
						  <p>参与讨论</p>
						</div> --%>
						<div class="ce_list1" resId="${activity.id }" isOver="${activity.isOver }" title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>" authorId="${activity.organizeUserId}">
						  <span class="ce_list"></span>
						  <p>查阅列表</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto","activityjs"],function(){
		var activityId = "${activity.id }";
		$("#discuss_iframe").attr("src",_WEB_CONTEXT_+"/jy/comment/discussIndex?activityId="+activityId+"&canReply=false&typeId=5&"+ Math.random());
	}); 
</script>
</html>