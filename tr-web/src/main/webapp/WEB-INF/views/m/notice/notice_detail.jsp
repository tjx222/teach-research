<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="消息"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/annunciate/css/annunciate.css" media="screen">
	<ui:require module="../m/annunciate/js"></ui:require>
	<script type="text/javascript">
		function getNoticeId(){
			return "${notice.id }";
		}
	</script>
</head> 
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header> 
		<span onclick="javascript:window.history.go(-1);"></span>消息查看
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="annunciate_content" id="annunciate_content">
			<div id="scroller">
				<div class="annunciate_content_width"> 
					<div class="annun_cont">
						<h5>${notice.title }</h5>
						<ul>
							<li><fmt:formatDate value="${notice.sendDate}" pattern="yyyy-MM-dd" /></li>
						</ul>
						<div class="annun_cont_c">
							<p>
							${notice.detail }
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'public'],function(){	
	}); 
</script>
</html>