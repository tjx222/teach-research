<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="计划总结信息"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
	<ui:require module="history/js"></ui:require>
</head>
<body style="background: #fff;">
<input type="hidden" id="currentUserId" value="${_CURRENT_USER_.id}">
<!-- 查看 -->
		<div id="jhzj_dialog_content" style="display:none">
			<div class="cont_list"  id="look_up_resName"> 
			</div>
			<div class="cont_list"  id="look_up_category"> 
			</div> 
			<div class="cont_list"  id="look_up_date"> 
			</div>
			<div class="cont_list"  id="look_up_share"> 
			</div>
			<div class="cont_list" id="look_up_resSize"> 
			</div>
			<div class="cont_list" id="look_up_resType"> 
			</div> 
		</div>

<div class="calendar_year_center">
	<div class="page_option"> 
		<form id="listForm" action="${ctx }jy/history/${year}/jhzj/index" method="post">
			<div class="a">
			<c:forEach items="${usList }" var="space">
				<input class="space" type="text" style="display:none;" value="${space.sysRoleId}" />
			</c:forEach>
			<input style="display:none;" value="${ps.userSpaceId }" id="roleId"/>
			<select id="roleIds" name="userSpaceId" class="chosen-select-deselect class_teacher full_year" style="width: 132px; height: 25px;">
			<option value="" data="">全部</option>
			<c:forEach items="${usList}" var="space"> 
					<option value="${space.id }" data="${space.sysRoleId }">${space.spaceName }</option>
			</c:forEach>
			</select>
			</div>
			<div class="left_border"></div>
			<div class="a1"><input id="termId" style="display:none;" value="${ps.term }"/>
				<select name="term" class="chosen-select-deselect full_year " id="term" style="width: 90px; height: 25px;">
					<option value="">全学年</option>
					<option value="0">上学期</option>
					<option value="1">下学期</option>
				</select>
			</div>  
			<div class="left_border"></div>
			<div class="a2">
				<input type="text" style="display:none;" value="${ps.category }" id = "category"/>
				<select name="category" class="chosen-select-deselect full_year" style="width: 120px; height: 25px;" id="chosenType">
					<option value="">全部类型</option>
				</select>
			</div> 
			<div class="serach">
				<input name="title" type="text" class="ser_txt" value="${ps.title }"/>
				<input type="button" class="ser_btn" />
			</div>
		</form>
	</div>
	<div class="resources_table_wrap">
		<c:if test="${empty data.datalist }"> 
			<div class="cont_empty">
				<div class="cont_empty_img"></div>
				<div class="cont_empty_words">您还没有撰写计划总结哟！</div> 
			</div>
		</c:if>
		<c:if test="${not empty data.datalist }">
			<table class="resources_table">
			<tr>
				<th style="width:402px;text-align:left;padding-left:30px;">资源名称</th>
				<th style="width:115px;">类别</th>
				<th style="width:130px;">日期</th>
				<th style="width:110px;">提交状态</th>
				<th style="width:110px;">分享状态</th>
				<th style="width:95px;">操作</th>
			</tr>
			<c:forEach var="ps" items="${data.datalist }">
				<tr>
					<td style="text-align:left;">
					<input type="checkbox" class="check" />
					<span class="psTitle" title="${ps.title}" data-resId="${ps.contentFileKey }"><ui:sout value="${ps.title}" length="26" needEllipsis="true"/></span>
					<c:if test="${ps.isCheck != 0 }">
						<span class="yue event_check_${ps.isCheck }" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></span>
					</c:if>
					<c:if test="${ps.isReview != 0 }">
						<span class="ping event_review_${ps.isReview}" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></span>
					</c:if>
					
					</td>
					<td>
						<c:if test="${ps.category=='1' }">个人计划</c:if>
						<c:if test="${ps.category=='2' }">个人总结</c:if>
						<c:if test="${ps.category=='3' }">
							<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">年级计划</c:if>
							<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr') ||jfn:checkSysRole(ps.roleId, 'fxz')}">学校计划</c:if>
							<c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">备课组计划</c:if>
							<c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">学科计划</c:if>
						</c:if>
						<c:if test="${ps.category=='4' }">
							<c:if test="${jfn:checkSysRole(ps.roleId, 'njzz')}">年级总结</c:if>
							<c:if test="${jfn:checkSysRole(ps.roleId, 'xz')||jfn:checkSysRole(ps.roleId, 'zr') ||jfn:checkSysRole(ps.roleId, 'fxz')}">学校总结</c:if>
							<c:if test="${jfn:checkSysRole(ps.roleId, 'bkzz')}">备课组总结</c:if>
							<c:if test="${jfn:checkSysRole(ps.roleId, 'xkzz')}">学科总结</c:if>
						</c:if>
					</td>
					<td>
						<fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/>
					</td>
					<td>
						<c:if test="${ps.isSubmit==1}">已提交</c:if>
						<c:if test="${ps.isSubmit==0}">未提交</c:if>
					</td>
					<td>
						<c:if test="${ps.isShare==0}">未分享</c:if>
					    <c:if test="${ps.isShare==1}">已分享</c:if>
					</td>
					<td>
					<span class="look_up" data-id="${ps.id }"></span>
					<a href="<ui:download filename="${ps.title}" resid="${ps.contentFileKey}"></ui:download>"><span class="download"></span>
					</td>
				</tr>
			</c:forEach>
		</table>
			
		<div class="resources_table_bottom">
			<div class="resources_table_check">
				<input type="checkbox" class="all" />
				全选
			</div>
			<input type="button" id="batchdownload" class="batch_edit" value="批量下载" />
		</div>
		<div class="pages">
		<!--设置分页信息 -->
			<form name="pageForm" method="post">
				<ui:page url="${ctx }jy/history/${schoolYear }/jhzj/index" data="${data}" views="5"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
			</form>
		</div>
		</c:if>
		
	</div>
	
	
</div>

</body>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
<script type="text/javascript">
	require(['jquery','plainSummary']);
	parent.changeNav('计划总结');
</script>
</html>