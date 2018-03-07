//局部加载
(function(jQuery) {
	$.fn.ajaxHtml = {
		data : {
			url : null
		},
		toUrl : function(url, data, id) {
			$.ajax({
				data : data,
				url : url,
				type : "POST",
				dataType : "html",
				success : function(data) {
					$("#" + id).empty();
					$("#" + id).html(data);
				},
				error : function(data) {
					// 错误显示
				},
				beforeSend : function(XMLHttpRequest) {
				}
			});
		}
	};
})(jQuery);