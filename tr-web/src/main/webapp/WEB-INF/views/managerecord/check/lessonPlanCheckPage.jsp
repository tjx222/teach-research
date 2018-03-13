<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="管理记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/managerecord/css/managerecord.css" media="screen" />
	<ui:require module="../m/managerecord/js"></ui:require>	
</head>
<body>
<div class="semester_wrapper">
	<div class="semester_wrap" style="top:18%;">
		<span class="check_menu_top"></span>
		<div class="semester_wrap1">  
			<p data-term="0">上学期</p>
			<p data-term="1">下学期</p> 
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_ });"></span>
		<ul>
			<li><a class="${listType==0||empty listType?'com_header_act':''}" data-type="0">已查阅(${checkInfoCount})</a></li>
			<li><a class="${listType==1?'com_header_act':''}" data-type="1">查阅意见(${optionCount })</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section>
	    <form id="hiddenForm" action="${ctx }jy/managerecord/check/0?_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" method="post">
			<input id="hid_term" type="hidden" name="term" value="${term }">
			<input id="hid_listType" type="hidden" name="listType" value="${listType }">
	    </form>
		<div class="managerecord_bottom_wrap">
			<div class="managerecord_check_top" style="width: 90%;height:6rem;margin: 0 auto;border-bottom:0.083rem solid #D0D1D2; ">
				<div class="semester" style="margin-right:8rem;">
				     <c:if test="${empty term||term==0}">上学期</c:if> <c:if test="${term==1}">下学期</c:if>
				  <strong></strong>
				</div>
			</div>
			<div class="referCourseware_cont_box">
				<div class="referCourseware_cont_box1">
				    <c:forEach items="${checkInfoPageList.datalist }" var="checkInfo">
						<div class="courseware_ppt viewLesson"  data-id="${checkInfo.resId}">
							<div class="courseware_img_ja">教案</div>
							<h3 title="${checkInfo.title}"><ui:sout value="${checkInfo.title }" length="20" needEllipsis="true"></ui:sout></h3>
							<p><img src="${ctxStatic }/m/check/images/ppt.png" /></p> 
							<div class="consult"></div> 
						</div> 
					</c:forEach>
				</div>
				<c:if test="${empty checkInfoPageList.datalist }"><div class="content_k"><dl><dd></dd><dt>您还没有可查阅的教案，请稍后再来吧！</dt></dl></div></c:if>
			</div>
			<div class="referCourseware_content_box" >
				<div class="referCourseware_content_box1">
				   <c:forEach items="${checkMapList}" var="checkMap">
						<div class="referCourseware_content referCourseware_content_two">
							<p class="referCourseware_content_title">
								<span>课题名称：</span>
								<b data-resId="${checkMap.checkInfo.resId}" data-type="0">${checkMap.checkInfo.title }</b>
							</p>
							<c:forEach items="${checkMap.optionMapList }" var="optionMap">
							    <ul>
							    	<li>
							    		<span class="referCourseware_name">${optionMap.parent.username }：</span>
										<p class="referCourseware_text">&nbsp;${optionMap.parent.content }</p>
										<span class="referCourseware_time"><fmt:formatDate value="${optionMap.parent.crtTime }" pattern="yyyy-MM-dd"/></span>
										<div class="clear"></div>
									</li>
								</ul>
								<ul>
								   <!-- 回复 -->
								   <c:forEach items="${optionMap.childList }" var="child">
								        <jy:di key="${child.userId}" className="com.tmser.tr.uc.service.UserService" var="u"/>
										<li style="padding-left:5%;">
											<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
											<span class="referCourseware_name1">${child.username}：</span>
											<p class="referCourseware_text1">${child.content }</p>
											<span class="referCourseware_time"><fmt:formatDate value="${child.crtTime}" pattern="yyyy-MM-dd"/></span>
											<div class="clear"></div>
										</li>
									</c:forEach>
									</ul>
							</c:forEach>
						</div> 
					</c:forEach>
					<c:if test="${empty checkMapList}"><div class="content_k"><dl><dd></dd><dt>还没有查阅意见，请稍后再来吧！</dt></dl></div></c:if>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','checkkejian'],function($){	
	});  
</script>
</html> 