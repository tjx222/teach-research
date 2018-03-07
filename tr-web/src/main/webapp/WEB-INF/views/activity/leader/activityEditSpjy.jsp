<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="视频研讨"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
	<ui:require module="activity/js"></ui:require>
<script type="text/javascript">
require(['jquery','editor','activityEdit'],function(){
});
</script>
</head>
<body style="background:#fff;">
<div style="overflow:hidden;">	
<form id="ztytForm" method="post">
<ui:token></ui:token>
<input value="${act.id}" id="id" name="id" type="hidden">
<input id="typeId" name="typeId" type="hidden" value="3">
	<div class='participation_scope'>
		<div class='participation_scope_icon'></div>
		<div class='participation_scope_right'>
			<h3 class='participation_scope_right_h3'>确定参与范围：</h3> 
			<div class='participation_subject' id='partake_subject'>
				 <h3>参与学科：</h3>
				 <div class='participation_subject_b'>
					 <input type="hidden" id="subjectIds" name="subjectIds" />
					 <c:forEach items="${subjectList}" var="subject">
					 	<div class='subject'>
					 		<input type="checkbox" ${fn:length(subjectList) > 1?'':'checked'} value="${subject.id}" name="subjects" class="validate[minCheckbox[1]]"/>${subject.name}
					 	</div>
					 </c:forEach>
				 </div>
				 <div class='clear'></div>
			</div>  
			<div class='participation_subject'>
				 <h3>参与年级：</h3>
				 <div class='participation_subject_b'>
					 <input type="hidden" id="gradeIds" name="gradeIds" />
					 <c:forEach items="${gradeList}" var="grade">
					 	<div class='subject'>
					 		<input type="checkbox" ${fn:length(gradeList) > 1?'':'checked'} value="${grade.id}" name="grades" class="validate[minCheckbox[1]]"/>${grade.name}
					 	</div>
					 </c:forEach>
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
	<div class='video_address'>
		<div class='video_address_icon'></div>
		<div class='video_address_right'>
			<h3 class='video_address_right_h3'>视频地址：</h3>
			<input type="text" class='validate[required,maxSize[100],custom[url]] theme_txt' name="url" id="url" value="${act.url }"/>
		</div> 
	</div>
	<div class='activity_require'>
		<div class='activity_require_icon'></div>
		<div class='activity_require_right'>
			<h3 class='activity_require_right_h3'>活动要求：</h3> 
			<h4 class='activity_require_right_h4'><span id="w_count">0</span>/200</h4>
			<div class="activity_require_textarea1">
				<textarea cols='110' rows='9' name="remark" id="remark">${act.remark}</textarea>
			</div>
		</div> 
	</div>
	<div class='reference_material'>
		<div class='reference_material_icon'></div>
		<div class='reference_material_right'>
			<h3 class='reference_material_right_h3'>参考资料：</h3>
			<input type="hidden" id="ztytRes" name="resIds" value="${resIds}">
			<div class='upload_files'> 
				<a id="span2" class="file1">
				    <ui:upload originFileName="fileName" relativePath="activity/o_${_CURRENT_USER_.orgId }" fileType="doc,docx,xls,xlsx,ppt,pptx,flV,swf,txt,pdf,zip,rar" containerID="span2" fileSize="50" startElementId="uploadFj" callback="backUpload_fj" name="fjResId" beforeupload="beforeUpload_fj"></ui:upload>
				</a>
				<input type="button" class='file_btn' value='上传附件' id="uploadFj" />
				<div class='files' id="attachListDiv">
					<c:forEach items="${resList}" var="attach">
						<div class='files_wrap'>
							<div class='files_wrap_left'></div>
							<div class='files_wrap_center'>
								<div class='files_wrap_center_t' id="${attach.resId }" title="${attach.attachName }.${attach.ext }">
									<span>${attach.attachName }</span>
									<b>${attach.ext }</b>
								</div>
								<div class='files_wrap_center_b'>
									上传完成
								</div>
							</div>
							<div class='files_wrap_right'>删除</div>
						</div>
					</c:forEach>
				</div>
			</div>   
		</div>
		<div class='clear'></div> 
	</div>
	<div class='activity_info_border'></div>
	<c:if test="${act.id==null || act.status==0 }">
	<div class="activity_btn">
		<input type="button" class='publish' onclick="saveActivityZtyt(false);"/>
		<input type="button" class='save_drafts' onclick="saveActivityZtyt(true);"/>
	</div>
	</c:if>
	<c:if test="${act.id!=null && act.status!=0 }">
	<div class="activity_btn">
		<input type="button" class='edit' onclick="editActivityZtyt();"/>
		<input type="button" class='no_edit' onclick="history.go(-1);"/>
	</div>
	</c:if>
</form>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	//年级-复选框加载
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
	//学科-复选框加载
	var ssid = '${act.subjectIds}';	
	if(ssid != '' && ssid != ',') {
		var sids = ssid.split(",");
		 $("[name='subjects']").each(function(){
			 for(var i=0;i<sids.length;i++) {
				 if($(this).val() == sids[i]) {
					 $(this).prop("checked",true);
					 break;
				 }
			 }
		 });
	}
	
});
</script>
</html>