<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教研一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
</head>
<body>
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教研一览"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl">
			<jy:param name="spaceId" value="${param.spaceId }"></jy:param>
		</jy:nav>
	</div>
	 <c:if test="${fn:length(phases) > 1 }">
		<div class="content_top_right" style="width: 22rem; height: 3.583rem; float: right; padding-top: 0.833rem; cursor: pointer;">
				 <label style="display: inline-block;width: 6rem;float: left; height: 3rem; line-height: 3rem; font-size: 1.333rem; color: #999;">学段：</label>
                <div>
                <select name="phaseId" id="phaseId" style="width: 6rem; border: none; line-height: 3rem;  height: 3rem; font-size: 1.267rem; color: #999; background: #f7f8f9;">
                 <c:forEach items="${phases }" var="phase">
                   <option value="${phase.id }" ${phase.id == phaseId ? 'selected':'' }>${phase.name }</option>
                 </c:forEach>
                </select>
            </div>
		</div>
	</c:if>
	<div class="index_content">
		<div class="index_con">
			<ul>
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_t?phaseId=${phaseId}"><img src="${ctxStatic }/modules/teachingview/images/index_img1.png"/></a>
					<span>教师教研情况一览</span>
				</li>
				<shiro:hasAnyRoles name="xz,fxz,njzz,zr,xkzz">
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_g?phaseId=${phaseId}"><img src="${ctxStatic }/modules/teachingview/images/index_img2.png"/></a>
					<span>年级教研情况一览</span>
				</li>
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_s?phaseId=${phaseId}"><img src="${ctxStatic }/modules/teachingview/images/index_img3.png"/></a>
					<span>学科教研情况一览</span>
				</li>
				<li>
					<a href="${pageContext.request.contextPath }/jy/teachingView/manager/m_user_list?phaseId=${phaseId}">
					<img src="${ctxStatic }/modules/teachingview/images/index_img4.png"/></a>
					<span>教学管理情况一览</span>
				</li>
        </shiro:hasAnyRoles>
			</ul>
			<div class="clear"></div>
		</div>
		<div class="index_bottom"></div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter> 
	<script>
	$(
	$("#phaseId").on("change",function(){
		if(location.href.indexOf("?phaseId") >=0 || location.href.indexOf("?") == -1){
			location.href = location.href.replace(/\?phaseId=\d*/,"?phaseId="+$(this).val());
		}else{
			location.href = location.href.replace(/&phaseId=\d*/,"")+"&phaseId="+$(this).val();
		}
	}));
	</script>
</body> 
</html>
