<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:htmlHeader title="教案修改"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/edit.css">
<script type="text/javascript" src="${ctxStatic}/modules/writelessonplan/js/js.js"></script>
<script type="text/javascript">
var wordObj; //word控件实体
var ww = $(window).width(),offsetWith = ww - 500 > 0?ww - 500:0;
var ops="height=600,width=500,scrollbars=yes,titlebar=no,left="+offsetWith+",top=10,toolbar=no,resizable=yes,location=no";
function AfterDocumentOpened() {
    wordObj = document.getElementById("PageOfficeCtrl1");
    wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
    wordObj.CustomToolbar = false;//不显示自定义工具栏
    wordObj.OfficeToolbars = true;//默认不显示工具栏
    wordObj.Menubar = false;//默认不显示菜单栏
    //定时保存到本地备份（每15分钟）
    saveToLocal();
}
//保存到服务器
function saveToServer(){
	if($("#lesson_id").val() =="" || $("#lesson_hours").val() ==""){
		alert("请先选择课时！");
	}else{
		wordObj.WebSave();
		//将返回的教案id赋值
		if(wordObj.CustomSaveResult=='success'){
			 //window.location.reload();
			 alert("修改成功！");
			 //window.close();
		}else{
			alert("保存失败，请联系管理员");
		}
	}
}
//工具栏显示隐藏
function fadeInOrOut(){
	if(wordObj.OfficeToolbars){
		wordObj.OfficeToolbars = false;
	}else{
		wordObj.OfficeToolbars = true;
	}
	 
}
//将当前word文件保存到本地
function saveToLocal(){
	var dingshi = window.setInterval(function(){
		var fileName = $("#lesson_name").val();
		wordObj.SaveAs("D:/"+fileName+"(备份).doc",true);
	},900000);
	
}
//获取右侧的同伴资源
var peer_data = [null,null,null];
function scanFile(resid){
	$('.add_push_wrap').hide();
	$('.add_peer_wrap').hide();
	scanResFile(resid,ops);
}

function getPeerRes(planType,currentPage){
	var lessonId = $("#lesson_id").val();
	if(peer_data[planType] != null){
		//展示从缓存中获得的数据
		showPeerData(planType);
	}else{
		//请求后台数据
		if(lessonId!=""){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{lessonId:lessonId,planType:planType,'page.currentPage':currentPage},
		        url: _WEB_CONTEXT_+"/jy/getPeerResource.json",
		        error: function () {
		            alert('请求同伴资源失败');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		peer_data[planType] = data.lessonPlanPageList;
		        		//展示数据
		        		showPeerData(planType);
		        	}else{
		        		alert('请求同伴资源失败');  
		        	}
		        }  
		    });
		}else{
			
		}
	}
}
//展示同伴资源数据
function showPeerData(planType){
	var htmlStr = '<div style="display:block;height:455px;">';
	var peerDataList = peer_data[planType].datalist;
	for(var i=0;i<peerDataList.length;i++){
		var icopath;
		$.ajax({
			url:_WEB_CONTEXT_+"/jy/manage/res/getResPath",
			async:false,
			data:{resId:peerDataList[i].resId,fileName:peerDataList[i].planName},
			success:function(data){
				icopath=data.data;
			}
		});
		var fileName = cutStr(peerDataList[i].planName,28,true);
		var orgName = cutStr(peerDataList[i].orgName+' '+peerDataList[i].userName,15,true);
		htmlStr = htmlStr+'<div class="Pre_cont_right_1_dl"><dl>'+
		'<dd><img src="${ctxStatic }/modules/writelessonplan/images/word1.png"></dd>'+
		'<dt><span title="'+peerDataList[i].planName+'">'+fileName+'</span><br /><span title="'+peerDataList[i].orgName+' '+peerDataList[i].userName+'">'+orgName+'</span></dt></dl>'+
		'<div class="show_p"><ol><li class="show_p_1" title="查看" onclick="scanFile(\''+peerDataList[i].planId+'\');"></li>'+
		'<li><a class="show_p_2" title="下载" href="'+icopath+'"></a></li></ol></div></div>';
	}
	htmlStr = htmlStr+'</div>';
	if(peerDataList.length>0){
		if(peer_data[planType].currentPage>1){
			htmlStr = htmlStr+'<div style="height:50px;line-height:50px;" class="page"><ol><li class="one_page"><a style="cursor: pointer;" onclick="peer_data['+planType+']=null;getPeerRes('+planType+','+(peer_data[planType].currentPage-1)+');">上一页</a></li>';
		}else{
			htmlStr = htmlStr+'<div style="height:50px;line-height:50px;" class="page"><ol><li class="on_one_page"><a disabled="disabled">上一页</a></li>';
		}
		if(peer_data[planType].currentPage<peer_data[planType].totalPages){
			htmlStr = htmlStr+'<li class="next_page"><a style="cursor: pointer;" onclick="peer_data['+planType+']=null;getPeerRes('+planType+','+(peer_data[planType].currentPage+1)+');">下一页</a></li></ol></div>';
		}else{
			htmlStr = htmlStr+'<li class="the_next_page"><a disabled="disabled">下一页</a></li></ol></div>';
		}
	}
	$("#peer_"+planType).html(htmlStr);
}
//获取右侧推送资源
function showCommendRes(){
	$("#li_0").trigger("click");
}
function getCommendRes(resType,currentPage){
	var lessonId = $("#lesson_id").val();
	var bookId = $("#book_id").val();
	if(lessonId!=""){
		//请求后台数据
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        data:{resType:resType,lessonId:lessonId,bookId:bookId,'page.currentPage':currentPage},
	        url: _WEB_CONTEXT_+"/jy/getAllCommendResource",
	        error: function () {
	            alert('请求推送资源失败');  
	        },  
	        success:function(data){
	        	$("#commendDiv_"+resType).html(data);
	        }  
	    });
	}else{
		$("#commendDiv_"+resType).html("请先点选课题");
	}
		
}

