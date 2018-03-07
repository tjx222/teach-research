<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="${ctx }/jy/back/jxtx/saveCbs" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" value="${pr.id }" name="id"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>出版社全称：</label>
				<span style="position: relative;top:5px;">${pr.name }</span>
			</p>
			<p>
				<label>出版社简称：</label>
				<input name="shortName" class="required" type="text" maxlength="10" value="${pr.shortName }" alt="请输入出版社简称"/>
			</p>
			<p>
				<label>显示顺序：</label>
				<input type="text" name="sort" value="${pr.sort }" class="required digits" maxlength="3" style="width: 60px;"/>
			</p>
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
function dialogAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
			alertMsg.correct("修改成功");
			parent.reloadCbsBox("${pr.phaseId }","${pr.subjectId}");
		}
	}
}
</script>

	