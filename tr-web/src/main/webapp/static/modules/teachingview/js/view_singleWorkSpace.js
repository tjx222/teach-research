define('view_singleWorkSpace',['require','jquery','common/dateFormat','hogan'],function(require){
	var $=require('jquery');
	var dateFormat = require('common/dateFormat');
	var hogan = require('hogan');
	var contentTemplate = null;
	var emptyTemplate = null;
	
	/**
	 * 加载数据
	 */
	var loadData = function(returnData,errorCallBack){
		if(returnData.code){
			var data = returnData.data;
			var ps = data.ps
			var fileSrc='jy/scanResFile?to=true&resId='+ps.contentFileKey;
			var resType=(ps.category==1||ps.category==3)?8:9;
			var checkSrc='jy/teachingView/view/infoIndex?flags=true&resType='+resType+'&authorId='+$('#authorId').val()+'&resId='+ps.id+'&title='+encodeURI(ps.title);
			$('#fileView').show();
			$('#checkedBox').show();
			$('#fileView').attr('src',fileSrc);
			$('#checkedBox').attr('src',checkSrc);
			$('#checkView').load(function(){
				$('#checkView')[0].contentWindow.submitSuccess(function(){
					location.href=location.href;
				});
			});
			var crtDttm =dateFormat.from( ps.crtDttm,'yyyy-MM-dd');
			if(ps.submitTime){
				var submitTime =dateFormat.from( ps.submitTime,'yyyy-MM-dd');
				data['submitTime']=dateFormat.format(submitTime,'yyyy-MM-dd');
			}
			$('#planSummaryId').val(ps.id);
			data['crtDttm']=dateFormat.format(crtDttm,'yyyy-MM-dd');
			data['checkStatus']=ps.checkStatus==1?'已查阅':'未查阅';
			$('#content').html(contentTemplate.render(data));
		}else{
			if(errorCallBack){
				errorCallBack(returnData.msg);
			}else{
				alert(returnData.msg);
			}
			
		}
	};
	//初始化方法
	$(function(){
		contentTemplate = hogan.compile($("#contentTemplate").html());
		emptyTemplate = hogan.compile($("#emptyTemplate").html());
		var planSummayId = $('#planSummaryId').val();
		var userId = $('#userId').val();
		$.get('./jy/teachingView/view/planSummaryCheck/'+planSummayId,function(result){
			if(result){
				loadData(result.result);
			}else{
				alert('未知错误!');
			}
		},'json');
	});
});