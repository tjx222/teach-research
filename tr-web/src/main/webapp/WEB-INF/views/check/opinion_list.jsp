<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅意见"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
</head>
<body style="background:none;overflow-x:hidden;">
<c:if test="${containsInput==1}" >
<div class="consult_opinion">
	<h3 class="consult_opinion_h3"><span></span>查阅意见：</h3>
	<div class="radios_sel">
		<input type="radio" name="level" id="level1" value="0" ${model.level==0 ? 'checked':'' }><label for="level1">A+</label>
		<input type="radio" name="level" id="level2" value="1" ${model.level==1 ? 'checked':'' }><label for="level2">A</label>
		<input type="radio" name="level" id="level3" value="2" ${model.level==2 ? 'checked':'' }><label for="level3">B+</label>
		<input type="radio" name="level" id="level4" value="3" ${model.level==3 ? 'checked':'' }><label for="level4">B</label>
		<input type="radio" name="level" id="level5" value="4" ${model.level==4 ? 'checked':'' }><label for="level5">C</label>
		<input type="radio" name="level" id="level6" value="5" ${model.level==5 ? 'checked':'' }><label for="level6">D</label>
	</div>
	<div class="textarea">
		<h4 class="textarea_h4"><span id="w_count">0</span>/300</h4>
		<form id="gudingIdeology_form">
			<textarea name="" placeholder="您可以在此处输入查阅意见，也可以直接点击“已查阅”按钮完成查阅..." id="content" cols="30" rows="10" class="textarea"></textarea>
			<div class="clear"></div>
			<input type="button" class="have_access_btn" value="查阅" id="check_btn"/>
			<input type="hidden" name="title" id="title" value="${model.title }"/>
			<input type="hidden" name="term" id="term" value="${model.term }"/>
		</form>
	</div>
</div>
</c:if>

<form name="pageForm" method="post">
<div class="consult_opinion_list">
<c:if test="${containsInput==1 || titleShow == '1'}" >
	<h3 class="consult_opinion_list_h3">查阅意见列表</h3>
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
												<jy:di key="${reply.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
										  		 <ui:photo src="${u.photo}" width="43" height="43"></ui:photo>
											</div> 
											<div class="consult_opinion_right1">
												<div class="consult_opinion_right_t">
													<span class="names">${u.name}：</span>
													<span class="names_date"><fmt:formatDate value="${reply.crtTime }" pattern="yyyy-MM-dd"/></span>
												</div>
												<div class="consult_opinion_right_c">
													${reply.content }
												</div>
												<div class="consult_opinion_right_b">
													<div class="reply">
														<span></span>
														<c:if test="${canReply != 0 }">
															 <b class="reply_rq" data-uname="${u.name }" data-opinionid="${reply.opinionId }" data-id="${reply.id }" data-index="${coStu.index }">回复</b>
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
	</div>
    </div>
		<div class="pages1">
		  <ui:page url="jy/check/infoIndex" data="${data}"/>
		  <input type="hidden" class="currentPage" name="page.currentPage" value="1">
		  <input type="hidden" name="resId" value="${model.resId }">
		  <input type="hidden" name="flags" value="${model.flags }">
		  <input type="hidden" name="flago" value="${model.flago }">
		  <input type="hidden" name="resType" value="${model.resType }">
		  <input type="hidden" name="titleShow" value="${titleShow}">
		</div>
	<c:if test="${empty data.datalist }">
		<div class="check_k_wrap">
			<div class="check_k"></div>
			<div class="check_k_info">暂无查阅意见,稍后再来看吧！</div>
		</div>
	</c:if>

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
	    webEditorOptions = {width:"1090px",height:"90px",style:0,afterChange : function() {
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
	jq("#check_btn").click(
	function(){
		if(editor.count('text') > 300){
			alert("您最多可以输入300个字！");
			return false;
		}
		var content =jq.trim(editor.text());
		var text =  content === '' || content == defautcontent? '':editor.text();
		content = text ? editor.html() : "";
		var title = jq.trim(jq("#title").val());
		var term = jq.trim(jq("#term").val());
		var level =$("input[name='level']:checked").val();
		if(confirm("您确定要提交查阅意见吗？")){
			jq.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/check/check",
				data:{"flago":content,"term":term,level:level,"title":title,"authorId":'${model.authorId}',"resType":'${model.resType}',"resId":'${model.resId}',"gradeId":"${model.gradeId}","subjectId":"${model.subjectId}"},
				success:function(data){
					if(data.code == 1){
						if(submitSuccessListeners.length>0){
							for(var i=0;i<submitSuccessListeners.length;i++){
								submitSuccessListeners[i](text);
							}
						}
						jq("#option_text").val('');
						//window.history.back(-1);
						window.location.href = window.location.href;
					}else{
						alert("提交失败！");
					}
				}
			});
		}
	});
	</c:if>
});
require(['check']);
</script>
</body>
</html>