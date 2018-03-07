<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教研平台"></ui:htmlHeader>
</head>
<body>
<div class='jyyl_top'>
<div class="jyyl_top_logo"> 
	<c:choose>
		<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
			<a href="jy/index" target="_top">
				<div class="jyyl_logo1"></div>
			</a>
		</c:when>
	    <c:otherwise>
			<a href="jy/index" target="_top">
				<div class="jyyl_logo ${sessionScope._sess_flag_ != 'hidebuttom'?'jy':'jx' }"></div>
			</a>
		</c:otherwise>
	</c:choose>
	<div class="jyyl_logo_right">
		<ul>
			<li class="jyyl_logo_right_li jyyl_avatar">
				<ui:photo src="${_CURRENT_USER_.photo }" width="30" height="30" /> 
				<span class="jyyl_head_mask" onclick="personal()"></span>
			</li>
			<li class="jyyl_logo_right_li" style="position:relative;">
				<b class="jyyl_name_news">${_CURRENT_USER_.name }</b>
			</li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li li_hover li_width" style="position:relative;">
				<b class="jyyl_name_news">${_CURRENT_SPACE_.spaceName }</b>
				<div class="identity_box identity_box1" style="width:200px;left:-5px">
					<span class="identity_sanjiao" style="left:92px"></span>
					<ol class="identity">
						<c:forEach items="${_USER_SPACE_LIST_ }" var="space"> 
							<c:if test="${_CURRENT_SPACE_.id != space.id }">
							<li>
								<a href="jy/uc/switch?spaceid=${space.id }"><span>${space.spaceName }</span></a>
							</li>
							</c:if>
						</c:forEach>
					</ol>
				</div>
			</li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li li_hover" style="position:relative;">
				<a href="${ctx}jy/uc/workspace" class="name_news">教研动态</a>
				<div class="identity_box" style="left:-70px">
					<span class="identity_sanjiao" style="left:92px"></span>
					<ol class="identity">
						<li>
							<a href="jy/annunciate/noticeIndex" style="color:#4a4a4a">
								<strong id="tzggNum1">通知公告（0）</strong>
							</a>
						</li>
						<li>
							<a href="jy/planSummary/punishs" style="color:#4a4a4a">
								<strong id="juzjnum1">计划总结（0）</strong>
							</a>
						</li>
					</ol>
				<div>
			</li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li"><a href="${ctx}jy/schoolview/index?orgID=${_CURRENT_USER_.orgId }">学校首页</a></li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li"><a href="${ctx}jy/notice/notices"><b id="notice-new1" style="display: none;"></b>消息中心</a><span id="noticeNum_top"></span></li>
			<li class="jyyl_logo_right_li"><a href="${ctx}/jy/logout"><img src="${ctxStatic }/common/images/exit.png" alt="">退出</a></li>
		</ul>
	</div>
</div>
</div>
<div class="jyyl_nav"> 
	<h3>当前位置： <a target="_top" href="index" id="ra-navs-nav1">空间首页</a>&gt;${param.flag=='about'?'关于我们':'帮助中心'}</h3>
</div>
</body>
</html>