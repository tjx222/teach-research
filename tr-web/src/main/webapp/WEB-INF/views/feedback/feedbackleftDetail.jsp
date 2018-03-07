<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
</head>
<script type="text/javascript">
</script>
<body>
				<div class="question">
					<span id="test">问题描述：</span><strong>${recieve.message}</strong>
				</div>
				<div class="clear"></div>
				<div class="tab">
					<table border="0" id="" style="width:320px;margin:0 auto;"> 
						<tbody>
							<c:forEach items="${recieve.list}" var="relist">
							<tr style="width:160px;" value="aa6f3c5cafd1412f829d8c9337c7ca84">
								<td style="width:110px;float:left;" title=""> 
									<strong class="delete_fj"></strong>
									<a href="jy/scanResFile?resId=${relist.id}" target="_blank">${fn:substring(relist.name , 0, 4)}.${relist.ext}</a>
								</td>
								<!-- <td class="delete_d" onclick="removeRes('undefined',$(this));"> </td> -->
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="question">
					<span >反馈时间：</span><strong><fmt:formatDate value="${recieve.senderTime}" pattern="yyyy-MM-dd HH:mm"/></strong>
				</div>
				<div class="clear"></div>
				<br><h2><span>意见回复</span></h2>
				<!-- huifu begin -->
				<div class="feedback_l_13">
				<c:forEach items="${voList}" var="datalist">
				
				<div class="question">
					<span>意见回复：</span><strong>${datalist.replyContent}</strong>
				</div>
				<c:if test="${datalist.list !='[null]'}">
				
				<div class="tab">
					<table border="0" id="" style="width:320px;margin:0 auto;"> 
						<tbody>
							<c:forEach items="${datalist.list}" var="fulist">
							<tr style="width:160px;" value="aa6f3c5cafd1412f829d8c9337c7ca84">
								<td style="width:110px;float:left;" title=""> 
									<strong class="delete_fj"></strong>
									<a href="jy/scanResFile?resId=${fulist.id}" target="_blank">${fn:substring(fulist.name , 0, 5)}.${fulist.ext}</a>
									
								</td>
								<!-- <td class="delete_d" onclick="removeRes('undefined',$(this));"> </td> -->
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div></c:if>
				<div class="question">
					<span>反馈时间：</span><strong><fmt:formatDate value="${datalist.senderTime}" pattern="yyyy-MM-dd HH:mm"/></strong>
				</div>
				<div class="border"></div>
				</c:forEach>
				</div>
			<!-- huifu end -->
</body>
</html>