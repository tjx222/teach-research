<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="学校资源展示页"></ui:htmlHeader>
<ui:require module="schoolview/js"></ui:require>
</head>
<body>
<span class="_sygg"></span>
<div class="wraper">
		<div class="top">
			<jsp:include page="common/top.jsp"></jsp:include>
			<div class="top4">
			</div>
		</div>
		<div class="clear"></div> 
	<div class="cont">
		<div class="clear"></div>
		<div class="cont_c1">
			<div class="cont_1_left">
				<h3>备课资源<span><a data-url="jy/schoolview/res/lessonres/getPreparationResDetailed?subject=0"
				href="javascript:" onclick="opearDom(this,'_self')">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div class="cont_3_right_cont">
					<div class="cont_3_right_cont_select">
						<ul>
							<li class="cont_3_right_cont_act" onclick="getSpecificRes(this);" restype="all">配套资源</li>
							<li onclick="getSpecificRes(this);" restype="0">教案</li>
							<li onclick="getSpecificRes(this);" restype="1">课件</li>
							<li onclick="getSpecificRes(this);" restype="fansi">反思</li>
						</ul>
					</div>
					<div class="lessoninfo">
						
					</div>
				</div>
			</div>
			<div class="cont_1_right">
				<h3>学校简介</h3>
				<div class="clear"></div>
				<div class="cont_1_right_1">
				
					<div class="cont_1_right_1_l">
						<h3><img src="${ctxStatic}/modules/schoolview/images/school/sc.png" alt=""/><span>学校概况</span></h3>
					</div>
					<div id="overviewShow" class="cont_1_right_1_r">
						
					</div>
				</div>
				<div class="cont_1_right_2">
					<div class="cont_1_right_2_l">
						<h3><img src="${ctxStatic}/modules/schoolview/images/school/xzfc.png" alt=""/><span>校长风采</span></h3>
					</div>
					<div id="masterShow" class="cont_1_right_2_r">
						
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="cont_c2">
			<div class="cont_2_left">
			<h3>教研活动<span><a data-url="jy/schoolview/res/teachactive/getSpecificAvtive?restype=1"
				href="javascript:" onclick="opearDom(this,'_self')">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div class="cont_3_right_cont1_index" style="width: 100%;">
					<ul>
						<li class="cont_3_right_cont1_act" onclick="getTeachActive(1,this);">集体备课</li>
						<li onclick="getTeachActive(2,this);">校际教研</li>
					</ul>
					<div class="actvie">
						
					</div>
				</div>
			</div>
			<div class="cont_2_right"> 
				<h3>学校要闻<span><a data-url="jy/schoolview/show/bigNewsList"
				href="javascript:" onclick="opearDom(this,'_self',false)">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div id="bignewsShow" class="cont_2_right_1">
					
					
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="cont_c5"> 
			<h3>图片新闻<span><a data-url="jy/schoolview/show/pic_new"
				href="javascript:" onclick="opearDom(this,'_blank',false)">更多<b>+</b></a></span></h3>
			<div class="clear"></div>
			<div class="slide_tab">
			
			</div>
		</div>
		<div class="cont_c3">
			<div class="cont_3_left">
				<h3>专业成长<span><a data-url="jy/schoolview/res/progrowth/getSpecificProfession?restype=1&subject=0"
				href="javascript:" onclick="opearDom(this,'_self')">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div class="cont_3_right_profession">
					<ul>
						<li class="cont_3_right_cont1_pro" onclick="getProfession(1,this);">计划总结</li>
						<li onclick="getProfession(2,this);">教学文章</li>
						<li onclick="getProfession(3,this);">听课记录</li>
					</ul>
					<div class="profession">
						
					</div>
				</div>
			</div>
			<div class="cont_3_right">
				<h3>通知公告<span><a href="jy/schoolview/show/notice?orgID=${cm.orgID}&xdid=${cm.xdid}">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div class="cont_3_right_1">
				
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="cont_c4">
			<div class="cont_4_left">
				<h3>成长档案袋<span><a data-url="jy/schoolview/res/recordbags/getSpecificRecordBag"
				href="javascript:" onclick="opearDom(this,'_self')">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div class="bags">
					
				</div>
			</div>
			<div class="cont_4_right">
				<h3>热点排行<span><a data-url="jy/schoolview/res/topres/getTopResDetailed"
				href="javascript:" onclick="opearDom(this,'_self')">更多<b>+</b></a></span></h3>
				<div class="clear"></div>
				<div class="cont_4_right_1">
					<div id="box1">
						
					</div>
					<ul id="index1">
						<li class="myLI1"></li>
						<li></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="companion_school">
		<div class="companion_school_c">
			<h3>同伴校在线<span><a data-url="jy/schoolview/res/vipschool/index/getVIPSchoolsDetailed"
				href="javascript:" onclick="opearDom(this,'_self')" >更多<b>+</b></a></span></h3>
			<div class="clear"></div> 
			<div id="companion_school_1">
				 <div class="companion_school_wrap">
				 	<div id="list_1">
				 	
				 	</div>
				 </div>
			</div> 
		</div>
	</div>
	<!--浮动   返回顶部 -->
	<div class="return_1"></div>
	<%@include file="common/bottom.jsp" %>
