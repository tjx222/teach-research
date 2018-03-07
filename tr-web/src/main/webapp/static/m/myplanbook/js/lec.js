define(["require","zepto","iscroll"], function (require) {
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	var  class_table = new IScroll('.class_table',{
    		scrollbars:true,
    		mouseWheel:true,
    		fadeScrollbars:true,
    		click:true,
    	}); 
    	//查看单个听课记录和回复
    	$(".chakan_huifu").click(function(){
    		var id=$(this).attr("id");
    		var teachingpeopleId=$(this).attr("teachingpeopleId");
    		var lecturepeopleId=$(this).attr("lecturepeopleId");
    		var url = _WEB_CONTEXT_+"/jy/lecturerecords/lecturereply?teachingpeopleId="+teachingpeopleId+"&lecturepeopleId="+lecturepeopleId+"&id="+id
    		window.open(url,"_top");
    	});
    }
});