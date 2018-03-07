/**
 * 查看活动js
 */
require([ 'jquery', 'editor' ], function($){
	var editor;
	var clickcount = 0;
	$(function() {
		$(window).scroll(function() {
			$("#kongdiv").toggle();
		});
		webEditorOptions = {
			width : "910px",
			height : "150px",
			style : 0,
			afterChange : function() {
				var txtcount = this.count('text');
				if (txtcount > 300) {
					$('#w_count').html("<font color='red'>" + txtcount + "</font>");
				} else {
					$('#w_count').html(txtcount);
				}
			}
		};
		editor = $("#discussion_content").editor(webEditorOptions)[0];

		var activityId = $("#activityId").val();
		var expertIds = $("#expertIds").val();
		var discussType = $("#discussType").val();
		var isOver = $("#isOver").val();
		var prarms = "?activityId=" + activityId + "&typeId=" + discussType + "&canReply="+(isOver == "true" ? "false" : "true")+"&expertIds=" + expertIds + "&" + Math.random();
		$("#sch_discuss_iframe").attr("src", "jy/comment/discussIndex;jsessionid="+sessionId + prarms);

		var typeId = $("#typeId").val();
		if (typeId == 4) {
			showOrgList(activityId);
		}

		// 发表讨论绑定
		$("#discussion_fabu").on("click", function() {
			if (clickcount != 0)
				return false;
			clickcount = 1;
			var txtcount = editor.count('text');
			if (txtcount == 0) {
				alert("讨论内容不能为空！");
				clickcount = 0;
				return false;
			}
			if (txtcount > 300) {
				alert("您最多可以输入300个字！");
				clickcount = 0;
				return false;
			}
			var content = $.trim(editor.text());
			discusstion_fabu(content);
		});

		// 结束活动
		$(".end_activity").click(function() {
			overActivity($(this).attr("data-id"));
		});

		// 修改教案事件
		$("#edit_lessonplan").click(function() {
			window.frames["iframe1"].fadeInOrOut($(this).prop("checked"));
			$("#saveEdit").toggle($(this).prop("checked"));
		});

		// 保存修改事件
		$("#saveEdit").click(function() {
			var isTongbei = $("#isTongbei").val();
			window.frames["iframe1"].saveLessonPlanTracks(isTongbei == "true" ? 1 : 0);
		});

		// 显示主备教案的修改教案
		$(".zb_lessonplan_cont_title ul li a").click(function() {
			$(this).closest("ul").find("a").removeClass("li_act");
			$(this).addClass("li_act");
			var planId = $(this).attr("data-planId");
			var activityId = $(this).attr("data-activityId");
			$("#iframe1").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/showLessonPlanTracks?editType=0&planId=" + planId + "&activityId=" + activityId);
			var frame = window.frames["iframe1"];
			if (window.frames["iframe1"].wordObj.IsDirty) {
				if (window.confirm("您修改的教案还未保存，是否保存当前修改的教案内容？")) {
					wantToEdit1();
					$("#saveEdit").trigger("click");
				}
			}
		});

		// 显示主备教案的意见教案集合
		$(".edit_zb_lessonplan ul li").click(function() {
			$(this).addClass("ul_li_act").siblings().removeClass("ul_li_act");
			var planId = $(this).attr("data-planId");
			var activityId = $(this).attr("data-activityId");
			$("#iframe2").attr("src", _WEB_CONTEXT_ + "/jy/schoolactivity/getYijianTrackList?planId=" + planId + "&activityId=" + activityId);
		});

		// 发送给参与人
		$("#sendToJoiners").click(function() {
			var id = $(this).attr("data-id");
			var listType = $(this).attr("data-type");
			if (confirm("发送后，所有集备参与人将收到您发送的集备教案，您需要将该课题下的所有教案都整理好后在发送。如果还未全部整理好，请继续整理。您确定发送吗？")) {
				$.ajax({
					async : false,
					cache : true,
					type : 'POST',
					dataType : "json",
					data : {
						id : id
					},
					url : _WEB_CONTEXT_ + "/jy/schoolactivity/sendToJoiner.json",
					error : function() {
						alert('操作失败，请稍后重试');
					},
					success : function(data) {
						if (data.result == "success") {
							alert("发送成功！");
							//window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/joinTbjaSchoolActivity?id=" + activityId + "&listType=" + listType;
							window.location.href = window.location.href;
						} else {
							alert("系统出错");
						}
					}
				});
			}
		});

		// 接收教案
		$("#receiveLessonPlan").click(function() {
			var id = $(this).attr("data-id");
			if (window.confirm("该操作会使您相应课题下的所有教案被覆盖，确定要接收吗？")) {
				$.getJSON(_WEB_CONTEXT_ + "/jy/myplanbook/receiveLessonPlanOfSchoolActivity", {
					'activityId' : id
				}, function(data) {
					alert(data.info);
					if (data.result == "fail3") {
						window.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/index";
					} else {
						window.location.reload();
					}
				});
			}
		});

		// 浏览修改后的整理教案
		$("#scanPlanTrack").click(function() {
			var resId = $(this).attr("data-id");
			//window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/scanLessonPlanTrack?resId=" + resId, "_blank");
			var hidenframe = $("#hidenframe");
		 	if(hidenframe!=null){
		 		hidenframe.attr("src",_WEB_CONTEXT_ + "/jy/schoolactivity/scanLessonPlanTrack?resId=" + resId);
		 	}
		});

		// 刷新iframe
		window.frushIframe = function() {
			if ($("#isTongbei").val() == "true") {
				$("#iframe3").prop("src", $("#iframe3").prop("src"));
				alert('整理的教案已保存成功，可以到下方的“修改后的主备教案”中查看哦！');
			} else {
				$("#iframe2").prop("src", $("#iframe2").prop("src"));
				alert('修改的教案已保存成功，可以到下方的“参与人修改留痕”中查看哦！');
			}
		}
	});
	$(document).ready(function(){ 
		var li1 = $(".in_reconsideration_see_title_box .ul_li ");
		var window1 = $(".out_reconsideration_see_title_box ul");
		var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
		var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
		window1.css("width", li1.length*88+"px");  
		if(li1.length >= 9){
			left1.show();
			right1.show();
		}else{
			left1.css({"visibility":"hidden"});
			right1.css({"visibility":"hidden"});
		} 
		var lc1 = 0;
		var rc1 = li1.length-9; 
		left1.click(function(){ 
			if (lc1 < 1) {
				return;
			}
			lc1--;
			rc1++;
			window1.animate({left:'+=88px'}, 500);  
		});

		right1.click(function(){
			if (rc1 < 1){
				return;
			}
			lc1++;
			rc1--;
			window1.animate({left:'-=88px'}, 500); 
		});
	});
	// 结束活动
	var overActivity = function(activityId) {
		if (window.confirm("您确定要结束吗？结束后，所有人将不能参与此教研活动！")) {
			$.getJSON(_WEB_CONTEXT_ + "/jy/schoolactivity/overActivity", {
				'id' : activityId
			}, function(data) {
				if (data.result == "success") {
					window.location.reload();
				} else {
					alert("系统出错");
				}
			});
		}
	}

	function wantToEdit1() {
		$("#edit_lessonplan").prop("checked", true);
		window.frames["iframe1"].fadeInOrOut(true);
		$("#saveEdit").toggle(true);
	}

	// 直播课堂下获取加入结构信息
	var showOrgList = function(activityId) {
		$.getJSON(_WEB_CONTEXT_ + "/jy/schoolactivity/getJoinOrgsOfActivity", {
			'activityId' : activityId
		}, function(orgList) {
			var orgCount = 0;
			if (orgList != null) {
				var htmlStr = "";
				orgCount = orgList.length;
				for (var i = 0; i < orgCount; i++) {
					htmlStr += "<li title='" + orgList[i].name + "'>" + orgList[i].name + "</li>";
				}
				$(".partake_school").html('学校(' + orgCount + ')<div class="school_info"><ul>' + htmlStr + '</ul></div>');
			}
		});
	}

	/**
	 * 发布讨论
	 */
	var discusstion_fabu = function(content) {
		$("#content_hidden").val(content);
		$.ajax({
			type : "post",
			dataType : "json",
			url : _WEB_CONTEXT_ + "/jy/comment/discussSave",
			data : $("#addDiscuss").serialize(),
			success : function(data) {
				if (data.isSuccess) {
					if (data.msg == "ok") {
						editor.html("");
						// 刷新讨论列表数据
						$("#sch_discuss_iframe").prop("src", $("#sch_discuss_iframe").prop("src"));
					} else if (data.msg == "isOver") {
						alert("活动已经结束，不能再发表讨论！");
						location.href = window.location.href;
					}
				} else {
					alert("保存失败!");
				}
				clickcount = 0;
			}
		});
	}
	
	window.schKjSave = function(data){
		if(data.code==0){
			$("#planName").val(data.filename.split(".")[0]);
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/schoolactivity/schKjSave.json",
				data:$("#schKjSaveForm").serialize(),
				success:function(data){
					//刷新对应的整理列表
					if($("#editType").val() == 0){
						$("#iframe2").prop("src", $("#iframe2").prop("src"));
					}else if($("#editType").val() == 1){
						$("#iframe3").prop("src", $("#iframe3").prop("src"));
					}
					$("#originFileName_file_process").html("");
				}
			});
		}
	}
	
});