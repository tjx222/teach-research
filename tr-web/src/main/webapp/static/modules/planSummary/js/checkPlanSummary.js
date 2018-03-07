var contentTemplate = null;
$(function(){
	contentTemplate = Handlebars.compile($("#contentTemplate").html());
	// 查询信息
	doSearch();
});

var doSearch = function () {
	var gradeId = $('.grade.nj_act').attr('data-gradeId');
	var subjectId = $('.subject.primary_act').attr('data-subjectId');
	$.get('./jy/planSummaryCheck/userSpace-check-statistics/' + gradeId + '-'
			+ subjectId + ".json", function(result) {
		if (result && result.result.code) {
			$('#content').html(contentTemplate(result.data));
		} else {
			alert(result.errorMsg);
		}
	}, 'json')
}