</div>
</body>
<script src="${ctxStatic}/modules/schoolview/js/jquery.slides.min.js"></script>
<script> 
require(['jquery','jp/jquery.cookie.min','schoolshow/index_show'],function(){
$(document).ready(function(){
	loadIndexADs();
	//横幅广告
 	loadSchoolBanner();
	//图片新闻
 	loadIndexPicNews();
	//同伴学校
  	getVIPSchools();
	//备课资源
	getPreparationRes();
	//教研活动
 	getTeachActive(1,$('.cont_3_right_cont1_act'));
	//专业成长
 	getProfession(1,$('.cont_3_right_cont1_pro'));
	//成长档案
 	getRecordBag();
	//校长风采
  	loadIndexMaster();
	//学校概况
 	loadIndexOverview();
	//学校要闻
 	loadIndexBigNews();
	//通知公告
 	loadIndexNotice();
	//图片新闻
 	loadIndexPicNews();
	//热点排行
	getTopRes();
	//滚动通知
	loadSlideNoticeIndex();
	//绑定学校资源链接点击事件（decrease）
	//bindClickEvent();
	
	$("#index1>li").mouseover(function () {
		var $this = $( this );
		var index = $this.index();
		var $ul = $('#box1')
		var liWidth = $("#box1 .plume").width();
		counter = index;
		 $ul.stop().animate( {
			'margin-left': -liWidth * index + 'px' 
		},300,function () {
		});
		$this.addClass( 'myLI1' ).siblings().removeClass( 'myLI1' );
	});
	
	$('#slides').slidesjs({
		width: 1002,
		height: 161,
		play: {
		  active: false,
		  auto: true,
		  interval: 5000,
		  effect: "slide",
		  swap: true,
		  pauseOnHover: true,
		  restartDelay: 2500
		}
	});
	
	$(".opcity").click(function(){
		var node = $(".recommend");
		var $this = $(this);
		$(".opcity").removeClass('opcity1');
		
		node.slideToggle(function(){
			var ishidden = node.is(':visible');
			if(ishidden){
				$(".opcity").addClass('opcity1');
				$this.html('<img src="${ctxStatic }/modules/schoolview/images/school/up.png" alt="">');
			}else{
				
				$this.html('<img src="${ctxStatic }/modules/schoolview/images/school/down.png" alt="">');
			}
		});
		
   });
})
});
</script> 
<script type="text/javascript">
	$(function(){  
        //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失  
        $(function () {  
            $(window).scroll(function(){  
                if ($(window).scrollTop()>1000){  
                    $(".return_1").fadeIn(1500);  
                }  
                else  
                {  
                    $(".return_1").fadeOut(1500);  
                }  
            });  
  
            //当点击跳转链接后，回到页面顶部位置  
  
            $(".return_1").click(function(){  
                $('body,html').animate({scrollTop:0},1000);  
                return false;  
            });  
        });  
    });
	</script>
	<script>
	jQuery(function ($) {
    if ($(".slide-pic").length > 0) {
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
});
	</script>
</html>