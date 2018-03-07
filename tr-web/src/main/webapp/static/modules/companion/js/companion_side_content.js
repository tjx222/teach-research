var messageTemplate;
define([ 'require', 'jquery', 'jp/jquery.placeholder.min' ], function(require) {
	var jq = require('jquery');
	require('jp/jquery.placeholder.min');
	var userNameObj = {};
	jq(function() {
		jq('.recentlyCompanions dd').click(function() {
			var userIdCompanion = jq(this).parent().attr('data-userIdCompanion');
			window.open(_WEB_CONTEXT_ + '/jy/companion/companions/' + userIdCompanion);
		});

		jq('.recentlyCompanions dt').click(function() {
			jq(this).closest("a").find(".header1_dl_act").removeClass("header1_dl_act");
			jq(this).closest(".recentlyCompanions").addClass("header1_dl_act");
		});

		jq('.searchItem').each(function() {
			var userName = $(this).attr('data-userNameCompanion');
			userNameObj[userName] = $(this);
		});
		// 搜索
		jq('#userNameSearch').click(function() {
			jq('.companions').removeClass('all_message_ol_act');
			var type = jq(this).attr('data-type');
			jq('.expmenu').hide();
			jq('#' + type).show();
			var userName = jq('#userName').val();
			for ( var key in userNameObj) {
				userNameObj[key].hide();
			}
			var noneSearchResult = true;
			for ( var key in userNameObj) {
				if (key.indexOf(userName) > -1) {
					userNameObj[key].show();
					noneSearchResult = false;
				}
			}
			if (noneSearchResult) {
				jq('#noneSearchResult').show();
			} else {
				jq('#noneSearchResult').hide();
			}
		});

		jq('.companions').click(function() {
			jq('.companions').removeClass('all_message_ol_act');
			jq('.searchItem').hide();
			jq(this).addClass('all_message_ol_act');
			var type = jq(this).attr('data-type');
			jq('.expmenu').hide();
			jq('#' + type).show();
		});

		jq('.serch').placeholder({
			word : '输入姓名进行查找'
		});

		jq("ol.expmenu li > a.header").click(function() {
			var arrow = $(this).find("span.arrow");
			if (arrow.hasClass("up")) {
				arrow.removeClass("up");
				arrow.addClass("down");
			} else if (arrow.hasClass("down")) {
				arrow.removeClass("down");
				arrow.addClass("up");
			}
			jq(this).parent().find("ol.menu").slideToggle();
		});

		jq('.remo').click(function(event) {
			var userIdCompanion = jq(this).attr('data-userIdCompanion');
			var userNameCompanion = jq(this).attr('data-userNameCompanion');
			window.parent.showCompanionCancel(userIdCompanion, userNameCompanion);
		});

		jq('.add').click(function(event) {
			var userIdCompanion = jq(this).attr('data-userIdCompanion');
			var userNameCompanion = jq(this).attr('data-userNameCompanion');
			window.parent.showCompanionAdd(userIdCompanion);
		});
	});
});
