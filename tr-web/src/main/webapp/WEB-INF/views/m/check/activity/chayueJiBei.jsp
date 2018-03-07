<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅集备"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<ui:require module="../m/check/js"></ui:require>
</head>  
<body>
	<div class="figure_wrap"> 
		<div class="act_info_wrap wrap1">
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
								<h3><span class="fqr"></span>发起人：${activity.organizeUserName }</h3>
								<h3><span class="zbr"></span>主备人：${activity.mainUserName }</h3>
								<h3><span class="cyfw"></span>参与范围：${activity.subjectName }    ${activity.gradeName}</h3>
								<h3><span class="sj"></span><fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></h3>
								<h3><span class="require"></span>活动要求</h3>
							</div> 
							<div class="act_info_right">
								<div class="act_info_right_c">
									<p>
										${activity.remark }
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="act_participants_wrap wrap1">
			<div class="act_participants">
				<div class="act_participants_title">
					<q></q>
					<h3>参与人修改留痕</h3>
					<span class="close"></span>
				</div>
				<div class="act_participants_content">
					<ul>
						<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
							<c:if test="${status.index==0 }">
							<li class="hour_act" zhubeiId="${zhubei.id}" activityId="${activity.id }">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
							<c:if test="${status.index!=0 }">
							<li zhubeiId="${zhubei.id}" activityId="${activity.id }">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
						</c:forEach>
					</ul>
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
						<iframe id="iframe2" style="width:35rem;margin:0 auto;display: block;height:100%;" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/getYijianTrackList?site_preference=mobile&planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
					</c:if>
				</div>
			</div>
		</div>
		<div class="act_modify_wrap wrap1">
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
		<div class="partake_discussion_vrap wrap1">
			<div class="partake_discussion1" >
				<div class="partake_discussion_title">
					<q></q>
					<h3>参与讨论</h3>
					<span class="close"></span>
				</div>
				<div class="partake_discussion_content" >
						<div>
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
							<div class="partake_discussion_content1"style="height:32rem;"> 
								<!-- 加载讨论内容 -->
								<iframe id="iframe_discuss" style="width:100%;height:100%;" frameborder="0" scrolling="no" src=""></iframe>
<!-- 								<iframe id="iframe_discussion" style="border:none;overflow:hidden;width:100%;height:32rem;" ></iframe> -->
							</div> 
						</div>
				</div>
			</div>
		</div> 
		<div class="look_opinion_list_wrap wrap1">
			<div class="look_opinion_list">
				<div class="look_opinion_list_title">
				    <q></q>
					<h3 id="lessonName_check">查阅意见列表</h3>
					<span class="close"></span>
				</div>
				<iframe id="iframe_checklist" style="border:none;overflow:hidden;width:100%;height:40rem;" ></iframe>
				<input type="hidden" id="checklistobj" resType="${type}" authorId="${activity.organizeUserId}" resId="${activity.id}" title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>">
			</div>
		</div>
		<!--查阅意见列表内容替换  start-->
		<!--查阅意见列表内容替换  end-->
	</div> 
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">	
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅集体备课(同备教案)     
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1">
				<div class="show">
				</div>
				<div class="content_bottom1_left" id="zhubei_chakan">
					<h3></h3>
					<ul>
						<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
							<c:if test="${status.index==0 }">
								<li class="ul_li_act" hourId="${zhubei.id}" activityId="${activity.id }">
								${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}
								</li>
							</c:if>
							<c:if test="${status.index!=0 }">
								<li hourId="${zhubei.id}" activityId="${activity.id }">
								${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="content_bottom1_center" style=" z-index: 1001;"> 
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
					<iframe id="iframe1" editType="0" style="height:100%;width:100%" name="iframe1" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/jy/activity/showLessonPlan?planId=${zhubeiList[0].id }"></iframe>
					</c:if>
				</div>
				<div class="content_bottom1_right">
					<div class="content_list">
						<figure > 
						  <span class="xx_list"></span>
						  <p>活动信息</p>
						</figure>
						<figure > 
						  <span class="xg_list"></span>
						  <p>修改列表</p>
						</figure>
						<figure>
						  <span class="zl_list"></span>
						  <p>整理列表</p>
						</figure>
					  	<figure activityId="${activity.id }" canReply="false">
						  <span class="cy_list"></span>
						  <p>参与讨论</p>
						</figure>
						<figure >
						  <span class="ck_list"></span>
						  <p>查阅列表</p>
						</figure>
					</div>
					
				</div>
			</div>
		</div>
	</section>
	
</div>
</body>
<script type="text/javascript">
	require(["zepto",'activityjs'],function($){	
	}); 
</script>
</html>