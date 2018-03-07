<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="评论信息"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
</head>
<body style="background:none;overflow-x:hidden;">
<ui:token></ui:token>
<c:if test="${containsInput != '1' }"><!--flags不等于1隐藏-->
	<div class="resources_view_cont1">
		<form class="comment"><!-- validationEngine验证框架 ，评论所需要的form-->
			<h3><span></span>评论：</h3>
			<h4><span id="w_count">0</span>/300</h4>
			<div class="clear"></div>
			<textarea  name="content" id="content" cols="100" rows="7" class="txtarea_1" ></textarea> 
			<div class="clear"></div>
			<input type="button" class="complete" id="comment_btn" value="完成">
			<input type="hidden" value="${model.title}" id="title"/><!--评论标题-->
		</form>
	</div>
</c:if>
<form name="pageForm" method="post" id="option_form">
	<div class="consult_opinion_list">
		<c:if test="${containsInput!=1 || titleShow == '1'}" >
		<h3 class="consult_opinion_list_h3"><span></span>评论列表：</h3>
		<c:if test="${not empty data.datalist }">
<!-- 			<h4 class="consult_opinion_list_h4">收起</h4> -->
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
										<div class="consult_opinion_right_b">
											<div class="reply">
												<span></span>
												<c:if test="${canReply != 0 }">
													<b class="reply_rq" data-uname="${u.name }" data-authorid="${u.id}" data-id="${co.id }" data-opinionid="${co.id }" data-index="${coStu.index }">回复</b>
												</c:if>
											</div>
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
													<div class="consult_opinion_right_b">
														<div class="reply">
															<span></span>
															<c:if test="${canReply != 0 }">
																 <b class="reply_rq" data-uname="${u2.name }" data-authorid="${u2.id}" data-opinionid="${reply.opinionId }" data-id="${reply.id }" data-index="${coStu.index }">回复</b>
															</c:if>
														</div>
													</div>
													<%-- <div class="reply_textarea" style="display:none">
														<textarea placeholder="回复 ${u.name }" class="reply_option_t validate[maxSize[300]] textarea" name="" cols="30" rows="10"></textarea>
														<input type="button" value="回复" class="reply_textarea_btn" data-authorid="${u2.id}" data-opinionid="${co.id }" data-index="${coStu.index }" data-pid="${co.id }"/>
													</div> --%>
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
<ui:require module="comment/js"></ui:require>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript">
require(['jquery','editor'],function(){
	var $ = require("jquery");
	var editor;
	$(function(){
		webEditorOptions = {width:"1120px",height:"90px",style:0,afterChange : function() {
	    	var txtcount = this.count('text');
	    	if(txtcount > 300){
	    		$('#w_count').html("<font color='red'>"+txtcount+"</font>");
	    	}else{
	    		$('#w_count').html(txtcount);
	    	}
		}
	    };
		editor = $("#content").editor(webEditorOptions)[0];
	});
	
	/**
	 * 提交评论
	 */
	$("#comment_btn").click(function(){
			var txtcount = editor.count('text');
			if(txtcount > 300){
				alert("您最多可以输入300个字！");
				return false;
			}
			if(txtcount == 0){
				alert("评论内容不能为空！");
				return false;
			}
			var content =$.trim(editor.text());
			var text = editor.text();
			var title = $.trim($("#title").val());
			var key = $("input[name='_TOKEN_']").val();
			var value = $("input[name='"+key+"']").val();
			var dataobject = {"content":editor.html(),"title":title,"authorId":'${model.authorId}',"resType":'${model.resType}',"resId":'${model.resId}',"_TOKEN_":key};
			dataobject[key]=value;
			$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/comment/addComment",
					data:dataobject,
					success:function(data){
						if(data.code == 1){
							document.location.reload();
						}else{
							alert("评论失败！");
						}
					}
				});
	});
});
require(['comment']);
</script>
</body>
</html>