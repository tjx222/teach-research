<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="校际教研内容-主题研讨"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css" />
	
	<script type="text/javascript" src="${ctxStatic }/modules/schoolactivity/js/activity.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		var activityId = "${activity.id}";
		var expertIds = "${activity.expertIds}";
		var prarms = "?activityId="+activityId+"&typeId=${type}&canReply=false&expertIds="+expertIds+"&"+ Math.random();
		$("#discuss_iframe").attr("src","jy/comment/discussIndex"+prarms);
		$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/teachingView/view/comment/list?authorId=${activity.organizeUserId}&resType=${type}&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=true&titleShow=true");
		
		//显示主备教案的意见教案集合
		$(".participant_edit_right ul li").click(function() {
			var planId = $(this).attr("data-planId");
			var activityId = $(this).attr("data-activityId");
			$(this).addClass("ul_li_act").siblings().removeClass("ul_li_act");
			$(".participant_edit_right_cont").hide().eq($('.participant_edit_right ul li').index(this)).show();
			$("#iframe2").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/getYijianTrackList?planId=" + planId + "&activityId=" + activityId);
		});
		$(window).scroll(function (){
			$(".kongdiv").toggle();
		});
		// 显示主备教案的修改教案
		$(".zb_lessonplan_cont_title ul li a").click(function() {
			$(this).closest("ul").find("a").removeClass("li_act");
			$(this).addClass("li_act");
			var planId = $(this).attr("data-planId");
			var activityId = $(this).attr("data-activityId");
			$("#iframe1").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/showLessonPlanTracks?editType=0&planId=" + planId + "&activityId=" + activityId);
			var frame = window.frames["iframe1"];
			if (window.frames["iframe1"].wordObj.IsDirty) {
				if (window.confirm("您修改的教案还未保存，是否保存当前修改的教案内容？")) {
					wantToEdit1();
					$("#saveEdit").trigger("click");
				}
			}			
		});
	});
	</script>
</head>
<body>
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="校际教研"></ui:tchTop>
	</div>
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
				</h3>
				<ol class="partake_info">
					<c:if test="${activity.typeId eq 1 }">
						<li class="partake_info_Standby"><span></span><b>主备人：${activity.mainUserName }</b>
							<div class='partake_Standby_info'>
								<ul>
									<li>${activity.organizeUserName }</li>
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
								<h5 class='zb_lessonplan_right_h5' id="saveEdit">
									<span></span>保存修改
								</h5>
							</c:if>
							<div class='zb_lessonplan_cont'>
								<div class='zb_lessonplan_cont_title'>
									<h3>${lessonName}</h3>
									<ul>
										<c:forEach var="zhubei" items="${zhubeiList }"
											varStatus="status">
											<li><a href="javascript:void(0);"
												${status.index==0 ? 'class="li_act"':'' }
												data-planId="${zhubei.id}" data-activityId="${activity.id }">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</a></li>
										</c:forEach>
									</ul>
								</div>
								<div class='zb_lessonplan_cont_word'>
									<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
									<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
										<iframe width="100%" height="496px" id="iframe1"
											name="iframe1" style="border: none;" frameborder="0"
											scrolling="no"
											src="${pageContext.request.contextPath }/jy/schoolactivity/showLessonPlanTracks?planId=${zhubeiList[0].id }&activityId=${activity.id}&editType=0"></iframe>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<input type="hidden" id="isTongbei" value="${zhengLiTongBei }" />
					<c:if test="${zhengLiTongBei }">
						<div class='edit_zb_lessonplan clearfix'>
							<div class='edit_zb_lessonplan_icon'></div>
							<div class='edit_zb_lessonplan_right'>
								<div class='edit_zb_lessonplan_title'>
									<h3 class='edit_zb_lessonplan_right_h3'>修改后的主备教案：</h3>
									<c:if
										test="${activity.mainUserId == _CURRENT_USER_.id && !activity.isSend}">
										<h5 class='edit_zb_lessonplan_right_h5'
											data-id='${activity.id}' id="sendToJoiners"
											data-type='${listType}'>
											<span></span>发送消息
										</h5>
									</c:if>
									<c:if
										test="${activity.mainUserId == _CURRENT_USER_.id && activity.isSend}">
										<h5 class='edit_zb_lessonplan_right_h51'>已发送</h5>
									</c:if>
									<div class="clear"></div>
								</div>
								<div class='edit_zb_lessonplan_cont'>
									<iframe width="860px" height="124px;" id="iframe3"
										style="border: none;"
										src="${pageContext.request.contextPath }/jy/schoolactivity/getZhengliTrackList?activityId=${activity.id}"></iframe>
								</div>
							</div>
						</div>
					</c:if>
					<c:if
						test="${!zhengLiTongBei && zhengliList!=null && fn:length(zhengliList)>0 }">
						<div class='edit_zb_lessonplan clearfix'>
							<div class='edit_zb_lessonplan_icon'></div>
							<div class='edit_zb_lessonplan_right'>
								<div class='edit_zb_lessonplan_title'>
									<h3 class='edit_zb_lessonplan_right_h3'>修改后的主备教案：</h3>
									<div class="clear"></div>
								</div>
								<div class='edit_zb_lessonplan_cont'>
									<c:forEach items="${zhengliList }" var="zhengli">
										<dl>
											<dd>
												<img src="${ctxStatic }/common/images/icon/word.png"
													title="${zhengli.planName }" style="cursor: pointer;"
													data-id="${zhengli.resId}" id="scanPlanTrack">
											</dd>
											<dt title="${zhengli.planName }">
												<ui:sout value="${zhengli.planName }" length="28"
													needEllipsis="true"></ui:sout>
											</dt>
										</dl>
									</c:forEach>
								</div>
							</div>
						</div>
					</c:if>
					<div class='participant_edit clearfix'>
						<div class='participant_edit_icon'></div>
						<div class='participant_edit_right'>
							<h3 class='participant_edit_right_h3'>参与人修改留痕：</h3>
							<ul>
								<c:forEach var="zhubei" items="${zhubeiList }"
									varStatus="status">
									<li class='ul_li ${status.index==0 ? "ul_li_act":"" }'
										data-planId="${zhubei.id}" data-activityId="${activity.id }"><a
										href="javascript:void(0);">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</a></li>
								</c:forEach>
							</ul>
							<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
								<iframe width="860px" height="150px;" id="iframe2"
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
								<div class='view_class_video_video clearfix'>
									<iframe src="${recordUrl }" width="100%" height="600px"
										frameborder="no"></iframe>
								</div>
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
				<iframe id="discuss_iframe" onload="setCwinHeight(this,false,100)"
					width="100%" style="margin-top: 20px; border: none;" scrolling="no"
					frameborder="no"></iframe>
				<iframe id="commentBox" onload="setCwinHeight(this,false,100)"
					width="100%" style="margin-top: 20px; border: none;" scroll="no"
					scrolling="no" frameborder="no"
					src="${ctx}jy/comment/list?authorId=${activity.organizeUserId }&resType=${activityType }&resId=${activity.id }&title=<ui:sout value='${activity.activityName}' encodingURL='true' escapeXml='true'></ui:sout>&flags=false"></iframe>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
</html>
