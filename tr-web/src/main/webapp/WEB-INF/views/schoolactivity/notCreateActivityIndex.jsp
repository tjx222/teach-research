<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="校际教研活动首页"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css"
	media="screen" />
</head>
<body>
	<div id="not_started_dialog" class="dialog">
		<div class="dialog_bg_wrap">
			<div class="dialog_bg_head">
				<span class="dialog_bg_close"></span>
			</div>
			<div class="dialog_content">
				<div class="not_started_info">
					<h3></h3>
					<p>活动时限</p>
					<p></p>
					<p>参与学校:</p>
					<p></p>
					<p>参与范围:</p>
					<p></p>
				</div>
				<div class="prompt_info">活动还没有开始哟！</div>
				<div class="prompt_info_btn" data-id="">
					<input type="button" class="enter"> <input type="button"
						class="not_enter"> <input type="button"
						class="access_view"> <input type="button"
						class="access_class"> <input type="button"
						class="end_class">
				</div>
			</div>
		</div>
	</div>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="校际教研"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="xjjy"></jy:nav>
			</h3>
		</div>
		<div class='teaching_research_list_cont' style="padding-bottom: 8px;">
			<div class='t_r_l_c'>
				<div class='t_r_l_c_cont_tab'>
					<div class="t_r_l_c_select">
						<div class="t_r_l_c_right">
							<div class='schedule_table_btn' data-key="look">教研进度表</div>
						</div>
					</div>
					<div class='t_r_l_c_cont'>
						<c:if test="${!empty listPage.datalist }">
							<div class='t_r_l_c_cont_tab'>
								<div class='t_r_l_c_cont_table'>
									<table>
										<tr>
											<th style="width: 230px;">活动主题</th>
											<th style="width: 70px;">教研圈</th>
											<th style="width: 120px;">参与学科</th>
											<th style="width: 90px;">参与年级</th>
											<th style="width: 90px;">发起人</th>
											<th style="width: 180px;">活动时限</th>
											<th style="width: 60px;">讨论数</th>
											<th style="width: 60px;">操作</th>
										</tr>
										<c:forEach items="${listPage.datalist }" var="activity">
											<tr id="tr_${activity.id}">
												<td style="text-align: left;">【${activity.typeName}】
													<a href="javascript:void(0);" class='td_name'
													title="${activity.activityName}" data-type="${listType}"
													data-id="${activity.id}" data-typeId="${activity.typeId}"
													data-isTuiChu="${activity.isTuiChu }"
													data-isOver="${activity.isOver }"
													data-startTime='<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
														<ui:sout value="${activity.activityName}" length="18"
															needEllipsis="true"></ui:sout>
												</a> <span class='spot'></span>
												</td>
												<td
													<c:if test="${activity.isTuiChu }">title="${activity.schoolTeachCircleName }"</c:if>><span
													class='school_num'> <b><ui:sout
																value="${activity.schoolTeachCircleName }" length="12"
																needEllipsis="true"></ui:sout></b> <c:if
															test="${!activity.isTuiChu }">
															<div class="school_name">
																<div class="hover_sj"></div>
																<ul>
																	<li class="schoolName">教研圈名称：${activity.schoolTeachCircleName }</li>
																	<c:forEach items="${activity.stcoList }" var="stco">
																		<li title="${stco.orgName }">${stco.orgName }<c:choose>
																				<c:when test="${stco.state==1}">
																					<label class="z_zc">待接受</label>
																				</c:when>
																				<c:when test="${stco.state==2}">
																					<label class="z_ty">已接受</label>
																				</c:when>
																				<c:when test="${stco.state==3}">
																					<label class="z_jj">已拒绝</label>
																				</c:when>
																				<c:when test="${stco.state==4}">
																					<label class="z_tc">已退出</label>
																				</c:when>
																				<c:when test="${stco.state==5}">
																					<label class="z_ty">已恢复</label>
																				</c:when>
																			</c:choose>
																		</li>
																	</c:forEach>
																</ul>
															</div>
														</c:if>
												</span></td>
												<td title="${activity.subjectName }"><ui:sout
														value="${activity.subjectName }" length="18"
														needEllipsis="true"></ui:sout></td>
												<td title="${activity.gradeName }"><ui:sout
														value="${activity.gradeName }" length="12"
														needEllipsis="true"></ui:sout></td>
												<td title="${activity.organizeUserName }"><ui:sout
														value="${activity.organizeUserName }" length="10"
														needEllipsis="true"></ui:sout></td>
												<td><fmt:formatDate value="${activity.startTime}"
														pattern="MM-dd HH:mm" />至<fmt:formatDate
														value="${activity.endTime}" pattern="MM-dd HH:mm" /></td>
												<td>${activity.commentsNum}</td>
												<td><c:if test="${activity.isTuiChu }">
														<span title="查看" class="see_btn" data-isOver="true"></span>
													</c:if> <c:if test="${!activity.isTuiChu }">
														<c:if test="${activity.isOver }">
															<span title="查看" class="see_btn"
																data-isOver="${activity.isOver }"></span>
														</c:if>
														<c:if test="${!activity.isOver }">
															<span title='参与' class='partake_btn'
																data-isOver="${activity.isOver }"></span>
														</c:if>
													</c:if></td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<div class="page">
									<form name="pageForm" method="post">
										<ui:page url="${ctx}jy/schoolactivity/index"
											data="${listPage}" />
										<input type="hidden" class="currentPage" name="currentPage">
										<input type="hidden" id="listType" name="listType"
											value="${listType }">
									</form>
								</div>
							</div>
						</c:if>
						<c:if test="${empty listPage.datalist }">
							<div class="empty_wrap">
								<div class="empty_info" style="text-align: center;">别人还没有发起校际教研活动，请稍后再来吧！</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<ui:require module="schoolactivity/js"></ui:require>
<script type="text/javascript">
	require([ 'activity_index' ]);
</script>
</html>