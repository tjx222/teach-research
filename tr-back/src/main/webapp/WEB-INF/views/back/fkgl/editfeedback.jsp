<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head></head>
<script type="text/javascript">
	var feedback = {
		imgCount : 4,
		uploadIco : function(file, data, response) {
			//维护附件id
			feedback.uploadIds(data);
			var data = eval('(' + data + ')');
			var _data = data.data;
			//为每一个删除按钮注册一个删除事件
			var cancel = $("#" + file.id + " .cancel a");
			if (cancel) {
				cancel.on('click', function() {
					$('#feedbackFileInput').uploadify('settings', 'uploadLimit',
							++feedback.imgCount);
					var images = $("#ztytRes").val();
					var imagesreplaced = "";
					imagesreplaced=images.replace(_data+",", '');//判断是否是，结尾
					if(imagesreplaced==images){
						imagesreplaced = images.replace(_data, '');
					}else{
						imagesreplaced = images.replace(_data+",", '');
					}
					$("#ztytRes").val(imagesreplaced);
					$(this).hide();
				});
			}
		},
		uploadStart : function(file) {
			//通过uploadify的settings方式重置上传限制数量
			$('#feedbackFileInput').uploadify('settings', 'uploadLimit',
					feedback.imgCount);
		},

		uploadIds : function(data) {//维护附件id
			var data = eval('(' + data + ')');
			var res = data.data;
			var resval = $('#ztytRes').val();
			var resstring = "";
			if (resval != null && resval != "") {
				//判断是否是,结尾
				var strs=resval.substr(resval.length-1,resval.length-1);
				if(strs!=","){
					resstring = $('#ztytRes').val() + "," + res;
				}else{
					resstring = $('#ztytRes').val() + res;
				}
			} else {
				resstring = data.data;
			}
			$('#ztytRes').val(resstring);
		},
		reloadfeedbackList : function(json) {
			//刷新页面
			DWZ.ajaxDone(json);
			if (json.statusCode == DWZ.statusCode.ok) {
				if ("closeCurrent" == json.callbackType) {
					$.pdialog.closeCurrent();
					alertMsg.correct("回复成功");
					parent.reloadfkgl();
				}
			}
		},
	};
	
</script>
<body>
	<div class="pageContent">
		<form method="post" action="${ctx }/jy/back/fkgl/savefeedback"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, feedback.reloadfeedbackList);">
			<div class="pageFormContent" layoutH="56">
				<div id="p_pic" style="float: left; width: 579px; margin-top: 10px;">
					<label style="width: 70px; line-height: 37px;">图片上传：</label>
					<div style="float: left">
						<input style="width: 100px" id="feedbackFileInput" type="file"
							name="file"
							uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:false,relativePath:'feedbackPic/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
 						buttonText:' 请选择图片 ', 
						fileSizeLimit:'512000KB',
						fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
						fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
						auto:false,
						multi:true,
						onUploadSuccess:feedback.uploadIco,
						onUploadStart:feedback.uploadStart,
						removeCompleted : false			
					}" />
						<input type="button"
							onclick="$('#feedbackFileInput').uploadify('upload', '*');"
							value="上传"
							style="float: left; position: absolute; left: 200px; top: 26px; margin: -6px 0 0 20px; width: 56px; height: 27px;" />
					</div>
					<input type="hidden" id="ztytRes" name="attachment1">
					<input type="hidden" name="pid" value="${pid}">
				</div>
				<div  style="float: left;">
				<p>
					<label>回复信息：</label>
					<textarea cols=50 rows="10" name="content" maxlength="500"></textarea>
					<!-- 				<input name="name" class="required" type="text" size="30" value="张慧华" alt="请输入客户名称"/> -->
				</p>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
