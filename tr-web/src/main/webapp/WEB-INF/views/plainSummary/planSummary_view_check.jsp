<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅计划总结"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/planSummary/css/planSummary_view.css" media="screen">
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
	<div class="wrap">
		<ui:tchTop hideMenuList="false"></ui:tchTop>
		<div class="wrap_top_2">
			<h3>
				当前位置：
				<jy:nav id="zyck">	
					<jy:param name="name" value="计划总结"></jy:param>
					<jy:param name="ckname" value="资源查看"></jy:param>
					<jy:param name="jxlwHref" value="jy/planSummary/index"></jy:param>
				</jy:nav>
			</h3>
		</div>
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<h3>${ps.title}</h3>
				<h4>
					<a href="<ui:download filename="${ps.title}" resid="${ps.contentFileKey}"></ui:download>"><input
						type="button" class="download" style="margin-left: 800px; position: static;"></a>
				</h4>
				<div class="see_word">
				    <div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
					<iframe id="view" src="jy/scanResFile?resId=${ps.contentFileKey}"
						width="930px" height="700px;" border="0" frameborder="0"style="border:none;" scrolling="no" ></iframe>
				</div>
			</div>
	
		<div>
				<iframe onload="setCwinHeight(this,false,600)" id="commentBox" src="jy/check/infoIndex?authorId=${_CURRENT_USER_.id}&resType=${(ps.category==1||ps.category==3)?8:9 }&resId=${ps.id }"
					width="100%" height="600px;" frameborder="0" style="border:none; overflow:hidden" scrolling="no"  ></iframe>
			</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
		</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
