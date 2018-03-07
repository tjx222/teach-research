<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
.form_cont{padding-left:10px;min-width:1400px;}
.form_cont select{margin-right:10px;width:120px;height:24px;}
.form_cont button{width:50px;height:24px;line-height:17px;}
#startTime{width: 120px; background-color: #fff;  height: 19px;}
#endTime{width: 120px; background-color: #fff;  height: 19px;} 
.form_cont div{float:left;font-size: 14px; line-height: 35px;}
#opr_content .grid .gridCol{height:42px;}
</style>
<div class="tabsContent pageContent" id="opr_content"> 
		<form id="pagerForm" action="jy/back/operation/toOrgOperationInfoList" onsubmit="return ${empty sessionScope._CURRENT_ORG_ ?"divSearch(this, 'operation_load')":"navTabSearch(this,'operationId')" };" method="post">
		<div>
			<input type="hidden" name="areaId" value="${area.id }"/>
		
			<div class="pageHeader" style="height:auto;border: 1px #B8D0D6 solid;">
				<div style="font-size:18px;height:35px;line-height:35px;padding-left:10px;border-bottom: 1px #B8D0D6 solid;font-family: '微软雅黑';">
					${area.name }各校教研情况总览   <input type="button" style="float:right;margin-top:4px;width:50px;height:22px;" onclick="exportIt_org();" value="导出">
				</div>
				<div class="form_cont"  id="orgInfoListDiv">
					<div>
					按学段查看：
					<select id="phase" name="phaseId" onchange="initSubjectAndGrade();">
						<c:forEach var="phase" items="${phaseList }">
							<option value="${phase.id }" <c:if test="${search.phaseId==phase.id }">selected="selected"</c:if>>${phase.name }</option>
						</c:forEach>
					</select>
					</div>
					<div>
					按学科查看：
					<select id="subject" name="subjectId">
						<option value="">--请选择--</option>
						<c:forEach var="subject" items="${subjectList }">
							<option value="${subject.id }" <c:if test="${search.subjectId==subject.id }">selected="selected"</c:if>>${subject.name }</option>
						</c:forEach>
					</select>
					</div>
					<div>
					按年级查看：
					<select id="grade" name="gradeId">
						<option value="">--请选择--</option>
						<c:forEach var="grade" items="${gradeList }">
							<option value="${grade.id }" <c:if test="${search.gradeId==grade.id }">selected="selected"</c:if>>${grade.name }</option>
						</c:forEach>
					</select>
					</div>
					<div>
					按时间段查看：
					<input id="startTime" type="text" name="startTime" value="<fmt:formatDate value="${search.startTime}" pattern="yyyy-MM-dd HH:mm"/>" dateFmt="yyyy-MM-dd HH:mm"  readonly="readonly"  maxDate="%y-%M-%d" class="date"/>到
					<input id="endTime" type="text" name="endTime" value="<fmt:formatDate value="${search.endTime}" pattern="yyyy-MM-dd HH:mm"/>" dateFmt="yyyy-MM-dd HH:mm" readonly="readonly"  maxDate="%y-%M-%d" class="date"/>
					<button type="submit" style="width:55px;">搜索</button>
					</div>
				</div>
				</div>
				<table class="table" width="99%" layoutH="195">
					<thead>
						<tr style="height:42px;">
							<th>序号</th>
							<th>学校名称</th>
							<th>用户数</th>
							<th>撰写教案<br />(篇数/课)</th>
							<th>教学检查<br />(查阅数)</th>
							<th>分享发表<br />(篇数)</th>
							<th>集体备课<br />(发起数)</th>
							<th>集体备课<br />(参与次数)</th>
							<th>成长档案<br />(资源数)</th>
							<th>同伴互助<br />(留言数)</th>
							<th>资源总数</th>
							<th>人均资源数</th>
							<th>教研详情</th>
						</tr>
					</thead>
					<tbody>
					<c:if test="${fn:length(infoPageList.datalist)>0 }">
					<tr style="background:#E8E8E8;height:27px;line-height:27px;">
						<td>总计</td>
						<td></td>
						<td>${totalVo.userCount }</td>
						<td>${totalVo.lessonPlanCount}/${totalVo.lessonPlanCountLesson}</td>
						<td>${totalVo.viewCount }</td>
						<td>${totalVo.shareCount }</td>
						<td>${totalVo.activityPushCount }</td>
						<td>${totalVo.activityJoinCount }</td>
						<td>${totalVo.progressResCount }</td>
						<td>${totalVo.peerMessageCount }</td>
						<td>${totalVo.resTotalCount }</td>
						<td>${totalVo.perResCount }</td>
						<td></td>
					</tr>
					</c:if>
					<c:forEach var="orgInfo" items="${infoPageList.datalist }" varStatus="status">
						<tr>
							<td>${status.index+1}</td>
							<td>${orgInfo.orgName }</td>
							<td>${orgInfo.userCount }</td>
							<td>${orgInfo.lessonPlanCount}/${orgInfo.lessonPlanCountLesson }</td>
							<td>${orgInfo.viewCount }</td>
							<td>${orgInfo.shareCount }</td>
							<td>${orgInfo.activityPushCount }</td>
							<td>${orgInfo.activityJoinCount }</td>
							<td>${orgInfo.progressResCount }</td>
							<td>${orgInfo.peerMessageCount }</td>
							<td>${orgInfo.resTotalCount }</td>
							<td>${orgInfo.perResCount }</td>
							<td>
								<a><a href="${ctx}jy/back/operation/toUserOperationInfo?orgId=${orgInfo.orgId}&areaId=${area.id}" target="navTab" rel="unit_info" style="color:#00f" title="${orgInfo.orgName }">进入学校</a>
								<a href="${ctx}jy/back/operation/exportUserOperation?orgId=${orgInfo.orgId}" target="_blank" style="color:#00f">导出</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="panelBar">
					<div class="pages">
						<span>显示</span>
						<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'operation_load')">
							<option value="20" ${infoPageList.page.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${infoPageList.page.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${infoPageList.page.pageSize == 100 ? 'selected':''}>100</option>
						</select>
						<span>条，共${infoPageList.page.totalCount}条</span>
				  </div>
				<input type="hidden" name="page.currentPage" value="1" />
			    <input type="hidden" name="page.pageSize" value="${infoPageList.page.pageSize }" />
			    <input type="hidden" name="order" value="${param.orderField}" />
			    <input type="hidden" name="flago" value="" />
				<input type="hidden" name="flags" value="" />
			    <div class="pagination" rel="operation_load" totalCount="${infoPageList.page.totalCount }" numPerPage="${infoPageList.page.pageSize }" pageNumShown="10" currentPage="${infoPageList.page.currentPage }"></div>
				</div>
			</div>
			 
		</form> 
		
</div>
<script>
$(document).ready(function(){
	if("${search.subjectId}" != "" || "${search.gradeId}" != ""){
		initSubjectAndGrade();
	}
});
function initSubjectAndGrade(){
	var phaseId = $("#phase").val();
	$.ajax({
		async : false, 
		type: 'POST',
		url:_WEB_CONTEXT_+"/jy/back/operation/getSubjectAndGradeByPhase.json",
		data:{'phaseId':phaseId},
		dataType:"json",
		cache: false,
		success: function(data){
			var subjectList = data.subjectList;
			var gradeList = data.gradeList;
			var subjectOptionStr = "<option value=''>--请选择--</option>";
			var gradeOptionStr = "<option value=''>--请选择--</option>";
			if(subjectList!=null){
				for(var i=0;i<subjectList.length;i++){
					if("${search.subjectId}" != "" && subjectList[i].id == '${search.subjectId}'){
						subjectOptionStr = subjectOptionStr+"<option value='"+subjectList[i].id+"' selected='selected'>"+subjectList[i].name+"</option>";
					}else{
						subjectOptionStr = subjectOptionStr+"<option value='"+subjectList[i].id+"'>"+subjectList[i].name+"</option>";
					}
				}
			}
			if(gradeList!=null){
				for(var i=0;i<gradeList.length;i++){
					if("${search.gradeId}" != "" && gradeList[i].id == '${search.gradeId}' ){
						gradeOptionStr = gradeOptionStr+"<option value='"+gradeList[i].id+"' selected='selected'>"+gradeList[i].name+"</option>";
					}else{
						gradeOptionStr = gradeOptionStr+"<option value='"+gradeList[i].id+"'>"+gradeList[i].name+"</option>";
					}
				}
			}
			$("#subject").html(subjectOptionStr);
			$("#grade").html(gradeOptionStr);
		},
		error: function(){
			alert("系统错误");
			return false;
		}
	});
}

function exportIt_org(){
    var downform2 = $("<form>");   //定义一个form表单
    downform2.attr('style', 'display:none');   //在form表单中添加查询参数
    downform2.attr('target', '_blank');
    downform2.attr('method', 'post');
    downform2.attr('action', _WEB_CONTEXT_+"/jy/back/operation/exportOrgsOperation");
   	var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'phaseId');
    input1.attr('value', $("#phase",$("#orgInfoListDiv")).val());
    var input2 = $('<input>');
    input2.attr('type', 'hidden');
    input2.attr('name', 'subjectId');
    input2.attr('value', $("#subject",$("#orgInfoListDiv")).val());
    var input3 = $('<input>');
    input3.attr('type', 'hidden');
    input3.attr('name', 'gradeId');
    input3.attr('value', $("#grade",$("#orgInfoListDiv")).val());
    var input4 = $('<input>');
    input4.attr('type', 'hidden');
    input4.attr('name', 'startTime');
    input4.attr('value', $("#startTime",$("#orgInfoListDiv")).val());
    var input5 = $('<input>');
    input5.attr('type', 'hidden');
    input5.attr('name', 'endTime');
    input5.attr('value', $("#endTime",$("#orgInfoListDiv")).val());
    var input6 = $('<input>');
    input6.attr('type', 'hidden');
    input6.attr('name', 'areaId');
    input6.attr('value','${area.id}');
    $('body').append(downform2);
    downform2.append(input1); 
    downform2.append(input2);
    downform2.append(input3);
    downform2.append(input4);
    downform2.append(input5);
    downform2.append(input6);
    downform2.submit(); 
}
</script>