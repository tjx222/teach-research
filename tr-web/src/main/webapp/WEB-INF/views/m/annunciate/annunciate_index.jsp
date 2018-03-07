<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="通知公告"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/annunciate/css/annunciate.css" media="screen">
	<ui:require module="../m/annunciate/js"></ui:require>
</head> 
<body>
<div class="fb_option_wrap">
	<div class="fb_option">
		<div class="fb_option_pt"></div>
		<div class="fb_option_ht"></div>
	</div>
</div>
<div class="act_draft_wrap" >
   <div class="act_draft">
		<div class="act_draft_title">
			<h3>草稿箱</h3>
			<span class="close"></span>
		</div>
		<iframe id="activity_iframe" src="" frameborder="0" scrolling="no" width="100%" height="100%"></iframe>
		<!-- <div class="act_draft_content">
			<div id="scroller"> 
			
			</div>
		</div> -->
	</div>
</div>
<div class="del_thesis_wrap">
	<div class="del_thesis">
		<div class="del_thesis_title">
			<h3>删除通知公告</h3>
			<span class="close"></span>
		</div>
		<div class="del_thesis_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该通知公告吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input id="deleteThesis" type="button" class="btn_confirm" value="确定" >
				<input id="cancelDelete" type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header> 
		<span onclick="javascript:window.history.go(-1);"></span>通知公告
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="annunciate_wrap">
		 	<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'xz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'zr')}">
				<div class="annunciate_top">
					<div class="annunciate_right"> 
					   
							<div class="draft">
								草稿箱(${draftNum})
							</div> 
							<input type="button" class="fbtz" value="发布通知">
						
					</div>
				</div>
			</c:if>
			<div class="annunciate_bottom_wrap" id="annunciate_b_wrap" style="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'xz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'zr')==true?'':'top: 5.1rem;'}"> 
				<div id="scroller">
					<div class="annunciate_bottom" >
						<table> 
						
						  <tr>
						     <th style="width:5%;"></th>
						     <th style="width:50%;text-align:left;padding-left: 1%;">标题</th>
						     <th style="width:18%;">发布时间</th>
						     <th style="width:10%;">作者</th>
						     <c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'xz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'zr')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fxz')}">
						     	<th style="width:16%;">操作</th>
						     </c:if>
						  </tr>
						  <tbody id="annunciateform">
						  <c:forEach items="${pagelist.datalist}" var="p">
							  <tr>
							     <c:if test="${p.redTitleId!=0}"><td><q></q></td></c:if>
							     <c:if test="${p.redTitleId==0}"><td></td></c:if> 
							     <td style="text-align:left;">【学校通知】<a href="${ctx}/jy/annunciate/view?id=${p.id}&&status=${p.status}&&type=0" title="${p.title}"><span><ui:sout value="${p.title}" length="50" needEllipsis="true" ></ui:sout></span></a></td>
							     <td><fmt:formatDate value="${p.crtDttm}" pattern="yyyy-MM-dd" /></td>
							     <jy:di key="${p.crtId}"
										className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
							     <td>${u.name}</td>
							     <c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'xz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'zr')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fxz')}">
								     <td> 
								     	<c:if test="${userId==u.id}"><span  title="删除" class="del deleteNotice" data-id="${p.id}" data-status="${p.status}"></span></c:if>
								     	<c:if test="${userId!=u.id}"><span title="取消删除" class="jz_del"></span></c:if>
								     	<c:if test="${p.isTop!=true}"><span class="top" title="置顶" data-id="${p.id}"></span></c:if>
								     	<c:if test="${p.isTop==true}"><span class="qx_top" title="取消置顶" data-id="${p.id}"></span></c:if>
								     </td>
							     </c:if> 
							  </tr>
						  </c:forEach></tbody>
						</table>
						<form name="pageForm" method="post">
							<ui:page url="${ctx}/jy/annunciate/punishs" data="${pagelist}" callback="addData" dataType="html"/>
							<input type="hidden" class="currentPage" name="page.currentPage">
						</form>
						<c:if test="${jaSum==0}">
							<div class="content_k"><dl><dd></dd><dt>还没有人发布通知公告哟，赶紧点击"发布"吧！</dt></dl></div>
				     	</c:if>
						<div style="height:2rem;"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>             
</body>
<script type="text/javascript">
	require(["zepto",'js'],function($){	
	}); 
</script>
</html>