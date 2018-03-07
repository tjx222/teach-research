<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<head>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="查看同备教案"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/activity.css" media="all">
<script type="text/javascript" src="${ctxStatic }/modules/schoolactivity/js/activity.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
	var activityId = "${activity.id}";
	var expertIds = "${activity.expertIds}";
	var prarms = "?activityId="+activityId+"&typeId=12&canReply=false&expertIds="+expertIds+"&"+ Math.random();
	$("#discuss_iframe").attr("src","jy/regschactivity/discussIndex"+prarms);
	$('.word_tab_1 ul li').click(function(){ 
	    $(this).addClass("word_tab_1_act").siblings().removeClass("word_tab_1_act");
	    $(".word_tab_1_big .word_tab_1_big_tab").hide().eq($('.word_tab_1 ul li').index(this)).show(); 
 	});
	$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=12&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=false");
});
//显示主备教案的修改教案
function showLessonPlanTrack(obj,planId){
	$(obj).addClass("word_tab_act").siblings().removeClass("word_tab_act");
	$("#iframe1").attr("src",_WEB_CONTEXT_+"/jy/schoolactivity/showLessonPlan?planId="+planId);
}
//显示主备教案的意见教案集合
function showTrackList(planId,activityId){
	$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/schoolactivity/getYijianTrackList?planId="+planId+"&activityId="+activityId);
}
</script>
</head>
<body>
	<div class="wrap">
		<div class="clear"></div>
		<div class="participate">
			<div class="participate_cont">
				<h3>${activity.activityName }</h3>
				<h4>
					主备人：
					<span>
						${activity.mainUserName }
						<div class="school_1">
							<dl>
								<dd>
									<jy:di key="${activity.mainUserId }" className="com.tmser.tr.uc.service.UserService" var="user1"/>
									<span><ui:photo src="${user1.photo }" width="80" height="85" ></ui:photo></span>
									<span>${user1.name }</span>
								</dd>
								<dt>
									<jy:di key="${activity.infoId }" className="com.tmser.tr.myplanbook.service.MyPlanBookService" var="lessonInfo"/>
									<c:if test="${!empty lessonInfo.subjectId && lessonInfo.subjectId!=0 }">
										<span>
											学科：<jy:dic key="${lessonInfo.subjectId }"/>
										</span>
									</c:if>
									<c:if test="${!empty lessonInfo.bookId }">
										<span>教材：
											<jy:ds key="${lessonInfo.bookId }" className="com.tmser.tr.manage.meta.service.BookService" var="book"/>
											${book.formatName }
										</span>
									</c:if>
									<c:if test="${!empty lessonInfo.gradeId && lessonInfo.gradeId!=0 }">
										<span>年级：
												<jy:dic key="${lessonInfo.gradeId }"/>
										</span>
									</c:if>
									<span>职务：
										教师
									</span>
									<c:if test="${!empty user1.profession }">
										<span>职称：
											${user1.profession }
										</span>
									</c:if>
								</dt>
							</dl>
						</div>
					</span>
				发起人：
				 	<span>
				 		${activity.organizeUserName }
				 		<div class="school_1">
							<dl>
								<dd>
									<jy:di key="${activity.organizeUserId }" className="com.tmser.tr.uc.service.UserService" var="user2"/>
									<span><ui:photo src="${user2.photo }" width="80" height="85" ></ui:photo></span>
									<span>${user2.name }</span>
								</dd>
								<dt>
									<jy:di key="${activity.spaceId }" className="com.tmser.tr.uc.service.UserSpaceService" var="us"/>
									<c:if test="${!empty us.subjectId && us.subjectId!=0 }">
										<span>
											学科：<jy:dic key="${us.subjectId }"/>
										</span>
									</c:if>
									<c:if test="${!empty us.bookId }">
										<span>教材：
											<jy:ds key="${us.bookId }" className="com.tmser.tr.manage.meta.service.BookService" var="book"/>
											${book.formatName }
										</span>
									</c:if>
									<c:if test="${!empty us.gradeId && us.gradeId!=0 }">
										<span>年级：
												<jy:dic key="${us.gradeId }"/>
										</span>
									</c:if>
									<span>职务：
										<jy:di key="${us.roleId }" className="com.tmser.tr.uc.service.RoleService" var="role"/>
										${role.roleName }
									</span>
									<c:if test="${!empty user2.profession }">
										<span>职称：
											${user2.profession }
										</span>
									</c:if>
								</dt>
							</dl>
						</div>
				 	</span>
					<c:if test="${empty activity.isTuiChu }">
						<input type="button" value="" class="yjs">
					</c:if>
				</h4>
				<div class="time">
					<h5>活动时间：<span><fmt:formatDate value="${activity.startTime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${activity.endTime }" pattern="yyyy-MM-dd HH:mm"/></span></h5>
					<h5>参与学校：<span title="${joinOrgNames}"><ui:sout value="${joinOrgNames}" length="46" needEllipsis="true"></ui:sout> </span></h5>
					<h5>参与范围：<span>${activity.subjectName } &nbsp;&nbsp;&nbsp;&nbsp; ${activity.gradeName}</span></h5>
				</div>
				<div class="clear"></div>
				<label for="" class="lable">活动要求:</label>
				<div class="clear"></div>
				<div class="activities">
					<textarea name="" id="" cols="110" rows="4" class="txtarea_1" readonly="readonly">${activity.remark }</textarea>
				</div>
				<label for="" class="lable">主备教案:</label>
				<div class="clear"></div>
				<div class="word_tab">
					<ul>
						<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
							<c:if test="${status.index==0 }">
							<li class="word_tab_act" onclick="showLessonPlanTrack(this,'${zhubei.id}');">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
							<c:if test="${status.index!=0 }">
							<li onclick="showLessonPlanTrack(this,'${zhubei.id}');">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
						</c:forEach>
						<!-- <li class="word_tab_act">第1课时</li><li>第2课时</li><li>第3课时</li><li>第4课时</li> -->
					</ul>
					<div class="word_tab_big">
					<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
						<iframe width="823px;" height="587px" id="iframe1" name="iframe1" style="border:none;border-radius: 6px;" src="${pageContext.request.contextPath }/jy/schoolactivity/showLessonPlan?planId=${zhubeiList[0].id }"></iframe>
					</c:if>
					</div>
				</div>
				<div class="clear"></div>
				<c:if test="${zhengliList!=null && fn:length(zhengliList)>0 }">
				<label for="" class="lable2">修改后的集备教案</label>
				
				<c:if test="${canReceive }">
					<input type="button" class="js"  onclick="receiveLessonPlan('${activity.id}');" value="接收教案"/>
				</c:if>
				<div class="clear"></div>
				<div class="word_tab_1" style="height:124px;">
					<div class="word_tab_1_big_tab" style="height:124px;">
						<div style="overflow:auto;height:124px;width:824px;">
							<c:forEach var="zhengli" items="${zhengliList }">
								<dl>
								<dd><img src="${ctxStatic }/common/icon/base/word.png" title="${zhengli.planName }" style="cursor: pointer;" onclick="scanLessonPlanTrack('${zhengli.resId}');"></dd>
								<dt title="${zhengli.planName }">
									<span><ui:sout value="${zhengli.planName }" length="28" needEllipsis="true"></ui:sout></span>
								</dt>
								</dl>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				</c:if>
				<label for="" class="lable2">参与人修改留痕</label>
				<div class="clear"></div>
				<div class="word_tab_1">
					<ul>
						<c:forEach var="zhubei" items="${zhubeiList }" varStatus="status">
							<c:if test="${status.index==0 }">
							<li class="word_tab_1_act" onclick="showTrackList('${zhubei.id}','${activity.id }');">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
							<c:if test="${status.index!=0 }">
							<li onclick="showTrackList('${zhubei.id}','${activity.id }');">${zhubei.hoursId=='1,2,3,4,5'?'':'第'}${zhubei.hoursId=='1,2,3,4,5'?'全':zhubei.hoursId }${zhubei.hoursId=='1,2,3,4,5'?'案':'课时'}</li>
							</c:if>
						</c:forEach>
					</ul>
					<div class="word_tab_1_big">
						<c:if test="${zhubeiList!=null && fn:length(zhubeiList)>0 }">
							<iframe width="823px" height="124px;" id="iframe2" style="border:none;border-radius: 6px;" src="${pageContext.request.contextPath }/jy/schoolactivity/getYijianTrackList?planId=${zhubeiList[0].id }&activityId=${activity.id}"></iframe>
						</c:if>
					</div>
				</div>
				
				<iframe id="discuss_iframe" onload="setCwinHeight(this,false,100)" width="100%" style="margin-top:20px;border: none;" scroll="no" scrolling="no"  frameborder="no" ></iframe>
				<iframe id="commentBox" onload="setCwinHeight(this,false,100)" width="100%" style="border: none;" scroll="no" scrolling="no" frameborder="no" ></iframe>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
