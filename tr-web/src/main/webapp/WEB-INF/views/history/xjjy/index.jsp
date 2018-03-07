<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="历年资源-校际教研"></ui:htmlHeader>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/history/css/history.css"> 
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/history/css/common.css"> 
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
<div class="calendar_year_center">
	<div class="page_option"> 
		<div class="a">
			<select class="chosen-select-deselect class_teacher" style="width: 132px; height: 25px;">
				<c:choose>
					<c:when test="${not empty userSpaces }">
						<c:forEach items="${userSpaces }" var="us">
							<option ${result.sv.spaceId==us.id ? 'selected="selected"' : '' }  value="${us.id }">${us.spaceName }</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option>请选择职务</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<div class="left_border"></div>
		<div class="a1">
			<select class="chosen-select-deselect full_year" style="width: 90px; height: 25px;">
				<option value="">全学年</option>
				<option ${result.sv.termId==0 ? 'selected="selected"' : '' } value="0">上学期</option>
				<option ${result.sv.termId==1 ? 'selected="selected"' : '' } value="1">下学期</option>
			</select>
		</div>    
		<div class="serach">
			<input type="text" class="ser_txt" value="${result.sv.searchStr }"/>
			<input type="button" class="ser_btn" />
		</div>
	</div>
	<c:if test="${not empty result}"> 
	<div class="menu_switch">
		<c:if test="${result.role=='canCreate' }">
			<c:if test="${result.sv.typeId==1 }">
				<span class="menu_switch_fq menu_switch_act">已发起(${result.currentCount })</span>
				<span class="menu_switch_cy change_type_event" typeid="2">可参与(${result.otherCount })</span>
			</c:if>
			<c:if test="${result.sv.typeId==2 }">
				<span class="menu_switch_fq change_type_event" typeid="1">已发起(${result.otherCount })</span>
				<span class="menu_switch_cy menu_switch_act">可参与(${result.currentCount })</span>
			</c:if>
		</c:if>
		<c:if test="${result.role=='notCreate' }">
			<span class="menu_switch_cy ">可参与(${result.currentCount })</span>
		</c:if>
		<input type="button" value="教研进度表" class="schedule_btn" typecode="jyjdb" year="${result.sv.schoolYear}" spaceId="${result.sv.spaceId}" canCreate="${result.role}"/>
		<input type="button" value="校际教研圈" class="schedule_btn" typecode="xjjyq" year="${result.sv.schoolYear}" spaceId="${result.sv.spaceId}" style="margin-right: 10px;"/>
	</div>
	<div class="resources_table_wrap">
	<c:if test="${result.role=='canCreate' && result.sv.typeId==1 }">
		<div class="resources_table_wrap_tab"> 
			<c:if test="${not empty result.data.datalist}">
				<table class="resources_table">
					<tr>
						<th style="width:220px;text-align:left;padding-left:5px;">活动主题</th>
						<th style="width:87px;">教研圈</th>
						<th style="width:87px;">参与学科</th>
						<th style="width:87px;">参与年级</th>
						<th style="width:175px;">活动时限</th> 
						<th style="width:50px;">评论数</th>  
						<th style="width:67px;">分享状态</th>
					</tr>
					<c:forEach items="${result.data.datalist }" var="activity">
						<tr>
							<td style="text-align:left;">
								【${activity.typeName }】
								<span title="${activity.activityName}" style="cursor: pointer;" onclick="chakan('${activity.id}','${activity.typeId}')"><ui:sout value="${activity.activityName}" length="20" needEllipsis="true"></ui:sout></span>
							</td>
							<td class="partake" ><ui:sout value="${activity.schoolTeachCircleName }" length="12" needEllipsis="true"></ui:sout>
								<span>
									<b title="${activity.schoolTeachCircleName }" style="font-weight: bold;border-bottom:1px #C5BDBD solid;">${activity.schoolTeachCircleName }</b>
									<c:forEach items="${activity.stcoList }" var="stco">
										<b>
											<a title="${stco.orgName }">${stco.orgName }</a>
										</b>
									</c:forEach>
								</span> 
							</td>
							<td title="${activity.subjectName }"><ui:sout value="${activity.subjectName }" length="12" needEllipsis="true"></ui:sout></td>
							<td title="${activity.gradeName }"><ui:sout value="${activity.gradeName }" length="12" needEllipsis="true"></ui:sout></td>
							<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td> 
							<td>${activity.commentsNum}</td>
							<td>${activity.isShare?"已分享":"未分享"}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${empty result.data.datalist}">
				<div class="cont_empty">
				    <div class="cont_empty_img"></div>
				    <div class="cont_empty_words">没有资源哟！</div> 
				</div>
			</c:if>
		</div>
	</c:if>
	<c:if test="${result.role=='notCreate' || (result.role=='canCreate' && result.sv.typeId==2)}">
		<div class="resources_table_wrap_tab" >    
			<c:if test="${not empty result.data.datalist}">
				<table class="resources_table">
					<tr>
						<th style="width:230px;text-align:left;padding-left:5px;">活动主题</th>
						<th style="width:76px;">教研圈</th>
						<th style="width:90px;">参与学科</th>
						<th style="width:80px;">参与年级</th>
						<th style="width:60px;">发起人</th> 
						<th style="width:177px;">活动时限</th>   
						<th style="width:60px;">评论数</th>  
					</tr>
					<c:forEach items="${result.data.datalist }" var="activity">
						<tr>
							<td style="text-align:left;">
								【${activity.typeName }】
								<span title="${activity.activityName}" style="cursor: pointer;" onclick="chakan('${activity.id}','${activity.typeId}')"><ui:sout value="${activity.activityName}" length="20" needEllipsis="true"></ui:sout></span>
							</td>
							<td  class="partake"><ui:sout value="${activity.schoolTeachCircleName }" length="12" needEllipsis="true"></ui:sout>
								<span id="orgSpan_${activity.id}">
									<b title="${activity.schoolTeachCircleName }" style="font-weight: bold;border-bottom:1px #C5BDBD solid;">${activity.schoolTeachCircleName }</b>
									<c:forEach items="${activity.stcoList }" var="stco">
										<b>
											<a title="${stco.orgName }">${stco.orgName }</a>
										</b>
									</c:forEach>
								</span>
							</td>
							<td title="${activity.subjectName }"><ui:sout value="${activity.subjectName }" length="12" needEllipsis="true"></ui:sout></td>
							<td title="${activity.gradeName }"><ui:sout value="${activity.gradeName }" length="12" needEllipsis="true"></ui:sout></td>
							<td title="${activity.organizeUserName }"><ui:sout value="${activity.organizeUserName }" length="10" needEllipsis="true"></ui:sout></td> 
							<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td> 
							<td>${activity.commentsNum}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${empty result.data.datalist}">
				<div class="cont_empty">
				    <div class="cont_empty_img"></div>
				    <div class="cont_empty_words">没有资源哟！</div> 
				</div>
			</c:if>
		</div>
	</c:if>
		<div class="pages"> 
			<!--设置分页信息 -->
			<form id="pageForm" name="pageForm" method="post">
				<ui:page url="${ctx }jy/history/${result.sv.schoolYear}/xjjy/index" data="${result.data}"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
				<input type="hidden" id="termId" name="termId"  value="${result.sv.termId }">
				<input type="hidden" id="typeId" name="typeId" value="${result.sv.typeId }">
				<input type="hidden" id="spaceId" name="spaceId" value="${result.sv.spaceId }">
				<input type="hidden" id="searchStr" name="searchStr" value="${result.sv.searchStr }">
			</form>
		</div>
	</div>
	</c:if>
	<c:if test="${empty result }">
		<div class="cont_empty">
		    <div class="cont_empty_img"></div>
		    <div class="cont_empty_words">没有资源哟！</div> 
		</div>
	</c:if>
</div>
</body>

<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','xjjy'],function(){}); 
</script> 
</html>