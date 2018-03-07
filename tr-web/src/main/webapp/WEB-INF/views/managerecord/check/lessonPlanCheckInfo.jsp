<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="查阅教案详情"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
		<div class="clear"></div>
		<div class="check_teacher_wrap">
			<div class="check_teacher_wrap2"> 
				<h3 class="file_title"><ui:sout value="${data.lessonName }" escapeXml="true" length="50" needEllipsis="true"/></h3>
				<div class="file_info">
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
					<input type="button" class="download">
				</div>
				<div class="word_plug_ins">
					<iframe id="view"  width="100%"	height="660px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
				</div>
			</div>
			<div class="see_word_nav">
				<ul>
					<c:forEach var="lesson" items="${lessonList}" varStatus="st">
						<c:set value="${empty rcount ? 0 : 1 }" var="rcount"></c:set>
						<c:choose>
							<c:when test="${rcount == 0}">
								<li class="see_word_nav_1 see_word_nav_act" data-resId="${lesson.resId }">
								第${lesson.hoursId}课时
								</li>
							</c:when>
							<c:otherwise>
								<li class="see_word_nav_1" data-resId="${lesson.resId }">
									第${lesson.hoursId}课时
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
			<div class="border"></div>
			<div class="check_teacher_wrap2"> 
				<iframe id="checkedBox"
				 onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/javascript">
	$(document).ready(function(){
	    $(window).scroll(function (){
				$("#kongdiv").toggle();
			});
		var resid = $("li.see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		$("#checkedBox").attr("src","jy/check/infoIndex?flags=false&gradeId=${data.gradeId}&subjectId=${data.subjectId}&title=<c:out value="${data.lessonName }" escapeXml="true"></c:out>&resType=${type}&authorId=${data.userId}&resId=${data.id}&titleShow=true");
		$("li.see_word_nav_1").click(function(){
			$this = $(this);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		});
		$("div.see_word_Annex dl").click(function(){
			 scanResFile($(this).attr("data-resId"));
		});
		$(".download").click(function(){
			var name = "${data.lessonName}";
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI(name),"_self");
		});
	});
	</script>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
