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
<input type="hidden" id="bagId" value="${id}" />
<input type="hidden" id="bagType" value="${type}" />
<div class="del_upload_wrap">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除档案袋</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width" style="width:20rem;">
				<q></q>
				<span>您确定要删除该教研活动吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定" id="del_confirm">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="edit_portfolio_wrap" style="display:none;">
	<div class="edit_portfolio" style="position: fixed;">
		<div class="edit_portfolio_title" >
			<h3 style="width:33rem;padding-left: 5rem;">微评</h3>
			<span class="close"></span> 
			<span class="portfolio_edit"></span>
		</div>
		<div class="edit_portfolio_content">
			<form>
			<ui:token></ui:token>
			<input type="hidden" id="one">
				<div class="form_input">
					<label style="width:5rem;">名称:</label>
					<p style="width:27rem;height:5rem">
						<input type="text" id="name" class="name_txt" style="width:27rem;border:none;" >
					</p> 
				</div>
				<div class="form_input">
					<label style="width:5rem;">微评:</label>
					<p style="width:27rem;height:7.5rem;">
						<textarea cols="100" rows="3"style="width:27rem;height:7rem;" maxlength="50" name="desc" class="desc" id="desc"></textarea>
						<a class="note">注：最多可输入50个字</a>
					</p> 
				</div>
				<div class="border_bottom" style="margin: 3rem auto;display:none;"></div>
				<div class="portfolio_btn" style="display:none;">
					<input type="button" class="btn_confirm" value="修改">
					<input type="button" class="btn_cencel" value="取消">
				</div>
			</form>
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span style="display:inline-block" onclick="javascript:window.history.go(-1);"></span>${name }
		<div class="more" onclick="more()"></div>
	</header>
	<section>	
		<div class="record_content">
			<div class="record_c_t">
				<input id="jingxuan" type="button" value="精 选">
			</div>	
			<div class="record_cont_bottom" id="act_list_yjx">
				<div id="scroller">
				<div class="record_cont_bottom_win">
				<c:forEach  items="${data.datalist  }" var="activity">
					<div class="activity_tch">
						<div <c:if test="${activity.ext.typeId==1 }">class="activity_tch_left"</c:if><c:if test="${activity.ext.typeId==2 }">class="activity_tch_left1"</c:if>>
						${activity.ext.typeName}
						</div> 
						<div class="activity_tch_right" activityId="${activity.resId}" typeId="${activity.flags}">
							<h3><span class="title">${activity.recordName}</span></h3>
							<h4><span class="del" id="${activity.recordId }"></span><c:if test="${activity.desc !=''}"><span class="wei" resId="${activity.recordId }"  resName="${activity.flago }${activity.recordName }"   id="sp" title="${activity.desc }"></span></c:if></h4>
							<div class="option">
								<div class="promoter"><strong></strong>发起人：<span>${activity.ext.mainUserName}</span></div>
								<div class="partake_sub"><strong></strong>参与学科：<span>${activity.ext.subjectName}</span></div>
								<div class="partake_class"><strong></strong>参与年级：<span>${activity.ext.gradeName}</span></div> 
							</div>
							<div class="option">
								<div class="time"><strong></strong><span>${activity.ext.startTime}<c:if test="${empty activity.ext.startTime}"> ~ </c:if>至<c:if test="${empty activity.ext.endTime}"> ~ </c:if>${activity.ext.endTime}</span></div>
								<div class="discussion_number"><strong></strong>讨论数：<span>${activity.ext.commentsNum}</span></div>
							</div>
						</div> 
					</div>
				</c:forEach>
				</div>
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/record/sysList" data="${data }" dataType="true" callback="addData"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" name="id" value="${id}" />
					<input type="hidden" name="type" value="${type}" />
				</form>  
				<div style="height:1rem;clear:both;"></div>
				</div>
				<c:if test="${data.datalist =='[]' }">
					<div class="content_k" style="margin-top: 5rem;">
						<dl>
							<dd></dd>
							<dt>您还没有精选“教学设计”哟，赶紧去“精选”吧！</dt>
						</dl>
					</div>
				</c:if>
			</div> 
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'actlist'],function($){	
	});
</script>
</html>