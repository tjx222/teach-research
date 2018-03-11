<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta charset="UTF-8">
<ui:mHtmlHeader title="校际教研"></ui:mHtmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/m/schoolactivity/css/schoolactivity.css"
	media="screen" />
<link rel="stylesheet"
	href="${ctxStatic }/m/schoolactivity/css/schoolact.css" media="screen" />
<ui:require module="../m/schoolactivity/js"></ui:require>
</head>
<body>
	<div id="not_started_dialog" class="dialog">
		<div class="dialog_bg_wrap">
			<div class="dialog_bg_head">
				<span class="dialog_bg_close dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="not_started_info">
					<h3></h3>
					<p>活动时限</p>
					<p></p>
					<p>参与学校:</p>
					<p></p>
					<p>参与范围:</p>
					<p></p>
				</div>
				<div class="prompt_info">活动还没有开始哟！</div>
				<div class="prompt_info_btn" data-id="">
					<input type="button" class="enter"> <input type="button"
						class="not_enter"> <input type="button"
						class="access_view"> <input type="button"
						class="access_class"> <input type="button"
						class="end_class">
				</div>
			</div>
		</div>
	</div>
	<c:set var="jyqids" value="," />
	<div id="xjjyq_detail_div">
		<c:forEach items="${listPage.datalist }" var="activity">
			<c:if test="${!fn:contains(jyqids,activity.schoolTeachCircleId)}">
				<div class="partake_school_wrap1"
					id="jyqids_${activity.schoolTeachCircleId }">
					<div class="partake_school_wrap">
						<div class="partake_school_title">
							<h3>教研圈名称：${activity.schoolTeachCircleName }</h3>
							<span class="close"></span>
						</div>
						<div class="partake_school_content">
							<div>
								<ul>
									<c:forEach items="${activity.stcoList }" var="stco">
										<li><strong>${stco.orgName }</strong> <c:if
												test="${stco.state ==1}">
												<a class="s1">待接受</a>
											</c:if> <c:if test="${stco.state ==2}">
												<span class="s2">已接受</span>
											</c:if> <c:if test="${stco.state ==3}">
												<q class="s3">已拒绝</q>
											</c:if> <c:if test="${stco.state ==4}">
												<q class="s4">已退出</q>
											</c:if> <c:if test="${stco.state ==5}">
												<span class="s2">已恢复</span>
											</c:if></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<c:set var="jyqids"
					value="${jyqids },${activity.schoolTeachCircleId }," />
			</c:if>
		</c:forEach>
	</div>
	<div class="fq_option_wrap">
		<div class="fq_option">
			<div class="fq_option_tb"></div>
			<div class="fq_option_zt"></div>
			<div class="fq_option_sp"></div>
			<c:if test="${jfn:cfgv('vedio.switch','off') eq 'on' }">
				<div class="fq_option_zb"></div>
			</c:if>
		</div>
	</div>
	<div class="act_draft_wrap">
		<div class="act_draft">
			<div class="act_draft_title">
				<h3>草稿箱</h3>
				<span class="close"></span>
			</div>
			<iframe id="activity_iframe" name="activity_content" frameborder="no"
				scrolling="no"
				style="border: none; overflow: hidden; width: 80rem; height: 100%;"></iframe>
		</div>
	</div>
	<div class="mask"></div>
	<div class="more_wrap_hide" onclick='moreHide()'></div>
	<div id="wrapper">
		<header>
			<span onclick="javascript:window.history.go(-1);"></span>校际教研
			<div class="more" onclick="more()"></div>
			<input type="hidden" id="listType" value="${listType}" />
		</header>
		<section>
			<div class="content">
				<div class="content_top">
					<c:if test="${listType == 0 }">
						<div class="draft">
							草稿箱(<span id="drafnum">${activityDraftNum}</span>)
						</div>
						<c:if test="${role != 'jy' }">
							<input type="button" class="btn_jd" id="uploadTeachschedule"
								value="上传教研进度表">
						</c:if>
						<input type="button" class="btn_launch" id="fqSchoolActivity"
							value="发起教研活动">
					</c:if>
					<c:if test="${listType == 1 }">
						<input type="button" class="btn_launch" id="lookTeachCircle"
							value="查看校际教研圈">
						<input type="button" class="btn_jd" id="lookTeachschedule"
							value="查看教研进度表">
					</c:if>
				</div>
				<div class="content_b">
					<div>
						<c:if test="${listType == 0 }">
							<c:if test="${!empty listPage.datalist }">
								<div id="addmoredatas">
									<c:forEach items="${listPage.datalist }" var="activity">
										<div class="activity_tch">
											<c:if test="${activity.typeId==1}">
												<div class="activity_tch_left">
													同<br />备<br />教<br />案
												</div>
											</c:if>
											<c:if test="${activity.typeId==2}">
												<div class="activity_tch_left1">
													主<br />题<br />研<br />讨
												</div>
											</c:if>
											<c:if test="${activity.typeId==3}">
												<div class="activity_tch_left2">
													视<br />频<br />研<br />讨
												</div>
											</c:if>
											<c:if test="${activity.typeId==4}">
												<div class="activity_tch_left3">
													直<br />播<br />课<br />堂
												</div>
											</c:if>
											<div class="activity_gl_right" onclick="zhenglixj(this)"
												data-activityId="${activity.id }"
												data-typeId="${activity.typeId }"
												data-isOver="${activity.isOver }"
												data-startDate="${activity.startTime}"
												data-isTuichu="${activity.isTuiChu }">
												<h3>
													<span class="title">${activity.activityName}</span>
													<c:if test="${activity.isOver}">
														<span class="end"></span>
													</c:if>
												</h3>
												<div class="option">
													<div class="promoter">
														<strong></strong>发起人：<span>${activity.organizeUserName }</span>
													</div>
													<div class="partake_sub">
														<strong></strong>参与学科：<span>${activity.subjectName }</span>
													</div>
													<div class="partake_class">
														<strong></strong>参与年级：<span>${activity.gradeName }</span>
													</div>
												</div>
												<div class="option">
													<div class="time">
														<strong></strong><span> <fmt:formatDate
																value="${activity.startTime}" pattern="MM-dd HH:mm" />至<fmt:formatDate
																value="${activity.endTime}" pattern="MM-dd HH:mm" /></span>
													</div>
													<div class="discussion_number">
														<strong></strong>讨论数：<span>${activity.commentsNum }</span>
													</div>
													<div class="partake_q"
														data-circleid="${activity.schoolTeachCircleId }">
														<strong></strong>教研圈：<span>${activity.schoolTeachCircleName }</span>
													</div>
												</div>
											</div>
											<div class="activity_gl_btn">
												<div class="gl_btn">管理</div>
												<div class="activity_option">
													<c:if test="${activity.isSubmit || activity.isOver}">
														<!-- 禁止修改 -->
														<div class="activity_option_jz_edit"></div>
													</c:if>
													<c:if test="${!activity.isSubmit && !activity.isOver}">
														<!-- 修改 -->
														<div class="activity_option_edit"
															onclick="editActivity('${activity.id}','${activity.typeId}')"></div>
													</c:if>
													<c:if test="${activity.isSubmit || activity.isDiscuss}">
														<!-- 禁止删除 -->
														<div class="activity_option_jz_del"></div>
													</c:if>
													<c:if test="${!activity.isSubmit && !activity.isDiscuss}">
														<!-- 删除 -->
														<div class="activity_option_del"
															onclick="deleteActivity('${activity.id}')"></div>
													</c:if>
													<c:if test="${activity.isShare }">
														<c:if test="${activity.isComment }">
															<!-- 禁止取消分享 -->
															<div class="activity_option_jz_share1"></div>
														</c:if>
														<c:if test="${!activity.isComment }">
															<!-- 取消分享  sharingActivity('${activity.id}',false)-->
															<div class="activity_option_qx_share1"
																data-actid="${activity.id}" data-actstate="false"
																onclick="sharingActivity(this)"></div>
														</c:if>
													</c:if>
													<c:if test="${!activity.isShare }">
														<!-- 分享 -->
														<div class="activity_option_share"
															data-actid="${activity.id}" data-actstate="true"
															onclick="sharingActivity(this)"></div>
													</c:if>
													<div class="activity_option_close"></div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${empty listPage.datalist }">
								<!-- 无文件 -->
								<div class="content_k" style="margin-top: 5rem;">
									<dl>
										<dd></dd>
										<dt>您还没有发起过校际教研活动，赶紧去“发起教研活动”吧！</dt>
									</dl>
								</div>
							</c:if>
						</c:if>
						<c:if test="${listType == 1 }">
							<c:if test="${!empty listPage.datalist }">
								<div id="addmoredatas">
									<c:forEach items="${listPage.datalist }" var="activity">
										<div class="activity_tch">
											<c:if test="${activity.typeId==1}">
												<div class="activity_tch_left">
													同<br />备<br />教<br />案
												</div>
											</c:if>
											<c:if test="${activity.typeId==2}">
												<div class="activity_tch_left1">
													主<br />题<br />研<br />讨
												</div>
											</c:if>
											<c:if test="${activity.typeId==3}">
												<div class="activity_tch_left2">
													视<br />频<br />研<br />讨
												</div>
											</c:if>
											<c:if test="${activity.typeId==4}">
												<div class="activity_tch_left3">
													直<br />播<br />课<br />堂
												</div>
											</c:if>
											<div class="activity_gl_right">
												<h3>
													<span class="title" onclick="canyuxj(this)"
														data-activityId="${activity.id }"
														data-typeId="${activity.typeId }"
														data-isOver="${activity.isOver }"
														data-startDate="${activity.startTime}"
														data-isTuichu="${activity.isTuiChu }">${activity.activityName}</span>
													<c:if test="${activity.isOver}">
														<span class="end"></span>
													</c:if>
												</h3>
												<div class="option">
													<div class="promoter">
														<strong></strong>发起人：<span>${activity.organizeUserName }</span>
													</div>
													<div class="partake_sub">
														<strong></strong>参与学科：<span>${activity.subjectName }</span>
													</div>
													<div class="partake_class">
														<strong></strong>参与年级：<span>${activity.gradeName }</span>
													</div>
												</div>
												<div class="option">
													<div class="time">
														<strong></strong><span> <fmt:formatDate
																value="${activity.startTime}" pattern="MM-dd HH:mm" />至<fmt:formatDate
																value="${activity.endTime}" pattern="MM-dd HH:mm" /></span>
													</div>
													<div class="discussion_number">
														<strong></strong>讨论数：<span>${activity.commentsNum }</span>
													</div>
													<div class="partake_q"
														data-circleid="${activity.schoolTeachCircleId }">
														<strong></strong>教研圈：<span>${activity.schoolTeachCircleName }</span>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${empty listPage.datalist }">
								<!-- 无文件 -->
								<div class="content_k" style="margin-top: 5rem;">
									<dl>
										<dd></dd>
										<dt>别人还没有发起校际教研活动，请稍后再来吧！</dt>
									</dl>
								</div>
							</c:if>
						</c:if>
						<form name="pageForm" method="post">
							<ui:page url="${ctx}jy/schoolactivity/index" data="${listPage}"
								dataType="true" callback="addmoredatas" />
							<input type="hidden" value="${listType }" name="listType">
							<input type="hidden" class="currentPage" name="currentPage">
						</form>
						<div style="height: 2rem; clear: both;"></div>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
<script type="text/javascript">
	require([ "zepto", "js" ], function() {
	});
</script>
</html>