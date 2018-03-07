<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="计划总结"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/annunciate/css/notice.css" media="screen">
<style type="text/css">
	.title a{
		color: #014efd;
	}
</style>

</head>
<body>
	<div class="wrapper">
		<div class="jyyl_top"><ui:tchTop  style="1" modelName="计划总结"></ui:tchTop></div>
		<div class="jyyl_nav">
			当前位置：
			<jy:nav id="punishPlanSummary"></jy:nav>
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
								<c:if test="${data.totalCount>0  }">
									<table> 
										<tr>
											<th style="width: 250px;">标题</th>
											<th style="width: 100px;">发布时间</th>
											<th style="width: 50px;">作者</th>
										</tr>
										<c:forEach items="${data.datalist}" var="item">
											<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ')}">
												<c:set var="roleName" value="备课"/>
											</c:if>
											<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ') }">
												<c:set var="roleName" value="年级"/>
											</c:if>
											<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')}">
												<c:set var="roleName" value="学科"/>
											</c:if>
											<c:if test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')
											||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'FXZ')
											||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
												<c:set var="roleName" value="学校"/>
											</c:if>
											<tr>
												<td style="text-align: left;">
													<span>【${roleName}${item.category==3?"计划":"总结" }】</span>
														<strong class="title"> 
															<a href="./jy/scanResFile?resId=${item.contentFileKey}" target="_blank" class="title viewPunish" data-id="${item.id }">${item.title }</a>
														</strong>
													<c:if test="${item.isViewed!=1 }"><b id="unviewNoitce_${item.id }"></b></c:if>
												</td>
												<td><fmt:formatDate value="${item.punishTime }" pattern="yyyy-MM-dd" /></td>
												<td>
													<div data-id="${item.id }"
														data-title="${item.title }">${item.userName }</div>
												</td>
											</tr>
										</c:forEach>
									</table>
								</c:if>
								<c:if test="${data.totalCount==0}">
									<div class="empty_wrap">
										<div class="empty_img"></div>
										<div class="empty_info">还没有计划总结哟，稍后再来查看吧！</div>
									</div>
						     	</c:if>
							</div>
							<div class="pages" style="margin-top:70px;">
								<form id="pageForm" name="pageForm" method="get">
									<ui:page url="./jy/planSummary/punishs" data="${data}" />
									<input type="hidden" name="page.currentPage" class="currentPage" value="${data.currentPage }">
								</form>
							</div>
						</div>
					</div> 
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
	<ui:require module=""></ui:require>
	<script type="text/javascript">
		$('.viewPunish').click(function(){
			$('#unviewNoitce_'+$(this).attr('data-id')).hide();
		});
	</script>
</body>
</html>
