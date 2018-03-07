var isReload = "";
$(document).ready(function () {
//	$('.close').click(function (){
//		$.unblockUI();
//		$(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
//		if(isReload == "ok"){
//			$("#wtjButton").attr("class","upload-bottom_tab_blue");
//			$("#ytjButton").attr("class","");
//			$("#hiddenForm").submit();  
//		}
//		isReload = "";
//	});
    
	$('.submit_up').click(function (){
		$("#submit_kj").dialog({width:800,height:549});
		$("#submit_iframe").attr("src","jy/courseware/preSubmit?isSubmit=0&"+ Math.random());
		isReload = "ok";
	});
	$('#wtjButton').click(function(){ 
	    $(this).addClass("upload-bottom_tab_blue");
	    $("#ytjButton").removeClass("upload-bottom_tab_blue");
	    $("#submit_iframe").attr("src","jy/courseware/preSubmit?isSubmit=0&"+ Math.random());
    });  
	$('#ytjButton').click(function(){ 
		$(this).addClass("upload-bottom_tab_blue");
		$("#wtjButton").removeClass("upload-bottom_tab_blue");
		$("#submit_iframe").attr("src","jy/courseware/preSubmit?isSubmit=1&"+ Math.random());
	});
	var bkb_planId = $("#bkb_planId").val();
	if(bkb_planId){
		var bkb_lessonId = $("#bkb_lessonId").val();
		var bkb_resId = $("#bkb_resId").val();
		updateThis(bkb_planId,bkb_lessonId,bkb_resId);
	}
});


//设置选择的课题目录名称和课题Id
function setValue(){
	var lessonId = $("#ktml").val();
	$("#lessonId").val(lessonId);
	$("#planName").val($.trim($("#"+lessonId).text()));
}
//清空form表单
function emptyForm(){
	$("#planId").val("");
	$("#lessonId").val("");
	$("#planName").val("");
	
	$("#ktml").val("");
	$("#ktml").trigger("chosen:updated"); 
	$("#kjms").val("");
	
	$("#originFileName").val("请选择文件");
	$("#hiddenFileId").val("");
	
	$(".formError").remove();
}
//表单验证
function start(fileArraySize){
	if($("#ktml").val()=="" || $("#ktml").val()==""){//选择课题目录
		$("#ktmlyz_to").css({"position":"relative"});
		$("#ktmlyz_to").prepend(getCheckHtml(-40,'请 选 择 课 题 目 录'));
		return false;
	}else{
		$("#ktmlyz_to").find(".parentFormkj_form").remove();
	}
	if($("#originFileName").val()=="请选择文件"){
		$(".scfj_to").css({"position":"relative"});
		$(".scfj_to").prepend(getCheckHtml(-39,"请 选 择 文 件"));
		return false;
	}else{
		$(".scfj_to").find(".parentFormkj_form").remove();
	}
	if(Number(fileArraySize) > 0){
		return ($("#kj_form").validationEngine('validate'));
	}else{
		$("#scfj_to").html("");
		if ($("#kj_form").validationEngine('validate')){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/courseware/save.json",
				data:$("#kj_form").serialize(),
				success:function(data){
					if(data.isOk){
						alert("保存成功");
						//刷新页面
						$("#hi_lessonId").val("");
						$("#hiddenForm").submit();
					}else{
						alert("保存失败，请稍后重试！");
					}
				}
			});
		}
	}
}

//getcheckhtml
function getCheckHtml(topNum,content){
	var str = '<div class="kjmsformError parentFormkj_form formError" style="opacity:0.87; position:absolute; top:'+topNum+'px; left:95px;">'+
			  '<div class="formErrorContent" style="padding:8px 10px;">* '+content+'<br></div>'+
			  '<div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div>'+
			  '<div class="line7"></div><div class="line6"></div><div class="line5"></div>'+
			  '<div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>'+
			  '</div></div>';			
	return str;
}

