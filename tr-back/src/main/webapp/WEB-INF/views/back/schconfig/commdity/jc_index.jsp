<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="pageContent" id="orgJcBox" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${canOverride }">
				<li><a class="add" href="${ctx }jy/back/schconfig/commdity/add_jc?phaseId=${book.phaseId}&subjectId=${book.subjectId}&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}&orgId=${book.orgId}&areaId=${book.areaId}" target="dialog" height="370" mask="true" ><span>添加教材</span></a></li>
				<li><a class="add" href="${ctx }jy/back/schconfig/commdity/custom/add?phaseId=${book.phaseId}&subjectId=${book.subjectId}&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}&orgId=${book.orgId}&areaId=${book.areaId}" target="dialog" mask="true" ><span>自定义教材</span></a></li>
				<li><a class="delete" href="${ctx }jy/back/schconfig/commdity/del_jc?id={sid_obj}&orgId=${book.orgId}&areaId=${book.areaId}" target="ajaxTodo" title="确定要删除吗?" callback="org_jxtx_jc_gl_ajaxDone" ><span>删除</span></a></li>
				<li><a class="edit" href="${ctx }jy/back/jxtx/edit_jc?id={sid_obj}" target="dialog" mask="true" ><span>修改</span></a></li>
				<li><a class="edit" href="${ctx }jy/back/jxtx/edit_gl?id={sid_obj}" height="250" target="dialog" mask="true" ><span>关联</span></a></li>
				<li><a class="edit" rel="jiaocai_box" callback="org_jxtx_jc_gl_ajaxDone" href="${ctx }jy/back/jxtx/auto_gl?phaseId=${book.phaseId}&subjectId=${book.subjectId}&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}" target="ajaxTodo" mask="true" ><span>自动关联</span></a></li>
			</c:if>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="104" rel="jbsxBox">
		<thead>
			<tr width="100%">
				<th>ID</th>
				<th width="20%">教材名称</th>
				<!-- <th width="15%">教材简称</th> -->
				<th width="10%">年级</th>
				<th width="10%">版次</th>
				<th width="8%">册别</th>
				<th width="10%">关联状态</th>
				<!-- <th width="8%">排序</th> -->
				<th width="15%">创建时间</th>
			</tr>
		</thead>
		<tbody style="overflow-y: auto; ">
			<c:forEach items="${booklist }" var="book">
				<tr target="sid_obj" rel="${book.id }">
					<td>${book.comId }</td>
					<td>${book.comName }</td>
					<%-- <td>${book.formatName }</td> --%>
					<td>${book.gradeLevel }</td>
					<td>${book.bookEdtion }</td>
					<td>${book.fascicule }</td>
					<td title="${book.relationComId}">${empty book.relationComId ? book.fasciculeId != 178 ? '<font style="line-height:21px" color="red">上下册教材未关联</font>':'全一册，无需关联' : book.fasciculeId != 178 ? '上下册教材已关联':'全一册，无需关联' }</td>
					<%-- <td>${book.comOrder }</td> --%>
					<td><fmt:formatDate value="${book.bookInTime }" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">

	</div>
<script type="text/javascript">
function org_jxtx_jc_gl_ajaxDone(json){
	DWZ.ajaxDone(json);
	if($.pdialog._current){
		$.pdialog.closeCurrent();
	}
	$("#orgJcBox").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/commdity/jcList?phaseId=${book.phaseId }&subjectId=${book.subjectId }&publisherId=${book.publisherId }&gradeLevelId=${book.gradeLevelId }&orgId=${book.orgId}&areaId=${book.areaId}",null,function(){
		$("#orgJcBox").find("[layoutH]").layoutH();
	});
}
</script>
</div>
