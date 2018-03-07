<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="提交听课记录"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/dlog_submit.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
</head>
<body  style="background: #fff">
<div class="upload-bottom_submit_big_tab">
 	<div style="height:467px;width:794px;">
		<div class="upload-bottom_submit">
			<p>
				<input type="checkbox" onclick="quanxuan(this);"> 
				<b>全选</b>
			</p>
			<c:if test="${!isSubmit}">
				<input type="button" class="submit-sj submit2" value="提交" onclick="submitLectrueRecords(true);">
			</c:if>
			<c:if test="${isSubmit}">
				<input type="button" class="submit-sj submit1" value="取消提交" onclick="submitLectrueRecords(false);">
				<span>注意：禁选的表示上级领导已查阅，不允许取消提交！</span>
			</c:if>
		</div>
		<div class="collective_cont_tab">
			<table border="1">
			  <tr>
			    <th style="width:270px;">课题</th>
			    <th style="width:120px;">年级学科</th>
			    <th style="width:120px;">授课人</th>
			    <th style="width:120px;">听课节数</th>
			    <th style="width:120px;">听课时间</th> 
			  </tr> 
			 </table>
			 <div class="collective_cont_table">
			 <table border="1">
			 <c:forEach var="record" items="${recordsList }">
			 	<tr id="${record.id}">
				  <td class="td1" style="text-align:left;">
				  	 <input type="checkbox" value="${record.id }"> 
				     <strong>【${record.type==0?'校内':'校外' }】</strong>
				     <span onclick="seetopic(this);" title="${record.topic }"><ui:sout value="${record.topic }" length="26" needEllipsis="true"></ui:sout></span>
				  </td>
				  <td class="td120"><c:if test="${record.type==0 }">${record.grade }${record.subject }</c:if><c:if test="${record.type==1 }">${record.gradeSubject }</c:if></td>
				  <td class="td120">${record.teachingPeople }</td>
				  <td class="td120">${record.numberLectures }</td>
				  <td class="td120"><fmt:formatDate value="${record.lectureTime }" pattern="MM-dd"/></td>
			  	</tr> 
			 </c:forEach>
			  
			</table>
			</div>
		</div>
	</div>
</div>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
	<script src="${ctxStatic }/modules/lecturerecords/js/lecturerecords.js"></script>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
});

//提交或取消提交备课资源
function submitLectrueRecords(isSubmit){
	var idsStr = "";
	var url;
	$(".collective_cont_table").find("input[type='checkbox']").each(function(){
		if($(this).prop("checked")){
			idsStr = idsStr+$(this).val()+",";
		}
	});
	if(idsStr==""){
		if(isSubmit){
			alert("请先选择听课记录！");
		}else{
			alert("您还没有选择可以被取消提交的听课记录，请检查");
		}
		return false;
	}
	idsStr = idsStr.substring(0,idsStr.length-1);
	$.ajax({  
        async : false,  
        cache:true,  
        type: 'POST',  
        dataType : "json",  
        data:{lessonPlanIdsStr:idsStr,isSubmit:isSubmit},
        url:  url = _WEB_CONTEXT_+"/jy/lecturerecords/submitLectureRecords.json",
        error: function () {
        	
        },  
        success:function(data){
        	if(data.result==0){
            	alert("操作成功！");
            	$("#is_submit",parent.document).attr("class","upload-bottom_tab_blue");
        		$("#not_submit",parent.document).removeAttr("class");
        		$("#iframe2",parent.document).attr("src",_WEB_CONTEXT_+"/jy/lecturerecords/getIsOrNotSubmitRecords?isSubmit=1");
            	//window.location.reload();
            }else if(data.result==1){
            	alert("您选择的听课记录包含了以被查阅的，部分取消提交成功");
            }else{
            	alert("操作失败");
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

</script>
</html>
