<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
	<ui:htmlHeader title="${activity.typeId==2?'参与主题研讨':'参与视频研讨' }"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
	
	<script type="text/javascript" src="${ctxStatic }/modules/activity/js/discuss.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		var activityId = "${activity.id}";
		$("#discuss_iframe").attr("src","jy/comment/discussIndex?typeId=5&activityId="+activityId+"&canReply=true&"+ Math.random());
		if("${activity.isShare}"=="true"){
			if("${activity.isOver}"=="true"){
				$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=5&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=false");
			}else if("${activity.isOver}"=="false"){
				$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=5&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=true&titleShow=true");
			}
		}
	});
	
	//结束活动
	function overActivity(activityId){
		if(window.confirm("您确定要结束吗？结束后，所有人将不能参与此集备活动！")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/overActivity.json",
		        error: function () {
		            alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		window.location.href = _WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId;
		        	}else{
		        		alert("系统出错");
		        	}
		        }  
		    });
		}
	}
	</script>
</head>
<body>
<div id="wrapper">
<div class='jyyl_top'>
	<ui:tchTop style="1" modelName="参与"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：<jy:nav id="cyztyt"></jy:nav>
</div>
<div class='partake_activity_cont'>
<div class='partake_info_cont'>
	<h3 class='partake_info_title'>
		<span>${activity.activityName }</span>
		<c:if test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id }">
			<input type="button" class='end_activity' onclick="overActivity('${activity.id}');"/>
		</c:if>
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
	<div class="partake_discuss_Wrap">
		<div class='partake_discuss_l'>
			<div class='partake_discuss_title'>
				<h5 class='partake_discuss_title_h5'><span></span>参与讨论：</h5>
				<h4 class='activity_require_right_h4'><span id="w_count">0</span>/300</h4>
			</div>
			<form id="fabu" method="post">
				<textarea  id='discussion_content'  placeholder='您可以在此处输入讨论意见...' maxlength="300"></textarea>
				<input type="hidden" name="content" id="content_hidden"/>
				<input type="hidden" name="activityId" value="${activity.id }"> 
				<input type="hidden" name="discussLevel" value="1"> 
				<input type="hidden" name="parentId" value="0"> 
				<input type="hidden" name="typeId" value="5"> 
				<div class="clear"></div>
				<input type='button' class='submit' value="说完了" onclick="fabu('${activity.id }')"/>
			</form>
		</div>
		<div class='partake_discuss_r'>
			<div class='partake_discuss_title'>
				<h5 class='partake_discuss_title_h51'><span></span>参与人：（点击头像可以阅读他的全部留言）</h5>
			</div>
			<div class="partake_discuss_r_b">
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
	</div>
	<div class='clear'></div>
</div>
<iframe id="discuss_iframe" frameborder=0 scrolling="no" onload="setCwinHeight(this,false,100)" style="width:1200px;"></iframe>
<c:if test="${activity.isShare}">
	<iframe id="commentBox" frameborder=0 scrolling="no" onload="setCwinHeight(this,false,100)" style="width:1200px;"></iframe>
</c:if>
 </div>
  <ui:htmlFooter style="1"></ui:htmlFooter>
</div>
</body>
</html>
