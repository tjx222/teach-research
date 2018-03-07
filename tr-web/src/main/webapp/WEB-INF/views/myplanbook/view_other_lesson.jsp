<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/planSummary_other.css" media="screen">
</head>
<body>
	<div class="wrap">
		<div class="resources_view">
			<div class="resources_view_cont">
				<c:if test="${empty lessonName}"><h3>${lp.planName}</h3></c:if>
				<c:if test="${!empty lessonName}"><h3>${lessonName}</h3></c:if>
				<h4>
					<jy:di key="${lp.userId}" className="com.tmser.tr.uc.service.UserService" var="u">
					提交人：<span class="h4_hover"> ${u.name} 
						<div class="Details1">
							<div class="Details1_img"> 
								<ui:photo src="${u.photo }"></ui:photo>
								<span style="text-align: center;display: inline-block;width: 90px;font-size:14px;">${u.name} </span> 
							</div>
							<div class="Details1_span"> 
								<span title="语文">学科:<jy:dic key="${lp.subjectId }"></jy:dic></span>  
								<span title="一年级">年级:<jy:dic key="${lp.gradeId }"></jy:dic></span> 
							    <span title="校长">职务:教师</span> 
								<span title="高级教师">职称:${u.profession }</span> 
							</div>
						</div>
					</span>
					</jy:di>
					提交日期：<span><fmt:formatDate value="${lp.crtDttm}" pattern="yyyy-MM-dd" /></span>
				</h4>
				<a href="<ui:download filename="${lessonName}" resid="${lp.resId}"></ui:download>"><b class="download"></b></a>
				<div class="see_word">
					<iframe id="view" width="100%" height="700px;" frameborder=no border=0 ></iframe>
				</div>
			</div>
			<div class="see_word_nav">
				<ul>
					<c:forEach var="lesson" items="${lpList}" varStatus="status">
						<li class="see_word_nav_1" data-resId="${lesson.resId}" onclick="hoursClick(this);" style="cursor: pointer;">
							<c:if test="${lesson.planType==0 }">
								第${lesson.hoursId}课时
							</c:if>
							<c:if test="${lesson.planType==1 }">
								课件${status.index+1 }
							</c:if>
							<c:if test="${lesson.planType==2||lessosn.planType==3 }">
								反思${status.index+1 }
							</c:if>			
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
		$("#view").attr("src","jy/scanResFile?resId="+resid);
	});
	
	//点击每个课时载入的内容
	function hoursClick(obj){
		$('.see_word_nav_1').removeClass("see_word_nav_act");
		$(obj).addClass("see_word_nav_act");
		var resid = $(".see_word_nav_act").attr("data-resId");
		$("#view").attr("src","jy/scanResFile?resId="+resid);
	}
	</script>
</body>
</html>
