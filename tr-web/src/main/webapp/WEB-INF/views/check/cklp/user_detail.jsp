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
<div class="semester_wrapper">
	<div class="semester_wrap">
		<span class="check_menu_top"></span>
		<div class="semester_wrap1" data-type="${type}" data-userId="${userId }" data-grade="${grade}" data-subject="${subject}" data-fasciculeId="${fasciculeId }" data-wc="${writeCount }" data-sc="${submitCount }" data-cc="${checkCount}">  
			<!-- <p>全部</p> -->
			<p data-val="0" class="${fasciculeId==0?'act':''}">上学期</p>
			<p data-val="1" class="${fasciculeId==1?'act':''}">下学期</p> 
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 :param._HS_ });"></span>
		<c:choose><c:when test="${type==0}">查阅教案</c:when><c:when test="${type==1}">查阅课件</c:when><c:otherwise>查阅反思</c:otherwise></c:choose>
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="check_content">
			<div class="check_tch_wrap">
			     <jy:di key="${userId }" className="com.tmser.tr.uc.service.UserService" var="u">
				<div class="check_content_left">
					<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
				</div>
				<div class="check_content_right">
					<div class="check_content_right_name">
						${u.name }
					</div> 
					<div class="check_content_right_option">
						<span>撰写：${writeCount }课 </span>
						<span>提交：${submitCount}课</span>
						<span>已查阅：${checkCount}课</span>
						<div class="semester">
						    <c:if test="${fasciculeId==0}">上学期</c:if>
							<c:if test="${fasciculeId==1}">下学期</c:if>
							<strong></strong>
						</div>
					</div>
				</div> </jy:di>
				<div class="clear"></div>
				<div class="nav_tab">
					<c:if test="${type!=0&&type!=1}">
					    <ul id="selectType">
							<li><a class="header_act">课后反思</a></li>
							<li><a href="jy/check/lesson/3/tch/other/${u.id}?fasciculeId=${fasciculeId }&wc=${writeCount }&sc=${submitCount }&cc=${checkCount }&grade=${grade}&subject=${subject}&_HS_=${empty param._HS_ ? 2 :param._HS_+1 }">其他反思</a></li>
					    </ul>
			        </c:if>
		         </div>
			</div>
			
			<div class="content_bottom" id="check_c_b">
				<div id="scroller">
					<div class="content_bottom_width"> 
						<c:choose>
						<c:when test="${!empty resList }">
							<c:forEach items="${resList }" var="res" varStatus="st">
								<div class="courseware_ppt">
									<c:choose><c:when test="${type==0}"><div class="courseware_img_ja">教案</div></c:when><c:when test="${type==1}"><div class="courseware_img_1">课件</div></c:when><c:otherwise><div class="courseware_img_fs">反思</div></c:otherwise></c:choose>
									<a href="jy/check/lesson/${type}/tch/${userId}/view?fasciculeId=${fasciculeId }&lesInfoId=${res.id}&zx=${writeCount}&tj=${submitCount}&cy=${checkCount}"><h3><ui:sout value="${res.lessonName }" length="16" needEllipsis="true" /></h3>
									<p><img src="${ctxStatic }/m/check/images/ppt.png" /></p></a>
									<c:if test="${not empty checkIds[res.id] }">
										<div class="consult"></div>
									</c:if>
								</div> 
						    </c:forEach>
						</c:when></c:choose>
					</div>
					<c:if test="${empty resList }">
					    <!-- 无文件 -->
						<c:if test="${type==0}">
						       <div class="content_k" style="margin:5rem auto;"><dl><dd></dd><dt>该教师还未提交“教案”，您可以提醒他提交，或者查阅其他教师的工作 !</dt></dl></div>
						</c:if>
						<c:if test="${type==1}">
						        <div class="content_k" style="margin:5rem auto;"><dl><dd></dd><dt>该教师还未提交“课件”，您可以提醒他提交，或者查阅其他教师的工作 !</dt></dl></div>
						</c:if>
						<c:if test="${type==2}">
						        <div class="content_k" style="margin:5rem auto;"><dl><dd></dd><dt>该教师还未提交“课后反思”，您可以提醒他提交，或者查阅其他教师的工作 !</dt></dl></div>
						</c:if>
					</c:if>
					<div style="height:2rem;"></div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'js'],function($){	
	}); 
</script>
</html>