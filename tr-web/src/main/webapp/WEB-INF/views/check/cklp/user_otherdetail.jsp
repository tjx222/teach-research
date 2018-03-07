<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅其他反思"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<style>
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 99%;
	}
	</style>
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:tchTop>
</div>
<div class="jyyl_nav">
	<jy:di key="${userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
	当前位置：
		<jy:nav id="ckkt_list">
			<jy:param name="name" value="查阅反思"></jy:param>
			<jy:param name="indexHref"
				value="jy/check/lesson/2/index?grade=${grade }&subject=${subject }"></jy:param>
			<jy:param name="listName" value="${u.name }"></jy:param>
		</jy:nav>
</div>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top check_teacher_top1" style="height:auto;">
			<div class="teacher_news"> 
				<div class="teacher_news_head">
					<div class="teacher_news_head_bg"></div>
					<ui:photo src="${u.photo}" width="63" height="65"></ui:photo>
				</div>
				<div class="name">${u.name}</div>
				<div class="name_right">
					<span>撰写：${param.wc1}篇/${param.wc}课</span>
					<span>提交：${param.sc}课</span>
					<span>已查阅：${param.cc}课</span>
				</div>
			</div>
			<div class="semester_sel_wrap semester_sel_wrap1" style="width:224px;">
				<div style="font-size:14px;height:27px;line-height:27px;float:left;'">
					按撰写学期：
				</div>
				<div id="div_1" class="semester_sel">
					<select name="termId" id="term"  class="chosen-select-deselect semester" style="width: 110px; height: 25px;">
						<option value="0" ${termId == 0 ?'selected':'' }>上学期</option>
						<option value="1" ${termId == 1 ?'selected':'' }>下学期</option>
					</select>
				</div>
			</div>
			<div class="clear"></div>
			<ol class="check_ol">
				<li><a href="jy/check/lesson/2/tch/${u.id}?grade=${grade}&subject=${subject}">课后反思</a></li>
				<li><a class="check_ol_act" href="javascript:void(0);" onclick="javascript:void(0)">其他反思</a></li>
			</ol>
		</div> 
		
		<div class="check_teacher_bottom1" style="border-top:none;" >
			<c:choose>
				<c:when test="${!empty resList }">
					<c:forEach items="${resList }" var="res" varStatus="st">
						<div class="doc_dl">
							<dl>
								<dd>
									<a href="jy/check/lesson/3/tch/other/${userId}/view?fasciculeId=${fasciculeId }&planId=${res.planId}&zx=${param.wc }&tj=${param.sc }&cy=${param.cc }" target="_blank">
										<ui:icon ext="doc"></ui:icon>
									</a>
									<c:if test="${not empty checkIds[res.planId] }">
										<c:if test="${checkIds[res.planId]['isUpdate'] }">
											<span class="spot"></span>
										</c:if>
										<a href="jy/check/lesson/3/tch/other/${userId}/view?fasciculeId=${fasciculeId }&planId=${res.planId}
							&zx=${param.wc }&tj=${param.sc}&cy=${param.cc }" target="_blank">
											<span class="have_access"></span>
										</a>
									</c:if>
								</dd>
								<dt>
									<span title="${res.planName }" class="doc_title">
										<a href="jy/check/lesson/3/tch/other/${userId}/view?planId=${res.planId}" target="_blank">
											<ui:sout value="${res.planName }" length="16" needEllipsis="true"/>
										</a>
									</span>
									<span class="doc_date"><fmt:formatDate value="${res.submitTime }"
											pattern="yyyy-MM-dd" /></span>
								</dt>
							</dl>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<!-- 无文件 -->
					<div class="cont_empty">
					    <div class="cont_empty_img"></div>
					    <div class="cont_empty_words">教师还没有提交${type == 0 ? '教案': type == 1? '课件':'反思' }哟，稍后再来查阅吧！</div> 
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/javascript">
		$(document).ready(function() {
			//下拉列表
			$(".chosen-select-deselect").chosen({disable_search : true});  
			$("#term").change(function(){
				window.location.href = _WEB_CONTEXT_ + "/jy/check/lesson/3/tch/other/${userId}?&grade=${grade}&subject=${subject}&termId=" + $(this).val()+"&searchType=1&wc=${param.wc}&wc1=${param.wc1}&sc=${param.sc}&cc=${param.cc}";//页面跳转并传参 
			});
		});
	</script>
</body>
</html>