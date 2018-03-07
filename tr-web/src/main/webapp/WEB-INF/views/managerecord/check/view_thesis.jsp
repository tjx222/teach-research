<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查阅资源查看"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
</head>
<body>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap2"> 
		<h3 class="file_title"><ui:sout value="${findOne.thesisTitle}" escapeXml="true" length="50" needEllipsis="true"/></h3>
		<div class="file_info">
			<div class="file_info_l">
				<span></span>
				<jy:di key="${thesis.userId}" className="com.tmser.tr.uc.service.UserService" var="us"/>
				提交人：${us.name}
			</div>
			<div class="file_info_r">
				<span></span>
				提交日期：<fmt:formatDate value="${findOne.submitTime}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="file_down_btn">
			<input type="button" class="download" />
		</div>
		<div class="word_plug_ins">
			<iframe id="view"  width="100%"	height="660px;"style="border:none;" frameborder="0" scrolling="no"></iframe>
		</div>
		<div class="check_teacher_wrap2" style="margin-top:20px;">
			<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no"></iframe>
		</div>
	</div> 
	<div class="clear"></div>
</div>
	
	<script type="text/javascript">
		var resid = "${findOne.resId}";
		$("#view").attr("src","jy/scanResFile?to=true&resId="+resid);
		$(".download").click(function(){
			var name = "${findOne.thesisTitle}";
			window.open(_WEB_CONTEXT_+"/jy/manage/res/download/"+resid+"?filename="+encodeURI(name),"_self");
		});
		$("#checkedBox").attr("src","jy/check/lookCheckOption?flags=false&term=${findOne.schoolTerm}&title=<ui:sout value='${findOne.thesisTitle}' encodingURL='true' escapeXml='true'></ui:sout>&resType=10&authorId=${findOne.userId}&resId=${findOne.id}&titleShow=true");
	</script>
</body>
</html>
