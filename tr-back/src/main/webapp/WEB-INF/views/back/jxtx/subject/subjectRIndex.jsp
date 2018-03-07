<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx }/jy/back/jxtx/addSubject?eid=${eid}" target="dialog" mask="true" ><span>添加学科</span></a></li>
			<li><a class="delete" href="${ctx }/jy/back/jxtx/deleteSubject?eid=${eid}&xkid={sid_obj}" target="ajaxTodo" callback="reloadSubjectGl" title="确定要删除吗?" ><span>删除</span></a></li>
			<li><a class="edit" href="${ctx }/jy/back/jxtx/refreshMetaCache" target="ajaxTodo"><span>刷新数据缓存</span></a></li>
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
		<tbody class="sortDrag" dragOptions="{dragSelector: 'tr',dragEnd:sortSubject}" style="overflow-y: auto; ">
			<c:forEach items="${subjectList }" var="subject" varStatus="c">
				<tr target="sid_obj" rel="${subject.id }" name="sub_ject">
					<td>${subject.id }</td>
					<td>${subject.standardCode }</td>
					<td>${subject.name }</td>
					<td>${c.count }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar"></div>
	<script type="text/javascript">
	var _OLD_SUBJECT_SORT = [];
	function sortSubject(){
		var _g_index = 0;
		var idList = new Array();
		var nameList = new Array();
		$("tr[name='sub_ject']").each(function(){
			idList.push($(this).attr("rel"));
			_g_index++;
			$(this).children("td:eq(3)").html(_g_index);
			nameList.push($(this).children("td:eq(2)").text());
		});
		if(_OLD_SUBJECT_SORT.toString() != idList.toString()){
			$.getJSON("${ctx }/jy/back/jxtx/sortSubject",{eid:${eid},subjectIds:idList,subjectNames:encodeURIComponent(nameList)},function(){_OLD_SUBJECT_SORT = idList});
			parent.reloadXkglBox("${eid }");
		}
	}
	</script>
</div>
<script type="text/javascript">
	function  reloadSubjectGl(){
		parent.reloadXkglBox("${eid }");
	}
</script>