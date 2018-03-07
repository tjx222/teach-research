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
		<div class="jyyl_top">
			<ui:tchTop style="1" hideMenuList="false"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			当前位置：
			<c:choose>
				<c:when test="${type==1}">
					<jy:nav id="cktzggxx"></jy:nav>
				</c:when>
				<c:otherwise>
					<c:if test="${ja.isForward==0}">
						<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
							<jy:nav id="cktzgg"></jy:nav>
						</c:if>
						<c:if test="${!jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')&&!jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
							<jy:nav id="ckwzftzgg"></jy:nav>
						</c:if>
					</c:if>
					<c:if test="${ja.isForward==1}"><jy:nav id="cktzgg"></jy:nav></c:if>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="clear"></div>
		<div class='file_wrap'>
			<c:if test="${!jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')&&!jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
				<c:if test="${ja.annunciateType==1&&ja.isForward==1}">
					<h2 style="width: 80%;font-size:16px;line-height:50px;height:50px;text-align:left;padding-left:55px;">
						转发通知:
					</h2>
					<div class="Teaching_schedule_cont" style="min-height: 0px;">
						<p style="text-align: justify;" class="forwardInfo">${ja.forwardDescription}</p>
					</div>
				</c:if>
			</c:if> 
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
					<c:if test="${!jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')&&!jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
						<c:if test="${type==0 && ja.annunciateType==1 && ja.isForward==0}">
							<input type='button' class="forward" value="转发" onclick="forwardAnnunciate('${ja.id}')">
						</c:if>
					</c:if>
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
			<ul class="article" data-id="${ja.id}" data-status="${ja.status}" data-type="${type}">
				<c:if test="${isFirst!=true}">
					<li class="last_one" style="cursor: pointer;"><a><span></span>上一篇</a></li>
				</c:if>
				<c:if test="${isLast!=true}">
					<li class="next_article"><a><span></span>下一篇</a></li>
				</c:if>
			</ul>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','annunciate'],function(){
		
	});
</script>
</html>
