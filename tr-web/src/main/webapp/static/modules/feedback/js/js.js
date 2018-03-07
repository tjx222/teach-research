$(document).ready(function(){
	$(document).ready(function(){
		$('.feedback_l_1 h2 strong').click(function (){
			$('.feedback_l_1').hide();
			$('.feedback_l').show();
		});
		$('.feedback_list_n_l').click(function (){
			$('.feedback_l_1').show();
			$('.feedback_l').hide();
		});
		$('.feedback_l_12 div:last-child').removeClass("border");
		/**
		 * 加载右侧反馈列表
		 */
		$.fn.ajaxHtml.toUrl('./jy/feedback/feedbackList',{'page.currentPage':1},'right_1');
		
	});
});