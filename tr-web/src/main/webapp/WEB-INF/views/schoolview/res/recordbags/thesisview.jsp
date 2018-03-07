<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看教学论文"></ui:htmlHeader>
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
<div class="wraper">
		<div class="gro_cont">
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<h3>${thesis.thesisTitle}</h3>
				<h4>
				<jy:di key="${thesis.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
					学校：<span><jy:di key="${u.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">
					  				 ${org.name }
					  			  </jy:di>
					  			  </span>作者：<span>
					  		 ${u.name }
					  		</span>分享时间：<span><fmt:formatDate
							value="${thesis.shareTime}" pattern="yyyy-MM-dd" /></span>
				</jy:di>
				</h4>
				
				<div class="see_word">
				   <div style="width:0px; height: 0px; display: none;" id="kongdiv"></div>
					<iframe id="view" style="border:none;" src="jy/scanResFile?resId=${thesis.resId}&to=true&orgId=${thesis.orgId}" width="100%" height="700px;" scrolling="no"></iframe>
				</div>
			</div>
			<div class="clear"></div>
<%-- 			<iframe id="commentBox" src="jy/comment/list?authorId=${_CURRENT_USER_.id}&resType=8&resId=${thesis.id}" --%>
<!-- 					width="100%" height="600px;" frameborder="0" style="border:none; overflow:hidden" scrolling="no"></iframe> -->
			<iframe id="commentBox" onload="setCwinHeight(this,false,600)" src="jy/comment/list?authorId=${thesis.crtId}&resType=${resType}&resId=${thesis.id}&title=<ui:sout value='${thesis.thesisTitle}' encodingURL='true' escapeXml='true'></ui:sout>" width="100%" height="600px;" style="border:none;" scrolling="no"></iframe>
		</div>
	</div>
</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
</html>
