<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					出版社名称筛选：<input type="text" name="area_cbs_srcname" id="area_cbs_srcname"/>
				</td>
				<td>
					<a class="buttonActive" href="${ctx}jy/back/schconfig/publisher/area/toadd/custom?phaseId=${phaseId}&subjectId=${subjectId }&areaId=${areaId}" target="dialog"><span>自定义出版社</span></a>
				</td>
			</tr>
			
		</table>
	</div>
</div>
<div class="pageContent">
	<table style="width:566px;" class="table" layoutH="90">
		<thead>
			<tr>
				<th style="width: 18px;">
						<input type="checkbox" group="publisherAddIds" class="checkboxCtrl">
				</th>
				<th style="width: 240px; cursor: pointer;">ID</th>
				<th style="width: 280px; cursor: pointer;">出版社名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${publisherList }"  var="cbs" varStatus="st">
				<tr data-name="${cbs.name }" target="grade_meta" rel="${cbs.id }">
					<td style="width: 18px;"><input name="publisherAddIds" value="${cbs.id }" type="checkbox"></td>
					<td style="width: 240px;">${cbs.id }</td>
					<td style="width: 280px;">${cbs.name }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="formBar">
		<ul>
			<li><a rel="publisherAddIds" target="selectedTodo" targetType="dialog" href="${ctx }jy/back/schconfig/publisher/area/save?phaseId=${phaseId}&subjectId=${subjectId }&areaId=${areaId}" class="button" callback="reloadAreaCBSAfterAdd"><span>保存</span></a></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">

function reloadAreaCBSAfterAdd(data){
	DWZ.ajaxDone(data);
	$.pdialog.closeCurrent();
	reLoadAreaCbs();
}
	var _selectCbs = function(obj){
		  var name = $(obj).val();
		  var ftr = $("div.pageContent tbody >tr:first-child", $.pdialog._current);
		  var fline = 0;
		  $("div.pageContent tbody tr",$.pdialog._current).each(function(index){
			  $this = $(this);
			  if($this.attr("data-name").indexOf(name) == -1){
				  $this.hide();
			  }else{
				  $this.show();
				  if(index != 0 && fline == 0){
					  $(">td",$this).each(function(i){
							$(this).width($($(">td",ftr)[i]).width());
						});
					  fline = 1;
				  }
			  }
		  });
	};
	$("#area_cbs_srcname",$.pdialog._current).focus(false).click(false).keyup(function(event){
		var $items = $(this);
		switch(event.keyCode){
			case DWZ.keyCode.ESC:
			case DWZ.keyCode.TAB:
			case DWZ.keyCode.SHIFT:
			case DWZ.keyCode.HOME:
			case DWZ.keyCode.END:
			case DWZ.keyCode.LEFT:
			case DWZ.keyCode.RIGHT:
			case DWZ.keyCode.ENTER:
			case DWZ.keyCode.DOWN:
			case DWZ.keyCode.UP:
				break;
			default:
				 _selectCbs($items);
		}
	});
	
	function reloadAreaCuntomPublisherList(){
		parent.area_add_reloadCbsBox("${phaseId}","${subjectId}");
	}

</script>
