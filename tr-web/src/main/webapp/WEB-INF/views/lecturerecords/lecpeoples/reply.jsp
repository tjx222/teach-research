<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="评论信息"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
</head>
<body style="background:none;">
<c:if test="${containsInput != '1' }">
	<div class="resources_view_cont1" style="width:920px">
			<form class="comment"><!-- validationEngine验证框架 ，评论所需要的form-->
				<h3>回复：</h3>
				<h4><span id="w_count">0</span>/300</h4>
				<div class="clear"></div>
				<textarea  name="content" id="content" cols="100" rows="7" class="txtarea_1" ></textarea>
				<div class="btn_bottom" style="margin-top:90px; text-align: center;">
				<input type="button" class="uploadBtn" id="comment_btn" value="完成">
				</div>
		</form>
	</div>
</c:if>
<form name="pageForm" method="post" id="option_form">
	<div class="consult_opinion_list">
	<c:if test="${containsInput != '1' || titleShow == '1'}" >
		<h3 class="consult_opinion_list_h3"><span></span>回复列表：</h3>
		<c:if test="${not empty data.datalist }">
			<h4 class="consult_opinion_list_h4">收起</h4>
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
											<span class="names_date"><fmt:formatDate value="${co.crtDttm }" pattern="yyyy-MM-dd"/>&nbsp;</span>
										</div>
										<div class="consult_opinion_right_c">
											<c:out value="${co.content }"></c:out>
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
														<c:out value="${reply.content }"></c:out>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
									<div class="rp_textarea" data-index="${coStu.index }" style="display:none;float:right;margin:10px 10px 0 0;" id="rp_${co.id}" data-opinionid="${co.id}">
										<textarea name="replyContent" id="replyContent" style="margin-left:100px;display:none" class="hf"></textarea>
										<div class="clear"></div>
										<input type="button" class="reply_btn reply_textarea_btn" value="回复" style="margin:5px 0 5px 420px;" data-opinionid="${co.id }" data-index="${coStu.index }" data-pid="${co.id }"/>
								    </div>
								</div> 
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty data.datalist }">
						<div class="empty_wrap">
							<div class="empty_img"></div>
							<div class="empty_info" style="text-align: center;">暂无回复！</div>
						</div>
					</c:if>
					 
				</div>
				<div class="pages1">
					<ui:page url="jy/lecturereply/reply?authorId=${model.authorId}&resId=${model.resId}&teacherId=${model.teacherId}&flags=${model.flags}" data="${data}" views="5"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
				</div>
		</div>
	</div>
</form>
<ui:require module="lecturerecords/js"></ui:require>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript">
require(['jquery','editor'],function(){
	var $ = require("jquery");
	var editor;
	$(function(){
	    webEditorOptions = {width:"915px",height:"90px",style:0,afterChange : function() {
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
	$(".consult_opinion_list_h4").click(function(){
		$(this).text($("div.div_option").is(":hidden") ? "收起" : "展开");
		$("div.div_option").slideToggle(10,function(){
			if($("div.div_option").is(":hidden")){
				window.parent.setCwinHeight("checkedBox",false);
			}else{
				window.parent.setCwinHeight("checkedBox",false);
			}
		});
	});
	$(".reply_option_t").click(function(event){
		return false;//防止冒泡
	})
	window.document.onclick = function(){bindBodyClick()};
	window.parent.document.onclick = function(){
		bindBodyClick();
	}
	var bindBodyClick = function(){
		var textVal = $(".reply_textarea:visible").find("textarea").val();
		textVal = $.trim(textVal);
		if(textVal.length>0){
			return false;
		}
		$(".reply_option_t").val("");
		$(".reply_textarea").hide();
	}
	/**
	 * 提交评论
	 */
	$("#comment_btn").click(
	function(){
		var txtcount = editor.count('text')
		if(txtcount > 300){
			alert("您最多可以输入300个字！");
			return false;
		}
		if(txtcount == 0){
			alert("回复不能为空");
			return false;
		}
		var content =$.trim(editor.text());
		var text = editor.text();
		var title = $.trim($("#title").val());
		$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/lecturereply/addreply",
				data:{"content":text,
					authorId:'${model.authorId}',
		    		resId:'${model.resId}',
		    		teacherId:'${model.teacherId}',
		    		parentId:'${model.parentId}'
		    	},
				success:function(data){
					if(data.code == 1){
						document.location.reload();
					}else{
						alert("回复失败，请重试！");
					}
				}
			});
	});
});
require(['comment']);
</script>
</body>
</html>