define(["require","jquery",'jp/jquery.placeholder.min'], function (require) {
	var jq=require("jquery");	
	require('jp/jquery.placeholder.min');
	jq(document).ready(function () {
		jq('input, textarea').placeholder();
	});
});

$(document).ready(function () {
	$('.close').click(function (){
		$(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
		$.unblockUI();
		$("#wtjButton").attr("class","upload-bottom_tab_blue");
		$("#ytjButton").attr("class","");
		$("#hiddenForm").submit();  
	});
	
	$("#button").click(function(){
	    //点击提交
		  var one = $("#one").val();
		  var desc = $("#desc").val();
		  if($("#formular").validationEngine('validate'))
		  location.href = we + "/jy/record/updateDesc?id="+one+"&page="+page+"&desc="+encodeURI(desc);
})
	
	$(".tspan").click(function(event){
		  //点击详情
		  event.stopPropagation();
		  var one = $(this).attr("resId");
		  var name = $(this).attr("resName");
		  var desc = $(this).attr("title");
		  var bagId = $(this).attr("rId");
		  $(".dlog-top").css('width','548');
		  $("#name1").html(name);
		  $("#one").val(one);
		  $("#desc").val(desc);
		  $("#bagId").val(bagId);
		  $("#_jx").dialog({width:536,height:350});
		
	})
    
	$('.submit_up').click(function (){
		$("#submit_iframe").attr("src","jy/courseware/preSubmit?isSubmit=0&"+ Math.random());
		$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
		$.blockUI({ message: $('#submit_courseware'),css:{width: '800px',height: '600px',top:'3%',left:'20%'}});
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
});


//设置选择的课题目录名称和课题Id
function setValue(){
	var lessonId = $("#ktml").val();
	$("#lessonId").val(lessonId);
	$("#planName").val($.trim($("#"+lessonId).text()));
}
//清空form表单
function emptyForm(){
	$("#name").val("");
	$("#kjms").val("");
}
//表单验证
function start(fileArraySize){
	if(Number(fileArraySize) > 0){
		return ($("#kj_form").validationEngine('validate'));
	}else{
		$("#file_process").html("<span style='color:red;white-space:nowrap;'>请选择上传的文件</span>");
	}
}
//表单提交
function backSave(data){
	if(data.code==0){
		//var resId = data.data;
		//$("#resId").val(resId);
		$("#kj_form").submit();
	}
}
//修改课件
function updateThis(recordId,bagId,resId,name,desc){
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
	
	$("#recordId").val(recordId);
	$("#id").val(bagId);
	$("#name").val(name);
	
	$("#kjms").val(desc);
	
	//显示修改按钮内容
	$("#save").attr({"class":"btn_bottom_3"});
	$("#empty").attr({"class":"btn_bottom_4"});
}
//放弃修改
function notUpdate(){
	$("#name").val("");
	$("#recordId").val("");
	$("#planName").val("");
	$("#kjms").val("");
	
	$("#originFileName").val("请选择文件");
	$("#hiddenFileId").val("");
	
	//显示修改按钮内容
	$("#save").attr({"class":"btn_bottom_1"});
	$("#empty").attr({"class":"btn_bottom_2"});
	$(".formError").remove();
}

function start(fileArraySize){
	if($("#originFileName").val()=="请选择文件"){
		$("#scfj_to").html(getCheckHtml(9,"请 选 择 文 件"));
		return false;
	}
	if(Number(fileArraySize) > 0){
		return ($("#kj_form").validationEngine('validate'));
	}else{
		if($("#kj_form").validationEngine('validate')){
			$("#kj_form").submit();
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


//删除课件
function deleteThis(recordId,id){
	if(confirm("您确定要删除该档案吗？")){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/record/delete",
			data:{"id":recordId,"bagId":id},
			success:function(data){
				if(data){
					alert("删除成功！");
					$("#hiddenForm").submit();
				}else{
					alert("删除失败！");
				}
			}
		});
	}
}
//分享课件
function sharingThis(planId,isShare){
	var shareOk="确定分享该课件吗？";
	if(isShare==0){
		shareOk="确定取消分享该课件吗？";
	}
	if(confirm(shareOk)){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/courseware/sharing.json",
			data:{"planId":planId,"isShare":isShare},
			success:function(data){
				if(data.isShare){
					if(data.isOk==0){
						alert("分享成功，同伴资源中您能看到您分享的课件！");
						$("#hiddenForm").submit();
					}else{
						alert("分享失败！");
					}
				}else{
					if(data.isOk==0){
						alert("取消分享成功！");
						$("#hiddenForm").submit();
					}else if(data.isOk==1){
						alert("该课件已经有评论，不能取消分享！");
					}else{
						alert("取消分享失败！");
					}
				}
			}
		});
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
	$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&resType="+planType+"&resId="+infoId);
	$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	$.blockUI({ message: $('#box_scan'),css:{width: '793px',height: '520px',top:'100px',left:'50%',marginLeft:'-397px'}});
	if(isUpdate){//更新为已查看
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{planType:planType,infoId:infoId},
	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanListAlreadyShowByType.json"
	    });
	}
}
//显示评论意见列表box（支持教案、课件、反思和其他反思）
function showCommentListBox(planType,planId,isUpdate){
	$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=1&resType="+planType+"&resId="+planId);
 	$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	$.blockUI({ message: $('#box_comment'),css:{width: '793px',height: '520px',top:'100px',left:'50%',marginLeft:'-397px'}});
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