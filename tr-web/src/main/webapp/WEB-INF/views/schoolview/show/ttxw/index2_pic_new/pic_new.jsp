<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="学校资源展示页"></ui:htmlHeader>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$(".pic_dl").each(function(i,item){
		if(i==3||i==7){
			$(this).css("margin-right","0");
		}
	});
});
</script>
<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../../common/top.jsp"></jsp:include>
	</div>
	<div class="pic_cont">
	<div class="top_nav">
			当前位置:<jy:nav id="tpxw">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav>
		</div>
		<ol>
			<li class="cont_3_right_cont_act">图片新闻</li>
		</ol>
		<div class="clear"></div>
		<div class="com_dl_wrap">
			<c:forEach items="${pictureNew_data.datalist}" var="databo">
			<div class="pic_dl">
				<dl>
					<dd>
						<jy:ds var="picresource" key="${fn:substring(databo.attachs , 0, 32)}" className="com.tmser.tr.manage.resources.service.ResourcesService"></jy:ds>
						<a data-url="jy/schoolview/show/atlas?id=${databo.id}&&orgID=${cm.orgID}" onclick="opearDom(this,'_blank',false)" href="javascript:"><ui:photo src="${picresource.path}"/></a>
					</dd>
					<dt>
						<span title="${databo.title}">${databo.title}</span>
						<c:if test="${fn:length(databo.content) >= 62}"><p>${fn:substring(databo.content , 0, 62)}...</p></c:if>
						<c:if test="${fn:length(databo.content) < 62}"><p>${databo.content}</p></c:if>
						<strong><fmt:formatDate value="${databo.crtDttm}" pattern="yyyy-MM-dd HH:mm"/></strong>
					</dt>
				</dl>
			</div>
			</c:forEach>
		</div>
		</div>
		<div class="clear"></div>
		<div class="pages">
			<!--设置分页信息 -->
			<form name="pageForm" method="post">
				<ui:page url="jy/schoolview/show/pic_new?orgID=${cm.orgID}" data="${pictureNew_data}"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
			</form>
		</div>
	<%@include file="../../../common/bottom.jsp" %>
</div>
</body>
</html>