<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="成长档案袋"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/record/css/record.css" media="screen">
	<ui:require module="../m/record/js"></ui:require>
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>教研活动
		<div class="more" onclick="more()"></div>
	</header>
	<section>	
		<div class="record_content">
		<form id="searchForm"  name="searchForm" method="post" action="${ctx}jy/record/findSysList">
		<input type="hidden" name="id" value="${id}"> 
		<input type="hidden" name="type" value="${type}"> 
			<div class="record_c_s">
				<div class="record_c_w">
					<div class="serch">
						<input type="text" class="search" name="planName" id="planName" value="${name }" placeholder="输入课题查找">
						<input type="button" class="search_btn">
					</div>
				</div>
			</div>	
		</form>
			<c:if test="${not empty  activityList.datalist}">
			<div class="record_cont_bottom_1" id="act_list">
			<div>
				<div class="record_cont_bottom_win">
					<c:forEach items="${activityList.datalist}" var="activity">
						<div class="activity_tch"> 
							<div <c:if test="${activity.typeId==1 }">class="activity_tch_left"</c:if><c:if test="${activity.typeId==2 }">class="activity_tch_left1"</c:if><c:if test="${activity.typeId==3 }">class="activity_tch_left2"</c:if>>
							 ${activity.typeName}
							</div> 
							<div class="activity_tch_right" activityId="${activity.id }" typeId="${activity.typeId}" isOver="${activity.isOver }">
								<h3><span class="title">${activity.activityName}</span></h3>
								<h4>
									<c:if test="${activity.flags==0 }">
										<strong status="jingxuan" id="${activity.id }" name="${activity.flago }${activity.activityName }">
											<span class="jx_span"><img src="${ctxStatic }/m/record/images/jx.png" alt=""></span>
					     					<q>精选</q>
					     				</strong>
									</c:if>
									<c:if test="${activity.flags!=0 }">
										<span class="jx_span"><img src="${ctxStatic }/m/record/images/yjx.png" alt=""></span>
					     				<q>已精选</q>
									</c:if>
					     		</h4>
								<div class="option">
									<div class="promoter"><strong></strong>发起人：<span>${activity.organizeUserName}</span></div>
									<div class="partake_sub"><strong></strong>参与学科：<span>${activity.subjectName}</span></div>
									<div class="partake_class"><strong></strong>参与年级：<span>${activity.gradeName}</span></div> 
								</div>
								<div class="option">
									<div class="time"><strong></strong><span><fmt:formatDate value="${activity.startTime}"
											pattern="MM-dd HH:mm" /> <c:if
											test="${empty activity.startTime}"> ~ </c:if>至<c:if
											test="${empty activity.endTime}"> ~ </c:if> <fmt:formatDate
											value="${activity.endTime}" pattern="MM-dd HH:mm" /></span></div>
									<div class="discussion_number"><strong></strong>讨论数：<span>${activity.commentsNum}</span></div>
								</div>
							</div> 
						</div>
					</c:forEach>
				</div> 
				<form id="pageForm"  name="pageForm" method="post" action="${ctx}jy/record/findSysList">
				<ui:page url="${ctx}jy/record/findSysList" data="${data }"  callback="addData" dataType="true"/>
				<input type="hidden" class="currentPage" name="page.currentPage"> 
				<input type="hidden" name="id" value="${id}"> 
				<input type="hidden" name="type" value="${type}"> 
				<div style="height:1rem;clear:both;"></div>
				</form>
				</div>
			</div>
			</c:if>
			<c:if test="${empty  activityList.datalist&&empty name}">
				<div class="content_k" style="margin-top: 5rem;">
					<dl>
						<dd></dd>
						<dt>您还没有可精选的教研活动</dt>
					</dl>
				</div>
			</c:if>
			<c:if test="${empty  activityList.datalist&&not empty name}">
				<div class="content_k" style="margin-top: 5rem;">
					<dl>
						<dd></dd>
						<dt>未找到可精选的教研活动！</dt>
					</dl>
				</div>
			</c:if>
		</div>
	</section>
	
</div>
<div class="edit_portfolio_wrap" style="display:none;">
	<div class="edit_portfolio" style="position: fixed;">
		<div class="edit_portfolio_title" >
			<h3 style="width:33rem;padding-left: 5rem;">微评</h3>
			<span class="close"></span>
		</div>
		<div class="edit_portfolio_content">
			<form>
			<ui:token></ui:token>
			<input type="hidden" id="one">
				<div class="form_input">
					<label style="width:5rem;">名称:</label>
					<p style="width:27rem;height:5rem">
						<input type="text" class="name_txt" style="width:27rem;border:none;" placeholder="请输入名称" value="" readonly="readonly">
					</p> 
				</div>
				<div class="form_input">
					<label style="width:5rem;">微评:</label>
					<p style="width:27rem;height:7.5rem;">
						<textarea cols="100" rows="3"style="width:27rem;height:7rem;" class="desc" maxlength="50" name="desc" id="desc"></textarea>
						<a class="note">注：最多可输入50个字</a>
					</p> 
				</div>
				<div class="border_bottom" style="margin: 3rem auto;"></div>
				<div class="portfolio_btn">
					<input type="button" class="btn_confirm" value="保存">
					<input type="button" class="btn_cencel" value="取消">
				</div>
			</form>
		</div>
	</div>
</div>
<div class="mask"></div>
</body>
<script type="text/javascript">
	require(["zepto",'jxactlist'],function($){	
	});
</script>
</html>