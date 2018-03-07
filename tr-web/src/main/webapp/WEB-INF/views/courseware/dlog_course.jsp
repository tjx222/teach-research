<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="上传课件"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/courseware/css/dlog_submit.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/courseware/css/courseware.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	
	<script type="text/javascript" src="${ctxStatic }/modules/courseware/js/courseware.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>

</head>
<div class="upload">
	<div class="upload_wrap">
		<div class="dlog-top">
			<span>提交上级</span>
			<span class="close"></span>
		</div>
		<div class="upload-bottom">
			<div class="upload-bottom_tab">
				<ul>
					<li class="upload-bottom_tab_blue">未提交</li><li>已提交</li>
				</ul>
			</div>
			<div class="clear"></div>
			<div class="upload-bottom_submit_big_tab">
						 	<div style="overflow:auto;height:558px;width:794px;">
								<div class="upload-bottom_submit">
									<p>
										<input type="checkbox"> 全选
									</p>
									<input type="button" class="submit1">
									<span>注意：禁选的课件表示已提交！</span>
								</div>
								<ul class="expmenu">
									<%-- <c:forEach var="level1" items="${treeList }">
									<li style="margin-top:10px;">
										<p>
											<input type="checkbox">
										</p>
										<c:if test="${level1.level=='leaf' }">
										</c:if>
									</c:forEach> --%>
									<li style="margin-top:10px;">
										<p>
											<input type="checkbox">
										</p>
										<a class="header">
											<span class="label">第一组</span>
											<span class="arrow up"></span>
											<!-- <span class="right_1"></span> -->
										</a>
										<span class="no">
											<ul class="menu" style="display:none;">
												<li class="checkbox_1">
													<p>
														<input type="checkbox" style="margin-top:5px;">
													</p>
													<a class="header1" >
														<span class="label_2 header_menu" >1.白杨礼赞</span>
														<strong>
															<input type="checkbox" class="check">
															课件：6
															<input type="checkbox" class="check_0">
															反思：6
															<input type="checkbox" class="check_1">
															教案：6
														</strong>
														<span class="arrow up"></span>
														<div class="clear"></div>
														<ol class="menu1" style="display:none;">
											               <li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件1</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件2</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件3</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											  			</ol>
													</a>
													
												</li>
												
											</ul>
										</span>
									</li>
									<div class="clear"></div>
									<li>
										<p>
											<input type="checkbox">
										</p>
										<a class="header">
											<span class="label">第二组</span>
											<span class="arrow up"></span>
											<!-- <span class="right_1"></span> -->
										</a>
										<span class="no">
											<ul class="menu" style="display:none;">
												<li>
													<p>
														<input type="checkbox" style="margin-top:5px;">
													</p>
													<a class="header0" >
														<span class="label_2" >1.白杨礼赞</span>
														<span class="arrow up"></span>
													</a>
													<div class="clear"></div>
													<a class="header01">
														<p>
															<input type="checkbox" style="margin-top:5px;">
														</p>
														<span class="label_2 header001" >1.白杨礼赞</span>
														<strong>
															<input type="checkbox" class="check">
															课件：6
															<input type="checkbox" class="check_0">
															反思：6
															<input type="checkbox" class="check_1">
															教案：6
														</strong>
														<span class="arrow up"></span>
														<div class="clear"></div>
														<ol class="menu1" style="display:none;">
											               <li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件1</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件2</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件3</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											  			</ol>
											  			<div class="clear"></div>
													</a>
													
										  			<div class="clear"></div>
												</li>
												<div class="clear"></div>
												<li>
													<p>
														<input type="checkbox" style="margin-top:5px;">
													</p>
													<a class="header0" >
														<span class="label_2" >2.小蝌蚪找妈妈</span>
														<span class="arrow up"></span>
													</a>
													<div class="clear"></div>
													
													<a class="header01">
														<p>
															<input type="checkbox" style="margin-top:5px;">
														</p>
														<span class="label_2 header001" >1.白杨礼赞</span>
														<strong>
															<input type="checkbox" class="check">
															课件：6
															<input type="checkbox" class="check_0">
															反思：6
															<input type="checkbox" class="check_1">
															教案：6
														</strong>
														<span class="arrow up"></span>
														<div class="clear"></div>
														<ol class="menu1" style="display:none;">
											               <li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件1</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件2</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件3</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											  			</ol>
											  			<div class="clear"></div>
													</a>
													<div class="clear"></div>
													<a class="header01">
														<p>
															<input type="checkbox" style="margin-top:5px;">
														</p>
														<span class="label_2 header001" >1.白杨礼赞1.白杨礼赞1.白杨礼赞
														</span>
														<strong>
															<input type="checkbox" class="check">
																课件：3
															<input type="checkbox" class="check_0">
																反思：3
														</strong>
														<span class="arrow up"></span>
														<div class="clear"></div>
														<ol class="menu1" style="display:none;">
											               <li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件1</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件2</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											               	<li>
											               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
											               		<span>课件3</span>
											               		<input type="checkbox" class="li_box">
											               	</li>
											  			</ol>
											  			<div class="clear"></div>
													</a>
												</li>
											</ul>
										</span>
									</li>
									<div class="clear"></div>
									<li class="checkbox">
										<p>
											<input type="checkbox">
										</p>
										<a class="header">
											<span class="label">第三组</span>
											<strong>
												<input type="checkbox" class="check">
												课件：6
												<input type="checkbox" class="check_0">
												反思：6
												<input type="checkbox" class="check_1">
												教案：6
											</strong>
											<span class="arrow up"></span>
											<!-- <span class="right_1"></span> -->
										</a>
										<span class="no">
											<ul class="menu" style="display:none;">
												<li>
													<ol class="menu1">
										               <li>
										               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
										               		<span>课件1</span>
										               		<input type="checkbox" class="li_box">
										               	</li>
										               	<li>
										               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
										               		<span>课件2</span>
										               		<input type="checkbox" class="li_box">
										               	</li>
										               	<li>
										               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
										               		<span>课件3</span>
										               		<input type="checkbox" class="li_box">
										               	</li>
										               	<li>
										               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
										               		<span>课件4</span>
										               		<input type="checkbox" class="li_box">
										               	</li>
										               	<li>
										               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
										               		<span>课件5</span>
										               		<input type="checkbox" class="li_box">
										               	</li>
										               	<li>
										               		<img src="${ctxStatic }/modules/rethink/images/ppt.png" alt="">
										               		<span>课件6</span>
										               		<input type="checkbox" class="li_box">
										               	</li>
										  			</ol>
												</li>
												
											</ul>
										</span>
										
										
									</li>
								</ul>
							</div>
						</div>
			</div>
		</div>
	</div>
</div>	
<div class="box" style="display:block;"></div>	
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
<script type="text/javascript">
$(document).ready(function(){
						   
	/* 滑动/展开 */
	$("ul.expmenu li > a.header").click(function(){
												   
		var arrow = $(this).find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}	
		if(arrow.hasClass("up")){
			$(this).removeClass("headers");
		}else if(arrow.hasClass("down")){
			$(this).addClass("headers");
		}
		/*$(this).addClass("headers").siblings().removeClass("headers");*/
		$(this).parent().find("ul.menu").slideToggle();
		/*$(this).$('.right_1').hide();*/
		
	});
	/* 滑动/展开 */
	$("ul.expmenu li > a.header1 > span.header_menu").click(function(){

		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().parent().find("ol.menu1").slideToggle();
		
	});
	
	$("ul.expmenu li > a.header0 span.label_2").click(function(){

		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().parent().find("a.header01").slideToggle();
	});
	
	$("ul.expmenu li > a.header01 > span.header001").click(function(){
		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().find("ol.menu1").slideToggle();
		 
	});
	
});
</script>
</html>
