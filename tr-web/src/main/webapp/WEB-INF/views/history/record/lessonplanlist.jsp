<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title=""></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen">  
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
<!-- 查看 -->
<div  id="t_view" style="display:none;">
	<div class="cont_list" id="t_title"></div>
	<!-- <div class="cont_list" id="t_restype"></div>
	<div class="cont_list" id="t_type"></div> 
	<div class="cont_list" id="t_publisher"></div> -->
	<div class="cont_list" id="t_time"></div>
	<div class="cont_list" id="t_size"></div>
	<div class="cont_list" id="t_ext"></div> 
</div>
<div class="calendar_year_center">
	<div class="page_option">  
		<form action="jy/history/${schoolYear}/czda/showList" id="selectForm" method="post">
		    <div class="a1">${recordbag.name }</div>
			<%-- 
				<select class="chosen-select-deselect full_year " name="schoolTerm" style="width: 101px; height: 25px;" onchange="this.form.submit();">
					<option value="" ${empty thesis.schoolTerm?'selected':''}>全学年</option>
					<option value="0" ${thesis.schoolTerm==0?'selected':''}>上学期</option>
					<option value="1" ${thesis.schoolTerm==1?'selected':''}>下学期</option>
				</select> <div class="left_border"></div>
			  --%> 
			<div class="serach">
				<input type="text" class="ser_txt" name="searchName" value="${searchName }"/>
				<input type="button" class="ser_btn" onclick="formsubmit()"/>
				<input type="hidden" name="name" value="${recordbag.name }"/>
				<input type="hidden" name="id" value="${recordbag.id }"/>
				<input type="hidden" name="year" value="${schoolYear}"/>
			</div>
		</form>
	</div>
	<div class="resources_table_wrap">
		<c:if test="${empty rlist.datalist }"> 
			<div class="cont_empty">
				<div class="cont_empty_img"></div>
				<div class="cont_empty_words">您还没有精选${recordbag.name }哟！</div> 
			</div>
		</c:if>
		<c:if test="${not empty rlist.datalist }">
			<table class="resources_table">
				<tr>
					<th style="width:350px;text-align:left;padding-left:30px;">资源名称</th>
					<c:if test="${recordbag.name=='教学反思'||recordbag.name=='教学文章'||recordbag.name=='计划总结' }"><th style="width:107px;">类别</th></c:if> 
					<th style="width:220px;">日期</th> 
					<th style="width:150px;">操作</th>
				</tr>
				<c:forEach items="${rlist.datalist }" var="data">
					<tr id="list">
						<td style="text-align:left;">
							<input type="checkbox" class="check"  data-resid="${data.path }"/>
							<c:choose>
							<c:when test="${recordbag.name=='我的教案'||recordbag.name=='自制课件'||recordbag.name=='教学反思'}">
								<c:if test="${data.ext.type==0||data.ext.type==1||data.ext.type==2 }">
									<a href="${ctx}/jy/teachingView/view/lesson?type=${data.ext.type }&infoId=${data.ext.infoId }" target="_blank">${data.flago } <ui:sout value="${data.recordName}" length="18" needEllipsis="true"></ui:sout></a>
								</c:if>
								<c:if test="${data.ext.type==3 }">
									<a href="${ctx}/jy/teachingView/view/other/lesson?type=2&planId=${data.resId }" target="_blank">${data.flago } <ui:sout value="${data.recordName}" length="18" needEllipsis="true"></ui:sout></a>
								</c:if>
							</c:when>
							<c:when test="${recordbag.name=='教学文章'}">
								<a href="${ctx}/jy/teachingView/view/thesisview?id=${data.resId}" target="_blank">${data.flago } <ui:sout value="${data.recordName}" length="18" needEllipsis="true"></ui:sout></a>
							</c:when>
							<c:when test="${recordbag.name=='计划总结'}">
								<a href="${ctx}/jy/teachingView/view/${_CURRENT_USER_.id}/planSummary/${data.resId}?userId=${_CURRENT_USER_.id}" target="_blank">${data.flago } <ui:sout value="${data.recordName}" length="18" needEllipsis="true"></ui:sout></a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/jy/scanResFile?resId=${data.path }" target="_blank">${data.flago } <ui:sout value="${data.recordName}" length="18" needEllipsis="true"></ui:sout></a>
							</c:otherwise> </c:choose>
							<c:if test="${!empty data.desc}"><span class="weiping wei" data-desc="${data.desc }" data-name="${data.recordName}"></span></c:if> 
						</td>
						<c:if test="${recordbag.name=='教学反思'}"><td>
							<c:if test="${data.ext.type==2 }">课后反思</c:if><c:if test="${data.ext.type==3 }">其他反思</c:if>
						</td></c:if>
						<c:if test="${recordbag.name=='教学文章'}"><td>
							${data.ext.type }
						</td></c:if>
						<c:if test="${recordbag.name=='计划总结' }"><td>
							${data.ext.type }
						</td></c:if>
						<td><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd" /></td> 
						<td>
							<span class="look_up" style="margin-left:47px;" data-title="${data.recordName}" data-name="${data.ext.name }" data-publisher="${data.ext.bookShortname}" data-type="${data.ext.type}" data-resType="${data.resType}" data-time="${data.ext.time}"  data-size="${data.ext.size}" data-ext="${data.flags}" ></span>
							<a href="<ui:download filename="${data.recordName}" resid="${data.path}"></ui:download>"><span class="download"></span></a> 
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
				<ui:page url="${ctx}jy/history/${schoolYear}/czda/showList" data="${rlist}" />
				<input type="hidden" class="currentPage" name="page.currentPage">
				<input type="hidden" name="name" value="${recordbag.name }"/>
				<input type="hidden" name="id" value="${recordbag.id }"/>
				<input type="hidden" name="year" value="${schoolYear}"/>
			</form>
		</c:if>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','recordbag'],function($){
		//调用方法修改面包屑
		window.parent.changeNav(_WEB_CONTEXT_+"/jy/history/${schoolYear}/czda/index",'${recordbag.name}');
	});
</script>
</html>