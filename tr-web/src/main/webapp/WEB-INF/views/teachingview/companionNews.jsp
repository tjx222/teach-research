<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="同伴互助内容"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="同伴互助内容"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
	</div>
	<div class="companionNews_content">
		<div class="companionNews_header">
			<dl class="companionNews_header_News">
				<dt class="photo">
					<span><ui:photo src="${user2.photo }" width="55" height="55" ></ui:photo></span>
				</dt>
				<dt class="photo_mask">
					<img src="${ctxStatic }/modules/teachingview/images/state.png"/>
				</dt>
				<dd>
					<span class="teacher_name">${user2.name}（<b>${user2.sex==0?'女':'男'}</b>）</span>
				</dd>
				<dd class="teacher_news">
					<span>职称：<b>${user2.profession}</b></span>
					<span>教龄：<b>${user2.schoolAge==null?0:user2.schoolAge} 年</b></span>
				</dd>
				<dd class="teacher_news">
					<span>学科：<b>
						<c:forEach items="${searchVo.subjectIds}" var="subject" varStatus="c">
							<c:if test="${fn:length(searchVo.subjectIds)==c.count}">
								<jy:dic key="${subject }"/>
							</c:if>
							<c:if test="${fn:length(searchVo.subjectIds)!=c.count}">
								<jy:dic key="${subject }"/>
							</c:if>
						</c:forEach>
					</b></span>
					<span>教材：<b>
						${searchVo.flags}
					</b></span>
					<span>年级：<b>
						<c:forEach items="${searchVo.gradeIds}" var="grade" varStatus="c">
							<c:if test="${fn:length(searchVo.gradeIds)==c.count}">
								<jy:dic key="${grade }"/>
							</c:if>
							<c:if test="${fn:length(searchVo.gradeIds)!=c.count}">
								<jy:dic key="${grade }"/>、
							</c:if>
						</c:forEach>
					</b></span>
				</dd>
				<dd class="teacher_news">
					<span>职务：<b>
					<c:forEach items="${searchVo.userSpaceList}" var="space" varStatus="c">
						<c:if test="${fn:length(searchVo.userSpaceList)==c.count}">
							${space.spaceName}
						</c:if>
						<c:if test="${fn:length(searchVo.userSpaceList)!=c.count}">
							${space.spaceName}、
						</c:if>
					</c:forEach>
					</b></span>
				</dd>
			</dl>
		</div>
		<div class="companionNews_bottom">
			<ul class="companionNews_bottom_ul">
				<c:forEach items="${messageData.datalist}" var="message" varStatus="c">
				<c:choose>
					<c:when test="${userIdReceiver==message.userIdReceiver}">
						<li>
							<dl class="companionNews_bottom_ul_con">
								<dt class="photo"></dt>
								<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
								<dd class="companionNews_dialog">
									<img src="${ctxStatic }/modules/teachingview/images/sanjiao2.png" class="sanjiao2"/>
									<p>${message.message}</p>
									<c:if test="${not empty message.attachment1}">
										<div class="companion_file">
											<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
											<span ><a style="color:#FF8D25;" href="${ctx}jy/scanResFile?resId=${message.attachment1}" target="_blank">${message.attachment1Name}</a></span>
										</div>
									</c:if>
									<c:if test="${not empty message.attachment2}">
										<div class="companion_file">
											<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
											<span><a style="color:#FF8D25;" href="${ctx}jy/scanResFile?resId=${message.attachment2}" target="_blank">${message.attachment2Name}</a></span>
										</div>
									</c:if>
									<c:if test="${not empty message.attachment3}">
										<div class="companion_file">
											<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
											<span><a style="color:#FF8D25;" href="${ctx}jy/scanResFile?resId=${message.attachment3}" target="_blank">${message.attachment3Name}</a></span>
										</div>
									</c:if>
								</dd>
								<dd class="companionNews_time">
									<span class="companion_sign_data"><fmt:formatDate value="${message.senderTime}" pattern="yyyy-MM-dd"/></span>
									<span class="companion_sign_time"><fmt:formatDate value="${message.senderTime}" pattern="HH:mm:ss"/></span>
								</dd>
							</dl>
						</li>
					</c:when>
					<c:otherwise>
							<li>
								<dl class="companionNews_bottom_ul_con2">
									<dd class="companionNews_dialog2">
										<img src="${ctxStatic }/modules/teachingview/images/sanjiao3.png" class="sanjiao3"/>
										<p>${message.message}</p>
										<c:if test="${not empty message.attachment1}">
											<div class="companion_file">
												<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
												<span><a style="color:#FF8D25;" href="${ctx}jy/scanResFile?resId=${message.attachment1}" target="_blank">${message.attachment1Name}</a></span>
											</div>
										</c:if>
										<c:if test="${not empty message.attachment2}">
											<div class="companion_file">
												<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
												<span><a style="color:#FF8D25;" href="${ctx}jy/scanResFile?resId=${message.attachment2}" target="_blank">${message.attachment2Name}</a></span>
											</div>
										</c:if>
										<c:if test="${not empty message.attachment3}">
											<div class="companion_file">
												<img src="${ctxStatic }/modules/teachingview/images/w_icon.png"/>
												<span><a style="color:#FF8D25;" href="${ctx}jy/scanResFile?resId=${message.attachment3}" target="_blank">${message.attachment3Name}</a></span>
											</div>
										</c:if>
									</dd>
									<dd class="companionNews_time2">
										<span class="companion_sign_data"><fmt:formatDate value="${message.senderTime}" pattern="yyyy-MM-dd"/></span>
										<span class="companion_sign_time"><fmt:formatDate value="${message.senderTime}" pattern="HH:mm:ss"/></span>
									</dd>
									<dt class="photo"></dt>
									<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
								</dl>
								<div class="clear"></div>
							</li>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</ul>
		</div>
			<form  name="pageForm" method="post">
				<ui:page url="${ctx}jy/teachingView/view/companionMessage?userId=${userIdReceiver}&termId=${searchVo.termId}&userIdSender=${userIdSender}" data="${messageData}"  />
				<input type="hidden" class="currentPage" name="page.currentPage">
				<input type="hidden" id="" name="listType" value="1"> 
			</form>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>
