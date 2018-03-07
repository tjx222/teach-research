<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅资源查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap2"> 
		<h3 class="file_title"><ui:sout value="${data.planName}" escapeXml="true" length="50" needEllipsis="true"/></h3>
		<div class="file_sel">
			<div class="anti_plagiarism">
				<label for="">反抄袭：</label>
				<div class="ser">
					<input type="text" class="ser_txt" id="searchFcx"/>
					<input type="button" class="ser_button" />
				</div>
			</div>
		</div>
		<div class="file_info" style="width:340px;float:left;margin-left:90px;">
		<jy:di key="${data.userId}" className="com.tmser.tr.uc.service.UserService" var="u"/>
			<div class="file_info_l"> 
				<span></span>
				提交人：${u.name}
			</div>
			<div class="file_info_r">
				<span></span>
				提交日期：<fmt:formatDate value="${data.submitTime}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="file_down_btn">
			<input type="button" class="download" id="downloadBtn"/>
		</div>
		<div class="word_plug_ins">
			<iframe id="view" src="jy/scanResFile?to=true&resId=${data.resId}" width="100%"	height="680px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>
	<div class="border"></div>
	<div class="check_teacher_wrap2"> 
		<iframe id="checkedBox" src="jy/check/lookCheckOption?flags=true&term=${data.termId}&gradeId=${data.gradeId}&subjectId=${data.subjectId}&resType=${type}&authorId=${data.userId}&resId=${data.planId}&title=<ui:sout value='${data.planName }' escapeXml='true' encodingURL='true'></ui:sout>"
		 onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
	</div>
</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>

	<script type="text/javascript">
	$(document).ready(function(){
	    $(window).scroll(function (){
				$("#kongdiv").toggle();
			});
		$("#downloadBtn").click(function(){
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/${data.resId}?filename="+encodeURI('${data.planName }'),"_self");
		});
		
		$("div.see_word_Annex dl").click(function(){
			 scanResFile($(this).attr("data-resId"));
		});
		$('.ser_button').click(function(){
			var search=$('#searchFcx').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
	});
	</script>
</body>
</html>
