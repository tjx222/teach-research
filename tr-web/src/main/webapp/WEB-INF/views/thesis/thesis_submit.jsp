<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="提交教学文章"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/dlog_submit.css" media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<ui:require module="thesis/js"></ui:require>
</head>
<body style="background:#fff;">
<div class="clear"></div>
<div class="upload-bottom_submit_big">
		<div class="upload-bottom_submit_big_tab">
		   <div style="overflow:auto;height:600px;width: 800px;">
		<div class="upload-bottom_submit">
			<p>
				<input id="quanxuan" type="checkbox" class="all_select"> 
				<b>全选</b>
			</p>
			<c:if test="${isSubmit==0 || empty isSubmit}">
				<input type="button" class="submit2 submit_select" data-value="0" value="提交">
			</c:if>
			<c:if test="${isSubmit==1 }">
				<input type="button" class="submit1 submit_select" data-value="1" value="取消提交">
				<span>注意：禁选的教学文章表示上级领导已查阅，不允许取消提交！</span>
			</c:if>
		</div>
		<ul class="expmenu">
			
			<!-- 动态数据的遍历展示 -->
			<c:choose>
				<c:when test="${not empty notSubmitData || not empty submitData }">
					<c:if test="${not empty notSubmitData }">
						<ol class="menu1" style="">
							<c:forEach items="${notSubmitData}" var="data">
				               <li title="[${data.thesisType}]${data.thesisTitle }">
				               		<ui:icon ext="${data.fileSuffix }" height="50" width="60"></ui:icon>
				               		<span style="overflow: hidden;"><ui:sout value="[${data.thesisType}]${data.thesisTitle}" length="18" needEllipsis="true"></ui:sout></span>
				               		<input type="checkbox" name="thesis" id="kj_${data.id }" value="${data.id }" class="li_box">
				               	</li>
							</c:forEach>
			  			</ol>
		  			</c:if>
					<c:if test="${not empty submitData }">
						<ol class="menu1" style="">
							<c:forEach items="${submitData}" var="data">
				               <li title="[${data.thesisType}]${data.thesisTitle }">
				               		<ui:icon ext="${data.fileSuffix }" height="50" width="60"></ui:icon>
				               		<span style="overflow: hidden;"><ui:sout value="[${data.thesisType}]${data.thesisTitle}" length="18" needEllipsis="true"></ui:sout></span>
				               		<input type="checkbox" name="thesis" id="kj_${data.id }" value="${data.id }" class="li_box" <c:if test="${isSubmit==1 && data.isScan==1 }">disabled="disabled" checked</c:if>>
									<c:if test="${isSubmit==1 && data.isScan==1 }"><b class="d-chenkbox"></b></c:if>
				               	</li>
							</c:forEach>
			  			</ol>
					</c:if>
				</c:when>
				<c:otherwise>
					<!-- 无文件 -->
					<div class="empty_wrap">
					    <div class="empty_img"></div>
					    <div class="empty_info">
							<c:if test="${isSubmit==0 }">您没有可提交的教学文章!</c:if>
							<c:if test="${isSubmit==1 }">您没有可取消提交的教学文章!</c:if>
					    </div> 
					</div>
				</c:otherwise>
			</c:choose>
					
		</ul>
	</div>
</div>
</div>
	<script type="text/javascript">
		require([ 'jquery', 'jp/jquery-ui.min', 'jp/jquery.blockui.min', 'js','thesis_submit'],
				function() {
				});
	</script>
</body>
</html>
