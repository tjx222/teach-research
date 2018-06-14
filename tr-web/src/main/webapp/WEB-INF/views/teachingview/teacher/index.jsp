<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教研情况一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教师教研一览"></ui:tchTop>
	</div>
	
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl">
		<jy:param name="spaceId" value="${param.spaceId }"></jy:param>
		</jy:nav>
	</div>
	<c:if test="${fn:length(spaces) > 1 }">
		<div class="content_top_right" style="width: 22rem; height: 3.583rem; float: right; padding-top: 0.833rem; cursor: pointer;">
				 <label style="display: inline-block;width: 6rem;float: left; height: 3rem; line-height: 3rem; font-size: 1.333rem; color: #999;">学段：</label>
                <div>
                <select name="spaceId" id="spaceId" style="width: 9.3rem; border: none; line-height: 3rem;  height: 3rem; font-size: 1.127rem; color: #999; background: #f7f8f9;">
                 <c:forEach items="${spaces }" var="space">
                   <option value="${space.id }" ${space.id == spaceId ? 'selected':'' }>${space.spaceName }</option>
                 </c:forEach>
                </select>
            </div>
		</div>
	</c:if>
	<div class="teachingTesearch_managers_details_content">
		<form id="form1" action="${pageContext.request.contextPath }/jy/teachingView/teacher/index" method="post">
		<div class="teachingTesearch_managers_top">
			<p class="teachingTesearch_managers_title">教研情况一览</p>
			<ul class="teachingTesearch_managers_semester">
				<li>
					<input type="radio" name="termId" id="radio_shang" value="0" <c:if test="${search.termId==0 }">checked="checked"</c:if>/>
					<label for="radio_shang">上学期</label>
				</li>
				<li>
					<input type="radio" name="termId" id="radio_xia" value="1" <c:if test="${search.termId==1 }">checked="checked"</c:if>/>
					<label for="radio_xia">下学期</label>
				</li>
			</ul>
		</div>
		</form>
		<div class="managers_details_con">
			<ul class="managers_details_con_box">
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_jiaoan?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<b><img src="${ctxStatic }/modules/teachingview/images/img1.png"/></b>
							<span>教案</span>
						</dt>
						<dd>
							<span>撰写数：${dataMap['jiaoanWrite']}</span>
							<span>分享数：${dataMap['jiaoanShare']}</span>
						</dd>
					</dl>
				</li>
				</a>
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_kejian?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li>
					<dl class="managers_details_con_type">
						<dt>
							<b><img src="${ctxStatic }/modules/teachingview/images/img2.png"/></b>
							<span>课件</span>
						</dt>
						<dd>
							<span>撰写数：${dataMap['kejianWrite']}</span>
							<span>分享数：${dataMap['kejianShare']}</span>
						</dd>
					</dl>
				</li>
				</a>
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_fansi?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li>
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img3.png"/></b>
								<span>反思</span>
						</dt>
						<dd>
							<span>撰写数：${dataMap['fansiWrite']}</span>
							<span>分享数：${dataMap['fansiShare']}</span>
						</dd>
					</dl>
				</li>
				</a>
				
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_activity?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li>
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img5.png"/></b>
								<span>集体备课</span>
						</dt>
						<dd>
							<span>可参与数：${dataMap['activityCanJoin']}</span>
							<span>参与数：${dataMap['activityJoin']}</span>
						</dd>
					</dl>
				</li>
				</a>
				
				
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_recordBag?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li style="border-bottom:none;">
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img9.png"/></b>
								<span>成长档案袋</span>
						</dt>
						<dd>
							<span>精选资源数：${dataMap['teacherRecordRes']}</span>
						</dd>
					</dl>
				</li>
				</a>
				
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','teacherList'],function(){
	$("#spaceId").on("change",function(){
		if(location.href.indexOf("?spaceId") >=0 || location.href.indexOf("?") == -1){
			location.href = location.href.replace(/\?spaceId=\d*/,'?spaceId='+$(this).val());
		}else{
			location.href = location.href.replace(/&spaceId=\d*/,'')+'&spaceId='+$(this).val();
		}
	});
});
</script>
</html>
