<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="计划总结查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
<div class="clear"></div>
<div class="check_teacher_wrap" >
		<div class="check_teacher_wrap2">
			<h3 class="file_title">${model.title }</h3>
			<div class="file_down_btn">
				<a href="<ui:download filename="${model.title}" resid="${model.contentFileKey}"></ui:download>"><b class="download"></b></a>
			</div>
			<div class="word_plug_ins">
				<iframe id="view"  width="920px" height="680px;" border="0" style="border: none;margin: 0 auto;display: block;">
				</iframe>
			</div>
		</div>
		<c:if test="${model.isReview != 0 }">
			<div>
				<iframe  onload="setCwinHeight(this,false,600)" id="commentBox" src="jy/comment/list?authorId=${_CURRENT_USER_.id}&resType=${(model.category==1||model.category==3)?8:9 }&resId=${model.id }&flags=1&title=<ui:sout value="${model.title }" encodingURL="true"/>"
						width="100%" height="100%" frameborder="0" style="border:none; overflow:hidden" scrolling="no"  >
				</iframe>
			</div>
		</c:if>
</div>
<div class="clear"></div>
	<script type="text/javascript">
		var resid = "${model.contentFileKey}";
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
	</script>
</body>
</html>
