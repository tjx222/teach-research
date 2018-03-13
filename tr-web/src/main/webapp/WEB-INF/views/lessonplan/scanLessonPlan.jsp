<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="备课资源查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
<div class="clear"></div>
<div class="check_teacher_wrap" >
		<div class="check_teacher_wrap2">
			<h3 class="file_title">${plan.planName}</h3>
			<div class="file_down_btn">
				<c:if test="${empty plan.orgId || _CURRENT_SPACE_.orgId==plan.orgId }">
					<a href="<ui:download filename="${plan.planName}" resid="${plan.resId}"></ui:download>"><b class="download"></b></a>
				</c:if>
			</div>
			<div class="word_plug_ins">
				<iframe id="view"  width="920px" height="680px;" border="0" style="border: none;margin: 0 auto;display: block;">
				</iframe>
			</div>
		</div>
		<c:if test="${model.isReview != 0 }">
			<div>
				<iframe  onload="setCwinHeight(this,false,600)" id="commentBox" src="${ctx }jy/comment/list?flags=1&resType=${plan.planType }&resId=${plan.planId}"
						width="100%" height="100%" frameborder="0" style="border:none; overflow:hidden" scrolling="no"  >
				</iframe>
			</div>
		</c:if>
</div>
<div class="clear"></div>
	<script type="text/javascript">
		var resid = "${plan.resId}";
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
	</script>
</body>
</html>
