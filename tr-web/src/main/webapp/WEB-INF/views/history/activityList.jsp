<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="">集体备课</ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/common.css"media="screen"> 
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
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
<form id="pageForm"  name="pageForm" method="post" action="${ctx}jy/history/${searchVo.schoolYear }/jtbk/index" onsubmit="dosearch();">
<input type="hidden" id="flags" name="flags" value=""/>
	<div class="page_option"> 
		<div class="a">
			<select class="chosen-select-deselect class_teacher" style="width: 132px; height: 25px;" name="spaceId">
				<c:choose>
					<c:when test="${not empty userSpaceList }">
						<c:forEach var="us" items="${userSpaceList }">
							<option value="${us.id }" <c:if test="${searchVo.spaceId==us.id }">selected="selected"</c:if> >${us.spaceName }</option>
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
			<select class="chosen-select-deselect full_year " style="width: 90px; height: 25px;" name="termId">
				<option value="">全学年</option>
				<option value="0"  <c:if test="${searchVo.termId==0 }">selected="selected"</c:if>>上学期</option>
				<option value="1"  <c:if test="${searchVo.termId==1 }">selected="selected"</c:if>>下学期</option>
			</select>
		</div>  
		<div class="serach">
			<input type="text" class="ser_txt" name="searchStr" value="${searchVo.searchStr }"/>
			<input type="button" class="ser_btn" />
		</div>
	</div>
	<div class="menu_switch">
		<c:if test="${jfn:checkSysRole(userSpace.sysRoleId,'BKZZ')|| jfn:checkSysRole(userSpace.sysRoleId,'XKZZ')}">
			<span flags="0" class="menu_switch_fq <c:if test='${empty searchVo.flags||searchVo.flags=="0" }'>menu_switch_act</c:if> ">发起(${activityCount_faqi })</span>
		</c:if>
		<span flags="1" class="menu_switch_cy <c:if test='${searchVo.flags=="1" }'>menu_switch_act</c:if>">可参与(${activityCount_canyu })</span>
	</div>
	<div class="resources_table_wrap">
		<c:if test="${empty searchVo.flags||searchVo.flags=='0' }">
		<c:if test="${!empty activityList_faqi.datalist}">
		<div class="resources_table_wrap_tab">
		
			<table class="resources_table">
				<tr>
					<th style="width:241px;text-align:left;padding-left:5px;">活动主题</th>
					<th style="width:87px;">学科</th>
					<th style="width:87px;">参与年级</th>
					<th style="width:175px;">活动时限</th> 
					<th style="width:50px;">评论数</th> 
					<th style="width:66px;">提交状态</th> 
					<th style="width:67px;">分享状态</th>
				</tr>
				<c:forEach var="activity" items="${activityList_faqi.datalist }">
					<tr>
						<td style="text-align:left;">
						<a href="${ctx}jy/teachingView/view/chayueActivity?activityId=${activity.id}&typeId=${activity.typeId}" target="_blank">
							【${activity.typeName }】<ui:sout value="${activity.activityName }" length="20" needEllipsis="true"></ui:sout> 
						</a>
							<c:if test="${activity.isAudit }"><span class="yue yue_right" activityId="${activity.id }"></span> </c:if>
						</td>
						<td title="${activity.subjectName }"><ui:sout value="${activity.subjectName }" length="12" needEllipsis="true"></ui:sout></td>
						<td title="${activity.gradeName }"><ui:sout value="${activity.gradeName }" length="12" needEllipsis="true"></ui:sout></td>
						<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
						<td>${activity.commentsNum }</td>
						<td>${activity.isSubmit?'已提交':'未提交' }</td>
						<td>${activity.isShare?'已分享':'未分享' }</td>
					</tr>
				</c:forEach>
				
			</table>
			<ui:page url="${ctx}jy/history/${searchVo.schoolYear }/jtbk/index" data="${activityList_faqi}"  />
			<input id="currentPage" type="hidden" class="currentPage" name="page.currentPage" value="1">
			<input type="hidden" name="termId" value="${searchVo.termId }">
			<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
		</div>
		</c:if>
		<c:if test="${empty activityList_faqi.datalist}">
			<div class="cont_empty">
				<div class="cont_empty_img"></div>
				<div class="cont_empty_words">您此学年没有资源哟！</div> 
			</div>
		</c:if>
		</c:if>
		
		<c:if test="${searchVo.flags=='1' }">
		<c:if test="${!empty activityList_canyu.datalist}">
		<div class="resources_table_wrap_tab">   
			<table class="resources_table">
				<tr>
					<th style="width:300px;text-align:left;padding-left:5px;">活动主题</th>
					<th style="width:90px;">学科</th>
					<th style="width:90px;">参与年级</th>
					<th style="width:88px;">发起人</th> 
					<th style="width:50px;">评论数</th>
					<th style="width:205px;">活动时限</th>  
				</tr>
				<c:forEach var="activity" items="${activityList_canyu.datalist }">
				<tr>
					<td style="text-align:left;">
					<a href="${ctx}jy/teachingView/view/chayueActivity?activityId=${activity.id}&typeId=${activity.typeId}" target="_blank">
						【${activity.typeName }】<ui:sout value="${activity.activityName }" length="25" needEllipsis="true"></ui:sout>
					</a>
					<c:if test="${activity.isAudit }"><span class="yue yue_right" activityId="${activity.id }"></span></c:if>
					</td>
					<td title="${activity.subjectName }"><ui:sout value="${activity.subjectName }" length="12" needEllipsis="true"></ui:sout></td>
					<td title="${activity.gradeName }"><ui:sout value="${activity.gradeName }" length="12" needEllipsis="true"></ui:sout></td>
					<td>${activity.organizeUserName }</td> 
					<td>${activity.commentsNum }</td>
					<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td> 
				</tr>
				</c:forEach>
			</table>
			<ui:page url="${ctx}jy/history/${searchVo.schoolYear }/jtbk/index" data="${activityList_canyu}"  />
			<input id="currentPage" type="hidden" class="currentPage" name="page.currentPage" value="1">
			<input type="hidden" name="termId" value="${searchVo.termId }">
			<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
		</div>
		</c:if>
		<c:if test="${empty activityList_canyu.datalist}">
			<div class="cont_empty">
				<div class="cont_empty_img"></div>
				<div class="cont_empty_words">您此学年没有资源哟！</div> 
			</div>
		</c:if>
		</c:if>
		
	</div>
	</form>
</div>
</body>
<script type="text/javascript">
	require(['jquery','activity'],function(){
		//调用方法修改面包屑
		window.parent.changeNav("集体备课");
		/*文本框提示语*/
	     $('.ser_txt').placeholder({
	       	 word: '输入关键词进行搜索'
	     });
	     $(".resources_table tr th").last().css({"border-right":"none"});
	     /* $(".look_up").click(function(){
			$('#look_up_dialog').dialog({
				width: 420,
				height: 317
			});
		}); */
	});
</script>
</html>