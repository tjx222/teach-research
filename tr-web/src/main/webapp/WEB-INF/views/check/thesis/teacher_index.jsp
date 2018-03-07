<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.tmser.tr.uc.SysRole"%>
<c:set var="BKZZ" value="<%=SysRole.BKZZ%>"/>
<c:set var="TEACHER" value="<%=SysRole.TEACHER%>"/>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅教学文章"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<style>
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
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅教学文章"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：
		<jy:nav id="check_thesis_select" hidden="${flags=='mt'?2:null}">
			<jy:param name="checkName" value="查阅教师"></jy:param>
			<jy:param name="flago" value="${flago}"></jy:param>
			<jy:param name="gradeId" value="${grade}"></jy:param>
			<jy:param name="subjectId" value="${subject}"></jy:param>
			<jy:param name="sysRoleId" value="${sysRole}"></jy:param>
		</jy:nav>
</div>

<div class="check_teacher_wrap">
	<input type="hidden" value="${term}" id="tesis_term">
	<input type="hidden" value="${TEACHER.id}" id="js_role">
	<input type="hidden" value="${flago}" id="flago_thesis">
	<input type="hidden" value="${flags}" id="flags_thesis">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top">
			<div class="semester_sel_wrap">
				<label for="">学期：</label>
				<div class="semester_sel">
					<select name="fasciculeId" id="fasciculeId" class="chosen-select-deselect semester" style="width: 110px; height: 25px;">
						<option value="0" ${term == 0 ?'selected':'' }>上学期</option>
						<option value="1" ${term == 1 ?'selected':'' }>下学期</option>
					</select>
				</div>
			</div>
		</div>
		<div class="check_teacher_bottom clearfix">
			<div class="out_reconsideration_see_title_box">
		        <span class="scroll_leftBtn"></span>
		        <div class="in_reconsideration_see_title_box" > 
					<ol class="ol_grade te_grade reconsideration_see_title">
						<c:choose>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
								<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${grades }" var="g" varStatus="st">
									<li data="${g.id }" class="li_bg ${grade == g.id || (empty grade && st.index == 0) || (grade==-1 && st.index == 0) ? 'li_act':'' }">${g.name }</li>
								</c:forEach>
							</c:when>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
								  <li data="${_CURRENT_SPACE_.gradeId }" class="li_bg li_act"><jy:dic key="${_CURRENT_SPACE_.gradeId }"/></li>
							</c:when>
							<c:otherwise>
							
							</c:otherwise>
						</c:choose>
					</ol>
				</div>
				<span class="scroll_rightBtn"></span>
			</div>
			
			<div class="clear"></div>
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="ol_subject te_subject">
						<c:choose>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
								<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${subjects }" var="s" varStatus="st">
									<li data="${s.id }" class="ol_subject_li ${subject == s.id || (empty subject && st.index == 0) || (subject==-1 && st.index == 0) ? 'ol_subject_li_act' :'' }">${s.name }</li>
								</c:forEach>
							</c:when>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
									<li class="ol_subject_li ol_subject_li_act" data="${_CURRENT_SPACE_.subjectId }"><jy:dic key="${_CURRENT_SPACE_.subjectId }"/></li>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</ol>
				</div>
				<div class="check_teacher_bottom_inside_r">
					
				</div>
			</div>
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