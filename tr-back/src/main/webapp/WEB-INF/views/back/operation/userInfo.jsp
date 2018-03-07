<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
.form_cont{padding-left:10px;font-size:14px;line-height: 35px;}
.form_cont select{margin-right:10px;width:120px;height:24px;}
.date{width: 120px; background-color: #fff;  height: 19px;}
</style>
<div class="tabsContent" id="tmpcontent">
	<div class="pageHeader" style="height:auto;border: 1px #B8D0D6 solid;">
		<div style="font-size:18px;height:35px;line-height:35px;padding-left:10px;font-family: '微软雅黑';">
			${org.name }教研一览<!--  <input type="button" onclick="fanhui();" value="返回" style="float:right;width: 50px;height: 24px; margin-top: 6px;">  -->
		</div>
	</div>
	<div class="tabs" style="margin-top: 5px;" currentIndex="0" eventType="click">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="${ctx}jy/back/operation/toTeacherOperationInfoList?orgId=${org.id}" class="j-ajax" target="ajax" rel="operation_info" id="operation_teacher"><span>教师教研情况一览</span></a></li>
				<li><a href="${ctx}jy/back/operation/toLeaderOperationInfoList?orgId=${org.id}" class="j-ajax" target="ajax" rel="operation_info" id="operation_leader"><span>学校管理情况一览</span></a></li>
			</ul>
		</div>
	</div>
	</div>
	<div id="operation_info">
		
	</div>
</div>
<script>
$(document).ready(function(){
	$("#operation_info").loadUrl("${pageContext.request.contextPath}/jy/back/operation/toTeacherOperationInfoList?orgId=${org.id}",{},function(){
		$("#operation_info").find("[layoutH]").layoutH();
	});
});
function fanhui(){
	$("#operationId").loadUrl("${pageContext.request.contextPath}/jy/back/operation/toOrgOperationInfoList?areaId=${search.areaId}",{},function(){
		$("#operation_load").find("[layoutH]").layoutH();
	});
}
</script>