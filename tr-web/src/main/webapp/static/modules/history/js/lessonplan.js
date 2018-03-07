define(["require","jquery"], function (require) {
	var $ = require("jquery");
	$(function(){
		init();
	}); 
	function init() {
		//加载下拉框
		$(".class_teacher").chosen({ disable_search : true });
		$(".full_year").chosen({ disable_search : true });
		
		$('.resources_table').find('.look_up').click(function(){
			viewDetailInfo(this);
		});
		$('.resources_table').find('.download').click(function(){
			location.href = _WEB_CONTEXT_+"/jy/manage/res/download/"+$(this).attr("resId")+"?filename="+encodeURI($(this).attr("planName"))
		});
		
		$(".page_option").find("select").change(function(){
			$("#pageForm").submit();
		});
		
		$(".ser_btn").click(function(){
			$("#pageForm").submit();
		}); 
		
		
		$(".resources_table").find(".yue").click(function(){
			window.parent.dialog({
				url:_WEB_CONTEXT_+"/jy/check/infoIndex?flago=false&flags=false&resType="+$(this).attr("planType")+"&resId="+$(this).attr("infoId"),
				width:954,
				height:514,
				title:"查阅意见"
			})   
		});

		$(".resources_table").find(".ping").click(function(){
			window.parent.dialog({
				url:_WEB_CONTEXT_+"/jy/comment/list?flago=0&flags=true&resType="+$(this).attr("planType")+"&resId="+$(this).attr("planId"),
				width:954,
				height:514,
				title:"评论意见"
			})
		});
		
		//全选或全不选
		$('.all').click(function(){
			if($(this).is(':checked')){
				$('.check').each(function(){
					this.checked=true;
				});
			}else{
				$('.check').removeAttr('checked');
			}
		});
		//批量下载
		$('.batch_edit').click(function(){
			var resids=[];
			var count=0;
			$(".check:checked").each(function(){
				resids.push($(this).attr('resId'));
				count++;
			});
			if(count==0){
				alert("请选择下载资源");
				return false;
			}
			window.open(_WEB_CONTEXT_+ "/jy/manage/res/batchDownload"+"?resids="+resids,"_self");
		})
	}
	
	window.dosearch = function(){
		if ($('.ser_txt').attr("data-flag") == '0') {
			$('.ser_txt').val("");
		}
	}
	
	function viewDetailInfo(obj){
		var planId = $(obj).attr("planId");
		var planType = $(obj).attr("planType");
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{'planId':planId},
	        url: _WEB_CONTEXT_+"/jy/history/getDetailInfo.json",
	        error: function () {
	            alert('请求失败');  
	        },  
	        success:function(data){
	        	var list = $('.dialog_content').find('.cont_list');
	        	list.eq(0).html("资源名称："+data.infoMap.planName);
        		list.eq(1).html("类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别："+data.infoMap.typeName);
	        	list.eq(2).html("所属课题："+data.infoMap.lessonName);
	        	list.eq(3).html("教材版本："+data.infoMap.formatName);
	        	list.eq(4).html("撰写日期："+data.infoMap.date);
	        	list.eq(5).html("提交状态："+data.infoMap.submitStatus);
	        	list.eq(6).html("分享状态："+data.infoMap.shareStatus);
	        	list.eq(7).html("文件大小："+bytesToSize(data.infoMap.size));
	        	list.eq(8).html("文件格式："+data.infoMap.ext);
	        	if(planType==3){
	        		list.eq(1).show();
		        	list.eq(2).hide();
		        	list.eq(3).hide();
	        	}else if(planType==2){
	        		list.eq(1).show();
		        	list.eq(2).show();
		        	list.eq(3).show();
	        	}else{
	        		list.eq(1).hide();
		        	list.eq(2).show();
		        	list.eq(3).show();
	        	}
	        	window.parent.dialog({
					width: 420,
					height: 317,
					title:"查看",
					content:$('.dialog_content').html()
				});
	        }  
	    });
	}
	
	function bytesToSize(bytes) { 
       if (bytes == 0 || bytes == "") return '0 B';  
        var k = 1024;  
        sizes = ['KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];  
        i = Math.floor(Math.log(bytes) / Math.log(k));
        var size = (bytes / Math.pow(k, i)).toFixed(2);
	    return size + ' ' + sizes[i];   
	}  

});