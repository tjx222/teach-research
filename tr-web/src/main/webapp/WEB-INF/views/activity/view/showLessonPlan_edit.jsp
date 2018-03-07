<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:htmlHeader title="集备教案修改"></ui:htmlHeader>
<script type="text/javascript">
var wordObj; //word控件实体
var openModeType; //当前的文档打开方式
openModeType = "${openModeType}";
function AfterDocumentOpened() {
    wordObj = document.getElementById("PageOfficeCtrl1");
    wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
    var checkbox1 = $("#checkbox1",window.parent.document);
    if(checkbox1 != null){
    	if(checkbox1.is(":checked")){
    		fadeInOrOut(true);
    		$("#saveButton",window.parent.document).show();
    	}
    }
   
    if('${editType}'=='1' && '${isSend}'=='false'){
    	//每隔一段时间获取一次控制权
    	getResZhengliPower(20000);
    }else if('${editType}'=='0'){
    	//每10分钟执行一次
    	requestAtInterval(600000);
    }
}
$(document).ready(function(){
	if('${isSend}'=='false'){
		if(openModeType=='docReadOnly'){
			$("#checkbox1",window.parent.document).prop("checked",false);
			$("#checkbox1",window.parent.document).hide();
			$(".lable1",window.parent.document).hide();
			$("#saveButton",window.parent.document).hide();
			alert("当前主备教案正在被另一用户整理，您此时还不可以整理，已为您自动切换成只读模式");
		}else{
			$("#checkbox1",window.parent.document).show();
			$(".lable1",window.parent.document).show();
		}
	}
});
//工具栏显示隐藏
function fadeInOrOut(flag){
	wordObj.OfficeToolbars = flag;
}
//保存修改的教案
function saveLessonPlanTracks(editType){
	$("#editType").val(editType);
	wordObj.WebSave();
	//将返回的教案id赋值
	if(wordObj.CustomSaveResult =='isOver'){
		alert("活动已结束，您不可再修改");
	}else if(wordObj.CustomSaveResult =='isSend'){
		alert("活动的整理教案已被发送，您不可再整理");
	}else if(wordObj.CustomSaveResult =='zhubeiIsEdit'){
		alert("该活动的主备教案已被发起人修改");
		parent.location.reload();
	}else{
		//if($("#trackId").val()==""){
			 parent.frushIframe();//新增则刷新父页内的列表页
		 //}
		if($("#trackId").val() != wordObj.CustomSaveResult){
			window.location.reload();
		}
		$("#trackId").val(wordObj.CustomSaveResult);
	}
}

/**
 * 每隔一段时间请求一次后台，保证session有效
 */
function requestAtInterval(timeRange){
	var dingshi = window.setInterval(function(){
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        url: _WEB_CONTEXT_+"/jy/toEmptyMethod.json?id="+Math.random(),
	        error: function () {
	        	window.clearInterval(dingshi);
	        },  
	        success:function(data){
	        }  
	    });
	},timeRange);
}
/**
 * 持续获取资源整理控制权
 */
function getResZhengliPower(timeRange){
	var dingshi1 = window.setInterval(function(){
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        url: _WEB_CONTEXT_+"/jy/activity/getResZhengliPower.json?resId=${resId}&id="+Math.random(),
	        error: function () {
	        	//window.clearInterval(dingshi1);
	        },  
	        success:function(data){
	        	if(data.result=='success' && openModeType=='docReadOnly'){
	        		alert("您现在可以整理该主备教案");
	        		window.location.reload();
	        		$("#checkbox1",window.parent.document).show();
	        		$(".lable1",window.parent.document).html("整理教案");
	        	}
	        }  
	    });
	},timeRange);
}
</script>
</head>
<body>
<input type="hidden" id="editType" name="editType" value="${editType }"/>
<input type="hidden" id="planId" name="planId" value="${planId }"/>
<input type="hidden" id="activityId" name="activityId" value="${activityId }"/>
<input type="hidden" id="trackId" name="trackId" value="${trackId }"/> 

	<div class="word_tab_big_tab" style="width: 1000px;height: 495px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1">
	    </po:PageOfficeCtrl>
	</div>
</body>
</html>
