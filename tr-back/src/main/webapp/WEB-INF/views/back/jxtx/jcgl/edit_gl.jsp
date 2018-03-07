<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="${ctx }/jy/back/jxtx/save_gl" class="pageForm required-validate" onsubmit="return validateCallback(this, ${book.orgId == '0' && book.areaId == '0' ? 'jxtx_jc_gl_ajaxDone':'org_jxtx_jc_gl_ajaxDone'});">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>教材名称：</label>
				${book.comName }-${book.bookEdtion }-${book.fascicule }
				<input type="hidden" name="comId" value="${book.comId }">
				<input type="hidden" name="phaseId" value="${book.phaseId}">
				<input type="hidden" name="subjectId" value="${book.subjectId}">
				<input type="hidden" name="publisherId" value="${book.publisherId}">
				<input type="hidden" name="gradeLevelId" value="${book.gradeLevelId}">
				<span style="position: relative;top:5px;"></span>
			</p>
			<p>
				<label>关联教材：</label>
				 <select name="relationComId">
				 	<c:forEach items="${booklist }" var="bk">
				 		<c:if test="${bk.fasciculeId != 178 && bk.fasciculeId != book.fasciculeId }">
				 			<option value="${bk.comId }" ${bk.comId == book.relationComId ?'selected':'' }>${bk.comName }-${bk.bookEdtion }-${bk.fascicule }</option>
				 		</c:if>
				 	</c:forEach>
				 </select>
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


	