define("common/global", ["require"], 
function(require) {
    function e() {
        var e = window.GLOBAL || {};
        for (var i in e) n.set(i, e[i]);
        r = !0
    }
    var  i = {},
    n = {
        set: function(e, t) {
            return i[e] = t,
            this
        },
        get: function(e) {
            return i[e]
        },
        clear: function() {
            return i = {},
            this
        },
        remove: function(e) {
            if (e) delete i[e];
            return this
        }
    },
    r = !1;
    return ! r && e(),
    n
});