<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta charset="UTF-8">
<ui:htmlHeader title="撰写教案"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/writelessonplan/css/index.css" media="screen">
<script type="text/javascript" src="${ctxStatic}/modules/writelessonplan/js/js.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
});
var _SAVE_PATH="${path}";
//显示新模板页面
function showNewTemplate(obj,currentTpId){
	if($(obj).prop("id")!=currentTpId){
			$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/ws/uclass/toEditWordPage?lessonId=${lessonId}&tpId="+$(obj).prop("id"));
	}
}

</script>
</head>
<body>
<div id="box2" style="width: 100%;height: 100%;display:none; position: absolute; z-index: 1001;overflow:hidden;" >
	<div class="box" style="z-index: 1001;">
	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:2; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
	</div>
</div>	
	<div class="wrap">
		<div class="com_cont">
				<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
				<iframe id="iframe2" style="border: none;border:0;z-index:-1;width: 970px;height:730px; float: left;" scrolling="no" frameborder="0"  allowtransparency="true" src="${pageContext.request.contextPath }/jy/ws/uclass/toEditWordPage?lessonId=${lessonId}">
					
				</iframe>
		</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		<div class="clear"></div>
	</div>
</body>
</html>