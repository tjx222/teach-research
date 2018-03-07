<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="学校资源展示页"></ui:htmlHeader>
</head>

<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include> 
	</div>
	<div class="not_cont">
		<div class="top_nav">
		当前位置： <jy:nav id="ck_xizy_tzgg">
		        <jy:param name="orgID" value="${cm.orgID}"></jy:param>
			    <jy:param name="xdid" value="${cm.xdid}"></jy:param>
		</jy:nav>
		</div>
		<!-- <ol>
			<li class="cont_3_right_cont1_act">通知公告</li>
		</ol> -->
		<div class="clear"></div>
		<div class="preparation_resources_2">
			<div class="preparation_resources_2_cont">
			    <c:if test="${ja.redTitleId!=0}">
				<h2>${redHeadTitle}</h2>
				<b>${ja.fromWhere}</b>
				<!-- <input type="button" class="zf"> -->
				<div class="border"></div>
		     	</c:if>
				<h6>${ja.title}</h6>
				<h5><strong>编辑：<jy:di var="u" key="${ja.crtId}" className="com.tmser.tr.uc.service.UserService">${u.name}</jy:di></strong>
				<strong>时间： <fmt:formatDate value="${ja.crtDttm}" pattern="yyyy-MM-dd"/> </strong><strong>浏览量：${count }</strong></h5>
				<p>${ja.content}</p>
 				<c:if test="${!empty ja.attachs}">
				<div class="Notice_Annex">
					<h5>附件：</h5>
					<table border="0" id="resTable" style="width: 840px;">
						<c:forEach items="${rList}" var="res">
							<tr value="${res.id}">
								<td title="${res.name}.${res.ext }">
									<span class="delete_fj"  ></span>
									<span onclick="scanResFile('${res.id}');">
										<ui:sout value="${res.name}.${res.ext }" length="30" needEllipsis="true" ></ui:sout>
									</span>
								</td>
							</tr>

						</c:forEach>
					</table>
				</div>
			</c:if>
			</div>
			<div class="clear"></div>
			<ul>
			    <c:if test="${isFirst!=true}">
				     <li><a href="${ctx}/jy/schoolview/show/previousAnnunciates?id=${ja.id}&orgID=${cm.orgID}&xdid=${cm.xdid}">上一篇</a></li>
				</c:if>
				<c:if test="${isLast!=true}">
				     <li style="border-right:0;"><a href="${ctx}/jy/schoolview/show/nextAnnunciates?id=${ja.id}&orgID=${cm.orgID}&xdid=${cm.xdid}"">下一篇</a></li>
			    </c:if>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	 <%@include file="../../common/bottom.jsp" %> 
</div>
</body>
</html>