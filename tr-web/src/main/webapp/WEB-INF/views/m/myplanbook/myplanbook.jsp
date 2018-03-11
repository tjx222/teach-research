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
<div class="cw_menu_wrap" >
	<div class="cw_menu_list" >
		<span class="cw_menu_list_top"></span>
		<div class="cw_menu_list_wrap1"> 
			<div id="scroller">
				<p>人教版语文一年级下册</p>
				<p>人教版历史与社会一年级上册</p>
				<p>人教版历史与社会一年级下册</p>
				<p>人教版历史与社会一年级下册</p>
				<p>人教版历史与社会一年级下册</p>
				<p>人教版历史与社会一年级下册</p>
			</div>
		</div>
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
			<div class="myplanbook_cont">
				<div class="myplanbook_cont_l">
					<div class="top_book">
						<div class="top_book_cont">人教版语文一年级上册</div>
						<strong></strong>
					</div>
					<img src="${ctxStatic }/m/myplanbook/images/title.png" />
					<div class="myplanbook_len">
						<ul>
							<li>教案：22</li>
							<li>课件：14</li>
							<li>反思：17</li> 
						</ul>
						<ol>
							<li>已提交：28</li> 
							<li>已分享：10</li> 
						</ol>
					</div>
					<div class="myplanbook_class">
						<p>
							<label>姓名：</label>
							<input type="text" class="my_txt" value="张宁">
						</p>
						<p>
							<label>学科：</label>
							<input type="text" class="my_txt" value="语文">
						</p>
						<p>
							<label>年级：</label>
							<input type="text" class="my_txt" value="五年级">
						</p>
					</div>
				</div>
				<div class="myplanbook_cont_r">
					<input type="button" class="submit_btn" value="提交上级">
					<h3><span>目</span><span>录</span></h3>
					<div class="myplanbook_catalog myplanbook_catalog_1">
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
							</div>
						</div>
					</div>
				</div>
				<div class="myplanbook_cont_r_hide">
					<input type="button" class="qx_submit_btn" value="取消">
					<input type="button" class="submit_btn" value="提交">
					<div class="notes">
					<span>!</span>
					<strong>选择课题，将课题下相应的文件提交给管理者，方便查阅。</strong>
					</div>
					<div class="myplanbook_catalog myplanbook_catalog2">
						<div id="scroll">
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act sub_strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act sub_strong_act">√</strong></a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act sub_strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong> </a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong> </a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div><div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong> </a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
								<ul>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
										<ol>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong> </a>	
											</li>
											<li>
												<a>第三单元  多数数<strong class="strong_act">√</strong></a>	
											</li>
										</ol>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
									<li>
										<a>第二单元    数一数<strong class="strong_act">√</strong></a>
									</li>
								</ul>
							</div>
							<div class="myplanbook_catalog1">
								<h4>
									<span></span><a>第一单元    一去二三里<strong class="strong_act">√</strong></a>
								</h4>
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
	require(["zepto",'js'],function(){	
	}); 
</script>
</html>