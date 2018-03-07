<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="${recordbagName}"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
<script type="text/javascript"
	src="${ctxStatic }/modules/thesis/js/js.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(window).scroll(function(){
			$("#kongdiv").toggle();
		});
	});
</script>
</head>
<body>
<div class="box1"></div>
<div class="wraper">
		<div class="gro_cont">
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<h3>${rd.recordName}</h3>
				<div class="see_word">
				   <div style="width:0px; height: 0px; display: none;" id="kongdiv"></div>
				   <iframe style="border:none;" scrolling="no" width="100%" height="700px;" src="jy/scanResFile?resId=${rd.path}&to=true&orgId=${cm.orgID}" ></iframe>
				</div>
			</div>
			<div class="clear"></div>
			<iframe id="commentBox" onload="setCwinHeight(this,false,600)" src="jy/comment/list?authorId=${_CURRENT_USER_.id}&resType=${resType}&resId=${rd.recordId}&title=<ui:sout value='${rd.recordName}' encodingURL='true' escapeXml='true'></ui:sout>" width="100%" height="600px;" style="border:none;" scrolling="no"></iframe>
		</div>
	</div>
</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
