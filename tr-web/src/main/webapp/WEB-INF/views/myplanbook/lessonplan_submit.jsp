<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="上传课件"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/myplanbook/css/dlog_submit.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
</head>
<body style="background:#fff;">
			<div class="upload-bottom_submit_big_tab">
			 	<div style="overflow:auto;height:420px;width:800px;">
					<div class="upload-bottom_submit">
						<p>
							<input type="checkbox" onclick="quanxuan(this);"> 
							<b>全选</b>
						</p>
						<c:if test="${!isSubmit}">
							<input type="button" class="submit2" onclick="submitLessonPlans(true);" value="提交">
						</c:if>
						<c:if test="${isSubmit}">
							<input type="button" class="submit1" onclick="submitLessonPlans(false);" value="取消提交">
							<span>注意：禁选的表示上级领导已查阅，不允许取消提交！</span>
						</c:if>
					</div>
					<ul class="expmenu">
						<c:forEach var="level1" items="${treeList }" varStatus="statu">
						<c:if test="${not empty dataMap[level1.lessonId]}">
							<li id="li_level1_${statu.index }" levelname="level1">
							<c:if test="${!dataMap[level1.lessonId]['isLeaf'] }">
							<%--处理父级节点  --%>
								<p>
									<input type="checkbox" name="check_${level1.lessonId }" level="parent" child="check_">
								</p>
								<a class="header">
									<span class="label">${level1.lessonName}</span>
									<span class="arrow up"></span>
								</a>
								<ui:submitLesson dataMap="${dataMap}" data="${level1 }" level="${statu.index }" jiaoan="true" kejian="true" fansi="true"></ui:submitLesson>
							</c:if>
							<c:if test="${dataMap[level1.lessonId]['isLeaf'] }">
							<%--处理叶节点  --%>
								<p>
									<input type="checkbox" name="check_${level1.lessonId }" child="check_" level="leaf">
								</p>
								<a class="header">
									<span class="label">${level1.lessonName}</span>
									<strong>
										<span id="span_level1_${level1.lessonId }_jiaoan" style="display: none;"><input type="checkbox" child="check_jiaoanAll" onclick="checkOrNot(this,'check_jiaoan');">教案：<span id="span_level1_${level1.lessonId }_jiaoan_num"></span></span>
										<span id="span_level1_${level1.lessonId }_kejian" style="display: none;"><input type="checkbox" child="check_kejianAll" onclick="checkOrNot(this,'check_kejian');">课件：<span id="span_level1_${level1.lessonId}_kejian_num"></span></span>
										<span id="span_level1_${level1.lessonId }_fansi" style="display: none;"><input type="checkbox" child="check_fansiAll" onclick="checkOrNot(this,'check_fansi');">反思：<span id="span_level1_${level1.lessonId }_fansi_num"></span></span>
									</strong>
									<span class="arrow up"></span>
								</a>
								<ul class="menu" style="display:none;">
									<li>
										<ol class="menu1" id="level1_${level1.lessonId }">
											<c:forEach var="lessonPlan" items="${dataMap[level1.lessonId].jiaoanList }" varStatus="statu1">
							                <li typename="jiaoan">
							                	<ui:icon ext="${lessonPlan.planType==1?'ppt':'doc' }" title="${lessonPlan.planName }"></ui:icon>
							               		<span title="${lessonPlan.planName }">教案${statu1.index+1 }</span>
						               			<input value="${lessonPlan.planId }" type="checkbox" class="li_box" child="check_jiaoan" name="check_${level1.lessonId }_jiaoan" <c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
							               	</li>
							               	</c:forEach>
							               	<c:forEach var="lessonPlan" items="${dataMap[level1.lessonId].kejianList }" varStatus="statu1">
							                <li typename="kejian">
							               		<ui:icon ext="${lessonPlan.planType==1?'ppt':'doc' }" title="${lessonPlan.planName }"></ui:icon>
							               		<span title="${lessonPlan.planName }">课件${statu1.index+1 }</span>
						               			<input value="${lessonPlan.planId }" type="checkbox" class="li_box" child="check_kejian" name="check_${level1.lessonId }_kejian" <c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
							               	</li>
							               	</c:forEach>
							               	<c:forEach var="lessonPlan" items="${dataMap[level1.lessonId].fansiList }" varStatus="statu1">
							                <li typename="fansi">
							               		<ui:icon ext="${lessonPlan.planType==1?'ppt':'doc' }" title="${lessonPlan.planName }"></ui:icon>
							               		<span title="${lessonPlan.planName }">反思${statu1.index+1 }</span>
						               			<input value="${lessonPlan.planId }" type="checkbox" class="li_box" child="check_fansi" name="check_${level1.lessonId }_fansi" <c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
							               	</li>
							               	</c:forEach>
							  			</ol>
									</li>
								</ul>
							</c:if>
							</li>
						</c:if>
						<div class="clear"></div>
						</c:forEach>
					</ul>
					</div>
			</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
