<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅课件"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/m/managerecord/css/managerecord.css" media="screen" />
	<ui:require module="../m/managerecord/js"></ui:require>	
</head>
<body>
<div class="look_opinion_list_wrap">
	<div class="look_opinion_list">
		<div class="look_opinion_list_title">
		    <q></q>
			<h3 id="lessonName_check">课件1</h3>
			<span class="close"></span>
		</div>
		<div class="look_opinion_list_content" id="lessonCrt_message">
			<div class="look_option"> 
			    <jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
				<span></span>作者：${u.name}
			</div>
			<div class="look_option" id="submitTime"> 
				<strong></strong>提交日期：<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="look_opinion_list_title1">
		    <q></q>
			<h3>查阅意见列表</h3> 
		</div>
		<iframe id="iframe_checklist" style="border:none;overflow:hidden;width:100%;height:30rem;" ></iframe>
		<input type="hidden" id="checklistobj" term="${data.termId}" gradeId="${data.gradeId}" subjectId="${data.subjectId}" resType="${type}" authorId="${data.userId}" resId="${data.id}" title="<ui:sout value='${data.lessonName }' encodingURL='true' escapeXml='true'></ui:sout>"/>
		<div class="left" style="bottom:24rem;"></div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅课件
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1">
			<div class="show">
			</div>
				<div class="content_bottom1_left">
					 <h3></h3>
					 <div class="content_bottom1_left1_wrap" id="viewLesson">
					 	 <div class="content_bottom1_left1" id="content_bottom1_left1">
						  	 <div id="scroller">
								<div class="cour">
									<div class="cour_name" ></div>
									<ul>
									    <c:forEach var="lesson" items="${lessonList}" varStatus="st">
											<c:set value="${empty rcount ? 0 : 1 }" var="rcount"></c:set>
											<c:set value="${empty rescount ? 1 : rescount+1 }" var="rescount"></c:set>
											<c:choose>
												<c:when test="${rcount == 0}" >
													<li  data-resId="${lesson.resId }" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>' class="ul_li_act">
													课件${rescount}
													</li>
												</c:when>
												<c:otherwise>
													<li  data-resId="${lesson.resId }" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>'>
														课件${rescount}	
														
													</li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<!-- <li class="ul_li_act">课件1</li>
										<li>课件2</li> -->
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="content_bottom1_center" style=" z-index: 1001;">
					<iframe id="iframe1" style="width:100%;height:100%;" frameborder="0" scrolling="no" src=""></iframe>
				</div>
				<div class="content_bottom1_right">
					<div class="content_list" style="height:13rem;">
						<div class="list_img"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'view'],function($){	
	}); 
</script>
</html>