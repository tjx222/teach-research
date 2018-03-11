<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="计划总结"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/common/css/index_m.css" media="screen" />
	<link rel="stylesheet" href="${ctxStatic }/m/plainSummary/css/plainSummary.css" media="screen" />
	<ui:require module="../m/plainSummary/js"></ui:require>	
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>		
		<span onclick="javascript:window.history.go(-1);"></span>计划总结
		<div class="more" onclick="more()"></div>
	</header> 
	<section> 
		<div class="annunciate_bottom_wrap2" id="annunciate_b_wrap"> 
			<div id="scroller">
				<div class="annunciate_bottom" >
					<table> 
					  <tr> 
					     <th style="width:60%;text-align:left;padding-left: 1%;">标题</th>
					     <th style="width:25%;">发布时间</th>
					     <th style="width:10%;">作者</th>
					  </tr>
					  <tbody id="pageContent">
					  <c:forEach items="${data.datalist }" var="item">
						  <tr>
						     <td style="text-align:left;"><c:if test="${item.isViewed!=1 }"><a id="view_${item.id}"></a></c:if>
						     	<c:choose>
						     		<c:when test="${item.category==1 }">
						     			【个人计划】
						     		</c:when>
						     		<c:when test="${item.category==2 }">
						     			【个人总结】
						     		</c:when>
						     		<c:when test="${item.category==3 }">
						     			【年级计划】
						     		</c:when>
						     		<c:otherwise>
						     			【年级总结】
						     		</c:otherwise>
						     	</c:choose><span class="viewPlanSummary" data-id="${item.id}"> ${item.title }</span>
						     </td>
						     <td><fmt:formatDate value="${item.punishTime }" pattern="yyyy-MM-dd"/> </td> 
						     <td>${item.userName}</td>
						  </tr>
					  </c:forEach></tbody>					  
					</table>
					<form id="pageForm" name="pageForm" method="get">
						<ui:page url="./jy/planSummary/punishs" data="${data}" callback="addData" dataType="html"/>
						<input type="hidden" name="page.currentPage" class="currentPage" value="${data.currentPage }">
					</form>
					<c:if test="${data.totalCount==0 }">
						<div class="content_k"><dl><dd></dd><dt>还没有计划总结哟，稍后再来查看吧！</dt></dl></div>
					</c:if>
					<div style="height:2rem;"></div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','index_pain'],function($){	
	});  
</script>
</html>