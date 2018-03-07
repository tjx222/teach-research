<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="教学文章"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/thesis/css/thesis.css" media="screen">
	<ui:require module="../m/thesis/js"></ui:require>
</head>
<body>
<!-- 评论意见 -->
<div class="opinions_comment_wrap" id="checkComment">
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>评论意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_comment" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
<div class="share_thesis_wrap">
	<div class="share_thesis">
		<div class="share_thesis_title">
			<h3>分享教学文章</h3>
			<input type="hidden" id="shareId"/>
			<input type="hidden" id="shareResId"/>
			<span class="close"></span>
		</div>
		<div class="share_thesis_content">
			<div class="share_width">
				<q></q>
				<span id="isOrNotShare">您确定要分享该教学文章吗？分享后，您的小伙伴就可以看到喽！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" id="shareThesis" class="btn_confirm" value="确定">
				<input type="button" id="qxshareThesis" class="btn_confirm" style="display:none;" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="del_thesis_wrap">
	<div class="del_thesis">
		<div class="del_thesis_title">
			<h3>删除教学文章</h3>
			<span class="close"></span>
		</div>
		<div class="del_thesis_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该教学文章吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input id="deleteThesis" type="button" class="btn_confirm" value="确定" >
				<input id="cancelDelete" type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="add_thesis_wrap"> 
	<div class="add_thesis_wrap1"></div>
	<div class="add_thesis">
		<div class="add_thesis_title">
			<h3 id="addorUpdate">上传教学文章</h3>
			<span class="close"></span>
		</div>
		<div class="add_thesis_content">
			<form action="jy/thesis/save" id="th_form" method="post">
			    <ui:token />
			    <input type="hidden" id="deleteId"/>
			    <input type="hidden" id="deleteResId"/>
			    <input type="hidden" name="resId" id="resId" value="">
			    <input type="hidden" name="originFileName" id="originFileName" value="">
				<div class="form_input">
					<label>标题</label>
					<input type="hidden" id="editId" name="id"/>
					<input type="text" id="thesisTitle" name="thesisTitle" placeholder="请输入标题" class="txt_title">
				</div>
				<div class="form_input">
					<label>学科</label>
					<c:if test="${_CURRENT_SPACE_.subjectId !=0}">
					<input type="hidden" id="subjectId" name="subjectId" value="${_CURRENT_SPACE_.subjectId}">
					<strong class="subject" id="selectSubject"><jy:dic key="${_CURRENT_SPACE_.subjectId}"/><q></q></strong></c:if>
					<c:if test="${_CURRENT_SPACE_.subjectId==0}">
					    <c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')
								||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'FXZ')
								||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
					    <input type="hidden" id="subjectId" name="subjectId" value="1383">
					    <strong class="subject" id="selectSubject">其他<q></q></strong></c:if>
					    <c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')}">
					    <input type="hidden" id="subjectId" name="subjectId" value="100">
					    <strong class="subject" id="selectSubject">语文<q></q></strong></c:if>
					</c:if>
					<div class="menu_list" >
						<span class="menu_list_top"></span>
						<div id="wrap1" class="menu_list_wrap1"> 
							<div id="scroller">
							<ui:relation var="list" type="xdToXk"
										id="${_CURRENT_SPACE_.phaseId}">
										<c:forEach var="xk" items="${list}">
											<p  id="${xk.id}">${xk.name}</p>
										</c:forEach>
							</ui:relation>
							<p id="1383">其他</p>
							</div>
						</div>
					</div> 
				</div>
				<div id="xueke_to"></div>
				<div class="form_input">
					<label>分类</label>
					<input type="hidden" id="thesisType" name="thesisType" value="教学论文">
					<strong class="classifying" id="selectType">教学论文<q></q></strong>
					<div class="menu_list1" >
						<span class="menu_list_top"></span>
						<div id="wrap2" class="menu_list_wrap1">
						<!-- <input type="hidden"  name="thesisType" value="">  -->
							<div id="scroller">
								<p id="教学论文" >教学论文</p>
								<p id="课题研究" >课题研究</p>
								<p id="教学随笔" >教学随笔</p>
								<p id="教育叙事" >教育叙事</p>
								<p id="读书笔记" >读书笔记</p>
								<p id="生活感悟" >生活感悟</p>
								<p id="其他" >其他</p> 
							</div>
						</div>
					</div>
				</div>
				<div id="fenlei_to"></div>
				<div class="form_input"  id="fileuploadContainer">
					<label style="background-color: #fff;position: absolute; z-index: 1;">附件</label>
					<div class="enclosure_name" style="display:none;">
					     <q></q>
					     <span id="uploadFileName">最大长度为十五个字.doc</span>
				         <div class="enclosure_del"></div>
				    </div>
					<strong style="margin-left:8rem;">
					<ui:upload_m fileType="doc,docx,ppt,pptx,pdf" fileSize="50"  relativePath="thesis/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }"
								startElementId="save" callback="backSave" beforeupload="start" ></ui:upload_m></strong> 
				</div>
				<div id="scfj_to"></div>
				<div id="uploadSave" style="margin-top:3rem;">
					<input id="save" type="button" class="btn_thesis" value="上传">
					<input id="cancel" type="button" class="btn_cencel"value="取消">
				</div>
				<div class="btn_sc" style="margin:3rem auto;display: none;" >
					<div class="spinner ">
					  <div class="rect1"></div>
					  <div class="rect2"></div>
					  <div class="rect3"></div>
					  <div class="rect4"></div>
					  <div class="rect5"></div>
					</div>
					<span>上传中...</span>
				</div>
			</form>	
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>教学文章
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
		    <form id="search_form" action="jy/thesis/index" method="post">
				<div class="content_top">
					<div class="content_top_left">
						
					</div>
					<div class="content_top_right">
						<input type="search" id="search" name="thesisTitle" class="btn_search" placeholder="按照文章名称进行搜索" value="${thesis.thesisTitle}"/>
						<input type="button"  class="search" value="搜索" >
					</div>
				</div>
			</form>
			<div class="content_bottom" id="wrap">
				<div id="scroller">
					<div class="content_bottom_width">
					<div class="add_cour">
						<div class="add_cour_div">
							<div class="add_cour_div_top">
								<div class="add_cour_div_top_img"></div> 
							</div>
							<div class="add_cour_div_bottom">上传教学文章</div>
						</div>
					</div>
				    <c:if test="${!empty tList.datalist}">
					     <c:forEach items="${tList.datalist}" var="data">
					            <div class="thesis_word thesis_css" >
									<div class="thesis_img_1">${data.thesisType}</div>
									<h3 title="${data.thesisTitle}"><ui:sout value="${data.thesisTitle}" length="20" needEllipsis="true"></ui:sout></h3>
									<p data-resId="${data.resId}"></p>
									<div class="thesis_img_2" data_id="${data.id}"></div>
									<c:if test="${data.isComment!=0}"><div class="thesis_img_4" data-resId="${data.id}"></div></c:if>
									<div id="cw_option_mask_${data.id}" class="cw_option_mask" style="display:none;"></div>
									<div class="cw_option_${data.id}" style="display:none;" data_id="${data.id}" data_thesisType="${data.thesisType}" data_title="${data.thesisTitle}" data_subjectId="${data.subjectId}" data_name="<jy:dic key='${data.subjectId}'/>" data_resId="${data.resId}">
									    <input type="hidden"  data="${data.id}"/>
										<c:if test="${data.isShare==0}"><div class="cw_option_edit"></div></c:if>
										<c:if test="${data.isShare==1}"><div class="cw_option_jz_edit" ></div></c:if>
										<c:if test="${data.isShare==0}"><div class="cw_option_del"></div></c:if>
										<c:if test="${data.isShare==1}"><div class=cw_option_jz_del ></div></c:if>
										<c:if test="${data.isShare==0}"><div class="cw_option_share" ></div></c:if>
										<c:if test="${data.isShare==1}"><div class="cw_option_qx_share"></div></c:if>
										<a href="<ui:download filename='${data.thesisTitle}' resid='${data.resId}'></ui:download>"><div class="cw_option_down"></div></a>
										<div class="cw_option_close"></div>
						             </div>
				                </div>
					     </c:forEach>
				    </c:if>
				 </div>
				 <c:if test="${empty tList.datalist}">
				    <c:choose>
						<c:when test="${!empty thesis.thesisTitle }">
						    <div class="content_k"><dl><dd></dd><dt>很抱歉，没有找到相关内容，请输入内容重新查找！</dt></dl></div>
						</c:when>
						<c:otherwise>
						    <div class="content_k"><dl><dd></dd><dt>您还没有上传教学文章哟，赶紧去左边“上传教学文章”吧！</dt></dl></div>
						</c:otherwise>
					</c:choose>
				 </c:if>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'js'],function($){	
	});
</script>
</html>