<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
</head>
<body>
<div class="return_1"></div>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include>
	</div>
	<div class="gro_cont">
		<div class="top_nav">
			当前位置:<jy:nav id="czdads">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav>
		</div>	
				<div class="gro_wrap">
				<c:if test="${empty data.datalist }" >
					<div style="text-align: center;margin-top: 10px;">暂无资源!</div>
				</c:if>
				<c:if test="${not empty data.datalist}" >
					<c:forEach var="p" items="${data.datalist}">
						<div class="gro_wrap_1">
						    <jy:di key="${p.teacherId}" className="com.tmser.tr.uc.service.UserService" var="u">
							    <div class="gro_wrap_left" data-url="jy/schoolview/res/recordbags/findList?teacherId=${p.teacherId}&subjectId=${p.subjectId}&gradeId=${p.gradeId}" onclick="opearDom(this)"><ui:photo src="${u.photo}"></ui:photo>   </div>
							</jy:di>
							<div class="gro_wrap_right">
								<h4>
									<span>分享数：${p.fenxiangshu}</span>
									<strong style="cursor: pointer;" data-url="jy/schoolview/res/recordbags/findList?teacherId=${p.teacherId}&subjectId=${p.subjectId}&gradeId=${p.gradeId}" onclick="opearDom(this)" >
										<jy:di key="${p.teacherId}" className="com.tmser.tr.uc.service.UserService" var="u">
											${u.name}
										</jy:di>
									</strong>
								</h4>
								<h4>
									<span>[<jy:dic key="${p.subjectId}"></jy:dic>][<jy:dic key="${p.gradeId}"></jy:dic>]</span>
									<strong>
										<c:choose>
											<c:when test="${not empty p.modifyTime}">
												<fmt:formatDate value="${p.modifyTime}"  pattern="yyyy-MM-dd"/>
											</c:when>
											<c:otherwise>
												<c:if test="${not empty p.shareTime}" >
													<fmt:formatDate value="${p.shareTime}"  pattern="yyyy-MM-dd"/>
												</c:if>				
											</c:otherwise>
										</c:choose>
									</strong>
								</h4>
							</div>
						</div>
					</c:forEach>
					<div class="clear"></div>
					<div class="pages">
						<!--设置分页信息 -->
						<form name="pageForm" method="post">
							<ui:page url="jy/schoolview/resindex/getSpecificRecordBag?orgID=${cm.orgID}&xdid=${cm.xdid}" data="${data}" views="5"/>
							<input type="hidden" class="currentPage" name="page.currentPage">
						</form>
					</div>
					</c:if>
					<div class="clear"></div>
				</div>
		</div>
	</div>
		<%@include file="../../common/bottom.jsp" %>
	</body>
	<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
</html>