$(document).ready(function(){
	var hoursIdStr = '${hoursStr}';
	var hourIdArray = hoursIdStr.split(',');
	for(var i=0;i<hourIdArray.length;i++){
		if(hourIdArray[i] == "") continue;
		if(",${lessonPlan.hoursId},".indexOf(","+hourIdArray[i]+",")>=0){
			$("#keshi"+hourIdArray[i]).attr("checked", true);
		}else{
			$("#keshi"+hourIdArray[i]).attr({"disabled":"disabled"});
			$("#keshi"+hourIdArray[i]).next().show();
		}
		$("#quankeshi").attr({"disabled":"disabled"});
	}
	
	//为每个课时绑定选中事件
	$("input[name='keshi']").bind("change",function(){
		var hoursIdStrTemp = ""; //课时id连接的字符串
		var flag = true;
		$("input[name='keshi']").each(function(){
			if($(this).is(":checked")){
				hoursIdStrTemp = hoursIdStrTemp+$(this).val()+",";
			}
			if($(this).is(":disabled")){
				flag = false;
			}
		});
		if(hoursIdStrTemp=="" && flag){
			$("#quankeshi").removeAttr("disabled");
		}else{
			$("#quankeshi").attr({"disabled":"disabled"});//全案置灰
		}
		hoursIdStrTemp = hoursIdStrTemp.substring(0,hoursIdStrTemp.length-1);
		$('#lesson_hours').val(hoursIdStrTemp);
	});
	//为全案绑定事件
	$("#quankeshi").bind("change",function(){
		var hoursIdStrTemp = ""; //课时id连接的字符串
		if($(this).is(":checked")){
			$("input[name='keshi']").attr({"disabled":"disabled"});  //其他课时置灰
			hoursIdStrTemp = "1,2,3,4,5";
		}else{
			$("input[name='keshi']").removeAttr("disabled"); 
		}
		$('#lesson_hours').val(hoursIdStrTemp);
	});
	
	requestAtInterval(600000);
});
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

//校验
function checksaveLesson(){
	//直接返回
	return true;
}
function callbacksaveLesson(data){
	var data = {
			"resourceId": data.data,
			"planId":$("#plan_id").val(),
	}
	console.log(data);
	//刷新页面
	$.ajax({
		type : 'POST',
		dataType : "json",
		data :data,
		url : _WEB_CONTEXT_ + "/jy/updateLessonPlanWithResId",
		success : function(data) {
			if (data.code == 1) {
				 window.location.reload();
			} else {
				alert('操作失败,请刷新重试');
			}
		}
	});
}
</script>
</head>


