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
			<ui:tchTop  style="1" modelName="未转发通知公告"></ui:tchTop></div>
		<div class="jyyl_nav">
				当前位置：<jy:nav id="wzftzgg"></jy:nav>
		</div>
		<div class="clear"></div>
		<div class='teaching_research_list_cont'>
			<div class='t_r_l_c'>
				<div class='t_r_l_c_cont_tab'>  
					<div class="t_r_l_c_select"> 
		                <div class="t_r_l_c_right">
						</div>
	                </div> 
					<div class='t_r_l_c_cont'> 
						<div class='t_r_l_c_cont_tab'>
							<div class='t_r_l_c_cont_table' >
								<table> 
									<tr>
										<th style="width:400px;">标题</th>
										<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
											<th style="width:70px;">发布范围</th>
										</c:if>
										<th style="width:200px;">发布时间</th>
										<th style="width:90px;">作者</th>
										<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'xz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'xkzz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'fxz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
											<th style="width:140px;">操作</th> 
										</c:if>
									</tr>
									<c:forEach items="${pagelist.datalist}" var="p">
										<tr>
											<td style="text-align:left;"> <span class='red_head'><c:if test="${p.redTitleId!=0}"><b></b></c:if></span><c:if test="${p.annunciateType==0}">【学校通知】</c:if><c:if test="${p.annunciateType==1}">【区域通知】</c:if><a class="td_name" href="${ctx}/jy/annunciate/view?id=${p.id}&&status=${p.status}&&type=0" title="${p.title}"><ui:sout value="${p.title}" length="35" needEllipsis="true" ></ui:sout></a><!-- <span class='spot'></span> --></td>
											<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
												<td>
													<span class='school_num' data-id="${p.id}">
														<b>${p.orgsJoinCount }</b>
														<div class='school_name'>
															<div class="hover_sj"></div>
															<ul id="orgids_${p.id}" >
															</ul>
														</div>
													</span> 
												</td>
											</c:if> 
											<td><fmt:formatDate value="${p.crtDttm}" pattern="yyyy-MM-dd" /></td>
											<jy:di key="${p.crtId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
											<td>${u.name }</td>
											<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'xz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'xkzz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'fxz')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyy')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId, 'jyzr')}">
												<td>	
													<span title="转发" class="forward_bt" data-id="${p.id}" data-isTop="${p.isTop}" onclick="forwardAnnunciate('${p.id}')" ></span>
												</td> 
											</c:if>
										</tr>
									</c:forEach>
								</table>
								<c:if test="${empty pagelist.datalist}">
									<div class="empty_wrap">
										<div class="empty_img"></div>
										<div class="empty_info">还没有未转发的区域通知公告哟！</div>
									</div>
						     	</c:if>
							</div>
							<form name="pageForm" method="post">
								<ui:page url="${ctx}/jy/annunciate/notForwardIndex" data="${pagelist}"/>
								<input type="hidden" class="currentPage" name="currentPage">
							</form>
						</div>
					</div> 
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','index'],function(){
		
	});
</script>
</html>
