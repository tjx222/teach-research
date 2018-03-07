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
//整理
function zhengli(activityId,typeId,startDateStr){
	if(ifActivityStart('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>',startDateStr)){
		if(typeId==1){//同备教案
			window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_blank");
		}else if(typeId==2){//主题研讨
			window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
		}else if(typeId==3){//视频教研
			window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
		}
	}
}
</script>
</head>
<body>
<div id="draft_box" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">草稿箱</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content" style="overflow: hidden;"> 
			<iframe id="activity_iframe" src="" width="600" height="420" style="overflow: hidden;" frameborder="0" scrolling="no"  ></iframe>
		</div>
	</div>
</div>
<div id="check_box" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">查阅意见</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content" style="overflow: hidden;"> 
			<iframe id="yue_iframe" src="" width="945" height="520" style="overflow: hidden;" frameborder="0" scrolling="no"  ></iframe>
		</div>
	</div>
</div>
<div class="wrapper"> 
	<div class='jyyl_top'>
	<ui:tchTop style="1" modelName="集体备课"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：<jy:nav id="jtbk"></jy:nav>
	</div>
	<form id="activityForm" action="${ctx}jy/activity/index" method="post">
		<input type="hidden" id="listType" name="listType" value="${listType}"> 
	</form>
	<div class='teaching_research_list_cont'>
		<div class='t_r_l_c'>
			<div class='t_r_l_c_cont_tab'>  
				<div class="t_r_l_c_select"> 
					<ol>
	                	<li class="t_r_l_c_li <c:if test='${listType!=1}'> t_r_l_c_li_act</c:if>" value="0">发起与管理</li>
						<li class="t_r_l_c_li <c:if test='${listType==1}'> t_r_l_c_li_act</c:if>" value="1">参与及查看</li>
	                </ol>
	                <c:if test="${listType!=1}">
	                <div class="t_r_l_c_right">
	                	<div class='fq_teaching_btn' onclick="toActivityEdit();"></div>
						<div class='drafts'  count="${activityDraftNum }">
							<span class='drafts_img'></span>
							<a>草稿箱<c:if test="${activityDraftNum > 0}"><span></span></c:if></a>
						</div>
					</div>
					</c:if>
                </div> 
				<div class='t_r_l_c_cont'> 
				<c:if test="${listType!=1}">
				<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
					<div class='t_r_l_c_cont_tab'>
						<div class='t_r_l_c_cont_table'>
							<table> 
								<tr>
									<th style="width:240px;">活动主题</th>
									<th style="width:120px;">参与学科</th>
									<th style="width:100px;">参与年级</th>
									<th style="width:180px;">活动时限</th>
									<th style="width:50px;">讨论数</th>
									<th style="width:50px;">修改</th>
									<th style="width:50px;">删除</th>
									<th style="width:50px;">提交</th>
									<th style="width:50px;">分享</th>
								</tr>
								<c:forEach items="${activityList.datalist}" var="activity">
								<tr>
									<td style="text-align:left;" title="${activity.activityName}"><a class="tdName">【${activity.typeName}】</a><a onclick="zhengli(${activity.id},${activity.typeId},'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')" class='td_name td_name_d' style="width:128px;">${activity.activityName}</a>
									<c:if test="${activity.isAudit && !activity.auditUp}">
										<span class="span_yue" activityId="${activity.id }"></span>
									</c:if>
									<c:if test="${activity.isAudit && activity.auditUp}">
										<span class="span_yue" activityId="${activity.id }"><b class="spot2"></b></span>
									</c:if>
									</td>
									<td title="${activity.subjectName}"><span class='ellipsis'>${activity.subjectName}</span></td>
									<td title="${activity.gradeName}"><span class='ellipsis1'>${activity.gradeName}</span></td>
									<td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
									<td>${activity.flago}</td>
									<td>
									<c:if test="${!activity.isSubmit && !activity.isOver}">
										<span title='编辑' class='edit_btn' onclick="editActivity('${activity.id}');"></span>
									</c:if>
									<c:if test="${activity.isSubmit}">
										<span title="活动已提交，禁止修改" class='jz_edit_btn'></span>
									</c:if>
									<c:if test="${activity.isOver && !activity.isSubmit}">
										<span title="活动已结束，禁止修改" class='jz_edit_btn'></span>
									</c:if>
									</td>
									<td>
									<c:if test="${activity.commentsNum>0 || activity.isSubmit}">
										<span title="已提交或已有讨论内容啦，不能删除哦" class='jz_del_btn'></span>
									</c:if>
									<c:if test="${(empty activity.commentsNum || activity.commentsNum==0) && !activity.isSubmit}">
										<span title="删除" class='del_btn' onclick="delActivity('${activity.id}');"></span>
									</c:if>
									</td>
									<td>
									<c:if test="${!activity.isOver}">
										<span title="该活动正在进行，如需提交,请您先在活动页面“结束活动”" class='jz_submit1_btn'></span>
									</c:if>
									<c:if test="${activity.isOver && !activity.isSubmit}">
										<span title="提交" onclick="tijiao_quxiao('${activity.id}',this);" class='submit1_btn'></span>
									</c:if>
									<c:if test="${activity.isSubmit && activity.isAudit}">
										<span title="已经有管理者查阅啦，您不能取消哦" class='jz_submit1_btn'></span>
									</c:if>
									<c:if test="${activity.isSubmit && !activity.isAudit}">
										<span title="取消提交" onclick="tijiao_quxiao('${activity.id}',this);" class='qx_submit1_btn'></span>
									</c:if>
									</td>
									<td>
									<c:if test="${!activity.isShare}">
										<span title='分享' class='share_btn' onclick="fenxiang_quxiao('${activity.id}',this);"></span>
									</c:if>
									<c:if test="${activity.isShare && !activity.isComment}">
										<span title='取消分享' class='qx_share_btn' onclick="fenxiang_quxiao('${activity.id}',this);"></span>
									</c:if>
									<c:if test="${activity.isShare && activity.isComment}">
										<span title="已被评论，您不能取消哦" class='jz_share_btn'></span>
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
							<div class="empty_info">您现在还没有发起集体备课哟，赶紧点击右上角的“发起集体备课”吧！</div>
						</div>
					</c:if>
					</c:if>
					<c:if test="${listType==1}">
					<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
					<div class='t_r_l_c_cont_tab'>
						<div class='t_r_l_c_cont_table' >
							<table> 
								<tr>
									<th style="width:300px;">活动主题</th>
									<th style="width:120px;">参与学科</th>
									<th style="width:90px;">参与年级</th>
									<th style="width:90px;">发起人</th>
									<th style="width:180px;">活动时限</th>
									<th style="width:60px;">讨论数</th> 
									<th style="width:60px;">操作</th> 
								</tr>
								<c:forEach items="${activityList.datalist}" var="activity">
									<tr>
										<td style="text-align:left;"><a class="tdName">【${activity.typeName}】</a><a class='td_name1 td_name_d' style="width:190px;" title="${activity.activityName}" onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver },'<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')">${activity.activityName}</a></td>
										<td title="${activity.subjectName}"><span class='ellipsis'>${activity.subjectName}</span></td>
										<td title="${activity.gradeName}"><span class='ellipsis1'>${activity.gradeName}</span></td>
										<td>${activity.organizeUserName}</td>
										<td class="tdName" style="width:180px;"><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
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
					</c:if>
					<form  name="pageForm" method="post">
						<ui:page url="${ctx}jy/activity/index" data="${activityList}" />
						<input type="hidden" class="currentPage" name="currentPage">
						<input type="hidden" id="" name="listType" value="${listType}">
					</form>
				</div> 
			</div>
		</div>
	</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div> 
</body>
</html>