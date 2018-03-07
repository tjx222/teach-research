define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
  var jq=require("jquery");
  jq(document).ready(function(){
	  
	jq("#right_2_right_1_click").click(function(){
		jq('#idea_pop .dlog-top ul li').removeClass("white").eq(0).addClass("white");
		jq("#modify").attr("src","./jy/comment/differing?resType=1&resId=1");
		jq.blockUI({ message: jq('#idea_pop'),css:{width:'630px',height:'362px',top:'6%',left:'4%'},showOverlay:false});
		jq(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	});
	
	//显示提交box
	window.showSubmitBox = function(bookId,isSubmit){
		$("#submit_bkb").dialog({width:800,height:500});
		jq("#is_submit").removeAttr("class");
		jq("#not_submit").attr("class","upload-bottom_tab_blue");
		jq("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/myplanbook/getIsOrNotSubmitLessonPlan?bookId="+bookId+"&isSubmit="+isSubmit);
		jq("#not_submit").bind("click",function(){
			jq("#is_submit").removeAttr("class");
			jq(this).attr("class","upload-bottom_tab_blue");
			jq("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/myplanbook/getIsOrNotSubmitLessonPlan?bookId="+bookId+"&isSubmit="+0);
		});
		jq("#is_submit").bind("click",function(){
			jq(this).attr("class","upload-bottom_tab_blue");
			jq("#not_submit").removeAttr("class");
			jq("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/myplanbook/getIsOrNotSubmitLessonPlan?bookId="+bookId+"&isSubmit="+1);
		});
		console.info(123);
	};
	

	//显示查阅意见box(支持教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
	window.showScanListBox = function(planType,infoId,isUpdate,lessonId){
		jq("#checkedBox").attr("src",_WEB_CONTEXT_+"/jy/check/checklist?flags=true&resType="+planType+"&resId="+infoId);
		$("#book_option_show").find(".dialog_close").attr("onclick","closeCallBack('"+lessonId+"')");
		$("#book_option_show").dialog({width:1125,height:680});
		if(isUpdate){//更新为已查看
			jq.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{planType:planType,id:infoId},
		        url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanListAlreadyShowByType.json"
		    });
		}
	}
	//显示评论意见列表box（支持教案、课件、反思和其他反思）
	window.showCommentListBox = function(planType,planId,isUpdate,lessonId){
		jq("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=1&resType="+planType+"&resId="+planId);
	 	$("#book_review_show").find(".dialog_close").attr("onclick","closeCallBack('"+lessonId+"')");
	 	$("#book_review_show").dialog({width:1125,height:680});
	 	if(isUpdate){//更新为已查看
	 		jq.ajax({  
	 	        async : false,  
	 	        cache:true,  
	 	        type: 'POST',  
	 	        dataType : "json",  
	 	        data:{planId:planId},
	 	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setCommentListAlreadyShowByType.json"
	 	    });
	 	}
	 }
	//显示听课意见列表box
	window.showVisitListBox = function(infoId,isUpdate,lessonId){
		jq("#iframe_visit").attr("src",_WEB_CONTEXT_+"/jy/lecturerecords/teacherleclist?topicId="+infoId);
		$("#book_listen_show").find(".dialog_close").attr("onclick","closeCallBack('"+lessonId+"')");
	 	$("#book_listen_show").dialog({width:860,height:450});
	 	if(isUpdate){//更新为已查看
			jq.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:infoId},
		        url:  _WEB_CONTEXT_+"/jy/myplanbook/setVisitListAlreadyShowByType.json"
		    });
		}
	 }
  });
});