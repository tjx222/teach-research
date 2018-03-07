define("cal/config", ["common/global"], 
function() {
    var e = require("common/global"),
    t = "" + window.location.protocol + "//" + window.location.host,
    exports = {
        URL: {
            ROOT: t,
            HOME_CAL: "" + t + _WEB_CONTEXT_+"/jy/schedule/listInfo",//天气
            CAL_LIST: "" + t + _WEB_CONTEXT_+"/jy/schedule/list",//日程
            CAL_HOLIDAY: "" + t+ _WEB_CONTEXT_+"/jy/schedule/list?caluri=calendars/ucvs/vacation",//假期
            CAL_ADD: "" + t + _WEB_CONTEXT_+"/jy/schedule/add",
            CAL_EDIT: "" + t + _WEB_CONTEXT_+"/jy/schedule/update",
            CAL_DEL: "" + t + _WEB_CONTEXT_+"/jy/schedule/del",
            CAL_GET: "" + t +_WEB_CONTEXT_+"/jy/schedule/get"

        }
    };
    return exports
});