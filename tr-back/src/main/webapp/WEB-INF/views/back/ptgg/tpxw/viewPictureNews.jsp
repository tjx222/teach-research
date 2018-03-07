<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		

	<div class="pageContent">
		<form method="post" action=""
			class="pageForm required-validate"
			onsubmit="return iframeCallback(this,dialogAjaxDone)"  enctype="multipart/form-data">
			<div class="pageFormContent" layoutH="58">

				<div class="unit">
					<c:if test="${!empty id}">
					      <label>编号：</label>
						  <h3>${id}</h3>
						  <input type="hidden" name="id" value="${id}"/>
				    </c:if>
				</div>
				<div class="unit">
					<label>标题：</label>
					<input type="text" name="title" size="30" maxlength="10"
						   class="required" disabled value="${pictureNews.title}"/>
				</div>
				<div class="unit">
					<label>发布时间：</label>
					<fmt:formatDate value="${pictureNews.crtDttm}" pattern="yyyy-MM-dd HH:mm"/>
				</div>
				<div class="unit">
					<label>作者：</label>
					<input type="text" name="title" size="30" maxlength="10"
						   class="required" disabled value="${pictureNews.crtname}"/>
				</div>
				<div class="unit">		   
				    <label>新闻内容：</label>
				    <div class="content">${pictureNews.content}</div>
				</div>
				<div class="unit">		   
				    <label>图片：</label>
				    <div style="float:left;width:600px;">
					    <c:if test="${imgUrls ne null}">
					    	<c:forEach items="${imgUrls}" var="imgUrl">
						    	<ui:photo src="${imgUrl}" width="128" height="134" />
						    </c:forEach>
					    </c:if>
					 </div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">关闭</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
