<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="资源查看"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
</head>
<body>
<div class="box1"></div>
<div class="wraper">
	<div class="com_cont">
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<h3>${data.lessonName}</h3>
				<h4>
					<jy:di key="${data.userId }"
						className="com.tmser.tr.uc.service.UserService" var="u">
					学校：<span><jy:di key="${u.orgId }"
								className="com.tmser.tr.manage.org.service.OrganizationService"
								var="org">
					  				 ${org.name }
					  			  </jy:di> </span>作者：<span> ${u.name } </span>分享时间：<span><fmt:formatDate
								value="${data.shareTime}" pattern="yyyy-MM-dd" /></span>
					</jy:di>
				</h4>
				<div class="see_word see_word_tab">
					<iframe id="view" width="95%" height="700px;" style="border:none;" scrolling="no"></iframe>
				</div>
				<div class="see_word_nav1">
					<ul>
					 	<c:set value="0" var="fscount"></c:set>
					    <c:set value="0" var="kjcount"></c:set>
						<c:forEach var="lesson" items="${lessonList}" varStatus="status">
							<c:choose>
								<c:when test="${(lesson.planId eq planId)||((status.index eq 0)&&(empty planId))}">
									<li class="see_word_nav_1 see_word_nav_act" style="cursor: pointer;"  data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
									data-id="${lesson.planId }"  data-resId="${lesson.resId}" data-type="${lesson.planType}" data-userId="${lesson.userId}" data-orgId="${lesson.orgId}">
										<c:if test="${lesson.planType == 0 }">第${lesson.hoursId}课时</c:if>
										<c:if test="${lesson.planType == 1 }"><c:set value="${kjcount+1 }" var="kjcount"></c:set>课件${kjcount}</c:if>
										<c:if test="${lesson.planType == 2 }"><c:set value="${fscount+1 }" var="fscount"></c:set>反思${fscount}</c:if>
									</li>
								</c:when>
								<c:otherwise>
									<li class="see_word_nav_1" style="cursor: pointer;" data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
									data-resId="${lesson.resId}" data-id="${lesson.planId }" data-type="${lesson.planType}" data-userId="${lesson.userId}" data-orgId="${lesson.orgId}">
										<c:if test="${lesson.planType == 0 }">第${lesson.hoursId}课时</c:if>
										<c:if test="${lesson.planType == 1 }"><c:set value="${kjcount+1 }" var="kjcount"></c:set>课件${kjcount}</c:if>
										<c:if test="${lesson.planType == 2 }"><c:set value="${fscount+1 }" var="fscount"></c:set>反思${fscount}</c:if>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div>
				<iframe id="commentBox" onload="setCwinHeight(this,false,600)" width="100%" height="600px;" style="border:none;"scrolling="no"></iframe>
			</div>
		</div>
	<div class="clear"></div>
</div></div>
	<script type="text/javascript">
	$(document).ready(function(){
		var planid = $("li.see_word_nav_act").attr("data-id");
		var resid = $("li.see_word_nav_act").attr("data-resId");
		var userid= $("li.see_word_nav_act").attr("data-userId");
		var type= $("li.see_word_nav_act").attr("data-type");
		var title= $("li.see_word_nav_act").attr("data-title");
		var orgId=$("li.see_word_nav_act").attr("data-orgId");
		$("#view").attr("src","jy/scanResFile?resId="+resid+"&to=true&orgId="+orgId);
		$("#commentBox").attr("src","jy/comment/list?authorId="+userid+"&resType="+type+"&resId="+planid+"&title="+encodeURI(title));
		$.post(_WEB_CONTEXT_+"/jy/schoolview/res/lessonres/addBrowsingRecord",{"type":1,"resId":planid},function(data){},"json");
		$("li.see_word_nav_1").click(function(){
			$this = $(this);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			var userid= $this.attr("data-userId");
			var type= $this.attr("data-type");
			var title= $this.attr("data-title");
			var planid = $this.attr("data-id");
			var orgId=$this.attr("data-orgId");
			$("#view").attr("src","jy/scanResFile?resId="+resid+"&orgId="+orgId);
			$("#commentBox").attr("src","jy/comment/list?authorId="+userid+"&resType="+type+"&resId="+planid+"&title="+encodeURI(title));
			$.post(_WEB_CONTEXT_+"/jy/schoolview/res/lessonres/addBrowsingRecord",{"type":1,"resId":planid},function(data,textStatus){},"json");
		});
	});
	
	</script>
</body>
</html>
