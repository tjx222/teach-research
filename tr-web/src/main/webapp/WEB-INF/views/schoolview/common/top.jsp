<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctxStatic}/modules/schoolview/css/reset.css">
<link rel="stylesheet" href="${ctxStatic}/modules/schoolview/css/common.css">
<c:if test="${theme==null}">
	<link rel="stylesheet" href="${ctxStatic}/modules/schoolview/css/index_2/school_index2.css">
</c:if>
<c:if test="${theme!=null}">
	<link rel="stylesheet" href="${ctxStatic}/modules/schoolview/css/${theme}">
</c:if>
<div class="bg_box1"></div>
<div class="login_wrap">
	<div class="login">
		<div class="banner_login_r">
		<span class="close1"></span>
			<div class="banner_login_r_c">
				<h3>平台用户登录</h3>
				<form id="login" action="jy/uc/login" method="post">
					<div style="color:#f00;position: absolute;width: 312px;">
						&nbsp;<span id="errmsg" style="position: absolute;top: -24px;height: 19px;line-height: 14px;">${error }</span>
					</div>
					<input type="text" name="username" class="validate[required,minSize[4]] txt" placeholder="请输入账号" title="用户名">
					<input type="password" class="validate[required,minSize[6]] pwd" name="password" placeholder="请输入密码">
					<input type="button" class="btn" id="login_btn" value="">
					<h4><a href="jy/uc/findps/retrievepassword" class="a_retrieve">忘记密码</a></h4>
					<h5>  
						<span>
							<input type="checkbox" class="check" id="rememberPassword" title="请勿在公用电脑上启用。">记住密码  
						</span>
						<span style="float:right;">
							<input type="checkbox" style="margin-top: -0.5px;" title="请勿在公用电脑上启用。" name="rememberMe" value="true">自动登录
						</span>
					</h5>
				</form>
			</div>
		</div>
	</div>  
</div>
<c:set value="${sessionScope._CURRENT_USER_}" var="user"></c:set>
<c:set value="${sessionScope._USER_SPACE_LIST_}" var="userSpaces"></c:set>
<c:set value="${sessionScope._CURRENT_SPACE_}" var="currentSpace"></c:set>

<div class="top1">
	<div class="top1_cont">
		<div class="top1_cont_right">
			<c:if test="${user eq null}">
				<span onclick="login();">马上登录</span>
			</c:if>
			<c:if test="${user ne null}">
				<span style="margin-right: 15px;">${currentSpace.spaceName}<img
					src="${ctxStatic}/modules/schoolview/images/school/sj.png" alt="">
					<ol>
						<c:forEach items="${userSpaces}" var="p">
							<a href="jy/uc/switch?spaceid=${p.id}"><li value="${p.id}">${p.spaceName}</li></a>
						</c:forEach>
					</ol>
				</span>
				<strong style="position: relative; top: 1px;">${user.name}<a
					href="javascript:"  onclick="logout()">[退出]</a></strong>
			</c:if>
		</div>
	</div>
</div>
<div class="top2">
	<div class="top2_logo">
		<div class="top2_logo_l">
		 <jy:di var="org" key="${cm.orgID }" className="com.tmser.tr.manage.org.service.OrganizationService"></jy:di>
			<c:if test="${not empty org.logo}">
			<jy:ds var="photo" key="${org.logo }" className="com.tmser.tr.manage.resources.service.ResourcesService"></jy:ds>
			<c:if test="${not empty photo}">
				<img src="${photo.path}" height="87px" width="87px">
			</c:if>
			</c:if>
		</div>
		<div class="top2_logo_r">
			<div class="school_name">${org.name}</div>
			<span>${org.shortName}</span>
		</div>
		<c:if test="${not empty cm.xueduans && fn:length(cm.xueduans) > 1 }">
			<div class="top2_logo_r1" data-id="${cm.xdid}">
			<div class="list_width">	
				<c:forEach items="${cm.xueduans}" var="p" >
					<span class="${p.id}"><a style="cursor: pointer;" class="nav_color" href="jy/schoolview/index?orgID=${cm.orgID}&xdid=${p.id}">${p.name}部</a></span>
				 </c:forEach>
			</div>
		</div>		
	    </c:if>
	</div>
</div>

<!-- 设置导航的类型 -->
<div class="top3">
	<div class="top3_nav">
		<ul>
			<li><a
				href="jy/schoolview/index?orgID=${cm.orgID}&xdid=${cm.xdid}"
				class="${cm.dh==1?'top3_nav_act':''}">首页</a></li>
			<li><a
				data-url="jy/schoolview/res/lessonres/getPreparationResDetailed?subject=0"
				href="javascript:" onclick="opearDom(this,'_self')"
				class="${cm.dh==2?'top3_nav_act':''}">备课资源</a></li>
			<li><a
				data-url="jy/schoolview/res/teachactive/getSpecificAvtive?restype=1&subject=0"
				href="javascript:" onclick="opearDom(this,'_self')"
				class="${cm.dh==3?'top3_nav_act':''}">教研活动</a></li>
			<li><a
				data-url="jy/schoolview/res/progrowth/getSpecificProfession?restype=1&subject=0"
				href="javascript:" onclick="opearDom(this,'_self')"
				class="${cm.dh==4?'top3_nav_act':''}">专业成长</a></li>
			<li><a
				data-url="jy/schoolview/res/recordbags/getSpecificRecordBag"
				href="javascript:" onclick="opearDom(this,'_self')"
				class="${cm.dh==5?'top3_nav_act':''}">成长档案袋</a></li>
				<%-- <c:if test="${not empty cm.xueduans && fn:length(cm.xueduans) > 1 }">
					<c:forEach items="${cm.xueduans}" var="p" >
							<li>
								<a style="cursor: pointer;" class="nav_color"
								href="jy/schoolview/index?orgID=${cm.orgID}&xdid=${p.id}">${p.name}部</a></li>
					 </c:forEach>
			    </c:if> --%>
		</ul>
	</div>
</div>

<!-- 学校资源信息 -->
<script src="${ctxStatic}/modules/schoolview/js/index3.js"></script>
<!-- 学校展示信息加载js-->
<script src="${ctxStatic}/modules/schoolview/js/opear3.js"></script>
<script type="text/javascript">
//学校首页全局变量声明区（以后用postData对象包装，并在url中转传递）
	var postData={
			orgID : "${cm.orgID}",
			xdid : "${cm.xdid}",
			subject:"0"
		}
		var orgID = "${cm.orgID}";//学校的ID主键
		var xdid = "${cm.xdid}";//学段的ID主键.(orgID必须带着，xdid可带可不带，带着就指定学段，不带就默认学段)
		var subject="0";//科目的全局变量
	var postDataStr="orgID=${cm.orgID}&xdid=${cm.xdid}"; 
	$(function(){
		    $("#login_btn").click(function(){
		    	openLogin();
		    });
			var xdid=$('.top2_logo_r1').attr('data-id');
			if(xdid==1){
				$('.top2_logo_r1 span:eq(0)').addClass('span_act');
			}
			if(xdid==2){
				$('.top2_logo_r1 span:eq(2)').addClass('span_act');
			}
			if(xdid==3){
				$('.top2_logo_r1 span:eq(1)').addClass('span_act');
			} 
	})
</script>
