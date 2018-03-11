<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="我的备课本"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/myplanbook/css/myplanbook.css" media="screen">
	<ui:require module="../m/myplanbook/js"></ui:require>
</head>
<body>
<div class="class_comments_wrap"> 
	<div class="class_comments">
		<div class="class_comments_title">
			<h3>听课意见列表</h3>
			<span class="close"></span>
		</div>
		<div class="class_comments_content">
			<table border="1" >
			  <tr>
			    <th>听课人</th>
			    <th>学科</th>
			    <th>年级</th>
			    <th>听课节数</th>
			    <th>操作</th>
			  </tr>
			 </table>
			 <div class="class_table">
				<div>
					 <table border="1" style="border-top: 0.083rem #fff solid;">
					  <tr>
					    <td>张单</td>
					    <td>语文</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>王大珩</td>
					    <td>思想品德</td>
					    <td>二年级</td>
					    <td>2节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>张单</td>
					    <td>语文</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>张单</td>
					    <td>语文</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>张单</td>
					    <td>语文</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>张单</td>
					    <td>语文</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					  <tr>
					    <td>孙庆世</td>
					    <td>书法与绘画</td>
					    <td>一年级</td>
					    <td>3节</td>
					    <td><strong></strong><span>查看</span></td> 
					  </tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="add_up_wrap">
	<div class="add_upload_wrap1"></div>
	<div class="add_upload">
		<div class="add_upload_title">
			<h3>撰写反思</h3>
			<span class="close"></span>
		</div>
		<div class="add_upload_content">
			<form id="fs_form" action="${ctx}jy/rethink/save" method="post">
				<div id="kh_kt" class="form_input">
					<label>课题目录</label>
					<strong class="select" id="uploadLesson">请选择<q></q></strong>
					<div class="menu_list" >
						<span class="menu_list_top"></span>
						<div id="wrap1" class="menu_list_wrap1"> 
							<div id="scroller"> 
								<p>全部 </p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_3">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
							</div>
						</div>
					</div> 
				</div>
				<div id="qt_kt" class="form_input" style="display: none;">
					<label>反思标题</label>
					<strong><input type="text" id="qt_planName" name="planName" style="width: 22.5rem;height: 3.5rem;border-radius: 0.5rem;padding-left:0.5rem;font-weight: normal;" maxlength="30"/></strong>
				</div>
				<div id="fileuploadContainer" class="form_input">
					<label style="background-color: #fff; position: absolute;left: -3px;z-index: 1;">上传附件</label>
					<strong id="uploadId" style="margin-left:6.3rem;">
						<ui:upload_m fileType="doc,docx,ppt,pptx,pdf" fileSize="50" startElementId="save" beforeupload="start" callback="afterUpload" relativePath="rethink/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload_m>					
					</strong>
				</div>
					<input id="save" type="button" class="btn_edit" value="上传">
					<input type="button" class="btn_cencel" value="取消">
			</form>	
		</div>
	</div>
</div>
<div class="add_upload_wrap">
	<div class="add_upload_wrap1"></div>
	<div class="add_upload">
		<div class="add_upload_title">
			<h3>上传课件</h3>
			<span class="close"></span>
		</div>
		<div class="add_upload_content">
			<form id="kj_form" action="${ctx}jy/courseware/save" method="post">
				<div class="form_input">
					<label>课题目录</label>
					<strong class="select" id="uploadLesson">请选择<q></q></strong>
					<div class="menu_list" >
						<span class="menu_list_top"></span>
						<div id="wrap1" class="menu_list_wrap1">
							<div id="scroller"> 
								<p>全部 </p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_3">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p>一去二三里</p>
								<p class="menu_2">一去二三里</p>
								<p class="menu_2">一去二三里</p>
							</div>
						</div>
					</div> 
				</div>
				<div id="fileuploadContainer" class="form_input">
					<label style="background-color: #fff; position: absolute;left: -3px;z-index: 1;">上传附件</label>
					<strong id="uploadId" style="margin-left:6.3rem;"> 
						<ui:upload_m fileType="doc,docx,ppt,pptx,pdf" fileSize="50" startElementId="save" beforeupload="start" callback="afterUpload" relativePath="courseware/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload_m>
					</strong>
				</div>
				<div id="editButton">
					<input style="display: none;" id="save" type="button" class="btn_edit" value="修改">
					<input id="save_edit" type="button" class="btn_edit" value="修改">
					<input type="button" class="btn_cencel" value="取消">
				</div>
				<input style="display: none;" id="save" type="button" class="btn_upload" value="上传">
			</form>	
		</div>
	</div>
