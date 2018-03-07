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
			window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_self");
		}else{//参与
			if(ifActivityStart('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>',startDateStr)){
				window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_self");
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
//整理
function zhengli(activityId,typeId,startDateStr){
	if(ifActivityStart('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>',startDateStr)){
		if(typeId==1){//同备教案
			window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_blank");
		}else if(typeId==2){//主题研讨
			window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_blank");
		}else if(typeId==3){//视频教研
			window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_blank");
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
		当前位置：<jy:nav id="jyhdjl_jtbk"></jy:nav>
	</div>
	<div class="clear"></div>
	<div class="box" style="display:none;"></div>
		<form id="activityForm" action="${ctx}jy/managerecord/activity" method="post">
			<input type="hidden" id="listType" name="listType" value="${listType}"> 
			<input type="hidden" id="count1" name="count1" value="${count1}"> 
			<input type="hidden" id="count2" name="count2" value="${count2}"> 
			<input type="hidden" id="term" name="term" value="${term}"> 
		</form>
	<div class='teaching_research_list_cont'>
		<div class='t_r_l_c'>
			<div class='t_r_l_c_cont_tab'> 
				<div class="t_r_l_c_select">
						<ol id="UL">
		                	<li value="0" ${listType==0?'class="t_r_l_c_li t_r_l_c_li_act"':''}${listType==1?'class="t_r_l_c_li"':''} >
		                	发起（${count1 }）
		                	</li>
							<li value="1" ${listType==1?'class="t_r_l_c_li t_r_l_c_li_act"':''}${listType==0?'class="t_r_l_c_li"':''}>
							参与（${count2 }）
							</li>
		                </ol>
						<label style="float: right;margin-right:10px;margin-top:15px;"> 
						 	学期： 
							<input type="radio" style="width:13px;height:13px;float: none;background-image: none;vertical-align:middle;margin-top:-3px;"  <c:if test="${term==0}">checked="checked" </c:if>   name="term" value="0"  onclick="toCheckInfo1(this);"><span>上学期</span>
					<input type="radio"  style="width:13px;height:13px;float: none;background-image: none;vertical-align:middle;margin-top:-3px;" <c:if test="${term==1}">checked="checked" </c:if>   name="term" value="1"  onclick="toCheckInfo1(this);">下学期
						</label>
				</div>
				<div class='t_r_l_c_cont'> 
					<c:if test="${listType!=1}">
						<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
						<div class='t_r_l_c_cont_tab'>
							<div class='t_r_l_c_cont_table' >
								<table> 
									<tr>
										<th style="width:310px;">活动主题</th>
									    <th style="width:120px;">学科</th>
									    <th style="width:120px;">参与年级</th>
									    <th style="width:200px;">活动时间</th>
									    <th style="width:60px;">评论数</th>
									    <th style="width:80px;">提交状态</th>
									    <th style="width:80px;">分享状态</th>
									</tr>
									<c:forEach items="${activityList.datalist}" var="activity">
										<tr>
											<td style="text-align:left;">【${activity.typeName}】<a onclick="zhengli(${activity.id},${activity.typeId},'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')" style="color:#014efd;cursor:pointer;"><ui:sout value="${activity.activityName}" length="28" needEllipsis="true"></ui:sout></a></td>
											<td title="${activity.subjectName}"><span class='ellipsis'>${activity.subjectName}</span></td>
											<td title="${activity.gradeName}"><span class='ellipsis1'>${activity.gradeName}</span></td>
											<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
											<td>${activity.commentsNum}</td>
													<td><c:if test="${ !activity.isSubmit}">
										    		未提交
										    	</c:if> <c:if test="${activity.isSubmit }">
										    		已提交
										    	</c:if></td>
																<td><c:if test="${!activity.isShare}">
										   			未分享
										   		</c:if> <c:if test="${activity.isShare}">
										   			已分享
										   		</c:if></td>
												</tr>
									</c:forEach> 
								</table>
							</div>
						</div>
						</c:if>
					</c:if>
					<c:if test="${listType==1}">
						<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
						<div class='t_r_l_c_cont_tab'>
							<div class='t_r_l_c_cont_table' >
								<table> 
									<tr>
									 <th style="width:270px;">活动主题</th>
								    <th style="width:150px;">学科</th>
								    <th style="width:150px;">参与年级</th>
								    <th style="width:90px;">发起人</th>
								    <th style="width:190px;">活动时间</th>
								    <th style="width:80px;">评论数</th>
									</tr>
									<c:forEach items="${activityList.datalist}" var="activity">
										<tr>
											<td style="text-align:left;">【${activity.typeName}】<a onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver },'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')" style="color:#014efd;cursor:pointer;"><ui:sout value="${activity.activityName}" length="28" needEllipsis="true"></ui:sout></a></td>
											<td title="${activity.subjectName}"><span class='ellipsis'>${activity.subjectName}</span></td>
											<td title="${activity.gradeName}"><span class='ellipsis1'>${activity.gradeName}</span></td>
											<td>${activity.organizeUserName}</td>
											<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
											<td>${activity.commentsNum}</td>
										</tr>
									</c:forEach> 
								</table>
							</div>
						</div>
						</c:if>
					</c:if>
					<c:if test="${empty activityList.datalist }">
						<div class="empty_wrap">
							<div class="empty_img"></div>
							<div class="empty_info">
							<c:if test="${listType!=1}">
								您还没有发起的教研活动！
							</c:if>
							<c:if test="${listType==1}">
								您现在还没有可参与的集体备课，稍后再来吧！
							</c:if>
							</div>
						</div>
					</c:if>
				</div> 
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/managerecord/activity" data="${activityList}"  />
					<input type="hidden" class="currentPage" name="currentPage">
					<input type="hidden" id="" name="listType" value="${listType}"> 
					<input type="hidden" id="count1" name="count1" value="${count1}"> 
			        <input type="hidden" id="count2" name="count2" value="${count2}"> 
			        <input type="hidden" id="term" name="term" value="${term}"> 
				</form>
			</div>
		</div>
	</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div> 
</body>
<script type="text/javascript">
$(document).ready(function(){
	 $('.close').click(function (){
			$.unblockUI();
			location.href= _WEB_CONTEXT_+"/jy/managerecord/activity";
	});
});

function toCheckInfo1(obj){
	location.href = _WEB_CONTEXT_ + "/jy/managerecord/activity?listType="+$("#listType").val()+"&term="+$(obj).val();
}
</script>
</html>