<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="课程表"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/school/tch/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css"
	media="screen">
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript"> 
<c:if test="${not empty close}">
$(parent.document).find(".dialog_close").trigger("click");
</c:if>
	$(document).ready(function() {
		$('.btn_bottom_2').click(function() {
			$(parent.document).find(".dialog_close").trigger("click");
		});
	});
	$(function() {
		$("#curriculum").validationEngine('attach',{
			scroll : false
		});
	});
	$(document).ready(function(){
		$("#imgs").on("click", "img", function(){
			parent.opencal();
		  });
	});
</script>
</head>
<body>
<form id="curriculum" action="jy/saveCurriculum" method="post">
    <ui:token></ui:token>
	<div class="table_curriculum">
			<table border="1" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th></th>
						<th>一</th>
						<th>二</th>
						<th>三</th>
						<th>四</th>
						<th>五</th>
						<th>六</th>
						<th>日</th>
					</tr>
					<c:forEach begin="1" end="10" step="1" var="status">
						<tr>
							<td style="border-top: 0px; border-bottom: 0px;"></td>
							<c:forEach begin="1" end="7" step="1" var="status2">
								<td><input type="text" class="validate[maxSize[50]]" maxlength="50"
									name="curList[${7*(status-1)+status2-1 }].content"
									value="${map[fn:trim(status2)][fn:trim(status)].content}" />
									 <input	type="hidden" name="curList[${7*(status-1)+status2-1 }].lesson"
									value="${status}" />
									 <input type="hidden"
									name="curList[${7*(status-1)+status2-1 }].week" value="${status2}" />
									<input type="hidden"
									name="curList[${7*(status-1)+status2-1 }].id" value="${map[fn:trim(status2)][fn:trim(status)].id}" />
								</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		<div class="mo">
			上<br />午
		</div>
		<div class="aft">
			下<br />午
		</div>
		<div class="ev">
			晚<br />上
		</div>
		<span>注：点击输入相应的排课信息</span>
		<div class="btn_bottom">
			<input type="submit" class="btn_bottom_1" value="保存"> <input
				type="button" class="btn_bottom_2"  value="暂不修改">
		</div>
	</div>
	</form>
</body>
</html>