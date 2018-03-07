<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.cookie.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css" media="all">
<script type="text/javascript">
var _WEB_CONTEXT_ = '${pageContext.request.contextPath }';
if(self != top)
   top.location.replace(self.location);
</script>
<link rel="stylesheet" href="${ctxStatic }/modules/uc/login/css/login1.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<title>教研平台</title>
</head>
<body>
	<shiro:user >
		<c:redirect url="/jy/uc/workspace"></c:redirect>
	</shiro:user>
	<shiro:guest>
	<div class="login_wrap">
		<div class="login_wrap_m">
		<c:choose>
			<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
				<div class="login_wrap_m_l_aijy"></div>
			</c:when>
		    <c:otherwise>
				<div class="login_wrap_m_l"></div>
			</c:otherwise>
		</c:choose>
			<div class="login_wrap_m_r">
				<ul>
					<li style="margin-top: 8px;"><script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA1ODQ1OV80MjI5OThfNDAwODk4NTE2Nl8"></script></li>
					<li class="email"><a href="#"></a><strong class="strong">jiaoyan@mainbo.com</strong></li>
					<li class="phone"><a href="#"></a><strong class="strong1">400-898-5166</strong></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="banner">
		<div class="banner_login">
		    <div class="banner_login_l">
		    </div>
			<div class="banner_login_r">
				<div class="banner_login_r_c">
					<h3>平台用户登录</h3>
					<form id="login" action="jy/uc/login" method="post">
						<div style="color:#f00;position: absolute;width: 312px;">
							&nbsp;<span id="errmsg">${error }</span>
						</div>
						<div class="login_news">
						<span>账号：</span><input type="text"  name="username" class="validate[required,minSize[4]] txt"  title="用户名"/>
						</div>
						<div class="login_news">
						<span>密码：</span><input type="password"  class="validate[required,minSize[6]] pwd" name="password"/>
						</div>
						<input type="submit" class="btn" value="">
						<h4><a href="jy/uc/findps/retrievepassword" class="a_retrieve">忘记密码</a></h4>
						<h5>  
							<span style="float:left;">
								<input type="checkbox" class="check" style="margin-top: -0.5px;margin-top:-2px\9\0;" id="rememberPassword" title="请勿在公用电脑上启用。">记住密码  
							</span>
							<span style="float:right;">
								<input type="checkbox" style="margin-top: -0.5px;margin-top:-2px\9\0;" title="请勿在公用电脑上启用。" name="rememberMe" value="true">自动登录
							</span>
						</h5>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="login_type_content_box">
		<ul class="login_type_content">
			<li class="yes"><a href="#product"><strong>产品特点</strong><span>PRODUCT</span></a></li>
			<li><a href="#teacher_studio"><strong>教师工作室</strong><span>TEACHER</span></a></li>
			<li><a href="#school_management"><strong>学校管理室</strong><span>SCHOOL</span></a></li>
			<li><a href="#performance"><strong>绩效考核</strong><span>EFFICIENCY</span></a></li>
			<li><a href="#resources" class="no"><strong>精品资源</strong><span>BOUTIQUE</span></a></li>
		</ul>
	</div>
	<div class="product cons" id="product">
		<div class="product_features">
			<div class="pro_fes_bottom">
				<div class="pro_fes">
					<img src="${ctxStatic }/modules/uc/login/images/pro_1.png" alt="">
					<div class="pro_title">服务常态</div>
					<div class="pro_cont">
						现实工作项就是功能点，教师或
						管理者,只需做好常态工作；解决
						了当前信息化产品增加教师工
						作负担问题。
					</div> 
				</div>
				<div class="pro_fes">
					<img src="${ctxStatic }/modules/uc/login/images/pro_2.png" alt="">
					<div class="pro_title">帮忙不添乱</div>
					<div class="pro_cont">
						发挥信息技术正能量，着眼于提
						高效率;使教师工作更便捷，解决
						了教师不愿用的问题。
					</div> 
				</div>
				<div class="pro_fes">
					<img src="${ctxStatic }/modules/uc/login/images/pro_3.png" alt="">
					<div class="pro_title">学校管理科研化</div>
					<div class="pro_cont">
						使学校管理者对教师的指导与交
						流更及时；解决了当前因管理人
						员少而管理不够深入等问题。
					</div> 
				</div>
				<div class="pro_fes">
					<img src="${ctxStatic }/modules/uc/login/images/pro_4.png" alt="">
					<div class="pro_title">绩效考核更客观</div>
					<div class="pro_cont">
						突破了当前教师“教师绩效”考
						核的难点；对教师的教学成绩进
						行标准化处理和系统跟踪；公正
						客观的评价教师工作实绩。
					</div> 
				</div>
				<div class="pro_fes">
					<img src="${ctxStatic }/modules/uc/login/images/pro_5.png" alt="">
					<div class="pro_title">海量精品资源</div>
					<div class="pro_cont">
						平台预置近30万条精品资源，
						根据教师的教学需要随课题推送相关资源，
						免去教师查找的麻烦。
					</div> 
				</div>
			</div>
		</div> 
	</div>
	<div class="clear"></div>
	<div class="teacher_studio cons" id="teacher_studio">
		<div class="t_s">
			<div class="te_st_title">
				<div class="teach_bor"></div>
				<div class="teach_t">教师工作室</div>
				<div class="teach_bor"></div>
			</div>
			<div class="t_b">
				<div class="t_b_left"></div>
				<div class="t_b_right">
					<div class="t_b_right_1">
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/uc/login/images/tea1.png" alt=""class="trans">
								
							</dd>
							<dt>集体备课</dt>
						</dl>
						<div class="img_hover1">
									<h5><span>·</span><strong>集体备课</strong></h5>
									<div class="img_description1">
										教师在平台可以进行
										“同备教案”、“主题
										  研讨”、“语音视频
										  研讨”。
									</div>
								</div>
					</div>
					<div class="t_b_right_2">
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/uc/login/images/tea2.png" alt=""class="trans">
								
							</dd>
							<dt>撰写教案</dt>
						</dl>
						<div class="img_hover2">
							<h5><span>·</span><strong>撰写教案</strong></h5>
							<div class="img_description2">
								<ul>
									<li>调用word文档进行
									     备课，不改变教师使
									    用习惯。
									</li>
									<li>随课题同步推送资源
									     免去查找麻烦，真正
									     实现帮忙不添乱。
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="t_b_right_3">
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/uc/login/images/tea3.png" alt=""class="trans">
								
							</dd>
							<dt>同伴互助</dt>
						</dl>
						<div class="img_hover3">
							<h5><span>·</span><strong>同伴互助</strong></h5>
							<div class="img_description3">
								将全国同一年级同一教
								师集结成群，实现跨区
								域跨时空的同伴互助，
								找到教改知音。
							</div>
						</div>
					</div>
					<div class="clear"></div>
					<div class="t_b_right_4">
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/uc/login/images/tea4.png" alt=""class="trans">
								
							</dd>
							<dt>同伴资源</dt>
						</dl>
						<div class="img_hover4">
							<h5><span>·</span><strong>同伴资源</strong></h5>
							<div class="img_description4">
								同年级同学科的教师自
								动集结成群，因备课本
								进度相似，因此平台自
								动生成资源的同时，对
								资源进行即时更新，确
								保资源新鲜而有质量。
							</div>
						</div>
					</div>
					<div class="t_b_right_5">
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/uc/login/images/tea5.png" alt=""class="trans">
								
							</dd>
							<dt>成长档案袋</dt>
						</dl>
						<div class="img_hover5">
							<h5><span>·</span><strong>成长档案袋</strong></h5>
							<div class="img_description5">
								自动收集教师网络教研
								情况，省去教师收集整
								理档案袋的繁琐步骤。
								不仅能够展示自我教学
								成果，同时也能共享精
								华资源。
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</div>
	<div class="clear"></div>
	<div class="school_management cons" id="school_management">
		<div class="sch_s">
			<div class="sch_title">
				<div class="sch_bor"></div>
				<div class="sch_t">学校管理室</div>
				<div class="sch_bor"></div>
			</div>
			<div class="sch_b">
				<div class="sch_b_1">
					<dl>
						<dd>
							<img src="${ctxStatic }/modules/uc/login/images/01.png" alt="" class="trans">
							
						</dd>
						<dt>成绩评价</dt>
					</dl>
					<div class="img_hover01">
						<h5><span>·</span><strong>成绩评价</strong></h5>
						<div class="img_description01">
							一键导入成绩，系统就
							可以自动进行各维度成
							绩分析，不仅有学生成
							绩的跟踪，同时能实现
							跨学科教师教学能力的
							比较分析。
						</div>
					</div>
				</div>
				<div class="sch_b_2">
					<dl>
						<dd>
							<img src="${ctxStatic }/modules/uc/login/images/02.png" alt="" class="trans">						
						</dd>
						<dt>教学检查</dt>
					</dl>
					<div class="img_hover02">
						<h5><span>·</span><strong>教学检查</strong></h5>
						<div class="img_description02">
							随时查阅备课、教研情
							况，使管理查阅工作日
							常化。并自动将查阅意
							见反馈给教师，实现教
							学管理科研化。
						</div>
					</div>
				</div>
				<div class="sch_b_3">
					<dl>
						<dd>
							<img src="${ctxStatic }/modules/uc/login/images/03.png" alt="" class="trans">
						</dd>
						<dt>绩效考核</dt>
					</dl>
					<div class="img_hover03">
						<h5><span>·</span><strong>绩效考核</strong></h5>
						<div class="img_description03">
							帮助学校完成平时与定
							期绩效考核工作，以常
							规管理流程和运行机制
							为基础预制考核标准。
						</div>
					</div>
				</div>
				<div class="sch_b_4">
					<dl>
						<dd>
							<img src="${ctxStatic }/modules/uc/login/images/04.png" alt="" class="trans" >
							
						</dd>
						<dt>教研一览</dt>
					</dl>
					<div class="img_hover04">
						<h5><span>·</span><strong>教研一览</strong></h5>
						<div class="img_description04">
							平台自动将教师教研情
							况统计为“教研一览表
							”,使教师教研情况一目
							了然。
						</div>
					</div>
				</div>
				<div class="sch_b_5">
					<dl>
						<dd>
							<img src="${ctxStatic }/modules/uc/login/images/05.png" alt="" class="trans">
						</dd>
						<dt>资源建设</dt>
					</dl>
					<div class="img_hover05">
						<h5><span>·</span><strong>资源建设</strong></h5>
						<div class="img_description05">
							教师教研工作中自动生
							成资源，并在学校管理
							过程中确保资源质量，
							真正解决资源更新与审
							核的问题。
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div class="performance cons" id="performance">
		<div class="sch_s">
			<div class="sch_title">
				<div class="sch_bor"></div>
				<div class="sch_t">绩效考核</div>
				<div class="sch_bor"></div>
			</div>
			<div class="performance_b">
				<div class="per_type">
					<img src="${ctxStatic }/modules/uc/login/images/pre1.png" alt="">
					<div class="pre_title">预置考核标准</div>
					<div class="pre_cont">
					系统预置考核标准通用模板，使考核更规范、更精准、
					更便捷破解当前绩效考核方法单一的问题。
					</div>
				</div>
				<div class="per_type">
					<img src="${ctxStatic }/modules/uc/login/images/pre2.png" alt="">
					<div class="pre_title">促进过程管理</div>
					<div class="pre_cont">
					考核采点为教师全部工作项，使考核常态化、
					促进过程管理避免当前结果性考核的漏洞。
					</div>
				</div>
				<div class="per_type">
					<img src="${ctxStatic }/modules/uc/login/images/pre3.png" alt="">
					<div class="pre_title">特有量化计算公式</div>
					<div class="pre_cont">
					平台内置数学建模公式（特有的），破解教师工作实绩难以量化的难点。
					</div>
				</div>
				<div class="per_type">
					<img src="${ctxStatic }/modules/uc/login/images/pre4.png" alt="">
					<div class="pre_title">定量与定性考核结合</div>
					<div class="pre_cont">
					定量考核使繁杂的工作数据得以量化，等级定性评价把好工作质量关。
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div class="resources cons" id="resources">
		<div class="sch_s">
			<div class="sch_title">
				<div class="sch_bor"></div>
				<div class="sch_t">精品资源</div>
				<div class="sch_bor"></div>
			</div>
			<div class="resources_b">
				<img src="${ctxStatic }/modules/uc/login/images/res_img.jpg" alt="" >
			</div>
		</div>
	</div>
	<div class="footer">  
		<c:choose>
			<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
				<span>QQ:328745735&nbsp;&nbsp;&nbsp;&nbsp;QQ群：549301059&nbsp;&nbsp;&nbsp;&nbsp;皖新的服务热线为：400-995-1080</span>
				<span>Copyright 2009-2014 版权所有 安徽新华教育图书发行有限公司 京ICP备09067472号 京公网安备11010802013475号</span>
			</c:when>
		    <c:otherwise>
				Copyright 2009-2014 版权所有 明博公司 京ICP备09067472号 京公网安备11010802013475号
			</c:otherwise>
		</c:choose> 
	</div>
	</shiro:guest>
