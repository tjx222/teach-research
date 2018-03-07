<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#ktFileInput{float:left;}
#ktFileInput-queue{position: absolute;  left: 339px; top: -8px;}
</style>
<div class="pageContent" id="dao_ru"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form id="form" method="post" action="${ctx }/jy/back/zygl/yz/dRuZiYuan" class="pageForm required-validate"
		onsubmit="return validateCallback(this,yzresAjaxDone);">
		<input type="hidden" name="lessonId" value="${bc.chapterId }">
		<div class="pageFormContent" layoutH="66" >
		<input id="ktFileInput" type="file" name="file" 
			uploaderOption="{
				swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
				uploader:'${ctx }jy/manage/res/upload;jsessionid=<%=session.getId() %>',
				formData:{relativePath:'comres/${bc.comId}'},
				buttonText:'继续添加',
				fileSizeLimit:'50000KB', 
				itemTemplate:'<b></b>',
				uploadLimit:20, 
				fileTypeDesc:'*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.pdf;*.txt;*.zip;*.rar;*.jpg;*.jpeg;*.gif;*.png;*.mp3;*.mp4;*.wma;*.rm;*.rmvb;*.flv;*.swf;*.avi', 
				fileTypeExts:'*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.pdf;*.txt;*.zip;*.rar;*.jpg;*.jpeg;*.gif;*.png;*.mp3;*.mp4;*.wma;*.rm;*.rmvb;*.flv;*.swf;*.avi', 
				auto:true, 
				multi:true, 
				itemTemplate:'<b></b>',
				onUploadSuccess:upSuccess				
			}"
		/>
		
					<table class="list nowrap" id="preres_tb" width="100%">
						<thead>
							<tr align="center">
								<th align="center" type="enum" name="rcdVo[#index#].resType" enumUrl="${ctx }/jy/back/zygl/yz/checkLX" size="8">选择类型</th>
								<th align="center" type="attach" name="rcdVo[#index#].title" lookupGroup="rcdVo[#index#]" lookupPk="resId" size="12">资源名称</th>
								<th align="center" type="enum" name="rcdVo[#index#].qualify" enumUrl="${ctx }/jy/back/zygl/yz/lookSelect" size="8">是否为优质资源</th>
								<th align="center" type="text" name="rcdVo[#index#].sort" defaultVal="#index#" size="4" fieldClass="digits">输入显示顺序</th>
								<th align="center" type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
		</div>
		
		<div class="formBar" style="margin-top: 10px">
						<ul>
							<li>
								<div class="button">
									<div class="buttonContent" align="center" style="margin-right: 338px" >
										<button type="submit" >保存</button>
									</div>
								</div>
							</li>
						</ul>
		</div>

	</form>

</div>
<script type="text/javascript">
	function yzresAjaxDone(json){
				dialogAjaxDone(json);
				$.pdialog.closeCurrent();
				reloadShow_yzResources(); 
				
	}

	function upSuccess(file, data, response){
		var data = eval('(' + data + ')');
		var opts = {hasFile:true};
		opts.files=[{filename:file.name,resId:data.data}];
		$("#preres_tb").itemDetail(opts);
	}
</script>

