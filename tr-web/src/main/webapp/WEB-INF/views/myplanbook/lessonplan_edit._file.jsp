<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<ui:htmlHeader title=""></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/edit.css">
<body style="background:#fff">
	<div id="folderbox2" class="saved_successfully1" style="z-index: -2;height:150px">
		<div class="saved_successfully_wrap1" style="z-index: 9002">
			<div class="saved_bottom" style="background: #fff;">
				<div class="route">
					<h5>上传教案：</h5>
					<div id="fileuploadContainer" class="courseware_title_p scfj_to">
						<span class="courseware_title_p_span">*</span> <label for=""></label>
						<ui:upload containerID="fileuploadContainer" fileType="doc,docx"
							fileSize="50" startElementId="save"
							beforeupload="checksaveLesson" callback="callbacksaveLesson"
							name="resId"
							relativePath="writelessonplan/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload>
					</div>
					<span class="kejian_tip">您可以上传doc,docx格式的文件</span>

					<div class="btn_bottom" style="margin-top: 20px;">
						<p id="save_b_btn">
							<input id="save" type="button" class="save_btn" value='上传'>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
//校验
function checksaveLesson(){
	//直接返回
	return true;
}
function callbacksaveLesson(data){
	var data = {
			"resourceId": data.data,
			"planId":$("#plan_id",parent.document).val(),
	}
	console.log(data);
	//刷新页面
	$.ajax({
		type : 'POST',
		dataType : "json",
		data :data,
		url : _WEB_CONTEXT_ + "/jy/updateLessonPlanWithResId",
		success : function(data) {
			if (data.code == 1) {
				// window.location.reload();
				 window.parent.location.reload();
			} else {
				alert('操作失败,请刷新重试');
			}
		}
	});
}
</script>
</html>