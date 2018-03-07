/**
 * 同伴互助详情js
 */
define([ 'jquery', 'editor' ], function() {
	require("editor");
	var jq = require("jquery");
	var editor;
	jq(function() {
		webEditorOptions = {
			width : "1078px",
			height : "140px",
			style : 0,
			afterChange : function() {
				var num = this.count('text');
				if (num <= 500) {
					jq('#w_count').html(num);
				} else {
					jq('#w_count').html('<font color="red">' + num + '</font>');
				}
			}
		};
		editor = jq("#messageInput").editor(webEditorOptions)[0];
		editor.text("");
	});
	jq('#confirm').click(function() {
		if (editor.text() == '') {
			jq('#add_sava').find(".info").html("请填写留言");
			jq("#add_sava").dialog({
				width : 400,
				height : 200
			});
			return;
		}
		if (editor.count('text') > 500) {
			jq('#add_sava').find(".info").html("您最多可以输入500个字！");
			jq("#add_sava").dialog({
				width : 400,
				height : 200
			});
			return;
		}
		editor.sync();
		jq.post('./jy/companion/messages', $('#messageForm').serialize(), function(data) {
			if (data.result.code == 1) {
				location.reload();
			} else {
				jq('#add_sava').find(".info").html("发送消息失败！");
				jq("#add_sava").dialog({
					width : 400,
					height : 200
				});
			}
		}, "json");
	});
	jq('.addFriend').click(function() {
		var self = this;
		var userIdCompanion = jq(this).attr('data-userIdCompanion');
		var url = './jy/companion/friends/' + userIdCompanion;
		jq.post(url, {}, function(result) {
			if(result.result.code==1){
				jq('#add_sava').find(".info").html('恭喜您，关注成功！');
				jq(".dialog_close").click(function(){
					location.reload();
				});
			}else{
				jq('#add_sava').find(".info").html('添加好友失败：'+result.result.errorMsg);
			}
			jq("#add_sava").dialog({
				width : 400,
				height : 200
			});
		}, 'json');
	});
	jq('.ygz').click(function(event) {
		var userIdCompanion = jq(this).attr('data-userIdCompanion');
		var userNameCompanion = jq(this).attr('data-userNameCompanion');
		var url = "./jy/companion/friends/" + userIdCompanion + ".json";
		jq("#cancel_attention").find(".info1").html("你确定要取消关注“" + userNameCompanion + "”吗?");
		jq("#cancel_attention").dialog({
			width : 350,
			height : 200
		});
		jq(".ascertain").click(function() {
			jq(".dialog_close").trigger("click");
			jq.ajax(url, {
				dataType : 'json',
				type : 'delete',
				success : function(result) {
					if (result.result.code == 1) {
						jq('#add_sava').find(".info").html("取消关注成功！");
						jq(".dialog_close").click(function(){
							location.reload();
						});
					} else {
						jq('#add_sava').find(".info").html(result.result.msg);
					}
					jq("#add_sava").dialog({
						width : 400,
						height : 200
					});
				}
			});
		});
		event.stopPropagation();// 阻止事件冒泡
	});
	jq('#attachViews').delegate('.removeAttachment', 'click', function() {
		fileNum--;
		var viewId = $(this).attr('data-viewId');
		$('#' + viewId).remove();
	});
});

/** 上传文件个数* */
var fileNum = 0;
var beforeupload = function() {
	// 最多只能上传三个文件
	if (fileNum >= 3) {
		$('#add_sava').find(".info").html("最多只能上传3个文件！");
		$("#add_sava").dialog({
			width : 400,
			height : 200
		});
		return false;
	}
	return true;
}

function savePsCallBack() {
	fileNum++;
	var contentFileName = $('#uploadFile').val();
	var contentFileId = $('#hiddenFileId').val();
	$("#originFileName").val('');
	$("#hiddenFileId").val('');
	$(".mes_file_process").html("");
	var innerHtml = '<div class="files_wrap" id="' + contentFileId + '">';
	innerHtml += '<input type="hidden" name="attachment' + fileNum + '" value="' + contentFileId + '"><input type="hidden" name="attachment' + fileNum + 'Name" value="' + contentFileId + '">';
	innerHtml += '<div class="files_wrap_left"></div>';
	innerHtml += '<div class="files_wrap_center" data-id="' + contentFileId + '">' + contentFileName + '</div>';
	innerHtml += '<div class="files_wrap_right removeAttachment" data-viewId="' + contentFileId + '"></div></div>';
	$('#attachViews').append(innerHtml);
}