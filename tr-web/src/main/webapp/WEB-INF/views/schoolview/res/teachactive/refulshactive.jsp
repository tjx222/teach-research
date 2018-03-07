<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${empty activities}" >
	<div class="check-bottom_1_right_top" align="center" style="padding-top: 10px;">
		暂无教研活动!
	</div>
</c:if>
<c:if test="${not empty activities}" >
	<c:forEach items="${activities}" var="p" begin="0" end="4" step="1">
		<div class="cont_3_right_cont1_tab" style="width: 602px;padding-left: 0px;padding-top: 0px;">
			<div class="c_3_r_c_t_l">
				<c:if test="${p.typeId==1}">
					<img src="${ctxStatic}/modules/schoolview/images/school/tb1.png" alt="">
				</c:if>
				<c:if test="${p.typeId==2}">
					<img src="${ctxStatic}/modules/schoolview/images/school/zt1.png" alt="">
				</c:if>
				<c:if test="${p.typeId==3}">
					<img src="${ctxStatic}/modules/schoolview/images/school/sp.png" alt="">
				</c:if>
				<c:if test="${p.typeId==4}">
					<img src="${ctxStatic}/modules/schoolview/images/school/zb.png" alt="">
				</c:if>
			</div>
			<div class="c_3_r_c_t_c" style="width: 530px;">
				<h5>【${p.typeName}】<span title="${p.activityName}" style="cursor: pointer;" onclick="canyu_chakan('${p.id}','${p.typeId}','${p.isOver}','${cm.orgID}','${cm.xdid}','${cm.restype}');">
				<ui:sout value="${p.activityName}" length="60" needEllipsis="true"></ui:sout></span><strong>${p.organizeUserName}</strong></h5>
				<h5 title='${p.subjectName}'><b>参与学科:${p.subjectName}</b><u><fmt:formatDate value="${p.createTime}" pattern="yyyy-MM-dd"/></u></h5>
				<h5 title="${p.gradeName}"><b>参与年级:${p.gradeName}</b></h5>
			</div>
		</div>
	</c:forEach>
</c:if>