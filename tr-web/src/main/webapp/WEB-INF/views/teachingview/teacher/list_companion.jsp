<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="同伴互助"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="同伴互助"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${userSpace.id!=_CURRENT_SPACE_.id }">
		<c:if test="${empty searchVo.flagz }">
		<jy:nav id="jyyl_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
		</jy:nav>
		</c:if>
		<c:if test="${searchVo.flagz=='grade' }">
		<jy:nav id="jyyl_grade_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
			<jy:param name="gradeName" value="${gradeName }"></jy:param>
		</jy:nav>
		</c:if>
		<c:if test="${searchVo.flagz=='subject' }">
		<jy:nav id="jyyl_subject_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
			<jy:param name="subjectName" value="${subjectName }"></jy:param>
		</jy:nav>
		</c:if> > 同伴互助
		</c:if>
		<c:if test="${userSpace.id==_CURRENT_SPACE_.id }">
		<jy:nav id="jyyl"></jy:nav> > 同伴互助
		</c:if>
	</div>
	<div class="companion_content teachingTesearch_managers_rethink_content" >
		<c:if test="${userSpace.id!=_CURRENT_SPACE_.id }">
		<div class="companion_title">
			<dl class="companion_title_News">
				<dt class="photo"><ui:photo src="${user.photo }" /></dt>
				<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
				<dd><span class="teacher_name">${userSpace.username }</span><span class="teacher_identity">${userSpace.spaceName }</span></dd>
			</dl>
		</div>
		</c:if>
		<div class="companion_con">
			<ul class="managers_rethink_con_bigType">
				<li class="li_active3">留言（<span>${messageCount }</span>）</li>
			</ul>
			<div class="companion_outBox">
				<div class="companion_outBox_type show">
					<ul class="companion_ul">
						<c:forEach var="message" items="${messageList.datalist }">
							<li>
								<p class="companion_sign">
									<span class="companion_sign_data"><fmt:formatDate value="${message.senderTime}" pattern="yyyy-MM-dd"/></span>
									<span class="companion_sign_time"><fmt:formatDate value="${message.senderTime}" pattern="HH:mm:ss"/></span>
									<span class="companion_sign_name">对<a href="${ctx}jy/teachingView/view/companionMessage?userId=${message.userIdReceiver}&termId=${searchVo.termId}&userIdSender=${message.userIdSender}" target="_blank">${message.userNameReceiver}</a>说：</span>
								</p>
								<div class="companion_dialog">
									<img src="${ctxStatic }/modules/teachingview/images/sanjiao.png" class="sanjiao"/>
									<p>${message.message }</p>
									<c:if test="${!empty message.attachment1 }">
									<div class="companion_file" onclick="scanResFile('${message.attachment1}');">
										<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
										<span><ui:sout value="${message.attachment1Name }" needEllipsis="true" length="34"></ui:sout> </span>
									</div>
									</c:if>
									<c:if test="${!empty message.attachment2 }">
									<div class="companion_file" onclick="scanResFile('${message.attachment2}');">
										<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
										<span><ui:sout value="${message.attachment2Name }" needEllipsis="true" length="34"></ui:sout> </span>
									</div>
									</c:if>
									<c:if test="${!empty message.attachment3 }">
									<div class="companion_file" onclick="scanResFile('${message.attachment3}');">
										<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
										<span><ui:sout value="${message.attachment3Name }" needEllipsis="true" length="34"></ui:sout> </span>
									</div>
									</c:if>
								</div>
							</li>
						</c:forEach>
					</ul>
					<c:if test="${empty messageList.datalist}">
						<!-- 无文件 -->
			  				<div class="nofile">
							<div class="nofile1">
								暂时还没有数据，稍后再来查看吧！
							</div>
						</div>
					</c:if>
					<form id="pageForm"  name="pageForm" method="post">
						<ui:page url="${ctx}jy/teachingView/teacher/list_companion" data="${messageList}"  />
						<input id="currentPage" type="hidden" class="currentPage" name="page.currentPage" >
						<input type="hidden" name="termId" value="${searchVo.termId }">
						<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
						<input type="hidden" name="flagz" value="${searchVo.flagz }">
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script>

</script>
</html>
