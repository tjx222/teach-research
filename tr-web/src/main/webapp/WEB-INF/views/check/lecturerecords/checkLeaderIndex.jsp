<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅听课记录"></ui:htmlHeader>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<style>
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 99%;
	}
	</style>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅管理者"></ui:tchTop>
</div>
<div class="jyyl_nav">
<c:if test="${!empty lr.gradeId }">
	当前位置：<jy:nav id="check_leader1">
				<jy:param name="gradeId" value="${lr.gradeId }"></jy:param>
				<jy:param name="termId" value="${lr.term }"></jy:param>
				<jy:param name="sysRoleId" value="${sysRoleId}"></jy:param>
		   </jy:nav>
</c:if>
<c:if test="${empty lr.gradeId }">
当前位置：<jy:nav id="check_leader">
				<jy:param name="termId" value="${lr.term }"></jy:param>
				<jy:param name="sysRoleId" value="${sysRoleId}"></jy:param>
	   </jy:nav>
</c:if>
</div> 
<form id="form1" action="${ctx}jy/check/lectureRecords/toCheckLeaderIndex" method="post">
<input type="hidden" id="gradeId" name="gradeId" value="${lr.gradeId }"/>
<input type="hidden" id="sysRoleId" name="sysRoleId" value="${sysRoleId }"/>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top">
			<div class="semester_sel_wrap">
				<label for="">学期：</label>
				<div class="semester_sel">
					<select name="term" class="chosen-select-deselect semester" style="width: 80px; height: 25px;" onchange="form1.submit();">
						<option value="0" <c:if test='${lr.term==0 }'>selected="selected"</c:if>>上学期</option>
						<option value="1" <c:if test='${lr.term==1 }'>selected="selected"</c:if>>下学期</option>
					</select>
				</div>
			</div>
		</div>
		<div class="check_teacher_bottom clearfix"> 
			<c:if test="${!empty gradeList }">
				<div class="out_reconsideration_see_title_box">
			        <span class="scroll_leftBtn"></span>
			        <div class="in_reconsideration_see_title_box"> 
						<ol class="olgrade reconsideration_see_title">
							<c:forEach var="grade" items="${gradeList }">
								<li class="li_bg <c:if test='${lr.gradeId==grade.id }'>li_act</c:if>" gradeId="${grade.id }">${grade.name }</li>
							</c:forEach>
						</ol> 
					</div>
					<span class="scroll_rightBtn"></span>
				</div>
			</c:if>
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="olsubject">
						<c:forEach var="roleMap" items="${roleMapList }">
							<li class="ol_subject_li <c:if test='${sysRoleId==roleMap.roleId }'>ol_subject_li_act</c:if>" roleId="${roleMap.roleId }">${roleMap.roleName }</li>
						</c:forEach>
					</ol>
				</div>
				<div class="check_teacher_bottom_inside_r">
					<div class="check_info">
						<div class="grade_subject">共${leaderCount }人</div>
						<div class="grade_subject">撰写总数：${totalWriteCount }课</div>
						<div class="grade_subject">提交总数：${totalSubmitCount }课</div>
						<div class="grade_subject">已查阅总数：${totalScanCount }课</div>
					</div>
					<c:forEach var="leaderMap" items="${leaderMapList }">
						<div class="leader_info" userId="${leaderMap.userSpace.userId }" termId="${lr.term }" gradeId="${!empty lr.gradeId?lr.gradeId:'' }">
							<div class="teacher_info_top">
								<div class="teacher_info_top_bg">
									<ol class="tea_info">
										<li><span></span>撰写：${leaderMap.writeCount }课</li>
										<li><span></span>提交：${leaderMap.submitCount }课</li>
										<li><span></span>已查阅：${leaderMap.scanCount }课</li>
									</ol> 
								</div>
								<jy:di key="${leaderMap.userSpace.userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
								<ui:photo src="${u.photo}"></ui:photo>
							</div>
							<div class="teacher_info_bottom">${leaderMap.userSpace.username }</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
<ui:htmlFooter style="1"></ui:htmlFooter>
<ui:require module="check/check_lecturerecords/js"></ui:require>
<script type="text/javascript">
require(['jquery','check_lecturerecords']);
</script>
</body>
</html>