<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div id="noiticIndexId">
<%--树形结构加载多tabs首页 --%>
</div>
<script type="text/javascript">
function load(){
	var orgId ='${orgId}';
	$("#noiticIndexId").loadUrl( _WEB_CONTEXT_+"/jy/back/xxsy/tzgg/ptgg_index",{orgId:orgId},function(){
		$("#noiticIndexId").find("[layoutH]").layoutH();
	}		
	);
}
load();
</script>