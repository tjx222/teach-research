<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="集体备课"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<ui:require module="../m/activity/js"></ui:require>
</head>
<body>
<div class="act_draft_wrap">
	<div class="act_draft">
		<div class="act_draft_title">
			<h3>草稿箱</h3>
			<span class="close"></span>
		</div>
		<iframe id="activity_iframe" src="" frameborder="0" scrolling="no" width="100%" height="100%"></iframe>
	</div>
</div>
<div class="fq_option_wrap">
	<div class="fq_option">
		<div class="fq_option_tb"></div>
		<div class="fq_option_zt"></div>
		<div class="fq_option_sp"></div>
	</div>
</div>
<div class="mask" ></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_});"></span>
		<ul>
			<li><a href="${tcx }jy/activity/index?listType=0&_HS_=${empty param._HS_ ? 2 : param._HS_+1}" <c:if test="${listType!=1}"> class="header_act" </c:if>>发起管理</a></li>
			<li><a href="${tcx }jy/activity/index?listType=1&_HS_=${empty param._HS_ ? 2 : param._HS_+1}" <c:if test="${listType==1}"> class="header_act" </c:if>>参与查看</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
<c:if test="${listType!=1}">
	<section>
		<div class="content">
			<div class="content_top">
				<div class="content_top_left">
					<input type="button" class="btn_launch" value="发起集备">
					<div class="draft">
					草稿箱(${activityDraftNum})
					</div>
					<input type="hidden" id="draftNumId" value="${activityDraftNum}" />
				</div>
				 <c:if test="${fn:length(phases) > 1 }">
				<div class="content_top_right">
				 <label>学段：</label>
                <div>
                <select name="phaseId" id="phaseId" style="width: 10rem; border: none;  line-height: 3rem;  height: 3rem; font-size: 1.267rem; color: #999; background: #f7f8f9;">
                 <c:forEach items="${phases }" var="phase">
                   <option value="${phase.id }" ${phase.id == phaseId ? 'selected':'' }>${phase.name }</option>
                 </c:forEach>
                </select>
                </div>
				</div>
				</c:if>
			</div>
			<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
			<div class="content_bottom" id="wrap">
				<div id="scroller">
				<div id="listdiv_0">
					<c:forEach items="${activityList.datalist}" var="activity">
						<div class="activity_tch">
							<c:if test="${activity.typeId==1 }">
							<div class="activity_tch_left">
							同<br />备<br />教<br />案
							</div>
							</c:if>
							<c:if test="${activity.typeId==2 }">
							<div class="activity_tch_left1">
							主<br />题<br />研<br />讨
							</div>
							</c:if>
							<c:if test="${activity.typeId==3 }">
							<div class="activity_tch_left2">
							视<br />频<br />研<br />讨
							</div>
							</c:if>
							<div class="activity_gl_right" activityId="${activity.id }" typeId="${activity.typeId }" isOver="${activity.isOver }" startDate="${activity.startTime}">
								<h3><span class="title">${activity.activityName}</span><c:if test="${activity.isOver }"><span class="end"></span></c:if></h3>
								<div class="option">
									<div class="partake_sub"><strong></strong>参与学科：<span>${activity.subjectName}</span></div>
									<div class="partake_class"><strong></strong>参与年级：<span>${activity.gradeName}</span></div> 
								</div>
								<div class="option">
									<div class="time"><strong></strong><span><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></span></div>
									<div class="discussion_number"><strong></strong>讨论数：<span>${activity.commentsNum}</span></div>
								</div>
							</div> 
							<div class="activity_gl_btn">
								<div class="gl_btn">管理</div>
								<div class="activity_option" activityId="${activity.id }" typeId="${activity.typeId }">
									<div <c:if test="${!activity.isSubmit && !activity.isOver}"> class="activity_option_edit"</c:if> <c:if test="${activity.isSubmit || activity.isOver}">class="activity_option_jz_edit" </c:if> ></div>
									<div <c:if test="${(empty activity.commentsNum || activity.commentsNum==0) && !activity.isSubmit}"> class="activity_option_del"</c:if> <c:if test="${activity.commentsNum>0 || activity.isSubmit}">class="activity_option_jz_del" </c:if> ></div>
									<div <c:if test="${!activity.isOver}">class="cw_option_jz_sbmt" </c:if> <c:if test="${activity.isOver && !activity.isSubmit }">class="activity_option_submit"</c:if><c:if test="${activity.isSubmit && !activity.isAudit}">class="cw_option_qx_sub"</c:if><c:if test="${activity.isSubmit && activity.isAudit}">class="act_option_jz_submit"</c:if>></div>
									<div <c:if test="${!activity.isShare }">class="activity_option_share"</c:if><c:if test="${activity.isShare && !activity.isComment}">class="activity_option_qx_share1"</c:if><c:if test="${activity.isShare && activity.isComment}">class="activity_option_jz_share1"</c:if>></div>
									<div class="activity_option_close"></div>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
					<form  name="pageForm" method="post">
						<ui:page url="${ctx}jy/activity/index?listType=0" data="${activityList}"  callback="addData" dataType="true"/>
						<input type="hidden" class="currentPage" name="currentPage">
					</form> 
					<div style="height:2rem;"></div>
				</div>
			</div>
			</c:if>
			<c:if test="${empty activityList.datalist || fn:length(activityList.datalist)<=0}">
			<div class="content_bottom">
				<div class="content_k" style="margin:6rem auto;">
					<dl>
						<dd></dd>
						<dt>您现在还没有发起集体备课哟，赶紧点击左上角的“发起集体备课”吧！</dt>
					</dl>
				</div>
			</div>
			</c:if>
		</div>
	</section>
	</c:if>
