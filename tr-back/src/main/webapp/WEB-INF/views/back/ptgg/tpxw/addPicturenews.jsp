<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
.uploadify-queue {
	float: left;
	width: 600px;
}
</style>
<div class="pageContent">
	<form method="post" id="form1" action="${ctx }/jy/back/ptgg/tpxw/savePictureNews"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this, notice.reloadnoticeList);">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>标题：</label>
				<div>
					<textarea style="width: 590px" class="required" id="_title"
						name="title" maxlength="50" value="" cols="42" rows="3"></textarea>
				</div>
			</div>

			<div class="unit">
				<label>内容：</label>
				<div style="float: left;">
					<textarea maxlength="300" class="required editor"
						eidtorOption="{width:'100%',height:'200px'}" name="content"></textarea>
				</div>
			</div>
			<div class="unit">
				<label>图片上传：</label>
				<div class="uploadify-queue">
					<input style="width: 100px" id="noticeFileInput" type="file"
						name="file"
						uploaderOption="{
							swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }jy/manage/res/upload',
							formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'back_news/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
	 						buttonText:' 请选择图片 ', 
							fileSizeLimit:'5000KB',
							fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
							fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
							auto:true,
							multi:true,
							uploadLimit:1,
							onUploadStart:notice.uploadStart,
							onUploadSuccess:notice.uploadIco,
							removeCompleted : false			
						}" />
					<input type="hidden" id="isDrift" name="flag" value=""/>
					<input type="hidden" id="ztytRes" class="required" name="attachs" value=""/>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">发布</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit" onclick="javascript:notice.saveDrift();">存草稿</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>

