define("cal/advancedOption", ["require", "jquery","jquery/jquery.json.min", "zxui/Calendar", "hogan", "./advancedOption.tpl", "common/dateFormat", "./basePopup", "./repeatDialog", "./Remoter"], 
function(require) {
    function e(e, t, i) {
        var n;
        return function() {
            var r = this,
            o = arguments,
            a = function() {
                if (n = null, !i) e.apply(r, o)
            },
            s = i && !n;
            if (clearTimeout(n), n = setTimeout(a, t), s) e.apply(r, o)
        }
    }
    function t(e) {
        return e = String(null === e || void 0 === e ? "": e),
        x.test(e) ? e.replace(m, "&").replace(y, "<").replace(v, ">").replace(b, "'").replace(_, '"').replace(m, "&") : e
    }
    function i(e) {
        return e = String(null === e || void 0 === e ? "": e),
        k.test(e) ? e.replace(w, "&amp;").replace(S, "&lt;").replace(C, "&gt;").replace(T, "&#39;").replace(E, "&quot;") : e
    }
    var n = require("jquery"),
    r = require("zxui/Calendar"),
    o = require("hogan"),
    a = require("./advancedOption.tpl"),
    s = require("common/dateFormat"),
    l = require("./basePopup"),
    h = require("./repeatDialog"),
    u = require("./Remoter"),
    c = new u("CAL_ADD"),
    f = new u("CAL_EDIT"),
    d = new u("CAL_GET"),
    p = {
        type: "new",
        repeatData: null,
        eventObj: null,
        getCalList: null,
        date: null,
        lunarStart: null,
        lunarEnd: null,
        init: function(e) {
            var t = this;
            if (t.resetData(), e) if (t.getCalList = e.getCalList, t.lunarStart = e.lunarStart, t.lunarEnd = e.lunarEnd, "edit" === e.type) t.type = "edit",
            t.eventObj.uuid = e.uuid,
            t.eventObj.caluri = e.caluri,
            t.eventObj.recurrenceid = e.recurenceId,
            t.getCalList = e.getCalList,
            d.remote({
               // recurrenceid: t.eventObj.recurrenceid,
                id: t.eventObj.uuid
              //  caluri: t.eventObj.caluri
            });
            else t.type = "new",
            t.eventObj.start = e.date,
            t.eventObj.end = e.date,
            t.eventObj.summary = i(e.summary),
            t.date = e.date,
            t.repeatData.deadlineDate = e.date,
            l.show({
                mask: !0,
                title: "",
                width: 500,
                content: o.compile(a).render(t.eventObj)
            }),
            t.bindEvent(),
            t.isAllDay(),
            h.init(t.repeatData);
            else return ! 1
        },
        initDialogData: function(e) {
            var t = this;//,e = e.event;
            t.eventObj = n.extend(!0, {},
            e),
            !t.eventObj.end && (t.eventObj.end = t.eventObj.start);
            var i = t.devideTimeStamp(t.eventObj.start),
            r = t.devideTimeStamp(t.eventObj.end);
            if (t.eventObj.start = i[0], t.eventObj.end = r[0], t.repeatData.deadlineDate = t.eventObj.end, t.eventObj.isallday) t.eventObj.startHour = "",
            t.eventObj.endHour = "";
            else t.eventObj.startHour = i[1],
            t.eventObj.endHour = r[1];
            if (e.recurrence) {
                switch (e.recurrence.frequency) {
                case "daily":
                    t.repeatData.unitValue = "day";
                    break;
                case "weekly":
                    t.repeatData.unitValue = "week",
                    t.repeatData.dataObj.weekly = n.extend(!0, [], e.recurrence.daysInWeek);
                    break;
                case "monthly":
                    t.repeatData.unitValue = "month";
                    var o = t.repeatData.dataObj;
                    if (e.recurrence.daysInMonth) o.monthly = "1",
                    o.dayNum = e.recurrence.daysInMonth[0];
                    else o.monthly = "2",
                    o.weekRank = e.recurrence.weeksInMonth[0],
                    o.weekDay = e.recurrence.daysInWeek[0];
                    break;
                case "yearly":
                    t.repeatData.unitValue = "year"
                }
                if (t.repeatData.rate = e.recurrence.interval, e.recurrence.isinfinite) t.repeatData.deadline = "0";
                else if (e.recurrence.count) t.repeatData.deadline = "1",
                t.repeatData.deadlineTime = e.recurrence.count;
                else t.repeatData.deadline = "2",
                t.repeatData.deadlineDate = t.devideTimeStamp(e.recurrence.until)[0]
            }
        },
        bindEvent: function() {
            var i = this;
            n(".ctx-smallcb").each(function() {
                var e = n(this).attr("data-value");
                if (e === i.eventObj.color) n(this).addClass("current")
            }),
            n("#editEvent").on("focus", 
            function() {
                n(this).next().css("visibility", "hidden")
            }).on("blur", 
            function() {
                if (!n(this).val()) n(this).next().css("visibility", "visible");
                i.eventObj.summary = n(this).val()
            }),
            n("#list-item-allday").on("click", 
            function() {
                if (n(this).hasClass("current")) n(this).removeClass("current"),
                n("#list-item-time").removeClass("current"),
                i.eventObj.isallday = !1,
                i.isAllDay();
                else n(this).addClass("current"),
                n("#list-item-time").addClass("current"),
                i.eventObj.isallday = !0
            }),
            n("#list-item-repeat").on("click", 
            function() {
                if (h.resetDialog(i.repeatData), n(this).hasClass("current")) n(this).removeClass("current"),
                n("#repeat-abstract").html(""),
                n("#repeat").removeClass("current");
                else n("#repeat").addClass("current"),
                n(this).addClass("current")
            }),
            n(".ctx-smallcb").on("click", 
            function() {
                n(".ctx-smallcb").removeClass("current"),
                n(this).addClass("current"),
                i.eventObj.color = n(this).attr("data-value")
            }),
            n("#list-item-ctx-submit").on("click", e(function() {
                if (n(this).attr("disabled", "disabled"), n("#list-item-repeat").hasClass("current")) i.eventObj.recurrence = p.submitRepeatData();
                else i.eventObj.recurrence = null;
                i.eventObj.location = n("#list-item-address").val(),
                i.eventObj.description = n("#list-item-description").val();
                if(n.trim(i.eventObj.summary) === ''){alert("日程内容不能为空!");n(this).removeAttr("disabled");return false;}
                if(i.eventObj.description.length > 300){alert("详情内容不能超过300字!");n(this).removeAttr("disabled");return false;}
                var e = n.extend(!0, {},
                i.eventObj);
                if (e.start = +i.combineTimeStamp(i.eventObj.start, n("#start-hour").val()) / 1e3, i.eventObj.isallday) e.end = +e.start + 86400;
                else e.end = +i.combineTimeStamp(i.eventObj.start, n("#end-hour").val()) / 1e3;
                if (e.summary = t(i.eventObj.summary), "new" === i.type) c.remote({
                    event: e
                });
                else f.remote(n.mvcParam(e))
            },
            1e3, !0)),
            n("#list-item-ctx-cancel").on("click", 
            function() {
                l.closePopup(),
                i.resetData()
            }),
            n("#end-hour").on("change", 
            function() {
                var e = n(this).val(),
                t = n("#start-hour").val();
                t = t.split(":"),
                e = e.split(":");
                var i = 100 * +t[0] + +t[1],
                r = 100 * +e[0] + +e[1];
                if (i >= r) alert("结束时间不能提前于开始时间"),
                n(this).val( + t[0] + 2 + ":" + t[1])
            }),
            n("#list-item-start-time").on("change", 
            function() {
                var e = n(this).val();
                if (!s.isDateFormat(e)) n(this).val(i.eventObj.start);
                else i.eventObj.start = e
            }),
            n("#list-item-end-time").on("change", 
            function() {
                var e = n(this).val();
                if (!s.isDateFormat(e)) n(this).val(i.eventObj.end);
                else i.eventObj.end = e
            }),
            this.datePick(),
            this.repeatShow(),
            h.submitRepeat = function() {
                var e = this;
                n("#repeat-submit").on("click", 
                function() {
                    e.setAbstract(),
                    n("#repeat-abstract").html(e.getAbstract()),
                    n("#repeat").removeClass("current"),
                    p.repeatData = n.extend(!0, p.repeatData, e.getData()),
                    p.submitRepeatData()
                })
            }
        },
        datePick: function() {
            var e = this,
            t = new r({
                monthes: 1,
                prefix: "ecl-ui-cal",
                dateFormat: "yyyy-MM-dd",
                triggers: n("#list-item-start-time")[0],
                target: n("#list-item-start-time")[0],
                onPick: function() {
                    var t = this.target;
                    if (e.eventObj.start = t.value, e.eventObj.end < t.value) n("#list-item-end-time").val(t.value)
                }
            }),
            i = new r({
                monthes: 1,
                prefix: "ecl-ui-cal",
                dateFormat: "yyyy-MM-dd",
                triggers: n("#list-item-end-time")[0],
                target: n("#list-item-end-time")[0],
                onBeforeShow: function() {
                    var t = this.target.value;
                    if (e.eventObj.start <= t) this.setRange({
                        begin: s.from(e.eventObj.start),
                        end: s.from("3023-01-01")
                    })
                },
                onPick: function() {
                    this.target
                }
            });
            t.render(),
            i.render()
        },
        addRemind: function() {
            n(".list-item-ctx-add").on("click", 
            function() {
                var e = n(this).prev(),
                t = n("<p></p>"),
                i = n('<select autocomplete="off" class="list-item-ctx-select"><option data-value="alert">弹出提醒</option><option data-value="sms">短信提醒</option><option data-value="email">邮件提醒</option></select>'),
                r = n('<select autocomplete="off" class="list-item-ctx-select-time"><option>分钟</option><option>小时</option><option>天</option><option>周</option></select>');
                i.appendTo(t),
                t.html(t.html() + '<span>提前&nbsp;&nbsp;</span><input type="text" class="list-item-ctx-smallipt" value="10" />'),
                r.appendTo(t),
                t.html(t.html() + '<i class="icon-remove" title="删除提醒"></i><br /><span class="overhide-info"><label class="overhide-info-label label-sms">短信</label><input class="label-sms" name="sms" type="text" /><label class="overhide-info-label label-email">电子邮件</label><input class="label-email" name="email" type="text" /></span>'),
                t.appendTo(e)
            })
        },
        clearRemind: function() {
            n(".list-item-ctx-remind").delegate(".icon-remove", "click", 
            function() {
                var e = n(this).closest("p");
                e.remove()
            })
        },
        remindSelect: function() {
            n(".list-item-ctx-remind").delegate(".list-item-ctx-select", "change", 
            function() {
                var e = n(this).children("option:selected"),
                t = e.attr("data-value"),
                i = n(this).closest("p");
                i.get(0).className = "list-item-ctx-remind",
                i.addClass(t)
            })
        },
        repeatShow: function() {
            var e = this;
            n(".repeat-abstract-edit").on("click", 
            function() {
                n("#repeat").addClass("current");
                var t = n(this).closest(".list-item-ctx");
                t.find(".list-item-ctx-checkbox").addClass("current"),
                h.resetDialog(e.repeatData)
            })
        },
        submitRepeatData: function() {
            var e = this,
            t = {};
            switch (e.repeatData.unitValue) {
            case "day":
                t.frequency = "daily";
                break;
            case "week":
                t.frequency = "weekly",
                t.daysInWeek = n.extend(!0, [], e.repeatData.dataObj.weekly);
                break;
            case "month":
                var i = e.repeatData.dataObj;
                if (t.frequency = "monthly", 1 === +i.monthly) t.daysInMonth = [i.dayNum];
                else t.weeksInMonth = [i.weekRank],
                t.daysInWeek = [i.weekDay];
                break;
            case "year":
                t.frequency = "yearly";
                var r = new Date(s.parseISO8601(e.eventObj.start).getTime());
                t.monthsInYear = [r.getMonth() + 1],
                t.daysInMonth = [r.getDate()]
            }
            if (t.interval = e.repeatData.rate, 0 === +e.repeatData.deadline) t.isinfinite = !0;
            else if (1 === +e.repeatData.deadline) t.isinfinite = !1,
            t.count = e.repeatData.deadlineTime;
            else if (2 === +e.repeatData.deadline) t.isinfinite = !1,
            t.until = +s.parseISO8601(e.repeatData.deadlineDate).getTime() / 1e3;
            return t
        },
        resetData: function() {
            var e = this;
            e.repeatData = n.extend(!0, {},
            g),
            e.eventObj = {
                isallday: !0,
                color: "#43A6C6"
            }
        },
        isAllDay: function() {
            var e = this,
            t = n("#start-hour"),
            i = n("#end-hour");
            if (!e.eventObj.isallday) if (e.eventObj.startHour && e.eventObj.endHour) t.val(e.eventObj.startHour),
            i.val(e.eventObj.endHour);
            else if (e.date === s.format(new Date)) {
                var r = new Date,
                o = r.getHours(),
                a = r.getMinutes();
                if (30 > +a) t.val(o + ":30"),
                i.val(o + 2 + ":30");
                else t.val(o + 1 + ":00"),
                i.val(o + 3 + ":00")
            } else t.val("9:00"),
            i.val("11:00");
            else t.val("9:00"),
            i.val("11:00")
        },
        combineTimeStamp: function(e, t) {
            var i = this,
            n = 0,
            t = t.split(":");
            if (i.eventObj.isallday) n = s.parseISO8601(e).getTime();
            else n = s.parseISO8601(e).getTime() + 60 * t[0] * 60 * 1e3 + 60 * t[1] * 1e3;
            return n
        },
        devideTimeStamp: function(e) {
            var t = [],
            i = new Date( + (e + "000"));
            if (t[0] = s.format(i), 0 === i.getMinutes()) t[1] = i.getHours() + ":" + i.getMinutes() + "0";
            else t[1] = i.getHours() + ":" + i.getMinutes();
            return t
        }
    },
    g = {
        rate: 1,
        unitValue: "day",
        dataObj: {
            weekly: [],
            monthly: "1",
            dayNum: 1,
            weekRank: 1,
            weekDay: 1
        },
        deadline: "0",
        deadlineTime: 10,
        deadlineDate: s.format(new Date)
    };
    c.on("success", 
    function() {
        l.closePopup(),
        p.getCalList.jumpTo = p.eventObj.start,
        p.getCalList.remote({
            fromdt: +p.lunarStart / 1e3,
            todt: +p.lunarEnd / 1e3
        }),
        p.resetData()
    }),
    c.on("fail", 
    function(e) {
        n("#list-item-ctx-submit").removeAttr("disabled"),
        alert(e.msg)
    }),
    f.on("success", 
    function(e) {
        l.closePopup();
        var t = n(n(".ecl-ui-date-tip")[7]).attr("data-date"),
        i = s.parseISO8601(t);
        if (new Date(1e3 * +e.start).getMonth() !== i.getMonth()) p.getCalList.jumpTo = p.eventObj.start;
        p.getCalList.remote({
            fromdt: +p.lunarStart / 1e3,
            todt: +p.lunarEnd / 1e3
        }),
        p.resetData()
    }),
    f.on("fail", 
    function(e) {
        n("#list-item-ctx-submit").removeAttr("disabled"),
        alert(e.msg)
    }),
    d.on("success", 
    function(e) {
        if (p.initDialogData(e), l.show({
            mask: !0,
            title: "",
            width: 500,
            content: o.compile(a).render(p.eventObj)
        }), p.bindEvent(), p.isAllDay(), h.init(p.repeatData), p.eventObj.recurrence) h.setAbstract(),
        n("#repeat-abstract").html(h.getAbstract())
    });
    var m = /&amp;/g,
    y = /&lt;/g,
    v = /&gt;/g,
    b = /&#39;/g,
    _ = /&quot;/g,
    x = /(&amp;)|(&lt;)|(&gt;)|(&#39;)|(&quot;)/,
    w = /&/g,
    S = /</g,
    C = />/g,
    T = /\'/g,
    E = /\"/g,
    k = /[&<>\"\']/;
    return p
});