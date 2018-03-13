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
<form id="msglist_form" action="${ctx }/jy/companion/companions/compSendMsg/${companionId}" method="post">
	<input type="hidden" name="msglist" value="true">
</form>

<c:if test="${companionId==0 || companionId==''  }">
	<c:if test="${empty  data.datalist }">
		<div class="content_k" style="margin-top: 10rem;">
			<dl>
				<dd></dd>
				<dt>很抱歉，没有找到任何互通消息！</dt>
			</dl>
		</div>
	</c:if>
</c:if>
<c:if test="${companionId!=0 }">
<jy:di key="${_CURRENT_USER_.id }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
<jy:di key="${companionId }" className="com.tmser.tr.uc.service.UserService" var="u1"></jy:di>
<h3 class="companion_content_r_r_h3">${u1.name }<span class="span2" userid="${u1.id }"></span><span class="span1"></span></h3>
<div class="chat_content" id="chat_content">
	<div id="scroller">
		<form  name="pageForm" method="post">
			<ui:page url="${ctx}jy/companion/companions/compSendMsg/${companionId}" data="${data}" dataType="true" callback="addmoredatas"/>
			<input type="hidden" name="userIdReceiver" value="${u1.id }">
			<input type="hidden" class="currentPage" name="currentPage">
		</form> 
		<div id="addmoredatas">
		<c:forEach items="${data.datalist }" var="comp">
			<div class="chat_content_div">
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
					<ui:photo src="${u.photo}" width="57" height="57"></ui:photo>
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
			</div>
		</c:forEach>
		</div>
	</div>
</div>
<div class="send_message" style="bottom: -0.7rem;">
	<form id="send_message_form" method="post" action="">
		<input type="hidden" name="userIdReceiver" value="${u1.id }">
		<input type="hidden" name="userNameReceiver" value="${u1.name }">
		<input id="attachment1" type="hidden" name="attachment1" >
		<input id="attachment1Name" type="hidden" name="attachment1Name" >
		<input type="text" class="send_message_txt" name="message" maxlength="500">
		<div style="margin-left: 2%;float:left;width:6rem;">
			<div style="position: relative;top:0.7rem;width: 6rem;overflow: hidden;z-index: 1;opacity: 0;cursor:pointer;">
			<ui:upload_m fileType="doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar,jpg,jpeg,gif,png,mp3,mp4,wma,rm,rmvb,flV,swf,avi" 
			fileSize="50" callback="afterUpload" beforeupload="beforeUpload" relativePath="companion/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload_m>					
			</div>
			<input type="button" class="add_fj_btn" value="添加附件" style="position: relative;top:-1.7rem;z-index: 0;">
		</div>  
		<input type="button" class="fs_btn" value="发送">
	</form>
</div>
</c:if>
</body>
<script type="text/javascript">
	require(["zepto","iscroll","msg"],function(){ 
		var chat_content = new IScroll('#chat_content',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true
      	});
		var hei = document.getElementById('scroller').offsetHeight;
		chat_content.scrollTo(0,-hei,0.1, IScroll.utils.ease.circular);
	});
</script>
</html>