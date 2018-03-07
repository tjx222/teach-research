<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="校际教研发布"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css"
	media="screen" />
<link rel="stylesheet"
	href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen" />
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css"
	media="screen" />
<style>
.activity_require_right .chosen-container .chosen-drop {
	width: 98%;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="校际教研发布"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<c:if test="${empty act.id }">
					<jy:nav id="xjjy_fq"></jy:nav>
				</c:if>
				<c:if test="${not empty act.id }">
					<jy:nav id="xjjy_xg"></jy:nav>
				</c:if>
			</h3>
		</div>
		<div class='fq_activity_cont'>
			<div class='activity_info_wrap'>
				<div class='activity_type'>
					<div class='activity_type_icon'></div>
					<div class='activity_type_right'>
						<h3 class='activity_type_right_h3'>活动类型：</h3>
						<c:if test="${empty act.id || act.status==0 }">
							<div class='tbja'>
								<input type='radio' id='tbja' name="type" value="1"
									${empty act.typeId || act.typeId==1?'checked="checked"':'' } />
								<label for='tbja'>同备教案</label>
							</div>
							<div class='ztyt'>
								<input type='radio' id='ztyt' name="type" value="2"
									${act.typeId==2?'checked="checked"':'' } /> <label for='ztyt'>主题研讨</label>
							</div>
							<div class='spyt'>
								<input type='radio' id='spyt' name="type" value="3"
									${act.typeId==3?'checked="checked"':'' } /> <label for='spyt'>视频研讨</label>
							</div>
							<c:if test="${jfn:cfgv('vedio.switch','off') eq 'on'}">
								<div class='zbkt'>
									<input type='radio' id='zbkt' name="type" value="4"
										${act.typeId==4?'checked="checked"':'' } /> <label for='zbkt'>直播课堂</label>
								</div>
							</c:if>
						</c:if>
						<c:if test="${not empty act.id && act.status != 0}">
							<div class='tbja'>
								<label>${act.typeId == 1?'同备教案':(act.typeId == 2 ? '主题研讨' : (act.typeId == 3 ? '视频研讨':'直播课堂'))}</label>
							</div>
						</c:if>
					</div>
				</div>
				<form id="schActivityForm" action="" method="post">
					<ui:token />
					<input value="${act.typeId}" name="typeId" id="typeId"
						type="hidden"> <input value="${act.typeName}"
						name="typeName" type="hidden"> <input value="${act.id}"
						id="id" name="id" type="hidden"> <input
						value="${act.status}" id="status" name="status" type="hidden">
					<input value="${isAudit }" id="isAudit" type="hidden" /> <input
						value="${isDiscuss}" name="haveDiscuss" type="hidden"> <input
						value="${haveTrack}" name="haveTrack" type="hidden">
					<div class='activity_theme'>
						<div class='activity_theme_icon'></div>
						<div class='activity_theme_right'>
							<h3 class='activity_theme_right_h3'>选择教研圈：</h3>
							<div class="sel_sch">
								<input value="${act.schoolTeachCircleId}"
									id="schoolTeachCircleId" name="schoolTeachCircleId"
									type="hidden"> <input
									value="${act.schoolTeachCircleName}" id="schoolTeachCircleName"
									name="schoolTeachCircleName" type="hidden"> <select
									id="selectCircleId"
									${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
									style="width: 151px; height: 25px;"
									class="chosen-select-deselect validate[required]"
									data-errormessage-value-missing="请选择教研圈">
									<option value="">请选择</option>
									<c:forEach items="${stcList }" var="stc">
										<option id="circleOpt_${stc.id }" value="${stc.id }"
											<c:if test="${stc.id==act.schoolTeachCircleId}"> selected="selected" </c:if>>${stc.name }</option>
									</c:forEach>
								</select> <b id="circleContent" class="hover_td"
									style="margin-left: 10px; color: #5096ff; padding: 3px 5px; cursor: pointer; display: none;">查
									看 <c:forEach items="${stcList }" var="stc">
										<div class="school1" style="color: #555;"
											id="circleUl_${stc.id}">
											<h5 style="font-weight: bold;">
												教研圈名称：<span id="circleName"></span>
											</h5>
											<ol>
												<c:forEach items="${stc.stcoList }" var="stcorg">
													<li data-id="${stcorg.orgId }"
														data-name="${stcorg.orgName }"
														data-state="${stcorg.state }"><a
														title="${stcorg.orgName }" class="w180">
															${stcorg.orgName }</a> <c:choose>
															<c:when test="${stcorg.state eq 1 }">
																<span class="z_zc">待接受</span>
															</c:when>
															<c:when test="${stcorg.state eq 2 }">
																<span class="z_ty">已接受</span>
															</c:when>
															<c:when test="${stcorg.state eq 3 }">
																<span class="z_jj">已拒绝</span>
															</c:when>
															<c:when test="${stcorg.state eq 4 }">
																<span class="z_tc">已退出</span>
															</c:when>
															<c:otherwise>
																<span class="z_ty">已恢复</span>
															</c:otherwise>
														</c:choose></li>
												</c:forEach>
											</ol>
										</div>
									</c:forEach>
								</b>
							</div>
						</div>
					</div>
					<div class='confirm_main_standby' id='confirm_main_standby'>
						<div class='confirm_main_standby_icon'></div>
						<div class='confirm_main_standby_right'>
							<h3 class='activity_require_right_h3'>确定主备人及教案：</h3>
							<input type="hidden" id="phaseId" value="${userSpace.phaseId }" />
							<div class="sel_sch">
								<div class='school_sel_wrap' data-orgId="${act.mainUserOrgId }">
									<select name="mainUserOrgId" id="mainUserOrgId"
										${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
										style="width: 145px; height: 25px;"
										class="chosen-select-deselect validate[required]"
										data-errormessage-value-missing="请选择学校">
										<option value="">请选择学校</option>
									</select>
								</div>
								<div class='subject_sel_wrap'>
									<select name="mainUserSubjectId" id="mainUserSubjectId"
										${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
										class="chosen-select-deselect validate[required]"
										style="width: 112px; height: 25px;"
										data-errormessage-value-missing="请选择学科">
										<option value="">请选择学科</option>
										<c:forEach items="${subjectList}" var="sub">
											<option value="${sub.id}"
												${sub.id==act.mainUserSubjectId ? 'selected="selected"' : ''}>${sub.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class='grade_sel_wrap'
									data-gradeId="${act.mainUserGradeId}">
									<c:if test="${fn:length(gradeList) > 1}">
										<select name="mainUserGradeId" id="mainUserGradeId"
											${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
											class="chosen-select-deselect validate[required]"
											style="width: 112px; height: 25px;"
											data-errormessage-value-missing="请选择年级">
											<option value="">请选择年级</option>
											<c:forEach items="${gradeList}" var="grade">
												<option value="${grade.id}"
													${grade.id==act.mainUserGradeId ? 'selected="selected"' : ''}>${grade.name}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${fn:length(gradeList) <= 1}">
										<select name="mainUserGradeId" id="mainUserGradeId"
											${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
											class="chosen-select-deselect validate[required]"
											style="width: 112px; height: 25px;"
											data-errormessage-value-missing="请选择年级">
											<option value="">请选择年级</option>
											<c:forEach items="${gradeList}" var="grade">
												<option value="${grade.id}">${grade.name}</option>
											</c:forEach>
										</select>
									</c:if>
								</div>
								<div class='main_standby_sel_wrap'>
									<input type="hidden" id="mainUserName" name="mainUserName"
										value="${act.mainUserName }" /> <select name="mainUserId"
										id="mainUserId"
										${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
										class="chosen-select-deselect validate[required]"
										style="width: 120px; height: 25px;"
										data-errormessage-value-missing="请选择主备人">
										<option value="">请选择主备人</option>
										<c:forEach items="${mainUserList}" var="user">
											<option value="${user.userId}" id="zbr_${user.userId}"
												<c:if test="${user.userId==act.mainUserId}"> selected="selected" </c:if>>${user.username}</option>
										</c:forEach>
									</select>
								</div>
								<div class='main_preparation_project_wrap'>
									<select name="infoId" id="chapterId" style="width: 150px;"
										${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
										class="chosen-select-deselect validate[required]"
										style="width: 172px; height: 25px;"
										data-errormessage-value-missing="请选择课题">
										<option value="">请选择课题</option>
										<c:forEach items="${chapterList}" var="chapter">
											<option value="${chapter.id}"
												<c:if test="${chapter.id==act.infoId}"> selected="selected" </c:if>>${chapter.lessonName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class='participation_scope'>
						<div class='participation_scope_icon'></div>
						<div class='participation_scope_right'>
							<h3 class='participation_scope_right_h3'>确定参与范围：</h3>
							<div class='participation_subject' id='partake_subject'>
								<h3>参与学科：</h3>
								<div class='participation_subject_b'>
									<c:if test="${fn:length(subjectList) > 1}">
										<input type="hidden" id="subjectIds" name="subjectIds"
											value="${act.subjectIds }" />
										<c:forEach items="${subjectList}" var="subject">
											<div class='subject'>
												<input id="subject_${subject.id}"
													<c:if test="${isDiscuss }">disabled="disabled"</c:if>
													type="checkbox" value="${subject.id}" name="subjects"
													class="validate[minCheckbox[1]]">${subject.name}
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(subjectList) <= 1}">
										<c:forEach items="${subjectList}" var="subject">
											<input type="hidden" name="subjectIds"
												value=",${subject.id }," />
											<div class='subject'>${subject.name}</div>
										</c:forEach>
									</c:if>
								</div>
								<div class='clear'></div>
							</div>
							<div class='participation_subject'>
								<h3>参与年级：</h3>
								<div class='participation_subject_b'>
									<c:if test="${fn:length(gradeList) > 1}">
										<input type="hidden" id="gradeIds" name="gradeIds"
											value="${act.gradeIds }" />
										<c:forEach items="${gradeList}" var="grade">
											<div class='subject'>
												<input id="grade_${grade.id}" type="checkbox"
													${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
													value="${grade.id}" name="grades"
													class="validate[minCheckbox[1]]">${grade.name}
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(gradeList) <= 1}">
										<c:forEach items="${gradeList}" var="grade">
											<input type="hidden" name="gradeIds" value=",${grade.id }," />
											<div class='subject'>${grade.name}</div>
										</c:forEach>
									</c:if>
								</div>
								<div class='clear'></div>
							</div>
						</div>
					</div>
					<div class='activity_time'>
						<div class='activity_time_icon'></div>
						<div class='activity_time_right'>
							<h3 class='activity_time_right_h3'>
								活动时限：<span class="sszbtip" style="${empty act.id || act.typeId != 4  ?'display:none':''}">间隔大于15分钟,小于24小时</span>
							</h3>
							<div class='activity_time_wrap'>
								<div class='start_time_wrap'>
									<span>开始时间：</span> <input type="text" style="height: 22px;"
										name="startTime" id="startTime"
										${(not empty isDiscuss && isDiscuss) || (not empty haveTrack && haveTrack) ?'disabled="disabled"':''}
										class="validate[required,custom[dateTimeFormat]]"
										value="<fmt:formatDate value="${act.startTime}" pattern="yyyy-MM-dd HH:mm"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m',maxDate:'#F{$dp.$D(\'endTime\')}'})" />
								</div>
								<div class='end_time_wrap'>
									<span>结束时间：</span> <input type="text" style="height: 22px;"
										name="endTime" id="endTime" class="validate[required,custom[dateTimeFormat]]"
										value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})" />
								</div>
							</div>
						</div>
					</div>
					<div class='activity_theme'>
						<div class='activity_theme_icon'></div>
						<div class='activity_theme_right'>
							<h3 class='activity_theme_right_h3'>活动主题：</h3>
							<input type="text"
								class="validate[required,maxSize[80]] txterea theme_txt"
								name="activityName" id="activityName"
								value="${act.activityName}" />
						</div>
					</div>
					<div class='activity_require' style="height: 220px;">
						<div class='activity_require_icon'></div>
						<div class='activity_require_right'>
							<h3 class='activity_require_right_h3'>
								活动要求： <b
									style="float: right; font-size: 16px; line-height: 50px; color: #9e9e9e; font-weight: normal;">
									<b id="w_count">0</b>/200
								</b>
							</h3>
							<textarea id="editerContent" name="remark">${act.remark}</textarea>
						</div>
					</div>
					<div class='video_address' id='video_address'>
						<div class='video_address_icon'></div>
						<div class='video_address_right'>
							<h3 class='video_address_right_h3'>视频地址：</h3>
							<input type="text"
								class="validate[required,custom[url],maxSize[100]] txterea theme_txt"
								name="videoUrl" id="videoUrl" placeholder="请将视频地址粘贴到这里"
								value="${act.videoUrl}" />
						</div>
					</div>
					<div class='reference_material' id="reference_material">
						<div class='reference_material_icon'></div>
						<div class='reference_material_right'>
							<h3 class='reference_material_right_h3'>参考资料：</h3>
							<div class='upload_files' id="containerUpload">
								<input type="hidden" id="fileRes" name="resIds"
									value="${resIds}"> 
									<a href="javascript:;" class="file">
										<ui:upload containerID="containerUpload"
										fileType="docx,doc,pptx,ppt,flv,swf,txt,pdf,zip,rar"
										fileSize="50" startElementId="uploadFileBtn"
										beforeupload="startUpload" callback="backUpload" name="resId"
										relativePath="schoolactivity/o_${_CURRENT_USER_.orgId}"></ui:upload>
									</a> 
									<input type="button" class='file_btn' id="uploadFileBtn" value='上传附件' />
									<div class="notes">（允许文件类型：docx,doc,pptx,ppt,flv,swf,txt,pdf,zip,rar）</div>
								<div class='files' id="fileFjs">
									<c:forEach items="${fjList }" var="fj">
										<div class='files_wrap_c' id="${fj.resId }"
											data-id="${fj.id }">
											<div class='files_wrap_left_c'></div>
											<div class='files_wrap_center_c'>
												<div class='files_wrap_center_t_c'>
													<span title="${fj.attachName }.${fj.ext }">${fj.attachName }</span>
													<b>.${fj.ext }</b>
												</div>
												<div class='files_wrap_center_b_c'>上传完成</div>
											</div>
											<div class='files_wrap_right_c'>删除</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class='clear'></div>
					</div>
				</form>
			</div>
			<div class='activity_info_border'></div>
			<div class="activity_btn">
				<c:if test="${empty act.id || act.status==0}">
					<input type="button" class='publish' />
					<input type="button" class='save_drafts' />
				</c:if>
				<c:if test="${not empty act.id && act.status!=0}">
					<c:if
						test="${act.id != 4 || (act.id eq 4 && jfn:cfgv('vedio.switch','off') eq 'on' )}">
						<input type="button" class='edit' />
					</c:if>
					<input type="button" class='no_edit'
						onclick="javascript:window.history.go(-1);" />
				</c:if>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/common/js/placeholder.js"></script>
<ui:require module="schoolactivity/js"></ui:require>
<script type="text/javascript">
	require([ 'activity_save' ]);
</script>
</html>