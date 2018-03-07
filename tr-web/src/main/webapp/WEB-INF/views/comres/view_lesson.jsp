<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="资源查看"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
</head>
<body>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
	<div>
		<div class="clear"></div>
		<div class="resources_view">
			<div class="resources_view_cont">
				<div class="resources_view_cont_top">
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
					<input value="下载" type="button" class="download" id="downloadBtn" data-name="<ui:sout value='${data.lessonName }' encodingURL='true' escapeXml='true'></ui:sout>">
				</div>
				
				<div class="see_word">
					<iframe id="view" width="100%" height="700px;" style="background:#fff;" allowtransparency="true" frameborder="0" scrolling="no"></iframe>
				</div>
			<div class="see_word_nav1">
				<ul>
				    <c:set value="0" var="fstpcount"></c:set>
				    <c:set value="0" var="kjcount"></c:set>
					<c:forEach var="lesson" items="${lessonList}" varStatus="status">
						<c:choose>
							<c:when test="${(lesson.planId eq planId)||((status.index eq 0)&&(empty planId))}">
								<li class="see_word_nav_1 see_word_nav_act" title="<ui:sout value='${lesson.planName }' escapeXml='true'></ui:sout>" data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
								data-id="${lesson.planId }"  data-resId="${lesson.resId}" data-type="${lesson.planType}" data-userId="${lesson.userId}">
									<c:if test="${lesson.planType == 0 }">
										<c:choose>
											<c:when test="${lesson.hoursId=='-1' }">不分课时</c:when>
											<c:when test="${lesson.hoursId=='0' }">简案</c:when>
											<c:otherwise>第${lesson.hoursId}课时</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${lesson.planType == 1 }"><c:set value="${kjcount+1 }" var="kjcount"></c:set>课件${kjcount}</c:if>
									<c:if test="${lesson.planType == 2 }"><c:set value="${fstpcount+1 }" var="fstpcount"></c:set>反思${fstpcount}</c:if>
								</li>
							</c:when>
							<c:otherwise>
								<li class="see_word_nav_1" title="<ui:sout value='${lesson.planName }' escapeXml='true'></ui:sout>" data-title="<ui:sout value='${lesson.planName }' encodingURL='true' escapeXml='true'></ui:sout>" 
									data-resId="${lesson.resId}" data-id="${lesson.planId }" data-type="${lesson.planType}" data-userId="${lesson.userId}">
									<c:if test="${lesson.planType == 0 }">
										<c:choose>
											<c:when test="${lesson.hoursId=='-1' }">不分课时</c:when>
											<c:when test="${lesson.hoursId=='0' }">简案</c:when>
											<c:otherwise>第${lesson.hoursId}课时</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${lesson.planType == 1 }"><c:set value="${kjcount+1 }" var="kjcount"></c:set>课件${kjcount}</c:if>
									<c:if test="${lesson.planType == 2 }"><c:set value="${fstpcount+1 }" var="fstpcount"></c:set>反思${fstpcount}</c:if>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
			
			</div>
		
		<div>
			<iframe id="commentBox" onload="setCwinHeight(this,false,200)" width="100%" height="200px;" style="border:none;"scrolling="no"></iframe>
			</div>
		</div>
	<div class="clear"></div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		var actli = $("li.see_word_nav_act");
		var planid = actli.attr("data-id");
		var resid = actli.attr("data-resId");
		var userid= actli.attr("data-userId");
		var title= actli.attr("data-title");
		var type= actli.attr("data-type");
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId="+userid+"&resType="+type+"&resId="+planid+"&title="+encodeURI(title));
		$.post(_WEB_CONTEXT_+"/jy/comres/addBrowsingRecord",{"type":1,"resId":planid},function(data,textStatus){},"json");
		$("li.see_word_nav_1").click(function(){
			$this = $(this);
			$("li.see_word_nav_act").removeClass("see_word_nav_act");
			$this.addClass('see_word_nav_act');
			var resid = $this.attr("data-resId");
			var userid= $this.attr("data-userId");
			var title= $this.attr("data-title");
			var type= $this.attr("data-type");
			var planid = $this.attr("data-id");
			$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?authorId="+userid+"&resType="+type+"&resId="+planid+"&title="+encodeURI(title));
			$.post(_WEB_CONTEXT_+"/jy/comres/addBrowsingRecord",{"type":1,"resId":planid},function(data,textStatus){},"json");
		});
		$("#downloadBtn").click(function(){
			var resid = $("li.see_word_nav_act").attr("data-resId");
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI($(this).attr("data-name")),"_self");
		});
	});
	</script>
</body>
</html>
