<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="集体备课"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/datetime/css/zepto.mdatetimer.css" media="screen">
		<script type="text/javascript" src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>集体备课
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_bottom1" id="wrap">
				<div id="scroller">	
				<div class="activity_tbja_wrap"> 
					<form id="tbjaForm">
					<ui:token></ui:token>
					<div class="activity_wrap_right">
						<div class="ja_content">
							<div class="ja"></div>
							<input value="${act.id}" id="id" name="id" type="hidden">
							<input id="typeId" name="typeId" type="hidden" value="1">
							<h3>确定主备人及教案<span>*</span></h3>
							<h4>学科：
							<c:forEach items="${subjectList}" var="sub">
									<span>${sub.name}</span>
							</c:forEach>
							</h4>
							<div class="ja_content_class">
							<input name="mainUserSubjectId" id="mainUserSubjectId" type="hidden" value="${_CURRENT_SPACE_.subjectId }"/>
							年级：
								<c:if test="${fn:length(gradeList) > 1}">
									<select  name="mainUserGradeId" id="mainUserGradeId" class="validate[required] chosen-select-deselect" <c:if test="${act.commentsNum>0 || haveTrack }">disabled="disabled"</c:if>>
										 <option value="">请选择年级</option>
										 <c:forEach items="${gradeList}" var="grade">
										 <option value="${grade.id}" <c:if test="${grade.id==act.mainUserGradeId}"> selected="selected" </c:if>>${grade.name}</option>
										 </c:forEach>
									 </select>
								</c:if>
								<c:if test="${fn:length(gradeList) < 2}">
									<input type="hidden" id="editmainuserid" value="${act.mainUserId}"/>
									<c:forEach items="${gradeList}" var="grade">
									<input type="hidden"  name="mainUserGradeId" id="mainUserGradeId" value="${grade.id }"/>
										${grade.name}
									</c:forEach>
									<script></script>
								</c:if>
							</div>
							<div class="ja_content_zbr">
							主备人：
								<select name="mainUserId" id="mainUserId" class="chosen-select-deselect validate[required]" <c:if test="${act.commentsNum>0 || haveTrack}">disabled="disabled"</c:if>>
									 <option value="">请选择主备人</option>
									 <c:forEach items="${mainUserList}" var="user">
										<option value="${user.userId}" <c:if test="${user.userId==act.mainUserId}"> selected="selected" </c:if>>${user.username}</option>
									 </c:forEach>
								 </select>
							</div>
							<div class="ja_content_zbtopic">
							主备课题：
								<select name="infoId" id="chapterId" class="chosen-select-deselect validate[required]" <c:if test="${act.commentsNum>0 || haveTrack}">disabled="disabled"</c:if> >
									 <option value="">请选择主备课题</option>
									 <c:forEach items="${chapterList}" var="chapter">
										<option value="${chapter.id}" id="option_${chapter.id}" <c:if test="${chapter.id==act.infoId}"> selected="selected" </c:if>>${chapter.lessonName}</option>
									</c:forEach>
								 </select>
							</div>
						</div>
						<div class="range1_content">
							<div class="range1"></div>
							<h3>确定参与范围<span>*</span></h3>
							<div class="range_class">
								<div class="label_div">年级：</div>
								<c:if test="${fn:length(gradeList) > 1}">
								<input type="hidden" id="gradeIds" name="gradeIds" />
								<c:forEach items="${gradeList}" var="grade">
									<%-- <p gradeId="${grade.id}"><a>${grade.name}</a></p> --%>
									<input type="button" class="p_option" gradeId="${grade.id}" value="${grade.name}">
								</c:forEach>
								</c:if>
								<c:if test="${fn:length(gradeList) < 2}">
								<c:forEach items="${gradeList}" var="grade">
								 <input type="hidden" name="gradeIds" value=",${grade.id },"/>
									<a>${grade.name}</a>
								</c:forEach>
							   </c:if>
							</div>
						</div>
						<div class="activity_duration_content">
							<div class="activity_duration1"></div>
							<h3>活动时限<span>（您可以不设置活动结束时限）</span></h3>
							<div class="activity_input">
							时间：
								<%-- <input placeholder="开始时间" type="text" style="height:22px;" name="startTime" id="startTime" class="validate[required,custom[dateTimeFormat]]"
					              value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m',maxDate:'#F{$dp.$D(\'endTime\')}'})" <c:if test="${act.commentsNum>0 || haveTrack}">disabled="disabled"</c:if>/> --%>
								  <%-- <input placeholder="结束时间" type="text" style="height:22px;" name="endTime" id="endTime" 
								  value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"/> --%>
								<c:if test="${act.commentsNum>0 || haveTrack}"><fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>
								<input type="hidden" name="startTime"  value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>"/>
								</c:if>
								<c:if test="${!(act.commentsNum>0 || haveTrack)}">
								<input type="text" id="picktime_start"  placeholder="开始时间" name="startTime"  value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>" readonly="readonly" />
								</c:if>
								&nbsp;一&nbsp;
								<input type="text" id="picktime_end"  placeholder="结束时间" name="endTime" value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>" readonly="readonly" />
							</div>
						</div>
						<div class="activity_theme_content">
							<div class="activity_theme1"></div>
							<h3>活动主题<span>*</span></h3>
							<input name="activityName" id="activityName" type="text" class="name" placeholder="输入活动主题（80字内）" value="${act.activityName}">
							<h3>活动要求<span>*</span></h3>
							<textarea cols="80" rows="10" class="validate[required,maxSize[200]] name1 remark" name="remark" id="remark">${act.remark}</textarea>
						</div>
					</div>
					</form>
					<div class="clear"></div>
					<div class="act_tbja_btn">
						<c:if test="${act.id==null || act.status==0 }">
						<input type="button" class="release">
						<input type="button" class="deposit_draft">
						</c:if>
						<c:if test="${act.id!=null && act.status!=0 }">
						<input type="button" class="modify"><!-- 修改 -->
						<input type="button" class="no-modify"><!-- 不改了 -->
						</c:if>
					</div>
				</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','edit'],function(){
		$(document).ready(function(){
			//复选框加载
			var sid = '${act.gradeIds}';	
			if(sid != '' && sid != ',') {
				var sids = sid.split(",");
				 $(".range_class").find("input[type='button']").each(function(){
					 for(var i=0;i<sids.length;i++) {
						 if($(this).attr("gradeId") == sids[i]) {
							 $(this).addClass("p_act");
							 break;
						 }
					 }
				 });
			}
		});	
	}); 
	
</script>
</html>