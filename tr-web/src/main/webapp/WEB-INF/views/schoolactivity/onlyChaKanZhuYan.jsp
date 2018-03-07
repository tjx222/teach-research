<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="查看主题研讨"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/activity.css" media="all">
	
	<script type="text/javascript" src="${ctxStatic }/modules/schoolactivity/js/activity.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		var activityId = "${activity.id}";
		var expertIds = "${activity.expertIds}";
		var prarms = "?activityId="+activityId+"&typeId=12&canReply=false&expertIds="+expertIds+"&"+ Math.random();
		$("#discuss_iframe").attr("src","jy/regschactivity/discussIndex"+prarms);
		$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId=${activity.organizeUserId}&resType=12&resId=${activity.id}&title=<ui:sout value='${activity.activityName }' encodingURL='true' escapeXml='true'></ui:sout>&flags=false&titleShow=true");
	});
	</script>
</head>
<body>
	<div class="wrap">
		<ui:tchTop hideMenuList="false"></ui:tchTop>
		<div class="wrap_top_2">
			<h3>当前位置：<jy:nav id="xjjy_ckzy"></jy:nav></h3>
		</div>
		<div class="clear"></div>
		<div class="participate">
			<div class="participate_cont">
				<h3>${activity.activityName }</h3>
				<h4>
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
				<c:if test="${!empty attachList }">
				<label for="" class="lable2">活动附件</label>
				<div class="clear"></div>
				<div class="word_tab_annex">
					<c:forEach var="attach" items="${attachList }" varStatus="status">
						<div class="word_tab_annex_dl">
						<dl>
							<dd style="cursor: pointer;" onclick="scanResFile('${attach.resId}');"><ui:icon ext="${attach.ext}"></ui:icon></dd>
							<dt title="${attach.attachName }"><ui:sout value="${attach.attachName }" length="28" needEllipsis="true"></ui:sout></dt>
						</dl>
						<div class="bor_der">
							<ul>
								<ui:isView ext="${attach.ext }">
									<li class="bor_der_1" onclick="scanResFile('${attach.resId}');" title="浏览"></li>
								</ui:isView>
									<a href="<ui:download resid="${attach.resId }" filename="${attach.attachName }"></ui:download>"><li class="bor_der_2" title="下载"></li></a>
							</ul>
						</div>
						</div>
					</c:forEach>
				</div>
				</c:if>
				<iframe id="discuss_iframe" onload="setCwinHeight(this,false,100)" width="100%" style="margin-top:20px;border: none;" scroll="no" scrolling="no"  frameborder="no" ></iframe>
				<iframe id="commentBox" onload="setCwinHeight(this,false,100)" width="100%" style="border: none;" scroll="no" scrolling="no" frameborder="no" ></iframe>
			</div>
		</div>
		<div class="clear"></div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>
</body>
</html>
