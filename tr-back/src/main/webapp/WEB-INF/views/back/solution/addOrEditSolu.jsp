<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid;">
	<form id="form" method="post" action="${ctx }/jy/back/solution/addOrEdit" class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<p>
				<label>方案名称：</label> 
				<input type="hidden" name="id" value="${solution.id }">
				<input class="required " name="name" type="text" size="36" maxlength="10" value="${solution.name }"/><br>
			</p>
			<p>
				<label>方案描述：</label>
				<textarea style="margin-left: 130px" maxlength="500" rows="10" cols="38" name="descs" value="${solution.descs}" >${solution.descs}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="submit" >保存</button>
						</div>
					</div>
				</li>
				<li>

					<div class="button">
						<div class="buttonContent"> 
							<button type="Button" class="close">取消</button>
						</div>
					</div>

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
				parent.reloadSolution();
			}
				
		}
	}

</script>

