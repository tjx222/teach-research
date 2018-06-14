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
<div class="return_1"></div>
<div class="cw_menu_wrap" >
	<div class="cw_menu_list" >
		<span class="cw_menu_list_top"></span>
		<div id="wrap2" class="cw_menu_list_wrap1"> 
			<div id="scroller">
			<!--<p>全部</p> 
			<p>人教版</p>
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
		<ul>
			<li><a href="${stc }jy/comres/index?_HS_=${empty param._HS_ ? 2 : param._HS_+1}">配套资源</a></li>
			<li><a href="javascript:;" class="header_act" >分类资源</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="mating_content">
			<form id="searchPlanForm" action="jy/comres/index_type?planType=${model.planType}" method="post">
				<div class="content_top">
					<div class="content_top_left">
						
					</div>
					<div class="content_top_right">
						<c:if test="${fn:length(phases) > 1 }">
             <div class="version">
                <label>学段：</label>
                <div>
                <select name="phaseId" id="phaseId">
                 <c:forEach items="${phases }" var="phase">
                   <option value="${phase.id }" ${phase.id == phaseId ? 'selected':'' }>${phase.name }</option>
                 </c:forEach>
                </select>
                </div>
              </div>
             </c:if>
             <c:if test="${fn:length(phases) < 2 }">
             <input type="hidden" name="phaseId" value="${phaseId}" />
             </c:if>
             
               <div class="version">
                <label>年级：</label>
                <div>
                <select name="gradeId">
                <ui:relation var="grades" type="xdToNj" id="${phaseId }"></ui:relation>
                 <c:forEach items="${grades }" var="grade">
                   <option value="${grade.id }" ${grade.id == model.gradeId ? 'selected':'' }>${grade.name }</option>
                 </c:forEach>
                </select>
                </div>
              </div>
             <div class="version">
              <label>学科：</label>
              <div>
               <select name="subjectId">
              <ui:relation var="subjects" type="xdToXk" id="${phaseId }"></ui:relation>
               <c:forEach items="${subjects }" var="subject">
                 <option value="${subject.id }" ${subject.id == model.subjectId ? 'selected':'' }>${subject.name }</option>
               </c:forEach>
              </select>   
              </div>
            </div>
						<input type="hidden" name="bookId" value="${model.bookId}" />
						<input type="hidden" id="bookShortname" name="bookShortname"  value="${model.bookShortname}" />
						<input type="search"  name="planName" class="btn_search" placeholder="" value="${model.planName}"/>
						<input type="button" id="searchBtn" class="search" value="搜索" >
					</div>
				</div>
			</form>
		</div>
		<div class="classify_tab">
			<ul>
				<li ${model.planType==0?'class="classify_tab_act"':'' }><a href="jy/comres/index_type?planType=0">教案</a></li>
				<li ${model.planType==1?'class="classify_tab_act"':'' }><a href="jy/comres/index_type?planType=1">课件</a></li>
				<li ${model.planType==2?'class="classify_tab_act"':'' }><a href="jy/comres/index_type?planType=2">反思</a></li>
			</ul> 
		</div>
		
		    <div class="classify_content_bottom" id="mating_li_wrap">
				<div id="scroller">
					<c:if test="${not empty data.datalist }" >
					<div class="classify_li_wrap">
						<div id="pageContent_type">
						     <c:forEach var="kt" items="${data.datalist }">
						         <div class="classify_li"> 
									<div class="classify_li_left">
										 <a href="jy/comres/view?lesid=${kt.planId}">
									  	  <div class="classify_title_option"><c:choose><c:when test="${model.planType==1}"><strong></strong></c:when><c:otherwise><span></span></c:otherwise></c:choose>【<jy:dic key="${kt.subjectId }"/>】【${kt.bookShortname}】【<jy:dic key="${kt.gradeId }"/>${kt.fasciculeId == 176?'上':kt.fasciculeId == 177?'下':'全' }】</div>
											<div class="classify_title"><ui:sout value="${kt.planName }" length="30" needEllipsis="true"></ui:sout></div>
											</a>
									</div> 
									<div class="classify_li_right">
									<jy:di key="${kt.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
										<div class="classify_head">
											<ui:photo src="${u.photo }" ></ui:photo>
										</div>
										<div class="classify_head_info">
											<h4><span>
							  			    ${u.orgId  == sessionScope._CURRENT_SPACE_.orgId ? u.name : u.nickname}</span><span><fmt:formatDate value="${kt.shareTime}" pattern="yyyy-MM-dd"/></span></h4>
											<h4><jy:di key="${kt.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org"><span title="<jy:di var="area" key="${org.areaId }" className="com.tmser.tr.manage.org.service.AreaService">${area.name }${org.shortName }</jy:di>"><ui:sout value="${area.name }${org.shortName }" length="20" needEllipsis="true"></ui:sout></span></jy:di></h4>
										</div>
									</div>
							    </div>
						     </c:forEach>
						     </div>
						     <form name="pageForm" method="post">
							    <ui:page url="jy/comres/index_type?planType=${model.planType }" data="${data}" callback="addDataType" dataType="html"/>
							    <input type="hidden" class="currentPage" name="page.currentPage" >
					        </form>
							<div style="height:2rem;"></div>
					</div>
					</c:if>
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