</div>
<div class="zx_option_wrap" style="display: none;">
	<div class="zx_option">
		<div class="zx_option_ja"></div>
		<div class="zx_option_kj"></div>
		<div class="zx_option_fs"></div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>我的备课本
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="content_left">
				<h3><span>目</span><span>录</span></h3>
				<div class="myplanbook_catalog" style="width:70%;top:7.4rem;">
					<div id="scroll">
						<div class="myplanbook_catalog1">
							<h4>
								<span></span><a>第一单元    一去二三里</a>
							</h4>
							<ul>
								<li>
									<a>第二单元    数一数</a>
									<ol>
										<li>
											<a>第三单元  多数数 </a>	
										</li>
										<li>
											<a>第三单元  多数数</a>	
										</li>
									</ol>
								</li>
								<li>
									<a>第二单元    数一数</a>
								</li>
								<li>
									<a>第二单元    数一数</a>
								</li>
							</ul>
						</div>
						<div class="myplanbook_catalog1">
							<h4>
								<span></span><a>第一单元    一去二三里</a>
							</h4>
							<ul>
								<li>
									<a>第二单元    数一数</a>
									<ol>
										<li>
											<a>第三单元  多数数 </a>	
										</li>
										<li>
											<a>第三单元  多数数</a>	
										</li>
									</ol>
								</li>
								<li>
									<a>第二单元    数一数</a>
								</li>
								<li>
									<a>第二单元    数一数</a>
								</li>
							</ul>
						</div>
						<div class="myplanbook_catalog1">
							<h4>
								<span></span><a>第一单元    一去二三里</a>
							</h4>
							<ul>
								<li>
									<a>第二单元    数一数</a>
								</li>
							</ul>
						</div>
						<div class="myplanbook_catalog1">
							<h4>
								<span></span><a>第一单元    一去二三里</a>
							</h4>
							<ul>
								<li>
									<a>第二单元    数一数</a>
									<ol>
										<li>
											<a>第三单元  多数数 </a>	
										</li>
										<li>
											<a>第三单元  多数数</a>	
										</li>
									</ol>
								</li>
								<li>
									<a>第二单元    数一数</a>
								</li>
								<li>
									<a>第二单元    数一数</a>
								</li>
							</ul>
						</div>
						<div class="myplanbook_catalog1">
							<h4>
								<span></span><a>第一单元    一去二三里</a>
							</h4>
						</div>
						<div class="myplanbook_catalog1">
							<h4>
								<span></span><a>第一单元    一去二三里</a>
							</h4>
						</div>
					</div>
				</div>
			</div>
			<div class="content_right">
				<div class="content_right_cont" style="top:2.5rem;">
					<div>
						<div class="content_bottom_width">
							<div class="courseware_ppt border1">
								<div class="courseware_img_courseware">课件</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_rethink">反思</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_courseware">课件</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_courseware">课件</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_rethink">反思</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_courseware">课件</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_courseware">课件</div>
								<h3>1.a o e </h3>
								<p>
									<img src="http://localhost:80/jy-web/static/common/icon_m/base/doc.png" title="1.a o e ">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="courseware_ppt border1">
								<div class="courseware_img_rethink">反思</div>
								<h3>1.a o e </h3>
								<p>
									<img src="static/common/icon_m/base/doc.png">
								</p>
								<div class="courseware_img_2" title="操作"></div> 
								<div class="courseware_img_4" title="评论意见" ></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<div class="cw_option" style="display:none;">
									<div class="cw_option_edit" title="编辑"></div>
									<div class="cw_option_del" title="删除"></div>
									<div class="cw_option_submit" title="提交"></div>
									<div class="cw_option_share" title="分享"></div>
									<div class="cw_option_down" title="下载"></div>
									<div class="cw_option_close"></div>
								</div>
							</div>
							<div class="add_cour">
								<div class="add_cour_div">
									<div class="add_cour_div_top">
										<div class="add_cour_div_top_img"></div> 
									</div>
									<div class="add_cour_div_bottom">添加文件</div>
								</div>
							</div>
						</div>
					</div> 
				</div>
			</div>
			<div class="content_right1">
				<div class="push_resources">
					<div class="push_resources_img"></div>
					<div class="push_resources_text">查阅意见</div>
				</div>
				<div class="peer_resources">
					<div class="peer_resources_img"></div>
					<div class="peer_resources_text">听课意见</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'detail'],function(){	
	}); 
</script>
</html>