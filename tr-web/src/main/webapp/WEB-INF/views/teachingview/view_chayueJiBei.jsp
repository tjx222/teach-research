<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<head>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="查看同备教案"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
<script type="text/javascript" src="${ctxStatic }/modules/activity/js/activity.js"></script>
<script type="text/javascript" src="${ctxStatic }/modules/activity/js/discuss.js"></script>
<script type="text/javascript">
var sessionId = "${sessionId}";
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
	 $('.word_tab ul li').click(function(){ 
		    $(this).addClass("word_tab_act").siblings().removeClass("word_tab_act");
		    $(".word_tab_big .word_tab_big_tab").hide().eq($('.word_tab ul li').index(this)).show(); 
	 });
	$('.word_tab_1 ul li').click(function(){ 
	    $(this).addClass("word_tab_1_act").siblings().removeClass("word_tab_1_act");
	    $(".word_tab_1_big .word_tab_1_big_tab").hide().eq($('.word_tab_1 ul li').index(this)).show(); 
 	});
	var activityId = "${activity.id}";
	$("#discuss_iframe").attr("src","jy/comment/discussIndex?typeId=${type}&activityId="+activityId+"&canReply=false&"+ Math.random());
	$("#checkedBox").attr("src","jy/teachingView/view/infoIndex?flags=true&resType=${type}&authorId=${activity.organizeUserId}&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>");
	$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/teachingView/view/comment/list?authorId=${activity.organizeUserId}&resType=${type}&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=false");
});
//显示主备教案的修改教案
function scanLessonPlanTrack(resId){
	window.open(_WEB_CONTEXT_+"/jy/activity/scanLessonPlanTrack?resId="+resId,"hidenframe");
}
//显示主备教案的意见教案集合
function showTrackList(obj,planId,activityId){
	$(obj).addClass("ul_li_act").siblings().removeClass("ul_li_act");
	$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/activity/getYijianTrackList?planId="+planId+"&activityId="+activityId);
}
//显示主备教案的修改教案
function showLessonPlanTrack(obj,planId,activityId){
	$(obj).find("a").addClass("li_act");
	$(obj).siblings().find("a").removeClass("li_act");
	$("#iframe1").attr("src",_WEB_CONTEXT_+"/jy/activity/showLessonPlan?planId="+planId);
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
						<li onclick="showLessonPlanTrack(this,'${zhubei.id}','${activity.id }');" ><a <c:if test="${status.index==0 }">class='li_act'</c:if> >${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</a></li>
					</c:forEach>
					</ul>
				</div>
				<div class='zb_lessonplan_cont_word'>
				<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
						<iframe width="100%" height="495px" id="iframe1" name="iframe1" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/showLessonPlan?planId=${zhubeiList[0].id }"></iframe>
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
				<c:if test="${canReceive }">
				<h5 class='edit_zb_lessonplan_right_h52' onclick="receiveLessonPlan('${activity.id}');"><span></span>接收教案</h5>
				</c:if>
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
				<li style="cursor:pointer" class='ul_li <c:if test="${status.index==0 }">ul_li_act</c:if>' onclick="showTrackList(this,'${zhubei.id}','${activity.id }');">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
				</c:forEach>
			</ul> 
			<div class='participant_edit_right_cont clearfix'>
				<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
					<iframe width="823px" height="124px;" id="iframe2" style="border:none;border-radius: 6px;" src="${pageContext.request.contextPath }/jy/activity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
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
			<div class="partake_wrap" onclick="discussUser(this,'${activity.id }','${u.id }',false)">
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
<c:if test="${data.flags=='manager'}">
	<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" scrolling="no" frameborder="no"></iframe>
</c:if>
 </div>
</body>
</html>
