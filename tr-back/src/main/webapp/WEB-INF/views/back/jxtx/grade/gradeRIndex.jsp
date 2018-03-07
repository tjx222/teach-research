<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }/jy/back/jxtx/addGrade?eid=${eid}" target="dialog" mask="true" ><span>添加年级</span></a></li>
			<li><a class="delete" href="${ctx }/jy/back/jxtx/deleteGrade?eid=${eid}&njid={sid_obj}" target="ajaxTodo" callback="reloadGardeGl" title="确定要删除吗?" ><span>删除</span></a></li>
			<li><a class="edit" href="${ctx }/jy/back/jxtx/refreshMetaCache" target="ajaxTodo" callback="DWZ.ajaxDone"><span>刷新数据缓存</span></a></li>
		</ul>
	</div>
	<input id="eid" type="hidden" name="eid" value="${eid }"/>
	<table class="table" width="100%" layoutH="104">
		<thead>
			<tr>
				<th width="120" orderField="number" class="asc">编号</th>
				<th orderField="name" width="250">代码</th>
				<th width="250">年级名称</th>
				<th width="250">显示顺序</th>
			</tr>
		</thead>
		<tbody class="sortDrag" dragOptions="{dragSelector: 'tr',dragEnd:sortGrade}" style="overflow-y: auto; ">
			<c:forEach items="${gradelist }" var="grade" varStatus="c">
				<tr rel="${grade.id }" target="sid_obj" name="gra_de">
					<td>${grade.id }</td>
					<td>${grade.standardCode }</td>
					<td>${grade.name }</td>
					<td>${c.count }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar"></div>
	<script type="text/javascript">
	var _OLD_GRADE_SORT = [];
	function sortGrade(){
		var _g_index = 0;
		var idList = new Array();
		var nameList = new Array();
		$("tr[name='gra_de']").each(function(){
			idList.push($(this).attr("rel"));
			_g_index++;
			$(this).children("td:eq(3)").html(_g_index);
			nameList.push($(this).children("td:eq(2)").text());
		});
		if(_OLD_GRADE_SORT.toString() != idList.toString()){
			$.getJSON("${ctx }/jy/back/jxtx/sortGrade",{eid:${eid},gradeIds:idList,gradeNames:encodeURIComponent(nameList)},function(){_OLD_GRADE_SORT = idList});
			parent.reloadNjglBox("${eid }");
		}
	}
	</script>
</div>

<script type="text/javascript">
	function  reloadGardeGl(){
		parent.reloadNjglBox("${eid }");
	}
</script>