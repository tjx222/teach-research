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
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
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
	<jy:nav id="check_thesis_select">
		<jy:param name="checkName" value="查阅管理者"></jy:param>
		<jy:param name="flago" value="${flago}"></jy:param>
		<jy:param name="gradeId" value="${grade}"></jy:param>
		<jy:param name="subjectId" value="${subject}"></jy:param>
		<jy:param name="sysRoleId" value="${sysRole}"></jy:param>
	</jy:nav>
</div>

<div class="check_teacher_wrap">
	<input type="hidden" value="${term}" id="tesis_term">
	<input type="hidden" value="${BKZZ.id}" id="bkzz_role">
	<input type="hidden" value="${flago}" id="flago_thesis">
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
			<div class="out_reconsideration_see_title_box" id="bkzz_grade" <c:if test="${sysRole!=BKZZ.id}">style="display:none;"</c:if> >
		        <span class="scroll_leftBtn"></span>
		        <div class="in_reconsideration_see_title_box" > 
					<ol class="ol_grade m_grade reconsideration_see_title"> 
						<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
						<c:forEach items="${grades }" var="g" varStatus="st">
							<li data="${g.id }" class="li_bg ${grade == g.id || (empty grade && st.index == 0) || (grade==-1 && st.index == 0) ? 'li_act':'' }">${g.name }</li>
						</c:forEach> 
					</ol>
				</div>
				<span class="scroll_rightBtn"></span>
			</div>
			<div class="clear"></div>
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="ol_subject m_sysrole">
						<c:forEach items="${sysRoles }" var="s" varStatus="sys">
							<li data="${s.id }" class="ol_subject_li ${sysRole == s.id || (empty sysRole && sys.index == 0) || (sysRole==-1 && sys.index == 0) ? 'ol_subject_li_act' :'' }">${s.cname }</li>
						</c:forEach>
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