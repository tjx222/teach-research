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
	<div id="draft_box" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">草稿箱</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<iframe id="activity_iframe" name="activity_content" width="562"
					height="390" frameborder="0" scrolling="no"
					style="border: none; overflow: hidden;"></iframe>
			</div>
		</div>
	</div>
	<div id="not_started_dialog" class="dialog">
		<div class="dialog_bg_wrap">
			<div class="dialog_bg_head">
				<span class="dialog_bg_close dialog_close"></span>
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
				<c:if test="${listType < 1 }">
					<jy:nav id="xjjy"></jy:nav>
				</c:if>
				<c:if test="${listType == 1 }">
					<jy:nav id="xjjy2"></jy:nav>
				</c:if>
			</h3>
		</div>
		<input type="hidden" id="listType" value="${listType == -1 ? 0:listType}">
		<div class='teaching_research_list_cont' style="padding-bottom: 8px;">
			<div class='t_r_l_c'>
				<div class='t_r_l_c_cont_tab'>
					<div class="t_r_l_c_select">
						<div class="t_r_l_c_right">
							<c:if test="${listType < 1 }">
								<div class='fq_teaching_btn'></div>
								<c:if test="${role != 'jy' && listType == 0}">
									<div class='schedule_table_btn' data-key="create">上传教研进度表</div>
								</c:if>
								<input type="hidden" value="${activityDraftNum}" id="drafnum" />
								<div class='drafts'>
									<span class='drafts_img'></span> <a href='javascript:void(0);'>草稿箱
										<c:if test="${activityDraftNum > 0}">
											<span class="xiaohongdian"></span>
										</c:if>
									</a>
								</div>
							</c:if>
							<c:if test="${listType == 1 }">
								<div class='schedule_table_btn' data-key="look">教研进度表</div>
								<div class="circle_btn">校际教研圈</div>
							</c:if>
						</div>
						<c:if test="${ listType < 0}">
							<div class="circle_btn" style="width:120px">创建校际教研圈</div>
						</c:if>
					</div>
					<div class='t_r_l_c_cont'>
						<c:if test="${!empty listPage.datalist }">
							<div class='t_r_l_c_cont_tab'>
								<div class='t_r_l_c_cont_table'>
									<c:if test="${listType < 1 }">
										<table>
											<tr>
												<th style="width: 230px;">活动主题</th>
												<th style="width: 70px;">教研圈</th>
												<th style="width: 120px;">参与学科</th>
												<th style="width: 100px;">参与年级</th>
												<th style="width: 180px;">活动时限</th>
												<th style="width: 50px;">讨论数</th>
												<th style="width: 50px;">修改</th>
												<th style="width: 50px;">删除</th>
												<th style="width: 50px;">分享</th>
											</tr>
											<c:forEach items="${listPage.datalist }" var="activity">
												<tr id="tr_${activity.id}">
													<td style="text-align: left;">&nbsp;&nbsp;【${activity.typeName}】<a
														href="javascript:void(0);" class='td_name'
														title="${activity.activityName}" data-type="${listType == -1 ? 0:listType}"
														data-id="${activity.id}" data-typeId="${activity.typeId}"
														data-isTuiChu="${activity.isTuiChu }"
														data-isOver="${activity.isOver }"
														data-startTime='<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><ui:sout
																value="${activity.activityName}" length="18"
																needEllipsis="true"></ui:sout></a><span class='spot'></span></td>
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
															value="${activity.gradeName }" length="16"
															needEllipsis="true"></ui:sout></td>
													<td><fmt:formatDate value="${activity.startTime}"
															pattern="MM-dd HH:mm" />至<fmt:formatDate
															value="${activity.endTime}" pattern="MM-dd HH:mm" /></td>
													<td>${activity.commentsNum}</td>
													<td><c:if
															test="${activity.isSubmit || activity.isOver}">
															<span title='禁止编辑' class='jz_edit_btn'></span>
														</c:if> <c:if test="${!activity.isSubmit && !activity.isOver}">
															<span title='编辑' class='edit_btn'
																data-id="${activity.id}"></span>
														</c:if></td>
													<td><c:if
															test="${activity.isSubmit || activity.isDiscuss}">
															<span title='禁止删除' class='jz_del_btn'></span>
														</c:if> <c:if test="${!activity.isSubmit && !activity.isDiscuss}">
															<span title='删除' class='del_btn' data-id="${activity.id}"></span>
														</c:if></td>
													<td id="td_${activity.id}"><c:if
															test="${activity.isShare }">
															<c:if test="${activity.isComment }">
																<span title='禁止取消分享' class='jz_share_btn'></span>
															</c:if>
															<c:if test="${!activity.isComment }">
																<span title='取消分享' class='qx_share_btn'
																	data-id="${activity.id}" data-isShare="false"></span>
															</c:if>
														</c:if> <c:if test="${!activity.isShare }">
															<span title='分享' class='share_btn'
																data-id="${activity.id}" data-isShare="true"></span>
														</c:if></td>
												</tr>
											</c:forEach>
										</table>

									</c:if>
									<c:if test="${listType > 0 }">
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
														title="${activity.activityName}" data-type="${listType == -1 ? 0:listType}"
														data-id="${activity.id}" data-typeId="${activity.typeId}"
														data-isTuiChu="${activity.isTuiChu }"
														data-isOver="${activity.isOver }"
														data-startTime='<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
															<ui:sout value="${activity.activityName}" length="20"
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
									</c:if>
								</div>
								<div class="page">
									<form name="pageForm" method="post">
										<ui:page url="${ctx}jy/schoolactivity/index"
											data="${listPage}" />
										<input type="hidden" class="currentPage" name="currentPage">
										<input type="hidden" name="listType" value="${listType == -1 ? 0:listType}">
									</form>
								</div>
							</div>
						</c:if>
						<c:if test="${empty listPage.datalist }">
							<div class="empty_wrap">
								<div class="empty_info" style="text-align: center;">您还没有发起过校际教研活动，赶紧去“发起教研活动”吧！</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript"
	src="${ctxStatic }/common/js/placeholder.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
<ui:require module="schoolactivity/js"></ui:require>
<script type="text/javascript">
	require([ 'activity_index' ]);
</script>
</html>