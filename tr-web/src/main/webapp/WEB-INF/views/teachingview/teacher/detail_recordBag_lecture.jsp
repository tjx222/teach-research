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
		<c:if test="${isTeacher}">
		<jy:nav id="jyyl"><jy:param name="spaceId" value="${param.spaceId }"></jy:param></jy:nav> > <a href="${ctx}/jy/teachingView/teacher/list_recordBag?spaceId=${userSpace.id}&termId=${searchVo.termId}">成长档案袋</a> > ${name }
		</c:if>
	</div>
	<div class="companion_content companion_content_other" style="margin-top:0;">
		<div class="teachingTesearch_jitibeike_con" style="margin-top:0;">
			<div class="teachingTesearch_jitibeike_outBox">
				<div class="teachingTesearch_jitibeike_outBox_type show">
					<table border="1" class="teachingTesearch_jitibeike_table ">
						  <tr>
						    <th style="width:45%;">课题</th>
				    		<th style="width:15%;">年级学科</th>
				    		<th style="width:15%;">授课人</th>
				    		<th style="width:10%;">听课节数</th>
				   			<th style="width:15%;">听课时间</th>
						  </tr>
						<c:forEach  items="${data.datalist  }" var="activity"><!-- 有精选记录 -->
							  	<tr>
								    <td title="${activity.recordName}" style="text-align:left;" >${activity.flago}<a href="${ctx}jy/teachingView/view/LectureRecord?id=${activity.resId}" target="_blank" style="color:#5378F8;cursor:pointer;"><ui:sout value="${activity.recordName}" length="28" ></ui:sout><c:if test="${activity.desc !=''}"><span class="tspan"  resId="${activity.recordId }"  resName="${activity.flago }${activity.recordName }"    id="sp" title="${activity.desc }"></span></c:if></a></td>
								    <td title="${activity.ext.grade}">${activity.ext.grade}  </td>
								    <td title="${activity.ext.teachPeople}">${activity.ext.teachPeople}</td>
								    <td>${activity.ext.num}</td>
								    <td class="jitibeike_td7">${activity.time}</td>
								  </tr>
							</c:forEach>
					</table>
					<c:if test="${data.datalist =='[]' }"><!--未精选 -->
					<div class="nofile">
						<div class="nofile1">还没有精选的资源</div>
					</div>
					</c:if>
				</div>
			</div>
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
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script>
$(".teachingTesearch_jitibeike_table").each(function(){
	$(this).find("tr").last().find("td").css("border-bottom","none");
});
$(".teachingTesearch_jitibeike_table").each(function(){
	$(this).find("tr").first().find("td").css("text-align","left");
});
</script>
</html>
