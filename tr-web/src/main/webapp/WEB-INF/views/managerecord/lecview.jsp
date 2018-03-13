<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="管理记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/managerecord/css/managerecord_l.css" media="screen" />
	<ui:require module="../m/managerecord/js"></ui:require>	
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>听课记录
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="inneredit_content"> 
			<div>
			    <c:if test="${lr.type==0}">
			        <div class="inneredit_content_c"> 
						<h3>听课记录</h3>
						<table border="1">
							<tr>
								<td>课题</td>
								<td colspan="3">${lr.topic}</td>
								<td>评价等级</td>
								<td>${lr.evaluationRank}</td>
							</tr>
							<tr>
								<td>授课教师</td>
								<td>${lr.teachingPeople}</td>
								<td>学科</td>
								<td>${lr.subject}</td>
								<td>年级</td>
								<td>${lr.grade}</td>
							</tr>
							<tr>
								<td>听课人</td>
								<td>${lr.lecturePeople}</td>
								<td>听课时间</td>
								<td><fmt:setLocale value="zh"/><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/></td>
								<td>听课杰数</td>
								<td>${lr.numberLectures}</td>
							</tr>
							<tr> 
								<td colspan="6">
									<div style="width:100%;height:40rem">${lr.lectureContent}</div>
								</td>
							</tr>
						</table>
					</div>
			    </c:if>
				<c:if test="${lr.type==1}">
				    <div class="inneredit_content_c"> 
						<h3>听课记录</h3>
						<table border="1">
							<tr>
								<td>课题</td>
								<td colspan="3">${lr.topic}</td>
								<td>听课地点</td>
								<td>${lr.lectureAddress}</td>
							</tr>
							<tr>
								<td>授课教师</td>
								<td>${lr.teachingPeople}</td>
								<td>单位</td>
								<td>${lr.lectureCompany}</td>
								<td>年级学科</td>
								<td>${lr.gradeSubject}</td>
							</tr>
							<tr>
								<td>听课人</td>
								<td>${lr.lecturePeople}</td>
								<td>听课时间</td>
								<td><fmt:setLocale value="zh"/><fmt:formatDate value="${lr.lectureTime}" pattern="yyyy-MM-dd"/></td>
								<td>听课杰数</td>
								<td>${lr.numberLectures}</td>
							</tr>
							<tr> 
								<td colspan="6">
									<div style="width:100%;height:40rem">${lr.lectureContent}</div>
								</td>
							</tr>
						</table> 
					</div>
				</c:if>
			</div>
		</div> 
		
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','lecview'],function($){	
	});  
</script>
</html>