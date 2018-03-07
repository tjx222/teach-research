define(["require","jquery"], function (require) {
	var $ = require("jquery");
	$(function(){
		init();
	}); 
	function init() {
		/* 文本框提示语 */
		$('.ser_txt').placeholder({
			word : '输入关键词进行搜索'
		});
		//调用方法修改面包屑
		window.parent.changeNav("听课记录");
		
		//加载下拉框
		$(".school_year").chosen({ disable_search : true });
		$(".category").chosen({ disable_search : true });
		
		
		//查看评论
		$('.ping').click(function() {
			var id = $(this).attr("data-id");
			var resType = $(this).attr("data-resType");
			var userId=$(this).attr("data-userId");
			var data = {
					url:'jy/comment/list?flags=true&resType='+ resType
					+ '&authorId='+ userId+ '&resId='+ id,
					width : 945,
					height : 514,	
					title : "查看评论"
			};
			window.parent.dialog(data);
		});
		//查看回复
		$('.hui').click(function() {
			var id = $(this).attr("data-id");
			var lecturepeopleId = $(this).attr("data-lecturepeopleId");
			var teachingpeopleId=$(this).attr("data-teachingpeopleId");
			var data = {
					url:'jy/lecturereply/reply?authorId='+lecturepeopleId+'&resId='+id+'&teacherId='+teachingpeopleId+'&flags=1',
					width : 945,
					height : 514,
					title : "回复"
			};
			window.parent.dialog(data);
		});
		window.formsubmit = function(){
			if ($('.ser_txt').attr("data-flag") == '0') {
				$('.ser_txt').val("");
			}
				$("#lr_form").submit();
		}
	}
});