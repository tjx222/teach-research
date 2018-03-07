<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@attribute name="type" type="java.lang.String" required="false"%>
<%@attribute name="needCompanionSide" type="java.lang.Boolean"
	required="false" description="是否同伴侧边栏,默认是"%>
<%@ attribute name="style" type="java.lang.Integer" required="false" description="皮肤样式: 0 默认,1 新样式"%>
 
<c:if test="${jfn:isNormal()}">
<div class="jyyl_footer">
	<div class="jyyl_footer_center">
		<div class="jyyl_footer_center_l">
		<c:choose>
			<c:when test="${fn:startsWith(pageContext.request.serverName,'aijy') }">
				<ul>	
				
				<li>
					<a href="#">工具下载</a>
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
						<a href="#">工具下载</a>
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
						<li><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=328745735&amp;site=qq&amp;menu=yes"><img src="${ctxStatic }/modules/teachingview/images/qq.png" alt=""></a></li>
						<li><img src="${ctxStatic }/modules/common/images/phone.png" alt=""></li>
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
<shiro:user>
	<c:if
		test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'TEACHER')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'FXZ')|| 
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'JYZR')||
				jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'JYY')}">
		<c:if test="${needCompanionSide==null||needCompanionSide }">
			<link rel="stylesheet" href="${ctxStatic}/modules/companion/css/companion_dlog.css" media="screen" />
			<script src="${ctxStatic }/modules/companion/js/companion_side.js"></script>
			<!--好友测试栏--> 
			<div id="companion_add" class="dialog">
				<div class="dialog_wrap">
					<div class="dialog_head">
						<span class="dialog_title">提示信息</span> <span class="dialog_close"></span>
					</div>
					<div class="dialog_content">
						<div class="table_cont">
							<div class="info">恭喜您，关注成功！</div>
						</div>
					</div>
				</div>
			</div>
			<div id="companion_cancel" class="dialog">
				<div class="dialog_wrap">
					<div class="dialog_head">
						<span class="dialog_title">取消关注</span> <span class="dialog_close"></span>
					</div>
					<div class="dialog_content">
						<div class="table_cont">
							<div class="info1">你确定要取消关注“”吗?</div>
							<input type='button' value="确定" class="ascertain" />
						</div>
					</div>
				</div>
			</div>
			<div class="suspension">
				<div class='companion_menu'></div>
				<iframe style="border: 0; border: none; height: 575px;" frameborder="0" scrolling="no"
					src="./jy/companion/companions/latestComunicate-friends/side" allowtransparency="true">
				</iframe> 
			</div> 
			<script type="text/javascript">
				//添加关注
				function showCompanionAdd(userIdCompanion){
					var url = './jy/companion/friends/' + userIdCompanion;
					$.post(url, {}, function(result) {
						if (result.result.code == 1) {
							$('#companion_add').find(".info").html('恭喜您，关注成功！');
							$(".dialog_close").click(function() {
								location.reload();
							});
						} else {
							$('#companion_add').find(".info").html('添加好友失败：' + result.result.errorMsg);
						}
						$("#companion_add").dialog({
							width : 400,
							height : 200
						});
					}, 'json');
				}
				//取消关注
				function showCompanionCancel(userIdCompanion,userNameCompanion){
					var url = "./jy/companion/friends/" + userIdCompanion + ".json";
					$("#companion_cancel").find(".info1").html("你确定要取消关注“" + userNameCompanion + "”吗?");
					$("#companion_cancel").dialog({
						width : 350,
						height : 200
					});
					$(".ascertain").click(function() {
						$(".dialog_close").trigger("click");
						$.ajax(url, {
							dataType : 'json',
							type : 'delete',
							success : function(result) {
								if (result.result.code == 1) {
									$('#companion_add').find(".info").html("取消关注成功！");
									$(".dialog_close").click(function() {
										location.reload();
									});
								} else {
									$('#companion_add').find(".info").html(result.result.msg);
								}
								$("#companion_add").dialog({
									width : 400,
									height : 200
								});
							}
						});
					});
				}
			</script>
		</c:if>
	</c:if>
</shiro:user>
<script type="text/javascript">
$(".jyyl_footer_center_l ul").find("li").first().mouseover(function(){
	$(".jyyl_navoL_li_bg").show();
});
$(".jyyl_navoL_li_bg").mouseout(function(){
	$(this).hide();
});
</script>
</c:if>
<c:if test="${jfn:isMobile() || jfn:isTablet()}">

</c:if> 