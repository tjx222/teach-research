<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<c:if test="${empty lr.id}">
	<ui:htmlHeader title="撰写校内听课记录"></ui:htmlHeader>
	</c:if>
	<c:if test="${not empty lr.id}">
	<ui:htmlHeader title="编辑草稿箱"></ui:htmlHeader>
	</c:if>
	<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/ztree/zTreeStyle.css" media="all">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
	<style type="text/css">
		.chosen-container{float:left;margin-top:4px;}
		.chosen-container-single .chosen-single{border:1px #ddd solid;}
		.chosen-container-active.chosen-with-drop .chosen-single{
		border:1px #ddd solid;}
	</style>
	<ui:require module="lecturerecords/js"></ui:require>
<script type="text/javascript">
//改变select的时候触发事件
function changeTopic(){
	var lessonId = $("#lessonId").val();
	if(lessonId!=""){
		var userId="";
		var subjectId="";
		var gradeId="";
		if("${lesson.gradeId}"!=""){
			userId="${lesson.userId}";
			subjectId="${lesson.subjectId}";
			gradeId="${lesson.gradeId}";
		}else if("${us.gradeId}"!=""){
			userId="${us.userId}";
			subjectId="${us.subjectId}";
			gradeId="${us.gradeId}";
		}
		$.ajax({    
		    url:_WEB_CONTEXT_+"/jy/lecturerecords/getLessonInfo.json",  
		    data:{"userId":userId,"subjectId":subjectId,"gradeId":gradeId,"lessonId":lessonId},    
		    type:"post",    
		    dataType:"json",    
		    success:function(data){
		    	if(data.info!=null && data.info.id!=""){
		    		$("#topicid").val(data.info.id);
		    		if(data.info.jiaoanCount!="" && data.info.jiaoanCount!="0"){
		    			$('.look').show();
		    		}else{
		    			$('.look').hide();
		    		}
		    	}else{
		    		$("#topicid").val("");
		    		$('.look').hide();
		    	}
		    	
		    }    
		});
	}else{
		$("#topicid").val("");
		$("#topic_sel").val("");
		$('.look').hide();
	}
	
}

$().ready(function(){
	if("${lr.id}"!=""){
		if("${lr.topic}"!=""){
			$("#lessonId option").each(function(){
				var txt = $(this).text();
				if(txt.indexOf('${lr.topic}') >= 0){
					$(this).attr("selected","selected"); 
				}
			});
			changeTopic();
			$("#lessonId").trigger("chosen:updated"); 
		}
	}else{
		$('.look').hide();
	}
});

</script>
</head>
<body>
<div class="wrapper">
	<div class='jyyl_top'>
		<ui:tchTop  modelName="撰写听课记录"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		<c:if test="${empty lr.id}">
			<h3>当前位置：<jy:nav id="zxxntkjl"></jy:nav></h3>
		</c:if>
		<c:if test="${not empty lr.id}">
			<h3>当前位置：<jy:nav id="bjcgx"></jy:nav></h3>
		</c:if>
	</div>
	<div class="check_teacher_wrap">
		<div class="check_teacher_wrap2">
			<h3 class="file_title">听课记录<input type="button" style="display:none" class="look" onclick="seelesson();" value="查看教案"></h3> 
		<div class="word_plug_ins"> 
		<form action="jy/lecturerecords/writeLectureRecords" method="post">
		    <ui:token></ui:token>
			<div class="record_sheet_cont" style="height:auto;">
				<div class="r_s_c">
					<h1>课题</h1>
					<div class="r_s_c_sel">
						<select name="lessonId" id="lessonId" class="chosen-select-deselect kt validate[required]" style="width:502px" onchange="changeTopic();">
							<option id="" value="">请选择</option>
							<option disabled="disabled" style="font-size: 16px;color: #474747;font-weight: bold;">${fasiciculeName }</option>
							<c:forEach items="${bookChapters }" var="bookChapter">
								<ui:bookChapter data="${bookChapter }" selectedid="${lr.lessonId }"></ui:bookChapter>
							</c:forEach>
							<c:if test="${not empty fasiciculeName2}">
								<option disabled="disabled" style="font-size: 16px;color: #474747;font-weight: bold;margin: 8px 5px 0 0;">${fasiciculeName2 }</option>
								<c:forEach items="${bookChapters2 }" var="bookChapter">
									<ui:bookChapter data="${bookChapter }" selectedid="${lr.lessonId }"></ui:bookChapter>
								</c:forEach>
							</c:if>
						</select>
					</div>
					<input type="hidden" id="topicid" name="topicId" value="${lr.topicId }"/><!-- 课题的ID -->
					<input type="hidden" id="topic_sel" name="topic" value="${lr.topic }"/><!-- 课题的ID -->
					<b>评价等级</b>
					<label for=""><input type="radio" name="evaluationRank" value="A+" class="validate[required]" ${lr.evaluationRank=='A+'?'checked="checked"':""}>A+</label>
					<label for=""><input type="radio" name="evaluationRank" value="A" class="validate[required]" ${lr.evaluationRank=='A'?'checked="checked"':""}>A</label>
					<label for=""><input type="radio" name="evaluationRank" value="B+" class="validate[required]" ${lr.evaluationRank=='B+'?'checked="checked"':""}>B+</label>
					<label for=""><input type="radio" name="evaluationRank" value="B" class="validate[required]" ${lr.evaluationRank=='B'?'checked="checked"':""}>B</label>
					<label for=""><input type="radio" name="evaluationRank" value="C" class="validate[required]" ${lr.evaluationRank=='C'?'checked="checked"':""}>C</label>
					<label for=""><input type="radio" name="evaluationRank" value="D" class="validate[required]" ${lr.evaluationRank=='D'?'checked="checked"':""}>D</label>
				</div>
				
				<c:if test="${empty lr.id}"><!-- 根据有没有主键判断是新增还是修改 -->
					<div class="r_s_c">
						<h1 style="border-top-left-radius:0;">授课教师</h1>
						<strong>${us.username}</strong>
						<input type="hidden" name="teachingpeopleId" value="${us.userId}"/><!-- 授课人隐藏域 -->
						<input type="hidden" name="teachingPeople" value="${us.username}"/><!-- 授课人隐藏域 -->
						<b>学科</b>
						<ui:relation var="subjects" type="xdToXk" id="${us.phaseId }"></ui:relation>
						<c:forEach items="${subjects}" var="s">
							<c:if test="${us.subjectId==s.id}">
								<strong>${s.name}</strong>
								<input type="hidden" name="subjectId" value="${s.id}"/><!-- 学科隐藏域 -->
								<input type="hidden" name="subject" value="${s.name}"/><!-- 学科隐藏域 -->
							</c:if>
						</c:forEach>
						<b>年级</b>
						<ui:relation var="grades" type="xdToNj" id="${us.phaseId}"></ui:relation>
						<c:forEach items="${grades}" var="g">
							<c:if test="${us.gradeId==g.id}">
								<strong>${g.name}</strong>
								<input type="hidden" name="gradeId" value="${g.id}"/><!-- 年级隐藏域 -->
								<input type="hidden" name="grade" value="${g.name}"/><!-- 年级隐藏域 -->
							</c:if>
						</c:forEach>
					</div>
				</c:if>
				
				<c:if test="${not empty lr.id}">
					<div class="r_s_c">
						<h1 style="border-top-left-radius:0;">授课教师</h1>
						<strong>${lr.teachingPeople}</strong>
						<c:if test="${not empty us }">
							<input type="hidden" name="bookId" value="${us.bookId}"/>
						</c:if>
						<c:if test="${not empty lesson }">
							<input type="hidden" name="bookId" value="${lesson.bookId}"/>
						</c:if>
						<input type="hidden" name="teachingpeopleId" value="${lr.teachingpeopleId}"/><!-- 授课人隐藏域 -->
						<input type="hidden" name="teachingPeople" value="${lr.teachingPeople}"/><!-- 授课人隐藏域 -->
						<b>学科</b>
						<strong>${lr.subject}</strong>
						<input type="hidden" name="subjectId" value="${lr.subjectId}"/><!-- 学科隐藏域 -->
						<input type="hidden" name="subject" value="${lr.subject}"/><!-- 学科隐藏域 -->
						
						<b>年级</b>
						<strong>${lr.grade}</strong>
						<input type="hidden" name="gradeId" value="${lr.gradeId}"/><!-- 年级隐藏域 -->
						<input type="hidden" name="grade" value="${lr.grade}"/><!-- 年级隐藏域 -->
					</div>
				</c:if>
				
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
					<b style="width: 1130px;height: 35px;line-height: 35px;border:none;">听课意见</b>
				</div>
				<textarea id="web_editor" data-orgid="${_CURRENT_USER_.orgId }" name="lectureContent">${lr.lectureContent}</textarea>
				<div class="clear"></div>
				<div class="btn_bottom" style="margin:6px auto 5px auto;">
					<input name="type" type="hidden" value="0"/><!--课类型隐藏域 校外听课 -->
					<input type="button" value="保存" class="fb" onclick="handle(this,'0');" isfb="1">
					<input type="button" value="存草稿" class="ccg" onclick="handle(this,'0');" isfb="0">
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
		//下拉列表
		  var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect: true},
		      '.chosen-select-deselect' : {disable_search:true}
		    };
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
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