<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看教案"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
</head>
<body>
	<div class="wrap">
		<div class="resources_view">
			<div class="resources_view_cont">
				<h3>${data.lessonName}</h3>
				<h4>
					<jy:di key="${data.userId}" className="com.tmser.tr.uc.service.UserService" var="u">
					作者：<span> ${u.name} </span>
					</jy:di>
					日期：<span><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd" /></span>
				</h4>

				<div class="see_word">
					<iframe id="view" width="100%" height="700px"; frameborder="0"></iframe>
				</div>
			</div>
			<div class="see_word_nav">
				<ul>
					<c:forEach var="lesson" items="${lessonPlans}">
						<li class="see_word_nav_1" data-resId="${lesson.resId}" onclick="hoursClick(this);" style="cursor: pointer;">
							第${lesson.hoursId}课时
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="clear"></div>
<%-- <ui:htmlFooter></ui:htmlFooter> --%>
	<script type="text/javascript">
	$(document).ready(function(){
		//首次进入页面载入样式和内容
		$('.see_word_nav_1').first().addClass("see_word_nav_act");
		var resid = $(".see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
	});
	
	//点击每个课时载入的内容
	function hoursClick(obj){
		$('.see_word_nav_1').removeClass("see_word_nav_act");
		$(obj).addClass("see_word_nav_act");
		var resid = $(".see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
	}
	</script>
</body>
</html>
