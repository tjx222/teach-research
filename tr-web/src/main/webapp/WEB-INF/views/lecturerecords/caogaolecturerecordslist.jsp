<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="草稿记录"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css">
</head>
<body>
			<div class="table_cont">
				 	<table border=1 >
						<tr>
							 <th style="width:250px;">课题</th>
						    <th style="width:110px;">年级学科</th>
						    <th style="width:110px;">授课人</th>
						    <th style="width:100px;">操作</th>
						</tr>
						<c:forEach var="kt" items="${data.datalist }">
						<tr id="${kt.id}">
						    <td style="text-align:left;"><strong>${kt.type==0?'【校内】':'【校外】'}</strong>
						    	<span onclick="editCaogao(this,${kt.type});">
						    		<c:choose>
								     	<c:when test="${fn:length(kt.topic)>13}">${fn:substring(kt.topic,0,12)}...</c:when>
								     	<c:otherwise>${kt.topic}</c:otherwise>
								     </c:choose>
						    	</span>
						    </td>
						    <td>
						    	<c:if test="${kt.type=='0'}">
						    		${kt.grade}${kt.subject}
						    	</c:if>
						    	<c:if test="${kt.type=='1'}">
						    		${empty kt.gradeSubject?'-':kt.gradeSubject}
						    	</c:if>
						    </td>
						    <td>${empty kt.teachingPeople?'-':kt.teachingPeople}</td>
						    <td>
						    <span title='编辑' class="continue_edit_btn" onclick="editCaogao(this,${kt.type});" title="编辑"></span>
							<span title='删除'  class='delete_btn deleteDraft' onclick="deletelecture(this);"  style="margin:0;" title="删除" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}"></span>
						    </td>
						  </tr>
						</c:forEach>
					</table> 
				</div>
		<div class="clear"></div>
		<div class="pages" style="margin-top: -60px;">
			<!--设置分页信息 --> 
			<form name="pageForm" method="post">
				<ui:page url="jy/lecturerecords/caogaoList" isAjax="true" callback="caoGaoList" data="${data}" views="10"/>
				<input type="hidden" class="currentPage" name="page.currentPage">
			</form>
		</div>
<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
<script type="text/javascript">
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
		window.parent.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/writeLectureRecordsInnerInput?addflag=false&id="+id;
	}
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
</script>
</body>
</html>