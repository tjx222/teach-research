<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title=""></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen"> 
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98%;
	}
	</style>
	<ui:require module="history/js"></ui:require>
</head>
<body style="background: #fff;">
<!-- 查看 -->
<div  id="t_view" style="display:none;">

	<div class="cont_list" id="t_title"> 
	</div>
	<div class="cont_list" id="t_type"> 
	</div> 
	<div class="cont_list" id="t_time"> 
	
	</div>
	<div class="cont_list" id="t_isShare"> 
	
	</div>
	<div class="cont_list" id="t_size"> 
	
	</div>
	<div class="cont_list" id="t_ext"> 
	
	</div> 
</div>
<div class="calendar_year_center">
	<div class="page_option">  
		<form action="jy/history/${year}/jxwz/index" id="selectForm" method="post">
			<div class="a1">
				<select class="chosen-select-deselect school_year " name="schoolTerm" style="width: 90px; height: 25px;" onchange="formsubmit();">
					<option value="" ${empty thesis.schoolTerm?'selected':''}>全学年</option>
					<option value="0" ${thesis.schoolTerm==0?'selected':''}>上学期</option>
					<option value="1" ${thesis.schoolTerm==1?'selected':''}>下学期</option>
				</select>
			</div>  
			<div class="left_border"></div>
			<div class="a2">
				<select class="chosen-select-deselect category " name="thesisType" style="width: 120px; height: 25px;" onchange="formsubmit();">
					<option value="" ${empty thesis.thesisType ? 'selected' : '' }>全部资源</option>
					<option value="教学论文" ${thesis.thesisType=='教学论文' ? 'selected' : '' }>教学论文</option>
					<option value="教学案例" ${thesis.thesisType=='教学案例' ? 'selected' : '' }>教学案例</option>
					<option value="课题研究" ${thesis.thesisType=='课题研究' ? 'selected' : '' }>课题研究</option>
					<option value="教学笔记" ${thesis.thesisType=='教学笔记' ? 'selected' : '' }>教学笔记</option>
					<option value="教育叙事" ${thesis.thesisType=='教育叙事' ? 'selected' : '' }>教育叙事</option>
					<option value="读书笔记" ${thesis.thesisType=='读书笔记' ? 'selected' : '' }>读书笔记</option>
					<option value="生活感悟" ${thesis.thesisType=='生活感悟' ? 'selected' : '' }>生活感悟</option>
					<option value="其他" ${thesis.thesisType=='其他' ? 'selected' : '' }>其他</option>
				</select>
			</div> 
			<div class="serach">
				<input type="text" class="ser_txt" name="thesisTitle" value="${thesis.thesisTitle}"/>
				<input type="button" class="ser_btn" onclick="formsubmit();"/>

			</div>
		</form>
	</div>
	<div class="resources_table_wrap">
		<c:if test="${empty tList.datalist }"> 
			<div class="cont_empty">
				<div class="cont_empty_img"></div>
				<div class="cont_empty_words">您此学年没有资源哟！</div> 
			</div>
		</c:if>
		<c:if test="${not empty tList.datalist }"> 
			<table class="resources_table">
				<tr>
					<th style="width:433px;text-align:left;padding-left:30px;">资源名称</th>
					<th style="width:140px;">类别</th>
					<th style="width:140px;">日期</th> 
					<th style="width:130px;">分享状态</th>
					<th style="width:120px;">操作</th>
				</tr>
				<c:forEach items="${tList.datalist }" var="data">
					<tr id="list">
						<td style="text-align:left;">
							<input type="checkbox" class="check"  data-resid="${data.resId }"/>
							<a href="jy/teachingView/view/thesisview?id=${data.id }" target="_blank">
							<ui:sout value="${data.thesisTitle}" length="18" needEllipsis="true"></ui:sout></a>
							<c:if test="${data.isComment==1}"><span class="ping" data-id="${data.id }"  data-userId="${_CURRENT_USER_.id}"></span></c:if> 
						</td>
						<td>${data.thesisType}</td>
						<td><fmt:formatDate value="${data.lastupDttm}" pattern="yyyy-MM-dd" /></td> 
						<td><c:if test="${data.isShare==0}">未分享</c:if><c:if test="${data.isShare==1}">已分享</c:if></td>
						<td>
							<span class="look_up" style="margin-left:30px;" data-title="${data.thesisTitle}" data-type="${data.thesisType}" data-time="<fmt:formatDate value='${data.lastupDttm}' pattern='yyyy-MM-dd' />" data-isShare="${data.isShare}" data-size="${data.flago}" data-ext="${data.fileSuffix}"></span>
							<a href="<ui:download filename="${data.thesisTitle}" resid="${data.resId}"></ui:download>"><span class="download"></span></a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div class="resources_table_bottom">
				<div class="resources_table_check">
					<input type="checkbox" class="all" id="selectAll"/>
					全选
				</div>
				<input type="button" class="batch_edit" value="批量下载" />
			</div> 
			<form name="pageForm" method="post">
				<ui:page url="${ctx}jy/history/${year}/jxwz/index" data="${tList}" />
				<input type="hidden" class="currentPage" name="currentPage">
				<input type="hidden" name="schoolTerm" value="${thesis.schoolTerm }">
				<input type="hidden" name="thesisType" value="${thesis.thesisType }">
			</form>
		</c:if>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','thesis'],function(){});
</script>
</html>