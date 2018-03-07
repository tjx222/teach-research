<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="校际教研"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/check_detail.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/check/css/check_activity.css" >
	<ui:require module="activity/js"></ui:require>
	<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','activity'],function(){});
	//参与或查看
	function canyu_chakan(activityId,typeId,isOver){
		var listType = $("#listType").val();
		if (typeId == 1) {// 同备教案
				window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewTbjaSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
		} else if (typeId == 4) {
			window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZbkt?id=" + activityId + "&listType=" + listType, "_self");
		} else {// 主题研讨，视频研讨
			window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZtytSchoolActivity?id=" + activityId + "&listType=" + listType, "_self");
		}
	}
	function toCheckInfo1(obj){
		location.href = _WEB_CONTEXT_ + "/jy/managerecord/school?listType="+$("#listType").val()+"&term="+$(obj).val();
	}
</script>
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="校际教研"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="jyhdjl_xjjy"></jy:nav></h3>
	</div>
	<div class="clear"></div>
	
	<div class="box" style="display:none;"></div>
		<form id="activityForm" action="${ctx}jy/managerecord/school" method="post">
			<input type="hidden" id="listType" name="listType" value="${listType}"> 
			<input type="hidden" id="count1" name="count1" value="${count1}"> 
			<input type="hidden" id="count2" name="count2" value="${count2}"> 
			<input type="hidden" id="term" name="term" value="${term}"> 
		</form>
	<div class="teaching_research_list_cont">
		<div class="t_r_l_c">
			<div class='t_r_l_c_cont_tab'>  
				<div class="t_r_l_c_select">
					<ol id="UL">
	                	<li value="0" ${listType==0?'class="t_r_l_c_li t_r_l_c_li_act"':''}${listType==1?'class="t_r_l_c_li"':''} >
	                	发起（${count1 }）
	                	</li>
						<li value="1" ${listType==1?'class="t_r_l_c_li t_r_l_c_li_act"':''}${listType==0?'class="t_r_l_c_li"':''}>
						参与（${count2 }）
						</li>
	                </ol>
					<label style="float: right;margin-right:10px;margin-top:15px;"> 
					 	学期： 
						<input type="radio" style="width:13px;height:13px;float: none;background-image: none;vertical-align:middle;margin-top:-3px;"  <c:if test="${term==0}">checked="checked" </c:if>   name="term" value="0"  onclick="toCheckInfo1(this);"><span>上学期</span>
				<input type="radio"  style="width:13px;height:13px;float: none;background-image: none;vertical-align:middle;margin-top:-3px;" <c:if test="${term==1}">checked="checked" </c:if>   name="term" value="1"  onclick="toCheckInfo1(this);">下学期
					</label>
			</div>
			<div class='t_r_l_c_cont'> 
				<div class='t_r_l_c_cont_tab'>
					<div class='t_r_l_c_cont_table'>
						<c:if test="${listType!=1}">
							<c:if test="${not empty activityList.datalist}">
							
								<table>
								  <tr>
								  	<th style="width:310px;">活动主题</th>
								    <th style="width:135px;">教研圈</th>
								    <th style="width:80px;">学科</th>
								    <th style="width:80px;">参与年级</th>
								    <th style="width:250px;">活动时间</th>
								    <th style="width:80px;">评论数</th>
								  </tr>
								  
							  	<c:forEach items="${activityList.datalist}" var="activity">
								  	<tr>
								    <td style="text-align:left;"><strong>【${activity.typeName }】</strong><span title="${activity.activityName}"  style="color:#014efd;cursor:pointer;white-space: nowrap;" onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver })"><ui:sout value="${activity.activityName}" length="26" needEllipsis="true"></ui:sout></span></td>
								    	    <td class="hover_td" <c:if test="${activity.isTuiChu }">title="${activity.schoolTeachCircleName }"</c:if>>
										    	<label class="w115">${activity.schoolTeachCircleName }</label>
										    	<c:if test="${!activity.isTuiChu }">
											    	<div style="display: none;" class="school1">
											    		<h5 style="font-weight: bold;">教研圈名称：${activity.schoolTeachCircleName }</h5>
														<ol>
															<c:forEach items="${activity.stcoList }" var="stco">
																<li>
																	<a title="${stco.orgName }" class="w180">${stco.orgName }</a>
																	<c:choose>
																	 <c:when test="${stco.state==1}"><label class="z_zc">待接受</label></c:when>
																	 <c:when test="${stco.state==2}"><label class="z_ty">已接受</label></c:when>
																	 <c:when test="${stco.state==3}"><label class="z_jj">已拒绝</label></c:when>
																	 <c:when test="${stco.state==4}"><label class="z_tc">已退出</label></c:when>
																	 <c:when test="${stco.state==5}"><label class="z_ty">已恢复</label></c:when>
																	</c:choose>
																</li>
															</c:forEach>
														</ol>	
													</div>
												</c:if>
										    </td>
								    <td title="${activity.subjectName}"><span style="display:block;width:135px;padding-left:10px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;"><ui:sout value="${activity.subjectName}" length="18" needEllipsis="true"></ui:sout></span></td>
								    <td title="${activity.gradeName}"><span style="display:block;width:135px;padding-left:10px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;"><ui:sout value="${activity.gradeName}" length="19" needEllipsis="true"></ui:sout></span></td>
								    <td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/><c:if test="${empty activity.startTime}"> ~ </c:if>至<c:if test="${empty activity.endTime}"> ~ </c:if><fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
								    <td>${activity.commentsNum}</td>
								  </tr>
								 </c:forEach>
								</table>
								</c:if>
							</c:if>
						<c:if test="${listType==1}">
							<c:if test="${not empty activityList.datalist}">
								<table>
								  <tr>
								    <th style="width:310px;">活动主题</th>
								    <th style="width:140px;">教研圈</th>
								    <th style="width:110px;">学科</th>
								    <th style="width:110px;">参与年级</th>
								    <th style="width:90px;">发起人</th>
								    <th style="width:250px;">活动时间</th>
								    <th style="width:80px;">评论数</th>
								  </tr>
								  
							  	<c:forEach items="${activityList.datalist}" var="activity">
									<tr>
								    <td style="text-align:left;"><strong>【${activity.typeName }】</strong><span title="${activity.activityName}"  style="color:#014efd;cursor:pointer;white-space: nowrap;" onclick="canyu_chakan(${activity.id},${activity.typeId},${activity.isOver })"><ui:sout value="${activity.activityName}" length="26" needEllipsis="true"></ui:sout></span></td>
								    	    <td class="hover_td" <c:if test="${activity.isTuiChu }">title="${activity.schoolTeachCircleName }"</c:if>>
										    	<label class="w115">${activity.schoolTeachCircleName }</label>
										    	<c:if test="${!activity.isTuiChu }">
											    	<div style="display: none;" class="school1">
											    		<h5 style="font-weight: bold;">教研圈名称：${activity.schoolTeachCircleName }</h5>
														<ol>
															<c:forEach items="${activity.stcoList }" var="stco">
																<li>
																	<a title="${stco.orgName }" class="w180">${stco.orgName }</a>
																	<c:choose>
																	 <c:when test="${stco.state==1}"><label class="z_zc">待接受</label></c:when>
																	 <c:when test="${stco.state==2}"><label class="z_ty">已接受</label></c:when>
																	 <c:when test="${stco.state==3}"><label class="z_jj">已拒绝</label></c:when>
																	 <c:when test="${stco.state==4}"><label class="z_tc">已退出</label></c:when>
																	 <c:when test="${stco.state==5}"><label class="z_ty">已恢复</label></c:when>
																	</c:choose>
																</li>
															</c:forEach>
														</ol>	
													</div>
												</c:if>
										    </td>
									    <td title="${activity.subjectName}"><span ><ui:sout value="${activity.subjectName}" length="13" needEllipsis="true"></ui:sout></span></td>
									    <td title="${activity.gradeName}"><span ><ui:sout value="${activity.gradeName}" length="10" needEllipsis="true"></ui:sout></span></td>
									    <td>${activity.organizeUserName}</td>
									    <td><fmt:formatDate value="${activity.startTime}" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${activity.endTime}" pattern="MM-dd HH:mm"/></td>
									    <td>${activity.commentsNum}</td>
									  </tr>
								  </c:forEach>
								</table>
								</c:if>
							</c:if>
					</div>
				</div>
				<c:if test="${empty activityList.datalist }">
					<div class="empty_wrap">
						<div class="empty_img"></div>
						<div class="empty_info">
						<c:if test="${listType!=1}">
							您还没有发起的教研活动！
						</c:if>
						<c:if test="${listType==1}">
							您现在还没有可参与的校际教研，稍后再来吧！
						</c:if>
						</div>
					</div>
				</c:if>
			</div>
			<form  name="pageForm" method="post">
				<ui:page url="${ctx}jy/managerecord/school" data="${activityList}"  />
				<input type="hidden" class="currentPage" name="currentPage">
				<input type="hidden" id="" name="listType" value="${listType}"> 
				<input type="hidden" id="count1" name="count1" value="${count1}"> 
		        <input type="hidden" id="count2" name="count2" value="${count2}"> 
		        <input type="hidden" id="term" name="term" value="${term}"> 
			</form>
		</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>