//表单提交
function backSave(data){
	if(data.code==0){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/courseware/save.json",
			data:$("#kj_form").serialize(),
			success:function(data){
				alert("保存成功");
				//刷新页面
				$("#hi_lessonId").val("");
				$("#hiddenForm").submit();
			}
		});
	}
}
//修改课件
function updateThis(planId,lessonId,resId){
	if(resId!=""){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/courseware/getFileById.json",
			data:{"resId":resId},
			success:function(data){
				if(data.res!=null){
					$("#originFileName").val(data.res.name);
					$("#hiddenFileId").val(resId);
					$(".mes_file_process").html("");
				}
			}
		});
	}
	
	$("#planId").val(planId);
	$("#lessonId").val(lessonId);
	$("#planName").val($("#planName_"+planId).val());
	
	$("#ktml").val(lessonId); 
	$("#ktml").trigger("chosen:updated"); 
	
	$("#kjms").val($("#digest_"+planId).val());
	
	//显示修改按钮内容
	$("#empty").show();
}
//放弃修改
function notUpdate(){
	$("#planId").val("");
	$("#lessonId").val("");
	$("#planName").val("");
	
	$("#ktml").val("");
	$("#ktml").trigger("chosen:updated"); 
	$("#kjms").val("");
	
	$("#originFileName").val("请选择文件");
	$("#hiddenFileId").val("");
	
	//显示修改按钮内容
	$("#empty").hide();
	
	$(".formError").remove();
}
//删除课件
function deleteThis(planId){
	$("#bt_delete").attr("onclick","deleteClose("+planId+")");
	$("#bt_cancel_delete").attr("onclick","deleteClose("+planId+",'cancel')");
	$("#course_del").dialog({width:400,height:220});
}
function deleteClose(planId,mark){
	if(mark){
		$("#course_del").dialog("close");
	}else{
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/courseware/delete.json",
			data:{"planId":planId},
			success:function(data){
				if(data.isOk){
					$("#course_del").dialog("close");
					$("#hiddenForm").submit();
				}else{
					$("#course_del").find(".del_info").html("删除失败！");
					$("#bt_delete").attr("onclick","deleteClose("+planId+",'cancel')");
				}
			}
		});
	}
}
function cancelShare(planId,isShare){
	$.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/courseware/sharing.json",
		data:{"planId":planId,"isShare":isShare},
		success:function(data){
			if(data.isOk==0){
				$("#hiddenForm").submit();
			}else if(data.isOk==1){
				$("#course_share").find(".del_info").html("该课件已经有评论，不能取消分享！");
				$("#querenbut").attr("onclick","sharingClose()");
			}else{
				$("#course_share").find(".del_info").html("取消分享失败！");
				$("#querenbut").attr("onclick","sharingClose()");
			}
		}
	});
}
//分享课件
function sharingThis(planId,isShare){
	if(isShare==0){
		$("#course_share").find(".del_info").show().html("您确定要取消分享吗？");
		$("#course_share").find(".share_info").hide();
		$("#querenbut").attr("onclick","cancelShare("+planId+","+isShare+")");
		$("#course_share").dialog({width:450,height:250});
	}else{
		$("#course_share").find(".del_info").hide();
		$("#course_share").find(".share_info").show();
		var title = $("#planName_"+planId).val();
		$("#res_title").html(title);
		$("#querenbut").attr("onclick","sharingOk("+planId+","+isShare+")");
		$("#course_share").dialog({width:510,height:300});		
	}
}
//筛选查找课件
function selectKJ(){
	var lessonId = $("#selectKT").val();
	if(lessonId==''){
		$("#hi_lessonId").attr("disabled","disabled");
	}else{
		$("#hi_lessonId").val(lessonId);
	}
	$("#hiddenForm").submit();
}
//显示查阅意见box(支持教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
function showScanListBox(planType,infoId,isUpdate){
	$("#checkedBox").attr("src",_WEB_CONTEXT_+"/jy/check/lookCheckOption?flags=false&resType="+planType+"&resId="+infoId);
	$("#course_option").dialog({width:1125,height:680});
	isReload = "ok";
	if(isUpdate){//更新为已查看
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{planType:planType,infoId:infoId},
	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanKJFASI.json"
	    });
	}
}
//显示评论意见列表box（支持教案、课件、反思和其他反思）
function showCommentListBox(planType,planId,isUpdate){
	$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=1&resType="+planType+"&resId="+planId);
	$("#course_review").dialog({width:1125,height:680});
	isReload = "ok";
 	if(isUpdate){//更新为已查看
 		$.ajax({  
 	        async : false,  
 	        cache:true,  
 	        type: 'POST',  
 	        dataType : "json",  
 	        data:{planId:planId},
 	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setCommentListAlreadyShowByType.json"
 	    });
 	}
 }	

//关闭分享提示框
function sharingClose(){
	$("#course_share").dialog("close");
 };
//分享保存
function sharingOk(planId,isShare){
	$.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/courseware/sharing.json",
		data:{"planId":planId,"isShare":isShare},
		success:function(data){
			if(data.isOk==0){
				$("#hiddenForm").submit();
			}else{
				$("#course_share").find(".del_info").show().html("分享失败！");
				$("#course_share").find(".share_info").hide();
				$("#querenbut").attr("onclick","sharingClose()");
			}
		}
	});
 };