var messageTemplate;
var $messageInfo = {
	'startTime' : null,
	'endTime' : null
};
define([ 'require', 'jquery', 'hogan', 'jp/jquery.placeholder.min' ], function(require) {
	var jq = require('jquery');
	require('jp/jquery.placeholder.min');
	var hogan = require('hogan');
	messageTemplate = hogan.compile($('#messageTemplate').html());
	var userNameObj = {};
	/**
	 * 获取最近、的消息
	 */
	var getLatestMessages = function() {
		$('#first_page').attr('class', '');
		$('#previous_page').attr('class', '');
		$('#next_page').attr('class', '');
		$('#last_page').attr('class', '');
		getPreMessages(-1, function(data) {
			$messageInfo.startTime = data.startTime;
			$messageInfo.endTime = data.endTime;
			if (data.hasPre == 1) {
				$('#first_page').addClass('first1');
				$('#previous_page').addClass('first2');
			} else {
				$('#first_page').addClass('not_first1_act');
				$('#previous_page').addClass('not_first2_act');
			}
			if (data.hasNext == 1) {
				$('#next_page').addClass('last1');
				$('#last_page').addClass('last2');
			} else {
				$('#next_page').addClass('not_last1_act');
				$('#last_page').addClass('not_last2_act');
			}
		});
	}

	jq(function() {
		getLatestMessages();
		getComunicateDates();
		jq('.recentlyCompanions').click(function() {
			$('.recentlyCompanions.header1_dl_act').removeClass('header1_dl_act');
			$(this).addClass('header1_dl_act');
			getLatestMessages();
			getComunicateDates();
		});

		jq('.searchItem').each(function() {
			var userName = $(this).attr('data-userNameCompanion');
			userNameObj[userName] = $(this);
		});

		// 搜索
		$('#userNameSearch').click(function() {
			jq('.companions').removeClass('all_message_ol_act');
			var type = jq(this).attr('data-type');
			jq('.expmenu').hide();
			jq('#' + type).show();
			var userName = jq('#userName').val();
			if (userName == jq('#userName').attr("placeholder")) {
				userName = "";
			}
			for ( var key in userNameObj) {
				userNameObj[key].hide();
			}
			var noneSearchResult = true;
			for ( var key in userNameObj) {
				if (userName == "" || key.indexOf(userName) > -1) {
					userNameObj[key].show();
					noneSearchResult = false;
				}
			}
			if (noneSearchResult) {
				$('#noneSearchResult').show();
			} else {
				$('#noneSearchResult').hide();
			}
		});

		jq('.companions').click(function() {
			jq('.companions').removeClass('all_message_ol_act');
			jq(this).addClass('all_message_ol_act');
			jq('.searchItem').hide();
			var type = jq(this).attr('data-type');
			jq('.expmenu').hide();
			jq('#' + type).show();
		});

		$('.serch').placeholder({
			word : '输入姓名进行查找'
		});

		$("ol.expmenu li > a.header").click(function() {
			var arrow = $(this).find("span.arrow");
			if (arrow.hasClass("up")) {
				arrow.removeClass("up");
				arrow.addClass("down");
			} else if (arrow.hasClass("down")) {
				arrow.removeClass("down");
				arrow.addClass("up");
			}
			$(this).parent().find("ol.menu").slideToggle();
		});

		jq('.remo').click(function(event) {
			var userIdCompanion = $(this).attr('data-userIdCompanion');
			var userNameCompanion = $(this).attr('data-userNameCompanion');
			var url = "./jy/companion/friends/" + userIdCompanion + ".json";
			var r = confirm('您确定要删除好友' + userNameCompanion);
			if (r) {
				jq.ajax(url, {
					dataType : 'json',
					type : 'delete',
					success : function(result) {
						if (result.result.code == 1) {
							alert('删除成功');
							location.reload();
						} else {
							alert(result.result.msg);
						}
					}
				});
			}
			event.stopPropagation();// 阻止事件冒泡
		});
	});

	jq('#first_page').click(function() {
		if ($(this).hasClass('not_first1_act')) {
			return false;
		}
		$('#first_page').addClass('not_first1_act').removeClass("first1");
		$('#previous_page').addClass('not_first2_act').removeClass("first2");
		$('#next_page').removeClass('not_last1_act').addClass("last1");
		$('#last_page').removeClass('not_last2_act').addClass("last2");
		getNextMessages(0, function(data) {
			$messageInfo.startTime = data.startTime;
			$messageInfo.endTime = data.endTime;
		});
	});
	jq('#previous_page').click(function() {
		if ($(this).hasClass('not_first2_act')) {
			return false;
		}
		$('#next_page').removeClass('not_last1_act').addClass("last1");
		$('#last_page').removeClass('not_last2_act').addClass("last2");
		getPreMessages($messageInfo.startTime, function(data) {
			$messageInfo.startTime = data.startTime;
			$messageInfo.endTime = data.endTime;
			if (data.hasPre != 1) {
				$('#first_page').addClass('not_first1_act').removeClass("first1");
				$('#previous_page').addClass('not_first2_act').removeClass("first2");
			}
		});
	});
	jq('#next_page').click(function() {
		if ($(this).hasClass('not_last1_act')) {
			return false;
		}
		$('#first_page').removeClass('not_first1_act').addClass("first1");
		$('#previous_page').removeClass('not_first2_act').addClass("first2");
		getNextMessages($messageInfo.endTime, function(data) {
			$messageInfo.startTime = data.startTime;
			$messageInfo.endTime = data.endTime;
			if (data.hasNext != 1) {
				$('#next_page').addClass('not_last1_act').removeClass("last1");
				$('#last_page').addClass('not_last2_act').removeClass("last2");
			}
		});
	});
	jq('#last_page').click(function() {
		if ($(this).hasClass('not_last2_act')) {
			return false;
		}
		$('#last_page').addClass('not_last2_act').removeClass("last2");
		$('#next_page').addClass('not_last1_act').removeClass("last1");
		$('#first_page').removeClass('not_first1_act').addClass("first1");
		$('#previous_page').removeClass('not_first2_act').addClass("first2");
		getPreMessages(-1, function(data) {
			$messageInfo.startTime = data.startTime;
			$messageInfo.endTime = data.endTime;
		});
	});
});

