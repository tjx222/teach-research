<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:if test="${type==0 && empty showType}">
	<c:set var="theme" value="教案"></c:set>
	<ui:htmlHeader title="教案"></ui:htmlHeader>
</c:if>
<c:if test="${type==0 &&  not empty showType}">
	<c:set var="theme" value="查看教案"></c:set>
	<ui:htmlHeader title="查看教案"></ui:htmlHeader>
</c:if>
<c:if test="${type==1 && empty showType}">
	<c:set var="theme" value="课件"></c:set>
	<ui:htmlHeader title="课件"></ui:htmlHeader>
</c:if>
<c:if test="${type==1 &&  not empty showType}">
	<c:set var="theme" value="查看课件"></c:set>
	<ui:htmlHeader title="查看课件"></ui:htmlHeader>
</c:if>
<c:if test="${type==2 && empty showType}">
	<c:set var="theme" value="课后反思"></c:set>
	<ui:htmlHeader title="课后反思"></ui:htmlHeader>
</c:if>
<c:if test="${type==2 &&  not empty showType}">
	<c:set var="theme" value="查看反思"></c:set>
	<ui:htmlHeader title="查看反思"></ui:htmlHeader>
</c:if>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="${theme}"></ui:tchTop>
	</div>
	<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
	<div class="check_teacher_wrap">
		<div class="check_teacher_wrap2"> 
			<h3 class="file_title"><ui:sout value="${data.lessonName }" escapeXml="true" length="50" needEllipsis="true"/></h3>
			<div class="file_info">
				<c:if test="${not empty showType}">
					<div class="file_info_l">
						<span></span>
						提交人：${u.name}
					</div>
					<div class="file_info_r">
						<span></span>
						提交日期：<fmt:formatDate value="${data.submitTime}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
				<c:if test="${empty showType}">
					<div class="file_info_r">
						<span></span>
						撰写日期：<fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd"/>
					</div>
				</c:if>
			</div>
			<div class="word_plug_ins">
				<iframe id="view"  width="100%"	height="660px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
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
							<li class="see_word_nav_1 see_word_nav_act"
							data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
							data-id="${lesson.planId }"  data-resId="${lesson.resId}" data-type="${lesson.planType}" data-userId="${lesson.userId}" data-orgId="${lesson.orgId}"
							>
							<c:if test="${type == 0 }">	第${lesson.hoursId}课时	</c:if>
							<c:if test="${type == 1 }">	课件${rescount}	</c:if>
							<c:if test="${type == 2 }">	反思${rescount}	</c:if>
							</li>
						</c:when>
						<c:otherwise>
							<li class="see_word_nav_1" 
							data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
							data-id="${lesson.planId }"  data-resId="${lesson.resId}" data-type="${lesson.planType}" data-userId="${lesson.userId}" data-orgId="${lesson.orgId}"
							>
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
		<div class="border"></div>
		<div class="check_teacher_wrap2"> 
			<iframe id="checkedBox"
			 onload="setCwinHeight(this,false,100)" style="border:none" width="100%" height="100%" frameborder="0"scrolling="no"></iframe>
			 <iframe id="commentBox" onload="setCwinHeight(this,false,100)" width="100%" height="100%" style="border: none;display:block;margin:0 auto;" scrolling="no" frameborder="no" ></iframe>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/javascript">
	$(document).ready(function(){
	    $(window).scroll(function (){
				$("#kongdiv").toggle();
			});
		var resid = $("li.see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?resId="+resid);
		$("#checkedBox").attr("src","jy/teachingView/view/infoIndex?flags=false&term=${data.termId}&gradeId=${data.gradeId}&subjectId=${data.subjectId}&title=<ui:sout value='${data.lessonName }' encodingURL='true' escapeXml='true'></ui:sout>&resType=${type}&authorId=${data.userId}&resId=${data.id}&titleShow=true");
		$("li.see_word_nav_1").click(function(){
			loadResource(this);
		});
		function loadResource(obj){
			$this = $(obj);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			var type = $this.attr("data-type");
			$("#view").attr("src","jy/scanResFile?resId="+resid);
			var planid = $this.attr("data-id");
			var userid= $this.attr("data-userId");
			var type= $this.attr("data-type");
			var title= $this.attr("data-title");
			var orgId=$this.attr("data-orgId");
			$("#view").attr("src","jy/scanResFile?resId="+resid+"&orgId="+orgId);
			$("#commentBox").attr("src","jy/teachingView/view/comment/list?titleShow=true&authorId="+userid+"&resType="+type+"&resId="+planid+"&flags=true&title="+encodeURI(title));
		}
		var size = $("li.see_word_nav_1").length;
		if(size >0){
			loadResource($("li.see_word_nav_1").eq(0));
		}
	});
	</script>
</body>
</html>
