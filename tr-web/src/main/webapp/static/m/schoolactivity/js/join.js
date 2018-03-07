define([ "require", "zepto", "iscroll" ], function(require) {
	var $ = Zepto;
	$(function() {
		init();
		bindEvent();
	});
	function init() {
		$(".publish1_btn").click(function() {
			$("#discussLevel").val(1);
			$("#parentId").val(0);
			saveTBDiscuss("publish1");
		});
		$('.head').click(function() {
			var activityId = $(this).attr('activityId');
			var userId = $(this).attr('userId');
			var canReply = $(this).attr('canReply');
			var activityType = $("#activityType").val();
			$("#discuss_iframe").attr("src", _WEB_CONTEXT_ + "/jy/comment/discussIndex?activityId=" + activityId + "&crtId=" + userId + "&typeId=" + activityType + "&canReply=" + canReply);
		});
		$('.range_class p').click(function() {
			if ($(this).hasClass('class_act')) {
				$(this).removeClass('class_act');
			} else {
				$(this).addClass('class_act');
			}
		});
		$('.act_participants_content ul li').click(function() {
			$(this).addClass("hour_act").siblings().removeClass("hour_act");
		});
		$('.content_list figure').click(function() {
			$(this).addClass("figure_bg").siblings().removeClass("figure_bg");
		});
		$('.content_list figure').eq(0).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			$('.act_info_wrap').show();
			var act_info_content = new IScroll('.act_info_content', {
				scrollbars : true,
				mouseWheel : true,
				fadeScrollbars : true,
			});
		});
		$('.content_list figure').eq(1).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			$("#iframe2").attr("src", $("#iframe2").attr("src"));
			$('.act_participants_wrap').show();
		});
		$('.content_list figure').eq(2).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			if ($("#iframe3").length > 0) {
				$("#iframe3").attr("src", $("#iframe3").attr("src"));
			}
			$('.act_modify_wrap').show();
			if ($("#act_modify").length > 0) {
				var act_modify = new IScroll('#act_modify', {
					scrollbars : true,
					mouseWheel : true,
					fadeScrollbars : true,
				});
			}
		});
		$('.content_list figure').eq(3).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			var activityId = $(this).attr("activityId");
			var canReply = $(this).attr("canReply");
			var activityType = $("#activityType").val();
			var discussUrl = _WEB_CONTEXT_ + "/jy/comment/discussIndexTB?activityId=" + activityId + "&canReply=" + canReply + "&typeId=" + activityType + "&" + Math.random();
			$("#iframe_discuss").attr("src", discussUrl);
			$('.partake_discussion_vrap').show();
			if ($("#partake_discussion").length > 0) {
				var partake_discussion = new IScroll('#partake_discussion', {
					scrollbars : true,
					mouseWheel : true,
					fadeScrollbars : true,
					click : true
				});
			}
		});
		$('.content_list figure').eq(4).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			if ($("#commentBox").attr("src") == "") {
				var resId = $(this).attr("resId");
				var title = $(this).attr("title");
				var authorId = $(this).attr("authorId");
				var activityType = $("#activityType").val();
				var url = _WEB_CONTEXT_ + "/jy/comment/list?resType=" + activityType + "&resId=" + resId + "&title=" + title + "&authorId=" + authorId + "&vn=/activity/view/tbComment";
				if ($(this).attr("isOver") == "true" && $(this).attr("isShare") == "true") {
					$("#commentBox").attr("src", url + "&flags=true");
				} else {
					$("#commentBox").attr("src", url + "&flags=false");
				}
			}
			$('.view_comments_wrap').toggle();
		});

		$(".act_info_close").click(function() {
			window.location.reload();// 刷新当前页面.
		});
		$('.content_list div').click(function() {
			$(this).addClass("figure_bg").siblings().removeClass("figure_bg");
		});
		if ($("#zt_head_wrap").length > 0) {
			var zt_head_wrap = new IScroll('#zt_head_wrap', {
				scrollbars : true,
				mouseWheel : true,
				fadeScrollbars : true,
				click : true
			});
		}
		$('.cy_list1').click(function() {
			$('.content_bottom_center_ck').hide();
			$('.content_bottom_center_zt').show();
		});
		$('.ck_list1').click(
				function() {
					$('.content_bottom_center_zt').hide();
					if ($("#commentBox").attr("src") == "") {
						var resId = $(this).attr("resId");
						var title = $(this).attr("title");
						var authorId = $(this).attr("authorId");
						var activityType = $("#activityType").val();
						var isOver = $(this).attr("isOver");
						var isShare = $(this).attr("isShare");
						$("#commentBox").attr(
								"src",
								_WEB_CONTEXT_ + "/jy/comment/list?flags=" + (isOver == "true" && isShare == "true" ? true : false) + "&resType=" + activityType + "&resId=" + resId + "&title=" + title
										+ "&authorId=" + authorId + "&titleShow=true");
					}
					$('.content_bottom_center_ck').show();
				});
		$('.show_btn_left').click(function() {
			$('.class_hour_wrap').slideUp();
			$('.show_btn').show();
		});
		$('.show_btn').click(function() {
			$('.class_hour_wrap').slideDown();
			$('.show_btn').hide();
		});
		$('.xx_list1').click(function() {
			$('.act_info_wrap').show();
			$('.mask').show();
			var act_info_left = new IScroll('.act_info_left', {
				scrollbars : true,
				mouseWheel : true,
				fadeScrollbars : true,
			});
		});

		// 绑定视频教研后侧操作列表
		$('.content_list_sp figure').click(function() {
			$(this).addClass("figure_bg").siblings().removeClass("figure_bg");
		});
		$('.content_list_sp figure').eq(0).click(function() {
			closeAll();
			$('.act_info_wrap').css({
				"width" : "100%",
				"height" : "100%",
				"position" : "fixed",
				"z-index" : "102"
			});
			$('.act_info_wrap').show();
			$('.mask').show();
		});
		$('.content_list_sp figure').eq(1).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			var activityId = $(this).attr("activityId");
			var canReply = $(this).attr("canReply");
			var activityType = $("#activityType").val();
			var discussUrl = _WEB_CONTEXT_ + "/jy/comment/discussIndexTB?activityId=" + activityId + "&typeId=" + activityType + "&canReply=" + canReply + "&" + Math.random();
			$("#iframe_discuss").attr("src", discussUrl);
			var partake_discussion = new IScroll('#partake_discussion', {
				scrollbars : true,
				mouseWheel : true,
				fadeScrollbars : true,
				click : true,
			});
			$('.partake_discussion_vrap').show();
		});
		$('.content_list_sp figure').eq(2).click(function() {
			closeAll();
			$("#iframe1").css({
				'width' : '50%',
				'-webkit-transition' : 'width .1s',
				"-webkit-animation-fill-mode" : "forwards"
			});
			if ($("#commentBox").attr("src") == "") {
				var resId = $(this).attr("resId");
				var title = $(this).attr("title");
				var authorId = $(this).attr("authorId");
				var activityType = $("#activityType").val();
				var url = _WEB_CONTEXT_ + "/jy/comment/list?resType=" + activityType + "&resId=" + resId + "&title=" + title + "&authorId=" + authorId + "&vn=/activity/view/tbComment";
				if ($(this).attr("isOver") == "true" && $(this).attr("isShare") == "true") {
					$("#commentBox").attr("src", url + "&flags=1");
				} else {
					$("#commentBox").attr("src", url + "&flags=0");
				}
			}
			$('.view_comments_wrap').toggle();
		});

		bindSpEvent();

		$('.close').click(function() {
			closeAll();
		});
	}

	function bindEvent() {
		$("#zhubei_canyu").find("li").click(function() {
			var hourId = $(this).attr("hourId");
			var activityId = $(this).attr("activityId");
			showLessonPlanTrack_canyu(hourId, activityId);
			$(this).attr("class", "ul_li_act");
			$(this).siblings("li").removeClass("ul_li_act");
		});
		$("#zhubei_chakan").find("li").click(function() {
			var hourId = $(this).attr("hourId");
			var activityId = $(this).attr("activityId");
			showLessonPlanTrack_chakan(hourId, activityId);
			$(this).attr("class", "ul_li_act");
			$(this).siblings("li").removeClass("ul_li_act");
		});
		$("#zhubei_zhengli").find("li").click(function() {
			var hourId = $(this).attr("hourId");
			var activityId = $(this).attr("activityId");
			showLessonPlanTrack_zhengli(hourId, activityId);
			$(this).attr("class", "ul_li_act");
			$(this).siblings("li").removeClass("ul_li_act");
		});

		$(".act_participants_content").find("li").click(function() { // 绑定修改列表的课时
			var zhubeiId = $(this).attr("zhubeiId");
			var activityId = $(this).attr("activityId");
			showTrackList(zhubeiId, activityId);
		});

		$(".enclosure_content").each(function() { // 绑定参与主题研讨的附件查看
			var resId = $(this).attr("resId");
			$(this).find("li").eq(0).click(function() {
				scanResFile_m(resId);
			});
		});

		$("#fasong").click(function() { // 绑定发送教案按钮
			sendToJoiners($(this).attr("activityId"));
		});
		$("#jieshoujiaoan").click(function() { // 绑定接收教案按钮
			receiveLessonPlan($(this).attr("activityId"));
		});
		$("#overIt").click(function() { // 绑定结束活动按钮
			overActivity($(this).attr("activityId"));
		});
		$("#overZtyt").click(function() { // 绑定结束主题研讨按钮
			overActivity($(this).attr("activityId"));
		});
		$("#overSpjy").click(function() { // 绑定结束主题研讨按钮
			overActivity($(this).attr("activityId"));
		});
		$(".act_modify_content1").find(".hour").click(function() { // 绑定参与页的整理教案的查看
			scanLessonPlanTrack($(this).attr("resId"));
		});
	}

	function bindSpEvent() {
		$('.par_head div').click(
				function() {
					$('.par_head').hide();
					$('#userPhoto').html($(this).html());
					$(".head_independent_r").html($(this).attr("userName") + "的全部留言");
					$('.par_head_1').show();
					var activityId = $(this).attr("activityId");
					var userId = $(this).attr("userId");
					var canReply = $(this).attr("canReply");
					var activityType = $("#activityType").val();
					var discussUrl = _WEB_CONTEXT_ + "/jy/comment/discussIndexTB?crtId=" + userId + "&activityId=" + activityId + "&typeId=" + activityType + "&canReply=" + canReply + "&time="
							+ Math.random();
					$("#iframe_discuss").attr("src", discussUrl);
					var partake_discussion = new IScroll('#partake_discussion', {
						scrollbars : true,
						mouseWheel : true,
						fadeScrollbars : true,
						click : true,
					});
				});
		$('.par_head_float>div div').click(
				function() {
					$('.par_head_float').hide();
					$('.par_head').hide();
					if ($('.par_head_r').html() == '收起') {
						$('.par_head_r').html('更多');
					}
					$('#userPhoto').html($(this).html());
					$(".head_independent_r").html($(this).attr("userName") + "的全部留言");
					$('.par_head_1').show();
					var activityId = $(this).attr("activityId");
					var userId = $(this).attr("userId");
					var canReply = $(this).attr("canReply");
					var activityType = $("#activityType").val();
					var discussUrl = _WEB_CONTEXT_ + "/jy/comment/discussIndexTB?crtId=" + userId + "&activityId=" + activityId + "&typeId=" + activityType + "&canReply=" + canReply + "&time="
							+ Math.random();
					$("#iframe_discuss").attr("src", discussUrl);
					var partake_discussion = new IScroll('#partake_discussion', {
						scrollbars : true,
						mouseWheel : true,
						fadeScrollbars : true,
						click : true,
					});
				});
		$('.par_head_r1').click(function() {
			$('.par_head_1').hide();
			$('.par_head').show();
			var activityId = $(this).attr("activityId");
			var canReply = $(this).attr("canReply");
			var activityType = $("#activityType").val();
			var discussUrl = _WEB_CONTEXT_ + "/jy/comment/discussIndexTB?&activityId=" + activityId + "&typeId=" + activityType + "&canReply=" + canReply + "&time=" + Math.random();
			$("#iframe_discuss").attr("src", discussUrl);
			var partake_discussion = new IScroll('#partake_discussion', {
				mouseWheel : true,
				fadeScrollbars : true,
				click : true,
			});

		});
		$('.par_head_r').click(function() {
			if ($(this).html() == '收起') {
				$(this).html('更多');
				$('.par_head_float').hide();
			} else if ($(this).html() == '更多') {
				$(this).html('收起');
				$('.par_head_float').show();
			}
			var par_head_float = new IScroll('.par_head_float', {
				scrollbars : true,
				mouseWheel : true,
				fadeScrollbars : true,
			});
		});
	}

	window.showLessonPlanTrack_zhengli = function(planId, activityId) {
		$("#iframe1").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/showLessonPlanTracks?editType=1&planId=" + planId + "&activityId=" + activityId);
	};
	window.showLessonPlanTrack_canyu = function(planId, activityId) {
		$("#iframe1").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/showLessonPlanTracks?editType=0&planId=" + planId + "&activityId=" + activityId);
	};
	window.showLessonPlanTrack_chakan = function(planId, activityId) {
		$("#iframe1").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/showLessonPlan?planId=" + planId);
	};

	// 显示主备教案的意见教案集合
	function showTrackList(planId, activityId) {
		$("#iframe2").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/getYijianTrackList?planId=" + planId + "&activityId=" + activityId);
	}

	// 发送给参与人
	function sendToJoiners(activityId) {
		if (confirm("发送后，所有集备参与人将收到您发送的集备教案，您需要将该课题下的所有教案都整理好后在发送。如果还未全部整理好，请继续整理。您确定发送吗？")) {
			$.ajax({
				async : false,
				cache : true,
				type : 'POST',
				dataType : "json",
				data : {
					id : activityId
				},
				url : _WEB_CONTEXT_ + "/jy/schoolactivity/sendToJoiner.json",
				error : function() {
					alert('操作失败，请稍后重试');
				},
				success : function(data) {
					if (data.result == "success") {
						alert("发送成功！");
						window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/joinTbjaSchoolActivity?id=" + activityId;
					} else {
						alert("系统出错");
					}
				}
			});
		}
	}

	// 接收教案
	function receiveLessonPlan(activityId) {
		if (window.confirm("该操作会使您相应课题下的所有教案被覆盖，确定要接收吗？")) {
			$.ajax({
				async : false,
				cache : true,
				type : 'POST',
				dataType : "json",
				data : {
					'activityId' : activityId
				},
				url : _WEB_CONTEXT_ + "/jy/myplanbook/receiveLessonPlanOfActivity.json",
				error : function() {
					alert('操作失败，请稍后重试');
				},
				success : function(data) {
					if (data.result == "fail3") {
						alert(data.info);
						window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/index";
					} else {
						alert(data.info);
						window.location.reload();
					}
				}
			});
		}
	}

	// 结束活动
	function overActivity(activityId) {
		if (window.confirm("您确定要结束吗？结束后，所有人将不能参与此教研活动！")) {
			$.ajax({
				async : false,
				cache : true,
				type : 'POST',
				dataType : "json",
				data : {
					id : activityId
				},
				url : _WEB_CONTEXT_ + "/jy/schoolactivity/overActivity.json",
				error : function() {
					alert('操作失败，请稍后重试');
				},
				success : function(data) {
					if (data.result == "success") {
						alert("操作成功！");
						window.location.href = window.location.href;
					} else {
						alert("系统出错");
					}
				}
			});
		}
	}

	function scanLessonPlanTrack(resId) {
		window.open(_WEB_CONTEXT_ + "/jy/activity/scanLessonPlanTrack?resId=" + resId, "_blank");
	}

	function saveTBDiscuss(clssname) {
		var content = $.trim($("." + clssname).val());
		if (content != "" && content.length > 0) {
			if (content.length <= 300) {
				$("#content_hidden").val(content);
				if (confirm("您确定要发送讨论意见吗？")) {
					$.ajax({
						type : "post",
						dataType : "json",
						url : _WEB_CONTEXT_ + "/jy/comment/discussSave",
						data : $("#jbdiscussform").serializeArray(),
						success : function(data) {
							$("." + clssname).val("");
							if (data.msg == "ok") {
								alert("发送讨论成功！");
								var activityId = $("#activityId").val();
								var activityType = $("#activityType").val();
								var discussUrl = _WEB_CONTEXT_ + "/jy/comment/discussIndexTB?activityId=" + activityId + "&canReply=true&typeId=" + activityType + "&" + Math.random();
								$("#iframe_discuss").attr("src", discussUrl);
								var partake_discussion = new IScroll('#partake_discussion', {
									mouseWheel : true,
									fadeScrollbars : true,
									click : true,
								});
							} else {
								alert(data.msg);
							}
						}
					});
				}
			} else {
				alert("讨论信息不能超过300字！");
			}
		} else {
			alert("请您填写讨论信息！");
		}
	}
	// 关闭全部
	function closeAll() {
		$('.act_participants_wrap').hide();
		$('.act_info_wrap').hide();
		$('.act_modify_wrap').hide();
		$('.view_comments_wrap').hide();
		$('.partake_discussion_vrap').hide();
		$('.mask').hide();
		$("#iframe1").css({
			"width" : "100%"
		});
	}
});