<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta charset="UTF-8">
<ui:htmlHeader title="撰写教案"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/writelessonplan/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic}/modules/writelessonplan/css/ztree/zTreeStyle.css" media="all">
<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/writelessonplan/js/js.js"></script>
<script type="text/javascript">
/* var hasChange = false;
window.onbeforeunload = function(event){
	if(hasChange){
		(event || window.event).returnValue = "离开页面前请保存好您的教案";
	}
} */
$(document).ready(function(){
	$(window).scroll(function (){
		$("#kongdiv").toggle();
	});
	//为每个课时绑定选中事件
	$("#ul1").delegate("input[name='lessonHours']","click",function(){
		var hoursIdStr = ""; //课时id连接的字符串
		var flag = true;
		$("input[name='lessonHours']").each(function(){
			if($(this).is(":checked")){
				hoursIdStr = hoursIdStr+$(this).val()+",";
			}
			if($(this).is(":disabled")){
				flag = false;
			}
		});
		if(hoursIdStr=="" && flag){
			$("input[name='lessonHours_all']").removeAttr("disabled");
		}else{
			$("input[name='lessonHours_all']").attr({"disabled":"disabled"});//全案置灰
		}
		hoursIdStr = hoursIdStr.substring(0,hoursIdStr.length-1);
		$('#lesson_hours',document.getElementById("iframe2").contentWindow.document).val(hoursIdStr);
		$('#lesson_hours').val(hoursIdStr);
		
		try {
			document.getElementById("iframe2").contentWindow.updateLessonName(hoursIdStr);
			$('#gradeId',document.getElementById("iframe2").contentWindow.document).val('${lessonPlan.gradeId}');
			$('#subjectId',document.getElementById("iframe2").contentWindow.document).val('${lessonPlan.subjectId}');
	　　     } catch(error) {
		
	　　     }
		hasChange = true;
	});
	//为全案绑定选中事件
	$("#ul1").delegate("#checkbox0","click",function(){
		var hoursId_all = "-1"; //全案id
		if($(this).is(":checked")){
			$("input[name='lessonHours']").attr({"disabled":"disabled"});  //其他课时置灰
			$('.add_class_hour').attr("disabled", true); 
			hasChange = true;
		}else{
			$("input[name='lessonHours']").removeAttr("disabled"); 
			try {
				document.getElementById("iframe2").contentWindow.updateLessonName("");
		　　     } catch(error) {
			
		　　     }
			$('.add_class_hour').attr("disabled", false);
		}
		$('#lesson_hours',document.getElementById("iframe2").contentWindow.document).val(hoursId_all);
		$('#lesson_hours').val(hoursId_all);
	});
	//每5分钟执行一次
	requestAtInterval(600000);
	//默认展开上次操作的章节树
	if(${lessonPlan != null}){
		showTree('${lessonPlan.bookId}');
		var defaultNodeArray = ztreeObj.getNodesByParam("lessonId",'${lessonPlan.lessonId}');
		ztreeObj.selectNode(defaultNodeArray[0],false);
		ztreeObj.cancelSelectedNode();
		$('#lesson_id',document.getElementById("iframe2").contentWindow.document).val('${lessonPlan.lessonId}');
		$('#lesson_id').val('${lessonPlan.lessonId}');
	}else if(${currentBook !=null}){
		showTree('${currentBook.comId}');
	}
	
	$("#spacelist").change(function () {  
		var so = $(this).children('option:selected');
        location.href="${ctx}jy/toWriteLessonPlan?nocookie=true&spaceId="+so.val();
	});
});
//将已选课题置灰
function checked_zhihui(hoursIdStr){
	if("," == hoursIdStr){
		return;
	}
	if(",-1," == hoursIdStr){
		$("#checkbox0").attr("checked", true);
		$("#checkbox0").next().show();
		$("input[name='lessonHours']").attr({"disabled":"disabled"});
		$('.add_class_hour').attr("disabled", true); 
	} else{
		$("#checkbox0").attr({"disabled":"disabled"});
		var hourIdArray = hoursIdStr.split(',');
		for(var i=0;i<hourIdArray.length;i++){
			if(hourIdArray[i] && hourIdArray[i] != "0"){
				$("#checkbox"+hourIdArray[i]).attr("checked", false);
				$("#checkbox"+hourIdArray[i]).attr({"disabled":"disabled"});
				$("#checkbox"+hourIdArray[i]).next().show();
			}
		}
	}
}
//将所有checkbox清空
function clearAllcheckbox(){
	$('#lesson_hours',document.getElementById("iframe2").contentWindow.document).val("");
	$('#lesson_hours').val("");
	$("#ul1").find("input[type='checkbox']").each(function(){
		$(this).attr("checked", false);
		$(this).removeAttr("disabled"); 
		$(this).next().hide();
});
}
//显示新模板页面
function showNewTemplate(obj,currentTpId){
	if($(obj).prop("id")!=currentTpId){
		if(window.confirm("您确定要更换“教案模板”吗？更换后您之前撰写的教案内容将会全部丢失！")){
			ztreeObj.cancelSelectedNode();
			$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/toEditWordPage?tpId="+$(obj).prop("id"));
		}else{
			return false;
		}
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
</script>
<script type="text/javascript">
var treeNodes;
var ztreeObj;
var setting = {  
        data:{
        	simpleData: {
    			enable: true,
    			idKey: "lessonId",
    			pIdKey: "parentId",
    			rootPId: 0
    		},
    		key:{
    			name: "lessonName"
    		}
        },
        callback:{
        	//beforeExpand:closeAll,
        	onClick: whenClickTitle,
        	beforeClick: beforeClickIt
        },
        view:{
        	selectedMulti:false
        }
    };  
  
//将节点的名称添加到word文档的标题处
function whenClickTitle(event, treeId, treeNode){
	//ajax请求后台获取已写过的课时
	var hoursStr;
	if($('#lesson_id').val() != treeNode.lessonId){
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{lessonId:treeNode.lessonId},
	        url: _WEB_CONTEXT_+"/jy/getHoursStrOfWritedLesson.json",
	        error: function () {
	            alert('请求失败');
	        },  
	        success:function(data){
	        	clearAllcheckbox();
	        	$(".add_class_hour").attr("disabled",false);
	        	hoursStr = data.hoursStr;
	        	var hourIdArray = hoursStr.split(',');
	        	var maxHour = 0;
	        	for(var i=0;i<hourIdArray.length;i++){
	        		if(hourIdArray[i]){
	        			maxHour = parseInt(hourIdArray[i]) > maxHour?parseInt(hourIdArray[i]):maxHour;
	        		}
	        	}
	        	
	   			var liStr = '<li>'
		   					+'<input id="checkbox0" type="checkbox" name="lessonHours_all" value="0" style="margin-top:0.5px;"/>不分课时<b class="d-chenkbox" style="display: none;"></b>'
		   					+'</li>';
	       		if(maxHour>5){
	       			for(var i=1;i<=maxHour;i++){
	       				liStr += '<li>'
	       		   				+'<input id="checkbox'+(i)+'" type="checkbox" name="lessonHours" value="'+i+'" style="margin-top:0.5px;"/>第'+i+'课时<b class="d-chenkbox" style="display: none;"></b>'
	       		   				+'</li>';
	       			}
	       		} else{
	       			liStr +='<li><input id="checkbox1" type="checkbox" name="lessonHours" value="1" style="margin-top:0.5px;"/>第1课时<b class="d-chenkbox" style="display: none;"></b></li>'
	    		   		   +'<li><input id="checkbox2" type="checkbox" name="lessonHours" value="2" style="margin-top:0.5px;"/>第2课时<b class="d-chenkbox" style="display: none;"></b></li>'
	    		   		   +'<li><input id="checkbox3" type="checkbox" name="lessonHours" value="3" style="margin-top:0.5px;"/>第3课时<b class="d-chenkbox" style="display: none;"></b></li>'
	    		   		   +'<li><input id="checkbox4" type="checkbox" name="lessonHours" value="4" style="margin-top:0.5px;"/>第4课时<b class="d-chenkbox" style="display: none;"></b></li>'
	    		   		   +'<li><input id="checkbox5" type="checkbox" name="lessonHours" value="5" style="margin-top:0.5px;"/>第5课时<b class="d-chenkbox" style="display: none;"></b></li>';
	       		}
	       		$("#ul1").html(liStr);
	       		//将已写的课时置灰
	           	checked_zhihui(hoursStr);
	        }  
	    });
	}
	//调用子页面的设置文档标题方法
	try {
		document.getElementById("iframe2").contentWindow.setLessonName(treeNode.lessonName);
　　     } catch(error) {
	
　　     }
	//隐藏域赋值
	$('#lesson_id',document.getElementById("iframe2").contentWindow.document).val(treeNode.lessonId);
	$('#lesson_name',document.getElementById("iframe2").contentWindow.document).val(treeNode.lessonName);
	$('#book_id',document.getElementById("iframe2").contentWindow.document).val(treeNode.comId);
	$('#lesson_id').val(treeNode.lessonId);
	$('#lesson_name').val(treeNode.lessonName);
	$('#book_id').val(treeNode.comId);
	//切换课题后则隐藏右侧展开的推荐资源div
	$('.add_lesson_wrap',document.getElementById("iframe2").contentWindow.document).hide();
	$('.add_push_wrap',document.getElementById("iframe2").contentWindow.document).hide();
	$('.add_electronics_wrap',document.getElementById("iframe2").contentWindow.document).hide();
	$('.add_peer_wrap',document.getElementById("iframe2").contentWindow.document).hide();
	//重置同伴资源的缓存为null
	document.getElementById("iframe2").contentWindow.peer_data = [null,null,null];
//	document.getElementById("iframe2").contentWindow.commend_data = [null,null,null,[null,null,null,null]];
	//显示待选课时
	var hoursDiv = $('#lesson_time',window.document);
 	var lessonObj = $("#" + treeNode.tId + "_span");
 	var X = lessonObj.offset().top;
 	var Y = lessonObj.offset().left;
 	hoursDiv.css("left",Y);
 	hoursDiv.css("top",X-hoursDiv.height()/2);
 	hoursDiv.fadeIn(300);
}

