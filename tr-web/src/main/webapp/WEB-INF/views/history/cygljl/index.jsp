<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="听课记录信息"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css" media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<ui:require module="history/js"></ui:require>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
</head>
<body style="background: #fff;">
<div class="calendar_year_center">
	<div class="page_option">
		<form id="search_form" action="jy/history/${year}/cygljl/index" method="post">
			<div class="a">
				<select class="chosen-select-deselect class_teacher" name="id" style="width: 150px; height: 25px;" onchange="this.form.submit()">
					<c:choose>
						<c:when test="${not empty spaceList }">
							<c:forEach items="${spaceList }" var="space">
								<c:if test="${space.sysRoleId!=27}">
									<option value="${space.id}" ${space.id==spaceId?'selected':''}>${space.spaceName}</option>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<option>请选择职务</option>
						</c:otherwise>
					</c:choose>
				</select>
				<input type="hidden" name="userId" value="${userSpace.userId}"> 
				<input type="hidden" name="year" id="year" value="${year}"> 
			</div> 
		</form>
	</div>
	<div class="resources_table_wrap resources_table_wrap1">
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_lesson?spaceId=${spaceId}&typeId=0">
				<div class="manage_check_img1 manage_check_img_1"></div>
				<div class="manage_check_1_title1">查阅教案</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.jiaoAnCount} </li>
						<li>查阅意见：${countMap.jiaoAnOptionCount}</li>
					</ul> 
				</div>
			</a>
		</div>
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_lesson?spaceId=${spaceId}&typeId=1">
			<div class="manage_check_img1 manage_check_img_2"></div>
				<div class="manage_check_1_title1">查阅课件</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.keJianCount} </li>
						<li>查阅意见：${countMap.keJianOptionCount}</li>
					</ul>
				</div>
			</a>
		</div>
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_fansi?spaceId=${spaceId}">
				<div class="manage_check_img_3"></div>
				<div class="manage_check_1_title1">查阅反思</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.fanSiCount}</li>
						<li>查阅意见：${countMap.fanSiOptionCount}</li>
					</ul> 
				</div>
			</a>
		</div>
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_thesis?spaceId=${spaceId}">
				<div class="manage_check_img_3"></div>
				<div class="manage_check_1_title1">查阅教学文章</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.thesisCount}</li>
						<li>查阅意见：${countMap.thesisOptionCount}</li>
					</ul> 
				</div>
			</a>
		</div>
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_lecture?spaceId=${spaceId}">
				<div class="manage_check_img_3"></div>
				<div class="manage_check_1_title1">查阅听课记录</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.lectureCount}</li>
						<li>查阅意见：${countMap.lectureOptionCount}</li>
					</ul> 
				</div>
			</a>
		</div>
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_activity?spaceId=${spaceId}">
				<div class="manage_check_img1 manage_check_img_4"></div>
				<div class="manage_check_1_title1">查阅集体备课</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.activityCount}</li>
						<li>查阅意见：${countMap.activityOptionCount}</li>
					</ul>  
				</div>
			</a>
		</div>
		<div class="manage_check_each manage_check_each_hover">
			<a href="${ctx}jy/history/${year}/cygljl/check_planSummary?spaceId=${spaceId}">
				<div class="manage_check_img_5"></div>
				<div class="manage_check_1_title1">查阅计划总结</div>
				<div class="manage_check_1_num">
					<ul>
						<li>查阅数：${countMap.lessonPlanCount}</li>
						<li>查阅意见：${countMap.lessonPlanOptionCount}</li>
					</ul>  
					 
				</div>
			</a>
		</div>
	</div>  
	<div class="clear"></div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/placeholders/placeholder.js"></script>

<script type="text/javascript">
require(['jquery','managerCheck'],function(){});
</script>
</html>
