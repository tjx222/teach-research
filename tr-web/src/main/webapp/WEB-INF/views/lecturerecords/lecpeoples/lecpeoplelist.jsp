<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="听课意见列表"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css">
<script type="text/javascript">
	//查看单个听课记录和回复
	function chakan_huifu(obj){
		var id=$(obj).attr("id");
		var teachingpeopleId=$(obj).attr("teachingpeopleId");
		var lecturepeopleId=$(obj).attr("lecturepeopleId");
		var url = _WEB_CONTEXT_+"/jy/lecturerecords/lecturereply?teachingpeopleId="+teachingpeopleId+"&lecturepeopleId="+lecturepeopleId+"&id="+id
		window.open(url);
	}
</script>
</head>

<body style="background:#fff;">
	<div class="collective_cont">
		<div class="collective_cont_big">
			<div class="collective_cont_tab">
				<c:if test="${empty lecpeoples}">
					<!-- <div style="text-align: center;font-size:20px;line-height:100px;">没有听课意见</div> -->
					<div class="empty_wrap" style="margin:50px auto;">
						<div class="empty_img"></div>
						<div class="empty_info">
						还没有教师对您进行听课哟，请稍后再查看吧！
						</div>
					</div>
				</c:if>
				
				<c:if test="${not empty lecpeoples}">
				<table style="width:100%;">
				  <tr>
				    <th style="width:250px;">听课人</th>
				    <th style="width:110px;">听课节数</th>
				    <th style="width:110px;">听课时间</th>
				    <th style="width:100px;">操作</th>
				  </tr>
				  <c:forEach var="kt" items="${lecpeoples}">
					  <tr>
					    <td>${kt.lecturePeople}</td>
					    <td>${kt.numberLectures}</td>
					    <td>
					    	<fmt:setLocale value="zh"/>
 							<fmt:formatDate value="${kt.lectureTime}" pattern="MM-dd"/>
					    <td style="text-decoration: underline;cursor: pointer;color:#2890fb;" 
					    	teachingpeopleId="${kt.teachingpeopleId}" lecturepeopleId="${kt.lecturepeopleId}"
					    	id="${kt.id}" onclick="chakan_huifu(this);">查看</td>
					  </tr>
				  </c:forEach>
				</table>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>