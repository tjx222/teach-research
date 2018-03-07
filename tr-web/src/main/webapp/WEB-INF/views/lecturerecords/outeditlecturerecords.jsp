<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:if test="${empty lr.id}">
<ui:htmlHeader title="撰写校外听课记录"></ui:htmlHeader>
</c:if>
<c:if test="${not empty lr.id}">
<ui:htmlHeader title="编辑草稿箱"></ui:htmlHeader>
</c:if>
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<ui:require module="lecturerecords/js"></ui:require>
</head>
<body>
<div class="wrapper">
<div class='jyyl_top'>
		<ui:tchTop style="1" modelName="撰写听课记录"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		<c:if test="${empty lr.id}">
			<h3>当前位置：<jy:nav id="zxxwtkjl"></jy:nav></h3>
		</c:if>
		<c:if test="${not empty lr.id}">
			<h3>当前位置：<jy:nav id="bjcgx"></jy:nav></h3>
		</c:if>
	</div>
		<div class="check_teacher_wrap">
	<div class="check_teacher_wrap2">
	<h3 class="file_title"></h3> 
	<div class="word_plug_ins">
	<form action="jy/lecturerecords/writeLectureRecords" method="post">
	    <ui:token></ui:token>
		<div class="record_sheet_cont">
			<div class="r_s_c">
				<h1 style="text-align:left;"><a href="">*</a>课题</h1>
				<input type="text" class="kt validate[required,maxSize[30]]" name="topic" value="${lr.topic}">
				<b style="margin-left: -2px;">听课地点</b>
				<strong><input type="text" name="lectureAddress" class="validate[maxSize[30]]" value="${lr.lectureAddress}"></strong>
			</div>
			<div class="r_s_c">
				<h1 style="border-top-left-radius:0;">授课教师</h1>
				<strong><input type="text" name="teachingPeople" class="validate[maxSize[4]]" value="${lr.teachingPeople}"></strong>
				<b>单位</b>
				<strong><input type="text" name="lectureCompany" class="validate[maxSize[30]]" value="${lr.lectureCompany}"></strong>
				<b>年级学科</b>
				<strong><input type="text" name="gradeSubject" class="validate[maxSize[30]]" value="${lr.gradeSubject}"></strong>
			</div>
			<div class="r_s_c">
				<h1 style="border-top-left-radius:0;">听课人</h1>
				<strong>${user.name}</strong>
				<c:if test="${empty lr.id}"><!-- 根据有没有主键判断是新增还是修改 -->
					<b>听课时间</b>
					<strong><input type="text" name="lectureTime" value="${nowDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'now'});" class="validate[required]"></strong>
				</c:if>
				<c:if test="${not empty lr.id}">
					<b>听课时间</b>
					<strong><input type="text" name="lectureTime" value='<fmt:setLocale value="zh"/><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/>' onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'now'});" class="validate[required]"></strong>
				</c:if>
				<b>听课节数</b>
				<label for=""><input type="radio" name="numberLectures" value="1" class="validate[required]" ${lr.numberLectures==1?'checked="checked"':""}>1</label>
				<label for=""><input type="radio" name="numberLectures" value="2" class="validate[required]" ${lr.numberLectures==2?'checked="checked"':""}>2</label>
				<label for=""><input type="radio" name="numberLectures" value="3" class="validate[required]" ${lr.numberLectures==3?'checked="checked"':""}>3</label>
				<label for=""><input type="radio" name="numberLectures" value="4" class="validate[required]" ${lr.numberLectures==4?'checked="checked"':""}>4</label>
				<label for=""><input type="radio" name="numberLectures" value="5" class="validate[required]" ${lr.numberLectures==5?'checked="checked"':""}>5</label>
			</div>
			<div  class="r_s_c" style="width: 1130px;height: 35px;float: left;">
				<b style="width: 1130px;height: 35px;line-height: 35px;">听课意见</b>
			</div>
			<textarea id="web_editor" name="lectureContent" data-orgId="${_CURRENT_USER_.orgId }" >${lr.lectureContent}</textarea>
			<div class="clear"></div>
			<div class="btn_bottom" style="margin:10px auto;">
				<input name="type" type="hidden" value="1"/><!--课类型隐藏域 校外听课 -->
					<input type="button" value="保存" class="fb" onclick="handle(this,'1');" isfb="1">
					<input type="button" value="存草稿" class="ccg" onclick="handle(this,'1');" isfb="0">
				<ui:token/><!-- 防止表单重复提交 -->
				<input type="hidden" name="id" value="${lr.id}"/><!--听课记录的主键ID-->
			</div>
		</div>
	   </form>
	</div>
	<div class="clear"></div>
		</div>
</div>
	<ui:htmlFooter></ui:htmlFooter>
</div>
</body>
<script type="text/javascript" src="${ctxStatic}/modules/lecturerecords/js/editlecturerecords.js"></script>
<script src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript">
	$(document) .ready( function() {
	    //每十分钟请求后台，保证session有效
	    requestAtInterval(600000);
	})
	
	/**
 * 每隔一段时间请求一次后台，保证session有效
 */
function requestAtInterval(timeRange){
	var dingshi = window.setInterval(function(){
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        url: _WEB_CONTEXT_+"/jy/toEmptyMethod.json?id="+Math.random(),
	        error: function () {
	        	window.clearInterval(dingshi);
	        },  
	        success:function(data){
	        }  
	    });
	},timeRange);
}
</script>
</html>