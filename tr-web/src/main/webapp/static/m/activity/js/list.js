define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto; 
	 jq(function(){
			init();
			bindEvent();
		});
	function init() { 
		if(jq("#act_hour").length>0){ 
			var act_hour = new IScroll('#act_hour',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true
	      	});
		}
		if(jq("#act_modify").length>0){
			var act_modify = new IScroll('#act_modify',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true
	      	}); 
		}
	}
	
	function bindEvent(){
		jq(".act_hour_wrap_tab").find(".hour").click(function(){
			scanLessonPlanTrack(jq(this).attr("resId"));
		});
		jq(".act_modify_content1").find(".hour").click(function(){
			scanLessonPlanTrack(jq(this).attr("resId"));
		});
	}
	
	//新选项卡查看修改教案
	function scanLessonPlanTrack(resId){
		parent.window.open(_WEB_CONTEXT_+"/jy/activity/scanLessonPlanTrack?resId="+resId,"_blank");
	}
	
})