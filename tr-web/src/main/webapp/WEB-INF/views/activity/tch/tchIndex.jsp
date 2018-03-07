<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,java.text.SimpleDateFormat"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="集体备课"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
	
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<ui:require module="activity/js"></ui:require>
<script type="text/javascript">
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','activity'],function(){});
//参与或查看
function canyu_chakan(activityId,typeId,isOver,startDateStr){
	if(typeId==1){//同备教案
		if(isOver){//已结束，则查看
			window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_blank");
		}else{//参与
			if(ifActivityStart('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>',startDateStr)){
				window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_blank");
			}
		}
	}else if(typeId==2){//主题研讨
		if(isOver){//已结束，则查看
			window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_self");
		}else{//参与
			if(ifActivityStart('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>',startDateStr)){
				window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
			}
		}
	}else if(typeId==3){//视频教研
		if(isOver){//已结束，则查看
			window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_self");
		}else{//参与
			if(ifActivityStart('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>',startDateStr)){
				window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
			}
		}
	}
}
</script>
</head>
<body>
<div class="wrapper"> 
	<div class='jyyl_top'>
	<ui:tchTop style="1" modelName="集体备课"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：<jy:nav id="jtbk"></jy:nav>
	</div>
	<div class='teaching_research_list_cont'>
		<div class='t_r_l_c'>
			<div class='t_r_l_c_cont_tab'>  
				<div class='t_r_l_c_cont' style="margin:0 auto;padding-top:23px;"> 
					<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
					<div class='t_r_l_c_cont_tab'>
						<div class='t_r_l_c_cont_table' >
							<table> 
								<tr>
									<th style="width:230px;">活动主题</th>
									<th style="width:120px;">参与学科</th>
									<th style="width:90px;">参与年级</th>
									<th style="width:150px;">发起人</th>
									<th style="width:190px;">活动时限</th>
									<th style="width:60px;">讨论数</th> 
									<th style="width:60px;">操作</th> 
								</tr>
								<c:forEach items="${activityList.datalist}" var="activity">
									<tr>
										<td style="text-align:left;"><a class="tdName">【${activity.typeName}】</a><a class='td_name1 td_name_d' title="${activity.activityName}" onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver },'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')">${activity.activityName}</a></td>
										<td title="${activity.subjectName}"><span class='ellipsis'>${activity.subjectName}</span></td>
										<td title="${activity.gradeName}"><span class='ellipsis1'>${activity.gradeName}</span></td>
										<td>${activity.organizeUserName}</td>
										<td style="width:180px;"><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
										<td>${activity.flago}</td>
										<td>
										<c:if test="${!activity.isOver}">
											<span title='参与' class='partake_btn' onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver },'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')"></span>
										</c:if>
										<c:if test="${activity.isOver}">
											<span title='查看' class='see_btn' onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver },'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')"></span>
										</c:if>
										</td> 
									</tr>
								</c:forEach> 
							</table>
						</div>
					</div>
					</c:if>
					<c:if test="${empty activityList.datalist }">
						<div class="empty_wrap">
							<div class="empty_img"></div>
							<div class="empty_info">您现在还没有可参与的集体备课，稍后再来吧！</div>
						</div>
					</c:if>
				</div> 
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/activity/tchIndex" data="${activityList}" />
					<input type="hidden" class="currentPage" name="currentPage">
					<input type="hidden" id="" name="listType" value="${listType}">
				</form>
			</div>
		</div>
	</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div> 
</body>

</html>