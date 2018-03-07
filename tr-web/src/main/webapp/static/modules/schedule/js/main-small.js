define(["require", "jquery", "hogan", "zxui/Lunar", "zxui/Calendar", "zxui/Tip", "./basePopup", "./advancedOption", "./schedule", "common/dateFormat", "./Remoter", "zxui/lib"], 
function(require) {
    function e() {
        v(),
        u(),
        f(),
        c(),
        l(),
        n(),
        o(),
        s(),
        h(),
        a(),
        z.initEvents(),
        D.on("success", 
        function(e) {
            if (F.eventsList = e, F.eventsMap = {},
            F.dateEvent = {},
            F.eventsHoliday.length > 0) {
                if (x.updateStatus(), this.jumpTo && this.jumpTo != w) x.setValue(this.jumpTo),
                this.jumpTo = null
            } else {
            	if (F.eventsHoliday = [], x.updateStatus(), this.jumpTo && this.jumpTo != w)
                	x.setValue(this.jumpTo), this.jumpTo = null;
            };
            C("#ibd-cal-sd") && C("#ibd-cal-sd").remove(),
            z.init(F.eventsList, D, {
                lunarStart: j,
                lunarEnd: q
            })
        }),
        I.on("success", 
        function() {
            if (b) b.onHide(),
            b = null,
            D.remote({
                fromdt: Math.floor(j / 1e3),
                todt: Math.floor(q / 1e3)
            });
            else x.setValue(C("#add-cal-date-target").val()),
            M.closePopup()
        }),
        P.on("success", 
        function() {
            if (b) b.onHide(),
            b = null;
            M && M.closePopup(),
            D.remote({
                fromdt: Math.floor(j / 1e3),
                todt: Math.floor(q / 1e3)
            })
        })//, t();
    }
    function t() {
        var e = C("#ibd-cal-wraper"),
        t = C('<a href="#monthView" class="ecl-ui-lunar-monthview current">月视图</a>'),
        i = C('<a href="#schedule" class="ecl-ui-lunar-schedule">日程</a>'),
        n = ["monthView", "schedule"],
        r = location.hash.substr(1);
        if (t.on("click", 
        function() {
            e.removeClass("current"),
            C(this).addClass("current"),
            i.removeClass("current")
        }), i.on("click", 
        function() {
            e.addClass("current"),
            C(this).addClass("current"),
            t.removeClass("current")
        }), e.append(t).append(i), C.inArray(r, n) > 0) i.trigger("click");
        else t.trigger("click")
    }
    function i() {
        _ = setInterval(function() {
            var e = C("#add-cal-popup").val();
            if (e && e.length > 50) C("#add-cal-popup").val(e.substr(0, 50))
        },
        10)
    }
    function n() {
        C("body").delegate(".ecl-ui-tip-body-row-more", "click", 
        function(e) {
            var t = C(this);
            H.edit = T.compile(V).render({
                text: t.html()
            }),
            B.edit = T.compile(Y).render({
                content: t.attr("data-location"),
                description: t.attr("data-description"),
                recurenceId: t.attr("data-recurenceId"),
                date: t.parent().prev().find(".ecl-ui-tip-title-text").html(),
                id: t.attr("id"),
                caluri: t.attr("caluri"),
                dataEtag: t.attr("data-etag")
            }),
            g({
                type: "edit",
                pos: m(e),
                arrow: "tc",
                isRemoveOtherTips: !1,
                triggers: "ecl-ui-tip-body-row-more",
                addClassName: "ecl-ui-tip-more-detail",
                e: e,
                target: this
            })
        })
    }
    function r(e, t, i) {
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
    function a() {
        C("body").delegate("#add-cal-popup", "keydown", 
        function(e) {
            if (13 === +e.keyCode) return C(".ecl-ui-tip-body-delete").attr("disabled", "disabled"),
            C(".ecl-ui-tip-body-save").trigger("click"),
            !1;
            else return void 0
        })
    }
    function o() {
        C("body").delegate(".ecl-ui-lunar-event", "mouseover", 
        function() {
            var e = C(this);
            e.addClass("ecl-ui-lunar-event-over")
        })
    }
    function s() {
        C("body").delegate(".ecl-ui-lunar-event", "mouseout", 
        function() {
            var e = C(this);
            e.removeClass("ecl-ui-lunar-event-over")
        })
    }
    function l() {
        C("body").delegate(".ecl-ui-tip-body-advance", "click", 
        function() {
            var e = C(this),
            t = "";
            if (C(this).closest(".detail-hastitle").length > 0) t = C("#add-cal-event-popup").val(),
            M.closePopup();
            else t = C("#add-cal-popup").val(),
            b.onHide(),
            b = null;
            L.init({
                date: e.attr("data-date"),
                summary: t,
                type: "new",
                getCalList: D,
                lunarStart: j,
                lunarEnd: q
            })
        })
    }
    function h() {
        C("body").delegate(".ecl-ui-tip-body-edit", "click", 
        function() {
            var e = C(this);
            b.onHide(),
            b = null,
            L.init({
                uuid: e.attr("uuid"),
                caluri: e.attr("caluri"),
                type: "edit",
                recurenceId: e.attr("data-recurenceId"),
                getCalList: D,
                lunarStart: j,
                lunarEnd: q
            })
        })
    }
    function u() {
        C("body").delegate(".ecl-ui-tip-body-save", "click", r(function() {
            var e = C(this),
            t = C(".ecl-ui-tip-body-event");
            if (!t[t.length - 1].value) C(".ecl-ui-tip-body-error").show();
            else e.attr("disabled", "disabled"),
            C(".ecl-ui-tip-body-error").hide(),
            I.remote({
                event: {
                    start: +e.attr("date-time") / 1e3,
                    end: (parseInt(e.attr("date-time")) + 864e5) / 1e3,
                    summary: t[t.length - 1].value,
                    isallday: !0
                }
            })
        },
        1e3, !0))
    }
    function c() {
        C("body").delegate(".ecl-ui-tip-body-delete", "click", r(function() {
            var e = C(this);
            e.attr("disabled", "disabled"),
            P.remote({
                id: e.attr("id")
            })
        },
        1e3, !0))
    }
    function f() {
        C("body").delegate(".ecl-ui-tip-title-close", "click", 
        function() {
            var e = C(this);
            e.parent().parent().hide(),
            clearInterval(_)
        })
    }
    function d(e, t, i) {
        t.title = H[i.type],
        t.content = B[i.type]
    }
    function p(e, t, i) {
        if ("ecl-ui-tip-more" === i.addClassName) t.main.style.left = i.pos.x + "px",
        t.main.style.top = i.pos.y + "px";
        i.addClassName && C(t.main).addClass(i.addClassName),
        C("#add-cal-popup")[0] && C("#add-cal-popup").focus()
    }
    function g(e) {
        if (e.isRemoveOtherTips) b && b.dispose(),
        C(".ecl-ui-tip").remove();
        else C("." + e.addClassName).remove();
        b = new A({
            triggers: e.triggers,
            mode: "click",
            arrow: e.arrow,
            hideDelay: 200,
            offset: {
                x: 5,
                y: 5
            },
            onBeforeShow: function(t) {
                d(t, this, e)
            },
            onShow: function(t) {
                p(t, this, e)
            }
        }).render(),
        b.onShow(e.e),
        setTimeout(function() {
            b.show(e.target)
        },
        0)
    }
    function m(e) {
        if (e.pageX || e.pageY) return {
            x: e.pageX,
            y: e.pageY
        };
        else return {
            x: e.clientX + document.body.scrollLeft - document.body.clientLeft,
            y: e.clientY + document.body.scrollTop - document.body.clientTop
        }
    }
    function y(e) {
        var t,
        i = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/,
        n = new Date(0 / 0),
        r = i.exec(e);
        if (r) if (t = +r[2], n.setFullYear(r[1], t - 1, r[3]), t !== n.getMonth() + 1) n.setTime(0 / 0);
        return n
    }
    function v() {
        x = new E({
            value: new Date,
            main: C("#lunar")[0],
            dateClassName: "ecl-ui-date-tip",
            process: function(e, t, i) {
                var n = this,
                r = F.eventsList,
                a = F.eventsHoliday;
                if (b) b.onHide(),b = null;
                C(".ecl-ui-lunar-event", e).remove();
                C(".ecl-ui-lunar-more", e).remove();
                C.each(r, 
                function(t, r) {
                    var a = n.format(new Date( + (r.start + "000")), n.dateFormat);
                    if (i === a) {
                        if (!F.eventsMap.hasOwnProperty(r.id)) F.eventsMap[r.id] = [];
                        if (F.eventsMap[r.id] = r, !F.dateEvent.hasOwnProperty(i)) F.dateEvent[i] = [],
                        F.dateEvent[i].push(r);
                        else if ( - 1 === +C.inArray(r, F.dateEvent[i])) F.dateEvent[i].push(r);
                        if ($div = C("<div></div"), $div.attr("class", "ecl-ui-lunar-event"), $div.attr("id", r.id),$div.attr("data-etag", r.etag),$div.attr("data-location", r.location),$div.attr("data-description", r.description),$div.attr("data-recurenceId", r.recurrenceid),$div.attr("title",F.eventsMap[r.id].summary), $div.attr("caluri", r.caluri), r.color && $div.css("background", r.color),$div.html("<i class='icon_ok'></i>"),C(e).append($div[0]), C(".ecl-ui-lunar-event", C(e)).length > 1) {
                        	C(e).find(".ecl-ui-lunar-event").last().remove();
                        }
                    }
                }),
                C.each(a, 
                function(t, r) {
                    var a = n.format(new Date( + (r.start + "000")), n.dateFormat);
                    if (i === a) {
                        var o = C("<div></div>");
                        if (r.isholiday) o.attr("class", "ecl-ui-lunar-holiday"),
                        o.html('<span>假</span><div class="ecl-ui-lunar-bg"></div>');
                        else o.attr("class", "ecl-ui-lunar-weekday"),
                        o.html('<span>班</span><div class="ecl-ui-lunar-bg"></div>');
                        C(e).append(o)
                    }
                })
            }
        });
        x.on("navigate", 
        function() {
            var e = C(".ecl-ui-date-tip"),
            t = e.length;
            j = y(C(e[0]).attr("data-date")).getTime(),
            q = y(C(e[t - 1]).attr("data-date")).getTime() + 864e5,
            D.remote({
                fromdt: Math.floor(j / 1e3),
                todt: Math.floor(q / 1e3)
            })
        });
        x.render();
        C(".ecl-ui-lunar-pre").html("&lt;");
        C(".ecl-ui-lunar-next").html("&gt;");
        //C(".ecl-ui-lunar-add-event").html('<span class="plus">+</span>新建日程');
    }
    var b,
    _,
    x,
    w,
    S,
    C = require("jquery"),
    T = require("hogan"),
    E = require("zxui/Lunar"),
    k = require("zxui/Calendar"),
    A = require("zxui/Tip"),
    M = require("./basePopup"),
    L = require("./advancedOption"),
    z = require("./schedule"),
    O = require("common/dateFormat"),
    N = require("./Remoter"),
    D = new N("CAL_LIST"),
    R = new N("CAL_HOLIDAY"),
    I = new N("CAL_ADD"),
    P = new N("CAL_DEL"),
    F = {
        eventsList: [],
        dateEvent: {},
        eventsMap: [],
        eventsHoliday: []
    },
    B = {},
    H = {},
    j = 0,
    q = 0,
    W = '<div class="ecl-ui-tip-body-row ecl-ui-tip-body-row-more" style="{{color}}"  data-etag="{{dataEtag}}" id="{{id}}" caluri="{{caluri}}" data-recurenceId="{{recurenceId}}" data-location="{{dataLocation}}" data-description="{{dataDescription}}">{{{summary}}}</div>',
    Y = '<div class="ecl-ui-tip-body-row">时间：{{{date}}}</div><div class="ecl-ui-tip-body-row" title="{{{content}}}">地点：{{{content}}}</div><div class="ecl-ui-tip-body-row" title="{{{description}}}">详情：{{{description}}}</div><div class="ecl-ui-tip-body-row ecl-ui-tip-body-operation"><span class="ecl-ui-tip-body-edit" data-recurenceId="{{recurenceId}}" uuid="{{id}}" caluri="{{caluri}}">编辑&nbsp;&gt;</span><span class="ecl-ui-tip-body-delete" data-etag="{{dataEtag}}" id="{{id}}">删除</span></div>',
    V = '<span class="ecl-ui-tip-title-text">{{{text}}}</span><span class="ecl-ui-tip-title-close"></span>',
    X = '<div class="ecl-ui-tip-body-row">时间：{{value}}&nbsp;&nbsp;{{week}}</div><div class="ecl-ui-tip-body-row">日程：<input type="text" id="add-cal-popup" class="ecl-ui-tip-body-event"/></div><div class="ecl-ui-tip-body-row"><span class="ecl-ui-tip-body-save" date-time="{{dataTime}}">保存</span><span class="ecl-ui-tip-body-error">请填写内容</span></div>',
    G = '<div class="ecl-ui-tip-body-row">时间：<input type="text" value="{{dataTime}}" class="ecl-ui-tip-body-time" id="add-cal-date-target" /></div><div class="ecl-ui-tip-body-row">日程：<input type="text" maxlength="50" id="add-cal-event-popup" class="ecl-ui-tip-body-event"/></div><div class="ecl-ui-tip-body-row"><span id="ecl-ui-tip-body-save" class="ecl-ui-tip-body-save" date-time="{{startTime}}">保存</span><span class="ecl-ui-tip-body-error">请填写内容</span></div>',
    U = {
        0: "周日",
        1: "周一",
        2: "周二",
        3: "周三",
        4: "周四",
        5: "周五",
        6: "周六"
    };
    w = function() {
        var e = new Date,
        t = e.getFullYear(),
        i = (e.getMonth() + 1).toString(),
        n = e.getDate().toString(),
        r = 1 === i.length ? "0" + i: i,
        a = 1 === n.length ? "0" + n: n;
        return t + "-" + r + "-" + a
    } (),
    S = function() {
        var e = new Date;
        return U[e.getDay()]
    } (),
    e()
});