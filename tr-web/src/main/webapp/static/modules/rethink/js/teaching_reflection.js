define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
	var $=require("jquery");
	var isReload ="";
	//修改反思
	window.updateThis = function(planId,planType,lessonId,resId){
		if(resId!=""){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/rethink/getFileById.json",
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
		
		$(".formError").remove();
		$("#planType_"+planType).prop("checked",true);
		$("#planId").val(planId);
		$("#planName").val($("#planName_"+planId).val());
		
		if(planType==2){
			$("#type_zc").css("display","none");
			$("#type_audit").css("display","");
			$("#typeStr").html("课后反思");
			$("#typeVal").val(2);
			$("#fsbt").css("display","none");
			$("#ktml").css("display","");
			
			$("#sel_ktml").show();
			$("#sel_fsbt").hide();
			
			$("#sel_kt").val(lessonId);
			$("#sel_kt").trigger("chosen:updated");
			$("#lessonId").val(lessonId);
		}else if(planType==3){
			$("#type_zc").css("display","none");
			$("#type_audit").css("display","");
			$("#typeStr").html("其他反思");
			$("#typeVal").val(3);
			$("#ktml").css("display","none");
			$("#fsbt").css("display","");
			$("#sel_ktml").hide();
			$("#sel_fsbt").show();
			$("#sel_fs").val($("#planName_"+planId).val());
			$("#lessonId").val("");
		}
		//显示修改按钮内容 class='btn_bottom_1' style='margin-left:50px;'
		$("#noUpdate").show();
	};

//切换反思类型
window.changeTitle = function(params){
	if(params=="khfs"){
		$("#fsbt").css("display","none");
		$("#ktml").css("display","");
		$("#sel_ktml").css("display","");
		$("#sel_fsbt").css("display","none");
		$("#sel_fs").val("");
		$("#qt_jcsj").hide();
	}else if(params=="qtfs"){
		$("#ktml").css("display","none");	
		$("#fsbt").css("display","");
		$("#sel_ktml").css("display","none");
		$("#sel_fsbt").css("display","");
		$("#sel_kt").val("");
		$("#sel_kt").trigger("chosen:updated");
		$("#qt_jcsj").css("display","");
		$(".chosen-select-deselect").chosen({disable_search : true});
	}
	$("#ktmlyz_to").find(".parentFormkj_form").remove();
	$(".sel_fs_to").find(".parentFormkj_form").remove();
	$(".scfj_to").find(".parentFormkj_form").remove();	
};
//设置反思标题和课题目录
window.setValue = function (params){
	if(params=="sel_kt"){
		var lessonId = $("#sel_kt").val();
		if(lessonId!=""){
			$("#lessonId").val(lessonId);
			$("#planName").val($.trim($("#"+lessonId).text()));
		}else{
			$("#sel_kt").val("");
			$("#sel_kt").trigger("chosen:updated");
		}
	}else if(params="sel_fs"){
		$("#lessonId").val("");
		$("#planName").val($.trim($("#sel_fs").val()));
	}
};
//表单验证
window.start = function(fileArraySize){
	var sel_type = $("input[name='planType']:checked").val();
	if(sel_type == "2"){//课后反思
		if($("#sel_kt").val()=="" || $("#sel_kt").val()==""){//选择课题目录
			$("#ktmlyz_to").css({"position":"relative"});
			$("#ktmlyz_to").prepend(getCheckHtml(-41,'请 选 择 课 题 目 录'));
			return false;
		}else{ 
			$("#ktmlyz_to").find(".parentFormkj_form").remove();
		}
	}else{
		if($.trim($("#sel_fs").val())=="" || $.trim($("#sel_fs").val())==null){
			$(".sel_fs_to").css({"position":"relative"});
			$(".sel_fs_to").prepend(getCheckHtml(-40,"请 输 入 标 题"));
			return false;
		}else{
			$(".sel_fs_to").find(".parentFormkj_form").remove();
		}
	}
	if($("#originFileName").val()=="请选择文件"){
		$(".scfj_to").css({"position":"relative"});
		$(".scfj_to").prepend(getCheckHtml(-40,"请 选 择 文 件"));
		return false;
	}else{
		$(".scfj_to").find(".parentFormkj_form").remove();
	}
	if(Number(fileArraySize) > 0){
		return ($("#fs_form").validationEngine('validate'));
	}else{
		if ($("#fs_form").validationEngine('validate')){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/rethink/save.json",
				data:$("#fs_form").serialize(),
				success:function(data){
					if(data.isOk){
						alert("保存成功");
						$("#hi_lessonId").val("");
						selectFSLX(data.planType);
					}else{
						alert("保存失败，请稍后重试！");
					}
				}
			});
		}
	}
};

