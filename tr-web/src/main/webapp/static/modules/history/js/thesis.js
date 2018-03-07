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
		window.parent.changeNav("教学文章");
		
		//加载下拉框
		$(".school_year").chosen({ disable_search : true });
		$(".category").chosen({ disable_search : true });
		
		//全选或全不选
		$('#selectAll').click(function(){
			if($(this).is(':checked')){
				$('.check').each(function(){
					this.checked=true;
				});
			}else{
				$('.check').removeAttr('checked');
			}
		});
		$(".check").click(function(){
			var size = $(".check").size();
			var checkedSize = $(".check:checked").size();
			if(checkedSize==size){
				$('#selectAll').prop("checked",true);
			}else{
				$('#selectAll').prop("checked",false);
			}
		});
		//批量下载
		$('.batch_edit').click(function(){
			var resids=[];
			var count=0;
			$(".check:checked").each(function(){
				resids.push($(this).attr('data-resid'));
				count++;
			});
			if(count==0){
				alert("请选择下载资源");
				return false;
			}
			window.open(_WEB_CONTEXT_+ "/jy/manage/res/batchDownload"+"?resids="+resids,"_self");
		});
		//查看评论
		$('.ping').click(function() {
			var id = $(this).attr("data-id");
			var userId=$(this).attr("data-userId");
			var data = {
					url:'jy/comment/list?flags=true&flago=0&resType=10&authorId='+ userId+ '&resId='+ id,
					width : 945,
					height : 514,
					title : "查看评论"
			};
			window.parent.dialog(data);
		});
		
		//查看
		$('.look_up').click(function() {
			var share="";
			if($(this).attr("data-isShare")==0)
				share="未分享";
			else
				share="已分享";
			$('#t_title').html("资源名称：&nbsp;"+$(this).attr("data-title"));
			$('#t_type').html("类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：&nbsp;"+$(this).attr("data-type"));
			$('#t_time').html("撰写日期：&nbsp;"+$(this).attr("data-time"));
			$('#t_isShare').html("分享状态：&nbsp;"+share);
			$('#t_size').html("文件大小：&nbsp;"+bytesToSize($(this).attr("data-size")));
			$('#t_ext').html("文件格式：&nbsp;"+$(this).attr("data-ext"));
			var data = {
					content: $('#t_view').html(),
					width : 420,
					height : 317,
					title : "查看"
			};
			window.parent.dialog(data);
		});
		function bytesToSize(bytes) { 
	       if (bytes == 0 || bytes == "") return '0 B';  
	        var k = 1024;  
	        sizes = ['B','KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];  
	        i = Math.floor(Math.log(bytes) / Math.log(k));
	        var size = (bytes / Math.pow(k, i)).toFixed(2);
		    return size + ' ' + sizes[i];   
		}
		 window.formsubmit = function(){
			if ($('.ser_txt').attr("data-flag") == '0') {
				$('.ser_txt').val("");
			}
				$("#selectForm").submit();
		}
		
	}
	
});