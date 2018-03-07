<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta charset="UTF-8">
<ui:htmlHeader title="我的备课本-同课题提交资源用户"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
</head>
<body style="background:#fff;">
	<div class="same_submission">
			<h3 class="same_submission_h3"><span></span>他们也提交了同课题${type == 0 ? '教案': type == 1? '课件':'反思' }：</h3><div class="clear"></div>
				<div class="same_submission_cont">
					<c:if test="${not empty datalist }">
						<c:forEach items="${datalist }" var="data">
							<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
							<div class="head_dl" data-planId="${data.planId }" style="cursor: pointer">
								<dl>
									<dd>
										<ui:photo src="${u.photo}" width="60" height="65"></ui:photo>
										<span class="have_access1"></span>
									</dd>
									<dt>
										<span class="head_name">${u.name}</span> 
									</dt>
								</dl>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty datalist }">
						<div class="empty_h">
							<div class="check_k"></div>
							<div class="empty_h_ine">其他人还没有提交哟!</div>
						</div>
					</c:if>
				</div>
		</div>
</body>
<ui:require module="myplanbook/js"></ui:require>
<script type="text/javascript">
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','myplanbook'],function(){
	$(".head_dl").click(function(){
		var planId=jq(this).attr("data-planId");
		window.open(_WEB_CONTEXT_+"/jy/myplanbook/viewOtherLesson?planId="+planId);
	})
});
</script>
</html>