<body>
<div class="wrap_edit" style="margin-top:0;">
<div id="qwe">
	<!-- <h3 style="width: 170px;float: left;cursor: pointer;margin-right:10px;margin-left: 10px;" onclick="fadeInOrOut();">
		<span class="qwe_h3_span" title="工具栏隐藏/显示"></span>点击这里，展开/收起工具栏
	</h3>
	<input type="button" onclick="uploadLocalLesson();" class="saveJA"
			value="上传本地教案">
	<input type="button" onclick="saveToServer();" value="保存教案" class="saveJA"> -->
	<ul style="display:none;">
		<li>
			<input type="checkbox" name="keshi" id="keshi1" value="1"/>第1课时
			<b class="d-chenkbox" style="display: none;"></b>
		</li>
		<li>
			<input type="checkbox" name="keshi" id="keshi2" value="2"/>第2课时
			<b class="d-chenkbox" style="display: none;"></b>
		</li>
		<li>
			<input type="checkbox" name="keshi" id="keshi3" value="3"/>第3课时
			<b class="d-chenkbox" style="display: none;"></b>
		</li>
		<li>
			<input type="checkbox" name="keshi" id="keshi4" value="4"/>第4课时
			<b class="d-chenkbox" style="display: none;"></b>
		</li>
		<li>
			<input type="checkbox" name="keshi" id="keshi5" value="5"/>第5课时
			<b class="d-chenkbox" style="display: none;"></b>
		</li>
		<li>
			<input type="checkbox" name="quankeshi" id="quankeshi" value="5"/>全案
		</li>
	</ul>
	
	<!-- <div id="book_option" class="dialog"> 
		  <div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">上传本地教案</span>
				<span onclick="dialog_close_callback();" class="dialog_close"></span>
			</div>
			<div class="dialog_content"></div>
		  </div>
	</div> -->
	
	
	<!-- 已选课时 -->
	<input type="hidden" id="lesson_hours" name="hoursIdStr" value="${lessonPlan.hoursId }"/>
	<!-- 教案id -->
	<input type="hidden" id="plan_id" name="planId" value="${lessonPlan.planId}"/>
	<input type="hidden" id="lesson_id" name="lessonId" value="${lessonPlan.lessonId }"/>
</div>
	<div class="com_cont_right1" style="z-index: 9000;height:105px;top:240px;">
		<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;" allowtransparency="true"></iframe>
		<div class="com_cont_right1_left">
		</div>
		<div class="com_cont_right1_right"  align="center">
			<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;" allowtransparency="true"></iframe>
			<ul>
				<li id="push" class="li_1" onclick="showCommendRes();">推送资源</li>
				<li id="peer" class="li_1" style="border:none;"  onclick="getPeerRes(0,1);">同伴资源</li>
				<!-- <li style="border:none;" id="electronics" class="li_1">电子教材</li> -->
			</ul>
		</div>
	</div>
		<div class="add_push_wrap" style="height: 568px; position: absolute; z-index: 1000;" align="center">
		   	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		   	<div class="dlog-top">
				<span>推送资源</span>
				<b class="close"></b>
			</div>
			<div class="dlog-bottom">
				<div class="menu_list"> <!-- id="commendDiv" -->
					<ul>
						<li class="menu_list_act" onclick="getCommendRes(0,1);" id="li_0">教案</li>
						<li onclick="getCommendRes(1,1);">课件</li>
						<li onclick="getCommendRes(2,1);">习题</li>
						<li style="border:none;" onclick="getCommendRes(3,1);">素材</li>
					</ul>
					<div class="clear"></div>
					<div class="menu_list_big">
						<div class="menu_list_tab" id="commendDiv_0">
							
						</div>
						<div class="menu_list_tab" id="commendDiv_1">
							
						</div>
						<div class="menu_list_tab" id="commendDiv_2">
							
						</div>
						<div class="menu_list_tab" id="commendDiv_3">
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="add_peer_wrap" style="height: 568px; position: absolute; z-index: 1000;" align="center">
		   	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		   	<div class="dlog-top">
				<span>同伴资源</span>
				<b class="close"></b>
			</div>
			<div class="dlog-bottom">
				<div id="firstpane" class="menu_list"> <!-- id="commendDiv" -->
					<ul>
						<li style="width: 73px;" class="menu_list_act" onclick="getPeerRes(0,1)" id="li_0">教案</li>
						<li style="width: 73px;" onclick="getPeerRes(1,1)">课件</li>
						<li style="width: 73px;border:none;" onclick="getPeerRes(2,1)">反思</li>
					</ul>
					<div class="clear"></div>
					<div class="menu_list_big">
						<div class="menu_list_tab" id="peer_0">
							
						</div>
						<div class="menu_list_tab" id="peer_1">
							
						</div>
						<div class="menu_list_tab" id="peer_2">
							
						</div>
					</div>
				</div>
			</div>
		</div> 
		<div class="pageOfficediv" style="width:1000px;margin-top:2px;">
	        <po:PageOfficeCtrl id="PageOfficeCtrl1" >
	        </po:PageOfficeCtrl>
	   </div>
</div>
<!-- <div class="clear"></div> -->

</body>
</html>