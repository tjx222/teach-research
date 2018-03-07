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
<div class="check_menu1_wrap">
	<div class="check_block_menu1">
		<span class="check_menu_top"></span>
		<div class="c_m_w check_menu_wrap2">
			<div id="scroller"> 
				<p>一年级</p>
				<p>二年级</p>
				<p>三年级</p>
				<p>四年级</p>
				<p>五年级</p>
				<p>六年级</p>
			</div>
		</div>
	</div>
</div>
<div class="check_menu_wrap">
	<div class="check_block_menu">
		<span class="check_menu_top"></span>
		<div class="c_m_w check_menu_wrap1"> 
			<div id="scroller"> 
				<p>语文</p>
				<p>数学</p>
				<p>英语</p>
				<p>音乐</p>
				<p>体育</p>
			    <p>美术</p>
				<p>思品</p>
				<p>诵读</p>
				<p>劳动与技术</p>
				<p>信息技术</p>
				<p>品德与社会</p>
				<p>品德与生活</p>
			</div>
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		听课记录(查找人)
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content"> 
			<div class="check_content_top">
				<h3>筛选：</h3>
				<div class="check_content_block">
					<label>学科</label> 
					<span>语文<strong></strong></span>   
				</div> 
				<div class="check_content_block1">
					<label>年级</label> 
					<span>一年级 <strong></strong></span>
				</div>
			</div>
		    <div class="check_content_bottom" >
				<div id="scroller">
					<div class="check_cont_wrap"> 
						<div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
						 <div class="check_cont">
							<div class="check_cont_left">
								<a target="_blank">
									<img src="static/common/images/default.jpg" height="" width="" alt="">
								</a>
								<span>
									<a target="_blank" href="jy/check/lesson/2/tch/1875?grade=102&amp;subject=100">胡艳芳 </a>
								</span>
							</div>
						 </div> 
					</div>
				</div> 
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'findpeople'],function(){	
	}); 
</script>
</html>