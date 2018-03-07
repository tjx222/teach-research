<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title=""></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/common.css"media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
	<ui:require module="history/js"></ui:require>
</head>
<body style="background: #fff;">
<div class="calendar_year_center">
	<div class="page_option"> 
	    <form action="jy/history/${schoolYear }/czda/index" id="selectForm" method="post">
	    	<div class="a">
				<select class="chosen-select-deselect class_teacher" name="spaceId" style="width: 132px; height: 25px;" onchange="formsubmit();">
					<c:choose>
						<c:when test="${not empty ulist }">
						    <c:forEach items="${ulist }" var="us">
						    	<option value="${us.id }" ${recordbag.spaceId==us.id ? 'selected' : '' }>${us.spaceName }</option>
						    </c:forEach>
						</c:when>
						<c:otherwise>
							<option>请选择职务</option>
						</c:otherwise>
					</c:choose>
				</select>
			</div>  
			<div class="serach">
				<input type="text" class="ser_txt" name="name" value="${recordbag.name}"/>
				<input type="button" class="ser_btn"  onclick="formsubmit()"/>
			</div>
	    </form>
	</div>
	<form id="showForm" action="jy/history/${schoolYear }/czda/showList" method="post">
		<input type="hidden" id="rid" name="id"   value=""/>
		<input type="hidden" id="rname" name="name" value=""/>
	</form>
	<div class="resources_table_wrap">
			<div class="record_wrap">
			    <c:forEach items="${rlist }" var="bag">
			    	<div class="record_wrap_h">
						<div class="record_wrap_each" data-id="${bag.id}" data-name="${bag.name}">
							<span class="record_title">${bag.name}</span>
							<div class="record_number">(${bag.resCount })</div>
						</div>
						<c:if test="${bag.isPinglun!=0 }">
						<div class="ping" data-id="${bag.id }"  data-userId="${_CURRENT_USER_.id}"><span></span></div></c:if>
					</div>
			    </c:forEach>
			    <c:if test="${empty rlist}">
					<div class="cont_empty">
						<div class="cont_empty_img"></div>
						<div class="cont_empty_words">您此学年没有资源哟！</div> 
					</div>
			    </c:if>
			</div> 
		<div class="clear"></div>
	</div> 
	<div class="clear"></div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','recordbag'],function($){
		$(function(){
			//加载下拉框
			$(".class_teacher").chosen({ disable_search : true });
		})
	});
</script>
</html>