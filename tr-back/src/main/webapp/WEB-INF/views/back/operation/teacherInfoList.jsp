<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
.form_cont button{width:50px;height:24px;line-height:17px;}
.form_cont div{float:left;font-size: 14px; line-height: 35px;}
#opr_content .grid .gridCol{height:42px;}
</style>
<div class="tabsContent pageContent" id="opr_content">
<form id="pagerForm" action="jy/back/operation/toTeacherOperationInfoList" onsubmit="return divSearch(this, 'operation_info');" method="post">
<input type="hidden" name="orgId" value="${org.id }" />
<div class="pageHeader" style="height:auto;border: 1px #B8D0D6 solid;" id="teacherInfoListDiv">
	<div class="form_cont">
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
		按姓名查看：
		<input id="userName" type="text" name="userName" style="width: 90px; background-color: #fff;  height: 19px;" value="${search.userName }"/> &nbsp;
		</div>
		<div style="width:430px;">
		按时间段查看：
		<input id="startTime" type="text" name="startTime" style="width: 90px; background-color: #fff;  height: 19px;"value="<fmt:formatDate value="${search.startTime}" pattern="yyyy-MM-dd HH:mm"/>" dateFmt="yyyy-MM-dd HH:mm"  readonly="readonly"  maxDate="%y-%M-%d" class="date"/>到
		<input id="endTime" type="text" name="endTime" style="width: 90px; background-color: #fff;  height: 19px;"value="<fmt:formatDate value="${search.endTime}" pattern="yyyy-MM-dd HH:mm"/>" dateFmt="yyyy-MM-dd HH:mm"  readonly="readonly"  maxDate="%y-%M-%d" class="date"/>
		<button type="submit" style="width:55px;">搜索</button> <input type="button" onclick="exportIt_teacher();" value="导出" style="width: 50px;height: 24px;">
		</div>
	</div>
</div>
<table class="table" width="100%" layoutH="238">
	<thead>
		<tr style="height:42px;">
			<th>序<br />号</th>
			<th>姓名</th>
			<th>状态</th>
			<th>撰写教案<br />(篇数/课)</th>
			<th>上传课件<br />(篇数/课)</th>
			<th>教学反思<br />(篇数/课)</th>
			<th>听课记录<br />(次数)</th>
			<th>教学文章<br />(篇数)</th>
			<th>计划总结<br />(篇数)</th>
			<th>集体备课<br />(参与次数)</th>
			<th>集体备课<br />(讨论数)</th>
			<th>集体备课<br />(任主备人次数)</th>
			<th>校际教研<br />(参与次数)</th>
			<th>校际教研<br />(讨论数)</th>
			<th>校际教研<br />(任主备人次数)</th>
			<th>区域教研<br />(参与次数)</th>
			<th>区域教研<br />(讨论数)</th>
			<th>同伴互助<br />(留言数)</th>
			<th>成长档案袋<br />(资源数)</th>
			<th>分享发表<br />(篇数)</th>
			<th>资源<br />总数</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${fn:length(infoPageList.datalist)>0  }">
	<tr style="background:#E8E8E8;height:27px;line-height:27px;">
		<td>总计</td>
		<td></td>
		<td></td>
		<td>${totalVo.lessonPlanCount}/${totalVo.lessonCount}</td>
		<td>${totalVo.kejianCount}/${totalVo.kejianLesson}</td>
		<td>${totalVo.fansiCount}/${totalVo.fansiLesson}</td>
		<td>${totalVo.listenCount}</td>
		<td>${totalVo.teachTextCount}</td>
		<td>${totalVo.planSummaryCount}</td>
		<td>${totalVo.activityJoinCount}</td>
		<td>${totalVo.activityDiscussCount}</td>
		<td>${totalVo.activityMainCount}</td>
		<td>${totalVo.schoolActivityJoinCount}</td>
		<td>${totalVo.schoolActivityDiscussCount}</td>
		<td>${totalVo.schoolActivityMainCount}</td>
		<td>${totalVo.regionActivityJoinCount}</td>
		<td>${totalVo.regionActivityDiscussCount}</td>
		<td>${totalVo.peerMessageCount}</td>
		<td>${totalVo.progressResCount}</td>
		<td>${totalVo.shareCount}</td>
		<td>${totalVo.resTotalCount}</td>
	</tr>
	</c:if>
	<c:forEach var="userInfo" items="${infoPageList.datalist }" varStatus="status">
		<tr>
			<td>${status.index+1}</td>
			<td>${userInfo.userName}</td>
			<td>${userInfo.status}</td>
			<td>${userInfo.lessonPlanCount}/${userInfo.lessonCount}</td>
			<td>${userInfo.kejianCount}/${userInfo.kejianLesson}</td>
			<td>${userInfo.fansiCount}/${userInfo.fansiLesson}</td>
			<td>${userInfo.listenCount}</td>
			<td>${userInfo.teachTextCount}</td>
			<td>${userInfo.planSummaryCount}</td>
			<td>${userInfo.activityJoinCount}</td>
			<td>${userInfo.activityDiscussCount}</td>
			<td>${userInfo.activityMainCount}</td>
			<td>${userInfo.schoolActivityJoinCount}</td>
			<td>${userInfo.schoolActivityDiscussCount}</td>
			<td>${userInfo.schoolActivityMainCount}</td>
			<td>${userInfo.regionActivityJoinCount}</td>
			<td>${userInfo.regionActivityDiscussCount}</td>
			<td>${userInfo.peerMessageCount}</td>
			<td>${userInfo.progressResCount}</td>
			<td>${userInfo.shareCount}</td>
			<td>${userInfo.resTotalCount}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'operation_info')">
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
    <div class="pagination" rel="operation_info" totalCount="${infoPageList.page.totalCount }" numPerPage="${infoPageList.page.pageSize }" pageNumShown="10" currentPage="${infoPageList.page.currentPage }"></div>
	</div>
