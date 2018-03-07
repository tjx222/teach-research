<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="同伴互助"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="同伴互助"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="同伴互助"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_lesson_fansi?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
		</jy:nav>
	</div>
	<div class="companion_content">
		<div class="companion_title">
			<dl class="companion_title_News">
				<dt class="photo">
					<span><ui:photo src="${user2.photo }" width="50" height="53" ></ui:photo></span>
				</dt>
				<dt class="photo_mask">
					<img src="${ctxStatic }/modules/teachingview/images/state.png"/>
				</dt>
				<dd>
					<span class="teacher_name">${user2.name}</span>
					<span class="teacher_identity">
					<c:forEach items="${searchVo.userSpaceList}" var="space" varStatus="c">
						<c:if test="${fn:length(searchVo.userSpaceList)==c.count}">
							${space.spaceName}
						</c:if>
						<c:if test="${fn:length(searchVo.userSpaceList)!=c.count}">
							${space.spaceName}、
						</c:if>
					</c:forEach>
					</span>
				</dd>
			</dl>
		</div>
		<div class="companion_con">
			<ul class="managers_rethink_con_bigType">
				<li class="li_active3">留言（<span>${messageData.page.totalCount }</span>）</li>
			</ul>
			<div class="companion_outBox">
				<div class="companion_outBox_type show">
					<c:if test="${empty messageData.datalist }">
						<!-- 无文件 -->
		   				<div class="nofile">
							<div class="nofile1">
								暂时还没有数据，稍后再来查阅吧！
							</div>
						</div>
					</c:if>
					<c:if test="${not empty messageData.datalist }">
						<c:forEach items="${messageData.datalist}" var="message">
							<ul class="companion_ul">
								<li>
									<p class="companion_sign">
										<span class="companion_sign_data"><fmt:formatDate value="${message.senderTime}" pattern="yyyy-MM-dd"/></span>
									<span class="companion_sign_time"><fmt:formatDate value="${message.senderTime}" pattern="HH:mm:ss"/></span>
										<span class="companion_sign_name">对
										<a target="_blank" href="${ctx}jy/teachingView/view/companionMessage?userId=${message.userIdReceiver}&termId=${searchVo.termId}&userIdSender=${message.userIdSender}">${message.userNameReceiver}
										</a>说：</span>
									</p>
									<div class="companion_dialog">
										<img src="${ctxStatic }/modules/teachingview/images/sanjiao.png" class="sanjiao"/>
										<p title="${message.message}"><ui:sout value="${message.message}" length="110" needEllipsis="true"></ui:sout></p>
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
									</div>
								</li>
							</ul>
						</c:forEach>
					</c:if>
				</div>
			</div>
					<form  name="pageForm" method="post">
						<ui:page url="${ctx}jy/teachingView/manager/m_companion?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}" data="${messageData}"  />
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden" id="" name="listType" value="1"> 
					</form>
			
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery',"jp/jquery.form.min",'managerList'],function(){});
</script>
</html>