var rnableDateAttr = [];
/**
 * 获取沟通过的所有日期
 */
function getComunicateDates() {
	var userIdCompanion = $('.recentlyCompanions.header1_dl_act').attr('data-userIdCompanion');
	var url = _WEB_CONTEXT_ + '/jy/companion/' + userIdCompanion + '/comunicateDates';
	$.ajax(url, {
		'type' : 'GET',
		'dataType' : 'json',
		'success' : function(data) {
			rnableDateAttr = data.result.data;
		}
	});
}

var onSelectDate = function() {
	var opposite = true;
	if (rnableDateAttr.length == 0) {
		opposite = false;
	}
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		skin : 'whyGreen',
		onpicked : timeChangeFunc,
		disabledDates : rnableDateAttr,
		opposite : opposite
	});
}

function timeChangeFunc(dp) {
	var d = new Date();
	d.setTime(0);
	d.setFullYear($dp.cal.newdate.y);
	d.setMonth($dp.cal.newdate.M - 1);
	d.setDate($dp.cal.newdate.d);
	d.setHours(0);
	console.log(d);
	$('#first_page').attr('class', '');
	$('#previous_page').attr('class', '');
	$('#next_page').attr('class', '');
	$('#last_page').attr('class', '');
	getNextMessages(d.getTime(), function(data) {
		$messageInfo.startTime = data.startTime;
		$messageInfo.endTime = data.endTime;
		if (data.hasPre == 1) {
			$('#first_page').addClass('first1');
			$('#previous_page').addClass('first2');
		} else {
			$('#first_page').addClass('not_first1_act');
			$('#previous_page').addClass('not_first2_act');
		}
		if (data.hasNext == 1) {
			$('#next_page').addClass('last1');
			$('#last_page').addClass('last2');
		} else {
			$('#next_page').addClass('not_last1_act');
			$('#last_page').addClass('not_last2_act');
		}
	});
}

/**
 * 获取上一页消息
 */
function getPreMessages(time, onReady) {
	var userIdCompanion = $('.recentlyCompanions.header1_dl_act').attr('data-userIdCompanion');
	var url = _WEB_CONTEXT_ + '/jy/companion/' + userIdCompanion + '/preMessages/' + time + '/' + 10;
	$.ajax(url, {
		'type' : 'GET',
		'dataType' : 'json',
		'success' : function(data) {
			var data = data.result.data;
			renderMessages(data);
			if (onReady) {
				onReady(data);
			}
		}
	});
};

/**
 * 获取下一页消息
 */
function getNextMessages(time, onReady) {
	var userIdCompanion = $('.recentlyCompanions.header1_dl_act').attr('data-userIdCompanion');
	var url = './jy/companion/' + userIdCompanion + '/nextMessages/' + time + '/' + 10;
	$.ajax(url, {
		'type' : 'GET',
		'dataType' : 'json',
		'success' : function(data) {
			var data = data.result.data;
			renderMessages(data);
			if (onReady) {
				onReady(data);
			}
		}
	});
};

/**
 * 展示消息
 */
function renderMessages(data) {
	var userNameCompanion = $('.recentlyCompanions.header1_dl_act').attr('data-userNameCompanion');
	var datalist = data.messages;
	// 标记消息发送人
	for (var i = 0; i < datalist.length; i++) {
		var item = datalist[i];
		if ($('#currentUserId').val() == item.userIdSender) {
			item['currentUser'] = true;
		} else {
			item['notCurrentUser'] = true;
		}
	}
	// 标记是否有消息
	if (datalist.length == 0) {
		$('#messagesTitle').html('');
		data['noMessages'] = true;
	} else {
		$('#messagesTitle').html('与"' + userNameCompanion + '"的留言记录');
	}
	$('#content').html(messageTemplate.render(data));
	$('#content')[0].scrollTop = 0;
}
