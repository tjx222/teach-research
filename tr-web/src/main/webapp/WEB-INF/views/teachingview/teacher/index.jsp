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
		</jy:nav>
	</div>
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
							<span>撰写数：${dataMap['jiaoanTotal']}篇/${dataMap['jiaoanWrite']}课</span>
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
							<span>撰写数：${dataMap['kejianTotal']}篇/${dataMap['kejianWrite']}课</span>
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
							<span>撰写数：${dataMap['fansiTotal']}篇/${dataMap['fansiWrite']}课</span>
							<span>分享数：${dataMap['fansiShare']}</span>
						</dd>
					</dl>
				</li>
				</a>
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_listen?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li style="border-right:none">
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img4.png"/></b>
								<span>听课记录</span>
						</dt>
						<dd>
							<span>听课次数：${dataMap['listen']}</span>
							<span>分享数：${dataMap['listenShare']}</span>
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
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_summary?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li>
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img6.png"/></b>
								<span>计划总结</span>
						</dt>
						<dd>
							<span>撰写数：${dataMap['summaryWrite']}</span>
							<span>分享数：${dataMap['summaryShare']}</span>
						</dd>
					</dl>
				</li>
				</a>
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_thesis?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li>
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img7.png"/></b>
								<span>教学文章</span>
						</dt>
						<dd>
							<span>撰写数：${dataMap['thesisWrite']}</span>
							<span>分享数：${dataMap['thesisShare']}</span>
						</dd>
					</dl>
				</li>
				</a>
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_companion?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li style="border-right:none;">
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img8.png"/></b>
								<span>同伴互助</span>
						</dt>
						<dd>
							<span>留言数：${dataMap['companionMessage']}</span>
							<span>资源交流数：${dataMap['companionRes']}</span>
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
				<a href="${pageContext.request.contextPath }/jy/teachingView/teacher/list_schoolActivity?spaceId=${searchVo.spaceId}&termId=${searchVo.termId}">
				<li style="border-bottom:none;">
					<dl class="managers_details_con_type">
						<dt>
								<b><img src="${ctxStatic }/modules/teachingview/images/img11.png"/></b>
								<span>校际教研</span>
						</dt>
						<dd>
							<span>可参与数：${dataMap['schoolActivityCanJoin']}</span>
							<span>参与数：${dataMap['schoolActivityJoin']}</span>
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
require(['jquery','teacherList'],function(){});
</script>
</html>
