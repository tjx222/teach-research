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
	<style>
		#progressDiv {
	      width: 45rem;
		  height: 3rem;
		}
		#process {
		  width: 45rem;
		  height: 0.1rem;
		  margin-left: -12rem;
		  margin-top: -11.2rem;/* 
		  background-color: #fe7e37; */
		  border:0;
		}
		#process span{
		   background-color: #fe7e37; 
		   display: block;
		  height: 0.1rem;
		}
		#processPercent_1{
		display:none;
		}
	</style>
</head>
<body>
<input type="hidden" id="bagId" value="${id}" />
<input type="hidden" id="bagType" value="${type}" />
<div class="edit1_portfolio_wrap">
	<div class="edit1_portfolio">
		<div class="edit1_portfolio_title" >
			<h3 style="width:33rem;padding-left: 5rem;">添加文件</h3>
			<span class="close"></span>
		</div>
		<div class="edit1_portfolio_content">
			<form id="kj_form" action="${ctx}jy/record/save" method="post">
				<ui:token></ui:token>
				<input type="hidden" name="id" value="${id }">
				<input type="hidden" name="recordId"  id="recordId">
				<input type="hidden" id="resId" name="resId"/>
				<input type="hidden" name="page">
				<div class="form_input">
					<label >名称</label>
					<p style="width:27rem;height:5rem">
						<input type="text" id="name" name="name" class="name_txt" style="width:27rem;margin-left:0;"  placeholder="请输入名称" maxlength="10">
					</p> 
				</div>
				<div id="fileuploadContainer" class="form_input">
					<label style="background-color: #fff; position: absolute;z-index: 1;">上传附件</label>
					<div class="edit_strong" style="display: none;"><a>24223424</a><span></span></div>
					<strong id="uploadId" style="margin-left:8rem;"> 
						<ui:upload_m fileType="doc,docx,ppt,pptx,pdf,jpg,jpeg,gif,png,mp3,mp4,flv,swf" progressBar="true" fileSize="50" startElementId="save" callback="afterUpload" beforeupload="uploading"></ui:upload_m>
					</strong>
				</div>
				<div class="form_input" style="height:5rem;">
					<label >微评</label>
					<p style="width:27rem;height:7.5rem;">
						<textarea name="desc" id="kjms" class="desc" cols="100" rows="3"style="width:27rem;height:7rem;" placeholder="请输入微评" maxlength="50"></textarea>
						<a class="note" style="display:none;">注：最多可输入50个字</a>
					</p> 
				</div>
				<div class="border_bottom" style="margin: 3rem auto 1rem auto;width:36rem;"></div>
				<div class="portfolio_btn">
					<input type="button" class="btn_preserve" value="保存" style="margin:2rem auto;display:none;" id="save">
					
					<div class="btn_sc" style="display: none;">
						<div class="spinner ">
						  <div class="rect1"></div>
						  <div class="rect2"></div>
						  <div class="rect3"></div>
						  <div class="rect4"></div>
						  <div class="rect5"></div>
						</div>
						<span>上传中...</span>
					</div>
					<input type="button" class="btn_preserve" value="保存" style="margin:2rem auto;display: none;" id="save1">
				</div>
			</form>
		</div>
	</div>
</div>
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
						<textarea name="desc" id="desc" cols="100" rows="3"style="width:27rem;height:7rem;" maxlength="50"></textarea>
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
				<span>您确定要删除该文件吗？</span>
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
							<div class="add_cour_wrap_div_bottom">添加文件</div>
						</div>
					</div>
					<c:forEach  items="${data.datalist  }" var="record">
						<div class="record_word">
							<div class="record_word_1">文件</div>
							<h3>${record.flago }${record.recordName }</h3>
							<p resId="${record.path }"><ui:icon ext="${record.flags }" /></p>
							<div class="record_word_21"></div> 
							<c:if test="${record.desc!=null&&record.desc!='' }">
								<div class="record_word_3" resId="${record.recordId }"  resName="${record.flago }${record.recordName }" title="${record.desc }"></div> 
							</c:if>
							<div class="record_option" style="display:none;">
								<c:choose>
									<c:when test="${record.status==0}">
										<div title="禁止修改" class="jz_record_edit"></div>
										<div title="禁止删除" class="jz_record_del"></div>
									</c:when>
									<c:otherwise>
										<div class="record_edit" recordId="${record.recordId}" bagId="${record.bagId }" resId="${record.path }" recordName="${record.recordName }" desc="${record.desc }"></div>
										<div class="record_del" recordId="${record.recordId}" bagId="${record.bagId }"></div>
									</c:otherwise>
								</c:choose>
								<a title="下载" href="<ui:download resid='${record.path}' filename='${record.recordName }'></ui:download>"><div class="record_down"></div></a>
								<div class="record_close" ></div>
							</div>
						</div>
					</c:forEach>
				</div>
				<form  name="pageForm" method="post">
					<ui:page url="${ctx}jy/record/findRList" data="${data }"  callback="addData" dataType="true"/>
					<input type="hidden" class="currentPage" name="page.currentPage">
					<input type="hidden" name="id" value="${id}" />
				</form> 
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'self'],function($){	
	});
</script>
</html>