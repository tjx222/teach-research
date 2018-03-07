<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="data"
	type="com.tmser.tr.manage.meta.vo.BookLessonVo" required="true"
	description="章节"%>
<%@ attribute name="dataMap" type="java.util.Map" required="true" description="提交信息"%>
<%@ attribute name="selectedid" type="java.lang.String" required="false"
	description="选中的id"%>
<%@ attribute name="jiaoan" type="java.lang.Boolean" required="false"
	description="展示教案"%>
<%@ attribute name="kejian" type="java.lang.Boolean" required="false"
	description="展示课件"%>
<%@ attribute name="fansi" type="java.lang.Boolean" required="false"
	description="展示反思"%>
<%@ attribute name="level" type="java.lang.Integer" required="false"
	description="当前层级"%>
<c:if test="${empty level}">
	<c:set var="level" value="0" />
</c:if>
<ul class="menu" style="display: none;">
	<c:forEach var="level1" items="${data.bookLessons }" varStatus="statu">
		<c:if test="${not empty dataMap[level1.lessonId]}">
			<li id="li_level1_${statu.index }" levelname="level1">
			<c:if test="${!dataMap[level1.lessonId]['isLeaf'] }">
					<%--处理父级节点  --%>
					<p>
						<input type="checkbox" name="check_${level1.lessonId }" level="parent"
							child="check_">
					</p>
					<a class="headertag"> <span class="label">${level1.lessonName}</span>
						<span class="arrow up"></span>
					</a>
					<ui:submitLesson dataMap="${dataMap}" data="${level1 }" level="${statu.index+1 }" jiaoan="${jiaoan }" fansi="${fansi }" kejian="${kejian }"></ui:submitLesson>
				</c:if>
				<c:if test="${dataMap[level1.lessonId]['isLeaf'] }">
					<%--处理叶节点  --%>
					<p>
						<input type="checkbox" name="check_${level1.lessonId}"
							child="check_" level="leaf">
					</p>
					<a class="headertag"> <span class="label">${level1.lessonName}</span>
						<strong> 
							<c:if test="${jiaoan}">
								<span id="span_level1_${level1.lessonId }_jiaoan" style="display: none;">
									<input type="checkbox" child="check_jiaoanAll" id="check_${level1.lessonId }_jiaoanAll" onclick="checkOrNot(this,'check_jiaoan');">
									教案：<span id="span_level1_${level1.lessonId }_jiaoan_num"></span>
								</span> 
							</c:if>
							<c:if test="${kejian}">
								<span id="span_level1_${level1.lessonId}_kejian" style="display: none;">
									<input type="checkbox" child="check_kejianAll" id="check_${level1.lessonId }_kejianAll" onclick="checkOrNot(this,'check_kejian');">
									课件：<span id="span_level1_${level1.lessonId}_kejian_num"></span>
								</span> 
							</c:if>
							<c:if test="${fansi}">
								<span id="span_level1_${level1.lessonId}_fansi" style="display: none;">
									<input type="checkbox" child="check_fansiAll" id="check_${level1.lessonId }_fansiAll" onclick="checkOrNot(this,'check_fansi');">
									反思：<span id="span_level1_${level1.lessonId}_fansi_num"></span>
								</span>
							</c:if>
					    </strong> <span class="arrow up"></span>
					</a>
					<ul class="menu" style="display: none;">
						<li>
							<ol class="menu1" id="level1_${level1.lessonId }">
							<c:if test="${jiaoan}">
								<c:forEach var="lessonPlan"
									items="${dataMap[level1.lessonId].jiaoanList }"
									varStatus="statu1">
										<li typename="jiaoan"><ui:icon
												ext="${lessonPlan.planType==1?'ppt':'doc' }"
												title="${lessonPlan.planName }"></ui:icon> <span
											title="${lessonPlan.planName }">教案${statu1.index+1 }</span>
											<input value="${lessonPlan.planId }" type="checkbox"
											class="li_box" name="check_${level1.lessonId }_jiaoan" child="check_jiaoan"
											<c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
										</li>
								</c:forEach>
							</c:if>
							<c:if test="${kejian}">
								<c:forEach var="lessonPlan"
									items="${dataMap[level1.lessonId].kejianList }"
									varStatus="statu1">
										<li typename="kejian"><ui:icon
												ext="${lessonPlan.planType==1?'ppt':'doc' }"
												title="${lessonPlan.planName }"></ui:icon> <span
											title="${lessonPlan.planName }">课件${statu1.index+1 }</span>
											<input value="${lessonPlan.planId }" type="checkbox" child="check_kejian"
											class="li_box" name="check_${level1.lessonId }_kejian"
											<c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
										</li>
								</c:forEach>
							</c:if>
							<c:if test="${fansi}">
								<c:forEach var="lessonPlan"
									items="${dataMap[level1.lessonId].fansiList }"
									varStatus="statu1">
										<li typename="fansi"><ui:icon
												ext="${lessonPlan.planType==1?'ppt':'doc' }"
												title="${lessonPlan.planName }"></ui:icon> <span
											title="${lessonPlan.planName }">反思${statu1.index+1 }</span>
											<input value="${lessonPlan.planId }" type="checkbox" child="check_fansi"
											class="li_box" name="check_${level1.lessonId }_fansi"
											<c:if test="${lessonPlan.isScan}">disabled="disabled"</c:if>>
										</li>
								</c:forEach>
							</c:if>
							</ol>
						</li>

					</ul>
				</c:if>
			</li>
		</c:if>
		<div class="clear"></div>
	</c:forEach>
</ul>
