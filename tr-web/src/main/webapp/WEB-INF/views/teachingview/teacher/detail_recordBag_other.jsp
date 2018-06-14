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
		<div style="padding-top:30px;overflow:hidden;">
		<c:forEach  items="${data.datalist  }" var="record"><!-- 有精选记录 -->
			<div class="Growth_jx_1" >
				<dl class="dl"  onclick="scanResFile('${record.path }');">
				<c:choose>
				<c:when test="${record.desc!=null&&record.desc!='' }">
					<dd><ui:icon ext="${record.flags }"></ui:icon><span class="tspan"  resId="${record.recordId }"  resName="${record.flago }${record.recordName }"   title="${record.desc }"></span></dd>
				</c:when>
				<c:otherwise>
					<dd><ui:icon ext="${record.flags }"></ui:icon></dd>
				</c:otherwise>
				</c:choose>
					<dt>
						<span title="${record.flago }${record.recordName }"><ui:sout value="${record.flago }${record.recordName }" length="40"  needEllipsis="true"></ui:sout></span>
						<span>${record.time }</span>
					</dt>
				</dl>
			</div>
			</c:forEach>
			<c:if test="${data.datalist =='[]' }"><!--未精选 -->
			<div class="nofile" style="margin-top:150px;">
				<div class="nofile1">没有精选的档案资源</div>
			</div>
			</c:if>
		</div>
		<div class="pages">
			<form name="pageForm"  method="post">
			 		<ui:page url="${ctx}jy/teachingView/teacher/detail_recordBag?bagId=${id}&type=${type}&termId=${searchVo.termId }&spaceId=${userSpace.id }"  data="${data }"  />
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" name="bagId" value="${id}">
					<input type="hidden" name="type" value="${type}">
			</form>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>
