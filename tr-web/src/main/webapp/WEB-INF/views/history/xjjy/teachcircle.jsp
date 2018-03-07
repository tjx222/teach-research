<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="查看校际教研圈"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/school_activity.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css" media="screen">
</head>

<body>
<div class="Teaching_schedule" style="margin-left: 7px;width: 800px;height: 830px;">
	<h3>校际教研圈列表</h3>
	<div style="overflow-y: auto;height: 793px;">
	<c:choose>
		<c:when test="${!empty stcList }">
			<c:forEach items="${stcList }" var="stc">
				<div class="Reflect_cont_right_1_dl">
					<dl>
						<dd>
							<img src="${ctxStatic }/modules/schoolactivity/images/xj.png" alt="">
							<div class="school">
								<ol>
									<c:forEach items="${stc.stcoList }" var="stco">
										<li>
											<a title="${stco.orgName }" class="w90">${stco.orgName }</a>
											<span>
												<c:choose>
												 <c:when test="${stco.state==1}"><label class="z_zc">待接受</label></c:when>
												 <c:when test="${stco.state==2}"><label class="z_ty">已接受</label></c:when>
												 <c:when test="${stco.state==3}"><label class="z_jj">已拒绝</label></c:when>
												 <c:when test="${stco.state==4}"><label class="z_tc">已退出</label></c:when>
												 <c:when test="${stco.state==5}"><label class="z_ty">已恢复</label></c:when>
												</c:choose>
											</span>
										</li>
									</c:forEach>
								</ol>	
							</div>
						</dd>
						<dt>
							<span class="w120">${stc.name }</span>
							<span><fmt:formatDate value="${stc.crtDttm }" pattern="yyyy-MM-dd"/></span>
						</dt>
					</dl>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="cont_empty">
			    <div class="cont_empty_img"></div>
			    <div class="cont_empty_words">没有校际教研圈信息哟！</div> 
			</div>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
</html>