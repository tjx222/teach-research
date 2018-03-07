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
	href="${ctxStatic }/lib/datetime/css/zepto.mdatetimer.css"
	media="screen">
<script type="text/javascript"
	src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
<ui:require module="../m/schoolactivity/js"></ui:require>
</head>
<body>
	<c:forEach items="${stcList }" var="stc">
		<div class="partake_school_wrap1" id="jyqid_${stc.id }">
			<div class="partake_school_wrap">
				<div class="partake_school_title">
					<h3>教研圈名称:${stc.name }</h3>
					<span class="close"></span>
				</div>
				<div class="partake_school_content">
					<div>
						<ul>
							<c:if test="${act.typeId == 1 }">
								<c:forEach items="${stc.stcoList }" var="stco">
									<li>${stco.orgName }<c:if test="${stco.state ==1}">
											<a class="s1">待接受</a>
										</c:if> <c:if test="${stco.state ==2}">
											<span class="s2">已接受</span>
											<label class="jbokorg" style="display: none;"
												data-orgid="${stco.orgId }" data-orgname="${stco.orgName }"></label>
										</c:if> <c:if test="${stco.state ==3}">
											<q class="s3">已拒绝</q>
										</c:if> <c:if test="${stco.state ==4}">
											<q class="s4">已退出</q>
										</c:if> <c:if test="${stco.state ==5}">
											<span class="s2">已恢复</span>
											<label class="jbokorg" style="display: none;"
												data-orgid="${stco.orgId }" data-orgname="${stco.orgName }"></label>
										</c:if>
									</li>
								</c:forEach>
							</c:if>
							<c:if
								test="${act.typeId == 2 || act.typeId == 3 || act.typeId == 4}">
								<c:forEach items="${stc.stcoList }" var="stco">
									<li>${stco.orgName }<c:if test="${stco.state ==1}">
											<a>待接受</a>
										</c:if> <c:if test="${stco.state ==2}">
											<span>已接受</span>
										</c:if> <c:if test="${stco.state ==3}">
											<q>已拒绝</q>
										</c:if> <c:if test="${stco.state ==4}">
											<q>已退出</q>
										</c:if> <c:if test="${stco.state ==5}">
											<span>已恢复</span>
										</c:if>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	<div class="mask"></div>
	<div class="more_wrap_hide" onclick='moreHide()'></div>
	<div id="wrapper">
		<header>
			<span onclick="javascript:window.history.go(-1);"></span>
			<c:if test="${act.typeId == 1}">
			${not empty act.id ? "修改同备教案" : "发起同备教案"}
			</c:if>
			<c:if test="${act.typeId == 2}">
			${not empty act.id ? "修改主题研讨" : "发起主题研讨"}
			</c:if>
			<c:if test="${act.typeId == 3}">
			${not empty act.id ? "修改视频研讨" : "发起视频研讨"}
			</c:if>
			<c:if test="${act.typeId == 4}">
			${not empty act.id ? "修改直播课堂" : "发起直播课堂"}
			</c:if>
			<div class="more" onclick="more()"></div>
		</header>
		<section>
			<div class="content">
				<div class="content_bottom1">
					<div>
						<div class="activity_tbja_wrap">
							<input type="hidden" value="${act.mainUserOrgId }"
								id="mainUserOrgIdhid" />
							<form id="schActivityForm" action="" method="post">
								<ui:token />
								<input value="${act.typeId}" name="typeId" type="hidden"
									id="typeId"> <input
									value="${act.typeId == 1 ?'同备教案':(act.typeId == 2 ? '主题研讨' : (act.typeId == 3 ? '视频研讨' : '直播课堂'))}"
									name="typeName" type="hidden"> <input value="${act.id}"
									id="id" name="id" type="hidden"> <input
									value="${act.status}" id="status" name="status" type="hidden">
								<input value="${isDiscuss}" name="haveDiscuss" type="hidden">
								<input value="${haveTrack}" name="haveTrack" type="hidden">
								<div class="activity_wrap_left"></div>
								<div class="activity_wrap_right">
									<div class="option_jyq_content">
										<div class="option_jyq"></div>
										<h3>
											选择教研圈<span>*</span>
										</h3>
										<div class="option_jyq_class">
											<input value="${act.schoolTeachCircleId}"
												id="schoolTeachCircleId" name="schoolTeachCircleId"
												type="hidden"> <input
												value="${act.schoolTeachCircleName}"
												id="schoolTeachCircleName" name="schoolTeachCircleName"
												type="hidden"> <select id="selectCircleId"
												onchange="selectCircle(${act.typeId == 1 ? 'tbja' : '' })"
												${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}>
												<option value="">请选择</option>
												<c:forEach items="${stcList }" var="stc">
													<option id="circleOpt_${stc.id }" value="${stc.id }"
														${stc.id==act.schoolTeachCircleId ? 'selected="selected"' : '' }>${stc.name }</option>
												</c:forEach>
											</select> <input type="button" class="see"
												style="${empty act.schoolTeachCircleId ? 'display: none;' : ''}"
												value="查看">
										</div>
									</div>
									<c:if test="${act.typeId == 1}">
										<div class="ja_content">
											<div class="ja"></div>
											<h3 style="width: 13rem; float: left; clear: both;">
												确定主备人及教案<span>*</span>
											</h3>
											<div class="ja_content_class"
												style="height: 3.5rem; line-height: 3.5rem;">
												学校： <select style="width: 10rem;" id="mainUserOrgId"
													name="mainUserOrgId" onchange="checkMainUserList();"
													${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}>
													<option value="">请选择学校</option>
												</select>
											</div>
											<div class="clear"></div>
											<h4>
												学科：
												<c:if test="${isDiscuss || haveTrack }">
													<input type="hidden" name="subjectIds"
														value=",${act.mainUserSubjectId }," />
												</c:if>
												<c:if test="${fn:length(subjectList) > 1}">
													<select name="mainUserSubjectId" id="mainUserSubjectId"
														${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
														onchange="checkMainUserList();"
														class="chosen-select-deselect validate[required]"
														data-errormessage-value-missing="请选择">
														<option value="">请选择</option>
														<c:forEach items="${subjectList}" var="sub">
															<option value="${sub.id}"
																${sub.id==act.mainUserSubjectId ? 'selected="selected"' : ''}>${sub.name}</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${fn:length(subjectList) < 2}">
													<c:forEach items="${subjectList}" var="sub">
														<span>${sub.name}</span>
														<input type="hidden" id="mainUserSubjectId"
															name="mainUserSubjectId" value="${sub.id}">
													</c:forEach>
												</c:if>
											</h4>
											<div class="ja_content_class">
												年级：
												<c:if test="${fn:length(gradeList) > 1}">
													<select name="mainUserGradeId" id="mainUserGradeId"
														${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
														onchange="checkMainUserList();"
														class="chosen-select-deselect validate[required]"
														data-errormessage-value-missing="请选择">
														<option value="">请选择</option>
														<c:forEach items="${gradeList}" var="grade">
															<option value="${grade.id}"
																${grade.id==act.mainUserGradeId ? 'selected="selected"' : ''}>${grade.name}</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${fn:length(gradeList) < 2}">
													<c:forEach items="${gradeList}" var="grade">
														<span>${grade.name}</span>
														<input type="hidden" id="mainUserGradeId"
															name="mainUserGradeId" value="${grade.id}">
													</c:forEach>
												</c:if>
											</div>
											<div class="ja_content_zbr">
												主备人： <input type="hidden" id="mainUserName"
													name="mainUserName" value="${act.mainUserName }" /> <select
													name="mainUserId" id="mainUserId"
													onchange="checkChapterList();"
													${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
													class="chosen-select-deselect validate[required]"
													data-errormessage-value-missing="请选择">
													<option value="">请选择</option>
													<c:forEach items="${mainUserList}" var="user">
														<option value="${user.userId}" id="zbr_${user.userId}"
															<c:if test="${user.userId==act.mainUserId}"> selected="selected" </c:if>>${user.username}</option>
													</c:forEach>
												</select>
											</div>
											<div class="ja_content_zbtopic">
												主备课题： <select name="infoId" id="chapterId"
													${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
													class="chosen-select-deselect validate[required]"
													data-errormessage-value-missing="请选择">
													<option value="">请选择</option>
													<c:forEach items="${chapterList}" var="chapter">
														<option value="${chapter.id}"
															<c:if test="${chapter.id==act.infoId}"> selected="selected" </c:if>>${chapter.lessonName}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:if>
									<div class="range1_content">
										<div class="range1"></div>
										<h3>
											确定参与范围<span>*</span>
										</h3>
										<div class="range_class" style="margin-top: 1rem;">
											<c:if test="${act.typeId != 1 }">
												<h4 class="subjecth4">
													<span>学科：</span> <strong> <c:if
															test="${fn:length(subjectList) > 1}">
															<input type="hidden" id="subjectIds" name="subjectIds" />
															<c:forEach items="${subjectList}" var="subject">
																<input type="button" id="p_xk${subject.id}"
																	class="p_option" data-dataid="${subject.id}"
																	value="${subject.name}">
															</c:forEach>
														</c:if> <c:if test="${fn:length(subjectList) < 2}">
															<c:forEach items="${subjectList}" var="subject">
																<input type="hidden" name="subjectIds"
																	value=",${subject.id }," />
																<span data-dataid="${subject.id }">${subject.name}</span>
															</c:forEach>
														</c:if>
													</strong>
												</h4>
											</c:if>
											<input type="hidden" id="gradeIds" name="gradeIds" />
											<h4 class="gradeh4">
												<span class="label_div">年级：</span>
												<c:if test="${fn:length(gradeList) > 2}">
													<c:forEach items="${gradeList}" var="grade">
														<input type="button" class="p_option"
															id="p_nj${grade.id }" data-dataid="${grade.id }"
															value="${grade.name}">
													</c:forEach>
												</c:if>
												<c:if test="${fn:length(gradeList) < 2}">
													<c:forEach items="${gradeList}" var="grade">
														<input type="button" id="p_nj${grade.id }"
															class="p_option p_act" data-dataid="${grade.id }"
															value="${grade.name}">
													</c:forEach>
												</c:if>
											</h4>
										</div>
									</div>
									<div class="activity_duration_content">
										<div class="activity_duration1"></div>
										<h3>
											活动时限<span>（您可以不设置活动结束时限）</span>
										</h3>
										<div class="activity_input">
											时间： <input type="text" id="picktime_start" name="startTime"
												placeholder="开始时间"
												value="<fmt:formatDate value="${act.startTime}" pattern="yyyy-MM-dd HH:mm"/>"
												readonly="readonly" /> &nbsp;一&nbsp; <input type="text"
												id="picktime_end" name="endTime" placeholder="结束时间"
												value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>"
												readonly="readonly" />
										</div>
									</div>
									<div class="activity_theme_content">
										<div class="activity_theme1"></div>
										<h3>
											活动主题<span>*</span>
										</h3>
										<input name="activityName" id="activityName" type="text"
											class="name" maxlength="80" placeholder="输入活动主题（80字内）"
											value="${act.activityName}">
										<c:if test="${act.typeId == 3 }">
											<h3>
												视频地址<span>*</span>
											</h3>
											<input name="videoUrl" id="videoUrl" type="text" class="name"
												maxlength="100" placeholder="输入活动主题（100字内）"
												value="${act.videoUrl}">
										</c:if>
										<h3>活动要求</h3>
										<textarea cols="80" rows="10" name="remark" id="remark"
											class="remark" maxlength="200">${act.remark}</textarea>
									</div>
									<c:if test="${act.typeId != 1 }">
										<div class="study_additional_content">
											<div class="study_additional"></div>
											<h3>
												研讨附件（最多可上传8个）<span></span>
											</h3>
											<input type="hidden" id="ztytRes" name="resIds"
												value="${resIds}">
											<c:if test="${empty act.commentsNum || act.commentsNum<=0 }">
												<div class="clear"></div>
												<div class="study_additional_content_l">
													<div class="add_study">
														<p></p>
														<span>添加附件</span>
													</div>
													<ui:upload_m callback="afterUpload"
														beforeupload="beforeUpload" progressBar="true"
														relativePath="schoolactivity/o_${_CURRENT_USER_.orgId }"></ui:upload_m>
												</div>
											</c:if>
											<div class="study_additional_content_r" id="ztytFJ">
												<c:forEach items="${fjList }" var="fj">
													<div id="${fj.resId }" class="add_study_content">
														<div class="add_study_content_l"></div>
														<div class="add_study_content_c"
															style="padding-top: 0.5rem;">
															<span style="overflow: hidden;">${fj.attachName }.${fj.ext }</span>
														</div>
														<input type="button" class="add_study_content_r"
															data-resId="${fj.resId }" onclick="removeFJ(this)"
															value="删除" />
													</div>
												</c:forEach>
											</div>
										</div>
									</c:if>
								</div>
								<div class="clear"></div>
								<div class="act_tbja_btn">
									<c:if
										test="${act.typeId != 4 || (act.typeId eq 4 && jfn:cfgv('vedio.switch','off') eq 'on') }">
										<c:if test="${act.typeId != 1}">
											<input type="button" class="submit_btn" value="提交中..." />
										</c:if>
										<c:if test="${act.id==null || act.status==0 }">
											<input type="button" class="release"
												onclick="saveSchoolActivity(1)">
											<input type="button" class="deposit_draft"
												onclick="saveSchoolActivity(0)">
										</c:if>
										<c:if test="${act.id!=null && act.status!=0 }">
											<input type="button" class="modify"
												onclick="saveSchoolActivity(1)">
											<!-- 修改 -->
											<input type="button" class="no-modify">
											<!-- 不改了 -->
										</c:if>
									</c:if>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
<script type="text/javascript">
	require([ "zepto", "fq_jb" ], function() {
		$(document).ready(function() {
			if ("${act.id}" != "") {
				var gradeids = "${act.gradeIds}";
				if (gradeids != "") {
					var graArr = gradeids.split(",");
					$.each(graArr, function(index, gar) {
						if (gar != null && gar != "") {
							$("#p_nj" + gar).addClass("p_act");
						}
					});
				}
				if("${act.typeId}" == 1){
					//设置教研圈学校
					selectCircle("tbja");
				}else{
					var subjectids = "${act.subjectIds}";
					if (subjectids != "") {
						var subArr = subjectids.split(",");
						$.each(subArr, function(index, sub) {
							if (sub != null && sub != "") {
								$("#p_xk" + sub).addClass("p_act");
							}
						});
					}
				}
			}
		});
	});
</script>
</html>