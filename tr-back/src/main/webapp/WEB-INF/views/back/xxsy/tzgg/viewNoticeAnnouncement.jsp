<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
	<div class="grid" id="viewNoticeId"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<form method="post" action=""
			class="pageForm required-validate"
			onsubmit="return iframeCallback(this,dialogAjaxDone)"  enctype="multipart/form-data">
			<div class="pageFormContent" layoutH="58">
			<c:if test="${jyAnnunciate.type==1}">
				<div class="unit">	
				    <jy:di var="redfile" key="${jyAnnunciate.redTitleId}" className="com.tmser.tr.back.xxsy.redhm.service.RedHeadManageService"></jy:di>
				    <label>红头标题：</label>
				    <div class="content">${redfile.title}</div>
				</div>
				<div class="unit">		   
				    <label>文件编号：</label>
				    <div class="content">${jyAnnunciate.fromWhere}</div>
				</div>
			</c:if>
				<div class="unit">		   
				    <label>通知标题：</label>
				    <div class="content">${jyAnnunciate.title}</div>
				</div>
				<div class="unit">
					<label>发布时间：</label>
					<fmt:formatDate value="${jyAnnunciate.crtDttm}" pattern="yyyy-MM-dd HH:mm"/>
				</div>
				<div class="unit">		   
				    <label>作者：</label>
				    <div class="content">${jyAnnunciate.crtName}</div>
				</div>
				<div class="unit">		   
				    <label>公告内容：</label>
				    <div class="content">${jyAnnunciate.content}</div>
				</div>
				<div class="unit">		   
				    <label>附件：</label>
				    <div style="float:left;width:600px;">
					    <c:if test="${rList ne null}">
						   <%--  <c:forEach items="${rList}" var="res">
							<tr value="${res.id}">
								<td title="${res.name}.${res.ext }">
									<b class="delete_fj"  ></b>
									<span onclick="scanResFile('${res.id}');">
										<a  href="javascript:void(0);"><ui:sout value="${res.name}.${res.ext }" length="30" needEllipsis="true" ></ui:sout></a><br>
									</span>
								</td>
							</tr>
						</c:forEach> --%>
						<c:forEach items="${rList}" var="res">
							<tr>
								<td>
								<ui:attach filename="${res.name}.${res.ext}" args="${res.id}"></ui:attach></td>
							</tr>

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
