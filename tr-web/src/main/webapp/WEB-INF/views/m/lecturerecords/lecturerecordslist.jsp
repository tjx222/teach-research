<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="听课记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/lecturerecords/css/lecturerecords.css" media="screen">
	<ui:require module="../m/lecturerecords/js"></ui:require>
</head>
<body>
<div class="school_lectures_wrap">
	<div class="school_lectures">
		<span class="check_menu_top"></span>
		<div class="school_lectures_wrapper">
			<div id="scroller"> 
				<p>校内听课记录</p>
				<p>校外听课记录</p> 
			</div>
		</div>
	</div>
</div>
<div class="submit_upload_wrap">
	<div class="submit_upload">
		<div class="submit_upload_title">
			<h3>发送听课记录</h3>
			<span class="close"></span>
		</div>
		<div class="submit_upload_content">
			<div class="submit_width">
				<q></q>
				<span>确定要把该听课记录发送给授课人吗?发送后，授课人将会看到您的听课记录！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="zx_option_wrap" style="display: none;">
	<div class="zx_option">
		<div class="in_school"></div>
		<div class="outside_school"></div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>听课记录
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="annunciate_top">
				<!-- <div class="annunciate_left"> 
					<div class="draft">
						草稿箱(3)
					</div> 
				</div> --><!-- 离线端不做草稿箱 -->
				<div class="annunciate_right">
					<span>全部听课记录</span>
					<strong></strong>
				</div>
			</div>
			<div class="lecturerecords_cont_bottom">
				<div id="scroller">
					<div class="content_bottom_width">
						<div class="add_cour border1">
							<div class="add_cour_div">
								<div class="add_cour_div_top">
									<div class="add_cour_div_top_img"></div> 
								</div>
								<div class="add_cour_div_bottom">撰写听课记录</div>
							</div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_21" title="发送"></div>
							<div class="courseware_img_4" title="评论意见" ><span></span></div>
							<div class="courseware_img_5" title="回复"><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaowai"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_4" title="评论意见" ><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaonei"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_4" title="评论意见"><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaowai"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div> 
							<div class="courseware_img_4" title="评论意见"><span></span></div>
							<div class="courseware_img_5" title="回复"><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaowai"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_21" title="发送"></div>
							<div class="courseware_img_4" title="评论意见" ><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaonei"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_4" title="评论意见" ><span></span></div>
							<div class="courseware_img_5" title="回复"><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaonei"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_21" title="发送"></div>
							<div class="courseware_img_4" title="评论意见" ><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaowai"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_4" title="评论意见" ><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaonei"></div>
						</div>
						<div class="courseware_ppt border1">
							<div class="courseware_img_1">听课记录</div>
							<h3>美好的生活</h3>
							<p><img src="static/common/icon_m/base/lett.jpg"></p>
							<div class="courseware_img_2" title="操作"></div>
							<div class="courseware_img_4" title="评论意见"><span></span></div>
							<div class="courseware_img_5" title="回复"><span></span></div>
							<div class="cw_option_mask" style="display:none;"></div>
							<div class="cw_option" style="display:none;">
								<div class="cw_option_edit" title="编辑"></div>
								<div class="cw_option_del" title="删除"></div> 
								<div class="cw_option_share" title="分享"></div> 
								<div class="cw_option_close" ></div>
							</div>
							<div class="xiaowai"></div>
						</div>
					</div>						
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'js'],function(){	
	}); 
</script>
</html>