//在点击节点前判断是否是父节点，父节点不作操作
function beforeClickIt(treeId, treeNode, clickFlag){
	if(treeNode.isParent){
		if(!treeNode.open){
			closeAll(treeId, treeNode);
		}
		$('#lesson_time',window.document).fadeOut(100);
		ztreeObj.expandNode(treeNode);
		return false;
	}else{
		//屏蔽了非当前学期的点选课题功能 
		//if(treeId!="lessonTree_${currentBook.comId}"){
		//	return false;
		//}
	}
	if($('#lesson_hours').val()!="" && 
			$('#lesson_id').val() != treeNode.lessonId){
		if(window.confirm("您当前撰写的教案内容还未保存，是否要切换到其他课题目录？")){
			return true;
		}else{
			return false;
		}
	}
	return true;
}
//关闭其他
function closeAll(treeId, treeNode){
	var nodesList = ztreeObj.getNodesByFilter(function filter(node){
		return node.open;
	}, true,treeNode.getParentNode());
	var nodes = ztreeObj.transformToArray(nodesList);
	if(nodes!=null){
		for(var i=0;i<nodes.length;i++){
			ztreeObj.expandNode(nodes[i],false,true,false);
		}
	}
}
function showTree(bookId){
	//获取课题树
	$.ajax({  
        async : false,  
        cache:true,  
        type: 'POST',  
        dataType : "json",  
        data:{bookId:bookId},
        url:  _WEB_CONTEXT_+"/jy/getLessonList.json",
        error: function () {
            alert('请求失败');  
        },  
        success:function(data){
            treeNodes = data.lessonList; 
        }  
    });
	$(".ztree").each(function(){
	    $(this).fadeOut(200);
	  });
	$(".cont_left_navbg1").find("span[class='down']").attr("class","up");
	$(".cont_left_navbg1").attr("class","cont_left_navbg");
    $(this).find("span[class='down']").attr("class","up");
	$("#bookId_"+bookId).attr("class","cont_left_navbg1");
	$("#bookId_"+bookId).find("span[class='up']").attr("class","down");
	$("#lessonTree_"+bookId).fadeIn(500);
    ztreeObj = $.fn.zTree.init($("#lessonTree_"+bookId), setting, treeNodes);
}
//显示保存成功后的提示框
function showSuccessBox(){
	$("#continueOther").bind("click",function(){
		$("#box1").hide();
		$("#box2").hide();
		document.getElementById('iframe2').contentWindow.location.reload(true);
	 });
	$("#closeButton").bind("click",function(){
		$("#box1").hide();
		$("#box2").hide();
		document.getElementById('iframe2').contentWindow.location.reload(true);
	 });
	 $("#box2").show();
	 $("#box1").show();
}
function closeBox(){
	$("#box1").hide();
	$("#box2").hide();
	document.getElementById('iframe2').contentWindow.location.reload(true);
}

