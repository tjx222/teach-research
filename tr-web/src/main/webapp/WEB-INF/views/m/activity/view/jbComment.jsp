<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="参与主研"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity_ztyt.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="comment_list">
	<h3><span class="comment"></span>评论列表</h3> 
		<div class="view_comments_content1" id="comment_list">
			<div id="scroller">
				<c:if test="${empty data.datalist }">
					<div class="comment_k" style="margin-top: 15rem;">
						<dl>
							<dd></dd>
							<dt>暂时还没有评论信息</dt>
						</dl>
					</div>
				</c:if>
				<div id="addmoredatas">
				<c:forEach items="${data.datalist }" var="kt">
					<jy:di key="${kt.crtId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
					<div class="consult_comments">
						<div class="consult_comments_left">
							<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
						</div>
						<div class="consult_comments_right">
							<span>${u.nickname }：</span>
							<strong><fmt:formatDate value="${kt.crtDttm }" pattern="yyyy-MM-dd"/></strong>
							<div class="comments_content">
								<c:out value="${kt.content }"></c:out>
							</div>
							<div class="comments_reply" onclick="commentreply(this)" id="reply_${kt.id }" divId="div_${kt.id }" opinionId="${kt.id }" parentId="${kt.id }" uname="${u.nickname }">回复</div>
							<div class="clear"></div>
							<c:forEach items="${huifuMap[kt.id]}" var="reply">
								<jy:di key="${reply.crtId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
								<div class="reply_comments">
									<div class="reply_comments_left">
										<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
									</div>
									<div class="reply_comments_right">
										<span>${u.nickname }：</span>
										<strong><fmt:formatDate value="${reply.crtDttm }" pattern="yyyy-MM-dd"/></strong>
										<div class="clear"></div>
										<div class="reply_content">
											<c:out value="${reply.content }"></c:out>
										</div>
										<div class="clear"></div>
										<div class="comments_reply1" onclick="commentreply(this)" divId="div_${reply.id }" opinionId="${kt.id }" parentId="${reply.id }" uname="${u.nickname }">回复</div>
									</div>
								</div>
							</c:forEach>
						</div> 
					</div> 
				</c:forEach>
				</div>
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/comment/list?resId=${model.resId }&flags=${model.flags }&resType=${model.resType }" data="${data}" dataType="true" callback="addmoredatas"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
				</form> 
				<div style="width:100%;height:1.5rem;"></div>
			</div>
		</div> 
		<div class="right"></div>
		<c:if test="${containsInput=='1' }">
			<div class="my_publish" style="bottom: 0rem;">
				<div class="my_publish_wrap">
					<div class="my_publish_left">
						<jy:di key="${_CURRENT_USER_.id }" className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
						<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
					</div>
					<div class="my_publish_right">
						<input type="text" class="publish" placeholder="有什么意见赶紧说出来吧！">
						<input type="button" class="publish_btn" value="发送">
						<div class="left1"></div>
					</div>
				</div>
			</div>
		</c:if>
	</div> 
<form id="jb_comment_form" method="post">
	<ui:token></ui:token>
	<input type="hidden" name="title" value="${model.title }"/>
	<input type="hidden" name="authorId" value="${model.authorId }"/>
	<input type="hidden" name="resType" value="${model.resType }"/>
	<input type="hidden" name="resId" value="${model.resId }"/>
	<input type="hidden" name="parentId" id="parentId"/>
	<input type="hidden" name="opinionId" id="opinionId"/>
	<input type="hidden" name="content" id="content_hidden" value=""/>
</form>
</body>
<script type="text/javascript">
	require(['jbcomment'],function(){
	}); 
</script>
</html>