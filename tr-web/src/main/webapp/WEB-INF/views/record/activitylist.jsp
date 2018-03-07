<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教研活动"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/record/css/recordCss.css" media="screen"> 
<ui:require module="record/js"></ui:require>
<script type="text/javascript">
var we = '${ctx}';
var id = '${id}';
var type = '${type}';
var page = '${page.currentPage}';
//参与或查看
function canyu_chakan(activityId,typeId,isOver){
	if(typeId==1){//同备教案
		if(isOver){//已结束，则查看
			window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_blank");
		}else{//参与
			window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_blank");
		}
	}else if(typeId==2){//主题研讨
		if(isOver){//已结束，则查看
			window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_blank");
		}else{//参与
			window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_blank");
		}
	}
}
	/* //参与活动
	function canyuActivity(activityId,typeId){
		if(typeId==1){//参与同备教案
			window.open(_WEB_CONTEXT_+"/jy/activity/cyjb","_blank");
		}else if(typeId==2){//参与主题研讨
			window.open(_WEB_CONTEXT_+"/jy/activity/cyzy","_blank");
		}
	}
	//查看活动
	function chakanActivity(activityId,typeId){
		if(typeId==1){//查看同备教案（整理）
			window.open(_WEB_CONTEXT_+"/jy/activity/zltb","_blank");
		}else if(typeId==2){//查看主题研讨
			window.open(_WEB_CONTEXT_+"/jy/activity/ckzy","_blank");
		}
	} */
</script>
</head>
<div id="_jx" class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">精选</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<form id="formular">
				<div class="Growth_Portfolio_bottom">
					<input type="hidden" id="one">
					<p>
						<label for="">名称：</label> <span id="name"></span><strong></strong>
					</p>
					<div class="clear"></div>
					<p>
						<label for="" id="lab">微评：</label>
						<textarea style="float: left;padding: 5px;font-size: 12px;" name="desc" id="desc" cols="38" rows="4"
							class="validate[optional,maxSize[50]] text-input" placeholder="您可以给精选文件添加微评，如不想添加，请直接点击“保存”"></textarea>
						<strong class="strong_sty" >注：最多可输入50个字</strong>
					</p>
					<div class="clear"></div>
					<p style="margin-top: 20px;text-align:center;">
						<input type="button" id="button" class="Growth_Portfolio_btn btn_bottom_1" value="保存" >
					</p>
				</div>
			</form>
		</div>
	</div>
</div>
<body> 
	<div class="wrapper">
		<div class="jyyl_top">
			<ui:tchTop style="1" modelName="成长档案袋" ></ui:tchTop>
		</div> 
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="czdad"></jy:nav>
				&nbsp;&gt;&nbsp;<a href="${ctx }jy/record/sysList?id=${id}&type=${type}">教研活动</a>&nbsp;&gt;&nbsp;精选
			</h3>
		</div>
		<div class="clear"></div>
		<div class="jx_list_content">
			<div class="Growth_Portfolio_jx">
				<h3 class="Growth_Portfolio_content1_h3">教研活动</h3> 
				<form id="searchForm" name="searchForm" method="post">
				 <p>
				    <input type="text" name="planName" id="planName" value="${name}" placeholder="输入活动主题进行查找" class="txt_seh"> 
					<input type="hidden" name="id" value="${id}"> 
					<input type="hidden" name="type" value="${type}"> 
					<input type="button" class="txt_btn">
				 </p>
				    <input type="button" class="cen_btn" value="返回" data-id="${id}" data-type="${type}">
				</form>
				<h4 id="all" style="cursor: pointer;">
					全部<span> < </span>
				</h4>
			</div>
			
			<div class="jx_list_content1">
				<form id="activityForm" action="${ctx}jy/activity/index"
					method="post"></form>
				<%-- <h3>
					<input type="button" class="btn" onclick="toActivityEdit('${ctx}');"> 
				</h3>--%>
				<div class="collective_cont_big">
					<div class="Growth_Portfolio_tab">
						<div class="Growth_Portfolio_table">
							<c:if test="${not empty  activityList.datalist}">
								<table >
									<tr>
										<th style="width: 340px;">活动主题</th>
										<th style="width: 100px;">参与学科</th>
										<th style="width: 100px;">参与年级</th>
										<th style="width: 60px;">发起人</th>
										<th style="width:180px;">活动时限</th>
										<th style="width: 60px;">评论数</th>
										<th style="width: 60px;">操作</th>
									</tr>
									<c:forEach items="${activityList.datalist}" var="activity">
										<tr>
											<td title="${activity.activityName}" style="text-align: left;">【${activity.flago}】【${activity.typeName}】<a
												onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver })"
												style="color: #014efd; cursor: pointer;"><ui:sout
														value="${activity.activityName}" length="14"
														needEllipsis="true"></ui:sout></a></td>
											<td title="${activity.subjectName}"><span
												style="display: block; width: 160px; padding-left: 10px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"><ui:sout
														value="${activity.subjectName}" length="8"
														needEllipsis="true"></ui:sout></span></td>
											<td title="${activity.gradeName}"><span
												style="display: block; width: 160px; padding-left: 10px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"><ui:sout
														value="${activity.gradeName}" length="19"
														needEllipsis="true"></ui:sout></span></td>
											<td>${activity.organizeUserName}</td>
											<td class="threeSpot"><fmt:formatDate value="${activity.startTime}"
													pattern="MM-dd HH:mm" /> <c:if
													test="${empty activity.startTime}"> ~ </c:if>至<c:if
													test="${empty activity.endTime}"> ~ </c:if> <fmt:formatDate
													value="${activity.endTime}" pattern="MM-dd HH:mm" /></td>
											<td>${activity.commentsNum}</td>
											<td>
												<c:choose>
													<c:when test="${activity.flags==0 }"> 
															<b style="color:#FF8400;cursor: pointer;" id="${activity.id }" class="img1" name="${activity.flago }${activity.activityName }">精选</b>
													</c:when>
													<c:otherwise>  
														<b style="color:#FF8400;cursor: pointer;"  class="img2">已精选</b>
													</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</table>
							</c:if>
							<c:if test="${empty  activityList.datalist&&empty name}">
								<div class="empty_wrap"> 
									<div class="empty_info">您还没有可精选的教研活动</div>
								</div>
							</c:if>
							<c:if test="${empty  activityList.datalist&&not empty name}">
								<div class="empty_wrap"> 
									<div class="empty_info">未找到可精选的教研活动！</div>
								</div>
							</c:if>
						</div>
						<div class="clear"></div>
					</div>
					<form name="pageForm" method="post">
						<ui:page url="${ctx}jy/record/findSysList?id=${id}&type=${type}"
							data="${activityList }" />
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden" name="id" value="${id}"> <input
							type="hidden" name="type" value="${type}"> <input
							type="hidden" name="planName" value="${word}">
					</form>
				</div>
			</div>
			
		</div>
		<div class="clear"></div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>
	<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.form.min','jp/jquery.validationEngine-zh_CN','jp/jquery.validationEngine.min','jx'],function(){});
</script>
</body>
</html>