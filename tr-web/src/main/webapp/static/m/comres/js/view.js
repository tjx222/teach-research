define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function init() { 
		$('.content_list figure').eq(0).click(function (){
			$("#iframe1").width("55%");
			var actli = $('#comresView ul li.ul_li_act');
			var planid = actli.attr("data-id");
			var userid= actli.attr("data-userId");
			var title= actli.attr("data-title");
			var type= actli.attr("data-type");
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=0&resType="+type+"&resId="+planid+"&title="+title+"&authorId="+userid+"&vn=/activity/view/tbzyComment");
			$('.look_opinion_list_wrap').show(); 
		});
		$('.close').click(function (){
	    		$('.look_opinion_list_wrap').hide();
	    		$('.mask').hide();
	    		$("#iframe1").width("100%");
	    });
		var actli = $('#comresView ul li.ul_li_act');
		var resid = actli.attr("data-resId");
		var type= actli.attr("data-type");
		var planName=actli.attr("data-name");
		$("#iframe1").attr("src","jy/scanResFile?to=true&resId="+resid);
		var url="jy/manage/res/download/"+$('#comresView ul li.ul_li_act').attr("data-resId")+"?filename="+encodeURI(planName);
		$('#dowloadpt').attr("href",url);
		$('#comresView ul li').click(function(){
			$(this).addClass('ul_li_act').siblings().removeClass('ul_li_act');
			$("#iframe1").attr("src","jy/scanResFile?to=true&resId="+$(this).attr("data-resId"));
			var planid = $(this).attr("data-id");
			var resid = $(this).attr("data-resId");
			var title= $(this).attr("data-title");
			var type= $(this).attr("data-type");
			var url="jy/manage/res/download/"+resid+"?filename="+encodeURI($(this).attr("data-name"));
			$('#dowloadpt').attr("href",url);
			$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=0&resType="+type+"&resId="+planid+"&title="+title+"&authorId="+userid+"&vn=/activity/view/tbzyComment");
		})
		//配套资源瀑布流加载下一页
		window.addData=function(data){
			var content = $("#pageContent",data).html();
			$("#pageContent").append(content);
		}
		//分类资源瀑布流加载下一页
		window.addDataType=function(data){
			var content = $("#pageContent_type",data).html();
			$("#pageContent_type").append(content);
		}
	}
});