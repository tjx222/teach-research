<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<title>模板列表</title>
</head>

<body>
    	<div>
    		<span>已上传的模板：</span>
    		<table>
    			<tr>
    				<th>序号</th><th>名称</th><th>科目</th><th>是否默认</th>
    			</tr>
    			<tr>
    				<td>1</td><td>${template.tpName }</td><td>${template.subjectId }</td><td><c:if test="${template.tpIsdefault == 1 }">默认</c:if></td>
    			</tr>
    		</table>
    	</div>
</body>
<script type="text/javascript">
window.parent.callback("${result}");
</script>
</html>