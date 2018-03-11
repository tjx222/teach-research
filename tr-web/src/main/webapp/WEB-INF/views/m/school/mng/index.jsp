<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="教研平台首页"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/school/css/school.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/m/school/css/swiper.min.css" media="screen">
	<ui:require module="../m/school/js"></ui:require>
	<style type="text/css">
.swiper-container {
    width: 80%;
    height: 100%;
}
	</style>
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<div class="header">
		<div class="logo"></div> 
		<div class="identity">
			<jy:di key="${_CURRENT_SPACE_.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
			<ui:photo src="${u.photo}" width="62" height="62"></ui:photo>
			<div class="identity_option">
				欢迎您，${_CURRENT_USER_.name }
			</div>
		</div>
		<ul>
			<c:choose>
			<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'jyzr')}">
				<li id="tzgg2">
					<span class="li_1"></span>	
					<span id="tzgg1">通知公告</span>
				</li>
			</c:when>
			<c:otherwise>
				<li> 
					<strong class="header_ul_menu_wrap">
						<strong class="header_ul_menu">
							<span class="header_ul_menu_top"></span>
							<strong class="header_ul_menu_wrap1"> 
								<p id="tzgg">通知公告(0)</p>
								<!-- <p id="jhzj" style="border-bottom:none;">计划总结(0)</p> -->
							</strong>
						</strong>
					</strong>
					<span class="li_1_d" style="display: none;"></span>
					<%--<span class="li_1" id="jydt"></span>		
					 <span>教研动态</span>  --%>
				</li>
			</c:otherwise>
			</c:choose> 
			<li>
				<a href="./jy/notice/notices?receiverState=">
					<span class="li_2_d" style="display: none;"></span>
					<span class="li_2"></span>
					<span>我的消息</span> 
				</a>
			</li>
			
			<jy:di var="curspace" key="${_CURRENT_SPACE_.id }" className="com.tmser.tr.uc.service.UserSpaceService"></jy:di>
			<c:if test="${curspace.phaseId == 0 }">  
				<li>
					<a href="./jy/uc/select">
						<span class="li_4"></span>
						<span>学段选择</span>
					</a>
				</li>
				
			</c:if>
		</ul>
		<div class="more" onclick="more()"></div>	
	</div>
	<section> 
		<div class="module_content">
			<div class="swiper-container">
				<div class="swiper-wrapper">
					<div class="swiper-slide">
						<c:forEach items="${menus }" var="m" varStatus="status">
						<c:choose>
							<c:when test="${status.index>0 && status.index%8==0 }">
								</div> 
								<div class="swiper-slide">
								<div class="slide_div" data-id="${m.id }">
									<dl>
										<dd url="${m.url }" target="${m.target }"><ui:menuIcon_m iconUrl="${m.icoId }"></ui:menuIcon_m></dd>
										<dt>${m.name }</dt>
									</dl>
								</div>
							</c:when>
							<c:otherwise>
								<div class="slide_div" data-id="${m.id }">
									<dl>
										<dd url="${m.url }" target="${m.target }"><ui:menuIcon_m iconId="${m.icoId }"></ui:menuIcon_m></dd>
										<dt>${m.name }</dt>
									</dl>
								</div>
							</c:otherwise>
						</c:choose>
						</c:forEach>
					</div> 
				</div>
				<div class="swiper-pagination"></div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
require(["zepto",'swiper.min','js'],function(){
	$(document).ready(function(){
		$.ajax({
			 url:'./jy/planSummary/punishs/unViewNum',
			 dataType:'json',
			 type:'GET',
			 success:function(rs){
				 if(rs.data.unViewNum>0){
					 $('.li_1_d').show();
					 $('#jhzj').html("计划总结("+rs.data.unViewNum+")");
				 }
				 if(rs.data.noticeNum>0){
					 $('.li_1_d').show();
					 $('#tzgg').html("通知公告("+rs.data.noticeNum+")");
					 $('#tzgg1').html("通知公告("+rs.data.noticeNum+")");
				 }
				 if(rs.data.unViewNum<=0 && rs.data.noticeNum<=0){
					 $('.li_1_d').hide();
				 }
			 }
		});
		$.ajax({
			url:'./jy/notice/unreadNum?r='+Math.random(),
			type:'get',
			dataType:'json',
			success:function(result){
				if(typeof result=='undefined'){
					return;
				}
				if(result.code==1){
					if(result.data.noticeNum>0){
						$('.li_2_d').show();
						//$('#noticeNum_top').text('('+result.data.noticeNum+')');
					}else{
						$('.li_2_d').hide();
						//$('#noticeNum_top').text('');
					}
				}
			},
			error:function(){
			}
			
		}); 
	});
}); 
</script>
</html>