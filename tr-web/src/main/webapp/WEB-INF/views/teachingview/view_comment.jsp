<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="评论信息"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
</head>
<body style="background:none;">
	<form name="pageForm" method="post" id="option_form">
		<div class="consult_opinion_list">
			<c:if test="${containsInput!=1 || titleShow == '1'}" >
			<h3 class="consult_opinion_list_h3"><span></span>评论列表：</h3>
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
			<div class="border1"></div>
			<div class="consult_opinion_list clearfix div_option">
					<div class="consult_opinion_list1"> 
						
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
												<span class="names_date"><fmt:formatDate value="${co.crtDttm }" pattern="yyyy-MM-dd"/>&nbsp;</span>
											</div>
											<div class="consult_opinion_right_c">
												${co.content }
											</div>
										</div>
										<c:forEach items="${huifuMap[co.id]}" var="reply">
											<div class="consult_opinion_list_cont1 clearfix" id="ch_${co.id}">
												<div id="cont_${reply.id }">
													<div class="tconsult_opinion_head">
														<div class="tconsult_opinion_head_bg"></div>
														<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u2"></jy:di>
												  		 <ui:photo src="${u2.photo}" width="43" height="43"></ui:photo>
													</div> 
													<div class="consult_opinion_right1">
														<div class="consult_opinion_right_t">
															<span class="names">${u2.name}：</span>
															<span class="names_date"><fmt:formatDate value="${reply.crtDttm }" pattern="yyyy-MM-dd"/></span>
														</div>
														<div class="consult_opinion_right_c">
															${reply.content }
														</div>
													</div>
												</div>
											</div>
										</c:forEach>
										<div class="rp_textarea" data-index="${co.id }" style="display:none;float:right;" id="rp_${co.id}" data-opinionid="${co.id}">
											<textarea name="replyContent" id="replyContent" style="margin-left:100px;display:none" class="hf"></textarea>
											<div class="clear"></div>
											<input type="button" class="reply_btn reply_textarea_btn" value="回复" style="margin:5px auto;"  data-opinionid="${co.id }" data-index="${coStu.index }" data-pid="${co.id }" data-authorid=""/>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty data.datalist}">  
							<div class="check_k_wrap">
								<div class="check_k"></div>
								<div class="check_k_info">暂无评论,稍后再来看吧！</div>
							</div>
						</c:if>
					</div>
					<div class="pages1">
					  <ui:page url="jy/comment/list" data="${data}" views="5"/>
					<input type="hidden" name="resId" value="${model.resId}">
					<input type="hidden" name="resType" value="${model.resType }">
					<input type="hidden" value="${model.authorId }" name="authorId">
					<input type="hidden" name="flags" value="${model.flags}"/>
					<input type="hidden" name="flago" value="${model.flago}"/>
					<input type="hidden" name="title" value="${model.title}"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
					</div>
			</div>
		</div>
</form>

<div class="clear"></div>
<ui:require module="comment/js"></ui:require>
<script type="text/javascript">
require(['jquery','editor'],function(){
	<c:if test="${containsInput != 1}" >
	var jq = require("jquery");
	var editor;
	jq(function(){
	    webEditorOptions = {width:"820px",height:"90px",style:0,afterChange : function() {
	    	var txtcount = this.count('text');
	    	if(txtcount > 300){
	    		jq('#w_count').html("<font color='red'>"+txtcount+"</font>");
	    	}else{
	    		jq('#w_count').html(txtcount);
	    	}
		}
	    };
		editor = jq("#content").editor(webEditorOptions)[0];
	});
	</c:if>
});
require(['comment']);
</script>
</body>
</html>