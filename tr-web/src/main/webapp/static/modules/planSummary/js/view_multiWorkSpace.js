define('view_multiWorkSpace',['require','jquery','hogan','common/dateFormat'],function(require){
	var $=require('jquery');
	var dateFormat = require('common/dateFormat');
	var hogan = require('hogan');
	var contentTemplate = null;
	var emptyTemplate = null;
	
	var emptyResultShow = function(){
		$('#fileView').hide();
		$('#checkedBox').hide();
		var roleId = $('#roleId').val();
		
		var roleName = '';
		if(roleId==1374){
			roleName = '年级组';
		}else if(roleId==1375){
			roleName = '学科组';
		}else if(roleId==1373){
			roleName = '备课组';
		}
		var categoryName = $('.category.check_lesson_wrap_11_act').attr('data-category')==3?'计划':'总结';
		var checkStatus = $('#checkStatus').val();
		var errorMsg='该'+roleName;
		if(checkStatus==''){
			errorMsg+='还未提交'+roleName+categoryName+'，您可以提醒他提交，或者查阅其他'+roleName+'工作';
		}else if(checkStatus==0){
			errorMsg+='没有未查阅的'+categoryName;
		}else if(checkStatus==1){
			errorMsg+='没有已查阅的'+categoryName;
		}
		
		$('#content').html(emptyTemplate.render({'errorMsg':errorMsg}));
	}
	/**
	 * 加载数据
	 */
	var loadData = function(returnData,errorCallBack){
		if(returnData.code){
			var data = returnData.data;
			var ps = data.ps
			var fileSrc='jy/scanResFile?to=true&resId='+ps.contentFileKey;
			var resType=(ps.category==1||ps.category==3)?8:9;
			var checkSrc='jy/check/lookCheckOption?flags=true&resType='+resType+'&authorId='+$('#authorId').val()+'&resId='+ps.id+'&title='+encodeURI(ps.title);
			$('#fileView').show();
			$('#checkedBox').show();
			$('#fileView').attr('src',fileSrc);
			$('#checkedBox').attr('src',checkSrc);
			var crtDttm =dateFormat.from( ps.crtDttm,'yyyy-MM-dd hh:MM:ss');
			$('#planSummaryId').val(ps.id);
			data['submitTime']=dateFormat.format(ps.submitTime,'yyyy-MM-dd');
			data['checkStatus']=ps.checkStatus==1?'已查阅':'未查阅';
			data['crtDttm']=dateFormat.format(crtDttm,'yyyy-MM-dd');
			$('#content').html(contentTemplate.render(data));
		}else{
			if(errorCallBack){
				errorCallBack(returnData.msg);
			}else{
				alert(returnData.msg);
			}
			
		}
	};
	
	//查询
	var index;
	var psId = $('#planSummaryId').val();
	var ids = $('#ids').val().split(",");
	index = ids.indexOf(psId);
	var doSearch = function(isPre,date,cantainCurrent,errorCallBack){
		/*var gradeId = $('.grade.nj_act').attr('data-gradeId');
		gradeId=gradeId==null?'':gradeId;
		var subjectId = $('.subject.primary_act1').attr('data-subjectId');
		subjectId=subjectId==null?'':subjectId;*/
		if(isPre){
			index--;
		} else{
			index++;
		}
		if(index >= ids.length){
			alert("已经是最后一篇了");
			index = ids.length-1;
			return false;
		}
		if(index < 0){
			alert("已经是第一篇了");
			index = 0;
			return false;
		}
		var checkplId = ids[index];
		var url = _WEB_CONTEXT_+'/jy/planSummaryCheck/role/'+$('#roleId').val()+'/'
		+$('#category').val()
		+'/planSummarys/'+$('#planSummaryId').val()+'/'+(isPre?'next':'pre')+''+'?checkplId='+checkplId;
		$.get(url,function(result){
			if(result&&result.result){
				loadData(result.result,errorCallBack);
			}else{
				alert('未知错误！');
			}
		},'json');
	};
	
	//初始化时间
	var initEvent = function(){
		$('#pre').click(function() {
			doSearch(true,null,null, function() {
				alert('已经是第一篇');
			});
		});
		$('#next').click(function() {
			doSearch(false,null,null, function() {
				alert('已经是最后一篇');
			});
		});
		$('.subject').click(function(){
			$('.subject').removeClass('primary_act1');
			$(this).addClass('primary_act1');
			doSearch(false,new Date().getTime(),1,function(){
				emptyResultShow();
			});
		});
		$('.grade').click(function(){
			$('.grade').removeClass('nj_act');
			$(this).addClass('nj_act');
			doSearch(false,new Date().getTime(),1,function(){
				emptyResultShow();
			});
		});
		$('#checkStatus').change(function(){
			doSearch(false,new Date().getTime(),1,function(){
				emptyResultShow();
			});
		});
		$('.category').click(function(){
			$('.category').removeClass('check_lesson_wrap_11_act');
			$(this).addClass('check_lesson_wrap_11_act');
			doSearch(false,new Date().getTime(),1,function(){
				emptyResultShow();
			});
		});
	};
	
	//初始化方法
	$(function(){
		contentTemplate = hogan.compile($("#contentTemplate").html());
		emptyTemplate = hogan.compile($("#emptyTemplate").html());
		initEvent();
		var planSummayId = $('#planSummaryId').val();
		
		$.get('./jy/planSummaryCheck/planSummary/'+planSummayId,function(result){
			if(result){
				loadData(result.result);
			}else{
				alert('未知错误!');
			}
		},'json');
	});
});