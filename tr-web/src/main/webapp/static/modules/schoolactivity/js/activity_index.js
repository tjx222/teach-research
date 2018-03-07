/**
 * 校际教研列表页
 */
require([ 'jquery'], function($) {
	$(function() {
		// 发起教研活动
		$('.fq_teaching_btn').click(function() {
			location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/fqSchoolActivity";
		});

		// 进入草稿箱
		$('.drafts a').click(function() {
			var num = Number($("#drafnum").val());
			if (num == 0) {
				alert("您的草稿箱还没有任何内容哟！");
				return false;
			}
			$("#draft_box").dialog({
				width : "562",
				height : "450"
			});
			$("#activity_iframe").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/indexDraft?" + Math.random());
		});

		// 上传或查看教研进度表
		$('.schedule_table_btn').click(function() {
			var operate = $(this).attr("data-key");
			if (operate == "look") {
				location.href = _WEB_CONTEXT_ + "/jy/teachschedule/read";// 查看教研进度表
			}
			if (operate == "create") {
				location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";// 上传教研进度表
			}
		});

		// 查看校际教研圈
		$(".circle_btn").click(function() {
			location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/circle/index";
		});

		// 编辑活动操作
		$(".edit_btn").click(function() {
			var activityId = $(this).attr("data-id");
			var typeId = $(this).closest("tr").find("td:eq(0) a").attr("data-typeId");
			window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/editActivity?id=" + activityId + "&typeId=" + typeId, "_self");
		});

		// 删除活动操作
		$(".del_btn").click(function() {
			var id = $(this).attr("data-id");
			if (confirm("确定要删除活动吗？")) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/schoolactivity/deleteActivity.json",
					data : {
						"id" : id
					},
					success : function(data) {
						if (data.isSuccess) {
							if (data.deleteState == "notdelete") {
								alert("此活动下已经有讨论信息，不能删除！");
							} else if (data.deleteState == "delete") {
								$("#tr_" + id).remove();
								alert("操作成功");
							}
						} else {
							alert("操作失败");
						}
					}
				});
			}
		});

		// 分享或者取消分享操作
		$(".share_btn,.qx_share_btn").click(function() {
			var id = $(this).attr("data-id");
			var isShare = $(this).attr("data-isShare");
			var confirmStr = "";
			if (isShare) {
				confirmStr = "确定要分享吗？";
			} else {
				confirmStr = "确定要取消分享吗？";
			}
			if (confirm(confirmStr)) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/schoolactivity/sharingActivity.json",
					data : {
						"id" : id,
						"isShare" : isShare
					},
					success : function(data) {
						if (data.isSuccess) {
							alert("操作成功");
							if (isShare) {
								$("#td_" + id).html('<span title="取消分享" class="qx_share_btn" data-id="' + id + '" data-isShare="false"></span>');
							} else {
								$("#td_" + id).html('<span title="分享" class="share_btn" data-id="' + id + '" data-isShare="true"></span>');
							}
						} else {
							alert("操作失败");
						}
					}
				});
			}
		});

		// 主题名称链接
		$(".td_name").click(function() {
			var listType = $(this).attr("data-type");
			var activityId = $(this).attr("data-id");
			var typeId = $(this).attr("data-typeId");
			var isTuiChu = $(this).attr("data-isTuiChu");
			var startDateStr = $(this).attr("data-startTime");
			var isOver = $(this).attr("data-isOver");
			if (listType == 1) {
				canyu_chakan($(this), activityId, typeId, isOver, startDateStr);
			}
			if (listType == 0) {
				zhengli($(this), activityId, typeId, isTuiChu, isOver, startDateStr);
			}
		});

		// 参与或查看事件
		$(".see_btn,.partake_btn").click(function() {
			var obj = $(this).closest("tr").find("td:eq(0) a");
			var listType = obj.attr("data-type");
			var activityId = obj.attr("data-id");
			var typeId = obj.attr("data-typeId");
			var isTuiChu = obj.attr("data-isTuiChu");
			var startDateStr = obj.attr("data-startTime");
			canyu_chakan($(this), activityId, typeId, $(this).attr("data-isOver"), startDateStr);
		});

		// 发起活动弹出框关闭事件
		$(".dialog_bg_close").click(function() {
			$("#not_started_dialog").hide();
		});

		// 参与课堂
		$(".enter").click(function() {
			var activityId = $(this).parent().attr("data-id");
			var listType = $("#listType").val();
			window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinZbkt?id=" + activityId + "&listType=" + listType, "_blank");
		});

		// 查看课堂
		$(".access_view").click(function() {
			var activityId = $(this).parent().attr("data-id");
			var listType = $("#listType").val();
//			$.getJSON(_WEB_CONTEXT_ + "/jy/schoolactivity/checkClassPlayRecord", {
//				'id' : activityId
//			}, function(data) {
//				if (data) {
//					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZbkt?id=" + activityId + "&listType=" + listType, "_self");
//				} else {
//					alert("回放视频暂未生成,请稍后再进行查看！");
//				}
//			});
			window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZbkt?id=" + activityId + "&listType=" + listType, "_self");
			
		});

		// 进入课堂
		$(".access_class").click(function() {
			var activityId = $(this).parent().attr("data-id");
			var listType = $("#listType").val();
			window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinZbkt?id=" + activityId + "&listType=" + listType, "_blank");
		});

		// 结束课堂
		$(".end_class").click(function() {
			var activityId = $(this).parent().attr("data-id");
			if (window.confirm("您确定要结束吗？结束后，所有人将不能参与此教研活动！")) {
				$.getJSON(_WEB_CONTEXT_ + "/jy/schoolactivity/overZbktActivity", {
					'id' : activityId
				}, function(data) {
					if (data.result == "success") {
						window.location.reload();
					} else {
						alert("课堂还未结束，请进入课堂进行结束！");
					}
				});
			}
		});
	});

	// 整理
	var zhengli = function(obj, activityId, typeId, isTuiChu, isOver, startDateStr) {
		if (activityIndex.ifActivityStart(activityIndex.getNowFormatDate(), startDateStr)) {
			var listType = $("#listType").val();
			if (isTuiChu == "true") {
				if (typeId == 1) {// 同备教案
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_blank");
				} else if (typeId == 4) {// 直播课堂
					canyu_chakanZbkt(obj, activityId, typeId, 'true', startDateStr, true);// 直播课堂
				} else {// 主题研讨,视频研讨
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			} else {
				if (typeId == 1) {// 同备教案
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_blank");
				} else if (typeId == 4) {// 直播课堂
					canyu_chakanZbkt(obj, activityId, typeId, isOver, startDateStr, true);// 直播课堂
				} else {// 主题研讨，视频研讨
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			}
		}
	};

	// 参与或查看
	var canyu_chakan = function(obj, activityId, typeId, isOver, startDateStr) {
		var currentDate = activityIndex.getNowFormatDate();
		var listType = $("#listType").val();
		if (typeId == 1) {// 同备教案
			if (isOver=='true') {// 已结束，则查看
				window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_blank");
			} else if(isOver=='false'){// 参与
				if (activityIndex.ifActivityStart(currentDate, startDateStr)) {
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_blank");
				}
			}
		} else if (typeId == 4) {
			canyu_chakanZbkt(obj, activityId, typeId, isOver, startDateStr, false);// 直播课堂
		} else {// 主题研讨，视频研讨
			if (isOver=='true') {// 已结束，则查看
				window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
			} else if (isOver=='false'){// 参与
				if (activityIndex.ifActivityStart(currentDate, startDateStr)) {
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			}
		}
	};

	// 直播课堂
	var canyu_chakanZbkt = function(obj, activityId, typeId, isOver, startDateStr, isCreater) {
		var parent = obj.closest("tr");
		var listType = $("#listType").val();
		// 直播课堂
		$(".not_started_info").find("h3").html(parent.find("td").eq(0).find("a").attr("title"));
		$(".not_started_info").find("p").eq(1).html(parent.find("td").eq(4+parseInt(listType)).html());
		var schoolName = "";
		parent.find("td").eq(1).find("span div ul li:gt(0)").each(function() {
			schoolName += "、" + $(this).attr("title");
		});
		$(".not_started_info").find("p").eq(3).html(schoolName != "" ? schoolName.substring(1) : "");
		$(".not_started_info").find("p").eq(5).html(parent.find("td").eq(2).attr("title") + "&nbsp;&nbsp;&nbsp;&nbsp;" + parent.find("td").eq(3).attr("title"));
		if (activityIndex.ifActivityStart(activityIndex.getNowFormatDate(), startDateStr)) {
			$(".prompt_info").hide();
			if (isCreater) {
				if (isOver == 'true' || isOver == true) {// 已结束，则查看
					$(".enter").hide();
					$(".not_enter").hide();
					$(".access_view").show();
					$(".access_class").hide();
					$(".end_class").hide();
				} else {
					$(".enter").hide();
					$(".not_enter").hide();
					$(".access_view").hide();
					$(".access_class").show();
					$(".end_class").show();
				}
			} else {
				if (isOver == 'true' || isOver == true) {// 已结束，则查看
					$(".enter").hide();
					$(".not_enter").show();
					$(".access_view").show();
					$(".access_class").hide();
					$(".end_class").hide();
				} else {
					$(".enter").show();
					$(".not_enter").hide();
					$(".access_view").hide();
					$(".access_class").hide();
					$(".end_class").hide();
				}
			}
			$(".prompt_info_btn").attr("data-id", activityId).show();
		} else {
			$(".prompt_info").show();
			$(".prompt_info_btn").hide();
		}
		$('.mask_wirte').show();
		$('#not_started_dialog').show();
	};
});

var activityIndex = {

	// 教研活动类型切换
	changeListType : function(obj, listType) {
		if (!$(obj).hasClass("collective_cont_act")) {
			location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/index?listType=" + listType;
		}
	},

	// 草稿箱数量减一
	setdrafnum : function() {
		var num = Number($("#drafnum").val());
		if (num > 0) {
			num = num - 1;
			$("#drafnum").val(num);
			if (num == 0) {
				$(".xiaohongdian").remove();
			}
		}
	},

	// 判断活动是否开始
	ifActivityStart : function(currentDate, startDate) {
		if (startDate.length > 0) {
			var startDateTemp = startDate.split(" ");
			var arrStartDate = startDateTemp[0].split("-");
			var arrStartTime = startDateTemp[1].split(":");

			var currentDateTemp = currentDate.split(" ");
			var arrStartDate1 = currentDateTemp[0].split("-");
			var arrStartTime1 = currentDateTemp[1].split(":");

			var allStartDate = new Date(arrStartDate[0], arrStartDate[1]-1, arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
			var allcurrentDate = new Date(arrStartDate1[0], arrStartDate1[1]-1, arrStartDate1[2], arrStartTime1[0], arrStartTime1[1], arrStartTime1[2]);
			if (allStartDate.getTime() > allcurrentDate.getTime()) {
				alert("该活动还未开始，请于"+startDate+"来准时参加活动");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	},

	// 当前日期yyyy-MM-dd HH:MM:SS
	getNowFormatDate : function() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
		return currentdate;
	}
}
