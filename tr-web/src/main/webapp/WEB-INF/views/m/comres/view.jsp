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
		<iframe id="commentBox" style="width: 100%;height: 47rem;border: none;" ></iframe>
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
									<ul>
									    <li class="ul_li_act" data-title="<ui:sout value='${data.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
													data-id="${data.planId }"  data-resId="${data.resId}" data-type="${data.planType}" data-userId="${data.userId}" data-name="${data.planName }"><c:choose><c:when test="${data.planType==0}">教案</c:when><c:when test="${data.planType==1}">课件</c:when><c:otherwise>反思</c:otherwise></c:choose></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="content_bottom1_center" style=" z-index: 1001;">
					<iframe id="iframe1" style="width:100%;height:100%;" frameborder="0" scrolling="no" src="jy/scanResFile?resId=${data.resId}"></iframe>
				</div>
				<div class="content_bottom1_right">
					<div class="content_list"> 
						<figure>
						  <span class="ck_list"></span>
						  <p>查看评论</p>
						</figure>
						<figure>
						  <a href="<ui:download filename='${data.planName}' resid='${data.resId}'></ui:download>"><span class="ck_down"></span>
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