<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="${activity.typeId==2?'查看主题研讨':'查看视频研讨' }"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/modules/activity/js/activity.js"></script>
	<script type="text/javascript" src="${ctxStatic }/modules/activity/js/discuss.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		var activityId = "${activity.id}";
		$("#discuss_iframe").attr("src","jy/comment/discussIndex?typeId=5&activityId="+activityId+"&canReply=false&"+ Math.random());
		$("#checkedBox").attr("src","jy/check/infoIndex?flags=true&resType=${type}&authorId=${activity.organizeUserId}&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>");
		$('.ser_btn').click(function(){
			var search=$('#searchFcx').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
	});
	</script>
</head>
<body>
<div id="wrapper">
<div class='jyyl_top'>
	<ui:tchTop style="1" modelName="查看"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：
		<jy:nav id="ckkt_list">
			<jy:param name="name" value="查阅集体集备"></jy:param>
			<jy:param name="indexHref" value="jy/check/activity/index"></jy:param>
			<jy:param name="listName" value="${activity.typeId==2?'查看主题研讨':'查看视频研讨' }"></jy:param>
		</jy:nav>
</div>
<div class='partake_activity_cont'>
<div class='partake_info_cont'>
	<h3 class='partake_info_title'>
		<span>${activity.activityName }</span>
	</h3>
	<ol class="partake_info">
		<li class="partake_info_author">
			<span></span><b>发起人：${activity.organizeUserName }</b>
		</li>
	</ol>
	<div class='activity_info'>
		<div class='activitytime'>活动时间：<fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></div>
		<div class='partake_range'>
			<b>参与范围：</b>
			<span class='a_info'>${activity.subjectName }</span>
			<span class='a_info'>${activity.gradeName}</span>
		</div>
	</div>
	<div class='info_border'></div>
	<div class='activity_demands'>
		<div class='activity_demands_icon'></div>
		<div class='activity_demands_right'>
			<h3 class='activity_demands_right_h3'>活动要求：</h3>
			<div class='demands_info'>
				${activity.remark }
			</div>
		</div>
	</div>
	<c:if test="${activity.typeId==3 }">
	<div class='view_class_video clearfix'>
		<div class='view_class_video_icon'></div>
		<div class='view_class_video_right'>
			<div class='view_class_video_title'>
				<h3 class='view_class_video_right_h3'>研讨视频：</h3> 
			</div>
			<div class='view_class_video_video clearfix'>
				<iframe src="${activity.url }"  width="100%" height="600px"frameborder="no"></iframe>
			</div> 
		</div>
	</div>
	</c:if>
	<div class='references clearfix'>
		<div class='references_icon'></div>
		<div class='references_right'>
			<div class='references_title'>
				<h3 class='references_right_h3'>活动附件：</h3> 
			</div>
			<div class='references_cont clearfix'>
				<c:if test="${!empty attachList }">
				<c:forEach var="attach" items="${attachList }" varStatus="status">
				<dl>
					<dd onclick="scanResFile('${attach.resId}');">
						<ui:icon ext="${attach.ext}"></ui:icon>
					</dd>
					<dt title="${attach.attachName }">
						<span>${attach.attachName }</span>
						<a href="<ui:download resid="${attach.resId }" filename="${attach.attachName }"></ui:download>"><b class='download_icon' title='下载'></b></a>
						<ui:isView ext="${attach.ext }">
						<b class='see_icon' onclick="scanResFile('${attach.resId}');" title='查看'></b>
						</ui:isView>
					</dt>
				</dl>
				</c:forEach>
				</c:if>
				<c:if test="${empty attachList }">
					<div class="emptyInfo">无活动附件！</div>
				</c:if>
			</div> 
		</div>
	</div>
	<div class='info_border'></div>
	<div class="partake_discuss_Wrap" style="width:1120px;margin: 10px auto;">
		<div class='partake_discuss_title'>
			<h5 class='partake_discuss_title_h51'><span></span>参与人：（点击头像可以阅读他的全部留言）</h5>
		</div>
		<div class="partake_discuss_r_b" style="width:1120px;">
			<c:forEach items="${usList }" var="user">
			<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
			<div class="partake_wrap" onclick="discussUser(this,'${activity.id }','${u.id }',true)">
				<div class="partake_head">
					<div class="partake_head_bg"></div> 
					<ui:photo src="${u.photo }" width="42" height="42"></ui:photo>
				</div>
				<div class="partake_name" title="${u.name }">${u.name }</div>
			</div>
			</c:forEach>
		</div>
	</div>
	<div class='clear'></div>
</div>
<iframe id="discuss_iframe" frameborder=0 scrolling="no" onload="setCwinHeight(this,false,100)" style="width:1200px;"></iframe>
	<div class="check_teacher_wrap2"> 
		<iframe id="checkedBox"  onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
	</div>
 </div>
  <ui:htmlFooter style="1"></ui:htmlFooter>
</div>
</body>
</html>
