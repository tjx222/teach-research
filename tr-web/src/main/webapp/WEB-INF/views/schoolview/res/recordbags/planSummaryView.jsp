<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅计划总结"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/planSummary_view.css" media="screen">
<script type="text/javascript"
	src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).scroll(function (){
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
				<h3>${ps.title}</h3>

				<div class="see_word">
				    <div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<iframe id="view" src="jy/scanResFile?resId=${ps.contentFileKey}&to=true&orgId=${ps.orgId}"
						width="100%" height="700px;" border="0" frameborder="0" scrolling="no" style="border:none;"></iframe>
				</div>
			</div>
	
		<div>
				<iframe onload="setCwinHeight(this,false,600)" id="commentBox" src="jy/comment/list?authorId=${_CURRENT_USER_.id}&resType=${(ps.category==1||ps.category==3)?8:9 }&resId=${ps.id }&title=<ui:sout value='${ps.title}' encodingURL='true' escapeXml='true'></ui:sout>"
					width="100%" height="600px;" frameborder="0" style="border:none; overflow:hidden" scrolling="no"  ></iframe>
			</div>
	</div>
	<div class="clear"></div>
		</div>
</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
