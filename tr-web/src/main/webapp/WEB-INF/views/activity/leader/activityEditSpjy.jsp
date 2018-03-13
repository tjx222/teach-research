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
					<div class="activity_wrap"> 
						<form  id="spjyForm">
						<ui:token></ui:token>
						<input value="${act.id}" id="id" name="id" type="hidden">
						<input id="typeId" name="typeId" type="hidden" value="3">
						<div class="activity_wrap_right">
							<div class="video_seminar_content">
								<div class="video_seminar"></div>
								<h3>视频地址<span>*</span></h3>
								 <input type="text" placeholder="请将视频地址粘贴到这里" name="url" id="url" maxlength="100" value="${act.url }">
							</div>
							<div class="range_content">
								<div class="range"></div>
								<h3>确定参与范围<span>*</span></h3>
								<input type="hidden" id="subjectIds" name="subjectIds" value=",${_CURRENT_SPACE_.subjectId },"/>
								<h4>学科：<span>
								<c:forEach items="${subjectList}" var="subject">
									&nbsp;${subject.name}
								</c:forEach>
								</span></h4>
								<div class="range_class">
									<div class="label_div">年级：</div>
									<c:if test="${fn:length(gradeList) > 1}">
									<input type="hidden" id="gradeIds" name="gradeIds" />
									<c:forEach items="${gradeList}" var="grade">
									<%-- 	<p gradeId="${grade.id}"><a>${grade.name}</a></p>  --%>
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
								<div class="activity_duration"></div>
								<h3>活动时限<span>（您可以不设置活动结束时限）</span></h3>
								<div class="activity_input">
								时间：<%-- <input placeholder="开始时间" type="text" style="height:22px;" name="startTime" id="startTime" class="validate[required,custom[dateTimeFormat]]"
					              value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m',maxDate:'#F{$dp.$D(\'endTime\')}'})" <c:if test="${act.commentsNum>0}">disabled="disabled"</c:if>/>&nbsp;一&nbsp;
								  <input placeholder="结束时间" type="text" style="height:22px;" name="endTime" id="endTime" 
								  value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"/> --%>
								  <c:if test="${act.commentsNum>0}"><fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>
								  <input type="hidden" name="startTime"  value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>"/>
								  </c:if>
								<c:if test="${!(act.commentsNum>0)}">
								<input type="text" id="picktime_start"  placeholder="开始时间" name="startTime"  value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm"/>" readonly="readonly" />
								</c:if>
								&nbsp;一&nbsp;
								<input type="text" id="picktime_end"  placeholder="结束时间" name="endTime" value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/>" readonly="readonly" />
								</div>
							</div>
							<div class="activity_theme_content activity_theme_content1">
								<div class="activity_theme"></div>
								<h3>活动主题<span>*</span></h3>
								<input name="activityName" id="activityName" type="text" autofocus placeholder="输入活动主题（80字内）" value="${act.activityName}">
								<h3>活动要求<span>*</span></h3>
								<textarea cols="80" rows="10"  autofocus class="remark" name="remark" id="remark">${act.remark}</textarea>
							</div> 
							<div class="study_additional_content">
								<div class="study_additional"></div>
								<h3>研讨附件（最多可上传6个）</h3>
								<input type="hidden" id="ztytRes" name="resIds" value="${resIds}">
								<c:if test="${empty act.commentsNum || act.commentsNum<=0 }">
									<div class="clear"></div>
									<div class="study_additional_content_l">
										<div class="add_study">
											<p></p> 
											<span>添加附件</span>
										</div>
										<ui:upload_m callback="afterUpload" fileType="doc,docx,xls,xlsx,ppt,pptx,flv,swf,txt,pdf,zip,rar" beforeupload="beforeUpload" progressBar="true" relativePath="activity/o_${_CURRENT_USER_.orgId }"></ui:upload_m>
									</div>
								</c:if>
								<div class="study_additional_content_r">
									<c:forEach items="${resList}" var="res">
										<div class="add_study_content" resId="${res.resId}">
											<div class="add_study_content_l">
											</div>
											<div class="add_study_content_c">
												<span><ui:sout value="${res.attachName}.${res.ext }" needEllipsis="true" length="38"></ui:sout></span>
											</div>
											<c:if test="${empty act.commentsNum || act.commentsNum<=0 }">
											<input type="button" class="add_study_content_r" value="删除" /> 
											</c:if>
										</div>
									</c:forEach>
								</div>
							</div>
						</div> 
						</form>
						<div class="clear"></div>
						<div class="act_spjy_btn">
					   	    <input type="button" class="submit_btn" value="提交中..." />
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
	require(['edit'],function(){	
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