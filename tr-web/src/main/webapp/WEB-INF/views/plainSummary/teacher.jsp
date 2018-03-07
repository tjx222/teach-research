<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="教研平台"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen"> 
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
</head>
<body>
    <div class="jyyl_top">
		<ui:tchTop style="1" hideMenuList="false"></ui:tchTop></div>
		<div class="jyyl_nav">
				当前位置：
				<jy:nav id="jhzj_check_teacher" hidden="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')?null:2 }">
					<jy:param name="teacherName" value="${user.name }"></jy:param>
				</jy:nav>
		</div>
		<div class="clear"></div>

		<%-- <div class="check_lesson_primary">
			<div class="check_lesson_primary_l">
				<h3>计划总结</h3>
				<c:choose>
					<c:when test="${_CURRENT_SPACE_.subjectId!=0 }">
						<h4>
							<span class="primary_act subject" data-subjectId="${_CURRENT_SPACE_.subjectId }" data-subjectName="<jy:dic
									key="${_CURRENT_SPACE_.subjectId }" />"><jy:dic
									key="${_CURRENT_SPACE_.subjectId }" /></span>
						</h4>
					</c:when>
					<c:otherwise>
						<ui:relation var="subjects" type="xdToXk"
							id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
						<c:forEach items="${subjects }" var="s" varStatus="st">
							<h4>
              					<span data-subjectId="${s.id }" data-subjectName="${s.name }"  class="${subjectId == s.id || (empty subjectId && st.index == 0) ? 'primary_act' :'' } subject">${s.name }</span> 
							</h4>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="check_lesson_primary_r">
				<h3>
					<ol>
						<c:choose>
							<c:when test="${_CURRENT_SPACE_.gradeId!=0 }">
								<li data-gradeId="${_CURRENT_SPACE_.gradeId }" class="grade nj_act" data-gradeName="<jy:dic
										key="${_CURRENT_SPACE_.gradeId }" />"><jy:dic
										key="${_CURRENT_SPACE_.gradeId }" /></li>
							</c:when>
							<c:otherwise>
								<ui:relation var="grades" type="xdToNj"
									id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${grades }" var="g" varStatus="st">
									<li data-gradeId="${g.id }" data-gradeName="${g.name }"
										class="grade ${gradeId == g.id || (empty gradeId && st.index == 0) ? 'nj_act':'' }">${g.name }</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ol>
				</h3>
				<div class="grade_tab" id="content">
					
				</div>
			</div>
		</div> --%>
		<div class="check_teacher_wrap">
			<div class="check_teacher_wrap1"> 
				<div class="check_teacher_bottom clearfix" style="border-top:0;">
					<div class="out_reconsideration_see_title_box">
				        <span class="scroll_leftBtn"></span>
				        <div class="in_reconsideration_see_title_box" > 
					        <ol class="olgrade reconsideration_see_title" >
								<c:choose>
									<c:when test="${_CURRENT_SPACE_.gradeId!=0 }">
										<li data-gradeId="${_CURRENT_SPACE_.gradeId }" class="grade nj_act" data-gradeName="<jy:dic
												key="${_CURRENT_SPACE_.gradeId }" />"><jy:dic
												key="${_CURRENT_SPACE_.gradeId }" /></li>
									</c:when>
									<c:otherwise>
										<ui:relation var="grades" type="xdToNj"
											id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
										<c:forEach items="${grades }" var="g" varStatus="st">
											<li data-gradeId="${g.id }" data-gradeName="${g.name }"
												class="grade ${gradeId == g.id || (empty gradeId && st.index == 0) ? 'nj_act':'' }">${g.name }</li>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</ol> 
						</div>
						<span class="scroll_rightBtn"></span>
					</div> 
					<div class="check_teacher_bottom_inside clearfix">
						<div class="check_teacher_bottom_inside_l">
							<ol class="olsubject">
								<c:choose>
									<c:when test="${_CURRENT_SPACE_.subjectId!=0 }">
											<li class="primary_act subject" data-subjectId="${_CURRENT_SPACE_.subjectId }" data-subjectName="<jy:dic
													key="${_CURRENT_SPACE_.subjectId }" />"><jy:dic
													key="${_CURRENT_SPACE_.subjectId }" /></li>
									</c:when>
									<c:otherwise>
										<ui:relation var="subjects" type="xdToXk"
											id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
										<c:forEach items="${subjects }" var="s" varStatus="st">
				              				<li data-subjectId="${s.id }" data-subjectName="${s.name }"  class="${subjectId == s.id || (empty subjectId && st.index == 0) ? 'primary_act' :'' } subject">${s.name }</li> 
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</ol>
						</div>
						<div class="check_teacher_bottom_inside_r" id="content">
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	
	<script type="text/x-Hanldebars" id="contentTemplate">
				<div class="check_info">
					<div class="grade_subject">{{gradeSubjectName}}</div>
					<div class="grade_subject">撰写：{{total.plainSummaryNum }}</div>
					<div class="grade_subject">提交：{{total.plainSummarySubmitNum }}</div>
					<div class="grade_subject">已查阅：{{total.plainSummaryCheckedNum }}</div>
				</div>
				{{#checkStatisticses}}
					<div class="leader_info" >
						<div class="teacher_info_top" data-spaceId="{{userSpaceId}}" data-userId="{{user.id}}">
							<a href="jy/planSummaryCheck/userSpace/{{userSpaceId}}/planSummary?userId={{user.id}}">
							<div class="teacher_info_top_bg">
								<ol class="tea_info" >
									<li><span></span>撰写：{{plainSummaryNum }}课</li>
									<li><span></span>提交：{{plainSummarySubmitNum }}课</li>
									<li><span></span>已查阅：{{plainSummaryCheckedNum }}课</li>
								</ol> 
							</div>
							<ui:photo src="${user.photo}"></ui:photo>
							</a>
						</div>
						<div class="teacher_info_bottom">{{user.name}}</div>
					</div>
				{{/checkStatisticses}}
				{{#noTeacher}}
					<div class="empty_wrap">
						<div class="empty_info">
							教师还没有提交计划总结哟，稍后再来查阅吧！
						</div>
					</div>
				{{/noTeacher}}
	</script>

	<ui:require module="planSummary/js"></ui:require>
	<script type="text/javascript">
	require([ 'teacher' ]);
	$(function(){
		$('#content').on("click",".teacher_info_top",function(){
			var spaceId=$(this).attr("data-spaceId");
			var userId=$(this).attr("data-userId");
			location.href= _WEB_CONTEXT_+'/jy/planSummaryCheck/userSpace/'+spaceId+'/planSummary?userId='+userId;
		})
	})
	</script>	
</body>
</html>