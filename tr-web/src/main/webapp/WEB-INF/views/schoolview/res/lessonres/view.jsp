<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="资源查看"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
<script type="text/javascript">
	$(document).ready(function() {
		$(window).scroll(function() {
			$("#kongdiv").toggle();
		});
		/* var title=encodeURI('${data.planName}');
		var href="jy/comment/list?authorId=${data.userId }&resType=${data.planType}&resId=${data.planId }&title="+title;
		$('#commentBox').attr('src',href); */
	});
</script>
</head>
<body>
	<div class="box1"></div>
	<div class="wraper">
		<div class="com_cont">
			<div class="clear"></div>
			<div class="resources_view">
				<div class="resources_view_cont">
					<h3>${data.planName}</h3>
					<h4>
						<jy:di key="${data.userId }"
							className="com.tmser.tr.uc.service.UserService" var="u" />
						<jy:di key="${u.orgId }"
							className="com.tmser.tr.manage.org.service.OrganizationService"
							var="org" />
						学校： <span> ${org.name } </span>作者：<span> ${u.name } </span> 分享时间：<span><fmt:formatDate
								value="${data.shareTime}" pattern="yyyy-MM-dd" /></span>
					</h4>

					<div class="see_word"> 
						<div style="width: 0px; height: 0px; display: none;" id="kongdiv"></div>
						<iframe id="view" src="jy/scanResFile?resId=${data.resId}&to=true&orgId=${data.orgId}"
							width="100%" height="700px;" style="border: none;" scrolling="no"></iframe>
					</div>
				</div>
				<div>
					<iframe id="commentBox" onload="setCwinHeight(this,false,600)"
						src="jy/comment/list?authorId=${data.userId }&resType=${data.planType}&resId=${data.planId }&title=<ui:sout value='${data.planName}' encodingURL='true' escapeXml='true'></ui:sout>"
						width="100%" height="600px;" style="border: none;" scrolling="no"></iframe>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>

</body>
</html>
