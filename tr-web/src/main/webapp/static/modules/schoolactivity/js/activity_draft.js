/**
 * 草稿箱js
 */
require([ 'jquery' ], function($) {
	$(function() {
		// 删除教研活动的草稿箱数据
		$(".delete_btn").click(function() {
			var id = $(this).closest("tr").attr("data-id");
			if (confirm("您确认要删除该草稿吗？")) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/schoolactivity/delSchoolActivity.json",
					data : {
						"id" : id
					},
					success : function(data) {
						if (data.isOk) {
							window.parent.activityIndex.setdrafnum();
							alert("删除成功！");
							// 刷新页面
							window.location.reload();
						} else {
							alert("删除出错！");
						}
					}
				});
			}
		});

		// 修改校际教研活动
		$(".continue_edit_btn,.td_name").click(function() {
			var activityId = $(this).closest("tr").attr("data-id");
			var typeId = $(this).closest("tr").attr("data-typeId");
			window.parent.location.href = _WEB_CONTEXT_ + "/jy/schoolactivity/editActivity?id=" + activityId + "&typeId=" + typeId;
		});
	});
});