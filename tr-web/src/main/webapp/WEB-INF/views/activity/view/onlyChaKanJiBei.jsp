<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<head>
<ui:htmlHeader title="查看同备教案"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
<script type="text/javascript" src="${ctxStatic }/modules/activity/js/discuss.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
	var activityId = "${activity.id}";
	$("#discuss_iframe").attr("src","jy/comment/discussIndex?typeId=5&activityId="+activityId+"&canReply=false&"+ Math.random());
	if("${activity.isShare}"=="true"){
		if("${activity.isOver}"=="true"){
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=5&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=false");
		}else if("${activity.isOver}"=="false"){
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=5&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=true&titleShow=true");
		}
	}
});
//显示主备教案的修改教案
function showLessonPlanTrack(obj,planId,activityId){
	 $(obj).find("a").addClass("li_act").siblings().find("a").removeClass("li_act");
	$("#iframe1").attr("src",_WEB_CONTEXT_+"/jy/activity/showLessonPlan?planId="+planId);
}
//显示主备教案的意见教案集合
function showTrackList(obj,planId,activityId){
	$(obj).addClass("ul_li_act").siblings().removeClass("ul_li_act");
	$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/activity/getYijianTrackList?planId="+planId+"&activityId="+activityId);
}
//新选项卡查看修改教案
function scanLessonPlanTrack(resId){
	window.open(_WEB_CONTEXT_+"/jy/activity/scanLessonPlanTrack?resId="+resId,"hidenframe");
}
//接收教案
function receiveLessonPlan(activityId){
	if(window.confirm("该操作会使您相应课题下的所有教案被覆盖，确定要接收吗？")){
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{'activityId':activityId},
	        url:   _WEB_CONTEXT_+"/jy/myplanbook/receiveLessonPlanOfActivity.json",
	        error: function () {
	            alert('操作失败，请稍后重试');  
	        },  
	        success:function(data){
	        	alert(data.info);
	        	if(data.result=="fail3"){
	        		window.location.href = _WEB_CONTEXT_+"/jy/activity/index";
	        	}else{
	        		window.location.reload();
	        	}
	        }  
	    });
	}
}
</script>
</head>
<body>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<div id="wrapper">
<div class='partake_activity_cont'>
<div class='partake_info_cont'>
	<h3 class='partake_info_title'>
		<span>${activity.activityName }</span>
	</h3>
	<ol class="partake_info">
		<li class="partake_info_Standby">
			<span></span><b>主备人：${activity.mainUserName }</b>
		</li>
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
	<div class='zb_lessonplan clearfix'>
		<div class='zb_lessonplan_icon'></div>
		<div class='zb_lessonplan_right'>
			<h3 class='zb_lessonplan_right_h3'>主备教案：</h3>
			<div class='zb_lessonplan_cont'>
				<div class='zb_lessonplan_cont_title'>
					<ul>
					<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
						<li onclick="showLessonPlanTrack(this,'${zhubei.id}','${activity.id }');" ><a <c:if test="${status.index==0 }">class='li_act'</c:if> >
						<c:choose>
							<c:when test="${zhubei.hoursId=='-1' }">不分课时</c:when>
							<c:when test="${zhubei.hoursId=='0' }">简案</c:when>
							<c:otherwise>第${zhubei.hoursId}课时</c:otherwise>
						</c:choose>
						</a></li>
					</c:forEach>
					</ul>
				</div>
				<div class='zb_lessonplan_cont_word'>
				<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
						<iframe width="100%" height="495px" id="iframe1" name="iframe1" style="border:none;border-radius: 6px;" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/showLessonPlan?planId=${zhubeiList[0].id }"></iframe>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class='edit_zb_lessonplan clearfix'>
		<div class='edit_zb_lessonplan_icon'></div>
		<div class='edit_zb_lessonplan_right'>
			<div class='edit_zb_lessonplan_title'>
				<h3 class='edit_zb_lessonplan_right_h3'>修改后的主备教案：</h3>
			</div>
			<div class='edit_zb_lessonplan_cont'>
			<c:if test="${!empty zhengliList }">
				<c:forEach var="zhengli" items="${zhengliList }">
				<dl>
					<dd>
						<img src="${ctxStatic }/common/icon/base/word.png" title="${zhengli.planName }" onclick="scanLessonPlanTrack('${zhengli.resId}');">
					</dd>
					<dt title="${zhengli.planName }" >${zhengli.planName }</dt>
				</dl>
				</c:forEach>
			</c:if>
			<c:if test="${empty zhengliList }">
				<div class="emptyInfo">还没有修改后的主备教案！</div>
			</c:if>
			</div> 
		</div>
	</div>
	<div class='participant_edit clearfix'>
		<div class='participant_edit_icon'></div>
		<div class='participant_edit_right'>
			<h3 class='participant_edit_right_h3'>参与人修改留痕：</h3>
			<ul>
				<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
				<li class='ul_li <c:if test="${status.index==0 }">ul_li_act</c:if>' onclick="showTrackList(this,'${zhubei.id}','${activity.id }');">
					<c:choose>
						<c:when test="${zhubei.hoursId=='-1' }">不分课时</c:when>
						<c:when test="${zhubei.hoursId=='0' }">简案</c:when>
						<c:otherwise>第${zhubei.hoursId}课时</c:otherwise>
					</c:choose>
				</li>
				</c:forEach>
			</ul> 
			<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
				<iframe width="823px" height="124px;" id="iframe2" style="border:none;border-radius: 6px;" src="${pageContext.request.contextPath }/jy/activity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
			</c:if> 
		</div>
	</div>
	<div class='info_border'></div>
	<div class="partake_discuss_Wrap">
		<div class='partake_discuss_title'>
			<h5 class='partake_discuss_title_h5'><span></span>参与人：（点击头像可以阅读他的全部留言）</h5>
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
	<div class='clear'></div>
</div>
<iframe id="discuss_iframe" frameborder=0 scrolling="no" onload="setCwinHeight(this,false,100)" style="width:1200px;"></iframe>
<iframe id="commentBox" frameborder=0 scrolling="no" onload="setCwinHeight(this,false,100)" style="width:1200px;"></iframe>
 </div>
</div>
<iframe id="hiddenIframe" style="display: none;"></iframe>
</body>
</html>