function setPlanFoder(){
	document.getElementById('iframe2').contentWindow.setPlanFoder();
}

function saveToServer_iframe(){
	try {
		document.getElementById("iframe2").contentWindow.saveToServer();
　　     } catch(error) {
	
　　     }
}
function fadeInOrOut_iframe(){
	try {
		document.getElementById("iframe2").contentWindow.fadeInOrOut();
　　     } catch(error) {
	
　　     }
}
//var iframedocument = document.getElementById("iframe2").contentWindow.document;
//上传本地教案
function uploadLocalLesson() {
	if ($("#lesson_id").val() == "" || $("#lesson_hours").val() == "") {
		alert("请先选择课题和课时！");
	} else {
		setPlanFoder2();
	}
}
function setPlanFoder2() {
	$("#folderbox2").css("visibility","");
}
function close2() {
	$("#folderbox2").css("visibility","hidden");
}
//资源上传成功,保存课件记录
function callbacksaveLesson(data) {
	//保存课件,持久化资源
	saveLessonRecord(data.data);
}

function saveLessonRecord(resId){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/saveLessonPlanWithResId",
			data:{
				"resId":resId,
				"planType":1,
				"lessonId":$("#lesson_id").val(),
				"lessonName":$("#lesson_name").val(),
				"lessonHours":$("#lesson_hours").val(),
				"planId":$("#plan_id").val(),
				"bookId":$("#book_id").val(),
				"gradeId":"${lessonPlan.gradeId}",
				"subjectId":"${lessonPlan.subjectId}"
			},
			success:function(data){
				if(data.code==1){
					close2();
					showSuccessBox();
				}else{
					alert("保存教案失败!");
				}
			}
		});
}
</script>

