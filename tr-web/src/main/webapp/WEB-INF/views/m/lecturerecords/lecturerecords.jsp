<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="听课记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/lecturerecords/css/lecturerecords.css" media="screen">
	<ui:require module="../m/lecturerecords/js"></ui:require>
</head>
<body>
<div class="opinions_comment_wrap" id="checkComment">
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>听课意见回复</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_scan" style="border:none;overflow:hidden;width:100%;height:31rem;" src="${ctx }jy/lecturereply/reply?resId=${lr.id}&authorId=${lr.lecturepeopleId}&teacherId=${lr.teachingpeopleId}"></iframe>
		<div class="my_publish1" style="height: 4.8rem;">
			<div class="my_publish1_left"> 
				<jy:di key="${_CURRENT_USER_.id }" className="com.tmser.tr.uc.service.UserService" var="cu"></jy:di>
				<ui:photo src="${cu.photo}" width="36" height="36"></ui:photo>
			</div>
			<div class="my_publish1_right">
				<input type="text" class="publish1" placeholder="有什么意见赶紧说出来吧！">
				<input type="button" class="publish1_btn" value="发送">
				<div class="left1"></div>
			</div>
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>
		查看听课记录
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="inneredit_content"> 
			<div>
				<div class="inneredit_content_c"> 
					<table border="1">
						<tr>
							<td>课题</td>
							<td colspan="3">
								${lr.topic}
							</td>
							<td>听课地点</td>
							<td>
								${lr.lectureAddress}
							</td>
						</tr>
						<tr>
							<td>授课教师</td>
							<td>
								${lr.teachingPeople}
							</td>
							<td>单位</td>
							<td>
								${lr.lectureCompany}
							</td>
							<td>年级学科</td>
							<td>
								${lr.gradeSubject}
							</td>
						</tr>
						<tr>
							<td>听课人</td>
							<td>${lr.lecturePeople}</td>
							<td>听课时间</td>
							<td><fmt:setLocale value="zh"/><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/></td>
							<td>听课节数</td>
							<td>
								${lr.numberLectures}
							</td>
						</tr>
						<tr> 
							<td colspan="6">
								${lr.lectureContent}
							</td>
						</tr>
					</table> 
					<c:if test="${pl=='1'}"><!--备课本查看过来有回复列表-->
						<div id="reply">回复</div>
					</c:if>
				</div>
			</div>
		</div> 
	</section>
</div>
<input type="hidden" id="authorId" value="${lr.lecturepeopleId}"/>
<input type="hidden" id="resId" value="${lr.id}"/>
<input type="hidden" id="teacherId" value="${lr.teachingpeopleId}"/>
</body>
<script type="text/javascript">
	require(["zepto",'outedit'],function(){	
	}); 
</script>
</html>