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
			<li><a class="${listType==0?'com_header_act':''}" data-type="0">撰写(${count})</a></li>
			<li><a class="${listType==1?'com_header_act':''}" data-type="1">分享(${shareCount })</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section>
	    <form id="hiddenForm" action="${ctx }jy/managerecord/planDetail?_HS_=${empty param._HS_ ? 2 :param._HS_+1 }" method="post">
			<input id="hid_term" type="hidden" name="term" value="${term }">
			<input id="hid_listType" type="hidden" name="listType" value="${listType }">
	    </form>
		<div class="managerecord_bottom_wrap">
			<div class="managerecord_check_top" style="width: 90%;height:6rem;margin: 0 auto;border-bottom:0.083rem solid #D0D1D2; ">
				<div class="managerecord_slide">
					<span class="mana_act" data-type="0">计划(${listCount })</span><span data-type="1">总结(${zCount })</span>
				</div>
				<div class="semester" style="margin-right:8rem;">
				   <c:if test="${empty term||term==0}">上学期</c:if> <c:if test="${term==1}">下学期</c:if>
				  <strong></strong>
				</div>
			</div>
			<div class="referCourseware_cont_box" id="planbox">
				<div class="referCourseware_cont_box1">
				    <c:forEach var="ps" items="${list }">
						<div class="courseware_ppt" data-id="${ps.contentFileKey}">
							<div class="courseware_img_fs">计划</div>
							<h3 title="${ps.title}"><ui:sout value="${ps.title}" escapeXml="true" length="20" needEllipsis="true"/></h3>
							<p><img src="${ctxStatic }/m/check/images/ppt.png" /></p> 
						</div> 
					</c:forEach>
				</div>
				<c:if test="${empty list }"><div class="content_k"><dl><dd></dd><dt>您还没有可撰写的计划总结，请稍后再来吧！</dt></dl></div></c:if>
			</div>
			<div class="referCourseware_cont_sum_box" style="display:none;">
				<div class="referCourseware_cont_sum_box1">
				    <c:forEach var="ps" items="${zlist }">
						<div class="planSummary_ppt" data-id="${ps.contentFileKey}">
							<div class="courseware_img_fs">总结</div>
							<h3 title="${ps.title}"><ui:sout value="${ps.title}" escapeXml="true" length="20" needEllipsis="true"/></h3>
							<p><img src="${ctxStatic }/m/check/images/ppt.png" /></p> 
						</div> 
					</c:forEach>
				</div>
				<c:if test="${empty zlist }"><div class="content_k"><dl><dd></dd><dt>您还没有可分享的计划总结，请稍后再来吧！</dt></dl></div></c:if>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','plandetail'],function($){	
	});  
</script>
</html> 