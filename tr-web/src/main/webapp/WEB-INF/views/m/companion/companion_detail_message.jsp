<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="同伴互助"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/companion/css/companion.css" media="screen">
	<ui:require module="../m/companion/js"></ui:require> 
</head>
<body>
<form id="msglist_form" action="${ctx }/jy/companion/messages/${companionUser.id }/page" method="post">
	<input type="hidden" id="currentPageSet" name="page.currentPage" value="${page.currentPage}">
</form>
<jy:di key="${_CURRENT_USER_.id }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
<jy:di key="${companionUser.id }" className="com.tmser.tr.uc.service.UserService" var="u1"></jy:di>
<div class="companion_content_r_r1">
	<h3>
	消息记录(
	${page.page.currentPage} / 
	<c:if test="${page.page.totalCount%page.page.pageSize==0}"><fmt:formatNumber type="number" value="${page.page.totalCount/page.page.pageSize}"/></c:if>
	<c:if test="${page.page.totalCount%page.page.pageSize!=0}"><fmt:formatNumber type="number" value="${(page.page.totalCount-page.page.totalCount%page.page.pageSize)/page.page.pageSize+1}"/></c:if>
	)</h3>
	<div class="chat_content" id="chat_content">
		<div id="scroller">
			<c:forEach items="${page.datalist }" var="comp">
				<c:if test="${comp.userIdSender==u1.id }">
				<div class="chat_content_l">
					<div class="chat_content_l_head">
						<ui:photo src="${u1.photo}" width="57" height="57"></ui:photo>
					</div>
					<div class="chat_content_l_cont">
						<span></span>
						<p>
							${comp.message }
							<c:if test="${not empty comp.attachment1}">
								<c:if test="${not empty comp.message}">
								<q style="border-bottom:0.083rem #bdbdbd solid;display: inline-block;width:100%;margin:0 auto; "></q>
								</c:if>
								<a class="chat_fj" href="<ui:download resid="${comp.attachment1}" filename="${comp.attachment1Name}"></ui:download>">
									<q class="chat_fj_l"></q>
									<q class="chat_fj_r">${comp.attachment1Name}</q>
								</a>
								<c:if test="${not empty comp.attachment2}">
								<a class="chat_fj" href="<ui:download resid="${comp.attachment2}" filename="${comp.attachment2Name}"></ui:download>">
									<q class="chat_fj_l"></q>
									<q class="chat_fj_r">${comp.attachment2Name}</q>
								</a>
								</c:if>
								<c:if test="${not empty comp.attachment3}">
								<a class="chat_fj" href="<ui:download resid="${comp.attachment3}" filename="${comp.attachment3Name}"></ui:download>">
									<q class="chat_fj_l"></q>
									<q class="chat_fj_r">${comp.attachment3Name}</q>
								</a>
								</c:if>
							</c:if>
							<strong style="clear:both;display:block;"></strong>
						</p>
						<div class="date_time">
							<fmt:formatDate value="${comp.senderTime}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${comp.userIdSender==u.id }">
				<div class="chat_content_r">
					<div class="chat_content_r_head">
						<ui:photo src="${u1.photo}" width="57" height="57"></ui:photo>
					</div>
					<div class="chat_content_r_cont">
						<span></span>
						<p>
							${comp.message }
							<c:if test="${not empty comp.attachment1}">
								<c:if test="${not empty comp.message}">
								<q style="border-bottom:0.083rem #bdbdbd solid;display: inline-block;width:100%;margin:0 auto; "></q>
								</c:if>
								<a class="chat_fj" href="<ui:download resid="${comp.attachment1}" filename="${comp.attachment1Name}"></ui:download>">
									<q class="chat_fj_l"></q>
									<q class="chat_fj_r">${comp.attachment1Name}</q>
								</a>
								<c:if test="${not empty comp.attachment2}">
								<a class="chat_fj" href="<ui:download resid="${comp.attachment2}" filename="${comp.attachment2Name}"></ui:download>">
									<q class="chat_fj_l"></q>
									<q class="chat_fj_r">${comp.attachment2Name}</q>
								</a>
								</c:if>
								<c:if test="${not empty comp.attachment3}">
								<a class="chat_fj" href="<ui:download resid="${comp.attachment3}" filename="${comp.attachment3Name}"></ui:download>">
									<q class="chat_fj_l"></q>
									<q class="chat_fj_r">${comp.attachment3Name}</q>
								</a>
								</c:if>
							</c:if>
							<strong style="clear:both;display:block;"></strong>
						</p>
						<div class="date_time">
							<fmt:formatDate value="${comp.senderTime}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
					</div>
				</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<div class="send_message1">
		<div class="first_page"></div>
		<div class="prev_page"></div>
		<div class="next_page"></div>
		<div class="last_page"></div>
	</div>
	<span class="lastPageClassValue" style="display: none;">
		<c:if test="${page.page.totalCount%page.page.pageSize==0}"><fmt:formatNumber type="number" value="${page.page.totalCount/page.page.pageSize}"/></c:if>
		<c:if test="${page.page.totalCount%page.page.pageSize!=0}"><fmt:formatNumber type="number" value="${(page.page.totalCount-page.page.totalCount%page.page.pageSize)/page.page.pageSize+1}"/></c:if>
	</span>
</div>
</body>
<script type="text/javascript">
	require(["zepto","msg"],function(){
	});
</script>
</html>