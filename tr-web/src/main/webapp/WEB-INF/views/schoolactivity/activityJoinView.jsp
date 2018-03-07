<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<head>
<ui:htmlHeader title="校际教研参与和查看"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" />
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css" />
	<style>
	.xj_sc{
	position: absolute;
    left: 0;
    width: 104px;
    height: 28px;
    filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);
    opacity: 0;
}
	</style>
<script type="text/javascript"
	src="${ctxStatic }/common/js/placeholder.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
<ui:require module="schoolactivity/js"></ui:require>
<script type="text/javascript">
var sessionId = "${sessionId}";
require(['jquery','editor', 'activity_joinView'],function(){});
</script>
</head>
<body>
	<div class="wrapper">
		<div class='partake_activity_cont'>
			<div class='partake_info_cont'>
				<h3 class='partake_info_title'>
					<input type="hidden" id="typeId" value="${activity.typeId}" /> <input
						type="hidden" id="isShare" value="${activity.isShare}" /> <input
						type="hidden" id="isOver" value="${activity.isOver}" /> <input
						type="hidden" id="organizeUserId"
						value="${activity.organizeUserId }" /> <input type="hidden"
						id="activityName" value="${ activity.activityName}" /> <span
						title="${activity.activityName }"> <ui:sout
							value="${activity.activityName}" length="72" needEllipsis="true"></ui:sout>
					</span>
					<c:choose>
						<c:when test="${activity.isOver}">
							<input type="button" class='over_activity' value='已结束' />
						</c:when>
						<c:otherwise>
							<c:if test="${_CURRENT_USER_.id eq activity.organizeUserId}">
								<input type="button" class='end_activity'
									data-id="${activity.id }" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</h3>
				<ol class="partake_info">
					<c:if test="${activity.typeId eq 1 }">
						<li class="partake_info_Standby"><span></span><b>主备人：${activity.mainUserName }</b>
							<div class='partake_Standby_info'>
								<ul>
									<li>${activity.mainUserName }</li>
									<c:if
										test="${!empty lessonInfo.subjectId && lessonInfo.subjectId!=0 }">
										<li>学科：<jy:dic key="${lessonInfo.subjectId }" />
										</li>
									</c:if>
									<c:if test="${!empty lessonInfo.bookId }">
										<li>教材：<jy:ds key="${lessonInfo.bookId }"
												className="com.tmser.tr.manage.meta.service.BookService"
												var="book" /> ${book.formatName }
										</li>
									</c:if>
									<c:if
										test="${!empty lessonInfo.gradeId && lessonInfo.gradeId!=0 }">
										<li>年级：<jy:dic key="${lessonInfo.gradeId }" />
										</li>
									</c:if>
									<li>职务：教师</li>
									<c:if test="${!empty user1.profession }">
										<li>职称：${user1.profession }</li>
									</c:if>
								</ul>
							</div></li>
					</c:if>
					<li class="partake_info_author"><span></span> <b>发起人：${activity.organizeUserName }</b>
						<div class='partake_Standby_info'>
							<ul>
								<li>${activity.organizeUserName }</li>
								<c:if test="${!empty us.subjectId && us.subjectId!=0 }">
									<li>学科：<jy:dic key="${us.subjectId }" />
									</li>
								</c:if>
								<c:if test="${!empty us.bookId }">
									<li>教材： <jy:ds key="${us.bookId }"
											className="com.tmser.tr.manage.meta.service.BookService"
											var="book" /> ${book.formatName }
									</li>
								</c:if>
								<c:if test="${!empty us.gradeId && us.gradeId!=0 }">
									<li>年级： <jy:dic key="${us.gradeId }" />
									</li>
								</c:if>
								<li>职务： <jy:di key="${us.roleId }"
										className="com.tmser.tr.uc.service.RoleService" var="role" />
									${role.roleName }
								</li>
								<c:if test="${!empty user2.profession }">
									<li>职称： ${user2.profession }</li>
								</c:if>
							</ul>
						</div></li>
				</ol>
				<div class='activity_info'>
					<div class='activitytime'>
						活动时间：
						<fmt:formatDate value="${activity.startTime }"
							pattern="yyyy-MM-dd HH:mm" />
						至
						<fmt:formatDate value="${activity.endTime }"
							pattern="yyyy-MM-dd HH:mm" />
					</div>
					<div class='partake_range'>
						<b>参与范围：</b>
						<c:if test="${not empty activity.subjectName}">
							<span class='a_info'>${activity.subjectName }</span>
						</c:if>
						<c:if test="${not empty activity.gradeName}">
							<span class='a_info'>${activity.gradeName}</span>
						</c:if>
						<span class='partake_school'> 学校(${joinOrgLength}) <c:if
								test="${ not empty joinOrgNames && joinOrgLength>0 }">
								<div class='school_info'>
									<ul>
										<c:forEach items="${joinOrgNames}" var="schoolName">
											<li title='${schoolName }'>${schoolName }</li>
										</c:forEach>
									</ul>
								</div>
							</c:if>
						</span>
					</div>
				</div>
				<div class='info_border'></div>
				<div class='activity_demands'>
					<div class='activity_demands_icon'></div>
					<div class='activity_demands_right'>
						<h3 class='activity_demands_right_h3'>活动要求：</h3>
						<div class='demands_info'>${activity.remark }</div>
					</div>
				</div>
				<c:if test="${activity.typeId eq 1 }">
					<div class='zb_lessonplan clearfix'>
						<div class='zb_lessonplan_icon'></div>
						<div class='zb_lessonplan_right'>
							<h3 class='zb_lessonplan_right_h3'>主备教案：</h3>
							<c:if test="${operateType eq 1 }">
								<h4 class='zb_lessonplan_right_h4'>
									<input type="checkbox" id='edit_lessonplan' /><label
										for='edit_lessonplan' class="lable1">修改教案</label>
								</h4>
								<h5 class='zb_lessonplan_right_h5' id="saveEdit" style="display: none;">
									<span></span>保存修改
								</h5>
							</c:if>
							<div class='zb_lessonplan_cont'>
								<div class='zb_lessonplan_cont_title'>
									<h3>${lessonInfo.lessonName}</h3>
									<ul>
										<c:forEach var="zhubei" items="${zhubeiList }"
											varStatus="status">
											<li>
												<a href="javascript:void(0);"	${status.index==0 ? 'class="li_act"':'' } data-planId="${zhubei.id}" data-activityId="${activity.id }">
													<c:choose>
														<c:when test="${zhubei.hoursId=='-1' }">不分课时</c:when>
														<c:when test="${zhubei.hoursId=='0' }">简案</c:when>
														<c:otherwise>第${zhubei.hoursId}课时</c:otherwise>
													</c:choose>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
								<div class='zb_lessonplan_cont_word'>
									<div style="width: 0px; height: 0px; display: none;"
										id="kongdiv"></div>
									<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
										<iframe width="100%" height="496px" id="iframe1"
											name="iframe1" style="border: none;" frameborder="0"
											scrolling="no"
											src="${pageContext.request.contextPath }/jy/schoolactivity/showLessonPlanTracks?planId=${zhubeiList[0].id }&activityId=${activity.id}&editType=0"></iframe>
									</c:if>
								</div>
								<div class="zb_lessonplan_cont_fj clearfix">
									<div class="zb_lessonplan_cont_fj_z">
										附：
									</div>
									<div class="zb_lessonplan_cont_fj_c">
										<c:if test="${not empty lplist }">
											<c:set value="1" var="kjcount"/>
											<c:set value="1" var="fscount"/>
											<c:forEach items="${lplist }" var="lp">
												<div class="fj_div">
													<jy:ds key="${lp.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
													<c:if test="${lp.planType==1 }">
														<a onclick="scanResFile('${lp.resId }');" href="javascript:;">
															<ui:icon ext="${res.ext }"></ui:icon>
														</a>
														<div class="fj_div_name" title="课件${kjcount }">
															课件${kjcount }
														</div>
														<c:set value="${kjcount+1 }" var="kjcount"/>
													</c:if>
													<c:if test="${lp.planType==2 }">
														<a onclick="scanResFile('${lp.resId }');" href="javascript:;">
															<ui:icon ext="${res.ext }"></ui:icon>
														</a>
														<div class="fj_div_name" title="反思${fscount }">
															反思${fscount }
														</div>
														<c:set value="${fscount+1 }" var="fscount"/>
													</c:if>
												</div>
											</c:forEach>
										</c:if>
									</div>
								</div>
								
							</div>
						</div>
					</div>
					<input type="hidden" id="isTongbei" value="${zhengLiTongBei }" /> 
						<div class='edit_zb_lessonplan clearfix'>
							<div class='edit_zb_lessonplan_icon'></div>
							<div class='edit_zb_lessonplan_right'>
								<div class='edit_zb_lessonplan_title'>
									<h3 class='edit_zb_lessonplan_right_h3'>修改留痕：</h3>
									<c:if test="${activity.mainUserId == _CURRENT_USER_.id && !activity.isSend}">
										<h5 class='edit_zb_lessonplan_right_h5' style="width:110px;"
											data-id='${activity.id}' id="sendToJoiners"
											data-type='${listType}'>
											<span></span>发给参与人
										</h5>
									</c:if>
									<c:if test="${activity.mainUserId == _CURRENT_USER_.id && activity.isSend}">
										<h5 class='edit_zb_lessonplan_right_h51'>已发送</h5>
									</c:if>
									
									<c:if test="${!activity.isOver}">
										<form id="schKjSaveForm" action="" method="post">
											<input type="hidden" name="activityId" value="${activity.id }">
											<input type="hidden" id="planName" name="planName" value="">
											<c:choose>
												<c:when test="${activity.mainUserId == _CURRENT_USER_.id}">
													<input type="hidden" id="editType" name="editType" value="1">
												</c:when>
												<c:otherwise>
													<input type="hidden" id="editType" name="editType" value="0">
												</c:otherwise>
											</c:choose>
											<h5 id="fileuploadContainer"  class='edit_zb_lessonplan_right_h53'>
												上传修改课件
												<ui:upload containerID="fileuploadContainer" fileType="ppt,pptx,zip,rar" fileSize="50" isSingle="false" callback="schKjSave" name="resId" relativePath="schoolActivityTracks/${_CURRENT_SPACE_.schoolYear}/${activity.id}"></ui:upload>
											</h5>
										</form>
									</c:if>
									
									<c:if test="${canReceive }">
										<h5 class='edit_zb_lessonplan_right_h5'
											data-id='${activity.id}' id="receiveLessonPlan">
											<span></span>接收教案
										</h5>
									</c:if>
									
									<div class="clear"></div>
								</div>
								<div class="out_reconsideration_see_title_box">
		        					<span class="scroll_leftBtn"></span>
									<div class="in_reconsideration_see_title_box">
										<ul class="reconsideration_see_title">
											<c:forEach var="zhubei" items="${zhubeiList }" 
												varStatus="status">
												<li class='ul_li ${status.index==0 ? "ul_li_act":"" }' data-planId="${zhubei.id}" data-activityId="${activity.id }" >
													<a href="javascript:void(0);" title="第${zhubei.hoursId}课时">
														<c:choose>
															<c:when test="${zhubei.hoursId=='-1' }">不分课时</c:when>
															<c:when test="${zhubei.hoursId=='0' }">简案</c:when>
															<c:otherwise>第${zhubei.hoursId}课时</c:otherwise>
														</c:choose>
													</a>
												</li>
											</c:forEach>
										</ul>
									</div>
									<span class="scroll_rightBtn"></span>
								</div>
								
								<div class='edit_zb_lessonplan_cont'>
									<div class="edit_zb_lessonplan_cont_zbr">
										<span>主备人</span>
									</div>
									<iframe width="990px" height="124px;" id="iframe3" style="border: none;"
										src="${pageContext.request.contextPath }/jy/schoolactivity/getZhengliTrackList?activityId=${activity.id}"></iframe>
								</div>
								<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
									<iframe width="1000px" height="170px;" id="iframe2"
										style="border: none;"
										src="${pageContext.request.contextPath }/jy/schoolactivity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
								</c:if>
							</div>
						</div>
				</c:if>
				<c:if test="${activity.typeId eq 3 }">
					<div class='view_class_video clearfix'>
						<div class='view_class_video_icon'></div>
						<div class='view_class_video_right'>
							<div class='view_class_video_title'>
								<h3 class='view_class_video_right_h3'>研讨视频：</h3>
							</div>
							<div class='view_class_video_video clearfix'>
								<iframe src="${activity.videoUrl }" width="100%" height="600px"
									frameborder="no"></iframe>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${activity.typeId eq 4 }">   
					<div class='view_class_video clearfix' style="border-left: none;">
						<div class='view_class_video_icon'></div>
						<div class='view_class_video_right'>
							<div class='view_class_video_title' style="border-bottom: 0;">
								<h3 class='view_class_video_right_h3'>
									直播课堂：<span>（点击可查看直播课堂视频）</span>
								</h3>
							</div>
							<div class="view_class_video_wrap" style="padding-bottom: 0px;">
					
							<c:if test="${empty recordUrl||recordUrl==''}">
								<div class="empty_wrap" style="margin:20px auto;">
									<div class="empty_img"></div>
									<div class="empty_info">无直播课堂视频！</div>
								</div>
							</c:if>
							<c:if test="${!empty recordUrl&&recordUrl!=''}">
									<div class='view_class_video_video clearfix'>
										<iframe src="${recordUrl }" width="100%" height="600px"
											frameborder="no"></iframe>
									</div>
							</c:if>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${activity.typeId != 1 }">
					<div class='references clearfix'>
						<div class='references_icon'></div>
						<div class='references_right'>
							<div class='references_title'>
								<h3 class='references_right_h3'>参考资料：</h3>
							</div>
							<div class='references_cont clearfix'>
								<c:forEach var="attach" items="${attachList }"
									varStatus="status">
									<dl>
										<dd style="cursor: pointer;"
											onclick="scanResFile('${attach.resId}');">
											<ui:icon ext="${attach.ext}"></ui:icon>
										</dd>
										<dt>
											<span title="${attach.attachName }"><ui:sout
													value="${attach.attachName }" length="28"
													needEllipsis="true"></ui:sout></span> <a
												href="<ui:download resid="${attach.resId }"
													filename="${attach.attachName }"></ui:download>"><b
												class='download_icon' title='下载'></b></a> <b class='see_icon'
												title='查看' onclick="scanResFile('${attach.resId}');"></b>
										</dt>
									</dl>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:if>
				<div class='info_border'></div>
				<div class='partake_discuss'
					<c:if test="${activity.isOver }">style="display:none;"</c:if>>
					<div class='partake_discuss_title'>
						<h5 class='partake_discuss_title_h5' style="width: 200px;">
							<span></span>参与讨论：
						</h5>
						<h4 class='partake_discuss_title_h4'>
							<b id="w_count">0</b>/300
						</h4>
					</div>
					<form id="addDiscuss" action="" method="post">
						<textarea id='discussion_content'></textarea>
						<input type="hidden" name="content" id="content_hidden" /> <input
							type="hidden" name="activityId" id="activityId"
							value="${activity.id }"> <input type="hidden"
							name="discussLevel" value="1"> <input type="hidden"
							name="parentId" value="0"> <input type="hidden"
							name="typeId" value="${activityType }" id="discussType">
						<input type="hidden" id="expertIds" value="${activity.expertIds }">
						<div class="clear"></div>
						<input type="button" class="submit1" value="说完了"
							id="discussion_fabu" />
					</form>
				</div>
				<iframe id="sch_discuss_iframe" onload="setCwinHeight(this,false,100)"
					width="100%" style="margin-top: 20px; border: none;" scrolling="no"
					frameborder="no"></iframe>
				<c:if test="${activity.isShare}">
					<c:if test="${activity.isOver }">
						<iframe id="commentBox" onload="setCwinHeight(this,false,100)"
							width="100%" style="margin-top: 20px; border: none;" scroll="no"
							scrolling="no" frameborder="no"
							src="${ctx}jy/comment/list?authorId=${activity.organizeUserId }&resType=${activityType }&resId=${activity.id }&title=<ui:sout value='${activity.activityName}' encodingURL='true' escapeXml='true'></ui:sout>&flags=false"></iframe>
					</c:if>
					<c:if test="${!activity.isOver }">
						<iframe id="commentBox" onload="setCwinHeight(this,false,100)"
							width="100%" style="margin-top: 20px; border: none;" scroll="no"
							scrolling="no" frameborder="no"
							src="${ctx}jy/comment/list?authorId=${activity.organizeUserId }&resType=${activityType }&resId=${activity.id }&title=title=<ui:sout value='${activity.activityName}' encodingURL='true' escapeXml='true'></ui:sout>&flags=true&titleShow=true"></iframe>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
