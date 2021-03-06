<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		

	<div class="pageContent">
		<form method="post" action=""
			class="pageForm required-validate"
			onsubmit="return iframeCallback(this,dialogAjaxDone)"  enctype="multipart/form-data">
			<div class="pageFormContent" layoutH="58">

				<div class="unit">		   
				    <label>标题：</label>
				    <div class="content">${pictureNews.title}</div>
				</div>
				<div class="unit">
					<label>发布时间：</label>
					<fmt:formatDate value="${pictureNews.crtDttm}" pattern="yyyy-MM-dd HH:mm"/>
				</div>
				<div class="unit">		   
				    <label>作者：</label>
				    <div class="content">${pictureNews.crtname}</div>
				</div>
				
				<c:if test="${not empty datalist }">
				<c:forEach items="${datalist}" var="data">
				<div class="unit">		   
				    <label>新闻内容：</label>
				    <div class="content">${data.content}</div>
				</div>
				<div class="unit">		   
				    <label>图片：</label>
				    <div style="float:left;width:600px;">
					    <c:if test="${data.path ne null}">
					    	<ui:photo src="${data.path}" width="128" height="134" />
					    </c:if>
					 </div>
				</div>
				</c:forEach>
				</c:if>
				
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
