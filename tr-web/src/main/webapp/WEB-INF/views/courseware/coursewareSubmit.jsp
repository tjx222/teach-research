<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="课件提交"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/courseware/css/dlog_submit.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/courseware/css/courseware.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen"> 
</head>
<body style="background:#fff;">
<div class="clear"></div>
<div class="upload-bottom_submit_big">
		<div class="upload-bottom_submit_big_tab">
		   <div style="overflow:auto;height:500px;width: 800px;">
		<div class="upload-bottom_submit">
			<p>
				<input id="quanxuan" type="checkbox" onclick="selectAll(this)"> 
				<b>全选</b>
			</p>
			<c:if test="${isSubmit==0 }">
				<input type="button" class="submit2" onclick="submitThis(0)" value="提交">
			</c:if>
			<c:if test="${isSubmit==1 }">
				<input type="button" class="submit1" onclick="submitThis(1)" value="取消提交">
				<span>注意：禁选的课件表示上级领导已查阅，不允许取消提交！</span>
			</c:if>
		</div>
		<ul class="expmenu">
			
			<!-- 动态数据的遍历展示 -->
			<c:choose>
				<c:when test="${not empty dataMap || not empty dataMap2 }">
					<c:if test="${not empty dataMap }">
					<li class="top_li">${bookName }</li>
					<c:forEach var="level1" items="${treeList }" varStatus="statu">
						<c:if test="${not empty dataMap[level1.lessonId]}">
							<li id="li_level1_${statu.index }" levelname="level1">
							<c:if test="${!dataMap[level1.lessonId]['isLeaf'] }">
							<%--处理父级节点  --%>
								<p>
									<input type="checkbox" name="check_${level1.lessonId }" level="parent" child="check_">
								</p>
								<a class="header">
									<span class="label">${level1.lessonName}</span>
									<span class="arrow up"></span>
								</a>
								<ui:submitLesson dataMap="${dataMap }" data="${level1 }" level="${statu.index }" kejian="true"></ui:submitLesson>
							</c:if>
							<c:if test="${dataMap[level1.lessonId]['isLeaf'] }">
							<%--处理叶节点  --%>
								<p>
									<input type="checkbox" name="check_${level1.lessonId }" child="check_" level="leaf">
								</p>
								<a class="header">
									<span class="label">${level1.lessonName}</span>
									<strong>
										<span id="span_level1_${level1.lessonId }_kejian" style="display: none;"><input type="checkbox" child="check_kejianAll" onclick="checkOrNot(this,'check_kejian');">课件：<span id="span_level1_${level1.lessonId}_kejian_num"></span></span>
									</strong>
									<span class="arrow up"></span>
								</a>
								<ul class="menu" style="display:none;">
									<li>
										<ol class="menu1" id="level1_${level1.lessonId }">
							               	<c:forEach var="lessonPlan" items="${dataMap[level1.lessonId].kejianList }" varStatus="statu1">
							                <li typename="kejian">
							               		<ui:icon ext="${lessonPlan.planType==1?'ppt':'doc' }" title="${lessonPlan.planName }"></ui:icon>
							               		<span title="${lessonPlan.planName }">课件${statu1.index+1 }</span>
						               			<input value="${lessonPlan.planId }" type="checkbox" class="li_box" child="check_kejian" name="check_${level1.lessonId }_kejian" <c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
							               	</li>
							               	</c:forEach>
							  			</ol>
									</li>
								</ul>
							</c:if>
							</li>
						</c:if>
						<div class="clear"></div>
						</c:forEach>
					</c:if>
					<c:if test="${not empty dataMap2 }">
					<li class="top_li">${bookName2 }</li>
					<c:forEach var="level1" items="${treeList2 }" varStatus="statu">
						<c:if test="${not empty dataMap2[level1.lessonId]}">
							<li id="li_level1_${statu.index }" levelname="level1">
							<c:if test="${!dataMap2[level1.lessonId]['isLeaf'] }">
							<%--处理父级节点  --%>
								<p>
									<input type="checkbox" name="check_${level1.lessonId }" level="parent" child="check_">
								</p>
								<a class="header">
									<span class="label">${level1.lessonName}</span>
									<span class="arrow up"></span>
								</a>
								<ui:submitLesson dataMap="${dataMap2 }" data="${level1 }" level="${statu.index }" kejian="true"></ui:submitLesson>
							</c:if>
							<c:if test="${dataMap2[level1.lessonId]['isLeaf'] }">
							<%--处理叶节点  --%>
								<p>
									<input type="checkbox" name="check_${level1.lessonId }" child="check_" level="leaf">
								</p>
								<a class="header">
									<span class="label">${level1.lessonName}</span>
									<strong>
										<span id="span_level1_${level1.lessonId }_kejian" style="display: none;"><input type="checkbox" child="check_kejianAll" onclick="checkOrNot(this,'check_kejian');">课件：<span id="span_level1_${level1.lessonId}_kejian_num"></span></span>
									</strong>
									<span class="arrow up"></span>
								</a>
								<ul class="menu" style="display:none;">
									<li>
										<ol class="menu1" id="level1_${level1.lessonId }">
							               	<c:forEach var="lessonPlan" items="${dataMap2[level1.lessonId].kejianList }" varStatus="statu1">
							                <li typename="kejian">
							               		<ui:icon ext="${lessonPlan.planType==1?'ppt':'doc' }" title="${lessonPlan.planName }"></ui:icon>
							               		<span title="${lessonPlan.planName }">课件${statu1.index+1 }</span>
						               			<input value="${lessonPlan.planId }" type="checkbox" class="li_box" child="check_kejian" name="check_${level1.lessonId }_kejian" <c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
							               	</li>
							               	</c:forEach>
							  			</ol>
									</li>
								</ul>
							</c:if>
							</li>
						</c:if>
						<div class="clear"></div>
						</c:forEach>
					</c:if>
				</c:when>
				<c:otherwise>
					<!-- 无文件 -->
					<div class="empty_wrap"> 
					    <div class="empty_info">
					    	<c:if test="${isSubmit==0 }">您没有可提交的课件!</c:if>
							<c:if test="${isSubmit==1 }">您没有可取消提交的课件!</c:if>
						</div> 
					</div>
				</c:otherwise>
			</c:choose>
					
		</ul>
	</div>
</div>
</div>
</body>
	<script src="${ctxStatic }/modules/courseware/js/courseware_submit.js"></script>
</html>