</body>
<script type="text/javascript">
$(document).ready(function(){
	chushiNum();
	/* 滑动/展开 */
	$("ul.expmenu li > a.header > span.label").click(function(){
		var arrow = $(this).parent().find("span.arrow");	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}
		if(arrow.hasClass("up")){
			$(this).parent().removeClass("headers");
		}else if(arrow.hasClass("down")){
			$(this).parent().addClass("headers");
		}
		$(this).parent().next('ul.menu').slideToggle();
		
	});
	$("ul.expmenu li > a.header > span.arrow").click(function(){
		var arrow = $(this).parent().find("span.arrow");	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}	
		if(arrow.hasClass("up")){
			$(this).parent().removeClass("headers");
		}else if(arrow.hasClass("down")){
			$(this).parent().addClass("headers");
		}
		$(this).parent().next('ul.menu').slideToggle();
		
	});
	/* 滑动/展开 */
	$("ul.expmenu li > a.headertag > span.label").click(function(){
		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().next("ul.menu").slideToggle();
		
	});
	$("ul.expmenu li > a.headertag > span.arrow").click(function(){

		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().next("ul.menu").slideToggle();
		
	});	
	
	//未checkbox添加事件
	$("input[level='leaf']").bind("click",function(){
		checkLeaf($(this));
	});
	$("input[level='parent']").bind("click",function(){
		checkLeaf($(this));
	});
});
//操作叶子节点复选框
function checkLeaf(obj){
	var ischecked = $(obj).prop("checked");
	var parent = $(obj).parents("li:first");
	parent.find("input[child='"+$(obj).attr("child")+"']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"jiaoanAll']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"kejianAll']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"fansiAll']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"jiaoan']:enabled").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"kejian']:enabled").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"fansi']:enabled").prop("checked",ischecked);
}

function checkOrNot(obj,childName){
	var ischecked = $(obj).prop("checked");
	var parent = $(obj).parents("li:first");
	parent.find("input[child='"+childName+"']:enabled").prop("checked",ischecked);
}
//提交或取消提交备课资源
function submitLessonPlans(isSubmit){
	var idsStr = "";
	var url;
	$("input[class='li_box']").each(function(){
		if($(this).prop("checked")){
			idsStr = idsStr+$(this).val()+",";
		}
	});
	if(idsStr==""){
		if(isSubmit){
			alert("请先选择备课资源！");
		}else{
			alert("您还没有选择可以被取消提交的备课资源，请检查");
		}
		return false;
	}
	idsStr = idsStr.substring(0,idsStr.length-1);
	if(isSubmit){
		url = _WEB_CONTEXT_+"/jy/myplanbook/submitLessonPlans.json";
	}else{
		url = _WEB_CONTEXT_+"/jy/myplanbook/unSubmitLessonPlans.json";
	}
	$.ajax({  
        async : false,  
        cache:true,  
        type: 'POST',  
        dataType : "json",  
        data:{lessonPlanIdsStr:idsStr},
        url:  url,
        error: function () {
        	
        },  
        success:function(data){
        	if(data.result=='success'){
            	alert("操作成功！");
            	$("#is_submit",parent.document).attr("class","upload-bottom_tab_blue");
        		$("#not_submit",parent.document).removeAttr("class");
        		$("#iframe2",parent.document).attr("src",_WEB_CONTEXT_+"/jy/myplanbook/getIsOrNotSubmitLessonPlan?bookId=${bookId}&isSubmit="+1);
            	//window.location.reload();
            }else{
            	alert("系统出错，提交失败");
            }
        }  
    });
}
//全选
function quanxuan(obj){
	var isOrNot = false;
	if($(obj).prop("checked")){
		isOrNot = true;
	}else{
		isOrNot = false;
	}
	$("input[type='checkbox']").each(function(){
		if(!$(this).is(":disabled")){
			$(this).prop("checked", isOrNot);
		}
	});
}
//初始数量
function chushiNum(){
	$(".menu1").each(function(){
		var jiaoanNum = $(this).find("li[typename='jiaoan']").length;
		var kejianNum = $(this).find("li[typename='kejian']").length;
		var fansiNum = $(this).find("li[typename='fansi']").length;
		if(jiaoanNum>0){
			$("#span_"+$(this).prop("id")+"_jiaoan_num").html(jiaoanNum);
			$("#span_"+$(this).prop("id")+"_jiaoan").show();
		}
		if(kejianNum>0){
			$("#span_"+$(this).prop("id")+"_kejian_num").html(kejianNum);
			$("#span_"+$(this).prop("id")+"_kejian").show();
		}
		if(fansiNum>0){
			$("#span_"+$(this).prop("id")+"_fansi_num").html(fansiNum);
			$("#span_"+$(this).prop("id")+"_fansi").show();
		}
		if(jiaoanNum<=0 && kejianNum<=0 && fansiNum<=0){
			$("#li_"+$(this).prop("id")).remove();
		}
	});
}
</script>
</html>
