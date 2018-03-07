<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${empty professions}" >
	<div class="check-bottom_1_right_top" align="center" style="padding-top: 10px;">
		暂无专业资料!
	</div>
</c:if>
<c:if test="${not empty professions}" >
	<c:if test="${cm.restype==1}">
		<c:forEach items="${professions}" var="p" begin="0" end="4" step="1">
			<div class="cont_3_right_cont1_pro1">
				<div class="c_3_r_c_t_c">
					<h5>
						<img src="${ctxStatic}/common/icon/base/word.png" alt=""><span style="cursor: pointer;" onclick="viewprofress(${p.id},${cm.orgID},${cm.xdid},${cm.restype});">${p.title}</span>
						<strong>
							<jy:di key="${p.userId}" className="com.tmser.tr.uc.service.UserService" var="u">
								${u.name}
							</jy:di>
						</strong>
					</h5>
					<h5>
						<b>
							<c:if test="${not empty p.label }">【${p.label }】</c:if>
							<c:if test="${p.subjectId!=0}">【<jy:dic key="${p.subjectId}"></jy:dic>】</c:if>
							<c:if test="${p.gradeId!=0}">【<jy:dic key="${p.gradeId}"></jy:dic>${p.term==0?"上":"下"}】</c:if>
						</b>			
						<u>
							<fmt:formatDate value="${p.shareTime}"  pattern="yyyy-MM-dd"/>
						</u>
					</h5>
				</div>
			</div>
		</c:forEach>
		
	</c:if>
	<c:if test="${cm.restype==2}">
		<c:forEach items="${professions}" var="p" begin="0" end="4" step="1">
			<div class="cont_3_right_cont1_pro1">
				<div class="c_3_r_c_t_c">
					<h5>
						<img src="${ctxStatic}/common/icon/base/word.png" alt=""><span style="cursor: pointer;" onclick="viewprofress(${p.id},${cm.orgID},${cm.xdid},${cm.restype});">${p.thesisTitle}</span>
						<strong>
							<jy:di key="${p.userId}" className="com.tmser.tr.uc.service.UserService" var="u">
								${u.name}
							</jy:di>
						</strong>
					</h5>
					<h5><b>【<jy:dic key="${p.subjectId}"></jy:dic>】【${p.thesisType}】</b><u><fmt:formatDate value="${p.shareTime}"  pattern="yyyy-MM-dd"/></u></h5>
				</div>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${cm.restype==3}">
		<c:forEach items="${professions}" var="p" begin="0" end="4" step="1">
			<div class="cont_3_right_cont1_pro1">
				<div class="c_3_r_c_t_c">
					<h5>
						<img src="${ctxStatic}/common/icon/base/word.png" alt=""><span style="cursor: pointer;" onclick="viewprofress(${p.id},${cm.orgID},${cm.xdid},${cm.restype});">${p.topic}</span>
						<strong>${p.lecturePeople}</strong>
					</h5>
					<h5>
						<c:if test="${p.type==0}">
							<b>【<jy:dic key="${p.subjectId}"></jy:dic>】【<jy:dic
									key="${p.gradeId}"></jy:dic>${p.term==0?"上":"下"}】【${p.type==0?"校内":"校外"}】</b>
							<u><fmt:formatDate value="${p.shareTime}" pattern="yyyy-MM-dd" /></u>
						</c:if>
						<!-- 校外听课记录 -->
						<c:if test="${p.type!=0}">
							<b>
							<c:if test="${p.gradeSubject!=''}">
							【${p.gradeSubject}】
							</c:if>
							【${p.type==0?"校内":"校外"}】</b>
							<u><fmt:formatDate value="${p.shareTime}" pattern="yyyy-MM-dd" /></u>
						</c:if>
					</h5>
				</div>
			</div>
		</c:forEach>
	</c:if>
<%-- 	<c:if test="${fn:length(professions)>5}"><!-- 数量大于5才会产生更多 --> --%>
<%-- 		<p><a style="cursor: pointer;" onclick="clickProfession(${cm.orgID},${cm.xdid},${restype});">更多>></a></p> --%>
<%-- 	</c:if> --%>
</c:if>