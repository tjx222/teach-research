<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="管理记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/managerecord/css/managerecord_l.css" media="screen" />
	<ui:require module="../m/managerecord/js"></ui:require>	
</head>
<body>
<div class="check_menu0_wrap">
	<div class="check_block_menu0">
		<span class="check_menu_top"></span>
		<div id="wrap0" class="check_menu_wrap10"> 
			<div id="scroller">
			     <p data-term="0">上学期</p>
			     <p data-term="1">下学期</p>
			</div>
		</div>
	</div>
</div>
<div class="check_menu2_wrap">
	<div class="check_block_menu2">
		<span class="check_menu_top"></span>
		<div id="wrap1" class="check_menu_wrap12"> 
			<div id="scroller">
			     <p data-phase="1">小学</p>
			     <p data-phase="3">初中</p>
			</div>
		</div>
	</div>
</div> 
<div class="check_menu_wrap">
	<div class="check_block_menu">
		<span class="check_menu_top"></span>
		<div id="wrap2" class="check_menu_wrap1"> 
			<div id="scroller">
			    <p data-subject="">全部学科</p>
			    <c:forEach items="${subjectList}" var="sub">
			         <p data-subject="${sub.id}">${sub.name}</p>
			    </c:forEach>
			</div>
		</div>
	</div>
</div>
<div class="check_menu1_wrap">
	<div class="check_block_menu1">
		<span class="check_menu_top"></span>
		<div id="wrap3" class="check_menu_wrap1"> 
			<div id="scroller">
			    <ui:relation var="list" type="xdToNj" id="${phaseId}">
			        <p data-grade="">全部年级</p>
					<c:forEach var="nj" items="${list}">
						<p data-grade="${nj.id}">${nj.name}</p>
					</c:forEach>
				</ui:relation>
			</div>
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>听课记录
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
	    <form action="jy/managerecord/lecDetail"  id="form">
	    	<input type="hidden"   name="term1" value="${term }">
			<input type="hidden"   name="subject1"  value="${subject }">
			<input type="hidden"  name="grade1" value="${grade }">
			<input type="hidden"  name="phaseId1" value="${phaseId}">
	    </form>
		<div class="check_content">
			<div class="check_content_top">
				<h3>筛选：</h3>
				<div class="check_content_block0">
					<label>学期</label>
					<span><c:if test="${term==0}">上学期</c:if><c:if test="${term==1}">下学期</c:if><strong></strong></span>  
				</div> 
				<%-- <div class="check_content_block">
					<label>学科</label>
					<span><c:choose><c:when test="${!empty subject}"><jy:dic key="${subject}"></jy:dic></c:when><c:otherwise>全部学科</c:otherwise></c:choose><strong></strong></span>
					
				</div> 
				<div class="check_content_block2">
					<label>学段</label>
					<span><c:if test="${phaseId==1}">小学</c:if><c:if test="${phaseId==3}">初中</c:if><strong></strong></span>  
				</div> 
				<div class="check_content_block1">
					<label>年级</label>
					<span><c:choose><c:when test="${!empty grade}"><jy:dic key="${grade}"></jy:dic></c:when><c:otherwise>全部年级</c:otherwise></c:choose><strong></strong></span>
				</div> --%>
			</div>
			<div class="lecturerecords_cont_bottom">
				<div id="scroller">
					<div class="content_bottom_width">
					    <c:if test="${not empty data.datalist }" >
					        <c:forEach var="kt" items="${data.datalist }">
						        <div class="courseware_ppt border1" data-id="${kt.id }" data-authorId="${kt.lecturepeopleId}" data-teacherId="${kt.teachingpeopleId}" data-resType="${kt.resType }">
									<div class="courseware_img_1">听课记录</div>
									<h3>
									     <c:choose>
									     	<c:when test="${fn:length(kt.topic)>13}">${fn:substring(kt.topic,0,12)}...</c:when>
									     	<c:otherwise>${kt.topic}</c:otherwise>
									     </c:choose>
									</h3>
									<p><img src="static/common/icon_m/base/lett.jpg"></p>
									<c:if test="${kt.isComment=='1'}"><div class="courseware_img_4" title="评论意见" ><c:if test="${kt.commentUp=='1'}"><span></span></c:if></div></c:if>
									<c:if test="${kt.isReply=='1'}"><div class="courseware_img_5" title="回复"><c:if test="${kt.replyUp=='1'}"><span></span></c:if></div></c:if>
									<c:choose><c:when test="${kt.type==0}"><div class="xiaonei"></div></c:when><c:otherwise><div class="xiaowai"></div></c:otherwise></c:choose>  
								</div>
							</c:forEach>
					    </c:if>
					</div>
					<c:if test="${empty data.datalist }" ><div class="content_k"><dl><dd></dd><dt>您还没有撰写“听课记录”哟！</dt></dl></div></c:if>
				</div>
			</div>
		</div>
	</section>
</div>
<!-- 评论意见 -->
<div class="opinions_comment_wrap" style="display: none;" >
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>评论意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_comment" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>

<!-- 回复 -->
<div class="opinions_comment_wrap1" style="display: none;" >
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>授课人回复</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_reply" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
</body>
<script type="text/javascript">
	require(['zepto','lecDetail'],function($){	
	});  
</script>
</html>
