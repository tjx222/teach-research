/**
 * 校际教研保存
 */
require([ 'jquery', 'editor' ], function($) {
	var editor;
	$(function() {
		/**
		 * 活动要求文本编辑对象
		 */
		webEditorOptions = {
			width : "840px",
			height : "150px",
			style : 0,
			afterChange : function() {
				var txtcount = this.count('text');
				if (txtcount > 200) {
					$('#w_count').html("<font color='red'>" + txtcount + "</font>");
				} else {
					$('#w_count').html(txtcount);
				}
			}
		};
		editor = $("#editerContent").editor(webEditorOptions)[0];

		// 初始化类型页面
		var typeId = $("#typeId").val();
		if (typeId == null || typeId == "") {
			// 类型切换事件
			$(":radio[name='type']").click(function() {
				$("#selectCircleId").val("").trigger("chosen:updated");
				$("#mainUserOrgId").val("").trigger("chosen:updated");
				$("#mainUserGradeId").val("").trigger("chosen:updated");
				$("#mainUserSubjectId").val("").trigger("chosen:updated");
				$("#mainUserId").val("").trigger("chosen:updated");
				$("#chapterId").val("").trigger("chosen:updated");
				$("input[type='checkbox']:checked").removeAttr("checked");
				var index = $(":radio[name='type']").index($(this));
				setTypePage(index + 1);
			});
			$("#typeId").val(1);
		} else {
			setTypePage(typeId);
			setCheckedInput(typeId);
		}

		// 下拉框设置
		$(".chosen-select-deselect").chosen({
			disable_search : true
		});

		// 教研圈选择事件
		$("#selectCircleId").change(function() {
			var circleId = $(this).val();
			if (circleId != "") {
				$("#schoolTeachCircleId").val(circleId);
				$("#schoolTeachCircleName").val($("#circleOpt_" + circleId).html());
			}
			$("#mainUserOrgId").parent().attr("data-orgId", "");
			setSelectMainOrgList(circleId);
		});

		// 发布
		$(".publish").click(function() {
			saveSchoolActivity(1);
		});

		// 存草稿箱
		$(".save_drafts").click(function() {
			saveSchoolActivity(0);
		});

		// 编辑
		$(".edit").click(function() {
			saveSchoolActivity(1);
		});

		// 主备人机构变更事件
		$("#mainUserOrgId").change(function() {
			$("#mainUserGradeId").parent().attr("data-gradeId", "");
			checkMainGradeList();
			checkMainUserList();
		});

		// 主备人年级变更事件
		$("#mainUserGradeId").change(function() {
			checkMainUserList();
		});

		// 主备人学科变更事件
		$("#mainUserSubjectId").change(function() {
			checkMainUserList();
		});

		// 主备人变更事件
		$("#mainUserId").change(function() {
			checkChapterList();
		});

		// 附件删除事件
		$("#fileFjs").find(".files_wrap_right_c").click(function() {
			removeFJ($(this).parent());
		});

		// 查看
		$("#circleContent").hover(function() {
			var circleId = $("#selectCircleId").val();
			$("#circleUl_" + circleId).show();
		}, function() {
			var circleId = $("#selectCircleId").val();
			$("#circleUl_" + circleId).hide();
		});
	});

	// 设置对应类型页面
	function setTypePage(index) {
		if ($("#id").val() != "") {
			$(".edit").show();
		} else {
			$(".publish").show();
		}
		if (index == 1) {
//			$('#partake_subject').hide();
			$('#reference_material').hide();
			$('#confirm_main_standby').show();
			$('#video_address').hide();
			$('.activity_require').css("border-left", "0");
			$('#videoUrl').val("");
		} else if (index == 2) {
			$('#standby_lessonplan').show();
			$('#reference_material').show();
			$('#confirm_main_standby').hide();
			$('#partake_subject').show();
			$('#video_address').hide();
			$('.activity_require').css("border-left", "2px #c7c7c7 solid");
			$('#videoUrl').val("");
		} else if (index == 3) {
			$('#standby_lessonplan').show();
			$('#reference_material').show();
			$('#partake_subject').show();
			$('#confirm_main_standby').hide();
			$('#video_address').show();
			// 视频地址提示语
			$('#videoUrl').placeholder({
				word : $("#videoUrl").attr("placeholder")
			});
			$('.activity_require').css("border-left", "2px #c7c7c7 solid");
		} 
		if (index == 4) {
			$('#standby_lessonplan').show();
			$('#reference_material').show();
			$('#partake_subject').show();
			$('#confirm_main_standby').hide();
			$('#video_address').hide();
			$('.reference_material_right_h3').text('相关内容：');
			$('.activity_require').css("border-left", "2px #c7c7c7 solid");
			$('#videoUrl').val("");
			$(".sszbtip").show();
		}else{
			$(".sszbtip").hide();
		}
		$("#typeId").val(index);
		setSelectMainOrgList($("#selectCircleId").val());
	}

	// 设置主备人机构下拉框
	function setSelectMainOrgList(circleId) {
		var typeId = $("#typeId").val();
		if (circleId != null && circleId != "") {
			var schoolOptions = "";
			$("#circleUl_" + circleId).find("ol li").each(function() {
				var state = $(this).attr("data-state");
				var orgId = $(this).attr("data-id");
				var orgName = $(this).attr("data-name");
				if (state == 2 || state == 5) {
					schoolOptions += '<option id="mainOrg_' + orgId + '" value="' + orgId + '">' + orgName + '</option>';
				}
			});
			$("#circleName").html($("#circleOpt_" + circleId).html());
			$("#circleContent").show();
			if (typeId == 1 || typeId == "") {
				$("#mainUserOrgId").html('<option value="">请选择学校</option>' + schoolOptions);
				var parOrgId = $("#mainUserOrgId").parent().attr("data-orgId");
				if (parOrgId != null && parOrgId != "") {
					$("#mainUserOrgId").val(parOrgId);
				}
				$("#mainUserOrgId").trigger("chosen:updated");
				checkMainGradeList();
			}
		} else {
			$("#circleContent").hide();
			if (typeId == 1 || typeId == "") {
				$("#mainUserOrgId").html('<option value="">请选择学校</option>');
				$("#mainUserOrgId").trigger("chosen:updated");
			}
		}
	}

	// 设置编辑多选框默认选择
	function setCheckedInput(index) {
		var isAudit = $("#isAudit").val();
		if (isAudit) {
			// 年级
			var gradeIds = $("#gradeIds").val();
			if (gradeIds != null && gradeIds != "") {
				gradeIds = gradeIds.substring(1, gradeIds.length - 1);
				var gradeArr = gradeIds.split(",");
				if (gradeArr.length > 1) {
					$(gradeArr).each(function(index, value) {
						$("#grade_" + value).attr("checked", "checked");
					});
				}
			}
			if (index != 1) {
				// 学科
				var subjectIds = $("#subjectIds").val();
				if (subjectIds != null && subjectIds != "") {
					subjectIds = subjectIds.substring(1, subjectIds.length - 1);
					var subjectArr = subjectIds.split(",");
					if (subjectArr.length > 1) {
						$(subjectArr).each(function(index, value) {
							$("#subject_" + value).attr("checked", "checked");
						});
					}
				}
			}
		}
	}

	// 根据机构更新主备人年级列表
	function checkMainGradeList() {
		var phaseId = $("#phaseId").val();
		var orgId = $("#mainUserOrgId").val();
		if (phaseId != null && orgId != null) {
			$.getJSON("jy/schoolactivity/mainGradeList?phaseId=" + phaseId + "&orgId=" + orgId, function(lis) {
				if (lis) {
					$("#mainUserGradeId").html('<option value="">请选择年级</option>');
					for ( var i in lis) {
						$("#mainUserGradeId").append('<option  value="' + lis[i].id + '">' + lis[i].name + '</option>');
					}
					var editGradeId = $("#mainUserGradeId").parent().attr("data-gradeId");
					if (editGradeId != null && editGradeId != "") {
						$("#mainUserGradeId").val(editGradeId);
					}
					$("#mainUserGradeId").trigger("chosen:updated");
				}
			});
		}
	}

	// 更新主备人list
	function checkMainUserList() {
		$("#chapterId").html('<option value="">请选择课题</option>');
		$("#mainUserId").html('<option value="">请选择主备人</option>');
		$("#mainUserId").trigger("chosen:updated");
		$("#chapterId").trigger("chosen:updated");
		if ($("#mainUserSubjectId").val() != '' && $("#mainUserGradeId").val() != '' && $("#mainUserOrgId").val() != '') {
			var mid = "subjectId=" + $("#mainUserSubjectId").val() + "&gradeId=" + $("#mainUserGradeId").val() + "&orgId=" + $("#mainUserOrgId").val();
			$.getJSON("jy/schoolactivity/mainUserList?" + mid, function(data) {
				var lis = data.userSpaceList;
				if (lis.length > 0) {
					for (var i = 0; i < lis.length; i++) {
						$("#mainUserId").append('<option  value="' + lis[i].userId + '" id="zbr_' + lis[i].userId + '">' + lis[i].username + '</option>');
					}
					$("#mainUserId").trigger("chosen:updated");
					$("#chapterId").trigger("chosen:updated");
				}
			});
		}
	}

	// 更新主备课题list
	function checkChapterList() {
		$("#chapterId").html('<option value="">请选择课题</option>');
		$("#chapterId").trigger("chosen:updated");
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
					$("#chapterId").trigger("chosen:updated");
				}
			});
		}
	}

	// 取复选框值
	function getCheckboxValue() {
		if ($("#typeId").val() != 1) {
			// 学科
			var xk = ",";
			$("[name='subjects']").each(function() {
				if ($(this).is(":checked")) {
					xk += $(this).val() + ",";
				}
			});
			$("#subjectIds").val(xk == "," ? "" : xk);
		}
		// 年级
		va = ",";
		$("[name='grades']").each(function() {
			if ($(this).is(":checked")) {
				va += $(this).val() + ",";
			}
		});
		$("#gradeIds").val(va == "," ? "" : va);
	}

	/**
	 * 保存校际教研活动
	 */
	function saveSchoolActivity(parStatus) {
		var confrimStr = "此校际教研活动将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布吗？";
		if (parStatus == 0) {// 存草稿箱
			var text = $.trim($("#activityName").val());
			if (text == null || text == "") {
				alert("请您至少要输入活动主题才能存入草稿箱");
				return false;
			}
			confrimStr = "为保证活动时限的准确性，您下次发布活动的时候再设置开始时间和结束时间吧，本次就不给您记录喽！";
		} else {
			if (!$("#schActivityForm").validationEngine('validate')) {
				return false;
			}
			var txtcount = editor.count('text');
			if (txtcount == 0) {
				alert("活动要求不能为空！");
				return false;
			}
			if (txtcount > 200) {
				alert("活动要求最多可以输入200个字！");
				return false;
			}
			
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var zbObj=$("input[name='type']:checked").val();
			if(!startTime||!endTime||(zbObj==4 && !activityBetween(startTime,endTime))){
				return false;
			}
			
			editor.sync();

			// 手工验证下拉框
			if ($("#selectCircleId").val() == "") {
				alert("请选择教研圈");
				return false;
			}
			if ($("#typeId").val() == 1) {
				if ($("#mainUserOrgId").val() == "" || $("#mainUserSubjectId").val() == "" || $("#mainUserGradeId").val() == "" || $("#mainUserId").val() == "" || $("#chapterId").val() == "") {
					alert("请选择主备人及教案");
					return false;
				}
			}
		}
		getCheckboxValue();
		getUploadFileIds();
		$("#status").val(parStatus);
		if (confirm(confrimStr)) {
			waiting();
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/schoolactivity/saveSchoolActivityTbja.json",
				data : $("#schActivityForm").serialize(),
				error : function() {
					alert("请求失败！");
				},
				success : function(data) {
					var rc = data.resultCode;
					if (rc == 1) {
						window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/index";
					} else if (rc == 2) {
						alert("活动已被讨论，只能修改部分数据");
						window.location.reload();
					} else {
						alert("操作失败，请重试！");
						$("div.waittingMask").remove();
						$("div.loadWrap").remove();
						$("body").css({"overflow-x":"auto","overflow-y":"auto"})
						return false;
					}
				}
			});
		}
	}

	// 上传文件时的检查
	window.startUpload = function() {
		var i = 0;
		$("#fileFjs .files_wrap_c").each(function(index, obj) {
			i++;
		});
		if (i >= 8) {
			alert("您最多只能上传8个活动附件！");
			return false;
		}
		return true;
	}

	// 上传回调 function
	window.backUpload = function(data) {
		var resId = $("input[name=resId]").val();
		var resFile = $("#originFileName").val();
		var index = resFile.lastIndexOf('.');
		var resName = resFile.substring(0, index);
		var ext = resFile.substring(index);
		var fjStr = '<div class="files_wrap_c" id="' + resId + '" data-id="0"><div class="files_wrap_left_c"></div><div class="files_wrap_center_c"><div class="files_wrap_center_t_c">';
		fjStr += '<span title="' + resName + '">' + resName + '</span> <b>' + ext + '</b>';
		fjStr += '</div><div class="files_wrap_center_b_c">上传完成</div></div><div class="files_wrap_right_c">删除</div></div>';
		$("#fileFjs").prepend(fjStr);
		// 绑定附件删除事件
		$("#" + resId).find(".files_wrap_right_c").click(function() {
			removeFJ($(this).parent());
		});
	}

	// 删除资源
	window.removeFJ = function(obj) {
		if (confirm("确定要删除活动附件吗？")) {
			var resId = obj.attr("id");
			$("#" + resId).remove();
		}
	}
	// 获得附件Ids
	window.getUploadFileIds = function() {
		var resIds = "";
		$("#fileFjs .files_wrap_c").each(function(index, obj) {
			resIds += obj.id + ",";
		});
		$("#fileRes").val(resIds == "" ? "" : resIds);
	}
	
	function activityBetween(startDate,endDate){
		 if(startDate.length > 0){
		    var startDateTemp = startDate.split(" ");    
		    var arrStartDate = startDateTemp[0].split("-");   
		    var arrStartTime = startDateTemp[1].split(":"); 

			var endDateTemp = endDate.split(" ");    
		    var arrEndDate = endDateTemp[0].split("-");   
		    var arrEndTime = endDateTemp[1].split(":");   

			var allStartDate = new Date(arrStartDate[0], arrStartDate[1]-1, arrStartDate[2], arrStartTime[0], arrStartTime[1], 0); 
			var allEndDate = new Date(arrEndDate[0], arrEndDate[1]-1, arrEndDate[2], arrEndTime[0], arrEndTime[1], 0); 
			var between = allEndDate.getTime() - allStartDate.getTime();
			if (between > 24*3600*1000 || between < 15*60*1000) {   
		        alert("实时直播时间间隔必须大于15分钟,小于24小时!");   
		        return false;   
			} else {   
			    return true;   
			}   
		} else {   
			    return true;   
		}   

	}
});
