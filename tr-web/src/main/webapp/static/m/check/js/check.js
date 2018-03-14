define(["require","zepto","iscroll","placeholder"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init();
	});
    function init() { 
    	var content_bottom1_left1 = new IScroll('#content_bottom1_left1',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      		click:true,
      	});	
    	$('.content_list').click(function (){
    		$("#iframe1").width("55%");
			var term = $("#checklistobj").attr("term");
			var gradeId = $("#checklistobj").attr("gradeId");
			var subjectId = $("#checklistobj").attr("subjectId");
			var title = $("#checklistobj").attr("title");
			var resType = $("#checklistobj").attr("resType");
			var authorId = $("#checklistobj").attr("authorId");
			var resId = $("#checklistobj").attr("resId");
			$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndexM?flags=true&term="+term+"&gradeId="+gradeId+"&subjectId="+subjectId+"&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    		$(".look_opinion_list_wrap").show();
    	});
    	$('.reply').one('touchend',function (e){
       	    $(this).after('<div class="clear"></div><textarea class="textarea" rows="4" cols="52" style="width:30rem;height:6rem;margin-top:1rem;border:0.083rem #bdbdbd solid;"></textarea><input type="button" value="发送" class="btn_fs">');
	       	 var look_opinion_list = new IScroll('#look_opinion_list',{
	       		scrollbars:true,
	       		mouseWheel:true,
	       		fadeScrollbars:true, 
	       		click:true,
	       	});	
	       	$(".textarea").placeholder({ 
		    	 word: '回复'
		   	});
        });
        $('.reply1').click(function (){
       	    $(this).after('<div class="clear"></div><textarea rows="4" cols="42" class="textarea" style="width:26rem;height:6rem;margin-top:1rem;border:0.083rem #bdbdbd solid;"></textarea><input type="button" value="发送" class="btn_fs">');
       	    var look_opinion_list = new IScroll('#look_opinion_list',{
	       		scrollbars:true,
	       		mouseWheel:true,
	       		fadeScrollbars:true, 
	       		click:true,
	       	});	
       		$(".textarea").placeholder({ 
		    	 word: '回复'
		   	});
        });
        $('.close').click(function (){
    		$('.look_opinion_list_wrap').hide();
    		$('.mask').hide();
    		$("#iframe1").width("100%");
    	});
        var resid=$('#viewLesson ul li.ul_li_act').attr("data-resId");
        if($('#ja_fj ul li').length==0){
        	$('#ja_fj').hide();
        }
        if($('#kj_fj ul li').length==0){
        	$('#kj_fj').hide();
        }
        if($('#fs_fj ul li').length==0){
        	$('#fs_fj').hide();
        }
        $('#lessonName_check').text($('#viewLesson ul li.ul_li_act').text());
        $("#iframe1").attr("src","jy/scanResFile?to=true&resId="+resid);
        $('#viewLesson ul li').click(function(){
			$(this).addClass("ul_li_act").siblings().removeClass("ul_li_act");
			$(this).addClass("ul_li_act").parent().parent().siblings().find("li").removeClass("ul_li_act");
			var resid=$(this).attr("data-resId");
			$("#iframe1").attr("src","jy/scanResFile?to=true&resId="+resid);
			$('#lessonName_check').text($(this).text());
			$('#checklistobj').attr("resId",$(this).attr("data-id"));
			$('#checklistobj').attr("resType",$(this).attr("data-type"));
			$('#checklistobj').attr("title",$(this).attr("data-title"));
			reloadChecklist();
	    });
    }
    
    window.reloadChecklist = function(){
    	var term = $("#checklistobj").attr("term");
		var gradeId = $("#checklistobj").attr("gradeId");
		var subjectId = $("#checklistobj").attr("subjectId");
		var title = $("#checklistobj").attr("title");
		var resType = $("#checklistobj").attr("resType");
		var authorId = $("#checklistobj").attr("authorId");
		var resId = $("#checklistobj").attr("resId");
		$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndexM?flags=true&term="+term+"&gradeId="+gradeId+"&subjectId="+subjectId+"&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    }
    
});