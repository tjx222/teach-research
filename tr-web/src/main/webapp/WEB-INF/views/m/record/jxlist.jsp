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

<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>${name1 }
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<form id="searchForm"  name="searchForm" method="post" action="${ctx}jy/record/findSysList">
		<input type="hidden" name="id" value="${id}"> 
		<input type="hidden" name="type" value="${type}"> 
		<div class="record_content_1">
			<div class="record_content_1_ser">
				<div class="serch">
					<input type="text" class="search" name="planName" id="planName" value="${name}" placeholder="输入${(name1=='自制课件'||name1=='教学反思')?'课题':(name1=='教学文章'||name1=='计划总结'?'标题':'教案')}进行查找">
					<input type="button" class="search_btn">
				</div>
			</div>
		</div>
		</form> 
		<c:if test="${not empty data.datalist }">
		<div class="record_content_table">
		<table> 
			<tr> 
				<th style="width:2%;"></th>
				<c:choose>
					<c:when test="${name1=='自制课件'||name1=='教学反思'}">
						<th style="width:60%;text-align:left;padding-left: 1%;">课题</th>
					</c:when>
					<c:when test="${name1=='教学文章'||name1=='计划总结'}">
						<th style="width:60%;text-align:left;padding-left: 1%;">标题</th>
					</c:when>
					<c:otherwise>
						<th style="width:60%;text-align:left;padding-left: 1%;">教案</th>
					</c:otherwise>
				</c:choose>
			 	<th style="width:20%;">时间</th>
			    <th style="width:16%;">操作</th>
			    <th style="width:2%;"></th>
			</tr>
		</table>
		</div>
		<div class="record_content_2" id="record_cont_2_list">
			<div id="scroller">
				<table id="listTable">
				  <c:forEach items="${data.datalist  }" var="record">
				  	  <tr>
					  	 <td><%-- <img src="${ctxStatic }/m/record/images/word.png" alt=""> --%></td>
					     <td style="text-align:left;">${record.flago }<span status="name" resId="${record.path }"> ${record.recordName}</span></td>
					     <td>${record.time }</td> 
					     <td>
					     	<c:if test="${record.status==0 }">
						     	<strong status="jingxuan"  name="${record.flago }${record.recordName }" id="${record.resId }">
						     		<span class="jx_span"><img src="${ctxStatic }/m/record/images/jx.png" alt=""></span>
					     			<q>精选</q>
				     			</strong>
					     	</c:if>
					     	<c:if test="${record.status!=0 }">
					     		<span class="jx_span"><img src="${ctxStatic }/m/record/images/yjx.png" alt=""></span>
					     		<q>已精选</q>
					     	</c:if>
					     </td>
					     <td></td>
					  </tr>
				  </c:forEach>  
				  
				</table>
				<form id="pageForm"  name="pageForm" method="post" action="${ctx}jy/record/findSysList">
					<input type="hidden" name="id" value="${id}"> 
					<input type="hidden" name="type" value="${type}"> 
					<ui:page url="${ctx}jy/record/findSysList" data="${data }"  callback="addData" dataType="true"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
			    </form>
			</div>
			
		</div>
		</c:if>
			<c:if test="${empty data.datalist&&!empty name }">
				<div class="content_k" style="margin-top: 5rem;">
					<dl>
						<dd></dd>
						<c:choose>
							<c:when test="${name1=='自制课件'}">
								<dt>您还没有可精选的课件，赶紧去“<a href="./jy/courseware/index" target="_blank"  style="color:#014efd;">上传课件</a>”吧！</dt>
							</c:when>
							<c:when test="${name1=='教学反思'}">
								<dt>您还没有可精选的反思，赶紧去“<a href="./jy/rethink/index" target="_blank"  style="color:#014efd;">撰写反思</a>”吧！</dt>
							</c:when>
							<c:when test="${name1=='教学文章'}">
								<dt>您还没有可精选的教学文章，赶紧去“<a href="./jy/thesis/index" target="_blank"  style="color:#014efd;">上传教学文章</a>”吧！</dt>
							</c:when>
							<c:when test="${name1=='计划总结'}">
								<dt>您还没有可精选的计划总结，赶紧去“<a href="./jy/planSummary/index" target="_blank"  style="color:#014efd;">上传计划总结</a>”吧！</dt>
							</c:when>
							<c:otherwise>
								<dt>您还没有可精选的教案，赶紧去“<a href="./jy/toWriteLessonPlan" target="_blank"  style="color:#014efd;">撰写教案</a>”吧！</dt>
							</c:otherwise>
						</c:choose>
					</dl>
				</div>
			</c:if>
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
	require(["zepto",'jxlist'],function($){	
	});
</script>
</html>