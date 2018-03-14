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
<input type="hidden" id="bagPage" value="${page}" />
<div class="edit_portfolio_wrap">
	<div class="edit_portfolio">
		<div class="edit_portfolio_title" >
			<h3 style="width:33rem;padding-left: 5rem;">微评</h3>
			<span class="close"></span>
			<span class="portfolio_edit"></span>
		</div>
		<div class="edit_portfolio_content">
			<form>
				<ui:token></ui:token>
				<div class="form_input">
					<label style="width:5rem;">名称:</label>
					<p style="width:27rem;height:5rem">
						<input type="text" class="name_txt" style="width:27rem;border:none;"  value="" readonly="readonly">
					</p> 
				</div>
				<div class="form_input">
					<label style="width:5rem;">微评:</label>
					<p style="width:27rem;height:7.5rem;">
						<textarea name="desc" id="desc" cols="100" rows="3"style="width:27rem;height:7rem;" class="desc"　maxlength="50"></textarea>
						<a class="note" style="display:none;">注：最多可输入50个字</a>
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
<div class="del_upload_wrap">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该教案吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
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
		<div class="record_content" id="c_b_w_list">
			<div id="scroller">
				<div class="content_bottom_width">
					<div class="add_cour_wrap">
						<div class="add_cour_wrap_div">
							<div class="add_cour_wrap_div_top">
								<div class="add_cour_wrap_div_top_img"></div> 
							</div>
							<div class="add_cour_wrap_div_bottom">精选</div>
						</div>
					</div>
					<c:forEach  items="${data.datalist  }" var="record">
						<div class="record_word">
							<div class="record_word_1">${name }</div>
							<h3>${record.flago }${record.recordName }</h3>
							<p resId="${record.resId }"><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="record_word_2" title="删除" id="${record.recordId }" name="${record.flago }${record.recordName }"></div> 
							<c:if test="${record.desc!=null&&record.desc!='' }">
								<div class="record_word_3" resId="${record.recordId }"  resName="${record.flago }${record.recordName }" title="${record.desc }"></div> 
							</c:if>
							<c:if test="${record.childType==0 }">
							<div class="xiaonei"></div>
							</c:if>
							<c:if test="${record.childType==1 }">
							<div class="xiaowai"></div>
							</c:if>
						</div>
					</c:forEach>
				</div>
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/record/sysList" data="${data }" dataType="true" callback="addData"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" name="id" value="${id}" />
					<input type="hidden" name="type" value="${type}" />
				</form> 
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'sysLeclist'],function($){	
	});
</script>
</html>