</head>
<body>
<input type="hidden" id="lesson_id" name="lessonId" value="" />
<input type="hidden" id="book_id" name="bookId" value="" /> 
<input type="hidden" id="lesson_name" name="lessonName" value="" />
<input type="hidden" id="gradeId" name="gradeId" value="${lessonPlan.gradeId }" />
<input type="hidden" id="subjectId" name="subjectId" value="${lessonPlan.subjectId }" />
<!-- 已选课时 -->
<input type="hidden" id="lesson_hours" name="hoursIdStr" value="" />
<!-- 教案id -->
<input type="hidden" id="plan_id" name="planId" value="" />
<!-- 上传本地教案弹窗 -->
	<div id="folderbox2" class="saved_successfully1"
		style="z-index: 2000;visibility: hidden;">
		<div class="saved_successfully_wrap1" style="z-index: 2000">
			<iframe
				style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
				frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
			<div class="dlog-top">
				<span>上传本地教案</span> <b class="dialog_close" style="right:0;top:0;" onclick="close2();"></b>
			</div>
			<div class="saved_bottom" style="background: #fff;">
				<div class="route">
					<h5>上传教案：</h5>
					<div id="fileuploadContainer" class="courseware_title_p scfj_to">
						<span class="courseware_title_p_span">*</span> <label for=""></label>
						<ui:upload containerID="fileuploadContainer" fileType="doc,docx"
							fileSize="50" startElementId="save"
							beforeupload="checksaveLesson" callback="callbacksaveLesson"
							name="resId"
							relativePath="writelessonplan/o_${_CURRENT_USER_.orgId}/u_${_CURRENT_USER_.id}"></ui:upload>
					</div>

					</br> <span class="kejian_tip">您可以上传doc,docx格式的文件</span>
					<div class="clear"></div>
					<div class="btn_bottom" style="margin-top: 20px;">
					<p id="save_b_btn">
						<input id="save" type="button" class="save_btn" value='上传'>
					</p>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="lesson_time" style="width: 98px;height: auto;padding-bottom:5px;background:#f7f7f7 ;border:1px #c2c2c2 solid;margin-top: 93px;margin-left: 120px;display:none; position: absolute; z-index: 1000;overflow:hidden;" align="center" onmouseleave="$(this).hide();">
	   	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
	   	<ul id="ul1">
	   	</ul>
	   	<input type='button' value="+添加" class="add_class_hour" style="margin:3px 0;"/>
	</div>
	<div id="box1" class="saved_successfully" style="z-index: 2000;display: none;">
		<div class="saved_successfully_wrap" style="z-index: 2000">
		<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
			<div class="dlog-top">
				<span>保存成功</span> 
				<span onclick="closeBox();"  class="close"></span>
			</div>
			<div class="saved_bottom">
					<h3>恭喜您，您撰写的教案已成功保存到“我的备课本”中！</h3>
					<p>
						<input type="button"  value="撰写其他课题" style="height:25px;" id="continueOther">
						<input type="button"  value="关闭" style="height:25px;" id="closeButton">
					</p>	
			</div>
		</div>
	</div>
	<div id="box2" style="width: 100%;height: 100%;display:none; position: absolute; z-index: 1001;overflow:hidden;" >
		<div class="box" style="z-index: 1001;">
		<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:2; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		</div>
	</div>	

	<div class="wrapper">
		<div class="com_cont">
			<div class="com_cont_left" style="display:block;">
			
				<h2>	
					<span class="com_cont_left_h2_span"></span>教材目录</span>
					<strong class="com_cont_left_h2_strong" title="设置保存位置" onclick="setPlanFoder();"></strong> 
				</h2>
				<div class="list">
					<select id="spacelist" style="width: 232px;border: none;line-height: 30px;height: 30px;font-size: 16px;" >
					<c:forEach items="${sessionScope._USER_SPACE_LIST_}" var="space">
						<c:if test="${not empty space.gradeId && not empty space.subjectId && not empty space.bookId }">
							<option value="${space.id }" ${currentBook.comId == space.bookId ?'selected':''}><jy:dic key="${space.gradeId}"></jy:dic><jy:dic key="${space.subjectId}"></jy:dic></option>
						</c:if>
					</c:forEach>
					</select>
				</div>
				<c:forEach var="book" items="${bookList }">
				<div class="list">
					<h3 class="cont_left_navbg" id="bookId_${book.comId }" onclick="showTree('${book.comId }');" title="${book.comName }"><ui:sout value="${book.comName }" length="30" needEllipsis="true"></ui:sout><span class="up"></span></h3>
					<div  class="clear"></div>
					<div class="com_cont_left_1">
						<ul id="lessonTree_${book.comId }" class="ztree">
			    		</ul>
		    		</div>
		    		
		    	</div>
				</c:forEach>
			</div>
			<div class="hiden_left">
    			<div class="hiden_left_right" title="收起目录">
    			</div>
    		</div>
			<div class="com_cont_right">
			<div id="qwe" style="height: 32px; overflow: hidden;">
				<h3 style="width: 13px; margin-right: 10px; float: left; cursor: pointer; line-height: 30px;"
					onclick="fadeInOrOut_iframe();" id="fade">
					<span class="qwe_h3_span" title="工具栏隐藏/显示"></span>点击这里，展开/收起工具栏
				</h3>
				<input type="button" onclick="uploadLocalLesson();" class="saveJA" value="上传本地教案"> 
				<input type="button" onclick="saveToServer_iframe();" class="saveJA" value="保存教案"> 
			</div>
				<div style="width:0px;height: 0px; display: none;" id="kongdiv"></div>
				<iframe id="iframe2" style="border: none;border:0;z-index:-1;width: 700px;height:730px; float: left;" scrolling="no" frameborder="0"  allowtransparency="true" src="${pageContext.request.contextPath }/jy/toEditWordPage">
					
				</iframe>
			</div>
			
		</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		<div class="clear"></div>
	</div>
	
</body>
<script>
$(document).ready(function(){
	$('.guide_8').hide();
	$('.guide_9').hide();
	$('.guide_10').hide();
	$('.guide_11').hide();
	$('.guide_7 span').click(function (){
		$('.guide_8').show();
		$('.guide_7').hide();
	});
	$('.guide_8 span').click(function (){
		$('.guide_8').hide();
		$('.guide_9').show();
	});
	$('.guide_9 span').click(function (){
		$('.guide_9').hide();
		$('.guide_10').show();
	});
	$('.guide_10 span').click(function (){
		$('.guide_10').hide();
		$('.guide_11').show();
	});
	$('.guide_11 span').click(function (){
		$('.guide_11').hide();
		$('.guide').hide();
	});
	var wH = document.documentElement.clientHeight? document.documentElement.clientHeight - 20:document.body.clientHeight - 20;
	$(".com_cont,.com_cont_left,.com_cont_right,#iframe2").css({"height": wH});
	$(window).resize(function() {
		var wH = document.documentElement.clientHeight? document.documentElement.clientHeight - 20:document.body.clientHeight - 20;
		$(".com_cont,.com_cont_left,.com_cont_right,#iframe2").css({"height": wH});
	});
})
</script>
</html>