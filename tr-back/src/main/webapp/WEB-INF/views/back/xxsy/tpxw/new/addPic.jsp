<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#FileInput{float:left;}
#FileInput-queue{position: absolute;  left: 339px; top: -8px;}
</style>
<div class="pageContent" id="addPicId"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form id="form" method="post" action="${ctx }/jy/back/xxsy/tpxw/savePictureNews?orgId=${orgId}" class="pageForm required-validate"
		onsubmit="return validateCallback(this,notice.viewIndex);">
		<input type="hidden" id="isDrift" name="flago" value="">
		<div class="pageFormContent" layoutH="66" >
		<div class="unit">
			<label>标题：</label> <input type="text" maxlength="50" name="title" />
		</div>
		<div class="unit">
			<label>是否置顶：</label> <input type="checkbox" value="1"  name="istop" />
		</div>
		<input  id="FileInput" type="file" name="file" 
			uploaderOption="{
				swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
				uploader:'${ctx }jy/manage/res/upload;jsessionid=<%=session.getId() %>',
				formData:{isWebRes:true,relativePath:'picnews/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
				buttonText:'添加图片',
				fileSizeLimit:'50000KB', 
				itemTemplate:'<b></b>',
				uploadLimit:20, 
				fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;*.bmp;', 
				fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;*.bmp;', 
				auto:true, 
				multi:true, 
				itemTemplate:'<b></b>',
				onUploadSuccess:upSuccess				
			}"
		/>
					<table class="list nowrap" id="picNews_tb" width="100%">
						<thead>
							<tr align="center">
								<th align="center"  type="attach" name="rcdVo[#index#].title" lookupGroup="rcdVo[#index#]" lookupPk="resId" size="15">图片名称</th>
								<th align="center"  type="text"  name="rcdVo[#index#].content" size="80" fieldClass="required">内容</th>
								<th align="center"  type="text" name="rcdVo[#index#].sort" size="5" fieldClass="digits">显示顺序</th>
								<th align="center"  type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
		</div>
		<div class="formBar" style="margin-top: 10px">
						<ul>
							<li>
								<div class="button">
									<div class="buttonContent" align="center" >
										<button type="submit" >发布</button>
									</div>
								</div>
								<div class="button">
									<div class="buttonContent" align="center" >
										<button type="submit" onclick="javascript:notice.saveDrift();" >存草稿</button>
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
		var opts = {hasFile:true};
		opts.files=[{filename:file.name,resId:data.data}];
		$("#picNews_tb").itemDetail(opts);
	}
	function saveDrift(){
		alert("闭包");
		$("#isDrift").val("true");
		return true;
	}
</script>

