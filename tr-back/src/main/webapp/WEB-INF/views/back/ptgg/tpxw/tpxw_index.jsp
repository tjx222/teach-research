<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent">
<div class="tabs" style="margin-top: 5px;">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="${ctx}/jy/back/ptgg/tpxw/picturenewsList"  target="ajax" rel="content" id="ptxw_id"><span>图片新闻列表</span></a></li>
				<li><a href="${ctx}/jy/back/ptgg/tpxw/picturenewsDraft"  target="ajax" rel="content" id="ptxw_id2"><span>草稿箱(<strong id="picnumid" style="color: red ">${_count}</strong>)</span></a></li>			</ul>
		</div>
	</div>
	<div class="tabsContent" id="content">
		
	</div>
</div>
	
</div>
<script type="text/javascript">
var notice = {
		imgCount : 4,
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
		decreaseimg:function(res){//删除图片
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
			var images_del = $("#images_del").val();
			$("#images_del").val(res+"#"+images_del);
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
		reloadDraftCount:function(isreleaseList,isRelease){//tabs页动态数据
			var  strongcount = document.getElementById("strongid").innerHTML;
			//判断是否是草稿列表的操作isDraftList','isRelease'
			if(isreleaseList=="isreleaseList"){
				if(isRelease!="isRelease"){
					$("#strongid").text(Number(strongcount)+1);
				}
			}else{//草稿列表
				//是否是发布为草稿
				if(isRelease=="isRelease"){//发布，count-1
					$("#strongid").text(Number(strongcount)-1);
				}
			}
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
			$.post("${ctx}/jy/back/ptgg/tpxw/deleteImg",{imgId:res,isweb:true},null,"json");
			$(this).hide();
		}
	};
$(document).ready(function(){
	 notice._load("#ptxw_id");
});
</script>
