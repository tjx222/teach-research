define(
		"common/dateFormat",
		[ "require", "zxui/lib" ],
		function(require) {
			function e(e) {
				return (e > 9 ? "" : "0") + e
			}
			var t = require("zxui/lib"), i = {
				dateFormat : "yyyy-MM-dd",
				from : function(e, i) {
					if (i = i || this.dateFormat, t.isString(e)) {
						if (!e)
							return new Date;
						i = i.split(/[^yMdW]+/i), e = e.split(/\D+/);
						for (var n = {}, r = 0, a = i.length; a > r; r++)
							if (i[r]
									&& e[r]
									&& (i[r].length > 1
											&& e[r].length === i[r].length || 1 === i[r].length))
								n[i[r].toLowerCase()] = e[r];
						var o = n.yyyy || n.y || (n.yy < 50 ? "20" : "19")
								+ n.yy, s = 0 | (n.m || n.mm), l = 0 | (n.d || n.dd);
						return new Date(0 | o, s - 1, l)
					}
					return e
				},
				format : function(i, n) {
					if (n = (n || this.dateFormat).toLowerCase(), t.isString(i))
						i = this.from(i);
					var r = i.getFullYear(), a = i.getMonth() + 1, o = i
							.getDate(), h=i.getHours(),min=i.getMinutes(),sec=i.getSeconds(),s = i.getDay(), l = {
						yyyy : r,
						yy : r % 100,
						y : r,
						mm : e(a),
						m : a,
						dd : e(o),
						d : o,
						w : s,
						hh : h,
						ss : sec,
						ff : min
					};
					return n.replace(/y+|M+|d+|h+|f+|s+|W+/gi, function(e) {
						return l[e] || ""
					})
				},
				parseISO8601 : function(e) {
					var t, i = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/, n = new Date(
							0 / 0), r = i.exec(e);
					if (r)
						if (t = +r[2], n.setFullYear(r[1], t - 1, r[3]), t != n
								.getMonth() + 1)
							n.setTime(0 / 0);
					return n
				},
				isDateFormat : function(e) {
					var t = /^\s*\d{4}-\d\d-\d\d\s*$/, i = t.test(e)
							&& !isNaN(this.parseISO8601(e).getTime()) ? !0 : !1;
					return i
				}
			};
			return i
		});