<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="通知公告"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/annunciate/css/annunciate.css" media="screen" />
	<ui:require module="../m/annunciate/js"></ui:require>	
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>通知公告
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="annunciate_bottom_wrap2" id="annunciate_b_wrap" > 
			<div id="scroller">
				<div class="annunciate_bottom" >
					<table> 
					  <tr> 
					 	 <th style="width:5%;text-align:left;padding-left: 1%;"></th>
					     <th style="width:60%;text-align:left;padding-left: 1%;">标题</th>
					     <th style="width:25%;">发布时间</th>
					     <th style="width:10%;">作者</th>
					  </tr>
					  <tbody  id="annunciateform">
					  <c:forEach items="${pagelist.datalist}" var="p">
						  <tr>
							  <td style="text-align:left;">
							  	  <c:if test="${p.redTitleId!=0}">
									<q></q>
							  </c:if>
							  </td>
						     <td style="text-align:left;">
						        <c:if test="${p.isViewed!=1}"><b></b></c:if>
						     	<c:if test="${p.orgId==-1}">【系统通知】</c:if>
						     	<c:if test="${p.orgId!=-1}">【学校通知】</c:if>
						     	<span> 
						     		<a href="${ctx}/jy/annunciate/readedPunishAnnunciates?id=${p.id}&&status=${p.status}&&type=1"  style="color:#169bd5;">
							     		<strong title="${p.title}">
							     			<ui:sout value="${p.title}" length="50" needEllipsis="true"></ui:sout>
							     		</strong>
										
									</a>
								</span>
							</td>
						     <td><fmt:formatDate value="${p.crtDttm}"
											pattern="yyyy-MM-dd" /></td> 
							<jy:di key="${p.crtId}"
										className="com.tmser.tr.uc.service.UserService" var="u"/>
						     <td>${u.name}</td>
						  </tr>
					  </c:forEach></tbody>
 				    </table>
 				    <form name="pageForm" method="post">
						<ui:page url="${ctx}/jy/annunciate/noticeIndex" data="${pagelist}" callback="addData" dataType="html"/>
						<input type="hidden" class="currentPage" name="currentPage">
					</form>
 				    
 				    <c:if test="${annunciateNum==0  }">
						<div class="content_k"><dl><dd></dd><dt>还没有通知公告哟，稍后再来查看吧！</dt></dl></div>
					</c:if>
					<div style="height:2rem;"></div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','js'],function($){	
	});  
</script>
</html>