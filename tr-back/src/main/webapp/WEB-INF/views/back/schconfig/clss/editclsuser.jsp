<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="pageContent" id="class_user_edit">
		<form method="post" action="${ctx }/jy/back/schconfig/clss/saveuser"
			class="pageForm"
			onsubmit="return validateCallback(this, clsdialogAjax)">
			<div class="pageFormContent" layoutH="58">
				<ui:relation var="subjectMetas" type="xdToXk" id="${phase }" orgId="${clss.orgId}"></ui:relation>
				<c:forEach items="${subjectMetas }" var="meta" varStatus="state">
					<div class="unit">
					 <label>${meta.name}：</label>
					 <input name="userList[${state.index}].tchId" value="${schUserMap[meta.id].tchId}" type="hidden">
					 <input name="userList[${state.index}].subjectId" value="${meta.id}" type="hidden">
					 <input type="text" name="userList[${state.index}].username" size="30" maxlength="30"
							  value="${schUserMap[meta.id].username}" 
							   suggestFields="tchId,username" suggestUrl="${ctx }jy/back/schconfig/clss/lookupuser?orgId=${clss.orgId}&grade=${clss.gradeId}&subject=${meta.id}" lookupGroup="userList[${state.index}]"/>
					</div>
				</c:forEach>
				<div class="unit">
					<label>班主任：</label>
					 <input name="master.tchId" value="${master.tchId }" type="hidden">
					 <input type="text" name="master.username" size="30" maxlength="30" class="search"
							    value="${master.username}" 
							   suggestFields="tchId,username" suggestUrl="${ctx }jy/back/schconfig/clss/lookupuser?orgId=${clss.orgId}" lookupGroup="master"/>
				</div>
			</div>
			<input type="hidden" name="clsid" value="${clss.id}"/>
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
