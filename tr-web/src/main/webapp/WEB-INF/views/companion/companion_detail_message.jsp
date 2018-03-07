<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="同伴互助"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic}/modules/companion/css/companion.css" media="screen"></link>
</head>
<body style="background: #fff;">
	<div class="detail_wrap_bottom">
		<h3 class="detail_wrap_bottom_h3">
			<span class='detail_wrap_bottom_h3_span'></span> 留言列表
			<c:if test="${not empty page.datalist }">
				<div class="toggle1" data-key="0">
					<span></span> <strong id="showNotice">收起</strong>
				</div>
			</c:if>
			<c:if test="${empty page.datalist }">
				<div class="toggle1" data-key="2">
					<span></span> <strong>收起</strong>
				</div>
			</c:if>
		</h3>
		<div class="companion_cont">
			<ul class="companionNews_bottom_ul">
				<c:if test="${empty page.datalist }">
					<div class="empty_wrap">
						<div class="empty_img"></div>
						<div class="empty_info" style="text-align: center;">当前列表为空，请稍后再来！</div>
					</div>
				</c:if>
				<c:if test="${not empty page.datalist }">
					<c:forEach items="${page.datalist }" var="message">
						<li><c:if test="${message.userIdSender == user.id }">
								<dl class="companionNews_bottom_ul_con2">
									<dd class="companionNews_dialog2"
										style="behavior:url(${ctxStatic }/modules/companion/css/ie-css3.htc);">
										<span class='sanjiao3'></span>
							</c:if> <c:if test="${message.userIdSender!=user.id }">
								<dl class="companionNews_bottom_ul_con">
									<dt class="photo">
										<a href="./jy/companion/companions/${companionUser.id }"
											target="_blank"> <ui:photo src="${companionUser.photo }"></ui:photo>
										</a> <span class='photo_mask'></span>
										<c:set var="messageUserName"
											value="${user.orgId eq companionUser.orgId ? companionUser.name : companionUser.nickname  }" />
										<span class="photo_name1" title="${messageUserName }">${messageUserName }</span>
									</dt>
									<dd class="companionNews_dialog"
										style="behavior:url(${ctxStatic }/modules/companion/css/ie-css3.htc);">
										<span class='sanjiao2'></span>
							</c:if>
							<p>${message.message }</p> <c:if
								test="${(message.attachment1 != null && message.attachment1 != '') || (message.attachment2 !=null && message.attachment2 !='') || (message.attachment3 !=null && message.attachment3 !='') }">
								<div class="files_wrap1">
									<c:if
										test="${message.attachment1 != null && message.attachment1 != '' }">
										<div class='files_wrap'>
											<div class='files_wrap_left'></div>
											<div class='files_wrap_center'
												title="${message.attachment1Name }">
												<c:if test="${message.userIdSender==user.id }">
													<a
														href="${pageContext.request.contextPath }/jy/scanResFile?resId=${message.attachment1 }"
														target="_blank">${message.attachment1Name }</a>
												</c:if>
												<c:if test="${message.userIdSender!=user.id }">
													<a
														href="${pageContext.request.contextPath }/jy/manage/res/download/${message.attachment1 }">${message.attachment1Name }</a>
												</c:if>
											</div>
										</div>
									</c:if>
									<c:if
										test="${message.attachment2 != null && message.attachment2 != '' }">
										<div class='files_wrap'>
											<div class='files_wrap_left'></div>
											<div class='files_wrap_center'
												title="${message.attachment2Name }">
												<c:if test="${message.userIdSender==user.id }">
													<a
														href="${pageContext.request.contextPath }/jy/scanResFile?resId=${message.attachment2 }"
														target="_blank">${message.attachment2Name }</a>
												</c:if>
												<c:if test="${message.userIdSender!=user.id }">
													<a
														href="${pageContext.request.contextPath }/jy/manage/res/download/${message.attachment2 }">${message.attachment2Name }</a>
												</c:if>
											</div>
										</div>
									</c:if>
									<c:if
										test="${message.attachment3 != null && message.attachment3 != '' }">
										<div class='files_wrap'>
											<div class='files_wrap_left'></div>
											<div class='files_wrap_center'
												title="${message.attachment3Name }">
												<c:if test="${message.userIdSender==user.id }">
													<a
														href="${pageContext.request.contextPath }/jy/scanResFile?resId=${message.attachment3 }"
														target="_blank">${message.attachment3Name }</a>
												</c:if>
												<c:if test="${message.userIdSender!=user.id }">
													<a
														href="${pageContext.request.contextPath }/jy/manage/res/download/${message.attachment3 }">${message.attachment3Name }</a>
												</c:if>
											</div>
										</div>
									</c:if>
								</div>
							</c:if>
							</dd> <c:if test="${message.userIdSender == user.id }">
								<dd class="companionNews_time2">
									<fmt:formatDate value="${message.senderTime }"
										pattern="yyyy-MM-dd" />
								</dd>
								<dt class="photo">
									<ui:photo src="${user.photo }" width="54" height="54" />
									<span class='photo_mask'></span> <span class="photo_name1"
										title="${user.name }">${user.name }</span>
								</dt>
								</dl>
								<div class="clear"></div>
							</c:if> <c:if test="${message.userIdSender != user.id }">
								<dd class="companionNews_time">
									<fmt:formatDate value="${message.senderTime }"
										pattern="yyyy-MM-dd" />
								</dd>
								</dl>
							</c:if></li>
					</c:forEach>
				</c:if>
			</ul>
			<div class="page">
				<form name="pageForm" method="post">
					<ui:page url="./jy/companion/messages/${companionUser.id }/page"
						data="${page}" />
					<input type="hidden" class="currentPage" name="page.currentPage"
						value="${page.currentPage}"> <input type="hidden"
						value="5" name="page.pageSize">
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		// 收起-展开
		$(".toggle1").click(function() {
			var key = $(this).attr("data-key");
			if (key == 1) {// 收起
				$('#showNotice').text('展开');
				$(this).attr("data-key", 0);
				$(this).find("span").css("background-position", "-142px -2px");
				$(".companion_cont").hide();
				$("#frameDetail", window.parent.document).css("height", "180px");
			}else if (key == 0) {// 展开
				$('#showNotice').text('收起');
				$(this).attr("data-key", 1);
				$(this).find("span").css("background-position", "-130px -1px");
				$(".companion_cont").show();
				$("#frameDetail", window.parent.document).css("height", "");
			}
		});

		$(".toggle1").trigger("click");
	});
</script>
</html>