//getcheckhtml
window.getCheckHtml = function(topNum,content){
	var str = '<div class="kjmsformError parentFormkj_form formError" style="opacity:0.87; position:absolute; top:'+topNum+'px; left:83px;">'+
			  '<div class="formErrorContent" style="padding:8px 10px;">* '+content+'<br></div>'+
			  '<div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div>'+
			  '<div class="line7"></div><div class="line6"></div><div class="line5"></div>'+
			  '<div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>'+
			  '</div></div>';			
	return str;
};

//表单提交
window.backSave = function(data){
	if(data.code==0){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/rethink/save.json",
			data:$("#fs_form").serialize(),
			success:function(data){
				alert("保存成功");
				$("#hi_lessonId").val("");
				selectFSLX(data.planType);
			}
		});
	}
};
//通过课题目录查询数据
window.selectKT = function(){
	var lessonId = $("#selectKT").val();
	if(lessonId==''){
		$("#hi_lessonId").attr("disabled","disabled");
	}else{
		$("#hi_lessonId").val(lessonId);
	}
	$("#hiddenForm").submit();
};
//通过反思类型查询数据
window.selectFSLX = function(type){
	$("#hi_currentPage").val(1);
	$("#hi_planType").val(type);
	$("#hiddenForm").submit();
};
//删除反思
window.deleteThis = function (fsId,resId,planType){
	$("#bt_delete").attr("onclick","deleteClose("+fsId+",'"+resId+"',"+planType+")");
	$("#bt_cancel_delete").attr("onclick","deleteClose("+fsId+",'"+resId+"',"+planType+",'cancel')");
	$("#jxfs_del").dialog({width:400,height:220});
};
window.deleteClose = function (fsId,resId,planType,mark){
	if(mark){
		$("#jxfs_del").dialog("close");
	}else{
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/rethink/delete.json",
			data:{"planId":fsId,"resId":resId,"planType":planType},
			success:function(data){
				if(data.isOk){
					$("#jxfs_del").dialog("close");
					selectFSLX(data.planType);
				}else{
					$("#jxfs_del").find(".del_info").html("删除失败！");
					$("#course_del").attr("onclick","deleteClose("+fsId+","+resId+","+planType+",'cancel')");
				}
			}
		});
	}
}

//不修改反思
window.notUpdate =  function(){
	$("#type_audit").css("display","none");
	$("#type_zc").css("display","");
	
	$("#typeStr").html("课后反思");
	$("#typeVal").val(2);
	$("#sel_kt").val("");
	$("#sel_kt").trigger("chosen:updated"); 
	$("#planType_2").click();
	$("#lessonId").val("");
	$("#planId").val("");
	$(".formError").remove();
	
	$("#originFileName").val("请选择文件");
	$("#hiddenFileId").val("");
	
	$("#noUpdate").hide();
	
};
window.cancelShare = function (planType,planId,isShare){
	$.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/rethink/sharing.json",
		data:{"planType":planType,"planId":planId,"isShare":isShare},
		success:function(data){
			if(data.isOk==0){
				selectFSLX(planType);
			}else if(data.isOk==1){
				$("#jxfs_share").find(".del_info").html("该反思已经有评论，不能取消分享！");
				$("#querenbut").attr("onclick","sharingClose()");
			}else{
				$("#jxfs_share").find(".del_info").html("取消分享失败！");
				$("#querenbut").attr("onclick","sharingClose()");
			}
		}
	});
}
//分享反思
window.sharingThis = function (planType,planId,isShare){
	if(isShare==0){
		$("#jxfs_share").find(".del_info").show().html("您确定要取消分享吗？");
		$("#jxfs_share").find(".share_info").hide();
		$("#querenbut").attr("onclick","cancelShare("+planType+","+planId+","+isShare+")");
		$("#jxfs_share").dialog({width:450,height:250});
	}else{
		$("#jxfs_share").find(".del_info").hide();
		$("#jxfs_share").find(".share_info").show();
		var title = $("#planName_"+planId).val();
		$("#res_title").html(title);
		$("#querenbut").attr("onclick","sharingOk("+planType+","+planId+","+isShare+")");
		$("#jxfs_share").dialog({width:500,height:290});
	}
};
//查阅意见列表
window.cyyj = function(planType,infoId,planId,isUpdate){
	if(planType==2){
		showScanListBox(planType,infoId,isUpdate);
	}else{
		showScanListBoxOthers(planType,planId,isUpdate);
	}
};

