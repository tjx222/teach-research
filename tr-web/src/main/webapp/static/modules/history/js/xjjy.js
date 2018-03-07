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
		window.parent.changeNav("校际教研");
		
		//加载下拉框
		$(".full_year").chosen({ disable_search : true });
		$(".class_teacher").chosen({ disable_search : true });
		
		//通过身份进行筛选
		$(".class_teacher").change(function(){
			$("#spaceId").val($(this).val());
			$(".currentPage").val(1);
			$("#pageForm").submit(); 
		}); 
		//通过学期进行筛选
		$(".full_year").change(function(){
			var termId = $(this).val();
			if(termId!=""){
				$("#termId").attr("disabled",false);
				$("#termId").val(termId);  
			}else{
				$("#termId").attr("disabled",true); 
			}
			$(".currentPage").val(1);
			$("#pageForm").submit();
		});
		//输入关键字进行查找
		$(".ser_btn").click(function(){
			if ($('.ser_txt').attr("data-flag") == '0') {
				$("#searchStr").val("");
			}else{
				$("#searchStr").val($(".ser_txt").val());
			}
			$(".currentPage").val(1);
			$("#pageForm").submit();
		});
		//切换发起或者参与选项
		$(".change_type_event").click(function(){
			$("#typeId").val($(this).attr("typeid"));
			$(".currentPage").val(1);
			$("#pageForm").submit();
		});
		
		//查看教研进度表或者是校级教研圈
		$(".schedule_btn").click(function(){
			var typecode = $(this).attr("typecode");
			var year = $(this).attr("year");
			if(typecode == "jyjdb"){//教研进度表
				//跳转到进度表页面
				window.location.href = _WEB_CONTEXT_+"/jy/history/"+year+"/xjjy/teachschedule?spaceId="+$(this).attr("spaceId")+"&searchStr="+$(this).attr("canCreate");
				//调用方法修改面包屑
				window.parent.changeNav(_WEB_CONTEXT_+"/jy/history/"+year+"/xjjy/index","教研进度表");
			}else if(typecode == "xjjyq"){//校级教研圈
				//校级教研圈页面
				window.location.href = _WEB_CONTEXT_+"/jy/history/"+year+"/xjjy/teachcircle?spaceId="+$(this).attr("spaceId");
				//调用方法修改面包屑
				window.parent.changeNav(_WEB_CONTEXT_+"/jy/history/"+year+"/xjjy/index","校级教研圈");
			}
		});
		
		//查看校际教研
		window.chakan = function(activityId,typeId){
			if(typeId==1){//同备教案
				window.open(_WEB_CONTEXT_+"/jy/teachingView/view/view_schActivity_jibei?id="+activityId,"_blank");
			}else if(typeId==2){//主题研讨
				window.open(_WEB_CONTEXT_+"/jy/teachingView/view/view_schActivity_zhuyan?id="+activityId,"_blank");
			}
		}
		
	}
});