<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="通知公告"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/annunciate/css/notice.css" media="screen">
<ui:require module="annunciate/js"></ui:require>
</head>
<body>
	<div class="wrapper">
		<div class="jyyl_top"><ui:tchTop style="1" hideMenuList="false"></ui:tchTop></div>
		<div class="jyyl_nav">
			当前位置：
			<c:if test="${ja.isForward==0}">
				<jy:nav id="zftzgg"></jy:nav>
			</c:if>
			<c:if test="${ja.isForward==1}">
				<jy:nav id="fbtzgg">
					<jy:param name="tzggname" value="转发通知公告"></jy:param>
				</jy:nav>
			</c:if>
		</div>
		<div class="clear"></div>
		<form id="annunciate_form" method="get">
			<input type="hidden" name="id" value="${ja.id }"/>
			<input type="hidden" name="isForward" value="1"/>
			<input type="hidden" name="flags" value="${ja.forwardDescription}"/>
			<div class='file_wrap'> 
				<h2 style="width: 80%;font-size:16px;line-height:50px;height:50px;text-align:left;padding-left:55px;">
					转发通知：
				</h2>
				<div style="margin:0 auto;width:90%;height:177px;margin-bottom:20px;"><textarea id="web_editor" name="forwardDescription" style="float:right"><c:if test="${ja.isForward==1}">${ja.forwardDescription}</c:if></textarea></div>
				<div style="border-bottom:1px #a9a9a9 dashed;height:1px;margin:10px auto;width:100%;clear:both;"></div>
				<c:if test="${ja.redTitleId!=0}">
					<div class="file_wrap_top">
						<h3 class="file_wrap_h3">
							<span>${jrt.title}</span>
						</h3>
						<h4 class="file_wrap_h4">${ja.fromWhere}</h4>
					</div> 
				</c:if>
				<div class="file_wrap_bottom">
					<h3 class="pt_file_wrap_h3">
						<span title="${ja.title}">${ja.title}</span>
					</h3> 
					<div class="clear"></div>
					<jy:di key="${ja.crtId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
					<h4 class="file_wrap_bottom_h4">发布时间：<fmt:formatDate value="${ja.crtDttm}" pattern="yyyy-MM-dd" />&nbsp;&nbsp;|&nbsp;&nbsp;       作者：${u.name }</h4> 
					<div class="file_wrap_border"></div>
					
					<p>${ja.content}</p>
				</div>
				<c:if test="${!empty ja.attachs}">
					<div class="file_enclosure">
						<h3>
							<span></span>
							<strong>附件</strong>
							<b>${attachSum }</b>
						</h3>
						<div class="file_enclosure_cont">
							<c:forEach items="${rList}" var="res">
								<dl onclick="scanResFile('${res.id}');">
									<dd></dd>
									<dt title="${res.name}.${res.ext }"><ui:sout value="${res.name}.${res.ext }" length="30" needEllipsis="true" ></ui:sout></dt>
								</dl>
							</c:forEach>
						</div>
					</div> 
				</c:if>
				<div class="clear"></div>
				<div class="publish_index" style="margin-left:49px;">
					<input type="checkbox" id='publish_check' ${ja.isDisplay==1?'checked':''} name="isDisplay" value="1">
					<label for="publish_check">发布到学校首页</label>
				</div>
				<div class="clear"></div>
				<p style="text-align:center;margin-top:10px;">
					<input type="button" class="publish" onclick="release(true);">
					<input type="button" class="save_drafts" onclick="release(false);">
				</p> 
				<div class="clear"></div>
			</div>
		</form> 
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','forward'],function(){
		
	});
</script>
</html>
