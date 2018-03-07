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
		window.parent.changeNav("区域教研");
		
		//通过身份进行筛选
		$(".class_teacher").change(function(){
			$("#spaceId").val($(this).val());
			$(".currentPage").val(1);
			$("#pageForm").submit();
		});
		
		//加载下拉框
		$(".full_year").chosen({ disable_search : true });
		$(".class_teacher").chosen({ disable_search : true });
		
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
		
		window.showOrgList = function (activityId){
			getAndSetOrgList(activityId);
			var htmlStr = "";
			for(var i=0;i<orgList.length;i++){
				htmlStr += "<b title='"+orgList[i].name+"'>"+cutStr(orgList[i].name,24,true)+"</b>";
			}
			$("#orgSpan_"+activityId).html(htmlStr);
		}
		
		window.getAndSetOrgList = function (activityId){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{'activityId':activityId},
		        url:   _WEB_CONTEXT_+"/jy/region_activity/getJoinOrgsOfActivity.json",
		        error: function () {
		            //alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	orgList = data.orgList;
		        }  
		    });	
		}
		
		//查看区域教研
		window.chakan = function(activityId,typeId){
			if(typeId==1){//同备教案
				window.open(_WEB_CONTEXT_+"/jy/teachingView/view/view_regoinActivity_jibei?id="+activityId,"_blank");
			}else if(typeId==2){//主题研讨
				window.open(_WEB_CONTEXT_+"/jy/teachingView/view/view_regoinActivity_zhuYan?id="+activityId,"_blank");
			}
		}
	}
});