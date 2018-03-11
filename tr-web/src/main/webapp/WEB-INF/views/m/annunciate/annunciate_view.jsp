<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="通知公告"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/annunciate/css/annunciate.css" media="screen">
	<ui:require module="../m/annunciate/js"></ui:require>
</head> 
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header> 
		<span onclick="javascript:window.history.go(-1);"></span>通知公告
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="annunciate_content" id="annunciate_content">
			<div id="scroller">
				<div class="annunciate_content_width"> 
				    <c:if test="${ja.redTitleId!=0}">
						<div class="annun_cont_red">
							<h3>${jrt.title}</h3>
							<h4>${ja.fromWhere}</h4>
							<span></span>
						</div>
					</c:if>
					<div class="annun_cont">
						<h5><ui:sout value="${ja.title}"></ui:sout></h5>
						<ul>
							<li>发布时间：<fmt:formatDate value="${ja.crtDttm}" pattern="yyyy-MM-dd" /></li>
							<li>|</li>
							<jy:di key="${ja.crtId}" className="com.tmser.tr.uc.service.UserService" var="u">
							<li>作者：${u.name}</li></jy:di>
						</ul>
						<div class="annun_cont_c">
							<p>${ja.content}</p>
							<!-- <ul>
								<li>1、确定研究主题，制定研究方案；</li>
								<li>2、搜索资讯，召开家庭会议，确定研究方法；</li>
								<li>3、记录旅游过程中研究内容，搜集相关资料；</li>
								<li>4、回家后，整理资料，可通过绘画、游记、照片、微博、统计图表或研究报告等各种形式记录下自己的研究成果。</li>
							</ul> -->
						</div>
						<div class="annun_cont_b">
							<h6>
								<span class="span1"></span>
								<span class="span2">附件</span>
								<span class="span3"><c:if test="${!empty ja.attachs}">${attachSum}</c:if><c:if test="${empty ja.attachs}">0</c:if></span>
							</h6>
							<c:if test="${!empty ja.attachs}">
								<c:forEach items="${rList}" var="res">
									<div class="annun_fj">
									   <a href="<ui:download filename='${res.name}' resid='${res.id}'></ui:download>">
										<dl>
											<dd></dd>
											<dt title="${res.name}.${res.ext }"><ui:sout value="${res.name}.${res.ext }" length="18" needEllipsis="true" ></ui:sout></dt>
										</dl>
										</a>
									</div>
								</c:forEach>
							</c:if>
							<!-- <div class="annun_fj">
								<dl>
									<dd></dd>
									<dt>查阅听课记录...</dt>
								</dl>
							</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'public'],function($){	
	}); 
</script>
</html>