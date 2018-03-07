<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="集体备课"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
	
<ui:require module="activity/js"></ui:require>
<script type="text/javascript">
require(['jquery','editor','activityEdit'],function(){
});
</script>
</head>
<body style="background:#fff;">
<div style="overflow:hidden;">	
<form action="" id="tbjaForm" method="post">
<ui:token></ui:token>
<input value="${act.id}" id="id" name="id" type="hidden">
<input id="typeId" name="typeId" type="hidden" value="1">
	<div class='confirm_main_standby' id='confirm_main_standby'>
		<div class='confirm_main_standby_icon'></div>
		<div class='confirm_main_standby_right'>
			<h3 class='activity_require_right_h3'>确定主备人及教案：</h3> 
			<div class="sel_sch">
				<div class='subject_sel_wrap'>
					<div class='discipline'>
						<select class="validate[required] chosen-select-deselect subject_sel" style="width: 112px; height: 25px;" name="mainUserSubjectId" id="mainUserSubjectId" onchange="checkMainUserList();" <c:if test="${haveTrack }">disabled="disabled"</c:if>>
							<option value="">请选择学科</option>
							<c:forEach items="${subjectList}" var="subject">
								<option value="${subject.id}" <c:if test="${subject.id==act.mainUserSubjectId}"> selected="selected" </c:if>>${subject.name}</option>
							</c:forEach>
						</select> 
					</div>
				</div>
				
				<input type="hidden" id="editmainuserid" value="${act.mainUserId}"/>
				<div class='grade_sel_wrap'>
					<select class="validate[required] chosen-select-deselect subject_sel" style="width: 112px; height: 25px;" name="mainUserGradeId" id="mainUserGradeId" onchange="checkMainUserList();" <c:if test="${haveTrack }">disabled="disabled"</c:if>>
						<option value="">请选择年级</option>
						<c:forEach items="${gradeList}" var="grade">
							<option value="${grade.id}" <c:if test="${grade.id==act.mainUserGradeId}"> selected="selected" </c:if>>${grade.name}</option>
						</c:forEach>
					</select> 
				</div>
				
				<div class='main_standby_sel_wrap'>
					<select class="validate[required] chosen-select-deselect subject_sel" style="width: 120px; height: 25px;" name="mainUserId" id="mainUserId" onchange="checkChapterList();" <c:if test="${haveTrack }">disabled="disabled"</c:if>>
						<option value="">请选择主备人</option>
						<c:forEach items="${mainUserList}" var="user">
							<option value="${user.userId}" <c:if test="${user.userId==act.mainUserId}"> selected="selected" </c:if>>${user.username}</option>
						</c:forEach>
					</select> 
				</div>
				<div class='main_preparation_project_wrap'>
					<select class="validate[required] chosen-select-deselect subject_sel" style="width: 172px; height: 25px;" name="infoId" id="chapterId" style="width:150px;" onchange="chushiZhuti(this);" <c:if test="${haveTrack }">disabled="disabled"</c:if>>
						<option value="">请选择主备课题</option>
						<c:forEach items="${chapterList}" var="chapter">
							<option value="${chapter.id}" id="option_${chapter.id}" <c:if test="${chapter.id==act.infoId}"> selected="selected" </c:if>>${chapter.lessonName}</option>
						</c:forEach>
					</select> 
				</div>
			</div>
		</div> 
	</div>
	<div class='participation_scope'>
		<div class='participation_scope_icon'></div>
		<div class='participation_scope_right'>
			<h3 class='participation_scope_right_h3'>确定参与范围：</h3>  
			<div class='participation_subject'>
				 <h3>参与年级：</h3>
				 <div class='participation_subject_b'>
					 <c:if test="${fn:length(gradeList) > 1}">
					 <input type="hidden" id="gradeIds" name="gradeIds" />
					 <c:forEach items="${gradeList}" var="grade">
					 	<div class='subject'>
					 		<input type="checkbox" ${fn:length(gradeList) > 1?'':'checked'} value="${grade.id}" name="grades" class="validate[minCheckbox[1]]"/>${grade.name}
					 	</div>
					 </c:forEach>
					 </c:if>
					 <c:if test="${fn:length(gradeList) < 2}">
						<c:forEach items="${gradeList}" var="grade">
						  <input type="hidden" name="gradeIds" value=",${grade.id },"/>
						  <div class='subject'>${grade.name}</div>
						</c:forEach>
				     </c:if>
				 </div>
				 <div class='clear'></div>
			</div> 
		</div>  
	</div>
	<div class='activity_time'>
		<div class='activity_time_icon'></div>
		<div class='activity_time_right'>
			<h3 class='activity_time_right_h3'>活动时限：<span>（您也可以不设置活动时限）</span></h3>  
			<div class='activity_time_wrap'>
				<div class='start_time_wrap'>
					<span>开始时间：</span>
					<input type="text" name="startTime" id="startTime" 
					class="validate[required,custom[dateTimeFormat]]"
					value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m',maxDate:'#F{$dp.$D(\'endTime\')}'})" <c:if test="${act.commentsNum>0 || haveTrack}">disabled="disabled"</c:if>/>
				</div>
				<div class='end_time_wrap'>
					<span>结束时间：</span>
					<input type="text" name="endTime" id="endTime" 
					value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"/>
				</div>
			</div>
		</div> 
	</div>
	<div class='activity_theme'>
		<div class='activity_theme_icon'></div>
		<div class='activity_theme_right'>
			<h3 class='activity_theme_right_h3'>活动主题：</h3>
			<input type="text" class='validate[required,maxSize[80]] theme_txt' name="activityName" id="activityName" value="${act.activityName}"/>
		</div> 
	</div>
	<div class='activity_require1'>
		<div class='activity_require_icon'></div>
		<div class='activity_require_right'>
			<h3 class='activity_require_right_h3'>活动要求：</h3> 
			<h4 class='activity_require_right_h4'><span id="w_count">0</span>/200</h4>
			<div class="activity_require_textarea">
			<textarea cols='110' rows='9' name="remark" id="remark">${act.remark}</textarea>
			</div>
		</div> 
	</div>
	<div class='activity_info_border'></div>
	<c:if test="${act.id==null || act.status==0 }">
	<div class="activity_btn">
		<input type="button" class='publish' onclick="saveActivity(false);"/>
		<input type="button" class='save_drafts' onclick="saveActivity(true);"/>
	</div>
	</c:if>
	<c:if test="${act.id!=null && act.status!=0 }">
	<div class="activity_btn">
		<input type="button" class='edit' onclick="editActivity();"/>
		<input type="button" class='no_edit' onclick="history.go(-1);"/>
	</div>
	</c:if>
</form>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$(".subject_sel").chosen({disable_search : true});
	//复选框加载
	var sid = '${act.gradeIds}';	
	if(sid != '' && sid != ',') {
		var sids = sid.split(",");
		 $("[name='grades']").each(function(){
			 for(var i=0;i<sids.length;i++) {
				 if($(this).val() == sids[i]) {
					 $(this).prop("checked",true);
					 break;
				 }
			 }
		 });
	}
});
//初始主题
function chushiZhuti(obj){
	if($(obj).val()!=""){
		$("#activityName").html($("#option_"+$(obj).val()).html()+"集体备课");
	}
}
</script>
</html>