</form>
</div>
<script>
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
					subjectOptionStr = subjectOptionStr+"<option value='"+subjectList[i].id+"'>"+subjectList[i].name+"</option>";
				}
			}
			if(gradeList!=null){
				for(var i=0;i<gradeList.length;i++){
					gradeOptionStr = gradeOptionStr+"<option value='"+gradeList[i].id+"'>"+gradeList[i].name+"</option>";
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
function exportIt_teacher(){
    var downform = $("<form>");   //定义一个form表单
    downform.attr('style', 'display:none');   //在form表单中添加查询参数
    downform.attr('target', '_blank');
    downform.attr('method', 'post');
    downform.attr('action', _WEB_CONTEXT_+"/jy/back/operation/exportTeacherOperation");
   	var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'phaseId');
    input1.attr('value', $("#phase",$("#teacherInfoListDiv")).val());
    var input2 = $('<input>');
    input2.attr('type', 'hidden');
    input2.attr('name', 'subjectId');
    input2.attr('value', $("#subject",$("#teacherInfoListDiv")).val());
    var input3 = $('<input>');
    input3.attr('type', 'hidden');
    input3.attr('name', 'gradeId');
    input3.attr('value', $("#grade",$("#teacherInfoListDiv")).val());
    var input4 = $('<input>');
    input4.attr('type', 'hidden');
    input4.attr('name', 'startTime');
    input4.attr('value', $("#startTime",$("#teacherInfoListDiv")).val());
    var input5 = $('<input>');
    input5.attr('type', 'hidden');
    input5.attr('name', 'endTime');
    input5.attr('value', $("#endTime",$("#teacherInfoListDiv")).val());
    var input6 = $('<input>');
    input6.attr('type', 'hidden');
    input6.attr('name', 'orgId');
    input6.attr('value','${org.id}');
    $('body').append(downform);
    downform.append(input1); 
    downform.append(input2);
    downform.append(input3);
    downform.append(input4);
    downform.append(input5);
    downform.append(input6);
    downform.submit(); 
}
</script>