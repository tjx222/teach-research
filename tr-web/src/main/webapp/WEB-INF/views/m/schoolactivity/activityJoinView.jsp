<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta charset="UTF-8">
<ui:mHtmlHeader title="查看参与活动"></ui:mHtmlHeader>
<c:if test="${activity.typeId == 1 }">
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css"
		media="screen">
</c:if>
<c:if test="${activity.typeId == 2 }">
	<link rel="stylesheet"
		href="${ctxStatic }/m/activity/css/activity_ztyt.css" media="screen">
</c:if>
<c:if test="${activity.typeId == 3 || activity.typeId == 4 }">
	<link rel="stylesheet"
		href="${ctxStatic }/m/activity/css/activity_spjy.css" media="screen">
</c:if>
<ui:require module="../m/schoolactivity/js"></ui:require>
</head>
<body>
	<div class="act_info_wrap">
		<c:if test="${activity.typeId == 1 ||activity.typeId == 2 }">
			<div class="act_info">
				<div class="act_info_title">
					<q></q>
					<h3>${activity.activityName }</h3>
					<span class="close"></span>
				</div>
				<div class="act_info_content">
					<c:if test="${activity.typeId == 1 }">
						<div>
							<div class="act_info_1_left_w">
								<div class="act_info_left">
									<h3>
										<span class="fqr"></span><span>发起人：${activity.organizeUserName }</span>
									</h3>
									<h3>
										<span class="zbr"></span><span>主备人：${activity.mainUserName }</span>
									</h3>
									<h3>
										<span class="cyxx"></span><span>参与学校： ${joinOrgLength > 0 ? fn:join(joinOrgNames,'、'): ''}</span>
									</h3>
									<h3>
										<span class="cyfw"></span><span>参与范围：${activity.subjectName }
											${activity.gradeName}</span>
									</h3>
									<h3>
										<span class="sj"></span><span><fmt:formatDate
												value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm" />
											至 <fmt:formatDate value="${activity.endTime }"
												pattern="yyyy-MM-dd HH:mm" /></span>
									</h3>
									<h3>
										<span class="require"></span>活动要求
									</h3>
								</div>
								<div class="act_info_right">
									<div class="act_info_right_c">
										<p>${activity.remark }</p>
									</div>
								</div>
								<c:if test="${empty zhengLiTongBei }">
									<div class="act_modify_btn">
										<c:choose>
											<c:when test="${activity.isOver }">
												<input type="button" value="已结束" class="input1"
													id="yijieshu" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id}">
													<input type="button" value="结束活动" class="input" id="overIt"
														activityId="${activity.id}" />
												</c:if>
											</c:otherwise>
										</c:choose>
									</div>
									<c:if test="${operateType == 1 }">
										<div class="act_modify_btn">
											<c:if test="${canReceive }">
												<input type="button" class="input"
													activityId="${activity.id}" value="接收教案" id="jieshoujiaoan" />
											</c:if>
										</div>
									</c:if>
								</c:if>
							</div>
						</div>
					</c:if>
					<c:if test="${activity.typeId == 2 }">
						<div class="act_info_left">
							<div>
								<div class="act_info_1_left_w">
									<h3>
										<span class="fqr"></span><span>发起人：${activity.organizeUserName }</span>
									</h3>
									<h3>
										<span class="cyxx"></span><span>参与学校：${joinOrgLength > 0 ? fn:join(joinOrgNames,'、'): ''}</span>
									</h3>
									<h3>
										<span class="cyfw"></span><span>参与范围：${activity.subjectName }&nbsp;&nbsp;&nbsp;&nbsp;
											${activity.gradeName}</span>
									</h3>
									<h3>
										<span class="sj"></span><span><fmt:formatDate
												value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm" />
											至 <fmt:formatDate value="${activity.endTime }"
												pattern="yyyy-MM-dd HH:mm" /></span>
									</h3>
								</div>
								<c:if test="${operateType == 1 }">
									<c:if
										test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id }">
										<input type="button" value="结束活动" class="input" id="overZtyt"
											activityId="${activity.id }">
									</c:if>
								</c:if>
							</div>
						</div>
						<div class="act_info_right">
							<div class="act_info_right_c">
								<h3>
									<span class="require"></span>活动要求
								</h3>
								<p>${activity.remark }</p>
								<h3>
									<span class="enclosure"></span>活动附件
								</h3>
								<c:forEach var="attach" items="${attachList }"
									varStatus="status">
									<div class="enclosure_content" resId="${attach.resId}">
										<span></span> <q><ui:sout
												value="${attach.attachName }.${attach.ext}" length="15"
												needEllipsis="true"></ui:sout></q>
										<ul>
											<li>查看</li>
											<a
												href="<ui:download resid="${attach.resId }" filename="${attach.attachName }"></ui:download>"><li>下载</li></a>
										</ul>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</c:if>
		<c:if test="${activity.typeId == 3 || activity.typeId == 4}">
			<div class="act_info_1">
				<div class="act_info_1_title">
					<h3>${activity.activityName }</h3>
					<span class="close"></span>
				</div>
				<div class="act_info_1_content">
					<div class="act_info_1_left">
						<h3>
							<span class="fqr"></span><span>发起人：${activity.organizeUserName }</span>
						</h3>
						<h3>
							<span class="cyfw"></span><span>参与范围：${activity.subjectName }
								${activity.gradeName}</span>
						</h3>
						<h3>
							<span class="sj"></span><span><fmt:formatDate
									value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm" /> 至
								<fmt:formatDate value="${activity.endTime }"
									pattern="yyyy-MM-dd HH:mm" /></span>
						</h3>
						<c:if test="${activity.isOver }">
							<input type="button" value="已结束" class="input1">
						</c:if>
						<c:if test="${!activity.isOver }">
							<c:if
								test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id }">
								<input type="button" value="结束活动" class="input" id="overSpjy"
									activityId="${activity.id }">
							</c:if>
						</c:if>
					</div>
					<div class="act_info_1_right">
						<div class="act_info_1_right_c">
							<h3>
								<span class="require"></span>活动要求
							</h3>
							<p>${activity.remark }</p>
							<h3>
								<span class="enclosure"></span>活动附件
							</h3>
							<c:forEach var="attach" items="${attachList }" varStatus="status">
								<div class="enclosure_content" resId="${attach.resId}">
									<span></span> <q><ui:sout
											value="${attach.attachName }.${attach.ext}" length="15"
											needEllipsis="true"></ui:sout></q>
									<ul>
										<li>查看</li>
										<a
											href="<ui:download resid="${attach.resId }" filename="${attach.attachName }"></ui:download>"><li>下载</li></a>
									</ul>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<c:if test="${activity.typeId == 1 }">
		<div class="act_modify_wrap">
			<div class="act_modify">
				<div class="act_modify_title">
					<q></q>
					<h3>修改后的集备教案</h3>
					<span class="close"></span>
				</div>
				<c:if test="${zhengLiTongBei }">
					<div class="act_modify_content" style="bottom: 4rem;">
						<iframe id="iframe3" frameborder="0" scrolling="no" width=100%
							height=100%
							src="${pageContext.request.contextPath }/jy/schoolactivity/getZhengliTrackList?activityId=${activity.id}"></iframe>
					</div>
					<div class="act_modify_btn_1">
						<c:if
							test="${activity.mainUserId == _CURRENT_USER_.id && !activity.isSend}">
							<input type="button" value="发给参与人" class="input"
								activityId="${activity.id}" id="fasong" />
						</c:if>
						<c:if
							test="${activity.mainUserId == _CURRENT_USER_.id && activity.isSend}">
							<%-- <c:if test="${activity.organizeUserId == _CURRENT_USER_.id && activity.spaceId == _CURRENT_SPACE_.id && activity.isSend}"> --%>
							<input type="button" value="已发送" class="input1" />
						</c:if>
						<c:if test="${canReceive }">
							<input type="button" class="input" activityId="${activity.id}"
								value="接收教案" id="jieshoujiaoan" />
						</c:if>
					</div>
				</c:if>
				<c:if test="${empty zhengLiTongBei }">
					<div class="act_modify_content">
						<div class="act_modify_content1" id="act_modify">
							<div id="scroller">
								<c:forEach var="zhengli" items="${zhengliList }">
									<div class="hour" resId="${zhengli.resId }">
										<div class="hour_title">教案</div>
										<h3>${zhengli.planName }</h3>
										<p>
											<img src="${ctxStatic }/common/icon_m/base/doc.png" />
										</p>
										<div class="hour_modified"></div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<c:if test="${operateType == 0 }">
						<div class="act_modify_btn_1">
							<c:if test="${canReceive }">
								<input type="button" class="input" activityId="${activity.id}"
									value="接收教案" id="jieshoujiaoan" />
							</c:if>
						</div>
					</c:if>
				</c:if>
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
							<c:if test="${status.index==0 }">
								<li class="hour_act" zhubeiId="${zhubei.id}"
									activityId="${activity.id }">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
							<c:if test="${status.index!=0 }">
								<li zhubeiId="${zhubei.id}" activityId="${activity.id }">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
						</c:forEach>
					</ul>
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
						<iframe id="iframe2"
							style="width: 33rem; margin: 0 auto; display: block; height: 100%;"
							frameborder="0" scrolling="no"
							src="${pageContext.request.contextPath }/jy/schoolactivity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
					</c:if>
				</div>
			</div>
		</div>
		<div class="partake_discussion_vrap">
			<div class="partake_discussion1">
				<div class="partake_discussion_title">
					<q></q>
					<h3>参与讨论</h3>
					<span class="close"></span>
				</div>
				<c:if test="${operateType == 0 }">
					<div class="partake_discussion_content" style="bottom: 0;">
						<!-- 加载讨论内容 -->
						<iframe id="iframe_discuss" style="width: 100%; height: 100%;"
							frameborder="0" scrolling="no" src=""></iframe>
					</div>
				</c:if>
				<c:if test="${operateType == 1 || zhengLiTongBei }">
					<div class="partake_discussion_content">
						<div class="partake_discussion_content1" style="height: 35rem;">
							<!-- 加载讨论内容 -->
							<iframe id="iframe_discuss" style="width: 100%; height: 100%;"
								frameborder="0" scrolling="no" src=""></iframe>
						</div>
					</div>
					<c:if test="${!activity.isOver}">
						<div class="my_publish1">
							<div class="my_publish1_left">
								<jy:di key="${_CURRENT_USER_.id }"
									className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
								<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
							</div>
							<div class="my_publish1_right">
								<input type="text" class="publish1" placeholder="有什么意见赶紧说出来吧！">
								<input type="button" class="publish1_btn" value="发送">
								<div class="left1"></div>
							</div>
						</div>
						<form id="jbdiscussform">
							<input type="hidden" id="activityId" name="activityId"
								value="${activity.id }"> <input type="hidden"
								id="discussLevel" name="discussLevel" value="1"> <input
								type="hidden" id="parentId" name="parentId" value="0"> <input
								type="hidden" id="typeId" name="typeId" value="${activityType }">
							<input type="hidden" name="content" id="content_hidden" />
						</form>
					</c:if>
				</c:if>
			</div>
		</div>
	</c:if>
	<c:if test="${activity.typeId == 3 || activity.typeId == 4}">
		<div class="partake_discussion_vrap">
			<div class="partake_discussion1">
				<div class="partake_discussion_title">
					<q></q>
					<h3>参与讨论</h3>
					<span class="close"></span>
				</div>
				<div class="partake_discussion_content" id="partake_discussion">
					<div id="scroller">
						<h3>
							<span class="participant"></span>参与人 <q>(点击头像可阅读他的全部留言)</q> <a
								class="par_head_r">更多</a>
						</h3>
						<div class="par_head">
							<c:forEach items="${usList }" var="user">
								<jy:di key="${user.userId }"
									className="com.tmser.tr.uc.service.UserService" var="u" />
								<div class="head_independent" activityId="${activity.id }"
									userId="${u.id }" canReply="false" userName="${u.name }">
									<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
								</div>
							</c:forEach>
						</div>
						<div class="par_head_float">
							<div>
								<c:forEach items="${usList }" var="user">
									<jy:di key="${user.userId }"
										className="com.tmser.tr.uc.service.UserService" var="u" />
									<div class="head_independent" activityId="${activity.id }"
										userId="${u.id }" canReply="false" userName="${u.name }">
										<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="clear"></div>
						<div class="par_head_1">
							<div class="par_head_l">
								<div class="head_independent" id="userPhoto"></div>
								<div class="head_independent_r"></div>
							</div>
							<div class="par_head_r1" activityId="${activity.id }"
								canReply="${!activity.isOver }">取消</div>
						</div>
						<div class="clear"></div>
						<div id="partake_discussion_content1"
							class="partake_discussion_content1">
							<!-- 加载讨论内容 -->
							<iframe id="iframe_discuss" style="width: 100%; height: 100%;"
								frameborder="0" scrolling="no" src=""></iframe>
						</div>
					</div>
				</div>
				<c:if test="${!activity.isOver}">
					<div class="my_publish1">
						<div class="my_publish1_left">
							<jy:di key="${_CURRENT_USER_.id }"
								className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
							<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
						</div>
						<div class="my_publish1_right">
							<input type="text" class="publish1" placeholder="有什么意见赶紧说出来吧！">
							<input type="button" class="publish1_btn" value="发送">
							<div class="left1"></div>
						</div>
					</div>
					<form id="jbdiscussform">
						<input type="hidden" id="activityId" name="activityId"
							value="${activity.id }"> <input type="hidden"
							id="discussLevel" name="discussLevel" value="1"> <input
							type="hidden" id="parentId" name="parentId" value="0"> <input
							type="hidden" id="typeId" name="typeId" value="${activityType }">
						<input type="hidden" name="content" id="content_hidden" />
					</form>
				</c:if>
			</div>
		</div>
	</c:if>
	<c:if
		test="${activity.typeId == 1 || activity.typeId == 3 || activity.typeId == 4}">
		<div class="view_comments_wrap">
			<div class="view_comments">
				<div class="view_comments_title">
					<q></q>
					<h3>查看评论</h3>
					<span class="close"></span>
				</div>
				<iframe id="commentBox"
					style="width: 100%; height: 91%; border: none;" frameborder="0"
					scrolling="no"></iframe>
			</div>
		</div>
	</c:if>
	<div class="mask" style="display: none;"></div>
	<div class="more_wrap_hide" onclick='moreHide()'></div>
	<div id="wrapper">
		<header>
			<span onclick="javascript:window.history.go(-1);"></span>${activity.typeId == 1 ? '同备教案' : (activity.typeId == 2 ? '主题研讨' : (activity.typeId == 3 ? '视频研讨' : '直播课堂'))}
			<div class="more" onclick="more()"></div>
			<input type="hidden" id="activityType" value="${activityType }" />
		</header>
		<section>
			<div class="content">
				<div class="content_bottom1">
					<c:if test="${activity.typeId == 1}">
						<div class="show"></div>
						<div class="content_bottom1_left"
							id="${zhengLiTongBei ? 'zhubei_zhengli' :(operateType eq 0 ? 'zhubei_chakan':'zhubei_canyu' )}">
							<h3></h3>
							<ul>
								<c:forEach var="zhubei" items="${zhubeiList }"
									varStatus="status">
									<c:if test="${status.index==0 }">
										<li class="ul_li_act" hourId="${zhubei.id}"
											activityId="${activity.id }">
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
						<div class="content_bottom1_center" style="z-index: 1001;">
							<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
								<iframe id="iframe1" editType="0" style="height: 100%;"
									name="iframe1" frameborder="0" scrolling="no"
									src="${pageContext.request.contextPath }/jy/schoolactivity/showLessonPlan?planId=${zhubeiList[0].id }<c:if test="${operateType eq 1}">&editType=${zhengLiTongBei ? 1 : 0}&activityId=${activity.id }</c:if>"></iframe>
							</c:if>
						</div>
						<div class="content_bottom1_right">
							<div class="content_list">
								<figure>
									<span class="xx_list"></span>
									<p>备课信息</p>
								</figure>
								<figure>
									<span class="xg_list"></span>
									<p>修改列表</p>
								</figure>
								<figure>
									<span class="zl_list"></span>
									<p>整理列表</p>
								</figure>
								<figure activityId="${activity.id }"
									canReply="${!activity.isOver}">
									<span class="cy_list"></span>
									<p>参与讨论</p>
								</figure>
								<figure resId="${activity.id }" isOver="${activity.isOver }"
									title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>"
									authorId="${activity.organizeUserId}"
									isShare="${activity.isShare}">
									<span class="ck_list"></span>
									<p>查看评论</p>
								</figure>
							</div>
						</div>
					</c:if>
					<c:if test="${activity.typeId == 2}">
						<div class="content_bottom1_left"></div>
						<div class="content_bottom_center_zt">
							<div class="partake_discussion_content">
								<iframe id="discuss_iframe"
									style="width: 80%; height: 48rem; border: none;" scrolling="no"
									frameborder="0"></iframe>
								<div class="participants" style="float: right;">
									<h3>
										<span class="participant"></span>参与人<br> <q>(点击头像可看全部留言)</q>
									</h3>
									<div class="head_wrap" id="zt_head_wrap" style="top: 5.2rem;">
										<div id="scroller">
											<div>
												<c:forEach items="${usList }" var="user">
													<jy:di key="${user.userId }"
														className="com.tmser.tr.uc.service.UserService" var="u" />
													<div class="head" activityId="${activity.id }"
														userId="${u.id }" canReply="false">
														<ui:photo src="${u.photo }" width="68" height="68"></ui:photo>
														<p>
															<ui:sout value="${u.name }" length="10"
																needEllipsis="true"></ui:sout>
														</p>
													</div>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="content_bottom_center_ck" style="display: none;">
							<iframe id="commentBox"
								style="width: 100%; height: 100%; border: none;" scrolling="no"
								frameborder="0"></iframe>
						</div>
						<div class="content_bottom1_right">
							<div class="content_list">
								<div class="xx_list1">
									<span class="xx_list"></span>
									<p>备课信息</p>
								</div>
								<div class="cy_list1 figure_bg">
									<span class="cy_list"></span>
									<p>参与讨论</p>
								</div>
								<div class="ck_list1" resId="${activity.id }"
									isOver="${activity.isOver }"
									title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>"
									authorId="${activity.organizeUserId}"
									isShare="${activity.isShare}">
									<span class="ck_list"></span>
									<p>查看评论</p>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${activity.typeId == 3 || activity.typeId == 4}">
						<div class="show"></div>
						<div class="content_bottom1_left"></div>
						<div class="content_bottom1_cen" style="z-index: 1001;">
							<iframe id="iframe1" editType="0" style="height: 100%;"
								name="iframe1" frameborder="0" scrolling="no"
								src="${activity.typeId == 3 ? activity.videoUrl:recordUrl }"></iframe>
						</div>
						<div class="content_bottom1_right">
							<div class="content_list_sp">
								<figure>
									<span class="xx_list"></span>
									<p>备课信息</p>
								</figure>
								<figure activityId="${activity.id }"
									canReply="${activity.isOver }">
									<span class="cy_list"></span>
									<p>参与讨论</p>
								</figure>
								<figure resId="${activity.id }" isOver="${activity.isOver }"
									title="<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>"
									authorId="${activity.organizeUserId}"
									isShare="${activity.isShare}">
									<span class="ck_list"></span>
									<p>查看评论</p>
								</figure>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</section>
	</div>
</body>
<script type="text/javascript">
	require([ 'join' ], function() {
		var typeId = "${activity.typeId }";
		if (typeId == 2) {
			var activityId = "${activity.id }";
			var activityType = "${activityType}";
			$("#discuss_iframe").attr("src", _WEB_CONTEXT_ + "/jy/comment/discussIndex?activityId=" + activityId + "&canReply=${!activity.isOver }&typeId=" + activityType + "&" + Math.random());
		}
	});
</script>
</html>