<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅集备"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<ui:require module="../m/check/js"></ui:require>
</head>
<body>
<div class="opinions_comment_content" style="height:40rem;" >
	<div class="look_opinion_list1" id="look_opinion_list" style="background:#fff;height: 32rem;overflow: hidden;" >
		<div id="scroller">
			<c:if test="${empty data.datalist }">
				<div class="comment_k" style="margin-top: 5rem;">
					<dl>
						<dd></dd>
						<dt>暂时还没有查阅信息</dt>
					</dl>
				</div>
			</c:if>
			<div id="addmoredatas">
			<c:forEach items="${data.datalist }" var="co">
				<div class="consult_opinion">
				<jy:di key="${co.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
					<div class="consult_opinion_left">
						<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
					</div>
					<div class="consult_opinion_right">
						<span>${u.name }：</span>
						<strong><fmt:formatDate value="${co.crtTime }" pattern="yyyy-MM-dd"/></strong>
						<div class="comment_content1">
							<c:out value="${co.content }"></c:out>
						</div>
						<div id="reply_${co.id }" onclick="checkreply(this)" class="reply" divId="div_${co.id }" opinionId="${co.id }" parentId="${co.id }" uname="${u.name }">回复</div>
						<div class="clear"></div>
						<c:forEach items="${coMap[co.id]}" var="reply">
							<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
							<div id="reply_${reply.id }" class="reply_opinion">
								<div class="reply_opinion_left">
									<ui:photo src="${u.photo}" width="36" height="36"></ui:photo>
								</div>
								<div class="reply_opinion_right">
									<span>${u.name }：</span>
									<strong><fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/></strong>
									<div class="reply_content">
										<c:out value="${reply.content }"></c:out>
									</div>
									<div class="reply1" divId="div_${reply.id }" onclick="checkreply(this)" opinionId="${co.id }" parentId="${reply.id }" uname="${u.name }">回复</div>
								</div>
							</div>
						</c:forEach>
					</div> 
				</div>
			</c:forEach>
			</div>
			<form  name="pageForm" method="post">
				<ui:page url="${ctx}jy/check/infoIndexMjb?resId=${model.resId }&flags=${model.flags }&resType=${model.resType }" data="${data}" dataType="true" callback="addmoredatas"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
			</form> 
			<div style="width:100%;height:1.5rem;"></div>
		</div>
	</div>
	<div class="my_publish" style="bottom: 0rem;height: 8rem;">
		<div class="my_publish_index_m">
			<label>查阅意见</label>
			<div class="cyxx">
				<span spid="0">A+</span><span spid="1" class="jz">&nbsp;A</span><span spid="2" class="jz">B+</span>
				<span spid="3" class="jz">&nbsp;B</span><span spid="4" class="jz">&nbsp;C</span><span spid="5" class="jz">&nbsp;D</span>
			</div>	
		</div>
		<div class="clear"></div>
		<div class="my_publish_left">
			<jy:di key="${_CURRENT_USER_.id }" className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
			<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
		</div>
		<div class="my_publish_right">
			<input type="text" class="publish" placeholder="有什么意见赶紧说出来吧！">
			<input type="button" class="publish_btn" value="已查阅">
			<div class="left1" style="bottom: 2.3rem;"></div>
		</div>
	</div>
</div>
<form id="check_comment_form" method="post">
	<input type="hidden" name="content" id="content" value=""/>
	<input type="hidden" name="parentId" id="parentId" value=""/>
	<input type="hidden" name="opinionId" id="opinionId" value=""/>
</form>
<form id="submit_check_comment_form" method="post">
	<input type="hidden" id="level" name="level" value="0"/><!-- 查阅等级 -->
	<input type="hidden" name="term" value="${model.term }"/>
	<input type="hidden" name="resId" value="${model.resId }"/>
	<input type="hidden" name="resType" value="${model.resType }"/>
	<input type="hidden" name="authorId" value="${model.authorId }"/>
	<input type="hidden" name="gradeId" value="${model.gradeId }"/>
	<input type="hidden" name="subjectId" value="${model.subjectId }"/>
	<input type="hidden" name="title" value="${model.title }"/>
	<input type="hidden" name="flago" id="checkFlago" value=""/>
</form>
</body>
<script type="text/javascript">
	require(["zepto","checklist"],function(){	
	}); 
</script>
</html>