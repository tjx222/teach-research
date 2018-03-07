<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<c:if test="${empty masters}">
		<ul id="pics" class="slide-pic">
			<li>
				<a>
				<ui:photo src="" width="100%" height="100%" defaultSrc="${ctxStatic}/modules/schoolview/images/school/nopic.png" />
				</a>
			</li>
		</ul>
	</c:if>
	<c:if test="${not empty masters}">
		<ul id="pics" class="slide-pic">
			<c:forEach items="${masters}" var="master">
				<li>
					<a	data-url="jy/schoolview/show/school_survey?showId=${master.id}" onclick="opearDom(this,'_blank',false)" href="javascript:">
						<ui:photo src="${master.images}" width="100%" height="100%" defaultSrc="${ctxStatic}/modules/schoolview/images/school/nopic.png" />
						<span>${master.title}</span>
					</a> 
				</li>
			</c:forEach>
		</ul>
		<ul id="num" class="slide-li op slide-txt">
			<c:forEach items="${masters}" var="master" varStatus="status">
		         <li <c:if test="${status.index==1}">class="on"</c:if> ></li>
	         </c:forEach>
	    </ul>
	</c:if>
<script type="text/javascript">
//校长风采滑动效果
if ($("#pics").length > 0) {
    var defaultOpts = { interval: 5000, fadeInTime: 300, fadeOutTime: 200 };
    var _titles = $("ul.slide-txt li");
    var _titles_bg = $("ul.op li");
    var _bodies = $("ul.slide-pic li");
    var _count = _titles.length;
    var _current = 0;
    var _intervalID = null;
    var stop = function () { window.clearInterval(_intervalID); };
    var slide = function (opts) {
        if (opts) {
            _current = opts.current || 0;
        } else {
            _current = (_current >= (_count - 1)) ? 0 : (++_current);
        };
        _bodies.filter(":visible").fadeOut(defaultOpts.fadeOutTime, function () {
            _bodies.eq(_current).fadeIn(defaultOpts.fadeInTime);
            _bodies.removeClass("on").eq(_current).addClass("on");
        });
        _titles.removeClass("on").eq(_current).addClass("on");
        _titles_bg.removeClass("on").eq(_current).addClass("on");
    };
    var go = function () {
        stop();
        _intervalID = window.setInterval(function () { slide(); }, defaultOpts.interval);
    };
    var itemMouseOver = function (target, items) {
        stop();
        var i = $.inArray(target, items);
        slide({ current: i });
    };
    _titles.hover(function () { if ($(this).attr('class') != 'on') { itemMouseOver(this, _titles); } else { stop(); } }, go);
    _bodies.hover(stop, go);
    go();
}
</script>
