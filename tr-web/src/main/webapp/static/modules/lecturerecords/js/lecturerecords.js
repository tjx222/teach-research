$().ready(function(){
	$('.zx1').click(function (){
		$('.school').slideToggle();
	})
	$('body').on("click",function(e){
		if($(e.target).attr("class")!="zx1"){
			 var target = $(e.target); 
	         $(".school").hide(); 
		}
	})
	$('.zx').click(function (){
		$('.school').slideToggle();
	})
	$('.close').click(function (){
		window.location.reload(); 
	})
});

/**
 * 调到撰写校外听课记录页面
 */
function writeLectureRecordsOuteInput(){
	window.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/writeLectureRecordsOuteInput?addflag=true";
}

/**
 * 调到校内听课记录-找人页面
 */
function findPeople(){
	window.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/findpeople";
}

/**
 * 查看一个详细听课记录
 * @param obj
 */
function seetopic(obj){
	var id=$(obj).parents("tr").attr("id");
	window.open(_WEB_CONTEXT_+"/jy/lecturerecords/seetopic?id="+id, "_blank");
}
/**
 * 查看一个详细听课记录
 * @param obj
 */
function jxgljlSeetopic(obj){
	var id=$(obj).parents("tr").attr("id");
	window.location.href=_WEB_CONTEXT_+"/jy/managerecord/lecView?id="+id;
}

/**
 * 分享听课记录或者取消听课记录
 * @param obj
 */
function changeShare(obj){
	var topic=$(obj).attr("topic");
	var id=$(obj).parents("tr").attr("id");
	var state=$(obj).attr("title");
	if(state=="分享"){
		var tipStr = "您确定要分享“"+topic+"”听课记录吗？分享成功后，您的小伙伴们就可以看到喽！";
		$("#dialog_tip .dialog_title").html("分享");
		$("#dialog_tip .del_info").html(tipStr);
		
	}else if(state=="取消分享"){
		var tipStr = "您确定要取消分享分享“"+topic+"”听课记录吗？";
		$("#dialog_tip .dialog_title").html("取消分享");
		$("#dialog_tip .del_info").html(tipStr);
	}
	$("#dialog_tip .confirm").click(function(){
		updateState(id, state,obj);
	})
	$("#dialog_tip").dialog({width:500,height:250});
}

/**
 * 删除听课记录
 * @param obj
 */
function deletelecture(obj){
	var topic=$(obj).attr("topic");
	var id=$(obj).parents("tr").attr("id");
	var state=$(obj).attr("title");
	if(state=="删除"){
		var tipStr = "您确定要删除“"+topic+"”听课记录吗？";
		$("#dialog_tip .dialog_title").html("删除");
		$("#dialog_tip .del_info").html(tipStr);
		$("#dialog_tip .confirm").click(function(){
			updateState(id, state,obj);
		})
		$("#dialog_tip").dialog({width:500,height:250});
	}
}

/**
 * 修改听课记录状态
 * @param id
 * @param state
 * @param obj
 */
function updateState(id,state,obj){
	$.ajax({    
	    url:_WEB_CONTEXT_+'/jy/lecturerecords/changeShare',// 跳转到 action    
	    data:{    
	        id : id,
	        state:state
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	var deleteFlag=$(data).find("#isdelete").val();//查找删除标记
	    	if(deleteFlag=="false"){//分享操作
	    		var quxiaofenxiang=$(data).find("#isshare").val();//取消分享操作
	    		$(obj).parents("tr").html($(data).html());
	    		window.location.reload();
	    	}else if(deleteFlag=="true"){//删除操作
	    		$(obj).parents("tr").remove();
	    		window.location.reload();
	    	}
	    },    
	     error : function() {    
	          alert("修改听课状态异常！");    
	     }    
	});
}

/**
 * 按照不同的条件查找听课记录
 */
function selectlecture(obj){
	var flags=$(obj).val();
	window.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/list?flags="+flags;
}

/**
 * 弹出草稿箱遮罩
 */
function draftBox(count){
	if(Number(count)==0){
		alert("您的草稿箱还没有任何内容哟！");
		return;
	}
	$.ajax({
	    url:_WEB_CONTEXT_+'/jy/lecturerecords/caogaoList',// 跳转到 action    
	    type:'post',    
	    cache:false,    
	    dataType:'html',  
	    success:function(htmlStr) {
	    	$("#draft_box .dialog_content").html(htmlStr);
	    	$("#draft_box").dialog("show");
	    }    
	});
}
function caoGaoList(html){
	$("#draft_box .dialog_content").html(html);
}

/**
 * 调到草稿箱的编辑页面
 * @param obj
 */
function editCaogao(obj,type){
	var id=$(obj).parents("tr").attr("id");
	if(type=="1"){//校外编辑草稿
		window.parent.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/writeLectureRecordsOuteInput?addflag=false&id="+id;
	}else if(type=="0"){//校内编辑草稿
		window.parent.location.href=_WEB_CONTEXT_+
		"/jy/lecturerecords/writeLectureRecordsInnerInput?addflag=false&id="+id;
	}
}

/**
 * 评论列表加载
 */
function commentlist(lecturepeopleId,resType,id,obj){
	//更改评论状态
	$.ajax( {    
	    url:_WEB_CONTEXT_+'/jy/lecturerecords/updateCommentState',// 跳转到 action
	    data:{    
	    	resId : id
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'json',  
	    async : false,//不异步
	    success:function(data) {
	    	$(obj).children("b").remove();//清除更新标识
	    }    
	});
	
	$('#commentBox').attr("src","jy/comment/list?authorId="+lecturepeopleId+"&resType="+resType+"&resId="+id+"&flags=1");
	$("#commentList_box").dialog({width:945,height:560});
}
/**
 * 弹出框授课人与听课人的回复列表
 * @param obj
 */
function replylist(lecturepeopleId,id,teachingpeopleId,obj){
	$('#hfym').attr("src","jy/lecturereply/reply?authorId="+lecturepeopleId+"&resId="+id+"&teacherId="+teachingpeopleId+"&flags=1");
	$(obj).children("b").remove();//清除更新标识
	$("#replayList_box").dialog({width:945,height:515});
}
function showSubmitBox(){
	$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	$("#is_submit").removeAttr("class");
	$("#not_submit").attr("class","upload-bottom_tab_blue");
	$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/lecturerecords/getIsOrNotSubmitRecords?isSubmit=0");
	$("#not_submit").bind("click",function(){
		$("#is_submit").removeAttr("class");
		$(this).attr("class","upload-bottom_tab_blue");
		$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/lecturerecords/getIsOrNotSubmitRecords?isSubmit="+0);
	});
	$("#is_submit").bind("click",function(){
		$(this).attr("class","upload-bottom_tab_blue");
		$("#not_submit").removeAttr("class");
		$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/lecturerecords/getIsOrNotSubmitRecords?isSubmit="+1);
	});
	$("#issubmit_box").dialog({width:794,height:560});
};
$("#dialog_tip .cancel").click(function(){
	$("#dialog_tip").dialog("close");
})
