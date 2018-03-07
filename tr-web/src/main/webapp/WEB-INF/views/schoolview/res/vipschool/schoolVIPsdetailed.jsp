<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="同伴校在线"></ui:htmlHeader>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$(".com_dl").each(function(i,item){
		if(i!=0&&(i+1)%4==0){
			$(this).css("margin-right","0");
		}
	});
});
</script>
<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include>
	</div>
		
	<div class="com_cont">
		<div class="top_nav">
			当前位置:<jy:nav id="tongbanxiao">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav>
		</div>
		<ol>
			<li class="cont_3_right_cont_act">同伴校在线</li>
		</ol>	
		<div class="clear"></div>
		<div class="com_dl_wrap">
		
			<c:if test="${empty data.datalist }" >
				暂无同伴校!
			</c:if>
			<c:if test="${not empty data.datalist}" >
				<c:forEach var="kt" items="${data.datalist }">
				<div class="com_dl">
					<a href="${ctx}jy/schoolview/index?orgID=${kt.id}" target="_self ">
					<dl>
						<dd>
						<jy:ds var="picresource" key="${kt.logo}" className="com.tmser.tr.manage.resources.service.ResourcesService"></jy:ds>
						<ui:photo src="${picresource.path}" height="119" width="116" defaultSrc="${ctxStatic}/modules/schoolview/images/school/schDefalutPic.png"></ui:photo>
						<dt>${kt.name}</dt>
					</dl>
					</a>
					</div>
				</c:forEach>
			</c:if>
		</div>	 
		</div>
		<div class="clear"></div>
		<div class="pages">
			<!--设置分页信息 -->
			<form name="pageForm" method="post">
					<ui:page url="jy/schoolview/res/vipschool/index/getVIPSchoolsDetailed?orgID=${cm.orgID}&xdid=${cm.xdid}" data="${data}" views="5"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
			</form>
		</div>
		<div class="clear"></div>
		<div style="height:20px;"></div>
	<%@include file="../../common/bottom.jsp" %>
</div>
	</body>
	<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
</html>