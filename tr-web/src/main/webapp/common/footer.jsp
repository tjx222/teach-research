<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教研平台"></ui:htmlHeader>
</head>
<body> 
 <div class="jyyl_footer" style="margin-top:0;">
	<div class="jyyl_footer_center">
		<div class="jyyl_footer_center_l">
		<c:choose>
			<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
				<ul>	
				
				<li>
					<a disable="true">工具下载</a>
					<div class="jyyl_navoL_li_bg">
						<p><a href="http://www.microsoft.com/zh-cn/download/internet-explorer-8-details.aspx" target="_blank">IE8</a></p>
						<p style="border:none;width:32px;"><a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/" target="_blank">IE11</a></p>
					</div>
				</li>
				<li>|</li>
				<li><a href="jy/feedback/index" target="_blank">反馈意见</a></li>
			</ul>
			<p>© 安徽新华教育图书发行有限公司</p>
			</c:when>
		    <c:otherwise>
				<ul>	 
					<li>
						<a disable="true">工具下载</a>
						<div class="jyyl_navoL_li_bg">
							<p><a href="http://www.microsoft.com/zh-cn/download/internet-explorer-8-details.aspx" target="_blank">IE8</a></p>
							<p style="border:none;width:32px;"><a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/" target="_blank">IE11</a></p>
						</div>
					</li>
					<li>|</li>
					<li><a href="${ctxStatic }/help/products_and_introduction.html" target="_blank">帮助中心</a></li>
					<li>|</li>
					<li><a href="${ctxStatic }/about/on_our_menu.html" target="_blank">关于我们</a></li>
					<li>|</li>
					<li><a href="jy/feedback/index" target="_blank">反馈意见</a></li>
				</ul>
				<p>©明博教育科技股份有限公司 </p>
			</c:otherwise>
		</c:choose> 
		</div> 
			<c:choose>
				<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
				<div class="jyyl_footer_center_r" style="width:240px;">
					<ol>
						<li><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=328745735&amp;site=qq&amp;menu=yes"><img src="${ctxStatic }/common/images/qq.png" alt=""></a></li>
						<li><img src="${ctxStatic }/common/images/phone.png" alt=""></li>
						<li>
							<span>400-995-1080</span>
							<span>周一至周五9:00-22:00</span>
						</li>
					</ol>
				</div>
				</c:when>
			    <c:otherwise>
			   	<div class="jyyl_footer_center_r" >
					<ol>
						<li><script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA1ODQ1OV80MjI5OThfNDAwODk4NTE2Nl8"></script></li>
						<li><a href="mailto:jiaoyan@mainbo.com"><img src="${ctxStatic }/common/images/email.png" alt=""></a><strong class="strong">jiaoyan@mainbo.com</strong></li>
						<li><img src="${ctxStatic }/common/images/cz.png" alt=""><strong class="strong1">010-62164501</strong></li>
						<li><img src="${ctxStatic }/common/images/phone.png" alt=""></li>
						<li>
							<span>400-898-5166</span>
							<span>周一至周五9:00-22:00</span>
						</li>
					</ol>
				</div>
				</c:otherwise>
			</c:choose> 
	</div>
</div> 
<script src="${ctxStatic }/common/js/comm.js"></script>
<shiro:user>
	<c:if
		test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'TEACHER')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'FXZ')}">
		<c:if test="${needCompanionSide==null||needCompanionSide }">
			<script src="${ctxStatic }/modules/companion/js/companion_side.js"></script>
			<!--好友测试栏-->
			<div class="companion_menu_box">
				<div class="companion_menu_1" style="height: 540px;">
					<div class="companion_menu_0"></div>
					<iframe style="border: 0; border: none; height: 540px;" frameborder="0" scrolling="no"
						src="./jy/companion/companions/latestComunicate-friends/side" allowtransparency="true">
					</iframe>
				</div>
			</div>
		</c:if>
	</c:if>
</shiro:user>
<div class="return"></div>
<div>
</div>
<script type="text/javascript">
$(".jyyl_footer_center_l ul").find("li").first().mouseover(function(){
	$(".jyyl_navoL_li_bg").show();
});
$(".jyyl_navoL_li_bg").mouseout(function(){
	$(this).hide();
});
</script>
<script type="text/javascript">
function toFeedbackDetail(){
	var url ="./jy/feedback/index";
	window.open(url);
}
</script>
</body>
