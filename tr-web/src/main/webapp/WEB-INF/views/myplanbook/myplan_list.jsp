<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
</head>
<body>
	<div class="home_cont_r1">
	<h3 class="home_cont_r1_h3">
		<span>${lessonInfo.lessonName }</span>
		<span>教案：${countData1.jiaoanCount }</span>
		<span>课件：${countData1.kejianCount }</span>
		<span>反思：${countData1.fansiCount }</span>
	</h3>
	<div class="clear"></div>
	<div class="home_cont_r1_bottom">
 		<div class="Pre_cont_right_1">
			<c:if test="${not empty fansiList || not empty lessonPlanList || not empty kejianList}">
	 			<c:forEach var="temp" items="${lessonPlanList }">
	 				<div class="Pre_cont_right_1_dl">
						<dl> 
							<dd <c:if test="${!temp.isShare && !temp.isSubmit}">onclick="editIt('${temp.planId}','${temp.planType }','${temp.lessonId}');"</c:if><c:if test="${temp.isShare || temp.isSubmit}">onclick="scanLessonPlan('${temp.planId }');"</c:if> ><ui:icon ext="doc"></ui:icon></dd>
							<dt>
								<c:choose>
									<c:when test="${temp.hoursId=='-1'}">
										<span style="width:138px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" title="<ui:sout value="不分课时"  needEllipsis="true"></ui:sout>"><ui:sout value="不分课时" length="44" needEllipsis="true"></ui:sout></span><br/>
									</c:when>
									<c:when test="${temp.hoursId=='0'}">
										<span style="width:138px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" title="<ui:sout value="简案"  needEllipsis="true"></ui:sout>"><ui:sout value="简案" length="44" needEllipsis="true"></ui:sout></span><br/>
									</c:when>
									<c:otherwise>
										<span style="width:138px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" title="<ui:sout value="第${temp.hoursId }课时"  needEllipsis="true"></ui:sout>"><ui:sout value="第${temp.hoursId }课时" length="44" needEllipsis="true"></ui:sout></span><br/>
									</c:otherwise>
								</c:choose>
								<strong><fmt:formatDate value="${temp.crtDttm }" pattern="yyyy-MM-dd" /> </strong>
							</dt>
						</dl>
						<div class="show_div show_div2"></div>
						<div class="show_p">
							<ol>
								<c:if test="${!temp.isShare && !temp.isSubmit}"><li title="修改" class="li_edit" onclick="editIt('${temp.planId}','${temp.planType }','${temp.lessonId}');"></li></c:if>
								<c:if test="${temp.isShare || temp.isSubmit}"><li title="禁止修改" class="jz_li_edit"></li></c:if>
								<c:if test="${!temp.isShare && !temp.isSubmit}">
									<li title="删除" class="li_del" onclick="deleteIt('${temp.planId}');"></li>
								</c:if>
								<c:if test="${temp.isShare || temp.isSubmit}">
									<li title="禁止删除" class="jz_li_del"></li>
								</c:if>
								<c:if test="${temp.isShare }"><li title="取消分享" class="qx_li_share" onclick="unShareIt('${temp.planId}');"></li></c:if>
								<c:if test="${!temp.isShare }"><li title="分享" class="li_share" onclick="shareIt('${temp.planId}');"></li></c:if>
								<a href="<ui:download resid="${temp.resId }" filename="${temp.planName }"></ui:download>" target="_blank"><li class="li_down" title="下载"></li></a>
								<c:if test="${temp.isComment }">
									<c:if test="${temp.commentUp }">
										<li title="显示评论意见" class="li_ping menu_li_6_1" onclick="updateIcon_comment(this);parent.showCommentListBox('${temp.planType}','${temp.planId}',true,'${lessonInfo.lessonId}');"><span class="spot"></span></li>
									</c:if>
									<c:if test="${!temp.commentUp }">
										<li title="显示评论意见" class="li_ping menu_li_6" onclick="parent.showCommentListBox('${temp.planType}','${temp.planId}',false,'${lessonInfo.lessonId}');"></li>
									</c:if>
								</c:if>
							</ol>
						</div>
					</div>  
				</c:forEach> 
	 			<c:forEach var="temp" items="${kejianList }" varStatus="status">
	 				<jy:ds key="${temp.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
					<div class="Pre_cont_right_1_dl">
						<dl>
							<dd onclick="scanLessonPlan('${temp.planId }');"><ui:icon ext="${res.ext}"></ui:icon></dd>
							<dt>
								<span><ui:sout value="课件${status.first?'':status.count}" length="44" needEllipsis="true"></ui:sout></span><br/>
								<strong><fmt:formatDate value="${temp.crtDttm }" pattern="yyyy-MM-dd" /> </strong>
							</dt>
						</dl>
						<div class="show_div show_div1"></div>
						<div class="show_p">
							<ol>
								<c:if test="${!temp.isShare && !temp.isSubmit && temp.origin != 1}"><li title="修改" class="li_edit" onclick="editIt('${temp.planId}','${temp.planType }','${temp.lessonId}');"></li></c:if>
								<c:if test="${temp.isShare || temp.isSubmit || temp.origin==1 }"><li title="禁止修改" class="jz_li_edit"></li></c:if>
								<c:if test="${!temp.isShare && !temp.isSubmit}">
									<li title="删除" class="li_del" onclick="deleteIt('${temp.planId}');"></li>
								</c:if>
								<c:if test="${temp.isShare || temp.isSubmit}">
									<li title="禁止删除" class="jz_li_del"></li>
								</c:if>
								<c:if test="${temp.isShare }"><li title="取消分享" class="qx_li_share" onclick="unShareIt('${temp.planId}');"></li></c:if>
								<c:if test="${!temp.isShare }"><li title="分享" class="li_share" onclick="shareIt('${temp.planId}');"></li></c:if>
								<c:if test="${temp.origin != 1}">
									<a href="<ui:download resid="${temp.resId }" filename="${temp.planName }"></ui:download>" target="_blank"><li class="li_down" title="下载"></li></a>
								</c:if>
								<c:if test="${temp.isComment }">
									<c:if test="${temp.commentUp }">
										<li title="显示评论意见" class="li_ping menu_li_6_1" onclick="updateIcon_comment(this);parent.showCommentListBox('${temp.planType}','${temp.planId}',true,'${lessonInfo.lessonId}');"><span class="spot"></span></li>
									</c:if>
									<c:if test="${!temp.commentUp }">
										<li title="显示评论意见" class="li_ping menu_li_6" onclick="parent.showCommentListBox('${temp.planType}','${temp.planId}',false,'${lessonInfo.lessonId}');"></li>
									</c:if>
								</c:if>
							</ol>
						</div>
					</div>
				</c:forEach>
				<c:forEach var="temp" items="${fansiList }" varStatus="status">
					<div class="Pre_cont_right_1_dl">
						<dl> 
							<jy:ds key="${temp.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"/>
							<dd onclick="scanLessonPlan('${temp.planId }');"><ui:icon ext="${res.ext}"></ui:icon></dd>
							<dt>
								<span><ui:sout value="反思${status.first?'':status.count}" length="44" needEllipsis="true"></ui:sout></span><br/>
								<strong><fmt:formatDate value="${temp.crtDttm }" pattern="yyyy-MM-dd" /> </strong>
							</dt>
						</dl>
						<div class="show_div show_div3"></div>
						<div class="show_p">
							<ol>
								<c:if test="${!temp.isShare && !temp.isSubmit}"><li title="修改" class="li_edit" onclick="editIt('${temp.planId}','${temp.planType }','${temp.lessonId}');"></li></c:if>
								<c:if test="${temp.isShare || temp.isSubmit}"><li title="禁止修改" class="jz_li_edit"></li></c:if>
								<c:if test="${!temp.isShare && !temp.isSubmit}">
									<li title="删除" class="li_del" onclick="deleteIt('${temp.planId}');"></li>
								</c:if>
								<c:if test="${temp.isShare || temp.isSubmit}">
									<li title="禁止删除" class="jz_li_del"></li>
								</c:if>
								<c:if test="${temp.isShare }"><li title="取消分享" class="qx_li_share" onclick="unShareIt('${temp.planId}');"></li></c:if>
								<c:if test="${!temp.isShare }"><li title="分享" class="li_share" onclick="shareIt('${temp.planId}');"></li></c:if>
								<a href="<ui:download resid="${temp.resId }" filename="${temp.planName }"></ui:download>" target="_blank"><li class="li_down" title="下载"></li></a>
								<c:if test="${temp.isComment }">
									<c:if test="${temp.commentUp }">
										<li title="显示评论意见" class="li_ping menu_li_6_1" onclick="updateIcon_comment(this);parent.showCommentListBox('${temp.planType}','${temp.planId}',true,'${lessonInfo.lessonId}');"><span class="spot"></span></li>
									</c:if>
									<c:if test="${!temp.commentUp }">
										<li title="显示评论意见" class="li_ping menu_li_6" onclick="parent.showCommentListBox('${temp.planType}','${temp.planId}',false,'${lessonInfo.lessonId}');"></li>
									</c:if>
								</c:if>
							</ol>
						</div>
					</div>  
				</c:forEach>
			</c:if>
			<c:if test="${empty fansiList && empty lessonPlanList && empty kejianList}">  
				<div class="empty_wrap">
				    <div class="empty_img"></div>
				    <div class="empty_info">暂无资源,稍后再来看吧！</div> 
				</div>
			</c:if>
		</div>
		<div class="Pre_cont_right_2">
			<div class="Pre_cont_right_2_l">
				<c:if test="${lessonInfo.scanUp }">
					<dl onclick="showScanListBox('0','${lessonInfo.id}',true,'${lessonInfo.lessonId}');">
						<dd></dd>
						<dt id="right_2_right_1_click">查阅意见（${lessonInfo.scanCount }条）<span class="spot1"></span></dt>
					</dl>
				</c:if>
				<c:if test="${!lessonInfo.scanUp }">
					<c:choose>
						<c:when test="${empty lessonInfo || lessonInfo.scanCount==0 }">
							<dl>
								<dd></dd>
								<dt id="right_2_right_1_click">查阅意见（0条）</dt>
							</dl>
						</c:when>
						<c:otherwise>
							<dl onclick="showScanListBox('0','${lessonInfo.id}',false,'${lessonInfo.lessonId}');">
								<dd></dd>
								<dt id="right_2_right_1_click" >查阅意见（${lessonInfo.scanCount }条）</dt>
							</dl>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
			<div class="Pre_cont_right_2_r">
				<c:if test="${lessonInfo.visitUp }">
					<dl onclick="showVisitListBox('${lessonInfo.id}',true,'${lessonInfo.lessonId}');">
						<dd></dd>
						<dt>听课意见（${lessonInfo.visitCount}条）<span class="spot1"></span></dt>
					</dl>
				</c:if>
				<c:if test="${!lessonInfo.visitUp }">
					<c:choose>
						<c:when test="${empty lessonInfo || lessonInfo.visitCount==0 }">
							<dl>
								<dd></dd>
								<dt>听课意见（0条）</dt>
							</dl>
						</c:when>
						<c:otherwise>
							<dl onclick="showVisitListBox('${lessonInfo.id}',false,'${lessonInfo.lessonId}');">
								<dd></dd>
								<dt>听课意见（${lessonInfo.visitCount}条）</dt>
							</dl>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
		</div>
 	</div>
	<div id="book_option" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">查阅意见列表</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<iframe id="modify" name="modify" width="630" height="362" scroll="no"
					style="border: none; overflow: hidden;"></iframe>
			</div>
		</div>
	</div>
	</div>
</body>
</html>