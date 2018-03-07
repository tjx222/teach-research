<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
 <div id="sch_clsss_container" class="pageContent j-resizeGrid" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid" >
	<div class="searchBar" id="sch_clss_searchbox" data-grade="${gradeId }"> 
	<form id="pagerForm" onsubmit="return divSearch(this, 'cls_phase_${phase }');" action="${ctx}jy/back/schconfig/clss/list" method="post">
		<table class="searchContent" >
			<tr>
				<td>
					年级：
					<ui:relation var="grades" type="xdToNj" id="${phase }" orgId="${orgId }"></ui:relation>
					<input type="radio" name="gradeId" value="" ${empty gradeId  ?'checked' :'' }/>全部
					<c:forEach var="g" items="${grades }">
						<input type="radio" name="gradeId" value="${g.id }" ${gradeId == g.id ?'checked' :'' }/>${g.name}
					</c:forEach>
				</td>
			</tr>
		</table>
		 <input type="hidden" name="order"/>
	     <input type="hidden" name="flago"/>
		<input type="hidden" name="flags"/>
		<input type="hidden" name="orgId" value="${orgId }"/>
		<input type="hidden" name="phase" value="${phase }"/>
	</form>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a id="sch_clss_add_link" class="add" href="jy/back/schconfig/clss/add?orgId=${orgId }&phase=${phase }&grade=${gradeId}" target="dialog" mask="true" width="500" height="255"><span>新增</span></a></li>
			<li><a class="edit" href="jy/back/schconfig/clss/add?id={clsId}&phase=${phase }" target="dialog" mask="true" width="500" height="315"><span>修改</span></a></li>
			<a class="add" href="${ctx}jy/back/schconfig/clss/batchadd?orgId=${orgId }&phase=${phase }&grade=${gradeId}"  id="class_batch_import" 
					target="dialog" mask="true" width="780" height="360"><span>快速添加</span></a>
			<li><a class="delete" href="jy/back/schconfig/clss/delete?id={clsId}" callback="clsdialogAjax" target="ajaxTodo" title="将删除班级信息，删除后不可恢复，确定要删除吗?" ><span>删除</span></a></li>
			<li><a class="edit" href="jy/back/schconfig/clss/editclsuser?id={clsId}&phase=${phase }" target="dialog" mask="true" width="500" height="315"><span>任课教师设置</span></a></li>
			<li><a class="add" href="jy/back/schconfig/clss/batchTeacher?phaseId=${phase }&orgId=${orgId }" target="dialog" mask="true" width="580" height="315"><span>批量设置任课教师</span></a></li>
		</ul>
	</div>
	   <c:if test="${not empty clsList }">
		<table class="table" width="99%" layoutH="148">
			<thead>
				<tr>
					<th orderField="code" class="${model.flago == 'code' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}" >编号</th>
					<th>班级名称</th>
					<th>年级</th>
					<th orderField="sort" class="${model.flago == 'sort' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">显示顺序</th>
					<th orderField="crtDttm" class="${model.flago == 'crtDttm' ?  model.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
					<th>禁用</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="cls" items="${clsList }" >
				<tr rel="${cls.id }" target="clsId">
					<td>${cls.code }</td>
					<td>${cls.name }</td>
					<td><jy:dic key="${cls.gradeId }"></jy:dic></td>
					<td>${cls.sort }</td>
					<td><fmt:formatDate value="${cls.crtDttm}" pattern="yyyy-MM-dd"/></td>
					<td>
						<c:if test="${cls.enable==0 }"><a class="btnClose" target="ajaxTodo"  href="jy/back/schconfig/clss/state?id=${cls.id}" callback="clsdialogAjax" title="您确定要启用它吗？"></a></c:if>
						<c:if test="${cls.enable==1 }"><a class="btnOpen" target="ajaxTodo"  href="jy/back/schconfig/clss/state?id=${cls.id}" callback="clsdialogAjax" title="您确定要禁用它吗？"></a></c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	    </c:if>
		<c:if test="${empty clsList }">
				<div class="prompt" style="margin-top: 130px; ">
					<p>
						<span>没有班级信息哟！ </span>
					</p>
				</div>
		   </c:if>
	</div>
<script type="text/javascript">
var BASEURL="jy/back/schconfig/clss/add?orgId=${orgId }&phase=${phase }";
$().ready(function(){
	$("#sch_clss_searchbox input[type='radio']").click(function(){
		var cls_gid = $("#sch_clss_searchbox").attr("data-grade");
		if(cls_gid != $(this).val()){
			$("#sch_clss_add_link").attr("href",BASEURL+"&gradeId="+$(this).val());
			$("#sch_clss_searchbox form").submit();
		}
	});
});
function clsdialogAjax(json){
	$.pdialog.closeCurrent();
	DWZ.ajaxDone(json);
	var turl = "${ctx}jy/back/schconfig/clss/list?orgId=${orgId }&gradeId=${gradeId}&phase=${phase }";
	$("#cls_phase_${phase }",navTab.getCurrentPanel()).loadUrl(turl);
}
</script>