/**
 * 主备教案修改js
 */
var wordObj;// word控件实体
require([ 'jquery' ], function($) {
	$(function() {
		var isSend = $("#isSend").val();
		var openModeType = $("#openModeType").val();
		if (isSend == 'false') {
			if (openModeType == 'docReadOnly') {
				$("#edit_lessonplan", window.parent.document).prop("checked", false);
				$("#edit_lessonplan", window.parent.document).hide();
				$(".lable1", window.parent.document).hide();
				$("#saveEdit", window.parent.document).hide();
				alert("当前主备教案正在被另一用户整理，您此时还不可以整理，已为您自动切换成只读模式");
			} else {
				$("#edit_lessonplan", window.parent.document).show();
				$(".lable1", window.parent.document).show();
			}
		}

		// 工具栏显示隐藏
		window.fadeInOrOut = function(flag) {
			wordObj.OfficeToolbars = flag;
		}

		// 保存修改的教案
		window.saveLessonPlanTracks = function(editType) {
			$("#editType").val(editType);
			wordObj.WebSave();
			// 将返回的教案id赋值
			if (wordObj.CustomSaveResult == 'isOver') {
				alert("活动已结束，您不可再修改");
			} else if (wordObj.CustomSaveResult == 'isSend') {
				alert("活动的整理教案已被发送，您不可再整理");
			} else if (wordObj.CustomSaveResult == 'zhubeiIsEdit') {
				alert("该活动的主备教案已被发起人修改");
				parent.location.reload();
			} else {
				parent.frushIframe();// 新增则刷新父页内的列表页
				if ($("#trackId").val() != wordObj.CustomSaveResult) {
					window.location.reload();
				}
				$("#trackId").val(wordObj.CustomSaveResult);
			}
		}

		/**
		 * 每隔一段时间请求一次后台，保证session有效
		 */
		window.requestAtInterval = function(timeRange) {
			var dingshi = window.setInterval(function() {
				$.ajax({
					async : false,
					cache : true,
					type : 'POST',
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/toEmptyMethod.json?id=" + Math.random(),
					error : function() {
						window.clearInterval(dingshi);
					},
					success : function(data) {
					}
				});
			}, timeRange);
		}

		/**
		 * 持续获取资源整理控制权
		 */
		window.getResZhengliPower = function(timeRange) {
			var dingshi1 = window.setInterval(function() {
				var resId = $("#resId").val();
				$.ajax({
					async : false,
					cache : true,
					type : 'POST',
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/schoolactivity/getResZhengliPower.json?resId=" + resId + "&id=" + Math.random(),
					error : function() {
					},
					success : function(data) {
						var openModeType = $("#openModeType").val();
						if (data.result == 'success' && openModeType == 'docReadOnly') {
							alert("您现在可以整理该主备教案");
							window.location.reload();
							$("#edit_lessonplan", window.parent.document).show();
							$(".lable1", window.parent.document).html("整理教案");
						}
					}
				});
			}, timeRange);
		}
		
		window.AfterDocumentOpened =function() {
			wordObj = document.getElementById("PageOfficeCtrl1");
			wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
			var checkbox1 = $("#edit_lessonplan", window.parent.document);
			if (checkbox1 != null) {
				if (checkbox1.is(":checked")) {
					fadeInOrOut(true);
					$("#saveEdit", window.parent.document).show();
				}
			}
			var isSend = $("#isSend").val();
			var editType = $("#editType").val();
			if (editType == '1' && isSend == 'false') {
				// 每隔一段时间获取一次控制权
				getResZhengliPower(20000);
			} else if (editType == '0') {
				// 每5分钟执行一次
				requestAtInterval(300000);
			}
		}
	});
});
