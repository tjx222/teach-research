<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<head>
<ui:htmlHeader title="整理同备教案"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
<script type="text/javascript" src="${ctxStatic }/modules/activity/js/discuss.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
	var activityId = "${activity.id}";
	$("#discuss_iframe").attr("src",_WEB_CONTEXT_+"/jy/comment/discussIndex?typeId=5&activityId="+activityId+"&canReply=true&"+ Math.random());
	if("${activity.isShare}"=="true"){
		if("${activity.isOver}"=="true"){
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=5&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=false");
		}else if("${activity.isOver}"=="false"){
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=5&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=true&titleShow=true");
		}
	}
	var li1 = $(".in_reconsideration_see_title_box .ul_li ");
	var window1 = $(".out_reconsideration_see_title_box ul");
	var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
	var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
	window1.css("width", li1.length*88+"px");  
	if(li1.length >= 9){
		left1.show();
		right1.show();
	}else{
		left1.css({"visibility":"hidden"});
		right1.css({"visibility":"hidden"});
	} 
	var lc1 = 0;
	var rc1 = li1.length-9; 
	left1.click(function(){ 
		if (lc1 < 1) {
			return;
		}
		lc1--;
		rc1++;
		window1.animate({left:'+=88px'}, 500);  
	});

	right1.click(function(){
		if (rc1 < 1){
			return;
		}
		lc1++;
		rc1--;
		window1.animate({left:'-=88px'}, 500); 
	}); 
});
function wantToEdit(obj){
	window.frames["iframe1"].fadeInOrOut($(obj).prop("checked"));
	$("#saveButton").toggle($(obj).prop("checked"));
}
function wantToEdit1(){
	$("#checkbox1").prop("checked",true);
	window.frames["iframe1"].fadeInOrOut(true);
	$("#saveButton").toggle(true);
	$(window).scrollTop(100);
}
//保存修改
function saveEdit(){
	window.frames["iframe1"].saveLessonPlanTracks(1);
	$("#fasong").show();
}
//显示主备教案的修改教案
function showLessonPlanTrack(obj,planId,activityId){
	if(window.frames["iframe1"].wordObj.IsDirty){
		if(window.confirm("您修改的教案还未保存，是否保存当前修改的教案内容？")){
			wantToEdit1();
			saveEdit();
		}
	}
	 $(obj).find("a").addClass("li_act");
	 $(obj).siblings().find("a").removeClass("li_act");
	$("#iframe1").attr("src",_WEB_CONTEXT_+"/jy/activity/showLessonPlanTracks?editType=1&planId="+planId+"&activityId="+activityId);
}
//显示主备教案的意见教案集合
function showTrackList(obj,planId,activityId){
	$(obj).addClass("ul_li_act").siblings().removeClass("ul_li_act");
	$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/activity/getYijianTrackList?planId="+planId+"&activityId="+activityId);
}
//刷新iframe
function frushIframe(){
	$("#iframe3").prop("src",$("#iframe3").prop("src"));
	alert('整理的教案已保存成功，可以到下方的“修改后的集备教案”中查看哦！');
}
//发送给参与人
function sendToJoiners(activityId){
	if(confirm("发送后，所有集备参与人将收到您发送的集备教案，您需要将该课题下的所有教案都整理好后在发送。如果还未全部整理好，请继续整理。您确定发送吗？")){
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{id:activityId},
	        url:   _WEB_CONTEXT_+"/jy/activity/sendToJoiner.json",
	        error: function () {
	            alert('操作失败，请稍后重试');  
	        },  
	        success:function(data){
	        	if(data.result=="success"){
	        		alert("发送成功！");
	        		window.location.href = window.location.href;
	        	}else{
	        		alert("系统出错");
	        	}
	        }  
	    });
	}
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
		<!-- <input type="button" class='end_activity' /> -->
		<c:choose>
			<c:when test="${activity.isOver }">
				<input type="button" class='over_activity' value='已结束' />
			</c:when>
			<c:otherwise>
				<c:if test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id}">
					<input type="button" class='end_activity' onclick="overActivity('${activity.id}',this);"/>
				</c:if>
			</c:otherwise>
		</c:choose>
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
			<c:if test="${!activity.isSend }"><h4 class='zb_lessonplan_right_h4'><input type="checkbox" onclick="wantToEdit(this);" id="checkbox1"/><label htmlFor='edit_lessonplan'>整理教案</label></h4></c:if>
			<h5 class='zb_lessonplan_right_h5' id="saveButton" style="display: none;" onclick="saveEdit();"><span></span>保存修改</h5>
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
						<iframe width="100%" height="495px" id="iframe1" name="iframe1" style="border:none;border-radius: 6px;" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/showLessonPlanTracks?planId=${zhubeiList[0].id }&activityId=${activity.id}&editType=1"></iframe>
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
				<!-- <h5 class='edit_zb_lessonplan_right_h5'><span></span>发送消息</h5> -->
				<c:if test="${activity.mainUserId == _CURRENT_USER_.id && !activity.isSend}">
				<h5 class='edit_zb_lessonplan_right_h5' onclick="sendToJoiners(${activity.id});" id="fasong"><span></span>发给参与人</h5>
				</c:if>
				<c:if test="${activity.mainUserId == _CURRENT_USER_.id && activity.isSend}">
				<h5 class='edit_zb_lessonplan_right_h51'>已发送</h5>
				</c:if>
				<c:if test="${canReceive }">
				<h5 class='edit_zb_lessonplan_right_h52' onclick="receiveLessonPlan('${activity.id}');"><span></span>接收教案</h5>
				</c:if>
			</div>
			 <iframe width="1000px" height="124px;" id="iframe3" style="border:none;border-radius: 6px;" scrolling="no" frameborder="no" src="jy/activity/getZhengliTrackList?activityId=${activity.id}"></iframe>
		</div>
	</div>
	<div class='participant_edit clearfix'>
		<div class='participant_edit_icon'></div>
		<div class='participant_edit_right'>
			<h3 class='participant_edit_right_h3'>参与人修改留痕：</h3>
			<div class="out_reconsideration_see_title_box">
      			<span class="scroll_leftBtn"></span>
				<div class="in_reconsideration_see_title_box">
				<ul class="reconsideration_see_title">
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
				</div>
				<span class="scroll_rightBtn"></span>
			</div> 
			<div class='participant_edit_right_cont clearfix'>
				<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
					<iframe width="1000px" height="124px;" id="iframe2" style="border:none;border-radius: 6px;" src="${pageContext.request.contextPath }/jy/activity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
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
				<textarea id='discussion_content' style="width:450px;height:157px;" placeholder='您可以在此处输入讨论意见...' maxlength="300"></textarea>
				<input type="hidden" name="content" id="content_hidden"/>
				<input type="hidden" name="activityId" value="${activity.id }"> 
				<input type="hidden" name="discussLevel" value="1"> 
				<input type="hidden" name="parentId" value="0"> 
				<input type="hidden" name="typeId" value="5"> 
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
</div>
</body>
</html>
