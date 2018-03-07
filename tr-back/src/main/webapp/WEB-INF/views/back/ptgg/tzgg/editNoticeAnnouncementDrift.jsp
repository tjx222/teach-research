<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head></head>
<script type="text/javascript">
	$(function() {/*编辑时，计算已上传数量*/
		var strArrA = new Array();
		var _attachs = $("#ztytRes").val();
		strArr = _attachs.split("#");
		if (null != strArr && "" != strArr) {
			notice.imgCount = 8 - strArr.length;
			if (notice.imgCount <= 0) {
				notice.imgCount = 0;
				$("#p_pic").hide();
			}
		}
	});
</script>
<body>
	<div class="pageContent">
		<form method="post" action="${ctx }/jy/back/ptgg/tzgg/editNoticeDrift"
			id="form1" class="pageForm required-validate"
			onsubmit="return iframeCallback(this, notice.reloadnoticeList);">

			<div class="unit" style="float: inherit;" >		   
				    <div style="float: left;margin-top: 10px;" ><label>标题：</label></div>
				    <textarea style="width: 580px;" id="_title"  name="title" size="40" maxlength="200" 
						   class="required" cols="42" rows="3">${jyAnnunciate.title}</textarea>
				</div>
				<div class="unit" style="float: inherit;"><br>		   
				    <div>
				    <input id="isDisplay" type="checkbox" name="isDisplay" value="1" <c:if test="${jyAnnunciate.isDisplay eq 1 }">checked="checked"</c:if>  ></input>是否在首页显示
				    </div>
				</div>
				<div class="unit" style="float: inherit;margin-top: 20px;"><br>		   
				    <label style="float: left;">内容：</label>
				    <div  style="float:left;">	
				    	<textarea maxlength="5000"  class="editor required" eidtorOption="{width:'100%',height:'200px'}" name="content" style="float: left;" >${jyAnnunciate.content}</textarea>
				    </div>
				</div>
			<div class="unit">
				<label></label>
				<div style="float: left; width: 640px; height: 200px; overflow: auto;">
					<div style="width: 300px;">
					<c:if test="${rList!='[null]'}">
						<c:forEach items="${rList}" var="res">
							<tr value="${res.id}">
								<td title="${res.name}.${res.ext }">
								<ui:attach isdel="true" filename="${res.name}.${res.ext }" function="notice.decreaseimg" args="${res.id}"></ui:attach></td>
							</tr>
						</c:forEach>
						</c:if>
					</div>
					<div class="pageFormContent" layoutH="40">
						<div id="p_pic"
							style="float: left; width: 579px; width: 600px;">
							<label style="width: 120px; line-height: 20px;">附件上传(最多8个)：</label>
							<div
								style="float: left; width: 600px; height: 200px; overflow: auto;">
								<input style="width: 100px" id="noticeFileInput" type="file"
									name="file"
									uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:false,relativePath:'notice_backPic/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
 						buttonText:' 请选择附件 ', 
						fileSizeLimit:'5000KB',
						auto:true,
						multi:true,
						onUploadStart:notice.uploadStart,
						onUploadSuccess:notice.uploadIco,
						removeCompleted : false			
					}" />
							</div>
							<input type="hidden" id="id" name="id" value="${jyAnnunciate.id}">
							<input type="hidden" id="ztytRes" name="attachs"value="${jyAnnunciate.attachs}"> 
							<input type="hidden"id="isDrift" name="flag" value="">
							<input type="hidden" id="images_del" name="flags" value="">
						</div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button">
								<div class="buttonContent">
									<button type="submit">发布</button>								</div>
							</div></li>
						<li><div class="button">
								<div class="buttonContent">
									<button type="submit" onclick="javascript:notice.saveDrift();">存草稿</button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
</body>
