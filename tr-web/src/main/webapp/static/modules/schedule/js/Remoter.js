define("cal/Remoter", ["require", "jquery", "saber-emitter", "cal/config", "common/global"], 
function(require) {
    function e(e) {
        return "[object Function]" === Object.prototype.toString.call(e)
    }
    function t(e) {
        if (this.url = e, !r.URL[e]) throw ["[err]", "url:", e, "undefined"].join(" ")
    }
    var i = require("jquery"),
    n = require("saber-emitter"),
    r = require("cal/config"),
    a = require("common/global");
    return n.mixin = function(t) {
        e(t) && (t = t.prototype);
        var i = n.prototype;
        for (var r in i) if (i.hasOwnProperty(r)) t[r] = i[r]
    },
    t.hooks = {
        token: a.get("token") || "default"
    },
    t.prototype.remote = function(e) {
        var n = this,
        e = e || {};
        if (/EDIT|ADD|DEL/.test(n.url)) var e = i.extend({},
        t.hooks, e);
        return i.post(r.URL[n.url], e, 
        function(e) {
            if (1 !== +e.code)
            	if (n.listeners("fail").length > 0)
            		n.emit("fail", e);
            else if (302 === +e.code) window.location.href = e.statusInfo || r.URL.ROOT;
            else;
            else n.emit("success", e.data)
        })
    },
    t.prototype.jsonp = function(e) {
        var t = this;
        i.getJSON(r.URL[t.url], e || {},
        function(e) {
            if (0 !== +e.code) t.listeners("fail").length > 0 ? t.emit("fail", e) : alert(e.msg || "网络连接失败，请重试");
            else t.emit("success", e.data)
        })
    },
    n.mixin(t),
    t
});