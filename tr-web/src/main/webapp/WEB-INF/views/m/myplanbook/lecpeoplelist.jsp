<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="我的备课本-听课意见用户列表"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/myplanbook/css/myplanbook.css" media="screen">
	<ui:require module="../m/myplanbook/js"></ui:require>
</head>
<body>
		<div class="class_comments_content">
			<table border="1" >
			  <tr>
			    <th>听课人</th>
			    <th>学科</th>
			    <th>年级</th>
			    <th>听课节数</th>
			    <th>操作</th>
			  </tr>
			 </table>
			 <div class="class_table">
				<div>
					 <table border="1" style="border-top: 0.083rem #fff solid;">
					 <c:forEach items="${lecpeoples }" var="data">
					 	<tr>
						    <td>${data.lecturePeople }</td>
						    <td>${data.subject }</td>
						    <td>${data.grade }</td>
						    <td>${data.numberLectures }节</td>
						    <td class="chakan_huifu" teachingpeopleId="${data.teachingpeopleId}" lecturepeopleId="${data.lecturepeopleId}" id="${data.id}" >
						    	<strong></strong><span>查看</span>
						    </td> 
						  </tr>
					 </c:forEach>
					</table>
				</div>
			</div>
		</div>

</body>
<script type="text/javascript">
	require(["zepto",'lec'],function(){	
	}); 
</script>
</html>