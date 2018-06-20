<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="学科教研情况一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="学科教研情况一览"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_xkjy">
		<jy:param name="phaseId" value="${param.phaseId }"></jy:param>
		</jy:nav>
	</div>
	<div class="teachingTesearch_class_content">
		<form id="form1" action="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_s?phaseId=${param.phaseId }" method="post">
		<div class="teachingTesearch_class_top">
			<p class="teachingTesearch_class_title">学科教研情况一览</p>
			<ul class="teachingTesearch_class_semester">
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
		<input id="orderFlag" type="hidden" name="orderFlag" value="${search.orderFlag }"/>
		<input id="orderMode" type="hidden" name="orderMode" value="${search.orderMode }"/>
		</form>
		<table cellpadding="0" cellspacing="0" class="teachingTesearch_class_table">
			<tr>
				<td>
					<b>
						学科/教师
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
		<div class="teachingTesearch_class_con">
			<table cellpadding="0" cellspacing="0" class="teachingTesearch_class_table2">
				<c:forEach var="data" items="${dataList }">
					<tr class="change_bg">
						<td><a href="${data['url'] }&phaseId=${param.phaseId }">${data['subjectName'] } / ${data['teacherCount'] }</a></td>
						<td>${data['jiaoanWrite']}</td>
						<td>${data['kejianWrite']}</td>
						<td>${data['activityJoin']}</td>
						<td>${data['share']}</td>
						<td class="no_border">${data['teacherRecordRes']}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p class="teachingTesearch_class_tip">注：表格为各项资源数，点击各项名称即可按该项达标率进行排序显示。</p>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','teacherList'],function(){});
</script>
</html>
