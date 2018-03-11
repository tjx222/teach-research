<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="邀请加入校际教研圈"></ui:htmlHeader>
	<script type="text/javascript">
	 	//是否接受邀请
	 	function isAccept(is_accept,circleId){
	 		var noticeId = parent.getNoticeId();
	 		if(noticeId!=null && parent.getNoticeId()!=""){
	 			var confirmStr = "";
		 		if(is_accept == "2"){
		 			confirmStr = "您确定接受邀请吗？";
		 			$("#content").val($("#jsContent").html());
		 		}else{
		 			confirmStr = "您确定拒绝邀请吗？";
		 			$("#content").val($("#jjContent").html());
		 		}
		 		if(confirm(confirmStr)){
		 			$("#state").val(is_accept);$("#stcId").val(circleId);
		 			$("#noticeId").val(noticeId);
			 		$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/saveYaoQing.json",
						data:$("#hiddenForm").serialize(),
						success:function(data){
							if(data.isSuccess){
								$("#yq_d").css("display","none");
								if(is_accept == "2"){
						 			$("#jsContent").css("display","");
						 			alert("您成功接受了邀请！");
						 		}else{
						 			$("#jjContent").css("display","");
						 			alert("您已拒绝了邀请！");
						 		}
							}else{
								alert("操作失败！");
							}
						}
					});
		 		}
	 		}else{
	 			alert("系统忙，请稍后再试！");
	 		}
	 		
	 	}
	</script>
	<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/teach_circle.css" media="screen">
</head>
<body>
	<form id="hiddenForm" action="" method="post">
		<input type="hidden" id="state" name="state">
		<input type="hidden" id="stcId" name="stcId">
		<input type="hidden" id="noticeId" name="noticeId">
		<input type="hidden" id="content" name="content">
	</form>
	<div id="yq_d" style="font-size: 16px;line-height: 25px;text-align:center;">
		${org.name }${userSpace.spaceName }“${userSpace.username }”邀请贵校加入“${stc.name }”校际教研圈，您是否接受邀请？
		<br/><br/><br/>
		<div class="btn_t">
			<input type="button" class="btn_jieshou" onclick="isAccept('2','${stc.id }')">
			<input type="button" class="btn_jujue" onclick="isAccept('3','${stc.id }')">
		</div>
	</div>
	<div id="jsContent" style="display: none;">
		<div style="font-size: 16px;line-height: 25px;text-align:center;" >
			您已接受了${org.name }${userSpace.spaceName }“${userSpace.username }”邀请加入“${stc.name }”校际教研圈的请求！
		</div>
	</div>
	<div id="jjContent" style="display: none;">
		<div style="font-size: 16px;line-height: 25px;text-align:center;">
			您已拒绝了${org.name }${userSpace.spaceName }“${userSpace.username }”邀请加入“${stc.name }”校际教研圈的请求！
		</div>
	</div>
</body>
</html>