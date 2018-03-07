<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="听课记录"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="听课记录"></ui:tchTop>
	</div>
	<jy:di key="${searchVo.userId}" className="com.tmser.tr.uc.service.UserService" var="user2"/>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_m_cylb">
			<jy:param name="username" value="${user2.name}"></jy:param>
			<jy:param name="url" value="jy/teachingView/manager/m_details?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}"></jy:param>
			<jy:param name="name" value="听课记录"></jy:param>
			<jy:param name="urlxqlb" value="jy/teachingView/manager/m_lecture_record?userId=${user2.id}&termId=${searchVo.termId}&orgId=${searchVo.orgId}&phaseId=${searchVo.phaseId}"></jy:param>
		</jy:nav>
	</div>
	<div class="teachingTesearch_jitibeike_content">
		<div class="teachingTesearch_jitibeike_title">
			<dl class="teachingTesearch_jitibeike_title_News">
				<dt class="photo">
					<span><ui:photo src="${user2.photo }"></ui:photo></span>
				</dt>
				<dt class="photo_mask">
					<img src="${ctxStatic }/modules/teachingview/images/state.png"/>
				</dt>
				<dd>
					<span class="teacher_name">${user2.name}</span>
					<span class="teacher_identity">
					<c:forEach items="${searchVo.userSpaceList}" var="space" varStatus="c">
						<c:if test="${fn:length(searchVo.userSpaceList)==c.count}">
							${space.spaceName}
						</c:if>
						<c:if test="${fn:length(searchVo.userSpaceList)!=c.count}">
							${space.spaceName}、
						</c:if>
					</c:forEach>
					</span>
				</dd>
			</dl>
		</div>
		<div class="teachingTesearch_jitibeike_con">
			<div class="teachingTesearch_jitibeike_outBox">
					<div class="teachingTesearch_jitibeike_outBox_type show">
						<c:if test="${not empty lectureData.datalist }">
						<table cellpadding="0" cellspacing="0" class="teachingTesearch_jitibeike_table">
							<tr>
								<th style="width:42%;">课题</th>
								<th style="width:10%;">年级学科</th>
								<th style="width:10%;">授课人</th>
								<th style="width:8%;">听课节数</th>
								<th style="width:15%;">听课时间</th>
								<th style="width:10%;">分享状态</th>
							</tr>
								<c:forEach var="kt" items="${lectureData.datalist }">
									  <tr id="${kt.id}">
									     <td class="jitibeike_td1">
									     	<a href="${ctx}jy/teachingView/view/LectureRecord?id=${kt.id}" target="_blank">
											     <b>${kt.type==0?'【校内】':'【校外】'}</b>
											     <span title="${kt.topic}"><ui:sout value="${kt.topic}" length="26" needEllipsis="true"/></span>
										     </a>
									     </td>
									    <td class="jitibeike_td2">
									    	<c:if test="${kt.type=='0'}">
									    		${kt.grade}${kt.subject}
									    	</c:if>
									    	<c:if test="${kt.type=='1'}">
									    		${empty kt.gradeSubject?'-':kt.gradeSubject}
									    	</c:if>
									    </td>
									    <td class="jitibeike_td3">${empty kt.teachingPeople?'-':kt.teachingPeople}</td>
									    <td class="jitibeike_td4">${kt.numberLectures}</td>
									    <td class="jitibeike_td5">
									    	<fmt:setLocale value="zh"/>
			 								<fmt:formatDate value="${kt.lectureTime}" pattern="MM-dd"/>
									    </td>
									    <td class="jitibeike_td7">
									    	<c:if test="${kt.isShare==0}">未分享</c:if>
									    	<c:if test="${kt.isShare==1}">已分享</c:if>
									    </td>
									  </tr>
						  		</c:forEach>
						</table>
						</c:if>
						<c:if test="${empty lectureData.datalist }">
							<!-- 无文件 -->
			   				<div class="nofile">
								<div class="nofile1">
									暂时还没有数据，稍后再来查阅吧！
								</div>
							</div>
						</c:if>
					</div>
			</div>
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/teachingView/manager/m_lecture_record?userId=${searchVo.userId}&termId=${searchVo.termId}&phaseId=${searchVo.phaseId}&orgId=${searchVo.orgId}" data="${lectureData}"  />
					<input type="hidden" class="currentPage" name="page.currentPage">
				</form>		
			</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery','managerList'],function(){});
</script>
</html>
