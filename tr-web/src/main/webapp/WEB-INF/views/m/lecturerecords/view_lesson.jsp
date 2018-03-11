<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查看教案"></ui:mHtmlHeader>
	<ui:require module="../m/check/js"></ui:require>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
</head>
<body>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1)"></span>查看教案
		<div class="more" onclick="more()"></div>
	</header>
	<c:choose>
		<c:when test="${not empty lessonPlans }">
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
											    <c:forEach var="lesson" items="${lessonPlans}" varStatus="st">
													<c:set value="${empty rcount ? 0 : 1 }" var="rcount"></c:set>
													<c:set value="${empty rescount ? 1 : rescount+1 }" var="rescount"></c:set>
													<c:choose>
														<c:when test="${rcount == 0}" >
															<li  data-resId="${lesson.resId }" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>' class="ul_li_act">
																第${lesson.hoursId}课时	
															</li>
														</c:when>
														<c:otherwise>
															<li  data-resId="${lesson.resId }" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>'>
																第${lesson.hoursId}课时
															</li>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="content_bottom1_center" style=" z-index: 1001;">
							<iframe id="iframe1" style="width:100%;height:100%;" frameborder="0" scrolling="no" src=""></iframe>
						</div>
					</div>
				</div>
			</section>
		</c:when>
		<c:otherwise>
			<div class="content_k" style="margin-top: 10rem;">
				<dl>
					<dd></dd>
					<dt>没有可查看的教案！</dt>
				</dl>
			</div>
		</c:otherwise>
	</c:choose>
</div>
</body>
	
<script type="text/javascript">
	require(["zepto",'check'],function($){	
		
	}); 
</script>
</html>