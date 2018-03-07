<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid;">
	<form id="form" method="post" action="${ctx }/jy/back/zygl/yz/editResources" class="pageForm required-validate"
		onsubmit="return validateCallback(this,yzresEditAjaxDone);">

		<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="areaIds" value="">
			<p id="jsyc">
				<label>选择类别：</label>
				<select name="resType" id="selresType">
						<c:forEach items="${resourcesType }" var="resType">
								<option value="${resType.id }" ${resRecommend.resType == resType.id ? "selected='selected'" : '' }>${resType.name }</option>
						</c:forEach>
				</select>
			</p>
			
			<p>
				<label>上传附件：</label> 
				<input type="hidden" id="res_id" name="resId" value="${resRecommend.resId }">
				<p style="margin-left: 130px;margin-top: -10px">
				<input id="ktFileInput" type="file" name="file" 
					uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload;jsessionid=<%=session.getId() %>',
						formData:{relativePath:'comres/${resRecommend.bookId}'},
						buttonText:'请选择上传文件',
						fileSizeLimit:'8000KB', 
						uploadLimit:20, 
						fileTypeDesc:'*.doc;*.docx;', 
						fileTypeExts:'*.doc;*.docx;', 
						auto:true, 
						multi:true, 
						onUploadSuccess:upSuccess				
					}"
				/>  
				</p>
			</p>
			<p>
				<label>资源名称：</label> 
				<input type="hidden"  name="bookId" value="${resRecommend.bookId }">
				<input type="hidden" name="id" value="${resRecommend.id }">
				<input type="hidden" name="lessonId" value="${resRecommend.lessonId }">
				<input class="required " id="title_sc" name="title" type="text" size="36" maxlength="30" value="${resRecommend.title }"/><br>
			</p>
			
			<p>
				<label>优质资源：</label> 
				<select name="qualify" >
					<option value="1" ${resRecommend.qualify==1 ?"selected='selected'":"" }>是</option>
					<option value="0"  ${resRecommend.qualify==0 ?"selected='selected'":"" }>否</option>
				</select>
			</p>

			<p>
				<label>显示顺序：</label> 
				<input type="text" name="sort" maxlength="4" value="${resRecommend.sort }">
			</p>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="submit" >保存</button>
						</div>
					</div>

				</li>

				<li>
					<div class="button">
						<div class="buttonContent"> 
							<button type="Button" class="close">取消</button>
						</div>
					</div>

				</li>

			</ul>

		</div>

	</form>

</div>
<script type="text/javascript">
	function upSuccess(file, data, response){
		var data = eval('(' + data + ')');
		$("#res_id").val(data.data);
		$("#span_file").hide();
		$("#title_sc").val(file.name);
	}
	
	function yzresEditAjaxDone(json){
		dialogAjaxDone(json);
		$.pdialog.closeCurrent();
		reloadShow_yzResources(); 
}
</script>

