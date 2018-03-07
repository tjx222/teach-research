<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="听课记录信息"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen"> 
	<ui:require module="history/js"></ui:require>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
</head>
<body style="background: #fff;"> 
	<div class="calendar_year_center">
	<div class="page_option"> 
		<div class="left_title">查阅集体备课</div>
		<form id="search_form" action="jy/history/${year}/cygljl/check_activity" method="post">
			<div class="a">
				<select id="sear_term" class="chosen-select-deselect class_teacher" name="termId" style="width: 101px; height: 25px;">
					<option value="" ${empty searchVo.termId? 'selected':'' }>全学年</option>
					<option value="0" ${searchVo.termId==0? 'selected' : '' }>上学期</option>
					<option value="1" ${searchVo.termId==1? 'selected' : '' }>下学期</option>
				</select>
			</div>  
			<div class="serach">
				<input type="text" class="ser_txt" name="searchStr" value="${searchVo.searchStr}"/>
				<input type="button" class="ser_btn"/>
			</div>
			<input type="hidden" class="ja_flago" name="flago" value="${checkInfo.flago}"> 
			<input type="hidden" name="spaceId" value="${searchVo.spaceId}"> 
			<input type="hidden" name="typeId" id="typeId" value="${searchVo.typeId}"> 
			<input type="hidden" name="year" id="year" value="${year}"> 
		</form>
	</div>
	<div class="manage_check_wrap">
		<div class="manage_check_tab">
			<div class="menu_switch">
				<span class="menu_switch_fq <c:if test="${checkInfo.flago==0 || checkInfo.flago==null}">menu_switch_act</c:if>">已查阅(${checkInfoList.page.totalCount})</span>
				<span class="menu_switch_cy <c:if test="${checkInfo.flago==1}">menu_switch_act</c:if>">查阅意见(${activityOptionCount})</span>
			</div>
			<div class="resources_table_wrap">
				<div class="resources_table_wrap_tab" <c:if test="${checkInfo.flago==1}">style="display:none;"</c:if>>
					<c:if test="${not empty activityList.datalist}">
						<table class="resources_table">
							<tr>
								<th style="width:238px;text-align:left;padding-left:5px;">活动主题</th>
								<th style="width:87px;">学科</th>
								<th style="width:87px;">参与年级</th>
								<th style="width:66px;">发起人</th>
								<th style="width:175px;">活动时限</th> 
								<th style="width:50px;">评论数</th>  
								<th style="width:67px;">分享状态</th>
							</tr>
								<c:forEach items="${activityList.datalist}" var="activity">
									  <tr>
									    <td style="text-align:left;" title="${activity.activityName}" style="text-align:left;">
									    	<a href="${ctx}/jy/teachingView/view/chayueActivity?activityId=${activity.id}&typeId=${activity.typeId}&flags=manager" target="_blank">
									    		<strong>【${activity.typeName}】</strong>
										    	<span style="cursor: pointer;width:225px;color:#5378F8;"><ui:sout value="${activity.activityName}" length="20" needEllipsis="true" ></ui:sout></span>
									    	</a>
									    </td>
									    <td title="${activity.subjectName}">
										    <ui:sout value="${activity.subjectName}" length="12" needEllipsis="true" ></ui:sout>
									    </td>
									    <td title="${activity.gradeName}">
									    	<ui:sout value="${activity.gradeName}" length="12" needEllipsis="true" ></ui:sout>
									    </td>
									    <td title="${activity.organizeUserName}">
									    	<ui:sout value="${activity.organizeUserName}" length="12" needEllipsis="true" ></ui:sout>
									    </td>
									    <td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
									    <td>${activity.commentsNum}</td>
									    <td>	
								    		<c:if test="${activity.isShare }">已分享</c:if>
								    		<c:if test="${!activity.isShare }">未分享</c:if>
									    </td>
									  </tr>
								  </c:forEach>
						</table>
					</c:if>
					<c:if test="${empty activityList.datalist}">
						<div class="cont_empty">
						    <div class="cont_empty_img"></div>
						    <div class="cont_empty_words">没有资源哟！</div> 
						</div>
					</c:if>
					<div class="clear"></div>
					<div class="">
						<form class="pageForm" name="pageForm" method="post">
							<ui:page url="${ctx}/jy/history/${year}/cygljl/check_activity" data="${checkInfoList}"/>
							<input type="hidden" class="currentPage" name="currentPage">
							<input type="hidden" name="flago" value="0">
							<input type="hidden" name="spaceId" value="${searchVo.spaceId}"> 
							<input type="hidden" class="ja_searchStr" name="searchStr" value="${searchVo.searchStr}">
							<input type="hidden" class="ja_termId" name="termId" value="${searchVo.termId}"> 
						</form>
					</div>
				</div>
				<div class="resources_table_wrap_tab" <c:if test="${checkInfo.flago==0 || checkInfo.flago==null}">style="display:none;"</c:if>>   
					<c:if test="${empty checkMapList}">
						<div class="cont_empty">
						    <div class="cont_empty_img"></div>
						    <div class="cont_empty_words">没有资源哟！</div> 
						</div>
					</c:if>
					<c:if test="${not empty checkMapList}">
						<c:forEach items="${checkMapList }" var="checkMap">
							<div class="consult_opinion">
								<div class="consult_opinion_title">
								<span>
									<a href="${ctx}/jy/teachingView/view/chayueActivity?activityId=${checkMap.checkInfo.resId}" target="_blank">
										${checkMap.checkInfo.title}
									</a>
								 </span></div>
									<c:forEach items="${checkMap.optionMapList }" var="optionMap">
										<div class="consult_opinion_wrap">
											<div class="consult_opinion_cont">
												${optionMap.parent.content }
											</div>
											<div class="consult_opinion_date">
												<fmt:formatDate value="${optionMap.parent.crtTime  }" pattern="yyyy-MM-dd"/>
											</div> 
										</div>
										<div class="border_dashed"></div>
										<c:forEach items="${optionMap.childList }" var="child">
										<div class="consult_opinion_reply">
											<jy:di key="${child.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
											<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
											<div class="consult_opinion_reply_cont">
											${u.name }说：${child.content }
											</div>
											<div class="consult_opinion_reply_date">
											<fmt:formatDate value="${child.crtTime   }" pattern="yyyy-MM-dd"/>
											</div> 
										</div> 
										</c:forEach>
										<div class="clear"></div>
									</c:forEach>
								</div>
						</c:forEach>
					</c:if>
					<div class="">
						<form class="pageForm" name="pageForm1" method="post">
							<ui:page url="${ctx}/jy/history/${year}/cygljl/check_activity" data="${checkOptionList}"/>
							<input type="hidden" class="currentPage" name="currentPage">
							<input type="hidden" id="" name="flago" value="1"> 
							<input type="hidden" class="ja_searchStr" name="searchStr" value="${searchVo.searchStr}"> 
							<input type="hidden" name="spaceId" value="${searchVo.spaceId}"> 
							<input type="hidden" class="ja_termId" name="termId" value="${searchVo.termId}"> 
						</form>
					</div>
				</div>	
				</div>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script>

<script type="text/javascript">
require(['jquery','managerCheck'],function(){});
</script>
</html>
