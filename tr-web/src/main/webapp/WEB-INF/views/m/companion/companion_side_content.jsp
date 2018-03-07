<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="同伴互助"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/companion/css/companion.css" media="screen">
	<ui:require module="../m/companion/js"></ui:require>
	<style>
	.com_personal_cont_head_cont1_r span{
		line-height:3rem;
	}
	</style>
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>同伴互助
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="companion_content">
			<div class="companion_content_l">
				<dl>
					<dd class="img_1"></dd>
					<dt>同伴在线</dt>
				</dl>
				<dl>
					<dd class="img_2"></dd>
					<dt>我的消息</dt>
				</dl>
				<dl class="com_img">
					<dd class="img_3"></dd>
					<dt>我的关注</dt>
				</dl>
			</div>
			<div class="companion_content_r">
				<div class="companion_content_r_l">
					<iframe id="companionsIframe" scrolling="no" frameborder="0" style="width: 100%;height: 100%" src=""></iframe>
				</div>
				<div class="companion_content_r_r"> 
					<iframe id="companionshareIframe" scrolling="no" frameborder="0" style="width: 100%;height: 100%;" src=""></iframe>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto"],function(){
		$("#companionsIframe").attr("src",_WEB_CONTEXT_+"/jy/companion/companions/mycomps?iscare=true");
		$("#companionshareIframe").attr("src",_WEB_CONTEXT_+"/jy/companion/companions/compshare?companionId=${companionId}&selfinfo=false");
		$('.companion_content_l dl').eq(0).click(function (){ 
			location.href = _WEB_CONTEXT_+"/jy/companion/companions/index"
		});
		$('.companion_content_l dl').eq(1).click(function (){ 
			location.href = _WEB_CONTEXT_+"/jy/companion/companions/mymsg"
		});
		window.userSenderMsg = function(userid){
			$("#companionshareIframe").attr("src",_WEB_CONTEXT_+"/jy/companion/companions/compshare?companionId="+userid+"&selfinfo=false");
		}
	});
</script>
</html>