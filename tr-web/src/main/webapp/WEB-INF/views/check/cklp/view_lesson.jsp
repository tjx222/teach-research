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
		<h3 class="file_title"><ui:sout value="${data.lessonName }" escapeXml="true" length="50" needEllipsis="true"/></h3>
		<div class="file_sel">
			<div class="anti_plagiarism">
				<label for="">反抄袭：</label>
				<div class="ser">
					<input type="text" class="ser_txt" id="searchFcx"/>
					<input type="button" class="ser_button" />
				</div>
			</div>
		</div>
		<div class="file_info" style="width:348px;float:left;margin-left:90px;">
			<div class="file_info_l">
				<span></span>
				<jy:di key="${data.userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
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
			<iframe id="view"  width="100%"	height="680px"style="border:none;" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>
	<div class="see_word_nav">
		<ul>
			<c:forEach var="lesson" items="${lessonList}" varStatus="st">
				<c:if test="${lesson.planType == type }">
				<c:set value="${empty rcount ? 0 : 1 }" var="rcount"></c:set>
				<c:set value="${empty rescount ? 1 : rescount+1 }" var="rescount"></c:set>
				<c:choose>
					<c:when test="${rcount == 0}">
						<li class="see_word_nav_1 see_word_nav_act" data-resId="${lesson.resId }">
						<c:if test="${type == 0 }">
							<c:choose>
								<c:when test="${lesson.hoursId eq '-1'}">不分课时</c:when>
								<c:when test="${lesson.hoursId eq '0'}">简案</c:when>
								<c:otherwise>第<ui:sout value="${lesson.hoursId}" length="44" needEllipsis="true"/>课时</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${type == 1 }">	课件${rescount}	</c:if>
						<c:if test="${type == 2 }">	反思${rescount}	</c:if>
						</li>
					</c:when>
					<c:otherwise>
						<li class="see_word_nav_1" data-resId="${lesson.resId }">
							<c:if test="${type == 0 }">
								<c:choose>
									<c:when test="${lesson.hoursId eq '-1'}">不分课时</c:when>
									<c:when test="${lesson.hoursId eq '0'}">简案</c:when>
									<c:otherwise>第<ui:sout value="${lesson.hoursId}" length="44" needEllipsis="true"/>课时</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${type == 1 }">	课件${rescount}	</c:if>
							<c:if test="${type == 2 }">	反思${rescount}	</c:if>
						</li>
					</c:otherwise>
				</c:choose>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	<div class="see_word_Annex">
			<h6>附：</h6>
			<div class="see_word_Annex_bottom">
			<c:forEach var="lesson" items="${lessonList}" varStatus="status">
				<c:if test="${lesson.planType != type }">
				<dl data-resId="${lesson.resId}">
					<dd ><ui:icon ext="${lesson.planType == 1?'ppt':'doc' }" width="26" height="25" title="${lesson.planName }"></ui:icon></dd>
					<dt title="${lesson.planName }">
					<c:if test="${lesson.planType == 0 }">
						<c:choose>
							<c:when test="${lesson.hoursId eq '-1'}">不分课时</c:when>
							<c:when test="${lesson.hoursId eq '0'}">简案</c:when>
							<c:otherwise>第<ui:sout value="${lesson.hoursId}" length="44" needEllipsis="true"/>课时</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${lesson.planType == 1 }">	<c:set value="${empty kjcount ? 1 : kjcount+1 }" var="kjcount"></c:set> 课件${kjcount}	</c:if>
					<c:if test="${lesson.planType == 2 }">	<c:set value="${empty fscount ? 1 : fscount+1 }" var="fscount"></c:set>反思${fscount}	</c:if>
					</dt>
				</dl>
				</c:if>
			</c:forEach>
		</div>
		<div class="clear"></div>
	</div>
	<div class="border"></div>
	<div class="check_teacher_wrap2"> 
		<iframe id="lessonOthers" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
		<iframe id="checkedBox" src="jy/check/lookCheckOption?flags=true&term=${data.termId}&gradeId=${data.gradeId}&subjectId=${data.subjectId}&title=<ui:sout value='${data.lessonName }' encodingURL='true' escapeXml='true'></ui:sout>&resType=${type}&authorId=${data.userId}&resId=${data.id}"
		 onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		//获取同时提交这个课题的其他人
		$("#lessonOthers").attr("src","jy/myplanbook/lessonSubmitOthers?orgId=${data.orgId}&lessonId=${data.lessonId}&schoolYear=${data.schoolYear}&userId=${data.userId}&phaseId=${type}");
	    $(window).scroll(function (){
				$("#kongdiv").toggle();
			});
		var resid = $("li.see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
// 		$("#checkedBox").attr("src","jy/check/infoIndex?flags=true&term=${data.termId}&gradeId=${data.gradeId}&subjectId=${data.subjectId}&title=<ui:sout value='${data.lessonName }' encodingURL='true' escapeXml='true'></ui:sout>&resType=${type}&authorId=${data.userId}&resId=${data.id}");
		$("li.see_word_nav_1").click(function(){
			$this = $(this);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		});
		$("div.see_word_Annex dl").click(function(){
			var url = _WEB_CONTEXT_+"/jy/scanResFile?resId="+$(this).attr("data-resId");
			window.open(url,"hidenframe");
			//scanResFile($(this).attr("data-resId"));
		});
		$("#downloadBtn").click(function(){
			var name = $.trim($("div.check_teacher_wrap2 h3.file_title").text()) + $.trim($("li.see_word_nav_act").text());
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI(name),"_self");
		});
		$('.ser_button').click(function(){
			var search=$('#searchFcx').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
	});
	</script>
</body>
</html>
