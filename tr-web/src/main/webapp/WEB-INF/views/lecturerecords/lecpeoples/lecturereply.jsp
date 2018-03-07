<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看听课意见"></ui:htmlHeader>
	<script type="text/javascript">
		$().ready(function(){
			$("#view").attr("src","jy/lecturerecords/seetopic?id="+${records.id});//查看单个听课记录iframe
		});
		
		//加载完之后再执行
		function iframeLoad(){
			var t=$('#huifu',window.frames["view"].document);//父页面取儿子页面上的属性
			t.append("<iframe width=\"100%\" height=\"800px\" frameborder=\"0\" id=\"commentBox\" onload=\"setCwinHeight(this,false,100)\" scrolling=\"yes\" style='border:0;border:none;' src=\"jy/lecturereply/reply?resId="+${records.id}+"&authorId="+${records.lecturepeopleId}+"&teacherId="+${records.teachingpeopleId}+"\"></iframe>");
		}
	</script>
</head>

<body> 
	<div>
		<iframe id="view" width="100%" height="1900px;" frameborder="0" scrolling="no" style="border:none;border:0;"name="view" onload="iframeLoad();"></iframe>
	</div>
</body>
</html>