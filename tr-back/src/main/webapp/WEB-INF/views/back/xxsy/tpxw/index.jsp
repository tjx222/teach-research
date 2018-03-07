<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div id="picIndexId">
<%--树形结构加载多tabs首页 --%>
</div>
<script type="text/javascript">
function load(){
	var orgId ='${orgId}';
	$("#picIndexId").loadUrl( _WEB_CONTEXT_+"/jy/back/xxsy/tpxw/tpxw_index",{orgId:orgId},function(){
		$("#picIndexId").find("[layoutH]").layoutH();
	}		
	);
}
load();
</script>