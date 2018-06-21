<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="集体备课"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity_cy.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="act_info_wrap">
	<div class="act_info">
		<div class="act_info_title">
		    <q></q>
			<h3>${activity.activityName }</h3>
			<span class="close"></span>
		</div>
		<div class="act_info_content">
			<div>
				<div class="act_info_content_w">
					<div class="act_info_left">
						<h3><span class="fqr"></span><span>发起人：${activity.organizeUserName }</span></h3>
						<h3><span class="zbr"></span><span>主备人：${activity.mainUserName }</span></h3>
						<h3><span class="cyfw"></span><span>参与范围：${activity.subjectName }    ${activity.gradeName}</span></h3>
						<h3><span class="sj"></span><span><fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></span></h3>
						<h3><span class="require"></span>活动要求</h3>
					</div>
					<div class="act_info_right">
						<div class="act_info_right_c">
							
							<p>
								${activity.remark }
							</p>
						</div>
					</div>
					<div class="act_modify_btn">
						<c:choose>
							<c:when test="${activity.isOver }">
								<input type="button" value="已结束" class="input1"/>
							</c:when>
							<c:otherwise>
								<c:if test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id}">
									<input type="button" value="结束活动" class="input" id="overIt" activityId="${activity.id}"/>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="act_modify_btn">
						<c:if test="${canReceive }">
							<input type="button" class="input" activityId="${activity.id}" value="接收教案" id="jieshoujiaoan"/>
						</c:if>
					</div>
				</div>
			</div>  
		</div> 
	</div>
</div>
<div class="act_modify_wrap">
	<div class="act_modify">
		<div class="act_modify_title">
			<q></q>
			<h3>修改后的集备教案</h3>
			<span class="close"></span>
		</div>
		<div class="act_modify_content">
			<div class="act_modify_content1" id="act_modify">
				<div id="scroller">
					<c:forEach var="zhengli" items="${zhengliList }">
						<div class="hour" resId="${zhengli.resId }">
							<div class="hour_title">教案</div>
							<h3>${zhengli.planName }</h3>
							<p><img src="${ctxStatic }/common/icon_m/base/doc.png" /></p>
							<div class="hour_modified">
							</div>
						</div>
					</c:forEach>
				</div> 
			</div>
		</div> 
	</div>
</div>
<div class="act_participants_wrap">
	<div class="act_participants">
		<div class="act_participants_title">
			<q></q>
			<h3>参与人修改留痕</h3>
			<span class="close"></span>
		</div>
		<div class="act_participants_content">
			<ul>
				<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
					<li <c:if test="${status.index==0 }">class="hour_act"</c:if> zhubeiId="${zhubei.id}" activityId="${activity.id }">
					<c:choose>
						<c:when test="${zhubei.hoursId=='-1' }">不分课时</c:when>
						<c:when test="${zhubei.hoursId=='0' }">简案</c:when>
						<c:otherwise>第${zhubei.hoursId}课时</c:otherwise>
					</c:choose>
					</li>
				</c:forEach>
			</ul>
			<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
				<iframe id="iframe2" style="width:33rem;margin:0 auto;display: block;height:100%;" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
			</c:if> 
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
		<div class="partake_discussion_content" >
			<h3>
				<span class="participant"></span>参与人
				<q>(点击头像可阅读他的全部留言)</q>
				<a class="par_head_r">更多</a>
			</h3>
			<div class="par_head">
				<c:forEach items="${usList }" var="user">
				   	<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
					<div class="head_independent" activityId="${activity.id }" userId="${u.id }" canReply="true" userName="${u.name }">
					   	<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
					</div>
			   </c:forEach> 
			</div>
			<div class="par_head_float">
				<div> 
					<c:forEach items="${usList }" var="user">
					   	<jy:di key="${user.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
						<div class="head_independent1" activityId="${activity.id }" userId="${u.id }" canReply="true" userName="${u.name }">
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
				<div class="par_head_r1" activityId="${activity.id }" canReply="true">
					取消
				</div> 
			</div>
			<div class="clear"></div>
			<div id="partake_discussion_content1" class="partake_discussion_content1"> 
				<!-- 加载讨论内容 -->
				<iframe id="iframe_discuss" style="width:100%;height:100%;" frameborder="0" scrolling="no" src=""></iframe>
			</div> 
		</div>
		<div class="my_publish1">
			<div class="my_publish1_left">
				<jy:di key="${_CURRENT_USER_.id }" className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
				<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
			</div>
			<div class="my_publish1_right">
				<input type="text" class="publish1" placeholder="有什么意见赶紧说出来吧！">
				<input type="button" class="publish1_btn" value="发送">
				<div class="left1"></div>
			</div>
		</div>
		<form id="jbdiscussform">
			<input type="hidden" id="activityId" name="activityId" value="${activity.id }">
			<input type="hidden" name="typeId" value="5"> 
			<input type="hidden" id="discussLevel" name="discussLevel" value="1"> 
			<input type="hidden" id="parentId" name="parentId" value="0"> 
			<input type="hidden" name="content" id="content_hidden"/>
		</form> 
	</div>
</div>
<div class="view_comments_wrap">
	<div class="view_comments">
		<div class="view_comments_title">
			<q></q>
			<h3>查看评论</h3>
			<span class="close"></span>
		</div>
		<iframe id="commentBox" style="width:100%;height: 91%;border: none;" ></iframe> 
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
				<div class="content_bottom1_left" id="zhubei_canyu">
					<h3></h3>
					<ul>
						<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
							<li <c:if test="${status.index==0 }"> class="ul_li_act"</c:if> hourId="${zhubei.id}" activityId="${activity.id }">
							<c:choose>
								<c:when test="${zhubei.hoursId=='-1' }">不分课时</c:when>
								<c:when test="${zhubei.hoursId=='0' }">简案</c:when>
								<c:otherwise>第${zhubei.hoursId}课时</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="content_bottom1_center" style=" z-index: 1001;">
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
					<iframe id="iframe1" editType="0" style="width:100%;height:100%;" name="iframe1" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/showLessonPlanTracks?planId=${zhubeiList[0].id }&activityId=${activity.id}&editType=0"></iframe>
					</c:if>
				</div>
				<div class="content_bottom1_right">
					<div class="content_list">
						<figure > 
						  <span class="xx_list"></span>
						  <p>备课信息</p>
						</figure>
						<figure > 
						  <span class="xg_list"></span>
						  <p>修改列表</p>
						</figure>
						<figure>
						  <span class="zl_list"></span>
						  <p>整理列表</p>
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