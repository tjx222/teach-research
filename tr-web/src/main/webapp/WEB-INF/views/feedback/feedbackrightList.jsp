<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
</head>
<script type="text/javascript" src="${ctxStatic }/modules/feedback/js/feedbackAjax.js"></script>
<body>
<div class="feedback_r">
<script type="text/javascript">
$(document).ready(function(){
	//返回详情
	$("#nomessage").hide();
	$('.feedback_l_1 h2 img').click(function (){
		$('.feedback_l_1').hide();
		$('.feedback_l').show();
	});
	//加载详情
	$('.feedback_list_n_l').click(function (){
		$('.feedback_l_1').show();
		$('.feedback_l').hide();
		//取主键id
		var name=''
		var id =$(this).find("input").val();
		$.fn.ajaxHtml.toUrl('./jy/feedback/feedbackDetail',{'id':id},'left_detail');
	});
	$('.feedback_l_12 div:last-child').removeClass("border");
	//处理分页的keydown事件报错
	var $go_page = $('#go_page');
	$go_page.bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
        	return false;
        }
    });
	var messageList = $("#messageId").val();
	if(messageList=="[]"){
		$("#nomessage").show();
	}
});
function reloadDetail(id){
	$('.feedback_l_1').show();
	$('.feedback_l').hide();
	$.fn.ajaxHtml.toUrl('./jy/feedback/feedbackDetail',{'id':id},'left_detail');
}
</script>
			<h2>
				<span>反馈列表</span>
			</h2>
			<div class="feedback_list">
			<div id="nomessage" class="feedback_list_cont"> 
				<div class="empty_wrap"> 
				    <div class="empty_info">
						没有相关信息呦
					</div>
				</div>
			</div>
			<input id="messageId" type="hidden" value="${data.datalist}">
				<div class="feedback_list_cont">
					<c:forEach items="${data.datalist}" var="datalist">
					<div class="feedback_list_n">
						<div class="feedback_list_n_l">
						<input type="hidden" name="id" id="${datalist.id }" value="${datalist.id }"/>
						<c:choose>  
						   <c:when test="${datalist.message.length()>19}">
						   <span id="messageLength">${fn:substring(datalist.message , 0, 19)}...</span>
						   </c:when>  
						   <c:otherwise>
						   <span title="查看回复详情" id="messageLength">${datalist.message}</span>
						   </c:otherwise>  
						</c:choose>
						</div>
						<c:choose>  
						   <c:when test="${datalist.ishavareply==1}">
						   <a href="javascript:reloadDetail(${datalist.id})">
						   <div title="查看回复详情" class="feedback_list_n_c"></div>
						   </a>
						   </c:when>  
						</c:choose>
						
						<div class="feedback_list_n_r">
							${fn:substring(datalist.senderTime , 0, 10)}
<%-- 						<fmt:formatDate value="${datalist.senderTime}" pattern="yyyy-MM-dd HH:mm"/> --%>
						</div>
					</div>
					</c:forEach>
				</div>
				<div class="clear"></div>
				<div class="pages">
					<form id="pageForm" name="pageForm" method="post">
						<ui:page url="./jy/feedback/feedbackList" data="${data}" views="10" isAjax="true" callback="feedpage"/>
						<input type="hidden" id="curPage" class="currentPage" name="page.currentPage">
					</form>
				</div>
			</div>
		</div>
</body>
</html>