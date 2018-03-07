<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>
<div id="phaseBox" class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">

<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add" href="${ctx}jy/back/schconfig/subject/area/toadd?phaseId=${phaseId}&areaId=${areaId}" target="dialog" mask="true"><span>添加</span></a></li>
				<li><a class="delete" href="${ctx}jy/back/schconfig/subject/area/delete/{sid_obj}?phaseId=${phaseId}&areaId=${areaId}" target="ajaxTodo" callback="reloadSubjectBox" title="确定要删除吗?"><span>删除</span></a></li>
<%-- 				<li><a class="edit" href="${ctx }pf/back/teach/phase/refreshMetaCache" target="ajaxTodo" ><span>刷新数据缓存</span></a></li> --%>
				<li class="line">line</li>
		</ul>
	</div>
	<table class="table" layoutH="76" width="100%">
		<thead>
			<tr>
				<th width="120">编号</th>
				<th width="120">ID</th>
				<th width="120">名称</th>
				<th width="120">显示顺序</th>
			</tr>
		</thead>
		<tbody class="sortDrag"  dragOptions="{dragSelector: 'tr',dragEnd:sortPhase}" > 
			<c:forEach items="${subjectList }" var="subject" varStatus="s" begin="0">
				
				<tr target="sid_obj" rel="${subject.id }" name="subject_se" >
					<td>${s.count }</td>
					<td>${subject.id }</td>
					<td>${subject.name }</td>
					<td>${s.count }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar"></div>
	<script type="text/javascript">
	var _OLD_PHASE_SORT = [];
	function sortPhase(){
		var _g_index = 0;
		var idList = new Array();
		$("tr[name='subject_se']").each(function(){
			idList.push($(this).attr("rel"));
			_g_index++;
			$(this).children("td:eq(3)").html(_g_index);
		});
// 		if(_OLD_PHASE_SORT.toString() != idList.toString()){
// 			$.getJSON("${ctx }/jy/back/jxtx/sortPhase",{paramIds:idList},function(){_OLD_PHASE_SORT = idList});
// 		}
	}
	</script>
</div>
</body>
<script type="text/javascript">
	function reloadSubjectBox(){
		$("#area_subjectBox").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/subject/area/list?phase=${phaseId}&areaId=${areaId}",null,function(){
			$("#area_subjectBox").find("[layoutH]").layoutH();
		} );
	}
</script>