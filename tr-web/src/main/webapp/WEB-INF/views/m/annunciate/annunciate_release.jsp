<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="通知公告"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/annunciate/css/annunciate.css" media="screen">
	<ui:require module="../m/annunciate/js"></ui:require>
</head> 
<body>
<div class="preview_dlog">
	<div class="preview_wrap">
		<div class="annunciate_cont" id="annunciate_cont">
			<div id="scroller">
				<div class="annunciate_content_width1"> 
				    <c:if test="${type==1}">
						<div class="annun_cont_red">
							<h3></h3>
							<h4></h4>
							<span></span>
						</div>
					</c:if>
					<div class="annun_cont">
						<h5></h5>
						<ul>
							<li>发布时间：</li>
							<li>|</li>
							<jy:di key="${_CURRENT_USER_.id}" className="com.tmser.tr.uc.service.UserService" var="u">
							<li>作者：${u.name}</li></jy:di>
						</ul>
						<div class="annun_cont_c">
							<p></p>
						</div>
						<div class="annun_cont_b">
							<h6>
								<span class="span1"></span>
								<span class="span2">附件</span>
								<span class="span3"></span>
							</h6>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<c:if test="${!empty list}">
	<div class="ht_select_wrap">
		<div class="ht_select_wrap1">
			<span class="cw_menu_list_top"></span>
			<div id="wrap2" class="cw_menu_list_wrap1"> 
				<div id="scroller">
				    <c:forEach items="${list}" var="l">
						<p data-id="${l.id}"><a>${l.title}</a><span></span></p>
					</c:forEach>
					<input type="button" class="add_ht" value="添加">
				</div>
			</div>
		</div> 
	</div>
</c:if>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header> 
		<span onclick="javascript:window.history.go(-1);"></span>通知公告
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="content">
			<div class="content_bottom1"id="tz_content_bottom1">
				<div id="scroller">
				    <form action="" id="annunciate_form" method="get">
				    <ui:token />
				    <input type="hidden" name="id" value="${jaAnnunciate.id}"/>
				    <input type="hidden" id="redTitleId" name="redTitleId"/>
				    <input type="hidden"  name="type" value="${type}"/>
					<div class="annun_wrap">
						<div class="annunciate_wrap_left">
						    <c:if test="${type==1}">
						        <div class="ht"></div>
							    <div class="ht_border"></div>
						    </c:if>
							<div class="bt"></div>
							<div class="bt_border"></div>
							<div class="nr"></div>
							<div class="nr_border"></div>
							<div class="fj"></div>
						</div>
						<div class="annunciate_wrap_right">
						    <c:if test="${type==1}">
							<div class="ht_content">
								<h3>红头<span>*</span></h3>
								<div class="ht_content_cont">
								     <c:if test="${empty list}"> 
										<input type="button" class="ht_btn" value="+ 添加红头标题"/> 
									</c:if>
									<c:if test="${!empty list}">
										<div class="ht_select">
											<input type="button" id="ht_title" value="${redHeadTitle}" /><q></q>
											
										</div>  
										<input type="text" class="ht_from" style="margin:0 auto;display:block;clear:both;" name="fromWhere" value="${jaAnnunciate.fromWhere}"/>
									</c:if>
									<div class="addRedHead">
										<input type="text" class="redtitle" data-type="${type}" name="redTitle" value=""/>
										<input type="button" class="saveRed" value="保存"/>
										<input type="button" data-list="${list}" class="cencel" value="取消"/>
									</div>
								</div>
							</div></c:if>
							<div class="bt_content">
								<h3>标题<span>*</span></h3>
								<input type="text" class="name" name="title" placeholder="输入通知主题（200字内）" value="${jaAnnunciate.title}">
							</div>
							<div class="nr_content">
								<h3>内容<span>*</span></h3>
								<div class="editor">
									<div>
									<textarea row="10" cols="100" id="content" name="content" placeholder="输入通知主题（5000字内）">${jaAnnunciate.content}</textarea>
									</div>
								</div>
								
							</div> 
							<div class="study_additional_content">
								<h3>附件(最多可上传6个)</h3>
								<div class="clear"></div>
								<input type="hidden" name="attachs" id="tzggRes" value=""/>
								<div class="study_additional_content_l">
									<div class="add_study" >
										<p></p>
										<span>添加附件</span>
									</div>
									<ui:upload_m progressBar="true" fileType="doc,docx,ppt,pptx,flV,swf,txt,pdf,zip,rar" beforeupload="beforeUpload" callback="backUpload"  relativePath="/annunciate/${_CURRENT_USER_.orgId}"></ui:upload_m>
								</div>
								<div class="study_additional_content_r">
								    <c:forEach items="${rList}" var="res">
								        <c:if test="${!empty res.id}">
									    <div class="add_study_content">
											<div class="add_study_content_l">
											</div>
											<div class="add_study_content_c">
												<span data-id="${res.id}"><ui:sout value="${res.name}.${res.ext }" length="30"
												needEllipsis="true"></ui:sout></span>
												<div class="complete">
													上传完成
												</div>
											</div>
											<input type="button" class="add_study_content_r" value="删除">
										</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
						<div class="annun_btn"> 
							<input type="button" class="submit_btn" value="提交中..." />
							<input type="button" class="release">
							<input type="button" class="preview">
							<input type="button" class="save_drafts">
						</div> 
					</div> 
					</form>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'release'],function($){	
	}); 
</script>
</html>