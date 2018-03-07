<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${empty listMapbags}" >
	<div class="check-bottom_1_right_top" align="center" style="padding-top: 10px;">
		暂无档案袋!
	</div>
</c:if>
<c:if test="${not empty listMapbags}" >
	<c:forEach items="${listMapbags}" var="p" begin="0" end="4" step="1">
		<div class="cont_4_left_bags">
			<jy:di key="${p.teacherId}" className="com.tmser.tr.uc.service.UserService" var="u">
			    <div class="c_4_l_b" data-url="jy/schoolview/res/recordbags/findList?teacherId=${p.teacherId}&subjectId=${p.subjectId}&gradeId=${p.gradeId}" onclick="opearDom(this)">
			    	<ui:photo src="${u.photo}"></ui:photo>
			    </div>
			</jy:di>
			<div class="c_4_l_b_r">
				<h5>
					<span style="cursor: pointer;" data-url="jy/schoolview/res/recordbags/findList?teacherId=${p.teacherId}&subjectId=${p.subjectId}&gradeId=${p.gradeId}" onclick="opearDom(this)">
						<jy:di key="${p.teacherId}" className="com.tmser.tr.uc.service.UserService" var="u">
							${u.name}
						</jy:di>
					</span>
					<strong>分享数：${p.fenxiangshu}</strong>
				</h5>
				<h4>
				<b>[<jy:dic key="${p.subjectId}"></jy:dic>][<jy:dic key="${p.gradeId}"></jy:dic>]</b>
					<u>
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
					</u>
				</h4>
			</div>
		</div>
	</c:forEach>
	<c:if test="${fn:length(listMapbags)>6}"><!-- 数量大于6才会产生更多 -->
		<p><a style="cursor: pointer;" onclick="clickBags(${cm.orgID},${cm.xdid});">更多>></a></p>
	</c:if>
</c:if>