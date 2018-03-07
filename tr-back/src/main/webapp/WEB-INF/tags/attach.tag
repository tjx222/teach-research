<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="src" type="java.lang.String" required="false" description="要显示的图像地址" %>
<%@ attribute name="style" type="java.lang.String" required="false" description="图像属性" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="图像宽" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="图像高" %>
<%@ attribute name="alt" type="java.lang.String" required="false" description="图像alt" %>
<%@ attribute name="function" type="java.lang.String" required="false" description="单击响应的方法" %>
<%@ attribute name="args" type="java.lang.String" required="false" description="单击传递的参数" %>
<%@ attribute name="filename" type="java.lang.String" required="false" description="附件名称" %>
<%@ attribute name="isdel" type="java.lang.Boolean" required="false" description="是否显示删除按钮" %>
<div style="position: relative;float: left;margin-right:20px;">
	<span id="${args}_btn">
	<a href="javascript:void(0);" onclick="scanResFile('${args}');">${filename}</a>
	<c:if test="${isdel==true}">
	<div class="img_editblock" id="${args}_btn"  onclick="${function}('${args}')" ></div>
	</c:if>
	</span>
</div><br>
<head>
<script type="text/javascript">
//资源文档浏览
function scanResFile(resId,ops){
	var url = _WEB_CONTEXT_+"/jy/scanResFile?resId="+resId;
	if(!ops){
		window.open(url,"_blank");
	}else{
		window.open(url,"_blank",ops);
	}
}
</script>
</head>
