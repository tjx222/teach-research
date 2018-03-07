<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅意见"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
</head>
<body style="background:none;">
<form name="pageForm" method="post" id="option_form">
	<div class="consult_opinion_list">
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
										<div class="consult_opinion_right_t" style="color:#ff5252;"> 
											<span class="names"><b style="margin-right:10px;">查阅${co.resType == 0 ? '教案': co.resType == 1? '课件':'反思' }</b>${u.name }：</span>
											<span class="names_date"><fmt:formatDate value="${co.crtTime }" pattern="yyyy-MM-dd"/>&nbsp;</span>
										</div>
										<div class="consult_opinion_right_c" style="color:#ff5252;">
											${co.content }
										</div>
										<div class="consult_opinion_right_b">
											<div class="reply">
												<span></span>
												<c:if test="${canReply != 0 }">
													<b class="reply_rq" data-uname="${u.name }" data-id="${co.id }" data-opinionid="${co.id }" data-index="${coStu.index }">回复</b>
												</c:if>
											</div>
										</div>
									</div>
									<c:forEach items="${coMap[co.id]}" var="reply">
										<div class="consult_opinion_list_cont1 clearfix" id="ch_${co.id}">
											<div id="cont_${reply.id }">
												<div class="tconsult_opinion_head">
													<div class="tconsult_opinion_head_bg"></div>
													<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u1"></jy:di>
											  		 <ui:photo src="${u1.photo}" width="43" height="43"></ui:photo>
												</div> 
												<div class="consult_opinion_right1">
													<div class="consult_opinion_right_t">
														<span class="names">${u1.name}：</span>
														<span class="names_date"><fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/></span>
													</div>
													<div class="consult_opinion_right_c">
														${reply.content }
													</div>
													<div class="consult_opinion_right_b">
														<div class="reply">
															<span></span>
															<c:if test="${canReply != 0 }">
																 <b class="reply_rq" data-uname="${u1.name }" data-opinionid="${reply.opinionId }" data-id="${reply.id }" data-index="${coStu.index }">回复</b>
															</c:if>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
									<div class="rp_textarea" data-index="${co.id }" style="display:none;float:right;" id="rp_${co.id}" data-opinionid="${co.id}">
										<textarea name="replyContent" id="replyContent" class="hf"></textarea>
										<div class="clear"></div>
									<input type="button" class="reply_textarea_btn" value="回复" data-opinionid="${co.id }" data-index="${coStu.index }" data-pid="${co.id }"/>
								</div>
								</div> 
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty data.datalist }">
						<div style="border-bottom:none;display:block;" class="consult_opinion_list_cont">
							<div class="check_k"></div>
							<div style="color:#ccc;font-size:15px;font-weight:bold;line-height:20px;height:30px;text-align:center;">暂无查阅意见,稍后再来看吧！</div>
						</div>
					</c:if>
					 
				</div>
				<div class="pages1">
				  <ui:page url="jy/check/checklist" data="${data}"/>
				  <input type="hidden" class="currentPage" name="page.currentPage" value="1">
				  <input type="hidden" name="resId" value="${model.resId }">
				  <input type="hidden" name="flags" value="${model.flags }">
				  <input type="hidden" name="resType" value="${model.resType }">
				</div>
		</div>
	</div>
</form>
<ui:require module="check/js"></ui:require>
<script type="text/javascript">
require(['jquery','check','editor'],function(){});
</script>
</body>
</html>