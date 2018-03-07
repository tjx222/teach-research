<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" id="sch_save" action="${ctx }jy/back/schconfig/commdity/custom/save" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneJC);">
		<input type="hidden" value="${book.publisherId}" name="publisherId">
		<input type="hidden" value="${book.phaseId }" name="phaseId">
		<input type="hidden" value="${book.subjectId }" name="subjectId">
		<input type="hidden" value="${book.gradeLevelId }" name="gradeLevelId">
		<input type="hidden" value="${book.orgId }" name="orgId">
		<input type="hidden" value="${book.areaId }" name="areaId">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>教材名称：</label>
				<input name="comName" id="comName" type="text" size="30" value="" class="val_name_ required" maxlength="20"/>
			</div>
			<div class="unit">
				<label>教材简称：</label>
				<input name="formatName" id="formatName" type="text" size="30" value="${book.formatName }" class="val_name_ required" maxlength="20"/>
			</div>
			
			<div class="unit">
				<label>册别：</label>
				<pft:config code="fascicule" var="fascicule"></pft:config>
				<select name="fasciculeId" id="fascicule_sel">
					<c:forEach items="${fascicule }" var = "cb">
						<option value="${cb.id }">${cb.name }</option>
					</c:forEach>
				</select>
			</div>
			<input id="fascicule_int" type="hidden" name="fascicule" value=""/>
			
			<div class="unit">
				<label>版次：</label>
				<pft:config code="edition" var="edition"></pft:config>
				<select name="bookEdtionId" id="edition_sel">
					<c:forEach items="${edition }" var = "bc">
						<option value="${bc.id }">${bc.name }</option>
					</c:forEach>
				</select>
			</div>
			<input id="bookEdition_int" type="hidden" name="bookEdtion" value=""/>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		$("#fascicule_int").val($("#fascicule_sel").find("option:selected").text());
		$("#bookEdition_int").val($("#edition_sel").find("option:selected").text());
	});
	$("#fascicule_sel").change(function(){
		var $this = $(this);
		$("#fascicule_int").val($this.find("option:selected").text());
	});
	
	$("#edition_sel").change(function(){
		var $this = $(this);
		$("#bookEdition_int").val($this.find("option:selected").text());
	});
	
	function dialogAjaxDoneJC(json){
		org_jxtx_jc_gl_ajaxDone(json);
	}
</script>
