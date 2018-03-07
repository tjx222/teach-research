<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="jy/uc/login" class="pageForm" onsubmit="return validateCallback(this,loginDialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用户名：</label>
				<input type="text" name="username" size="30" class="required" minlength="4"/>
			</div>
			<div class="unit">
				<label>密码：</label>
				<input type="password" name="password" size="30" class="required"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	<script type="text/javascript">
	function loginDialogAjaxDone(json){
		dialogAjaxDone(json);
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			if($("#switchEnvBox").length > 0){
				var current = $("#switchEnvBox span:first").html();
				$("#switchEnvBox ul li").each(function(){
					var aele = $(this).find("a");
					console.log("current a select " + aele.html());
					if(aele.html() == current){
						console.log("current a url " + aele.attr("href"));
						$.get(aele.attr("href"));
						return false;
					}
				});
			}
		}
	}
	</script>
</div>
	