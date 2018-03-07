<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent">
			<form method="post" id="form1"
				action="${ctx }/jy/back/xxsy/tzgg/saveNotice"
				class="pageForm required-validate"
				onsubmit="return iframeCallback(this, notice.viewIndex);">
				<div class="pageFormContent" layoutH="58">
					<div class="unit">
						<label>标题：</label>
						<div>
							<textarea style="width: 580px;" id="_title" name="title"
								 maxlength="200" class="required" value="" cols="42"
								rows="3"></textarea>
						</div>
					</div>
					<div class="unit">
						<div>
							<input id="isDisplay" type="checkbox" name="isDisplay" value="1" />是否在首页显示
						</div>
					</div>
					<div class="unit">
						<label>内容：</label>
						<div style="float: left;">
							<textarea maxlength="5000" class="editor required"
								eidtorOption="{width:'100%',height:'200px'}" name="content"
								style="float: left;"></textarea>
						</div>
					</div>

					<div class="unit">
						<label>附件上传：</label>
						<div class="uploadify-queue">
							<div id="p_pic">
								<input style="width: 100px" id="noticeFileInput" type="file"
									name="file"
									uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:false,relativePath:'notice_back_pic/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
 						buttonText:' 请选择附件 ', 
						fileSizeLimit:'5000KB',
						auto:true,
						multi:true,
						uploadLimit:8,
						onUploadStart:notice.uploadStart,
						onUploadSuccess:notice.uploadIco,
						removeCompleted : false			
					}" />
							</div>
						</div>
						<input type="hidden" id="ztytRes" name="attachs" value="">
					<input type="hidden" id="isDrift" name="flag" value="">
					<input type="hidden" id="orgId" name="orgId" value="${orgId}">
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
	<style>
.uploadify-queue {
	float: left;
	width: 600px;
}
</style>
