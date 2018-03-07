define('planSummary_multiWorkSpace', [ 'require', 'hogan', 'jquery' ], function(require) {
	var $ = require('jquery'), hogan = require('hogan');
	var contentTemplate = null;
	//查询
	var doSearch = function () {
		var gradeId = $('.grade.nj_act').attr('data-gradeId');
		var subjectId = $('.subject.primary_act').attr('data-subjectId');
		var gradeName = $('.grade.nj_act').attr('data-gradeName');
		var subjectName = $('.subject.primary_act').attr('data-subjectName');
		var checkStatus = $('#checkStatus').val();
		var roleId = $('#roleId').val();
		$.get('./jy/planSummaryCheck/role/'+roleId+'/plainSummary/list.json?gradeId=' + (gradeId==null?'':gradeId) + '&subjectId='
				+(subjectId==null?'':subjectId) +'&checkStatus='+(checkStatus==null?'':checkStatus) , function(result) {
			if (result && result.result.code) {
				var data = result.result.data;
				data['gradeSubjectName']=gradeName+subjectName;
				if(data.planItems.length==0){
					data['noPlanItems']=true;
				}
				for(var i =0 ;i< data.planItems.length;i++){
					var item=data.planItems[i];
					item.crtDttm=item.crtDttm.substring(0,10);
					if(item.submitTime != null){
						item.submitTime=item.submitTime.substring(0,10);
					}
				}
				if(data.summaryItems.length==0){
					data['noSummaryItems']=true;
				}
				for(var i =0 ;i< data.summaryItems.length;i++){
					var item=data.summaryItems[i];
					item.crtDttm=item.crtDttm.substring(0,10);
					if(item.submitTime != null){
						item.submitTime=item.submitTime.substring(0,10);
					}
				}
				$('#content').html(contentTemplate.render(result.result.data));
			} else {
				alert(result.result.msg);
			}
		}, 'json')
	}
	$(function(){
		contentTemplate = hogan.compile($("#contentTemplate").html());
		$('.grade').click(function(){
			$('.grade').removeClass('nj_act');
			$(this).addClass('nj_act');
			doSearch();
		});
		$('.subject').click(function(){
			$('.subject').removeClass('primary_act');
			$(this).addClass('primary_act');
			doSearch();
		});
		$('#checkStatus').change(doSearch);
		// 查询信息
		doSearch();
		$('#content').delegate('.planSummary','click',function(){
			var check_lesson_dls = $(this).parent().parent().children('.check_lesson_dl');
			var ids = new Array();
			for(var i=0;i<check_lesson_dls.length;i++){
				ids[i] = $(check_lesson_dls[i]).children('.planSummary').attr('data-planSummaryId');
			}
			ids = ids.join(",");
			var planSummaryId=$(this).attr('data-planSummaryId');
			var gradeId = $('.grade.nj_act').attr('data-gradeId');
			var subjectId = $('.subject.primary_act').attr('data-subjectId');
			var checkStatus = $('#checkStatus').val();
			$("#fm_subjectId").val(subjectId);
			$("#fm_gradeId").val(gradeId);
			$("#fm_checkStatus").val(checkStatus);
			$("#fm_ids").val(ids);
			$("#fm_plan_submit").attr("action",_WEB_CONTEXT_+'/jy/planSummaryCheck/role/'+$('#roleId').val()+"/planSummary/"+planSummaryId);
			$("#fm_plan_submit").attr("method","post");
			$("#fm_plan_submit").submit();

			/*var url = _WEB_CONTEXT_+'/jy/planSummaryCheck/role/'+$('#roleId').val()+"/planSummary/"+planSummaryId
				+'?subjectId='+(subjectId==null?'':subjectId)+'&gradeId='+(gradeId==null?'':gradeId)+'&checkStatus='+(checkStatus==null?'':checkStatus)
				+'&ids='+ids;
			window.open(url);*/
		});
	});
});