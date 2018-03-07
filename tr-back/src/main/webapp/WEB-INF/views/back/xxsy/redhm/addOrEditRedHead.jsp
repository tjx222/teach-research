<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<script type="text/javascript">
/* 	function dialogAjaxDone(json) {
	} */
</script>
</head>
<body>
	<div class="pageContent">

		<form method="post" action="${ctx }/jy/back/xxsy/redhm/saveRedHead"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<div class="pageFormContent" layoutH="58">

				<div class="unit">
					<label>红头标题：</label>
					<c:choose>
						<c:when test="${empty id}">
							<input type="text" name="title" size="40" maxlength="80"
								class="required" value="${title}" />
						</c:when>
						<c:otherwise>
							<input type="text" name="title" size="40" maxlength="80"
								class="required" value="${title}" />
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="orgId" value="${orgId}" />  <input
						type="hidden" name="id" value="${id}" />
				</div>

			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div></li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>

	</div>
</body>
</html>