//显示查阅意见box(支持教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
window.showScanListBox = function (planType,infoId,isUpdate){
	$("#checkedBox").attr("src",_WEB_CONTEXT_+"/jy/check/lookCheckOption?flags=false&resType="+planType+"&resId="+infoId);
	$("#jxfs_option").dialog({width:1125,height:680});
	isReload="ok";
	if(isUpdate){//更新为已查看
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{planType:planType,infoId:infoId},
	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanKJFASI.json",
	    });
	}
};
//显示查阅意见box(其他反思)
window.showScanListBoxOthers = function (planType,planId,isUpdate){
	$("#checkedBox").attr("src",_WEB_CONTEXT_+"/jy/check/lookCheckOption?flags=false&resType="+planType+"&resId="+planId);
	$("#jxfs_option").dialog({width:950,height:680});
	isReload="ok";
	if(isUpdate){//更新为已查看
		$.ajax({  
			async : false,  
			cache:true,  
			type: 'POST',  
			dataType : "json",  
			data:{planType:planType,planId:planId},
			url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanKJFASI.json",
		});
	}
};
//显示评论意见列表box（支持教案、课件、反思和其他反思）
window.showCommentListBox = function (planType,planId,isUpdate){
	$("#commentBox").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=1&resType="+planType+"&resId="+planId);
 	$("#jxfs_review").dialog({width:1125,height:680});
	isReload="ok";
 	if(isUpdate){//更新为已查看
 		$.ajax({  
 	        async : false,  
 	        cache:true,  
 	        type: 'POST',  
 	        dataType : "json",  
 	        data:{planId:planId},
 	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setCommentListAlreadyShowByType.json",
 	    });
 	}
 };

//关闭分享提示框
 window.sharingClose = function (){
	 $("#jxfs_share").dialog("close");
 };
//分享保存
 window.sharingOk = function (planType,planId,isShare){
	 $.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/rethink/sharing.json",
		data:{"planType":planType,"planId":planId,"isShare":isShare},
		success:function(data){
			if(data.isOk==0){
				selectFSLX(planType);
			}else{
				$("#jxfs_share").find(".share_info").hide();
				$("#jxfs_share").find(".del_info").show().html("分享失败！");
				$("#querenbut").attr("onclick","sharingClose()");
			}
		}
	});
 };
 $(document).ready(function () {
		$("#fs_form").validationEngine();
		$('.submit_up').click(function (){
			$("#submit_iframe").attr("src","jy/rethink/preSubmit?isSubmit=0&"+ Math.random());
			$("#submit_fs").dialog({width:800,height:615});
			isReload="ok";
		});
		$('.close').click(function (){
			$.unblockUI();
			if(isReload=="ok"){
				$("#wtjButton").attr("class","upload-bottom_tab_blue");
				$("#ytjButton").attr("class","");
				$("#hiddenForm").submit();  
			}
			isReload ="";
			$(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
		});
		$('#wtjButton').click(function(){ 
		    $(this).addClass("upload-bottom_tab_blue");
		    $("#ytjButton").removeClass("upload-bottom_tab_blue");
		    $("#submit_iframe").attr("src","jy/rethink/preSubmit?isSubmit=0&"+ Math.random());
	    });  
		$('#ytjButton').click(function(){ 
			$(this).addClass("upload-bottom_tab_blue");
			$("#wtjButton").removeClass("upload-bottom_tab_blue");
			$("#submit_iframe").attr("src","jy/rethink/preSubmit?isSubmit=1&"+ Math.random());
		});
		var bkb_planId = $("#bkb_planId").val();
		if(bkb_planId){
			var bkb_lessonId = $("#bkb_lessonId").val();
			var bkb_resId = $("#bkb_resId").val();
			var bkb_planType = $("#bkb_planType").val();
			updateThis(bkb_planId,bkb_planType,bkb_lessonId,bkb_resId);
		}
		$("#qt_jcsj").hide();
	});

});
