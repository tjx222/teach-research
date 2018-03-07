define("cal/schedule", ["require", "jquery", "./Remoter", "common/dateFormat", "./advancedOption", "hogan", "./schedule.tpl"], 
function(require) {
    function e(e, n, o) {
        v.getCalList = n,
        _ = o.lunarStart,
        x = o.lunarEnd;
        var s = c(c(".ecl-ui-date-tip")[7]).attr("data-date"),
        l = d.parseISO8601(s),
        h = new Date;
        if (l.getYear() === h.getYear() && l.getMonth() === h.getMonth()) v.currentDay = !0;
        else v.currentDay = !1;
        v.currentMonth = l.getMonth(),
        t(e);
        var u = 0 === v.eventsList.length ? !1: !0;
        c("#ibd-cal-wraper").append(g.compile(m).render({
            data: u,
            currentMonth: v.currentMonth + 1
        })),
        i(),
        a(),
        r()
    }
    function t(e) {
        v.today = d.format(new Date),
        v.eventsList = [];
        var t = c.extend(!0, [], e);
        t.sort(function(e, t) {
            return + e.start - +t.start
        });
        for (var i = t.length, n = 0; i > n; n++) {
            var r = new Date(1e3 * +t[n].start),
            a = d.format(r);
            if (r.getMonth() === v.currentMonth) t[n].time = a,
            v.eventsList.push(t[n]);
            else;
        }
        v.eventsLength = v.eventsList.length
    }
    function i() {
        b.container = c("#ibd-cal-sd"),
        b.box = c("#ibd-cal-sd-list"),
        b.titleBtn = c(".ibd-cal-sd-op-title:eq(0)"),
        b.detailBtn = c(".ibd-cal-sd-op-detail:eq(0)"),
        b.prevBtn = c(".ibd-cal-sd-prev:eq(0)"),
        b.nextBtn = c(".ibd-cal-sd-next:eq(0)"),
        b.nomoreBtn = c(".ibd-cal-sd-nomore:eq(0)")
    }
    function n() {
        c("body").delegate(".ibd-cal-sd-list-item-op-del", "click", h(function() {
            var e = c(this);
            y.remote({
                event: {
                    etag: e.attr("data-etag"),
                    uuid: e.attr("data-uuid")
                }
            })
        },
        1e3, !0)),
        c("body").delegate(".ibd-cal-sd-list-item-op-edit", "click", h(function() {
            var e = c(this);
            p.init({
                uuid: e.attr("data-uuid"),
                caluri: e.attr("data-caluri"),
                type: "edit",
                recurenceId: e.attr("data-recurenceId"),
                getCalList: v.getCalList,
                lunarStart: _,
                lunarEnd: x
            })
        },
        1e3, !0)),
        c("body").delegate(".ibd-cal-sd-new", "click", h(function(e) {
            e.preventDefault(),
            c(".ecl-ui-lunar-add-event:eq(0)")[0].click()
        },
        1e3, !0)),
        y.on("success", 
        function() {
            v.eventsEnd--,
            v.getCalList.remote({
                fromdt: Math.floor(_ / 1e3),
                todt: Math.floor(x / 1e3)
            })
        })
    }
    function r() {
        if (b.titleBtn.on("click", 
        function() {
            w = 0,
            c(this).addClass("current"),
            b.detailBtn.removeClass("current"),
            b.container.addClass("current")
        }), b.detailBtn.on("click", 
        function() {
            w = 1,
            c(this).addClass("current"),
            b.titleBtn.removeClass("current"),
            b.container.removeClass("current")
        }), b.prevBtn.on("click", 
        function() {
            var e = v.eventsStart - 1;
            if (v.eventsStart = v.eventsStart - 12 > 0 ? v.eventsStart - 12: 0, c(".ibd-cal-sd-list-item:eq(0)").removeClass("first"), o(v.eventsStart, e, "prev"), c(".ibd-cal-sd-list-item:eq(0)").addClass("first"), !v.eventsStart) c(this).css("display", "none")
        }), b.nextBtn.on("click", 
        function() {
            var e = v.eventsEnd;
            if (v.eventsEnd = v.eventsEnd + 12 > v.eventsLength ? v.eventsLength: v.eventsEnd + 12, o(e, v.eventsEnd), v.eventsEnd === v.eventsLength) c(this).css("display", "none"),
            b.nomoreBtn.css("display", "block")
        }), w) b.detailBtn.trigger("click")
    }
    function a() {
        if (v.eventsStart = 0, v.eventsEnd = 0, v.eventsLength > 12 && v.currentDay) {
            for (var e = 0, t = 0, i = 0; i < v.eventsLength; i++) if (d.format(new Date(1e3 * +v.eventsList[i].start)) === v.today) {
                e = 1,
                t = i;
                break
            }
            if (e) v.eventsStart = t,
            0 !== t && b.prevBtn.css("display", "block");
            else v.eventsStart = 0
        } else v.eventsStart = 0;
        if (v.eventsEnd = v.eventsStart + 12 > v.eventsLength ? v.eventsLength: v.eventsStart + 12, v.eventsEnd === v.eventsLength) if (b.nextBtn.css("display", "none"), v.eventsEnd - v.eventsStart > 12) b.nomoreBtn.css("display", "block");
        o(v.eventsStart, v.eventsEnd)
    }
    function o(e, t, i) {
        if ("prev" === i) for (var n = t; n >= e; n--) s(v.eventsList[n], i);
        else for (var n = e; t > n; n++) s(v.eventsList[n], i)
    }
    function s(e, t) {
        var i = c(".ibd-cal-sd-list-item"),
        n = c.extend(!0, {},
        e);
        n.startHour = u(n.start)[1],
        n.endHour = u(n.end)[1];
        var r = new Date(1e3 * +n.start);
        n.today = v.today === d.format(r) ? !0: !1,
        n.start = d.format(r, "MM.dd"),
        n.getDay = T[r.getDay()];
        var a = 0;
        if (i.each(function() {
            if (c(this).attr("data-date") == e.time) return c(this).find(".ibd-cal-sd-list-item-right:eq(0)").append(g.compile(C).render(n)),
            a = 1,
            !1;
            else return void 0
        }), !a) if (0 === i.length && (n.first = !0), "prev" === t) b.box.prepend(g.compile(S).render(n));
        else b.box.append(g.compile(S).render(n))
    }
    function l() {
        v = {
            eventsList: [],
            eventsStart: 0,
            eventsEnd: 0,
            eventsLength: 0,
            today: null,
            getCalList: null
        },
        _ = 0,
        x = 0
    }
    function h(e, t, i) {
        var n;
        return function() {
            var r = this,
            a = arguments,
            o = function() {
                if (n = null, !i) e.apply(r, a)
            },
            s = i && !n;
            if (clearTimeout(n), n = setTimeout(o, t), s) e.apply(r, a)
        }
    }
    function u(e) {
        var t = [],
        i = new Date( + (e + "000"));
        if (t[0] = d.format(i), 0 === i.getMinutes()) t[1] = i.getHours() + ":" + i.getMinutes() + "0";
        else t[1] = i.getHours() + ":" + i.getMinutes();
        return t
    }
    var c = require("jquery"),
    f = require("./Remoter"),
    d = require("common/dateFormat"),
    p = require("./advancedOption"),
    g = require("hogan"),
    m = require("./schedule.tpl"),
    y = new f("CAL_DEL"),
    v = {
        eventsList: [],
        eventsStart: 0,
        eventsEnd: 0,
        eventsLength: 0,
        today: null,
        getCalList: null
    },
    b = {},
    _ = 0,
    x = 0,
    w = 0,
    S = '<li class="ibd-cal-sd-list-item {{#first}}first{{/first}} {{#today}}today{{/today}}" data-date="{{time}}"><div class="ibd-cal-sd-list-item-left">{{start}} {{getDay}}</div><div class="ibd-cal-sd-list-item-right"><div class="ibd-cal-sd-list-item-detail clearfix"><span class="ibd-cal-sd-list-item-time">{{#isallday}}全天{{/isallday}}{{^isallday}}{{startHour}} - {{endHour}}{{/isallday}}</span><span class="ibd-cal-sd-list-item-content first"><div class="ibd-cal-sd-list-item-title" title="{{{summary}}}">{{{summary}}}</div>{{#location}}<div class="ibd-cal-sd-list-item-loc"><i class="icon-map-marker" title="地点"></i><span title="{{{location}}}">{{{location}}}</span></div>{{/location}}{{#description}}<div class="ibd-cal-sd-list-item-desc"><i class="icon-book" title="详情"></i><span title="{{{description}}}">{{{description}}}</span></div>{{/description}}<span class="ibd-cal-sd-list-item-op"><a class="ibd-cal-sd-list-item-op-del" data-uuid="{{uuid}}" data-etag="{{etag}}">删除</a><a class="ibd-cal-sd-list-item-op-edit" data-uuid="{{uuid}}" data-caluri="{{caluri}}" data-recurenceId="{{recurenceId}}">编辑 &gt;</a></span></span></div></div></li>',
    C = '<div class="ibd-cal-sd-list-item-detail clearfix"><span class="ibd-cal-sd-list-item-time">{{#isallday}}全天{{/isallday}}{{^isallday}}{{startHour}} - {{endHour}}{{/isallday}}</span><span class="ibd-cal-sd-list-item-content"><div class="ibd-cal-sd-list-item-title" title="{{{summary}}}">{{{summary}}}</div>{{#location}}<div class="ibd-cal-sd-list-item-loc"><i class="icon-map-marker" title="地点"></i><span title="{{{location}}}">{{{location}}}</span></div>{{/location}}{{#description}}<div class="ibd-cal-sd-list-item-desc"><i class="icon-book" title="详情"></i><span title="{{{description}}}">{{{description}}}</span></div>{{/description}}<span class="ibd-cal-sd-list-item-op"><a class="ibd-cal-sd-list-item-op-del" data-uuid="{{uuid}}" data-etag="{{etag}}">删除</a><a class="ibd-cal-sd-list-item-op-edit" data-uuid="{{uuid}}" data-caluri="{{caluri}}" data-recurenceId="{{recurenceId}}">编辑 &gt;</a></span></span></div>',
    T = {
        0: "周日",
        1: "周一",
        2: "周二",
        3: "周三",
        4: "周四",
        5: "周五",
        6: "周六"
    };
    return {
        init: e,
        initEvents: n,
        reset: l
    }
});