<c:if test="${listType==1}">
<c:if test="${!empty activityList.datalist && fn:length(activityList.datalist)>0}">
	<section>
		<div class="content">
			<div class="content_bottom1" id="wrap">
				<div id="scroller">
				<div id="listdiv_1">
					<c:forEach items="${activityList.datalist}" var="activity">
						<div class="activity_tch" activityId="${activity.id }" typeId="${activity.typeId }" isOver="${activity.isOver }" startDate="${activity.startTime}">
							<c:if test="${activity.typeId==1 }">
							<div class="activity_tch_left">
							同<br />备<br />教<br />案
							</div>
							</c:if>
							<c:if test="${activity.typeId==2 }">
							<div class="activity_tch_left1">
							主<br />题<br />研<br />讨
							</div>
							</c:if>
							<c:if test="${activity.typeId==3 }">
							<div class="activity_tch_left2">
							视<br />频<br />研<br />讨
							</div>
							</c:if>
							<div class="activity_ck_right">
								<h3><span class="title">${activity.activityName}</span><c:if test="${activity.isOver }"><span class="end"></span></c:if></h3>
								<div class="option">
									<div class="promoter"><strong></strong>发起人：<span>${activity.organizeUserName}</span></div>
									<div class="partake_sub"><strong></strong>参与学科：<span>${activity.subjectName}</span></div>
									<div class="partake_class"><strong></strong>参与年级：<span>${activity.gradeName}</span></div> 
								</div>
								<div class="option">
									<div class="time"><strong></strong><span><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></span></div>
									<div class="discussion_number"><strong></strong>讨论数：<span>${activity.commentsNum}</span></div>
								</div>
							</div> 
						</div>
					</c:forEach>
					</div>
					<form  name="pageForm" method="post">
						<ui:page url="${ctx}jy/activity/index?listType=1" data="${activityList}"  callback="addData1"/>
						<input type="hidden" class="currentPage" name="currentPage">
					</form> 
					<div style="height:2rem;"></div>
				</div>
			</div>
		</div>
	</section>
</c:if>
<c:if test="${empty activityList.datalist || fn:length(activityList.datalist)<=0}">
<div class="content_bottom" style="top: 5.1rem;">
	<div class="content_k" style="margin:10rem auto;">
		<dl>
			<dd></dd>
			<dt>您现在还没有可参与的集体备课，稍后再来吧！</dt>
		</dl>
	</div>
</div>
</c:if>
</c:if>
</div>
</body>
<script type="text/javascript">
	require(['leader'],function(){	
	}); 
</script>
</html>