<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta charset="UTF-8">
<ui:htmlHeader title="我的备课本-同课题提交资源用户"></ui:htmlHeader>
<ui:require module="myplanbook/js"></ui:require>
<style type="text/css">

.same_submission_cont{
	height: 147px;
	width: 1100px;
	border:1px #abadb3 solid;
	border-radius: 4px;
	overflow: auto;
	padding:10px 9px;
}
.same_submission_cont ol{}
.same_submission_cont ol li{
	float: left;
	width: 265px;
	height: 30px;
	line-height: 30px;
	font-size: 16px;  
	margin-left: 10px;
	margin-right: 15px;
}
.same_submission_cont ol li b{
	display: inline-block;
    width: 4px;
    height: 4px; 
    float: left;
    font-size: 30px;
    line-height: 24px;
    color: #C0C0C0;
    margin-right: 9px; 
}
.same_submission_cont ol li a{
	color: #3976f3;
	display: block;
	float: left;
	white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    width: 162px;
    margin-right: 10px;
}
.same_submission_cont ol li span{ 
	display: block;
	float: left;
}
</style>
</head>
<body style="background:#fff;"> 
<div class="same_submission_cont">
	<ol>
		<c:if test="${not empty datalist }">
			<c:forEach items="${datalist }" var="data">
			<li>
				<b>·</b>
				<a style="cursor:pointer;">
					<span  class="viewOthers" data-id="${data.id }" title="${data.title }">
					<ui:sout value="${data.title }" needEllipsis="true" length="20"></ui:sout>
					</span>
				</a> 
				<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
				<span class="span_lru">${u.name}</span>
			</li>
			</c:forEach>
		</c:if>
		<c:if test="${empty datalist }">
			<div class="check_k_wrap">
				<div class="check_k"></div>
				<div class="check_k_info">其他人还没有提交哟!</div>
			</div>
		</c:if>
	</ol>
</div> 
</body>
<script type="text/javascript">
	$(function(){
		$(".viewOthers").click(function(){
			var id=$(this).attr("data-id");
			window.open(_WEB_CONTEXT_+"/jy/planSummary/viewOthers?id="+id,"hidenframe");
		})
	})
</script>
</html>