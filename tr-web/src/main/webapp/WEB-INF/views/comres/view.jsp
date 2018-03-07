<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="资源查看"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
</head>
<script type="text/javascript">
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
	$("#downloadBtn").click(function(){
		var resid = $(this).attr("data-resid");
		window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI($(this).attr("data-name")));
	});
});
</script>
<body>
	<div>
		<%-- <div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="zyck">
					<jy:param name="name" value="同伴资源"></jy:param>
					<jy:param name="ckname" value="资源查看"></jy:param>
					<jy:param name="jxlwHref" value="jy/comres/index"></jy:param>
				</jy:nav>
			</h3>
		</div> --%>
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<div class="resources_view_cont_top">
					<h3>${data.planName}</h3>
					<h4>
						<jy:di key="${data.userId }"
							className="com.tmser.tr.uc.service.UserService" var="u"/>
						<jy:di key="${u.orgId }"
									className="com.tmser.tr.manage.org.service.OrganizationService"
									var="org"/>
						学校：     <span>
						  				 ${org.name }
						  		  </span>作者：<span> ${u.name } </span>
						  	分享时间：<span><fmt:formatDate value="${data.shareTime}"
									pattern="yyyy-MM-dd" /></span>
					</h4>
					<input value="下载" type="button" id="downloadBtn" data-name="<ui:sout value='${data.planName}' encodingURL='true' escapeXml='true'></ui:sout>" data-resid="${data.resId}" class="download">
				</div>	
				<div class="see_word" style="height:750px;">
				    <div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<%-- <iframe id="view" src="jy/scanResFile?resId=${data.resId}" width="100%" height="700px;" style="background-color:#fff;" allowtransparency="true"  frameborder="0" scrolling="no"></iframe> --%>
					<div style="width:1000px;height:700px" style="background-color:#fff;" allowtransparency="true"  frameborder="0" scrolling="no">
				    	<po:PageOfficeCtrl id="PageOfficeCtrl1" height="700" width='920'>
				       	</po:PageOfficeCtrl>
					</div>
				</div>
			</div>
			<div>
			<iframe id="commentBox" onload="setCwinHeight(this,false,200)" src="jy/comment/list;jsessionid=<%=session.getId() %>?authorId=${data.userId }&resType=${data.planType}&resId=${data.planId }&title=<ui:sout value='${data.planName }' encodingURL='true' escapeXml='true'></ui:sout>" width="100%" height="200px;" style="border:none;" scrolling="no"></iframe>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	
</body>
</html>
