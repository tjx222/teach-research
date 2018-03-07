<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="${ctx }/jy/back/jxtx/save_jc" class="pageForm required-validate" onsubmit="return validateCallback(this, ${book.orgId == '0' && book.areaId == '0' ? 'jxtx_jc_gl_ajaxDone':'org_jxtx_jc_gl_ajaxDone'});">
		<input type="hidden" value="${book.id }" name="id"/>
		<input type="hidden" value="${book.comId }" name="comId"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>教材名称：</label>
				<input name="comName" class="required" type="text" maxlength="20" value="${book.comName }" alt="请输入教材名称"/>
				<span style="position: relative;top:5px;"></span>
			</p>
			<p>
				<label>教材简称：</label>
				<input name="formatName" class="required" type="text" maxlength="10" value="${book.formatName }" alt="请输入教材简称"/>
			</p>
			<p>
				<label>版次：</label>
				<select name="bookEdtionId" onchange="changejcbc(this)">
					<c:forEach items="${bclist }" var="bc">
						<option id="bc_${bc.id }" ${bc.id==book.bookEdtionId?'selected="selected"':'' }  value="${bc.id }">${bc.name }</option>
					</c:forEach>
				</select>
				<input id="bookEdtion" type="hidden" name="bookEdtion" value="${book.bookEdtion }" />
			</p>
			<p>
				<label>显示顺序：</label>
				<input type="text" name="comOrder" value="${book.comOrder }" class="required digits" maxlength="3" style="width: 60px;"/>
			</p>
			<p>
				<label>册别：</label>
				<span style="position: relative;top:5px;">${book.fascicule }</span>
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
//版次切换选择修改
function changejcbc(obj){
	var idv = $(obj).val();
	$("#bookEdtion").val($("#bc_"+idv).text())
}
</script>

	