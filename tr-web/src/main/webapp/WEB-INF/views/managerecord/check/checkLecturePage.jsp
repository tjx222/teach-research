<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<c:if test="${empty listType || listType==0 }"><ui:htmlHeader title="查阅活动 已查阅"></ui:htmlHeader></c:if>
	<c:if test="${listType==1 }"><ui:htmlHeader title="查阅活动 查阅意见"></ui:htmlHeader></c:if>
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/check_detail.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/check/css/check_activity.css" >
	<script type="text/javascript">
		//切换选项
		function changeListType(listType){
			$("#hid_listType").val(listType);
			$("#hiddenForm").submit();
		}
		//切换学期
		function changTerm(obj){
			$("#hid_term").val($(obj).val());
			$("#hiddenForm").submit();
		}
		//查阅活动
		function chayueLecture(lectureRecordId){
 			window.open(_WEB_CONTEXT_+"/jy/managerecord/check/6/checkLectureRecordInfo?id="+lectureRecordId,"_blank");
		}
	</script>
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="查阅听课记录"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="cyjl_tkjl"></jy:nav></h3>
	</div>
	<div class="clear"></div>
	
	<div class="box" style="display:none;"></div>
	<form id="hiddenForm" action="${ctx }jy/managerecord/check/6" method="post">
		<input id="hid_term" type="hidden" name="term" value="${term }">
		<input id="hid_listType" type="hidden" name="listType" value="${listType }">
	</form>
	
	<div class="teaching_research_list_cont">
		<div class="t_r_l_c">
			<div class='t_r_l_c_cont_tab'>
				<div class="t_r_l_c_select">
					<ol id="UL">
						<li ${listType==0?'class="t_r_l_c_li t_r_l_c_li_act"':''}${listType==1?'class="t_r_l_c_li"':''} ${listType==1&&checkCount>0?'onclick="changeListType(0)"':''} >
						已查阅（${checkCount }）
						</li>
						<li ${listType==1?'class="t_r_l_c_li t_r_l_c_li_act"':''}${listType==0?'class="t_r_l_c_li"':''} ${listType==0&&yijianCount>0?'onclick="changeListType(1)"':''} >
						查阅意见（${yijianCount }）
						</li>
					</ol>
					<label style="float: right;margin-right:10px;margin-top:15px;"> 
					 	学期： 
						<input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="0"  <c:if test="${term==0}">checked="checked" </c:if> onclick="changTerm(this);"  >上学期
					    <input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="1"  <c:if test="${term==1}">checked="checked" </c:if> onclick="changTerm(this);"  >下学期
					</label>
				</div>
			<div class="clear"></div>
			<div class='t_r_l_c_cont'>
				<div class='t_r_l_c_cont_tab'>
					<div class='t_r_l_c_cont_table'>
						<c:if test="${listType==0 }">
							<table>
							  <tr>
							    <th style="width:280px;">课题</th>
							    <th style="width:140px;">年级学科</th>
							    <th style="width:120px;">授课人</th>
							    <th style="width:80px;">听课节数</th>
							    <th style="width:200px;">听课时间</th>
							    <th style="width:60px;">分享状态</th>
							  </tr>
						  	<c:forEach items="${lectureList.datalist}" var="lecture">
								  <tr>
								    <td title="${lecture.topic}" style="text-align:left;"><strong>【${lecture.type==0?"校内":"校外"}】</strong><span style="cursor: pointer;width:225px;color:#5378F8;" onclick="chayueLecture('${lecture.id}')"><ui:sout value="${lecture.topic}" length="36" needEllipsis="true" ></ui:sout></span></td>
								    <td title="${lecture.grade}${lecture.subject}"><label style="display:block;width:82px;padding-left:5px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${lecture.grade}${lecture.subject}</label></td>
								    <td>${lecture.teachingPeople}</td>
								    <td>${lecture.numberLectures}</td>
								    <td><fmt:formatDate value="${lecture.lectureTime}" pattern="yyyy-MM-dd"/></td>
								    <td>	
								    		<c:if test="${lecture.isShare==1 }">已分享</c:if>
								    		<c:if test="${lecture.isShare!=1 }">未分享</c:if>
								    </td>
								  </tr>
							  </c:forEach>
							  
							</table>
							<div class="clear"></div>
							<form name="pageForm" method="post" style="display: block;">
								<ui:page url="${ctx}jy/managerecord/check/6" data="${lectureList}"  />
								<input type="hidden" class="currentPage" name="currentPage">
								<input type="hidden" name="term" value="${term }">
								<input type="hidden" name="listType" value="${listType }">
								<input type="hidden" name="resType" value="${resType }">
							</form>
						</c:if>
					</div>
				</div>
				<div class='t_r_l_c_cont_tab'>
					<div class='t_r_l_c_cont_table'>
						<c:if test="${listType==1 }">
							<div class="record_reco_cont">
							<c:forEach items="${checkMapList }" var="checkMap">
								<div class="record_reco_cont_1">
									<div class="check-bottom_1">
										<h5>活动名称：<span onclick="chayueLecture('${checkMap.checkInfo.resId }')" style="cursor: pointer;">${checkMap.checkInfo.title }</span></h5>
										<c:forEach items="${checkMap.optionMapList }" var="optionMap">
											<div class="check-bottom_1_right">
												<div class="check-bottom_1_right_top_1">
													<div class="check-bottom_1_right_top">
														${optionMap.parent.content }
													</div>
													<div class="check-bottom_1_right_botm">
														<span><fmt:formatDate value="${optionMap.parent.crtTime  }" pattern="yyyy-MM-dd"/></span>
													</div>
												</div>
												<div class="clear"></div>
												<div style="border-bottom:1px #bdbdbd dashed;;width:1100px;margin:5px auto;"></div>
												<c:forEach items="${optionMap.childList }" var="child">
													<div class="check-bottom_2">
														<jy:di key="${child.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
														<div class="check-bottom_2_left">
															<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
														</div>
														<div class="check-bottom_2_right">
															<div class="check-bottom_2_right_top1">
																${u.name }说：${child.content }
																
															</div>
															<div class="check-bottom_2_right_botm">
																<span><fmt:formatDate value="${child.crtTime   }" pattern="yyyy-MM-dd"/></span>
															</div>
														</div>
													</div>
													<div class="clear"></div>
												</c:forEach>
											</div>
										</c:forEach>
									</div>
									<div class="clear"></div>
								</div>
						
								</c:forEach>
							</div>
								<div class="clear"></div>
							<form name="pageForm" method="post">
								<ui:page url="${ctx}jy/managerecord/check/6" data="${checkInfoList}"  />
								<input type="hidden" class="currentPage" name="currentPage">
								<input type="hidden" name="term" value="${term }">
								<input type="hidden" name="listType" value="${listType }">
							</form>
					</c:if>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>