</body>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script src="${ctxStatic }/modules/uc/login/js/login.js"></script>
<script>
$(function() {
	$(".login_wrap_m_r  li").last().css("margin-right","0px");
	$(".login_type_content li").each(function(){
		var index=$(this).index();
		var pos = $(".cons").eq(index).offset().top-164;
		var self=this;
		$(this).click(function(){
			$(self).addClass("yes").siblings().removeClass("yes");
			$("body,html").animate({scrollTop:pos},50);
		})
	})
	if($.cookie("_checked_")){
		$('input[name="username"]').val($.cookie("_username_"));
		$('input[name="password"]').val($.cookie("_password_"));
		$("#rememberPassword").prop("checked",true);
	}
	$('input[name="username"]').blur(function(){
		var newname = $(this).val();
		if($.cookie("_checked_")){
			var oldname = $.cookie("_username_");
			if(newname != oldname){
				$('input[name="password"]').val("");
				$("#rememberPassword").prop("checked",false);
			}
		}
	});
	$("#login").validationEngine('attach',{
		onValidationComplete: function(form, status){
			if(status){
				if($("#rememberPassword").prop("checked")){
					var nm = $('input[name="username"]').val();
					var ps = $('input[name="password"]').val();
					$.cookie("_username_",nm,{expires:7});
					$.cookie("_password_",ps,{expires:7});
					$.cookie("_checked_",true,{expires:7});
				}else{
					$.removeCookie("_username_");
					$.removeCookie("_password_");
					$.removeCookie("_checked_");
				}
				return true;
			}
		},scroll:false,onFieldFailure:function(){$("#errmsg").hide();}});
 
});
window.onload=function(){
	 window.onscroll=function(){
	  	var top=document.documentElement.scrollTop||document.body.scrollTop;
	  	var h=$(".banner").height();
	  	var h2=$(".login_wrap").height();
	  	if(top>=h+h2){
	  		$(".login_type_content_box").addClass("_fixed")
	  	}else{
	  		$(".login_type_content_box").removeClass("_fixed")
	  	}
	 }	
}

</script>
</html>