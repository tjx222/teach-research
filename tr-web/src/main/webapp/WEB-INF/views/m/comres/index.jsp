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

<body >
<div class="return_1"></div>
<div class="cw_menu_wrap" >
	<div class="cw_menu_list" >
		<span class="cw_menu_list_top"></span>
		<div id="wrap2" class="cw_menu_list_wrap1"> 
			<div id="scroller">
			<!--<p>人教版</p>
			<p>鳄鱼版</p> -->
			</div>
		</div>
	</div> 
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_});"></span>
		<ul id="selectType">
			<li><a href="javascript:;" class="header_act">配套资源</a></li>
			<li><a href="${stc }jy/comres/index_type?_HS_=${empty param._HS_ ? 2 : param._HS_+1}">分类资源</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="mating_content">
			<form id="searchPlanForm" action="jy/comres/index" method="post">
				<div class="content_top">
					<div class="content_top_left">
						
					</div>
					<div class="content_top_right">
						<div class="version">
							<label>教材版本：</label>
							<div id="versionSelect">
								<span>${model.bookShortname}</span>
								<strong></strong>     
							</div>
						</div>
						<input type="hidden" name="bookId" value="${model.bookId}" />
						<input type="hidden" id="bookShortname" name="bookShortname"  value="${model.bookShortname}" />
						<input type="search"  name="planName" class="btn_search" placeholder="" value="${model.lessonName}"/>
						<input type="button" id="searchBtn" class="search" value="搜索" >
					</div>
				</div>
			</form>
		</div>
		<div class="mating_content_bottom" id="mating_li_wrap">
			<div id="scroller">
			    <c:if test="${not empty data.datalist }" >
			    <div id="pageContent">
			        <c:forEach var="kt" items="${data.datalist }">
			            <div class="mating_li">
							<div class="mating_li_left">
								<a href="jy/comres/viewlesson?lessPlan=${kt.id}">
								    <div class="mating_title_option">【<jy:dic key="${kt.subjectId }"/>】【${kt.bookShortname}】【<jy:dic key="${kt.gradeId }"/>${kt.fasciculeId == 176?'上':kt.fasciculeId == 177?'下':'全' }】</div>
									<div class="mating_title"><ui:sout value="${kt.lessonName }" length="20" needEllipsis="true"></ui:sout></div>
								</a>
							</div>
							<div class="mating_li_center">
								<h3><span></span>教案(${kt.jiaoanShareCount})</h3>
								<h3><strong></strong>课件(${kt.kejianShareCount})</h3>
								<h3><span></span>反思(${kt.fansiShareCount})</h3>
							</div>
							<div class="mating_li_right">
							<jy:di key="${kt.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
								<div class="mating_head">
									<ui:photo src="${u.photo }" ></ui:photo>
								</div>
								<div class="mating_head_info">
									<h4><span>
						  			${u.orgId  == sessionScope._CURRENT_SPACE_.orgId ? u.name : u.nickname} </span><span><fmt:formatDate value="${kt.shareTime}" pattern="yyyy-MM-dd"/></span></h4>
									<h4><jy:di key="${kt.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">
					  				 <span title="<jy:di var="area" key="${org.areaId }" className="com.tmser.tr.manage.org.service.AreaService">${area.name }${org.shortName }</jy:di>"><ui:sout value="${area.name }${org.shortName }" length="20" needEllipsis="true"></ui:sout></span>
					  	            </jy:di></h4>
								</div>
							</div>
				        </div>
			        </c:forEach>
			       </div>
			    </c:if>
			    <form name="pageForm" method="post">
						    <ui:page url="jy/comres/index" data="${data}" callback="addData" dataType="html"/>
						    <input type="hidden" class="currentPage" name="page.currentPage" >
			   </form>
				<div style="height:2rem;"></div>
				<c:if test="${empty data.datalist }" >
						<div class="content_k" style="margin:5rem auto;">
							<dl>
								<dd></dd>
								<dt>您的小伙伴还没有分享资源哟，稍后再来看看吧！</dt>
							</dl>
						</div>
					</c:if> 
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
