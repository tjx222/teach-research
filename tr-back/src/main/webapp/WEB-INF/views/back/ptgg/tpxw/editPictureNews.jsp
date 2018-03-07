<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	.uploadify-queue{float:left;width:600px;}
</style>
<div class="pageContent">
	<form method="post"
		action="${ctx }/jy/back/xxsy/tpxw/updatePictureNews" id="form1"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this, notice.viewIndex);">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>标题：</label>
				<div>
					<textarea id="_title" required="required" style="width: 590px"
						name="title" maxlength="50" value="" cols="42" rows="3">${pictureNews.title}</textarea>
				</div>
			</div>
			<div class="unit">
				<label>内容：</label>
				<div style="float: left;">
					<textarea class="required editor" maxlength="300"
						eidtorOption="{width:'100%',height:'180px'}" name="content">${pictureNews.content}</textarea>
				</div>
			</div>
			<div class="unit">
				<label>图片上传</label>
					<input type="hidden" id="id" name="id" value="${pictureNews.id}">
					<input type="hidden" id="ztytRes" required="required" name="attachs" value="${pictureNews.attachs}">
					<input type="hidden" id="isDrift" name="flag" value="">
					<input type="hidden" id="images_del" name="flags" value="">
				<div class="uploadify-queue">
					<div>
						<c:if test="${imgs ne null}">
							<c:forEach items="${imgs}" var="imgObj">
								<ui:photo_del src="${imgObj.value}"
									function="notice.decreaseimg" args="${imgObj.key}" width="60"
									height="60" />
							</c:forEach>
						</c:if>
					</div>
					<div id="p_pic">
						<input style="width: 100px" id="noticeFileInput" type="file"
							class="required" name="file"
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
						onUploadStart:notice.uploadStart,
						onUploadSuccess:notice.uploadIco,
						removeCompleted : false			
					}" />
					</div>
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
<script type="text/javascript">
$(function(){/*编辑时，计算已上传数量*/
		var _attachs = $("#ztytRes").val();
		if(null!=_attachs&&""!=_attachs){
			notice.imgCount =  0;
			if(notice.imgCount<=0){
				notice.imgCount = 0;
				$("#p_pic").hide();
			}
		}
});
</script>
