define([ "require", "zepto", "iscroll", "datetime", "placeholder" ], function(require) {
	require("zepto");
	var $ = Zepto;
	$(function() {
		$('#picktime_start').mdatetimer({
			mode : 2, // 时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
			format : 2, // 时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10
			// 17:30:46。默认：2
			years : [ 2000, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030 ], // 年份数组
			nowbtn : true, // 是否显示现在按钮
			onOk : null,
			onCancel : null
		});
		$('#picktime_end').mdatetimer({
			mode : 2, // 时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
			format : 2, // 时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10
			// 17:30:46。默认：2
			years : [ 2000, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030 ], // 年份数组
			nowbtn : true, // 是否显示现在按钮
			onOk : null,
			onCancel : null
		});
		init();
	});
	function init() {
		var content_bottom1 = new IScroll('.content_bottom1', {
			scrollbars : true,
			mouseWheel : true,
			fadeScrollbars : true,
		});
		$(".remark").placeholder({
			word : '输入活动要求（200字内）'
		});
		var newwidth = $(".range1_content").height();
		$(".range1_border").css('height', newwidth);
		$('.p_option').click(function() {
			if ($(this).hasClass('p_act')) {
				$(this).removeClass('p_act');
			} else {
				$(this).addClass('p_act');
			}
		});
		$('.close').click(function() {
			$('.partake_school_wrap1').hide();
			$('.mask').hide();
		});
		$('.see').click(function() {
			var circleid = $("#selectCircleId").val();
			$('#jyqid_' + circleid).show();
			$('.mask').show();
		});
		$('.no-modify').click(function() {
			window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/index";
		});
	}

	// 删除主题研讨附件
	window.removeFJ = function(obj) {
		if (confirm("确定要删除活动附件吗？")) {
			var resId = $(obj).attr("data-resId");
			$("#" + resId).remove();
		}
	};
	// 上传文件之前的检查
	$('.submit_btn').hide();
	window.beforeUpload = function() {
		$('.release').hide();
		$('.deposit_draft').hide();
		$('.modify').hide();
		$('.no-modify').hide();
		$('.submit_btn').show();
		var i = 0;
		$("#ztytFJ .add_study_content").each(function(index, obj) {
			i++;
		});
		if (i >= 8) {
			successAlert("您最多只能上传8个活动附件！");
			return false;
		}
		return true;
	};
	// 上传回调
	window.afterUpload = function(data) {
		if (data.code == 0) {
			var resId = data.data;
			var title = data.filename;
			var fjstr = '<div id="' + resId + '" class="add_study_content"><div class="add_study_content_l"></div>' + '<div class="add_study_content_c" ><span style="overflow: hidden;">' + title
					+ '</span><div class="complete">上传完成</div>' + '</div><input type="button" class="add_study_content_r" data-resId="' + resId + '" onclick="removeFJ(this)" value="删除" /></div>';
			$("#ztytFJ").prepend(fjstr);
		} else {
			successAlert("上传失败！");
		}
		$('.release').show();
		$('.deposit_draft').show();
		$('.modify').show();
		$('.no-modify').show();
		$('.submit_btn').hide();
	};
	// 获得附件Ids
	window.getUploadFileIds = function() {
		var resIds = "";
		$("#ztytFJ .add_study_content").each(function(index, obj) {
			resIds += obj.id + ",";
		});
		$("#ztytRes").val(resIds == "" ? "" : resIds);
	};
	// 取复选框值
	function getCheckboxValue(notempty, xktrue) {
		// 学科
		if (xktrue) {
			var xk = ",";
			$(".subjecth4 .p_act").each(function() {
				xk += $(this).attr("data-dataid") + ",";
			});
			if (xk == ",") {
				if (notempty) {
					var xkid = "";
					$(".subjecth4 span").each(function() {
						xkid = $(this).attr("data-dataid");
					});
					if (xkid == "") {
						successAlert("请选择参与学科！");
						return false;
					}
				}
			} else {
				$("#subjectIds").val(xk);
			}
		}
		// 年级
		var nj = ",";
		$(".gradeh4 .p_act").each(function() {
			nj += $(this).attr("data-dataid") + ",";
		});
		if (nj == ",") {
			if (notempty) {
				var njid = "";
				$(".gradeh4 span").each(function() {
					njid = $(this).attr("data-dataid");
				});
				if (njid == "") {
					successAlert("请选择参与年级！");
					return false;
				}
			}
		} else {
			$("#gradeIds").val(nj);
		}
		return true;
		// 专家,暂无
	}
	// 选择教研圈
	window.selectCircle = function(type) {
		var circleId = $("#selectCircleId").val();
		if (circleId != "") {
			if (type == "tbja") {
				var schoolOptions = "";
				$.each($("#jyqid_" + circleId + " .jbokorg"), function(index, obj) {
					var orgId = $(obj).attr("data-orgid");
					var orgName = $(obj).attr("data-orgname");
					schoolOptions += '<option id="mainOrg_' + orgId + '" value="' + orgId + '">' + orgName + '</option>';
				});
				$("#mainUserOrgId").html('<option value="">请选择学校</option>' + schoolOptions);
				if ($("#id").val() != "") {
					$("#mainUserOrgId").val($("#mainUserOrgIdhid").val());
				}
			}
			$(".see").show();
			$("#schoolTeachCircleId").val(circleId);
			$("#schoolTeachCircleName").val($("#circleOpt_" + circleId).text());
		} else {
			$("#schoolTeachCircleId").val("");
			$("#schoolTeachCircleName").val("");
			$(".see").hide();
			if (type == "tbja") {
				$("#mainUserOrgId").html('<option value="">请选择学校</option>');
			}
		}
	};
	// 保存备课活动
	window.saveSchoolActivity = function(parStatus) {
		var confrimStr = "此校际教研活动将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布吗？";
		var typeId = $("#typeId").val();
		if (parStatus == "0") {// 存草稿箱
			var text = $.trim($("#activityName").val());
			if (text == null || text == "") {
				successAlert("请您至少要输入活动主题才能存入草稿箱");
				return false;
			}
			confrimStr = "为保证活动时限的准确性，您下次发布活动的时候再设置开始时间和结束时间吧，本次就不给您记录喽！";
			if (typeId == "1") {
				getCheckboxValue(false, false);
			} else if (typeId == "2" || typeId == "3" || typeId == "4") {
				getCheckboxValue(false, true);
			}
			getUploadFileIds();
		} else {
			var circleId = $("#selectCircleId").val();
			if (circleId == "") {
				successAlert("请选择教研圈");
				return false;
			}
			var returnsta = true;
			if (typeId == "1") {
				if ($("#mainUserOrgId").val() == "") {
					successAlert("请选择学校");
					return false;
				}
				if ($("#mainUserSubjectId").val() == "") {
					successAlert("请选择学科");
					return false;
				}
				if ($("#mainUserGradeId").val() == "") {
					successAlert("请选择年级");
					return false;
				}
				if ($("#mainUserName").val() == "") {
					successAlert("请选择数主备人");
					return false;
				}
				if ($("#chapterId").val() == "") {
					successAlert("请选择主备课题");
					return false;
				}
				returnsta = getCheckboxValue(true, false);
			} else if (typeId == "2" || typeId == "3" || typeId == "4") {
				returnsta = getCheckboxValue(true, true);
				if (typeId == "3") {
					var videoUrl = $.trim($("#videoUrl").val());
					if (videoUrl == null || videoUrl == "") {
						successAlert("请您输入视频地址");
						return false;
					}else{
						if(!isUrl(videoUrl)){
							successAlert("您输入视频地址格式不正确");
							return false;
						}
					}
				}
			}
			if (!returnsta) {
				return false;
			}
			if ($("#picktime_start").val() == null || $("#picktime_start").val() == "") {
				successAlert("请选择活动开始时间");
				return false;
			}
			var text = $.trim($("#activityName").val());
			if (text == null || text == "") {
				successAlert("请您输入活动主题");
				return false;
			}
			getUploadFileIds();
		}
		$("#status").val(parStatus);
		if (confirm(confrimStr)) {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/schoolactivity/saveSchoolActivityTbja.json",
				data : $("#schActivityForm").serialize(),
				error : function() {
					successAlert("请求失败！");
				},
				success : function(data) {
					var rc = data.resultCode;
					if (rc == 1) {
						window.parent.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/index";
					} else if (rc == 2) {
						successAlert("活动已被讨论，只能修改部分数据", false, function() {
							document.location.reload();
						});
					} else {
						successAlert("该主备课题下资源读取有误，请确定该资源是否有效！");
						return false;
					}
				}
			});
		}
	};
	// 更新主备人list
	window.checkMainUserList = function() {
		$("#chapterId").html('<option value="">请选择</option>');
		$("#mainUserId").html('<option value="">请选择</option>');
		if ($("#mainUserSubjectId").val() != '' && $("#mainUserGradeId").val() != '' && $("#mainUserOrgId").val() != '') {
			var mid = "subjectId=" + $("#mainUserSubjectId").val() + "&gradeId=" + $("#mainUserGradeId").val() + "&orgId=" + $("#mainUserOrgId").val();
			$.getJSON("jy/schoolactivity/mainUserList?" + mid, function(data) {
				var lis = data.userSpaceList;
				if (lis.length > 0) {
					for (var i = 0; i < lis.length; i++) {
						$("#mainUserId").append('<option  value="' + lis[i].userId + '" id="zbr_' + lis[i].userId + '">' + lis[i].username + '</option>');
					}
				}
			});
		}
	};
	// 更新主备课题list
	window.checkChapterList = function() {
		$("#chapterId").html('<option value="">请选择</option>');
		if ($("#mainUserId").val() != '' && $("#mainUserSubjectId").val() != '' && $("#mainUserGradeId").val() != '') {
			var name = $("#zbr_" + $("#mainUserId").val()).text();
			$("#mainUserName").val(name);
			var mid = "userId=" + $("#mainUserId").val() + "&subjectId=" + $("#mainUserSubjectId").val() + "&gradeId=" + $("#mainUserGradeId").val();
			$.getJSON("jy/schoolactivity/chapterList?" + mid, function(data) {
				var lis = data.lessonInfoList;
				if (lis.length > 0) {
					for (var i = 0; i < lis.length; i++) {
						$("#chapterId").append('<option value="' + lis[i].id + '">' + lis[i].lessonName + '</option>');
					}
				}
			});
		}
	};

});