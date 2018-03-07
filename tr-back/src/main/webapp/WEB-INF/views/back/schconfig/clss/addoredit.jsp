<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent">
		<form method="post" action="${ctx }/jy/back/schconfig/clss/save"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, clsdialogAjax)">
			<div class="pageFormContent" layoutH="58">
				<div class="unit">
				 <label>班级编号：</label>
				 <input type="text" name="code" size="30" maxlength="30"
						   class="required" value="${cls.code}"/>
				</div>
				<div class="unit">
					<label>班级名称：</label>
					<input type="text" name="name" size="30" maxlength="10"
						   class="required" value="${cls.name}"/>
				</div>
				<div class="unit">
					<label>所属年级：</label>
					<ui:relation var="grades" type="xdToNj" id="${phase }" orgId="${cls.orgId }"></ui:relation>
					<select name="gradeId">
						<c:forEach items="${grades }" var="g">
							<option value="${g.id }" ${cls.gradeId == g.id ? 'selected':'' }>${g.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="unit">		   
				    <label>顺序：</label>
				    <input type="text" name="sort" size="10" maxlength="10"
						   class="required digits" value="${cls.sort}"/>
				</div>
			</div>
			<input type="hidden" name="id" value="${cls.id}"/>
			<input type="hidden" name="orgId" value="${cls.orgId}"/>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" style="margin-top: 0px;">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div></li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
