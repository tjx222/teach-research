<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent">
	<form method="post" id="uForm" action="${ctx }jy/back/jxtx/update" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="id" value="${metaship.id}" />
		<input type="hidden" name="type" value="${metaship.type}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>学段名称：</label>
				<input type="text" size="30" name="name" value="${metaship.name }" class="required"/>
			</p>
			<p>
				<label>显示顺序：</label>
				<input class="required,digits" name="sort" type="text" size="30" value="${metaship.sort }"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onclick="submitThis();">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitThis(){
		$("#uForm").submit();
	}
  	function dialogAjaxDone(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
				parent.reloadPhaseBox(3);
			}
		}
	}
	
</script>