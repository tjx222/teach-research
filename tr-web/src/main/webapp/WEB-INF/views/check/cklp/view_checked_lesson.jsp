<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅资源查看"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/check/css/check.css" media="screen">
</head>
<body>
	<div class="wrap">
		<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
	<div class="resources_view">
			<div class="resources_view_cont">
			  <div class="see_word_big">
				<div class="see_word">
						<h5><ui:sout value="${data.lessonName }" escapeXml="true" length="50" needEllipsis="true"/></h5>
						<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<input type="button" class="download" id="downloadBtn" data-name="">
					<iframe id="view"  width="100%"	height="700px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
				</div>
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
								<c:if test="${type == 0 }">	第${lesson.hoursId}课时	</c:if>
								<c:if test="${type == 1 }">	课件${rescount}	</c:if>
								<c:if test="${type == 2 }">	反思${rescount}	</c:if>
								</li>
							</c:when>
							<c:otherwise>
								<li class="see_word_nav_1" data-resId="${lesson.resId }">
									<c:if test="${type == 0 }">	第${lesson.hoursId}课时	</c:if>
									<c:if test="${type == 1 }">	课件${rescount}	</c:if>
									<c:if test="${type == 2 }">	反思${rescount}	</c:if>
								</li>
							</c:otherwise>
						</c:choose>
						</c:if>
					</c:forEach>
				</ul>
			</div>
	</div>	
	<div class="clear"></div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
	    $(window).scroll(function (){
				$("#kongdiv").toggle();
			});
		var resid = $("li.see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?resId="+resid);
		$("#checkedBox").attr("src","jy/check/infoIndex?flags=true&term=${data.termId}&gradeId=${data.gradeId}&subjectId=${data.subjectId}&title=<ui:sout value='${data.lessonName }' encodingURL='true' escapeXml='true'></ui:sout>&resType=${type}&authorId=${data.userId}&resId=${data.id}");
		$("li.see_word_nav_1").click(function(){
			$this = $(this);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			$("#view").attr("src","jy/scanResFile?resId="+resid);
		});
		$("div.see_word_Annex dl").click(function(){
			 scanResFile($(this).attr("data-resId"));
		});
		$("#downloadBtn").click(function(){
			var name = $.trim($("div.see_word h5").text()) + $.trim($("li.see_word_nav_act").text());
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI(name),"_self");
		});
	});
	</script>
</body>
</html>
