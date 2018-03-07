<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<input type="hidden" value="${book.publisherId }" id="publisherId"/>
	<input type="hidden" value="${book.subjectId }" id="subjectId"/>
	<input type="hidden" value="${book.orgId }" id="orgId"/>
	<input type="hidden" value="${book.areaId }" id="areaId"/>
	<div class="searchBar">
	<div id="org_combox_jc_add_phase" class="select" ref="combox_jc_add_grade">
		<select class="combox" name="phase" id="w_org_combox_jc_add_phase" ref="org_combox_jc_add_grade" refurl="${pageContext.request.contextPath}/pf/back/teach/grade/map?phaseId={value}">
			<c:forEach items="${phaseList }" var="phase">
				<option value="${phase.id }" <c:if test="${phase.id == book.phaseId}"> selected="selected" </c:if> >${phase.name}</option>
			</c:forEach>
		</select>
		</div>
		<div id="org_combox_jc_add_grade" class="select">
			<select class="combox" name="grade" id="org_w_combox_jc_add_grade">
			<c:forEach items="${gradeList }" var="grade">
				<option value="${grade.id }" <c:if test="${grade.id == book.gradeLevelId}"> selected="selected" </c:if> >${grade.name}</option>
			</c:forEach>
			</select>
		</div>
		<div class="buttonActive"><div class="buttonContent"><button id="orgsearchBookBtn">搜索</button></div></div>
	</div>
</div>
<div class="pageContent" style="width: 800px;">

	<div id="book_add_list" class="pageContent" style="width:100%; height:260px;">
		<table width="100%" class="table">
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
	</table>
	</div>
	<div class="formBar">
		<ul>
			<li><a rel="bookAddIds" target="selectedTodo" targetType="dialog" href="${ctx }jy/back/schconfig/commdity/org/save?phaseId=${book.phaseId}&subjectId=${book.subjectId }&publisherId=${book.publisherId}&gradeLevelId=${book.gradeLevelId}&orgId=${book.orgId}&areaId=${book.areaId}" class="button" callback="reloadSchJCAfterAdd"><span>保存</span></a></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	function reloadSchJCAfterAdd(json){
		DWZ.ajaxDone(json);
		$.pdialog.closeCurrent();
		org_jxtx_jc_gl_ajaxDone(json);
	}
	
	function parseValue(obj,cid){
		$("#tid_"+cid,$.pdialog.getCurrent()).val(cid+"#"+$(obj).val());
	}
	
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
		$("#AllChosen").attr("checked", false);
		var curnj = "${book.gradeLevelId}";
		var $selectedvalue = $("input[name='jcgl_nj']:checked",$.pdialog._current).val();
		var ftr = $("div.pageContent tbody >tr:first-child", $.pdialog._current);
		var fline = 0;
		$("input[type='checkbox']").prop("checked", false);
		$("div.pageContent tbody tr",$.pdialog._current).each(function(index){
				  $this = $(this);
				  if($selectedvalue == '0' && $this.attr("data-nj") == curnj){
					  $this.show();
					  $this.find("input[type='checkbox']").attr("name","bookAddIds");
					  if(index != 0 && fline == 0){
						  $(">td",$this).each(function(i){
								$(this).width($($(">td",ftr)[i]).width());
							});
						  fline = 1;
					  }
				  }else if($selectedvalue == '1' && $this.attr("data-nj") != curnj){
					  $this.show();
					  $this.find("input[type='checkbox']").attr("name","bookAddIds");
					  if(index != 0 && fline == 0){
						  $(">td",$this).each(function(i){
								$(this).width($($(">td",ftr)[i]).width());
							});
						  fline = 1;
					  }
				  }else{
					  $this.find("input[type='checkbox']").attr("name","");
					  $this.hide();
				  }
		});
	});
	$(function(){
		$("#book_add_list").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/commdity/unaddList?phaseId=${book.phaseId}"+"&subjectId=${book.subject}"+"&gradeLevelId=${book.gradeLevelId}"+"&publisherId=${book.publisherId}"+"&orgId=${book.orgId}"+"&areaId=${book.areaId}",{},function(){
			$("#pageContent").find("[layoutH]").layoutH();
		});
	});
	$("#orgsearchBookBtn").click(function(){
		var phase = $("#w_org_combox_jc_add_phase").val();
		var grade = $("#org_w_combox_jc_add_grade").val();
		var publisher = $("#publisherId").val();
		var subject = $("#subjectId").val();
		var orgId = $("#orgId").val();
		var areaId = $("#areaId").val();
		$("#book_add_list").loadUrl(_WEB_CONTEXT_+"/jy/back/schconfig/commdity/unaddList?phaseId="+phase+"&subjectId="+subject+"&gradeLevelId="+grade+"&publisherId="+publisher+"&orgId="+orgId+"&areaId="+areaId,{},function(){
			$("#pageContent").find("[layoutH]").layoutH();
		});
	});
</script>
