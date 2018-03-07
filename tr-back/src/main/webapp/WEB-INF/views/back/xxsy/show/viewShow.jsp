<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	.content span{line-height:18px;	}
	.content em{line-height:18px;}
	.content strong{line-height:18px;}
</style>
	<div class="pageContent">
		<form method="post" action="${ctx }/jy/school/show/save"
			class="pageForm required-validate"
			onsubmit="return iframeCallback(this,dialogAjaxDone)"  enctype="multipart/form-data">
			<div class="pageFormContent" layoutH="58">

				<%-- <div class="unit">
					<c:if test="${!empty id}">
					      <label>编号：</label>
						  <h3>${id}</h3>
						  <input type="hidden" name="id" value="${id}"/>
				    </c:if>
				</div> --%>
				<div class="unit">
					<label>标题：</label>
					<input type="text" name="title" size="30" maxlength="10"
						   class="required" disabled value="${shoolShow.title}"/>
				</div>
				<div class="unit">
					<label>所属类型：</label>
					<spna ${type=='master' ? 'checked':'style="display:none"'}><input type="radio" name="type" size="40" ${type=='master' ? 'checked':'disabled'}  class="required" value="master"/>校长风采</spna>
				    <spna ${type=='overview' ? 'checked':'style="display:none"'} ><input type="radio" name="type" size="40" ${type=='overview' ? 'checked':'disabled'}  class="required" value="overview"/>学校概况</spna>
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
				<div class="unit">
					<hr/>	
				</div>	
				<div class="unit">		   
				    <label style="display:inline-block;width:100%;">详细内容：</label>
				    <div  class="content" style="padding:5px;" >	
				    	${shoolShow.introduction}
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
