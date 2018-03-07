<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="通知公告"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/annunciate/css/annunciate.css" media="screen">
	<ui:require module="../m/annunciate/js"></ui:require>
</head> 
<body>
<div class="act_draft_content" id="wrap_draft">
	<div id="scroller">
	 <c:forEach items="${draftlist.datalist}" var="d">
	     <div class="draft_list"> 
	            <input type="hidden" name="${d.id}">
				<div class="draft_list_top"> 
					<c:if test="${d.redTitleId!=0}"><div class="red"></div></c:if> 
					<c:if test="${d.redTitleId==0}"><div  style="margin-left: 52px;"></div></c:if>
					<div style="float:left;">【学校通知】</div>
					<a data-id="${d.id}" data-type="${d.type}" style="cursor:pointer;">
						<span title="${d.title}"><ui:sout value="${d.title}" length="36" needEllipsis="true"></ui:sout></span>
					</a>
				</div>  
				<div class="draft_list_bottom">
					<ul>
						<li class="edit" data-id="${d.id}" data-type="${d.type}"><span>修改</span></li>
						<li class="del" style="border-right:none;" data-id="${d.id}" data-status="${d.status}"><span>删除</span></li>
					</ul>
				</div>
			</div>
	 </c:forEach>
	</div>
</div>
<script type="text/javascript">
	require(["zepto",'js'],function(){	
		$(function(){ 
			$('.draft_list_top a').click(function(){
				parent.location.href=_WEB_CONTEXT_+"/jy/annunciate/release?id="+$(this).attr("data-id")+"&type="+$(this).attr("data-type");
			})
		})
	}); 
</script>
</body>

</html>