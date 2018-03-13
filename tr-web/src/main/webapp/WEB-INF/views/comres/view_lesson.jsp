<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="同伴资源"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/comres/css/comres.css" media="screen">
	<ui:require module="../m/comres/js"></ui:require>
</head>
<body>
<div class="look_opinion_list_wrap">
	<div class="look_opinion_list">
		<div class="look_opinion_list_title">
		    <q></q>
			<h3>课件1</h3>
			<span class="close"></span>
		</div>
		<div class="look_opinion_list_content">
		    <jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
		    <div class="look_option1"> 
				<span></span>学校：<jy:di key="${u.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">${org.name }</jy:di>
			</div>
			<div class="look_option"> 
				<span></span>作者：${u.name }
			</div>
			<div class="look_option"> 
				<strong></strong>提交日期：<fmt:formatDate value="${data.shareTime}" pattern="yyyy-MM-dd" />
			</div>
			</jy:di>
		</div>
		<div class="look_opinion_list_title1">
		    <q></q>
			<h3>评论意见列表</h3> 
		</div>
		<iframe id="commentBox" style="width: 100%;height: 26rem;border: none;" ></iframe>
		<div class="left"style="bottom:22rem;"></div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>同伴资源
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1">
			<div class="show">
			</div>
				<div class="content_bottom1_left">
					 <h3></h3>
					 <div class="content_bottom1_left1_wrap">
					 	 <div class="content_bottom1_left1" id="content_bottom1_left1">
						  	 <div id="scroller">
								<div class="cour" id="comresView">
								    <!-- <div class="cour_name" ><span>教案</span></div> -->
									<ul>
									    <c:set value="0" var="fstpcount"></c:set>
									    <c:set value="0" var="kjcount"></c:set>
										<c:forEach var="lesson" items="${lessonList}" varStatus="status">
											<c:choose>
												<c:when test="${(lesson.planId eq planId)||((status.index eq 0)&&(empty planId))}">
													<li class="ul_li_act" title="<ui:sout value='${lesson.planName }' escapeXml='true'></ui:sout>" data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
													data-id="${lesson.planId }"  data-resId="${lesson.resId}" data-type="${lesson.planType}" data-userId="${lesson.userId}" data-name="${lesson.planName }">
														<c:if test="${lesson.planType == 0 }">第${lesson.hoursId}课时</c:if>
														<c:if test="${lesson.planType == 1 }"><c:set value="${kjcount+1 }" var="kjcount"></c:set>课件${kjcount}</c:if>
														<c:if test="${lesson.planType == 2 }"><c:set value="${fstpcount+1 }" var="fstpcount"></c:set>反思${fstpcount}</c:if>
													</li>
												</c:when>
												<c:otherwise>
													<li  title="<ui:sout value='${lesson.planName }' escapeXml='true'></ui:sout>" data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
														data-resId="${lesson.resId}" data-id="${lesson.planId }" data-type="${lesson.planType}" data-userId="${lesson.userId}" data-name="${lesson.planName }">
														<c:if test="${lesson.planType == 0 }">第${lesson.hoursId}课时</c:if>
														<c:if test="${lesson.planType == 1 }"><c:set value="${kjcount+1 }" var="kjcount"></c:set>课件${kjcount}</c:if>
														<c:if test="${lesson.planType == 2 }"><c:set value="${fstpcount+1 }" var="fstpcount"></c:set>反思${fstpcount}</c:if>
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
				<div class="content_bottom1_right">
					<div class="content_list"> 
						<figure>
						  <span class="ck_list"></span>
						  <p>查看评论</p>
						</figure>
						<figure>
						  <a id="dowloadpt" href=""> <span class="ck_down"></span>
						  <p>下载</p></a>
						</figure>
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