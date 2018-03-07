<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="hideMenuList" type="java.lang.Boolean" required="false" description="是否显示快捷菜单"%>
<%@ attribute name="style" type="java.lang.Integer" required="false" description="皮肤样式: 0 默认,1 新样式"%>
<%@ attribute name="modelName" type="java.lang.String" required="false" description="移动端title"%>
<c:if test="${jfn:isNormal()}">
<script type="text/javascript">
$(function(){
	$.ajax('./jy/planSummary/punishs/unViewNum?'+Math.random(),{
		 dataType:'json',
		 type:'GET',
		 success:function(rs){
			 if(rs.data.unViewNum>=0){
				 $('#juzjnum1').html("计划总结("+rs.data.unViewNum+")");
			 }
			 if(rs.data.noticeNum>=0){
				 $('#tzggNum1').html("通知公告("+rs.data.noticeNum+")");
			 }
			 if(rs.data.unViewNum+rs.data.noticeNum>0){ 
				 $('#notice-new1').show();
			 }
		 }
	});  
	 var li_width = $(".li_width").width();    
	 $(".lth").css({"width":li_width});
	 var box_width = $(".identity_box1").width()/2;
	 $(".identity_box1").css({"left":li_width/2,"margin-left":-box_width});
});
</script>
<div class="jyyl_top_logo"> 
	<c:choose>
		<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
			<a href="jy/index">
				<div class="jyyl_logo1"></div>
			</a>
		</c:when>
	    <c:otherwise>
			<a href="jy/index">
				<div class="${sessionScope._CURRENT_USER_.userType==0?'jyyl_logo':'area_logo'} ${sessionScope._sess_flag_ != 'hidebuttom'?'jy':'jx' }"></div>
			</a>
		</c:otherwise>
	</c:choose>
	<div class="jyyl_logo_right">
		<ul>
			<li class="jyyl_logo_right_li jyyl_avatar">
				<ui:photo src="${_CURRENT_USER_.photo }" width="30" height="30" /> 
				<span class="jyyl_head_mask" onclick="personal()"></span>
			</li>
			<li class="jyyl_logo_right_li" style="position:relative;">
				<b class="jyyl_name_news">${_CURRENT_USER_.name }</b>
			</li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li li_hover li_width" style="position:relative;">
				<b class="jyyl_name_news">${_CURRENT_SPACE_.spaceName }</b>
				<c:set value="0" var="hasOtherSpace"></c:set>
				<c:forEach items="${_USER_SPACE_LIST_ }" var="space" end="1"> 
					<c:if test="${_CURRENT_SPACE_.id != space.id }">
						<c:set value="1" var="hasOtherSpace"></c:set>
					</c:if>
				</c:forEach>
				<c:if test="${hasOtherSpace == 1 }">
					<div class="identity_box identity_box1" style="width:200px;left:-5px">
						<span class="identity_sanjiao" style="left:92px"></span>
						<ol class="identity">
							<c:forEach items="${_USER_SPACE_LIST_ }" var="space"> 
								<c:if test="${_CURRENT_SPACE_.id != space.id }">
								<li>
									<a href="jy/uc/switch?spaceid=${space.id }"><span>${space.spaceName }</span></a>
								</li>
								</c:if>
							</c:forEach>
						</ol>
					</div>
				</c:if>
				<u></u>
			</li>
			<c:if test="${_CURRENT_USER_.userType == 0 }">
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li li_hover" style="position:relative;">
				<a href="${ctx}jy/uc/workspace" class="name_news">教研动态</a>
				<div class="identity_box" style="left:-70px">
					<span class="identity_sanjiao" style="left:92px"></span>
					<ol class="identity"> 
						<li>
							<a href="jy/annunciate/noticeIndex" style="color:#4a4a4a">
								<strong id="tzggNum1">通知公告（0）</strong>
							</a>
						</li>
						<li>
							<a href="jy/planSummary/punishs" style="color:#4a4a4a">
								<strong id="juzjnum1">计划总结（0）</strong>
							</a>
						</li>
					</ol>
				<div>
			</li>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li"><a href="${ctx}jy/schoolview/index?orgID=${_CURRENT_USER_.orgId }" target="_blank">学校首页</a></li>
			</c:if>
			<li class="jyyl_logo_right_li">|</li>
			<li class="jyyl_logo_right_li"><a href="${ctx}jy/notice/notices"><b id="notice-new1"></b>消息中心</a><span id="noticeNum_top"></span></li>
			<li class="jyyl_logo_right_li"><a href="${ctx}/jy/logout"><img src="${ctxStatic }/common/images/exit.png" alt="">退出</a></li>
		</ul>
	</div>
</div>
<shiro:user>
	<script src="${ctxStatic }/modules/notice/js/notice.js"></script>
</shiro:user>
</c:if>
<c:if test="${jfn:isMobile() || jfn:isTablet() }">
<header>
	<span onclick="javascript:window.history.go(-1);"></span><em>${modelName}</em>
	<div class="more" onclick="more()"></div>
	<script type="text/javascript">
	require(['jquery'],function($){
		 $(document).ready(function(){
			 if($("header em").html() == ""){
				 $("header em").html($("title").html());
			 } 
		 });
		
	});
	
	</script>
</header>
</c:if>