<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="参与主研"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity_ztyt.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="act_info_wrap">
	<div class="act_info">
		<div class="act_info_title">
			<h3>${activity.activityName }</h3>
			<span class="close act_info_close"></span>
		</div>
		<div class="act_info_content">
			<div class="act_info_left">
				<div class="act_info_left_w">
					<h3><span class="fqr"></span><span>发起人：${activity.organizeUserName }</span></h3>
					<h3><span class="cyfw"></span><span>参与范围：${activity.subjectName }  &nbsp;&nbsp;&nbsp;&nbsp; ${activity.gradeName}</span></h3>
					<h3><span class="sj"></span><span><fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></span></h3>
				</div>
				<input type="button" value="已结束" class="input1">
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
		<span onclick="javascript:window.history.go(-1);"></span>主题研讨
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
						<iframe id="discuss_iframe" style="width: 80%;height: 100%;border: none;"scrolling="no" frameborder="0"  ></iframe>
						<div class="participants" style="float: right;">
							<h3><span class="participant"></span><b>参与人</b></h3>
							<h4>(点击头像可看全部留言)</h4>
							<div class="clear"></div>
							<div class="head_wrap" id="partake_discussion">
								<div id="scroller">
									<div class="">
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
					<iframe id="commentBox" style="width: 100%;height:100%;border: none;" ></iframe> 
				</div>
				<div class="content_bottom1_right">
					<div class="content_list">
						<div class="xx_list1"> 
						  <span class="xx_list"></span>
						  <p>备课信息</p>
						</div>
						<div class="cy_list1 figure_bg">
						  <input type="hidden" value="${activity.id}">
						  <span class="cy_list"></span>
						  <p>参与讨论</p>
						</div>
						<div class="ck_list1" resId="${activity.id}" isOver="${activity.isOver }" title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>" authorId="${activity.organizeUserId}">
						  <span class="ck_list"></span>
						  <p>查看评论</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["join","iscroll"],function(){
		var activityId = "${activity.id }";
		$("#discuss_iframe").attr("src",_WEB_CONTEXT_+"/jy/comment/discussIndex?activityId="+activityId+"&canReply=false&typeId=5&"+ Math.random());
		var partake_discussion = new IScroll('#partake_discussion',{
     		 scrollbars:true,
     		 mouseWheel:true,
     		 fadeScrollbars:true,
     		 click:true
     	});
	}); 
</script>
</html>