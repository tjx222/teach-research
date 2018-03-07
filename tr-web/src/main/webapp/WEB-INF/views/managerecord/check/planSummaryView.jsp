<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅资源查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body>
<div class="clear"></div>
<div class="check_teacher_wrap">
		<div class="check_teacher_wrap2">
			<h3 class="file_title">${model.title }</h3>
			<div class="file_info">
				<div class="file_info_l">
					<span></span>
					<jy:di key="${model.userId}" className="com.tmser.tr.uc.service.UserService" var="us"/>
					作者：${us.name}
				</div>
				<div class="file_info_r">
					<span></span>
					提交日期：<fmt:formatDate value="${model.submitTime}" pattern="yyyy-MM-dd"/>
				</div>
			</div>
			<div class="file_down_btn">
				<input type="button" class="download">
			</div>
			<div class="word_plug_ins">
				<iframe id="view"  width="100%" height="660px;" border="0" style="border: none;margin: 0 auto;display: block;">
				</iframe>
			</div>
		</div>
		<div class="border"></div>
		<div class="check_teacher_wrap2"> 
			<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" height="650" scrolling="no"></iframe>
		</div>
</div>
<div class="clear"></div>
	
	<script type="text/javascript">
		var resid = "${model.contentFileKey}";
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		$(".download").click(function(){
			var name = "${model.title}";
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI(name),"_self");
		});
		$("#checkedBox").attr("src","jy/check/lookCheckOption?flags=false&term=${model.term}&title=<ui:sout value='${model.title}' encodingURL='true' escapeXml='true'></ui:sout>&resType=${resType}&authorId=${model.userId}&resId=${model.id}&titleShow=true");
	</script>
</body>
</html>
