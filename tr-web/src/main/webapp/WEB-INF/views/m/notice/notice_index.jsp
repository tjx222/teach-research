<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="消息"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/notice/css/notice.css" media="screen" />
	<ui:require module="../m/notice/js"></ui:require>	
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1: param._HS_+1});"></span>
		<ul>
			<li><a <c:if test="${receiverState==0 }">class="header_act"</c:if> href="./jy/notice/notices?receiverState=0&_HS_=${empty param._HS_ ? 1: param._HS_+1}">未读通知</a></li>
			<li><a <c:if test="${receiverState==2 }">class="header_act"</c:if> href="./jy/notice/notices?receiverState=2&_HS_=${empty param._HS_ ? 1: param._HS_+1}">已读通知</a></li>
		</ul>
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="annunciate_bottom_wrap" id="annunciate_b_wrap"> 
			<div id="scroller">
				<div class="annunciate_bottom" >
					<table id="tableList"> 
					  <tr>
					     <th style="width:5%;"></th>
					     <th style="width:60%;text-align:left;padding-left: 1%;">标题</th>
					     <th style="width:25%;">发布时间</th>
					     <th style="width:10%;">操作</th>
					  </tr>
					  <c:forEach items="${ data.datalist}" var="item">
					  	  <tr>
						     <td><q dataId="${item.id }"></q></td>
						     <td style="text-align:left;">【${item.titlePrefix }】<span dataId="${item.id }"> <ui:sout value="${item.title }" length="93" needEllipsis="true"></ui:sout></span></td>
						     <td><fmt:formatDate value="${item.sendDate }" pattern="yyyy-MM-dd" /></td> 
						     <td class="del" dataId="${item.id }"></td>
						  </tr>
					  </c:forEach>
					  <!-- <tr>
					     <td><q class="radio_act"></q></td>
					     <td style="text-align:left;">【学校通知】<span> "美丽笑容 幸福校园"的通知</span></td>
					     <td>2015-06-12</td> 
					     <td class="del"></td>
					  </tr>
					  <tr>
					     <td><q></q></td>
					     <td style="text-align:left;">【学校通知】<span> "美丽笑容 幸福校园"的通知</span></td>
					     <td>2015-06-12</td> 
					     <td class="del"></td>
					  </tr>
					   <tr>
					     <td><q></q></td>
					     <td style="text-align:left;">【学校通知】<span> "美丽笑容 幸福校园"的通知</span></td>
					     <td>2015-06-12</td> 
					     <td class="del"></td>
					  </tr> -->
					  
					</table>
					<form  name="pageForm">
						<ui:page url="${ctx}jy/notice/notices_mobile" data="${data}"  callback="addData" dataType="true"/>
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden" name="receiverState" value="${receiverState}">
					</form>
					<div style="height:2rem;"></div>
				</div>
			</div>
		</div>
		<div class="annunciate_bottom1_wrap">
			<div class="annunciate_bottom1" >
				<div class="notice">
					<q></q><span>全选</span>
				</div>
				<input type="button" class="delete" value="删除">
				<c:if test="${param.receiverState != '2' }">
				<input type="button" class="yd" value="标记已读">
				</c:if>
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