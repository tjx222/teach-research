<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="教师教研情况一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
	<style>
		.teachingTesearch_managers_b_l li{
		border-right:1px solid #D5D5D5;
		}
	</style>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教师教研情况一览"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<c:if test="${empty search.flagz }">
		<jy:nav id="jyyl_jsjy"></jy:nav>
		</c:if>
		<c:if test="${search.flagz=='grade' }">
		<jy:nav id="jyyl_jsjy_grade"><jy:param name="gradeName" value="${gradeName }"></jy:param></jy:nav>
		</c:if>
		<c:if test="${search.flagz=='subject' }">
		<jy:nav id="jyyl_jsjy_subject"><jy:param name="subjectName" value="${subjectName }"></jy:param></jy:nav>
		</c:if>
	</div>
	<div class="teachingTesearch_managers_content">
		<form id="form1" action="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_t?flagz=${search.flagz}" method="post">
			<div class="teachingTesearch_managers_top">
				<p class="teachingTesearch_managers_title">教师教研情况一览</p>
				<ul class="teachingTesearch_managers_semester">
					<li>
						<input type="radio" name="termId" id="radio_shang" value="0" <c:if test="${search.termId==0 }">checked="checked"</c:if>/>
						<label for="radio_shang">上学期</label>
					</li>
					<li>
						<input type="radio" name="termId" id="radio_xia" value="1" <c:if test="${search.termId==1 }">checked="checked"</c:if>/>
						<label for="radio_xia">下学期</label>
					</li>
				</ul>
			</div>
			<input id="gradeId" type="hidden" name="gradeId" value="${search.gradeId }"/>
			<input id="subjectId" type="hidden" name="subjectId" value="${search.subjectId }"/>
			<input id="orderFlag" type="hidden" name="orderFlag" value="${search.orderFlag }"/>
			<input id="orderMode" type="hidden" name="orderMode" value="${search.orderMode }"/>
		</form>
		
		<div class="out_reconsideration_see_title_box">
	        <span class="scroll_leftBtn"></span>
	        <div class="in_reconsideration_see_title_box">
				<ul class="teachingTesearch_managers_middle_nav reconsideration_see_title" >
					<c:forEach var="grade" items="${gradeList }">
						<li id="${grade['id'] }" <c:if test="${grade['id'] == search.gradeId }">class="li_active2"</c:if> >${grade['name'] }</li>
					</c:forEach>
				</ul>
	        </div>
			<span class="scroll_rightBtn"></span>
	    </div> 
		<div class="teachingTesearch_managers_bottom">
			<ul class="teachingTesearch_managers_b_l">
				<c:forEach var="subject" items="${subjectList }">
					<li id="${subject['id'] }" <c:if test="${subject['id'] == search.subjectId }">class="li_active1"</c:if> >${subject['name'] }</li>
				</c:forEach>
				<!-- <li class="li_active1">语文</li>
				<li>数学</li>
				<li>英语</li> -->
			</ul>
			<div class="teachingTesearch_managers_b_r">
					<table cellpadding="0" cellspacing="0" class="teachingTesearch_managers_table">
						<tr>
						    <td>
								<b>
									姓名
								</b>
							</td>
							<td orderFlag="jiaoanWrite">
								<b>
									教案
									<c:if test="${search.orderFlag=='jiaoanWrite' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='jiaoanWrite' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（撰写数）</span>
							</td>
							<td orderFlag="kejianWrite">
								<b>
									课件
									<c:if test="${search.orderFlag=='kejianWrite' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='kejianWrite' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（撰写数）</span>
							</td>
							<td orderFlag="listen">
								<b>
									听课记录
									<c:if test="${search.orderFlag=='listen' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='listen' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（节数）</span>
							</td>
							<td orderFlag="activityJoin">
							    <b>
									集体备课
									<c:if test="${search.orderFlag=='activityJoin' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='activityJoin' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（参与数）</span>
							</td>
							<td orderFlag="summaryWrite">
								<b>
									计划总结
									<c:if test="${search.orderFlag=='summaryWrite' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='summaryWrite' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（撰写数）</span>
							</td>
							<td orderFlag="share">
								<b>
									分享发表
									<c:if test="${search.orderFlag=='share' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='share' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（总数）</span>
							</td>
							<td orderFlag="teacherRecordRes">
								<b>
									成长档案
									<c:if test="${search.orderFlag=='teacherRecordRes' }">
						    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
										<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
									</c:if>
									<c:if test="${search.orderFlag!='teacherRecordRes' }">
										<span class="up"></span>
										<span class="down"></span>
									</c:if>
								</b>
								<span class="num_tip">（精选数）</span>
							</td>
						</tr>
					</table>
					<div class="teachingTesearch_managers_b_r_con" style="border-bottom: none;">
						<table cellpadding="0" cellspacing="0" class="teachingTesearch_managers_table2" style="border-bottom: 1px solid #d5d5d5;">
							<c:forEach var="data" items="${dataList }">
								<tr class="change_bg">
									<td><a href="${data['url']}&flagz=${search.flagz}">${data['userName']}</a></td>
									<td>${data['jiaoanWrite']}</td>
									<td>${data['kejianWrite']}</td>
									<td>${data['listen']}</td>
									<td>${data['activityJoin']}</td>
									<td>${data['summaryWrite']}</td>
									<td>${data['share']}</td>
									<td class="no_border">${data['teacherRecordRes']}</td>
								</tr>
							</c:forEach>
						</table>
						
				    </div> 
				    <p>注：表格为各项资源数，点击各项名称即可按该项达标率进行排序显示。</p>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','teacherList'],function(){});
</script>
</html>