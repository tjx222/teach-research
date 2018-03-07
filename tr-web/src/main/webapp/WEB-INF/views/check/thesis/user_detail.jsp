<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="查阅教学文章"></ui:htmlHeader>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<style type="text/css">
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 99%;
	}
	</style>
	<ui:require module="check/check_thesis/js"></ui:require>
</head>
<body>

	<form id="view_form" target="_blank">
		<input type="hidden" name="schoolTerm" id="view_term">
		<input type="hidden" name="flago" id="view_flago">
		<input type="hidden" name="flags" id="view_flags">
		<input type="hidden" name="ids" id="view_ids">
		<input type="hidden" name="id" id="view_id">
		<input type="hidden" name="gradeId" id="f_grade" value="${paramSpace.gradeId}">
		<input type="hidden" name="subjectId" id="f_subject" value="${paramSpace.subjectId}">
		<input type="hidden" name="sysRoleId" id="f_sysRoleId" value="${paramSpace.sysRoleId}">
	</form>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅教学文章"></ui:tchTop>
</div>
<div class="jyyl_nav">
	<jy:di key="${userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
	当前位置：
		<jy:nav id="check_thesis_detail" hidden="${thesis.flags=='mt'?2:null}">
			<jy:param name="indexHref"
				value="jy/check/thesis/tch/${userId}?schoolTerm=${thesis.schoolTerm}&flago=${thesis.flago}&flags=${thesis.flags}
				&gradeId=${paramSpace.gradeId==null?-1:paramSpace.gradeId}
				&subjectId=${paramSpace.subjectId==null?-1:paramSpace.subjectId}
				&sysRoleId=${paramSpace.sysRoleId==null?-1:paramSpace.sysRoleId}"></jy:param>
			<jy:param name="gradeId" value="${paramSpace.gradeId==null?-1:paramSpace.gradeId}"></jy:param>
			<jy:param name="subjectId" value="${paramSpace.subjectId==null?-1:paramSpace.subjectId}"></jy:param>
			<jy:param name="sysRoleId" value="${paramSpace.sysRoleId==null?-1:paramSpace.sysRoleId}"></jy:param>
			<jy:param name="teacherName" value="${u.name }"></jy:param>
			<jy:param name="flago" value="${thesis.flago}"></jy:param>
			<jy:param name="checkName" value="${thesis.flago=='t'?'查阅教师':'查阅管理者'}"></jy:param>
		</jy:nav>
</div>
<div class="check_teacher_wrap">
	<input type="hidden" value="${userId}" id="d_userId">
	<input type="hidden" value="${paramSpace.gradeId}" id="d_grade">
	<input type="hidden" value="${paramSpace.subjectId}" id="d_subject">
	<input type="hidden" value="${paramSpace.sysRoleId}" id="d_sysRoleId">
	<input type="hidden" value="${thesis.flago}" id="flago_thesis">
	<input type="hidden" value="${thesis.flags}" id="flags_thesis">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top check_teacher_top1">
			<div class="teacher_news"> 
				<div class="teacher_news_head">
					<div class="teacher_news_head_bg"></div>
					<ui:photo src="${u.photo}" width="63" height="65"></ui:photo>
				</div>
				<div class="name">${u.name}</div>
				<div class="name_right">
					<span>撰写：${writeCount}课</span>
					<span>提交：${submitCount}课</span>
					<span>已查阅：${checkCount}课</span>
				</div>
			</div>
			<div class="semester_sel_wrap semester_sel_wrap1">
				<label for="">学期：</label>
				<div class="semester_sel">
					<select name="fasciculeId" id="fasciculeId_detail"  class="chosen-select-deselect semester" style="width: 110px; height: 25px;">
						<option value="0" ${term == 0 ?'selected':'' }>上学期</option>
						<option value="1" ${term == 1 ?'selected':'' }>下学期</option>
					</select>
				</div>
			</div>
		</div> 
		<div class="check_teacher_bottom1">
			<c:choose>
				<c:when test="${!empty writeList }">
					<c:forEach items="${writeList }" var="res" varStatus="st">
						<div class="doc_dl" data-resid="${res.id}">
							<dl>
								<dd>
									<a class="go_viewa" data-id="${res.id}" data-userid="${userId}" data-term="${thesis.schoolTerm}" data-flago="${thesis.flago }" data-flags="${thesis.flags!='mt'?'':thesis.flags}" >
										<img src="${ctxStatic }/common/icon/base/word.png" alt="">
									</a>
									<c:if test="${not empty checkMaps[res.id] }">
										<c:if test="${checkMaps[res.id]['isUpdate'] }">
											<span class="spot"></span>
										</c:if>
										<a  class="go_viewa" data-id="${res.id}" data-userid="${userId}" data-term="${thesis.schoolTerm}" data-flago="${thesis.flago }" data-flags="${thesis.flags!='mt'?'':thesis.flags}" >
											<span class="have_access"></span>
										</a>
									</c:if>
								</dd>
								<dt>
									<span title="[${res.thesisType}]${res.thesisTitle }" class="doc_title">
										<a class="go_viewa" data-id="${res.id}" data-userid="${userId}" data-term="${thesis.schoolTerm}" data-flago="${thesis.flago }" data-flags="${thesis.flags!='mt'?'':thesis.flags}" >
											<ui:sout value="[${res.thesisType}]${res.thesisTitle }" length="16"
													needEllipsis="true" />
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
					    <div class="cont_empty_words">没有资源哟！</div> 
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/javascript">
		require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','AmazeUI/amazeui.chosen.min','check_thesis'],function($){
		}); 
	</script>
</body>
</html>