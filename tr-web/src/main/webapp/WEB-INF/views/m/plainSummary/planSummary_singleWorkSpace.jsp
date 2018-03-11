<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅计划总结"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/common/css/index_m.css" media="screen" />
	<link rel="stylesheet" href="${ctxStatic }/m/plainSummary/css/ps_check.css" media="screen" />  
	<ui:require module="../m/plainSummary/js"></ui:require>
</head> 
<body>
<div class="semester_wrapper">
	<div class="semester_wrap">
		<span class="check_menu_top"></span>
		<div class="semester_wrap1" 
			data-workSpaceId="${us.id }"
			data-term="${term}" >			
			<p data-val="" class="${empty term ?'act':''}">全部</p>
			<p data-val="0" class="${term==0?'act':''}">上学期</p>
			<p data-val="1" class="${term==1?'act':''}">下学期</p> 
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅教师计划总结
		<div class="more" onclick="more()"></div>
	</header>
	<section> 		
		<div class="check_content">
			<div class="check_tch_wrap">			   
				<div class="check_content_left">
					<ui:photo src="${user.photo }" width="60" height="65"></ui:photo>
				</div>
				<div class="check_content_right">
					<div class="check_content_right_name">
						${user.name }
					</div> 
					<div class="check_content_right_option">
						<span><jy:dic key="${us.gradeId }"></jy:dic><jy:dic key="${us.subjectId }"></jy:dic></span>
						<span>撰写：${total.plainNum + total.summaryNum}课 </span>
						<span>提交：${total.plainSubmitNum + total.summarySubmitNum}课</span>
						<span>已查阅：${total.plainCheckedNum + total.summaryCheckedNum}课</span>						
						<div class="semester">							
							<c:if test="${empty term}">全部</c:if>
						    <c:if test="${term==0}">上学期</c:if>
							<c:if test="${term==1}">下学期</c:if>
							<strong></strong>
						</div>
						<span style="float:right;font-size:1.333rem;">学期：</span>
					</div>					
				</div>
				<div class="clear"></div>
				<div class="nav_tab">
				    <ul id="selectType">
				    	<input type="hidden" name="category" id="category" value="${empty category ? 1 : category }" />
						<li><a ${category==1 ? 'class="header_act"':'' } data-category="1">个人计划</a></li>
						<li><a ${category==2 ? 'class="header_act"':'' } data-category="2">个人总结</a></li>
				    </ul>
	         	</div>
			</div>			
			<div class="content_bottom" id="check_c_b" style="top:18.1rem;">
				<div id="scroller" >										
					<div class="content_k" style="margin-top: 5rem; display:none;" data-plan="${empty planItems}" data-summary="${empty summaryItems}">
						<dl>
							<dd></dd>
							<dt>暂时还没有查阅信息</dt>
						</dl>
					</div>
					<div class="content_bottom_width njjh"> 
						<c:if test="${!empty planItems }">
							<c:forEach items="${planItems }" var="res" varStatus="st">
								<div class="courseware_ppt">
									<div class="courseware_img_1">个人计划</div>
									<a href="${ctx }/jy/planSummaryCheck/userSpace/${us.id }/planSummary/${res.id}">
										<h3><ui:sout value="${res.title }" escapeXml="false" length="36" needEllipsis="true" /></h3>
										<p><img src="${ctxStatic }/m/plainSummary/images/word.png" /></p>
										<c:if test="${res.checkStatus == 1 }">
											<div class="consult"></div>
										</c:if>
									</a>									
								</div> 
						    </c:forEach>
						</c:if>
					</div>
					<div class="content_bottom_width njzj" style="display:none;"> 
						<c:if test="${!empty summaryItems }">
							<c:forEach items="${summaryItems }" var="res" varStatus="st">
								<div class="courseware_ppt">
									<div class="courseware_img_1">个人总结</div>
										<a href="${ctx }/jy/planSummaryCheck/userSpace/${us.id }/planSummary/${res.id}">
										<h3><ui:sout value="${res.title }" escapeXml="false" length="36" needEllipsis="true" /></h3>
										<p><img src="${ctxStatic }/m/plainSummary/images/word.png" /></p>
										<c:if test="${res.checkStatus == 1 }">
											<div class="consult"></div>
										</c:if>
									</a>									
								</div> 
						    </c:forEach>
						</c:if>						
					</div>
					<div style="height:2rem;"></div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'planSummary_singleWorkSpace'],function($){	
	}); 
</script>
</html>
