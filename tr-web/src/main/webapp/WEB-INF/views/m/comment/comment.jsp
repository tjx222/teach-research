<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta charset="UTF-8">
<ui:mHtmlHeader title="评论列表"></ui:mHtmlHeader>
<link rel="stylesheet" href="${ctxStatic }/m/comment/css/comment.css"
	media="screen">
<ui:require module="../m/comment/js"></ui:require>
</head>
<body>
	<div class="opinions_comment_content">
		<div class="opinions_comment_content_scrol" id="wrap3">
			<div id="scroller">
				<c:if test="${empty data.datalist }">
					<div class="comment_k" style="margin-top: 10rem;">
						<dl>
							<dd></dd>
							<dt>暂时还没有评论信息</dt>
						</dl>
					</div>
				</c:if>
				<div id="addmoredatas">
					<c:forEach items="${data.datalist }" var="kt">
						<jy:di key="${kt.userId }"
							className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
						<div class="consult_opinion">
							<div class="consult_opinion_left">
								<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
							</div>
							<div class="consult_opinion_right">
								<span>${u.nickname }：</span> <strong><fmt:formatDate
										value="${kt.crtDttm }" pattern="yyyy-MM-dd" /></strong>
								<div class="comment_content">
									${kt.content }
								</div>
								<div id="reply_${kt.id }" onclick="commentreply(this)"
									class="reply" data-authorid="${u.id}" divId="div_${kt.id }"
									opinionId="${kt.id }" parentId="${kt.id }"
									uname="${u.nickname }">回复</div>
								<div class="clear"></div>
								<c:forEach items="${huifuMap[kt.id]}" var="reply">
									<jy:di key="${reply.userId }"
										className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
									<div id="reply_${reply.id }" class="reply_opinion">
										<div class="reply_opinion_left">
											<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
										</div>
										<div class="reply_opinion_right">
											<span>${u.nickname }：</span> <strong><fmt:formatDate
													value="${reply.crtDttm }" pattern="yyyy-MM-dd" /></strong>
											<div class="reply_content">
												<c:out value="${reply.content }"></c:out>
											</div>
											<div class="reply1" onclick="commentreply(this)"
												data-authorid="${u.id}" divId="div_${reply.id }"
												opinionId="${kt.id }" parentId="${reply.id }"
												uname="${u.nickname }">回复</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
				<form name="pageForm" method="post">
					<ui:page
						url="${ctx}jy/comment/list?resId=${model.resId }&flags=${model.flags }&resType=${model.resType }"
						data="${data}" dataType="true" callback="addmoredatas" />
					<input type="hidden" class="currentPage" name="page.currentPage">
				</form>
				<div style="width: 100%; height: 1.5rem;"></div>

			</div>
		</div>
		<c:if test="${containsInput eq '1' }">
			<div class="my_publish">
				<div class="my_publish_left">
					<jy:di key="${_CURRENT_USER_.id }"
						className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
					<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
				</div>
				<div class="my_publish_right">
					<input type="text" class="publish" placeholder="有什么意见赶紧说出来吧！">
					<input type="button" class="publish_btn" value="发送">
					<div class="left1"></div>
				</div>
			</div>
		</c:if>
	</div>
	<form id="check_comment_form" method="post">
		<input type="hidden" value="${model.authorId }" name="authorId"
			id="authorId"> <input type="hidden" name="title"
			value="${model.title }" /> <input type="hidden" name="content"
			id="content" value="" /> <input type="hidden" name="resType"
			value="${model.resType }" /> <input type="hidden" name="resId"
			value="${model.resId }" /> <input type="hidden" name="parentId"
			id="parentId" value="" /> <input type="hidden" name="opinionId"
			id="opinionId" value="" />
		<ui:token></ui:token>
	</form>
</body>
<script type="text/javascript">
	require([ 'commentjs' ]);
</script>
</html>