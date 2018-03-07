define([ "require", "zepto", "iscroll", 'common/dateFormat' ], function(require) {
	require("zepto");
	var dateFormat = require("common/dateFormat");
	var $ = Zepto;
	var content_b;
	$(function() {
		init();
	});
	function init() {
		content_b = new IScroll('.content_b', {
			scrollbars : true,
			mouseWheel : true,
			fadeScrollbars : true,
			click : true,
		});
		$('.partake_q').click(function(e) {
			var circleid = $(this).attr("data-circleid");
			$('#jyqids_' + circleid).show();
			$('.mask').show();
			return false;
		});
		$(".gl_btn").click(function() { // 绑定管理按钮
			$('.activity_option').hide();
			$(this).parent().find('.activity_option').show();
		});
		$('.activity_option_close').click(function() {
			$('.activity_option').hide();
		});
		$('.draft').click(function() {// 进入草稿箱
			var num = Number($("#drafnum").html());
			if (num == 0) {
				successAlert("您的草稿箱还没有任何内容哟！");
				return false;
			} else {
				if ($("#activity_iframe").attr("src") == "") {
					$("#activity_iframe").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/indexDraft?" + Math.random());
				}
				$('.act_draft_wrap').show();
				$('.mask').show();
			}
		});
		$('.close').click(function() {
			$('.act_draft_wrap').hide();
			$('.mask').hide();
			$('.partake_school_wrap1').hide();
		});
		$('#fqSchoolActivity').click(function() {
			$('.mask').show();
			$('.fq_option_wrap').show();
		});
		$('#uploadTeachschedule').click(function(event) {// 上传教研进度表
			window.location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
			event.stopPropagation();
		});
		$('#lookTeachCircle').click(function(event) {// 查看校级教研圈
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/circle/lookTeachCircle";
			event.stopPropagation();
		});
		$('#lookTeachschedule').click(function(event) {// 查看教研进度表
			window.location.href = _WEB_CONTEXT_ + "/jy/teachschedule/read";
			event.stopPropagation();
		});
		$('.fq_option_wrap').click(function() {
			$('.mask').hide();
			$('.fq_option_wrap').hide();
		});
		$('.fq_option_tb').click(function(event) {
			// 发起同备教案
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/fqSchoolActivity?typeId=1";
			event.stopPropagation();
		});
		$('.fq_option_zt').click(function(event) {
			// 发起主题研讨
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/fqSchoolActivity?typeId=2";
			event.stopPropagation();
		});
		$('.fq_option_sp').click(function(event) {
			// 发起主题研讨
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/fqSchoolActivity?typeId=3";
			event.stopPropagation();
		});
		$('.fq_option_zb').click(function(event) {
			// 发起主题研讨
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/fqSchoolActivity?typeId=4";
			event.stopPropagation();
		});
		$('.partake_school').click(function() {
			$('.partake_school_wrap1').show();
			$('.mask').show();
			var partake_school_content = new IScroll('.partake_school_content', {
				scrollbars : true,
				mouseWheel : true,
				fadeScrollbars : true,
			});
		});
		// 上传教研进度表按钮
		$('#uploadTeachschedule').click(function() {
			window.location.href = _WEB_CONTEXT_ + "/jy/teachschedule/index";
		});
		// 查看教研进度表按钮
		$('#lookTeachSchedule').click(function() {
			window.location.href = _WEB_CONTEXT_ + "/jy/teachschedule/read";
		});
		// 查看校际教研圈
		$('#lookTeachCircle').click(function() {
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/circle/lookTeachCircle";
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
	}
	// 整理校际教研事件
	window.zhenglixj = function(obj) {// 为已发布列表绑定整理事件
		var activityId = $(obj).attr("data-activityId");
		var typeId = $(obj).attr("data-typeId");
		var startDate = $(obj).attr("data-startDate");
		var isTuiChu = $(obj).attr("data-isTuichu");
		var isOver = $(obj).attr("data-isOver");
		zhengli(obj, activityId, typeId, isTuiChu, isOver, startDate);
	};
	// 参与校际教研事件
	window.canyuxj = function(obj) {// 为已发布列表绑定参与事件
		var activityId = $(obj).attr("data-activityId");
		var typeId = $(obj).attr("data-typeId");
		var startDate = $(obj).attr("data-startDate");
		var isTuiChu = $(obj).attr("data-isTuichu");
		var isOver = $(obj).attr("data-isOver");
		if (isTuiChu == 'true') {
			canyu_chakan(obj, activityId, typeId, 'true', startDate)
		} else if (isTuiChu == "false") {
			canyu_chakan(obj, activityId, typeId, isOver, startDate)
		}
	};
	// 修改校际教研活动
	window.editActivity = function(activityId, typeId) {
		window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/editActivity?id=" + activityId + "&typeId=" + typeId;
	};
	// 草稿箱数量减一
	window.setdrafnum = function() {
		var num = Number($("#drafnum").html());
		if (num > 0) {
			num = num - 1;
			$("#drafnum").html(num);
		}
	};
	// 分享或者取消分享操作
	window.sharingActivity = function(obj) {
		var id = $(obj).attr("data-actid");
		var isShare = $(obj).attr("data-actstate");
		var confirmStr = "";
		if (isShare == "true") {
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
						successAlert("操作成功");
						if (isShare == "true") {
							$(obj).removeClass("activity_option_share");
							$(obj).addClass("activity_option_qx_share1");
							$(obj).attr({
								"onclick" : "sharingActivity(this)",
								"actstate" : "false"
							});
						} else {
							$(obj).removeClass("activity_option_qx_share1");
							$(obj).addClass("activity_option_share");
							$(obj).attr({
								"onclick" : "sharingActivity(this)",
								"actstate" : "true"
							});
						}
					} else {
						successAlert("操作失败");
					}
				}
			});
		}
	};

	// 整理
	window.zhengli = function(obj, activityId, typeId, isTuiChu, isOver, startDateStr) {
		var listType = $("#listType").val();
		if (ifActivityStart(dateFormat.format(new Date(), "yyyy-MM-dd hh:ff:ss"), startDateStr)) {
			if (isTuiChu == 'true') {
				if (typeId == 1) {// 同备教案
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				} else if (typeId == 4) {// 直播课堂
					canyu_chakanZbkt(obj, activityId, typeId, 'true', startDateStr, true);// 直播课堂
				} else {// 主题研讨-视频研讨
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			} else if (isTuiChu == 'false') {
				if (typeId == 1) {// 同备教案
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				} else if (typeId == 4) {// 直播课堂
					canyu_chakanZbkt(obj, activityId, typeId, isOver, startDateStr, true);// 直播课堂
				} else {// 主题研讨-视频研讨
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			}
		}
	};

	window.canyu_chakan = function(obj, activityId, typeId, isOver, startDateStr) {
		var listType = $("#listType").val();
		if (typeId == 1) {// 同备教案
			if (isOver == 'true') {// 已结束，则查看
				window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
			} else if (isOver == 'false') {// 参与
				if (ifActivityStart(dateFormat.format(new Date(), "yyyy-MM-dd hh:ff:ss"), startDateStr)) {
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			}
		} else if (typeId == 4) {
			canyu_chakanZbkt(obj, activityId, typeId, isOver, startDateStr, false);// 直播课堂
		} else if (typeId == 2 || typeId == 3) {// 主题研讨
			if (isOver == 'true') {// 已结束，则查看
				window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
			} else if (isOver == 'false') {// 参与
				if (ifActivityStart(dateFormat.format(new Date(), "yyyy-MM-dd hh:ff:ss"), startDateStr)) {
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/joinZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
				}
			}
		}
	};

	// 直播课堂
	window.canyu_chakanZbkt = function(obj, activityId, typeId, isOver, startDateStr, isCreater) {
		var listType = $("#listType").val();
		// 直播课堂
		$(".not_started_info").find("h3").html($(obj).find("h3 .title").html());
		$(".not_started_info").find("p").eq(1).html($(obj).find(".promoter span").html());
		var schoolName = "";
		var circleid = $(obj).find(".partake_q").attr("data-circleid");
		$("#jyqids_" + circleid).find(".partake_school_content div ul li").each(function() {
			schoolName += "、" + $(this).find("strong").html();
		});
		$(".not_started_info").find("p").eq(3).html(schoolName != "" ? schoolName.substring(1) : "");
		$(".not_started_info").find("p").eq(5).html($(obj).find(".partake_sub span").html() + "&nbsp;&nbsp;&nbsp;&nbsp;" + $(obj).find(".partake_class span").html());
		if (ifActivityStart(dateFormat.format(new Date(), "yyyy-MM-dd hh:ff:ss"), startDateStr)) {
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

	// 判断活动是否开始
	window.ifActivityStart = function(currentDate, startDate) {
		if (startDate.length > 0) {
			// var currentDate = new Date(+new
			// Date()+8*3600*1000).toISOString().replace(/T/g,'
			// ').replace(/\.[\d]{3}Z/,'');
			var startDateTemp = startDate.split(" ");
			var arrStartDate = startDateTemp[0].split("-");
			var arrStartTime = startDateTemp[1].split(":");

			var currentDateTemp = currentDate.split(" ");
			var arrStartDate1 = currentDateTemp[0].split("-");
			var arrStartTime1 = currentDateTemp[1].split(":");

			var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
			var allcurrentDate = new Date(arrStartDate1[0], arrStartDate1[1], arrStartDate1[2], arrStartTime1[0], arrStartTime1[1], arrStartTime1[2]);
			if (allStartDate.getTime() > allcurrentDate.getTime()) {
				successAlert("该活动还未开始，请于" + startDate + "来准时参加活动");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	};

	window.addmoredatas = function(data) {
		var content = $("#addmoredatas", data);
		$("#addmoredatas").append(content.find(".activity_tch"));
		var jyqcontent = $("#xjjyq_detail_div", data);
		$.each(jyqcontent.find(".xjjyq_detail_div"), function(index, obj) {
			var jyqid = $(obj).id();
			if (!document.getElementById(jyqid)) {
				$("#xjjyq_detail_div").append(obj);
			}
		});
		content_b.refresh();
		$('.partake_q').click(function() {
			var circleid = $(this).attr("data-circleid");
			$('#jyqids_' + circleid).show();
			$('.mask').show();
		});
	};
	window.deleteActivity = function(activityId) {
		$.ajax({
			url : _WEB_CONTEXT_ + "/jy/schoolactivity/deleteActivity_m",
			type : "POST",
			data : {
				id : activityId
			},
			success : function(data) {
				if (data.code == 1) {
					successAlert(data.msg);
					// $(this).parents(".activity_tch").remove();
					window.location.reload();
				} else {
					successAlert(data.msg);
				}

			},
			error : function() {
			}
		});
	}
});