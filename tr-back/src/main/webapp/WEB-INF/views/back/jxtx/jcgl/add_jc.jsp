<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<input type="radio" name="jcgl_nj" value="0"/>当前年级&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="jcgl_nj" value="1">其他年级
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="pageContent" style="width: 800px;">
	<form method="post" action="${ctx }/jy/back/jxtx/save_jc" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	<div class="panelBar" style="height: 0px;">
	</div>
	<table class="table" width="100%" layoutH="80">
		<thead>
				<tr>
					<th style="width: 18px;">
							<input id="AllChosen" type="checkbox" group="bookAddIds" class="checkboxCtrl">
					</th>
					<th width="100">编号</th>
					<th width="160">教材名称</th>
					<th width="160">出版社</th>
					<th width="120">出版社简称</th>
					<th width="100">年级</th>
					<th width="80">版次</th>
					<th width="80">册别</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${booklist }"  var="book" varStatus="st">
					<tr data-nj="${book.gradeLevelId }" target="grade_meta" rel="${book.comId }">
						<td style="width: 18px;"><input id="tid_${book.comId }" name="bookAddIds" value="${book.comId }" type="checkbox"></td>
						<td>${book.comId  }</td>
						<td>${book.comName }</td>
						<td >${book.publisher }</td>
						<td><input id="shortName_${st.index }" onblur="parseValue(this,'${book.comId }')" class="saveCbsShortName" type="text" value="${empty publishShortName ? book.formatName : publishShortName }" maxlength="10" /></td>
						<td>${book.gradeLevel }</td>
						<td>${book.bookEdtion }</td>
						<td>${book.fascicule }</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="formBar">
		<label style="float:left"><input type="checkbox" class="checkboxCtrl" group="jc1" onclick="setAllDefaultCbs()" />全选</label>
		<ul>
			<li>
				<a rel="bookAddIds" target="selectedTodo" targetType="dialog" href="${ctx }jy/back/jxtx/save_jc?phaseId=${book.phaseId}&subjectId=${book.subjectId }&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}" class="button" callback="jxtx_jc_gl_ajaxDone"><span>保存</span></a>
			</li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>
</div>
<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 300,width:700}}, $.pdialog.getCurrent(), "");
	function setDefaultCbs(index){
		var getSaveCbsShortName = parent.getSaveCbsShortName();
		if($("#shortName_"+index).val()==null || $("#shortName_"+index).val()==""){
			$("#shortName_"+index).val(getSaveCbsShortName);
		}
		if($("#sortVal_"+index).val()==null || $("#sortVal_"+index).val()==""){
			$("#sortVal_"+index).val(index);
		}
	}
	function setAllDefaultCbs(index){
		var getSaveCbsShortName = parent.getSaveCbsShortName();
		$.each($(".saveCbsShortName"),function(){
			if($(this).val()==null || $(this).val()==""){
				$(this).val(getSaveCbsShortName);
			}
		});
		$.each($(".saveCbsSort"),function(){
			if($(this).val()==null || $(this).val()==""){
				$(this).val($(this).attr("atrindex"));
			}
		});
	}
	
	$("input[name='jcgl_nj']",$.pdialog._current).change(function() {
		var curnj = "${book.gradeLevelId}";
		var $selectedvalue = $("input[name='jcgl_nj']:checked",$.pdialog._current).val();
		var ftr = $("div.pageContent tbody >tr:first-child", $.pdialog._current);
		var fline = 0;
		$("div.pageContent tbody tr",$.pdialog._current).each(function(index){
				  $this = $(this);
				  if($selectedvalue == '0' && $this.attr("data-nj") == curnj){
					  $this.show();
					  if(index != 0 && fline == 0){
						  $(">td",$this).each(function(i){
								$(this).width($($(">td",ftr)[i]).width());
							});
						  fline = 1;
					  }
				  }else if($selectedvalue == '1' && $this.attr("data-nj") != curnj){
					  $this.show();
					  if(index != 0 && fline == 0){
						  $(">td",$this).each(function(i){
								$(this).width($($(">td",ftr)[i]).width());
							});
						  fline = 1;
					  }
				  }else{
					  $this.find("input[name='jc1']").attr("checked",false);
					  $this.hide();
				  }
		});
	});
	
</script>
