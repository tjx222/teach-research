<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<style>
#templateFileInput{float:left;}
#icoFileInput1{float:left;}
#templateFileInput-queue{position: absolute;  left: 339px; top: -8px;}
#icoFileInput-queue{position: absolute;  left: 129px; top: 39px;}
.pageFormContent_l{width:300px;float:left;}
.pageFormContent_l div{height:28px;}
.cancel{position: absolute;right: 0px;top: 3px;}
.uploadify-progress{display:none;}
</style>
</head>
<body>
<div  class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;height: 100%;">
	<form method="post" action="jy/back/xxsy/hfgg/saveHomeAds" class="pageForm required-validate" onsubmit="return beforeSubmit(this)">
		<input id="id" name="id" type="hidden" value="${schoolBannerdata.id}"/>
		<input type="hidden" name="orgId" value=${schoolBannerdata.orgId }>
		<div class="pageFormContent">
			<div class="pageFormContent1">
			</div> 
			<div style="clear:both;"></div>
			<div id="p_pic" style="margin-bottom:20px;width:379px;height:400px;position: relative;<c:if test="${!empty schoolBannerdata.id }">display: none;</c:if>">
				<label style="line-height:37px;width:60px;">上传图片：</label>
				<input style="width:300px" id="icoFileInput1" type="file" name="file" 
					uploaderOption="{
						swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
						uploader:'${ctx }jy/manage/res/upload',
						formData:{jsessionid:'<%=session.getId() %>',isWebRes:true,relativePath:'schoolbanner/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }'},
						buttonText:'&nbsp; 请添加长宽比为：6:1的图片 &nbsp;',
						fileSizeLimit:'1000KB',
						fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;*.bmp;',
						fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;*.bmp;',
						auto:true,
						multi:false,
						width:160,
						onUploadSuccess:uploadIco,
						removeCompleted:true,
					}"
				/>
				<input id="ico" type="hidden" name="attachs" value="${schoolBannerdata.attachs}"/>
				<input type="hidden" id="images_del" name="flags" value="">
				<script type="text/javascript">
					function uploadIco(file, data, response){
						var data = eval('(' + data + ')');
						$("#ico").val("");
						$("#ico").val(data.data);
						$("#picDiv").html("<img width='400' height='385' src='${ctx }jy/manage/res/download/"+data.data+"?isweb=true'><input type='button' onclick='deletePic();'/>");
						$("#picDiv").parent().show();
						$("#p_pic").hide();
					}
				</script>
			</div>
			<div <c:if test="${empty schoolBannerdata.id }">style="display: none;"</c:if> >
				<label style="line-height:33px;height:33px;">横幅广告：</label>
				<div id="picDiv">
					<ui:photo_del src="${picpath}" width='400' height='385' args="${schoolBannerdata.attachs}" function="deletePicEdit"></ui:photo_del>
				</div>
			</div> 
		</div>
		<div class="formBar">
			<button type="submit" style="width: 56px;height: 27px;margin-left:160px;"> 保存</button>
		</div>
	</form>
</div>
</body>
<script type="text/javascript">
function beforeSubmit(obj){
	var $form = $(obj);
	if (!$form.valid()) {
		return false;
	}
	if($("#ico").val()==""){
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
			success: viewSchoolBanner,
			error: DWZ.ajaxError
		});
	}
	_submitFn();
	return false;
}
var _submitFn = function(){
	$.ajax({
		async : false, 
		type: obj.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: afterSysUserAdd,
		error: DWZ.ajaxError
	});
}
function deletePic(){
	var imgId =$("#ico").val();
	$("#p_pic").show();
// 	$("#picDiv").hide();
	$("#picDiv").html("");
	$("#ico").val("");
	$(".uploadify-queue-item").hide();
	$.post("${ctx}/jy/back/xxsy/hfgg/deleteImg",{imgId:imgId,isweb:true},null,"json");
}
function deletePicEdit(imgid){
	$("#images_del").val(imgid);
	$("#p_pic").show();
	$("#"+imgid).hide();
	$("#"+imgid + "_btn" ).hide();
	$("#ico").val("");
	
}
function reload(json){
	$.pdialog.closeCurrent();
	parent.reloadhfgg();
	alertMsg.correct("操作成功");
}
function viewSchoolBanner(result){
	if(result.statusCode==200){
		if(confirm("已经发布成功，您是否需要去学校首页中查看效果")){
			window.open(result.message);
		}
	}else{
		alert(result.message);
	}
	reload();
}
</script>
