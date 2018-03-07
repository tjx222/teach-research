<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<c:set var="jihuazongjie" value="${roleId==1375?'学科': (roleId==1374? '年级' : '备课') }" />
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅${jihuazongjie }计划总结"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/plainSummary/css/ps_check.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<ui:require module="../m/plainSummary/js" />
</head>
<body>
<div class="look_opinion_list_wrap">
	<div class="look_opinion_list">
		<div class="look_opinion_list_title">
		    <q></q>
			<h3 id="planName_check">${ps.title}</h3>
			<span class="close"></span>
		</div>
		<div class="look_opinion_list_content" id="lessonCrt_message">
			<div class="look_option"> 
				<jy:di key="${ps.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
				<span></span>作者：${u.name}
			</div>
			<div class="look_option" id="submitTime"> 
				<strong></strong>提交日期：<fmt:formatDate value="${ps.submitTime }" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="look_opinion_list_title1">
		    <q></q>
			<h3>查阅意见列表</h3> 
		</div>
		<iframe id="iframe_checklist" style="border:none;overflow:hidden;width:100%;height:30rem;" ></iframe>
		<input type="hidden" id="checklistobj" term="${ps.term}" gradeId="${ps.gradeId}" subjectId="${ps.subjectId}" resType="${ps.category==1 || ps.category==3 ? 8 : 9}" authorId="${ps.userId}" resId="${ps.id}" title="<ui:sout value='${ps.title }' encodingURL='true' escapeXml='true'></ui:sout>"/>
		<div class="left" style="bottom:24rem;"></div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅${jihuazongjie}计划总结
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1">
			<div class="show">
			</div>
				<div class="content_bottom1_left">
					 <h3></h3>
					 <div class="content_bottom1_left1_wrap" id="viewPlan">
					 	 <div class="content_bottom1_left1" id="content_bottom1_left1">
						  	 <div id="scroller">
								<div class="cour">
									<div class="cour_name" >
										<span>${jihuazongjie}${(ps.category==3)?'计划':'总结'}</span>
									</div>
									<ul>
										<li  data-resId="${ps.contentFileKey}" data-type="${ps.category==1 || ps.category==3 ? 8 : 9}" data-title="${ps.title }" data-id="${ps.id}" data-time='<fmt:formatDate value="${ps.submitTime }" pattern="yyyy-MM-dd"/>' class="ul_li_act">
											查阅	
										</li>									
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
<script type="text/javascript">
	require(["zepto",'view_multiWorkSpace'],function($){	
	}); 
</script>
</body>
</html>
