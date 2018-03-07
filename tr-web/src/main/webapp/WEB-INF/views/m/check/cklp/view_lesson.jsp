<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<ui:require module="../m/check/js"></ui:require>
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
		<span onclick="javascript:window.history.go(-1);"></span>
		<c:choose><c:when test="${type==0}">查阅教案</c:when><c:when test="${type==1}">查阅课件</c:when><c:otherwise>查阅反思</c:otherwise></c:choose>
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
											<c:if test="${lesson.planType == type }">
											<c:set value="${empty rcount ? 0 : 1 }" var="rcount"></c:set>
											<c:set value="${empty rescount ? 1 : rescount+1 }" var="rescount"></c:set>
											<c:choose>
												<c:when test="${rcount == 0}" >
													<li  data-resId="${lesson.resId }" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>' class="ul_li_act">
													<c:if test="${type == 0 }">
														<c:choose>
															<c:when test="${lesson.hoursId eq '-1'}">不分课时</c:when>
															<c:when test="${lesson.hoursId eq '0'}">简案</c:when>
															<c:otherwise>第<ui:sout value="${lesson.hoursId}" length="44" needEllipsis="true"/>课时</c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${type == 1 }">	课件${rescount}	</c:if>
													<c:if test="${type == 2 }">	反思${rescount}	</c:if>
													</li>
												</c:when>
												<c:otherwise>
													<li  data-resId="${lesson.resId }" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>'>
														<c:if test="${type == 0 }">
															<c:choose>
																<c:when test="${lesson.hoursId eq '-1'}">不分课时</c:when>
																<c:when test="${lesson.hoursId eq '0'}">简案</c:when>
																<c:otherwise>第<ui:sout value="${lesson.hoursId}" length="44" needEllipsis="true"/>课时</c:otherwise>
															</c:choose>
														</c:if>
														<c:if test="${type == 1 }">	课件${rescount}	</c:if>
														<c:if test="${type == 2 }">	反思${rescount}	</c:if>
													</li>
												</c:otherwise>
											</c:choose>
											</c:if>
										</c:forEach>
										<!-- <li class="ul_li_act">课件1</li>
										<li>课件2</li> -->
									</ul>
								</div>
								<c:if test="${type!=0 }">
								    <div class="cour" id="ja_fj">
										<div class="cour_name">
											<span>教案</span>
										</div>
										<ul>
											<c:forEach var="lesson" items="${lessonList}" varStatus="status">
												<c:if test="${lesson.planType != type && lesson.planType == 0 }">
												<li data-resId="${lesson.resId}" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>'>
													<%-- <dd ><ui:icon ext="${lesson.planType == 1?'ppt':'doc' }" width="26" height="25" title="${lesson.planName }"></ui:icon></dd> --%>
													<c:choose>
														<c:when test="${lesson.hoursId eq '-1'}">不分课时</c:when>
														<c:when test="${lesson.hoursId eq '0'}">简案</c:when>
														<c:otherwise>第<ui:sout value="${lesson.hoursId}" length="44" needEllipsis="true"/>课时</c:otherwise>
													</c:choose>	
												</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</c:if>
								<c:if test="${type!=1}">
									<div class="cour" id="kj_fj">
										<div class="cour_name">
											<span>课件</span>
										</div>
										<ul>
											<c:forEach var="lesson" items="${lessonList}" varStatus="status">
												<c:if test="${lesson.planType != type && lesson.planType == 1 }">
												<li data-resId="${lesson.resId}" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>'>
													<%-- <dd ><ui:icon ext="${lesson.planType == 1?'ppt':'doc' }" width="26" height="25" title="${lesson.planName }"></ui:icon></dd> --%>
													<c:set value="${empty kjcount ? 1 : kjcount+1 }" var="kjcount"></c:set> 课件${kjcount}	
												</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</c:if>
								<c:if test="${type!=2 }">
									<div class="cour" id="fs_fj">
										<div class="cour_name">
											<span>反思</span>
										</div>
										<ul>
											<c:forEach var="lesson" items="${lessonList}" varStatus="status">
												<c:if test="${lesson.planType != type && lesson.planType == 2 }">
												<li data-resId="${lesson.resId}" data-type="${lesson.planType}" data-title="${lesson.planName}" data-id="${lesson.infoId}" data-time='<fmt:formatDate value="${data.submitTime }" pattern="yyyy-MM-dd"/>'>
													<%-- <dd ><ui:icon ext="${lesson.planType == 1?'ppt':'doc' }" width="26" height="25" title="${lesson.planName }"></ui:icon></dd> --%>
													<c:set value="${empty fscount ? 1 : fscount+1 }" var="fscount"></c:set>反思${fscount}	
												</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</c:if>
								<!-- <div class="cour">
									<div class="cour_name">
										<span>反思</span>
									</div>
									<ul>
										<li>第1课时</li>
										<li>第2课时</li>
									</ul>
								</div>  -->
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
	require(["zepto",'check'],function($){	
	}); 
</script>
</html>