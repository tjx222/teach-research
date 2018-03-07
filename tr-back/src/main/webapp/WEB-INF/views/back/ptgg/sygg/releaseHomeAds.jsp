<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
#icoFileInput1{float:left;}
#icoFileInput2{float:left;}
#templateFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#icoFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#picDiv1 input{
	width:10px;
	height:10px;
	background:url("${ctxStatic}/modules/back/images/del.png") no-repeat;
	cursor: pointer;
	border:none;
	margin-left: 4px;
  	position: absolute;
}
#picDiv2 input{
	width:10px;
	height:10px;
	background:url("${ctxStatic}/modules/back/images/del.png") no-repeat;
	cursor: pointer;
	border:none;
	margin-left: 4px;
  	position: absolute;
}
</style>
<div class="pageContent"  style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;width: 600px;height: 480px;">
	<form method="post" action="jy/back/ptgg/sygg/saveHomeAds" class="pageForm required-validate" onsubmit="return beforeSubmit(this)">
	<input id="id" name="id" type="hidden" value="${flat.id}"/>		
		<div class="pageFormContent">
			<div id="p_pic1" style="width:579px;height: 200px;margin-top:10px;position: relative;<c:if test="${!empty flat.pictureid}">display: none;</c:if>" >
				<label style="line-height:37px;">1、添加首页图片<span style="color: red">*</span>：</label>
				<input style="width:300px" id="icoFileInput1" type="file" name="file" 
					uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'front_index_ads/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
						buttonText:'请选择图片', 
						fileSizeLimit:'1000KB',
						fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
						fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
						auto:false,
						multi:false,
						onUploadSuccess:uploadIco1			
					}"
				/>
				<input id="ico1" type="hidden" name="pictureid" value="${flat.pictureid }"/>
				<input type="button" onclick="$('#icoFileInput1').uploadify('upload', '*');" value="上传" style="float:left;position: absolute;left: 249px; top: 8px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/>
				<script type="text/javascript">
					function uploadIco1(file, data, response){
						var data = eval('(' + data + ')');
						var _html = "<img width='100' height='100' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><input type='button' data_resid="+data.data+" onclick='deletePic1(this,1);'/>";
						$("#ico1").val(data.data);
						$("#picDiv1").html(_html);
						$("#picDiv1").parent().show();
						$("#p_pic1").hide();
					}
				</script>
			</div>
			<div <c:if test="${empty flat.pictureid}">style="display: none;" </c:if> >
				<label style="line-height:33px;height:33px;">首页大图：</label>
				<div id="picDiv1">
					<img width='100' height='100' src='${ctx }jy/manage/res/download/${flat.pictureid}?isweb=true'><input type='button' data_resid="${flat.pictureid}" onclick='deletePic1(this,"update");'/>
				</div>
			</div>
			<input id="del_id" type="hidden" value="" name="flags">
			<div id="p_pic2" style="width:579px;margin-top:10px;height:200px;position: relative;<c:if test="${!empty flat.littlepictureId}">display: none;</c:if>" >
				<label style="line-height:37px;">2、添加缩小图片<span style="color: red">*</span>：</label>
				<input style="width:300px" id="icoFileInput2" type="file" name="file" 
				uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'front_index_ads/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
						buttonText:'请选择图片',
						fileSizeLimit:'1000KB',
						fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
						fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
						auto:false,
						multi:false,
						onUploadSuccess:uploadIco2			
					}"
				/>
				<input id="ico2" type="hidden" name="littlepictureId" value="${flat.littlepictureId }"/>
				<input type="button" onclick="$('#icoFileInput2').uploadify('upload', '*');" value="上传" style="float:left;position: absolute;left: 249px; top: 8px;margin: -6px 0 0 20px;width: 56px; height: 27px;"/>
				<script type="text/javascript">
					function uploadIco2(file, data, response){
						var data = eval('(' + data + ')');
						$("#ico2").val(data.data);
						$("#picDiv2").html("<img width='100' height='100' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><input type='button' data_resid="+data.data+" onclick='deletePic2(this,1);'/>");
						$("#picDiv2").parent().show();
						$("#p_pic2").hide();
					}
				</script>
			</div>
			<div <c:if test="${empty flat.littlepictureId}">style="display: none;" </c:if> >
				<label style="line-height:33px;height:33px;">首页小图：</label>
				<div id="picDiv2">
					<img width='100' height='100' src='${ctx }jy/manage/res/download/${flat.littlepictureId}?isweb=true'><input type='button' data_resid="${flat.littlepictureId}" onclick='deletePic2(this,"update");'/>
				</div>
			</div>
		</div>
		<div class="formBar">
			<button type="submit" style="width: 56px;height: 27px;margin-left:190px;"> 发布</button>
		</div>
	</form>
</div>
<script type="text/javascript">
function beforeSubmit(obj){
	var $form = $(obj);
	if (!$form.valid()) {
		return false;
	}
	if($("#ico1").val()==""||$("#ico2").val()==""){
		alert("请上传图片");
		return false;
	}
	var _submitFn = function(){
		$.ajax({
			type: obj.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: reload,
			error: DWZ.ajaxError
		});
	}
	_submitFn();
	return false;
}
function reload(json){
	$.pdialog.closeCurrent();
	parent.reloadsygg();
	alertMsg.correct("发布成功");
}
function deletePic1(obj,flag){
	var imageId =$(obj).attr("data_resid");
	if(flag!="update"){
		$.post("${ctx}/jy/back/xxsy/hfgg/deleteImg",{"imgId":imageId,isweb:true},null,"json");
	}else{
		update_del(imageId);
	}
	$("#p_pic1").show();
	$(obj).parent().parent().hide();
	$("#ico1").val("");
}
function deletePic2(obj,flag){
	var imageId =$(obj).attr("data_resid");
	if(flag!="update"){
			$.post("${ctx}/jy/back/xxsy/hfgg/deleteImg",{"imgId":imageId,isweb:true},null,"json");
		}else{
			update_del(imageId);
		}
	$("#p_pic2").show();
	$(obj).parent().parent().hide();
	$("#ico2").val("");
}
function update_del(resId){
	var oldId =  $("#del_id").val();
	$("#del_id").val(resId+","+oldId);
}
</script>
