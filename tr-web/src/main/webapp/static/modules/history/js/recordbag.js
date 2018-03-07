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
		window.parent.changeNav("成长档案袋");
		
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
		})
		//
		$('.record_wrap_each').click(function(){
			$('#rid').val($(this).attr("data-id"));
			$('#rname').val($(this).attr("data-name"));
			$('#showForm').submit();
		})
		
		//查看评论
		$('.ping').click(function() {
			var id = $(this).attr("data-id");
			var userId=$(this).attr("data-userId");
			var data = {
					url:'jy/comment/list?flags=true&flago=0&resType=7&authorId='+userId+ '&resId='+ id,
					width : 945,
					height : 514,
					title : "查看评论"
			};
			window.parent.dialog(data);
		});
		
		//查看微评
		$('.weiping').click(function() {
			var desc = $(this).attr("data-desc");
			var name=$(this).attr("data-name");
			var html=$('<div>名称：'+name+'</div><div>微评：'+desc+'</div>');
			var data = {
					content:html,
					width : 425,
					height : 244,
					title : "微评"
			};
			window.parent.dialog(data);
		});
		
		//查看
		$('.look_up').click(function() {
			var resType=$(this).attr("data-resType");
			var type=$(this).attr("data-type");
			if(resType=='0'||resType=='1'||resType=='2'){
				$('.alltype').remove();
				$('#t_title').after('<div class="cont_list alltype">教材版本：&nbsp;'+$(this).attr("data-publisher")+'</div>');
				$('#t_title').after('<div class="cont_list alltype">所属课题：&nbsp;'+$(this).attr("data-name")+'</div>');
				if(type == '2')
					$('#t_title').after('<div class="cont_list alltype">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：课后反思</div>');
				if(type == '3'){
					$('.alltype').remove();
					$('#t_title').after('<div class="cont_list alltype">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：其他反思</div>');
				}
			}
			if(resType=='4'||resType=='5'){
				$('.alltype').remove();
				$('#t_title').after('<div class="cont_list alltype">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：'+$(this).attr("data-type")+'</div>');
			}
			$('#t_title').html("资源名称：&nbsp;"+$(this).attr("data-title"));
			$('#t_time').html("撰写日期：&nbsp;"+$(this).attr("data-time"));
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