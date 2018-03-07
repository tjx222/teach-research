<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>模板管理</title>

<script type="text/javascript">
function uploadTemplate(){
	form1.submit();
}
function callback(msg){
	alert(msg);
}
</script>
</head>
<body>
	<form name="form1" action="addTemplate" target="iframe1" method="POST" enctype="multipart/form-data">
	<div>
		<select name="subjectId">
			<option value="1">语文</option>
			<option value="2">数学</option>
			<option value="3">英语</option>
		</select>
		上传模板：<input type="file" name="tp_file"/><input type="button" value="上传" onclick="uploadTemplate();"/>
	</div>
    </form>
    
    
    <iframe name="iframe1">
    	<div>
    		<span>已上传的模板：</span>
    		<table>
    			<tr>
    				<th>序号</th><th>名称</th><th>科目</th><th>是否默认</th>
    			</tr>
    			<tr>
    				<td></td><td></td><td></td><td></td>
    			</tr>
    		</table>
    	</div>
    </iframe>
</body>
</html>