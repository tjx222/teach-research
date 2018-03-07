<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="听课记录"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="听课记录"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${userSpace.id!=_CURRENT_SPACE_.id }">
		<c:if test="${empty searchVo.flagz }">
		<jy:nav id="jyyl_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
		</jy:nav>
		</c:if>
		<c:if test="${searchVo.flagz=='grade' }">
		<jy:nav id="jyyl_grade_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
			<jy:param name="gradeName" value="${gradeName }"></jy:param>
		</jy:nav>
		</c:if>
		<c:if test="${searchVo.flagz=='subject' }">
		<jy:nav id="jyyl_subject_js">
			<jy:param name="userName" value="${userSpace.username }"></jy:param>
			<jy:param name="url" value="${ctx}jy/teachingView/manager/teachingView_t_detail?flagz=${searchVo.flagz }&termId=${searchVo.termId}&gradeId=${userSpace.gradeId }&subjectId=${userSpace.subjectId }&spaceId=${userSpace.id }"></jy:param>
			<jy:param name="subjectName" value="${subjectName }"></jy:param>
		</jy:nav>
		</c:if> > 听课记录
		</c:if>
		<c:if test="${userSpace.id==_CURRENT_SPACE_.id }">
		<jy:nav id="jyyl"></jy:nav> > 听课记录
		</c:if>
	</div>
	<div class="teachingTesearch_jitibeike_content">
		<c:if test="${userSpace.id!=_CURRENT_SPACE_.id }">
		<div class="teachingTesearch_jitibeike_title">
			<dl class="teachingTesearch_jitibeike_title_News">
				<dt class="photo"><ui:photo src="${user.photo }" /></dt>
				<dt class="photo_mask"><img src="${ctxStatic }/modules/teachingview/images/state.png"/></dt>
				<dd><span class="teacher_name">教师姓名</span><span class="teacher_identity">一年级语文教师</span></dd>
			</dl>
		</div>
		</c:if>
		<div class="teachingTesearch_jitibeike_con" style="margin-top:0;">
			<div class="teachingTesearch_jitibeike_outBox">
				<div class="teachingTesearch_jitibeike_outBox_type show">
					<table cellpadding="0" cellspacing="0" class="teachingTesearch_jitibeike_table">
					<tr>
						<th style="width:45%;">课题</th>
						<th style="width:15%;">年级学科</th>
						<th style="width:10%;">授课人</th>
						<th style="width:10%;">听课节数</th>
						<th style="width:10%;">听课时间</th>
						<th style="width:10%;">分享状态</th>
					</tr>
					<c:forEach var="record" items="${recordList.datalist }">
						<tr>
							<td style="text-align:left;">
								<a href="${ctx}jy/teachingView/view/LectureRecord?id=${record.id}" target="_blank">
									<b>【${record.type==0?'校内':'校外'}】</b>
									<span title="${record.topic }">${record.topic }</span>
								</a>
							</td>
							<td <c:if test="${record.type==0 }">title="${record.grade }${record.subject }"</c:if>><c:if test="${record.type==0 }">${record.grade }${record.subject }</c:if><c:if test="${record.type==1 }">——</c:if></td>
							<td <c:if test="${record.type==0 }">title="${record.teachingPeople }"</c:if>><c:if test="${record.type==0 }">${record.teachingPeople }</c:if><c:if test="${record.type==1 }">——</c:if></td>
							<td>${record.numberLectures }</td>
							<td><fmt:formatDate value="${record.crtDttm}" pattern="MM-dd"/></td>
							<td class="jitibeike_td7">${record.isShare==0?'未分享':'已分享' }</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${empty recordList.datalist}">
					<!-- 无文件 -->
		  				<div class="nofile">
						<div class="nofile1">
							暂时还没有数据，稍后再来查看吧！
						</div>
					</div>
				</c:if>
				</div>
			</div>
			<form id="pageForm"  name="pageForm" method="post">
				<ui:page url="${ctx}jy/teachingView/teacher/list_listen" data="${recordList}"  />
				<input id="currentPage" type="hidden" class="currentPage" name="page.currentPage" >
				<input type="hidden" name="termId" value="${searchVo.termId }">
				<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
				<input type="hidden" name="flagz" value="${searchVo.flagz }">
			</form>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script>
$(".teachingTesearch_jitibeike_table").each(function(){
	$(this).find("tr").last().find("td").css("border-bottom","none");
})
$(".teachingTesearch_jitibeike_table").each(function(){
	$(this).find("tr").first().find("td").css("text-align","left");
});
</script>
</html>
