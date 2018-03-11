<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查看教研进度表"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/schoolactivity/css/teachscheduleview.css" media="screen" />
	<ui:require module="../m/schoolactivity/js"></ui:require>	
</head>
<body>
<div class="act_info_wrap">
	<div class="act_info">
		<div class="act_info_title">
		    <q></q>
			<h3>详情信息</h3>
			<span class="close"></span>
		</div>
		<div class="act_info_content">
			<div>
				<div class="act_info_content_w">
					<div class="act_info_left">
						<h3><span class="participant"></span><span>教研圈：${schoolTeachCircleName}</span><a class="par_head_r">查看</a></h3>
						<div class="par_head_float">
							<div> 
							    <c:forEach items="${stcolist}" var="stco">  
									<c:if test="${stco.state==1}">
								        <div class="head_independent1"><span>${stco.orgName }</span><span class="s1">待接受</span></div></c:if>
								    <c:if test="${stco.state==2}">
								        <div class="head_independent1"><span>${stco.orgName }</span><span class="s2">已接受</span></div></c:if>
								    <c:if test="${stco.state==3}">
								    	<div class="head_independent1"><span>${stco.orgName }</span><span class="s3">已拒绝</span></div></c:if>
								    <c:if test="${stco.state==4}">
								    	<div class="head_independent1"><span>${stco.orgName }</span><span class="s4">已退出</span></div></c:if>
								    <c:if test="${stco.state==5}">
								    	<div class="head_independent1"><span>${stco.orgName }</span><span class="s2">已恢复</span></div></c:if>
								</c:forEach>
							</div>
						</div>
						<jy:di key="${teachSchedule.crtId}" className="com.tmser.tr.uc.service.UserService" var="u">
						<h3><span class="cyfw"></span><span>学校：<jy:di key="${u.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">${org.name }</jy:di></span></h3>
						<h3><span class="fqr"></span><span>作者：${u.name }</span></h3></jy:di>
						<h3><span class="sj"></span><span>发布时间：<fmt:formatDate value="${teachSchedule.releaseTime}" pattern="yyyy-MM-dd" /></span></h3>
					</div>
				</div>
			</div>  
		</div>
		<div class="left"style="bottom:10rem;"></div> 
	</div>
</div>
<div class="mask"></div> 
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>教研进度表详情
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="content"> 
			<div class="content_bottom1">
				<div class="show"> 
				</div> 
				<div class="content_bottom1_center" style=" z-index: 1001;">
					<iframe id="view" style="width:100%;height:100%;" frameborder="0" scrolling="no" src="jy/scanResFile?resId=${teachSchedule.resId}" ></iframe>
				</div>
				<div class="content_bottom1_right">
					<div class="content_list">
						<figure > 
						  <span class="xq_list"></span>
						  <p>详情信息</p>
						</figure>
					</div>
				</div>
			</div> 
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto","teachschedule"],function(){	
	}); 
</script>
</html>