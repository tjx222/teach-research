<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
</head>
<body>
<div class="box1"></div>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include>
	</div>
	<div class="gro_cont">
		<div class="top_nav">
			当前位置:<jy:nav id="zylx"> 
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
						<jy:param name="subjectId" value="${cm.subjectId}"></jy:param>
					    <jy:param name="gradeId" value="${cm.gradeId}"></jy:param>
					    <jy:param name="teacherId" value="${cm.teacherId}"></jy:param>
						<jy:di key="${userID}" className="com.tmser.tr.uc.service.UserService" var="u">
							<jy:param name="userId" value="${u.id}"></jy:param>
							<jy:param name="bagmaster" value="${u.name}"></jy:param>
						</jy:di>
						
						<jy:param name="id" value="${recordbag.id}"></jy:param>
						<jy:param name="zyname" value="${recordbag.name}"></jy:param>
					</jy:nav>
		</div>	
		<div class="preparation_resources">
			<div class="preparation_resources_1">
				<div class="preparation_resources_2">
					<c:if test="${empty data.datalist }" >
						暂无资源!
					</c:if>
					<c:if test="${not empty data.datalist}" >
						<c:forEach var="kt" items="${data.datalist }">
							<div class="cont_3_right_cont1_tab_w">
								<div class="c_3_r_c_t_l">
									<img src="${ctxStatic}/modules/schoolview/images/school/zy.png" alt="">
								</div>
								<div class="c_3_r_c_t_c">
									<h5>
										<img src="${ctxStatic}/common/icon/base/word.png" alt="">
										<c:choose>
									         <c:when test="${zytype==0}">
										         <span><a href="javascript:" onclick="opearDom(this,'_blank')" class="user_login" data-url="jy/schoolview/res/recordbags/viewThesis?lesid=${kt.resId}&recordbagID=${recordbag.id}&teacherId=${cm.teacherId}&gradeId=${cm.gradeId}&subjectId=${cm.subjectId}">${kt.recordName}</a></span>
										    </c:when>
										    <c:when test="${zytype==1}">
										         <span><a href="javascript:" onclick="opearDom(this,'_blank')" class="user_login" data-url="jy/schoolview/res/recordbags/viewPlain?lesid=${kt.resId}&recordbagID=${recordbag.id}&teacherId=${cm.teacherId}&gradeId=${cm.gradeId}&subjectId=${cm.subjectId}">${kt.recordName}</a></span>
										    </c:when>
										    <c:otherwise>
										         <span><a href="javascript:" onclick="opearDom(this,'_blank')" class="user_login" data-url="jy/schoolview/res/recordbags/viewLessons?lesid=${kt.resId}&recordbagID=${recordbag.id}&teacherId=${cm.teacherId}&gradeId=${cm.gradeId}&subjectId=${cm.subjectId}">${kt.recordName}</a></span>
										    </c:otherwise>
										</c:choose>
										<strong>
											<jy:di key="${userID}" className="com.tmser.tr.uc.service.UserService" var="u">
												${u.name}
											</jy:di>
										</strong>
									</h5>
									<c:choose>
									     <c:when test="${zytype==0}">
									         <h5><b>【<jy:dic key="${kt.crtId}"></jy:dic>】【${kt.flags}】</b><u><fmt:formatDate value="${kt.createTime}"  pattern="yyyy-MM-dd"/></u></h5>
								         </c:when>
								         <c:when test="${zytype==1}">
									         <h5><b>【<jy:dic key="${kt.crtId}"></jy:dic>】【<jy:dic key="${kt.lastupId}"></jy:dic><jy:dic key="${kt.enable}"></jy:dic>】</b><u><fmt:formatDate value="${kt.createTime}"  pattern="yyyy-MM-dd"/></u></h5>
								         </c:when>
								         <c:otherwise>
								             <h5><b>【<jy:dic key="${kt.crtId}"></jy:dic>】【${kt.flags}】【<jy:dic key="${kt.lastupId}"></jy:dic><jy:dic key="${kt.enable}"></jy:dic>】</b><u><fmt:formatDate value="${kt.createTime}"  pattern="yyyy-MM-dd"/></u></h5>
								         </c:otherwise>
								    </c:choose>
								</div>
							</div>
						</c:forEach>
					</c:if>
					
				</div>
			</div>
		</div>
	</div>
</div>
	<div class="pages">
		<!--设置分页信息 -->
		<form name="pageForm" method="post">
			<ui:page url="jy/schoolview/res/recordbags/findresByBagID?orgID=${cm.orgID}&xdid=${cm.xdid}&userID=${userID}&id=${data.datalist[0].bagId}&teacherId=${cm.teacherId}&gradeId=${cm.gradeId}&subjectId=${cm.subjectId}" data="${data}" views="5"/>
			<input type="hidden" class="currentPage" name="page.currentPage">
		</form>
	</div>
		<%@include file="../../common/bottom.jsp" %>
	</body>
	<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
</html>