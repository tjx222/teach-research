<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen"> 
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="成长档案袋"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${not isTeacher }">
		<jy:nav id="jyyl_js">
			<jy:param name="phaseId" value="${param.phaseId }"></jy:param>
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
		</jy:nav> > <a href="${ctx}/jy/teachingView/teacher/list_recordBag?spaceId=${userSpace.id}&termId=${searchVo.termId}">成长档案袋</a> > ${name }
		</c:if>
		<c:if test="${isTeacher }">
		<jy:nav id="jyyl"><jy:param name="spaceId" value="${param.spaceId }"></jy:param></jy:nav> > <a href="${ctx}/jy/teachingView/teacher/list_recordBag?spaceId=${userSpace.id}&termId=${searchVo.termId}">成长档案袋</a> > ${name }
		</c:if>
	</div>
	<div class="companion_content companion_content_other" style="margin-top:0;">
		<div class="teachingTesearch_jitibeike_con" style="margin-top:0;">
			<div class="teachingTesearch_jitibeike_outBox">
				<div class="teachingTesearch_jitibeike_outBox_type show">
					<table border="1" class="teachingTesearch_jitibeike_table">
					  <tr>
					    <th style="width:280px;">活动主题</th>
					    <th style="width:100px;">参与学科</th>
					    <th style="width:120px;">参与年级</th>
					    <th style="width:60px;">发起人</th>
					    <th style="width:160px;">活动时限</th>
					    <th style="width:40px;">评论数</th>
					  </tr>
						<c:forEach  items="${data.datalist  }" var="activity"><!-- 有精选记录 -->
						  	<tr>
							    <td title="${activity.recordName}" style="text-align:left;" >${activity.flago}<a href="${ctx}jy/teachingView/view/chayueActivity?activityId=${activity.resId}&typeId=${activity.flags}" style="color:#014efd;cursor:pointer;" target="_blank"><ui:sout value="${activity.recordName}" length="23"  needEllipsis="true" ></ui:sout><c:if test="${activity.desc !=''}"><span class="tspan"  resId="${activity.recordId }"  resName="${activity.flago }${activity.recordName }"   id="sp" title="${activity.desc }"></span></c:if></a></td>
							    <td title="${activity.ext.subjectName}"><ui:sout value="${activity.ext.subjectName}" length="20"  needEllipsis="true" > </ui:sout> </td>
							    <td title="${activity.ext.gradeName}"><ui:sout value="${activity.ext.gradeName}" length="20"  needEllipsis="true" > </ui:sout> </td>
							    <td title="${activity.ext.mainUserName}"><ui:sout value="${activity.ext.mainUserName}" length="10"  needEllipsis="true" > </ui:sout></td>
							    <td>${activity.ext.startTime}<c:if test="${empty activity.ext.startTime}"> ~ </c:if>至<c:if test="${empty activity.ext.endTime}"> ~ </c:if>${activity.ext.endTime}</td>
							    <td>${activity.ext.commentsNum}</td>
							  </tr>
						</c:forEach>
					</table>
					<c:if test="${data.datalist =='[]' }"><!--未精选 -->
					<div class="nofile">
						<div class="nofile1">还没有精选的资源</div>
					</div>
					</c:if>
				</div>
				<div class="pages">
				<form name="pageForm"  method="post">
					<ui:page url="${ctx}jy/teachingView/teacher/detail_recordBag?bagId=${id}&type=${type}"  data="${data }"  />
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" name="bagId" value="${id}">
					<input type="hidden" name="type" value="${type}">
				</form>
				</div>
			</div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>
