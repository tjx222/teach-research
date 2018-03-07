<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:mHtmlHeader title="集备教案修改"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/activity/css/activity.css" media="screen">
	<ui:require module="../m/schoolactivity/js"></ui:require>
<script type="text/javascript">
var wordObj; //word控件实体
var openModeType; //当前的文档打开方式
openModeType = "${openModeType}";
function AfterDocumentOpened() {
    wordObj = document.getElementById("PageOfficeCtrl1");
    wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
    wordObj.Titlebar = false;
    wordObj.Document.Application.ActiveWindow.View.ReadingLayout = true;
    var checkbox1 = $("#checkbox1",window.parent.document);
    if(checkbox1 != null){
    	if(checkbox1.is(":checked")){
    		fadeInOrOut(true);
    		$("#saveButton",window.parent.document).show();
    	}
    }
}
</script>
</head>
<body>
<input type="hidden" id="editType" name="editType" value="${editType }"/>
<input type="hidden" id="planId" name="planId" value="${planId }"/>
<input type="hidden" id="activityId" name="activityId" value="${activityId }"/>
<input type="hidden" id="trackId" name="trackId" value="${trackId }"/> 

	<div style="width: 100%;height: 100%;z-index: -5;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1">
	    </po:PageOfficeCtrl>
	</div>
	
	<div style="position: absolute;bottom:5rem;right:10rem;z-index:1000;">
	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; background:none;margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
	<button class="edit_text"></button>
	</div>
</body>
<script type="text/javascript">
	require(['zepto','edit'],function(){	
		if(window.parent.document.getElementById("yijieshu")){
			$(".edit_text").hide();
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
			if('${editType}'=='1' && '${isSend}'=='false'){
		    	//每隔一段时间获取一次控制权
		    	getResZhengliPower(20000);
		    }else if('${editType}'=='0'){
		    	//每10分钟执行一次
		    	requestAtInterval(600000);
		    }
		});
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
			        url: _WEB_CONTEXT_+"/jy/schoolactivity/getResZhengliPower.json?resId=${resId}&id="+Math.random(),
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
	}); 
</script>
</html>
