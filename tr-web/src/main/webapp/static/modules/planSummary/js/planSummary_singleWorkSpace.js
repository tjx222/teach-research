define('planSummary_singleWorkSpace', [ 'require', 'hogan', 'jquery' ], function(require) {
	var $ = require('jquery'), hogan = require('hogan');
	var contentTemplate = null;
	var doSearch = function() {
		var checkStatus = $('#checkStatus').val();
		var workSpaceId = $('#workSpaceId').val();
		var userId = $("#userId").val();
		$.get('./jy/planSummaryCheck/userSpace/' + workSpaceId
				+ '/plainSummary/list.json?&checkStatus=' + checkStatus+"&userId=" + userId,
				function(result) {
					if (result && result.result.code) {
						var data = result.result.data;
						var total = data.total;
						$('#planSummaryNum').html(total.plainNum + total.summaryNum);
						$('#planSummarySubmitNum').html(total.plainSubmitNum + total.summarySubmitNum);
						$('#planSummaryCheckedNum').html(total.plainCheckedNum+ total.summaryCheckedNum);
						data['planSummaryNum'] = total.plainNum+ total.summaryNum;
						data['planSummarySubmitNum'] = total.plainSubmitNum+ total.summarySubmitNum;
						data['planSummaryCheckedNum'] = total.plainCheckedNum+ total.summaryCheckedNum;
						if(data.planItems.length==0){
							data['noPlanItems']=true;
						}
						for(var i =0 ;i< data.planItems.length;i++){
							var item=data.planItems[i];
							item.crtDttm=item.crtDttm.substr(0,10);
						}
						if(data.summaryItems.length==0){
							data['noSummaryItems']=true;
						}
						for(var i =0 ;i< data.summaryItems.length;i++){
							var item=data.summaryItems[i];
							item.crtDttm=item.crtDttm.substr(0,10);
						}
						
						$('#content').html(contentTemplate.render(data));
					} else {
						alert(result.result.msg);
					}
				}, 'json')
	};
	
	$(function() {
		contentTemplate = hogan.compile($("#contentTemplate").html());
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
			var userSpaceId = $("#workSpaceId").val();
			var planSummayNum = $(this).attr("data-planSummayNum");
			var planSummaySubmitNum = $(this).attr("data-planSummaySubmitNum");
			var planSummayCheckedNum = $(this).attr("data-planSummayCheckedNum");
			$("#f_plannum").val(planSummayNum);
			$("#f_plansnum").val(planSummaySubmitNum);
			$("#f_plancnum").val(planSummayCheckedNum);
			$("#f_planids").val(ids);
			$("#f_plan_submit").attr("action",_WEB_CONTEXT_+'/jy/planSummaryCheck/userSpace/'+userSpaceId+"/planSummary/"+planSummaryId);
			$("#f_plan_submit").attr("method","post");
			$("#f_plan_submit").submit();
			/*var url = _WEB_CONTEXT_+'/jy/planSummaryCheck/userSpace/'+userSpaceId+"/planSummary/"+planSummaryId
				+'?planSummayNum='+planSummayNum+'&planSummaySubmitNum='+planSummaySubmitNum+'&planSummayCheckedNum='+planSummayCheckedNum
				+'&ids='+ids;
			window.open(url);*/
		});
		
	});

});