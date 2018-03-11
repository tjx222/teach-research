<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
	<meta charset="UTF-8" />
	<ui:mHtmlHeader title="教研平台"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/common/css/index_m.css" media="screen" />
	<link rel="stylesheet" href="${ctxStatic }/m/plainSummary/css/ps_check.css" media="screen" /> 	
	<ui:require module="../m/plainSummary/js"></ui:require>
</head>
<body> 
<div class="check_menu_wrap">
	<div class="check_block_menu">
		<span class="check_menu_top"></span>
		<div id="wrap2" class="check_menu_wrap1"> 
			<div id="scroller">
			   <c:choose>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
					<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
					<c:forEach items="${subjects }" var="s" varStatus="st">
							<p data="${s.id }" class="${st.index==0?'act':''}">${s.name }</p>
					</c:forEach>
				</c:when>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
					<h4 class="_subOrGrade">
						<p data="${_CURRENT_SPACE_.subjectId }"><jy:dic key="${_CURRENT_SPACE_.subjectId }"/></p>
					</h4>
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
<div class="check_menu1_wrap">
	<div class="check_block_menu1">
		<span class="check_menu_top"></span>
		<div id="wrap3" class="check_menu_wrap1"> 
			<div id="scroller">
			    <c:choose>
					<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
						<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
						<c:forEach items="${grades }" var="g" varStatus="st">
							<p data="${g.id }" class="${st.index==0?'act':''}">${g.name }</p>
						</c:forEach>
					</c:when>
					<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
						  <p data="${_CURRENT_SPACE_.gradeId }" ><jy:dic key="${_CURRENT_SPACE_.gradeId }"/></p>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅教师计划总结
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="check_content">
			<div class="check_content_top">
				<h3>筛选：</h3>
				<div class="check_content_block">
					<label>学科</label>
					<c:choose><c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
					<span id="subjectSelect">${empty subjects ? '' : subjects[0].name }<strong></strong></span> 
					<div id="selectsubject" data="${empty subjects ? '' : subjects[0].id }"></div></c:when>
					<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'xkZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
					<span><jy:dic key="${_CURRENT_SPACE_.subjectId }"></jy:dic><strong></strong></span> 
					<div id="selectsubject" data="${_CURRENT_SPACE_.subjectId }" ></div></c:when><c:otherwise></c:otherwise></c:choose>
				</div> 
				<div class="check_content_block1">
				<label>年级</label>
				<c:choose><c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
				<span id="gradeSelect">${empty grades ? '' : grades[0].name } <strong></strong></span><div id="selectgrade" data="${empty grades ? '' : grades[0].id }"></div></c:when>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
				<span style="width:9rem;"><jy:dic key="${_CURRENT_SPACE_.gradeId }"/> <strong></strong></span><div id="selectgrade" data="${_CURRENT_SPACE_.gradeId }"></div></c:when><c:otherwise></c:otherwise></c:choose>
				</div>
			</div>
			<div id="userListContent">
				
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
require(['zepto','teacher'],function($){	
}); 
</script>
</html>