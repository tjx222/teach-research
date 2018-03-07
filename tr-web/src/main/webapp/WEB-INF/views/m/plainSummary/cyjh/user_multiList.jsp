<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="semester_wrapper">
	<div class="semester_wrap" style="top:9%;">
		<span class="check_menu_top"></span>
		<div class="semester_wrap1">			
			<p data-val="" class="${empty term?'act':''}">全部</p>
			<p data-val="0" class="${term==0?'act':''}">上学期</p>
			<p data-val="1" class="${term==1?'act':''}">下学期</p> 
		</div>
	</div>
</div>
<div class="check_content" style="border-top:0rem;">
	<c:set var="jihuazongjie" value="${roleId==1375?'学科': (roleId==1374? '年级' : '备课') }" />
	<div class="check_tch_wrap" style="height:9rem;">			   		
		<div class="check_content_right" style="width:95%;">
			<div class="check_content_right_name">				
			</div> 
			<div class="check_content_right_option">
				<span>撰写：${total.plainNum + total.summaryNum}课 </span>
				<span>提交：${total.plainSubmitNum + total.summarySubmitNum}课</span>
				<span>已查阅：${total.plainCheckedNum + total.summaryCheckedNum}课</span>
				<div class="semester">
					<c:if test="${empty term}">全部</c:if>
				    <c:if test="${term==0}">上学期</c:if>
					<c:if test="${term==1}">下学期</c:if>
					<strong></strong>
				</div>
				<span style="float:right;margin-right:1rem;font-size:1.333rem;">学期：</span>
			</div>					
		</div>		
		<div class="clear"></div>
		<div class="nav_tab">
		    <ul id="selectType">		    	
				<li><a ${category==3 ?'class="header_act"' : '' } data-category="3">${jihuazongjie }计划</a></li>
				<li><a ${category==4 ?'class="header_act"' : '' } data-category="4">${jihuazongjie }总结</a></li>
		    </ul>
      	</div>
	</div>							
	<div class="content_bottom" id="check_c_b" style="top:20.873rem;">
		<div id="scroller">
			<c:if test="${empty planItems && empty summaryItems}">	
				<div class="content_k" style="margin-top: 5rem; display:none;" data-plan="${empty planItems}" data-summary="${empty summaryItems}">
					<dl>
						<dd></dd>
						<dt>暂时还没有查阅信息</dt>
					</dl>
				</div>
			</c:if>			
				<div class="content_bottom_width njjh"> 
					<c:if test="${!empty planItems }">
						<c:forEach items="${planItems }" var="res" varStatus="st">
							<div class="courseware_ppt">
								<div class="courseware_img_1">${jihuazongjie }计划</div>
								<a href="${ctx }/jy/planSummaryCheck/role/${roleId }/planSummary/${res.id}?site_preference=mobile&gradeId=${gradeId}&subjectId=${subjectId}" >
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
								<div class="courseware_img_1">${jihuazongjie }总结</div>
								<a href="${ctx }/jy/planSummaryCheck/role/${roleId }/planSummary/${res.id}?site_preference=mobile&gradeId=${gradeId}&subjectId=${subjectId}" >
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