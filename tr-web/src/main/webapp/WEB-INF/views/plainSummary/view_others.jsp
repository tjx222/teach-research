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
				<h3>${data.title}</h3>
				<h4>
					<jy:di key="${data.userId}" className="com.tmser.tr.uc.service.UserService" var="u">
					提交人：<span class="h4_hover"> ${u.name} 
						<div class="Details1">
							<div class="Details1_img"> 
								<img src="static/common/images/default.jpg" height="" width="" alt=""> 
								<span style="text-align: center;display: inline-block;width: 90px;font-size:14px;">${u.name}</span> 
							</div>
							<div class="Details1_span"> 
								<span title="语文">学科:<jy:dic key="${data.subjectId }"></jy:dic></span>  
								<span title="一年级">年级:<jy:dic key="${data.gradeId }"></jy:dic></span> 
							    <span title="校长">职务:<jy:dic key="${data.roleId }"></jy:dic></span> 
								<span title="高级教师">职称:${u.profession }</span>
							</div>
						</div>
					</span>
					</jy:di>
					提交日期：<span><fmt:formatDate value="${data.crtDttm}" pattern="yyyy-MM-dd" /></span>
				</h4>
				<a href="<ui:download filename="${data.title}" resid="${data.contentFileKey}"></ui:download>"><b class="download"></b></a>
				<div class="see_word">
					<iframe id="view" width="100%" height="700px;" frameborder=no border=0 ></iframe>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
<%-- <ui:htmlFooter></ui:htmlFooter> --%>
	<script type="text/javascript">
	$(document).ready(function(){
		//首次进入页面载入样式和内容
		$('.see_word_nav_1').first().addClass("see_word_nav_act");
		//var resid = $(".see_word_nav_act").attr("data-resId");
		var resid = "${data.contentFileKey}";
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
	});
	</script>
</body>
</html>
