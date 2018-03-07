<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent">
<div class="tabs" style="margin-top: 5px;">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
		<ul>
				<li><a href="${ctx}/jy/back/xxsy/tpxw/picturenewsList?orgId=${orgId}"  target="ajax" rel="contentindex" id="ptxwindex_id"><span>图片新闻列表</span></a></li>
				<li><a href="${ctx}/jy/back/xxsy/tpxw/picturenewsDraft?orgId=${orgId}"  target="ajax" rel="contentindex" id="ptxw_id2"><span>草稿箱(<strong id="picnumid" style="color: red ">${_count}</strong>)</span></a></li>			
		</ul>
		</div>
	</div>
	<div class="tabsContent" id="contentindex">
	</div>
</div>
</div>
<script type="text/javascript">
var notice = {
		imgCount : 1,
		uploadIco : function(file, data, response) {
			//维护附件id
			notice.uploadIds(data);
			var data = eval('(' + data + ')');
			var res = data.data;
			//如果上传成功之后，notice.Imgcount=最大值，隐藏上传框
			//为每一个删除按钮注册一个删除事件
			var cancel = $("#" + file.id + " .cancel a");
			if (cancel) {
				cancel.on('click', function() {
					notice.deleteimg(res);
				});
			}
		},
		uploadStart : function(file) {
			//通过uploadify的settings方式重置上传限制数量
			$('#noticeFileInput').uploadify('settings', 'uploadLimit',
					notice.imgCount);
		},
		uploadIds : function(data) {//维护附件id
			var data = eval('(' + data + ')');
			var res = data.data;
			var resval = $('#ztytRes').val();
			var resstring = "";
			if (resval != null && resval != "") {
				//判断是否是#结尾
				var strs=resval.substr(resval.length-1,resval.length-1);
				if(strs!="#"){
				resstring = $('#ztytRes').val() + "#" + res;
				}else{
					resstring = $('#ztytRes').val() + res;
				}
			} else {
				resstring = data.data;
			}
			$('#ztytRes').val(resstring);
		},
		decreaseimg:function(res){//删除图片(编辑)
			$("#p_pic").show();
			$('#noticeFileInput').uploadify('settings', 'uploadLimit',
					++notice.imgCount);
			var images = $("#ztytRes").val();
			var imagesreplaced = "";
			imagesreplaced=images.replace(res+"#", '');//判断是否是#结尾
			if(imagesreplaced==images){
				imagesreplaced = images.replace(res, '');
			}else{
				imagesreplaced = images.replace(res+"#", '');
			}
			var images_del = $("#images_del").val();
			$("#images_del").val(res+"#"+images_del);
			$("#ztytRes").val(imagesreplaced);
			$("#"+res).hide();
			$("#"+res + "_btn" ).hide();
		},
		saveDrift:function(){
			$("#isDrift").val("true");
			return true;
		},
		_load:function(id){
			var $this = $(id);
			var rel = $this.attr("rel");
			if (rel) {
				var $rel = $("#"+rel);
				$rel.loadUrl($this.attr("href"), {}, function(){
					$rel.find("[layoutH]").layoutH();
				});
			}
		},
		reloadnoticeList : function(json) {
			DWZ.ajaxDone(json);
			if (json.statusCode == DWZ.statusCode.ok) {
				if ("closeCurrent" == json.callbackType) {
					$.pdialog.closeCurrent();
					parent.reloadtpxw();
				}
			}
		},
		viewIndex:function(result){
			if(result.statusCode==200&&result.rel==1){
				if(confirm("已经发布成功，您是否需要去学校首页中查看效果")){
					window.open(result.message);
				}
			}else{
				alert("操作成功");
			}
			$.pdialog.closeCurrent();
			parent.reloadtpxw();
		},
		release:function(result){
			if(result.statusCode==200&&result.rel==1){
				if(confirm("已经发布成功，您是否需要去学校首页中查看效果")){
					window.open(result.message);
				}
			}else{
				alert(result.message);
			}
			reloadtpxw();
		},
		deleteimg:function(res){
			$("#p_pic").show();
			$('#noticeFileInput').uploadify('settings', 'uploadLimit',
					++notice.imgCount);
			var images = $("#ztytRes").val();
			var imagesreplaced = "";
			imagesreplaced=images.replace(res+"#", '');//判断是否是#结尾
			if(imagesreplaced==images){
				imagesreplaced = images.replace(res, '');
			}else{
				imagesreplaced = images.replace(res+"#", '');
			}
			$("#ztytRes").val(imagesreplaced);
			$.post("${ctx}/jy/back/xxsy/tpxw/deleteImg",{imgId:res,isweb:true},null,"json");
			$(this).hide();
		}
	};
$(document).ready(function(){
	 notice._load("#ptxwindex_id");
});
</script>
