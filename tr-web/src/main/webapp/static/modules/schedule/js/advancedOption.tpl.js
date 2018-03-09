define(function () {return '<div class="center-content"><ul class="content-list"><li class="content-list-item"><label class="list-item-tip">日程</label><span class="list-item-ctx"><input id="editEvent" style="*position:relative;*left:-5px;z-index:10;" class="list-item-ctx-ipt" type="text" {{#summary}}value="{{{summary}}}"{{/summary}} maxlength="50" /><label for="editEvent" class="list-item-ctx-tip" {{#summary}}style="visibility:hidden;"{{/summary}}>例如：晚八点海淀剧院 看话剧</label></span></li><li class="content-list-item" style="display:none"><label class="list-item-tip">全天</label><span class="list-item-ctx"><span id="list-item-allday" class="list-item-ctx-checkbox {{#isallday}}current{{/isallday}}"><i class="icon-ok"></i></span></span></li><li class="content-list-item" style="display:none"><label class="list-item-tip">时间</label><span class="list-item-ctx"><span id="list-item-time" class="list-item-ctx-desp {{#isallday}}current{{/isallday}}"><input id="list-item-start-time" type="text" class="list-item-ctx-date" value="{{start}}" /><span class="list-item-hour"><select id="start-hour"><option value="0:00">0:00</option><option value="0:30">0:30</option><option value="1:00">1:00</option><option value="1:30">1:30</option><option value="2:00">2:00</option><option value="2:30">2:30</option><option value="3:00">3:00</option><option value="3:30">3:30</option><option value="4:00">4:00</option><option value="4:30">4:30</option><option value="5:00">5:00</option><option value="5:30">5:30</option><option value="6:00">6:00</option><option value="6:30">6:30</option><option value="7:00">7:00</option><option value="7:30">7:30</option><option value="8:00">8:00</option><option value="8:30">8:30</option><option value="9:00">9:00</option><option value="9:30">9:30</option><option value="10:00">10:00</option><option value="10:30">10:30</option><option value="11:00">11:00</option><option value="11:30">11:30</option><option value="12:00">12:00</option><option value="12:30">12:30</option><option value="13:00">13:00</option><option value="13:30">13:30</option><option value="14:00">14:00</option><option value="14:30">14:30</option><option value="15:00">15:00</option><option value="15:30">15:30</option><option value="16:00">16:00</option><option value="16:30">16:30</option><option value="17:00">17:00</option><option value="17:30">17:30</option><option value="18:00">18:00</option><option value="18:30">18:30</option><option value="19:00">19:00</option><option value="19:30">19:30</option><option value="20:00">20:00</option><option value="20:30">20:30</option><option value="21:00">21:00</option><option value="21:30">21:30</option><option value="22:00">22:00</option><option value="22:30">22:30</option><option value="23:00">23:00</option><option value="23:30">23:30</option></select></span><span class="list-item-hour">至</span><input id="list-item-end-time" type="text" class="list-item-ctx-date" value="{{end}}" /><span class="list-item-hour"><select id="end-hour"><option value="0:00">0:00</option><option value="0:30">0:30</option><option value="1:00">1:00</option><option value="1:30">1:30</option><option value="2:00">2:00</option><option value="2:30">2:30</option><option value="3:00">3:00</option><option value="3:30">3:30</option><option value="4:00">4:00</option><option value="4:30">4:30</option><option value="5:00">5:00</option><option value="5:30">5:30</option><option value="6:00">6:00</option><option value="6:30">6:30</option><option value="7:00">7:00</option><option value="7:30">7:30</option><option value="8:00">8:00</option><option value="8:30">8:30</option><option value="9:00">9:00</option><option value="9:30">9:30</option><option value="10:00">10:00</option><option value="10:30">10:30</option><option value="11:00">11:00</option><option value="11:30">11:30</option><option value="12:00">12:00</option><option value="12:30">12:30</option><option value="13:00">13:00</option><option value="13:30">13:30</option><option value="14:00">14:00</option><option value="14:30">14:30</option><option value="15:00">15:00</option><option value="15:30">15:30</option><option value="16:00">16:00</option><option value="16:30">16:30</option><option value="17:00">17:00</option><option value="17:30">17:30</option><option value="18:00">18:00</option><option value="18:30">18:30</option><option value="19:00">19:00</option><option value="19:30">19:30</option><option value="20:00">20:00</option><option value="20:30">20:30</option><option value="21:00">21:00</option><option value="21:30">21:30</option><option value="22:00">22:00</option><option value="22:30">22:30</option><option value="23:00">23:00</option><option value="23:30">23:30</option></select></span></span></span></li><li id="repeat"  style="display:none" class="content-list-item"><label class="list-item-tip">重复</label><span class="list-item-ctx"><span id="list-item-repeat" class="list-item-ctx-checkbox {{#recurrence}}current{{/recurrence}}"><i class="icon-ok"></i></span><span id="repeat-abstract"></span><span class="repeat-abstract-edit">修改</span></span><div class="repeat-dialog"><div class="repeat-dialog-title"><span>重复</span><span title="关闭浮层" class="repeat-dialog-title-close">×</span></div><div id="repeat-content" class="repeat-dialog-content day"><ul class="repeat-dialog-context"><li><label>重复：</label><select id="repeat-unit"><option value="day" data-value="day" data-unit="天">每天</option><option value="week" data-value="week" data-unit="周">每周</option><option value="month" data-value="month" data-unit="月">每月</option><option value="year" data-value="year" data-unit="年">每年</option></select></li><li><label>频率：</label><select id="repeat-rate"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option></select><span id="unit">天</span></li><li id="rate-time"><label>时间：</label><span id="weekly" class="repeat-dialog-ctx weekly"><input id="workday_1" type="checkbox" value="1" /><label for="workday_1">一</label><input id="workday_2" type="checkbox" value="2" /><label for="workday_2">二</label><input id="workday_3" type="checkbox" value="3" /><label for="workday_3">三</label><input id="workday_4" type="checkbox" value="4" /><label for="workday_4">四</label><input id="workday_5" type="checkbox" value="5" /><label for="workday_5">五</label><input id="workday_6" type="checkbox" value="6" /><label for="workday_6">六</label><input id="workday_7" type="checkbox" value="0" /><label for="workday_7">日</label></span><span id="monthly" class="repeat-dialog-ctx monthly"><input title="按一月的某天重复" type="radio" id="monthday_1" name="monthly" value="1" /><select title="按一月的某天重复" disabled="disabled" id="select-day-num"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option></select>号<br /><input title="按一周的某天重复" type="radio" id="monthday_2" name="monthly" value="2" /><select title="按一周的某天重复" disabled="disabled" id="select-week-rank"><option value="1">第一个</option><option value="2">第二个</option><option value="3">第三个</option><option value="4">第四个</option><option value="5">最后一个</option></select>星期<select title="按一周的某天重复" disabled="disabled" id="select-week-day"><option value="1">星期一</option><option value="2">星期二</option><option value="3">星期三</option><option value="4">星期四</option><option value="5">星期五</option><option value="6">星期六</option><option value="0">星期日</option></select></span></li><li><label>结束：</label><span class="repeat-dialog-ctx"><input id="never-radio" type="radio" name="deadline" value="0" /><label for="never-radio">从不</label><br /><input id="times-radio" type="radio" name="deadline"  value="1" />发生<input id="times" disabled="disabled" title="发生次数" type="text" />次后<br /><input id="aftertime-radio" type="radio" name="deadline" value="2" />在&nbsp;&nbsp;&nbsp;<input id="aftertime" disabled="disabled" title="指定日期" type="text" /></span></li><li><span class="repeat-dialog-content-desp"><span>摘要：</span><span id="abstract"></span></span></li><li><span class="repeat-submit" id="repeat-submit">完成</span><span class="repeat-cancel" id="repeat-cancel">取消</span></li></ul></div></div></li><!--<li class="content-list-item"><label class="list-item-tip">提醒</label><span class="list-item-ctx"><div class="list-item-ctx-remind sms"><p><select autocomplete="off" class="list-item-ctx-select"><option data-value="alert">弹出提醒</option><option data-value="sms">短信提醒</option><option data-value="email">邮件提醒</option></select><span>提前&nbsp;&nbsp;</span><input type="text" class="list-item-ctx-smallipt" value="10" /><select autocomplete="off" class="list-item-ctx-select-time"><option>分钟</option><option>小时</option><option>天</option><option>周</option></select><i class="icon-remove" title="删除提醒"></i><br /><span class="overhide-info"><label class="overhide-info-label label-sms">短信</label><input class="label-sms" name="sms" type="text" /><label class="overhide-info-label label-email">电子邮件</label><input class="label-email" name="email" type="text" /></span></p></div><span class="list-item-ctx-add">增加提醒</span></span></li>--><li class="content-list-item"><label class="list-item-tip">地点</label><span class="list-item-ctx"><input id="list-item-address" class="list-item-ctx-ipt" type="text" {{#location}}value="{{{location}}}"{{/location}} maxlength="50" /></span></li><li class="content-list-item"><label class="list-item-tip" style="vertical-align:top;">详情</label><span class="list-item-ctx"><textarea id="list-item-description" class="list-item-ctx-textarea">{{{description}}}</textarea></span></li><li class="content-list-item"><label class="list-item-tip">颜色</label><span class="list-item-ctx"><span class="ctx-smallcb bg-darkblue" data-value="#43A6C6"><i class="icon-ok"></i></span><span class="ctx-smallcb bg-orange" data-value="#FE9901"><i class="icon-ok"></i></span><span class="ctx-smallcb bg-pink" data-value="#FF99C9"><i class="icon-ok"></i></span><span class="ctx-smallcb bg-blue" data-value="#73B7FE"><i class="icon-ok"></i></span><span class="ctx-smallcb bg-yellow" data-value="#F7E567"><i class="icon-ok"></i></span><span class="ctx-smallcb bg-purple" data-value="#D2A6E5"><i class="icon-ok"></i></span><span class="ctx-smallcb bg-green" data-value="#C6E061"><i class="icon-ok"></i></span></span></li><li class="content-list-item"><label class="list-item-tip"></label><span class="list-item-ctx"><span id="list-item-ctx-submit" class="list-item-ctx-submit">保存</span><span id="list-item-ctx-cancel" class="list-item-ctx-cancel">取消</span></span></li></ul></div>';});