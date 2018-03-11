<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅意见"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
</head>
<body style="background:none;">
<form name="pageForm" method="post" id="option_form">
	<div class="consult_opinion_list">
		<c:if test="${containsInput==1 || titleShow == '1'}" >
		<h3 class="consult_opinion_list_h3"><span></span>查阅意见列表：</h3>
		<c:if test="${not empty data.datalist }">
			<div class="search_criteria" >
				<div class="toggle">
					<span style="background-position: -130px -2px;"></span>
					<strong>收起</strong>
				</div>
			</div>
		</c:if>
		<c:if test="${empty data.datalist }">
			<h4></h4>
		</c:if>
		</c:if>
		<div class="clear"></div>
		<div class="consult_opinion_list clearfix div_option">
				<div class="consult_opinion_list1"> 
					<div class="border1"></div>
					<c:if test="${!empty data.datalist }">
						<c:forEach items="${data.datalist}" var="co" varStatus="coStu">
						<div class="consult_opinion_list_cont clearfix">
					   		<jy:di key="${co.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
								<div class="tconsult_opinion_head">
									<div class="tconsult_opinion_head_bg"></div>
									<ui:photo src="${u.photo}" width="43" height="43"></ui:photo>
								</div> 
								<div class="consult_opinion_right">
									<div id="cont_${co.id }">
										<div class="consult_opinion_right_t">
											<span class="names">${u.name }：</span>
											<span class="names_date"><fmt:formatDate value="${co.crtTime }" pattern="yyyy-MM-dd"/>&nbsp;</span>
										</div>
										<div class="consult_opinion_right_c">
											<c:out value="${co.content }"></c:out>
										</div>
									</div>
									<c:forEach items="${coMap[co.id]}" var="reply">
										<div class="consult_opinion_list_cont1 clearfix" id="ch_${co.id}">
											<div id="cont_${reply.id }">
												<div class="tconsult_opinion_head">
													<div class="tconsult_opinion_head_bg"></div>
													<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
											  		 <ui:photo src="${u.photo}" width="43" height="43"></ui:photo>
												</div> 
												<div class="consult_opinion_right1">
													<div class="consult_opinion_right_t">
														<span class="names">${u.name}：</span>
														<span class="names_date"><fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/></span>
													</div>
													<div class="consult_opinion_right_c">
														<c:out value="${reply.content }"></c:out>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div> 
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty data.datalist }">
						<div class="check_k_wrap">
							<div class="check_k"></div>
							<div class="check_k_info">暂无查阅意见,稍后再来看吧！</div>
						</div>
					</c:if>
					 
				</div>
				<div class="pages1">
				  <ui:page url="jy/check/lookCheckOption" data="${data}"/>
				  <input type="hidden" class="currentPage" name="page.currentPage" value="1">
				  <input type="hidden" name="resId" value="${model.resId }">
				  <input type="hidden" name="flags" value="${model.flags }">
				  <input type="hidden" name="flago" value="${model.flago }">
				  <input type="hidden" name="resType" value="${model.resType }">
				</div>
		</div>
	</div>
</form>
<ui:require module="check/js"></ui:require>
<script type="text/javascript">

var submitSuccessListeners=new Array();
function submitSuccess(listener){
	submitSuccessListeners.push(listener);
}
require(['jquery','editor'],function(){
	<c:if test="${containsInput==1}" >
	var jq = require("jquery");
	var editor;
	var defautcontent = "您可以在此处撰写查阅意见，也可以直接点击“已查阅”按钮完成查阅。";
	jq(function(){
	    webEditorOptions = {width:"820px",height:"90px",style:0,afterChange : function() {
	    	var txtcount = this.count('text');
	    	var text = this.text();
	    	if(txtcount > 300){
	    		jq('#w_count').html("<font color='red'>"+txtcount+"</font>");
	    	}else if(text != defautcontent){
	    		jq('#w_count').html(txtcount);
	    	}
		},afterBlur:function(){
			if(this.count('text') == 0)
				this.html("<span style='color:#999;'>"+defautcontent+"</span>");
		},afterFocus:function(){
				if(this.text() == defautcontent)
					this.text("");
		},initContent:"<span style='color:#999;'>"+defautcontent+"</span>"
	    };
		editor = jq("#content").editor(webEditorOptions)[0];
	});
	/**
	 * 提交查阅意见
	 */
	</c:if>
});
require(['check']);
</script>
</body>
</html>